package com.ibsplc.neoicargo.mail.vo;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;

/** 
 * Mail Webesrvice VO for GHA input to iCargo.
 * @author A-3109
 */
@Setter
@Getter
public class MailWebserviceVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	public static final String MAIL_STATUS_REASSIGN = "RSN";
	public static final String MAIL_STATUS_EXPORT = "EXP";
	public static final String MAIL_STATUS_CANCEL = "CNL";
	private String companyCode;
	private String hhtVersion;
	private String scanningPort;
	private int messagePartId;
	private String product;
	private String scanType;
	private String carrierCode;
	private String flightNumber;
	private ZonedDateTime flightDate;
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

	public void setPAbuilt(boolean isPAbuilt) {
		this.isPAbuilt = isPAbuilt;
	}

	public void setDelivered(boolean isDelivered) {
		this.isDelivered = isDelivered;
	}

	private ZonedDateTime toFlightDate;
	private String toContainerPou;
	private String toContainerDestination;
	private String consignmentDocNumber;
	private int serialNumber;
	private boolean isPAbuilt;
	private boolean isDelivered;
	private String userName;
	private ZonedDateTime scanDateTime;
	private String uldFullIndicator;
}
