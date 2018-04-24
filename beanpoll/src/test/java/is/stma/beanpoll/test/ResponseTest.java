/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.beanpoll.test;/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import is.stma.beanpoll.data.ResponseRepo;
import is.stma.beanpoll.model.Contest;
import is.stma.beanpoll.model.Response;
import is.stma.beanpoll.model.Task;
import is.stma.beanpoll.model.Team;
import is.stma.beanpoll.rules.ResponseRules;
import is.stma.beanpoll.service.ContestService;
import is.stma.beanpoll.service.ResponseService;
import is.stma.beanpoll.service.TaskService;
import is.stma.beanpoll.service.TeamService;
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
public class ResponseTest {

    @Inject
    private ContestService contestService;

    @Inject
    private ResponseService responseservice;

    @Inject
    private TaskService taskService;

    @Inject
    private TeamService teamService;

    private Contest testContest;

    private Task testTask;

    private Response testResponse;
    private Response checkResponse;

    private Team testTeam;

    /**
     * Create a web archive (WAR) for deployment via Arquillian
     * @return the web archive
     */
    @Deployment
    public static WebArchive createDeployment() {
        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class, "ResponseRulesTest.war")
                .addPackages(true, Response.class.getPackage(),
                        ResponseRepo.class.getPackage(),
                        ResponseService.class.getPackage(),
                        ResponseRules.class.getPackage(),
                        EMProducer.class.getPackage())
                .addClass(TestUtility.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("META-INF/apache-deltaspike.properties")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml") // Deploy test datasource
                .addAsLibraries(files); // Add necessary stuff from pom.xml
    }

    /**
     * Create a test contest and a test Response in that contest, and persist both
     */
    @Before
    public void setUp() {
        if (null == testContest) {
            testContest = TestUtility.makeContest();
            testContest.setEnabled(true);
            testContest.setRunning(true);
            testContest = contestService.create(testContest);
        }
        if (null == testTeam) {
            testTeam = TestUtility.makeTeam(testContest);
            testTeam = teamService.create(testTeam);
        }
        if (null == testTask) {
            testTask = TestUtility.makeTask(testContest);
            testTask = taskService.create(testTask);
        }
        if (null == testResponse) {
            testResponse = TestUtility.makeResponse(testTask, testTeam);
            testResponse = responseservice.create(testResponse);
        }
    }

    @Test
    public void testResponseCreation() {
        checkResponse = responseservice.readById(testResponse.getId());
        Assert.assertTrue(testResponse.equalByUUID(checkResponse));
        Assert.assertEquals(testResponse, checkResponse);
    }

    @Test
    public void testResponseUpdate() {
        testResponse.setComments("UPDATED");
        responseservice.update(testResponse);
        String UUID = testResponse.getId();
        testResponse = null;
        testResponse = responseservice.readById(UUID);
        Assert.assertEquals("UPDATED", testResponse.getComments());
    }

    @Test
    public void testResponseDeletion() {
        responseservice.delete(testResponse);
        String UUID = testResponse.getId();
        testResponse = null;
        testResponse = responseservice.readById(UUID);
        Assert.assertNull(testResponse);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testResponseUpdateNonexistent() {
        checkResponse = TestUtility.makeResponse(testTask, testTeam);
        checkResponse.setComments("Check Response");
        responseservice.update(checkResponse);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testResponseNonUniqueUUID() {
        checkResponse = testResponse;
        checkResponse.setComments("We Copied You!");
        responseservice.create(checkResponse);
    }
}