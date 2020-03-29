package org.fluentness.controller.desktop.template.swing;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Swing<V extends Container> implements CharSequence {

    private static final Map<String, Swing> ID_MAP = new HashMap<>();
    private static final Map<String, List<Swing>> CLASS_MAP = new HashMap<>();

    public static Swing getById(String id) {
        return ID_MAP.get(id);
    }

    public static List<Swing> getByClass(String clazz) {
        return CLASS_MAP.get(clazz);
    }


    @Override
    public int length() {
        return 0;
    }

    @Override
    public char charAt(int i) {
        return 0;
    }

    @Override
    public CharSequence subSequence(int i, int i1) {
        return null;
    }

    @Override
    public IntStream chars() {
        return null;
    }

    @Override
    public IntStream codePoints() {
        return null;
    }

    private V actualSwing;

    Swing(V actualSwing, CharSequence... swings) {
        this.actualSwing = actualSwing;
        handleSwings(swings);
    }

    private void handleSwings(CharSequence[] swings) {
        for (CharSequence swing : swings) {
            if (swing instanceof Swing) {
                this.actualSwing.add(((Swing) swing).getActualSwing());
            } else if (swing instanceof String) {
                handleString((String) swing);
            }
        }
    }

    private void handleString(String string) {
        if (string.startsWith(SwingAttribute.PREFIX)) {
            handleAttribute(string);
        } else {
            handleNormalText(string);
        }
    }

    private void handleAttribute(String string) {
        SwingAttribute key = SwingAttribute.valueOf(string.split("=")[0].replace(SwingAttribute.PREFIX, ""));
        String value = string.split("=")[1];

        switch (key) {
            case ID:
                ID_MAP.put(value, this);
                break;
            case CLASS:
                for (String clazz : value.split(" ")) {
                    List<Swing> list = CLASS_MAP.containsKey(clazz) ? CLASS_MAP.get(clazz) : new LinkedList<>();
                    list.add(this);
                    CLASS_MAP.put(clazz, list);
                }
                break;
        }
    }

    private void handleNormalText(String string) {
        if (JLabel.class.equals(actualSwing.getClass())) {
            ((JLabel) actualSwing).setText(string);
        }
    }

    public V getActualSwing() {
        return actualSwing;
    }


    // ==== instead of layout()
//    public Self borderLayout(String... arrangements) {
//        view.setLayout(new BorderLayout());
//        Component[] components = view.getComponents();
//        view.removeAll();
//        for (int i = 0; i < components.length && i < arrangements.length; i++) {
//            view.add(components[i], arrangements[i]);
//        }
//        return (Self) this;
//    }
//
//    public Self boxLayout(int align) {
//        view.setLayout(new BoxLayout(view, align));
//        Component[] components = view.getComponents();
//        view.removeAll();
//        for (Component component : components) {
//            view.add(component, Component.CENTER_ALIGNMENT);
//        }
//        return (Self) this;
//    }
//
//    public Self flowLayout() {
//        view.setLayout(new FlowLayout());
//        return (Self) this;
//    }

}