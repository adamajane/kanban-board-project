package serverSide;

import java.net.*;
import java.io.*;

public class ServerSocket implements Runnable {
    private int port = 8080;

    public ServerSocket() {
    }

    @Override
    public void run() {
        try {
            // Create a server socket at port 8080
            java.net.ServerSocket serverSocket = new java.net.ServerSocket(8080);

            // Run indefinitely to accept multiple client connections
            while (true) {
                System.out.println("Server waiting for client...");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected.");

                // Handle each client connection in a separate thread
                ClientHandler clientHandler = new ClientHandler(socket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

    class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter outputToClient = new PrintWriter(clientSocket.getOutputStream(), true);

                // Read data sent from the client
                String messageFromClient = inputFromClient.readLine();
                System.out.println("Client: " + messageFromClient);

                // Example response to client
                outputToClient.println("Server received: " + messageFromClient);

                // Read JSON data from client
                String jsonString = inputFromClient.readLine();
                System.out.println("Received from client: " + jsonString);

                // Save JSON string to a JSON file
                try (FileWriter fileWriter = new FileWriter("src/main/resources/server_data.json")) {
                    fileWriter.write(jsonString);
                    System.out.println("JSON data saved to server_data.json");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Close resources
                inputFromClient.close();
                outputToClient.close();
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
