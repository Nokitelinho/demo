package com.ibsplc.neoicargo.mail.vo;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-5991
 */
@Setter
@Getter
public class CarditVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	/** 
	*/
	private static final long serialVersionUID = 1L;
	private String companyCode;
	/** 
	* Cardit Key
	*/
	private String carditKey;
	/** 
	* Interchange reference
	*/
	private String interchangeCtrlReference;
	/** 
	* Syntax id of this interchange
	*/
	private String interchangeSyntaxId;
	/** 
	* Syntax version
	*/
	private int interchangeSyntaxVer;
	/** 
	* Recipient idr
	*/
	private String recipientId;
	/** 
	* Sender id
	*/
	private String senderId;
	/** 
	* Actual Sender Id
	*/
	private String actualSenderId;
	/** 
	* consignment completion date
	*/
	private ZonedDateTime consignmentDate;
	/** 
	* Interchange preparation date
	*/
	private ZonedDateTime preparationDate;
	/** 
	* Consignment doc num
	*/
	private String consignmentNumber;
	/** 
	* message name
	*/
	private String messageName;
	/** 
	* mail catergory code
	*/
	private String mailCategoryCode;
	/** 
	* Msg reference num
	*/
	private String messageRefNum;
	/** 
	* message version
	*/
	private String messageVersion;
	/** 
	* message segment number
	*/
	private int messageSegmentNum;
	/** 
	* message release number
	*/
	private String messageReleaseNum;
	/** 
	* Message type identifier
	*/
	private String messageTypeId;
	/** 
	* testIndicator
	*/
	private int tstIndicator;
	/** 
	* Controlling agency
	*/
	private String controlAgency;
	/** 
	* Interchange control count
	*/
	private int interchangeControlCnt;
	/** 
	* cardit
	*/
	private ZonedDateTime carditReceivedDate;
	/** 
	* last update user
	*/
	private String lastUpdateUser;
	private long msgSeqNum;
	private String stationCode;
	private String messageFunction;
	private Collection<CarditTransportationVO> transportInformation;
	private Collection<CarditReceptacleVO> receptacleInformation;
	private Collection<CarditContainerVO> containerInformation;
	private Collection<CarditTotalVO> totalsInformation;
	private String associationAssignedCode;
	private String carditType;
	private boolean isSenderIdChanged;
	/** 
	* Store the Handover InformationVO of this cardit
	*/
	private Collection<CarditHandoverInformationVO> handoverInformation;
	/** 
	* Store the Reference InformationVO of this cardit
	*/
	private Collection<CarditReferenceInformationVO> referenceInformation;
	/** 
	* COntract reference number
	*/
	private String contractReferenceNumber;
	private Collection<ConsignmentScreeningVO> consignementScreeningVOs;
	private String securityStatusParty;
	private String securityStatusCode;
	private String additionalSecurityInfo;
	private ZonedDateTime securityStatusDate;
	private String applicableRegTransportDirection;
	private String applicableRegBorderAgencyAuthority;
	private String applicableRegReferenceID;
	private String applicableRegFlag;
	private CarditPawbDetailsVO carditPawbDetailsVO;
	private String consignmentIssuerName;

	public void setSenderIdChanged(boolean isSenderIdChanged) {
		this.isSenderIdChanged = isSenderIdChanged;
	}
}
