package dev.codescreen.api;

import dev.codescreen.model.Amount;
import dev.codescreen.model.DebitCredit;
import dev.codescreen.model.LoadRequest;
import dev.codescreen.model.LoadResponse;
import dev.codescreen.service.LoadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void testLoadPut_MissingMessageId() {
        LoadRequest request = new LoadRequest();
        request.setUserId("user123");
        request.setTransactionAmount(new Amount("100.00", "USD", DebitCredit.CREDIT));

        ResponseEntity<?> entity = loadApiController.loadPut(request);

        assertEquals(HttpStatus.BAD_REQUEST, entity.getStatusCode());
    }
}