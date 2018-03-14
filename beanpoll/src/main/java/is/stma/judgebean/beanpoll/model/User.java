package is.stma.judgebean.beanpoll.model;

import is.stma.judgebean.beanpoll.util.HashUtility;
import is.stma.judgebean.beanpoll.util.PasswordUtility;

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
        } else if (null != team) {
            return team.getDisplayName() + ": " + name;
        }
        return name;
    }

    public String getLook() {
        if (admin) {
            return "danger";
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
