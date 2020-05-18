package org.fluentness;

import org.fluentness.controller.AbstractGameController;
import org.fluentness.controller.scene.Scene;
import org.fluentness.service.algebra.AlgebraImpl;
import org.fluentness.service.animator.Animator;
import org.fluentness.service.animator.AnimatorImpl;
import org.fluentness.service.configuration.Configuration;
import org.fluentness.service.display.Display;
import org.fluentness.service.display.GlfwDisplay;
import org.fluentness.service.generator.GeneratorImpl;
import org.fluentness.service.injection.Provider;
import org.fluentness.service.loader.LoaderImpl;
import org.fluentness.service.memory.Memory;
import org.fluentness.service.memory.MemoryImpl;
import org.fluentness.service.parser.MeshParser;
import org.fluentness.service.parser.TextureParser;
import org.fluentness.service.render.EntityRender;
import org.fluentness.service.render.TerrainRender;
import org.fluentness.service.shader.EntityShader;
import org.fluentness.service.shader.TerrainShader;

public abstract class AbstractGameApplication implements Application {


    @Override
    public void provide(Provider provider) {
        provider
            .service(GeneratorImpl.class)
            .service(LoaderImpl.class)
            .service(MeshParser.class)
            .service(TextureParser.class)
            .service(EntityRender.class)
            .service(TerrainRender.class)
            .service(EntityShader.class)
            .service(TerrainShader.class)
            .service(GlfwDisplay.class)
            .service(AlgebraImpl.class)
            .service(AnimatorImpl.class)
            .service(MemoryImpl.class)
        ;
    }

    @Override
    public void configure(Configuration configuration) {

    }

    @Override
    public void run(String[] args) throws Exception {

        Display display = Fluentness.getInstance(Display.class);
        TerrainRender terrainRender = Fluentness.getInstance(TerrainRender.class);
        EntityRender entityRender = Fluentness.getInstance(EntityRender.class);
        Memory memory = Fluentness.getInstance(Memory.class);
        Animator animator = Fluentness.getInstance(Animator.class);
        AbstractGameController controller = Fluentness.getInstances(AbstractGameController.class).get(0);
        Scene scene = (Scene) controller.getGame().getTemplate();

        while (!display.shouldClose()) {
            display.clear(scene.background.colour);
            terrainRender.render(scene);
            entityRender.render(scene);
            controller.loop();
            animator.step();
            display.update();
        }
        memory.cleanUp();
        display.close();
    }
}
