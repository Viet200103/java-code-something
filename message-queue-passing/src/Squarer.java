import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class Squarer {

    private final BlockingQueue<Integer> in;
    private final BlockingQueue<SquareResult> out;

    public Squarer(BlockingQueue<Integer> requests, BlockingQueue<SquareResult> replies) {
        this.in = requests;
        this.out = replies;
    }

    public void start() {
        new Thread(() -> {
            while (true) {
                try {
                    int x = in.take();
                    int y = x * x;
                    out.put(new SquareResult(x, y));
                } catch (InterruptedException ie) {
                    break;
                }
            }
        }).start();
    }
}
