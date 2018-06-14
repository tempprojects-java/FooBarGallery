package com.gmail.gak.artem.config;

import com.vaadin.spring.server.SpringVaadinServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebInitializer  implements WebApplicationInitializer {
    public void onStartup(javax.servlet.ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.scan(WebInitializer.class.getPackage().getName());
        servletContext.addListener(new ContextLoaderListener(ctx));
        registerServlet(servletContext);
    }

    private void registerServlet(ServletContext servletContext) {
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("vaadin", SpringVaadinServlet.class);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/*");
    }
}
