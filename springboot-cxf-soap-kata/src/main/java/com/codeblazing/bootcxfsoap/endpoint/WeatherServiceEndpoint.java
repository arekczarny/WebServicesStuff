package com.codeblazing.bootcxfsoap.endpoint;

import com.codeblazing.bootcxfsoap.controller.WeatherServiceController;
import com.codeblazing.namespace.weatherservice.WeatherException;
import com.codeblazing.namespace.weatherservice.WeatherService;
import com.codeblazing.namespace.weatherservice.general.ForecastRequest;
import com.codeblazing.namespace.weatherservice.general.ForecastReturn;
import com.codeblazing.namespace.weatherservice.general.WeatherInformationReturn;
import com.codeblazing.namespace.weatherservice.general.WeatherReturn;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: Arek Czarnoglowski
 * @created: 2016-11-21 22:28
 * @version: 1.0
 */
public class WeatherServiceEndpoint implements WeatherService {

	@Autowired
	WeatherServiceController weatherServiceController;

	@Override
	public ForecastReturn getCityForecastByZIP(ForecastRequest forecastRequest) throws WeatherException {
		return weatherServiceController.getCityForecastByZIP(forecastRequest);
	}

	@Override
	public WeatherInformationReturn getWeatherInformation(String zip) throws WeatherException {
		return null;
	}

	@Override
	public WeatherReturn getCityWeatherByZIP(ForecastRequest forecastRequest) throws WeatherException {
		return null;
	}
}
