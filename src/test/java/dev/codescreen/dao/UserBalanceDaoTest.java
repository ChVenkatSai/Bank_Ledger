package dev.codescreen.dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dev.codescreen.model.Amount;
import dev.codescreen.model.DebitCredit;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class UserBalanceDaoTest {

    private MongoDatabase mockDatabase;
    private MongoCollection<Document> mockCollection;
    private UserBalanceDao userBalanceDao;
    private FindIterable<Document> mockFindIterable;

    @BeforeEach
    public void setUp() {
        mockDatabase = mock(MongoDatabase.class);
        mockCollection = mock(MongoCollection.class);
        mockFindIterable = mock(FindIterable.class);

        when(mockDatabase.getCollection("Balance")).thenReturn(mockCollection);

        userBalanceDao = new UserBalanceDao(mockDatabase);
    }

    @Test
    public void testUpdateBalance_InsertNewUser() {
        Amount amount = new Amount();
        amount.setAmount("100");
        amount.setCurrency("USD");
        amount.setDebitOrCredit(DebitCredit.CREDIT);

        Document queryDocument = new Document("_id", "user123");
        when(mockCollection.find(queryDocument)).thenReturn(mockFindIterable);
        when(mockFindIterable.first()).thenReturn(null);

        userBalanceDao.updateBalance("user123", amount);

        ArgumentCaptor<Document> documentCaptor = ArgumentCaptor.forClass(Document.class);
        verify(mockCollection).insertOne(documentCaptor.capture());
        Document capturedDocument = documentCaptor.getValue();

        assertEquals("user123", capturedDocument.getString("_id"));
        assertEquals("100", capturedDocument.getString("amount"));
        assertEquals("USD", capturedDocument.getString("currency"));
        assertEquals(DebitCredit.CREDIT, capturedDocument.get("debitOrCredit"));
    }


}
