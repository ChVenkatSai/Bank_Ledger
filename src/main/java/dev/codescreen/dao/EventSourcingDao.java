package dev.codescreen.dao;

import dev.codescreen.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EventSourcingDao implements EventSourcing{

    ArrayList<Transaction> DB = new ArrayList<Transaction>();

    public void setDB(ArrayList<Transaction> DB){
        this.DB = DB;
    }

    @Override
    public int addTransaction(Transaction transaction) {
        DB.add(transaction);
        return 1;
    }
}
