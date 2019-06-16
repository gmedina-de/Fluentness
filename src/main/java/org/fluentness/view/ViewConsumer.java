package org.fluentness.view;

import org.fluentness.Fluentness;
import org.fluentness.common.generics.Consumer;

public interface ViewConsumer<T extends ViewProvider> extends Consumer {

    default T views() {
        return (T) Fluentness.INSTANCE.views;
    }

}
