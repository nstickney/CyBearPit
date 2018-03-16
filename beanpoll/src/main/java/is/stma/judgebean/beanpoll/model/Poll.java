package is.stma.judgebean.beanpoll.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Poll extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private Resource resource;

    @Column
    private int score = 0;

    @Column
    private LocalDate timestamp = LocalDate.now();

    @Lob
    @Column
    private String information;

    @Override
    public String getName() {
        return getId();
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int total) {
        this.score = total;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
