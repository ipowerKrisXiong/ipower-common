package com.ipower.framework.common.core.text;

import com.ipower.framework.common.core.collection.Lists;
import com.ipower.framework.common.core.entity.Demo;
import com.ipower.framework.common.core.map.Maps;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * StrFormatter Tester.
 *
 * @author diablo
 * @version 1.0.0
 */
public class StringFormatterTest {

    @Before
    public void before() {
        //TODO: Test before goes here...
    }

    @After
    public void after() {
        //TODO: Test after goes here...
    }

    /**
     * Method: format(final String pattern, final Object... args)
     */
    @Test
    public void testFormat() {
        String str = "this is {} for {}";
        Assert.assertEquals("this is a for 2", StringFormatter.format(str, "a", 2));
        str = "this is \\{} for {}";
        Assert.assertEquals("this is {} for a", StringFormatter.format(str, "a", 2));
        str = "this is \\\\{} for {}";
        Assert.assertEquals("this is \\a for 2", StringFormatter.format(str, "a", 2));
        str = "this is {{}} for {}";
        Assert.assertEquals("this is {a} for 2", StringFormatter.format(str, "a", 2));
        str = "this is [{}] for [{}]";
        Assert.assertEquals("this is [a] for [2]", StringFormatter.format(str, "a", 2));
    }

    @Test
    public void testGroupMap() {
        int size = 42135;
        Map<String, Demo> map = Maps.hashMap();
        for (int i = 0; i < size; i++) {
            Demo demo = new Demo();
            demo.setCode("c-" + i);
            demo.setName("n-" + i);
            demo.setAge(i % 2 == 0 ? 18 : 20);
            demo.setStudentId(1);
            map.put(demo.getCode(), demo);
        }
        Assert.assertEquals(42135, map.size());
        List<Map<String, Demo>> list = groupDemoMap(map);
        Assert.assertEquals(43, list.size());
        list.stream().mapToInt(Map::size).forEach(System.out::println);
    }

    private List<Map<String, Demo>> groupDemoMap(Map<String, Demo> areaMap) {
        List<Map<String, Demo>> list = Lists.arrayList();
        Map<String, Demo> map = null;
        int i = 0;
        for (Map.Entry<String, Demo> entry : areaMap.entrySet()) {
            if (i % 1000 == 0) {
                map = Maps.hashMap();
                list.add(map);
            }
            map.put(entry.getKey(), entry.getValue());
            i++;
        }
        return list;
    }
} 
