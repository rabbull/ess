package command;

import command.exceptions.InvalidCommandFormatException;

import java.io.IOException;
import java.io.InputStream;

public class Command {
    private String cmd;
    private String[] args;

    public Command(String commandString) {
        String[] splitCommand = commandString.split(" ");
        cmd = splitCommand[0];
        if (splitCommand.length > 1) {
            args = new String[splitCommand.length - 1];
            System.arraycopy(splitCommand, 1, args, 0, splitCommand.length - 1);
        } else {
            args = null;
        }
    }

    public String getCmd() {
        return cmd;
    }

    public String[] getArgs() {
        return args;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(cmd);
        stringBuilder.append(" ");
        for (String arg : args) {
            stringBuilder.append(arg);
            stringBuilder.append(" ");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public static Command getOneCommandFromInputStream(InputStream inputStream) throws InvalidCommandFormatException {
        StringBuilder sizeBufferBuilder = new StringBuilder();
        byte[] singleByte = new byte[1];
        while (true) {
            try {
                if (inputStream.read(singleByte) == -1) {
                    throw new InvalidCommandFormatException();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (singleByte[0] == ' ') {
                break;
            } else if (!Character.isDigit(singleByte[0])) {
                throw new InvalidCommandFormatException();
            }
            sizeBufferBuilder.append((char) singleByte[0]);
        }
        int size = Integer.valueOf(sizeBufferBuilder.toString());
        byte[] commandByteBuffer = new byte[size];
        try {
            assert (inputStream.read(commandByteBuffer) == size);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Command(new String(commandByteBuffer));
    }
}
