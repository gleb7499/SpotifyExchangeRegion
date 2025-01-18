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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.Set;

import static java.time.Duration.of;
import static java.time.temporal.ChronoUnit.SECONDS;

public class Parsing implements AutoCloseable {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final CookieSpotify cookieSpotify;
    private final Account account;

    private final static String url = "https://www.spotify.com/us/account/overview/";

    public Parsing(Account account) {
        driver = new ChromeDriver(new ChromeOptions().addExtensions(new File("3.2.1_0.crx")));
        wait = new WebDriverWait(driver, of(15, SECONDS));
        cookieSpotify = new CookieSpotify(driver, account);
        this.account = account;
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

        driver.get(url);
    }

    public void changeRegion(String region) {
        driver.get(url);
        if (cookieSpotify.setCookie()) {
            setVPN(region);
            login(account);
        } else {
            change("BY");
            setVPN(region);
        }
        change("US");
    }

    private void change(String region) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#menu-group-account > div:nth-child(3) > a"))).click();
        Select countries = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#country"))));
        countries.selectByValue(region);
        WebElement submit = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#__next > div.encore-layout-themes.encore-dark-theme > div > div.sc-85f631f4-0.bihHnb > div.sc-bc5846-0.jxcVMq > article > section > form > div > button")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submit);
        // Ждем, пока сообщение о смене региона не появится
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#__next > div.encore-layout-themes.encore-dark-theme > div > div.sc-85f631f4-0.bihHnb > div.sc-bc5846-0.jxcVMq > section > div")));
    }

    private void login(@NotNull Account account) {
        WebElement loginInput = driver.findElement(By.id("login-username"));
        WebElement passwordInput = driver.findElement(By.id("login-password"));
        loginInput.sendKeys(account.login());
        passwordInput.sendKeys(account.password());
        driver.findElement(By.id("login-button")).click();
    }
}
