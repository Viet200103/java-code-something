package business.entities;

public class ProductReceipt extends Item {

    private final String productName;

    private final int quantity;
    private final int price;

    public ProductReceipt(String code, String productName, int quantity, int price) {
        super(code);
        this.quantity = quantity;
        this.price = price;
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public String buildReceipt() {
        return String.format("%s | %s | %d | %d | %d", getCode(), productName, quantity, price, getTotalAmount());
    }

    public long getTotalAmount() {
        return (long) price * quantity;
    }
}
