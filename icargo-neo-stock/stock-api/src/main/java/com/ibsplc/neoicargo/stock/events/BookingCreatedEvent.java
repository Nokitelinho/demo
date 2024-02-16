package com.ibsplc.neoicargo.stock.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingCreatedEvent implements Serializable {

  @Schema(example = "081", description = "Airline Prefix")
  /** Airline Prefix */
  @JsonProperty("shipment_prefix")
  private String shipmentPrefix = null;

  @Schema(example = "FRA", description = "Master Document Number")
  /** Master Document Number */
  @JsonProperty("master_document_number")
  private String masterDocumentNumber = null;
}
