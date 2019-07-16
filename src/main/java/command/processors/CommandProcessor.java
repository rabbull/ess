package command.processors;

import command.Command;

public interface CommandProcessor {
    Command run(String[] args);
}
