package com.bespinglobal.alertnow.client;

import com.bespinglobal.alertnow.models.Integration;

public interface Client {
    void post(Integration integration);
}
