package models.entities;

import models.Model;

public class Expert extends Model {
    private String name;
    private Boolean sex;  // true for male, vice versa
    private String phoneNumber;

    public Expert() {}

    public Expert(String name, boolean sex, String phoneNumber) {
        this.name = name;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
