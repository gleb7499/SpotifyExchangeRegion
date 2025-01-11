package org.example.spotifyexchangeregion;

import org.example.spotifyexchangeregion.parsing.Parsing;
import org.example.spotifyexchangeregion.secrets.Secrets;
import org.junit.jupiter.api.Test;

class ParsingTest {

    @Test
    void changeRegion() {
        Secrets.Initialize();
        Secrets.load();
        String login = Secrets.get("login");
        String password = Secrets.get("password");
        try (Parsing parsing = new Parsing("https://accounts.spotify.com/ru/login")) {
            parsing.changeRegion(login, password);
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}