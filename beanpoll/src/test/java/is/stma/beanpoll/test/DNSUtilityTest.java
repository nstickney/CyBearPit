/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.beanpoll.test;

import is.stma.beanpoll.model.AbstractEntity;
import is.stma.beanpoll.util.DNSUtility;
import is.stma.beanpoll.util.EMProducer;
import is.stma.beanpoll.util.LogProducer;
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
import org.xbill.DNS.Type;

import java.io.File;

@RunWith(Arquillian.class)
public class DNSUtilityTest {

    private static final String query = "baylor.edu";

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
        Assert.assertTrue(DNSUtility.lookup("baylor.ccdc").startsWith("ERROR"));
    }

    @Test
    public void testNoSuchIP() {
        Assert.assertTrue(DNSUtility.lookup("129.62.148.39", query).startsWith("ERROR"));
    }

    @Test
    public void testBadIP() {
        Assert.assertTrue("ERROR: resolution failed",
                DNSUtility.lookup("256.0.0.1", query).startsWith("ERROR: resolution failed"));
    }

    @Test
    public void testBadHostname() {
        Assert.assertTrue(DNSUtility.lookup("ns1.baylor.ccdc", query).startsWith("ERROR: resolution failed"));
    }
}
