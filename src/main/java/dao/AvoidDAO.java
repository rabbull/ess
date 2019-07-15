package dao;

import database.Database;
import models.Avoid;
import models.Company;
import models.Profession;
import models.Project;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Predicate;

public class AvoidDAO extends BaseDAO {
    private static final String name="avoid";
    private static final String[] title = {"project_code", "company", "reason"};
    private static final String[] readableTitle = {"", "", ""};

    private ProjectDAO projectDAO;
    private CompanyDAO companyDAO;

    protected AvoidDAO(Database database, ProjectDAO projectDAO, CompanyDAO companyDAO) {
        super(database);
        this.projectDAO = projectDAO;
        this.companyDAO = companyDAO;
    }


    @Override
    protected String getName() {
        return name;
    }

    @Override
    protected String[] getTitle() {
        return title;
    }

    @Override
    public String[] getReadableTitle() {
        return readableTitle;
    }

    public List<Avoid> getAvoids() throws IOException {
        List<Avoid> avoids = new ArrayList<>();
        List<Project> projects = projectDAO.getProjects();
        List<Company> companies = companyDAO.getCompanies();

        List<String[]> entries = database.getAllEntries(name);
        for (String[] entry : entries) {
            String projectCode = entry[0];
            String companyName = entry[1];
            String reason = entry[2];

            Project project = null;
            for (Project proj : projects) {
                if (proj.getCode().equals(projectCode)) {
                    project = proj;
                    break;
                }
            }
            if (project == null) {
                throw new IOException("undefined project code: " + projectCode);
            }

            Company company = null;
            for (Company comp : companies) {
                if (comp.getName().equals(companyName)) {
                    company = comp;
                    break;
                }
            }
            if (company == null) {
                throw new IOException("undefined company name: " + companyName);
            }

            Avoid avoid = new Avoid(project, company, reason);
            avoids.add(avoid);
        }
        return avoids;
    }

    public void addAvoids(Iterable<Avoid> avoids) {
        for (Avoid avoid : avoids) {
            database.addEntry(name, avoid.toStringArray());
        }
        try {
            database.presist();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteAvoids(Iterable<Avoid> avoids) {
        for (Avoid avoid : avoids) {
            database.deleteEntry(name, (entry) -> entry[0].equals(avoid.getProject().getCode()) && entry[1].equals(avoid.getCompany().getName()));
        }
    }
}
