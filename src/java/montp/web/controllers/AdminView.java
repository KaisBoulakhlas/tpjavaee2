package montp.web.controllers;

import montp.data.model.security.User;
import montp.locale.Messages;
import montp.services.UserService;
import montp.tools.EMailer;
import montp.tools.Logger;
import montp.web.FacesTools;
import montp.web.UserSession;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named("adminView")
public class AdminView implements Serializable {
    @Inject
    private UserService userService;
    @Inject private UserSession session;
    @Inject private EMailer eMailer;
    @Inject private Messages messages;

    private String emailTo;

    @PostConstruct
    public void init() {
        Logger.log(Logger.LogLevel.INFO, AdminView.class.getSimpleName(), "initializing view controller");
        if(!session.isAdmin()){
            FacesTools.redirect("index");
        }
    }

    public List<User> getUsers() { return userService.getUsers(); }

    public boolean isUserActive(User user) { return userService.isActive(user); }

    public void changeActive(User user){
        if(isUserActive(user)){
            userService.disable(user);
        }else{
            userService.enable(user);
        }
    }
}