package is.stma.judgebean.model.scoreable;

import is.stma.judgebean.model.AEntity;
import is.stma.judgebean.model.poll.HTTPPoll;
import org.xbill.DNS.Type;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class HTTPScorer extends AEntity implements IScorer {

    private final static int DEFAULT_HTTP_TIMEOUT = 3;

    /* Overrides ------------------------------------------------------------ */
    @Override
    public String getName() {
        return name;
    }

    @Override
    public HTTPPoll createPoll() {
        return new HTTPPoll(this);
    }

    /* Fields --------------------------------------------------------------- */
    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String dnsAddress;

    @Column(nullable = false)
    private String hostAddress;

    @Column(nullable = false)
    private int hostPort = 80;

    @Column(nullable = false)
    private String query;

    @Column(nullable = false)
    private String expected;

    @Column(nullable = false)
    private boolean followRedirects = true;

    @Column(nullable = false)
    private int timeout = DEFAULT_HTTP_TIMEOUT;

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

    public boolean isFollowRedirects() {
        return followRedirects;
    }

    public void setFollowRedirects(boolean followRedirects) {
        this.followRedirects = followRedirects;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
    /* Methods -------------------------------------------------------------- */
}
