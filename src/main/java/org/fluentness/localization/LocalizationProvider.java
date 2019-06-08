package org.fluentness.localization;

import org.fluentness.common.lambdas.NamedValue;
import org.fluentness.common.Provider;

public interface LocalizationProvider extends Provider<Localization> {

    default Localization translations(NamedValue<String>... translations) {
        return new Localization(translations);
    }

}
