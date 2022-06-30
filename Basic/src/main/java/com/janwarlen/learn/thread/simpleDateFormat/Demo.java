package com.janwarlen.learn.thread.simpleDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Demo {

    /**
     *
     */
    public static void func() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String[] dates = {"2001-01-01", "2002-02-02", "2003-03-03", "2004-04-04", "2005-05-05",
                "2006-06-06", "2007-07-07", "2008-08-08", "2009-09-09", "2010-10-10"};
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            threads[i] = new Thread(() -> {
                try {
                    Date parse = sdf.parse(dates[finalI]);
                    System.out.println(dates[finalI] + "convert into " + sdf.format(parse));
                } catch (Exception e) {
                    System.out.println("ex:" + dates[finalI]);
                    throw new RuntimeException(e);
                }
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public static void funcFix() {
        String[] dates = {"2001-01-01", "2002-02-02", "2003-03-03", "2004-04-04", "2005-05-05",
                "2006-06-06", "2007-07-07", "2008-08-08", "2009-09-09", "2010-10-10"};
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            threads[i] = new Thread(() -> {
                try {
                    SimpleDateFormat simpleDateFormat = DateFormatUtil.getSimpleDateFormat("yyyy-MM-dd");
                    Date parse = simpleDateFormat.parse(dates[finalI]);
                    System.out.println(dates[finalI] + "convert into " + simpleDateFormat.format(parse));
                } catch (Exception e) {
                    System.out.println("ex:" + dates[finalI]);
                    throw new RuntimeException(e);
                }
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public static void main(String[] args) {
//        func();
        funcFix();
    }
}
