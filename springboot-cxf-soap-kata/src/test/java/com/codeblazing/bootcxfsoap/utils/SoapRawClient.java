package com.codeblazing.bootcxfsoap.utils;

import com.codeblazing.bootcxfsoap.common.XmlUtils;
import com.codeblazing.bootcxfsoap.common.XmlUtilsException;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * @author: Arek Czarnoglowski
 * @created: 19.12.2016 12:40
 * @version: 1.0
 */
@Component
public class SoapRawClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(SoapRawClient.class);
	private String soapAction;

	private String soapServiceUrl;

	public <T> SoapRawClient(String soapServiceUrl, Class<T> jaxWsServiceInterfaceClass) throws XmlUtilsException {
		this.soapAction = XmlUtils.getSoapActionFromJaxWsServiceInterface(jaxWsServiceInterfaceClass);
		this.soapServiceUrl = soapServiceUrl;
	}

	public SoapRawClientResponse callSoapService(InputStream xmlFile) throws XmlUtilsException {
		SoapRawClientResponse rawSoapResponse = new SoapRawClientResponse();

		LOGGER.debug("Calling SoapService with POST on Apache HTTP-Client and configured URL: {}", soapServiceUrl);

		try {
			Response httpResponseContainer = Request
					.Post(soapServiceUrl)
					.bodyStream(xmlFile, contentTypeTextXmlUtf8())
					.addHeader("SOAPAction", "\"" + soapAction + "\"")
					.execute();

			HttpResponse httpResponse = httpResponseContainer.returnResponse();
			rawSoapResponse.setHttpStatusCode(httpResponse.getStatusLine().getStatusCode());
			rawSoapResponse.setHttpResponseBody(XmlUtils.parseFileStream2Document(httpResponse.getEntity().getContent()));

		} catch (Exception exception) {
			throw new XmlUtilsException("Some Error accured while trying to Call SoapService for test: " + exception.getMessage());
		}
		return rawSoapResponse;
	}

	private ContentType contentTypeTextXmlUtf8() {
		return ContentType.create(ContentType.TEXT_XML.getMimeType(), Consts.UTF_8);
	}
}
