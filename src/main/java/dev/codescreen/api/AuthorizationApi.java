/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.5.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package dev.codescreen.api;

import dev.codescreen.model.AuthorizationRequest;
import dev.codescreen.model.AuthorizationResponse;
import dev.codescreen.model.Error;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Validated
@Tag(name = "authorization", description = "the authorization API")
public interface AuthorizationApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * PUT /authorization : Removes funds from a user&#39;s account if sufficient funds are available.
     *
     * @param authorizationRequest An authorization request message that needs to be decisioned. (optional)
     * @return The result of an authorization (status code 201)
     *         or Server Error response (status code 200)
     */
    @Operation(
        operationId = "authorizationPut",
        summary = "Removes funds from a user's account if sufficient funds are available.",
        responses = {
            @ApiResponse(responseCode = "201", description = "The result of an authorization", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorizationResponse.class))
            }),
            @ApiResponse(responseCode = "default", description = "Server Error response", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.PUT,
        value = "/authorization",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    default ResponseEntity<?> authorizationPut(
        @Parameter(name = "AuthorizationRequest", description = "An authorization request message that needs to be decisioned.") @Valid @RequestBody(required = false) AuthorizationRequest authorizationRequest
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
