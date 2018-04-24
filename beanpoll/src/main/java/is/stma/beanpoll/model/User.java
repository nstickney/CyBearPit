/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.beanpoll.model;

import is.stma.beanpoll.util.HashUtility;
import is.stma.beanpoll.util.PasswordUtility;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
public class User extends AbstractEntity implements Comparable<User> {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String salt;

    @Column(nullable = false)
    private String secret;

    @Column(nullable = false)
    private boolean admin = false;

    @Column(nullable = false)
    private boolean judge = false;

    @ManyToOne
    private Team team;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        if (admin) {
            return "Admin: " + name;
        } else if (judge) {
            return "Judge: " + name;
        } else if (null != team) {
            return team.getDisplayName() + ": " + name;
        }
        return name;
    }

    public String getLook() {
        if (admin) {
            return "danger";
        } else if (judge) {
            return "info";
        } else if (null == team) {
            return "warning";
        }
        return "default";
    }

    public String getPassword() {
        return "";
    }

    public boolean setPassword(String password) {
        if (PasswordUtility.meetsRequirements(password)) {
            this.salt = UUID.randomUUID().toString();
            this.secret = HashUtility.hash(salt + password);
            return true;
        }
        return false;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isJudge() {
        return judge;
    }

    public void setJudge(boolean judge) {
        this.judge = judge;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public boolean checkPassword(String password) {
        return null != password && HashUtility.verify(salt + password, secret);
    }

    @Override
    public int compareTo(User o) {

        // If one is an admin, but not the other, decide based on that
        if (this.isAdmin() && !o.isAdmin()) {
            return -1;
        } else if (!this.isAdmin() && o.isAdmin()) {
            return 1;
        }

        // If one is an judge, but not the other, decide based on that
        if (this.isJudge() && !o.isJudge()) {
            return -1;
        } else if (!this.isJudge() && o.isJudge()) {
            return 1;
        }

        // If one is on a team, but not the other, decide based on that
        if (null == getDisplayName() && null != o.getDisplayName()) {
            return -1;
        } else if (null != getDisplayName() && null == o.getDisplayName()) {
            return 1;
        }

        int byDisplayName = getDisplayName().compareToIgnoreCase(o.getDisplayName());

        // If no other criteria have decided the issue, decide based on UUID
        if (0 == byDisplayName) {
            return getId().compareTo(o.getId());
        }
        return byDisplayName;
    }

    @Override
    public boolean equals(Object o) {
        return null != o &&
                o.getClass().equals(getClass()) &&
                equalByUUID((User) o);
    }

    @Override
    public int hashCode() {
        String hashCodeString = getId() + getDisplayName();
        return hashCodeString.hashCode();
    }
}
