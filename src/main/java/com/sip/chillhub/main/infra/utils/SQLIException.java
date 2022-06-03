package com.sip.chillhub.main.infra.utils;

import com.sip.chillhub.main.infra.exception.ApiDataValidationException;

public class SQLIException extends ApiDataValidationException {

	public SQLIException() {
		super("error.msg.found.sql.injection", "Unexpected SQL Commands found", null);
	}

}
