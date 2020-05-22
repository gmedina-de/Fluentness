package org.fluentness.view.html;

import org.fluentness.controller.Controller;

public class HtmlAction extends HtmlContainer {

    public HtmlAction(Class<? extends Controller> controller, String actionName, CharSequence[] html) {
        super("a", html);
//        Map<String, String> methodPathMap = AbstractWebController.methodPathMap;
//        attributes.append(" href=\"").append(methodPathMap.get(controller.getCanonicalName() + actionName)).append("\"");
    }
}
