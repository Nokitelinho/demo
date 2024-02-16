package com.ibsplc.neoicargo.mail.model;

import java.util.Collection;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class ULDAtAirportModel extends BaseModel {
	private String companyCode;
	private String uldNumber;
	private String airportCode;
	private String finalDestination;
	private LocalDate assignedDate;
	private String assignedUser;
	private int carrierId;
	private String carrierCode;
	private int numberOfBags;
	private Measure totalWeight;
	private String remarks;
	private String warehouseCode;
	private String locationCode;
	private LocalDate lastUpdateTime;
	private String lastUpdateUser;
	private String transferFromCarrier;
	private Collection<DSNInULDAtAirportModel> dsnInULDAtAirportVOs;
	private Collection<MailbagInULDAtAirportModel> mailbagInULDAtAirportVOs;
	private Collection<OnwardRoutingAtAirportModel> onwardRoutingAtAirportVOs;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
