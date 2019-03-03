import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread{

    private Socket client;

    public Client(int port) throws IOException{
        try {
            client = new Socket("localhost", port);
            
        }
        catch (IOException e){
            e.printStackTrace();
            System.exit(-3);
        }
    }

    public void run(){
        if (client.isConnected()){
            System.out.println("Client connected on client side.");
        }
        
        // Keep running
        while(true){}
    }

    public void close(){
        try {
            client.close();
        }
        catch (IOException e){
            e.printStackTrace();
            System.exit(-3);
        }
    }

    public int readNumber(){
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            int num = Integer.parseInt(in.readLine());
            in.close();
            return num;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void sendNumber(int num){
        try{
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            out.print(num);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}