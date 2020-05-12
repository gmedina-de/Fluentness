package org.fluentness.controller;

import org.fluentness.service.algebra.Vector3f;
import org.fluentness.service.display.Display;

import static org.lwjgl.glfw.GLFW.*;

public abstract class AbstractGameController<V extends AbstractGame> implements Controller {

    protected final V game;
    protected final Display display;

    public AbstractGameController(V game, Display display) {
        this.game = game;
        this.display = display;
    }

    public final V getGame() {
        return game;
    }

    public abstract void loop();

    protected final boolean isKeyPressed(int glfwKey) {
        return glfwGetKey(display.getWindow(), glfwKey) == GLFW_PRESS;
    }

    protected boolean isMouseButtonPressed(int glfwMouseButton) {
        return glfwGetMouseButton(display.getWindow(), glfwMouseButton) == 1;
    }


    private final double[] posHolder = new double[1];

    protected double getCursorPositionX() {
        glfwGetCursorPos(display.getWindow(), posHolder, null);
        return posHolder[0];
    }

    protected double getCursorPositionY() {
        glfwGetCursorPos(display.getWindow(), null, posHolder);
        return posHolder[0];
    }

    protected int rotationStep = 5;
    private double lastCursorX;
    private double lastCursorY;
    protected void rotateWithMouse(Vector3f rotation) {
        double currentCursorX = getCursorPositionX();
        double currentCursorY = getCursorPositionY();
        if (currentCursorX > lastCursorX) {
            rotation.y += rotationStep;
        }
        if (currentCursorY > lastCursorY) {
            rotation.x += rotationStep;
        }
        if (currentCursorX < lastCursorX) {
            rotation.y -= rotationStep;
        }
        if (currentCursorY < lastCursorY) {
            rotation.x -= rotationStep;
        }
        lastCursorX = currentCursorX;
        lastCursorY = currentCursorY;
    }


    protected int translationStep = 5;
    protected void translateWithArrows(Vector3f translation) {
        if (isKeyPressed(GLFW_KEY_UP)) {
            translation.z -= translationStep;
        }
        if (isKeyPressed(GLFW_KEY_LEFT)) {
            translation.x -= translationStep;
        }
        if (isKeyPressed(GLFW_KEY_DOWN)) {
            translation.z += translationStep;
        }
        if (isKeyPressed(GLFW_KEY_RIGHT)) {
            translation.x += translationStep;
        }
    }
}
