package data.repositories;

import business.entities.Product;
import business.entities.ProductReceipt;
import business.entities.Receipt;
import business.utilities.ReceiptType;
import data.dao.product.IProductDao;
import data.dao.product.ProductDao;
import data.dao.warehouse.IWarehouseDao;
import data.dao.warehouse.WarehouseDao;
import data.managers.FileManager;
import data.managers.IFileManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StoreRepository implements IStoreRepository {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private final IProductDao productDao;
    private final IWarehouseDao warehouseDao;

    private static StoreRepository INSTANCE;


    public StoreRepository(
            IFileManager pFileManager,
            IFileManager wFileManager
    ) {
        this.productDao = new ProductDao(pFileManager);
        this.warehouseDao = new WarehouseDao(wFileManager);
    }

    @Override
    public Product getProduct(String code) throws Exception {
        return productDao.loadProduct(code);
    }

    @Override
    public void addNewProduct(Product product) throws Exception {
        productDao.addNewProduct(product);
    }

    @Override
    public void addNewReceipt(Receipt receipt) throws Exception {
        warehouseDao.addNewReceipt(receipt);
    }

    @Override
    public boolean deleteProduct(String productCode) throws Exception {
        boolean isReceiptGenerated = isProductReceiptExist(productCode);
        if (isReceiptGenerated) {
            throw new RuntimeException("Can not delete product once receipt is generated");
        }

        return productDao.deleteProduct(productCode);
    }

    @Override
    public boolean updateProduct(Product product) throws Exception {
        return productDao.updateProduct(product);
    }

    private boolean isProductReceiptExist(String productCode) throws Exception {
        return warehouseDao.isProductExist(productCode);
    }

    @Override
    public List<Product> getAllProducts() throws Exception {
        return productDao.loadProductFromFile();
    }

    @Override
    public List<Product> getExpiredProducts() throws Exception {
        return filterProductBy(
                (item) -> isProductExpired(item.getManufacturingDate(), item.getExpirationDate())
        );
    }

    @Override
    public List<Product> getSellingProducts() throws Exception {
        return filterProductBy(
                (item) -> item.getQuantity() > 0 && !isProductExpired(item.getManufacturingDate(), item.getExpirationDate())
        );
    }

    @Override
    public List<Product> getOutOfStockProducts() throws Exception {
        return filterProductBy(
                (item) -> item.getQuantity() <= 3
        );
    }

    private List<Product> filterProductBy(Predicate<Product> action) throws Exception {
        List<Product> list = getAllProducts();
        List<Product> resultList = new ArrayList<>();

        for (Product p : list) {
            if (action.test(p)) {
                resultList.add(p);
            }
        }

        return resultList;
    }


    @Override
    public List<Receipt> getReceiptsBy(String productCode) throws Exception {
        List<String> rawList = warehouseDao.loadRawReceipts(productCode);
        List<Receipt> rList = new ArrayList<>();

        for (String raw : rawList) {
            rList.add(rawDataToReceipt(raw));
        }

        return rList;
    }

    private Receipt rawDataToReceipt(String receiptRaw) throws Exception {
        String[] components = receiptRaw.split(",");

        List<ProductReceipt> pList = new ArrayList<>();

        Pattern pattern = Pattern.compile("\\[(.*?)]");
        Matcher matcher = pattern.matcher(receiptRaw);

        while (matcher.find()) {
            pList.add(
                    rawToProductReceipt(matcher.group(1))
            );
        }

        return new Receipt(
                components[0],
                ReceiptType.valueOf(components[1]),
                Long.parseLong(components[2]),
                components[3],
                components[4],
                components[5],
                components[6],
                Long.parseLong(components[7]),
                pList
        );
    }


    private ProductReceipt rawToProductReceipt(String raw) throws Exception {
        String[] components = raw.split(",");
        String productCode = components[0];

        Product p = getProduct(productCode);

        return new  ProductReceipt(
                productCode,
                p.getName(),
                Integer.parseInt(components[1]),
                Integer.parseInt(components[2])
        );
    }

    @Override
    public String generateReceiptCode() throws Exception {
        return warehouseDao.generateCode();
    }

    private boolean isProductExpired(String man, String exp) {
        try {
            Date manDate = dateFormat.parse(man);
            Date expDate = dateFormat.parse(exp);
            return expDate.before(manDate);
        } catch (ParseException e) {
            return false;
        }
    }

    public static IStoreRepository getInstance() {

        if (INSTANCE == null) {
            synchronized (StoreRepository.class) {
                try {
                    IFileManager pFileManager = new FileManager(FileManager.PRODUCT_FILE_NAME);
                    IFileManager wFileManager = new FileManager(FileManager.WAREHOUSE_FILE_NAME);

                    INSTANCE = new StoreRepository(
                            pFileManager, wFileManager
                    );
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return INSTANCE;
    }

}
