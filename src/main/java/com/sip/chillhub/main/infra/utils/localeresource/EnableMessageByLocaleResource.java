package com.sip.chillhub.main.infra.utils.localeresource;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;


/**
 * 
 * Annotation to enable message by locale resource
 * 
 * to change messages path, just declare a new MessageByLocaleResource and specify the path
 * 
 * @see CustomMessageByLocaleResourceConfig
 * 
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({CustomMessageByLocaleResourceConfig.class})
public @interface EnableMessageByLocaleResource {

}