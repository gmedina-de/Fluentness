package org.fluentness.repository;

import java.io.Serializable;

public interface Model implements Serializable {

    private long id = 0;

    public final long getId() {
        return id;
    }

    public final void setId(long id) {
        this.id = id;
    }
}
