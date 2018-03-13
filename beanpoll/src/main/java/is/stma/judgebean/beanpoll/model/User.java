package is.stma.judgebean.beanpoll.model;

import is.stma.judgebean.beanpoll.util.HashUtility;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
public class User extends AbstractEntity implements Comparable {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String salt;

    @Column(nullable = false)
    private String secret;

    @Column(nullable = false)
    private boolean admin;

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

    public String getSalt() {
        return salt;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String password) {
        this.salt = UUID.randomUUID().toString();
        this.secret = HashUtility.getHash(salt + password);
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public boolean checkPassword(String password) {
        return HashUtility.checkHash(salt + password, secret);
    }

    public int compareTo(Object o) {

        User user = (User) o;

        // If both are admins, decide based on name
        if (this.isAdmin() && user.isAdmin()) {
            return this.getName().compareTo(user.getName());
        }

        // If one is an admin, but not the other, decide based on that
        if (this.isAdmin()) {
            return -1;
        } else if (user.isAdmin()) {
            return 1;
        }

        // If both are assigned to Teams, decide based on Team name
        if (null != this.getTeam() && null != user.getTeam()) {
            int contestNameComparison = this.getDisplayName().compareTo(user.getDisplayName());

            // If the Contests have the same name, decide based on Entity name
            if (0 == contestNameComparison) {
                return this.getName().compareTo(user.getName());
            }
            return contestNameComparison;
        }

        // If neither is assigned to a Contest, decide based on Entity name
        if (null == this.getTeam() && null == user.getTeam()) {
            return this.getName().compareTo(user.getName());
        }

        // Return whichever is not assigned to a Contest
        if (null == this.getName()) {
            return -1;
        }
        return 1;
    }
}
