package data.dao.warehouse;

import business.entities.Receipt;

import java.util.List;

public interface IWarehouseDao {
    String generateCode() throws Exception;

    boolean isProductExist(String productCode) throws Exception;

    List<String> loadRawReceipts(String productCode) throws Exception;

    void addNewReceipt(Receipt receipt) throws Exception;
}
