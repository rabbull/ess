package models.entities;

import models.Model;

public class Project extends Model {

    private String name;
    private String description;
    private Integer expectedExpertNumber;
    private Integer realExpertNumber;
    private Boolean finished;

    public Project(){}

    public Project(String name, String description, int expectedExpertNumber, int realExpertNumber) {
        this.name = name;
        this.description = description;
        this.expectedExpertNumber = expectedExpertNumber;
        this.realExpertNumber = realExpertNumber;
        this.finished = false;
    }

    public Project(String name, String description, int expectedExpertNumber, int realExpertNumber, boolean finished) {
        this.name = name;
        this.description = description;
        this.expectedExpertNumber = expectedExpertNumber;
        this.realExpertNumber = realExpertNumber;
        this.finished = finished;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getExpectedExpertNumber() {
        return expectedExpertNumber;
    }

    public Integer getRealExpertNumber() {
        return realExpertNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExpectedExpertNumber(Integer expectedExpertNumber) {
        this.expectedExpertNumber = expectedExpertNumber;
    }

    public void setRealExpertNumber(Integer realExpertNumber) {
        this.realExpertNumber = realExpertNumber;
    }
}
