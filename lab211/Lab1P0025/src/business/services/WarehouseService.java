package business.services;

import business.entities.Product;
import business.entities.Receipt;
import data.repositories.IStoreRepository;

public class WarehouseService implements ItemService<Receipt> {

    private final IStoreRepository pRepository;

    public WarehouseService(IStoreRepository pRepository) {
        this.pRepository = pRepository;
    }

    @Override
    public void add(Receipt item) {
        try {
            pRepository.addNewReceipt(item);
            System.out.println("Receipt is added successfully.");
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println(item.buildReceipt());
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        } catch (Exception e) {
            System.out.println(">> " + e.getMessage());
        }
    }

    @Override
    public void delete(String itemCode) {

    }

    @Override
    public void printList() {

    }

    @Override
    public void update(Receipt item) {

    }

    @Override
    public Product productExist(String code) throws Exception {
        Product product = pRepository.getProduct(code);

        if (product == null) {
            System.out.println(">> Product does not exist");
        }
        return product;
    }
}
