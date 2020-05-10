package org.fluentness.service.injection;

import org.fluentness.Application;
import org.fluentness.ApplicationComponent;
import org.fluentness.Fluentness;
import org.fluentness.service.MultiService;
import org.fluentness.service.Service;
import org.fluentness.service.configuration.Configuration;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public final class FinalInjection implements Injection {

    private final Application application;

    public FinalInjection(Application application) {
        this.application = application;
    }

    @Override
    public void inject() throws InjectionException {
        inject(application.services().get());
        inject(application.repositories().get());
        inject(application.views().get());
        inject(application.controllers().get());
    }

    private <I extends ApplicationComponent> void inject(List<Class<? extends I>> classes) throws InjectionException {
        List<Class<? extends I>> notInstantiated = new LinkedList<>();
        List<Class<? extends I>> singleServices = new LinkedList<>();
        for (Class<? extends I> clazz : classes) {
            Class key = getKey(clazz);
            if (!singleServices.contains(key)) {
                Object instance = instantiate(clazz);
                if (instance instanceof Class) {
                    notInstantiated.add(clazz);
                } else {
                    if (instance instanceof Configuration) {
                        application.configure((Configuration) instance);
                    }
                    Fluentness.instances.put(key, instance);
                }
                singleServices.add(key);
            }
        }
        if (notInstantiated.size() > 0) {
            if (notInstantiated.size() >= classes.size()) {
                throw new InjectionException(
                    "Cannot resolve dependencies instantiating of [%s], possible causes: Circle dependency or dependency not declared in application",
                    notInstantiated.stream().map(Class::getSimpleName).collect(Collectors.joining(", "))
                );
            }
            inject(notInstantiated);
        }
    }

    private Class getKey(Class aClass) {
        if (!Service.class.isAssignableFrom(aClass)) {
            return aClass;
        }
        Class currentClass = aClass;
        while (!currentClass.equals(Object.class)) {
            for (Class anInterface : currentClass.getInterfaces()) {
                if (anInterface.equals(Service.class)) {
                    return currentClass.isAnnotationPresent(MultiService.class) ? aClass : currentClass;
                }
                if (Service.class.isAssignableFrom(anInterface)) {
                    return anInterface.isAnnotationPresent(MultiService.class) ? aClass : anInterface;
                }
            }
            currentClass = currentClass.getSuperclass();
        }
        return null;
    }

    private Object instantiate(Class aClass) throws InjectionException {
        Constructor constructor = getConstructor(aClass);
        try {
            if (constructor.getParameterCount() == 0) {
                // no dependencies, just instantiate
                return aClass.newInstance();
            }
            Parameter[] parameters = constructor.getParameters();
            Object[] result = new Object[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                Class<?> type = parameters[i].getType();
                if (Fluentness.instances.containsKey(type)) {
                    result[i] = Fluentness.instances.get(type);
                } else if (!ApplicationComponent.class.isAssignableFrom(type)) {
                    throw new InjectionException(
                        "All constructor parameters must be ApplicationComponents, %s's '%s' isn't",
                        aClass.getSimpleName(),
                        parameters[i].getName()
                    );
                } else {
                    // could not be instantiated, return type and try again later
                    return type;
                }
            }
            return constructor.newInstance(result);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new InjectionException(e);
        }
    }

    private Constructor getConstructor(Class aClass) throws InjectionException {
        if (Modifier.isInterface(aClass.getModifiers())) {
            throw new InjectionException("%s's class cannot be an interface in order to be instantiated", aClass.getName());
        }
        if (Modifier.isAbstract(aClass.getModifiers())) {
            throw new InjectionException("%s's class cannot be abstract in order to be instantiated", aClass.getName());
        }
        if (!Modifier.isPublic(aClass.getModifiers())) {
            throw new InjectionException("%s's class must be public in order to be instantiated", aClass.getName());
        }
        return Arrays.stream(aClass.getConstructors())
            .filter(constructor -> Modifier.isPublic(constructor.getModifiers()))
            .findFirst()
            .orElseThrow(() -> new InjectionException("%s should have at least one public constructor", aClass.getName()));

    }
}