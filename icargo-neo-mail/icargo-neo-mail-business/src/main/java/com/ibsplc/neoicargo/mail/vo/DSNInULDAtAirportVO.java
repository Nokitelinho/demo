package com.ibsplc.neoicargo.mail.vo;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * TODO Add the purpose of this class
 * @author A-5991
 */
@Setter
@Getter
public class DSNInULDAtAirportVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String dsn;
	private String originOfficeOfExchange;
	private String destinationOfficeOfExchange;
	private String mailClass;
	private int year;
	private int statedBags;
	private Quantity statedWeight;
	private int acceptedBags;
	private Quantity acceptedWeight;
	private String pltEnabledFlag;
	private String operationFlag;
	private int documentOwnerIdentifier;
	private String masterDocumentNumber;
	private int duplicateNumber;
	private int sequenceNumber;
	private String documentOwnerCode;
	private String mailCategoryCode;
	private String mailSubclass;
	private Collection<DSNInContainerAtAirportVO> dsnInContainerAtAirports;
	private Collection<MailbagInULDAtAirportVO> mailbagInULDVOs;
	private Collection<DSNInConsignmentForULDAtAirportVO> dsnInConsignments;
}
