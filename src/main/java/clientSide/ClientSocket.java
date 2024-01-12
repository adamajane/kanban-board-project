package clientSide;


import java.net.*;
import java.io.*;

public class ClientSocket {

    public ClientSocket() {
    }

    public void request(String json) {
        try {
            // Connect to server running on localhost at port 8080
            Socket socket = new Socket("10.209.152.92", 8080);

            // Create input and output streams for communication
            BufferedReader inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outputToServer = new PrintWriter(socket.getOutputStream(), true);

            // Send data to server
            outputToServer.println("Hello, Server!");

            // Read server response
            String messageFromServer = inputFromServer.readLine();
            System.out.println("Server: " + messageFromServer);

            // Send JSON object to client
            outputToServer.println(json);

            // Close resources
            inputFromServer.close();
            outputToServer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
