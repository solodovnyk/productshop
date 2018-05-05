package site.productshop.controllers;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ControllerConfig {
    String defaultActionName() default "";
    String defaultViewLayoutName() default "";
}