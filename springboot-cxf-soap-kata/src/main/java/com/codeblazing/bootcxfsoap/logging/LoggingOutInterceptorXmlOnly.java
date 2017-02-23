package com.codeblazing.bootcxfsoap.logging;

import org.apache.cxf.interceptor.LoggingMessage;
import org.apache.cxf.interceptor.LoggingOutInterceptor;

/**
 * @author: Arek Czarnoglowski
 * @created: 23.02.2017 23:46
 * @version: 1.0
 */
public class LoggingOutInterceptorXmlOnly extends LoggingOutInterceptor {

	@Override
	protected String formatLoggingMessage(LoggingMessage loggingMessage) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("Outbound Message:\n");

		// Only write the Payload (SOAP-Xml) to Logger
		if (loggingMessage.getPayload().length() > 0) {
			buffer.append(loggingMessage.getPayload());
		}
		return buffer.toString();
	}
}
