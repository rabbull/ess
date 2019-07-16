package models.relations;

import models.Model;
import models.entities.Company;
import models.entities.Project;

public class Bans extends Model {
    private Project project;
    private Company company;
    private String reason;

    public Bans() {
    }

    public Bans(Project project, Company company, String reason) {
        this.project = project;
        this.company = company;
        this.reason = reason;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
