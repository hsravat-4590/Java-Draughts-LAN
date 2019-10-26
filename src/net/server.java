import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

package lan_draughts.net; // part of the network package

public class LANServer{

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket connection;
    private ServerSocket server;
    private int totalClients = 2; // Only two players at a time
    private int port = 6789; // Default port number.

    public LANServer() {

        initComponents();
        this.setTitle("Server");
        this.setVisible(true);
    }
    public void startRunning()
    {
        try
        {
            server=new ServerSocket(port, totalClients);
            while(true)
            {
                try
                {
                    connection=server.accept();
                    println(" Now Connected to "+connection.getInetAddress().getHostName());


                    output = new ObjectOutputStream(connection.getOutputStream());
                    output.flush();
                    input = new ObjectInputStream(connection.getInputStream());

                    whileChatting();

                }catch(EOFException eofException)
                {
                }
            }
        }
        catch(IOException ioException)
        {
            ioException.printStackTrace();
        }
    }

    private void whileChatting() throws IOException
    {
        String message="";
        do{
            try
            {
                message = (String) input.readObject();
                println(message); // Incoming messages.
            }catch(ClassNotFoundException classNotFoundException)
            {

            }
        }while(!message.equals("Client - END")); // Termination message.
    }
    private void sendMessage(String message)
    {
        try
        {
            output.writeObject("Server - " + message); //Send the message as the server
            output.flush();
        }
        catch(IOException ioException)
        {
            println("ERROR: Unable to send the message")
        }
    }
}