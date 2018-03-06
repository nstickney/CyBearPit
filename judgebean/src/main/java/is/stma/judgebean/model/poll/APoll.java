package is.stma.judgebean.model.poll;

import is.stma.judgebean.model.AEntity;
import is.stma.judgebean.model.scoreable.AScoreable;
import is.stma.judgebean.model.PollResult;

import javax.persistence.*;

public abstract class APoll<S extends AScoreable> extends AEntity {

    /* Abstract Methods ----------------------------------------------------- */
    public abstract void doPoll();

    /* Overrides ------------------------------------------------------------ */
    @Override
    public String getName() {
        return scoreable.getName() + ": " + getId();
    }

    /* Fields --------------------------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id")
    private S scoreable;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result_id")
    private PollResult result;

    @Column
    private String pollOutput = "";

    /* Getters and Setters -------------------------------------------------- */
    public S getScoreable() {
        return scoreable;
    }

    void setScoreable(S scoreable) {
        this.scoreable = scoreable;
    }

    public PollResult getResult() {
        return result;
    }

    void setResult(PollResult result) {
        this.result = result;
    }

    public String getPollOutput() {
        return pollOutput;
    }
    /* Methods -------------------------------------------------------------- */
    void appendOutput(String toAppend) {
        pollOutput += toAppend;
    }
}
