package com.ipower.framework.common.core.bean;

import com.ipower.framework.common.core.Editor;
import com.ipower.framework.common.core.collection.ArrayUtil;
import com.ipower.framework.common.core.collection.Lists;
import com.ipower.framework.common.core.constant.StringPool;
import com.ipower.framework.common.core.lang.ObjectUtil;
import com.ipower.framework.common.core.lang.StringUtil;
import com.ipower.framework.common.core.lang.Validate;
import com.ipower.framework.common.core.reflect.ClassUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.ipower.framework.common.core.lang.ObjectUtil.*;

/**
 * @author kris
 * @since 1.0.0
 */
@Slf4j
public final class BeanUtil {

    /**
     * 私有化构造函数
     */
    private BeanUtil() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    private static final Map<String, Method> CACHE_GET_METHOD = new ConcurrentHashMap<>();

    private static final Map<String, Method> CACHE_SET_METHOD = new ConcurrentHashMap<>();

    private static final Map<String, List<Field>> CACHE_FIELDS = new ConcurrentHashMap<>();

    public enum MethodType {
        /**
         * 方法类型
         */
        READ,
        WRITE
    }

    //////////////////////////////// 工具方法 /////////////////////////////////////////////////////////////////////////////////


    /**
     * 实体类数据拷贝
     * 拷贝原则：
     * 1.只拷贝名称和类型都相同的属性，即使jdk的原类型与包装类型，都会被看成不同的类型，如int与Integer
     * 2.拷贝的属性必须存在getter和setter方法
     *
     * @param orig 源对象
     * @param dest 目标对象
     */
    public static void copy(Object orig, Object dest) {
        copy(orig, dest, new String[]{});
    }

    /**
     * 对象拷贝，会忽略ignores指定的字段集
     *
     * @param orig    源对象
     * @param dest    模板对象
     * @param ignores 忽略字段集
     */
    public static void copy(Object orig, Object dest, String... ignores) {
        BeanUtils.copyProperties(orig, dest, ignores);
    }

    /**
     * 对象拷贝，会根据目标对象类型，构造一个新的对象，拷贝值后再返还
     *
     * @param orig      源对象
     * @param destClass 目标对象类型
     * @param ignores   忽略字段集
     * @param <T>       目标类型的泛型参数
     * @return T 目标类型对象
     */
    public static <T> T copyNew(Object orig, Class<T> destClass, String... ignores) {
        T dest = BeanUtil.newInstance(destClass);
        copy(orig, dest, ignores);
        return dest;
    }

    /**
     * 批量对象拷贝，会循源对象集合，逐个构造新对象，拷贝后填充到新集合中返回
     *
     * @param origins   源对象集合
     * @param destClass 目标对象类型
     * @param ignores   忽略字段集
     * @param <T>       目标类型的泛型参数
     * @return List<T> 目标类型对象集合
     */
    public static <T> List<T> copyNew(List<?> origins, Class<T> destClass, String... ignores) {
        return nullToDefault(origins, new ArrayList<>()).stream().map(s -> copyNew(s, destClass, ignores))
                .collect(Collectors.toList());
    }

    /**
     * 去空格通用方法，会做内存缓存处理。
     * <p>
     * 当前方法接收一个bean对象，对其字符串类型的字段值做去空格处理（不在排除字段集中）
     *
     * @param bean     实体对象
     * @param excludes 需要排除的字段集
     */
    public static void trimProperty(@NonNull Object bean, String... excludes) {
        BeanMethodUtil.beanMethodCache(bean).stream().filter(m -> !ArrayUtil.contains(excludes, m.getField()))
                .forEach(m -> trimProperty(bean, m));
    }

    /**
     * 执行java bean 的标准getter方法
     *
     * @param bean   bean对象
     * @param getter getter方法
     * @return Object 获取的值
     */
    public static Object executeGetter(Object bean, Method getter) {
        try {
            return getter.invoke(bean);
        } catch (IllegalAccessException | InvocationTargetException e) {
            String message = StringUtil.format("执行标准javaBean的getter方法异常！bean={}，getter={}，message={}", bean, getter, e.getMessage());
            log.warn(message, e);
        }
        return null;
    }

