package is.stma.judgebean.beanpoll.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Contest extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private boolean running = false;

    @OneToMany(mappedBy = "contest")
    private List<Resource> resources = new ArrayList<>();

    @OneToMany(mappedBy = "contest")
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "contest")
    private List<Team> teams = new ArrayList<>();

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLook() {
        if (running) {
            return "success";
        }
        return "default";
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
