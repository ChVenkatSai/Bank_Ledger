package dev.codescreen.dao;

import dev.codescreen.model.Amount;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class UserBalanceDao implements UserBalance{

    //Hashmap to store balances of Users.
    private HashMap<String, Amount> DB = new HashMap<String, Amount>();

    public UserBalanceDao(HashMap<String, Amount> DB){
        this.DB = DB;
    }

    //Update Balance
    @Override
    public void updateBalance(String userId, Amount amount) {
        DB.put(userId, amount);
    }

    //Get current Balance
    @Override
    public Amount getBalance(String userId) {
        return DB.get(userId);
    }
}

