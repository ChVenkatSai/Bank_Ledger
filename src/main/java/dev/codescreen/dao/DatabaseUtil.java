package dev.codescreen.dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DatabaseUtil{

    private static final String ConnectionString = "mongodb://localhost:27017";
    private static final MongoClient mongoClient = MongoClients.create(ConnectionString);
    private static final MongoDatabase database = mongoClient.getDatabase("BankLedger");

    public static MongoDatabase getDatabase(){
        return database;
    }

}
