package dev.codescreen.api;

import dev.codescreen.model.Amount;
import dev.codescreen.model.DebitCredit;
import dev.codescreen.model.LoadRequest;
import dev.codescreen.model.LoadResponse;
import dev.codescreen.service.LoadService;
import dev.codescreen.model.Error;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoadApiControllerTest {


    @Mock
    private NativeWebRequest request;

    @Mock
    private LoadService loadService;

    private LoadApiController loadApiController;

    @BeforeEach
    void setUp() {
        loadApiController = new LoadApiController(request, loadService);
    }

    //A Valid Request
    @Test
    void testLoadPut_ValidRequest() {
        LoadRequest request = new LoadRequest();
        request.setMessageId("123");
        request.setUserId("user123");
        request.setTransactionAmount(new Amount("100.00", "USD", DebitCredit.CREDIT));

        LoadResponse response = new LoadResponse();
        response.setUserId("user123");
        response.setMessageId("123");
        response.setBalance(new Amount("100.00", "USD", DebitCredit.CREDIT));

        when(loadService.getResponse()).thenReturn(response);

        ResponseEntity<?> entity = loadApiController.loadPut(request);

        assertEquals(HttpStatus.CREATED, entity.getStatusCode());
        assertEquals("/load", entity.getHeaders().getLocation().getPath());
        assertEquals(response, entity.getBody());
    }

    //Failure CASES
    //Missing Message Id
    @Test
    void testLoadPut_MissingMessageId() {
        LoadRequest request = new LoadRequest();
        request.setUserId("user123");
        request.setTransactionAmount(new Amount("100.00", "USD", DebitCredit.CREDIT));

        ResponseEntity<?> entity = loadApiController.loadPut(request);

        assertEquals(HttpStatus.BAD_REQUEST, entity.getStatusCode());
    }

    //Missing Transaction Amount
    @Test
    void testLoadPut_MissingTransactionAmount() {
        // Given
        LoadRequest request = new LoadRequest();
        request.setMessageId("123");
        request.setUserId("user123");

        Error expectedError = new Error();
        expectedError.setMessage("Transaction amount is missing");
        expectedError.setCode("400");

        // When
        ResponseEntity<?> responseEntity = loadApiController.loadPut(request);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(expectedError, responseEntity.getBody());
    }

    //Missing User Id
    @Test
    void testLoadPut_MissingUserId() {
        // Given
        LoadRequest request = new LoadRequest();
        request.setMessageId("123");
        request.setTransactionAmount(new Amount("100.00", "USD", DebitCredit.CREDIT));

        Error expectedError = new Error();
        expectedError.setMessage("User Id is missing");
        expectedError.setCode("400");

        // When
        ResponseEntity<?> responseEntity = loadApiController.loadPut(request);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(expectedError, responseEntity.getBody());
    }

    //No amount
    @Test
    void testLoadPut_InvalidAmount() {
        // Given
        LoadRequest request = new LoadRequest();
        request.setMessageId("123");
        request.setUserId("user123");
        request.setTransactionAmount(new Amount("abc", "USD", DebitCredit.CREDIT));

        Error expectedError = new Error();
        expectedError.setMessage("Amount is missing or not a valid number");
        expectedError.setCode("400");

        // When
        ResponseEntity<?> responseEntity = loadApiController.loadPut(request);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(expectedError, responseEntity.getBody());
    }

    //No currency
    @Test
    void testLoadPut_MissingCurrency() {
        // Given
        LoadRequest request = new LoadRequest();
        request.setMessageId("123");
        request.setUserId("user123");
        request.setTransactionAmount(new Amount("100", "", DebitCredit.CREDIT));

        Error expectedError = new Error();
        expectedError.setMessage("Currency is missing");
        expectedError.setCode("400");

        // When
        ResponseEntity<?> responseEntity = loadApiController.loadPut(request);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(expectedError, responseEntity.getBody());
    }

    //Throws Exception
    @Test
    void testLoadPut_InternalServerError() {
        // Given
        LoadRequest request = new LoadRequest();
        request.setMessageId("123");
        request.setUserId("user123");
        request.setTransactionAmount(new Amount("100.00", "USD", DebitCredit.CREDIT));

        doThrow(new RuntimeException("Internal server error")).when(loadService).setLoadRequest(any());

        Error expectedError = new Error();
        expectedError.setMessage("Internal server error");
        expectedError.setCode("500");

        // When
        ResponseEntity<?> responseEntity = loadApiController.loadPut(request);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(expectedError, responseEntity.getBody());
    }

}