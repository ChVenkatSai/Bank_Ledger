package dev.codescreen.api;

import dev.codescreen.model.DebitCredit;
import dev.codescreen.model.Error;
import dev.codescreen.model.LoadRequest;
import dev.codescreen.model.LoadResponse;


import dev.codescreen.service.LoadService;
import dev.codescreen.service.LoadServiceImpl;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("${openapi.transactionService.base-path:}")
public class LoadApiController implements LoadApi {

    private final NativeWebRequest request;
    private final LoadService loadService;
    private static final String AMOUNT_REGEX = "^\\d+(\\.\\d+)?$"; // Allows positive decimal numbers

    // Pattern for amount validation
    private static final Pattern AMOUNT_PATTERN = Pattern.compile(AMOUNT_REGEX);


    @Autowired
    public LoadApiController(NativeWebRequest request, LoadService loadService) {
        this.request = request;
        this.loadService = loadService;
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
    public ResponseEntity<?> loadPut(@Valid @RequestBody LoadRequest loadRequest) {
        Error error = new Error();
        if (loadRequest.getMessageId() == null || loadRequest.getMessageId().isEmpty()){
            error.setMessage("Message Id is missing");
            error.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } else if (loadRequest.getTransactionAmount() == null) {
            error.setMessage("Transaction amount is missing");
            error.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } else if (loadRequest.getUserId() == null || loadRequest.getUserId().isEmpty()) {
            error.setMessage("User Id is missing");
            error.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } else if (!isValidAmount(loadRequest.getTransactionAmount().getAmount())) {
            error.setMessage("Amount is missing or not a valid number");
            error.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } else if (loadRequest.getTransactionAmount().getCurrency() == null || loadRequest.getTransactionAmount().getCurrency().isEmpty()) {
            error.setMessage("Currency is missing");
            error.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        try {
            loadService.setLoadRequest(loadRequest);
            LoadResponse loadResponse = loadService.getResponse();
            return ResponseEntity
                    .created(URI.create("/load")) // Set the URI of the newly created resource
                    .body(loadResponse);
        } catch (Exception e){
            error.setMessage("Internal server error");
            error.setCode("500");
            //error.setCode(e.getStatusCode().toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
