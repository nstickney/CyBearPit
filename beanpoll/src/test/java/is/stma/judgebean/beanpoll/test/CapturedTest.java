package is.stma.judgebean.beanpoll.test;/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import is.stma.judgebean.beanpoll.data.CapturedRepo;
import is.stma.judgebean.beanpoll.model.Capturable;
import is.stma.judgebean.beanpoll.model.Captured;
import is.stma.judgebean.beanpoll.model.Contest;
import is.stma.judgebean.beanpoll.model.Team;
import is.stma.judgebean.beanpoll.rules.CapturedRules;
import is.stma.judgebean.beanpoll.service.CapturableService;
import is.stma.judgebean.beanpoll.service.CapturedService;
import is.stma.judgebean.beanpoll.service.ContestService;
import is.stma.judgebean.beanpoll.service.TeamService;
import is.stma.judgebean.beanpoll.util.EMProducer;
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
public class CapturedTest {

    @Inject
    private CapturableService capturableService;

    @Inject
    private CapturedService capturedService;

    @Inject
    private ContestService contestService;

    @Inject
    private TeamService teamService;

    private Capturable testCapturable;

    private Contest testContest;


    private Captured testCaptured;
    private Captured checkCaptured;

    private Team testTeam;

    /**
     * Create a web archive (WAR) for deployment via Arquillian
     * @return the web archive
     */
    @Deployment
    public static WebArchive createDeployment() {
        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class, "CapturedRulesTest.war")
                .addPackages(true, Captured.class.getPackage(),
                        CapturedRepo.class.getPackage(),
                        CapturedService.class.getPackage(),
                        CapturedRules.class.getPackage(),
                        EMProducer.class.getPackage())
                .addClass(TestUtility.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("META-INF/apache-deltaspike.properties")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml") // Deploy test datasource
                .addAsLibraries(files); // Add necessary stuff from pom.xml
    }

    /**
     * Create a test contest and a test Captured in that contest, and persist both
     */
    @Before
    public void setUp() {
        if (null == testContest) {
            testContest = TestUtility.makeContest();
            testContest.setEnabled(true);
            testContest.setRunning(true);
            contestService.create(testContest);
        }
        if (null == testCapturable) {
            testCapturable = TestUtility.makeCapturable(testContest);
            capturableService.create(testCapturable);
        }
        if (null == testTeam) {
            testTeam = TestUtility.makeTeam(testContest);
            teamService.create(testTeam);
        }
        if (null == testCaptured) {
            testCaptured = TestUtility.makeCaptured(testCapturable, testTeam);
            capturedService.create(testCaptured);
        }
    }

    @Test
    public void testCapturedCreation() {
        checkCaptured = capturedService.readById(testCaptured.getId());
        Assert.assertTrue(testCaptured.equalByUUID(checkCaptured));
        Assert.assertEquals(testCaptured, checkCaptured);
    }

    @Test
    public void testCapturedUpdate() {
        Team checkTeam = TestUtility.makeTeam(testContest);
        teamService.create(checkTeam);
        testCaptured.setTeam(checkTeam);
        capturedService.update(testCaptured);
        String UUID = testCaptured.getId();
        testCaptured = null;
        testCaptured = capturedService.readById(UUID);
        Assert.assertTrue(checkTeam.equalByUUID(testCaptured.getTeam()));
    }

    @Test
    public void testCapturedDeletion() {
        capturedService.delete(testCaptured);
        String UUID = testCaptured.getId();
        testCaptured = null;
        testCaptured = capturedService.readById(UUID);
        Assert.assertNull(testCaptured);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testCapturedUpdateNonexistent() {
        checkCaptured = TestUtility.makeCaptured(testCapturable, testTeam);
        capturedService.update(checkCaptured);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testCapturedNonUniqueUUID() {
        checkCaptured = testCaptured;
        capturedService.create(checkCaptured);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testCapturedAlready() {
        checkCaptured = TestUtility.makeCaptured(testCapturable, testTeam);
        capturedService.create(checkCaptured);
    }
}