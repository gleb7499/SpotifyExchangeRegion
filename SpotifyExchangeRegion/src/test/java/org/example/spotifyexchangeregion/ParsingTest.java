package org.example.spotifyexchangeregion;

import org.example.spotifyexchangeregion.parsing.Parsing;
import org.example.spotifyexchangeregion.secrets.Secrets;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParsingTest {
    private static Parsing parsing;

    @BeforeAll
    static void setUp() {
        parsing = new Parsing("https://accounts.spotify.com/ru/login");
    }

    @Test
    void changeRegion() {
        Secrets.Initialize();
        Secrets.load();
        String login = Secrets.get("login");
        String password = Secrets.get("password");
        try {
            parsing.changeRegion(login, password);
            Thread.sleep(10000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}