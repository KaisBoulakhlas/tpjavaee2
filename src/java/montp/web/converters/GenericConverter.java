package montp.web.converters;

import montp.data.dao.GenericDAO;
import montp.data.model.GenericEntity;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class GenericConverter<T extends GenericEntity> implements Converter<T> {

    protected GenericDAO<T> dao;

    public GenericConverter(GenericDAO<T> dao) {
        this.dao = dao;
    }

    @Override
    public T getAsObject(FacesContext context, UIComponent component, String id) {
        if (id.isEmpty()) return null;
        return dao.get(Integer.parseInt(id));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, T value) {
        if (value == null) return "";
        if (value.getId() == null) return "";
        return value.getId().toString();
    }

}
