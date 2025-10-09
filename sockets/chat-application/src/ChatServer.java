import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    private ServerSocket serverSocket;

    public ChatServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void startServer() throws IOException {
        while (!serverSocket.isClosed()) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client is connected");

            ClientHandler clientHandler = new ClientHandler(clientSocket);

            Thread thread = new Thread(clientHandler);
            thread.start();
        }
    }

    public void closeServer() throws IOException {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ChatServer server = new ChatServer(59001);
        server.startServer();
    }
}