    /**
     * 执行java bean 的标准setter方法
     *
     * @param bean   bean对象
     * @param setter setter方法
     * @param value  需要设置的值
     */
    public static void executeSetter(Object bean, Method setter, Object value) {
        try {
            setter.invoke(bean, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            String message = StringUtil.format("执行标准javaBean的setter方法异常！bean={}，setter={}，value={}，message={}", bean, setter, value, e.getMessage());
            log.error(message, e);
        }
    }

    /**
     * 判断是否为Bean对象<br>
     * 判定方法是是否存在只有一个参数的setXXX方法
     *
     * @param clazz 待测试类
     * @return 是否为Bean对象
     * @see #hasSetter(Class)
     */
    public static boolean isBean(Class<?> clazz) {
        return hasSetter(clazz);
    }

    /**
     * 判断是否有Setter方法<br>
     * 判定方法是是否存在只有一个参数的setXXX方法
     *
     * @param clazz 待测试类
     * @return 是否为Bean对象
     */
    public static boolean hasSetter(Class<?> clazz) {
        if (ClassUtil.isNormalClass(clazz)) {
            final Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getParameterTypes().length == 1 && method.getName().startsWith("set")) {
                    // 检测包含标准的setXXX方法即视为标准的JavaBean
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否为Bean对象<br>
     * 判定方法是是否存在只有一个参数的getXXX方法
     *
     * @param clazz 待测试类
     * @return 是否为Bean对象
     */
    public static boolean hasGetter(Class<?> clazz) {
        if (ClassUtil.isNormalClass(clazz)) {
            final Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getParameterTypes().length == 0) {
                    if (method.getName().startsWith("get") || method.getName().startsWith("is")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // --------------------------------------------------------------------------------------------- beanToMap

    /**
     * 对象转Map，不进行驼峰转下划线，不忽略值为空的字段
     *
     * @param bean   bean对象
     * @param fields 指定的字段
     * @return Map
     */
    public static Map<String, Object> toMap(Object bean, String... fields) {
        return toMap(bean, false, false, fields);
    }

    /**
     * 对象转Map
     *
     * @param bean          bean对象
     * @param underlineCase 是否转换为下划线模式
     * @param ignoreNull    是否忽略值为空的字段
     * @param fields        指定的字段
     * @return Map
     */
    public static Map<String, Object> toMap(Object bean, boolean underlineCase, boolean ignoreNull, String... fields) {
        return toMap(bean, new LinkedHashMap<>(), underlineCase, ignoreNull, fields);
    }

    /**
     * 对象转Map
     *
     * @param bean          bean对象
     * @param target        目标的Map
     * @param underlineCase 是否转换为下划线模式
     * @param ignoreNull    是否忽略值为空的字段
     * @param fields        指定的字段
     * @return Map
     */
    public static Map<String, Object> toMap(Object bean, Map<String, Object> target, boolean underlineCase, boolean ignoreNull,
                                            String... fields) {
        return toMap(bean, target, ignoreNull, key -> {
            if (ArrayUtil.isNotEmpty(fields) && !ArrayUtil.contains(fields, key)) {
                return null;
            }
            return underlineCase ? StringUtil.camelToUnderline(key) : key;
        });
    }

    /**
     * 对象转Map<br>
     * 通过实现{@link Editor} 可以自定义字段值，如果这个Editor返回null则忽略这个字段，以便实现：
     *
     * <pre>
     * 1. 字段筛选，可以去除不需要的字段
     * 2. 字段变换，例如实现驼峰转下划线
     * 3. 自定义字段前缀或后缀等等
     * </pre>
     *
     * @param bean       bean对象
     * @param target     目标的Map
     * @param ignoreNull 是否忽略值为空的字段
     * @param keyEditor  属性字段（Map的key）编辑器，用于筛选、编辑key
     * @return Map
     */
    public static Map<String, Object> toMap(Object bean, Map<String, Object> target, boolean ignoreNull,
                                            Editor<String> keyEditor) {
        if (bean == null) {
            return new LinkedHashMap<>();
        }
        final Collection<BeanDesc.PropDesc> props = BeanUtil.getBeanDesc(bean.getClass()).getProps();
        for (BeanDesc.PropDesc prop : props) {
            // 过滤class属性
            // 得到property对应的getter方法
            Method getter = prop.getGetter();
            if (isNull(getter)) {
                continue;
            }
            Object value;
            // 只读取有getter方法的属性
            try {
                value = getter.invoke(bean);
            } catch (Exception ignore) {
                continue;
            }
            //判断是否过滤空
            if (isNull(value) && ignoreNull) {
                continue;
            }
            String key = keyEditor.edit(prop.getFieldName());
            if (isNotEmpty(key)) {
                target.put(key, value);
            }
        }
        return target;
    }

    /**
     * 集合对象转集合Map，不进行驼峰转下划线，不忽略值为空的字段
     *
     * @param beans  bean对象集合
     * @param fields 指定的字段
     * @return Map
     */
    public static <T> List<Map<String, Object>> toMapList(List<T> beans, String... fields) {
        return toMapList(beans, false, false, fields);
    }

    /**
     * 集合对象转集合Map
     *
     * @param beans         bean对象集合
     * @param underlineCase 是否转换为下划线模式
     * @param ignoreNull    是否忽略值为空的字段
     * @param fields        指定的字段
     * @return Map
     */
    public static <T> List<Map<String, Object>> toMapList(List<T> beans, boolean underlineCase, boolean ignoreNull,
                                                          String... fields) {
        List<Map<String, Object>> mapList = Lists.arrayList();
        for (Object bean : nullToDefault(beans, new ArrayList<>())) {
            mapList.add(toMap(bean, underlineCase, ignoreNull, fields));
        }
        return mapList;
    }

    /**
     * 获取{@link BeanDesc} Bean描述信息
     *
     * @param clazz Bean类
     * @return {@link BeanDesc}
     */
    public static BeanDesc getBeanDesc(Class<?> clazz) {
        BeanDesc beanDesc = BeanDescCache.INSTANCE.getBeanDesc(clazz);
        if (null == beanDesc) {
            beanDesc = new BeanDesc(clazz);
            BeanDescCache.INSTANCE.putBeanDesc(clazz, beanDesc);
        }
        return beanDesc;
    }

    /**
     * 获取Bean类的 getxxx方法列表
     *
     * @param bean:Bean对象
     * @return List<Method>方法列表
     */
    public static List<Method> getReadMethod(Object bean) {
        Validate.notNull(bean, "bean can't be null!");
        return getReadMethod(bean.getClass());
    }

    /**
     * 获取Bean类某个字段的 getxxx方法列表
     *
     * @param bean:Bean对象
     * @param field:Bean类的字段
     * @return Method方法
     */
    public static Method getReadMethod(Object bean, String field) {
        Validate.notNull(bean, "bean can't be null!");
        return getReadMethod(bean.getClass(), field);
    }

    /**
     * 获取Bean类的 setxxx方法列表
     *
     * @param bean:Bean对象
     * @return List<Method>方法列表
     */
    public static List<Method> getWriteMethod(Object bean) {
        Validate.notNull(bean, "bean can't be null!");
        return getWriteMethod(bean.getClass());
    }

    /**
     * 获取Bean类某个字段的 setxxx方法列表
     *
     * @param bean:Bean对象
     * @param field:Bean类的字段
     * @return Method方法
     */
    public static Method getWriteMethod(Object bean, String field) {
        Validate.notNull(bean, "bean can't be null!");
        return getWriteMethod(bean.getClass(), field);
    }

    /**
     * 获取Bean类的 getxxx方法列表
     *
     * @param clazz:Bean类
     * @return List<Method>方法列表
     */
    public static List<Method> getReadMethod(Class<?> clazz) {
        return getReadOrWriteMethod(clazz, MethodType.READ);
    }

    /**
     * 获取Bean类某个字段的 getxxx方法列表
     *
     * @param clazz:Bean类
     * @param field:Bean类的字段
     * @return Method方法
     */
    public static Method getReadMethod(Class<?> clazz, String field) {
        List<Method> list = getReadOrWriteMethod(clazz, MethodType.READ, field);
        return !list.isEmpty() ? list.get(0) : null;
    }

    /**
     * 获取Bean类的 getxxx方法列表
     *
     * @param clazz:Bean类
     * @return List<Method>方法列表
     */
    public static List<Method> getWriteMethod(Class<?> clazz) {
        return getReadOrWriteMethod(clazz, MethodType.WRITE);
    }

    /**
     * 获取Bean类某个字段的 getxxx方法列表
     *
     * @param clazz:Bean类
     * @param field:Bean类的字段
     * @return Method方法
     */
    public static Method getWriteMethod(Class<?> clazz, String field) {
        List<Method> list = getReadOrWriteMethod(clazz, MethodType.WRITE, field);
        return !list.isEmpty() ? list.get(0) : null;
    }

    /**
     * 获取Bean类的 getxxx和setxxx方法列表方法列表
     *
     * @param clazz:Bean类
     * @return List<Method>方法列表
     */
    public static List<Method> getReadAndWriteMethod(Class<?> clazz) {
        return getReadOrWriteMethod(clazz, null);
    }

    /**
     * 获取Bean类某个字段的 getxxx和setxxx方法列表方法列表
     *
     * @param clazz:Bean类
     * @param field:Bean对象的字段
     * @return List<Method>方法列表
     */
    public static List<Method> getReadAndWriteMethod(Class<?> clazz, String field) {
        return getReadOrWriteMethod(clazz, null, field);
    }

    /**
     * 获取Bean类的 getxxx和setxxx方法列表方法列表
     *
     * @param bean:Bean对象
     * @return List<Method>方法列表
     */
    public static List<Method> getReadAndWriteMethod(Object bean) {
        Validate.notNull(bean, "bean can't be null!");
        return getReadOrWriteMethod(bean.getClass(), null);
    }

    /**
     * 获取Bean类某个字段的 getxxx和setxxx方法列表方法列表
     *
     * @param bean:Bean对象
     * @param field:Bean对象的字段
     * @return List<Method>方法列表
     */
    public static List<Method> getReadAndWriteMethod(Object bean, String field) {
        Validate.notNull(bean, "bean can't be null!");
        return getReadOrWriteMethod(bean.getClass(), null, field);
    }


    /**
     * 获取类属性对象
     *
     * @param bean:Bean对象
     * @param fieldName:属性名
     * @return 类属性对象
     */
    public static Field getField(Object bean, String fieldName) {
        Validate.notNull(bean, "bean can't be null!");
        return getField(bean.getClass(), fieldName);
    }

    /**
     * 获取类属性对象
     *
     * @param clazz:Bean类
     * @param fieldName:属性变量名
     * @return 类属性对象
     */
    public static Field getField(Class<?> clazz, String fieldName) {
        List<Field> fieldList = getFields(clazz, true);
        for (Field field : fieldList) {
            if (ObjectUtil.equals(field.getName(), fieldName)) {
                return field;
            }
        }
        return null;
    }

    /**
     * 获取类属性对象列表
     *
     * @param bean:Bean对象
     * @return 类属性对象列表
     */
    public static List<Field> getFields(Object bean) {
        Validate.notNull(bean, "bean can't be null!");
        return getFields(bean.getClass());
    }

    /**
     * 获取类属性对象列表
     *
     * @param bean:Bean对象
     * @param excludes:需要排除的字段集
     * @return 类属性对象列表
     */
    public static List<Field> getFields(Object bean, String... excludes) {
        return getFields(bean, false, excludes);
    }

    /**
     * 获取类属性对象列表
     *
     * @param bean:Bean对象
     * @param superclass:是否忽略父类的属性
     * @return 类属性对象列表
     */
    public static List<Field> getFields(Object bean, boolean superclass) {
        Validate.notNull(bean, "bean can't be null!");
        return getFields(bean.getClass(), superclass);
    }

    /**
     * 获取类属性对象列表
     *
     * @param bean:Bean对象
     * @param superclass:是否忽略父类的属性
     * @param excludes:需要排除的字段集
     * @return 类属性对象列表
     */
    public static List<Field> getFields(Object bean, boolean superclass, String... excludes) {
        Validate.notNull(bean, "bean can't be null!");
        return getFields(bean.getClass(), superclass, excludes);
    }

    /**
     * 获取类属性对象列表
     *
     * @param clazz:Bean类
     * @return 类属性对象列表
     */
    public static List<Field> getFields(Class<?> clazz) {
        return getFields(clazz, false);
    }

    /**
     * 获取类属性对象列表
     *
     * @param clazz:Bean类
     * @param excludes:需要排除的字段集
     * @return 类属性对象列表
     */
    public static List<Field> getFields(Class<?> clazz, String... excludes) {
        return getFields(clazz, false, excludes);
    }

    /**
     * 获取类的属性对象列表
     *
     * @param clazz:Bean类
     * @param superclass: 是否忽略父类的属性
     * @return 类属性对象列表
     */
    public static List<Field> getFields(Class<?> clazz, boolean superclass) {
        return getFields(clazz, superclass, "");
    }

    /**
     * 获取类的属性对象列表
     *
     * @param clazz:Bean类
     * @param ignoreSuperclass: 是否忽略父类的属性
     * @param excludes:需要排除的字段集
     * @return 类属性对象列表
     */
    public static List<Field> getFields(Class<?> clazz, boolean ignoreSuperclass, String... excludes) {
        return getFields(clazz, ignoreSuperclass, false, excludes);
    }

    /**
     * 获取类的属性对象列表
     *
     * @param clazz            Bean类
     * @param ignoreSuperclass 是否忽略父类的属性
     * @param isInclude        是否只返回包含的
     * @param properties       包含或排除的字段集
     * @return List<Field> 类属性对象列表
     */
    public static List<Field> getFields(Class<?> clazz, boolean ignoreSuperclass, boolean isInclude, final String... properties) {
        Validate.notNull(clazz, "clazz can't be null!");
        //定义过滤列表
        List<String> filters = isInclude ? Lists.arrayList() : Lists.arrayList(properties);
        //定义返回列表
        List<Field> fieldList = Lists.arrayList();
        for (Class<?> c = clazz; c != Object.class; c = c.getSuperclass()) {
            List<Field> list = screenFields(c, filters);
            fieldList.addAll(list);
            //如果是忽略父类的属性，直接退出循环
            if (ignoreSuperclass) {
                break;
            }
            //将返回的字段添加到过滤列表中
            filters.addAll(list.stream().map(Field::getName).toList());
        }
        //如果是只返回包含的，删除不在参数集合中的字段
        if (isInclude) {
            List<String> includes = Arrays.asList(properties);
            fieldList.removeIf(it -> !includes.contains(it.getName()));
        }
        return fieldList;
    }

    /**
     * 获取指定类型超类中泛型的实际类型
     *
     * @param clazz 指定类型
     * @param index 需要获取的第几个泛型的类型
     * @return Class
     */
    public static Class<?> getSuperClassActualType(Class<?> clazz, int index) {
        Type genType = clazz.getGenericSuperclass();
        //检验泛型是否被参数化
        if (!(genType instanceof ParameterizedType)) {
            log.warn(StringUtil.format("{}'s superclass not ParameterizedType", clazz.getSimpleName()));
            return null;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index < 0 || index >= params.length) {
            log.warn(StringUtil.format("Index: {}, Size of {}'s Parameterized Type: {} .", index, clazz
                    .getSimpleName(), params.length));
            return null;
        }
        if (!(params[index] instanceof Class)) {
            log.warn(StringUtil.format("{} not set the actual class on superclass generic parameter", clazz.getSimpleName()));
            return null;
        }
        return (Class<?>) params[index];
    }

    /**
     * 获取类型的泛型参数类型
     *
     * @param type  指定类型
     * @param index 需要获取的第几个泛型类型
     * @return Class 泛型类型
     */
    public static Class<?> getParameterizedType(Type type, int index) {
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
            if (index >= 0 && index < types.length) {
                return (Class<?>) types[index];
            }
        }
        return null;
    }

    /**
     * 根据传入的类型，构造一个类型的对象
     *
     * @param clazz class类型
     * @param <T>   泛型参数
     * @return 实例化后的对象
     */
    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("根据class类型[" + clazz + "]实例化对象异常！原因：" + e.getMessage(), e);
        }
    }

    /**
     * 往bean对象的属性注入值
     *
     * @param bean:bean对象
     * @param property:属性
     * @param value:对应的值
     */
    public static void setProperty(@NonNull Object bean, String property, Object value) {
        try {
            //先从缓存获取执行方法，若没有，则通过bean对象获取，并添加到缓存中
            String cacheKey = bean.getClass().getName() + StringPool.HASH + property;
            if (!CACHE_SET_METHOD.containsKey(cacheKey)) {
                Method method = getWriteMethod(bean, property);
                if (isNull(method)) {
                    log.warn("不能从java类[{}]中获取到属性[{}]的赋值方法！", bean.getClass().getName(), property);
                    return;
                }
                CACHE_SET_METHOD.put(cacheKey, method);
            }
            CACHE_SET_METHOD.get(cacheKey).invoke(bean, value);
        } catch (Exception e) {
            throw new RuntimeException("设置数据的属性值异常：" + e.getMessage(), e);
        }
    }

    /**
     * 利用反射安全获取bean对象的属性值
     *
     * @param bean     bean对象
     * @param property 属性名
     * @param <V>      返回值泛型类型
     * @return V 属性值
     */
    @SuppressWarnings("unchecked")
    public static <V> V getProperty(Object bean, String property) {
        try {
            //先从缓存获取执行方法，若没有，则通过bean对象获取，并添加到缓存中
            String cacheKey = bean.getClass().getName() + StringPool.HASH + property;
            if (!CACHE_GET_METHOD.containsKey(cacheKey)) {
                Method method = getReadMethod(bean, property);
                if (isNull(method)) {
                    log.warn("不能从java类[{}]中获取到属性[{}]的取值方法！", bean.getClass().getName(), property);
                    return null;
                }
                CACHE_GET_METHOD.put(cacheKey, method);
            }
            return (V) CACHE_GET_METHOD.get(cacheKey).invoke(bean);
        } catch (Exception e) {
            log.error("获取bean属性异常！bean=" + bean + " ,property" + property + ", 异常：" + e.getMessage(), e);
        }
        return null;
    }

    /**
     * 利用转换函数安全获取bean对象的属性值
     *
     * @param bean     bean对象，key为null
     * @param function 转换函数
     * @param <T>      bean对象的泛型参数
     * @param <V>      返回值泛型类型
     * @return V 属性值
     */
    public static <T, V> V getProperty(T bean, @NonNull Function<T, V> function) {
        return isNull(bean) ? null : function.apply(bean);
    }

    /**
     * 利用转换函数安全获取bean对象的属性值，属性值为null的情况下，使用默认值
     *
     * @param bean         bean对象，key为null
     * @param function     转换函数
     * @param defaultValue 返回值为null的情况下，会使用默认值
     * @param <T>          bean对象的泛型参数
     * @param <V>          返回值泛型类型
     * @return V 属性值
     */
    public static <T, V> V getProperty(T bean, @NonNull Function<T, V> function, V defaultValue) {
        return nullToDefault(getProperty(bean, function), defaultValue);
    }

    /**
     * 根据bean类型、字段名称、annotation类型获取字段上的annotation属性，获取规则：
     * <p>
     * 1. 首先从子类中获取，若取不到时会到父类获取
     * <p>
     * 2. 子类和父类同时存在的情况，取子类
     *
     * @param beanClass       bean类型
     * @param property        字段属性名
     * @param annotationClass annotation类型
     * @param <T>             annotation泛型参数
     * @return T annotation属性
     */
    public static <T extends Annotation> T getPropertyAnnotation(Class<?> beanClass, String property, Class<T> annotationClass) {
        return getFields(beanClass, false).stream().filter(field -> ObjectUtil.equals(field.getName(), property))
                .map(field -> field.getAnnotation(annotationClass)).filter(ObjectUtil::isNotNull)
                .findFirst().orElse(null);
    }

    /**
     * 比较俩个对象字段值是否有差异，会过滤excludes中指定的字段
     *
     * @param origin   源对象
     * @param target   待比较的目标对象
     * @param excludes 需要排除的字段
     * @return boolean 是否有差异
     */
    public static boolean isDifferent(Object origin, Object target, String... excludes) {
        return isDifferent(origin, target, false, false, excludes);
    }

    /**
     * 比较俩个对象字段值是否有差异，只包含includes中指定的字段
     *
     * @param origin   源对象
     * @param target   待比较的目标对象
     * @param includes 比较指定的字段
     * @return boolean 是否有差异
     */
    public static boolean isDifferentSelected(Object origin, Object target, String... includes) {
        return isDifferent(origin, target, false, true, includes);
    }

    /**
     * 比较俩个对象字段值是否有差异，忽略父级字段值，会过滤excludes中指定的字段
     *
     * @param origin   源对象
     * @param target   待比较的目标对象
     * @param excludes 需要排除的字段
     * @return boolean 是否有差异
     */
    public static boolean isDifferentSingle(Object origin, Object target, String... excludes) {
        return isDifferent(origin, target, true, false, excludes);
    }

    /**
     * 比较俩个对象字段值是否有差异，忽略父级字段值，只包含includes中指定的字段
     *
     * @param origin   源对象
     * @param target   待比较的目标对象
     * @param includes 比较指定的字段
     * @return boolean 是否有差异
     */
    public static boolean isDifferentSingleSelected(Object origin, Object target, String... includes) {
        return isDifferent(origin, target, true, true, includes);
    }

    /**
     * 比较俩个对象，返回俩个对象中不同值的字段信息，会过滤excludes中指定的字段
     *
     * @param origin   源对象
     * @param target   待比较的目标对象
     * @param excludes 需要排除的字段
     * @return List<Different> 不同值的字段信息
     */
    public static List<Different> compare(Object origin, Object target, String... excludes) {
        return compare(origin, target, false, false, excludes);
    }

    /**
     * 比较俩个对象，返回俩个对象中不同值的字段信息，只包含includes中指定的字段
     *
     * @param origin   源对象
     * @param target   待比较的目标对象
     * @param includes 比较指定的字段
     * @return List<Different> 不同值的字段信息
     */
    public static List<Different> compareSelected(Object origin, Object target, String... includes) {
        return compare(origin, target, false, true, includes);
    }

    /**
     * 比较俩个对象，会忽略父级继承的字段，返回俩个对象中不同值的字段信息，会过滤excludes中指定的字段
     *
     * @param origin   源对象
     * @param target   待比较的目标对象
     * @param excludes 需要排除的字段
     * @return List<Different> 不同值的字段信息
     */
    public static List<Different> compareSingle(Object origin, Object target, String... excludes) {
        return compare(origin, target, true, false, excludes);
    }

    /**
     * 比较俩个对象，会忽略父级继承的字段，返回俩个对象中不同值的字段信息，只包含includes中指定的字段
     *
     * @param origin   源对象
     * @param target   待比较的目标对象
     * @param includes 比较指定的字段
     * @return List<Different> 不同值的字段信息
     */
    public static List<Different> compareSingleSelected(Object origin, Object target, String... includes) {
        return compare(origin, target, true, true, includes);
    }

    /**
     * 比较俩个对象，返回俩个对象中不同值的字段信息
     *
     * @param origin           源对象
     * @param target           待比较的目标对象
     * @param ignoreSuperclass 是否忽略父级
     * @param isInclude        是否只处理包含的字段
     * @param properties       包含或排除的字段集
     * @return List<Different> 不同值的字段信息
     */
    private static List<Different> compare(@NonNull Object origin, @NonNull Object target, boolean ignoreSuperclass, boolean isInclude, String... properties) {
        Validate.isTrue(ObjectUtil.equals(origin.getClass(), target.getClass()), "target type must be the same as source type!");
        //定义返回数据集合
        List<Different> differences = Lists.arrayList();
        //获取字段，并循环构建不同的数据
        getFields(origin.getClass(), ignoreSuperclass, isInclude, properties).forEach(it -> {
            Object originValue = getProperty(origin, it.getName());
            Object targetValue = getProperty(target, it.getName());
            if (isDifferentValue(it.getType(), originValue, targetValue)) {
                differences.add(new Different(it.getName(), it.getType(), originValue, targetValue));
            }
        });
        return differences;
    }

    /**
     * 比较俩个对象字段值是否有差异
     *
     * @param origin           源对象
     * @param target           待比较的目标对象
     * @param ignoreSuperclass 是否忽略父级
     * @param isInclude        是否只处理包含的字段
     * @param properties       包含或排除的字段集
     * @return boolean 是否有差异
     */
    private static boolean isDifferent(@NonNull Object origin, Object target, boolean ignoreSuperclass, boolean isInclude, String... properties) {
        if (isNull(target) || !ObjectUtil.equals(origin.getClass(), target.getClass())) {
            return true;
        }
        //循环所有字段，比较字典值是否相等
        for (Field field : getFields(origin.getClass(), ignoreSuperclass, isInclude, properties)) {
            if (isDifferentValue(field.getType(), getProperty(origin, field.getName()), getProperty(target, field.getName()))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 比较俩个对象字段的值是否不同
     *
     * @param fieldType   字段类型
     * @param originValue 源对象字段的值
     * @param targetValue 目标对象字段的值
     * @return boolean 是否不同
     */
    private static boolean isDifferentValue(Class<?> fieldType, Object originValue, Object targetValue) {
        if (ObjectUtil.equals(fieldType, BigDecimal.class)) {
            return isDifferentValue((BigDecimal) originValue, (BigDecimal) targetValue);
        }
        return !ObjectUtil.equals(originValue, targetValue);
    }

    private static boolean isDifferentValue(BigDecimal originValue, BigDecimal targetValue) {
        if (isNotNull(originValue) && isNotNull(targetValue)) {
            return originValue.compareTo(targetValue) != 0;
        }
        return !(isNull(originValue) && isNull(targetValue));
    }

    //////////////////////////////// 私有方法 /////////////////////////////////////////////////////////////////////////////////

    /**
     * 获取方法对象列表
     */
    private static List<Method> getReadOrWriteMethod(Class<?> clazz, MethodType methodType) {
        List<Method> methods = Lists.arrayList();
        PropertyDescriptor[] descriptors = PropertyUtil.getPropertyDescriptors(clazz);
        for (PropertyDescriptor descriptor : descriptors) {
            if ("class".equals(descriptor.getName())) {
                continue;
            }
            if (ObjectUtil.equals(methodType, MethodType.READ)) {
                methods.add(descriptor.getReadMethod());
                continue;
            }
            if (ObjectUtil.equals(methodType, MethodType.WRITE)) {
                methods.add(descriptor.getWriteMethod());
                continue;
            }
            methods.add(descriptor.getReadMethod());
            methods.add(descriptor.getWriteMethod());
        }
        return methods;
    }

    /**
     * 获取方法对象
     */
    private static List<Method> getReadOrWriteMethod(Class<?> clazz, MethodType methodType, String field) {
        List<Method> methods = Lists.arrayList();
        PropertyDescriptor[] descriptors = PropertyUtil.getPropertyDescriptors(clazz);
        for (PropertyDescriptor descriptor : descriptors) {
            if (ObjectUtil.equals(field, descriptor.getName())) {
                if (ObjectUtil.equals(methodType, MethodType.READ)) {
                    methods.add(descriptor.getReadMethod());
                } else if (ObjectUtil.equals(methodType, MethodType.WRITE)) {
                    methods.add(descriptor.getWriteMethod());
                } else {
                    methods.add(descriptor.getReadMethod());
                    methods.add(descriptor.getWriteMethod());
                }
                break;
            }
        }
        return methods;
    }

    /**
     * 过滤字段
     */
    private static List<Field> screenFields(Class<?> clazz, List<String> excludes) {
        //增加过滤条件的字段
        excludes.add("serialVersionUID");
        //如果缓存中不存在
        if (!CACHE_FIELDS.containsKey(clazz.getName())) {
            CACHE_FIELDS.put(clazz.getName(), Lists.arrayList(clazz.getDeclaredFields()));
        }
        return CACHE_FIELDS.get(clazz.getName()).stream().filter(field -> !excludes.contains(field.getName())).collect(Collectors.toList());
    }

    /**
     * 执行去空格处理，会先执行getter获取字段值，对值去空格后再复制给字段
     */
    private static void trimProperty(Object bean, BeanMethod beanMethod) {
        Object value = executeGetter(bean, beanMethod.getGetterMethod());
        if (isNotNull(value)) {
            executeSetter(bean, beanMethod.getSetterMethod(), value.toString().trim());
        }
    }
}
