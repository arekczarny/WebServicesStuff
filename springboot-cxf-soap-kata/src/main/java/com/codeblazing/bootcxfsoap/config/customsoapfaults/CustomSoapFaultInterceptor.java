package com.codeblazing.bootcxfsoap.config.customsoapfaults;

import com.codeblazing.bootcxfsoap.common.FaultConst;
import com.codeblazing.bootcxfsoap.logging.SoapFrameworkLogger;
import com.ctc.wstx.exc.WstxException;
import com.ctc.wstx.exc.WstxUnexpectedCharException;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;

import javax.xml.bind.UnmarshalException;

/**
 * @author: Arek Czarnoglowski
 * @created: 18.12.2016 22:48
 * @version: 1.0
 */
public class CustomSoapFaultInterceptor extends AbstractSoapInterceptor {

	private static final SoapFrameworkLogger LOG = SoapFrameworkLogger.getLogger(CustomSoapFaultInterceptor.class);

	public CustomSoapFaultInterceptor() {
		super(Phase.PRE_STREAM);
	}

	@Override
	public void handleMessage(SoapMessage soapMessage) {
		Fault fault = (Fault) soapMessage.getContent(Exception.class);
		Throwable faultCause = fault.getCause();
		String faultMessage = fault.getMessage();

		if (containsFaultIndicatingNotSchemeCompliantXml(faultCause, faultMessage)) {
			LOG.schemaValidationError(FaultConst.SCHEME_VALIDATION_ERROR, faultMessage);
			WeatherSoapFaultHelper.buildWeatherFaultAndSet2SoapMessage(soapMessage, FaultConst.SCHEME_VALIDATION_ERROR);
		} else if (containsFaultIndicatingSyntacticallyIncorrectXml(faultCause)) {
			LOG.schemaValidationError(FaultConst.SYNTACTICALLY_INCORRECT_XML_ERROR, faultMessage);
			WeatherSoapFaultHelper.buildWeatherFaultAndSet2SoapMessage(soapMessage, FaultConst.SYNTACTICALLY_INCORRECT_XML_ERROR);
		}
	}

	private boolean containsFaultIndicatingNotSchemeCompliantXml(Throwable faultCause, String faultMessage) {
		if(faultCause instanceof UnmarshalException
				// 1.) If the root-Element of the SoapBody is syntactically correct, but not scheme-compliant,
				// 		there is no UnmarshalException and we have to look for
				// 2.) Missing / lead to Faults without Causes, but to Messages like "Unexpected wrapper element XYZ found. Expected"
				// 		One could argue, that this is syntactically incorrect, but here we just take it as Non-Scheme-compliant
				|| isNotNull(faultMessage) && faultMessage.contains("Unexpected wrapper element")) {
			return true;
		}
		return false;
	}

	private boolean containsFaultIndicatingSyntacticallyIncorrectXml(Throwable faultCause) {
		if(faultCause instanceof WstxException
				// If Xml-Header is invalid, there is a wrapped Cause in the original Cause we have to check
				|| isNotNull(faultCause) && faultCause.getCause() instanceof WstxUnexpectedCharException
				|| faultCause instanceof IllegalArgumentException) {
			return true;
		}
		return false;
	}

	private boolean isNotNull(Object object) {
		return object != null;
	}
}
