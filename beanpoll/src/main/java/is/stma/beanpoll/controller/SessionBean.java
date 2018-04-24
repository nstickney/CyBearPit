/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.beanpoll.controller;

import is.stma.beanpoll.model.User;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class SessionBean extends AbstractFacesController implements Serializable {

    private static final long serialVersionUID = 721057087394449169L;

    @Produces
    @Named("sessionUser")
    @RequestScoped
    private User user;

    private String username;
    private String password;
    private String newPassword;

    @PostConstruct
    private void updateUsername() {
        if (null != user) {
            username = user.getLogName();
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

    public User getUser() {
        if (null == user) {
            return new User();
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        password = null;
        newPassword = null;
    }

    public boolean isAuthenticated() {
        return null != user;
    }

    public boolean isAdmin() {
        return null != user && user.isAdmin();
    }

    public boolean isJudge() {
        return null != user && user.isJudge();
    }

    public boolean hasTeam() {
        return null != user && null != user.getTeam();
    }

    public String logOut() {
        facesContext.getExternalContext().invalidateSession();
        return SessionController.LOGIN_PAGE;
    }

}