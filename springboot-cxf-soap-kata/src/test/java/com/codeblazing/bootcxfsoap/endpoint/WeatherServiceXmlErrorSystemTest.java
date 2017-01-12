package com.codeblazing.bootcxfsoap.endpoint;

import com.codeblazing.bootcxfsoap.SimpleBootCxfSystemTestApplication;
import com.codeblazing.bootcxfsoap.common.FaultConst;
import com.codeblazing.bootcxfsoap.common.XmlUtilsException;
import com.codeblazing.bootcxfsoap.utils.SoapRawClient;
import com.codeblazing.bootcxfsoap.utils.SoapRawClientResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author: Arek Czarnoglowski
 * @created: 19.12.2016 12:52
 * @version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=SimpleBootCxfSystemTestApplication.class)
@WebIntegrationTest("server.port:8099")
public class WeatherServiceXmlErrorSystemTest {

	@Autowired
	private SoapRawClient soapRawClient;


	@Value(value="classpath:requests/xmlerrors/xmlErrorNotXmlSchemeCompliantUnderRootElementTest.xml")
	private Resource xmlErrorNotXmlSchemeCompliantUnderRootElementTestXml;

	@Value(value="classpath:requests/xmlerrors/xmlErrorNotXmlSchemeCompliantRootElementTest.xml")
	private Resource xmlErrorNotXmlSchemeCompliantRootElementTestXml;

	@Value(value="classpath:requests/xmlerrors/xmlErrorSoapHeaderMissingSlash.xml")
	private Resource xmlErrorSoapHeaderMissingSlashXml;

	@Value(value="classpath:requests/xmlerrors/xmlErrorSoapBodyTagMissingBracketTest.xml")
	private Resource xmlErrorSoapBodyTagMissingBracketTestXml;

	@Value(value="classpath:requests/xmlerrors/xmlErrorSoapHeaderTagMissingBracketTest.xml")
	private Resource xmlErrorSoapHeaderTagMissingBracketTestXml;

	@Value(value="classpath:requests/xmlerrors/xmlErrorSoapEnvelopeTagMissingBracketTest.xml")
	private Resource xmlErrorSoapEnvelopeTagMissingBracketTestXml;

	@Value(value="classpath:requests/xmlerrors/xmlErrorXMLHeaderDefinitionMissingBracket.xml")
	private Resource xmlErrorXMLHeaderDefinitionMissingBracketXml;

	@Value(value="classpath:requests/xmlerrors/xmlErrorXMLTagNotClosedInsideBodyTest.xml")
	private Resource xmlErrorXMLTagNotClosedInsideBodyTestXml;



	/*
	 * Non-Scheme-compliant Errors
	 */

	@Test
	public void xmlErrorNotXmlSchemeCompliantUnderRootElementTest() throws XmlUtilsException, IOException {
		checkXmlError(xmlErrorNotXmlSchemeCompliantUnderRootElementTestXml, FaultConst.SCHEME_VALIDATION_ERROR);
	}

	@Test
	public void xmlErrorNotXmlSchemeCompliantRootElementTest() throws XmlUtilsException, IOException {
		checkXmlError(xmlErrorNotXmlSchemeCompliantRootElementTestXml, FaultConst.SCHEME_VALIDATION_ERROR);
	}

	@Test
	public void xmlErrorSoapHeaderMissingSlash() throws XmlUtilsException, IOException {
		checkXmlError(xmlErrorSoapHeaderMissingSlashXml, FaultConst.SCHEME_VALIDATION_ERROR);
	}

	/*
	 * Errors with syntactically incorrect XML
	 */

	@Test
	public void xmlErrorSoapBodyTagMissingBracketTest() throws XmlUtilsException, IOException {
		checkXmlError(xmlErrorSoapBodyTagMissingBracketTestXml, FaultConst.SYNTACTICALLY_INCORRECT_XML_ERROR);
	}

	@Test
	public void xmlErrorSoapHeaderTagMissingBracketTest() throws XmlUtilsException, IOException {
		checkXmlError(xmlErrorSoapHeaderTagMissingBracketTestXml, FaultConst.SYNTACTICALLY_INCORRECT_XML_ERROR);
	}

	@Test
	public void xmlErrorSoapEnvelopeTagMissingBracketTest() throws XmlUtilsException, IOException {
		checkXmlError(xmlErrorSoapEnvelopeTagMissingBracketTestXml, FaultConst.SYNTACTICALLY_INCORRECT_XML_ERROR);
	}

	@Test
	public void xmlErrorXMLHeaderDefinitionMissingBracket() throws XmlUtilsException, IOException {
		checkXmlError(xmlErrorXMLHeaderDefinitionMissingBracketXml, FaultConst.SYNTACTICALLY_INCORRECT_XML_ERROR);
	}

	@Test
	public void xmlErrorXMLTagNotClosedInsideBodyTest() throws XmlUtilsException, IOException {
		checkXmlError(xmlErrorXMLTagNotClosedInsideBodyTestXml, FaultConst.SYNTACTICALLY_INCORRECT_XML_ERROR);
	}


	private void checkXmlError(Resource testFile, FaultConst faultContent) throws XmlUtilsException, IOException {
		// When
		SoapRawClientResponse soapRawResponse = soapRawClient.callSoapService(testFile.getInputStream());

		// Then
		assertNotNull(soapRawResponse);
		assertEquals("500 Internal Server Error expected", 500, soapRawResponse.getHttpStatusCode());
		assertEquals(faultContent.getMessage(), soapRawResponse.getFaultstringValue());

		com.codeblazing.namespace.weatherservice.exception.WeatherException weatherException =
				soapRawResponse.getUnmarshalledObjectFromSoapMessage(com.codeblazing.namespace.weatherservice.exception.WeatherException.class);
		assertNotNull("<soap:Fault><detail> has to contain a de.codecentric.namespace.weatherservice.exception.WeatherException",  weatherException);

		assertEquals("ExtremeRandomNumber", weatherException.getUuid());
		assertEquals("The correct BusinessId is missing in WeatherException according to XML-scheme.", faultContent.getId(), weatherException.getBusinessErrorId());
	}
}
