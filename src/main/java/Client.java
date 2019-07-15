import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class Client implements ClientInterface {
    private Socket socket;

    public Client(Socket socket) {
        registerSocket(socket);
    }

    @Override
    public void registerSocket(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() { return this.socket; }

    @Override
    public void run() {
        try {
            socket.getOutputStream().write("SHIT\0FUCK".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            socket.getOutputStream().write("MY ASS".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
