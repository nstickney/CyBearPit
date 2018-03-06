package is.stma.judgebean.model.scoreable;

import is.stma.judgebean.model.poll.PollDNS;

import javax.enterprise.inject.Model;
import javax.persistence.Column;

@Model
public class ScoreableDNS extends AScoreable<PollDNS> {

    /* Overrides ------------------------------------------------------------ */
    @Override
    public PollDNS createPoll() {
        return new PollDNS(this);
    }

    /* Fields --------------------------------------------------------------- */
    @Column(nullable = false)
    private String hostAddress;

    @Column(nullable = false)
    private int hostPort = 53;

    @Column(nullable = false)
    private String dnsQuery;

    @Column(nullable = false)
    private String expected;

    @Column(nullable = false)
    private boolean reverseCheck = false;

    @Column(nullable = false)
    private boolean recursiveCheck = false;

    /* Getters and Setters -------------------------------------------------- */
    public String getHostAddress() {
        return hostAddress;
    }

    public void setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
    }

    public int getHostPort() {
        return hostPort;
    }

    public void setHostPort(int hostPort) {
        this.hostPort = hostPort;
    }

    public String getDnsQuery() {
        return dnsQuery;
    }

    public void setDnsQuery(String dnsQuery) {
        this.dnsQuery = dnsQuery;
    }

    public String getExpected() {
        return expected;
    }
    public void setExpected(String expected) {
        this.expected = expected;
    }

    public boolean isReverseCheck() {
        return reverseCheck;
    }

    public void setReverseCheck(boolean reverseCheck) {
        this.reverseCheck = reverseCheck;
    }

    public boolean isRecursiveCheck() {
        return recursiveCheck;
    }

    public void setRecursiveCheck(boolean recursiveCheck) {
        this.recursiveCheck = recursiveCheck;
    }


    /* Methods -------------------------------------------------------------- */
}
