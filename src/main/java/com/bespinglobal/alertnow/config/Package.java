package com.bespinglobal.alertnow.config;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

public class Package {
    @NotNull
    private String name;
    @NotNull
    private String version;
    @Nullable
    private Map<String, Object> unknown;

    public Package(@NotNull String name, @NotNull String version) {
        this.name = (String) Objects.requireNonNull(name, "name is required.");
        this.version = (String)Objects.requireNonNull(version, "version is required.");
    }

    @Deprecated
    public Package() {
        this("", "");
    }

    @NotNull
    public String getName() {
        return this.name;
    }

    public void setName(@NotNull String name) {
        this.name = (String)Objects.requireNonNull(name, "name is required.");
    }

    @NotNull
    public String getVersion() {
        return this.version;
    }

    public void setVersion(@NotNull String version) {
        this.version = (String)Objects.requireNonNull(version, "version is required.");
    }

    @Internal
    public void acceptUnknownProperties(@NotNull Map<String, Object> unknown) {
        this.unknown = unknown;
    }
}
