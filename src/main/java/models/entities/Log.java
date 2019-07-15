package models.entities;

import models.Model;

import java.util.Date;

public class Log extends Model {
    private Date date;
    private String what;

    public Log(String what) {
        this.date = new Date();
        this.what = what;
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
