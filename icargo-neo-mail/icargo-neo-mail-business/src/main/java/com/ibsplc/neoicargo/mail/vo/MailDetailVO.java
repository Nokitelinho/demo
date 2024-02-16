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
public class MailDetailVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
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
	private Quantity weight;
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
	private ZonedDateTime rcvDate;
	private ZonedDateTime malUldSegLastUpdateTime;
	private String lastUpdateUser;
}
