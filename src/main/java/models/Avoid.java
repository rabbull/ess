package models;

public class Avoid {
    private Project project;
    private Company company;
    private String reason;

    public Avoid(Project project, Company company, String reason) {
        this.project = project;
        this.company = company;
        this.reason = reason;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String[] toStringArray() {
        return new String[]{project.getCode(), company.getName(), reason};
    }
}
