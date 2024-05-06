package dev.codescreen.dao;

import dev.codescreen.model.Amount;

public interface UserBalance {

    public void updateBalance (String userId, Amount amount);

    public Amount getBalance (String userId);

}
