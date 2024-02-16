package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class DSNAtAirportModel extends BaseModel {
	private String airportCode;
	private int totalAcceptedBags;
	private Measure totalAcceptedWeight;
	private int totalBagsInFlight;
	private Measure totalWeightInFlight;
	private int totalBagsAtDestination;
	private Measure totalWeightAtDestination;
	private int totalFlownBags;
	private Measure totalFlownWeight;
	private int totalBagsOffloaded;
	private Measure totalWeightOffloaded;
	private int totalBagsArrived;
	private Measure totalWeightArrived;
	private int totalBagsDelivered;
	private Measure totalWeightDelivered;
	private int totalBagsReturned;
	private Measure totalWeightReturned;
	private boolean isFlightToDest;
	private boolean isOffload;
	private String mailClass;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
