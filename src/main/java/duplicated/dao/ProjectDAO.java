package duplicated.dao;

import duplicated.database.Database;
import duplicated.models.Project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO extends BaseDAO {
    private static final String name = "project";
    private static final String[] title = {"code", "name", "amount", "industry", "organizationForm", "method", "category"};
    public static final String[] readableTitle = {"项目编码", "项目名称", "项目总额(万元)", "行业类型", "招标组织形式", "招标方式", "招标类型"};

    protected String getName() {
        return name;
    }

    protected String[] getTitle() {
        return title;
    }

    public String[] getReadableTitle() {
        return readableTitle;
    }

    public ProjectDAO(Database database) {
        super(database);
    }

    public List<Project> getProjects() {
        List<String[]> entries = database.getAllEntries(name);
        List<Project> projects = new ArrayList<>();

        for (String[] entry : entries) {
            int index = 0;
            String code = entry[index++];
            String name = entry[index++];
            int amount = Integer.valueOf(entry[index++]);
            String industry = entry[index++];
            String organizationForm = entry[index++];
            String method = entry[index++];
            String category = entry[index++];

            Project project = new Project(code, name, amount, method, industry, organizationForm, category);
            projects.add(project);
        }
        return projects;
    }

    public void addProjects(Iterable<Project> projects) {
        for (Project project : projects) {
            String[] entry = {project.getCode(), project.getName(), Long.toString(project.getAmount()), project.getIndustry(), project.getOrganizationForm(), project.getMethod(), project.getCategory()};
            database.addEntry(name, entry);
        }
        try {
            database.presist();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
