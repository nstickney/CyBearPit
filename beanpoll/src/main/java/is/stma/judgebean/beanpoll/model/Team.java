package is.stma.judgebean.beanpoll.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team extends ComparableByContest {

    @Column(nullable = false)
    private String name;

    @Column
    private String flag;

    @ManyToOne
    private Contest contest;

    @OneToMany(mappedBy = "team")
    private List<User> users;

    @ManyToMany(mappedBy = "teams")
    private List<Resource> resources = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<TaskResponse> taskResponses = new ArrayList<>();

    @OneToMany(mappedBy = "team")
    private List<Points> points = new ArrayList<>();

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public List<TaskResponse> getTaskResponses() {
        return taskResponses;
    }

    public void setTaskResponses(List<TaskResponse> taskResponses) {
        this.taskResponses = taskResponses;
    }

    public List<Points> getPoints() {
        return points;
    }

    public void setPoints(List<Points> points) {
        this.points = points;
    }

    public int getScore() {
        int score = 0;
        for (Points p : points) {
            score += p.getScore();
        }
        return score;
    }

    @Override
    public int compareTo(Object o) {
        return compare(this, (ComparableByContest) o);
    }
}
