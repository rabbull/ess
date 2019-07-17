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
    private Integer biddingType;
    private Integer biddingMethod;
    private Integer industryType;
    private Integer orgType;
    private String code;
    private String bidder;

    public Project(String name, Integer amount, Integer numExpertExpected, Integer numExpertReal, Date biddingDateBegin, Date biddingDateEnd, Integer duration, String location, Integer biddingType, Integer biddingMethod, Integer industryType, Integer orgType, String code, String bidder) {
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
        this.code = code;
        this.bidder = bidder;
    }

    public Integer getBiddingType() {
        return biddingType;
    }

    public void setBiddingType(Integer biddingType) {
        this.biddingType = biddingType;
    }

    public Integer getBiddingMethod() {
        return biddingMethod;
    }

    public void setBiddingMethod(Integer biddingMethod) {
        this.biddingMethod = biddingMethod;
    }

    public Integer getIndustryType() {
        return industryType;
    }

    public void setIndustryType(Integer industryType) {
        this.industryType = industryType;
    }

    public Integer getOrgType() {
        return orgType;
    }

    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBidder() {
        return bidder;
    }

    public void setBidder(String bidder) {
        this.bidder = bidder;
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
