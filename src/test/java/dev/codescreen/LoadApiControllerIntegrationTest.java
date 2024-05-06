package dev.codescreen;

import dev.codescreen.api.LoadApiController;
import dev.codescreen.model.Amount;
import dev.codescreen.model.DebitCredit;
import dev.codescreen.model.Error;
import dev.codescreen.model.LoadRequest;
import dev.codescreen.model.LoadResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class LoadApiControllerIntegrationTest {

    private static RestTemplate restTemplate;
    private String baseUrl = "http://localhost";

    @Autowired
    private LoadApiController loadApiController;


    @BeforeAll
    public static void init(){
        restTemplate = new RestTemplate();
    }


    @Test
    void testLoadPut_Success() throws Exception {
        // Given
        LoadRequest request = new LoadRequest();
        request.setMessageId("123");
        request.setUserId("user123");
        Amount amount = new Amount("100.00", "USD", DebitCredit.CREDIT);
        request.setTransactionAmount(amount);

        //when(loadService.getResponse()).thenReturn(response);

        ResponseEntity<LoadResponse> entity = restTemplate.exchange(
                baseUrl.concat(":8080") + "/load",
                HttpMethod.PUT,
                new HttpEntity<>(request),
                LoadResponse.class
        );

        // Then
        assertEquals(HttpStatus.CREATED, entity.getStatusCode());
        LoadResponse responseBody = entity.getBody();
        assertNotNull(responseBody);
        assertEquals("user123", responseBody.getUserId());
        assertEquals("123", responseBody.getMessageId());
        assertEquals("100.00", responseBody.getBalance().getAmount());
        assertEquals("USD", responseBody.getBalance().getCurrency());

        // When/Then
        amount.setAmount("1.46");
        request.setTransactionAmount(amount);

        entity = restTemplate.exchange(
                baseUrl.concat(":8080") + "/load",
                HttpMethod.PUT,
                new HttpEntity<>(request),
                LoadResponse.class
        );

        // Then
        assertEquals(HttpStatus.CREATED, entity.getStatusCode());
        responseBody = entity.getBody();
        assertNotNull(responseBody);
        assertEquals("user123", responseBody.getUserId());
        assertEquals("123", responseBody.getMessageId());
        assertEquals(101.46, Double.parseDouble(responseBody.getBalance().getAmount()));

    }

    @Test
    void testLoadPut_MissingUserId() throws Exception {
        // Given
        LoadRequest request = new LoadRequest();
        request.setMessageId("123");
        request.setTransactionAmount(new Amount("100.00", "USD", DebitCredit.CREDIT));

        Error error = new Error("User Id is missing");
        error.setCode("400");
        // When
        try {
            ResponseEntity<Error> entity = restTemplate.exchange(
                    baseUrl.concat(":8080") + "/load",
                    HttpMethod.PUT,
                    new HttpEntity<>(request),
                    Error.class
            );
        } catch (HttpClientErrorException ex) {
            // Handle HttpClientErrorException (e.g., HTTP status code 4xx)
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
            assertTrue(ex.getResponseBodyAs(Error.class).equals(error));        }

    }

    @Test
    void testLoadPut_MissingTransactionAmount() throws Exception {
        // Given
        LoadRequest request = new LoadRequest();
        request.setMessageId("123");
        request.setUserId("user123");

        Error error = new Error("Transaction amount is missing");
        error.setCode("400");
        // When
        try {
            ResponseEntity<Error> entity = restTemplate.exchange(
                    baseUrl.concat(":8080") + "/load",
                    HttpMethod.PUT,
                    new HttpEntity<>(request),
                    Error.class
            );
        } catch (HttpClientErrorException ex) {
            // Handle HttpClientErrorException (e.g., HTTP status code 4xx)
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
            assertTrue(ex.getResponseBodyAs(Error.class).equals(error));
        }
    }

    @Test
    void testLoadPut_MissingMessage() throws Exception {
        // Given
        LoadRequest request = new LoadRequest();
        request.setTransactionAmount(new Amount("100.00", "USD", DebitCredit.CREDIT));
        request.setUserId("user123");

        Error error = new Error("Message Id is missing");
        error.setCode("400");
        // When
        try {
            ResponseEntity<Error> entity = restTemplate.exchange(
                    baseUrl.concat(":8080") + "/load",
                    HttpMethod.PUT,
                    new HttpEntity<>(request),
                    Error.class
            );
        } catch (HttpClientErrorException ex) {
            // Handle HttpClientErrorException (e.g., HTTP status code 4xx)
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
            assertTrue(ex.getResponseBodyAs(Error.class).equals(error));        }

        request.setMessageId(null);
        try {
            ResponseEntity<Error> entity = restTemplate.exchange(
                    baseUrl.concat(":8080") + "/load",
                    HttpMethod.PUT,
                    new HttpEntity<>(request),
                    Error.class
            );
        } catch (HttpClientErrorException ex) {
            // Handle HttpClientErrorException (e.g., HTTP status code 4xx)
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
            assertTrue(ex.getResponseBodyAs(Error.class).equals(error));        }
    }

    @Test
    void testLoadPut_MissingAmount() throws Exception {
        // Given
        LoadRequest request = new LoadRequest();
        request.setTransactionAmount(new Amount("", "USD", DebitCredit.CREDIT));
        request.setUserId("user123");
        request.setMessageId("123");

        Error error = new Error("Amount is missing or not a valid number");
        error.setCode("400");
        // When
        try {
            ResponseEntity<Error> entity = restTemplate.exchange(
                    baseUrl.concat(":8080") + "/load",
                    HttpMethod.PUT,
                    new HttpEntity<>(request),
                    Error.class
            );
        } catch (HttpClientErrorException ex) {
            // Handle HttpClientErrorException (e.g., HTTP status code 4xx)
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
            assertTrue(ex.getResponseBodyAs(Error.class).equals(error));        }

        request.setTransactionAmount(new Amount(null, "USD", DebitCredit.CREDIT));
        try {
            ResponseEntity<Error> entity = restTemplate.exchange(
                    baseUrl.concat(":8080") + "/load",
                    HttpMethod.PUT,
                    new HttpEntity<>(request),
                    Error.class
            );
        } catch (HttpClientErrorException ex) {
            // Handle HttpClientErrorException (e.g., HTTP status code 4xx)
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
            assertTrue(ex.getResponseBodyAs(Error.class).equals(error));        }
    }

    @Test
    void testLoadPut_MissingCurrency() throws Exception {
        // Given
        LoadRequest request = new LoadRequest();
        request.setTransactionAmount(new Amount("100", "", DebitCredit.CREDIT));
        request.setUserId("user123");
        request.setMessageId("123");

        Error error = new Error("Currency is missing");
        error.setCode("400");
        // When
        try {
            ResponseEntity<Error> entity = restTemplate.exchange(
                    baseUrl.concat(":8080") + "/load",
                    HttpMethod.PUT,
                    new HttpEntity<>(request),
                    Error.class
            );
        } catch (HttpClientErrorException ex) {
            // Handle HttpClientErrorException (e.g., HTTP status code 4xx)
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
            assertTrue(ex.getResponseBodyAs(Error.class).equals(error));
        }

        request.setTransactionAmount(new Amount("100", null, DebitCredit.CREDIT));

        try {
            ResponseEntity<Error> entity = restTemplate.exchange(
                    baseUrl.concat(":8080") + "/load",
                    HttpMethod.PUT,
                    new HttpEntity<>(request),
                    Error.class
            );
        } catch (HttpClientErrorException ex) {
            // Handle HttpClientErrorException (e.g., HTTP status code 4xx)
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
            assertTrue(ex.getResponseBodyAs(Error.class).equals(error));
        }
    }

// Add more test methods for other scenarios like additional unexpected fields, etc.

}