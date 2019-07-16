package command.processors;

import command.Command;

public class MessageCommandProcessor implements CommandProcessor {
    @Override
    public Command run(String[] args) {
        for (String arg : args) {
            System.out.println(arg);
        }
        System.out.println();
        return null;
    }
}
