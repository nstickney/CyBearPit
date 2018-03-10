package is.stma.judgebean.beanpoll.controller;

import is.stma.judgebean.beanpoll.model.User;
import is.stma.judgebean.beanpoll.service.UserService;
import is.stma.judgebean.beanpoll.util.AuthenticationException;
import is.stma.judgebean.beanpoll.util.PasswordUtility;

import javax.ejb.EJBException;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.xml.bind.ValidationException;

@Model
public class SessionController extends AbstractFacesController {

    private static final String LOGIN_SUCCESS = "Welcome to BeanPoll";

    private static final String LOGIN_PAGE = "index.xhtml";
    private static final String ADMIN_PAGE = "control.xhtml";
    private static final String TEAM_PAGE = "team.xhtml";

    @Inject
    private SessionBean bean;

    @Inject
    private UserService userService;

    @Inject
    private UserController userController;

    private String username;

    private String password;
    private String newPassword;

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

    public String authenticate() {

        // Early fail if username or password is empty
        if (null == username || username.isEmpty() || null == password || password.isEmpty()) {
            return failAuthentication(AuthenticationException.LOGIN_BLANK);
        }

        // Find the user in question, or fail gracefully
        User checkUser;
        try {
            checkUser = userService.getByName(username);
        } catch (NoResultException | EJBException e) {
            errorOut(e, AuthenticationException.LOGIN_INCORRECT);
            return LOGIN_PAGE;
        }

        // Authenticate only if the username and password match a user in the database
        if (null != checkUser && checkUser.checkPassword(password)) {

            //Set the session user
            bean.setUser(checkUser);

            // Send admin user to the admin page; otherwise go to the team page
            if (bean.isAdmin()) {
                return ADMIN_PAGE;
            } else {
                return TEAM_PAGE;
            }
        }

        // Fail
        return failAuthentication(AuthenticationException.LOGIN_INCORRECT);
    }

    private String failAuthentication(String summary) {
        bean.setUser(null);
        errorOut(new AuthenticationException(), summary);
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        return LOGIN_PAGE;
    }

    public String checkAdminNavigation() {
        if (bean.isAdmin()) {
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

    public String checkLoginNavigation() {
        if (bean.checkAuthenticationStatus()) {
            checkTeamNavigation();
            checkAdminNavigation();
        }
        return null;
    }

    public String changeUsername() {
        userController.update(bean.getUser());
        bean.setUser(userService.getByName(username));
        return "";
    }

    public String changePassword() {
        if (bean.getUser().checkPassword(password) &&
                PasswordUtility.meetsRequirements(newPassword)) {
            username = bean.getUser().getName();
            password = newPassword;
            bean.getUser().setSecret(newPassword);
            userController.update(bean.getUser());
            return authenticate();
        }
        errorOut(new ValidationException("Incorrect password"), "Incorrect password");
        return "";
    }

}
