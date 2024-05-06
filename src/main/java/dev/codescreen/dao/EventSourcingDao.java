package dev.codescreen.dao;

import dev.codescreen.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Repository
public class EventSourcingDao implements EventSourcing{

    LinkedList<Transaction> TDB = new LinkedList<Transaction>();

    public void setDB(LinkedList<Transaction> TDB){
        this.TDB = TDB;
    }

    @Override
    public int addTransaction(Transaction transaction) {
        TDB.add(transaction);
        return 1;
    }
}
