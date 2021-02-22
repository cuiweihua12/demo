package com.example.demo.annotation;

import java.lang.annotation.*;

/**
 * <p>Title: 访问统计注解</p>
 * <p>Description: </p>
 * @author cwh
 * @date 2021/2/22 11:06
 */
@Target(ElementType.METHOD)//使用在方法上
@Retention(RetentionPolicy.RUNTIME)//运行时有效
@Documented
public @interface AccessStatistics {
}
