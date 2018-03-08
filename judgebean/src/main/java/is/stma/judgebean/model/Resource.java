package is.stma.judgebean.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Resource extends AbstractEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "resources", cascade = CascadeType.ALL)
    private List<Team> teams = new ArrayList<>();

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
