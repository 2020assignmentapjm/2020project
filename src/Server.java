import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server extends Thread {

    private ServerSocket serverSocket;

    // Constants
    private int TIMEOUT = 50000;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(TIMEOUT);
        System.out.println("Server created.");
    }

    public void run(){
        while(true){
            try {
                System.out.println("Waiting for clients.");
                Socket server = serverSocket.accept();
                System.out.println("Client connected on server side.");
                server.close();
                break;
            }
            catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } 
            catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
