package models.relations;

import models.Model;
import models.entities.Profession;

public class Selects extends Model {
    private Profession main;
    private Profession substitute;

    public Selects(Profession main, Profession substitute) {
        this.main = main;
        this.substitute = substitute;
    }

    public Profession getMain() {
        return main;
    }

    public void setMain(Profession main) {
        this.main = main;
    }

    public Profession getSubstitute() {
        return substitute;
    }

    public void setSubstitute(Profession substitute) {
        this.substitute = substitute;
    }
}
