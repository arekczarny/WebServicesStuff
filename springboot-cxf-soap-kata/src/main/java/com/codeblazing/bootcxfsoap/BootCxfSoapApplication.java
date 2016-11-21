package com.codeblazing.bootcxfsoap;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * @author: Arek Czarnoglowski
 * @created: 2016-11-21 20:38
 * @version: 1.0
 */
@SpringBootApplication
public class BootCxfSoapApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootCxfSoapApplication.class);
	}

	@Bean
	public ServletRegistrationBean dispatcherServlet() {
		return new ServletRegistrationBean(new CXFServlet(), "/soap-api/*");
	}

	@Bean(name = Bus.DEFAULT_BUS_ID)
	public SpringBus cxfSpringBus() {
		return new SpringBus();
	}
}
