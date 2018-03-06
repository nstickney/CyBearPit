package is.stma.judgebean.model.poll;

import is.stma.judgebean.model.scoreable.ScoreableDNS;

public class PollDNS extends APoll {

    /* Overrides ------------------------------------------------------------ */
    @Override
    public int doPoll() {
        return 0;
    }

    /* Fields --------------------------------------------------------------- */

    /* Getters and Setters -------------------------------------------------- */

    /* Methods -------------------------------------------------------------- */
    public PollDNS(ScoreableDNS dns) {
        this.setScoreable(dns);
    }
}
