package org.fluentness.controller.web;

import java.lang.annotation.*;

import static org.fluentness.controller.web.Controller.HttpMethod.GET;

public abstract class Controller<W extends View> implements org.fluentness.controller.Controller {

    protected final W web;

    public Controller() {
        W web = null;
        try {
            web = ((Class<W>) Class.forName(getClass().getCanonicalName().replace("Controller", ""))).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            System.exit(1);
        }
        this.web = web;
    }

    public final W getWeb() {
        return web;
    }

    @Override
    public Class<? extends Annotation> getActionClass() {
        return Action.class;
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Action {

        String path();

        HttpMethod method() default GET;

        String selector() default "";

        boolean authenticate() default false;

        boolean cache() default true;
    }

    protected enum HttpMethod {
        GET,
        HEAD,
        POST,
        PUT,
        DELETE,
        CONNECT,
        OPTIONS,
        TRACE,
        PATCH,
    }
}