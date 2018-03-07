package is.stma.judgebean.model.poll;

import is.stma.judgebean.model.AEntity;
import is.stma.judgebean.model.scoreable.DNSScorer;
import is.stma.judgebean.model.scoreable.HTTPScorer;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class HTTPPoll extends AEntity implements IPoll {

    /* Overrides ------------------------------------------------------------ */
    @Override
    public String getName() {
        return http.getName() + ": " + getId();
    }

    @Override
    public void doPoll() {
    }

    /* Fields --------------------------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id")
    private HTTPScorer http;

    @Column
    private String pollOutput = "";

    /* Getters and Setters -------------------------------------------------- */
    public HTTPScorer getHttp() {
        return http;
    }

    void setHttp(HTTPScorer http) {
        this.http = http;
    }

    public String getPollOutput() {
        return pollOutput;
    }

    /* Methods -------------------------------------------------------------- */
    public HTTPPoll(HTTPScorer http) {
        this.http = http;
    }
}
