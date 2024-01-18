package utils;

import com.sun.tools.javac.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtils {

    public static File getResourceDocument(String documentName){
        ClassLoader classLoader = Main.class.getClassLoader();
        URL fileURL = classLoader.getResource("resources/" + documentName);

        if(fileURL == null) {
            System.out.println("File not found");
            return null;
        }

        return new File(fileURL.getFile());
    }

    public static List<String> getArrayFromDocument(String documentName) {
        File document = getResourceDocument(documentName);

        if(document == null) {
            return Collections.emptyList();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(document))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Error reading file");
        }

        return Collections.emptyList();
    }

}
