package dev.codescreen.dao;

import dev.codescreen.model.Amount;
import dev.codescreen.model.DebitCredit;
import dev.codescreen.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*
Unit Test for User Balance DAO
 */
@ExtendWith(MockitoExtension.class)
class UserBalanceDaoTest {

    @Mock
    private HashMap<String, Amount> mockDB;
    private UserBalanceDao underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserBalanceDao(mockDB);
    }

    //Test Update Balance
    @Test
    void canUpdateBalance() {
        //given
        Amount amount = new Amount("100.23", "USD", DebitCredit.CREDIT);
        String userId = "2226e2f9-ih09-46a8-958f-d659880asdfD";

        // When
        underTest.updateBalance(userId, amount);

        //then
        ArgumentCaptor<String> userIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Amount> amountCaptor = ArgumentCaptor.forClass(Amount.class);
        verify(mockDB).put(userIdCaptor.capture(), amountCaptor.capture());

        // Verify the captured arguments
        assertEquals(userId, userIdCaptor.getValue());
        assertEquals(amount, amountCaptor.getValue());
    }

    //Test Get Balance
    @Test
    void getBalance() {
        //Given
        Amount expectedAmount = new Amount("100.23", "Dollar", DebitCredit.CREDIT);
        String userId = "2226e2f9-ih09-46a8-958f-d659880asdfD";

        when(mockDB.get(userId)).thenReturn(expectedAmount);

        // When
        Amount actualAmount = underTest.getBalance(userId);

        // Then
        assertEquals(expectedAmount, actualAmount);
    }
}