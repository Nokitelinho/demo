package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-5991
 */
@Setter
@Getter
public class CarditEnquiryFilterVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String carrierCode;
	private int carrierId;
	private String flightNumber;
	private int legSerialNumber;
	private long flightSequenceNumber;
	private ZonedDateTime flightDate;
	private String searchMode;
	private String resdit;
	private String consignmentDocument;
	private ZonedDateTime fromDate;
	private ZonedDateTime toDate;
	private String ooe;
	private String doe;
	private String mailCategoryCode;
	private String mailClass;
	private String year;
	private String despatchSerialNumber;
	private String receptacleSerialNumber;
	private String flightType;
	private String flighDirection;
	private String pol;
	private String paoCode;
	private String mailbagId;
	private int absoluteIndex;
	private String mailSubclass;
	private String uldNumber;
	private ZonedDateTime reqDeliveryTime;
	private int totalRecordsCount;
	private int pageNumber;
	private int pageSize;
	private boolean isPendingResditChecked;
	private ZonedDateTime consignmentDate;
	public String shipmentPrefix;
	private String documentNumber;
	private String mailOrigin;
	private String maildestination;
	private String consignmentLevelAWbAttachRequired;
	private ZonedDateTime transportServWindow;
	private String fromScreen;
	private int mailCount;
	/** 
	* Flag for notaccepted filter
	*/
	private String notAccepted;
	private String mailStatus;
	private String isAWBAttached;

	/** 
	* @author A-8061 
	* @param isPendingResditChecked
	*/
	public void setPendingResditChecked(boolean isPendingResditChecked) {
		this.isPendingResditChecked = isPendingResditChecked;
	}
}
