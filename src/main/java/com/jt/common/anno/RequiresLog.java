package com.jt.common.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.Target;

/**
 * 自定义注解
 * @author Administrator
 *
 */
@Retention(RUNTIME)
@Target(ElementType.METHOD)
public @interface RequiresLog {
	String value() default "";
	
	
	
}
