package clientSide;

import java.net.*;
import java.io.*;

public class ClientSocket {
    public static void main(String[] args) {
        String ip = "localhost";
        int port = 8080;

        try {
            // Connect to server running on localhost at port 8080
            Socket socket = new Socket(ip, port);

            // Create input and output streams for communication
            BufferedReader inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outputToServer = new PrintWriter(socket.getOutputStream(), true);

            // Send data to server
            outputToServer.println("Hello, Server!");

            // Read server response
            String messageFromServer = inputFromServer.readLine();
            System.out.println("Server: " + messageFromServer);

            // Close resources
            inputFromServer.close();
            outputToServer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
