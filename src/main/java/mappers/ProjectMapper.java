package mappers;

import models.entities.Project;

import java.util.Collection;

public interface ProjectMapper extends GetAllable {
    int insertProject(Project project) throws Exception;
    int updateProject(Project project, int id) throws Exception;
    int deleteProject(int id) throws Exception;
    Project selectProjectById(int id) throws Exception;
}
