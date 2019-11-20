package org.fluentness.controller.desktop.swing.component.button.toggle;

import javax.swing.*;

public class ToggleButtonView extends AbstractToggleButtonView<ToggleButtonView, JToggleButton> {

    public ToggleButtonView(String text) {
        super(new JToggleButton(text));
    }

}
