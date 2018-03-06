package is.stma.judgebean.model.poll;

import is.stma.judgebean.model.scoreable.ScoreableDNS;

import static is.stma.judgebean.util.DNSUtil.forwardLookup;

public class PollDNS extends APoll<ScoreableDNS> {

    /* Overrides ------------------------------------------------------------ */
    @Override
    public void doPoll() {
        System.out.println("HOST = " + getScoreable().getHostAddress() + ":" + getScoreable().getHostPort());
        System.out.println("QUERY = " + getScoreable().getDnsQuery());
        String response = forwardLookup(getScoreable().getHostAddress(),
                getScoreable().getHostPort(),
                getScoreable().getDnsQuery());
        System.out.println("RESPONSE = " + response);
        appendOutput(response);
    }

    /* Fields --------------------------------------------------------------- */

    /* Getters and Setters -------------------------------------------------- */

    /* Methods -------------------------------------------------------------- */
    public PollDNS(ScoreableDNS dns) {
        this.setScoreable(dns);
    }
}
