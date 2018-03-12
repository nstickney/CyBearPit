package is.stma.judgebean.beanpoll.model;

import is.stma.judgebean.beanpoll.util.HashUtility;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
public class User extends AbstractEntity {

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
}
