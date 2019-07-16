package models.relations;

import models.Model;
import models.entities.Company;
import models.entities.Expert;

public class WorksAt extends Model {
    private Expert expert;
    private Company company;

    public WorksAt() {
    }

    public WorksAt(Expert expert, Company company) {
        this.expert = expert;
        this.company = company;
    }
}
