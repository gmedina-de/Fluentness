package org.fluentness.service.routing;

import org.fluentness.service.Service;

import java.util.Map;

public interface RoutingService extends Service {

    Map<String, HttpHandler> getRoutingMap();

}