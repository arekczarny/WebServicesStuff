package com.codeblazing.bootcxfsoap.config;

import com.codeblazing.bootcxfsoap.config.customsoapfaults.CustomSoapFaultInterceptor;
import com.codeblazing.bootcxfsoap.endpoint.WeatherServiceEndpoint;
import com.codeblazing.bootcxfsoap.logging.LoggingInInterceptorXmlOnly;
import com.codeblazing.bootcxfsoap.logging.LoggingOutInterceptorXmlOnly;
import com.codeblazing.namespace.weatherservice.Weather;
import com.codeblazing.namespace.weatherservice.WeatherService;
import org.apache.cxf.Bus;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.interceptor.AbstractLoggingInterceptor;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
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

	public static final String BASE_URL = "/soap-api";
	public static final String SERVICE_URL = "/WeatherSoapService_1.0";

	@Bean
	public ServletRegistrationBean dispatcherServlet() {
		return new ServletRegistrationBean(new CXFServlet(), BASE_URL + "/*");
	}

	@Bean(name = Bus.DEFAULT_BUS_ID)
	public SpringBus cxfSpringBus() {
		SpringBus springBus = new SpringBus();

		//Logging
		LoggingFeature loggingFeature = new LoggingFeature();
		loggingFeature.setPrettyLogging(true);
		loggingFeature.initialize(springBus);
		springBus.getFeatures().add(loggingFeature);

		//Interceptors
		springBus.getInInterceptors().add(logInInterceptor());
		springBus.getInFaultInterceptors().add(logInInterceptor());
		springBus.getOutInterceptors().add(logOutInterceptor());
		springBus.getOutFaultInterceptors().add(logOutInterceptor());
		springBus.getOutFaultInterceptors().add(soapInterceptor());

		return springBus;
	}

	@Bean
	public WeatherService weatherService() {
		return new WeatherServiceEndpoint();
	}


	@Bean
	public Weather weather() {
		// Needed for correct ServiceName & WSDLLocation to publish contract first incl. original WSDL
		return new Weather();
	}

	@Bean
	public Endpoint endpoint() {
		EndpointImpl endpoint = new EndpointImpl(cxfSpringBus(), weatherService());
		endpoint.setServiceName(weather().getServiceName());
		endpoint.setWsdlLocation(weather().getWSDLDocumentLocation().toString());
		endpoint.publish(SERVICE_URL);

//		LoggingFeature loggingFeature = new LoggingFeature();
//		loggingFeature.setPrettyLogging(true);
//		loggingFeature.initialize(cxfSpringBus());
//
//		endpoint.getFeatures().add(loggingFeature);
//		endpoint.getOutFaultInterceptors().add(soapInterceptor());
		return endpoint;
	}

	@Bean
	public AbstractSoapInterceptor soapInterceptor() {
		return new CustomSoapFaultInterceptor();
	}

	@Bean
	public AbstractLoggingInterceptor logInInterceptor() {
		LoggingInInterceptor logInInterceptor = new LoggingInInterceptorXmlOnly();
		// The In-Messages are pretty without setting it - when setting it Apache CXF throws empty lines into the In-Messages
		logInInterceptor.setPrettyLogging(true);
		return logInInterceptor;
	}

	@Bean
	public AbstractLoggingInterceptor logOutInterceptor() {
		LoggingOutInterceptor logOutInterceptor = new LoggingOutInterceptorXmlOnly();
		logOutInterceptor.setPrettyLogging(true);
		return logOutInterceptor;
	}
}
