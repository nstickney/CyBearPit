package is.stma.judgebean.beanpoll.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Task extends ComparableByContest {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int pointValue;

    @Column
    private String description;

    @Column
    private LocalDateTime available;

    @Column
    private LocalDateTime expiration;

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

    public LocalDateTime getAvailable() {
        return available;
    }

    public void setAvailable(LocalDateTime available) {
        this.available = available;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
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
    public int compareTo(ComparableByContest o) {
        return compare(this, o);
    }
}
