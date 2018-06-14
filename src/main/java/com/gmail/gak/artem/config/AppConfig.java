package com.gmail.gak.artem.config;

import com.vaadin.spring.annotation.EnableVaadin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@EnableVaadin
@ComponentScan("com.gmail.gak.artem")
public class AppConfig {
}