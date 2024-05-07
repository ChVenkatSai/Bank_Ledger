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
import static org.mockito.Mockito.*;

/*
Unit Test for Authorization Service.
 */
@ExtendWith(MockitoExtension.class)
class AuthorizationServiceImplTest {


    @Mock
    private AuthorizationRequest mockAuthorizationRequest;

    @Mock
    private UserBalance mockUserBalance;

    @Mock
    private EventSourcing mockEventSourcing;

    private AuthorizationServiceImpl authorizationService;

    @BeforeEach
    void setUp() {
        authorizationService = new AuthorizationServiceImpl(mockAuthorizationRequest, mockUserBalance, mockEventSourcing);
    }

    //Testing for an approved response
    @Test
    void testGetResponse_Approved() {
        // Given
        String userId = "2226e2f9-ih09-46a8-958f-d659880asdfD";
        String messageId = "55210c62-e480-asdf-bc1b-e991ac67FSAC";
        Amount transactionAmount = new Amount("100.00", "USD", DebitCredit.DEBIT);
        when(mockAuthorizationRequest.getUserId()).thenReturn(userId);
        when(mockAuthorizationRequest.getMessageId()).thenReturn(messageId);
        when(mockAuthorizationRequest.getTransactionAmount()).thenReturn(transactionAmount);

        // Mock currentBalance to be sufficient for the transaction
        Amount currentBalance = new Amount("200.00", "USD", DebitCredit.CREDIT);
        when(mockUserBalance.getBalance(userId)).thenReturn(currentBalance);

        // When
        AuthorizationResponse response = authorizationService.getResponse();

        // Then
        assertEquals(ResponseCode.APPROVED, response.getResponseCode());
        assertEquals(userId, response.getUserId());
        assertEquals(messageId, response.getMessageId());
        assertEquals(100.00, Double.parseDouble(response.getBalance().getAmount()));

        // Verify interactions
        verify(mockUserBalance).updateBalance(eq(userId), any());
        verify(mockEventSourcing).addTransaction(any(Transaction.class));
    }

    //Testing for a declined Response
    @Test
    void testGetResponse_Declined() {
        // Given
        String userId = "2226e2f9-ih09-46a8-958f-d659880asdfD";
        String messageId = "55210c62-e480-asdf-bc1b-e991ac67FSAC";
        Amount transactionAmount = new Amount("300.00", "USD", DebitCredit.DEBIT);
        when(mockAuthorizationRequest.getUserId()).thenReturn(userId);
        when(mockAuthorizationRequest.getMessageId()).thenReturn(messageId);
        when(mockAuthorizationRequest.getTransactionAmount()).thenReturn(transactionAmount);

        // Mock currentBalance to be insufficient for the transaction
        Amount currentBalance = new Amount("200.00", "USD", DebitCredit.CREDIT);
        when(mockUserBalance.getBalance(userId)).thenReturn(currentBalance);

        // When
        AuthorizationResponse response = authorizationService.getResponse();

        // Then
        assertEquals(ResponseCode.DECLINED, response.getResponseCode());
        assertEquals(userId, response.getUserId());
        assertEquals(messageId, response.getMessageId());
        assertEquals(200.00, Double.parseDouble(response.getBalance().getAmount())); // Balance should remain unchanged

        // Verify interactions
        verify(mockUserBalance, times(2)).getBalance(userId);
        verify(mockUserBalance, never()).updateBalance(any(), any());
        verify(mockEventSourcing).addTransaction(any(Transaction.class));

        // Mock currentBalance to be null
        when(mockUserBalance.getBalance(userId)).thenReturn(null);

        // When
        response = authorizationService.getResponse();
        Amount expectedAmount = new Amount(" ", " ", DebitCredit.DEBIT);
        // Then
        assertEquals(ResponseCode.DECLINED, response.getResponseCode());
        assertEquals(userId, response.getUserId());
        assertEquals(messageId, response.getMessageId());
        assertEquals(expectedAmount, response.getBalance()); // Balance should be null when current balance is null

        // Verify interactions
        verify(mockUserBalance, times(3)).getBalance(userId); // Only one call to getBalance since it's null
        verify(mockUserBalance, never()).updateBalance(any(), any());
        verify(mockEventSourcing, times(2)).addTransaction(any(Transaction.class));
    }

    @Test
    void testSetResponse() {
        authorizationService.setAuthorizationRequest(mockAuthorizationRequest);
        assertEquals(mockAuthorizationRequest, authorizationService.getAuthorizationRequest());
    }
}