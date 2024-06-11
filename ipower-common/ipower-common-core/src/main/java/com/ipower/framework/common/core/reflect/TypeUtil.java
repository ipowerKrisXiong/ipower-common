package com.ipower.framework.common.core.reflect;

import com.ipower.framework.common.core.collection.ArrayUtil;
import com.ipower.framework.common.core.collection.Lists;

import java.lang.reflect.*;
import java.util.List;

import static com.ipower.framework.common.core.lang.ObjectUtil.isNull;

/**
 * 针对 {@link Type} 的工具类封装<br>
 * 最主要功能包括：
 *
 * <pre>
 * 1. 获取方法的参数和返回值类型（包括Type和Class）
 * 2. 获取泛型参数类型（包括对象的泛型参数或集合元素的泛型类型）
 *
 * 参考:<a href="https://gitee.com/loolly/hutool">hutool</a>
 * </pre>
 *
 * @author kris
 * @since 1.0.0
 */
public class TypeUtil {

    /**
     * 获得Type对应的原始类
     *
     * @param type {@link Type}
     * @return 原始类，如果无法获取原始类，返回{@code null}
     */
    public static Class<?> getClass(Type type) {
        if (type != null) {
            if (type instanceof Class) {
                return (Class<?>) type;
            } else if (type instanceof ParameterizedType) {
                return (Class<?>) ((ParameterizedType) type).getRawType();
            } else if (type instanceof TypeVariable) {
                Type[] bounds = ((TypeVariable<?>) type).getBounds();
                if (bounds.length == 1) {
                    return getClass(bounds[0]);
                }
            } else if (type instanceof WildcardType) {
                final Type[] upperBounds = ((WildcardType) type).getUpperBounds();
                if (upperBounds.length == 1) {
                    return getClass(upperBounds[0]);
                }
            }
        }
        return null;
    }

    /**
     * 获取字段对应的Type类型<br>
     * 方法优先获取GenericType，获取不到则获取Type
     *
     * @param field 字段
     * @return {@link Type}，可能为{@code null}
     */
    public static Type getType(Field field) {
        return field == null ? null : field.getGenericType();
    }

    /**
     * 获得Field对应的原始类
     *
     * @param field {@link Field}
     * @return 原始类，如果无法获取原始类，返回{@code null}
     */
    public static Class<?> getClass(Field field) {
        return field == null ? null : field.getType();
    }

    // ----------------------------------------------------------------------------------- Param Type

    /**
     * 获取方法的第一个参数类型<br>
     * 优先获取方法的GenericParameterTypes，如果获取不到，则获取ParameterTypes
     *
     * @param method 方法
     * @return {@link Type}，可能为{@code null}
     */
    public static Type getParamType(Method method) {
        return getParamType(method, 0);
    }

    /**
     * 获取方法的参数类型<br>
     * 优先获取方法的GenericParameterTypes，如果获取不到，则获取ParameterTypes
     *
     * @param method 方法
     * @param index  第几个参数的索引，从0开始计数
     * @return {@link Type}，可能为{@code null}
     */
    public static Type getParamType(Method method, int index) {
        Type[] types = getParamTypes(method);
        return (types == null || index < 0 || index >= types.length) ? null : types[index];
    }

    /**
     * 获取方法的参数类型列表<br>
     * 优先获取方法的GenericParameterTypes，如果获取不到，则获取ParameterTypes
     *
     * @param method 方法
     * @return {@link Type}列表，可能为{@code null}
     * @see Method#getGenericParameterTypes()
     * @see Method#getParameterTypes()
     */
    public static Type[] getParamTypes(Method method) {
        return method == null ? null : method.getGenericParameterTypes();
    }

    /**
     * 获取方法的第一个参数类
     *
     * @param method 方法
     * @return 第一个参数类型，可能为{@code null}
     */
    public static Class<?> getParamClass(Method method) {
        return getParamClass(method, 0);
    }

    /**
     * 获取方法的参数类
     *
     * @param method 方法
     * @param index  第几个参数的索引，从0开始计数
     * @return 参数类，可能为{@code null}
     */
    public static Class<?> getParamClass(Method method, int index) {
        Class<?>[] classes = getParamClasses(method);
        return (classes == null || index < 0 || index >= classes.length) ? null : classes[index];
    }

    /**
     * 解析方法的参数类型列表<br>
     * 依赖jre\lib\rt.jar
     *
     * @param method t方法
     * @return 参数类型类列表
     * @see Method#getGenericParameterTypes
     * @see Method#getParameterTypes
     */
    public static Class<?>[] getParamClasses(Method method) {
        return method == null ? null : method.getParameterTypes();
    }

    // ----------------------------------------------------------------------------------- Return Type

    /**
     * 获取方法的返回值类型<br>
     * 获取方法的GenericReturnType
     *
     * @param method 方法
     * @return {@link Type}，可能为{@code null}
     * @see Method#getGenericReturnType()
     * @see Method#getReturnType()
     */
    public static Type getReturnType(Method method) {
        return method == null ? null : method.getGenericReturnType();
    }

