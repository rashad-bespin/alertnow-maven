package com.bespinglobal.alertnow.logcollector;

import com.bespinglobal.alertnow.logcollector.rest.RestService;
import com.bespinglobal.alertnow.logcollector.rest.RestServiceImpl;

import javax.validation.constraints.NotNull;

public class AlertNow {
    private static RestService restService;

    public static void init(@NotNull OptionsConfiguration<Options> optionsConfiguration) {
        Options options = new Options();
        optionsConfiguration.configure(options);
        init(options);
    }

    private static void init(@NotNull Options options) {
        restService = new RestServiceImpl(options);
    }

    public interface OptionsConfiguration<T extends Options> {
        void configure(@NotNull T var1);
    }

    public static void info(String message) {
        Log log = new Log();
        log.setLevel(Level.INFO);
        log.setMessage(message);
        restService.postLog(log);
    }

    public static void error(Throwable throwable) {
        Log log = new Log();
        log.setLevel(Level.ERROR);
        log.setMessage(throwable.getLocalizedMessage());
        restService.postLog(log);
    }
}
