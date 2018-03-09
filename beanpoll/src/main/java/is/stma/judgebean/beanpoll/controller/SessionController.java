package is.stma.judgebean.beanpoll.controller;

import is.stma.judgebean.beanpoll.model.User;
import is.stma.judgebean.beanpoll.service.UserService;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;

@Model
public class SessionController extends AbstractFacesController {

    private static final String LOGIN_FAILURE = "Authentication failed";
    private static final String LOGIN_BLANK = "Username and password are required";
    private static final String LOGIN_INCORRECT = "Username or password is incorrect";

    private static final String LOGIN_SUCCESS = "Welcome to BeanPoll";

    private static final String LOGIN_PAGE = "index.xhtml";
    private static final String ADMIN_PAGE = "admin.xhtml";
    private static final String TEAM_PAGE = "team.xhtml";

    @Inject
    private SessionUserBean bean;

    @Inject
    private UserService userService;

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

    public boolean checkAuthenticated() {
        return null != bean.getSessionUser();
    }

    public boolean checkAdmin() {
        return checkAuthenticated() && bean.getSessionUser().isAdmin();
    }

    public boolean checkTeamExists() {
        return checkAuthenticated() && null != bean.getSessionUser().getTeam();
    }

    public String authenticate() {

        // Early fail if username or password is empty
        if (null  == username || username.isEmpty() || null == password || password.isEmpty()) {
            return failAuthentication(LOGIN_BLANK);
        }

        // Find the user in question
        User checkUser = userService.getByUsername(username);

        // Authenticate only if the username and password match a user in the database
        if (null != checkUser && checkUser.checkPassword(password)) {

            //Set the session user
            bean.setSessionUser(checkUser);

            // Send admin user to the admin page; otherwise go to the team page
            if (bean.getSessionUser().isAdmin()) {
                return ADMIN_PAGE;
            } else {
                return TEAM_PAGE;
            }
        }

        // Fail
        return failAuthentication(LOGIN_INCORRECT);
    }

    private String failAuthentication(String summary) {
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, LOGIN_FAILURE, summary));
        facesContext.getExternalContext().getFlash().setKeepMessages(true);
        return LOGIN_PAGE;
    }
}
