package com.sample.controller;

import com.sample.repository.NoteRepository;
import org.fluentness.controller.web.AbstractWebController;
import org.fluentness.controller.web.template.html.Html;

import static com.sample.service.Translator.*;
import static org.fluentness.controller.web.template.html.HtmlAttribute.CLASS;
import static org.fluentness.controller.web.template.html.HtmlFactory.*;

public class WebNotesController extends AbstractWebController<WebView> {

    private final NoteRepository noteRepository;

    @BasePath("/notes")
    public WebNotesController(NoteRepository noteRepository) {
        super(new WebView());
        this.noteRepository = noteRepository;
    }

    @Action(path = "/")
    Html notes() {
        return div(
            table(
                thead(
                    th(_note_title),
                    th(_note_description),
                    th("")
                ),
                tbody(
                    forEach(noteRepository.select(), note ->
                        tr(
                            td(note.getTitle()),
                            td(note.getDescription()),
                            td(
                                button(CLASS + "button button-outline", "\uD83D\uDD89"),
                                button(CLASS + "button", "⨯")
                            )
                        )
                    )
                )
            ),
            div(CLASS + "row",
                button(CLASS + "button column", _create)
            )
        );
    }


}