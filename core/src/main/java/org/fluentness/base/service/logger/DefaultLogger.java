package org.fluentness.base.service.logger;

import org.fluentness.Fluentness;
import org.fluentness.base.common.constant.PrivateDirectories;
import org.fluentness.base.common.exception.DefinitionException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;

import static org.fluentness.base.common.environment.BooleanKey.ENABLE_LOG_TO_CONSOLE;
import static org.fluentness.base.common.environment.BooleanKey.ENABLE_LOG_TO_FILE;
import static org.fluentness.base.common.environment.StringKey.LOG_LEVEL;

public class DefaultLogger implements Logger {


    private final java.util.logging.Logger internalLogger;

    public DefaultLogger() throws DefinitionException {

        internalLogger = java.util.logging.Logger.getLogger(String.valueOf(System.currentTimeMillis()));
        internalLogger.setUseParentHandlers(false);

        String logLevel = "ALL";
        if (Fluentness.getBase().getConfig().has(LOG_LEVEL)) {
            logLevel = Fluentness.getBase().getConfig().get(LOG_LEVEL);
        }

        internalLogger.setLevel(Level.parse(logLevel));

        // console logging
        if (Fluentness.getBase().getConfig().has(ENABLE_LOG_TO_CONSOLE) && Fluentness.getBase().getConfig().get(ENABLE_LOG_TO_CONSOLE)) {
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new ConsoleFormatter());
            consoleHandler.setLevel(Level.parse(logLevel));
            internalLogger.addHandler(consoleHandler);
        }

        // file logging
        try {
            if (Fluentness.getBase().getConfig().has(ENABLE_LOG_TO_FILE) && Fluentness.getBase().getConfig().get(ENABLE_LOG_TO_FILE)) {
                new File(PrivateDirectories.LOG).mkdirs();
                String logFilePath = PrivateDirectories.LOG + "/" +
                    new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())) + ".txt";
                File file = new File(logFilePath);
                FileHandler fileHandler;
                if (file.exists()) {
                    fileHandler = new FileHandler(logFilePath, true);
                } else {
                    file.createNewFile();
                    fileHandler = new FileHandler(logFilePath);
                }
                fileHandler.setFormatter(new FileFormatter());
                fileHandler.setLevel(Level.parse(logLevel));
                internalLogger.addHandler(fileHandler);

            }
        } catch (IOException e) {
            throw new DefinitionException(e);
        }
    }

    @Override
    public void fine(String message, Object... parameters) {
        internalLogger.fine(format(message, parameters));
    }

    @Override
    public void info(String message, Object... parameters) {
        internalLogger.info(format(message, parameters));
    }

    @Override
    public void warning(String message, Object... parameters) {
        internalLogger.warning(format(message, parameters));
    }

    @Override
    public void severe(String message, Object... parameters) {
        internalLogger.severe(format(message, parameters));
    }

    @Override
    public void severe(Exception exception) {
        String message;
        if (exception.getMessage() == null) {
            message = exception.getClass().getSimpleName() + ":";
        } else {
            message = exception.getClass().getSimpleName() + " " + exception.getMessage() + ":";
        }
        message = message.concat(stackTraceToString(exception.getStackTrace()));
        severe(message);
    }
}
