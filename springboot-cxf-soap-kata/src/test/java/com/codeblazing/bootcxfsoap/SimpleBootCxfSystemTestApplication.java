package com.codeblazing.bootcxfsoap;

import com.codeblazing.bootcxfsoap.config.WebServiceSystemTestConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author: Arek Czarnoglowski
 * @created: 2016-11-23 23:27
 * @version: 1.0
 */
@SpringBootApplication
@Import(WebServiceSystemTestConfiguration.class)
public class SimpleBootCxfSystemTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleBootCxfSystemTestApplication.class, args);
	}
}
