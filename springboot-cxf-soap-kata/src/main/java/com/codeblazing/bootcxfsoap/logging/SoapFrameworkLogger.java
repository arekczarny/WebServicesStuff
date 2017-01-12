package com.codeblazing.bootcxfsoap.logging;

import com.codeblazing.bootcxfsoap.common.FaultConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: Arek Czarnoglowski
 * @created: 18.12.2016 22:53
 * @version: 1.0
 */
public class SoapFrameworkLogger {

	private Logger delegateLogger;

	private SoapFrameworkLogger() {}

	public static <L> SoapFrameworkLogger getLogger(Class<L> class2LogFor) {
		SoapFrameworkLogger frameworkLogger = new SoapFrameworkLogger();
		frameworkLogger.delegateLogger = LoggerFactory.getLogger(class2LogFor);
		return frameworkLogger;
	}

	/*
	 * Errors - 9xx
	 */
	public void errorAccuredInBackendProcessing(Throwable cause) {
		logError("901", "An Error accured in backend-processing: {}", cause.getMessage());
	}

	public void failedToBuildWeatherServiceCompliantSoapFaultDetails(Throwable cause) {
		logError("902", "Failed to build Weather-compliant SoapFault-details: {}\nStacktrace: {}", cause.getMessage(), cause.getStackTrace());
	}

	public void schemaValidationError(FaultConst error, String faultMessage) {
		logDebug("903", error.getMessage() + ": {}", faultMessage);
	}


	/*
	 * Logger-Methods - only private, to use just inside this class
	 */

	private void logDebug(String id, String messageTemplate, Object... parameters) {
		String msg = formatMessage(id, messageTemplate);
		delegateLogger.debug(msg, parameters);
	}

	private void logError(String id, String messageTemplate, Object... parameters) {
		String msg = formatMessage(id, messageTemplate);
		delegateLogger.error(msg, parameters);
	}

	private String formatMessage(String id, String messageTemplate) {
		return id + " >>> " + messageTemplate;
	}
}
