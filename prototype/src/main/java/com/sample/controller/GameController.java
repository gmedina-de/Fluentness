package com.sample.controller;

import org.fluentness.controller.AbstractGameController;

public class GameController extends AbstractGameController<Game> {

    public GameController(Game view) {
        super(view);
    }

    @Override
    public void loop() {
        game.light.getAmbientLight();
    }
}
