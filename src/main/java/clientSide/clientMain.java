package clientSide;

import java.io.IOException;

public class clientMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        Client clientSide = new Client();
        clientSide.run();
    }
}
