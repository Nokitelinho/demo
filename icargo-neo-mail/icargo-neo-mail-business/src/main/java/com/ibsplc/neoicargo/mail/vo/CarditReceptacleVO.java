package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-5991
 */
@Setter
@Getter
public class CarditReceptacleVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String receptacleId;
	/** 
	* The origin office of exchange
	*/
	private String originExchangeOffice;
	/** 
	* The destination office of exchange
	*/
	private String destinationExchangeOffice;
	/** 
	* The category of mail in despatch
	*/
	private String mailCategoryCode;
	/** 
	* The last digit of the year
	*/
	private int lastDigitOfYear;
	/** 
	* The weight of the receptacle
	*/
	private Quantity receptacleWeight;
	/** 
	* String indicating whether this is the highest numbered receptacle in the container
	*/
	private String highestNumberReceptacleIndicator;
	/** 
	* String indicating whether receptacle contains registered or insured items
	*/
	private String regdOrInsuredIndicator;
	/** 
	* The mail sub class of the despatch
	*/
	private String mailSubClassCode;
	/** 
	* The serial number of the despatch
	*/
	private String despatchNumber;
	/** 
	* The receptacle serial number in the despatch
	*/
	private String receptacleSerialNumber;
	/** 
	* Receptacle handling class
	*/
	private String handlingClass;
	/** 
	* Code list responsible agency, coded
	*/
	private String codeListResponsibleAgency;
	/** 
	* Receptacle dangerous goods indicator
	*/
	private String dangerousGoodsIndicator;
	/** 
	* Package identification
	*/
	private String referenceQualifier;
	/** 
	* Type pf receptacle
	*/
	private String receptacleType;
	/** 
	* The despatch identification information
	*/
	private String despatchIdentification;
	/** 
	* Measurement value
	*/
	private String measurementApplicationQualifier;
	/** 
	* Measure unit qualifier -Kilogramme
	*/
	private String measureUnitQualifier;
	/** 
	* Measurement dimension, coded
	*/
	private String receptacleWeightType;
	/** 
	* Document name coded
	*/
	private String documentOrMessageNameCode;
	/** 
	* Last udpate user
	*/
	private String lastUpdateUser;
	/** 
	* The CDTTYP : CarditType (Message Function)
	*/
	private String carditType;
	private String pkgType;
	private String mailBagId;
	/** 
	* The RCPSTA : ReceptacleStatus (Message Function)
	*/
	private String receptacleStatus;
	/** 
	* The UPDTIM : UpdatedTime (Message Function)
	*/
	private ZonedDateTime updatedTime;
	private String carditKey;
	private long mailSeqNum;
	private ZonedDateTime reqDeliveryTime;
	private String mailOrigin;
	private String mailDestination;
	private ZonedDateTime handoverTime;
	private String sealNumber;
	private String masterDocumentNumber;
	private int ownerId;
	private int duplicateNumber;
	private int sequenceNumber;
}
