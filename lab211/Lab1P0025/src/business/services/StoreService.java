package business.services;

import business.entities.Product;
import business.entities.Receipt;
import data.repositories.IStoreRepository;

import java.util.List;

public class StoreService implements IStoreService {
    private final IStoreRepository pRepository;

    public StoreService(IStoreRepository pRepository) {
        this.pRepository = pRepository;
    }


    @Override
    public void printExpiredProducts() {
        try {
            List<Product> expList = pRepository.getExpiredProducts();
            if (expList.isEmpty()) {
                System.out.println("No product is expired in store");
            } else  {
                expList.forEach(System.out::println);
            }

        } catch (Exception e) {
            System.out.println(">> " + e.getMessage());
        }
    }

    @Override
    public void printSellingProducts() {
        try {

            List<Product> sellingList = pRepository.getSellingProducts();
            if (sellingList.isEmpty()) {
                System.out.println("Store is not sell any product");
            } else  {
                sellingList.forEach(System.out::println);
            }

        } catch (Exception e) {
            System.out.println(">> " + e.getMessage());
        }
    }

    @Override
    public void printOutOfStockProducts() {
        try {
            List<Product> pList = pRepository.getOutOfStockProducts();
            if (pList.isEmpty()) {
                System.out.println("No product are out of stock in store");
            } else  {
                pList.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println(">> " + e.getMessage());
        }
    }

    @Override
    public void printReceiptOfProduct(String productCode) {
        try {
            List<Receipt> rList = pRepository.getReceiptsBy(productCode);

            if (rList.isEmpty()) {
                System.out.println(">> No receipt which is generated for product with code: " + productCode);
            } else  {
                System.out.println(">> Receipt List >>");
                System.out.println(">>");
                rList.forEach(
                        (receipt) -> {
                            System.out.println(receipt.buildReceipt());
                            System.out.println("\n\n>>>>>>\n\n");
                        }
                );
            }

        } catch (Exception e) {
            System.out.println(">> " + e.getMessage());
        }
    }

}
