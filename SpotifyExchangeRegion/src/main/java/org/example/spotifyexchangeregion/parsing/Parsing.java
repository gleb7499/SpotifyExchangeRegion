package org.example.spotifyexchangeregion.parsing;

import org.example.spotifyexchangeregion.models.Account;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Parsing implements AutoCloseable {
    private final WebDriver driver;
    private CookieSpotify cookieSpotify;

    public Parsing(String url) {
        driver = new ChromeDriver();
        cookieSpotify = new CookieSpotify(driver);
        driver.get(url);
        if (cookieSpotify.setCookie()) {
            driver.navigate().refresh();
        }
    }

    @Override
    public void close() {
        cookieSpotify.saveCookie();
        driver.quit();
    }

    public void changeRegion(Account account) {
        if (cookieSpotify.IS_FIRST()) {
            login(account);
        }

//        driver.findElement(By.cssSelector("#account-settings-link")).click();
//        driver.findElement(By.cssSelector("#menu-group-account > div:nth-child(3) > a")).click();
//        driver.findElement(By.cssSelector("#country")).click();

    }

    private void login(@NotNull Account account) {
        WebElement loginInput = driver.findElement(By.id("login-username"));
        WebElement passwordInput = driver.findElement(By.id("login-password"));
        loginInput.sendKeys(account.getLogin());
        passwordInput.sendKeys(account.getPassword());
        driver.findElement(By.id("login-button")).click();
    }
}
