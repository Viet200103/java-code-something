import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class ChatClient {

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String username;


    public ChatClient(String host, int port, String username) {
        try {
            this.socket = new Socket(host, port);
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.username = username;
        } catch (IOException e) {
            closeEverything();
        }
    }

    public void sendMessage() {
        Objects.requireNonNull(socket);

        try {
            writer.write(username + '\n');
            writer.newLine();
            writer.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected() && scanner.hasNextLine()) {
                String message = scanner.nextLine();
                writer.write(username + ": " + message);
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            closeEverything();
        }
    }

    private void closeEverything() {
        try {
            if (reader != null) reader.close();
            if (writer != null) writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listenForMessages() {
        new Thread(() -> {
            String msgFromGroupChat;
            System.out.println("Waiting for messages...");
            while (true) {
                try {
                    msgFromGroupChat = reader.readLine();
                    if (msgFromGroupChat != null) {
                        System.out.println(msgFromGroupChat);
                    }
                } catch (Exception e) {
                    closeEverything();
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        System.out.println("Enter your username for the group chat: ");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();

        ChatClient chatClient = new ChatClient("127.0.0.1", 59001, username);
        chatClient.listenForMessages();
        chatClient.sendMessage();
    }
}
