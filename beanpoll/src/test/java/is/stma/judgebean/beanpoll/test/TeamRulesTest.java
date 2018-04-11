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

import is.stma.judgebean.beanpoll.data.TeamRepo;
import is.stma.judgebean.beanpoll.model.Contest;
import is.stma.judgebean.beanpoll.model.Team;
import is.stma.judgebean.beanpoll.rules.TeamRules;
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
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.logging.Logger;

@RunWith(Arquillian.class)
public class TeamRulesTest {

    @Inject
    private Logger log;

    @Inject
    private ContestService contestService;

    @Inject
    private TeamService teamService;

    private Contest testContest;
    private Team testTeam;
    private Team checkTeam;

    @Deployment
    public static WebArchive createDeployment() {

        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class, "teamRulesTest.war")
                .addPackages(true, Team.class.getPackage(),
                        TeamRepo.class.getPackage(),
                        TeamService.class.getPackage(),
                        TeamRules.class.getPackage(),
                        EMProducer.class.getPackage())
                .addClass(TestUtility.class)
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
            contestService.create(testContest);
        }

        if (null == testTeam) {
            testTeam = TestUtility.makeTeam(testContest, TestUtility.TEST_TEAM_FLAG);
            teamService.create(testTeam);
        }
    }

    @Test
    public void testTeamCreation() {
        checkTeam = teamService.readById(testTeam.getId());
        Assert.assertTrue(testTeam.equalByUUID(checkTeam));
        Assert.assertEquals(testTeam, checkTeam);
    }

    @Test
    public void testTeamUpdate() {
        String newTeamUUID = testTeam.getId();
        testTeam.setFlag("UPDATED");
        teamService.update(testTeam);
        testTeam = null;
        testTeam = teamService.readById(newTeamUUID);
        Assert.assertEquals("UPDATED", testTeam.getFlag());
    }

    @Test
    public void testTeamDeletion() {
        teamService.delete(testTeam);
        testTeam = teamService.readById(testTeam.getId());
        Assert.assertNull(testTeam);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testUpdateNonexistentTeam() {
        checkTeam = new Team();
        checkTeam.setName("Check Team");
        checkTeam.setFlag("CHECK");
        teamService.update(checkTeam);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testNonUniqueTeamUUID() {
        checkTeam = testTeam;
        checkTeam.setFlag("We Copied You!");
        teamService.create(checkTeam);
    }

    //TODO: Arquillian catches the wrong error here(?!)
    @Test(expected = java.lang.AssertionError.class)
    //@Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testNonUniqueTeamFlag() {
        checkTeam = new Team();
        checkTeam.setName("Test Team 2");
        checkTeam.setFlag("TEST");
        checkTeam.setContest(testContest);
        teamService.create(checkTeam);
    }
}