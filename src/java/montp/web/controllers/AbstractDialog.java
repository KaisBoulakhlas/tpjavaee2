package montp.web.controllers;

import montp.data.dao.GenericDAO;
import montp.data.model.GenericEntity;
import montp.locale.Messages;
import montp.services.GenericService;
import montp.web.FacesTools;

import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import java.io.Serializable;

public abstract class AbstractDialog<T extends GenericEntity, DAO extends GenericDAO<T>, SVC extends GenericService<T, DAO>> implements Serializable {

    @Inject protected Messages msg;
    @Inject protected SVC service;

    protected T instance;
    protected AbstractDataTableView<T, DAO, SVC> dataTableView;

    public abstract void instanciate();

    public void create(AbstractDataTableView<T, DAO, SVC> dataTableView) {
        this.dataTableView = dataTableView;
        instanciate();
    }

    public void save() {
        if (instance.getId() == null) {
            service.insert(instance);
            FacesTools.addMessage(FacesMessage.SEVERITY_INFO, msg.get("app.added"));
            dataTableView.init();

        } else {
            service.update(instance);
            FacesTools.addMessage(FacesMessage.SEVERITY_INFO, msg.get("app.updated"));
        }
    }

    public T getInstance() {
        return instance;
    }

    public void setInstance(T instance) {
        this.instance = instance;
    }
}
