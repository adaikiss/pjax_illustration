/**
 * 
 */
package com.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.example.spring.config.AppConfig;
import com.example.spring.config.WebMvcConfig;

/**
 * @author hlw
 * 
 */
public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{

		final AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(AppConfig.class);
		final AnnotationConfigWebApplicationContext dispatcherContext  = new AnnotationConfigWebApplicationContext();
		dispatcherContext .register(WebMvcConfig.class);
        final ServletHolder servletHolder = new ServletHolder(new DispatcherServlet(dispatcherContext ));

		WebAppContext context = new WebAppContext();
		context.setContextPath("/");
		context.setResourceBase("src/main/webapp");
		context.addEventListener(new ContextLoaderListener(rootContext));
		context.addServlet(servletHolder, "/*");
		context.addServlet(DefaultServlet.class, "/");
		int webPort = 80;

		final Server server = new Server(webPort);

		server.setStopAtShutdown(true);

		server.setHandler(context);
		server.start();
		System.out.println("Hit Enter in console to stop server");
		if (System.in.read() != 0) {
			server.stop();
			System.out.println("Server stopped");
		}
	}

}
