package org.fluentness.view;

import org.fluentness.Fluentness;
import org.fluentness.base.generics.Component;
import org.fluentness.base.generics.Register;
import org.fluentness.base.lambdas.KeyValuePair;
import org.fluentness.configuration.Configuration;
import org.fluentness.localization.Localization;
import org.fluentness.localization.LocalizationFunctions;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.fluentness.base.constants.SettingKeys.VIEW_CACHE;


public abstract class View implements Component, Serializable, LocalizationFunctions, Register<String, Object> {

    public static final String LOCALIZATION_PLACEHOLDER = "{{%s}}";

    abstract String render();

    public String toString() {

        // retrieving cache
        if (Configuration.getBoolean(VIEW_CACHE) && ViewCache.INSTANCE.isCacheable(this)) {
            return ViewCache.INSTANCE.retrieve(this);
        }

        // initialization
        String rendered;
        ViewProducer viewProvider = Fluentness.INSTANCE.views;
        String viewName = viewProvider.getKeyForValue(this);

        // templating
        if (viewProvider.isAnnotationPresent(viewName, ViewProducer.Template.class)) {
            View template = viewProvider.get(
                ((ViewProducer.Template) viewProvider.getAnnotation(viewName, ViewProducer.Template.class)).value()
            );
            rendered = template.render().replace(ViewProducer.TEMPLATE_PLACEHOLDER, render());
        } else {
            rendered = render();
        }

        // localization
        Map<String, Localization> all = Fluentness.INSTANCE.localizations.getAll();
        Localization localization = Fluentness.INSTANCE.localizations.get(getLocale().toString());
        if (localization == null) {
            localization = Fluentness.INSTANCE.localizations.get(getLocale().getLanguage());
        }
        if (localization == null) {
            localization = new Localization();
        }
        localization.get("cancel");

        Matcher matcher = Pattern.compile("\\{\\{([A-Za-z1-9_]+)}}").matcher(rendered);
        while (matcher.find()) {
            rendered = rendered.replace(matcher.group(0), localization.get(matcher.group(1)));
        }

        // storing cache
        if (Configuration.getBoolean(VIEW_CACHE)) {
            ViewCache.INSTANCE.store(this,rendered);
        }

        return rendered;
    }

    public View with(KeyValuePair<Object>... parameters) {
        Arrays.stream(parameters).forEach(objectNamedValue -> put(objectNamedValue.key(), objectNamedValue.value()));
        return this;
    }

}
