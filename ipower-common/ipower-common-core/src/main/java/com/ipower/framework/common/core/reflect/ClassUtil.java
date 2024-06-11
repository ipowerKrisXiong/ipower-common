package com.ipower.framework.common.core.reflect;

import com.ipower.framework.common.core.Filter;
import com.ipower.framework.common.core.bean.Singleton;
import com.ipower.framework.common.core.collection.ArrayUtil;
import com.ipower.framework.common.core.constant.CharPool;
import com.ipower.framework.common.core.constant.StringPool;
import com.ipower.framework.common.core.convert.BasicType;
import com.ipower.framework.common.core.exception.UtilException;
import com.ipower.framework.common.core.lang.StringUtil;
import com.ipower.framework.common.core.lang.Validate;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * 类工具类 <br>
 * <p>
 * 参考:<a href="https://gitee.com/loolly/hutool">hutool</a>
 *
 * @author kris
 * @since 1.0.0
 */
public class ClassUtil {

    /**
     * {@code null}安全的获取对象类型
     *
     * @param <T> 对象类型
     * @param obj 对象，如果为{@code null} 返回{@code null}
     * @return 对象类型，提供对象如果为{@code null} 返回{@code null}
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClass(final T obj) {
        return ((null == obj) ? null : (Class<T>) obj.getClass());
    }

    /**
     * 获得外围类<br>
     * 返回定义此类或匿名类所在的类，如果类本身是在包中定义的，返回{@code null}
     *
     * @param clazz 类
     * @return 外围类
     */
    public static Class<?> getEnclosingClass(final Class<?> clazz) {
        return null == clazz ? null : clazz.getEnclosingClass();
    }

    /**
     * 获取类名
     *
     * @param obj 获取类名对象
     * @return 类名
     */
    public static String getClassName(final Object obj) {
        return obj == null ? StringPool.EMPTY : obj.getClass().getName();
    }

    /**
     * 获取类名<br>
     * 类名并不包含“.class”这个扩展名<br>
     * 例如：ClassUtil这个类<br>
     *
     * <pre>
     * "com.uneed.common.core.reflect.ClassUtil"
     * </pre>
     *
     * @param clazz 类
     * @return 类名
     */
    public static String getClassName(final Class<?> clazz) {
        return clazz == null ? StringPool.EMPTY : clazz.getName();
    }

    /**
     * 获取简单类名
     *
     * @param obj 获取类名对象
     * @return 类名
     */
    public static String getClassSimpleName(final Object obj) {
        return obj == null ? StringPool.EMPTY : obj.getClass().getSimpleName();
    }

    /**
     * 获取简单类名<br>
     * 类名并不包含“.class”这个扩展名<br>
     * 例如：ClassUtil这个类<br>
     *
     * <pre>
     * "ClassUtil"
     * </pre>
     *
     * @param clazz 类
     * @return 类名
     */
    public static String getClassSimpleName(final Class<?> clazz) {
        return clazz == null ? StringPool.EMPTY : clazz.getSimpleName();
    }

    /**
     * 获取完整类名的短格式如：<br>
     * com.uneed.core.lang.StringUtil -》c.u.c.l.StringUtil
     *
     * @param className 类名
     * @return 短格式类名
     */
    public static String getShortClassName(final String className) {
        final List<String> packages = StringUtil.split(className, StringPool.BACK_SLASH_DOT);
        if (packages.size() < 2) {
            return className;
        }
        final int size = packages.size();
        final StringBuilder result = new StringBuilder();
        result.append(packages.get(0).charAt(0));
        for (int i = 1; i < size - 1; i++) {
            result.append(CharPool.DOT).append(packages.get(i).charAt(0));
        }
        result.append(CharPool.DOT).append(packages.get(size - 1));
        return result.toString();
    }

    /**
     * 获得对象数组的类数组
     *
     * @param objs 对象数组，如果数组中存在{@code null}元素，则此元素被认为是Object类型
     * @return 类数组
     */
    public static Class<?>[] getClasses(final Object... objs) {
        Class<?>[] classes = new Class<?>[objs.length];
        Object obj;
        for (int i = 0; i < objs.length; i++) {
            obj = objs[i];
            classes[i] = obj == null ? Object.class : obj.getClass();
        }
        return classes;
    }

