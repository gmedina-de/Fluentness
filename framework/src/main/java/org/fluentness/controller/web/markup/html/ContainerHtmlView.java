package org.fluentness.controller.web.markup.html;

import org.fluentness.controller.web.markup.ContainerMarkupView;
import org.fluentness.controller.web.markup.MarkupView;

public class ContainerHtmlView extends ContainerMarkupView implements HtmlView {

    public ContainerHtmlView(String tag, MarkupView... inner) {
        super(tag, inner);
    }

}
