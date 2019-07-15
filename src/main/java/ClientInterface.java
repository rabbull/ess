import java.net.Socket;

public interface ClientInterface extends Runnable {
    public void registerSocket(Socket socket);
    public void run();
}
