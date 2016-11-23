package com.codeblazing.bootcxfsoap.config;

import com.codeblazing.bootcxfsoap.controller.WeatherServiceController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Arek Czarnoglowski
 * @created: 2016-11-23 21:17
 * @version: 1.0
 */
@Configuration
public class ApplicationConfiguration {

	@Bean
	WeatherServiceController weatherServiceController() {
		return new WeatherServiceController();
	}
}
