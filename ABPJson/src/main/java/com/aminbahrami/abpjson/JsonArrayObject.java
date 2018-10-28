package com.aminbahrami.abpjson;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface JsonArrayObject
{
	String name() default "";

	Class<?> type();
}
