package org.fluentness.base.networking;


import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private int statusCode;
    private Map<String, String> headers = new HashMap<>();
    private String body = "";

    public HttpResponse(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public HttpResponse withStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public HttpResponse withHeader(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

    public String getBody() {
        return body;
    }

    public HttpResponse withBody(String body) {
        this.body = body;
        return this;
    }


}