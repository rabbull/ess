package models.relations;

import com.sun.org.apache.xpath.internal.operations.Bool;
import models.Model;
import models.entities.Expert;
import models.entities.Project;

public class Invites extends Model {
    private Project project;
    private Expert expert;
    private Boolean accepted;
    private String reason;

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Invites() {}

    public Invites(Project project, Expert expert, boolean accepted) {
        this.project = project;
        this.expert = expert;
        this.accepted = accepted;
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
}
