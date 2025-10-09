package business.utilities;

public enum ProductType {
    DAILY(0), LONG_LIFE(1), EMPTY(-1);

    private final int value;

    ProductType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
