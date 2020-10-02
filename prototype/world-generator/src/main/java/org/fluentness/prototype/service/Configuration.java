package org.fluentness.prototype.service;

import org.fluentness.service.configuration.BaseConfiguration;
import org.fluentness.service.display.Display;

public class Configuration extends BaseConfiguration {


    @Override
    protected void configure() {
        set(Display.WIDTH, 1800);
        set(Display.HEIGHT, 900);
        set(Display.FULLSCREEN, false);

    }

}