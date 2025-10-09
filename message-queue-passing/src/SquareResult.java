public class SquareResult {
    private final int input;
    private final int output;

    public SquareResult(int input, int output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public String toString() {
        return input + "^2= " + output;
    }
}
