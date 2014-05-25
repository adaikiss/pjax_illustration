/**
 * 
 */
package com.example.freemarker;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * expanded freemarker view resolver
 * @author hlw
 * 
 */
public class RichFreeMarkerViewResolver extends FreeMarkerViewResolver {

	@Override
	protected Class<? extends FreeMarkerView> requiredViewClass() {
		return RichFreeMarkerView.class;
	}

}
