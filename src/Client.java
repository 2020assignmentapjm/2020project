import java.io.IOException;
import java.net.Socket;

public class Client extends Thread{

    public Client(int port) throws IOException{
        try {
            Socket clientSocket = new Socket("localhost", port);
            System.out.println("Client connected on client side.");
            clientSocket.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}