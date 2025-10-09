import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try(var socket = new Socket("127.0.0.1", 59090)){

            try (var in = new Scanner(socket.getInputStream())) {
                System.out.println("Server response: " + in.nextLine());
            }

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}