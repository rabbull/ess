package models.entities;

import models.Model;

public class Profession extends Model {
    private Profession father;
    private Integer level;
    private String name;

    public Profession(Profession father, String name) {
        this.father = father;
        this.level = father.level + 1;
        this.name = name;
    }

    public Profession(String name) {
        this.father = null;
        this.level = 0;
        this.name = name;
    }

    public Profession getFather() {
        return father;
    }

    public Integer getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public void setFather(Profession father) {
        this.father = father;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setName(String name) {
        this.name = name;
    }
}
