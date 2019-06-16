package org.fluentness.controller;

import org.fluentness.common.networking.HttpRequest;
import org.fluentness.common.networking.HttpResponse;

@FunctionalInterface
public interface ActionExecutor {
    HttpResponse execute(HttpRequest request);
}
