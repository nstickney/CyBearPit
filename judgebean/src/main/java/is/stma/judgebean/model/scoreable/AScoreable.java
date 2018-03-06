package is.stma.judgebean.model.scoreable;

import is.stma.judgebean.model.AEntity;
import is.stma.judgebean.model.Contest;
import is.stma.judgebean.model.poll.APoll;

import javax.persistence.*;

public abstract class AScoreable<P extends APoll> extends AEntity {

    /* Abstract Methods ----------------------------------------------------- */
    public abstract P createPoll();

    /* Overrides ------------------------------------------------------------ */
    @Override
    public String getName() {
        return name;
    }

    /* Fields --------------------------------------------------------------- */
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id")
    private Contest contest;

    /* Getters and Setters -------------------------------------------------- */
    public void setName(String name) {
        this.name = name;
    }

    /* Methods -------------------------------------------------------------- */
}
