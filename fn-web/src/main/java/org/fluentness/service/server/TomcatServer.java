package org.fluentness.service.server;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.fluentness.service.configuration.Configuration;
import org.fluentness.service.dispatcher.Dispatcher;
import org.fluentness.service.log.Log;
import org.fluentness.service.log.LogLevel;

import java.io.File;
import java.util.Arrays;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class TomcatServer extends Handler implements Server {

    private final Log log;

    private final Tomcat tomcat;

    public TomcatServer(Configuration configuration, Log log, Dispatcher[] dispatchers) {
        this.log = log;

        tomcat = new Tomcat();
        tomcat.setPort(configuration.get(PORT));
        tomcat.getHost().setAppBase(".");

        Context dynamicContext = tomcat.addContext(configuration.get(CONTEXT), new File(".").getAbsolutePath());
        for (Dispatcher dispatcher : dispatchers) {
            Tomcat.addServlet(dynamicContext, dispatcher.getClass().getName(), dispatcher);
            dynamicContext.addServletMappingDecoded(dispatcher.getUrlPattern(), dispatcher.getClass().getName());
        }

        java.util.logging.Logger tomcatLogger = java.util.logging.Logger.getLogger("");
        Arrays.stream(tomcatLogger.getHandlers()).forEach(tomcatLogger::removeHandler);
        tomcatLogger.addHandler(this);
    }

    @Override
    public void start() throws LifecycleException {
        tomcat.start();
        log.info(
            "Tomcat Server is listening, visit http://%s:%s/",
            tomcat.getHost().getName(),
            tomcat.getConnector().getPort()
        );
        new Thread(() -> tomcat.getServer().await()).start();
    }

    @Override
    public void stop() {
        if (tomcat != null) {
            try {
                tomcat.stop();
            } catch (LifecycleException e) {
                log.error(e);
            }
        } else {
            log.warning("Server was stopped without being started");
        }
    }

    @Override
    public void publish(LogRecord record) {
        log.log(LogLevel.fromJulLevel(record.getLevel()), record.getMessage());
    }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws SecurityException {

    }
}
