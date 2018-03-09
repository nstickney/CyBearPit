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
    
    @ManyToOne
    private Contest contest;

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
