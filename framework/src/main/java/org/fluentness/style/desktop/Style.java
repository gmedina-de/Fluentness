package org.fluentness.style.desktop;

import java.awt.*;

public class Style<T extends Container> {
    private final Class<T> swingClass;
    private final DesktopStyleLambda<T> styleLambda;

    public Style(Class<T> swingClass, DesktopStyleLambda<T> styleLambda) {
        this.swingClass = swingClass;
        this.styleLambda = styleLambda;
    }

    public Class<T> getSwingClass() {
        return swingClass;
    }

    public DesktopStyleLambda<T> getStyleLambda() {
        return styleLambda;
    }
}