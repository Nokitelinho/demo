package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-3109
 */
@Setter
@Getter
public class OffloadDetailVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private int offloadSerialNumber;
	private ZonedDateTime offloadedDate;
	private String offloadUser;
	private int offloadedBags;
	private Quantity offloadedWeight;
	private String offloadRemarks;
	private String offloadReasonCode;
	private String carrierCode;
	private String offloadDescription;
	private String offloadType;
	private String dsn;
	private String originExchangeOffice;
	private String destinationExchangeOffice;
	private String mailClass;
	private int year;
	private String mailId;
	private String containerNumber;
	private String airportCode;
	private String mailSubclass;
	private String mailCategoryCode;
	private long mailSequenceNumber;
	private String offloadedStation;
	private ZonedDateTime flightDate;
	private String billingBasis;
	private int csgSequenceNumber;
	private String csgDocumentNumber;
	private String poaCode;
	private String irregularityStatus;
	private int rescheduledFlightCarrierId;
	private String rescheduledFlightNo;
	private long rescheduledFlightSeqNo;
	private ZonedDateTime rescheduledFlightDate;

	/** 
	* @param mailSubClass The mailSubClass to set.
	*/
	public void setMailSubClass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
	}
}
