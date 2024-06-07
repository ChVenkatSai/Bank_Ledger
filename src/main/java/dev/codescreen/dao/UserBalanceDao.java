package dev.codescreen.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dev.codescreen.model.Amount;
import dev.codescreen.model.DebitCredit;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class UserBalanceDao implements UserBalance{

    //Hashmap to store balances of Users.
    private final MongoDatabase database = DatabaseUtil.getDatabase();
    private MongoCollection<Document> collection = database.getCollection("Balance");

    public UserBalanceDao(MongoDatabase database) {
        this.collection = database.getCollection("Balance");
    }
    //Update Balance
    @Override
    public void updateBalance(String userId, Amount amount) {
        Document document = new Document("_id", userId);
        if (collection.find(document).first() == null){
            document.append("amount", amount.getAmount())
                    .append("currency",amount.getCurrency())
                    .append("debitOrCredit",amount.getDebitOrCredit());
            collection.insertOne(document);
        }
        else{
            Document toUpdate = new Document("amount", amount.getAmount())
                    .append("currency",amount.getCurrency())
                    .append("debitOrCredit",amount.getDebitOrCredit());
            Document update = new Document("$set",toUpdate);
            collection.updateOne(document, update);
        }
    }

    //Get current Balance
    @Override
    public Amount getBalance(String userId) {
        Document toReturn = collection.find(new Document("_id", userId)).first();
        Amount amount = new Amount();
        if (toReturn != null){
            amount.setAmount(toReturn.getString("amount"));
            amount.setCurrency(toReturn.getString("currency"));
            String value = toReturn.getString("debitOrCredit");
            if (value.equals("DEBIT")){
                amount.setDebitOrCredit(DebitCredit.DEBIT);
            } else if (value.equals("CREDIT")) {
                amount.setDebitOrCredit(DebitCredit.CREDIT);
            }
            return amount;
        }else {
            return null;
        }
    }
}

