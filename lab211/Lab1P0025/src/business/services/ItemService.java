package business.services;

import business.entities.Product;

public interface ItemService<T> {

    void add(T item);

    void delete(String itemCode);

    void printList() throws Exception;

    void update(T item);

    Product productExist(String code) throws Exception;
}
