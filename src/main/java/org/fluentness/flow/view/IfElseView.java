package org.fluentness.flow.view;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

public class IfElseView<T> extends View {

    private String parameter;
    private Function<T, View> function;

    IfElseView(String condition, Function<T, View> function) {
        this.parameter = parameter;
        this.function = function;
    }

    @Override
    public String render() {
        Collection<T> collection = (Collection<T>) View.retrieveParameterForCurrentThread(parameter);
        return collection.stream().map(function).map(View::render).collect(Collectors.joining());
    }
}
