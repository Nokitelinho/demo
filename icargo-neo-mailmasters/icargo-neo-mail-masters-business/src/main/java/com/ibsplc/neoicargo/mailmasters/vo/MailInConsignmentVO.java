package com.ibsplc.neoicargo.mailmasters.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-3109
 */
@Setter
@Getter
public class MailInConsignmentVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private int statedBags;
	private Quantity statedWeight;
	private String uldNumber;
	private String mailId;
	private String mailCategoryCode;
	private String mailSubclass;
	private String receptacleSerialNumber;
	private String registeredOrInsuredIndicator;
	private String highestNumberedReceptacle;
	private String companyCode;
	private String consignmentNumber;
	private String paCode;
	private int consignmentSequenceNumber;
	private String dsn;
	private String originExchangeOffice;
	private String destinationExchangeOffice;
	private String mailClass;
	private int year;
	private long mailSequenceNumber;
	private double declaredValue;
	private ZonedDateTime consignmentDate;
	private String airportCode;
	private int carrierId;
	private Quantity volume;
	private int TotalLetterBags;
	private int TotalParcelBags;
	private Quantity TotalLetterWeight;
	private String paBuiltFlag;
	private Quantity TotalParcelWeight;
	private ZonedDateTime reqDeliveryTime;
	private String displayUnit;
	private String mailOrigin;
	private String mailDestination;
	private String mailSource;
	private String contractIDNumber;
	private ZonedDateTime transWindowEndTime;
	private String mailServiceLevel;
	private String operation;
	private String mailbagJrnIdr;
	private String mailSubClassGroup;
	private String sealNumber;
	private Quantity totalEmsWeight;
	private int totalEmsBags;
	private Quantity totalSVWeight;
	private int totalSVbags;
	private String shipmentPrefix;
	private String masterDocumentNumber;
	private String mailStatus;
	private int mailDuplicateNumber;
	private int sequenceNumberOfMailbag;
	private int mailBagDocumentOwnerIdr;
	private String keyCondition;
	private boolean isAwbAttached;

	public void setAwbAttached(boolean isAwbAttached) {
		this.isAwbAttached = isAwbAttached;
	}

	private String currencyCode;
	private String operationFlag;
	private Quantity strWeight;
}
