package org.fluentness.engine.render;

import org.fluentness.engine.AbstractScene;
import org.fluentness.engine.entity.Terrain;
import org.fluentness.engine.shader.TerrainShader;
import org.fluentness.engine.texture.TerrainTexture;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import java.util.List;

import static org.fluentness.engine.algebra.MatrixFactory.transformationMatrix;

public class TerrainRender extends AbstractRender<TerrainShader> {

    public TerrainRender(AbstractScene scene) {
        super(new TerrainShader(), scene);

        // bind multi-texture slots to uniform
        shader.start();
        shader.set(shader.blendMap, 0);
        shader.set(shader.blackTexture, 1);
        shader.set(shader.redTexture, 2);
        shader.set(shader.greenTexture, 3);
        shader.set(shader.blueTexture, 4);
        shader.stop();
    }

    public void render(List<Terrain> terrainList) {
        shader.start();
        for (Terrain terrain : terrainList) {
            bind(terrain.getModel().getVao());
            bindTextures(terrain.getRepeatTextures(), terrain.getTextures());
            renderTerrain(terrain);
            unbind();
        }
        shader.stop();
    }

    private void bindTextures(float repeatTextures, TerrainTexture[] textures) {

        shader.set(shader.repeatTextures, repeatTextures);
        // todo handle transparency

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textures[0].getId());
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textures[1].getId());
        GL13.glActiveTexture(GL13.GL_TEXTURE2);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textures[2].getId());
        GL13.glActiveTexture(GL13.GL_TEXTURE3);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textures[3].getId());
        GL13.glActiveTexture(GL13.GL_TEXTURE4);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textures[4].getId());
    }

    private void renderTerrain(Terrain terrain) {
        shader.set(shader.transformationMatrix, transformationMatrix(terrain.getTranslation(), terrain.getRotation(), terrain.getScale()));
        shader.set(shader.shineDamper, 1);
        shader.set(shader.reflectivity, 0);

        GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
    }
}
