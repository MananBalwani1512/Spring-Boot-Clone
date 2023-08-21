package com.thinking.machine.webRock.annotations;
import java.lang.annotation.*;
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface SecuredAccess
{
    String checkPost() default "";
    String gaurd() default "";    
}
