package data.dao.warehouse;

import business.entities.ProductReceipt;
import business.entities.Receipt;
import data.managers.FileManager;
import data.managers.IFileManager;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WarehouseDao implements IWarehouseDao {
    private final IFileManager fileManager;

    private final File codeFile;

    private static int currentReceiptCode = 0;

    public WarehouseDao(IFileManager fileManager) {
        this.fileManager = fileManager;

        codeFile = new File("rc.count");
        try {
            if (!codeFile.exists()) {
                codeFile.createNewFile();
            }

            List<String> codes = Files.readAllLines(codeFile.toPath());
            if (codes.isEmpty()) {
                currentReceiptCode = 0;
            } else {
                currentReceiptCode = Integer.parseInt(codes.get(0));
            }
        } catch (Exception ignored) {
        }
    }


    @Override
    public List<String> loadRawReceipts(String productCode) throws Exception {
        List<String> receiptList = new ArrayList<>();

        List<String> rawList = fileManager.readDataFromFile();

        String codeExpect = productCode  + ",";

        Pattern pattern = Pattern.compile("\\[(.*?)]");
        Matcher matcher;

        for (String raw: rawList) {

            matcher = pattern.matcher(raw);

            while (matcher.find()) {
                String p = matcher.group(1);
                if (p.startsWith(codeExpect)) {
                    receiptList.add(raw);
                    break;
                }
            }
        }

        return receiptList;
    }



    @Override
    public void addNewReceipt(Receipt receipt) throws Exception {
        String raw = receiptToRaw(receipt);
        fileManager.saveItem(raw);
    }

    private String receiptToRaw(Receipt receipt) {
        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append(receipt.getCode());
        sBuilder.append(",");
        sBuilder.append(receipt.getType().name());
        sBuilder.append(",");
        sBuilder.append(receipt.getTimeCreated());
        sBuilder.append(",");
        sBuilder.append(receipt.getSellerName());
        sBuilder.append(",");
        sBuilder.append(receipt.getSellerAddress());
        sBuilder.append(",");
        sBuilder.append(receipt.getBuyerName());
        sBuilder.append(",");
        sBuilder.append(receipt.getBuyerAddress());
        sBuilder.append(",");
        sBuilder.append(receipt.getShippingFee());
        sBuilder.append(",");

        for (ProductReceipt pr : receipt.getItemList()) {
            sBuilder.append(productReceiptToRaw(pr));
            sBuilder.append(",");
        }

        sBuilder.deleteCharAt(sBuilder.length() - 1);

        return sBuilder.toString();
    }

    private String productReceiptToRaw(ProductReceipt pr) {
        return '[' + pr.getCode() + "," + pr.getQuantity() + "," + pr.getPrice() + ']';
    }


    @Override
    public boolean isProductExist(String productCode) throws Exception {
        FileManager manager = (FileManager) fileManager;

        try (BufferedReader bReader = manager.getReader()) {
            String line;
            String codeExpected = productCode + ",";

            while ((line = bReader.readLine()) != null) {

                Pattern pattern = Pattern.compile("\\[(.*?)]");
                Matcher matcher;

                matcher = pattern.matcher(line);

                while (matcher.find()) {
                    String p = matcher.group(1);
                    if (p.startsWith(codeExpected)) {
                        bReader.close();
                        return true;
                    }
                }

            }
        }

        return false;
    }

    @Override
    public String generateCode() throws Exception {

        String code;
        do {
            ++currentReceiptCode;
            code = String.format("%07d", ++currentReceiptCode);
        } while (fileManager.isCodeExists(code));

        saveCurrentReceiptCode(currentReceiptCode);
        return code;
    }

    public void saveCurrentReceiptCode(int code) {
        currentReceiptCode = code;
        try {
            FileWriter fileWriter = new FileWriter(codeFile, true);
            fileWriter.write(currentReceiptCode);
            fileWriter.close();
        } catch (IOException ignored) {
        }
    }
}
