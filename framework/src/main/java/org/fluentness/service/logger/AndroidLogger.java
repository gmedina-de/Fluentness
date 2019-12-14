package org.fluentness.service.logger;

import android.util.Log;
import org.fluentness.service.configurator.Configurator;

public class AndroidLogger implements Logger {

    private final int androidPriority;

    public AndroidLogger(Configurator configurator) {
        this.androidPriority = configurator.getOrDefault(LEVEL, LogLevel.DEBUG).toAndroidPriority();
    }

    @Override
    public void debug(String message, Object... parameters) {
        log(LogLevel.DEBUG, message, parameters);
    }

    @Override
    public void info(String message, Object... parameters) {
        log(LogLevel.INFO, message, parameters);
    }

    @Override
    public void warning(String message, Object... parameters) {
        log(LogLevel.WARNING, message, parameters);
    }

    @Override
    public void error(String message, Object... parameters) {
        log(LogLevel.ERROR, message, parameters);
    }

    @Override
    public void log(LogLevel logLevel, String message, Object... parameters) {
        if (logLevel.toAndroidPriority() >= androidPriority) {
            Log.println(logLevel.toAndroidPriority(), Logger.getLoggerCaller(), String.format(message, parameters));
        }
    }

}