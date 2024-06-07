package dev.codescreen.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dev.codescreen.model.Amount;
import dev.codescreen.model.DebitCredit;
import dev.codescreen.model.Transaction;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EventSourcingDaoTest {

    private MongoDatabase mockDatabase;
    private MongoCollection<Document> mockCollection;
    private EventSourcingDao eventSourcingDao;

    @BeforeEach
    public void setUp() {
        mockDatabase = mock(MongoDatabase.class);
        mockCollection = mock(MongoCollection.class);
        when(mockDatabase.getCollection("Transactions")).thenReturn(mockCollection);

        eventSourcingDao = new EventSourcingDao(mockDatabase);
    }

    @Test
    public void testAddTransaction() {
        Transaction transaction = new Transaction();
        Amount transactionAmount = new Amount();
        transactionAmount.setAmount("100");
        transactionAmount.setCurrency("USD");
        transactionAmount.setDebitOrCredit(DebitCredit.CREDIT);
        transaction.setTransactionAmount(transactionAmount);
        transaction.setUserId("user123");
        transaction.setMessageId("msg123");
        transaction.setTransactionType(DebitCredit.CREDIT);
        transaction.setTimestamp(LocalDateTime.now());

        int result = eventSourcingDao.addTransaction(transaction);

        ArgumentCaptor<Document> documentCaptor = ArgumentCaptor.forClass(Document.class);
        verify(mockCollection, times(1)).insertOne(documentCaptor.capture());

        Document capturedDocument = documentCaptor.getValue();
        assertEquals("100", capturedDocument.get("Amount", Document.class).get("amount"));
        assertEquals("USD", capturedDocument.get("Amount", Document.class).get("currency"));
        assertEquals(DebitCredit.CREDIT, capturedDocument.get("Amount", Document.class).get("debitOrCredit"));
        assertEquals("user123", capturedDocument.getString("userId"));
        assertEquals("msg123", capturedDocument.getString("messageId"));
        assertEquals(DebitCredit.CREDIT, capturedDocument.get("transactionType"));
        assertEquals(result, 1);
    }
}
