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

@RunWith(Arquillian.class)
public class TeamTest {

    @Inject
    private ContestService contestService;

    @Inject
    private TeamService TeamService;

    private Contest testContest;

    private Team testTeam;
    private Team checkTeam;

    /**
     * Create a web archive (WAR) for deployment via Arquillian
     * @return the web archive
     */
    @Deployment
    public static WebArchive createDeployment() {
        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class, "TeamRulesTest.war")
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

    /**
     * Create a test contest and a test team in that contest, and persist both
     */
    @Before
    public void setUp() {
        if (null == testContest) {
            testContest = TestUtility.makeContest();
            contestService.create(testContest);
        }
        if (null == testTeam) {
            testTeam = TestUtility.makeTeam(testContest);
            TeamService.create(testTeam);
        }
    }

    @Test
    public void testTeamCreation() {
        checkTeam = TeamService.readById(testTeam.getId());
        Assert.assertTrue(testTeam.equalByUUID(checkTeam));
        Assert.assertEquals(testTeam, checkTeam);
    }

    @Test
    public void testTeamUpdate() {
        testTeam.setName("UPDATED");
        TeamService.update(testTeam);
        String UUID = testTeam.getId();
        testTeam = null;
        testTeam = TeamService.readById(UUID);
        Assert.assertEquals("UPDATED", testTeam.getName());
    }

    @Test
    public void testTeamDeletion() {
        TeamService.delete(testTeam);
        String UUID = testTeam.getId();
        testTeam = null;
        testTeam = TeamService.readById(UUID);
        Assert.assertNull(testTeam);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testTeamUpdateNonexistent() {
        checkTeam = TestUtility.makeTeam(testContest);
        checkTeam.setName("Check Team");
        TeamService.update(checkTeam);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testTeamNonUniqueUUID() {
        checkTeam = testTeam;
        checkTeam.setName("We Copied You!");
        checkTeam.setFlag("We Copied You!");
        TeamService.create(checkTeam);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testTeamNonUniqueNameInContest() {
        checkTeam = TestUtility.makeTeam(testContest);
        checkTeam.setName(testTeam.getName());
        TeamService.create(checkTeam);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testTeamNonUniqueFlagInContest() {
        testContest = contestService.readById(testContest.getId());
        checkTeam = TestUtility.makeTeam(testContest);
        checkTeam.setFlag(testTeam.getFlag());
        TeamService.create(checkTeam);
    }

    @Test
    public void testTeamNonUniqueName() {
        Contest checkContest = TestUtility.makeContest();
        contestService.create(checkContest);
        checkTeam = TestUtility.makeTeam(checkContest);
        checkTeam.setName(testTeam.getName());
        TeamService.create(checkTeam);
        String UUID = checkTeam.getId();
        String name = checkTeam.getName();
        checkTeam = null;
        checkTeam = TeamService.readById(UUID);
        Assert.assertEquals(name, testTeam.getName());
    }

    @Test
    public void testTeamNonUniqueFlag() {
        Contest checkContest = TestUtility.makeContest();
        contestService.create(checkContest);
        checkTeam = TestUtility.makeTeam(checkContest);
        checkTeam.setFlag(testTeam.getFlag());
        TeamService.create(checkTeam);
        String UUID = checkTeam.getId();
        String flag = checkTeam.getFlag();
        checkTeam = null;
        checkTeam = TeamService.readById(UUID);
        Assert.assertEquals(flag, testTeam.getFlag());
    }
}