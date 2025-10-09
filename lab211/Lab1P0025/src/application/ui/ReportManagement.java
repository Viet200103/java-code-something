package application.ui;

import application.utilities.DataIOHelper;
import business.services.IStoreService;
import business.services.StoreService;
import data.repositories.StoreRepository;

public class ReportManagement {
    private final DataIOHelper inputHelper = DataIOHelper.getInstance();

    private final IStoreService storeService = new StoreService(
            StoreRepository.getInstance()
    );

    public void displayMenu() {

        try {
            int choice;

            System.out.println("******Report Management******");
            Menu.print(
                    "1. List expired products." + "|" +
                    "2. List selling product" + "|" +
                    "3. List products that are out of stock" + "|" +
                    "4. List receipt of product"
            );
            choice = inputHelper.getIntegerNumber();
            System.out.println("--------------------------------");

            switch (choice) {
                case 1 -> storeService.printExpiredProducts();
                case 2 -> storeService.printSellingProducts();
                case 3 -> storeService.printOutOfStockProducts();
                case 4 -> storeService.printReceiptOfProduct(
                        inputHelper.getStringWithMessage("Enter product code: ")
                );
                default -> Menu.printRequireNotFound();
            }
            System.out.println("--------------------------------");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
