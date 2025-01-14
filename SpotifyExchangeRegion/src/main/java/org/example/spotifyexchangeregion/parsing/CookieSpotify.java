package org.example.spotifyexchangeregion.parsing;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public class CookieSpotify {
    private final WebDriver driver;
    private Set<Cookie> cookies;

    public final boolean IS_FIRST_LAUNCH;

    private final File file;
    private final static String FILE_NAME = "cookies.data";

    {
        Path path = Path.of(FILE_NAME);
        try {
            if (Files.notExists(path)) {
                IS_FIRST_LAUNCH = true;
                file = Files.createFile(path).toFile();
            } else {
                IS_FIRST_LAUNCH = false;
                file = path.toFile();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public CookieSpotify(WebDriver driver) {
        this.driver = driver;
    }

    public boolean setCookie() {
        if (!IS_FIRST_LAUNCH) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                cookies = (Set<Cookie>) ois.readObject();
                for (Cookie cookie : cookies) {
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
}
