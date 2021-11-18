package montp.data.dao;

import montp.data.model.GenericEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

public abstract class GenericDAO<T extends GenericEntity> {

    @PersistenceContext
    protected EntityManager em;
    private Class<T> instanceClass;

    public GenericDAO() {
    }

    public GenericDAO(Class<T> instanceClass) {
        this.instanceClass = instanceClass;
    }

    public T get(long id) {
        return em.find(instanceClass, id);
    }

    @Transactional
    public void insert(T instance) {
        em.persist(instance);
        em.flush();
    }

    @Transactional
    public void update(T instance) {
        em.merge(instance);
    }

    @Transactional
    public void delete(T instance) {
        em.remove(em.find(instanceClass, instance.getId()));
    }

    public boolean canDelete(T instance) {
        return false;
    }
}
