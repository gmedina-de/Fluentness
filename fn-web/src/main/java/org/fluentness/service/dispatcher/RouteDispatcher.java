package org.fluentness.service.dispatcher;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.fluentness.controller.JavaScriptCommand;
import org.fluentness.controller.WebController;
import org.fluentness.service.authenticator.Authenticator;
import org.fluentness.service.configuration.Configuration;
import org.fluentness.service.log.Log;
import org.fluentness.view.WebView;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class RouteDispatcher extends BaseDispatcher {

    private final Configuration configuration;

    protected final Map<String, Method> routes = new HashMap<>();
    protected final Map<String, WebController> controllers = new HashMap<>();

    public RouteDispatcher(Authenticator authenticator, Log log, Configuration configuration) {
        super(authenticator, log);
        this.configuration = configuration;
    }

    @Override
    public String getUrlPattern() {
        return "/*";
    }

    public final void addRoute(String method, String path, Method action, WebController controller) {
        path = method + " " + getUrlPattern().replace("/*", "") + path;
        routes.put(path, action);
        controllers.put(path, controller);
    }

    @Override
    protected void dispatch(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, IOException {
        String path = request.getMethod() + " " + request.getRequestURI();

        if (routes.containsKey(path)) {
            Method action = routes.get(path);
            WebController controller = controllers.get(path);

            action.setAccessible(true);
            Object[] args = prepareArgs(action, request, response);
            Object returned = action.getParameterCount() > 0 ?
                action.invoke(controller, args) :
                action.invoke(controller);
            if (returned instanceof CharSequence) {
                respond(response, returned.toString());
            } else if (returned instanceof WebView) {
                respond(response, ((WebView) returned).getRenderedHtml());
            } else if (returned instanceof Integer) {
                response.setStatus((Integer) returned);
            } else {
                respond(response, JavaScriptCommand.getCommands());
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private static Object[] prepareArgs(Method action, HttpServletRequest request, HttpServletResponse response) {
        Parameter[] parameters = action.getParameters();
        Object[] result = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Class<?> type = parameter.getType();
            String name = parameter.getName();

            if (HttpServletRequest.class.isAssignableFrom(type)) {
                result[i] = request;
            } else if (HttpServletResponse.class.isAssignableFrom(type)) {
                result[i] = response;
            } else if (parameter.isAnnotationPresent(WebController.RequestParameter.class)) {
                WebController.RequestParameter requestParameter = parameter.getAnnotation(WebController.RequestParameter.class);
                result[i] = request.getParameter(!requestParameter.name().isEmpty() ? requestParameter.name() : name);
            } else if (parameter.isAnnotationPresent(WebController.SessionAttribute.class)) {
                WebController.SessionAttribute sessionAttribute = parameter.getAnnotation(WebController.SessionAttribute.class);
                result[i] = request.getSession().getAttribute(!sessionAttribute.name().isEmpty() ? sessionAttribute.name() : name);
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
