package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailbagInULDAtAirportModel extends BaseModel {
	private String mailId;
	private String containerNumber;
	private String damageFlag;
	private LocalDate scannedDate;
	private Measure weight;
	private String mailClass;
	private String mailSubclass;
	private String mailCategoryCode;
	private String transferFromCarrier;
	private String sealNumber;
	private long mailSequenceNumber;
	private int statedBags;
	private Measure statedWgt;
	private int acceptedBags;
	private Measure acceptedWgt;
	private int deliveredPcs;
	private Measure deliveredWgt;
	private int recievedPcs;
	private Measure recievedWgt;
	private String comapnyCode;
	private int carrierId;
	private String airportCode;
	private String uldNumber;
	private String arrivedFlag;
	private String deliveredFlag;
	private String acceptedFlag;
	private String mailSource;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
