package application.ui;

import application.utilities.DataIOHelper;
import business.entities.Product;
import business.entities.ProductReceipt;
import business.entities.Receipt;
import business.services.ItemService;
import business.services.WarehouseService;
import business.utilities.DataValidation;
import business.utilities.ReceiptType;
import data.repositories.IStoreRepository;
import data.repositories.StoreRepository;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WarehouseManagement {
    private final DataIOHelper inputHelper = DataIOHelper.getInstance();

    private final IStoreRepository storeRepository = StoreRepository.getInstance();
    private final ItemService<Receipt> itemService = new WarehouseService(storeRepository);

    public void displayMenu() {

        try {
            int choice;
            boolean isRunning = true;

            do {
                System.out.println("******Receipt Management******");
                Menu.print(
                        "1. Add import receipt" + "|" +
                                "2. Add export receipt" + "|" +
                                "3. Stop"
                );
                choice = inputHelper.getIntegerNumber();
                System.out.println("--------------------------------");

                switch (choice) {
                    case 1 -> addImportReceipt();
                    case 2 -> addExportReceipt();
                    case 3 -> isRunning = false;

                    default -> Menu.printRequireNotFound();
                }
            } while (isRunning);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void addImportReceipt() {
        addReceipt(ReceiptType.IMPORT);
    }

    private void addExportReceipt() {
        addReceipt(ReceiptType.EXPORT);
    }

    private void addReceipt(ReceiptType type) {
        try {
            String code = storeRepository.generateReceiptCode();
            Receipt pr = getReceipt(type, code);
            itemService.add(pr);
        } catch (Exception e) {
            DataIOHelper.printlnNotification(e.getMessage());
        }
    }

    @NotNull
    private Receipt getReceipt(ReceiptType type, String code) {
        long time = getSystemTime();

        String sellerName = getSellerName();
        String sellerAddress = getSellerAddress();

        String buyerName = getBuyerName();
        String buyerAddress = getBuyerAddress();

        List<ProductReceipt> productReceiptList = getProductReceiptList();

        long shippingFee = getShippingFee();

        return new Receipt(
                code, type, time,
                sellerName, sellerAddress,
                buyerName, buyerAddress,
                shippingFee,
                productReceiptList
        );
    }

    private List<ProductReceipt> getProductReceiptList() {
        DataIOHelper.printlnNotification("Enter product list of receipt");

        List<ProductReceipt> productReceiptList = new ArrayList<>();
        boolean isContinue;

        do {
            ProductReceipt pReceipt = getProductReceipt();
            if (pReceipt != null) {
                productReceiptList.add(pReceipt);
            }

            DataIOHelper.printlnMessage("--------------------------------");
            DataIOHelper.printMessage("Do you want to continue(Y/N)? ");
            isContinue = inputHelper.getString().matches("[\\s{y, Y}]");
            DataIOHelper.printlnMessage("--------------------------------");
        } while (isContinue);

        return productReceiptList;
    }

    private ProductReceipt getProductReceipt() {
        ProductReceipt pReceipt = null;

        String code = inputHelper.getStringWithMessage("Enter product code: ");
        Product product;
        try {
            product = itemService.productExist(code);

            if (product == null) {
                return null;
            }

            int quantity = getProductQuantity();
            int price = getProductPrice();

            pReceipt = new ProductReceipt(
                    product.getCode(),
                    product.getName(),
                    quantity, price
            );
        } catch (Exception e) {
            DataIOHelper.printlnNotification(e.getMessage());
        }
        return pReceipt;
    }

    private int getProductQuantity() {
        return getPositiveNumber("Enter Product quantity: ");
    }

    private int getProductPrice() {
        return getPositiveNumber("Enter product price: ");
    }

    private long getShippingFee() {
        return getPositiveNumber("Enter shipping free: ");
    }

    private int getPositiveNumber(String msg) {
        int number;
        do {
            try {
                String str = inputHelper.getStringWithMessage(msg);

                DataValidation.requirePositiveNumber(str, "Data must be positive integer");
                number = Integer.parseInt(str);
                break;
            } catch (Exception e) {
                DataIOHelper.printlnNotification(e.getMessage());
                DataIOHelper.displayTryAgainMessage();
            }
        } while (true);
        return number;
    }


    private String getSellerName() {
        return inputHelper.getStringWithMessage("Enter seller name: ");
    }

    private String getSellerAddress() {
        return inputHelper.getStringWithMessage("Enter seller address: ");
    }

    private String getBuyerName() {
        return inputHelper.getStringWithMessage("Enter buyer name: ");
    }

    private String getBuyerAddress() {
        return inputHelper.getStringWithMessage("Enter buyer address: ");
    }

    private long getSystemTime() {
        return System.currentTimeMillis();
    }
}
