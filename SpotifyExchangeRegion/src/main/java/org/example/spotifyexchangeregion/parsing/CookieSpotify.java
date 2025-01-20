package org.example.spotifyexchangeregion.parsing;

import org.example.spotifyexchangeregion.models.Account;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public class CookieSpotify {
    private final WebDriver driver;
    private Set<Cookie> cookies;

    public boolean IS_FIRST_LAUNCH;

    private File file;
    private String fileName;
    private final static String DIRECTORY_NAME = "cookies";

    @Contract(pure = true)
    public CookieSpotify(WebDriver driver, @NotNull Account account) {
        this.driver = driver;
        fileName = account.login() + "_" + account.password().hashCode();
    }

    private void openFile() {
        Path path = Path.of(DIRECTORY_NAME);
        try {
            if (Files.notExists(path)) {
                Files.createDirectories(path);
            }
            Path filePath = path.resolve(fileName + ".data");
            if (Files.notExists(filePath)) {
                file = Files.createFile(filePath).toFile();
                IS_FIRST_LAUNCH = true;
            } else {
                file = filePath.toFile();
                IS_FIRST_LAUNCH = false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean setCookie() {
        openFile();
        if (!IS_FIRST_LAUNCH) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                cookies = (Set<Cookie>) ois.readObject();
                for (final Cookie cookie : cookies) {
                    try {
                        driver.manage().addCookie(cookie);
                    } catch (Exception e) {
                        System.out.println("Cookie cannot be added -> " + cookie);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        return false;
    }

    public void saveCookie() {
        if (IS_FIRST_LAUNCH) {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                cookies = driver.manage().getCookies();
                oos.writeObject(cookies);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void readDataAndWriteTxt() {
        try (BufferedReader br = new BufferedReader(new FileReader(DIRECTORY_NAME + "/" + fileName + ".data"));
             BufferedWriter bw = new BufferedWriter(new FileWriter(DIRECTORY_NAME + "/" + fileName + ".txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
