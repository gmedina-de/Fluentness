package org.fluentness.base.common.injection;

import org.fluentness.flow.producer.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InjectProvider {
    Class<? extends Component> value();
}
