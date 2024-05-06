package dev.codescreen.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import dev.codescreen.model.Amount;
import dev.codescreen.model.ResponseCode;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.stereotype.Component;


import java.util.*;

/**
 * The result of an authorization
 */
@Component
@Schema(name = "AuthorizationResponse", description = "The result of an authorization")
public class AuthorizationResponse {

  private String userId;

  private String messageId;

  private ResponseCode responseCode;

  private Amount balance;

  public AuthorizationResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public AuthorizationResponse(String userId, String messageId, ResponseCode responseCode, Amount balance) {
    this.userId = userId;
    this.messageId = messageId;
    this.responseCode = responseCode;
    this.balance = balance;
  }

  public AuthorizationResponse userId(String userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Get userId
   * @return userId
  */
  @NotNull @Size(min = 1) 
  @Schema(name = "userId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("userId")
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public AuthorizationResponse messageId(String messageId) {
    this.messageId = messageId;
    return this;
  }

  /**
   * Get messageId
   * @return messageId
  */
  @NotNull @Size(min = 1) 
  @Schema(name = "messageId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("messageId")
  public String getMessageId() {
    return messageId;
  }

  public void setMessageId(String messageId) {
    this.messageId = messageId;
  }

  public AuthorizationResponse responseCode(ResponseCode responseCode) {
    this.responseCode = responseCode;
    return this;
  }

  /**
   * Get responseCode
   * @return responseCode
  */
  @NotNull @Valid 
  @Schema(name = "responseCode", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("responseCode")
  public ResponseCode getResponseCode() {
    return responseCode;
  }

  public void setResponseCode(ResponseCode responseCode) {
    this.responseCode = responseCode;
  }

  public AuthorizationResponse balance(Amount balance) {
    this.balance = balance;
    return this;
  }

  /**
   * Get balance
   * @return balance
  */
  @NotNull @Valid 
  @Schema(name = "balance", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("balance")
  public Amount getBalance() {
    return balance;
  }

  public void setBalance(Amount balance) {
    this.balance = balance;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AuthorizationResponse authorizationResponse = (AuthorizationResponse) o;
    return Objects.equals(this.userId, authorizationResponse.userId) &&
        Objects.equals(this.messageId, authorizationResponse.messageId) &&
        Objects.equals(this.responseCode, authorizationResponse.responseCode) &&
        Objects.equals(this.balance, authorizationResponse.balance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, messageId, responseCode, balance);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthorizationResponse {\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    messageId: ").append(toIndentedString(messageId)).append("\n");
    sb.append("    responseCode: ").append(toIndentedString(responseCode)).append("\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

