package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-5991
 */
@Setter
@Getter
public class DSNForSegmentVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private int acceptedBags;
	private Quantity acceptedWeight;
	private int statedBags;
	private Quantity statedWeight;
	private String companyCode;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private String dsn;
	private String originOfficeOfExchange;
	private String destinationOfficeOfExchange;
	private String mailClass;
	private int year;
	private int receivedBags;
	private Quantity receivedWeight;
	private int deliveredBags;
	/** 
	* The mailSubclass
	*/
	private String mailSubclass;
	/** 
	* The mailCategoryCode
	*/
	private String mailCategoryCode;
	private Quantity deliveredWeight;
}
