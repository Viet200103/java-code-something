package data.dao.product;

import business.entities.Product;
import business.utilities.ProductType;
import data.managers.IFileManager;

import java.io.File;
import java.util.*;

public class ProductDao implements IProductDao {
    private IFileManager fileManager;

    private ProductDao() {

    }

    public ProductDao(IFileManager fileManager)  {
        this.fileManager = fileManager;
    }

    @Override
    public Product loadProduct(String code) throws Exception {
        String productRaw = fileManager.readItemByCode(code);

        if (productRaw == null) {
            return null;
        }

        return rawDataToProduct(productRaw);
    }

    @Override
    public List<Product> loadProductFromFile() throws Exception {
        List<String> dataList = fileManager.readDataFromFile();
        List<Product> productList = new ArrayList<>();

        for (String productRaw: dataList) {
            Product product = rawDataToProduct(productRaw);
            productList.add(product);
        }

        return productList;
    }

    private Product rawDataToProduct(String raw) {
        List<String> raws = Arrays.asList(raw.split(","));

        String code = raws.get(0).trim();

        ProductType type = null;
        switch (Integer.parseInt(raws.get(1).trim())) {
            case 0 -> type = ProductType.DAILY;
            case 1 -> type = ProductType.LONG_LIFE;
        }

        String name = raws.get(2).trim();
        String maker = raws.get(3).trim();
        String manufacturingDate = raws.get(4).trim();
        String expirationDate = raws.get(5).trim();

        int quantity = Integer.parseInt(
                raws.get(6).trim()
        );

        return new Product(code, type, name, maker, manufacturingDate, expirationDate, quantity);
    }

    @Override
    public void addNewProduct(Product product) throws Exception {
       boolean isExists = fileManager.isCodeExists(product.getCode());

       if (isExists) {
           throw new IllegalArgumentException("Product code is duplicated");
       }

       fileManager.saveItem(productToRawData(product));
    }

    @Override
    public boolean deleteProduct(String code) throws Exception {
        List<String> dataList = fileManager.readDataFromFile();
        String codeExpected = code + ",";
        boolean isDeleted = dataList.removeIf(
                (product) -> product.startsWith(codeExpected)
        );

        if (isDeleted) {
            fileManager.commit(dataList);
        }

        return isDeleted;
    }

    @Override
    public boolean updateProduct(Product product) throws Exception {
        String productRaw = productToRawData(product);
        List<String> dataList = fileManager.readDataFromFile();

        boolean isUpdated = false;

        String codeExpected = product.getCode() + ",";
        for (int i = 0; i < dataList.size() ; i++) {
            String raw = dataList.get(i);


            if (raw.startsWith(codeExpected)) {
                dataList.set(i, productRaw);
                isUpdated = true;
                break;
            }
        }

        if (isUpdated) {
            fileManager.commit(dataList);
        }

        return isUpdated;
    }

    private String productToRawData(Product product) {
        return product.getCode() + ','
                + product.getType().getValue() + ','
                + product.getName() + ','
                + product.getMaker() + ','
                + product.getManufacturingDate() + ','
                + product.getManufacturingDate() + ','
                + product.getQuantity();

    }
}
