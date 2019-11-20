package org.fluentness.controller.desktop;

import org.fluentness.controller.Controller;
import org.fluentness.controller.desktop.swing.SwingViewRegistry;

import java.awt.*;

public abstract class AbstractDesktopController<V extends DesktopViews> implements Controller {

    protected V desktop;

    protected AbstractDesktopController() {
        desktop = initViews();
    }

    protected abstract V initViews();

    protected <SwingView extends Container> SwingView getSwingViewByName(Class<SwingView> viewClass, String name) {
        return SwingViewRegistry.getByName(viewClass, name);
    }

}
