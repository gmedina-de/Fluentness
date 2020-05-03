package org.fluentness.controller.web.html;

import org.fluentness.controller.web.WebTemplate;

import java.util.stream.IntStream;

public class HtmlCombination implements WebTemplate {

    private final CharSequence html1;
    private final CharSequence html2;

    public HtmlCombination(CharSequence html1, CharSequence html2) {
        this.html1 = html1;
        this.html2 = html2;
    }

    @Override
    public String toString() {
        return html1.toString() + html2.toString();
    }
}
