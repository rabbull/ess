package command.processors;

import dao.exceptions.DAONotFoundException;

public interface CommandProcessor {
    public Object run(String[] args) throws DAONotFoundException;
}
