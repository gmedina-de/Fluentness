package org.fluentness.service.localization;

import java.util.Locale;
import java.util.ResourceBundle;

public class PropertiesLocalizationService implements LocalizationService {

    private Locale currentLocale;
    private XMLResourceBundleControl control;

    public PropertiesLocalizationService() {
        control = new XMLResourceBundleControl();
    }

    @Override
    public String translate(String key, String... parameters) {
        return String.format(ResourceBundle.getBundle("localization", control).getString(key), parameters);
    }

}