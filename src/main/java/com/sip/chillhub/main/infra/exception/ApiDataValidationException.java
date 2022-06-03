package com.sip.chillhub.main.infra.exception;

import java.util.List;

public class ApiDataValidationException extends RuntimeException {

    private final String globalisationMessageCode;
    private final String defaultUserMessage;
    private final List<String> errors;

    public ApiDataValidationException(final List<String> errors) {
    	super("Validation errors exist.");
        this.globalisationMessageCode = "validation.msg.validation.errors.exist";
        this.defaultUserMessage = "Validation errors exist.";
        this.errors = errors;
    }

    public ApiDataValidationException(final String globalisationMessageCode, final String defaultUserMessage,
            final List<String> errors) {
    	super(defaultUserMessage);
        this.globalisationMessageCode = globalisationMessageCode;
        this.defaultUserMessage = defaultUserMessage;
        this.errors = errors;
    }

    public String getGlobalisationMessageCode() {
        return this.globalisationMessageCode;
    }

    public String getDefaultUserMessage() {
        return this.defaultUserMessage;
    }

    public List<String> getErrors() {
        return this.errors;
    }
}
