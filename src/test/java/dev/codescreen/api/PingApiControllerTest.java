package dev.codescreen.api;

import dev.codescreen.model.Ping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PingApiControllerTest {

    private PingApiController pingApiController;

    @BeforeEach
    void setUp() {
        NativeWebRequest mockRequest = mock(NativeWebRequest.class);
        pingApiController = new PingApiController(mockRequest);
    }

    @Test
    void testPingGet() {
        // Create a fake current time for the expected Ping response
        OffsetDateTime currentTime = OffsetDateTime.now();

        // Call the pingGet method
        ResponseEntity<Ping> responseEntity = pingApiController.pingGet();

        // Check that the response status is OK (200)
        assertEquals(200, responseEntity.getStatusCodeValue());

        // Get the actual Ping object from the response
        Ping ping = responseEntity.getBody();

        // Check that the Ping object is not null
        assertNotNull(ping);

        // Check that the server time in the Ping object is close to the current time
        assertTrue(currentTime.isBefore(ping.getServerTime().plusSeconds(1)));
        assertTrue(currentTime.isAfter(ping.getServerTime().minusSeconds(1)));
    }
}
