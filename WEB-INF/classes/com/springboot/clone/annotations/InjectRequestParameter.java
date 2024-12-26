package com.springboot.clone.annotations;
import java.lang.annotation.*;
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface InjectRequestParameter
{
	String name() default "";
}