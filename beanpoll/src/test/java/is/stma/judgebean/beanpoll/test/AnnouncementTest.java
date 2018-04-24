package is.stma.judgebean.beanpoll.test;/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import is.stma.judgebean.beanpoll.data.AnnouncementRepo;
import is.stma.judgebean.beanpoll.model.Contest;
import is.stma.judgebean.beanpoll.model.Announcement;
import is.stma.judgebean.beanpoll.rules.AnnouncementRules;
import is.stma.judgebean.beanpoll.service.ContestService;
import is.stma.judgebean.beanpoll.service.AnnouncementService;
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
public class AnnouncementTest {

    @Inject
    private ContestService contestService;

    @Inject
    private AnnouncementService announcementservice;

    private Contest testContest;

    private Announcement testAnnouncement;
    private Announcement checkAnnouncement;

    /**
     * Create a web archive (WAR) for deployment via Arquillian
     * @return the web archive
     */
    @Deployment
    public static WebArchive createDeployment() {
        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class, "AnnouncementRulesTest.war")
                .addPackages(true, Announcement.class.getPackage(),
                        AnnouncementRepo.class.getPackage(),
                        AnnouncementService.class.getPackage(),
                        AnnouncementRules.class.getPackage(),
                        EMProducer.class.getPackage())
                .addClass(TestUtility.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("META-INF/apache-deltaspike.properties")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml") // Deploy test datasource
                .addAsLibraries(files); // Add necessary stuff from pom.xml
    }

    /**
     * Create a test contest and a test Announcement in that contest, and persist both
     */
    @Before
    public void setUp() {
        if (null == testContest) {
            testContest = TestUtility.makeContest();
            contestService.create(testContest);
        }
        if (null == testAnnouncement) {
            testAnnouncement = TestUtility.makeAnnouncement(testContest);
            announcementservice.create(testAnnouncement);
        }
    }

    @Test
    public void testAnnouncementCreation() {
        checkAnnouncement = announcementservice.readById(testAnnouncement.getId());
        Assert.assertTrue(testAnnouncement.equalByUUID(checkAnnouncement));
        Assert.assertEquals(testAnnouncement, checkAnnouncement);
    }

    @Test
    public void testAnnouncementUpdate() {
        testAnnouncement.setName("UPDATED");
        announcementservice.update(testAnnouncement);
        String UUID = testAnnouncement.getId();
        testAnnouncement = null;
        testAnnouncement = announcementservice.readById(UUID);
        Assert.assertEquals("UPDATED", testAnnouncement.getName());
    }

    @Test
    public void testAnnouncementDeletion() {
        announcementservice.delete(testAnnouncement);
        String UUID = testAnnouncement.getId();
        testAnnouncement = null;
        testAnnouncement = announcementservice.readById(UUID);
        Assert.assertNull(testAnnouncement);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testAnnouncementUpdateNonexistent() {
        checkAnnouncement = TestUtility.makeAnnouncement(testContest);
        checkAnnouncement.setName("Check Announcement");
        announcementservice.update(checkAnnouncement);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testAnnouncementNonUniqueUUID() {
        checkAnnouncement = testAnnouncement;
        checkAnnouncement.setName("We Copied You!");
        announcementservice.create(checkAnnouncement);
    }
}