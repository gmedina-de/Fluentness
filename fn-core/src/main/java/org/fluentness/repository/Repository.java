package org.fluentness.repository;

import org.fluentness.model.Model;

import java.util.List;

public interface Repository<M extends Model> {
    M select(long id);

    List<M> selectAll();

    List<M> selectByField(String field, Object value);

    int insert(M model);

    int update(M model);

    int delete(M model);

    int delete(long id);
}