    /**
     * 指定类是否与给定的类名相同
     *
     * @param clazz     类
     * @param className 类名，可以是全类名（包含包名），也可以是简单类名（不包含包名）
     * @return 指定类是否与给定的类名相同
     */
    public static boolean equals(Class<?> clazz, String className) {
        if (clazz == null || StringUtil.isEmpty(className)) {
            return false;
        }
        return className.equals(clazz.getName()) || className.equals(clazz.getSimpleName());
    }

    /**
     * 指定类是否与给定的类名相同，会忽略大小写
     *
     * @param clazz     类
     * @param className 类名，可以是全类名（包含包名），也可以是简单类名（不包含包名）
     * @return 指定类是否与给定的类名相同
     */
    public static boolean equalsIgnoreCase(Class<?> clazz, String className) {
        if (clazz == null || StringUtil.isEmpty(className)) {
            return false;
        }
        return className.equalsIgnoreCase(clazz.getName()) || className.equalsIgnoreCase(clazz.getSimpleName());
    }

    // ----------------------------------------------------------------------------------------- Method

    /**
     * 获得指定类中的Public方法名<br>
     * 去重重载的方法
     *
     * @param clazz 类
     * @return 方法名Set
     */
    public static Set<String> getPublicMethodNames(Class<?> clazz) {
        return ReflectUtil.getPublicMethodNames(clazz);
    }

    /**
     * 获得本类及其父类所有Public方法
     *
     * @param clazz 查找方法的类
     * @return 过滤后的方法列表
     */
    public static Method[] getPublicMethods(Class<?> clazz) {
        return ReflectUtil.getPublicMethods(clazz);
    }

    /**
     * 获得指定类过滤后的Public方法列表
     *
     * @param clazz  查找方法的类
     * @param filter 过滤器
     * @return 过滤后的方法列表
     */
    public static List<Method> getPublicMethods(Class<?> clazz, Filter<Method> filter) {
        return ReflectUtil.getPublicMethods(clazz, filter);
    }

    /**
     * 获得指定类过滤后的Public方法列表
     *
     * @param clazz          查找方法的类
     * @param excludeMethods 不包括的方法
     * @return 过滤后的方法列表
     */
    public static List<Method> getPublicMethods(Class<?> clazz, Method... excludeMethods) {
        return ReflectUtil.getPublicMethods(clazz, excludeMethods);
    }

    /**
     * 获得指定类过滤后的Public方法列表
     *
     * @param clazz              查找方法的类
     * @param excludeMethodNames 不包括的方法名列表
     * @return 过滤后的方法列表
     */
    public static List<Method> getPublicMethods(Class<?> clazz, String... excludeMethodNames) {
        return ReflectUtil.getPublicMethods(clazz, excludeMethodNames);
    }

