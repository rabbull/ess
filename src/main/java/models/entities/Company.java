package models.entities;

import models.Model;

public class Company extends Model {
    private String name;

    public Company() {}

    public Company(String name) { this.name = name; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
