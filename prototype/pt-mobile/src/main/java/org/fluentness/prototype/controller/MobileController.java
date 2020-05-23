package org.fluentness.prototype.controller;

import org.fluentness.controller.AbstractMobileController;
import org.fluentness.prototype.view.MobileView;
import org.fluentness.service.log.Log;

import static org.fluentness.prototype.service.Localization._login;

public class MobileController extends AbstractMobileController<MobileView> {

    public MobileController(MobileView view, Log log) {
        super(view);
        onClick(view.button1, this::changeText);
    }

    private void changeText() {
        view.button1.setText(_login);
    }
}
