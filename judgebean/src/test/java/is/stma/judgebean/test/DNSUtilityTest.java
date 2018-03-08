package is.stma.judgebean.test;

import is.stma.judgebean.model.AbstractEntity;
import is.stma.judgebean.model.poll._Poll;
import is.stma.judgebean.model.poll.DNSPoll;
import is.stma.judgebean.model.scoring._Scoring;
import is.stma.judgebean.model.scoring.ScoringDNS;
import is.stma.judgebean.util.DNSUtility;
import is.stma.judgebean.util.EMProducer;
import is.stma.judgebean.util.Resources;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xbill.DNS.Type;

import java.io.File;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Arquillian.class)
public class DNSUtilityTest {

    @Deployment
    public static Archive<?> createTestArchive() {

        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class, "pollDNSTest.war")
                .addClasses(AbstractEntity.class, DNSUtility.class, EMProducer.class, Resources.class,
                        _Scoring.class, ScoringDNS.class,
                        _Poll.class, DNSPoll.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml") // Deploy test datasource
                .addAsLibraries(files); // Add necessary stuff from pom.xml
    }

    private ScoringDNS dns;

    @Before
    public void setUp() {
        dns = new ScoringDNS();
        dns.setHostAddress("9.9.9.9");
        dns.setQuery("baylor.edu");
    }

    private String check(ScoringDNS s) {
        DNSPoll poll = s.createPoll();
        poll.doPoll();
        return poll.getPollOutput();
    }

    @Test
    public void testARecordLookup() {
        Assert.assertEquals(dns.getQuery() + "./129.62.3.230", check(dns));
    }

    @Test
    public void testPTRRecordLookup() {
        dns.setQuery("69.171.239.12");
        dns.setType(Type.PTR);
        Assert.assertEquals("a.ns.facebook.com.", check(dns));
    }

    @Test
    public void testTXTRecordLookup() {
        dns.setQuery("baylor.edu");
        dns.setType(Type.TXT);
        // TODO: This test is terrible; need a server/record pair that responds
        // with exactly one TXT record to properly check.
        Assert.assertTrue(check(dns).length() > 0);
    }

    @Test
    public void testNoResults() {
        dns.setQuery("baylor.ccdc");
        Assert.assertThat(check(dns), anyOf(equalTo("ERROR: host not found"), equalTo("ERROR: type not found")));
    }

    @Test
    public void testNoSuchIP() {
        dns.setHostAddress("129.62.148.39");
        Assert.assertThat(check(dns), anyOf(equalTo("ERROR: network error"), equalTo("ERROR: timed out")));
    }

    @Test
    public void testBadIP() {
        dns.setHostAddress("256.0.0.1");
        Assert.assertEquals("ERROR: resolution failed", check(dns));
    }

    @Test
    public void testBadHostname() {
        dns.setHostAddress("ns1.baylor.ccdc");
        Assert.assertEquals("ERROR: resolution failed", check(dns));
    }
}
