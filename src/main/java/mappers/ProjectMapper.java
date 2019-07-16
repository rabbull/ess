package mappers;

import models.entities.Project;

import java.util.Collection;

public interface ProjectMapper {
    public int insertProject(Project project) throws Exception;
    public int updateProject(Project project, int id) throws Exception;
    public int deleteProject(int id) throws Exception;
    public Project selectProjectById(int id) throws Exception;
    public Collection<Project> selectAllProjects() throws Exception;
}
