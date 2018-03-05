package is.stma.judgebean.model;

import javax.enterprise.inject.Model;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Model
public class Contest extends AEntity {

    /* Fields --------------------------------------------------------------- */
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "contest")
    private List<Team> teams = new ArrayList<>();

    @OneToMany(mappedBy = "contest")
    private List<Scoreable> scoreables = new ArrayList<>();

    /* Overrides ------------------------------------------------------------ */
    @Override
    public String getName() {
        return name;
    }

    /* Getters and Setters -------------------------------------------------- */
    public void setName(String name) {
        this.name = name;
    }

    /* Methods -------------------------------------------------------------- */
}
