package serverSide;

import java.net.*;
import java.io.*;

public class ServerSocket {
    public static void main(String[] args) {
        int port = 8080;
        try {
            // Create a server socket at port 8080
            java.net.ServerSocket serverSocket = new java.net.ServerSocket(port);

            // Wait for client connection
            System.out.println("Server waiting for client...");
            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");

            // Create input and output streams for communication
            BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outputToClient = new PrintWriter(socket.getOutputStream(), true);

            // Read data from client and display
            String messageFromClient;
            while ((messageFromClient = inputFromClient.readLine()) != null) {
                System.out.println("Client: " + messageFromClient);
                // Example response to client
                outputToClient.println("Server received: " + messageFromClient);
            }

            // Close resources
            inputFromClient.close();
            outputToClient.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
