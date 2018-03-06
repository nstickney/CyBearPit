package is.stma.judgebean.model.poll;

import is.stma.judgebean.model.AEntity;
import is.stma.judgebean.model.scoreable.ScoreableDNS;

import javax.persistence.*;

import static is.stma.judgebean.util.DNSUtility.forwardLookup;

public class PollDNS extends AEntity implements IPoll {

    /* Overrides ------------------------------------------------------------ */
    @Override
    public String getName() {
        return scoreable.getName() + ": " + getId();
    }

    @Override
    public void doPoll() {
        String response = forwardLookup(getScoreable().getHostAddress(),
                getScoreable().getHostPort(),
                getScoreable().getQuery(),
                getScoreable().isTcp(),
                getScoreable().getType(),
                getScoreable().isRecursive());
        pollOutput += response;
    }

    /* Fields --------------------------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id")
    private ScoreableDNS scoreable;

    @Column
    private String pollOutput = "";

    /* Getters and Setters -------------------------------------------------- */
    public ScoreableDNS getScoreable() {
        return scoreable;
    }

    void setScoreable(ScoreableDNS scoreable) {
        this.scoreable = scoreable;
    }

    public String getPollOutput() {
        return pollOutput;
    }

    /* Methods -------------------------------------------------------------- */
    public PollDNS(ScoreableDNS dns) {
        this.scoreable = dns;
    }
}
