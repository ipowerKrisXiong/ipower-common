package com.ipower.framework.common.core.session;

import com.alibaba.fastjson2.TypeReference;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.ipower.framework.common.core.collection.Lists;
import com.ipower.framework.common.core.constant.StringPool;
import com.ipower.framework.common.core.lang.ObjectUtil;
import com.ipower.framework.common.core.map.Maps;
import com.ipower.framework.common.core.text.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.ipower.framework.common.core.lang.ObjectUtil.*;

/**
 * Class description goes here.
 *
 * @author kris
 * @date 2020/1/4
 */
public class Users {
    
    /**
     * 私有化构造函数，禁止实例化该类
     */
    private Users() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /////////////////////////////////////////////// 属性数据 /////////////////////////////////////////////

    /**
     * 日志
     */
    private static final Logger log = LoggerFactory.getLogger(Users.class);

    /**
     * 当前线程，子线程可以传递
     */
    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new TransmittableThreadLocal<>();

    /**
     * 用户信息实体
     */
    public static final String KEY_USER = "user";

    /** x
     * 用户id的key值
     */
    public static final String KEY_USER_ID = "userId";

    /**
     * 用户账号key名称
     */
    public static final String KEY_USER_NAME = "userName";

    /**
     * 用户名称的key名称
     */
    public static final String KEY_NAME = "name";

    /**
     * 用户角色id集合key名称
     */
    public static final String KEY_ROLE_IDS = "roleIds";

    /**
     * 用户角色编码集合key名称
     */
    public static final String KEY_ROLE_CODES = "roleCodes";

    /**
     * 用户部门id集合key名称
     */
    public static final String KEY_DEPT_IDS = "deptIds";

    /**
     * token的key名称
     */
    public static final String KEY_TOKEN = "token";

    /**
     * 租户id的key名称
     */
    public static final String KEY_TENANT_ID = "tenantId";

    /**
     * 租户组 key名称
     */
    public static final String KEY_TENANT_IDS = "tenantIds";

    /**
     * 企业id的key名称
     */
    private static final String KEY_ENTERPRISE_ID = "enterpriseId";

    /**
     * 企业id的key名称
     */
    private static final String KEY_ENTERPRISE_NAME = "enterpriseName";
    
    /**
     * 语言key名称
     */
    private static final String KEY_LANGUAGE = "language";

    /////////////////////////////////////////////// 对外提供的功能方法 /////////////////////////////////////////////

    //------------------>> 取值

    /**
     * 获取当前用户的id值
     *
     * @return Long 用户id
     */
    public static Long id() {
        return parse(KEY_USER_ID, Users::setAndGetId, Long.class, 0L);
    }

    /**
     * 获取当前用户的登录账号
     *
     * @return String 用户名称
     * @deprecated 已弃用，推荐使用{@link Users#userName()}
     */
    @Deprecated
    public static String loginName() {
        return userName();
    }

    /**
     * 获取当前用户的登录账号
     *
     * @return String 登录账号
     */
    public static String userName() {
        return parseString(KEY_USER_NAME, Users::setAndGetUserName);
    }

    /**
     * 获取当前用户的名称
     *
     * @return String 用户名称
     */
    public static String name() {
        return parseString(KEY_NAME, Users::setAndGetName);
    }

    /**
     * 获取当前用户的角色id集合
     *
     * @return List<String> 角色id集合
     */
    public static List<Long> roleIds() {
        return parseList(KEY_ROLE_IDS, Users::setAndGetRoleIds);
    }

    /**
     * 获取当前用户的角色编码集合
     *
     * @return List<String> 角色编码集合
     */
    public static List<String> roleCodes() {
        return parseList(KEY_ROLE_CODES, Users::setAndGetRoleCodes);
    }

    /**
     * 获取当前用户的部门id集合
     *
     * @return List<Long> 部门id集合
     */
    public static List<Long> deptIds() {
        return parseList(KEY_DEPT_IDS, Users::setAndGetDeptIds);
    }

