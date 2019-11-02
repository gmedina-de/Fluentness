package org.fluentness.service.manager;

import org.fluentness.service.Service;

import java.util.List;

public interface Manager extends Service {
    <T> List<T> getInstances(Class<T> tClass);

    <T> T getInstance(Class<T> tClass);

    <T> void inject(List<Class<? extends T>> classes) throws InjectionException;

    <T> List<Class<? extends T>> loadClasses(String packageName, Class<T> parent) throws ClassLoadingException;
}