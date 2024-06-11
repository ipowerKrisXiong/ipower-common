package com.ipower.framework.common.core.reflect;

import org.junit.Test;

import java.lang.reflect.*;
import java.util.List;
import java.util.Map;

/**
 * Class description goes here.
 *
 * @author diablo
 */
public class TestReflect<E> {
    public static <T> void test(TestReflect p0, List<TestReflect> p1, Map<String, TestReflect> p2, List<String>[] p3,
                                Map<String, TestReflect>[] p4, List<? extends TestReflect> p5,
                                Map<? extends TestReflect, ? super TestReflect> p6, T p7, int p8) {

    }

    private Integer a;

    private int b;

    private E c;

    private int[] d;

    private Integer[] e;

    private String[] f;

    private TestReflect g;

    private TestReflect<Double> h;

    public static void main(String[] args) {

        Method[] methods = TestReflect.class.getMethods();

        for (int i = 0; i < methods.length; i++) {
            Method oneMethod = methods[i];

            if (oneMethod.getName().equals("test")) {

                System.out.println("--" + oneMethod.getReturnType());
                System.out.println("--" + TypeUtil.getClass(oneMethod.getReturnType()));
                Type[] types = oneMethod.getGenericParameterTypes();


                int j = 1;
                for (Type type : types) {
                    System.out.println(j + "======>" + TypeUtil.getClass(type));
                    j++;
                }

                //第一个参数，TestReflect p0
                Class type0 = (Class) types[0];
                System.out.println("type0:" + type0.getName());
                System.out.println("=======" + TypeUtil.getClass(type0));

                //第二个参数，List<TestReflect> p1
                Type type1 = types[1];
                Type[] parameterizedType1 = ((ParameterizedType) type1).getActualTypeArguments();
                Class parameterizedType1_0 = (Class) parameterizedType1[0];
                System.out.println("parameterizedType1_0:" + parameterizedType1_0.getName());
                System.out.println("=======" + TypeUtil.getClass(type1));

                //第三个参数，Map<String,TestReflect> p2
                Type type2 = types[2];
                Type[] parameterizedType2 = ((ParameterizedType) type2).getActualTypeArguments();
                Class parameterizedType2_0 = (Class) parameterizedType2[0];
                System.out.println("parameterizedType2_0:" + parameterizedType2_0.getName());
                Class parameterizedType2_1 = (Class) parameterizedType2[1];
                System.out.println("parameterizedType2_1:" + parameterizedType2_1.getName());
                System.out.println("=======" + TypeUtil.getClass(type2));


                //第四个参数，List<String>[] p3
                Type type3 = types[3];
                Type genericArrayType3 = ((GenericArrayType) type3).getGenericComponentType();
                ParameterizedType parameterizedType3 = (ParameterizedType) genericArrayType3;
                Type[] parameterizedType3Arr = parameterizedType3.getActualTypeArguments();
                Class class3 = (Class) parameterizedType3Arr[0];
                System.out.println("class3:" + class3.getName());
                System.out.println("=======" + TypeUtil.getClass(type3));

                //第五个参数，Map<String,TestReflect>[] p4
                Type type4 = types[4];
                Type genericArrayType4 = ((GenericArrayType) type4).getGenericComponentType();
                ParameterizedType parameterizedType4 = (ParameterizedType) genericArrayType4;
                Type[] parameterizedType4Arr = parameterizedType4.getActualTypeArguments();
                Class class4_0 = (Class) parameterizedType4Arr[0];
                System.out.println("class4_0:" + class4_0.getName());
                Class class4_1 = (Class) parameterizedType4Arr[1];
                System.out.println("class4_1:" + class4_1.getName());


                //第六个参数，List<? extends TestReflect> p5
                Type type5 = types[5];
                Type[] parameterizedType5 = ((ParameterizedType) type5).getActualTypeArguments();
                for (Type type : parameterizedType5) {
                    System.out.println("----->" + TypeUtil.getClass(type));
                }

                Type[] parameterizedType5_0_upper = ((WildcardType) parameterizedType5[0]).getUpperBounds();
                System.out.println(">>>>>>>" + TypeUtil.getClass(parameterizedType5_0_upper[0]));
                Type[] parameterizedType5_0_lower = ((WildcardType) parameterizedType5[0]).getLowerBounds();
                System.out.println(">>>>>>>" + parameterizedType5_0_lower.length);
                System.out.println("=======" + TypeUtil.getClass(type5));

                //第七个参数，Map<? extends TestReflect,? super TestReflect> p6
                Type type6 = types[6];
                Type[] parameterizedType6 = ((ParameterizedType) type6).getActualTypeArguments();
                Type[] parameterizedType6_0_upper = ((WildcardType) parameterizedType6[0]).getUpperBounds();
                System.out.println(">>>>>>>" + TypeUtil.getClass(parameterizedType6_0_upper[0]));
                Type[] parameterizedType6_0_lower = ((WildcardType) parameterizedType6[0]).getLowerBounds();
                Type[] parameterizedType6_1_upper = ((WildcardType) parameterizedType6[1]).getUpperBounds();
                System.out.println(">>>>>>>" + TypeUtil.getClass(parameterizedType6_1_upper[0]));
                Type[] parameterizedType6_1_lower = ((WildcardType) parameterizedType6[1]).getLowerBounds();
                System.out.println(">>>>>>>" + TypeUtil.getClass(parameterizedType6_1_lower[0]));
                System.out.println("=======" + TypeUtil.getClass(type6));

            }
        }
    }

    @Test
    public void testGetType() {
        Field[] fields = TestReflect.class.getDeclaredFields();
        for (Field field : fields) {
            Type t1 = field.getGenericType();
            Type t2 = field.getType();
            Type t3 = TypeUtil.getType(field);
            Class c = TypeUtil.getClass(field);
            System.out.println("======== " + field.getName());
            System.out.println("==" + t1);
            System.out.println("==" + t2);
            System.out.println("==" + t3);
            System.out.println("==" + c);

        }

        TestReflect<String> h = new TestReflect<>();
        for (Field field : h.getClass().getDeclaredFields()) {
            Type t1 = field.getGenericType();
            Type t2 = field.getType();
            Type t3 = TypeUtil.getType(field);
            Class c = TypeUtil.getClass(field);
            System.out.println("======== " + field.getName());
            System.out.println("==" + t1);
            System.out.println("==" + t2);
            System.out.println("==" + t3);
            System.out.println("==" + c);

        }
    }

    @Test
    public void testGetClassType() {
        Method[] methods = TestReflect.class.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method oneMethod = methods[i];

            if (oneMethod.getName().equals("test")) {
                Type[] types = oneMethod.getGenericParameterTypes();

                long t1 = System.currentTimeMillis();


                for (int k = 0; k < 100000; k++) {
                    int j = 1;
                    for (Type type : types) {
                        Class c = TypeUtil.getClass(type);
                        //                        System.out.println(j + "======>" + TypeUtil.getClass(type));
                        j++;
                    }
                }
                long t2 = System.currentTimeMillis();
                System.out.println("----------->" + (t2 - t1));

            }
        }

    }
}
