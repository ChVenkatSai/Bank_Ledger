package dev.codescreen.model;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

/*
Unit test
Given that models only contain standard getter, setter, Printer and Equality methods
We only need to test the equality method. The Print methods are used for debugging and
Setter and Getter are standard.
 */

class GeneralTest {

    //Test Authorization Requests and Responses
    @Test
    void testEquals_Authorization() {

        Amount obj1 = new Amount("100.00", "USD", DebitCredit.CREDIT);/* initialize with values */
        ;
        Amount obj2 = new Amount("100.00", "USD", DebitCredit.CREDIT);/* initialize with same values */
        ;
        Amount obj3 = new Amount("506", " ", DebitCredit.CREDIT);
        assertTrue(obj1.equals(obj2), "Model1 objects should be equal");
        assertTrue(obj1.equals(obj1));
        assertFalse(obj1.equals(null));
        assertFalse(obj1.equals(obj3));

        AuthorizationRequest request1 = new AuthorizationRequest();
        request1.setMessageId("123");
        request1.setUserId("user123");
        request1.setTransactionAmount(new Amount("100.00", "USD", DebitCredit.DEBIT));

        AuthorizationRequest request2 = new AuthorizationRequest();
        request2.setMessageId("123");
        request2.setUserId("user123");
        request2.setTransactionAmount(new Amount("100.00", "USD", DebitCredit.DEBIT));

        assertTrue(request1.equals(request2), "AuthorizationRequest objects should be equal");

        // Test for non-equal AuthorizationRequest objects
        AuthorizationRequest request3 = new AuthorizationRequest();
        request3.setMessageId("123");
        request3.setUserId("user123");
        request3.setTransactionAmount(new Amount("200.00", "USD", DebitCredit.DEBIT));

        assertFalse(request1.equals(request3), "AuthorizationRequest objects should not be equal");

        //AuthorizationResponse
        AuthorizationResponse response1 = new AuthorizationResponse();
        response1.setMessageId("123");
        response1.setUserId("user123");
        request1.setTransactionAmount(new Amount("200.00", "USD", DebitCredit.CREDIT));
        response1.setResponseCode(ResponseCode.APPROVED);

        AuthorizationResponse response2 = new AuthorizationResponse();
        response2.setMessageId("123");
        response2.setUserId("user123");
        request2.setTransactionAmount(new Amount("200.00", "USD", DebitCredit.CREDIT));
        response2.setResponseCode(ResponseCode.APPROVED);

        assertTrue(response1.equals(response2), "AuthorizationResponse objects should be equal");

        // Test for non-equal AuthorizationResponse objects
        AuthorizationResponse response3 = new AuthorizationResponse();
        response3.setMessageId("123");
        response3.setUserId("user123");
        request3.setTransactionAmount(new Amount("200.00", "USD", DebitCredit.CREDIT));
        response3.setResponseCode(ResponseCode.DECLINED);

        assertFalse(response1.equals(response3), "AuthorizationResponse objects should not be equal");
    }

    //Test Error
    @Test
    public void testEquals_Error(){
        Error error1 = new Error();
        error1.setMessage("Message");
        error1.setCode("500");

        Error error2 = new Error();
        error2.setMessage("Message");
        error2.setCode("500");

        assertTrue(error1.equals(error2), "Error objects should be equal");

        // Test for non-equal Error objects
        Error error3 = new Error();
        error3.setMessage("Different Message");
        error3.setCode("500");

        assertFalse(error1.equals(error3), "Error objects should not be equal");

}

    //Test Load Request and responses.
    @Test
    void testEquals_Load() {
        // Test for equal LoadRequest objects
        LoadRequest request1 = new LoadRequest();
        request1.setMessageId("123");
        request1.setUserId("user123");
        Amount amount1 = new Amount("100.00", "USD", DebitCredit.CREDIT);
        request1.setTransactionAmount(amount1);

        LoadRequest request2 = new LoadRequest();
        request2.setMessageId("123");
        request2.setUserId("user123");
        Amount amount2 = new Amount("100.00", "USD", DebitCredit.CREDIT);
        request2.setTransactionAmount(amount2);

        assertTrue(request1.equals(request2), "LoadRequest objects should be equal");

        // Test for non-equal LoadRequest objects
        LoadRequest request3 = new LoadRequest();
        request3.setMessageId("456");
        request3.setUserId("user456");
        Amount amount3 = new Amount("200.00", "USD", DebitCredit.CREDIT);
        request3.setTransactionAmount(amount3);

        assertFalse(request1.equals(request3), "LoadRequest objects should not be equal");

        // Test for equal LoadResponse objects
        LoadResponse response1 = new LoadResponse();
        response1.setUserId("user123");
        response1.setMessageId("123");
        Amount balance1 = new Amount("100.00", "USD", DebitCredit.CREDIT);
        response1.setBalance(balance1);

        LoadResponse response2 = new LoadResponse();
        response2.setUserId("user123");
        response2.setMessageId("123");
        Amount balance2 = new Amount("100.00", "USD", DebitCredit.CREDIT);
        response2.setBalance(balance2);

        assertTrue(response1.equals(response2), "LoadResponse objects should be equal");

        // Test for non-equal LoadResponse objects
        LoadResponse response3 = new LoadResponse();
        response3.setUserId("user456");
        response3.setMessageId("456");
        Amount balance3 = new Amount("200.00", "USD", DebitCredit.CREDIT);
        response3.setBalance(balance3);

        assertFalse(response1.equals(response3), "LoadResponse objects should not be equal");
    }

    //Test Ping
    @Test
    void testEquals_Ping() {
        // Test for equal Ping objects
        OffsetDateTime time1 = OffsetDateTime.now();
        Ping ping1 = new Ping();
        ping1.setServerTime(time1);

        Ping ping2 = new Ping();
        ping2.setServerTime(time1);

        assertTrue(ping1.equals(ping2), "Ping objects should be equal");

        // Test for non-equal Ping objects
        OffsetDateTime time3 = OffsetDateTime.now().minusDays(1);
        Ping ping3 = new Ping();
        ping3.setServerTime(time3);

        assertFalse(ping1.equals(ping3), "Ping objects should not be equal");
    }
}