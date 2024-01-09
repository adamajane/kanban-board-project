package clientSide;

import org.json.JSONObject;

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

            // Create a JSON object
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Column", "Backlog");
            jsonObject.put("Name", "New task");
            jsonObject.put("Description", "JSON sent from client");

            // Send data to server
            outputToServer.println("Hello, Server!");

            // Read server response
            String messageFromServer = inputFromServer.readLine();
            System.out.println("Server: " + messageFromServer);

            // Read JSON data from server
            String jsonString = inputFromServer.readLine();
            System.out.println("Received from server: " + jsonString);

            // Send JSON object to client
            outputToServer.println(jsonObject.toString());

            // Save JSON string to a JSON file
            try (FileWriter fileWriter = new FileWriter("JSON/client_data.json")) {
                fileWriter.write(jsonString);
                System.out.println("JSON data saved to client_data.json");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Close resources
            inputFromServer.close();
            outputToServer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
