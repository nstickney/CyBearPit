package is.stma.judgebean.beanpoll.test;

import is.stma.judgebean.beanpoll.util.DNSUtility;
import is.stma.judgebean.beanpoll.util.EMProducer;
import is.stma.judgebean.beanpoll.util.HTTPUtility;
import is.stma.judgebean.beanpoll.util.LogProducer;
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

import java.io.File;

@RunWith(Arquillian.class)
public class HTTPUtilityTest {

    @Deployment
    public static Archive<?> createTestArchive() {

        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class, "testDNSUtility.war")
                .addClasses(HTTPUtility.class, EMProducer.class, LogProducer.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml") // Deploy test datasource
                .addAsLibraries(files); // Add necessary stuff from pom.xml
    }

    @Test
    public void testGetRequest() {
        String response = HTTPUtility.get("https://www.baylor.edu");
        Assert.assertTrue(response.contains("Baylor"));
    }

    @Test
    public void testAnotherGetRequest() {
        String response = HTTPUtility.get("http://httpbin.org");
        Assert.assertTrue(response.contains("BONUSPOINTS"));
    }
}
