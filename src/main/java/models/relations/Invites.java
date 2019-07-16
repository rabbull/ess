package models.relations;

import models.Model;
import models.entities.Expert;
import models.entities.Project;

public class Invites extends Model {
    private Project project;
    private Expert expert;
    private String reason;
    private Integer score;

    public Invites() {}

    public Invites(Project project, Expert expert) {
        this.project = project;
        this.expert = expert;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Expert getExpert() {
        return expert;
    }

    public void setExpert(Expert expert) {
        this.expert = expert;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
