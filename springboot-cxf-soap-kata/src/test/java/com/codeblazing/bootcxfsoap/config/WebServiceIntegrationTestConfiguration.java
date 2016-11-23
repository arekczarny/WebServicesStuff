package com.codeblazing.bootcxfsoap.config;

import com.codeblazing.namespace.weatherservice.WeatherService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Arek Czarnoglowski
 * @created: 2016-11-23 22:48
 * @version: 1.0
 */
@Configuration
public class WebServiceIntegrationTestConfiguration {

	@Bean
	WeatherService weatherServiceIntegrationTestClient() {
		JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
		jaxWsProxyFactoryBean.setServiceClass(WeatherService.class);
		jaxWsProxyFactoryBean.setAddress("http://localhost:8090" + WSConfiguration.BASE_URL + WSConfiguration.SERVICE_URL);
		return (WeatherService) jaxWsProxyFactoryBean.create();
	}
}
