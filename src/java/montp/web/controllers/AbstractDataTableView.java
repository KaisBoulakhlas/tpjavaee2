package montp.web.controllers;

import montp.data.dao.GenericDAO;
import montp.data.model.GenericEntity;
import montp.locale.Messages;
import montp.services.GenericService;
import montp.tools.Logger;
import montp.web.FacesTools;

import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.transaction.TransactionalException;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractDataTableView<T extends GenericEntity, DAO extends GenericDAO<T>, SVC extends GenericService<T, DAO>> implements Serializable {

    @Inject protected Messages msg;
    @Inject protected SVC service;

    protected List<T> datas;

    public abstract void init();

    public boolean canDelete(T instance) {
        return service.canDelete(instance);
    }

    public void update(T instance) {
        service.update(instance);
    }

    public void delete(T instance) {
        try {
            service.delete(instance);
            if (datas != null) datas.remove(instance);
            FacesTools.addMessage(FacesMessage.SEVERITY_INFO, msg.get("app.delete.success"));

        } catch (TransactionalException e) {
            FacesTools.addMessage(FacesMessage.SEVERITY_ERROR, msg.get("app.delete.error"));
            Logger.log(Logger.LogLevel.ERROR, IndexView.class.getName(), "delete : %s", e.getMessage());
        }
    }

    public List<T> getDatas() {
        return datas;
    }
}
