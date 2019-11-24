package org.fluentness;

import org.fluentness.controller.Controller;
import org.fluentness.repository.Repository;
import org.fluentness.service.Service;
import org.fluentness.service.configurator.Configurator;
import org.fluentness.service.loader.Loader;
import org.fluentness.service.loader.LoaderException;

import java.util.List;

public interface Application {

    default String getName() {
        return this.getClass().getSimpleName().replace("Application","");
    }

    default List<Class<? extends Controller>> getControllers(Loader loader) throws LoaderException {
        return loader.load(this.getClass().getPackage().getName() + ".controller", Controller.class);
    }

    default List<Class<? extends Repository>> getRepositories(Loader loader) throws LoaderException {
        return loader.load(this.getClass().getPackage().getName() + ".repository", Repository.class);
    }

    default List<Class<? extends Service>> getServices(Loader loader) throws LoaderException {
        return loader.load(this.getClass().getPackage().getName() + ".service", Service.class);
    }

    default Platform getPlatform() {
        return Platform.WEB;
    }

    default Environment getEnvironment() {
        return Environment.DEV;
    }

    default void configure(Configurator configurator, Environment environment) {

    }

    enum Platform {
        CONSOLE,
        DESKTOP,
        WEB,
    }

    enum Environment {
        DEV,
        TEST,
        PROD,
    }

}
