
package com.ibsplc.neoicargo.tracking.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.neoicargo.framework.core.lang.event.DomainEvent;
import com.ibsplc.neoicargo.framework.core.lang.modal.Units;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ShipmentHistoryEvent implements DomainEvent, Serializable{

	@JsonProperty("shipment_key")
	private String shipmentKey;

	@JsonProperty("shipment_type")
	private String shipmentType;

	@JsonProperty("shipment_sequence_number")
	private Long shipmentSequenceNumber;

	@JsonProperty("airport_code")
	private String airportCode;
	
	@JsonProperty("transaction_pieces")
	private Integer transactionPieces;
	
	@JsonProperty("transaction_weight")
	private Double transactionWeight;
	
	@JsonProperty("transaction_volume")
	private Double transactionVolume;
	
	@JsonProperty("transaction_date")
	private LocalDate transactionDate;

	@JsonProperty("milestone_code")
	private String milestoneCode;
	
	@JsonProperty("transaction_time")
	private String transactionTime;
	
	@JsonProperty("transaction_time_utc")
	private String transactionTimeUTC;

	@JsonProperty("updated_user")
	private String lastUpdatedUser;
	
	@JsonProperty("transaction_detail")
	private Object transactionDetails;
	
	@JsonProperty("units_of_measure")
    private Units unitsOfMeasure;


}
