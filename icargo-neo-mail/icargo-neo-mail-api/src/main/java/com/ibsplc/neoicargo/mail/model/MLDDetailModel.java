package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MLDDetailModel extends BaseModel {
	private String mailModeInb;
	private String carrier;
	private String modeDescriptionInb;
	private String polInb;
	private LocalDate eventTimeInb;
	private String carrierCodeInb;
	private String flightNumberInb;
	private LocalDate flightOperationDateInb;
	private String postalCodeInb;
	private String mailModeOub;
	private String modeDescriptionOub;
	private String pouOub;
	private LocalDate eventTimeOub;
	private String carrierCodeOub;
	private String flightNumberOub;
	private LocalDate flightOperationDateOub;
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
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
