package com.example.spring.support.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.method.support.UriComponentsContributor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Method argument resolver supports optional path variable. usage: <code>
 * <pre>
 * &#64;RequestMapping(value = {"/page", "/page/{page}"})
 * public String page(&#64;OptionalPathVariable(defaultValue = "1") int page){
 * &nbsp;&nbsp;......
 * }
 * <pre>
 * </code>
 * 
 * @author hlw
 * 
 */
public class OptionalPathVariableMethodArgumentResolver extends
		AbstractNamedValueMethodArgumentResolver implements
		UriComponentsContributor {

	private static final TypeDescriptor STRING_TYPE_DESCRIPTOR = TypeDescriptor
			.valueOf(String.class);

	public OptionalPathVariableMethodArgumentResolver() {
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if (!parameter.hasParameterAnnotation(OptionalPathVariable.class)) {
			return false;
		}
		return true;
	}

	@Override
	protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
		OptionalPathVariable annotation = parameter
				.getParameterAnnotation(OptionalPathVariable.class);
		return new OptionalPathVariableNamedValueInfo(annotation);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected Object resolveName(String name, MethodParameter parameter,
			NativeWebRequest request) throws Exception {
		Map<String, String> uriTemplateVars = (Map<String, String>) request
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE,
						RequestAttributes.SCOPE_REQUEST);
		return (uriTemplateVars != null) ? uriTemplateVars.get(name) : null;
	}

	@Override
	protected void handleMissingValue(String name, MethodParameter param)
			throws ServletRequestBindingException {
		String defaultValue = param.getParameterAnnotation(
				OptionalPathVariable.class).defaultValue();
		// we have defaltValue (づ￣ 3￣)づ
		if (!StringUtils.isEmpty(defaultValue)) {
			return;
		}
		String paramType = param.getParameterType().getName();
		throw new ServletRequestBindingException(
				"Missing URI template variable '" + name
						+ "' for method parameter type [" + paramType + "]");
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void handleResolvedValue(Object arg, String name,
			MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest request) {

		String key = View.PATH_VARIABLES;
		int scope = RequestAttributes.SCOPE_REQUEST;
		Map<String, Object> pathVars = (Map<String, Object>) request
				.getAttribute(key, scope);
		if (pathVars == null) {
			pathVars = new HashMap<String, Object>();
			request.setAttribute(key, pathVars, scope);
		}
		pathVars.put(name, arg);
	}

	@Override
	public void contributeMethodArgument(MethodParameter parameter,
			Object value, UriComponentsBuilder builder,
			Map<String, Object> uriVariables,
			ConversionService conversionService) {

		if (Map.class.isAssignableFrom(parameter.getParameterType())) {
			return;
		}

		OptionalPathVariable annot = parameter
				.getParameterAnnotation(OptionalPathVariable.class);
		String name = StringUtils.isEmpty(annot.value()) ? parameter
				.getParameterName() : annot.value();

		if (conversionService != null) {
			value = conversionService.convert(value, new TypeDescriptor(
					parameter), STRING_TYPE_DESCRIPTOR);
		}

		uriVariables.put(name, value);
	}

	private static class OptionalPathVariableNamedValueInfo extends
			NamedValueInfo {

		public OptionalPathVariableNamedValueInfo(
				OptionalPathVariable annotation) {
			super(annotation.value(), true, annotation.defaultValue());
		}
	}

}