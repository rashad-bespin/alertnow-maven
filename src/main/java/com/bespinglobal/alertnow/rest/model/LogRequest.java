package com.bespinglobal.alertnow.rest.model;

import com.bespinglobal.alertnow.Platform;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogRequest {
    private Platform platform;
    private String value;
}
