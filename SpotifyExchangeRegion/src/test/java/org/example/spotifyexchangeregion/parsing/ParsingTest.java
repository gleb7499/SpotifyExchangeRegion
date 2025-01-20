package org.example.spotifyexchangeregion.parsing;

import org.example.spotifyexchangeregion.models.Account;
import org.example.spotifyexchangeregion.secrets.Secrets;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ParsingTest {
    private static Parsing parsing;

    @BeforeAll
    static void setUp() {
        Secrets.Initialize();
        Secrets.load();
        parsing = new Parsing( new Account(Secrets.get("login"), Secrets.get("password")));
    }

    @AfterAll
    static void tearDown() {
        parsing.close();
    }

    @Test
    public void changeRegion() {
        try {
            parsing.changeRegion("USA");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}