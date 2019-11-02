package org.fluentness.service.persistence;

import org.apache.openjpa.lib.log.LogFactory;
import org.fluentness.service.configuration.Configuration;
import org.fluentness.service.logger.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.Map;

public class OpenJpaPersistence implements Persistence {

    private Logger logger;
    private EntityManager entityManager;

    public OpenJpaPersistence(Configuration configuration, Logger logger) {

        this.logger = logger;

        Map<String, Object> properties = new HashMap<>();
        properties.put("openjpa.Log", (LogFactory) channel -> new OpenJpaLoggingBridge(logger));

        if (configuration.has("persistence_unit")) {
            this.entityManager = javax.persistence.Persistence
                    .createEntityManagerFactory(configuration.get("persistence_unit"), properties)
                    .createEntityManager();
        }
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public boolean persist(Object model) {
        if (!entityManager.contains(model)) {
            if (!isActive()) {
                begin();
            }
            entityManager.persist(model);
            entityManager.flush();
            commit();
            logger.debug("%s record created successfully", model.getClass().getSimpleName());
            return true;
        }
        logger.debug("%s record already exists, cannot create", model.getClass().getSimpleName());
        return false;
    }

    @Override
    public boolean remove(Object model) {
        if (entityManager.contains(model)) {
            if (!isActive()) {
                begin();
            }
            entityManager.remove(model);
            entityManager.flush();
            commit();
            logger.debug("%s record deleted successfully", model.getClass().getSimpleName());
            return true;
        }
        logger.debug("%s record doesn't exists, cannot delete", model.getClass().getSimpleName());
        return false;
    }

    @Override
    public <M> M find(Class<M> modelClass, int id) {
        logger.debug("Retrieving %s record by ID %s", modelClass.getSimpleName(), id);
        return entityManager.find(modelClass, id);
    }

    @Override
    public Query query(String query) {
        return entityManager.createQuery(query);
    }

    @Override
    public boolean isActive() {
        return entityManager.getTransaction().isActive();
    }

    @Override
    public void begin() {
        entityManager.getTransaction().begin();
    }

    @Override
    public void commit() {
        entityManager.getTransaction().commit();
    }

    @Override
    public void rollback() {
        entityManager.getTransaction().rollback();
    }

}






















