package is.stma.judgebean.model.poll;

import is.stma.judgebean.model.scoreable.ScoreableDNS;

import static is.stma.judgebean.util.DNSUtility.forwardLookup;

public class PollDNS extends APoll<ScoreableDNS> {

    /* Overrides ------------------------------------------------------------ */
    @Override
    public void doPoll() {
        String response = forwardLookup(getScoreable().getHostAddress(),
                getScoreable().getHostPort(),
                getScoreable().getDnsQuery());
        appendOutput(response);
    }

    /* Fields --------------------------------------------------------------- */

    /* Getters and Setters -------------------------------------------------- */

    /* Methods -------------------------------------------------------------- */
    public PollDNS(ScoreableDNS dns) {
        this.setScoreable(dns);
    }
}
