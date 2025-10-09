package data.managers;

import java.io.IOException;
import java.util.List;

public interface IFileManager {
    List<String> readDataFromFile() throws IOException;

    void commit(List<String> rawList) throws IOException;
}
