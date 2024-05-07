package dev.codescreen.api;

import dev.codescreen.model.Error;
import dev.codescreen.model.Ping;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.constraints.*;
import javax.validation.Valid;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("${openapi.transactionService.base-path:}")
public class PingApiController implements PingApi {

    private final NativeWebRequest request;

    @Autowired
    public PingApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<Ping> pingGet(){

        //Print Ping and convey the server is active
        Ping p = new Ping(OffsetDateTime.now());
        System.out.println(("The time would be " + p.getServerTime()));
        getRequest().ifPresent(request -> {
            System.out.println("The request is" + request.toString());
        });

        return ResponseEntity.ok(p);
    }

}