    /**
     * 查找指定Public方法 如果找不到对应的方法或方法不为public的则返回<code>null</code>
     *
     * @param clazz      类
     * @param methodName 方法名
     * @param paramTypes 参数类型
     * @return 方法
     * @throws SecurityException 无权访问抛出异常
     */
    public static Method getPublicMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) throws SecurityException {
        return ReflectUtil.getPublicMethod(clazz, methodName, paramTypes);
    }

    /**
     * 获得指定类中的Public方法名<br>
     * 去重重载的方法
     *
     * @param clazz 类
     * @return 方法名Set
     */
    public static Set<String> getDeclaredMethodNames(Class<?> clazz) {
        return ReflectUtil.getMethodNames(clazz);
    }

    /**
     * 获得声明的所有方法，包括本类及其父类和接口的所有方法和Object类的方法
     *
     * @param clazz 类
     * @return 方法数组
     */
    public static Method[] getDeclaredMethods(Class<?> clazz) {
        return ReflectUtil.getMethods(clazz);
    }

    /**
     * 查找指定对象中的所有方法（包括非public方法），也包括父对象和Object类的方法
     *
     * @param obj        被查找的对象
     * @param methodName 方法名
     * @param args       参数
     * @return 方法
     * @throws SecurityException 无访问权限抛出异常
     */
    public static Method getDeclaredMethodOfObj(Object obj, String methodName, Object... args) throws SecurityException {
        return getDeclaredMethod(obj.getClass(), methodName, getClasses(args));
    }

    /**
     * 查找指定类中的所有方法（包括非public方法），也包括父类和Object类的方法 找不到方法会返回<code>null</code>
     *
     * @param clazz          被查找的类
     * @param methodName     方法名
     * @param parameterTypes 参数类型
     * @return 方法
     * @throws SecurityException 无访问权限抛出异常
     */
    public static Method getDeclaredMethod(Class<?> clazz, String methodName,
                                           Class<?>... parameterTypes) throws SecurityException {
        return ReflectUtil.getMethod(clazz, methodName, parameterTypes);
    }

    // ----------------------------------------------------------------------------------------- Field

    /**
     * 查找指定类中的所有字段（包括非public字段）， 字段不存在则返回<code>null</code>
     *
     * @param clazz     被查找字段的类
     * @param fieldName 字段名
     * @return 字段
     * @throws SecurityException 安全异常
     */
    public static Field getDeclaredField(Class<?> clazz, String fieldName) throws SecurityException {
        if (null == clazz || StringUtil.isEmpty(fieldName)) {
            return null;
        }
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            // e.printStackTrace();
        }
        return null;
    }

    /**
     * 查找指定类中的所有字段（包括非public字段)
     *
     * @param clazz 被查找字段的类
     * @return 字段
     * @throws SecurityException 安全异常
     */
    public static Field[] getDeclaredFields(Class<?> clazz) throws SecurityException {
        if (null == clazz) {
            return null;
        }
        return clazz.getDeclaredFields();
    }

    // ----------------------------------------------------------------------------------------- Classpath


    /**
     * @return 获得Java ClassPath路径，不包括 jre
     */
    public static String[] getJavaClassPaths() {
        return System.getProperty("java.class.path").split(File.pathSeparator);
    }

    /**
     * 获取当前线程的{@link ClassLoader}
     *
     * @return 当前线程的class loader
     * @see Thread#currentThread()#getClassLoader()
     */
    public static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 获取{@link ClassLoader}<br>
     * 获取顺序如下：<br>
     *
     * <pre>
     * 1、获取当前线程的ContextClassLoader
     * 2、获取{@link ClassUtil}类对应的ClassLoader
     * 3、获取系统ClassLoader（{@link ClassLoader#getSystemClassLoader()}）
     * </pre>
     *
     * @return 类加载器
     */
    public static ClassLoader getClassLoader() {
        return ClassLoaderUtil.getClassLoader();
    }

    /**
     * 比较判断types1和types2两组类，如果types1中所有的类都与types2对应位置的类相同，或者是其父类或接口，则返回<code>true</code>
     *
     * @param types1 类组1
     * @param types2 类组2
     * @return 是否相同、父类或接口
     */
    public static boolean isAllAssignableFrom(Class<?>[] types1, Class<?>[] types2) {
        if (ArrayUtil.isEmpty(types1) && ArrayUtil.isEmpty(types2)) {
            return true;
        }
        if (null == types1 || null == types2) {
            // 任何一个为null不相等（之前已判断两个都为null的情况）
            return false;
        }
        if (types1.length != types2.length) {
            return false;
        }

        Class<?> type1;
        Class<?> type2;
        for (int i = 0; i < types1.length; i++) {
            type1 = types1[i];
            type2 = types2[i];
            if (isBasicType(type1) && isBasicType(type2)) {
                // 原始类型和包装类型存在不一致情况
                if (BasicType.unWrap(type1) != BasicType.unWrap(type2)) {
                    return false;
                }
            } else if (!type1.isAssignableFrom(type2)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 加载类
     *
     * @param <T>           对象类型
     * @param className     类名
     * @param isInitialized 是否初始化
     * @return 类
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> loadClass(String className, boolean isInitialized) {
        return (Class<T>) ClassLoaderUtil.loadClass(className, isInitialized);
    }

    /**
     * 加载类并初始化
     *
     * @param <T>       对象类型
     * @param className 类名
     * @return 类
     */
    public static <T> Class<T> loadClass(String className) {
        return loadClass(className, true);
    }

    // ---------------------------------------------------------------------------------------------------- Invoke start

    /**
     * 执行方法<br>
     * 可执行Private方法，也可执行static方法<br>
     * 执行非static方法时，必须满足对象有默认构造方法<br>
     * 非单例模式，如果是非静态方法，每次创建一个新对象
     *
     * @param <T>                     对象类型
     * @param classNameWithMethodName 类名和方法名表达式，类名与方法名用<code>.</code>或<code>#</code>连接 例如：com.uneed.common.core.lang.StringUtil.isEmpty 或 com.uneed.common.core.lang.StringUtil#isEmpty
     * @param args                    参数，必须严格对应指定方法的参数类型和数量
     * @return 返回结果
     */
    public static <T> T invoke(String classNameWithMethodName, Object[] args) {
        return invoke(classNameWithMethodName, false, args);
    }

    /**
     * 执行方法<br>
     * 可执行Private方法，也可执行static方法<br>
     * 执行非static方法时，必须满足对象有默认构造方法<br>
     *
     * @param <T>                     对象类型
     * @param classNameWithMethodName 类名和方法名表达式，例如：com.uneed.common.core.lang.StringUtil#isEmpty或com.uneed.common.core.lang.StringUtil.isEmpty
     * @param isSingleton             是否为单例对象，如果此参数为false，每次执行方法时创建一个新对象
     * @param args                    参数，必须严格对应指定方法的参数类型和数量
     * @return 返回结果
     */
    public static <T> T invoke(String classNameWithMethodName, boolean isSingleton, Object... args) {
        if (StringUtil.isEmpty(classNameWithMethodName)) {
            throw new UtilException("Blank classNameDotMethodName!");
        }

        int splitIndex = classNameWithMethodName.lastIndexOf('#');
        if (splitIndex <= 0) {
            splitIndex = classNameWithMethodName.lastIndexOf('.');
        }
        if (splitIndex <= 0) {
            throw new UtilException("Invalid classNameWithMethodName [{}]!", classNameWithMethodName);
        }

        final String className = classNameWithMethodName.substring(0, splitIndex);
        final String methodName = classNameWithMethodName.substring(splitIndex + 1);

        return invoke(className, methodName, isSingleton, args);
    }

    /**
     * 执行方法<br>
     * 可执行Private方法，也可执行static方法<br>
     * 执行非static方法时，必须满足对象有默认构造方法<br>
     * 非单例模式，如果是非静态方法，每次创建一个新对象
     *
     * @param <T>        对象类型
     * @param className  类名，完整类路径
     * @param methodName 方法名
     * @param args       参数，必须严格对应指定方法的参数类型和数量
     * @return 返回结果
     */
    public static <T> T invoke(String className, String methodName, Object[] args) {
        return invoke(className, methodName, false, args);
    }

    /**
     * 执行方法<br>
     * 可执行Private方法，也可执行static方法<br>
     * 执行非static方法时，必须满足对象有默认构造方法<br>
     *
     * @param <T>         对象类型
     * @param className   类名，完整类路径
     * @param methodName  方法名
     * @param isSingleton 是否为单例对象，如果此参数为false，每次执行方法时创建一个新对象
     * @param args        参数，必须严格对应指定方法的参数类型和数量
     * @return 返回结果
     */
    public static <T> T invoke(String className, String methodName, boolean isSingleton, Object... args) {
        Class<Object> clazz = loadClass(className);
        try {
            final Method method = getDeclaredMethod(clazz, methodName, getClasses(args));
            if (null == method) {
                throw new NoSuchMethodException(StringUtil.format("No such method: [{}]", methodName));
            }
            if (isStatic(method)) {
                return ReflectUtil.invoke(null, method, args);
            } else {
                return ReflectUtil.invoke(isSingleton ? Singleton.get(clazz) : clazz.newInstance(), method, args);
            }
        } catch (Exception e) {
            throw new UtilException(e);
        }
    }

    // ---------------------------------------------------------------------------------------------------- Invoke end

    /**
     * 是否为包装类型
     *
     * @param clazz 类
     * @return 是否为包装类型
     */
    public static boolean isPrimitiveWrapper(Class<?> clazz) {
        if (null == clazz) {
            return false;
        }
        return BasicType.WRAPPER_PRIMITIVE_MAP.containsKey(clazz);
    }

    /**
     * 是否为基本类型（包括包装类和原始类）
     *
     * @param clazz 类
     * @return 是否为基本类型
     */
    public static boolean isBasicType(Class<?> clazz) {
        if (null == clazz) {
            return false;
        }
        return (clazz.isPrimitive() || isPrimitiveWrapper(clazz));
    }

    /**
     * 是否简单值类型或简单值类型的数组<br>
     * 包括：原始类型,、String、other CharSequence, a Number, a Date, a URI, a URL, a Locale or a Class及其数组
     *
     * @param clazz 属性类
     * @return 是否简单值类型或简单值类型的数组
     */
    public static boolean isSimpleTypeOrArray(Class<?> clazz) {
        if (null == clazz) {
            return false;
        }
        return isSimpleValueType(clazz) || (clazz.isArray() && isSimpleValueType(clazz.getComponentType()));
    }

    /**
     * 是否为简单值类型<br>
     * 包括：原始类型,、String、other CharSequence, a Number, a Date, a URI, a URL, a Locale or a Class.
     *
     * @param clazz 类
     * @return 是否为简单值类型
     */
    public static boolean isSimpleValueType(Class<?> clazz) {
        return isBasicType(clazz)
                //
                || clazz.isEnum()
                //
                || CharSequence.class.isAssignableFrom(clazz)
                //
                || Number.class.isAssignableFrom(clazz)
                //
                || Date.class.isAssignableFrom(clazz)
                //
                || clazz.equals(URI.class)
                //
                || clazz.equals(URL.class)
                //
                || clazz.equals(Locale.class)
                //
                || clazz.equals(Class.class);
    }

    /**
     * 检查目标类是否可以从原类转化<br>
     * 转化包括：<br>
     * 1、原类是对象，目标类型是原类型实现的接口<br>
     * 2、目标类型是原类型的父类<br>
     * 3、两者是原始类型或者包装类型（相互转换）
     *
     * @param targetType 目标类型
     * @param sourceType 原类型
     * @return 是否可转化
     */
    public static boolean isAssignable(Class<?> targetType, Class<?> sourceType) {
        if (null == targetType || null == sourceType) {
            return false;
        }

        // 对象类型
        if (targetType.isAssignableFrom(sourceType)) {
            return true;
        }

        // 基本类型
        if (targetType.isPrimitive()) {
            // 原始类型
            Class<?> resolvedPrimitive = BasicType.WRAPPER_PRIMITIVE_MAP.get(sourceType);
            return targetType.equals(resolvedPrimitive);
        } else {
            // 包装类型
            Class<?> resolvedWrapper = BasicType.PRIMITIVE_WRAPPER_MAP.get(sourceType);
            return resolvedWrapper != null && targetType.isAssignableFrom(resolvedWrapper);
        }
    }

    /**
     * 指定类是否为Public
     *
     * @param clazz 类
     * @return 是否为public
     */
    public static boolean isPublic(Class<?> clazz) {
        if (null == clazz) {
            throw new NullPointerException("Class to provided is null.");
        }
        return Modifier.isPublic(clazz.getModifiers());
    }

    /**
     * 指定方法是否为Public
     *
     * @param method 方法
     * @return 是否为public
     */
    public static boolean isPublic(Method method) {
        Validate.notNull(method, "Method to provided is null.");
        return Modifier.isPublic(method.getModifiers());
    }

    /**
     * 指定类是否为非public
     *
     * @param clazz 类
     * @return 是否为非public
     */
    public static boolean isNotPublic(Class<?> clazz) {
        return !isPublic(clazz);
    }

    /**
     * 指定方法是否为非public
     *
     * @param method 方法
     * @return 是否为非public
     */
    public static boolean isNotPublic(Method method) {
        return !isPublic(method);
    }

    /**
     * 是否为静态方法
     *
     * @param method 方法
     * @return 是否为静态方法
     */
    public static boolean isStatic(Method method) {
        Validate.notNull(method, "Method to provided is null.");
        return Modifier.isStatic(method.getModifiers());
    }

    /**
     * 设置方法为可访问
     *
     * @param method 方法
     * @return 方法
     */
    public static Method setAccessible(Method method) {
        if (null != method && !method.isAccessible()) {
            method.setAccessible(true);
        }
        return method;
    }

    /**
     * 是否为抽象类
     *
     * @param clazz 类
     * @return 是否为抽象类
     */
    public static boolean isAbstract(Class<?> clazz) {
        return Modifier.isAbstract(clazz.getModifiers());
    }

    /**
     * 是否为标准的类<br>
     * 这个类必须：
     *
     * <pre>
     * 1、非接口
     * 2、非抽象类
     * 3、非Enum枚举
     * 4、非数组
     * 5、非注解
     * 6、非原始类型（int, long等）
     * </pre>
     *
     * @param clazz 类
     * @return 是否为标准类
     */
    public static boolean isNormalClass(Class<?> clazz) {
        //
        return null != clazz
                //
                && !clazz.isInterface()
                //
                && !isAbstract(clazz)
                //
                && !clazz.isEnum()
                //
                && !clazz.isArray()
                //
                && !clazz.isAnnotation()
                //
                && !clazz.isSynthetic()
                //
                && !clazz.isPrimitive();
    }

    /**
     * 判断类是否为枚举类型
     *
     * @param clazz 类
     * @return 是否为枚举类型
     */
    public static boolean isEnum(Class<?> clazz) {
        return null != clazz && clazz.isEnum();
    }

    /**
     * 获得给定类的第一个泛型参数
     *
     * @param clazz 被检查的类，必须是已经确定泛型类型的类
     * @return {@link Class}
     */
    public static Class<?> getTypeArgument(Class<?> clazz) {
        return getTypeArgument(clazz, 0);
    }

    /**
     * 获得给定类的泛型参数
     *
     * @param clazz 被检查的类，必须是已经确定泛型类型的类
     * @param index 泛型类型的索引号，既第几个泛型类型
     * @return {@link Class}
     */
    public static Class<?> getTypeArgument(Class<?> clazz, int index) {
        final Type argumentType = TypeUtil.getTypeArgument(clazz, index);
        return TypeUtil.getClass(argumentType);
    }

    /**
     * 获得给定类所在包的名称<br>
     * 例如：<br>
     * com.uneed.common.core.reflect.ClassUtil =》 com.uneed.common.core.reflect
     *
     * @param clazz 类
     * @return 包名
     */
    public static String getPackage(Class<?> clazz) {
        if (clazz == null) {
            return StringPool.EMPTY;
        }
        final String className = clazz.getName();
        int packageEndIndex = className.lastIndexOf(StringPool.DOT);
        if (packageEndIndex == -1) {
            return StringPool.EMPTY;
        }
        return className.substring(0, packageEndIndex);
    }

    /**
     * 获得给定类所在包的路径<br>
     * 例如：<br>
     * com.uneed.common.core.reflect.ClassUtil =》 com/uneed/common/core/reflect
     *
     * @param clazz 类
     * @return 包名
     */
    public static String getPackagePath(Class<?> clazz) {
        return getPackage(clazz).replace(CharPool.DOT, CharPool.SLASH);
    }

    /**
     * 获取指定类型分的默认值<br>
     * 默认值规则为：
     *
     * <pre>
     * 1、如果为原始类型，返回0
     * 2、非原始类型返回{@code null}
     * </pre>
     *
     * @param clazz 类
     * @return 默认值
     */
    public static Object getDefaultValue(Class<?> clazz) {
        if (clazz.isPrimitive()) {
            if (long.class == clazz) {
                return 0L;
            } else if (int.class == clazz) {
                return 0;
            } else if (short.class == clazz) {
                return (short) 0;
            } else if (char.class == clazz) {
                return (char) 0;
            } else if (byte.class == clazz) {
                return (byte) 0;
            } else if (double.class == clazz) {
                return 0D;
            } else if (float.class == clazz) {
                return 0f;
            } else if (boolean.class == clazz) {
                return false;
            }
        }

        return null;
    }

    /**
     * 获得默认值列表
     *
     * @param classes 值类型
     * @return 默认值列表
     */
    public static Object[] getDefaultValues(Class<?>... classes) {
        final Object[] values = new Object[classes.length];
        for (int i = 0; i < classes.length; i++) {
            values[i] = getDefaultValue(classes[i]);
        }
        return values;
    }

    /**
     * 是否为JDK中定义的类或接口，判断依据：
     *
     * <pre>
     * 1、以java.、javax.开头的包名
     * 2、ClassLoader为null
     * </pre>
     *
     * @param clazz 被检查的类
     * @return 是否为JDK中定义的类或接口
     */
    public static boolean isJdkClass(Class<?> clazz) {
        final Package objectPackage = clazz.getPackage();
        if (null == objectPackage) {
            return false;
        }
        final String objectPackageName = objectPackage.getName();
        return objectPackageName.startsWith("java.") || objectPackageName.startsWith("javax.") || clazz.getClassLoader() == null;
    }
}