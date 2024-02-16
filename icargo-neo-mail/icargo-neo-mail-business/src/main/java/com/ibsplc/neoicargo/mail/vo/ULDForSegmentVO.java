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
public class ULDForSegmentVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private String carrierCode;
	private ZonedDateTime flightDate;
	/** 
	* For Bulk , String is BULK-POU
	*/
	private String uldNumber;
	private String pou;
	private String operationalFlag;
	private int receivedBags;
	private Quantity receivedWeight;
	/** 
	* No of mailbags in this ULD
	*/
	private int noOfBags;
	/** 
	* total weight of this ULD
	*/
	private Quantity totalWeight;
	private String remarks;
	private String warehouseCode;
	private String locationCode;
	private String lastUpdateUser;
	private String transferFromCarrier;
	private String transferToCarrier;
	private String releasedFlag;
	/** 
	* The despatches in this ULD
	*/
	private Collection<DSNInULDForSegmentVO> dsnInULDForSegmentVOs;
	private Collection<MailbagInULDForSegmentVO> mailbagInULDForSegmentVOs;
	/** 
	* The onwardRouting of this ULD
	*/
	private Collection<OnwardRouteForSegmentVO> onwardRoutings;
}
