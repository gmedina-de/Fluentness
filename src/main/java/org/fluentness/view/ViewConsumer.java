package org.fluentness.view;

import org.fluentness.Fluentness;
import org.fluentness.base.generics.Consumer;

public interface ViewConsumer<T extends ViewProducer> extends Consumer {

    default T views() {
        return (T) Fluentness.INSTANCE.views;
    }

}
