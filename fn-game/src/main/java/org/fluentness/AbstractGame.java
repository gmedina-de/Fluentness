package org.fluentness;

import org.fluentness.controller.AbstractGameController;
import org.fluentness.controller.Controller;
import org.fluentness.controller.Input;
import org.fluentness.service.Service;
import org.fluentness.service.algebra.DefaultAlgebra;
import org.fluentness.service.display.Display;
import org.fluentness.service.display.GlfwDisplay;
import org.fluentness.service.generator.TerrainGenerator;
import org.fluentness.service.injection.Provider;
import org.fluentness.service.loader.DefaultLoader;
import org.fluentness.service.memory.DefaultMemory;
import org.fluentness.service.memory.Memory;
import org.fluentness.service.parser.ShapeParser;
import org.fluentness.service.parser.TextureParser;
import org.fluentness.service.render.EntityRender;
import org.fluentness.service.render.TerrainRender;
import org.fluentness.service.shader.EntityShader;
import org.fluentness.service.shader.TerrainShader;
import org.fluentness.view.AbstractGameView;

public abstract class AbstractGame implements Application {

    public AbstractGame(Provider<Controller> controllers) {

    }

    @Override
    public Provider<Service> services() {
        return Application.super.services()
            .add(TerrainGenerator.class)
            .add(DefaultLoader.class)
            .add(ShapeParser.class)
            .add(TextureParser.class)
            .add(EntityRender.class)
            .add(TerrainRender.class)
            .add(EntityShader.class)
            .add(TerrainShader.class)
            .add(GlfwDisplay.class)
            .add(DefaultAlgebra.class)
            .add(DefaultMemory.class)
            ;
    }

    @Override
    public void run(String[] args) throws Exception {

        Display display = Fluentness.getInstance(Display.class);
        TerrainRender terrainRender = Fluentness.getInstance(TerrainRender.class);
        EntityRender entityRender = Fluentness.getInstance(EntityRender.class);
        Memory memory = Fluentness.getInstance(Memory.class);
        AbstractGameController controller = Fluentness.getInstances(AbstractGameController.class).get(0);
        AbstractGameView scene = controller.getView();


        // todo integrate into controller
        //glfwSetKeyCallback(display.getWindow(), glfwKeyCallback);
        Input input = new Input(scene);

        while (!display.shouldClose()) {
            display.clear(scene.getBackground().getColour());
            input.handle(display.getWindow());
            controller.loop();
            terrainRender.render(scene);
            entityRender.render(scene);
            display.update();
        }
        memory.cleanUp();
        display.close();
    }
}