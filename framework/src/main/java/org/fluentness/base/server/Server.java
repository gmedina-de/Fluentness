package org.fluentness.base.server;

import org.fluentness.base.Service;

import java.util.Map;

public interface Server extends Service {

    void start(Map<String, HttpHandler> routing);

    void stop();
}
