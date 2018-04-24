package is.stma.judgebean.beanpoll.test;/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import is.stma.judgebean.beanpoll.data.PollRepo;
import is.stma.judgebean.beanpoll.data.ResourceRepo;
import is.stma.judgebean.beanpoll.model.Contest;
import is.stma.judgebean.beanpoll.model.Poll;
import is.stma.judgebean.beanpoll.model.Resource;
import is.stma.judgebean.beanpoll.rules.PollRules;
import is.stma.judgebean.beanpoll.rules.ResourceRules;
import is.stma.judgebean.beanpoll.service.ContestService;
import is.stma.judgebean.beanpoll.service.PollService;
import is.stma.judgebean.beanpoll.service.ResourceService;
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

@RunWith(Arquillian.class)
public class PollTest {

    @Inject
    private ContestService contestService;

    @Inject
    private PollService pollService;

    @Inject
    private ResourceService resourceService;

    private Contest testContest;
    
    private Poll testPoll;
    private Poll checkPoll;

    private Resource testResource;

    /**
     * Create a web archive (WAR) for deployment via Arquillian
     * @return the web archive
     */
    @Deployment
    public static WebArchive createDeployment() {
        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class, "ResourceRulesTest.war")
                .addPackages(true, Poll.class.getPackage(),
                        PollRepo.class.getPackage(),
                        PollService.class.getPackage(),
                        PollRules.class.getPackage(),
                        EMProducer.class.getPackage())
                .addClass(TestUtility.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("META-INF/apache-deltaspike.properties")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml") // Deploy test datasource
                .addAsLibraries(files); // Add necessary stuff from pom.xml
    }

    /**
     * Create a test contest and a test Resource in that contest, and persist both
     */
    @Before
    public void setUp() {
        if (null == testContest) {
            testContest = TestUtility.makeContest();
            contestService.create(testContest);
        }
        if (null == testResource) {
            testResource = TestUtility.makeResource(testContest, Resource.DNS);
            resourceService.create(testResource);
        }
        if (null == testPoll) {
            testPoll = TestUtility.makePoll(testResource);
            pollService.create(testPoll);
        }
    }

    @Test
    public void testPollCreation() {
        checkPoll = pollService.readById(testPoll.getId());
        Assert.assertTrue(testPoll.equalByUUID(checkPoll));
        Assert.assertEquals(testPoll, checkPoll);
    }

    @Test
    public void testPollUpdate() {
        LocalDateTime dtg = LocalDateTime.now();
        testPoll.setTimestamp(dtg);
        pollService.update(testPoll);
        String UUID = testPoll.getId();
        testPoll = null;
        testPoll = pollService.readById(UUID);
        Assert.assertEquals(dtg, testPoll.getTimestamp());
    }

    @Test
    public void testPollDeletion() {
        pollService.delete(testPoll);
        String UUID = testPoll.getId();
        testPoll = null;
        testPoll = pollService.readById(UUID);
        Assert.assertNull(testPoll);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testPollUpdateNonexistent() {
        checkPoll = TestUtility.makePoll(testResource);
        pollService.update(checkPoll);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testPollNonUniqueUUID() {
        checkPoll = testPoll;
        checkPoll.setTimestamp(LocalDateTime.now());
        pollService.create(checkPoll);
    }
}