package org.fluentness.repository;

import java.io.Serializable;

public interface Model extends Serializable {

    long getId();

    void setId(long id);
}
