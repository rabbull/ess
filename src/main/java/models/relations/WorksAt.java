package models.relations;

import models.Model;
import models.entities.Company;
import models.entities.Expert;

public class WorksAt extends Model {
    private Expert expert;
    private Company company;

    public Expert getExpert() {
        return expert;
    }

    public void setExpert(Expert expert) {
        this.expert = expert;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public WorksAt() {
    }

    public WorksAt(Expert expert, Company company) {
        this.expert = expert;
        this.company = company;
    }
}
