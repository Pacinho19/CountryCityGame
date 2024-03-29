package pl.pracinho.countrycitygame.utils;

import lombok.SneakyThrows;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    @SneakyThrows
    public static List<String> readTxt(File file) {
        List<String> out = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), "Cp1250"))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                out.add(line);
            }
        }
        return out;
    }

    @SneakyThrows
    public static File getFileFromResource(String fileName) {

        ClassLoader classLoader = FileUtils.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null)
            throw new IllegalArgumentException("file not found! " + fileName);

        return new File(resource.toURI());
    }
}