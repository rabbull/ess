package duplicated.dao;


import duplicated.database.Database;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public abstract class BaseDAO {
    protected Database database;

    protected BaseDAO(Database database) {
        this.database = database;
    }

    protected abstract String getName();
    protected abstract String[] getTitle();
    public abstract String[] getReadableTitle();

    public void initialize() throws IOException {
        List<String> pages = database.getAllPages();
        String name = getName();
        String[] title = getTitle();
        if (pages.contains(name)) {
            if (database.getTitleOfPage(name).equals(Arrays.asList(title))) {
                return;
            }
            throw new IOException("conflict title");
        }
        database.createPage(name, title);
        try {
            database.presist();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
