package org.fluentness.quatsch;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Translates.class)
public @interface Translate {
    Language to();
    String as();
}
