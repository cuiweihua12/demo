package com.example.demo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

/**
 * @author cwh
 * @date 2020/10/9 17:01
 */
public class Demo {
    public static void main(String[] args) {
        System.out.println(0/10);
        //时间戳
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        System.out.println(timestamp);
        System.out.println(timestamp.getTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String format = simpleDateFormat.format(timestamp.getTime());
        System.out.println(format);
    }


}
