package duplicated.dao;


import duplicated.database.Database;
import duplicated.models.Company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAO extends BaseDAO {
    private static final String name = "company";
    private static final String[] title = {"name"};
    private static final String[] readableTitle = {"公司名称"};

    protected String getName() {
        return name;
    }

    protected String[] getTitle() {
        return title;
    }

    public String[] getReadableTitle() { return readableTitle; }

    public CompanyDAO(Database database) {
        super(database);
    }

    public List<Company> getCompanies() {
        List<String[]> entries = database.getAllEntries(name);
        List<Company> companies = new ArrayList<>();
        for (String[] entry : entries) {
            companies.add(new Company(entry[0]));
        }
        return companies;
    }

    public void addCompanies(Iterable<Company> companies) {
        for (Company company : companies) {
            database.addEntry(name, company.toStringArray());
        }
        try {
            database.presist();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteCompanies(Iterable<Company> companies) {
        for (Company company : companies) {
            database.deleteEntry(name, "name", company.getName());
        }
        try {
            database.presist();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
