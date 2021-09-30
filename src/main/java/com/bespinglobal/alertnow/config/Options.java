package com.bespinglobal.alertnow.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Locale;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Options {
    @NotNull
    private String apiKey;
    @NotNull
    private String host;
    @NotNull
    private int connectionTimeout = 15_000;
    @NotNull
    private int responseTimeout = 10_000;
    @Nullable
    private Boolean debug;
    @Nullable
    private String release;
    @Nullable
    private Map<String, String> tags;
    @Nullable
    private SdkVersion sdkVersion;

    public String getLanguage() {
        return Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getCountry();
    }

    public String getUserAgent() {
        return "LogCollector";
    }
}
