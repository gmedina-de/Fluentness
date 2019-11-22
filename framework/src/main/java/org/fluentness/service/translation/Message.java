package org.fluentness.service.translation;

public class Message {
    private final Language language;
    private final String message;

    public Message(Language language, String message) {
        this.language = language;
        this.message = message;
    }

    public Language getLanguage() {
        return language;
    }

    public String getMessage() {
        return message;
    }
}