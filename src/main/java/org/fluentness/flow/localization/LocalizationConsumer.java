package org.fluentness.flow.localization;

import org.fluentness.Fluentness;
import org.fluentness.base.generics.Consumer;

public interface LocalizationConsumer<T extends LocalizationProvider> extends Consumer {

    default T localizations() {
        return (T) Fluentness.INSTANCE.localizations;
    }
}
