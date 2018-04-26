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

import is.stma.beanpoll.controller.poller.AbstractPoller;
import is.stma.beanpoll.controller.poller.PollerFactory;
import is.stma.beanpoll.data.PollRepo;
import is.stma.beanpoll.model.*;
import is.stma.beanpoll.rules.PollRules;
import is.stma.beanpoll.service.*;
import is.stma.beanpoll.service.parameterizer.EmailParameterizer;
import is.stma.beanpoll.util.EMProducer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RunWith(Arquillian.class)
public class EmailPollerTest {

    @Inject
    private ContestService contestService;

    @Inject
    private ParameterService parameterService;

    @Inject
    private ResourceService resourceService;

    @Inject
    private TeamService teamService;

    private Contest testContest;

    private Poll testPoll;

    private Team testTeam;
    private Team checkTeam;

    private Resource testResource;

    @Deployment
    public static Archive<?> createTestArchive() {

        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class, "EmailPollerTest.war")
                .addPackages(true, Poll.class.getPackage(),
                        PollRepo.class.getPackage(),
                        PollService.class.getPackage(),
                        PollRules.class.getPackage(),
                        EmailParameterizer.class.getPackage(),
                        AbstractPoller.class.getPackage(),
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
            testContest = contestService.create(testContest);
            List<Team> teams = new ArrayList<>();
            if (null == testTeam) {
                testTeam = TestUtility.makeTeam(testContest);
                testTeam.setFlag("Baylor");
                testTeam = teamService.create(testTeam);
                teams.add(testTeam);
            }
            if (null == checkTeam) {
                checkTeam = TestUtility.makeTeam(testContest);
                checkTeam.setFlag("BONUSPOINTS");
                checkTeam = teamService.create(checkTeam);
                teams.add(checkTeam);
            }
            testContest.setTeams(teams);
            testContest = contestService.update(testContest);
        }
    }

    private void setUpResource(ResourceType type, String address, int port) {
        testResource = TestUtility.makeResource(testContest, type);
        testResource.setAddress(address);
        testResource.setPort(port);
        List<Team> teams = new ArrayList<>();
        teams.add(testTeam);
        testResource.setTeams(teams);
        testResource = resourceService.create(testResource);
    }

    @Test
    public void testIMAPPollWorks() {
        setUpResource(ResourceType.IMAP, "imap.yandex.com", 993);
        AbstractPoller poller = PollerFactory.getPoller(testResource);
        Poll poll = poller.poll();
        Assert.assertTrue(poll.getTeam().equalByUUID(testTeam));
        Assert.assertEquals(testResource.getPointValue(), poll.getScore());
    }

    @Test
    public void testPOPPollWorks() {
        setUpResource(ResourceType.POP, "pop.yandex.com", 995);
        AbstractPoller poller = PollerFactory.getPoller(testResource);
        Poll poll = poller.poll();
        Assert.assertTrue(poll.getTeam().equalByUUID(testTeam));
        Assert.assertEquals(testResource.getPointValue(), poll.getScore());
    }

    @Test
    public void testIMAPPollNoTeam() {
        setUpResource(ResourceType.IMAP, "imap.yandex.com", 993);
        testResource.setTeams(new ArrayList<>());
        resourceService.update(testResource);
        AbstractPoller poller = PollerFactory.getPoller(testResource);
        testPoll = poller.poll();
        Assert.assertNull(testPoll.getTeam());
        Assert.assertEquals(0, testPoll.getScore());
    }

    @Test
    public void testIMAPPollTwoTeams() {
        setUpResource(ResourceType.IMAP, "imap.yandex.com", 993);
        List<Team> teams = new ArrayList<>();
        teams.add(testTeam);
        teams.add(checkTeam);
        testResource.setTeams(teams);
        testResource = resourceService.update(testResource);
        AbstractPoller poller = PollerFactory.getPoller(testResource);
        testPoll = poller.poll();
        Assert.assertNull(testPoll.getTeam());
        Assert.assertEquals(0, testPoll.getScore());
    }

    @Test
    public void testIMAPPollFails() {
        setUpResource(ResourceType.IMAP, "blue.com", 993);
        AbstractPoller poller = PollerFactory.getPoller(testResource);
        testPoll = poller.poll();
        Assert.assertNull(testPoll.getTeam());
        Assert.assertEquals(0, testPoll.getScore());
    }

    @Test
    public void testPOPPollFails() {
        setUpResource(ResourceType.IMAP, "blue.com", 995);
        AbstractPoller poller = PollerFactory.getPoller(testResource);
        testPoll = poller.poll();
        Assert.assertNull(testPoll.getTeam());
        Assert.assertEquals(0, testPoll.getScore());
    }

    // Yandex does not use MX records, so I have no way of testing this without
    // setting up my own DNS and email servers... which, no.
//    @Test
//    public void testIMAPSpecificResolver() {
//        setUpResource(ResourceType.IMAP, "imap.yandex.com", 993);
//        TestUtility.setResourceParameter(parameterService, testResource, EmailParameterizer.EMAIL_RESOLVER, "1.1.1.1");
//        testResource = resourceService.update(testResource);
//        testPoll = PollerFactory.getPoller(testResource).poll();
//        Assert.assertTrue(testPoll.getTeam().equalByUUID(testTeam));
//        Assert.assertEquals(testResource.getPointValue(), testPoll.getScore());
//    }

    @Test
    public void testIMAPResolverFails() {
        setUpResource(ResourceType.IMAP, "imap.yandex.com", 993);
        TestUtility.setResourceParameter(parameterService, testResource, EmailParameterizer.EMAIL_RESOLVER, "1.1.1.1");
        testResource = resourceService.update(testResource);
        testPoll = PollerFactory.getPoller(testResource).poll();
        Assert.assertNull(testPoll.getTeam());
        Assert.assertEquals(0, testPoll.getScore());
    }
}