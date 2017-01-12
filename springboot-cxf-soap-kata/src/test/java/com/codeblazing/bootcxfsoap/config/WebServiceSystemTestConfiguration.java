package com.codeblazing.bootcxfsoap.config;

import com.codeblazing.bootcxfsoap.common.XmlUtilsException;
import com.codeblazing.bootcxfsoap.utils.SoapRawClient;
import com.codeblazing.namespace.weatherservice.WeatherService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Arek Czarnoglowski
 * @created: 2016-11-23 23:24
 * @version: 1.0
 */
@Configuration
public class WebServiceSystemTestConfiguration {

	private static final String WEB_SERVICE_URL = "http://localhost:8099" + WSConfiguration.BASE_URL + WSConfiguration.SERVICE_URL;

	@Bean
	WeatherService weatherServiceSystemTestClient() {
		JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
		jaxWsProxyFactoryBean.setServiceClass(WeatherService.class);
		jaxWsProxyFactoryBean.setAddress(WEB_SERVICE_URL);
		return (WeatherService) jaxWsProxyFactoryBean.create();
	}

	@Bean
	public SoapRawClient soapRawClient() throws XmlUtilsException {
		return new SoapRawClient(WEB_SERVICE_URL, WeatherService.class);
	}
}
