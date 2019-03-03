import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server extends Thread {

    private ServerSocket server;
    private Socket[] clients;

    // Constants
    private int TIMEOUT = 50000;
    private int PLAYER_NUM = 1;


    public Server(int port) throws IOException {
        server = new ServerSocket(port);
        server.setSoTimeout(TIMEOUT);
        System.out.println("Server created.");

        clients = new Socket[PLAYER_NUM];
    }

    public void run(){
        try {
            for(int i=0; i<PLAYER_NUM; i++){
                System.out.println("Waiting for clients.");
                clients[i] = server.accept();
                System.out.println("New client connected on server side with address: " + clients[i].getInetAddress());
            }
        }
        catch (SocketTimeoutException s) {
            System.err.println("Socket timed out!");
            System.exit(-1);
        } 
        catch (IOException e) {
            e.printStackTrace();
            System.exit(-3);
        }

        // Keep running
        while(true){}
    }

    public void closeServer(){
        try {
            server.close();
        }
        catch (IOException e){
            e.printStackTrace();
            System.exit(-3);
        }
    }

    public void closeClients(){
        try {
            for (int i=0; i<PLAYER_NUM; i++){
                if (clients[i] != null){
                    clients[i].close();
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.exit(-3);
        }
    }

    public int readNumber(int clientIdx){
        if (clients[clientIdx] == null){
            System.err.println("client doesn't exist");
            System.exit(-1);
        }
        if (clients[clientIdx].isClosed()){
            System.err.println("client is closed");
            System.exit(-2);
        }

        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(clients[clientIdx].getInputStream()));

            int num = Integer.parseInt(in.readLine());
            in.close();
            return num;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void sendNumber(int num, int clientIdx){
        if (clients[clientIdx] == null){
            System.err.println("client doesn't exist");
            System.exit(-1);
        }
        if (clients[clientIdx].isClosed()){
            System.err.println("client is closed");
            System.exit(-2);
        }

        try{
            PrintWriter out = new PrintWriter(clients[clientIdx].getOutputStream(), true);
            out.print(num);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}