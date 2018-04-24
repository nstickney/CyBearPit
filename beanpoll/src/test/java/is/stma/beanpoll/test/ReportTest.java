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

import is.stma.beanpoll.data.ReportRepo;
import is.stma.beanpoll.model.Report;
import is.stma.beanpoll.model.Contest;
import is.stma.beanpoll.model.Team;
import is.stma.beanpoll.rules.ReportRules;
import is.stma.beanpoll.service.ReportService;
import is.stma.beanpoll.service.ContestService;
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
public class ReportTest {

    @Inject
    private ContestService contestService;

    @Inject
    private ReportService reportservice;

    @Inject
    private TeamService teamService;

    private Contest testContest;

    private Report testReport;
    private Report checkReport;

    private Team testTeam;

    /**
     * Create a web archive (WAR) for deployment via Arquillian
     * @return the web archive
     */
    @Deployment
    public static WebArchive createDeployment() {
        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class, "ReportRulesTest.war")
                .addPackages(true, Report.class.getPackage(),
                        ReportRepo.class.getPackage(),
                        ReportService.class.getPackage(),
                        ReportRules.class.getPackage(),
                        EMProducer.class.getPackage())
                .addClass(TestUtility.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("META-INF/apache-deltaspike.properties")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml") // Deploy test datasource
                .addAsLibraries(files); // Add necessary stuff from pom.xml
    }

    /**
     * Create a test contest and a test Report in that contest, and persist both
     */
    @Before
    public void setUp() {
        if (null == testContest) {
            testContest = TestUtility.makeContest();
            contestService.create(testContest);
        }
        if (null == testTeam) {
            testTeam = TestUtility.makeTeam(testContest);
            teamService.create(testTeam);
        }
        if (null == testReport) {
            testReport = TestUtility.makeReport(testTeam);
            reportservice.create(testReport);
        }
    }

    @Test
    public void testReportCreation() {
        checkReport = reportservice.readById(testReport.getId());
        Assert.assertTrue(testReport.equalByUUID(checkReport));
        Assert.assertEquals(testReport, checkReport);
    }

    @Test
    public void testReportUpdate() {
        testReport.setComments("UPDATED");
        reportservice.update(testReport);
        String UUID = testReport.getId();
        testReport = null;
        testReport = reportservice.readById(UUID);
        Assert.assertEquals("UPDATED", testReport.getComments());
    }

    @Test
    public void testReportDeletion() {
        reportservice.delete(testReport);
        String UUID = testReport.getId();
        testReport = null;
        testReport = reportservice.readById(UUID);
        Assert.assertNull(testReport);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testReportUpdateNonexistent() {
        checkReport = TestUtility.makeReport(testTeam);
        checkReport.setComments("Check Report");
        reportservice.update(checkReport);
    }

    @Test(expected = EJBException.class)
    //@Test(expected = ValidationException.class)
    public void testReportNonUniqueUUID() {
        checkReport = testReport;
        checkReport.setComments("We Copied You!");
        reportservice.create(checkReport);
    }
}