package models.entities;

import models.Model;

import java.util.Date;

public class Log extends Model {
    private Date date;
    private String what;
    private Integer project;

    public Log(Integer project, String what) {
        this.project = project;
        this.date = new Date();
        this.what = what;
    }

    public Integer getProject() {
        return project;
    }

    public void setProject(Integer project) {
        this.project = project;
    }

    public Date getDate() {
        return date;
    }

    public String getWhat() {
        return what;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setWhat(String what) {
        this.what = what;
    }
}
