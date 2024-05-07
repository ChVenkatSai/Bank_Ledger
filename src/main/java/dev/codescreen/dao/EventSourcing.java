package dev.codescreen.dao;

import dev.codescreen.model.Transaction;

/*
Interface to the class that implements Event sourcing.
 */
public interface EventSourcing {

    //Adds Transaction to Database.
    public int addTransaction(Transaction transaction);
}
