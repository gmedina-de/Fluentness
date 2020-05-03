package org.fluentness.controller.html;

public class HtmlEmpty extends Html {

    public HtmlEmpty(String tag, CharSequence[] html) {
        super(tag, html);
    }

    @Override
    public String toString() {
        return "<" + tag + attributes + ">";
    }
}