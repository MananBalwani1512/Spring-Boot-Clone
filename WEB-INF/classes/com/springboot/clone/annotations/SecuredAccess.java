package com.springboot.clone.annotations;
import java.lang.annotation.*;
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SecuredAccess
{
	String checkPost();
	String guard();
}