package com.codeblazing.bootcxfsoap.endpoint;

import com.codeblazing.bootcxfsoap.SimpleBootCxfSystemTestApplication;
import com.codeblazing.namespace.weatherservice.WeatherException;
import com.codeblazing.namespace.weatherservice.WeatherService;
import com.codeblazing.namespace.weatherservice.general.ForecastRequest;
import com.codeblazing.namespace.weatherservice.general.ForecastReturn;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.codeblazing.bootcxfsoap.utils.TestHelper.generateDummyRequest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author: Arek Czarnoglowski
 * @created: 2016-11-23 23:29
 * @version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SimpleBootCxfSystemTestApplication.class)
@WebIntegrationTest("server.port:8099")
public class WeatherServiceSystemTest {

	@Autowired
	private WeatherService weatherServiceSystemTestClient;

	@Test
	public void getCityForecastByZIP() throws WeatherException {
		// Given
		ForecastRequest forecastRequest = generateDummyRequest();

		// When
		ForecastReturn forecastReturn = weatherServiceSystemTestClient.getCityForecastByZIP(forecastRequest);

		// Then
		assertNotNull(forecastReturn);
		assertEquals(true, forecastReturn.isSuccess());
		assertEquals("Weimar", forecastReturn.getCity());
		assertEquals("22%", forecastReturn.getForecastResult().getForecast().get(0).getProbabilityOfPrecipiation().getDaytime());
	}
}
