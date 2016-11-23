package com.codeblazing.bootcxfsoap.controller;

import com.codeblazing.bootcxfsoap.transformation.GetCityForecastByZIPOutMapper;
import com.codeblazing.namespace.weatherservice.general.ForecastRequest;
import com.codeblazing.namespace.weatherservice.general.ForecastReturn;
import org.springframework.stereotype.Component;

/**
 * @author: Arek Czarnoglowski
 * @created: 2016-11-23 21:10
 * @version: 1.0
 */
/*
 *  Example-Controller:
 *  This Class would be responsible for Mapping from Request to internal Datamodel (and backwards),
 *  for calling Backend-Services and handling Backend-Exceptions
 *  So it decouples the WSDL-generated Classes from the internal Classes - for when the former changes,
 *  nothing or only the mapping has to be changed
 */
@Component
public class WeatherServiceController {

	public ForecastReturn getCityForecastByZIP(ForecastRequest forecastRequest) {
	    /*
	     * We leave out inbound transformation, plausibility-checking, logging, backend-calls e.g.
	     * for the moment
	     */
		return GetCityForecastByZIPOutMapper.mapGeneralOutlook2Forecast();
	}
}
