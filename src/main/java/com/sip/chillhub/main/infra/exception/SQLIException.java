package com.sip.chillhub.main.infra.exception;

public class SQLIException extends ApiDataValidationException {

	public SQLIException() {
		super("error.msg.found.sql.injection", "Unexpected SQL Commands found", null);
	}

}
