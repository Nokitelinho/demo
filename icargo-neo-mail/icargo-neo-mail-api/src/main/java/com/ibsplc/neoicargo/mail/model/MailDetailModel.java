package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailDetailModel extends BaseModel {
	private String companyCode;
	private String mailId;
	private String dsn;
	private String originOfficeOfExchange;
	private String destinationOfficeOfExchange;
	private String mailCategoryCode;
	private String mailSubclass;
	private int year;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private String containerNumber;
	private String scannedPort;
	private Measure weight;
	private String mailClass;
	private String uldNumber;
	private String ubrNumber;
	private String controlDocumentNumber;
	private String originPACode;
	private String destinationPACode;
	private String originCity;
	private String destinationCity;
	private String originAirport;
	private String destinationAirport;
	private String pou;
	private String destnAirportName;
	private LocalDate rcvDate;
	private LocalDate malUldSegLastUpdateTime;
	private String lastUpdateUser;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
