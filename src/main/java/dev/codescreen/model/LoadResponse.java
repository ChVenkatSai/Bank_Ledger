package dev.codescreen.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import dev.codescreen.model.Amount;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.stereotype.Component;


import java.util.*;

/**
 * The result of an load.
 */
@Component
@Schema(name = "LoadResponse", description = "The result of an load.")
public class LoadResponse {

  private String userId;

  private String messageId;

  private Amount balance;

  public LoadResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public LoadResponse(String userId, String messageId, Amount balance) {
    this.userId = userId;
    this.messageId = messageId;
    this.balance = balance;
  }

  public LoadResponse userId(String userId) {
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

  public LoadResponse messageId(String messageId) {
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

  public LoadResponse balance(Amount balance) {
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
    LoadResponse loadResponse = (LoadResponse) o;
    return Objects.equals(this.userId, loadResponse.userId) &&
        Objects.equals(this.messageId, loadResponse.messageId) &&
        Objects.equals(this.balance, loadResponse.balance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, messageId, balance);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LoadResponse {\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    messageId: ").append(toIndentedString(messageId)).append("\n");
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

