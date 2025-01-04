package org.example.spotifyexchangeregion.models;

public class Account {
    String login;
    String password;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Account(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
