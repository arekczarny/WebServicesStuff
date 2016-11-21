package com.codeblazing.bootcxfsoap.config;

import com.codeblazing.bootcxfsoap.endpoint.WeatherServiceEndpoint;
import com.codeblazing.namespace.weatherservice.WeatherService;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * @author: Arek Czarnoglowski
 * @created: 2016-11-21 22:25
 * @version: 1.0
 */
@Configuration
public class WSConfiguration {

	@Bean
	public ServletRegistrationBean dispatcherServlet() {
		return new ServletRegistrationBean(new CXFServlet(), "/soap-api/*");
	}

	@Bean(name = Bus.DEFAULT_BUS_ID)
	public SpringBus cxfSpringBus() {
		return new SpringBus();
	}

	@Bean
	public WeatherService weatherService() {
		return new WeatherServiceEndpoint();
	}

	@Bean
	public Endpoint endpoint() {
		EndpointImpl endpoint = new EndpointImpl(cxfSpringBus(), weatherService());
		endpoint.publish("/WeatherSoapService_1.0");
		endpoint.setWsdlLocation("Weather1.0.wsdl");
		return endpoint;
	}
}
