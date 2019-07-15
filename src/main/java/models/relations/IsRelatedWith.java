package models.relations;

import models.Model;
import models.entities.Company;
import models.entities.Project;

public class IsRelatedWith extends Model {
    private Project project;
    private Company company;

    public IsRelatedWith() {
    }

    public IsRelatedWith(Project project, Company company) {
        this.project = project;
        this.company = company;
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
}
