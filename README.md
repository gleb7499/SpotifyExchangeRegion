# SpotifyExchangeRegion

Desktop app that automates changing the region of a Spotify account via a desktop Chrome browser controlled with Selenium.

Spotify ties your account region to your payment method and current location. This tool logs into your Spotify account in a browser, routes traffic through a VPN Chrome extension, and switches the account country through the official account settings page — all automated.

> **Disclaimer:** this project is for educational purposes. Automating Spotify's web UI may violate Spotify's Terms of Service. Use at your own risk.

## How it works

1. Launches Chrome via Selenium WebDriver with a VPN extension (`3.2.1_0.crx`) loaded.
2. On first launch: runs VPN onboarding, connects to a chosen region (USA by default), logs into Spotify with your credentials.
3. Saves session cookies to `cookies.data` so subsequent runs skip the login.
4. Changes the account country on the [Spotify account overview](https://www.spotify.com/us/account/overview/) page (BY → US flow), confirming the change.

## Tech stack

- Java 23
- JavaFX (UI) + FXML
- Selenium WebDriver (browser automation)
- JUnit 5 + Mockito (tests)
- Maven

## Prerequisites

- JDK 23
- Maven (or use the included Maven Wrapper: `mvnw` / `mvnw.cmd`)
- Chrome browser installed
- ChromeDriver matching your Chrome version, available on `PATH`
- A VPN Chrome extension packaged as `3.2.1_0.crx`, placed in the project root (`SpotifyExchangeRegion/`) — the region is set through this extension

## Running

```bash
cd SpotifyExchangeRegion
./mvnw clean javafx:run
```

On Windows:

```cmd
cd SpotifyExchangeRegion
mvnw.cmd clean javafx:run
```

Enter your Spotify account email and password in the window and press the exchange button.

## Testing

```bash
cd SpotifyExchangeRegion
./mvnw test
```

## Project structure

```
SpotifyExchangeRegion/
├── src/main/java/org/example/spotifyexchangeregion/
│   ├── main/          # JavaFX entry point and controller
│   ├── models/        # Account record (login, password)
│   └── parsing/       # Selenium automation and cookie persistence
├── src/test/java/     # Unit tests
└── pom.xml
```

## CI

A GitHub Actions workflow (`.github/workflows/build.yml`) builds the project with JDK 23 and runs SonarCloud analysis on pushes and pull requests to `main`.

## Security notes

- Your password is only used to log into Spotify in the automated browser session; it is never sent anywhere else.
- Session cookies are stored locally in `cookies.data` (gitignored). Delete this file to force a fresh login.
- Do not commit real credentials or VPN extension keys.

## License

[MIT](LICENSE)
