package dev.codescreen.service;

import dev.codescreen.dao.EventSourcing;
import dev.codescreen.dao.UserBalance;
import dev.codescreen.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthorizationServiceImpl implements AuthorizationService{

    private AuthorizationRequest authorizationRequest;
    public UserBalance userBalance;
    public EventSourcing eventSourcing;

    @Autowired
    public AuthorizationServiceImpl(AuthorizationRequest authorizationRequest, UserBalance userBalance, EventSourcing eventSourcing) {
        this.authorizationRequest = authorizationRequest;
        this.userBalance = userBalance;
        this.eventSourcing = eventSourcing;
    }

    private Amount updateBalance(String userId, Amount currentBalance, Amount transactionAmount, AuthorizationResponse authorizationResponse){
        if (currentBalance == null) { //return the same as we don't want to show
            authorizationResponse.setResponseCode(ResponseCode.DECLINED);
            return new Amount(" ", " ", transactionAmount.getDebitOrCredit());
        }
        else{
            double current = Double.parseDouble(currentBalance.getAmount());
            double extra = Double.parseDouble(transactionAmount.getAmount());
            if (current - extra < 0){
                authorizationResponse.setResponseCode(ResponseCode.DECLINED);
                return userBalance.getBalance(userId);
            }
            else{
                authorizationResponse.setResponseCode(ResponseCode.APPROVED);
                String newBalance = String.valueOf(current-extra);
                Amount toUpdate = new Amount(newBalance, transactionAmount.getCurrency(), transactionAmount.getDebitOrCredit());
                userBalance.updateBalance(userId, toUpdate);
                return toUpdate;
            }
        }
    }

    private AuthorizationResponse fillResponse(){
        String userId = this.authorizationRequest.getUserId();
        Amount transactionAmount = this.authorizationRequest.getTransactionAmount();
        Amount currentBalance = userBalance.getBalance(userId);
        AuthorizationResponse authorizationResponse = new AuthorizationResponse();
        Amount updatedBalance = updateBalance(userId, currentBalance, transactionAmount, authorizationResponse);
        Transaction transaction = new Transaction();
        transaction.setTransactionAmount(updatedBalance);
        transaction.setMessageId(this.authorizationRequest.getMessageId());
        transaction.setUserId(this.authorizationRequest.getUserId());
        transaction.setTransactionType(DebitCredit.DEBIT);
        transaction.setTimestamp(LocalDateTime.now());
        eventSourcing.addTransaction(transaction);
        authorizationResponse.setUserId(this.authorizationRequest.getUserId());
        authorizationResponse.setMessageId(this.authorizationRequest.getMessageId());
        authorizationResponse.setBalance(updatedBalance);
        return authorizationResponse;
    }

    public AuthorizationRequest getAuthorizationRequest(){
        return this.authorizationRequest;
    }

    @Override
    public void setAuthorizationRequest(AuthorizationRequest authorizationRequest) {
        this.authorizationRequest = authorizationRequest;
    }

    @Override
    public AuthorizationResponse getResponse() {
        return fillResponse();
    }
}
