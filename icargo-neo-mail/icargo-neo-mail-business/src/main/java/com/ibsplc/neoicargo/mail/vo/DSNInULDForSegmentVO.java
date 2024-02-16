package com.ibsplc.neoicargo.mail.vo;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-5991
 */
@Setter
@Getter
public class DSNInULDForSegmentVO extends AbstractVO {
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
	private String operationFlag;
	private String pltEnabledFlag;
	private int receivedBags;
	private Quantity receivedWeight;
	private int deliveredBags;
	private Quantity deliveredWeight;
	private int documentOwnerIdentifier;
	private String masterDocumentNumber;
	private int duplicateNumber;
	private int sequenceNumber;
	private String documentOwnerCode;
	/** 
	* The mailSubclass
	*/
	private String mailSubclass;
	/** 
	* The mailCategoryCode
	*/
	private String mailCategoryCode;
	private Collection<DSNInContainerForSegmentVO> dsnInContainerForSegs;
	private Collection<MailbagInULDForSegmentVO> mailBags;
	private Collection<DSNInConsignmentForULDSegmentVO> dsnInConsignments;
	private ZonedDateTime lastUpdateTime;
	private String lastUpdateUser;
	private String ubrNumber;
	private double mailrate;
	private String currencyCode;
	private int transferredPieces;
	private Quantity transferredWeight;
}
