package com.codeblazing.bootcxfsoap.utils;

/**
 * @author: Arek Czarnoglowski
 * @created: 01.12.2016 00:03
 * @version: 1.0
 */
public class XmlUtilsException extends Exception {
	private static final long serialVersionUID = 1L;

	public XmlUtilsException(Throwable cause) {
		super(cause);
	}

	public XmlUtilsException(String message, Throwable cause) {
		super(message, cause);
	}

	public XmlUtilsException(String message) {
		super(message);
	}

}
