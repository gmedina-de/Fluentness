package org.fluentness.controller;

import org.fluentness.Application;

public interface View<V extends Template> extends Application.Component {

    V getTemplate();

}
