package dev.codescreen.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.stereotype.Component;


import java.util.*;

/**
 * Ping
 */
@Component
public class Ping {

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime serverTime;

  public Ping() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Ping(OffsetDateTime serverTime) {
    this.serverTime = serverTime;
  }

  public Ping serverTime(OffsetDateTime serverTime) {
    this.serverTime = serverTime;
    return this;
  }

  /**
   * Current server time
   * @return serverTime
  */
  @NotNull @Valid 
  @Schema(name = "serverTime", description = "Current server time", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("serverTime")
  public OffsetDateTime getServerTime() {
    return serverTime;
  }

  public void setServerTime(OffsetDateTime serverTime) {
    this.serverTime = serverTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Ping ping = (Ping) o;
    return Objects.equals(this.serverTime, ping.serverTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(serverTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Ping {\n");
    sb.append("    serverTime: ").append(toIndentedString(serverTime)).append("\n");
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

