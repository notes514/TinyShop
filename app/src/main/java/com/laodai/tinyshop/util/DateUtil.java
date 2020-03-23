package com.laodai.tinyshop.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <pre>
 *     author : laodai
 *     e-mail : 851559442@qq.com
 *     time   : 2019/12/05
 *     desc   : 日期工具类
 *     version: 1.0
 * </pre>
 */
public class DateUtil {
    private static final Locale LOCALE = Locale.CHINA;

    public static String format(Date date, String str) {
        return new SimpleDateFormat(str, LOCALE).format(date);
    }
}
