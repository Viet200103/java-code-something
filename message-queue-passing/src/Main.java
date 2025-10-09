import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args) {
        BlockingQueue<Integer> requests  = new LinkedBlockingQueue<>();
        BlockingQueue<SquareResult> replies = new LinkedBlockingQueue<>();

        Squarer squarer = new Squarer(requests, replies);
        squarer.start();

        try {
            requests.put(42);
            System.out.println(requests.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}