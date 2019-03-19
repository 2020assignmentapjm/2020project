package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class Server extends Thread {

    // Sockets
    private ServerSocket server;
    private Socket[] clients;

    // I/O
    private BufferedReader[] in;
    private PrintWriter[] out;

    private int clientCons; // Number of client connections

    // Constants
    private final int TIMEOUT = 60000; // 1 minute
    private final int PLAYER_NUM;



    /**
     * Creates a server socket with a defined number of players and a port number
     * Also creates client and I/O arrays for server access and communication
     * @param playerNum number of players that will connect to the server
     * @param port port number to connect
     */
    public Server(int playerNum, int port) {

        PLAYER_NUM = playerNum;

        try{
            server = new ServerSocket(port);    // Create a server socket with the port number
            server.setSoTimeout(TIMEOUT);       // Set a timeout for the server socket

            System.out.println("Server created.");
        }
        catch (IOException e) {
            System.err.println("IO exception");
            System.exit(-1);
        }

        clients = new Socket[PLAYER_NUM];       // Create client array

        in = new BufferedReader[PLAYER_NUM];    // Create input stream array for clients
        out = new PrintWriter[PLAYER_NUM];      // Create output stream array for clients

        clientCons = 0;
    }


    /**
     * "run" method extended from Thread
     * It accepts and stores the clients, creates an input and output stream for clients and keeps the server running until interrupted
     */
    @Override
    public void run(){

        try {
            for(int i=0; i<PLAYER_NUM; i++){
                System.out.println("Waiting for clients.");
                clients[i] = server.accept();       // Accept new clients and store them in the array

                clientCons++;
                System.out.println("New client connected on server side with address: " + clients[i].getInetAddress());

                in[i] = new BufferedReader(new InputStreamReader(clients[i].getInputStream()));     // Input stream for current connected client
                out[i] = new PrintWriter(clients[i].getOutputStream(), true);                       // Output stream for current connected client
            }
        }
        catch (SocketTimeoutException s) {
            System.err.println("Socket timed out!");
            System.exit(-1);
        } 
        catch (IOException e) {
            System.err.println("IO exception");
            System.exit(-1);
        }


        // Keep server running until interrupted with busy-waiting
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
     * Closes the server
     */
    public void closeServer(){
        try {
            server.close();

            System.out.println("Server has closed");
        }
        catch (IOException e){
            System.err.println("IO exception");
            System.exit(-1);
        }
    }


    /**
     * Closes the clients and the input and output streams
     */
    public void closeClients(){
        try {
            for (int i=0; i<clientCons; i++){
                clients[i].close();

                in[i].close();
                out[i].close();

                System.out.println("Clients and I/O have closed");
            }
        }
        catch (IOException e){
            System.err.println("IO exception");
            System.exit(-1);
        }
    }


    /**
     * Reads a string from the input stream of a specific client.
     * If input stream is empty, waits until input stream contains an input.
     * The input is read from a queue.
     * @param clientIdx index of a specific client
     * @return the string read or null if: client doesn't exist, client is closed or IOException occurs
     */
    public String readMsg(int clientIdx){
        if (clients[clientIdx] == null){
            System.err.println("Client doesn't exist");
            return null;
        }
        if (clients[clientIdx].isClosed()){
            System.err.println("Client is closed");
            return null;
        }
        
        try{
            String input = in[clientIdx].readLine();

            if (input == null){
                System.err.println("Client is closed");
                return null;
            }

            return input;
        }
        catch (IOException e) {
            System.err.println("IO exception");
        }
        return null;
    }


    /**
     * Sends a string to the output stream of a specific client which gets added to a queue
     * @param msg the string to be outputed
     * @param clientIdx index of a specific client
     */
    public void sendMsg(String msg, int clientIdx){
        if (clients[clientIdx] == null){
            System.err.println("Client doesn't exist");
            return;
        }
        if (clients[clientIdx].isClosed()){
            System.err.println("Client is closed");
            return;
        }

        out[clientIdx].write(msg);
    }

    
    /**
     * Returns the number of clients connected to the server
     * 
     * @return number of connections
     */
    public int getclientCons(){
        return clientCons;
    }

    /**
     * Returns the IP address of the server
     * 
     * @return IP address in a string format
     */
    public String getHost(){
        try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			System.err.println("Unknown host exception");
        }
        return "error";
    }
}