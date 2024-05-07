package dev.codescreen.dao;

import dev.codescreen.model.Amount;
import dev.codescreen.model.DebitCredit;
import dev.codescreen.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/*
Unit test for Event Sourcing DAO
 */
@ExtendWith(MockitoExtension.class)
class EventSourcingDaoTest {

    @Mock
    private LinkedList<Transaction> mockDB;
    private EventSourcingDao underTest;

    @BeforeEach
    void setUp(){
        underTest = new EventSourcingDao();
        underTest.setDB(mockDB);
    }

    //Add a transaction.
    @Test
    void CanAddTransaction() {
        //given
        Amount amount = new Amount("100.23", "USD", DebitCredit.CREDIT);
        Transaction transaction = new Transaction(amount, "2226e2f9-ih09-46a8-958f-d659880asdfD", "55210c62-e480-asdf-bc1b-e991ac67FSAC", DebitCredit.CREDIT, LocalDateTime.now());
        //when
        int result = underTest.addTransaction(transaction);
        //then
        ArgumentCaptor<Transaction> transactionArgumentCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(mockDB).add(transactionArgumentCaptor.capture());
        Transaction capturedTransaction = transactionArgumentCaptor.getValue();
        assertThat(capturedTransaction).isEqualTo(transaction);
        assertEquals(1, result);

    }
}