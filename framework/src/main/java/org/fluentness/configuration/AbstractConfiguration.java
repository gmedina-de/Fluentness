package org.fluentness.configuration;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractConfiguration implements Configuration {

    private final Map<Key, Object> settings = new HashMap<>();

    public AbstractConfiguration() {
        configure();
    }

    @Override
    public <T> boolean has(Key<T> key) {
        return settings.containsKey(key);
    }

    @Override
    public <T> T get(Key<T> key) {
        return settings.containsKey(key) ? (T) settings.get(key) : key.getFallback();
    }

    protected <T> void set(Key<T> key, T value) {
        settings.put(key, value);
    }

    protected abstract void configure();
}