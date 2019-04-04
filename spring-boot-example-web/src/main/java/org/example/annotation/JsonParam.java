package org.example.annotation;

import java.lang.annotation.*;

/**
 * Json参数注解
 */
@Documented
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonParam {

    String value() default "rawData";

}