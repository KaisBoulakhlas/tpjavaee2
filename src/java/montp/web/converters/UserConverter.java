package montp.web.converters;

import montp.data.dao.UserDAO;
import montp.data.model.security.User;
import montp.tools.Tools;

import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = User.class)
public class UserConverter extends GenericConverter<User>{

    public UserConverter() {
        super(Tools.lookupBean(UserDAO.class));
    }
    
}
