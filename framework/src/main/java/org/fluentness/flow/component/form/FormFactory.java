package org.fluentness.flow.component.form;

import org.fluentness.base.common.constant.HttpMethods;
import org.fluentness.base.common.lambda.KeyValuePair;
import org.fluentness.flow.component.localization.Localizator;
import org.fluentness.flow.component.view.HtmlFunctions;
import org.fluentness.flow.component.view.RawView;
import org.fluentness.flow.component.view.View;

public interface FormFactory extends FieldFactory, Localizator, HttpMethods, HtmlFunctions {
    default Form get(String action, KeyValuePair<Field>... fields) {
        return new Form(GET, action, fields);
    }

    default Form post(String action, KeyValuePair<Field>... fields) {
        return new Form(POST, action, fields);
    }

    default View raw(String string) {
        return new RawView(string);
    }
}