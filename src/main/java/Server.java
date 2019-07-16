import command.Command;
import command.CommandRouter;
import command.exceptions.*;
import command.processors.MessageCommandProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements ServerInterface {
    private ServerSocket serverSocket;

    private CommandRouter commandRouter;

    public Server(ServerSocket serverSocket) {
        this.registerServerSocket(serverSocket);
        commandRouter = new CommandRouter();

        initialize();
    }

    @Override
    public void registerServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    private void initialize() {
        try {
            commandRouter.registerCommandProcessor("msg", new MessageCommandProcessor());
        } catch (CommandAlreadyRegisteredException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Socket socket;
        try {
            socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        InputStream inputStream;
        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while (true) {
            Command command;
            try {
                command = Command.getOneCommandFromInputStream(inputStream);
            } catch (InvalidCommandFormatException e) {
                e.printStackTrace();
                return;
            }
            try {
                commandRouter.resolve(command);
            } catch (CommandNotAcceptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
