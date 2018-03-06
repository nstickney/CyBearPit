package is.stma.judgebean.model.scoreable;

import is.stma.judgebean.model.poll.APoll;
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
    private String expectedAnswer;

    @Column(nullable = false)
    private boolean checkUCP = true;

    @Column(nullable = false)
    private boolean checkTCP = false;

    @Column(nullable = false)
    private boolean checkForward = true;

    @Column(nullable = false)
    private boolean checkReverse = false;

    @Column(nullable = false)
    private boolean checkRecursive = false;

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

    public String getExpectedAnswer() {
        return expectedAnswer;
    }

    public void setExpectedAnswer(String expectedAnswer) {
        this.expectedAnswer = expectedAnswer;
    }

    /* Methods -------------------------------------------------------------- */
}
