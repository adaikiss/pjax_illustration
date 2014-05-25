/**
 * 
 */
package com.example.freemarker;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * @author hlw
 * 
 */
public class MyFreeMarkerConfigurer extends FreeMarkerConfigurer implements
		ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	protected void postProcessConfiguration(Configuration config)
			throws IOException, TemplateException {
		// 获取实现TemplateModel的bean
		Map<String, TemplateModel> beans = applicationContext
				.getBeansOfType(TemplateModel.class);

		for (String key : beans.keySet()) {
			config.setSharedVariable(key, beans.get(key));
		}

		// 添加静态方法
		config.setSharedVariable("statics", BeansWrapper.getDefaultInstance()
				.getStaticModels());
		config.setSharedVariable("enums", BeansWrapper.getDefaultInstance()
				.getEnumModels());
		super.postProcessConfiguration(config);
	}

}
