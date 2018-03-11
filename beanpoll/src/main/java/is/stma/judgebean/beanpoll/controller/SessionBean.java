package is.stma.judgebean.beanpoll.controller;

import is.stma.judgebean.beanpoll.model.User;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ConversationScoped
public class SessionBean extends AbstractFacesController implements Serializable {

    private static final long serialVersionUID = 721057087394449169L;

    private User user;

    private String username;
    private String password;
    private String newPassword;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if (null != user) {
            this.username = user.getName();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
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