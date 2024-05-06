package dev.codescreen;

import dev.codescreen.api.AuthorizationApiController;
import dev.codescreen.model.*;
import dev.codescreen.model.Error;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthorizationApiControllerIntegrationTest {

    private static RestTemplate restTemplate;
    private String baseUrl = "http://localhost";

    @Autowired
    private AuthorizationApiController authorizationApiController;


    @BeforeAll
    public static void init(){
        restTemplate = new RestTemplate();
    }


    @Test
    void testLoadPut_Success() throws Exception {
        // Given
        AuthorizationRequest request = new AuthorizationRequest();
        request.setMessageId("123");
        request.setUserId("user123");
        Amount amount = new Amount("100.00", "USD", DebitCredit.CREDIT);
        request.setTransactionAmount(amount);

        //when(loadService.getResponse()).thenReturn(response);

        ResponseEntity<AuthorizationResponse> entity = restTemplate.exchange(
                baseUrl.concat(":8080") + "/authorization",
                HttpMethod.PUT,
                new HttpEntity<>(request),
                AuthorizationResponse.class
        );

        // Then
        assertEquals(HttpStatus.CREATED, entity.getStatusCode());
        AuthorizationResponse responseBody = entity.getBody();
        assertNotNull(responseBody);
        assertEquals("user123", responseBody.getUserId());
        assertEquals("123", responseBody.getMessageId());
        assertEquals(ResponseCode.DECLINED, responseBody.getResponseCode());
        assertEquals(" ", responseBody.getBalance().getAmount());
        assertEquals(" ", responseBody.getBalance().getCurrency());

        // When/Then
        LoadRequest loadRequest = new LoadRequest();
        request.setMessageId("123");
        request.setUserId("user123");
        Amount amount1 = new Amount("100.00", "USD", DebitCredit.CREDIT);
        request.setTransactionAmount(amount);

        //when(loadService.getResponse()).thenReturn(response);

        ResponseEntity<LoadResponse> entity1 = restTemplate.exchange(
                baseUrl.concat(":8080") + "/load",
                HttpMethod.PUT,
                new HttpEntity<>(request),
                LoadResponse.class
        );
        amount.setAmount("10.0");
        request.setTransactionAmount(amount);

        entity = restTemplate.exchange(
                baseUrl.concat(":8080") + "/authorization",
                HttpMethod.PUT,
                new HttpEntity<>(request),
                AuthorizationResponse.class
        );

        // Then
        assertEquals(HttpStatus.CREATED, entity.getStatusCode());
        responseBody = entity.getBody();
        assertNotNull(responseBody);
        assertEquals("user123", responseBody.getUserId());
        assertEquals("123", responseBody.getMessageId());
        assertEquals(ResponseCode.APPROVED, responseBody.getResponseCode());
        assertEquals(90, Double.parseDouble(responseBody.getBalance().getAmount()));

    }

    @Test
    void testLoadPut_MissingUserId() throws Exception {
        // Given
        AuthorizationRequest request = new AuthorizationRequest();
        request.setMessageId("123");
        request.setTransactionAmount(new Amount("100.00", "USD", DebitCredit.CREDIT));

        dev.codescreen.model.Error error = new dev.codescreen.model.Error("User Id is missing");
        error.setCode("400");
        // When
        try {
            ResponseEntity<dev.codescreen.model.Error> entity = restTemplate.exchange(
                    baseUrl.concat(":8080") + "/authorization",
                    HttpMethod.PUT,
                    new HttpEntity<>(request),
                    dev.codescreen.model.Error.class
            );
        } catch (HttpClientErrorException ex) {
            // Handle HttpClientErrorException (e.g., HTTP status code 4xx)
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
            assertTrue(ex.getResponseBodyAs(dev.codescreen.model.Error.class).equals(error));        }

    }

    @Test
    void testLoadPut_MissingTransactionAmount() throws Exception {
        // Given
        AuthorizationRequest request = new AuthorizationRequest();
        request.setMessageId("123");
        request.setUserId("user123");

        dev.codescreen.model.Error error = new dev.codescreen.model.Error("Transaction amount is missing");
        error.setCode("400");
        // When
        try {
            ResponseEntity<dev.codescreen.model.Error> entity = restTemplate.exchange(
                    baseUrl.concat(":8080") + "/authorization",
                    HttpMethod.PUT,
                    new HttpEntity<>(request),
                    dev.codescreen.model.Error.class
            );
        } catch (HttpClientErrorException ex) {
            // Handle HttpClientErrorException (e.g., HTTP status code 4xx)
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
            assertTrue(ex.getResponseBodyAs(dev.codescreen.model.Error.class).equals(error));
        }
    }

    @Test
    void testLoadPut_MissingMessage() throws Exception {
        // Given
        AuthorizationRequest request = new AuthorizationRequest();
        request.setTransactionAmount(new Amount("100.00", "USD", DebitCredit.CREDIT));
        request.setUserId("user123");

        dev.codescreen.model.Error error = new dev.codescreen.model.Error("Message Id is missing");
        error.setCode("400");
        // When
        try {
            ResponseEntity<dev.codescreen.model.Error> entity = restTemplate.exchange(
                    baseUrl.concat(":8080") + "/authorization",
                    HttpMethod.PUT,
                    new HttpEntity<>(request),
                    dev.codescreen.model.Error.class
            );
        } catch (HttpClientErrorException ex) {
            // Handle HttpClientErrorException (e.g., HTTP status code 4xx)
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
            assertTrue(ex.getResponseBodyAs(dev.codescreen.model.Error.class).equals(error));        }

        request.setMessageId(null);
        try {
            ResponseEntity<dev.codescreen.model.Error> entity = restTemplate.exchange(
                    baseUrl.concat(":8080") + "/authorization",
                    HttpMethod.PUT,
                    new HttpEntity<>(request),
                    dev.codescreen.model.Error.class
            );
        } catch (HttpClientErrorException ex) {
            // Handle HttpClientErrorException (e.g., HTTP status code 4xx)
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
            assertTrue(ex.getResponseBodyAs(dev.codescreen.model.Error.class).equals(error));        }
    }

    @Test
    void testLoadPut_MissingAmount() throws Exception {
        // Given
        AuthorizationRequest request = new AuthorizationRequest();
        request.setTransactionAmount(new Amount("", "USD", DebitCredit.CREDIT));
        request.setUserId("user123");
        request.setMessageId("123");

        dev.codescreen.model.Error error = new dev.codescreen.model.Error("Amount is missing or not a valid number");
        error.setCode("400");
        // When
        try {
            ResponseEntity<dev.codescreen.model.Error> entity = restTemplate.exchange(
                    baseUrl.concat(":8080") + "/authorization",
                    HttpMethod.PUT,
                    new HttpEntity<>(request),
                    dev.codescreen.model.Error.class
            );
        } catch (HttpClientErrorException ex) {
            // Handle HttpClientErrorException (e.g., HTTP status code 4xx)
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
            assertTrue(ex.getResponseBodyAs(dev.codescreen.model.Error.class).equals(error));        }

        request.setTransactionAmount(new Amount(null, "USD", DebitCredit.CREDIT));
        try {
            ResponseEntity<dev.codescreen.model.Error> entity = restTemplate.exchange(
                    baseUrl.concat(":8080") + "/authorization",
                    HttpMethod.PUT,
                    new HttpEntity<>(request),
                    dev.codescreen.model.Error.class
            );
        } catch (HttpClientErrorException ex) {
            // Handle HttpClientErrorException (e.g., HTTP status code 4xx)
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
            assertTrue(ex.getResponseBodyAs(dev.codescreen.model.Error.class).equals(error));        }
    }

    @Test
    void testLoadPut_MissingCurrency() throws Exception {
        // Given
        AuthorizationRequest request = new AuthorizationRequest();
        request.setTransactionAmount(new Amount("100", "", DebitCredit.CREDIT));
        request.setUserId("user123");
        request.setMessageId("123");

        dev.codescreen.model.Error error = new dev.codescreen.model.Error("Currency is missing");
        error.setCode("400");
        // When
        try {
            ResponseEntity<dev.codescreen.model.Error> entity = restTemplate.exchange(
                    baseUrl.concat(":8080") + "/authorization",
                    HttpMethod.PUT,
                    new HttpEntity<>(request),
                    dev.codescreen.model.Error.class
            );
        } catch (HttpClientErrorException ex) {
            // Handle HttpClientErrorException (e.g., HTTP status code 4xx)
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
            assertTrue(ex.getResponseBodyAs(dev.codescreen.model.Error.class).equals(error));
        }

        request.setTransactionAmount(new Amount("100", null, DebitCredit.CREDIT));

        try {
            ResponseEntity<dev.codescreen.model.Error> entity = restTemplate.exchange(
                    baseUrl.concat(":8080") + "/authorization",
                    HttpMethod.PUT,
                    new HttpEntity<>(request),
                    dev.codescreen.model.Error.class
            );
        } catch (HttpClientErrorException ex) {
            // Handle HttpClientErrorException (e.g., HTTP status code 4xx)
            assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
            assertTrue(ex.getResponseBodyAs(Error.class).equals(error));
        }
    }

// Add more test methods for other scenarios like additional unexpected fields, etc.

}
