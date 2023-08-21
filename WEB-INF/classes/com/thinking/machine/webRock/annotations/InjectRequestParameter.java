package com.thinking.machine.webRock.annotations;
import java.lang.annotation.*;
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InjectRequestParameter
{
    String Name() default "";
}
