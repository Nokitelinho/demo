package com.ibsplc.neoicargo.mail.vo;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-3109
 */
@Setter
@Getter
public class PreAdviceVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private int legSerialNumber;
	private ZonedDateTime flightDate;
	private String carrierCode;
	/** 
	* Added By karthick  V as the part of NCA Mail Tracking  to calculate the total Number of mail Bags for that Flight  which has  hit the system as the part of the Cardit Processing ...
	*/
	private int totalBags;
	/** 
	* Added By karthick  V as the part of NCA Mail Tracking  to calculate the total Number of Weight for all mail Bags for that Flight  which has  hit the system as the part of the Cardit Processing ...
	*/
	private Quantity totalWeight;
	private Collection<PreAdviceDetailsVO> preAdviceDetails;
}
