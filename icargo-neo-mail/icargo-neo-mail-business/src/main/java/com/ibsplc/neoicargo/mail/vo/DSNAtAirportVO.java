package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-5991
 */
@Setter
@Getter
public class DSNAtAirportVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String airportCode;
	private int totalAcceptedBags;
	private Quantity totalAcceptedWeight;
	private int totalBagsInFlight;
	private Quantity totalWeightInFlight;
	private int totalBagsAtDestination;
	private Quantity totalWeightAtDestination;
	private int totalFlownBags;
	private Quantity totalFlownWeight;
	private int totalBagsOffloaded;
	private Quantity totalWeightOffloaded;
	private int totalBagsArrived;
	private Quantity totalWeightArrived;
	private int totalBagsDelivered;
	private Quantity totalWeightDelivered;
	private int totalBagsReturned;
	private Quantity totalWeightReturned;
	private boolean isFlightToDest;
	private boolean isOffload;
	private String mailClass;

	/** 
	* @param isOffload The isOffload to set.
	*/
	public void setOffload(boolean isOffload) {
		this.isOffload = isOffload;
	}

	/** 
	* @param isFlightToDest The isFlightToDest to set.
	*/
	public void setFlightToDest(boolean isFlightToDest) {
		this.isFlightToDest = isFlightToDest;
	}
}
