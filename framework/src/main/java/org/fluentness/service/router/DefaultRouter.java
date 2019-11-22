package org.fluentness.service.router;

import org.fluentness.controller.web.AbstractWebController;
import org.fluentness.controller.web.WebAction;
import org.fluentness.controller.web.WebView;
import org.fluentness.service.authenticator.Authenticator;
import org.fluentness.service.cache.Cache;
import org.fluentness.service.configurator.Configurator;
import org.fluentness.service.injector.Injector;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fluentness.service.configurator.Configurator.router_encoding;

public class DefaultRouter implements Router {

    private final Injector injector;
    private final Configurator configurator;
    private final Authenticator authenticator;
    private final Cache cache;

    public DefaultRouter(Injector injector,
                         Configurator configurator,
                         Authenticator authenticator,
                         Cache cache) {
        this.injector = injector;
        this.configurator = configurator;
        this.authenticator = authenticator;
        this.cache = cache;
    }

    private Map<String, HttpHandler> routingMap;

    @Override
    public Map<String, HttpHandler> getRoutingMap() {
        if (routingMap != null) {
            return routingMap;
        }
        routingMap = new HashMap<>();
        List<AbstractWebController> webControllers = injector.getInstances(AbstractWebController.class);
        webControllers.forEach(controller -> {
            List<WebAction> actions = controller.getActions();
            actions.forEach(action -> routingMap.put(action.getPath(), getHttpHandlerForAction(controller, action)));
        });
        return routingMap;
    }

    private HttpHandler getHttpHandlerForAction(AbstractWebController controller, WebAction action) {
        Method method = action.getMethod();
        method.setAccessible(true);
        return (request, response) -> {
            if (!request.getMethod().equals(action.getHttpMethod())) {
                response.setStatus(405);
            } else if (!action.isAuthentication() || authenticator.authenticate(request, response)) {
                response.setCharacterEncoding(configurator.getOrDefault(router_encoding, "UTF-8"));
                // todo cache
                Object returnValue = method.invoke(controller, new AbstractWebController.Request(request));
                if (returnValue instanceof String) {
                    response.getWriter().write((String) returnValue);
                } else if (returnValue instanceof WebView) {
                    response.getWriter().write(((WebView) returnValue).render());
                } else if (returnValue instanceof Integer) {
                    response.setStatus((Integer) returnValue);
                } else if (returnValue instanceof AbstractWebController.Response) {
                    ((AbstractWebController.Response) returnValue).response(response);
                }
            }

        };
    }
}