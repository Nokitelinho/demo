package com.ibsplc.neoicargo.mail.vo;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * TODO Add the purpose of this class
 * @author A-3109
 */
@Setter
@Getter
public class ULDAtAirportVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String uldNumber;
	private String airportCode;
	private String finalDestination;
	private ZonedDateTime assignedDate;
	private String assignedUser;
	private int carrierId;
	private String carrierCode;
	private int numberOfBags;
	private Quantity totalWeight;
	private String remarks;
	private String warehouseCode;
	private String locationCode;
	private ZonedDateTime lastUpdateTime;
	private String lastUpdateUser;
	private String transferFromCarrier;
	private Collection<DSNInULDAtAirportVO> dsnInULDAtAirportVOs;
	private Collection<MailbagInULDAtAirportVO> mailbagInULDAtAirportVOs;
	private Collection<OnwardRoutingAtAirportVO> onwardRoutingAtAirportVOs;
}
