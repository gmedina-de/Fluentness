package org.fluentness.base.service.logger;

import java.util.logging.LogRecord;

public class ConsoleAppender extends org.apache.log4j.ConsoleAppender {

//    @Override
    public String format(LogRecord logRecord) {
//        StringBuilder builder = new StringBuilder();
//        if (logRecord.getLevel().intValue() == Level.FINE.intValue()) {
//            builder.append(ANSI_GREEN);
//        } else if (logRecord.getLevel().intValue() == Level.INFO.intValue()) {
//            builder.append(ANSI_BLUE);
//        } else if (logRecord.getLevel().intValue() == Level.WARNING.intValue()) {
//            builder.append(ANSI_YELLOW);
//        } else if (logRecord.getLevel().intValue() == SEVERE.intValue()) {
//            builder.append(ANSI_RED);
//        }
//        appendLogRecordTitle(builder, logRecord);
//        builder.append(ANSI_RESET);
//        builder.append(ANSI_RESET);
//        builder.append(" ");
//        builder.append(logRecord.getMessage());
//        builder.append("\n");
//        return builder.toString();
        return null;
    }
}