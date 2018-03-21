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
import is.stma.judgebean.beanpoll.rules.UserRules;
import is.stma.judgebean.beanpoll.service.UserService;
import is.stma.judgebean.beanpoll.util.AuthenticationException;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.ValidationException;

@Model
public class UserController extends AbstractEntityController<User, UserRules,
        UserService> {

    private static final String PASSWORD_CHANGE_FAIL = "Cannot change password: ";

    @Inject
    private UserService service;

    private User newUser;

    private String newPassword;

    @Override
    @Produces
    @Named("newUser")
    User getNew() {
        if (newUser == null) {
            newUser = new User();
        }
        return newUser;
    }

    @Override
    void setNew(User entity) {
        newUser = entity;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    UserService getService() {
        return service;
    }

    @Override
    public void update(User entity) {
        doUpdate(entity);
    }

    @Override
    public void delete(User entity) {
        doDelete(entity);
    }

    public User getById(String id) {
        return service.readById(id);
    }

    public User getByName(String name) {
        return service.getByName(name);
    }

    public void changePassword(User entity, String password,
                               String newPassword, boolean adminOverride) {
        this.newPassword = null;
        if (null != entity) {
            if (entity.checkPassword(password) || adminOverride) {
                if (entity.setPassword(newPassword)) {
                    update(entity);
                } else {
                    errorOut(new ValidationException("password does not meet requirements"),
                            PASSWORD_CHANGE_FAIL);
                }
            } else {
                errorOut(new AuthenticationException(AuthenticationException.LOGIN_INCORRECT),
                        PASSWORD_CHANGE_FAIL);
            }
        } else {
            errorOut(new ValidationException("unknown user"), PASSWORD_CHANGE_FAIL);
        }
    }
}
