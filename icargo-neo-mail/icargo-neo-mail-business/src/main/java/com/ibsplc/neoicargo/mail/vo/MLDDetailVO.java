package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-3109
 */
@Setter
@Getter
public class MLDDetailVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String mailModeInb;
	private String carrier;
	private String modeDescriptionInb;
	private String polInb;
	private ZonedDateTime eventTimeInb;
	private String carrierCodeInb;
	private String flightNumberInb;
	private ZonedDateTime flightOperationDateInb;
	private String postalCodeInb;
	private String mailModeOub;
	private String modeDescriptionOub;
	private String pouOub;
	private ZonedDateTime eventTimeOub;
	private String carrierCodeOub;
	private String flightNumberOub;
	private ZonedDateTime flightOperationDateOub;
	private String postalCodeOub;
	private String messageVersion;
	private long messageSequence;
	private int serialNumber;
	private String companyCode;
	private String mailIdr;
	private long flightSequenceNumberOub;
	private int carrierIdOub;
	private long flightSequenceNumberInb;
	private int carrierIdInb;
	private String flightDay;
	private String flight;
	private String airport;
	private String containerJourneyId;
}
