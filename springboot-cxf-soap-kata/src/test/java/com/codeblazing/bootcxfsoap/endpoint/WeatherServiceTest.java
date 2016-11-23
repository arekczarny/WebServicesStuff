package com.codeblazing.bootcxfsoap.endpoint;

import com.codeblazing.bootcxfsoap.config.ApplicationTestConfiguration;
import com.codeblazing.namespace.weatherservice.WeatherException;
import com.codeblazing.namespace.weatherservice.WeatherService;
import com.codeblazing.namespace.weatherservice.general.ForecastRequest;
import com.codeblazing.namespace.weatherservice.general.ForecastReturn;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.codeblazing.bootcxfsoap.utils.TestHelper.generateDummyRequest;
import static org.junit.Assert.*;

/**
 * @author: Arek Czarnoglowski
 * @created: 2016-11-23 21:05
 * @version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationTestConfiguration.class)
public class WeatherServiceTest {

	@Autowired
	WeatherService weatherServiceEndpoint;

	@Test
	public void getCityForecastByZIP() throws WeatherException {
		// Given
		ForecastRequest forecastRequest = generateDummyRequest();

		// When
		ForecastReturn forecastReturn = weatherServiceEndpoint.getCityForecastByZIP(forecastRequest);

		// Then
		assertNotNull(forecastReturn);
		assertEquals(true, forecastReturn.isSuccess());
		assertEquals("Weimar", forecastReturn.getCity());
		assertEquals("22%", forecastReturn.getForecastResult().getForecast().get(0).getProbabilityOfPrecipiation().getDaytime());
	}


}