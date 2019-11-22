package org.fluentness.service.injector;

import org.fluentness.ApplicationComponent;
import org.fluentness.service.Service;

import java.util.List;

public interface Injector extends Service {
    <A extends ApplicationComponent> List<A> getInstances(Class<A> tClass);

    <A extends ApplicationComponent> A getInstance(Class<A> tClass);

    <A extends ApplicationComponent> void inject(List<Class<? extends A>> classes) throws InjectorException;
}