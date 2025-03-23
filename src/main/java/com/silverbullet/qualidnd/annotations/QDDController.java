package com.silverbullet.qualidnd.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Custom annotation to prepend the mapping "/QDD/api" to all request mappings
 * 
 * @author Batman
 *
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@RestController
@RequestMapping("/QDD/api")
@ResponseBody
public @interface QDDController {
	@AliasFor(annotation = Component.class)
	String value() default "";
}