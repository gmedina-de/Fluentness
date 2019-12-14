package org.fluentness.controller.desktop;

import org.fluentness.view.View;
import org.fluentness.style.desktop.DesktopStyle;
import org.fluentness.style.desktop.Style;

import java.util.List;

public abstract class DesktopView implements View {

    private static List<Style> styleMap;

    public static void setGlobalStyle(DesktopStyle desktopStyle) {
        DesktopView.styleMap = desktopStyle.getStyles();
    }

    protected DesktopView(Object view) {
        super();
        // Apply global styles
        if (styleMap != null) {
            styleMap.stream()
                .filter(entry -> entry.getSwingClass().isAssignableFrom(view.getClass()))
                .map(entry -> entry.getStyleLambda())
                .forEach(desktopStyleLambda -> desktopStyleLambda.style(view));
        }
    }

    @Override
    public abstract Boolean render();
}
