package com.ibsplc.neoicargo.tracking.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.types.Volume;
import com.ibsplc.neoicargo.framework.util.unit.quantity.types.Weight;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
public class ShipmentDetailsVO extends AbstractVO {

	private String shipmentPrefix;
	private String masterDocumentNumber;
	private Integer pieces;
	private Quantity<Weight> weight;
	private Quantity<Volume> volume;
	private String specialHandlingCode;
	private String productName;
	private String shipmentDescription;
	private String originAirportCode;
	private String destinationAirportCode;
	private List<MilestoneVO> milestones = Collections.emptyList();
	private LocalDateTime departureTime;
	private FlightTimePostfixEnum departureTimePostfix;
	private LocalDateTime arrivalTime;
	private FlightTimePostfixEnum arrivalTimePostfix;
	private TransitStationVO transitStations;

	private List<ShipmentMilestonePlanVO> plans = Collections.emptyList();
	private List<ShipmentMilestoneEventVO> events = Collections.emptyList();

	public String getShipmentKey(){
		return String.format("%s-%s", this.getShipmentPrefix(), this.getMasterDocumentNumber());
	}
}
