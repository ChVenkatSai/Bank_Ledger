package dev.codescreen.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Component
public class Transaction {

    private Amount amount;
    private String userId;
    private String messageId;
    private DebitCredit transactionType;
    private LocalDateTime timestamp; // Add timestamp field

    public Transaction(){super();}
    // Constructor with all fields including timestamp
    public Transaction(Amount amount, String userId, String messageId, DebitCredit transactionType, LocalDateTime timestamp) {
        this.amount = amount;
        this.userId = userId;
        this.messageId = messageId;
        this.transactionType = transactionType;
        this.timestamp = timestamp;
    }


   /**
            * Get userId
   * @return userId
  */
    @NotNull
    @Size(min = 1)
    @Schema(name = "userId", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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


    /**
     * Get transactionAmount
     * @return transactionAmount
     */
    @NotNull @Valid
    @Schema(name = "Amount", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("amount")
    public Amount getTransactionAmount() {
        return amount;
    }

    public void setTransactionAmount(Amount amount) {
        this.amount = amount;
    }


    @Schema(name = "transactionType", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("transactionType")
    public DebitCredit getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(DebitCredit transactionType) {
        this.transactionType= transactionType;
    }

    @Schema(description = "The timestamp of the transaction", example = "2024-05-10T08:30:00")
    @JsonProperty("timestamp")
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Transaction {\n");
        sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
        sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
        sb.append("    messageId: ").append(toIndentedString(messageId)).append("\n");
        sb.append("    transactionType: ").append(toIndentedString(transactionType)).append("\n");
        sb.append("    serverTime: ").append(toIndentedString(timestamp)).append("\n");
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
