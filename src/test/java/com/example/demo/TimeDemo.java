package com.example.demo;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author cwh
 * @date 2020/10/10 9:13
 */
public class TimeDemo {

    @Test
    public void test(){
        /*System.out.println(System.currentTimeMillis());*/
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(simpleDateFormat.format("time:"+new Date()+"time"));




        /*
        System.out.println(new Date());
        System.out.println(LocalTime.now());
        System.out.println(LocalDateTime.now());*/
    }

}
