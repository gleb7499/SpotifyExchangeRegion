package org.example.spotifyexchangeregion.main;

import org.example.spotifyexchangeregion.models.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class MainControllerTest {

    @Mock
    private Account account;

    @InjectMocks
    private MainController mainController;

    private AutoCloseable autoCloseable;

    @BeforeEach
    public void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void testCheckDataFromField_InvalidEmail() {
        lenient().when(account.login()).thenReturn("invalid-email");
        lenient().when(account.password()).thenReturn("password123");
        Assertions.assertFalse(mainController.checkDataFromField());
    }

    @Test
    public void testCheckDataFromField_InvalidPassword() {
        lenient().when(account.login()).thenReturn("test@test.com");
        lenient().when(account.password()).thenReturn("short");
        Assertions.assertFalse(mainController.checkDataFromField());
    }

    @Test
    public void testCheckDataFromField_ValidData() {
        lenient().when(account.login()).thenReturn("test@test.com");
        lenient().when(account.password()).thenReturn("password123");
        Assertions.assertTrue(mainController.checkDataFromField());
    }
}
