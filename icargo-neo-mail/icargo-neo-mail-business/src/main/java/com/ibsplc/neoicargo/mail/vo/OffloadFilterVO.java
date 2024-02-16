package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-3109
 */
@Setter
@Getter
public class OffloadFilterVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String pol;
	private int carrierId;
	private String flightNumber;
	private int legSerialNumber;
	private long flightSequenceNumber;
	private String carrierCode;
	private ZonedDateTime flightDate;
	private String offloadType;
	private String containerNumber;
	private String dsn;
	private String dsnOriginExchangeOffice;
	private String dsnDestinationExchangeOffice;
	private String dsnMailClass;
	private String dsnYear;
	private String mailbagOriginExchangeOffice;
	private String mailbagDestinationExchangeOffice;
	private String mailbagCategoryCode;
	private String mailbagSubclass;
	private int mailbagYear;
	private String mailbagDsn;
	private String mailbagRsn;
	private String mailbagId;
	private int pageNumber;
	private String containerType;
	private int defaultPageSize;
	private int totalRecords;
	private boolean remove;
}
