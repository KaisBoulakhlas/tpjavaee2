package montp.web.controllers;

import montp.data.dao.UserDAO;
import montp.data.model.security.User;
import montp.services.UserService;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@ViewScoped
@Named
public class EditUserDialog extends AbstractDialog<User, UserDAO, UserService> {

    @Override
    public void instanciate() {
        instance = new User();
    }

}