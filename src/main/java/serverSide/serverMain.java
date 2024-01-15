package serverSide;

public class serverMain {
    public static void main(String[] args) {
        ServerRemoteSpace serverSide = new ServerRemoteSpace();
        Thread serverThread = new Thread(serverSide);
        serverThread.start();
    }
}