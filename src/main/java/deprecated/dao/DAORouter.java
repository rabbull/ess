package deprecated.dao;

import deprecated.dao.exceptions.DAOAlreadyRegisteredException;
import deprecated.dao.exceptions.DAONotFoundException;

import java.util.HashMap;
import java.util.Map;

public class DAORouter {
    private Map<String, Object> DAOs;

    public DAORouter() {
        DAOs = new HashMap<>();
    }

    public Object getDAO(String name) throws DAONotFoundException {
        if (!DAOs.containsKey(name)) {
            throw new DAONotFoundException();
        }
        return DAOs.get(name);
    }

    public void registerDAO(String name, Object DAO) throws DAOAlreadyRegisteredException {
        if (DAOs.containsKey(name)) {
            throw new DAOAlreadyRegisteredException();
        }
        DAOs.put(name, DAO);
    }
}
