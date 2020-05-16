package com.sample.controller;

import org.fluentness.controller.AbstractWeb;
import org.fluentness.controller.WebView;

import static com.sample.service.StringTranslator.*;
import static org.fluentness.controller.html.HtmlAttribute.*;

public class Web extends AbstractWeb {

    @Override
    public WebView render() {
        return html(
            head(
                title("The book library made with Fluentness"),
                meta(NAME + "lang", CONTENT + "en"),
                meta(CHARSET + "UTF-8"),
                meta(NAME + "viewport", CONTENT + "width=device-width, initial-scale=1"),
                link(REL + "stylesheet", TYPE + "text/css", HREF + "https://cdn.jsdelivr.net/npm/picnic"),
                link(REL + "stylesheet", TYPE + "text/css", HREF + "https://icono-49d6.kxcdn.com/icono.min.css"),
                link(REL + "stylesheet", TYPE + "text/css", HREF + "/resources/css/styles.css"),

                script(SRC + "/resources/js/script.min.js", "-")
            ),
            body(
                nav(
                    input(ID + "burger", TYPE + "checkbox", CLASS + "show"),
                    label(FOR + "burger", CLASS + "burger pseudo button", _menu),
                    div(CLASS + "menu",
                        action(WebController.class, "index", CLASS + "pseudo button", "WorkStation"),
                        action(WebFilesController.class, "files", CLASS + "pseudo button", _files),
                        action(WebEmailController.class, "email", CLASS + "pseudo button", _email),
                        action(WebCalendarController.class, "month", CLASS + "pseudo button", _calendar),
                        action(WebContactsController.class, "contacts", CLASS + "pseudo button", _contacts),
                        action(WebTasksController.class, "tasks", CLASS + "pseudo button", _tasks),
                        action(WebNotesController.class, "notes", CLASS + "pseudo button", _notes),
                        action(WebBookmarksController.class, "bookmarks", CLASS + "pseudo button", _bookmarks),
                        action(WebPasswordsController.class, "passwords", CLASS + "pseudo button", _passwords),
                        div(CLASS + "right",
                            action(WebSettingsController.class, "users", CLASS + "pseudo button right", _users),
                            action(WebSettingsController.class, "settings", CLASS + "pseudo button right", _settings)
                        )
                    )
                ),
                div(CLASS + "flex wrapper",
                    ACTION_RESULT
                )
            )
        );
    }

}