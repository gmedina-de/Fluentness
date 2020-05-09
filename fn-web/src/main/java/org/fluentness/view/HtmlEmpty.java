package org.fluentness.view;

public class HtmlEmpty extends Html {

    public HtmlEmpty(String tag, CharSequence[] html) {
        super(tag, html);
    }

    @Override
    public String toString() {
        return "<" + tag + attributes + ">";
    }
}