import command.Command;
import command.CommandRouter;
import command.exceptions.*;
import command.processors.MessageCommandProcessor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements ServerInterface {
    private ServerSocket serverSocket;

    private CommandRouter commandRouter;

    public static SqlSession session;

    public Server(ServerSocket serverSocket) {
        this.registerServerSocket(serverSocket);
        commandRouter = new CommandRouter();
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("mybatis.cfg.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
        session = factory.openSession();
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
        OutputStream outputStream;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
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
            Command ret;
            try {
                ret = commandRouter.resolve(command);
            } catch (CommandNotAcceptedException e) {
                e.printStackTrace();
                return;
            }
            if (ret != null) {
                try {
                    outputStream.write(ret.serialize());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
