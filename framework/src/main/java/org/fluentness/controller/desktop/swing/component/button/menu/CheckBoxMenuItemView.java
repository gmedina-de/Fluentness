package org.fluentness.controller.desktop.swing.component.button.menu;

import javax.swing.*;

public class CheckBoxMenuItemView implements AbstractMenuItemView<CheckBoxMenuItemView, JCheckBoxMenuItem> {

    JCheckBoxMenuItem jCheckBoxMenuItem = new JCheckBoxMenuItem();

    @Override
    public JCheckBoxMenuItem getView() {
        return jCheckBoxMenuItem;
    }

}
