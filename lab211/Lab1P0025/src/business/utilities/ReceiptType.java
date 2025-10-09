package business.utilities;

public enum ReceiptType {
    IMPORT("Import receipt"), EXPORT("Export receipt");

    private final String value;

    ReceiptType(String value) {
        this.value = value;
    }

    public String  getValue() {
        return value;
    }
}
