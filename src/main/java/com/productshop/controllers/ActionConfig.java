package com.productshop.controllers;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ActionConfig {
    String defaultViewLayoutName() default "";
    String title() default "";
}