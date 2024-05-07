package dev.codescreen.service;

import dev.codescreen.dao.EventSourcing;
import dev.codescreen.dao.UserBalance;
import dev.codescreen.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoadServiceImpl implements LoadService {

    private LoadRequest loadRequest;
    public UserBalance userBalance;
    public EventSourcing eventSourcing;

    @Autowired
    public LoadServiceImpl(LoadRequest loadRequest, UserBalance userBalance, EventSourcing eventSourcing) {
        this.loadRequest = loadRequest;
        this.userBalance = userBalance;
        this.eventSourcing = eventSourcing;
    }

    //Updates the Balance of the user based on the request and returns the updatedBalance.
    private Amount updateBalance(Amount currentBalance, Amount transactionAmount){

        //If the current balance is null
        if (currentBalance == null)
            return new Amount(transactionAmount.getAmount(), transactionAmount.getCurrency(), transactionAmount.getDebitOrCredit());
        //There is some current balance
        else{
            //Using Double as the type
            double current = Double.parseDouble(currentBalance.getAmount());
            double extra = Double.parseDouble(transactionAmount.getAmount());

            String newBalance = String.valueOf(current+extra);
            return new Amount(newBalance, transactionAmount.getCurrency(), transactionAmount.getDebitOrCredit());
        }
    }

    //Fill the response to the request and return it.
    private LoadResponse fillResponse(){
        String userId = this.loadRequest.getUserId();
        Amount transactionAmount = this.loadRequest.getTransactionAmount();

        //Update Balance
        Amount currentBalance = userBalance.getBalance(userId);
        Amount updatedBalance = updateBalance(currentBalance, transactionAmount);
        userBalance.updateBalance(userId, updatedBalance);

        //Create a transaction as a transaction has happened
        Transaction transaction = new Transaction();
        transaction.setTransactionAmount(updatedBalance);
        transaction.setMessageId(this.loadRequest.getMessageId());
        transaction.setUserId(this.loadRequest.getUserId());
        transaction.setTransactionType(DebitCredit.CREDIT);
        transaction.setTimestamp(LocalDateTime.now());

        //Perform Event Sourcing
        eventSourcing.addTransaction(transaction);

        //Return the Response
        LoadResponse loadResponse = new LoadResponse();
        loadResponse.setUserId(this.loadRequest.getUserId());
        loadResponse.setMessageId(this.loadRequest.getMessageId());
        loadResponse.setBalance(updatedBalance);
        return loadResponse;
    }

    public LoadRequest getLoadRequest(){
        return this.loadRequest;
    }

    @Override
    public void setLoadRequest(LoadRequest loadRequest) {
        this.loadRequest = loadRequest;
    }

    //Return the response
    @Override
    public LoadResponse getResponse() {
        return fillResponse();
    }
}
