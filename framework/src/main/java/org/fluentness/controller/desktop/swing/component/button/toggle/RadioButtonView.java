package org.fluentness.controller.desktop.swing.component.button.toggle;

import javax.swing.*;

public class RadioButtonView extends AbstractToggleButtonView<RadioButtonView, JRadioButton> {

    JRadioButton jRadioButton = new JRadioButton();

    @Override
    public JRadioButton getView() {
        return jRadioButton;
    }

}