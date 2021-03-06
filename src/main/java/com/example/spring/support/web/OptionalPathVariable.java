/**
 * 
 */
package com.example.spring.support.web;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hlw
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OptionalPathVariable {
	/**
	 * The URI template variable to bind to.
	 */
	String value() default "";

	/**
	 * Default value for the path variable
	 */
	String defaultValue() default "";
}
