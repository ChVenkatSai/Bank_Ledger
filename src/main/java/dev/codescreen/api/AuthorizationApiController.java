package dev.codescreen.api;

import dev.codescreen.model.*;


import dev.codescreen.model.Error;
import dev.codescreen.service.AuthorizationService;
import dev.codescreen.service.LoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;


import java.net.URI;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("${openapi.transactionService.base-path:}")
public class AuthorizationApiController implements AuthorizationApi {

    private final NativeWebRequest request;
    private final AuthorizationService authorizationService;

    private static final String AMOUNT_REGEX = "^\\d+(\\.\\d+)?$"; // Allows positive decimal numbers

    // Pattern for amount validation
    private static final Pattern AMOUNT_PATTERN = Pattern.compile(AMOUNT_REGEX);


    @Autowired
    public AuthorizationApiController(NativeWebRequest request, AuthorizationService authorizationService) {
        this.request = request;
        this.authorizationService = authorizationService;
    }

    // Method to validate the amount string
    private boolean isValidAmount(String amount) {
        return amount != null && AMOUNT_PATTERN.matcher(amount).matches() && !amount.isEmpty();
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<?> authorizationPut(AuthorizationRequest authorizationRequest) {

        //Handling cases that don't match schema i.e Bad Requests
        Error error = new Error();
        //No messageId
        if (authorizationRequest.getMessageId() == null || authorizationRequest.getMessageId().isEmpty()){
            error.setMessage("Message Id is missing");
            error.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        //No Transaction Amount
        else if (authorizationRequest.getTransactionAmount() == null) {
            error.setMessage("Transaction amount is missing");
            error.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        // No userId
        else if (authorizationRequest.getUserId() == null || authorizationRequest.getUserId().isEmpty()) {
            error.setMessage("User Id is missing");
            error.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        //No amount or invalid amount
        else if (!isValidAmount(authorizationRequest.getTransactionAmount().getAmount())) {
            error.setMessage("Amount is missing or not a valid number");
            error.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        //No currency
        else if (authorizationRequest.getTransactionAmount().getCurrency() == null || authorizationRequest.getTransactionAmount().getCurrency().isEmpty()) {
            error.setMessage("Currency is missing");
            error.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        //Sending response obtained from Service.

        try {
            authorizationService.setAuthorizationRequest(authorizationRequest);
            AuthorizationResponse authorizationResponse = authorizationService.getResponse();
            return ResponseEntity
                    .created(URI.create("/authorization")) // Set the URI of the newly created resource
                    .body(authorizationResponse);
        } catch (Exception e){
            error.setMessage("Internal server error");
            error.setCode("500");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

}
