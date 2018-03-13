package is.stma.judgebean.beanpoll.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Resource extends AbstractEntity implements Comparable {

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

    @OneToMany(mappedBy = "resource")
    private List<ResourceParameter> parameters = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Contest contest;

    @ManyToMany
    @JoinTable(name = "ResourceTeams",
            joinColumns = {@JoinColumn(name = "resource_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "team_id", referencedColumnName = "id")})
    private List<Team> teams = new ArrayList<>();

    @OneToMany(mappedBy = "resource")
    private List<Points> points = new ArrayList<>();

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        String displayName = name + " (" + type + ")";
        if (null != contest) {
            displayName = contest.getName() + ": " + displayName;
        }
        return displayName;
    }

    public String getLook() {
        if (null == contest) {
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

    public List<Points> getPoints() {
        return points;
    }

    public void setPoints(List<Points> points) {
        this.points = points;
    }

    @Override
    public int compareTo(Object o) {

        // Typecast the compared object to a Resource
        Resource r = (Resource) o;

        // If both are assigned to Contests, decide based on Contest name
        if (null != r.getContest() && null != this.contest) {
            int contestNameComparison = this.contest.getName().compareTo(r.getContest().getName());

            // If the Contests have the same name, decide based on Resource name
            if (0 == contestNameComparison) {
                return this.name.compareTo(r.getName());
            }
            return contestNameComparison;
        }

        // If neither is assigned to a Contest, decide based on Resource name
        if (null == r.getContest() && null == this.contest) {
            return this.name.compareTo(r.getName());
        }

        // If this is assigned to a Contest, r is the lessor
        if (null != this.contest) {
            return 1;
        }

        return -1;
    }
}
