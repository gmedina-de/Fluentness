package org.fluentness.service.server;

import org.fluentness.service.logger.Logger;
import org.fluentness.service.router.Router;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

public class DispatcherServlet extends HttpServlet {

    private Logger logger;
    private Router router;

    public DispatcherServlet(Logger logger, Router router) {
        this.logger = logger;
        this.router = router;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug(req.getMethod() + " " + req.getPathInfo());
        Locale.setDefault(req.getLocale());
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getPathInfo().startsWith("/resources")) {
            handleStaticResource(req, resp);
        } else if (router.getRoutingMap().containsKey(req.getPathInfo())) {
            try {
                router.getRoutingMap().get(req.getPathInfo()).handle(req, resp);
            } catch (InvocationTargetException | IllegalAccessException e) {
                logger.error(e);
                resp.setStatus(500);
            }
        } else {
            resp.setStatus(404);
        }
        logger.debug(resp.getStatus()+"");
    }

    //todo support other methods


    private void handleStaticResource(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String resourcePath = req.getPathInfo().substring(req.getPathInfo().indexOf("/resources")+11);
        if (resourcePath.startsWith("js") || resourcePath.startsWith("css")) {
            InputStream in = getClass().getClassLoader().getResourceAsStream(resourcePath);
            if (in == null) {
                resp.setStatus(404);
                return;
            }
            try(OutputStream out = resp.getOutputStream()) {
                byte[] buffer = new byte[100];
                int numBytesRead;
                while ((numBytesRead = in.read(buffer)) > 0) {
                    out.write(buffer, 0, numBytesRead);
                }
            } catch (Exception e) {
                logger.error(e);
            }
        }
    }
}