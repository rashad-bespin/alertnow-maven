package com.bespinglobal.alertnow.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class AlertNowId {
    @NotNull
    private final UUID uuid;
    public static final AlertNowId EMPTY_ID = new AlertNowId(new UUID(0L, 0L));

    public AlertNowId() {
        this((UUID) null);
    }

    public AlertNowId(@Nullable UUID uuid) {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        this.uuid = uuid;
    }

    public AlertNowId(@NotNull String alertNowId) {
        this.uuid = this.fromStringSentryId(alertNowId);
    }

    public String toString() {
        return this.uuid.toString().replace("-", "");
    }

    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            AlertNowId sentryId = (AlertNowId) o;
            return this.uuid.compareTo(sentryId.uuid) == 0;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.uuid.hashCode();
    }

    @NotNull
    private UUID fromStringSentryId(@NotNull String alertNowId) {
        if (alertNowId.length() == 32) {
            alertNowId = (new StringBuilder(alertNowId))
                    .insert(8, "-")
                    .insert(13, "-")
                    .insert(18, "-")
                    .insert(23, "-")
                    .toString();
        }

        if (alertNowId.length() != 36) {
            throw new IllegalArgumentException("String representation of AlertNowId has either 32 (UUID no dashes) or 36 characters long (completed UUID). Received: " + alertNowId);
        } else {
            return UUID.fromString(alertNowId);
        }
    }
}
