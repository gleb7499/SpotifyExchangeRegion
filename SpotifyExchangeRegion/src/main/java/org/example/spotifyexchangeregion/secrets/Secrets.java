package org.example.spotifyexchangeregion.secrets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Secrets {
    private static innerSecrets secrets;

    private static final String FILE_NAME = "secrets.txt";

    private Secrets() {
    }

    public static void Initialize() {
        if (secrets == null) {
            secrets = new innerSecrets();
        }
    }

    public static void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    secrets.secretsMap.put(parts[0], parts[1]);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String key) {
        return secrets.secretsMap.get(key);
    }

    private static class innerSecrets {
        private final Map<String, String> secretsMap;

        private innerSecrets() {
            secretsMap = new HashMap<>();
        }
    }
}
