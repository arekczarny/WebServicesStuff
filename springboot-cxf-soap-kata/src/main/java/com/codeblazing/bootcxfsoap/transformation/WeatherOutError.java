package com.codeblazing.bootcxfsoap.transformation;

import com.codeblazing.bootcxfsoap.common.FaultConst;
import com.codeblazing.namespace.weatherservice.exception.WeatherException;

/**
 * @author: Arek Czarnoglowski
 * @created: 18.12.2016 23:13
 * @version: 1.0
 */
public class WeatherOutError {
	private static final com.codeblazing.namespace.weatherservice.exception.ObjectFactory objectFactoryDatatypes =
			new com.codeblazing.namespace.weatherservice.exception.ObjectFactory();

	private WeatherOutError() {
		// private Constructor for Utility-Class
	}

	public static WeatherException createWeatherException(FaultConst faultContent, String originalFaultMessage) {
		// Build SOAP-Fault detail <datatypes:WeatherException>
		WeatherException weatherException = objectFactoryDatatypes.createWeatherException();
		weatherException.setBigBusinessErrorCausingMoneyLoss(true);
		weatherException.setBusinessErrorId(faultContent.getId());
		weatherException.setExceptionDetails(originalFaultMessage);
		weatherException.setUuid("ExtremeRandomNumber");
		return weatherException;
	}
}
