package org.fluentness.service.server;

import org.fluentness.service.configuration.Configuration;
import org.fluentness.service.logger.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.net.HttpURLConnection;
import java.net.URL;

public class TomcatServerTest {

    private Configuration configuration;
    private Logger logger;
    private DispatcherServlet dispatcherServlet;

    private Server server;

    @Before
    public void setUp() {
        configuration = Mockito.mock(Configuration.class);
        logger = Mockito.mock(Logger.class);
        dispatcherServlet = Mockito.mock(DispatcherServlet.class);

        server = new TomcatServer(configuration, logger, dispatcherServlet);
    }

    @Test(timeout = 3000)
    public void start_noConfigurationIsSet_serverCanBeStartedAndStopped() throws Exception {
        server.start();

        Mockito.verify(logger, Mockito.times(1)).info(Mockito.any(), Mockito.any(), Mockito.any());
    }

    @Test(timeout = 3000)
    public void start_rootPathIsRequestedToLocalhost8000_statusCode200IsResponded() throws Exception {
        server.start();
        HttpURLConnection connection = (HttpURLConnection) new URL("http://127.0.0.1:8000").openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        Assert.assertEquals(200, connection.getResponseCode());
    }

    @Test(timeout = 3000, expected = NumberFormatException.class)
    public void start_invalidPortIsSet_lifecycleExceptionIsThrown() throws Exception {
        Mockito.when(configuration.has("server_port")).thenReturn(true);
        Mockito.when(configuration.get("server_port")).thenReturn("notAnInteger");

        server.start();
    }

    @Test(timeout = 3000)
    public void stop_serverWasNotStarted_infoIsLogged() {
        server.stop();

        Mockito.verify(logger, Mockito.times(1)).info(Mockito.any());
    }

    @After
    public void tearDown() {
        server.stop();
    }
}