    /**
     * 解析方法的返回类型类列表
     *
     * @param method 方法
     * @return 返回值类型的类
     * @see Method#getGenericReturnType
     * @see Method#getReturnType
     */
    public static Class<?> getReturnClass(Method method) {
        return method == null ? null : method.getReturnType();
    }

    // ----------------------------------------------------------------------------------- Type Argument

    /**
     * 获得给定类的第一个泛型参数
     *
     * @param type 被检查的类型，必须是已经确定泛型类型的类型
     * @return {@link Type}，可能为{@code null}
     */
    public static Type getTypeArgument(Type type) {
        return getTypeArgument(type, 0);
    }

    /**
     * 获得给定类的泛型参数
     *
     * @param type  被检查的类型，必须是已经确定泛型类型的类
     * @param index 泛型类型的索引号，既第几个泛型类型
     * @return {@link Type}
     */
    public static Type getTypeArgument(Type type, int index) {
        final Type[] arguments = getTypeArguments(type);
        return (arguments == null || index < 0 || index >= arguments.length) ? null : arguments[index];
    }

    /**
     * 获得指定类型中所有泛型参数类型，例如：
     *
     * <pre>
     * class A&lt;T&gt;
     * class B extends A&lt;String&gt;
     * </pre>
     * <p>
     * 通过此方法，传入B.class即可得到String
     *
     * @param type 指定类型
     * @return 所有泛型参数类型
     */
    public static Type[] getTypeArguments(Type type) {
        final ParameterizedType parameterizedType = toParameterizedType(type);
        return parameterizedType == null ? null : parameterizedType.getActualTypeArguments();
    }

    /**
     * 将{@link Type} 转换为{@link ParameterizedType}<br>
     * {@link ParameterizedType}用于获取当前类或父类中泛型参数化后的类型<br>
     * 一般用于获取泛型参数具体的参数类型，例如：
     *
     * <pre>
     * class A&lt;T&gt;
     * class B extends A&lt;String&gt;
     * </pre>
     * <p>
     * 通过此方法，传入B.class即可得到B{@link ParameterizedType}，从而获取到String
     *
     * @param type {@link Type}
     * @return {@link ParameterizedType}
     */
    public static ParameterizedType toParameterizedType(Type type) {
        return toParameterizedType(type, 0);
    }

    /**
     * 将{@link Type} 转换为{@link ParameterizedType}<br>
     * {@link ParameterizedType}用于获取当前类或父类中泛型参数化后的类型<br>
     * 一般用于获取泛型参数具体的参数类型，例如：
     *
     * <pre>{@code
     *   class A<T>
     *   class B extends A<String>;
     * }</pre>
     * <p>
     * 通过此方法，传入B.class即可得到B对应的{@link ParameterizedType}，从而获取到String
     *
     * @param type           {@link Type}
     * @param interfaceIndex 实现的第几个接口
     * @return {@link ParameterizedType}
     */
    public static ParameterizedType toParameterizedType(final Type type, final int interfaceIndex) {
        if (type instanceof ParameterizedType) {
            return (ParameterizedType) type;
        }

        if (type instanceof Class) {
            final ParameterizedType[] generics = getGenerics((Class<?>) type);
            if (interfaceIndex >= 0 && interfaceIndex < generics.length) {
                return generics[interfaceIndex];
            }
        }
        return null;
    }

    /**
     * 获取指定类所有泛型父类和泛型接口
     *
     * @param clazz 类
     * @return 泛型父类或接口数组
     * @since 6.0.0
     */
    public static ParameterizedType[] getGenerics(final Class<?> clazz) {
        final List<ParameterizedType> result = Lists.arrayList();
        // 泛型父类（父类及祖类优先级高）
        final Type genericSuper = clazz.getGenericSuperclass();
        if (genericSuper != null && !Object.class.equals(genericSuper)) {
            final ParameterizedType parameterizedType = toParameterizedType(genericSuper);
            if (parameterizedType != null) {
                result.add(parameterizedType);
            }
        }
        // 泛型接口
        final Type[] genericInterfaces = clazz.getGenericInterfaces();
        if (ArrayUtil.isNotEmpty(genericInterfaces)) {
            for (final Type genericInterface : genericInterfaces) {
                if (genericInterface instanceof ParameterizedType) {
                    result.add((ParameterizedType) genericInterface);
                }
            }
        }
        return result.toArray(new ParameterizedType[0]);
    }

    /**
     * 是否未知类型<br>
     * type为null或者{@link TypeVariable} 都视为未知类型
     *
     * @param type Type类型
     * @return 是否未知类型
     */
    public static boolean isUnknown(Type type) {
        return isNull(type) || type instanceof TypeVariable;
    }

    /**
     * 指定泛型数组中是否含有泛型变量
     *
     * @param types 泛型数组
     * @return 是否含有泛型变量
     */
    public static boolean hasTypeVerifiable(Type... types) {
        for (Type type : types) {
            if (type instanceof TypeVariable) {
                return true;
            }
        }
        return false;
    }
}
