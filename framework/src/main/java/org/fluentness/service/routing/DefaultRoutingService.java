package org.fluentness.service.routing;

import org.fluentness.controller.web.AbstractWebController;
import org.fluentness.controller.web.WebAction;
import org.fluentness.controller.web.WebView;
import org.fluentness.service.authentication.AuthenticationService;
import org.fluentness.service.caching.CachingService;
import org.fluentness.service.configuration.ConfigurationService;
import org.fluentness.service.injection.InjectionService;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fluentness.service.configuration.ConfigurationService.router_encoding;

public class DefaultRoutingService implements RoutingService {

    private final InjectionService injectionService;
    private final ConfigurationService configurationService;
    private final AuthenticationService authenticationService;
    private final CachingService cachingService;

    public DefaultRoutingService(InjectionService injectionService,
                                 ConfigurationService configurationService,
                                 AuthenticationService authenticationService,
                                 CachingService cachingService) {
        this.injectionService = injectionService;
        this.configurationService = configurationService;
        this.authenticationService = authenticationService;
        this.cachingService = cachingService;
    }

    private Map<String, HttpHandler> routingMap;

    @Override
    public Map<String, HttpHandler> getRoutingMap() {
        if (routingMap != null) {
            return routingMap;
        }
        routingMap = new HashMap<>();
        List<AbstractWebController> webControllers = injectionService.getInstances(AbstractWebController.class);
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
            if (!request.getMethod().equals(action.getHttpMethod().toString())) {
                response.setStatus(HttpStatusCode.METHOD_NOT_ALLOWED.toInt());
            } else if (!action.isAuthentication() || authenticationService.authenticate(request, response)) {

                response.setCharacterEncoding(configurationService.getOrDefault(router_encoding, "UTF-8"));

                Object[] parameters = (action.getMethod().getParameters().length > 0 &&
                    action.getMethod().getParameters()[0].getType().equals(AbstractWebController.Request.class)) ?
                    new Object[]{new AbstractWebController.Request(request)} :
                    null;

                if (method.getReturnType().equals(WebView.class)) {
                    if (action.isCache()) {
                        response.getWriter().write(
                            cachingService.cache(request, () -> (WebView) method.invoke(controller, parameters))
                        );
                    } else {
                        response.getWriter().write(
                            ((WebView) method.invoke(controller, parameters)).render()
                        );
                    }
                } else {
                    Object returnValue = method.invoke(controller, parameters);
                    if (returnValue instanceof String) {
                        response.getWriter().write((String) returnValue);
                    } else if (returnValue instanceof Integer) {
                        response.setStatus((Integer) returnValue);
                    } else if (returnValue instanceof HttpStatusCode) {
                        response.setStatus(((HttpStatusCode) returnValue).toInt());
                    } else if (returnValue instanceof AbstractWebController.Response) {
                        ((AbstractWebController.Response) returnValue).response(response);
                    }
                }
            }
        };
    }


}