    /**
     * 获取token信息，从本地线程中获取
     *
     * @return String token信息
     */
    public static String token() {
        String token = parseFromLocal(KEY_TOKEN, String.class);
        return isNotEmpty(token) ? token : StringPool.EMPTY;
    }

    /**
     * 获取租户id,从本地线程中获取
     *
     * @return String tenantId信息
     */
    public static Long tenantId() {
        Object obj = parse(KEY_TENANT_ID, Users::setAndGetTenantId, Object.class, null);
        return isNull(obj) ? null : Long.valueOf(obj.toString());
    }

    /**
     * 获取是否集团内部,从本地线程中获取
     *
     * @return boolean intraGroup信息
     */
    public static List<Long> tenantIds() {
        List<?> objectList = parseList(KEY_TENANT_IDS, Users::setAndGetTenantIds);
        return objectList.stream().filter(ObjectUtil::isNotEmpty)
                .map(o -> Long.valueOf(o.toString())).collect(Collectors.toList());
    }
    
    public static Long enterpriseId() {
        return parse(KEY_ENTERPRISE_ID, Users::setAndGetEnterpriseId, Long.class, 0L);
    }
    
    public static String enterpriseName() {
        return parse(KEY_ENTERPRISE_NAME, Users::setAndGetEnterpriseName, String.class, null);
    }
    
    public static String language() {
        return parseString(KEY_LANGUAGE, Users::setAndGetLanguage);
    }

    /**
     * 根据key值，从当前线程中获取值
     *
     * @param key key值
     * @return Object key配置本地线程中的值
     */
    public static Object get(String key) {
        return localMap().get(key);
    }

