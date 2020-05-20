package com.sample.controller;

import com.sample.view.Web;
import org.fluentness.controller.AbstractWebController;
import org.fluentness.view.html.Html;

import static org.fluentness.view.AbstractWeb.form;
import static org.fluentness.view.AbstractWeb.input;
import static org.fluentness.view.html.HtmlAttribute.*;

public class WebEmailController extends AbstractWebController {

    @BasePath("/email")
    public WebEmailController(Web web) {
        super(web);
    }

    @Action
    Html email() {
        return form(ACTION + "/email/inbox", METHOD + "GET",
            input(NAME + "email", TYPE + "email"),
            input(NAME + "password", TYPE + "password"),
            input(TYPE + "submit")
        );
    }

    @Action
    String inbox(String email, String password) {
        String check = new CheckingEmails().check(email, password);
        System.out.println(check);
        return check;
    }
}