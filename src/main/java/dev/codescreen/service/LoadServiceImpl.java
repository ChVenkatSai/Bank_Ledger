package dev.codescreen.service;

import dev.codescreen.dao.EventSourcing;
import dev.codescreen.dao.UserBalance;
import dev.codescreen.model.Amount;
import dev.codescreen.model.LoadRequest;
import dev.codescreen.model.LoadResponse;
import dev.codescreen.model.Transaction;
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

    private Amount updateBalance(Amount currentBalance, Amount transactionAmount){

        if (currentBalance == null)
            return new Amount(transactionAmount.getAmount(), transactionAmount.getCurrency(), transactionAmount.getDebitOrCredit());
        else{
            double current = Double.parseDouble(currentBalance.getAmount());
            double extra = Double.parseDouble(transactionAmount.getAmount());
            String newBalance = String.valueOf(current+extra);
            return new Amount(newBalance, transactionAmount.getCurrency(), transactionAmount.getDebitOrCredit());
        }
    }

    private LoadResponse fillResponse(){
        String userId = this.loadRequest.getUserId();
        Amount transactionAmount = this.loadRequest.getTransactionAmount();
        Amount currentBalance = userBalance.getBalance(userId);
        Amount updatedBalance = updateBalance(currentBalance, transactionAmount);
        userBalance.updateBalance(userId, updatedBalance);
        Transaction transaction = new Transaction();
        transaction.setTransactionAmount(updatedBalance);
        transaction.setMessageId(this.loadRequest.getMessageId());
        transaction.setUserId(this.loadRequest.getUserId());
        transaction.setTransactionType("Load Transaction");
        transaction.setTimestamp(LocalDateTime.now());
        LoadResponse loadResponse = new LoadResponse();
        eventSourcing.addTransaction(transaction);
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

    @Override
    public LoadResponse getResponse() {
        return fillResponse();
    }
}
