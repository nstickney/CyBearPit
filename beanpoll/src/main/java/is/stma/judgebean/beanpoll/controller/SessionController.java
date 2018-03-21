/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.judgebean.beanpoll.controller;

import is.stma.judgebean.beanpoll.model.User;
import is.stma.judgebean.beanpoll.util.AuthenticationException;

import javax.ejb.EJBException;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.NoResultException;

@Model
public class SessionController extends AbstractFacesController {

    public static final String LOGIN_PAGE = "index.xhtml";
    private static final String ADMIN_PAGE = "contests.xhtml";
    private static final String JUDGE_PAGE = "grading.xhtml";
    private static final String TEAM_PAGE = "team.xhtml";
    private static final String USER_PAGE = "account.xhtml";

    private static final String LOGIN_SUCCESS = "Welcome to BeanPoll";

    @Inject
    private SessionBean bean;

    @Inject
    private UserController userController;

    private String username;
    private String password;

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

    public String authenticate() {

        // Early fail if username or password is empty
        if (null == username || username.isEmpty() || null == password || password.isEmpty()) {
            return failAuthentication(AuthenticationException.LOGIN_BLANK);
        }

        // Find the user in question, or fail gracefully
        User checkUser;
        try {
            checkUser = userController.getByName(username);
        } catch (EJBException | NoResultException e) {
            errorOut(e, AuthenticationException.LOGIN_INCORRECT);
            return LOGIN_PAGE;
        }

        // Authenticate only if the username and password match a user in the database
        if (null != checkUser && checkUser.checkPassword(password)) {

            //Set the session user
            bean.setUser(checkUser);

            // Send admin user to the admin page; otherwise go to the team page
            messageOut(LOGIN_SUCCESS);
            facesContext.getExternalContext().getFlash().setKeepMessages(true);
            return checkLoginNavigation();
        }

        // Fail
        return failAuthentication(AuthenticationException.LOGIN_INCORRECT);
    }

    private String failAuthentication(String summary) {
        bean.setUser(null);
        errorOut(new AuthenticationException(summary), "");
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        return LOGIN_PAGE;
    }

    public String checkAdminNavigation() {
        if (bean.isAdmin()) {
            return null;
        }
        return LOGIN_PAGE;
    }

    public String checkJudgeNavigation() {
        if (bean.isJudge()) {
            return null;
        }
        return LOGIN_PAGE;
    }

    public String checkTeamNavigation() {
        if (bean.hasTeam()) {
            return null;
        }
        return LOGIN_PAGE;
    }

    public String checkAuthenticated() {
        if (bean.isAuthenticated()) {
            return null;
        }
        return LOGIN_PAGE;
    }

    public String checkLoginNavigation() {
        if (bean.isAuthenticated()) {
            if (bean.hasTeam()) {
                return TEAM_PAGE;
            } else if (bean.isJudge()) {
                return JUDGE_PAGE;
            } else if (bean.isAdmin()) {
                return ADMIN_PAGE;
            }
            return USER_PAGE;
        }
        return LOGIN_PAGE;
    }

    public void changePassword() {
        userController.changePassword(bean.getUser(), bean.getPassword(),
                bean.getNewPassword(), false);
        bean.setPassword(null);
        bean.setNewPassword(null);
    }

    public String deleteAccount() {
        if (bean.isAuthenticated()) {
            userController.delete(bean.getUser());
            bean.setUser(null);
        }
        return checkAuthenticated();
    }

}
