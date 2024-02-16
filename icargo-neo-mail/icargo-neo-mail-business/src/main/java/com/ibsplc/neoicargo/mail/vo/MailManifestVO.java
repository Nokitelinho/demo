package com.ibsplc.neoicargo.mail.vo;

import java.util.Collection;
import java.util.HashMap;
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
public class MailManifestVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String depPort;
	private String flightNumber;
	private String flightCarrierCode;
	private int carrierId;
	private ZonedDateTime depDate;
	private String strDepDate;
	private String flightStatus;
	private String flightRoute;
	private int totalbags;
	private int totalAcceptedBags;
	private Quantity totalAcceptedWeight;
	private Quantity totalWeight;
	private Collection<ContainerDetailsVO> containerDetails;
	private Collection<MailSummaryVO> mailSummaryVOs;
	private HashMap<String, Collection<String>> polPouMap;
}
