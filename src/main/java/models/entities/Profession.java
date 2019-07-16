package models.entities;

import models.Model;

public class Profession extends Model {
    private Profession father;
    private Integer level;
    private String name;
    private String code;

    public Profession(Profession father, String name, String code) {
        this.father = father;
        if (father == null) {
            this.level = 1;
        }
        this.name = name;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
