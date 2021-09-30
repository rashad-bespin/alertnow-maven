package com.bespinglobal.alertnow.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.TimeZone;

public class ObjectMapperFactory {
    private static boolean isNotStrict = true;
    private static ObjectMapper objectMapper;

    public static ObjectMapper get() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.setTimeZone(TimeZone.getDefault());
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.registerModule(new JavaTimeModule());
            if (isNotStrict) {
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
                objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
                objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
                objectMapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
            }
        }

        return objectMapper;
    }

    static void setNotStrict(boolean isNotStrict) {
        ObjectMapperFactory.isNotStrict = isNotStrict;
    }
}
