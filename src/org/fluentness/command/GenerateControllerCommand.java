package org.fluentness.command;

import org.fluentness.common.ClassRegister;
import org.fluentness.common.Configuration;
import org.fluentness.controller.Controller;
import org.fluentness.controller.Route;
import org.fluentness.generator.ClassGenerator;
import org.fluentness.generator.FieldGenerator;
import org.fluentness.generator.MethodGenerator;
import org.fluentness.networking.Request;
import org.fluentness.networking.Response;
import org.fluentness.repository.Repository;

import java.lang.reflect.Modifier;

public class GenerateControllerCommand implements Command {

    @Override
    public String getName() {
        return "generate:controller";
    }

    @Override
    public String getDescription() {
        return "Creates a new controller class within the controller package";
    }

    @Override
    public Parameters getParameters() {
        return new Parameters()
                .add("name");
    }

    @Override
    public void execute(Parameters parameters) {
        String name = parameters.get("name");

        new ClassGenerator(name + "Controller")
                .setPackage(Configuration.getString(Configuration.APP_PACKAGE) + "." + ClassRegister.CONTROLLER)
                .addModifier(Modifier.PUBLIC)
                .addAnnotation(Route.class, "\"/" + name.toLowerCase() + "\"")
                .addInterface(Controller.class)
                .addField(
                        new FieldGenerator(Repository.class, "repository")
                                .addModifier(Modifier.PRIVATE)
                                .setDefaultValue("new ServerStartCommand()")
                                .generate()
                )
                .addMethod(
                        new MethodGenerator(Response.class, "list")
                                .addAnnotation(Route.class, "\"/list\"")
                                .addModifier(Modifier.PUBLIC)
                                .addParameter(Request.class, "request")
                                .setImplementationLines(
                                        "// todo implement list " + name,
                                        "return redirect(\"/\");"
                                )
                                .generate()
                )
                .addMethod(
                        new MethodGenerator(Response.class, "find")
                                .addAnnotation(Route.class, "\"/find\"")
                                .addModifier(Modifier.PUBLIC)
                                .addParameter(Request.class, "request")
                                .setImplementationLines(
                                        "// todo implement list " + name,
                                        "return redirect(\"/\");"
                                )
                                .generate()
                )
                .addMethod(
                        new MethodGenerator(Response.class, "create")
                                .addAnnotation(Route.class, "\"/create\"")
                                .addModifier(Modifier.PUBLIC)
                                .addParameter(Request.class, "request")
                                .setImplementationLines(
                                        "// todo implement create " + name,
                                        "return redirect(\"/\");"
                                )
                                .generate()
                )
                .addMethod(
                        new MethodGenerator(Response.class, "update")
                                .addAnnotation(Route.class, "\"/update\"")
                                .addModifier(Modifier.PUBLIC)
                                .addParameter(Request.class, "request")
                                .setImplementationLines(
                                        "// todo implement update " + name,
                                        "return redirect(\"/\");"
                                )
                                .generate()
                )
                .addMethod(
                        new MethodGenerator(Response.class, "delete")
                                .addAnnotation(Route.class, "\"/delete\"")
                                .addModifier(Modifier.PUBLIC)
                                .addParameter(Request.class, "request")
                                .setImplementationLines(
                                        "// todo implement delete " + name,
                                        "return redirect(\"/\");"
                                )
                                .generate()
                )
                .generate()
                .writeToFile()
        ;
    }
}
