package models.relations;

import models.Model;
import models.entities.Expert;
import models.entities.Profession;

public class MajorIn extends Model {
    private Expert expert;
    private Profession profession;

    public MajorIn() {}

    public MajorIn(Expert expert, Profession profession) {
        this.expert = expert;
        this.profession = profession;
    }

    public Expert getExpert() {
        return expert;
    }

    public void setExpert(Expert expert) {
        this.expert = expert;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }
}
