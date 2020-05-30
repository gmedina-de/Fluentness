package org.fluentness.view.component;

import org.fluentness.controller.view.JavaScriptCommand;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class HtmlContainer extends HtmlComponent {

    private final StringBuilder inner = new StringBuilder();
    private final List<HtmlComponent> innerHtml = new LinkedList<>();

    public HtmlContainer(String tag) {
        super(tag);
    }

    public List<HtmlComponent> getInnerHtml() {
        return innerHtml;
    }

    public HtmlContainer withInner(HtmlComponent inner) {
        innerHtml.add(inner);
        inner.setParent(this);
        return this;
    }

    public HtmlContainer withInner(HtmlComponent[] inner) {
        for (HtmlComponent htmlComponent : inner) {
            withInner(htmlComponent);
        }
        return this;
    }

    public HtmlContainer withInner(CharSequence inner) {
        this.inner.append(inner);
        return this;
    }

    @Override
    public HtmlContainer withAttribute(String key, Object value) {
        return (HtmlContainer) super.withAttribute(key, value);
    }

    @Override
    public String toString() {
        return super.toString() +
            innerHtml.stream().map(HtmlComponent::toString).collect(Collectors.joining()) + inner + "</" + tag + ">";
    }

    public void append(HtmlComponent child) {
        JavaScriptCommand.appendChild(getXpath(), child);
    }
}
