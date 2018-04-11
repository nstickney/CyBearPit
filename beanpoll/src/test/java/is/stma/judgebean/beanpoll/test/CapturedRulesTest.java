/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.judgebean.beanpoll.test;

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
import javax.validation.ValidationException;
import java.io.File;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@RunWith(Arquillian.class)
public class CapturedRulesTest {

    @Inject
    private Logger log;

    @Inject
    private CapturedService capturedService;

    @Inject
    private ContestService contestService;

    @Inject
    private CapturableService capturableService;

    @Inject
    private TeamService teamService;

    private Captured testCaptured;
    private Contest testContest;
    private Capturable testCapturable;
    private Team testTeam;

    @Deployment
    public static WebArchive createDeployment() {

        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class, "capturedRulesTest.war")
                .addPackages(true, Captured.class.getPackage(),
                        CapturedRepo.class.getPackage(),
                        CapturedService.class.getPackage(),
                        CapturedRules.class.getPackage(),
                        EMProducer.class.getPackage())
                .addClass(TestUtility.class)
                .addClass(ValidationException.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("META-INF/apache-deltaspike.properties")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml") // Deploy test datasource
                .addAsLibraries(files); // Add necessary stuff from pom.xml
    }

    @Before
    public void setUp() {
        if (null == testContest) {
            testContest = TestUtility.makeContest();
            testContest.setEnabled(true);
            testContest.setRunning(true);
            contestService.create(testContest);
        }
        testContest = contestService.readById(testContest.getId());
        if (null == testCapturable) {
            testCapturable = TestUtility.makeCapturable(testContest, TestUtility.TEST_CAPTURABLE_FLAG, 10);
            capturableService.create(testCapturable);
        }
        testCapturable = capturableService.readById(testCapturable.getId());
        if (null == testTeam) {
            testTeam = TestUtility.makeTeam(testContest, TestUtility.TEST_TEAM_FLAG);
            teamService.create(testTeam);
        }
        testTeam = teamService.readById(testTeam.getId());
        if (null == testCaptured) {
            testCaptured = TestUtility.makeCaptured(testCapturable, testTeam);
            capturedService.create(testCaptured);
        }
        testCaptured = capturedService.readById(testCaptured.getId());
    }

    @Test
    public void testCapturedCreation() {
        Captured checkCaptured = capturedService.readById(testCaptured.getId());
        Assert.assertTrue(testCaptured.equalByUUID(checkCaptured));
        Assert.assertEquals(testCaptured, checkCaptured);
    }

    @Test
    public void testCapturedUpdate() {
        String testCapturedUUID = testCaptured.getId();
        LocalDateTime checkTimestamp = LocalDateTime.now().plusMinutes(1);
        testCaptured.setTimestamp(checkTimestamp);
        capturedService.update(testCaptured);
        testCaptured = null;
        testCaptured = capturedService.readById(testCapturedUUID);
        Assert.assertEquals(checkTimestamp, testCaptured.getTimestamp());
    }

    @Test
    public void testCapturedDeletion() {
        capturedService.delete(testCaptured);
        testCaptured = capturedService.readById(testCaptured.getId());
        Assert.assertNull(testCaptured);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testUpdateNonexistentCaptured() {
        Captured checkCaptured = new Captured();
        checkCaptured.setTeam(testTeam);
        checkCaptured.setCapturable(testCapturable);
        capturedService.update(checkCaptured);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testContestNotEnabled() {
        capturedService.delete(testCaptured);
        testContest.setEnabled(false);
        contestService.update(testContest);
        testCapturable = capturableService.readById(testCapturable.getId());
        testCaptured.setCapturable(testCapturable);
        capturedService.create(testCaptured);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testContestNotRunning() {
        capturedService.delete(testCaptured);
        testContest.setRunning(false);
        contestService.update(testContest);
        testCapturable = capturableService.readById(testCapturable.getId());
        testCaptured.setCapturable(testCapturable);
        capturedService.create(testCaptured);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testTeamNotInContest() {
        Team checkTeam = TestUtility.makeTeam(null, TestUtility.TEST_TEAM_FLAG);
        testCaptured.setTeam(checkTeam);
        capturedService.update(testCaptured);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testDoubleCapturableSubmission() {
        Captured checkCaptured = TestUtility.makeCaptured(testCapturable, testTeam);
        capturedService.create(checkCaptured);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testNonUniqueCapturedUUID() {
        Team checkTeam = TestUtility.makeTeam(testContest, "CHECK_TEAM_FLAG");
        teamService.create(checkTeam);
        Captured checkCaptured = capturedService.readById(testCaptured.getId());
        checkCaptured.setTeam(checkTeam);
        capturedService.create(checkCaptured);
    }
}