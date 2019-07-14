package org.fluentness.base.logging;

import org.fluentness.base.constants.AnsiColors;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

abstract class BaseFormatter extends Formatter implements AnsiColors {

    void appendLogRecordTitle(StringBuilder builder, LogRecord logRecord) {
        builder.append("[");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        builder.append(df.format(new Date(logRecord.getMillis())));
        builder.append("] [");
        builder.append(logRecord.getLevel().getName());
        for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
            if (!stackTraceElement.getClassName().startsWith("java") &&
                !stackTraceElement.getClassName().startsWith(BaseFormatter.class.getPackage().getName())) {
                builder.append("] [");
                builder.append(stackTraceElement.getClassName().replaceAll(".*\\.",""));
                break;
            }
        }
        builder.append("]");
    }

}
