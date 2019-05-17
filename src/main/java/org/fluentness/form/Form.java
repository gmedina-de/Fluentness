package org.fluentness.form;

import org.fluentness.common.NamedValue;
import org.fluentness.localization.Localizable;
import org.fluentness.renderable.HtmlFunctions;
import org.fluentness.renderable.Renderable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public interface Form extends Renderable, HtmlFunctions, FieldFunctions, Localizable {

    String getMethod();

    String getAction();

    Fields getFields();

    Renderable getRenderable();

    default Fields fields(NamedValue<Field>... fields) {
        return new Fields(fields);
    }

    default Field field(String name) {
        return getFields().get(name);
    }

    @Override
    default String render() {
        return getRenderable().render();
    }

    class Fields {

        private Map<String, Field> fields = new HashMap<>();

        private Fields(NamedValue<Field>... fields) {
            Arrays.stream(fields).forEach(field -> {
                Field value = field.value();
                value.setName(field.name());
                this.fields.put(field.name(), value);
            });
        }

        public Field get(String name) {
            return fields.get(name);
        }
    }
}
