package is.stma.judgebean.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(unique = true)
    private String flag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id")
    private Contest contest;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "team_resources",
            joinColumns = {@JoinColumn(name = "resource_id",
                    referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "team_id",
                    referencedColumnName = "id")})
    private List<Resource> resources = new ArrayList<>();

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

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

}
