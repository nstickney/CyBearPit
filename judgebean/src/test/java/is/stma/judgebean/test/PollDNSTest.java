package is.stma.judgebean.test;

import is.stma.judgebean.model.AEntity;
import is.stma.judgebean.model.poll.APoll;
import is.stma.judgebean.model.poll.PollDNS;
import is.stma.judgebean.model.scoreable.AScoreable;
import is.stma.judgebean.model.scoreable.ScoreableDNS;
import is.stma.judgebean.util.DNSUtil;
import is.stma.judgebean.util.Resources;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.io.File;
import java.util.logging.Logger;

@RunWith(Arquillian.class)
public class PollDNSTest {
    @Deployment
    public static Archive<?> createTestArchive() {
        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class, "pollDNSTest.war")
                .addClasses(AEntity.class, DNSUtil.class, Resources.class,
                        AScoreable.class, ScoreableDNS.class,
                        APoll.class, PollDNS.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml") // Deploy test datasource
                .addAsLibraries(files); // Add necessary stuff from pom.xml
    }

    @Inject
    private Logger log;

    @Test
    public void testForwardLookup() {
        ScoreableDNS scoreable = new ScoreableDNS();
        scoreable.setHostAddress("ns1.baylor.edu");
        scoreable.setDnsQuery("baylor.edu");
        PollDNS poll = scoreable.createPoll();
        poll.doPoll();
        Assert.assertEquals("129.62.3.230", poll.getPollOutput());
    }
}
