import java.net.ServerSocket;
import java.net.Socket;


public class Main {

    public static void main(String[] args) {
        int begin = 60000;
        if(args.length > 0) {
            boolean invalid = false;
            try {
                begin = Integer.valueOf(args[1]);
            } catch (NumberFormatException e) {
                invalid = true;
            }
            if (invalid || begin > 65535) {
                System.err.println("[EE] Invalid port");
                System.exit(-1);
            }
        }
        int port = begin;
        ServerSocket serverSocket;
        Socket clientSocket;
        do {
            try {
                serverSocket = new ServerSocket(port);
                clientSocket = new Socket("127.0.0.1", port);
            } catch (Exception e) {
                port++;
                continue;
            }
            break;
        } while (true);

        final ClientInterface clientObj = new SwingNovice();
        clientObj.registerSocket(clientSocket);
        Thread clientThread = new Thread(clientObj);

        final Server serverObj = new Server(serverSocket);
        Thread serverThread = new Thread(serverObj);

        clientThread.start();
        serverThread.start();

        try {
            clientThread.join();
            serverThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
