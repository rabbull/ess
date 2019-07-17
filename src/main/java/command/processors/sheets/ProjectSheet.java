package command.processors.sheets;

import models.entities.*;

public class ProjectSheet {
    private Project project;
    private BiddingType biddingType;
    private BiddingMethod biddingMethod;
    private IndustryType industryType;
    private OrgType orgType;

    public ProjectSheet(Project project, BiddingType biddingType, BiddingMethod biddingMethod, IndustryType industryType, OrgType orgType) {
        this.project = project;
        this.biddingType = biddingType;
        this.biddingMethod = biddingMethod;
        this.industryType = industryType;
        this.orgType = orgType;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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
}
