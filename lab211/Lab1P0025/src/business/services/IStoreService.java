package business.services;

public interface IStoreService {

    void printExpiredProducts();

    void printSellingProducts();

    void printOutOfStockProducts();

    void printReceiptOfProduct(String productCode);
}
