package com.janwarlen.learn.thread.simpleDateFormat;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class DateFormatUtil {

    private static ThreadLocal<Map<String, SimpleDateFormat>> tl = new ThreadLocal<>();

    public static SimpleDateFormat getSimpleDateFormat(String format) {
        Map<String, SimpleDateFormat> map = tl.get();
        if (null == map) {
            map = new HashMap<>();
            tl.set(map);
        }
        if (!map.containsKey(format)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            map.put(format, simpleDateFormat);
        }
        return map.get(format);
    }
}
