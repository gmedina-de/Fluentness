package org.fluentness.controller.web.html;

import org.fluentness.controller.web.WebAction;
import org.fluentness.controller.web.WebView;
import org.fluentness.repository.Model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.fluentness.controller.web.html.HtmlFactory.*;

public class HtmlForm<T> extends HtmlContainer {


    public HtmlForm(Model model, WebAction action) {
        super("form", renderForm(model, action));
    }

    private static WebView[] renderForm(Model object, WebAction action) {
        List<WebView> result = new LinkedList<>();
        result.add(_method(action.getHttpMethod()));
        result.add(_action(action.getPath()));
        Arrays.stream(Model.getGetters(object.getClass()))
            .map(getter -> input(getHtmlAttributesFor(object, getter)))
            .forEach(result::add);
        result.add(input(_class("button"), _type("submit"), _value("Submit")));
        return result.toArray(new WebView[0]);
    }

    private static HtmlAttribute[] getHtmlAttributesFor(Object object, Method getter) {
        List<HtmlAttribute> result = new LinkedList<>();
        try {
            getter.setAccessible(true);
            result.add(_name(getter.getName()));
            result.add(_type(getter.getAnnotation(Model.Type.class).value().name()));
            result.add(_value(getter.invoke(object) == null ? "" : String.valueOf(getter.invoke(object))));
            getter.setAccessible(false);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return result.toArray(new HtmlAttribute[0]);
    }

}
