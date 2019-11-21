package org.fluentness.service.serving;

import org.fluentness.service.logging.LoggingService;
import org.fluentness.service.routing.RoutingService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

public class DispatcherServlet extends HttpServlet {

    private LoggingService loggingService;
    private RoutingService routingService;

    public DispatcherServlet(LoggingService loggingService, RoutingService routingService) {
        this.loggingService = loggingService;
        this.routingService = routingService;
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Locale.setDefault(request.getLocale());
            if (request.getPathInfo().startsWith("/resources")) {
                handleStaticResource(request, response);
            } else if (routingService.getRoutingMap().containsKey(request.getPathInfo())) {
                routingService.getRoutingMap().get(request.getPathInfo()).handle(request, response);
            } else {
                response.setStatus(404);
            }
            if (response.getStatus() < 200 || response.getStatus() >= 300) {
                if (routingService.getRoutingMap().containsKey("/"+response.getStatus())) {
                    routingService.getRoutingMap().get("/"+response.getStatus()).handle(request,response);
                }
            }
        } catch (Exception e) {
            loggingService.error(e);
            response.setStatus(500);
        }
    }

    private void handleStaticResource(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String resourcePath = request.getPathInfo().substring(request.getPathInfo().indexOf("/resources") + 11);
        if (resourcePath.startsWith("js") || resourcePath.startsWith("css")) {
            InputStream in = getClass().getClassLoader().getResourceAsStream(resourcePath);
            if (in == null) {
                response.setStatus(404);
                return;
            }
            OutputStream out = response.getOutputStream();
            byte[] buffer = new byte[100];
            int numBytesRead;
            while ((numBytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, numBytesRead);
            }
        }
    }
}
