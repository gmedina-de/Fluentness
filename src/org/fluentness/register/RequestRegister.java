package org.fluentness.register;

import org.fluentness.networking.Request;

import java.util.HashMap;
import java.util.Map;

public final class RequestRegister
{
    private final static Map<Thread, Request> requestMap = new HashMap<>();

    public static void putCurrent(Request request) {
        requestMap.put(Thread.currentThread(),request);
    }

    public static Request getCurrent() {
        return requestMap.get(Thread.currentThread());
    }

    public static void removeCurrent() {
        requestMap.remove(Thread.currentThread());
    }
}
