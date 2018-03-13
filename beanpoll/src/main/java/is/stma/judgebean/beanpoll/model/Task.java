package is.stma.judgebean.beanpoll.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Task extends AbstractEntity implements Comparable {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int pointValue;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    private Contest contest;

    @OneToMany(mappedBy = "task")
    private List<TaskResponse> responses = new ArrayList<>();

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        if (null != contest) {
            return contest.getName() + ": " + name;
        }
        return name;
    }

    public String getLook() {
        if (null == contest) {
            return "warning";
        }
        return "default";
    }

    public int getPointValue() {
        return pointValue;
    }

    public void setPointValue(int pointValue) {
        this.pointValue = pointValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public List<TaskResponse> getResponses() {
        return responses;
    }

    public void setResponses(List<TaskResponse> responses) {
        this.responses = responses;
    }

    @Override
    public int compareTo(Object o) {

        // Typecast the compared object to a Resource
        Task t = (Task) o;

        // If both are assigned to Contests, decide based on Contest name
        if (null != t.getContest() && null != this.contest) {
            int contestNameComparison = this.contest.getName().compareTo(t.getContest().getName());

            // If the Contests have the same name, decide based on Resource name
            if (0 == contestNameComparison) {
                return this.name.compareTo(t.getName());
            }
            return contestNameComparison;
        }

        // If neither is assigned to a Contest, decide based on Resource name
        if (null == t.getContest() && null == this.contest) {
            return this.name.compareTo(t.getName());
        }

        // If this is assigned to a Contest, r is the lessor
        if (null != this.contest) {
            return 1;
        }

        return -1;
    }
}
