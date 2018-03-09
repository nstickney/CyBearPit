package is.stma.judgebean.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Resource extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Contest contest;

    @OneToMany(mappedBy = "resource")
    private List<ResourceParameter> parameters = new ArrayList<>();

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public List<ResourceParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<ResourceParameter> parameters) {
        this.parameters = parameters;
    }
}
