package com.ibsplc.neoicargo.mail.vo;

import java.util.Collection;
import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;

/** 
 * @author a-5991
 */
@Setter
@Getter
public class MailAcceptanceVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String pol;
	private int carrierId;
	private String flightNumber;
	private int legSerialNumber;
	private long flightSequenceNumber;
	private ZonedDateTime flightDate;
	private boolean isInventory;
	private boolean isInventoryForArrival;
	private Collection<ContainerDetailsVO> containerDetails;
	/** 
	* This status will be set when operated by PC after validation by client
	*/
	private String flightStatus;
	private String ownAirlineCode;
	private int ownAirlineId;
	private String flightCarrierCode;
	private String destination;
	private String strFlightDate;
	private String acceptedUser;
	private boolean isScanned;
	private boolean isPreassignNeeded;
	private String duplicateMailOverride;
	private String transactionCode;
	private String mailSource;
	private boolean isConsignmentGenerationNotNeeded;
	private HashMap<String, Collection<String>> polPouMap;
	private boolean mailModifyflag;
	private String isFromTruck;
	private String flightRoute;
	private String flightOperationalStatus;
	private String flightOrigin;
	private String flightDestination;
	private String flightType;
	private String aircraftType;
	private String flightDateDesc;
	private String carrierCode;
	private int totalContainerCount;
	private Quantity totalContainerWeight;
	private String departureGate;
	private String mailDataSource;
	private PreAdviceVO preadvice;
	private String DCSinfo;
	private String DCSregectionReason;
	private String paBuiltFlag;
	private boolean fromDeviationList;
	private ZonedDateTime flightDepartureDate;
	private ZonedDateTime GHTtime;
	private boolean assignedToFlight;
	private String messageVersion;
	private boolean fromCarditList;
	private boolean fromOutboundScreen;
	private boolean transferOnModify;
	private boolean isFoundTransfer;
	private boolean modifyAfterExportOpr;
	private String popupAction;
	private Collection<EmbargoDetailsVO> embargoDetails;
	private boolean mailbagPresent;
	private String showWarning;
	private ZonedDateTime std;

	public void setConsignmentGenerationNotNeeded(boolean isConsignmentGenerationNotNeeded) {
		this.isConsignmentGenerationNotNeeded = isConsignmentGenerationNotNeeded;
	}

	/** 
	* @param isScanned The isScanned to set.
	*/
	public void setScanned(boolean isScanned) {
		this.isScanned = isScanned;
	}

	/** 
	* @param isPreassignNeeded The isPreassignNeeded to set.
	*/
	public void setPreassignNeeded(boolean isPreassignNeeded) {
		this.isPreassignNeeded = isPreassignNeeded;
	}

	/** 
	* @param isInventory The isInventory to set.
	*/
	public void setInventory(boolean isInventory) {
		this.isInventory = isInventory;
	}

	public void setInventoryForArrival(boolean isInventoryForArrival) {
		this.isInventoryForArrival = isInventoryForArrival;
	}

	/** 
	* @param isFoundTransfer the isFoundTransfer to setSetter for isFoundTransfer  Added by : A-8061 on 27-Aug-2020 Used for :
	*/
	public void setFoundTransfer(boolean isFoundTransfer) {
		this.isFoundTransfer = isFoundTransfer;
	}
}
