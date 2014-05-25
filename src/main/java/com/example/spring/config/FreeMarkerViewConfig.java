/**
 * 
 */
package com.example.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.example.freemarker.MyFreeMarkerConfigurer;
import com.example.freemarker.RichFreeMarkerViewResolver;

/**
 * @author hlw
 *
 */
@Configuration
public class FreeMarkerViewConfig {

	@Bean
	public FreeMarkerViewResolver freemarkerViewResolver(){
		FreeMarkerViewResolver resolver = new RichFreeMarkerViewResolver();
		resolver.setContentType("text/html; charset=UTF-8");
		resolver.setExposeSpringMacroHelpers(true);
		resolver.setRequestContextAttribute("rc");
		resolver.setPrefix("/");
		resolver.setSuffix(".ftl");
		return resolver;
	}

	@Bean
	public FreeMarkerConfigurer freemarkerConfig(){
		FreeMarkerConfigurer configurer = new MyFreeMarkerConfigurer();
		configurer.setTemplateLoaderPath("WEB-INF/ftl/");
		configurer.setConfigLocation(new ClassPathResource("freemarker.properties"));
		return configurer;
	}

}
