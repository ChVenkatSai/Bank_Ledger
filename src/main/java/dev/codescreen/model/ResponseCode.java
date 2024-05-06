package dev.codescreen.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The response code sent back to the network for the merchant. Multiple declines reasons may exist but only one will be sent back to the network. Advice messages will include the response code that was sent on our behalf.
 */

public enum ResponseCode {
  
  APPROVED("APPROVED"),
  
  DECLINED("DECLINED");

  private String value;

  ResponseCode(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static ResponseCode fromValue(String value) {
    for (ResponseCode b : ResponseCode.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

