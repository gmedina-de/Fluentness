package org.fluentness.service.configuration;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractConfiguration implements Configuration {

    private final Map<Setting, Object> settings = new HashMap<>();

    @Override
    public final <T> boolean has(Setting<T> setting) {
        return settings.containsKey(setting);
    }

    @Override
    public final <T> T get(Setting<T> setting) {
        return settings.containsKey(setting) ? (T) settings.get(setting) : setting.getFallback();
    }

    protected final <T> AbstractConfiguration set(Setting<T> setting, T value) {
        settings.put(setting, value);
        return this;
    }

}