package com.aminbahrami.abpjson;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface JsonField
{
	String name() default "";
	
	Type type() default Type.String;
}