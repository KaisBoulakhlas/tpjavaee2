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
import java.util.Objects;

@ViewScoped
@Named("register_user")
public class RegisterView implements Serializable {

    @Inject private UserSession session;

    @Inject private UserService userService;
    @Inject private EMailer eMailer;
    @Inject private Messages messages;

    private String emailTo;

    @PostConstruct
    public void init() {
        Logger.log(Logger.LogLevel.INFO, RegisterView.class.getSimpleName(), "initializing view controller");
    }

    public void registerUser(){
        String email = FacesTools.getRequest().getParameter("register_email");
        if(email != null){
            String password = FacesTools.getRequest().getParameter("register_password");
            String repeat_password = FacesTools.getRequest().getParameter("repeat_password");
            if(Objects.equals(password, repeat_password)){
                User user = new User(email,password);
                userService.insert(user);
                FacesTools.redirect("login");
            } else{
                FacesTools.addMessage(FacesMessage.SEVERITY_ERROR, messages.get("app.mdpRepeatmdp"));
            }
        }
    }
}