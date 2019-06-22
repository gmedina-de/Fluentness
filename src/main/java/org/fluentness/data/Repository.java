package org.fluentness.data;

import org.fluentness.common.lambdas.KeyValuePair;
import org.fluentness.common.logging.Log;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public final class Repository<T extends Model> {

    private Class<T> modelClass;

    public Repository(Class<T> modelClass) {
        this.modelClass = modelClass;
    }

    public List<T> findAll() {
        Transaction transaction = null;
        try {
            Session session = SessionFactory.INSTANCE.openSession();
            transaction = session.beginTransaction();
            List<T> list = session.createQuery("from " + modelClass.getName()).list();
            transaction.commit();
            Log.INSTANCE.debug("All %s records retrieved successfully", this.getClass().getSimpleName());
            return list;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            Log.INSTANCE.error(e);
        }
        return new ArrayList<>();
    }

    public List<T> query(String namedQuery, KeyValuePair<Object>... parameters) {
        Transaction transaction = null;
        try {
            Session session = SessionFactory.INSTANCE.openSession();
            transaction = session.beginTransaction();
            Query<T> query = session.createNamedQuery(namedQuery, modelClass);
            for (KeyValuePair<Object> parameter : parameters) {
                query.setParameter(parameter.getKey(), parameter.getValue());
            }
            List<T> list = query.list();
            transaction.commit();
            Log.INSTANCE.debug("%s records retrieved successfully using named query '%s'",
                this.getClass().getSimpleName(),
                namedQuery
            );
            return list;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            Log.INSTANCE.error(e);
        }
        return new ArrayList<>();
    }
}
