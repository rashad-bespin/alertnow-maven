package com.bespinglobal.alertnow.client.exception;

public class IntegrationException extends ClientException {
    public IntegrationException(String message) {
        super(message);
    }

    public IntegrationException(String message, Object... args) {
        super(String.format(message, args));
    }
}
