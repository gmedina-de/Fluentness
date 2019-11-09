package org.fluentness.service.configuration;

import org.fluentness.Application;
import org.fluentness.Fluentness;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractConfigurationService implements ConfigurationService {

    private final Map<Key, Object> settings = new HashMap<>();

    public AbstractConfigurationService() {
        configure(Fluentness.getApplication().getEnvironment());
    }

    @Override
    public boolean has(Key key) {
        return settings.containsKey(key);
    }

    @Override
    public <T> T get(Key<T> key) {
        return (T) settings.get(key);
    }

    @Override
    public <T> T getOrDefault(Key<T> key, T fallback) {
        return (T) settings.getOrDefault(key, fallback);
    }

    protected <T> void set(Key<T> key, T value) {
        settings.put(key, value);
    }

    protected abstract void configure(Application.Environment environment);
}