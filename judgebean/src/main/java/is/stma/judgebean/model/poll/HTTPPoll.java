package is.stma.judgebean.model.poll;

import is.stma.judgebean.model.AEntity;
import is.stma.judgebean.model.scoring.ScoringHTTP;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class HTTPPoll extends AEntity implements _Poll {

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
    private ScoringHTTP http;

    @Column
    private String pollOutput = "";

    /* Getters and Setters -------------------------------------------------- */
    public ScoringHTTP getHttp() {
        return http;
    }

    void setHttp(ScoringHTTP http) {
        this.http = http;
    }

    public String getPollOutput() {
        return pollOutput;
    }

    /* Methods -------------------------------------------------------------- */
    public HTTPPoll(ScoringHTTP http) {
        this.http = http;
    }
}
