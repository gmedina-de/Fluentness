package org.fluentness.flow.form;

import org.fluentness.Fluentness;
import org.fluentness.base.generics.Consumer;

public interface FormConsumer<T extends FormProvider> extends Consumer {

    default T forms() {
        return (T) Fluentness.flow.getProvider(FormProvider.class);
    }
}
