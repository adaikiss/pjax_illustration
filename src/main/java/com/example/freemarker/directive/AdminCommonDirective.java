/**
 * 
 */
package com.example.freemarker.directive;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.example.freemarker.FreemarkerUtils;
import com.example.spring.support.stereotype.WebComponent;

import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * @author hlw
 * 
 */
@WebComponent("adminHeader")
public class AdminCommonDirective implements TemplateDirectiveModel {

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		// title
		String title = FreemarkerUtils.parseString("title", params);
		StringBuilder titleElement = new StringBuilder();
		titleElement.append("<title>");
		if (StringUtils.isNotBlank(title)) {
			titleElement.append(title).append("--");
		}
		titleElement.append("控制台</title>");
		env.getOut().write(titleElement.toString());
		env.getOut().write(getCommonHeader(env));
	}

	private String getCommonHeader(Environment env) throws TemplateException {
		StringBuilder html = new StringBuilder();

		String base = ((SimpleScalar) env.getGlobalVariable("base"))
				.getAsString();
		// metas
		html.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		html.append("<meta charset=\"UTF-8\">");

		// shortcut
		html.append("<link type=\"image/x-icon\" rel=\"shortcut icon\" href=\"")
				.append(base).append("/res/shortcut.ico\" />").append("\n");
		// js global variables
		html.append("<script type=\"text/javascript\">window.base = '")
				.append(base).append("'</script>").append("\n");
		// stylesheets
		addStyleSheets(html, base, "/res/admin.css");
		// javascripts
		addJavaScripts(html, base, "/res/jquery.min.js", "/res/jquery.pjax.js", "/res/admin.js");
		return html.toString();
	}

	private void addStyleSheets(StringBuilder html, String base,
			String... styleSheetPaths) {
		for (String styleSheetPath : styleSheetPaths) {
			html.append("<link rel=\"stylesheet\" href=\"").append(base)
					.append(styleSheetPath).append("\"/>").append("\n");
		}
	}

	private void addJavaScripts(StringBuilder html, String base,
			String... javaScriptPaths) {
		for (String javaScriptPath : javaScriptPaths) {
			html.append(
					"<script type=\"text/javascript\" language=\"javascript\" src=\"")
					.append(base).append(javaScriptPath).append("\"></script>")
					.append("\n");
		}
	}
}
