/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.5.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package dev.codescreen.api;

import dev.codescreen.model.Error;
import dev.codescreen.model.LoadRequest;
import dev.codescreen.model.LoadResponse;
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
@Tag(name = "load", description = "the load API")
public interface LoadApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * PUT /load : Adds funds to a user&#39;s account.
     *
     * @param loadRequest An load request message that needs to be decisioned. This balance will be added to a user&#39;s balance. (optional)
     * @return The result of an load (status code 201)
     *         or Server Error response (status code 200)
     */
    @Operation(
        operationId = "loadPut",
        summary = "Adds funds to a user's account.",
        responses = {
            @ApiResponse(responseCode = "201", description = "The result of an load", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = LoadResponse.class))
            }),
            @ApiResponse(responseCode = "default", description = "Server Error response", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.PUT,
        value = "/load",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    default ResponseEntity<?> loadPut(
        @Parameter(name = "LoadRequest", description = "An load request message that needs to be decisioned. This balance will be added to a user's balance.") @Valid @RequestBody LoadRequest loadRequest
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
