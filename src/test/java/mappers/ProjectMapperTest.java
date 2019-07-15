package mappers;

import junit.framework.TestCase;
import models.entities.Project;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class ProjectMapperTest extends TestCase {
    private static SqlSession session;

    public void setUp() throws Exception {

        Reader reader;
        SqlSessionFactory sessionFactory;
        try {
            reader = Resources.getResourceAsReader("mybatis.cfg.xml");
            sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        session = sessionFactory.openSession();
    }

    public void testInsertProject() {
        Project project = new Project("prj", "a test project", 10, 2, false);
        ProjectMapper mapper = session.getMapper(ProjectMapper.class);
        try {
            mapper.insertProject(project);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.rollback();
        }
    }

    public void testUpdateProject() {
    }

    public void testDeleteProject() {
    }

    public void testSelectProjectById() {
    }

    public void testSelectAllProjects() {
    }
}