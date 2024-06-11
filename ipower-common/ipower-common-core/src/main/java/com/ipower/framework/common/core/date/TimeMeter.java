package com.ipower.framework.common.core.date;

import com.ipower.framework.common.core.map.Maps;

import java.util.Map;

import static com.ipower.framework.common.core.lang.ObjectUtil.isNull;

/**
 * 计时器，用来记录用时.
 *
 * @author kris
 */
public class TimeMeter {

    /**
     * 开始时间，用来记录创建对象的时间
     */
    private final Long start = System.currentTimeMillis();

    /**
     * 结束时间，用来记录meter()的时间，每次只需会更新结束时间
     */
    private Long end;

    /**
     * 用来记录每次执行meter(String step)的时间
     */
    private final Map<String, Long> map = Maps.hashMap();

    public TimeMeter() {

    }

    /**
     * 计算时长，计算从创建TimeMeter对象到执行该方法的时长
     *
     * @return long ms
     */
    public long sign() {
        end = System.currentTimeMillis();
        return end - start;
    }

    /**
     * 用来记录执行步骤标记，并计算从创建TimeMeter对象到执行该步骤的时长
     *
     * @param step 执行步骤，标记需唯一，重复步骤标记将会被覆盖
     * @return long ms
     */
    public long sign(String step) {
        Long time = map.get(step);
        if (isNull(time)) {
            time = System.currentTimeMillis();
            map.put(step, time);
        }
        return time - start;
    }

    /**
     * 用来记录俩个步骤标记的时长。
     * 如果步骤一没被标记过，则开始时间将会取创建TimeMeter对象的时间。
     * 如果步骤二没被标记过，则先取结束时间，取不到结束时间的情况下，取当前时间，并对步骤二进行标记。
     *
     * @param stepOne 执行步骤一
     * @param stepTwo 执行步骤二
     * @return long ms
     */
    public long sign(String stepOne, String stepTwo) {
        Long one = map.get(stepOne);
        if (isNull(one)) {
            one = start;
        }
        Long two = map.get(stepTwo);
        if (isNull(two)) {
            two = end;
            if (isNull(two)) {
                two = System.currentTimeMillis();
                map.put(stepTwo, two);
            }
        }
        return two - one;
    }
}
