package com.leotoneo.concurrency.annoations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标记线程不安全类
 */
@Target(ElementType.TYPE )
@Retention(RetentionPolicy.SOURCE)
public @interface UnSafeThread {
    String value() default "";
}
