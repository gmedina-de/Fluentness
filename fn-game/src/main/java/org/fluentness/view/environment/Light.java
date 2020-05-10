package org.fluentness.view.environment;

import org.fluentness.service.algebra.Vector3f;
import org.fluentness.view.SceneElement;

public class Light implements SceneElement {

    private Vector3f translation;
    private Vector3f colour;
    private float ambientLight = 0.5f;

    public Light(Vector3f translation, Vector3f colour) {
        this.translation = translation;
        this.colour = colour;
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public Light setTranslation(Vector3f translation) {
        this.translation = translation;
        return this;
    }

    public Vector3f getColour() {
        return colour;
    }

    public Light setColour(Vector3f colour) {
        this.colour = colour;
        return this;
    }

    public float getAmbientLight() {
        return ambientLight;
    }

    public Light setAmbientLight(float ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }
}
