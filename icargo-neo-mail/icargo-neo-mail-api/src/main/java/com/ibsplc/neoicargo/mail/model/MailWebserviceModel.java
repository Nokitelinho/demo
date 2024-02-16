package com.ibsplc.neoicargo.mail.model;

import java.util.Collection;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailWebserviceModel extends BaseModel {
	private String companyCode;
	private String hhtVersion;
	private String scanningPort;
	private int messagePartId;
	private String product;
	private String scanType;
	private String carrierCode;
	private String flightNumber;
	private LocalDate flightDate;
	private String containerPou;
	private String containerNumber;
	private String containerType;
	private String containerDestination;
	private String containerPol;
	private String remarks;
	private String mailBagId;
	private String damageCode;
	private String damageRemarks;
	private String offloadReason;
	private String returnCode;
	private String toContainerType;
	private String toContainer;
	private String toCarrierCod;
	private String toFlightNumber;
	private Collection<FlightValidationVO> flightValidationVOS;
	private LocalDate toFlightDate;
	private String toContainerPou;
	private String toContainerDestination;
	private String consignmentDocNumber;
	private int serialNumber;
	private boolean isPAbuilt;
	private boolean isDelivered;
	private String userName;
	private LocalDate scanDateTime;
	private String uldFullIndicator;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
