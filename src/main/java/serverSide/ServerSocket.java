package serverSide;

import java.net.*;
import java.io.*;
import org.json.*;

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

            // Create a JSON object
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Column", "Backlog");
            jsonObject.put("Name", "New task");
            jsonObject.put("Description", "JSON sent from server");

            // Read data from client and display
            String messageFromClient;
            while ((messageFromClient = inputFromClient.readLine()) != null) {
                System.out.println("Client: " + messageFromClient);
                // Example response to client
                outputToClient.println("Server received: " + messageFromClient);

                // Send JSON object to client
                outputToClient.println(jsonObject.toString());

                // Read JSON data from server
                String jsonString = inputFromClient.readLine();
                System.out.println("Received from server: " + jsonString);

                // Save JSON string to a JSON file
                try (FileWriter fileWriter = new FileWriter("JSON/server_data.json")) {
                    fileWriter.write(jsonString);
                    System.out.println("JSON data saved to server_data.json");
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
