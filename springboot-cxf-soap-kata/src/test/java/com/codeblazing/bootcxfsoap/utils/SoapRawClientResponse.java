package com.codeblazing.bootcxfsoap.utils;

import com.codeblazing.bootcxfsoap.common.XmlUtils;
import com.codeblazing.bootcxfsoap.common.XmlUtilsException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * @author: Arek Czarnoglowski
 * @created: 19.12.2016 12:42
 * @version: 1.0
 */
public class SoapRawClientResponse {

	private int httpStatusCode;
	private Document httpResponseBody;

	public int getHttpStatusCode() {
		return httpStatusCode;
	}
	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}
	public Document getHttpResponseBody() {
		return httpResponseBody;
	}
	public void setHttpResponseBody(Document httpResponseBody) {
		this.httpResponseBody = httpResponseBody;
	}

	public Node getElementByNameWithNamespace(String namespaceUrl, String elementName) {
		return httpResponseBody.getElementsByTagNameNS(namespaceUrl, elementName).item(0);
	}

	public String getElementValueByName(String elementName) {
		Node node = httpResponseBody.getElementsByTagName(elementName).item(0);
		return node.getNodeValue();
	}

	public String getFaultstringValue() {
		Node fault = getElementByNameWithNamespace("http://schemas.xmlsoap.org/soap/envelope/", "Fault");
		// The second Node (with List-Nr. 1) is the <faultstring>
		Node faultstring = fault.getChildNodes().item(1);
		return faultstring.getTextContent();
	}

	public <T> T getUnmarshalledObjectFromSoapMessage(Class<T> jaxbClass) throws XmlUtilsException {
		return XmlUtils.getUnmarshalledObjectFromSoapMessage(httpResponseBody, jaxbClass);
	}
}
