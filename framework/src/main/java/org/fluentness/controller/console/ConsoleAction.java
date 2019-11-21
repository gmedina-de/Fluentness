package org.fluentness.controller.console;

import org.fluentness.controller.Controller;

import java.lang.reflect.Method;

public final class ConsoleAction implements Controller.Action {

    private String description;
    private String category;
    private Method method;

    ConsoleAction(String description, String category, Method method) {
        this.description = description;
        this.category = category;
        this.method = method;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public Method getMethod() {
        return method;
    }
}
