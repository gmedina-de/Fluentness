package org.fluentness.controller.web.html;

import org.fluentness.repository.Model;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.fluentness.controller.web.html.HtmlFactory.*;

public class HtmlTable<T> extends HtmlContainer {

    private final List<T> list;
    private final List<Field> fields;
    private AppendColumnView<T> appendColumnView;

    public HtmlTable(List<T> list) {
        super("table");
        this.list = list;
        this.fields = (list == null || list.isEmpty()) ?
            new LinkedList<>() :
            Model.FieldExtractor.INSTANCE.getModelFields(list.get(0));
    }

    public HtmlTable<T> appendColumn(AppendColumnView<T> appendColumnView) {
        this.appendColumnView = appendColumnView;
        return this;
    }

    @Override
    public String render() {
        this.innerViews = Arrays.asList(renderTable());
        return super.render();
    }

    private Html[] renderTable() {
        return new Html[]{
            thead(tr(renderHeader())),
            tbody(renderRows())
        };
    }

    private Html[] renderHeader() {
        return fields.stream().map(field -> th(field::getName)).toArray(Html[]::new);
    }

    private Html[] renderRows() {
        return list.stream().map(object -> tr(renderRow(object))).toArray(Html[]::new);
    }

    private Html[] renderRow(T object) {
        List<Html> collect = fields.stream()
            .map(field ->
                td(() -> {
                    try {
                        field.setAccessible(true);
                        Object fieldValue = field.get(object);
                        field.setAccessible(false);
                        return String.valueOf(fieldValue);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return "";
                })
            )
            .collect(Collectors.toList());
        if (appendColumnView != null) {
            collect.add(appendColumnView.toAppend(object));
        }
        return collect.toArray(new Html[0]);
    }

    @FunctionalInterface
    public interface AppendColumnView<T> {
        Html toAppend(T t);
    }
}