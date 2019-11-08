package org.fluentness.controller.desktop.swing.component.button.menu;

import javax.swing.*;

public class RadioButtonMenuItemView extends AbstractMenuItemView<RadioButtonMenuItemView, JRadioButtonMenuItem> {

    private JRadioButtonMenuItem jRadioButtonMenuItem = new JRadioButtonMenuItem();

    @Override
    public JRadioButtonMenuItem getSwingView() {
        return jRadioButtonMenuItem;
    }

}
