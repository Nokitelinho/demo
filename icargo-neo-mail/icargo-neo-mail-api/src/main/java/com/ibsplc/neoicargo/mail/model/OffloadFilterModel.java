package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class OffloadFilterModel extends BaseModel {
	private String companyCode;
	private String pol;
	private int carrierId;
	private String flightNumber;
	private int legSerialNumber;
	private long flightSequenceNumber;
	private String carrierCode;
	private LocalDate flightDate;
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
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
