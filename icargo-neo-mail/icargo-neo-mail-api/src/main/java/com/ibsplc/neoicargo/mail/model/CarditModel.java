package com.ibsplc.neoicargo.mail.model;

import java.util.Collection;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class CarditModel extends BaseModel {
	private String companyCode;
	private String carditKey;
	private String interchangeCtrlReference;
	private String interchangeSyntaxId;
	private int interchangeSyntaxVer;
	private String recipientId;
	private String senderId;
	private String actualSenderId;
	private LocalDate consignmentDate;
	private LocalDate preparationDate;
	private String consignmentNumber;
	private String messageName;
	private String mailCategoryCode;
	private String messageRefNum;
	private String messageVersion;
	private int messageSegmentNum;
	private String messageReleaseNum;
	private String messageTypeId;
	private int tstIndicator;
	private String controlAgency;
	private int interchangeControlCnt;
	private LocalDate carditReceivedDate;
	private String lastUpdateUser;
	private long msgSeqNum;
	private String stationCode;
	private String messageFunction;
	private Collection<CarditTransportationModel> transportInformation;
	private Collection<CarditReceptacleModel> receptacleInformation;
	private Collection<CarditContainerModel> containerInformation;
	private Collection<CarditTotalModel> totalsInformation;
	private String associationAssignedCode;
	private String carditType;
	private boolean isSenderIdChanged;
	private Collection<CarditHandoverInformationModel> handoverInformation;
	private Collection<CarditReferenceInformationModel> referenceInformation;
	private String contractReferenceNumber;
	private Collection<ConsignmentScreeningModel> consignementScreeningVOs;
	private String securityStatusParty;
	private String securityStatusCode;
	private String additionalSecurityInfo;
	private LocalDate securityStatusDate;
	private String applicableRegTransportDirection;
	private String applicableRegBorderAgencyAuthority;
	private String applicableRegReferenceID;
	private String applicableRegFlag;
	private CarditPawbDetailsModel carditPawbDetailsVO;
	private String consignmentIssuerName;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
