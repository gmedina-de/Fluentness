package org.fluentness.repository.persistence;

import org.fluentness.model.Model;
import org.fluentness.service.persistence.Persistence;

import java.util.List;

public abstract class AbstractPersistenceRepository<M extends Model> implements PersistenceRepository<M> {

    protected final Class<M> modelClass;
    protected final Persistence persistence;

    public AbstractPersistenceRepository(Persistence persistence, Class<M> modelClass) {
        this.modelClass = modelClass;
        this.persistence = persistence;
    }

    @Override
    public M select(long id) {
        return persistence.retrieve(modelClass, id);
    }

    @Override
    public List<M> selectAll() {
        return persistence.retrieve(modelClass);
    }

    @Override
    public List<M> selectByField(String field, Object value) {
        return persistence.retrieve(modelClass, field + "=" + value);
    }

    @Override
    public int insert(M model) {
        return persistence.persist(model);
    }

    @Override
    public int update(M model) {
        return persistence.persist(model);
    }

    @Override
    public int delete(M model) {
        return persistence.remove(model);
    }

}