package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadJSONFromFile {
    public ReadJSONFromFile() {
    }

    public String read(String filePath) {
        StringBuilder jsonContent = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                jsonContent.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonContent.toString();
    }
}
