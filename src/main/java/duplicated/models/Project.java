package duplicated.models;

public class Project {
    private String code;
    private String name;
    private long amount;
    private String method;
    private String industry;
    private String organizationForm;
    private String category;

    public Project(String code, String name, long amount, String method, String industry, String organizationForm, String category) {
        this.code = code;
        this.name = name;
        this.amount = amount;
        this.method = method;
        this.industry = industry;
        this.organizationForm = organizationForm;
        this.category = category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getOrganizationForm() {
        return organizationForm;
    }

    public void setOrganizationForm(String organizationForm) {
        this.organizationForm = organizationForm;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String[] toStringArray() {
        return new String[]{code, name, Long.toString(amount), industry, organizationForm, method, category};
    }
}
