package org.fluentness.controller.web;

import org.fluentness.controller.web.template.WebTemplate;
import org.fluentness.controller.web.template.html.style.WebStyle;

public abstract class AbstractWebView<C extends AbstractWebController> {

    protected C controller;

    final void setController(C controller) {
        this.controller = controller;
    }

    public abstract WebStyle getStyle();

    public abstract WebTemplate getTemplate(CharSequence actionResult);
}