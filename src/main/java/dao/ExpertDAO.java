package dao;

import database.Database;
import models.Company;
import models.Expert;
import models.Profession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExpertDAO extends BaseDAO {
    public static final String name = "expert";
    public static final String[] title = {"name", "gender", "phone", "company"};
    public static final String[] readableTitle = {"姓名", "性别", "电话号码", "单位"};

    private ProfessionDAO professionDAO;
    private CompanyDAO companyDAO;

    public String[] getReadableTitle() {
        return readableTitle;
    }

    protected String getName() {
        return name;
    }

    protected String[] getTitle() {
        return title;
    }

    public ExpertDAO(Database database, ProfessionDAO professionDAO, CompanyDAO companyDAO) {
        super(database);
        this.professionDAO = professionDAO;
        this.companyDAO = companyDAO;
    }

    public List<Expert> getExperts() {
        List<String[]> entries = database.getAllEntries(name);
        List<Expert> experts = new ArrayList<>();
        List<Profession> professions = professionDAO.getProfessions();
        List<Company> companies = companyDAO.getCompanies();
        Company company = null;
        for (String[] entry : entries) {
            for (Company comp : companies) {
                if (comp.getName().equals(entry[3])) {
                    company = comp;
                    break;
                }
            }
            if (company != null) {
                experts.add(new Expert(entry[0], entry[1], entry[2], company));
                company = null;
            }
        }
        return experts;
    }

    public void addExperts(Iterable<Expert> experts) {
        for (Expert expert : experts) {
            database.addEntry(name, expert.toStringArray());
        }
        try {
            database.presist();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteExperts(Iterable<Expert> experts) {
        for (Expert expert : experts) {
            database.deleteEntry(name, "name", expert.getName());
        }
        try {
            database.presist();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
