package org.fluentness.controller.scene.entity;

import org.fluentness.controller.scene.SceneElement;
import org.fluentness.repository.Shape;
import org.fluentness.repository.Texture;
import org.fluentness.service.algebra.Vector3f;

public class Entity implements SceneElement {

    public Shape shape;
    public Texture texture;

    public Vector3f translation;
    public Vector3f rotation;
    public float scale;

    public float translationSpeed = 200;
    public float rotationSpeed = 200;

    public float jumpPower = 100;
    public float gravity = -250;
    public float jumpSpeed = 0;
    public boolean isJumping = false;

    public float shineDamper = 1;
    public float reflectivity = 0;

    public Entity(Shape shape, Texture texture) {
        this.shape = shape;
        this.texture = texture;
        this.translation = new Vector3f(0, 0, 0);
        this.rotation = new Vector3f(0, 0, 0);
        this.scale = 1;
    }

    public void control(float delta, float upDown, float leftRight, float jump) {
        float distance = -this.translationSpeed * upDown * delta;
        translation.x += (float) (distance * Math.sin(Math.toRadians(rotation.y)));
        translation.z += (float) (distance * Math.cos(Math.toRadians(rotation.y)));
        rotation.y += -this.rotationSpeed * leftRight * delta;

        if (jump > 0 && !isJumping) {
            jumpSpeed = jumpPower * jump;
            isJumping = true;
        }
        jumpSpeed += gravity * delta;
        translation.y += jumpSpeed * delta;
        if (translation.y <= 0) {
            jumpSpeed = 0;
            translation.y = 0;
            isJumping = false;
        }
    }

}
