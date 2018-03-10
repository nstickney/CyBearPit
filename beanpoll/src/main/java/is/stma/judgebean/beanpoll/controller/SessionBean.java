package is.stma.judgebean.beanpoll.controller;

import is.stma.judgebean.beanpoll.model.User;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class SessionBean implements Serializable {

    private static final long serialVersionUID = 721057087394449169L;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean checkAuthenticationStatus() {
        return null == user;
    }

    public boolean isAdmin() {
        return null != user && user.isAdmin();
    }

    public boolean hasTeam() {
        return null != user && null != user.getTeam();
    }

}