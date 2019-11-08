package org.fluentness;

import org.fluentness.controller.Controller;
import org.fluentness.repository.Repository;
import org.fluentness.service.Service;
import org.fluentness.service.loader.LoaderException;
import org.fluentness.service.loader.LoaderService;

import java.util.List;

public interface Application {

    enum Platform {
        CONSOLE,
        DESKTOP,
        MOBILE,
        WEB,
    }

    enum Environment {
        DEV,
        TEST,
        STAGE,
        PROD,
    }

    default List<Class<? extends Controller>> getControllers(LoaderService loader) throws LoaderException {
        return loader.load(this.getClass().getPackage().getName() + ".controller", Controller.class);
    }

    default List<Class<? extends Repository>> getRepositories(LoaderService loader) throws LoaderException {
        return loader.load(this.getClass().getPackage().getName() + ".repository", Repository.class);
    }

    default List<Class<? extends Service>> getServices(LoaderService loader) throws LoaderException {
        return loader.load(this.getClass().getPackage().getName() + ".service", Service.class);
    }

    Platform getPlatform();

    Environment getEnvironment();
}
