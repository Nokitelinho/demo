package com.ibsplc.neoicargo.mailmasters.vo;

import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-3109
 */
@Setter
@Getter
public class MailEventVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String companyCode;
	private String mailboxId;
	private String paCode;
	private String mailCategory;
	private String mailClass;
	private boolean isReceived;
	private boolean isUplifted;
	private boolean isAssigned;
	private boolean isPending;
	private boolean isReadyForDelivery;
	private boolean isTransportationCompleted;
	private boolean isArrived;
	private boolean isHandedOver;
	private boolean isReturned;
	private boolean isDelivered;
	private String operationFlag;
	private boolean isLoadedResditFlag;
	private boolean isOnlineHandedOverResditFlag;
	private boolean isHandedOverReceivedResditFlag;
	private boolean isLostFlag;
	private boolean isFoundFlag;

	/** 
	* @param isAssigned The isAssigned to set.
	*/
	public void setAssigned(boolean isAssigned) {
		this.isAssigned = isAssigned;
	}

	/** 
	* @param isHandedOver The isHandedOver to set.
	*/
	public void setHandedOver(boolean isHandedOver) {
		this.isHandedOver = isHandedOver;
	}

	/** 
	* @param isPending The isPending to set.
	*/
	public void setPending(boolean isPending) {
		this.isPending = isPending;
	}

	/** 
	* @param isReadyForDelivery the isReadyForDelivery to setSetter for isReadyForDelivery  Added by : A-5201 on 13-Oct-2014 Used for :
	*/
	public void setReadyForDelivery(boolean isReadyForDelivery) {
		this.isReadyForDelivery = isReadyForDelivery;
	}

	/** 
	* @param isTransportationCompleted the isTransportationCompleted to setSetter for isTransportationCompleted  Added by : A-5201 on 13-Oct-2014 Used for :
	*/
	public void setTransportationCompleted(boolean isTransportationCompleted) {
		this.isTransportationCompleted = isTransportationCompleted;
	}

	/** 
	* @param isArrived the isArrived to setSetter for isArrived  Added by : A-5201 on 13-Oct-2014 Used for :
	*/
	public void setArrived(boolean isArrived) {
		this.isArrived = isArrived;
	}

	/** 
	* @param isReceived The isReceived to set.
	*/
	public void setReceived(boolean isReceived) {
		this.isReceived = isReceived;
	}

	/** 
	* @param isReturned The isReturned to set.
	*/
	public void setReturned(boolean isReturned) {
		this.isReturned = isReturned;
	}

	/** 
	* @param isUplifted The isUplifted to set.
	*/
	public void setUplifted(boolean isUplifted) {
		this.isUplifted = isUplifted;
	}

	/** 
	* @param isDelivered The isDelivered to set.
	*/
	public void setDelivered(boolean isDelivered) {
		this.isDelivered = isDelivered;
	}

	/** 
	* @param isHandedOverReceivedResditFlag The isHandedOverReceivedResditFlag to set.
	*/
	public void setHandedOverReceivedResditFlag(boolean isHandedOverReceivedResditFlag) {
		this.isHandedOverReceivedResditFlag = isHandedOverReceivedResditFlag;
	}

	/** 
	* @param isLoadedResditFlag The isLoadedResditFlag to set.
	*/
	public void setLoadedResditFlag(boolean isLoadedResditFlag) {
		this.isLoadedResditFlag = isLoadedResditFlag;
	}

	/** 
	* @param isOnlineHandedOverResditFlag The isOnlineHandedOverResditFlag to set.
	*/
	public void setOnlineHandedOverResditFlag(boolean isOnlineHandedOverResditFlag) {
		this.isOnlineHandedOverResditFlag = isOnlineHandedOverResditFlag;
	}

	public void setLostFlag(boolean isLostFlag) {
		this.isLostFlag = isLostFlag;
	}

	public void setFoundFlag(boolean isFoundFlag) {
		this.isFoundFlag = isFoundFlag;
	}
}
