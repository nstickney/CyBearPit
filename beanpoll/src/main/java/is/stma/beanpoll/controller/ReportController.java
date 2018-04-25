/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.beanpoll.controller;

import is.stma.beanpoll.model.Report;
import is.stma.beanpoll.model.Team;
import is.stma.beanpoll.rules.ReportRules;
import is.stma.beanpoll.service.ReportService;
import is.stma.beanpoll.service.TaskService;
import is.stma.beanpoll.service.TeamService;
import org.apache.commons.io.IOUtils;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Servlets;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.ws.rs.Produces;
import java.io.IOException;
import java.io.InputStream;

@Model
public class ReportController extends AbstractEntityController<Report,
        ReportRules, ReportService> {

    @Inject
    private ReportService service;

    @Inject
    private TaskService taskService;

    @Inject
    private TeamService teamService;

    private Report newReport;

    private Part uploadedFile;

    @Override
    @Produces
    @Named("newreport")
    Report getNew() {
        if (newReport == null) {
            newReport = new Report();
        }
        return newReport;
    }

    @Override
    void setNew(Report entity) {
        newReport = entity;
    }

    @Override
    ReportService getService() {
        return service;
    }

    @Override
    public void update(Report entity) {
        doUpdate(entity);
    }

    @Override
    public void delete(Report entity) {
        doDelete(entity);
    }

    public Part getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public void uploadReport(Team team) {

        if (null != uploadedFile) {

            getNew().setTeam(team);

            // Upload the report
            try {
                getNew().setFileName(Servlets.getSubmittedFileName(uploadedFile));
                getNew().setFileType(uploadedFile.getContentType());
                getNew().setFileSize(uploadedFile.getSize());
                InputStream input = uploadedFile.getInputStream();
                byte[] uploaded = IOUtils.toByteArray(input);
                getNew().setUploaded(uploaded);
                create();
            } catch (IOException e) {
                delete(getNew());
                errorOut(e, "File upload failed");
            }
        } else {
            errorOut(null, "No file selected for upload");
        }
    }

    public void downloadReport(Report report) {
        try {
            Faces.sendFile(report.getUploaded(), report.getFileName(), true);
        } catch (IOException e) {
            errorOut(e, "File download failed");
        }
    }
}
