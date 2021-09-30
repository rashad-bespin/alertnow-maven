package com.bespinglobal.alertnow.client;

import com.bespinglobal.alertnow.client.exception.ClientException;

import java.util.Objects;

public class ClientResponse {
    private final Status status;
    private final String json;

    ClientResponse(Status status) {
        this(Status.EMPTY, (String)null);
    }

    ClientResponse(Status status, String json) {
        this.status = (Status) Objects.requireNonNull(status);
        switch(status) {
            case EMPTY:
                if (json != null) {
                    throw new IllegalArgumentException("Message not expected");
                }
                this.json = json;
                break;
            case MESSAGE:
            case UNAUTHORIZED:
            case ERROR:
                this.json = (String)Objects.requireNonNull(json);
                break;
            default:
                throw new IllegalArgumentException("Unexpected status " + status);
        }
    }

    void verify() throws ClientException {
        switch(this.status) {
            case EMPTY:
            case MESSAGE:
            case UNAUTHORIZED:
            case ERROR:
            default:
        }
    }

    public Status getStatus() {
        return this.status;
    }

    public String getJsonString() {
        return this.json;
    }

    public enum Status {
        EMPTY,
        MESSAGE,
        UNAUTHORIZED,
        ERROR;

        Status() {
        }
    }
}
