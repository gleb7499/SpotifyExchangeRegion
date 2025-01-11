package org.example.spotifyexchangeregion.parsing;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.util.Set;

public class Parsing implements AutoCloseable {
    private final WebDriver driver;

    public Parsing(String url) {
        driver = new ChromeDriver();
        setCookie();
        driver.get(url);
    }

    @Override
    public void close() {
        driver.quit();
    }

    public void changeRegion(String login, String password) {
        WebElement loginInput = driver.findElement(By.id("login-username"));
        WebElement passwordInput = driver.findElement(By.id("login-password"));
        loginInput.sendKeys(login);
        passwordInput.sendKeys(password);
        driver.findElement(By.id("login-button")).click();
        driver.findElement(By.cssSelector("#account-settings-link")).click();

        driver.findElement(By.cssSelector("#menu-group-account > div:nth-child(3) > a")).click();
        driver.findElement(By.cssSelector("#country")).click();

        saveCookies();
    }

    private void saveCookies() {
        File file = new File("cookies.data");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            Set<Cookie> cookies = driver.manage().getCookies();
            oos.writeObject(cookies);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setCookie() {
        File file = new File("cookies.data");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Set<Cookie> cookies = (Set<Cookie>) ois.readObject();
            for (Cookie cookie : cookies) {
                driver.manage().addCookie(cookie);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
