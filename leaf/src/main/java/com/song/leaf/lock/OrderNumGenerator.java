package com.song.leaf.lock;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderNumGenerator {
    private static int count = 0;

    public static String getNumber() {
        // 生成订单号
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        return simpleDateFormat.format(new Date()) + "-" + ++count;
    }
}
