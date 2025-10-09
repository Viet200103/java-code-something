package business.services;

import business.entities.Product;
import data.repositories.IStoreRepository;

public class ProductService implements ItemService<Product> {

    private final IStoreRepository pRepository;

    public ProductService(IStoreRepository pRepository) {
        this.pRepository = pRepository;
    }

    @Override
    public Product productExist(String code) throws Exception {
        Product product = pRepository.getProduct(code);

        if (product == null) {
            System.out.println(">> Product does not exist");
        }
        return product;
    }

    @Override
    public void update(Product product) {
        try {
            boolean isUpdated = pRepository.updateProduct(product);
            if (isUpdated) {
                System.out.println(">> update product successfully.");
                System.out.println(">> " + product);
            } else  {
                System.out.println(">> update product with code: " + product.getCode() + " is failed");
            }
        } catch (Exception e) {
            System.out.println(">> update product with code: " + product.getCode() + " is failed");
        }
    }

    @Override
    public void add(Product item) {
        try {
            pRepository.addNewProduct(item);
            System.out.println(">>Product added successfully.");
        } catch (Exception e) {
            System.out.println(">>" + e.getMessage());
        }
    }

    @Override
    public void delete(String itemCode) {
        try {
            boolean isDeleted = pRepository.deleteProduct(itemCode);
            if (isDeleted) {
                System.out.println(">> Delete product with code: " + itemCode + " is succeed");
            } else {
                System.out.println(">> Delete product with code: " + itemCode + " is failed!.");
            }
        } catch (Exception e) {
            System.out.println(">> Delete product with code: " + itemCode + " is failed!.");
            System.out.println(">> " + e.getMessage());
        }
    }

    @Override
    public void printList() {
        try {
            pRepository.getAllProducts().forEach(
                    System.out::println
            );
        } catch (Exception e) {
            System.out.println(">> Load data from file is failed");
        }
    }
}
