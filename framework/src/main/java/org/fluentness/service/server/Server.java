package org.fluentness.service.server;

import org.fluentness.service.Service;

public interface Server extends Service {

    void start() throws Exception;

    void stop();
}