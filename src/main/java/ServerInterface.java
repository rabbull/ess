import java.net.ServerSocket;

public interface ServerInterface extends Runnable {
    public void registerServerSocket(ServerSocket serverSocket);
    public void run();
}
