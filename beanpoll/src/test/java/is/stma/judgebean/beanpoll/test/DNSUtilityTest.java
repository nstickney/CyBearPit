package is.stma.judgebean.beanpoll.test;

import is.stma.judgebean.beanpoll.model.AbstractEntity;
import is.stma.judgebean.beanpoll.util.DNSUtility;
import is.stma.judgebean.beanpoll.util.EMProducer;
import is.stma.judgebean.beanpoll.util.LogProducer;
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

import static is.stma.judgebean.beanpoll.util.DNSUtility.lookup;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(Arquillian.class)
public class DNSUtilityTest {

    private String query = "baylor.edu";

    @Deployment
    public static Archive<?> createTestArchive() {

        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class, "testDNSUtility.war")
                .addClasses(AbstractEntity.class, DNSUtility.class, EMProducer.class, LogProducer.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml") // Deploy test datasource
                .addAsLibraries(files); // Add necessary stuff from pom.xml
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testARecordLookup() {
        Assert.assertEquals(query + "./129.62.3.230", DNSUtility.lookup(query));
    }

    @Test
    public void testPTRRecordLookup() {
        Assert.assertEquals("a.ns.facebook.com.",
                DNSUtility.lookup("69.171.239.12", Type.PTR));
    }

    @Test
    public void testTXTRecordLookup() {
        // TODO: This test is terrible; need a server/record pair that responds
        // with exactly one TXT record to properly check.
        Assert.assertTrue(DNSUtility.lookup("baylor.edy", Type.TXT).length() > 0);
    }

    @Test
    public void testNoResults() {
        Assert.assertThat(DNSUtility.lookup("baylor.ccdc"), anyOf(equalTo("ERROR: host not found"), equalTo("ERROR: type not found")));
    }

    @Test
    public void testNoSuchIP() {
        Assert.assertThat(DNSUtility.lookup("129.62.148.39", query),
                anyOf(equalTo("ERROR: network error"), equalTo("ERROR: timed out")));
    }

    @Test
    public void testBadIP() {
        Assert.assertEquals("ERROR: resolution failed",
                DNSUtility.lookup("256.0.0.1", query));
    }

    @Test
    public void testBadHostname() {
        Assert.assertEquals("ERROR: resolution failed",
                DNSUtility.lookup("ns1.baylor.ccdc", query));
    }
}
