package data.managers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class FileManager implements IFileManager {
    private File inputFile;

    public FileManager(String fileName) throws IOException {
        inputFile = new File(fileName);
        if (inputFile.exists()) {
            inputFile.createNewFile();
        }
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

}
