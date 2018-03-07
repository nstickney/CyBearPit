package is.stma.judgebean.model.scoring;

import is.stma.judgebean.model.AEntity;
import is.stma.judgebean.model.poll.DNSPoll;
import org.xbill.DNS.Type;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ScoringDNS extends AEntity implements _Scoring {

    private final static int DEFAULT_DNS_TIMEOUT = 3;

    /* Overrides ------------------------------------------------------------ */
    @Override
    public String getName() {
        return name;
    }

    @Override
    public DNSPoll createPoll() {
        return new DNSPoll(this);
    }

    /* Fields --------------------------------------------------------------- */
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String hostAddress;

    @Column(nullable = false)
    private int hostPort = 53;

    @Column(nullable = false)
    private String query;

    @Column(nullable = false)
    private String expected;

    @Column(nullable = false)
    private boolean tcp = false;

    @Column(nullable = false)
    private int type = Type.A;

    @Column(nullable = false)
    private boolean recursive = false;

    @Column(nullable = false)
    private int timeout = DEFAULT_DNS_TIMEOUT;

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

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getExpected() {
        return expected;
    }
    public void setExpected(String expected) {
        this.expected = expected;
    }

    public boolean isTcp() {
        return tcp;
    }

    public void setTcp(boolean tcp) {
        this.tcp = tcp;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isRecursive() {
        return recursive;
    }

    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
    /* Methods -------------------------------------------------------------- */
}
