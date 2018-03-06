package is.stma.judgebean.test;

import is.stma.judgebean.model.AEntity;
import is.stma.judgebean.model.poll.IPoll;
import is.stma.judgebean.model.poll.PollDNS;
import is.stma.judgebean.model.scoreable.IScoreable;
import is.stma.judgebean.model.scoreable.ScoreableDNS;
import is.stma.judgebean.util.DNSUtility;
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

import javax.inject.Inject;
import java.io.File;
import java.util.logging.Logger;

@RunWith(Arquillian.class)
public class DNSUtilityTest {

    @Deployment
    public static Archive<?> createTestArchive() {

        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class, "pollDNSTest.war")
                .addClasses(AEntity.class, DNSUtility.class, Resources.class,
                        IScoreable.class, ScoreableDNS.class,
                        IPoll.class, PollDNS.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml") // Deploy test datasource
                .addAsLibraries(files); // Add necessary stuff from pom.xml
    }

    private ScoreableDNS scoreable;

    @Before
    public void setUp() {
        scoreable = new ScoreableDNS();
        scoreable.setHostAddress("9.9.9.9");
        scoreable.setQuery("baylor.edu");
    }

    private String check(ScoreableDNS s) {
        PollDNS poll = s.createPoll();
        poll.doPoll();
        return poll.getPollOutput();
    }

    @Test
    public void testForwardLookup() {
        Assert.assertTrue(check(scoreable).contains("129.62.3.230"));
    }

    @Test
    public void testReverseLookup() {
        scoreable.setQuery("69.171.239.12");
        scoreable.setType(Type.PTR);
        Assert.assertTrue(check(scoreable).contains("a.ns.facebook.com"));
    }

    @Test
    public void testTxtLookup() {
        scoreable.setQuery("baylor.edu");
        scoreable.setType(Type.TXT);
        // TODO: This test is terrible; need a server/record pair that responds
        // with exactly one TXT record to properly check.
        Assert.assertTrue(check(scoreable).length() > 0);
    }

    @Test
    public void testNoResults() {
        scoreable.setQuery("baylor.ccdc");
        Assert.assertEquals("ERROR: host not found", check(scoreable));
    }

    @Test
    public void testNoSuchIP() {
        scoreable.setHostAddress("129.62.148.39");
        Assert.assertEquals("ERROR: network error", check(scoreable));
    }

    @Test
    public void testBadIP() {
        scoreable.setHostAddress("256.0.0.1");
        Assert.assertEquals("ERROR: resolution failed", check(scoreable));
    }

    @Test
    public void testBadHostname() {
        scoreable.setHostAddress("ns1.baylor.ccdc");
        Assert.assertEquals("ERROR: resolution failed", check(scoreable));
    }
}
