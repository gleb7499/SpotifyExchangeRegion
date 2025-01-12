package org.example.spotifyexchangeregion.main;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.spotifyexchangeregion.models.Account;

public class MainController {
    @FXML
    private Label infoText;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;

    private Account account;

    @FXML
    private void onExchangeButtonClick() {
        setAccount();
        if (checkDataFromField()) {
            startExchange();
        } else {
            infoText.setText("Некорректные данные!");
        }
    }

    public void setAccount() {
        account = new Account(loginField.getText(), passwordField.getText());
    }

    public boolean checkDataFromField() {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return account.getLogin().matches(emailRegex) && account.getPassword().length() >= 8;
    }

    private void startExchange() {
        try {
            Main.changeRegion(account);
        } catch (Exception e) {
            infoText.setText("Ошибка\n" + e.getMessage());
        }
    }
}