package dev.codescreen.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import dev.codescreen.model.DebitCredit;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.stereotype.Component;


import java.util.*;


/**
 * Amount
 */
@Component
public class Amount {

  private String amount;

  private String currency;

  private DebitCredit debitOrCredit;

  public Amount() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Amount(String amount, String currency, DebitCredit debitOrCredit) {
    this.amount = amount;
    this.currency = currency;
    this.debitOrCredit = debitOrCredit;
  }

  public Amount amount(String amount) {
    this.amount = amount;
    return this;
  }

  /**
   * The amount in the denomination of the currency. For example, $1 = '1.00'
   * @return amount
  */
  @NotNull @Size(min = 1) 
  @Schema(name = "amount", description = "The amount in the denomination of the currency. For example, $1 = '1.00'", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("amount")
  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public Amount currency(String currency) {
    this.currency = currency;
    return this;
  }

  /**
   * Get currency
   * @return currency
  */
  @NotNull @Size(min = 1) 
  @Schema(name = "currency", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("currency")
  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public Amount debitOrCredit(DebitCredit debitOrCredit) {
    this.debitOrCredit = debitOrCredit;
    return this;
  }

  /**
   * Get debitOrCredit
   * @return debitOrCredit
  */
  @NotNull @Valid 
  @Schema(name = "debitOrCredit", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("debitOrCredit")
  public DebitCredit getDebitOrCredit() {
    return debitOrCredit;
  }

  public void setDebitOrCredit(DebitCredit debitOrCredit) {
    this.debitOrCredit = debitOrCredit;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Amount amount = (Amount) o;
    return Objects.equals(this.amount, amount.amount) &&
        Objects.equals(this.currency, amount.currency) &&
        Objects.equals(this.debitOrCredit, amount.debitOrCredit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount, currency, debitOrCredit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Amount {\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    debitOrCredit: ").append(toIndentedString(debitOrCredit)).append("\n");
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

