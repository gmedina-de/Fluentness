package org.fluentness.controller.desktop.swing.component.button.toggle;

import javax.swing.*;

public class RadioButtonView extends AbstractToggleButtonView<RadioButtonView, JRadioButton> {

    public RadioButtonView(String text) {
        super(new JRadioButton(text));
    }

}
