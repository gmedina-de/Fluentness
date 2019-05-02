package org.fluentness.controller;

import org.fluentness.common.Configuration;
import org.fluentness.networking.HttpResponse;
import org.fluentness.networking.HttpStatusCode;
import org.fluentness.view.View;

public interface Controller {

    default HttpResponse render(View view, String language) {
        return new HttpResponse(HttpStatusCode.Ok).setBody(view.renderWithCacheAndTemplateAndLocalization(language));
    }

    default HttpResponse render(View view) {
        return new HttpResponse(HttpStatusCode.Ok).setBody(view.renderWithCacheAndTemplateAndLocalization(Configuration.getString(Configuration.APP_LANGUAGE)));
    }

    default HttpResponse response(String body) {
        return new HttpResponse(HttpStatusCode.Ok).setBody(body);
    }

    default HttpResponse redirect(String to) {
        return new HttpResponse(HttpStatusCode.MovedPermanently).setHeader("Location", to);
    }

}
