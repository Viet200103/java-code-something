package business.entities;

import business.utilities.DateUtils;
import business.utilities.ReceiptType;

import java.util.List;

public class Receipt extends Item {
    private final long timeCreated;
    private final ReceiptType type;

    private final String buyerName;
    private final String buyerAddress;

    private final String sellerName;
    private final String sellerAddress;

//    private String totalAmount;

    private final long shippingFee;

    private final List<ProductReceipt> itemList;

    public Receipt(
            String code,
            ReceiptType type,
            long timeCreated,
            String sellerName,
            String sellerAddress,
            String buyerName,
            String buyerAddress,
            long shippingFee,
            List<ProductReceipt> itemList
    ) {
        super(code);
        this.timeCreated = timeCreated;
        this.type = type;
        this.buyerName = buyerName;
        this.buyerAddress = buyerAddress;
        this.sellerName = sellerName;
        this.sellerAddress = sellerAddress;
        this.shippingFee = shippingFee;
        this.itemList = itemList;
    }


    public long getTimeCreated() {
        return timeCreated;
    }

    public ReceiptType getType() {
        return type;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public String getSellerName() {
        return sellerName;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }

    public long getShippingFee() {
        return shippingFee;
    }

    public List<ProductReceipt> getItemList() {
        return itemList;
    }

    public String buildReceipt() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(type.getValue().toUpperCase());
        sBuilder.append("\n\n");
        sBuilder.append("Date: ").append(DateUtils.formatDate(timeCreated));
        sBuilder.append("\n");
        sBuilder.append("Receipt code: ").append(getCode());
        sBuilder.append("\n\n");
        sBuilder.append("Seller: ");
        sBuilder.append("\n");
        sBuilder.append("Name: ").append(sellerName);
        sBuilder.append("\n");
        sBuilder.append("Address: ").append(sellerAddress);
        sBuilder.append("\n\n");
        sBuilder.append("Buyer: ");
        sBuilder.append("\n");
        sBuilder.append("Name: ").append(buyerName);
        sBuilder.append("\n");
        sBuilder.append("Address: ").append(buyerAddress);
        sBuilder.append("\n\n");
        sBuilder.append(buildItemListReceipt());
        sBuilder.append("\n\n");

        long totalAmount = getTotalAmount();

        sBuilder.append("Total Amount: ").append(totalAmount);
        sBuilder.append("\n");
        sBuilder.append("Shipping Fee: ").append(shippingFee);
        sBuilder.append("\n");
        sBuilder.append("Total Payment Amount: ").append(totalAmount + shippingFee);
        return sBuilder.toString();
    }

    private long getTotalAmount() {
        long amount = 0L;

        for (ProductReceipt pr: itemList) {
            amount += pr.getTotalAmount();
        }

        return amount;
    }

    private String buildItemListReceipt() {
        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append("Item No. | P. Code | Name | Quantity | Price | Total Amount");
        sBuilder.append("\n--------------------------------------------------------------------------------------\n");

        int i = 0;
        for (ProductReceipt pr : itemList) {
            sBuilder.append(++i).append(". ");
            sBuilder.append(pr.buildReceipt());
            sBuilder.append("\n");
        }

        sBuilder.append("--------------------------------------------------------------------------------------");

        return sBuilder.toString();
    }

    public boolean containProduct(String productCode) {
        return itemList.stream().anyMatch(
                (item) -> item.getCode().equals(productCode)
        );
    }
}
