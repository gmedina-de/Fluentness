package org.fluentness.controller.desktop.swing.component.button.toggle;

import javax.swing.*;

public class CheckBoxView extends AbstractToggleButtonView<CheckBoxView, JCheckBox> {

    JCheckBox jCheckBox = new JCheckBox();

    @Override
    public JCheckBox getView() {
        return jCheckBox;
    }

    public CheckBoxView borderPaintedFlat(boolean b) {
        jCheckBox.setBorderPaintedFlat(b);
        return this;
    }
}