package duplicated.models;

public class Expert {
    private String name;
    private Gender gender;
    private String phone;
    private Company company;

    public Expert(String name, String gender, String phone, Company company) {
        this.name = name;
        try {
            this.gender = new Gender(gender);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.phone = phone;
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String[] toStringArray() {
        return new String[]{name, gender.toString(), phone, company.getName()};
    }
}
