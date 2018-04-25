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

import is.stma.beanpoll.data.ContestRepo;
import is.stma.beanpoll.model.Contest;
import is.stma.beanpoll.rules.ContestRules;
import is.stma.beanpoll.service.ContestService;
import is.stma.beanpoll.util.EMProducer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJBException;
import javax.inject.Inject;
import java.io.File;

@RunWith(Arquillian.class)
public class ContestTest {

    @Inject
    private ContestService contestService;

    private Contest testContest;
    private Contest checkContest;

    /**
     * Create a web archive (WAR) for deployment via Arquillian
     *
     * @return the web archive
     */
    @Deployment
    public static WebArchive createDeployment() {
        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class, "contestTest.war")
                .addPackages(true, Contest.class.getPackage(),
                        ContestRepo.class.getPackage(),
                        ContestService.class.getPackage(),
                        ContestRules.class.getPackage(),
                        EMProducer.class.getPackage())
                .addClass(TestUtility.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("META-INF/apache-deltaspike.properties")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml") // Deploy test datasource
                .addAsLibraries(files); // Add necessary stuff from pom.xml
    }

    /**
     * Create a test contest and persist it
     */
    @Before
    public void setUp() {
        if (null == testContest) {
            testContest = TestUtility.makeContest();
            contestService.create(testContest);
        }
    }

    @Test
    public void testContestCreation() {
        checkContest = contestService.readById(testContest.getId());
        Assert.assertTrue(testContest.equalByUUID(checkContest));
        Assert.assertEquals(testContest, checkContest);
    }

    @Test
    public void testContestUpdate() {
        testContest.setName("UPDATED");
        contestService.update(testContest);
        String UUID = testContest.getId();
        testContest = null;
        testContest = contestService.readById(UUID);
        Assert.assertEquals("UPDATED", testContest.getName());
    }

    @Test
    public void testContestDeletion() {
        contestService.delete(testContest);
        String UUID = testContest.getId();
        testContest = null;
        testContest = contestService.readById(UUID);
        Assert.assertNull(testContest);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testContestUpdateNonexistent() {
        checkContest = TestUtility.makeContest();
        checkContest.setName("Check Contest");
        contestService.update(checkContest);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testContestNonUniqueUUID() {
        checkContest = testContest;
        checkContest.setName("We Copied You!");
        contestService.create(checkContest);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testContestNonUniqueName() {
        checkContest = TestUtility.makeContest();
        checkContest.setName(testContest.getName());
        contestService.create(checkContest);
    }
}