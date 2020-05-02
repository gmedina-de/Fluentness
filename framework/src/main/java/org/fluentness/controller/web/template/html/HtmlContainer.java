package org.fluentness.controller.web.template.html;

public class HtmlContainer extends Html {

    public HtmlContainer(String tag, CharSequence[] html) {
        super(tag, html);
    }

    @Override
    public String toString() {
        return "<" + tag + attributes + ">" + inner + "</" + tag + ">";
    }
}
