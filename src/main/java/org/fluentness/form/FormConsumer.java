package org.fluentness.form;

import org.fluentness.Fluentness;
import org.fluentness.base.onion.Consumer;

public interface FormConsumer<T extends FormProducer> extends Consumer {

    default T forms() {
        return (T) Fluentness.INSTANCE.forms;
    }
}
