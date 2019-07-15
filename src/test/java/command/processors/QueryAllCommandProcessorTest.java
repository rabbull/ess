package command.processors;

import dao.DAORouter;
import dao.ProjectDAO;
import dao.exceptions.DAOAlreadyRegisteredException;
import database.Database;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class QueryAllCommandProcessorTest {

    public QueryAllCommandProcessor processor;

    @Before
    public void setUp() {
        Database database = null;
        try {
            database = new Database("test_db");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        DAORouter router = new DAORouter();
        ProjectDAO projectDAO = new ProjectDAO(database);
        try {
            router.registerDAO("project", projectDAO);
        } catch (DAOAlreadyRegisteredException e) {
            e.printStackTrace();
        }
        processor = new QueryAllCommandProcessor(router);
    }

    @Test
    public void run() {
        processor.run(new String[]{"project"});
    }
}
