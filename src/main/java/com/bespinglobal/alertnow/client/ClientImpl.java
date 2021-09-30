package com.bespinglobal.alertnow.client;

import com.bespinglobal.alertnow.models.Integration;
import com.bespinglobal.alertnow.config.Options;
import com.bespinglobal.alertnow.models.Platform;
import com.bespinglobal.alertnow.client.exception.ClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Slf4j
public class ClientImpl extends AbstractClient implements Client {
    private final ObjectMapper objectMapper;
    private final Options options;

    public ClientImpl(@NotNull Options options) {
        super(options);
        objectMapper = ObjectMapperFactory.get();
        this.options = options;
    }

    @Override
    public void post(@NotNull Integration integration) {
        String url = String.format("/api/integration/appinsight/v1/%s", options.getApiKey());

        integration.setEventId(UUID.randomUUID().toString().replaceAll("-", ""));
        integration.setPlatform(Platform.maven.toString());
        integration.setPackage(options.getRelease());

        Map<String, String> tag = new Hashtable<>();
        tag.put("Level", "Info");

        try {
            if (tag != null && tag.size() > 0) {
                integration.setTag(objectMapper.writeValueAsString(tag));
            }
            doPost(url, toJson(integration));
        } catch (ClientException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private <T> String toJson(T object) {
        try {
            return this.objectMapper.writeValueAsString(Objects.requireNonNull(object));
        } catch (JsonProcessingException var3) {
            throw new IllegalArgumentException("Failed to create JSON", var3);
        }
    }
}
