package is.stma.judgebean.model.poll;

import is.stma.judgebean.model.AEntity;
import is.stma.judgebean.model.scoreable.DNSScorer;

import javax.persistence.*;

import static is.stma.judgebean.util.DNSUtility.forwardLookup;

public class DNSPoll extends AEntity implements _Poll {

    /* Overrides ------------------------------------------------------------ */
    @Override
    public String getName() {
        return dns.getName() + ": " + getId();
    }

    @Override
    public void doPoll() {
        String response = forwardLookup(getDns().getHostAddress(), getDns().getHostPort(),
                getDns().getQuery(), getDns().isTcp(), getDns().getType(), getDns().isRecursive(),
                getDns().getTimeout());
        pollOutput += response;

    }

    /* Fields --------------------------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id")
    private DNSScorer dns;

    @Column
    private String pollOutput = "";

    /* Getters and Setters -------------------------------------------------- */
    public DNSScorer getDns() {
        return dns;
    }

    void setDns(DNSScorer dns) {
        this.dns = dns;
    }

    public String getPollOutput() {
        return pollOutput;
    }

    /* Methods -------------------------------------------------------------- */
    public DNSPoll(DNSScorer dns) {
        this.dns = dns;
    }
}
