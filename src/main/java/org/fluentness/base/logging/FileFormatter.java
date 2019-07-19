package org.fluentness.base.logging;

import java.util.logging.LogRecord;

public class FileFormatter extends BaseFormatter {

    @Override
    public String format(LogRecord logRecord) {
        StringBuilder builder = new StringBuilder();
        appendLogRecordTitle(builder, logRecord);
        builder.append(" ");
        builder.append(logRecord.getMessage());
        builder.append("\n");
        return builder.toString();
    }

}