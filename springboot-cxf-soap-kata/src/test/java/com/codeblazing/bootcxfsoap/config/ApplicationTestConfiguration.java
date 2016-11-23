package com.codeblazing.bootcxfsoap.config;

import com.codeblazing.bootcxfsoap.endpoint.WeatherServiceEndpoint;
import com.codeblazing.namespace.weatherservice.WeatherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author: Arek Czarnoglowski
 * @created: 2016-11-23 21:08
 * @version: 1.0
 */
@Configuration
@Import(ApplicationConfiguration.class)
public class ApplicationTestConfiguration {

	@Bean
	WeatherService weatherService() {
		return new WeatherServiceEndpoint();
	}
}
