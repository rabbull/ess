package models.sheets;

import models.entities.Company;
import models.entities.Expert;

public class ExpertGetAllSheet {
    private Expert expert;
    private Company company;

    public ExpertGetAllSheet(Expert expert, Company company) {
        this.expert = expert;
        this.company = company;
    }

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
}
