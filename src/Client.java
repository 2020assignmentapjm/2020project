import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread{

    // Socket
    private Socket client;

    // I/O
    private BufferedReader in;
    private PrintWriter out;



    /**
     * Creates a client socket with a host name and a port number
     * @param host host name of the server
     * @param port port number of the server
     */
    public Client(String host, int port) {
        try {
            client = new Socket(host, port);
            
            System.out.println("Client created.");
        }
        catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
    }


    /**
     * "run" method extended from Thread
     * It creates an input and output stream for the client and keeps the client running until interrupted
     */
    public void run(){
        if (client.isConnected()){
            System.out.println("Client connected on client side.");
        }
        
        try{
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));    // Input stream for current connected client
            out = new PrintWriter(client.getOutputStream(), true);                      // Output stream for current connected client
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        // Keep client running until interrupted with busy-waiting
        while(true){
            try{
                Thread.sleep(1000);     // Make the server thread sleep for 1 second in order to reduce processor load
            }
            catch (InterruptedException ex){
                Thread.currentThread().interrupt();     // To prevent the thread not shutting down correctly
                System.err.println("Server interrupted");
                break;
            }
        }
    }


    /**
     * Closes the client and the input and output stream
     */
    public void close(){
        try {
            client.close();

            in.close();
            out.close();
        }
        catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
    }


    /**
     * Reads a number from the server with the input stream of the client
     * If input stream is empty, waits until input stream contains an input
     * The input is read from a queue
     * @return the number read or -1 if client doesn't exist, client is not connected, server is closed or IOException occurs
     */
    public int readNumber(){
        if (client == null){
            System.err.println("Client doesn't exist");
            return -1;
        }
        if (!client.isConnected()){
            System.err.println("Client is not connected");
            return -1;
        }

        try{
            String input = in.readLine();

            if (input == null){
                System.err.println("Server is closed");
                return -1;
            }

            int num = Integer.parseInt(input);
            return num;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }


    /**
     * Sends a number to the server which gets added to a queue
     * @param num the number to be outputed
     */
    public void sendNumber(int num){
        if (client == null){
            System.err.println("Server doesn't exist");
            return;
        }
        if (!client.isConnected()){
            System.err.println("Server is closed");
            return;
        }

        out.println(num);
    }
}