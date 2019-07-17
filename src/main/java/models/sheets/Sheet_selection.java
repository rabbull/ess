package models.sheets;

import com.alibaba.fastjson.JSON;
import models.entities.Profession;

import java.util.Date;
import java.util.List;

public class Sheet_selection {
    private String proj_name;

    private long biding_amount;

    private String biding_location;

    private long biding_timebridge;

    private String bider;

    private String biding_type;

    private String biding_method;

    private String industry_type;

    private String organ_type;

    private Date start_date;

    private Date end_date;

    private List<String> Avoid_companies;

    private List<String> main_Conditions;

    private List<String> aux_Conditions;

    public Sheet_selection(String proj_name, long biding_amount, String biding_location, long biding_timebridge, String bider, String biding_type, String biding_method, String industry_type, String organ_type, Date start_date, Date end_date, List<String> avoid_companies, List<String> main_Conditions, List<String> aux_Conditions) {
        this.proj_name = proj_name;
        this.biding_amount = biding_amount;
        this.biding_location = biding_location;
        this.biding_timebridge = biding_timebridge;
        this.bider = bider;
        this.biding_type = biding_type;
        this.biding_method = biding_method;
        this.industry_type = industry_type;
        this.organ_type = organ_type;
        this.start_date = start_date;
        this.end_date = end_date;
        Avoid_companies = avoid_companies;
        this.main_Conditions = main_Conditions;
        this.aux_Conditions = aux_Conditions;
    }

    public String getProj_name() {
        return proj_name;
    }

    public void setProj_name(String proj_name) {
        this.proj_name = proj_name;
    }

    public long getBiding_amount() {
        return biding_amount;
    }

    public void setBiding_amount(long biding_amount) {
        this.biding_amount = biding_amount;
    }

    public String getBiding_location() {
        return biding_location;
    }

    public void setBiding_location(String biding_location) {
        this.biding_location = biding_location;
    }

    public long getBiding_timebridge() {
        return biding_timebridge;
    }

    public void setBiding_timebridge(long biding_timebridge) {
        this.biding_timebridge = biding_timebridge;
    }

    public String getBider() {
        return bider;
    }

    public void setBider(String bider) {
        this.bider = bider;
    }

    public String getBiding_type() {
        return biding_type;
    }

    public void setBiding_type(String biding_type) {
        this.biding_type = biding_type;
    }

    public String getBiding_method() {
        return biding_method;
    }

    public void setBiding_method(String biding_method) {
        this.biding_method = biding_method;
    }

    public String getIndustry_type() {
        return industry_type;
    }

    public void setIndustry_type(String industry_type) {
        this.industry_type = industry_type;
    }

    public String getOrgan_type() {
        return organ_type;
    }

    public void setOrgan_type(String organ_type) {
        this.organ_type = organ_type;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public List<String> getAvoid_companies() {
        return Avoid_companies;
    }

    public void setAvoid_companies(List<String> avoid_companies) {
        Avoid_companies = avoid_companies;
    }

    public List<String> getMain_Conditions() {
        return main_Conditions;
    }

    public void setMain_Conditions(List<String> main_Conditions) {
        this.main_Conditions = main_Conditions;
    }

    public List<String> getAux_Conditions() {
        return aux_Conditions;
    }

    public void setAux_Conditions(List<String> aux_Conditions) {
        this.aux_Conditions = aux_Conditions;
    }

    public String toString(){
        return JSON.toJSONString(this);
    }
}
