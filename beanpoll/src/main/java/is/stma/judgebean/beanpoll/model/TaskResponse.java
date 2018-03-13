package is.stma.judgebean.beanpoll.model;

import javax.persistence.*;

@Entity
public class TaskResponse extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Column
    private String contents;

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

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
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
