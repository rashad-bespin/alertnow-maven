package com.bespinglobal.alertnow.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Integration {
    private String eventId;
    private String Package;
    private String platform;
    private String eventTime;
    private String message;
    private Level level;
    private String tag;
    private Detail detail;
}
