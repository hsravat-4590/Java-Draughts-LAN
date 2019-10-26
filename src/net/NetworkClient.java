import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

//package lan_draughts.net; // part of the network package

public class NetworkClient{

    // Variable declarations
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String message = "";
    private String serverIP;
    private Socket connection;
    private int port = 6789; // Constant port

    public NetworkClient(String sIP){
        this.setVisible(true);
        serverIP = sIP;
    }
    public void startRunning()
    {
        try
        {
            println("Attempting Connection with host server...");
            try
            {
                connection = new Socket(InetAddress.getByName(serverIP),port);
            }catch(IOException ioException)
            {
                system.out.println("WARNING: Server might be down!");
            }
            system.out.println("Connected to: " + connection.getInetAddress().getHostName());


            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());

            whileChatting();
        }
        catch(IOException ioException)
        {
            ioException.printStackTrace();
        }
    }

    private void whileChatting() throws IOException
    {
        do{
            try
            {
                message = (String) input.readObject();
            }
            catch(ClassNotFoundException classNotFoundException)
            {
            }
        }while(!message.equals("Client - END"));
    }


    private void sendMessage(String message)
    {
        try
        {
            output.writeObject("Client - " + message);
            output.flush();
        }
        catch(IOException ioException)
        {
            system.out.println("\n Unable to send a message");
        }
    }
}

