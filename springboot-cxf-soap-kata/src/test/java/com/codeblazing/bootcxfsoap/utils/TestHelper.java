package com.codeblazing.bootcxfsoap.utils;

import com.codeblazing.namespace.weatherservice.datatypes.ProductName;
import com.codeblazing.namespace.weatherservice.general.ForecastCustomer;
import com.codeblazing.namespace.weatherservice.general.ForecastRequest;

/**
 * @author: Arek Czarnoglowski
 * @created: 2016-11-23 21:34
 * @version: 1.0
 */
public class TestHelper {

	public static ForecastRequest generateDummyRequest() {
		ForecastRequest forecastRequest = new ForecastRequest();
		forecastRequest.setZIP("99425");
		forecastRequest.setFlagcolor("blackblue");
		forecastRequest.setProductName(ProductName.FORECAST_BASIC);
		ForecastCustomer customer = new ForecastCustomer();
		customer.setAge(67);
		customer.setContribution(500);
		customer.setMethodOfPayment("Bitcoin");
		forecastRequest.setForecastCustomer(customer);
		return forecastRequest;
	}
}
