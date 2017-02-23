package com.codeblazing.bootcxfsoap.logging;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingMessage;

/**
 * @author: Arek Czarnoglowski
 * @created: 23.02.2017 23:38
 * @version: 1.0
 */
public class LoggingInInterceptorXmlOnly extends LoggingInInterceptor {

	@Override
	protected String formatLoggingMessage(LoggingMessage loggingMessage) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("Inbound Msg:\n");

		// Only write the Payload (SOAP-Xml) to Logger
		if (loggingMessage.getPayload().length() > 0) {
			buffer.append(loggingMessage.getPayload());
		}
		return buffer.toString();
	}
}
