package com.bespinglobal.alertnow;

import com.bespinglobal.alertnow.client.Client;
import com.bespinglobal.alertnow.client.ClientFactory;
import com.bespinglobal.alertnow.config.Options;
import com.bespinglobal.alertnow.models.Integration;
import com.bespinglobal.alertnow.models.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AlertNow {
    private static Client client;

    public static void init(@NotNull OptionsConfiguration<Options> optionsConfiguration) {
        Options options = new Options();
        optionsConfiguration.configure(options);
        init(options);
    }

    private static void init(@NotNull Options options) {
        Objects.requireNonNull(options, "AlertNow was not init!");
        client = new ClientFactory(options).get();
    }

    public interface OptionsConfiguration<T extends Options> {
        void configure(@NotNull T var1);
    }

    public static void info(@NotNull String message) {
        Integration log = new Integration();
        log.setLevel(Level.INFO);
        log.setMessage(message);
        Objects.requireNonNull(client, "Options is not configured!");
        client.post(log);
    }

    public static void error(@NotNull Throwable throwable) {
        Integration log = new Integration();
        log.setLevel(Level.ERROR);
        log.setMessage(throwable.getLocalizedMessage());
        client.post(log);
    }
}
