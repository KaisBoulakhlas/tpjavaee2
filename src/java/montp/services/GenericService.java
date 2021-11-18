package montp.services;

import montp.data.dao.GenericDAO;
import montp.data.model.GenericEntity;

import javax.inject.Inject;
import javax.transaction.Transactional;

public class GenericService<T extends GenericEntity, DAO extends GenericDAO<T>> {

    @Inject
    protected DAO dao;

    public T get(long id) { return dao.get(id); }

    @Transactional
    public void insert(T instance) { dao.insert(instance); }

    @Transactional
    public void update(T instance) { dao.update(instance); }

    @Transactional
    public void delete(T instance) { dao.delete(instance); }

    public boolean canDelete(T instance) { return dao.canDelete(instance); }

}
