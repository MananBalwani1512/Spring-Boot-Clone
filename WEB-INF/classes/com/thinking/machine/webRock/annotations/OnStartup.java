package com.thinking.machine.webRock.annotations;
import java.lang.annotation.*;
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnStartup
{
	int Priority() default 0;
}