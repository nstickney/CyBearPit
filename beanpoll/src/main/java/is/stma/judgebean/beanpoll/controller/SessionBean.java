package is.stma.judgebean.beanpoll.controller;

import is.stma.judgebean.beanpoll.model.User;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ConversationScoped
public class SessionBean implements Serializable {

    private static final long serialVersionUID = 721057087394449169L;

    private User sessionUser = null;

    public User getSessionUser() {
        return sessionUser;
    }

    public void setSessionUser(User sessionUser) {
        this.sessionUser = sessionUser;
    }

    public String checkAuthenticationStatus() {
        if (sessionUser == null) {
            return "login.xhtml";
        }
        return null;
    }

    public String isAdmin() {
        if (sessionUser != null && sessionUser.isAdmin()) {
            return null;
        }
        return "login.xhtml";
    }

    public String logout() {
        sessionUser = null;
        return "login.xhtml";
    }

}