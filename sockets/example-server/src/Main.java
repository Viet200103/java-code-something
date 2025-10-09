import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // create instance socket at port 59090 local ip: 0.0.0.0 IPv4, [::]:59090 IPv6
        try (var listener = new ServerSocket(59090)) {

            System.out.println("The date server is running...");
            while (true) {
                try (var socket = listener.accept()) {
                    var out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(new Date());
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}