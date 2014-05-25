/**
 * 
 */
package com.example.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;

import com.example.spring.support.stereotype.WebComponent;

/**
 * @author hlw
 * 
 */
@Configuration
@EnableScheduling
@ComponentScan(basePackages = { "com.example" }, excludeFilters = {
		@Filter(value = Controller.class, type = FilterType.ANNOTATION),
		@Filter(value = WebComponent.class, type = FilterType.ANNOTATION) })
public class AppConfig {
}
