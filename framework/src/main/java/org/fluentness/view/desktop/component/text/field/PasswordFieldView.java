package org.fluentness.view.desktop.component.text.field;

import javax.swing.*;

public class PasswordFieldView extends AbstractTextFieldView<PasswordFieldView,JPasswordField> {

    public PasswordFieldView() {
        super(new JPasswordField());
    }

    public PasswordFieldView echoChar(char c) {
        view.setEchoChar(c);
        return this;
    }
}