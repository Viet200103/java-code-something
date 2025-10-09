package data.managers;

import java.util.List;

public interface IFileManager {

    List<String> readDataFromFile() throws Exception;

    String readItemByCode(String code) throws Exception;

    void saveItem(String rawData) throws Exception;

    boolean isCodeExists(String itemCode) throws Exception;

    void commit(List<String> raw) throws Exception;
}
