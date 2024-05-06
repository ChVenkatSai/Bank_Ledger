package dev.codescreen.dao;

import dev.codescreen.model.Transaction;

public interface EventSourcing {

    public int addTransaction(Transaction transaction);
}
