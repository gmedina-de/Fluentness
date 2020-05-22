package org.fluentness.prototype;

import org.fluentness.AbstractWeb;
import org.fluentness.Fluentness;
import org.fluentness.FluentnessException;
import org.fluentness.prototype.controller.WebController;
import org.fluentness.service.server.Server;

public class Web extends AbstractWeb {

    public Web(Server server, WebController controller) {
        super(server, controller);
    }

    public static void main(String[] args) throws FluentnessException {
        Fluentness.launch(Web.class, args);
    }
}
