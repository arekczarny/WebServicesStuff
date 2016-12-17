package com.codeblazing.bootcxfsoap.endpoint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import com.codeblazing.bootcxfsoap.SimpleBootCxfSystemTestApplication;
import com.codeblazing.bootcxfsoap.utils.XmlUtils;
import com.codeblazing.bootcxfsoap.utils.XmlUtilsException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.codeblazing.namespace.weatherservice.WeatherException;
import com.codeblazing.namespace.weatherservice.WeatherService;
import com.codeblazing.namespace.weatherservice.general.ForecastReturn;
import com.codeblazing.namespace.weatherservice.general.GetCityForecastByZIP;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=SimpleBootCxfSystemTestApplication.class)
@WebIntegrationTest("server.port:8099")
public class WeatherServiceXmlFileSystemTest {

	@Autowired
	private WeatherService weatherServiceSystemTestClient;

	@Value(value="classpath:requests/GetCityForecastByZIPTest.xml")
	private Resource getCityForecastByZIPTestXml;

	@Test
	public void getCityForecastByZIP() throws WeatherException, XmlUtilsException, IOException {
		// Given
		GetCityForecastByZIP getCityForecastByZIP = XmlUtils.readSoapMessageFromStreamAndUnmarshallBody2Object(getCityForecastByZIPTestXml.getInputStream(), GetCityForecastByZIP.class);

		// When
		ForecastReturn forecastReturn = weatherServiceSystemTestClient.getCityForecastByZIP(getCityForecastByZIP.getForecastRequest());

		// Then
		assertNotNull(forecastReturn);
		assertEquals(true, forecastReturn.isSuccess());
		assertEquals("Weimar", forecastReturn.getCity());
		assertEquals("22%", forecastReturn.getForecastResult().getForecast().get(0).getProbabilityOfPrecipiation().getDaytime());
	}
}
