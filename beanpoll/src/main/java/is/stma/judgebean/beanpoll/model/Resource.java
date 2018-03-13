package is.stma.judgebean.beanpoll.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Resource extends ComparableByContest {

    public static final String DNS = "DNS";
    public static final String HTTP = "HTTP";

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String type = DNS;

    @Column(nullable = false)
    private String address = "baylor.edu";

    @Column(nullable = false)
    private int port = 53;

    @Column(nullable = false)
    private boolean scoring = false;

    @OneToMany(mappedBy = "resource")
    private List<ResourceParameter> parameters = new ArrayList<>();

    @ManyToOne
    private Contest contest;

    @ManyToMany
    @JoinTable(name = "ResourceTeams",
            joinColumns = {@JoinColumn(name = "resource_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "team_id", referencedColumnName = "id")})
    private List<Team> teams = new ArrayList<>();

    @OneToMany(mappedBy = "resource")
    private List<Poll> points = new ArrayList<>();

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getLook() {
        if (scoring) {
            if (null == contest || !contest.isRunning()) {
                return "warning";
            }
            return "success";
        } else if (null != contest && contest.isRunning()) {
            return "warning";
        }
        return "default";
    }

    public String getType() {
        return type;
    }

    public void setType(String tag) {
        this.type = tag;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isScoring() {
        return scoring;
    }

    public void setScoring(boolean scoring) {
        this.scoring = scoring;
    }

    public List<ResourceParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<ResourceParameter> parameters) {
        this.parameters = parameters;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public void addTeam(Team team) {
        if (null != team) {
            teams.add(team);
        }
    }

    public void removeTeam(Team team) {
        if (null != team && teams.contains(team)) {
            teams.remove(team);
        }
    }

    public List<Poll> getPoints() {
        return points;
    }

    public void setPoints(List<Poll> points) {
        this.points = points;
    }

    @Override
    public int compareTo(ComparableByContest o) {
        return compare(this, (ComparableByContest) o);
    }
}
