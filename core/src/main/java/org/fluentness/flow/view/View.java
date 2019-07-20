package org.fluentness.flow.view;

import org.fluentness.base.generics.Component;
import org.fluentness.base.lambdas.KeyValuePair;
import org.fluentness.base.networking.HttpRequestRegister;
import org.fluentness.flow.Flow;
import org.fluentness.flow.locale.Locale;
import org.fluentness.flow.locale.LocaleProvider;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class View extends Component {

    private static final Map<Thread, Map<String, Object>> parameters = new HashMap<>();

    boolean hasParameters() {
        return parameters.get(Thread.currentThread()) != null && !parameters.get(Thread.currentThread()).isEmpty();
    }

    protected static Object retrieveParameterForCurrentThread(String parameter) {
        return parameters.get(Thread.currentThread()).get(parameter);
    }

    public View assigning(KeyValuePair<Object>... parameters) {
        if (View.parameters.containsKey(Thread.currentThread())) {
            View.parameters.get(Thread.currentThread()).clear();
        } else {
            View.parameters.put(Thread.currentThread(), new HashMap<>());
        }
        Arrays.stream(parameters).forEach(
            parameter -> View.parameters.get(Thread.currentThread()).put(parameter.getKey(), parameter.getValue())
        );
        return this;
    }

    public String renderWithCache() {
        return ViewCache.instance.cache(this);
    }

    @Override
    public String toString() {
        return localize(render());
    }

    public abstract String render();

    private String localize(String text) {
        Locale localeToApply = Flow.instance.getProvider(LocaleProvider.class)
            .getComponent(HttpRequestRegister.getCurrentLocale().toString());

        Matcher matcher = Pattern.compile("\\{\\{L:([A-Za-z1-9_]+)}}").matcher(text);
        while (matcher.find()) {
            text = text.replace(matcher.group(0), localeToApply.get(matcher.group(1)));
        }
        return text;
    }
}