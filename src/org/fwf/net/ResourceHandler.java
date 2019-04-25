package org.fwf.net;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.fwf.log.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ResourceHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) {
        Logger.d(exchange.getRequestMethod() + " " + exchange.getRequestURI());

        String path = exchange.getRequestURI().getPath();
        if (path.equals("/favicon.ico")) {
            path = "/res/icon/favicon.ico";
        }
        try {
            path = path.substring(1);
            path = path.replaceAll("//", "/");

            boolean exists = Files.exists(Paths.get(path));
            if (exists) {
                HttpResponse response = new HttpResponse(HttpStatusCode.Ok);

                response.setBody(new String(Files.readAllBytes(Paths.get(path))));

                if (path.endsWith(".js")) {
                    response.setHeader("Content-Type", "text/javascript");
                } else if (path.endsWith(".html")) {
                    response.setHeader("Content-Type", "text/html");
                } else if (path.endsWith(".css")) {
                    response.setHeader("Content-Type", "text/css");
                } else if (path.endsWith(".json")) {
                    response.setHeader("Content-Type", "application/json");
                } else if (path.endsWith(".svg")) {
                    response.setHeader("Content-Type", "image/svg+xml");
                }

                Server.respond(exchange, response);
            } else {
                Logger.w("File " + path + " doesn't exists");
                Server.respond(exchange, new HttpResponse(HttpStatusCode.NotFound));
            }
        } catch (IOException e) {
            Logger.e(e);
            Server.respond(exchange, new HttpResponse(HttpStatusCode.InternalServerError));
        }
    }
}
