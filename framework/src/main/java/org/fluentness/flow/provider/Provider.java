package org.fluentness.flow.provider;

import org.fluentness.base.common.annotation.Inject;
import org.fluentness.base.common.exception.ProviderException;
import org.fluentness.base.service.logger.Logger;
import org.fluentness.flow.component.Component;
import org.fluentness.flow.component.task.Task;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public abstract class Provider<C extends Component> {

    @Inject
    protected Logger logger;

    private final List<C> components = new ArrayList<>();

    public final List<C> provideComponents() {
        if (components.isEmpty()) {

            try {
                Field[] fields = this.getClass().getDeclaredFields();
                // todo: improve adding default tasks
                if (this instanceof TaskProvider) {
                    fields = Stream.concat(Arrays.stream(fields), Arrays.stream(TaskProvider.class.getDeclaredFields())).toArray(Field[]::new);
                }
                for (Field field : fields) {
                    if (field.getModifiers() != 0) {
                        throw new ProviderException(
                            "Component %s->%s should have no modifiers, found %s",
                            this.getClass().getSimpleName(),
                            field.getName(),
                            Modifier.toString(field.getModifiers())
                        );
                    }
                    field.setAccessible(true);
                    if (!getComponentClass().isAssignableFrom(field.get(this).getClass())) {
                        throw new ProviderException(
                            "Provider %s should provide Components of type %s, use consumer interfaces for dependency injection instead",
                            this.getClass().getSimpleName(),
                            field.getName(),
                            getComponentClass().getSimpleName()
                        );
                    }
                    C component = (C) field.get(this);
                    component.setName(field.getName());
                    if (component.getClass().equals(Task.class)) {
                        component.setName(field.getName().replaceAll("_", ":"));
                    }
                    components.add(component);
                }
            } catch (IllegalAccessException e) {
                logger.error(e);
            } catch (ProviderException e) {
                logger.error(e.getMessage());
                System.exit(1);
            }
        }
        return components;
    }

    public final C getComponent(String name) {
        return provideComponents().stream().filter(component -> component.getName().equals(name)).findFirst().get();
    }

    public abstract Class<C> getComponentClass();
}
