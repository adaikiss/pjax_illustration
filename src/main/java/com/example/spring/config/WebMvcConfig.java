package com.example.spring.config;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2CollectionHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.ParameterContentNegotiationStrategy;
import org.springframework.web.accept.PathExtensionContentNegotiationStrategy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.example.spring.support.page.PageRequestArgumentResolver;
import com.example.spring.support.stereotype.WebComponent;
import com.example.spring.support.web.OptionalPathVariableMethodArgumentResolver;

@Configuration
@Import({ AppConfig.class, FreeMarkerViewConfig.class })
@EnableScheduling
@ComponentScan(basePackages = { "com.example" }, includeFilters = {
		@Filter(value = Controller.class, type = FilterType.ANNOTATION),
		@Filter(value = WebComponent.class, type = FilterType.ANNOTATION) })
public class WebMvcConfig extends WebMvcConfigurationSupport {

	@Autowired
	private FreeMarkerViewResolver freemarkerViewResolver;

	@Bean
	public ContentNegotiationManager mvcContentNegotiationManager() {
		ContentNegotiationManager contentNegotiationManager = super
				.mvcContentNegotiationManager();
		contentNegotiationManager
				.addFileExtensionResolvers(
						new ParameterContentNegotiationStrategy(
								getDefaultMediaTypes()),
						new PathExtensionContentNegotiationStrategy(
								getDefaultMediaTypes()));
		return contentNegotiationManager;
	}

	protected Map<String, MediaType> getDefaultMediaTypes() {
		Map<String, MediaType> mediaTypes = super.getDefaultMediaTypes();
		mediaTypes.put("html", MediaType.TEXT_HTML);
		return mediaTypes;
	}

	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		configurer.setDefaultTimeout(30 * 1000L);
	}

	@Override
	protected void addArgumentResolvers(
			List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new PageRequestArgumentResolver());
		argumentResolvers.add(new OptionalPathVariableMethodArgumentResolver());
	}

	@Override
	protected void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		addDefaultHttpMessageConverters(converters);
		@SuppressWarnings("rawtypes")
		Jaxb2CollectionHttpMessageConverter jaxb2CollectionHttpMessageConverter = new Jaxb2CollectionHttpMessageConverter();
		converters.add(jaxb2CollectionHttpMessageConverter);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/res/**").addResourceLocations(
				"resources/");
	}

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public ViewResolver viewResolver() {
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setContentNegotiationManager(mvcContentNegotiationManager());
		resolver.setViewResolvers(Arrays
				.asList(new ViewResolver[] { freemarkerViewResolver }));
		return resolver;
	}
}
