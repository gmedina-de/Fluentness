package org.fluentness.controller;

public class Action {
    private String method;
    private String route;
    private ActionExecutor executor;

    public Action(String method, String route, ActionExecutor executor) {
        this.method = method;
        this.route = route;
        this.executor = executor;
    }

    public String getMethod() {
        return method;
    }

    public String getRoute() {
        return route;
    }

    public ActionExecutor getExecutor() {
        return executor;
    }
}