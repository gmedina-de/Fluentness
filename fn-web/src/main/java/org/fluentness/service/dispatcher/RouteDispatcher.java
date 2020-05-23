package org.fluentness.service.dispatcher;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.fluentness.controller.WebController;
import org.fluentness.service.authentication.Authentication;
import org.fluentness.service.configuration.Configuration;
import org.fluentness.service.log.Log;
import org.fluentness.view.AbstractWebView;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RouteDispatcher extends AbstractDispatcher {

    private final Configuration configuration;

    protected final Map<String, Method> routes = new HashMap<>();
    protected final Map<Method, WebController> controllers = new HashMap<>();

    public RouteDispatcher(Authentication[] authentications, Log log, Configuration configuration) {
        super(authentications, log);
        this.configuration = configuration;
    }

    @Override
    public String getUrlPattern() {
        return "/*";
    }

    public final void addRoute(String method, String path, Method action, WebController controller) {
        routes.put(method + " " + getUrlPattern().replace("/*", "") + path, action);
        controllers.put(action, controller);
    }

    @Override
    protected void dispatch(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, IOException {
        String path = request.getMethod() + " " + request.getRequestURI();

        if (routes.containsKey(path)) {
            String key = request.getMethod() + " " + request.getRequestURI();
            Method action = routes.get(key);
            WebController controller = controllers.get(action);

            action.setAccessible(true);
            Object[] args = prepareArgs(action, request);
            Object returned = action.getParameterCount() > 0 ?
                action.invoke(controller, args) :
                action.invoke(controller);
            if (returned instanceof CharSequence) {
                respond(response, returned.toString());
            } else if (returned instanceof AbstractWebView) {
                respond(response, ((AbstractWebView)returned).getHtml());
            } else if (returned instanceof Integer) {
                response.setStatus((Integer) returned);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private static Object[] prepareArgs(Method action, HttpServletRequest request) {
        Parameter[] parameters = action.getParameters();
        Object[] result = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Class<?> type = parameter.getType();
            String name = parameter.getName();

            if (HttpServletRequest.class.isAssignableFrom(type)) {
                result[i] = request;
            } else if (type.equals(String.class)) {
                result[i] = request.getParameter(name);
            } else if (int.class.isAssignableFrom(type)) {
                result[i] = Integer.parseInt(request.getParameter(name));
            } else if (float.class.isAssignableFrom(type)) {
                result[i] = Float.parseFloat(request.getParameter(name));
            } else if (boolean.class.isAssignableFrom(type)) {
                result[i] = Boolean.parseBoolean(request.getParameter(name));
            } else if (Date.class.isAssignableFrom(type)) {
                result[i] = Date.parse(request.getParameter(name));
            }
        }
        return result;
    }

    private void respond(HttpServletResponse response, String render) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html");
        response.setCharacterEncoding(configuration.get(RESPONSE_ENCODING));
        response.getWriter().print(render);
    }
}
