# Spotify Exchange Region

A personal Java/JavaFX automation I built to simplify a repetitive Spotify account maintenance workflow. The tool controls a desktop Chrome session through Selenium and uses a VPN browser extension to reach the account settings flow from a selected region.

## The problem

Changing the country associated with a Spotify account can require repeating the same browser steps: launch a suitable connection, authenticate, open the account settings, select the new country, and confirm the change. Repeating that flow manually is slow and easy to get wrong.

## The solution

This desktop utility packages the workflow into a small JavaFX application. It starts Chrome with the configured VPN extension, performs the login flow, stores the browser session locally, and automates the country-selection steps on Spotify's official account page.

The tool is intentionally personal and local: credentials are entered into the automated browser session, cookies stay in a local gitignored file, and no account data is sent to a separate service.

## Technology

- Java 23
- JavaFX and FXML
- Selenium WebDriver
- JUnit 5 and Mockito
- Maven

## Requirements

- JDK 23;
- Maven or the included Maven Wrapper;
- Chrome and a matching ChromeDriver on `PATH`;
- the packaged VPN extension `3.2.1_0.crx` in the project root.

## Run

```bash
./mvnw clean javafx:run
```

On Windows:

```cmd
mvnw.cmd clean javafx:run
```

Enter the account credentials in the local application window and select the target region.

## Test

```bash
./mvnw test
```

## Security and operational notes

- `cookies.data` is local session state and is ignored by Git; delete it to force a new login.
- Never commit credentials, cookies, VPN extension keys, or personal configuration.
- Automating Spotify's web interface may violate Spotify's Terms of Service. This project is for personal and educational use; operate it only where permitted and at your own risk.

## License

This personal automation is available under the [Creative Commons Attribution-NonCommercial 4.0 International license](LICENSE). Attribution to Loginov Gleb is required; commercial use requires prior written permission.
