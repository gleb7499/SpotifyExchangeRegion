package org.example.spotifyexchangeregion.parsing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Parsing implements AutoCloseable {
    private final WebDriver driver;

    public Parsing(String url) {
        driver = new ChromeDriver();
        driver.get(url);
    }

    @Override
    public void close() {
        driver.quit();
    }

    public void changeRegion(String login, String password) {
        try {
            WebElement loginInput = driver.findElement(By.id("login-username"));
            WebElement passwordInput = driver.findElement(By.id("login-password"));
            loginInput.sendKeys(login);
            passwordInput.sendKeys(password);
            driver.findElement(By.id("login-button")).click();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
