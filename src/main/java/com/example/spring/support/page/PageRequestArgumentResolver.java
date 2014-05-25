/**
 * 
 */
package com.example.spring.support.page;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.core.MethodParameter;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.example.spring.support.page.PageRequest.Sort;
import com.example.spring.support.page.PageRequestDefaults.SortDefaults;

/**
 * @author hlw
 * 
 */
public class PageRequestArgumentResolver implements
		HandlerMethodArgumentResolver {

	private static final PageRequest DEFAULT_PAGE_REQUEST = new PageRequest(1,
			10);
	private static final String DEFAULT_PREFIX = "page";
	private static final String DEFAULT_SEPARATOR = ".";

	private PageRequest fallbackPageRequest = DEFAULT_PAGE_REQUEST;
	private String prefix = DEFAULT_PREFIX;
	private String separator = DEFAULT_SEPARATOR;

	public void setFallbackPageRequest(PageRequest fallbackPageRequest) {
		this.fallbackPageRequest = null == fallbackPageRequest ? DEFAULT_PAGE_REQUEST
				: fallbackPageRequest;
	}

	/**
	 * Setter to configure the prefix of request parameters to be used to
	 * retrieve paging information. Defaults to {@link #DEFAULT_PREFIX}.
	 * 
	 * @param prefix
	 *            the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = null == prefix ? DEFAULT_PREFIX : prefix;
	}

	/**
	 * Setter to configure the separator between prefix and actual property
	 * value. Defaults to {@link #DEFAULT_SEPARATOR}.
	 * 
	 * @param separator
	 *            the separator to set
	 */
	public void setSeparator(String separator) {
		this.separator = null == separator ? DEFAULT_SEPARATOR : separator;
	}

	private PageRequest getDefaultFromAnnotationOrFallback(
			PageRequestDefaults defaults) {
		if (null != defaults) {
			return new PageRequest(defaults.pageNumber(), defaults.pageSize());
		}

		// Construct request with fallback request to ensure sensible
		// default values. Create fresh copy as Spring will manipulate the
		// instance under the covers
		return new PageRequest(fallbackPageRequest.getPageNumber(),
				fallbackPageRequest.getPageSize());
	}

	private PageRequestDefaults getPageRequestDefaults(
			MethodParameter methodParameter) {

		// search for PageableDefaults annotation
		for (Annotation annotation : methodParameter.getParameterAnnotations()) {
			if (annotation instanceof PageRequestDefaults) {
				PageRequestDefaults defaults = (PageRequestDefaults) annotation;
				return defaults;
			}
		}

		return null;
	}

	/**
	 * Resolves the prefix to use to bind properties from. Will prepend a
	 * possible {@link Qualifier} if available or return the configured prefix
	 * otherwise.
	 * 
	 * @param parameter
	 * @return
	 */
	private String getPrefix(MethodParameter parameter) {

		for (Annotation annotation : parameter.getParameterAnnotations()) {
			if (annotation instanceof Qualifier) {
				return new StringBuilder(((Qualifier) annotation).value())
						.append("_").append(prefix).toString();
			}
		}

		return prefix;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(PageRequest.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		PageRequestDefaults defaults = getPageRequestDefaults(parameter);
		PageRequest pageRequest = getDefaultFromAnnotationOrFallback(defaults);
		ServletRequest servletRequest = (ServletRequest) webRequest
				.getNativeRequest();
		PropertyValues propertyValues = new ServletRequestParameterPropertyValues(
				servletRequest, getPrefix(parameter), separator);
		DataBinder binder = new ServletRequestDataBinder(pageRequest);
		binder.initDirectFieldAccess();
		binder.registerCustomEditor(List.class, new SortPropertyEditor(
				"sort.order", propertyValues));
		binder.bind(propertyValues);
		if (pageRequest.getSort() == null && defaults != null && defaults.sorts().length > 0) {
			List<Sort> sort = new ArrayList<Sort>(4);
			for (SortDefaults sortDefaults : defaults.sorts()) {
				sort.add(new Sort(sortDefaults.sort(), sortDefaults.order()));
			}
			pageRequest.setSort(sort);
		}
		return pageRequest;

	}

	private static class SortPropertyEditor extends CustomCollectionEditor {

		private final String orderName;
		private final PropertyValues values;

		public SortPropertyEditor(String orderName, PropertyValues values) {
			super(ArrayList.class, false);
			this.orderName = orderName;
			this.values = values;
		}

		@Override
		protected Object convertElement(Object element) {
			PropertyValue rawOrder = values.getPropertyValue(orderName);
			return new Sort(element.toString(),
					rawOrder == null ? Sort.ORDER_ASC : rawOrder.getValue()
							.toString());
		}
	}
}
