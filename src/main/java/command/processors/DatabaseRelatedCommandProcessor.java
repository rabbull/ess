package command.processors;

import dao.BaseDAO;

import java.util.Map;

public abstract class DatabaseRelatedCommandProcessor {
    private Map<String, BaseDAO> DAOs;

}
