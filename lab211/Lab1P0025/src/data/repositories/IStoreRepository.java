package data.repositories;

import business.entities.Product;
import business.entities.Receipt;

import java.util.List;

public interface IStoreRepository {
    void addNewProduct(Product product) throws Exception;

    void addNewReceipt(Receipt receipt) throws Exception;

    List<Product> getAllProducts() throws Exception;

    List<Product> getExpiredProducts() throws Exception;

    List<Product> getSellingProducts() throws Exception;

    List<Product> getOutOfStockProducts() throws Exception;

    boolean deleteProduct(String productCode) throws Exception;

    Product getProduct(String code) throws Exception;

    boolean updateProduct(Product product) throws Exception;

    String generateReceiptCode() throws Exception;

    List<Receipt> getReceiptsBy(String productCode) throws Exception;
}
