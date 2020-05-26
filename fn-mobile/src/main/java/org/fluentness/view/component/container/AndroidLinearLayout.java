package org.fluentness.view.component.container;

import android.content.Context;
import android.view.View;
import org.fluentness.view.component.Component;

public class AndroidLinearLayout extends android.widget.LinearLayout implements LinearLayout {

    public AndroidLinearLayout(Context context, int orientation, Component[] components) {
        super(context);
        setOrientation(orientation);
        for (Component component : components) {
            add(component);
        }
    }

    @Override
    public void add(Component component) {
        addView((View) component);
    }

    @Override
    public void setPadding(int top, int left, int bottom, int right) {
        super.setPadding(left, top, right, bottom);
    }
}