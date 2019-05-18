package com.sample.view;

import org.fluentness.renderable.Renderable;
import org.fluentness.view.View;

public class BaseView implements View.Html {

    @Placeholder
    private View placeholder;

    @Override
    public Renderable getRenderable() {
        return html(
                head(
                        title("A music archive made with love and Fluentness"),
                        meta(with(NAME -> "lang", CONTENT -> getLocale().getLanguage())),
                        meta(with(CHARSET -> "utf-8")),
//                        new FormStyle().getRuleset().toString(),
                        includeCss("milligram.min.css"),
                        includeCss("custom.css"),
                        includeJs("script.min.js")
                ),
                body(
                        div(with(CLASS -> "container"),
                                h1(translate("welcome_message")),
                                placeholder.getRenderable()
                        )
                )
        );
    }
}
