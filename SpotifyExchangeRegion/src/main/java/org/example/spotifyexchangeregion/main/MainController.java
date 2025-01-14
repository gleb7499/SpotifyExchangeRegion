package org.example.spotifyexchangeregion.main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.spotifyexchangeregion.models.Account;

public class MainController {
    @FXML
    private Button exchangeButton;
    @FXML
    private Label infoText;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;

    private Account account;
    private final static String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    @FXML
    private void onExchangeButtonClick() {
        exchangeButton.setDisable(true);
        setAccount();
        if (checkDataFromField()) {
            startExchange();
        } else {
            infoText.setText("Некорректные данные!");
        }
        exchangeButton.setDisable(false);
    }

    public void setAccount() {
        account = new Account(loginField.getText(), passwordField.getText());
    }

    public boolean checkDataFromField() {
        return account.getLogin().matches(EMAIL_REGEX) && account.getPassword().length() >= 8;
    }

    private void startExchange() {
        try {
            Main.changeRegion(account);
        } catch (Exception e) {
            infoText.setText("Ошибка\n" + e.getMessage());
        }
    }
}