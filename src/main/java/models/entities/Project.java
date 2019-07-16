package models.entities;

import models.Model;

import java.util.Date;

public class Project extends Model {
    private String name;
    private Integer amount;
    private Integer numExpertExpected;
    private Integer numExpertReal;
    private Date biddingDateBegin;
    private Date biddingDateEnd;
    private Integer duration;
    private String location;
    private BiddingType biddingType;
    private BiddingMethod biddingMethod;
    private IndustryType industryType;
    private OrgType orgType;

    public Project(String name, Integer amount, Integer numExpertExpected, Integer numExpertReal, Date biddingDateBegin, Date biddingDateEnd, Integer duration, String location, BiddingType biddingType, BiddingMethod biddingMethod, IndustryType industryType, OrgType orgType) {
        this.name = name;
        this.amount = amount;
        this.numExpertExpected = numExpertExpected;
        this.numExpertReal = numExpertReal;
        this.biddingDateBegin = biddingDateBegin;
        this.biddingDateEnd = biddingDateEnd;
        this.duration = duration;
        this.location = location;
        this.biddingType = biddingType;
        this.biddingMethod = biddingMethod;
        this.industryType = industryType;
        this.orgType = orgType;
    }

    public BiddingType getBiddingType() {
        return biddingType;
    }

    public void setBiddingType(BiddingType biddingType) {
        this.biddingType = biddingType;
    }

    public BiddingMethod getBiddingMethod() {
        return biddingMethod;
    }

    public void setBiddingMethod(BiddingMethod biddingMethod) {
        this.biddingMethod = biddingMethod;
    }

    public IndustryType getIndustryType() {
        return industryType;
    }

    public void setIndustryType(IndustryType industryType) {
        this.industryType = industryType;
    }

    public OrgType getOrgType() {
        return orgType;
    }

    public void setOrgType(OrgType orgType) {
        this.orgType = orgType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getNumExpertExpected() {
        return numExpertExpected;
    }

    public void setNumExpertExpected(Integer numExpertExpected) {
        this.numExpertExpected = numExpertExpected;
    }

    public Integer getNumExpertReal() {
        return numExpertReal;
    }

    public void setNumExpertReal(Integer numExpertReal) {
        this.numExpertReal = numExpertReal;
    }

    public Date getBiddingDateBegin() {
        return biddingDateBegin;
    }

    public void setBiddingDateBegin(Date biddingDateBegin) {
        this.biddingDateBegin = biddingDateBegin;
    }

    public Date getBiddingDateEnd() {
        return biddingDateEnd;
    }

    public void setBiddingDateEnd(Date biddingDateEnd) {
        this.biddingDateEnd = biddingDateEnd;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
