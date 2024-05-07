package dev.codescreen.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import dev.codescreen.model.*;
import dev.codescreen.service.AuthorizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;

/*
Unit Test for Load.
 */
@ExtendWith(MockitoExtension.class)
class AuthorizationApiControllerTest {

    private AuthorizationApiController authorizationApiController;

    @Mock
    private NativeWebRequest mockRequest;

    @Mock
    private AuthorizationService mockAuthorizationService;

    @BeforeEach
    void setUp() {
        authorizationApiController = new AuthorizationApiController(mockRequest, mockAuthorizationService);
    }

    //A Valid Request
    @Test
    void authorizationPut_ValidRequest() {
        // Given
        AuthorizationRequest request = new AuthorizationRequest();
        request.setMessageId("123");
        request.setUserId("user123");
        request.setTransactionAmount(new Amount("100.00", "USD", DebitCredit.CREDIT));

        AuthorizationResponse response = new AuthorizationResponse();
        response.setUserId("user123");
        response.setMessageId("123");
        response.setResponseCode(ResponseCode.DECLINED);
        response.setBalance(new Amount("100.00", "USD", DebitCredit.CREDIT));

        when(mockAuthorizationService.getResponse()).thenReturn(response);

        // When
        ResponseEntity<?> entity = authorizationApiController.authorizationPut(request);

        // Then
        assertEquals(HttpStatus.CREATED, entity.getStatusCode());
        assertEquals("/authorization", entity.getHeaders().getLocation().getPath());
        assertEquals(response, entity.getBody());
    }

    //Failure CASES
    //Missing Message Id
    @Test
    void authorizationPut_MissingMessageId() {
        // Given
        AuthorizationRequest request = new AuthorizationRequest();
        request.setUserId("user123");
        request.setTransactionAmount(new Amount("100.00", "USD", DebitCredit.CREDIT));

        // When
        ResponseEntity<?> entity = authorizationApiController.authorizationPut(request);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, entity.getStatusCode());
    }

    //Missing Transaction Amount
    @Test
    void authorizationPut_MissingTransactionAmount() {
        // Given
        AuthorizationRequest request = new AuthorizationRequest();
        request.setMessageId("123");
        request.setUserId("user123");

        // When
        ResponseEntity<?> entity = authorizationApiController.authorizationPut(request);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, entity.getStatusCode());
    }

    //Missing User Id
    @Test
    void authorizationPut_MissingUserId() {
        // Given
        AuthorizationRequest request = new AuthorizationRequest();
        request.setMessageId("123");
        request.setTransactionAmount(new Amount("100.00", "USD", DebitCredit.CREDIT));

        // When
        ResponseEntity<?> entity = authorizationApiController.authorizationPut(request);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, entity.getStatusCode());
    }

    //No amount
    @Test
    void authorizationPut_MissingAmount() {
        // Given
        AuthorizationRequest request = new AuthorizationRequest();
        request.setMessageId("123");
        request.setUserId("user123");
        request.setTransactionAmount(new Amount("", "USD", DebitCredit.CREDIT));

        // When
        ResponseEntity<?> entity = authorizationApiController.authorizationPut(request);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, entity.getStatusCode());
    }

    //No currency
    @Test
    void authorizationPut_MissingCurrency() {
        // Given
        AuthorizationRequest request = new AuthorizationRequest();
        request.setMessageId("123");
        request.setUserId("user123");
        request.setTransactionAmount(new Amount("100.00", "", DebitCredit.CREDIT));

        // When
        ResponseEntity<?> entity = authorizationApiController.authorizationPut(request);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, entity.getStatusCode());
    }

    //Throws Exception
    @Test
    void authorizationPut_InternalServerError() {
        // Given
        AuthorizationRequest request = new AuthorizationRequest();
        request.setMessageId("123");
        request.setUserId("user123");
        request.setTransactionAmount(new Amount("100.00", "USD", DebitCredit.CREDIT));

        doThrow(new RuntimeException("Internal server error")).when(mockAuthorizationService).setAuthorizationRequest(any());
        // When
        ResponseEntity<?> entity = authorizationApiController.authorizationPut(request);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, entity.getStatusCode());
    }

}
