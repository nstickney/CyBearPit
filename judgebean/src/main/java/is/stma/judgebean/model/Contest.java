package is.stma.judgebean.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Contest extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    public ContestState state = ContestState.STOPPED;

    @OneToMany(mappedBy = "contest")
    private List<Resource> resources = new ArrayList<>();

    @OneToMany(mappedBy = "contest")
    private List<Team> teams = new ArrayList<>();

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public enum ContestState {
        RUNNING, STOPPED
    }
}
