package org.fluentness.service.translator;

import org.fluentness.service.Service;


public interface Translator extends Service {

    String translate(String translation, String... sortedLanguages);

}
