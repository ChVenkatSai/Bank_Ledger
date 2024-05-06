package dev.codescreen.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.stereotype.Component;

/**
 * Load request that needs to be processed.
 */
@Component
@Schema(name = "LoadRequest", description = "Load request that needs to be processed.")
public class LoadRequest {

  private String userId;

  private String messageId;

  private Amount transactionAmount;

  public LoadRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public LoadRequest(String userId, String messageId, Amount transactionAmount) {
    this.userId = userId;
    this.messageId = messageId;
    this.transactionAmount = transactionAmount;
  }

  public LoadRequest userId(String userId) {
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


  public LoadRequest messageId(String messageId) {
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

  public LoadRequest transactionAmount(Amount transactionAmount) {
    this.transactionAmount = transactionAmount;
    return this;
  }

  /**
   * Get transactionAmount
   * @return transactionAmount
  */
  @NotNull @Valid 
  @Schema(name = "transactionAmount", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("transactionAmount")
  public Amount getTransactionAmount() {
    return transactionAmount;
  }

  public void setTransactionAmount(Amount transactionAmount) {
    this.transactionAmount = transactionAmount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LoadRequest loadRequest = (LoadRequest) o;
    return Objects.equals(this.userId, loadRequest.userId) &&
        Objects.equals(this.messageId, loadRequest.messageId) &&
        Objects.equals(this.transactionAmount, loadRequest.transactionAmount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, messageId, transactionAmount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LoadRequest {\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    messageId: ").append(toIndentedString(messageId)).append("\n");
    sb.append("    transactionAmount: ").append(toIndentedString(transactionAmount)).append("\n");
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

