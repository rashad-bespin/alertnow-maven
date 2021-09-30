package com.bespinglobal.alertnow.client;

import com.bespinglobal.alertnow.config.Options;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;

public class ClientFactory {
    private static final AtomicReference<Client> client = new AtomicReference();
    private final Options options;

    public ClientFactory(Options options) {
        this.options = options;
    }

    public Client get() {
        return client.updateAndGet(this.asSingleton());
    }

    private UnaryOperator<Client> asSingleton() {
        return (ref) -> (Client) (ref != null ? ref : new ClientImpl(options));
    }
}