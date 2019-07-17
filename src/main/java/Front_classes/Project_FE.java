package Front_classes;

import java.util.Date;

public class Project_FE {
    private String ID;

    private String Name;

    private Integer amount;

    private String location;

    private String time_bridge;

    private String bider;

    private Integer Expected_number;

    private String Biding_type;

    private String Biding_method;

    private  String industry_type;

    private  String organ_type;

    private Date start_date;

    private  Date end_date;

    public Project_FE(String ID, String name, Integer amount, String location, String time_bridge, String bider, Integer expected_number, String biding_type, String biding_method, String industry_type, String organ_type, Date start_date, Date end_date) {
        this.ID = ID;
        Name = name;
        this.amount = amount;
        this.location = location;
        this.time_bridge = time_bridge;
        this.bider = bider;
        Expected_number = expected_number;
        Biding_type = biding_type;
        Biding_method = biding_method;
        this.industry_type = industry_type;
        this.organ_type = organ_type;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime_bridge() {
        return time_bridge;
    }

    public void setTime_bridge(String time_bridge) {
        this.time_bridge = time_bridge;
    }

    public String getBider() {
        return bider;
    }

    public void setBider(String bider) {
        this.bider = bider;
    }

    public Integer getExpected_number() {
        return Expected_number;
    }

    public void setExpected_number(Integer expected_number) {
        Expected_number = expected_number;
    }

    public String getBiding_type() {
        return Biding_type;
    }

    public void setBiding_type(String biding_type) {
        Biding_type = biding_type;
    }

    public String getBiding_method() {
        return Biding_method;
    }

    public void setBiding_method(String biding_method) {
        Biding_method = biding_method;
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
}
