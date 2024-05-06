package dev.codescreen.service;

import dev.codescreen.dao.EventSourcing;
import dev.codescreen.dao.UserBalance;
import dev.codescreen.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoadServiceImplTest {

    @Mock
    private LoadRequest mockLoadRequest;

    @Mock
    private UserBalance mockUserBalance;

    @Mock
    private EventSourcing mockEventSourcing;

    private LoadServiceImpl loadService;

    @BeforeEach
    void setUp() {
        loadService = new LoadServiceImpl(mockLoadRequest, mockUserBalance, mockEventSourcing);
    }

    @Test
    void testGetResponse() {
        // Given
        String userId = "2226e2f9-ih09-46a8-958f-d659880asdfD";
        String messageId = "55210c62-e480-asdf-bc1b-e991ac67FSAC";
        Amount transactionAmount = new Amount("100.00", "USD", DebitCredit.CREDIT);
        when(mockLoadRequest.getUserId()).thenReturn(userId);
        when(mockLoadRequest.getMessageId()).thenReturn(messageId);
        when(mockLoadRequest.getTransactionAmount()).thenReturn(transactionAmount);

        Amount currentBalance = new Amount("500.00", "USD", DebitCredit.CREDIT);
        when(mockUserBalance.getBalance(userId)).thenReturn(currentBalance);

        // When
        LoadResponse response = loadService.getResponse();

        // Then
        assertEquals(userId, response.getUserId());
        assertEquals(messageId, response.getMessageId());
        assertEquals(600.00, Double.parseDouble(response.getBalance().getAmount())); // Expected updated balance: 500 (current) + 100 (transaction)

        // Verify that userBalance.updateBalance and eventSourcing.addTransaction were called
        verify(mockUserBalance).updateBalance(eq(userId), any());
        verify(mockEventSourcing).addTransaction(any(Transaction.class));
    }


    @Test
    void testSetLoadRequest() {
        loadService.setLoadRequest(mockLoadRequest);
        assertEquals(mockLoadRequest, loadService.getLoadRequest());
    }

    @Test
    void testGetResponseNoUser(){
        // Given
        String userId = "2226e2f9-ih09-46a8-958f-d659880asdfD";
        String messageId = "55210c62-e480-asdf-bc1b-e991ac67FSAC";
        Amount transactionAmount = new Amount("100.00", "USD", DebitCredit.CREDIT);
        when(mockLoadRequest.getUserId()).thenReturn(userId);
        when(mockLoadRequest.getMessageId()).thenReturn(messageId);
        when(mockLoadRequest.getTransactionAmount()).thenReturn(transactionAmount);

        Amount currentBalance = null;
        when(mockUserBalance.getBalance(userId)).thenReturn(currentBalance);

        // When
        LoadResponse response = loadService.getResponse();

        // Then
        assertEquals(userId, response.getUserId());
        assertEquals(messageId, response.getMessageId());
        assertEquals(100.00, Double.parseDouble(response.getBalance().getAmount())); // Expected updated balance: 500 (current) + 100 (transaction)

        // Verify that userBalance.updateBalance and eventSourcing.addTransaction were called
        verify(mockUserBalance).updateBalance(eq(userId), any());
        verify(mockEventSourcing).addTransaction(any(Transaction.class));

    }


}