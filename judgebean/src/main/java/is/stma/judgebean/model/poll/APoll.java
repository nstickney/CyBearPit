package is.stma.judgebean.model.poll;

import is.stma.judgebean.model.AEntity;
import is.stma.judgebean.model.scoreable.AScoreable;
import is.stma.judgebean.model.PollResult;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

public abstract class APoll extends AEntity {

    /* Abstract Methods ----------------------------------------------------- */
    public abstract int doPoll();

    /* Overrides ------------------------------------------------------------ */
    @Override
    public String getName() {
        return scoreable.getName() + ": " + getId();
    }

    /* Fields --------------------------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id")
    private AScoreable scoreable;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result_id")
    private PollResult result;

    /* Getters and Setters -------------------------------------------------- */
    public AScoreable getScoreable() {
        return scoreable;
    }

    void setScoreable(AScoreable scoreable) {
        this.scoreable = scoreable;
    }

    public PollResult getResult() {
        return result;
    }

    /* Methods -------------------------------------------------------------- */
}
