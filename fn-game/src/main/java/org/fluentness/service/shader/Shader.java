package org.fluentness.service.shader;

import org.fluentness.service.algebra.Matrix4f;
import org.fluentness.service.algebra.Vector3f;
import org.fluentness.service.Service;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;

@Service.MultiService
public interface Shader extends Service {

    FloatBuffer MATRIX_4F_BUFFER = BufferUtils.createFloatBuffer(16);

    int getProgram();

    default void start() {
        GL20.glUseProgram(getProgram());
    }

    default void stop() {
        GL20.glUseProgram(0);
    }

    default void set(int uniform, int value) {
        GL20.glUniform1i(uniform, value);
    }

    default void set(int uniform, float value) {
        GL20.glUniform1f(uniform, value);
    }

    default void set(int uniform, boolean value) {
        GL20.glUniform1f(uniform, value ? 1 : 0);
    }

    default void set(int uniform, Vector3f vector) {
        GL20.glUniform3f(uniform, vector.x, vector.y, vector.z);
    }

    default void set(int uniform, Matrix4f matrix) {
        matrix.toBuffer(MATRIX_4F_BUFFER);
        GL20.glUniformMatrix4fv(uniform, false, MATRIX_4F_BUFFER);
    }

}
