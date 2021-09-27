package com.bespinglobal.alertnow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Options {
    private String accessToken;

    @NotNull
    private String apiKey;

    @NotNull
    private String host;
}
