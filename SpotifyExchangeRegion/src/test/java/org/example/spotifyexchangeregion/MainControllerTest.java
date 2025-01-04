package org.example.spotifyexchangeregion;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MainControllerTest {

    @Mock
    private Label infoText;

    @Mock
    private TextField loginField;

    @Mock
    private TextField passwordField;

    @InjectMocks
    private MainController mainController;

    @BeforeAll
    public static void initToolkit() {
        Platform.startup(() -> {
        });
    }

    @Test
    void testCheckDataFromField_InvalidEmail() {
        when(loginField.getText()).thenReturn("invalid-email");
        when(passwordField.getText()).thenReturn("password123");

        mainController.setAccount();

        Assertions.assertFalse(mainController.checkDataFromField());
    }

    @Test
    void testCheckDataFromField_InvalidPassword() {
        when(loginField.getText()).thenReturn("test@test.com");
        when(passwordField.getText()).thenReturn("short");

        mainController.setAccount();

        Assertions.assertFalse(mainController.checkDataFromField());
    }

    @Test
    void testCheckDataFromField_ValidData() {
        when(loginField.getText()).thenReturn("test@test.com");
        when(passwordField.getText()).thenReturn("password123");

        mainController.setAccount();

        Assertions.assertTrue(mainController.checkDataFromField());
    }
}
