/**
 * 
 */
package com.ibsplc.neoicargo.framework.icgsupport.persistence;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author A-1759
 *
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface Key {

	String generator();

	String startAt() default "1";

	String resetAt() default "";

	String prefix() default "";

	String suffix() default "";

	boolean isCached() default false;

	String expression() default "";
}
