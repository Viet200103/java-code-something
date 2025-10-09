package data.managers;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class FileManager implements IFileManager {

    private File inputFile = null;

    private FileManager() {

    }

    public FileManager(String fileName) {
        inputFile = new File(fileName);
    }

    @Override
    public String readItemByCode(String code) throws Exception {
        try (BufferedReader bReader = getReader()) {
            String line;
            String codeExpected = code + ",";
            while ((line = bReader.readLine()) != null) {
                if (line.startsWith(codeExpected)) {
                    return line;
                }
            }
        }
        return null;
    }

    @Override
    public List<String> readDataFromFile() throws IOException {
        List<String> result;
        result = Files.readAllLines(inputFile.toPath(), StandardCharsets.UTF_8);
        return result;
    }

    @Override
    public void commit(List<String> rawList) throws IOException {
        FileWriter fileWriter = new FileWriter(inputFile, false);

        try (BufferedWriter bWriter = new BufferedWriter(fileWriter)) {

            StringBuilder sBuilder = new StringBuilder();
            for (String raw : rawList) {
                sBuilder.append(raw).append("\n");
            }
            sBuilder.deleteCharAt(sBuilder.length() - 1);

            bWriter.write(sBuilder.toString());
        }
    }


    @Override
    public void saveItem(String rawData) throws Exception {
        try (BufferedWriter bWriter = getWriter()) {
            bWriter.append(rawData);
            bWriter.newLine();
        }
    }

    @Override
    public boolean isCodeExists(String itemCode) throws Exception {

        try (BufferedReader bReader = getReader()) {
            String line;
            String codeExpected = itemCode + ",";
            while ((line = bReader.readLine()) != null) {
                if (line.startsWith(codeExpected)) {
                    bReader.close();
                    return true;
                }
            }
        }

        return false;
    }

    public BufferedReader getReader() throws FileNotFoundException {
        FileReader fileReader = new FileReader(inputFile);
        return new BufferedReader(fileReader);
    }

    private BufferedWriter getWriter() throws IOException {
        FileWriter fileWriter = new FileWriter(inputFile, true);
        return new BufferedWriter(fileWriter);
    }

    public static final String PRODUCT_FILE_NAME = "product.txt";
    public static final String WAREHOUSE_FILE_NAME = "warehouse.txt";

}
