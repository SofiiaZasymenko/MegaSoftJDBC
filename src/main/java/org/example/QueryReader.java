package org.example;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QueryReader {
    public static List<String> readAllQueriesFromFile(String fileName) {
        List<String> result = new ArrayList<>();
        try (Scanner scanner = new Scanner(Paths.get(fileName), StandardCharsets.UTF_8)
                .useDelimiter(";")) {
            while (scanner.hasNext()) {
                String query = scanner.next().strip();
                if (!query.isBlank()) {
                    result.add(query);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static String readQueryFromFile(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
}