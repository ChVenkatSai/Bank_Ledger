package dev.codescreen.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dev.codescreen.model.Transaction;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository
public class EventSourcingDao implements EventSourcing{

    //Linked List to store Transactions as part of Event Sourcing
    private final MongoDatabase database = DatabaseUtil.getDatabase();
    private MongoCollection<Document> collection = database.getCollection("Transactions");

    public EventSourcingDao(MongoDatabase database) {
        this.collection = database.getCollection("Transactions");
    }
    //Adds Transaction
    @Override
    public int addTransaction(Transaction transaction) {
        Document amount = new Document().append("amount", transaction.getTransactionAmount().getAmount())
                .append("currency",transaction.getTransactionAmount().getCurrency())
                .append("debitOrCredit",transaction.getTransactionAmount().getDebitOrCredit());
        Document add = new Document().append("Amount", amount)
                        .append("userId",transaction.getUserId())
                                .append("messageId",transaction.getMessageId())
                                        .append("transactionType",transaction.getTransactionType())
                                                .append("time",transaction.getTimestamp());
        collection.insertOne(add);
        return 1;
    }
}
