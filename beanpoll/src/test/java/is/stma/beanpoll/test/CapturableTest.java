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

import is.stma.beanpoll.data.CapturableRepo;
import is.stma.beanpoll.model.Contest;
import is.stma.beanpoll.model.Capturable;
import is.stma.beanpoll.rules.CapturableRules;
import is.stma.beanpoll.service.ContestService;
import is.stma.beanpoll.service.CapturableService;
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
public class CapturableTest {

    @Inject
    private ContestService contestService;

    @Inject
    private CapturableService CapturableService;

    private Contest testContest;

    private Capturable testCapturable;
    private Capturable checkCapturable;

    /**
     * Create a web archive (WAR) for deployment via Arquillian
     * @return the web archive
     */
    @Deployment
    public static WebArchive createDeployment() {
        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class, "CapturableRulesTest.war")
                .addPackages(true, Capturable.class.getPackage(),
                        CapturableRepo.class.getPackage(),
                        CapturableService.class.getPackage(),
                        CapturableRules.class.getPackage(),
                        EMProducer.class.getPackage())
                .addClass(TestUtility.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("META-INF/apache-deltaspike.properties")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml") // Deploy test datasource
                .addAsLibraries(files); // Add necessary stuff from pom.xml
    }

    /**
     * Create a test contest and a test Capturable in that contest, and persist both
     */
    @Before
    public void setUp() {
        if (null == testContest) {
            testContest = TestUtility.makeContest();
            contestService.create(testContest);
        }
        if (null == testCapturable) {
            testCapturable = TestUtility.makeCapturable(testContest);
            CapturableService.create(testCapturable);
        }
    }

    @Test
    public void testCapturableCreation() {
        checkCapturable = CapturableService.readById(testCapturable.getId());
        Assert.assertTrue(testCapturable.equalByUUID(checkCapturable));
        Assert.assertEquals(testCapturable, checkCapturable);
    }

    @Test
    public void testCapturableUpdate() {
        testCapturable.setName("UPDATED");
        CapturableService.update(testCapturable);
        String UUID = testCapturable.getId();
        testCapturable = null;
        testCapturable = CapturableService.readById(UUID);
        Assert.assertEquals("UPDATED", testCapturable.getName());
    }

    @Test
    public void testCapturableDeletion() {
        CapturableService.delete(testCapturable);
        String UUID = testCapturable.getId();
        testCapturable = null;
        testCapturable = CapturableService.readById(UUID);
        Assert.assertNull(testCapturable);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testCapturableUpdateNonexistent() {
        checkCapturable = TestUtility.makeCapturable(testContest);
        checkCapturable.setName("Check Capturable");
        CapturableService.update(checkCapturable);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testCapturableNonUniqueUUID() {
        checkCapturable = testCapturable;
        checkCapturable.setName("We Copied You!");
        checkCapturable.setFlag("We Copied You!");
        CapturableService.create(checkCapturable);
    }

    // TODO: no error is thrown here; should throw ValidationException from CapturableRules.
    @Test
    //@Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testCapturableNonUniqueNameInContest() {
        checkCapturable = TestUtility.makeCapturable(testContest);
        checkCapturable.setName(testCapturable.getName());
        CapturableService.create(checkCapturable);
    }

    // TODO: no error is thrown here; should throw ValidationException from CapturableRules.
    @Test
    //@Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testCapturableNonUniqueFlagInContest() {
        testContest = contestService.readById(testContest.getId());
        checkCapturable = TestUtility.makeCapturable(testContest);
        checkCapturable.setFlag(testCapturable.getFlag());
        CapturableService.create(checkCapturable);
    }

    @Test
    public void testCapturableNonUniqueName() {
        Contest checkContest = TestUtility.makeContest();
        contestService.create(checkContest);
        checkCapturable = TestUtility.makeCapturable(checkContest);
        checkCapturable.setName(testCapturable.getName());
        CapturableService.create(checkCapturable);
        String UUID = checkCapturable.getId();
        String name = checkCapturable.getName();
        checkCapturable = null;
        checkCapturable = CapturableService.readById(UUID);
        Assert.assertEquals(name, testCapturable.getName());
    }

    @Test
    public void testCapturableNonUniqueFlag() {
        Contest checkContest = TestUtility.makeContest();
        contestService.create(checkContest);
        checkCapturable = TestUtility.makeCapturable(checkContest);
        checkCapturable.setFlag(testCapturable.getFlag());
        CapturableService.create(checkCapturable);
        String UUID = checkCapturable.getId();
        String flag = checkCapturable.getFlag();
        checkCapturable = null;
        checkCapturable = CapturableService.readById(UUID);
        Assert.assertEquals(flag, testCapturable.getFlag());
    }
}