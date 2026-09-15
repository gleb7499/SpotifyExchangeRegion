# Contributing

Contributions are welcome — bug reports, feature ideas, and pull requests.

## Getting started

1. Fork the repository and clone your fork.
2. Make sure you have JDK 23, Maven, Chrome, and ChromeDriver installed.
3. Build and run:

   ```bash
   cd SpotifyExchangeRegion
   ./mvnw clean javafx:run
   ```

4. Run tests before submitting changes:

   ```bash
   ./mvnw test
   ```

## Guidelines

- Keep changes focused; one fix or feature per pull request.
- Match the existing code style (Java, package `org.example.spotifyexchangeregion`).
- Add or update tests for behavior changes.
- Never commit credentials, cookies, VPN extension keys, or other secrets (`cookies.data`, `secrets.txt` are gitignored — keep it that way).
- CSS selectors in `Parsing.java` target Spotify's live web pages and may break when Spotify updates their UI; if you change them, verify against the current page structure.

## Reporting issues

Open an issue with a clear description, steps to reproduce, and your environment (OS, Chrome version, JDK version).

## License

By contributing, you agree that your contributions are licensed under the [MIT License](LICENSE).
