package org.fluentness.networking;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.fluentness.logging.Log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HttpResourceHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) {
        Log.info(this.getClass(), exchange.getRequestMethod() + " " + exchange.getRequestURI());

        String path = exchange.getRequestURI().getPath();
        if (path.equals("/favicon.ico")) {
            path = "/res/icon/favicon.ico";
        }
        try {
            path = path.substring(1).replaceAll("//", "/");

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

                HttpServer.serve(exchange, response);
            } else {
                Log.warning(this.getClass(), "File " + path + " doesn't exists");
                HttpServer.serve(exchange, new HttpResponse(HttpStatusCode.NotFound));
            }
        } catch (IOException e) {
            Log.severe(this.getClass(), e);
            HttpServer.serve(exchange, new HttpResponse(HttpStatusCode.InternalServerError));
        }
    }
}
