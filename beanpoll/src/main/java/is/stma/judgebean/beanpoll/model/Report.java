/*
 * Copyright 2018 Nathaniel Stickney
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package is.stma.judgebean.beanpoll.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Report extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Column
    private LocalDateTime timestamp = LocalDateTime.now();

    @Column
    private String fileName;

    @Column
    private String fileType;

    @Column
    private long fileSize;

    @Column
    @Lob
    private byte[] uploaded;

    @Column
    private int score = 0;

    @Column
    private String comments;

    @Override
    public String getName() {
        return getId();
    }

    public String getLook() {
        if (null == comments || comments.equals("")) {
            return "warning";
        }
        return "default";
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestampString() {
        return timestamp.toString();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String contents) {
        this.fileName = contents;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getUploaded() {
        return uploaded.clone();
    }

    public void setUploaded(byte[] uploaded) {
        this.uploaded = uploaded.clone();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