    /**
     * 从Spring security 中，获取当前用户信息
     */
    public static JwtUser getUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (isNotNull(authentication)) {
                Object object = authentication.getPrincipal();
                if (object instanceof JwtUser) {
                    return (JwtUser) object;
                }
            }
        } catch (Exception e) {
            log.warn("获取security鉴权用户失败:" + e.getMessage());
        }
        return null;
    }

    //------------------>> 赋值

    /**
     * 本地线程中设置用户id值
     *
     * @param id 用户id值
     */
    public static void setId(Long id) {
        set(KEY_USER_ID, id);
    }

    /**
     * 本地线程中设置用户id值，并返回id值
     *
     * @param id 用户id值
     * @return Long 用户id值
     */
    public static Long setAndGetId(Long id) {
        setId(id);
        return id;
    }

    /**
     * 本地线程中设置用户账户名
     *
     * @param userName 用户账户名
     */
    public static void setUserName(String userName) {
        set(KEY_USER_NAME, userName);
    }

    /**
     * 本地线程中设置用户账户名，并返回用户账户名
     *
     * @param userName 用户账户名
     * @return String 用户账户名
     */
    public static String setAndGetUserName(String userName) {
        setUserName(userName);
        return userName;
    }

    /**
     * 本地线程中设置用户名称
     *
     * @param name 用户名称
     */
    public static void setName(String name) {
        set(KEY_NAME, name);
    }

    /**
     * 本地线程中设置用户名称，并返回用户名称
     *
     * @param name 用户名称
     * @return String 用户名称
     */
    public static String setAndGetName(String name) {
        setName(name);
        return name;
    }

    /**
     * 本地线程中设置用户角色id集合
     *
     * @param roleIds 用户角色id集合
     */
    public static void setRoleIds(List<Long> roleIds) {
        set(KEY_ROLE_IDS, roleIds);
    }

    /**
     * 本地线程中设置用户角色id集合，并返回用户角色id集合
     *
     * @param roleIds 用户角色id集合
     * @return List<Long> 用户角色id集合
     */
    public static List<Long> setAndGetRoleIds(List<Long> roleIds) {
        setRoleIds(roleIds);
        return roleIds;
    }

    /**
     * 本地线程中设置用户角色编码集合
     *
     * @param roleCodes 用户角色编码集合
     */
    public static void setRoleCodes(List<String> roleCodes) {
        set(KEY_ROLE_CODES, roleCodes);
    }

    /**
     * 本地线程中设置用户角色编码集合
     *
     * @param roleCodes 用户角色编码集合，并返回用户角色编码集合
     * @return List<String> 用户角色编码集合
     */
    public static List<String> setAndGetRoleCodes(List<String> roleCodes) {
        setRoleCodes(roleCodes);
        return roleCodes;
    }


    /**
     * 本地线程中设置用户部门id集合
     *
     * @param deptIds 用户部门id集合
     */
    public static void setDeptIds(List<Long> deptIds) {
        set(KEY_DEPT_IDS, deptIds);
    }

    /**
     * 本地线程中设置用户部门id集合，并返回用户部门id集合
     *
     * @param deptIds 用户部门id集合
     * @return List<Long> 用户部门id集合
     */
    public static List<Long> setAndGetDeptIds(List<Long> deptIds) {
        setDeptIds(deptIds);
        return deptIds;
    }

    /**
     * 本地线程中设置token信息
     *
     * @param token token信息
     */
    public static void setToken(String token) {
        set(KEY_TOKEN, token);
    }

    /**
     * 本地线程中设置token信息，并返回token信息
     *
     * @param token token信息
     * @return String token信息
     */
    @SuppressWarnings("unused")
    public static String setAndGetToken(String token) {
        setToken(token);
        return token;
    }

    /**
     * 本地线程中设置tenantId信息
     *
     * @param tenantId tenantId信息
     */
    public static void setTenantId(Object tenantId) {
        set(KEY_TENANT_ID, tenantId);
    }

    /**
     * 本地线程中设置租户id
     *
     * @param tenantId 租户id
     * @return Long 租户id
     */
    private static Object setAndGetTenantId(Object tenantId) {
        setTenantId(tenantId);
        return tenantId;
    }

    /**
     * 本地线程中设置tenantIds 信息
     *
     * @param tenantIds 租户集合
     */
    public static void setTenantIds(List<?> tenantIds) {
        set(KEY_TENANT_IDS, tenantIds);
    }

    /**
     * 本地线程中设置是否集团内部
     *
     * @param tenantIds 租户id集合
     * @return List<Long>
     */
    private static List<?> setAndGetTenantIds(List<?> tenantIds) {
        setTenantIds(tenantIds);
        return tenantIds;
    }
    
    /**
     * 本地线程中设置enterpriseId信息
     *
     * @param enterpriseId 企业id
     */
    public static void setEnterpriseId(Long enterpriseId) {
        set(KEY_ENTERPRISE_ID, enterpriseId);
    }

    /**
     * 本地线程中设置并获取企业id
     *
     * @param enterpriseId 企业id
     * @return Long
     */
    private static Long setAndGetEnterpriseId(Long enterpriseId) {
        setEnterpriseId(enterpriseId);
        return enterpriseId;
    }

    /**
     * 本地线程中设置enterpriseName信息
     *
     * @param enterpriseName 企业名称
     */
    public static void setEnterpriseName(String enterpriseName) {
        set(KEY_ENTERPRISE_NAME, enterpriseName);
    }

    /**
     * 本地线程中设置并获取企业名称
     *
     * @param enterpriseName 企业名称
     * @return String
     */
    private static String setAndGetEnterpriseName(String enterpriseName) {
        setEnterpriseName(enterpriseName);
        return enterpriseName;
    }

    public static void setLanguage(String language) {
        set(KEY_LANGUAGE, language);
    }
    
    private static String setAndGetLanguage(String language) {
        setLanguage(language);
        return language;
    }

    /**
     * 设置值至当前线程中
     */
    public static void set(String key, Object value) {
        localMap().put(key, value);
    }

    //------------------>> 删除

    /**
     * 本地线程删除数据
     */
    public static void remove() {
        THREAD_LOCAL.remove();
    }

    /**
     * 本地线程删除数据
     */
    public static void remove(String key) {
        localMap().remove(key);
    }

    //------------------>> 打印

    /**
     * 打印当前线程信息
     */
    public static void print() {
        if (log.isDebugEnabled()) {
            log.debug("Current Thread:{}", Thread.currentThread());
            log.debug("Users ThreadLocal:{}", JsonUtil.toJson(localMap()));
        }
    }

    /////////////////////////////////////////////// 私有方法 /////////////////////////////////////////////

    /**
     * 获取当前线程中的map对象
     */
    private static Map<String, Object> localMap() {
        Map<String, Object> map = THREAD_LOCAL.get();
        if (isNull(map)) {
            map = Maps.hashMap();
            THREAD_LOCAL.set(map);
        }
        return map;
    }

    /**
     * 解析指定泛型类型的数据
     */
    public static <T> T parse(String key, Function<T, T> function, Class<T> clazz, T defaultValue) {
        // 优先从当前线程中获取
        T value = parseFromLocal(key, clazz);
        if (isNotNull(value)) {
            return value;
        }
        // 从JwtUser中解析，如果为空，返回默认值""
        value = parseFromJwtUser(key, clazz);
        return isNotNull(value) ? function.apply(value) : defaultValue;
    }

    /**
     * 解析字符串类型的数据
     */
    public static String parseString(String key, Function<String, String> function) {
        // 优先从当前线程中获取
        String value = parseFromLocal(key, String.class);
        if (isNotEmpty(value)) {
            return value;
        }
        // 从JwtUser中解析，如果为空，返回默认值""
        value = parseFromJwtUser(key, String.class);
        return isNotEmpty(value) ? function.apply(value) : StringPool.EMPTY;
    }

    /**
     * 解析集合类型的数据
     */
    private static <T> List<T> parseList(String key, Function<List<T>, List<T>> function) {
        // 优先从当前线程中获取
        TypeReference<List<T>> reference = new TypeReference<>() {};
        List<T> values = parseFromLocal(key, reference);
        if (isNotNull(values)) {
            return values;
        }
        // 从JwtUser中解析，如果为空，返回默认值""
        values = parseFromJwtUser(key, reference);
        return isNotNull(values) ? function.apply(values) : Lists.arrayList();
    }

    /**
     * 解析本地线程中的数据值，指定响应的泛型
     */
    @SuppressWarnings("unused")
    private static <T> T parseFromLocal(String key, Class<T> clazz) {
        return parseFromLocal(key);
    }

    /**
     * 解析本地线程中的数据值，指定响应的赋值结构泛型
     */
    @SuppressWarnings("unused")
    private static <T> T parseFromLocal(String key, TypeReference<T> reference) {
        return parseFromLocal(key);
    }

    /**
     * 解析本地线程中的数据值
     */
    @SuppressWarnings("unchecked")
    private static <T> T parseFromLocal(String key) {
        return (T) get(key);
    }

    /**
     * 解析JwtUser中的数据值，指定响应的泛型
     */
    @SuppressWarnings("unused")
    private static <T> T parseFromJwtUser(String key, Class<T> clazz) {
        return parseFromJwtUser(key);
    }

    /**
     * 解析JwtUser中的数据值，指定响应的赋值结构泛型
     */
    @SuppressWarnings("unused")
    private static <T> T parseFromJwtUser(String key, TypeReference<T> reference) {
        return parseFromJwtUser(key);
    }

    /**
     * 解析JwtUser中的数据值
     */
    @SuppressWarnings("unchecked")
    private static <T> T parseFromJwtUser(String key) {
        //1. 优先获取用户，如果用户为空，直接返回null
        JwtUser user = getUser();
        if (isNull(user)) {
            return null;
        }
        return switch (key) {
            case KEY_USER_ID -> (T) user.getId();
            case KEY_USER_NAME -> (T) user.getUsername();
            case KEY_NAME -> (T) user.getName();
            case KEY_ROLE_IDS -> (T) user.getRoleIds();
            case KEY_ROLE_CODES -> (T) user.getRoleCodes();
            case KEY_DEPT_IDS -> (T) user.getDeptIds();
            case KEY_TENANT_ID -> (T) user.getTenantId();
            case KEY_TENANT_IDS -> (T) user.getTenantIds();
            default -> null;
        };
    }

}
