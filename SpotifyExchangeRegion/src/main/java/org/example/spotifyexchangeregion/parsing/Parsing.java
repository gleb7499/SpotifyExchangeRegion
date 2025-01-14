package org.example.spotifyexchangeregion.parsing;

import org.example.spotifyexchangeregion.models.Account;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.Set;

import static java.time.Duration.of;
import static java.time.temporal.ChronoUnit.SECONDS;

public class Parsing implements AutoCloseable {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final CookieSpotify cookieSpotify;

    private final static String url = "https://www.spotify.com/us/account/overview/";

    public Parsing(String region) {
        driver = new ChromeDriver(new ChromeOptions().addExtensions(new File("3.2.1_0.crx")));
        wait = new WebDriverWait(driver, of(15, SECONDS));
        setVPN(region);
        driver.get(url);
        cookieSpotify = new CookieSpotify(driver);
        setCookie();
    }

    @Override
    public void close() {
        cookieSpotify.saveCookie();
        driver.quit();
    }

    private void setVPN(String region) {
        driver.get("chrome-extension://majdfhpaihoncoakbjgbdhglocklcgno/src/popup/popup.html");
        while ("Extension".equals(driver.getTitle())) {
            Set<String> windowHandles = driver.getWindowHandles();
            if (windowHandles.size() > 1) {
                driver.switchTo().window(windowHandles.toArray(new String[0])[1]);
                driver.close();
                driver.switchTo().window(windowHandles.toArray(new String[0])[0]);
                break;
            }
        }

        WebElement greetingsList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#root > div.onboarding-view.fullheight > div > footer > ul")));
        int size = greetingsList.findElements(By.tagName("li")).size();
        for (int i = 0; i < size; ++i) {
            driver.findElement(By.cssSelector("#root > div.onboarding-view.fullheight > div > footer > button")).click();
        }

        driver.findElement(By.cssSelector("#root > div.navigation-layout.fullheight > div > main > div.main-view__inner > div > div.main-view__region > div > button")).click();
        driver.findElement(By.cssSelector("#search")).sendKeys(region);
        WebElement countryButton = driver.findElement(By.cssSelector("#root > div.navigation-layout.fullheight > div > div > main > ul > li:nth-child(1) > div > div.location-section__wrap > div > ul > li > div > div.location-country__header"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", countryButton);
        WebElement regionButton = driver.findElement(By.cssSelector("#root > div.navigation-layout.fullheight > div > div > main > ul > li:nth-child(1) > div > div.location-section__wrap > div > ul > li > div > div.location-country__wrap > div > ul > li:nth-child(1) > div"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", regionButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#root > div.navigation-layout.fullheight > div > main > div.main-view__inner > div > div.main-view__connect > button"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".connect-button.connect-button--connected")));
    }

    private void setCookie() {
        if (cookieSpotify.setCookie()) {
            driver.navigate().refresh();
        }
    }

    public void changeRegion(Account account) {
        if (cookieSpotify.IS_FIRST_LAUNCH) {
            login(account);
        }
        change();
    }

    private void change() {
        driver.findElement(By.cssSelector("#account-settings-link")).click();
        driver.findElement(By.cssSelector("#menu-group-account > div:nth-child(3) > a")).click();
        WebElement country = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#country")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", country);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", country);
    }

    private void login(@NotNull Account account) {
        WebElement loginInput = driver.findElement(By.id("login-username"));
        WebElement passwordInput = driver.findElement(By.id("login-password"));
        loginInput.sendKeys(account.getLogin());
        passwordInput.sendKeys(account.getPassword());
        driver.findElement(By.id("login-button")).click();
    }
}
