package is.stma.judgebean.model;

import javax.enterprise.inject.Model;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Model
public class Team extends AEntity {

    /* Fields --------------------------------------------------------------- */
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id")
    private Contest contest;

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
