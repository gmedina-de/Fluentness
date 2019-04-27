package org.fluentness.logging;

import org.fluentness.command.Console;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    public static void info(Class calling, String message, Object... parameters) {
        System.out.println(Console.ANSI_PURPLE + getCurrentTime() +
                Console.ANSI_BLUE + " [INFO] " + calling.getName() +
                Console.ANSI_WHITE + String.format(message, parameters));
    }

    public static void debug(Class calling, String message, Object... parameters) {
        System.out.println(Console.ANSI_PURPLE + getCurrentTime() +
                Console.ANSI_GREEN + " [DEBUG] " + calling.getName() +
                Console.ANSI_WHITE + String.format(message, parameters));
    }

    public static void warning(Class calling, String message, Object... parameters) {
        System.out.println(Console.ANSI_PURPLE + getCurrentTime() +
                Console.ANSI_YELLOW + " [WARNING] " + calling.getName() +
                Console.ANSI_WHITE + String.format(message, parameters));
    }

    public static void error(Class calling, String message, Object... parameters) {
        System.err.println(Console.ANSI_PURPLE + getCurrentTime() +
                Console.ANSI_RED + " [ERROR] " + calling.getName() +
                Console.ANSI_WHITE + String.format(message, parameters));
    }

    public static void error(Class calling, Exception exception) {
        String message;
        if (exception.getMessage() == null) {
            message = "Exception " + exception.getClass().getName();
        } else {
            message = exception.getMessage();
        }
        message = message.concat(stackTraceToString(exception.getStackTrace()));
        error(calling, message);
    }

    public static void error(Class calling, Exception exception, String message, Object... parameters) {
        if (message == null) {
            if (exception.getMessage() == null) {
                message = "Exception " + exception.getClass().getName();
            } else {
                message = exception.getMessage();
            }
        }
        message = message.concat(stackTraceToString(exception.getStackTrace()));
        error(calling, message, parameters);
    }

    private static String stackTraceToString(StackTraceElement[] stackTraceElements) {
        StringBuilder res = new StringBuilder();
        res.append("\n").append("Stacktrace:");
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            res.append("\n    ").append(stackTraceElement.toString());
        }
        return res.toString();
    }

    private static String getCurrentTime() {
        return DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
    }
}