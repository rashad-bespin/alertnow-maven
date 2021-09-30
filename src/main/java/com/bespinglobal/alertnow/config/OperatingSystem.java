package com.bespinglobal.alertnow.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

import java.util.Map;

public final class OperatingSystem {
    public static final String TYPE = "os";
    @Nullable
    private String name;
    @Nullable
    private String version;
    @Nullable
    private String rawDescription;
    @Nullable
    private String build;
    @Nullable
    private String kernelVersion;
    @Nullable
    private Boolean rooted;
    @Nullable
    private Map<String, Object> unknown;

    public OperatingSystem() {
    }

    OperatingSystem(@NotNull OperatingSystem operatingSystem) {
        this.name = operatingSystem.name;
        this.version = operatingSystem.version;
        this.rawDescription = operatingSystem.rawDescription;
        this.build = operatingSystem.build;
        this.kernelVersion = operatingSystem.kernelVersion;
        this.rooted = operatingSystem.rooted;
    }

    @Nullable
    public String getName() {
        return this.name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getVersion() {
        return this.version;
    }

    public void setVersion(@Nullable String version) {
        this.version = version;
    }

    @Nullable
    public String getRawDescription() {
        return this.rawDescription;
    }

    public void setRawDescription(@Nullable String rawDescription) {
        this.rawDescription = rawDescription;
    }

    @Nullable
    public String getBuild() {
        return this.build;
    }

    public void setBuild(@Nullable String build) {
        this.build = build;
    }

    @Nullable
    public String getKernelVersion() {
        return this.kernelVersion;
    }

    public void setKernelVersion(@Nullable String kernelVersion) {
        this.kernelVersion = kernelVersion;
    }

    @Nullable
    public Boolean isRooted() {
        return this.rooted;
    }

    public void setRooted(@Nullable Boolean rooted) {
        this.rooted = rooted;
    }

    @TestOnly
    @Nullable
    Map<String, Object> getUnknown() {
        return this.unknown;
    }
}
