package data.dao.product;

import business.entities.Product;

import java.util.List;

public interface IProductDao {

    List<Product> loadProductFromFile() throws Exception;

    void addNewProduct(Product product) throws Exception;

    boolean deleteProduct(String code) throws Exception;

    boolean updateProduct(Product product) throws Exception;

    Product loadProduct(String code) throws Exception;
}
