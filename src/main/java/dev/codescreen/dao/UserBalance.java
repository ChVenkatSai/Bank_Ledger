package dev.codescreen.dao;

import dev.codescreen.model.Amount;

/*
Interface to the class that stores balances for users.
 */
public interface UserBalance {

    //Updates balance of user
    public void updateBalance (String userId, Amount amount);

    //Gets current balance of User
    public Amount getBalance (String userId);

}
