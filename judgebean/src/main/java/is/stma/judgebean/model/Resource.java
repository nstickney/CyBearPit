package is.stma.judgebean.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Resource extends AbstractEntity {

    public static final String DNS = "DNS";
    public static final String HTTP = "HTTP";

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String tag = DNS;

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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
