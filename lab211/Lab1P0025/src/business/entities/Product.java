package business.entities;

import business.utilities.ProductType;

public class Product extends Item {
    private final String name;
    private final ProductType type;

    private final String maker;

    private final String manufacturingDate;
    private final String expirationDate;

    private final int quantity;

    public Product(
            String code,
            ProductType type,
            String name,
            String maker,
            String manufacturingDate,
            String expirationDate,
            int quantity
    ) {
        super(code);
        this.name = name;
        this.type = type;
        this.maker = maker;
        this.manufacturingDate = manufacturingDate;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getMaker() {
        return maker;
    }

    public ProductType getType() {
        return type;
    }

    public String getManufacturingDate() {
        return manufacturingDate;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return String.format(
                "%s,%s,%s,%s,%s,%s,%s",
                getCode(), type, name, maker, manufacturingDate, expirationDate, quantity
        );
    }
}
