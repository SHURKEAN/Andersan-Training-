package com.example.andersantrainingspring.config;

import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext rootCtx = new AnnotationConfigWebApplicationContext();
        rootCtx.register(AppConfig.class, WebMvcConfig.class);
        rootCtx.refresh();

        // DispatcherServlet
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher",
                new DispatcherServlet(rootCtx));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        // UTF-8 filter
        CharacterEncodingFilter enc = new CharacterEncodingFilter();
        enc.setEncoding("UTF-8");
        enc.setForceEncoding(true);
        FilterRegistration.Dynamic filter = servletContext.addFilter("encodingFilter", enc);
        filter.addMappingForUrlPatterns(null, false, "/*");
    }
}
