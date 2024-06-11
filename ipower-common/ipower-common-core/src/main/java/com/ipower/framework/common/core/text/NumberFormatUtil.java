package com.ipower.framework.common.core.text;

import com.ipower.framework.common.core.lang.Validate;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import static com.ipower.framework.common.core.lang.ObjectUtil.nullToDefault;
import static java.math.RoundingMode.HALF_UP;

/**
 * @author kris
 */
public final class NumberFormatUtil {
    /**
     * Don't let anyone instantiate this class.
     */
    private NumberFormatUtil() {
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    public static String format(Number value, String pattern, RoundingMode mode) {
        Validate.notNull(value, "value can't be null!");
        Validate.notEmpty(pattern, "pattern can't be null!");
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        decimalFormat.setRoundingMode(nullToDefault(mode, HALF_UP));
        return decimalFormat.format(value);
    }
}
