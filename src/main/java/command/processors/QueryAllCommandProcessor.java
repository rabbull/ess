package command.processors;

import dao.DAORouter;
import dao.exceptions.DAONotFoundException;

public class QueryAllCommandProcessor implements CommandProcessor {

    private DAORouter router;

    public QueryAllCommandProcessor(DAORouter router) {
        this.router = router;
    }

    @Override
    public Object run(String[] args) {
        Object DAO;
        try {
            DAO = router.getDAO(args[0]);
        } catch (DAONotFoundException e) {
            e.printStackTrace();
            return null;
        }
        System.out.println(DAO.getClass().toString());
        return null;
    }
}
