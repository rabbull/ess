package command;

import command.exceptions.CommandAlreadyRegisteredException;
import command.exceptions.CommandNotAcceptedException;
import command.processors.CommandProcessor;
import dao.exceptions.DAONotFoundException;

import java.util.HashMap;
import java.util.Map;

public class CommandRouter {

    private Map<String, CommandProcessor> commandProcessors;

    public CommandRouter() {
        commandProcessors = new HashMap<>();
    }

    public Object resolve(Command command) throws CommandNotAcceptedException {
        String cmd = command.getCmd();
        String[] args = command.getArgs();
        if (!commandProcessors.containsKey(cmd)) {
            throw new CommandNotAcceptedException();
        }
        try {
            return commandProcessors.get(cmd).run(args);
        } catch (DAONotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void registerCommandProcessor(String cmd, CommandProcessor processor) throws CommandAlreadyRegisteredException {
        if (commandProcessors.containsKey(cmd)) {
            throw new CommandAlreadyRegisteredException();
        }
        commandProcessors.put(cmd, processor);
    }
}
