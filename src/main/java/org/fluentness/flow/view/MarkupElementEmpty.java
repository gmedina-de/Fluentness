package org.fluentness.flow.view;

import org.fluentness.base.lambdas.KeyValuePair;

public class MarkupElementEmpty extends MarkupElement {

    protected MarkupElementEmpty(String tag, KeyValuePair<String>... attributes) {
        super(false);
        this.tag = tag;
        this.attributes = new MarkupAttributes(attributes);
    }

}
