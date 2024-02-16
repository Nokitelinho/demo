package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.CarditTempMsgVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.ReceptacleInformationMessageVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.CarditTempDetail.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-6287	:	25-Feb-2020	:	Draft
 */
@Setter
@Getter
@Slf4j
@IdClass(CarditTempDetailsPK.class)
@SequenceGenerator(name = "MALCDTMSGTMPSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALCDTMSGTMP_SEQ")
@Entity
@Table(name = "MALCDTMSGTMP")
public class CarditTempDetails extends BaseEntity implements Serializable {
	private static final String MODULE = "Mail MESSAGE";
	//	@Id
//	@Transient
//	private String companyCode;
	@Id
	@Column(name = "SEQNUM")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALCDTMSGTMPSEQ")
	private long sequenceNumber;
	@Column(name = "CNSMNTIDR")
	private String ConsignmentIdentifier;
	@Column(name = "SYNIDR")
	private String SyntaxID;
	@Column(name = "SYNVER")
	private String SyntaxVersion;
	@Column(name = "SNDIDR")
	private String SenderID;
	@Column(name = "PRCFLD")
	private String ProcessingFailed;
	@Column(name = "SNDPRTIDRQFR")
	private String SenderPartnerIDQualifier;
	@Column(name = "RECPNTIDR")
	private String RecipientID;
	@Column(name = "RCPPRTIDRQFR")
	private String RecipientPartnerIDQualifier;
	@Column(name = "PRPDAT")
	private String PreparationDate;
	@Column(name = "ICHCTLREF")
	private String InterchangeControlReference;
	@Column(name = "TSTIND")
	private String TestIndicator;
	@Column(name = "INTCHGCNLCNT")
	private String InterchangeControlCount;
	@Column(name = "TRLINTCHGCNLREF")
	private String TrailerInterchangeControlReference;
	@Column(name = "CTLAGY")
	private String ControllingAgency;
	@Column(name = "MSGREFNUM")
	private String MessageReferenceNumber;
	@Column(name = "MSGTYP")
	private String MessageTypeIdentifier;
	@Column(name = "MSGVER")
	private String MessageVersionNumber;
	@Column(name = "MSGRELNUM")
	private String MessageReleaseNumber;
	@Column(name = "ASNASGCOD")
	private String AssociationAssignedCode;
	@Column(name = "MSGSEQNUM")
	private long messageSeqNum;
	@Column(name = "MSGFUN")
	private String MessageFunction;
	@Column(name = "DTMPRDQLF")
	private String DateTimePeriodQualifier;
	@Column(name = "DTMFMTQLF")
	private String DateTimeFormatQualifier;
	@Column(name = "TRLMSGREFNUM")
	private String TrailerMessageReferenceNumber;
	@Column(name = "NUMSEG")
	private String NumberOfSegments;
	@Column(name = "ERRCAR")
	private String ErrorCardit;
	@Column(name = "MALCATCODIDR")
	private String MailCategoryCodeIndicator;
	@Column(name = "CNSCMPDAT")
	private String ConsignmentCompletionDate;
	@Column(name = "REQDLVTIM")
	private String ReqDeliveryTime;
	@Column(name = "TRPSTGQFR")
	private String TransportStageQualifier;
	@Column(name = "CNVREFNUM")
	private String ConveyanceReference;
	@Column(name = "TRSIDR")
	private String TransportIdentification;
	@Column(name = "CARIDR")
	private String CarrierID;
	@Column(name = "DEPLOCQFR")
	private String DepartureLocationQualifier;
	@Column(name = "DEPPLC")
	private String DeparturePlace;
	@Column(name = "ARRLOCQFR")
	private String ArrivalLocationQualifier;
	@Column(name = "ARRPLC")
	private String ArrivalPlace;
	@Column(name = "TRPDEPDAT")
	private String TransportInfoDepartureDate;
	@Column(name = "TRPARRDAT")
	private String TransportInfoArrivalDate;
	@Column(name = "MALCATCOD")
	private String MailCategoryCode;
	@Column(name = "DOMTRPSTGQFR")
	private String DomesticTransportStageQualifier;
	@Column(name = "DOMCNVREFNUM")
	private String DomesticConveyanceReference;
	@Column(name = "DOMTRSIDR")
	private String DomesticTransportIdentification;
	@Column(name = "DOMCARIDR")
	private String DomesticCarrierID;
	@Column(name = "TRPMODTRNSPT")
	private String ModeOfTransport;
	@Column(name = "CARNAM")
	private String CarrierName;
	@Column(name = "CODLSTQFR")
	private String CodeListQualifier;
	@Column(name = "AGYCARCOD")
	private String AgencyForCarrierCode;
	@Column(name = "TSRLEGRAT")
	private String TransportLegRate;
	@Column(name = "CNTREF")
	private String ContractReference;
	@Column(name = "DOMDEPLOCQFR")
	private String DomesticDepartureLocationQualifier;
	@Column(name = "DOMDEPPLC")
	private String DomesticDeparturePlace;
	@Column(name = "DOMARRLOCQFR")
	private String DomesticArrivalLocationQualifier;
	@Column(name = "DOMARRPLC")
	private String DomesticArrivalPlace;
	@Column(name = "DEPPLCNAM")
	private String DeparturePlaceName;
	@Column(name = "DEPCODLSTQFR")
	private String DepartureCodeListQualifier;
	@Column(name = "DEPCODLSTAGY")
	private String DepartureCodeListAgency;
	@Column(name = "ARRPLCNAM")
	private String ArrivalPlaceName;
	@Column(name = "ARRCODLSTQFR")
	private String ArrivalCodeListQualifier;
	@Column(name = "ARRCODLSTAGY")
	private String ArrivalCodeListAgency;
	@Column(name = "TRPDTMPRDQLF")
	private String TransportInfoDateTimePeriodQualifier;
	@Column(name = "TRPDTMFMTQFR")
	private String TransportInfoDateTimeFormatQualifier;
	@Column(name = "DEPDAT")
	private String DepartureDate;
	@Column(name = "ARRDAT")
	private String ArrivalDate;
	@Column(name = "MALCLSCOD")
	private String MailClassCode;
	@Column(name = "CNLVAL")
	private String ControlValue;
	@Column(name = "TOTPCSCNLQFR")
	private String TotalPiecesControlQualifier;
	@Column(name = "NUMRCP")
	private String NumberOfReceptacles;
	@Column(name = "TOTPCSMSRUNTQFR")
	private String TotalPiecesMeasureUnitQualifier;
	@Column(name = "TOTWGTCNLQFR")
	private String TotalWeightControlQualifier;
	@Column(name = "WGTRCP")
	private String WeightOfReceptacles;
	@Column(name = "TOTWGTMSRUNTQFR")
	private String TotalWeightMeasureUnitQualifier;
	@Column(name = "CNSCRTREFNUM")
	private String ConsignmentContractReferenceNumber;
	@Column(name = "TRPCRTREFQFR")
	private String TransportContractReferenceQualifier;
	@Column(name = "HNDOVRTRPSTGQFR")
	private String RefInfoTransportStageQualifier;
	@Column(name = "HNDOVRORGLOCQFR")
	private String HandoverOriginLocationQualifier;
	@Column(name = "HNDOVRORGLOCIDR")
	private String HandoverOriginLocationIdentifier;
	@Column(name = "HNDOVRORGNAM")
	private String HandoverOriginLocationName;
	@Column(name = "HNDOVRORGCODQFR")
	private String HandoverOriginCodeListQualifier;
	@Column(name = "HNDOVRORGCODAGY")
	private String HandoverOriginCodeListAgency;
	@Column(name = "HNDOVRDSTLOCQFR")
	private String HandoverDestnLocationQualifier;
	@Column(name = "HNDOVRDSTLOCIDR")
	private String HandoverDestnLocationIdentifier;
	@Column(name = "HNDOVRDSTNAM")
	private String HandoverDestnLocationName;
	@Column(name = "HNDOVRDSTCODQFR")
	private String HandoverDestnCodeListQualifier;
	@Column(name = "HNDOVRDSTCODAGY")
	private String HandoverDestnCodeListAgency;
	@Column(name = "HNDOVRDTMQFR")
	private String HandOverInfoDateTimePeriodQualifier;
	@Column(name = "HNDOVRDTMFMTQFR")
	private String HandOverInfoDateTimeFormatQualifier;
	@Column(name = "HNDOVRORGCUTOFF")
	private String OriginCutOffPeriod;
	@Column(name = "HNDOVRDSTCUTOFF")
	private String DestinationCutOffPeriod;
	@Column(name = "EQPQFR")
	private String EquipmentQualifier;
	@Column(name = "CTRNUM")
	private String ContainerNumber;
	@Column(name = "CODLSTRSPAGY")
	private String ContainerInfoCodeListResponsibleAgency;
	@Column(name = "CTRTYP")
	private String ContainerType;
	@Column(name = "CTRCODLSTQFR")
	private String ContainerInfoCodeListQualifier;
	@Column(name = "TYPCODLSTRSPAGY")
	private String TypeCodeListResponsibleAgency;
	@Column(name = "CTRJNYIDR")
	private String ContainerJourneyIdentifier;
	@Column(name = "MSRAPP")
	private String MeasurementApplication;
	@Column(name = "MSRDIM")
	private String MeasurementDimension;
	@Column(name = "CTRMSRUNTQFR")
	private String ContainerInfoMeasureUnitQualifier;
	@Column(name = "CTRWGT")
	private String ContainerWeight;
	@Column(name = "SELNUM")
	private String SealNumber;
	@Column(name = "ERRCOD")
	private String errorCodes;
	@Column(name = "PRCSTA")
	private String messageStatus;
	@Column(name = "MSGPRC")
	private String isProcessed;
	@Column(name = "POSAWBNUM")
	private String postalAwbNumber;
	@Column(name = "SHPUPUCOD")
	private String shipperUpuCode;
	@Column(name = "CNSUPUCOD")
	private String consigneeUpuCode;
	@Column(name = "CSGORGEXGOFCCOD")
	private String consignmentOrigin;
	@Column(name = "CSGDSTEXGOFCCOD")
	private String consignmentDestination;
	@OneToMany
	@JoinColumns({
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "SEQNUM", referencedColumnName = "SEQNUM", insertable = false, updatable = false),
			@JoinColumn(name = "CNSMNTIDR", referencedColumnName = "CNSMNTIDR", insertable = false, updatable = false)
	})
	private Set<CarditTempMailBagDetails> mailBagVOs;
	public CarditTempDetails() {
	}

	/**
	 * Constructor	: 	@param carditTempMsgVOs Constructor	: 	@throws CreateException Constructor	: 	@throws SystemException Created by	:	A-6287 Created on	:	26-Feb-2020
	 * @throws SystemException
	 */
	public CarditTempDetails(Collection<CarditTempMsgVO> carditTempMsgVOs) {
		for (CarditTempMsgVO tmpVO : carditTempMsgVOs) {
			populateAttributes(tmpVO);
			try {
				PersistenceController.getEntityManager().persist(this);
			} catch (CreateException e) {
				throw new SystemException(e.getMessage());
			}
			populateChildren(tmpVO);
		}
	}

	/**
	 * Method		:	CarditTempDetails.populateChildren Added by 	:	A-6287 on 02-Mar-2020 Used for 	: Parameters	:	@param tmpVO  Return type	: 	void
	 * @throws SystemException
	 */
	private void populateChildren(CarditTempMsgVO tmpVO) {
		CarditTempDetailsPK carditTempDetailsPK = constructCarditTempDetailsPK();
		CarditTempMailBagDetails carditTempMailBagDetails = null;
		Set<String> mailBagList = new HashSet<>();
		if (tmpVO.getMailbagVOs() != null)
			for (ReceptacleInformationMessageVO rcpInfoVO : tmpVO.getMailbagVOs()) {
				if (!mailBagList.add(rcpInfoVO.getDRTagNo())) {
					continue;
				}
				try {
					carditTempMailBagDetails = new CarditTempMailBagDetails(carditTempDetailsPK, rcpInfoVO);
					if (this.mailBagVOs != null) {
						this.mailBagVOs.add(carditTempMailBagDetails);
					} else {
						this.mailBagVOs = new HashSet<CarditTempMailBagDetails>();
						this.mailBagVOs.add(carditTempMailBagDetails);
					}
				} finally {
				}
			}
	}
	private CarditTempDetailsPK constructCarditTempDetailsPK(){
		CarditTempDetailsPK carditTempDetailsPK = new CarditTempDetailsPK();
		carditTempDetailsPK.setCompanyCode(this.getCompanyCode());
		carditTempDetailsPK.setConsignmentIdentifier(this.getConsignmentIdentifier());
		carditTempDetailsPK.setSequenceNumber(this.getSequenceNumber());
		return carditTempDetailsPK;

	}

	/**
	 * Method		:	CarditTempDetails.populateAttributes Added by 	:	A-6287 on 26-Feb-2020 Used for 	: Parameters	:	@param carditTempMsgVOs  Return type	: 	void
	 */
	private void populateAttributes(CarditTempMsgVO tmpVO) {
		log.debug("CarditTempDetails" + " : " + "populateAttributes" + " Entering");
		this.setCompanyCode(tmpVO.getCompanyCode());
		this.setConsignmentIdentifier(tmpVO.getConsignmentIdentifier());
		setSyntaxID(tmpVO.getSyntaxID());
		setSyntaxVersion(tmpVO.getSyntaxVersion());
		setSenderID(tmpVO.getSenderID());
		setProcessingFailed(tmpVO.getProcessingFailed());
		setSenderPartnerIDQualifier(tmpVO.getSenderPartnerIDQualifier());
		setRecipientID(tmpVO.getRecipientID());
		setRecipientPartnerIDQualifier(tmpVO.getRecipientPartnerIDQualifier());
		setPreparationDate(tmpVO.getPreparationDate());
		setInterchangeControlReference(tmpVO.getInterchangeControlReference());
		setTestIndicator(tmpVO.getTestIndicator());
		setInterchangeControlCount(tmpVO.getInterchangeControlCount());
		setTrailerInterchangeControlReference(tmpVO.getTrailerInterchangeControlReference());
		setControllingAgency(tmpVO.getControllingAgency());
		setMessageReferenceNumber(tmpVO.getMessageReferenceNumber());
		setMessageTypeIdentifier(tmpVO.getMessageTypeIdentifier());
		setMessageVersionNumber(tmpVO.getMessageVersionNumber());
		setMessageReleaseNumber(tmpVO.getMessageReleaseNumber());
		setAssociationAssignedCode(tmpVO.getAssociationAssignedCode());
		setMessageFunction(tmpVO.getMessageFunction());
		setDateTimePeriodQualifier(tmpVO.getDateTimePeriodQualifier());
		setDateTimeFormatQualifier(tmpVO.getDateTimeFormatQualifier());
		setTrailerMessageReferenceNumber(tmpVO.getTrailerMessageReferenceNumber());
		setNumberOfSegments(tmpVO.getNumberOfSegments());
		setErrorCardit(tmpVO.getErrorCardit());
		setMailCategoryCodeIndicator(tmpVO.getMailCategoryCodeIndicator());
		setConsignmentCompletionDate(tmpVO.getConsignmentCompletionDate());
		setReqDeliveryTime(tmpVO.getReqDeliveryTime());
		setTransportStageQualifier(tmpVO.getTransportStageQualifier());
		setConveyanceReference(tmpVO.getConveyanceReference());
		setTransportIdentification(tmpVO.getTransportIdentification());
		setCarrierID(tmpVO.getCarrierID());
		setDepartureLocationQualifier(tmpVO.getDepartureLocationQualifier());
		setDeparturePlace(tmpVO.getDeparturePlace());
		setArrivalLocationQualifier(tmpVO.getArrivalLocationQualifier());
		setArrivalPlace(tmpVO.getArrivalPlace());
		setTransportInfoDepartureDate(tmpVO.getTransportInfoDepartureDate());
		setTransportInfoArrivalDate(tmpVO.getTransportInfoArrivalDate());
		setMailCategoryCode(tmpVO.getMailCategoryCode());
		setDomesticTransportStageQualifier(tmpVO.getDomesticTransportStageQualifier());
		setDomesticConveyanceReference(tmpVO.getDomesticConveyanceReference());
		setDomesticTransportIdentification(tmpVO.getDomesticTransportIdentification());
		setDomesticCarrierID(tmpVO.getDomesticCarrierID());
		setModeOfTransport(tmpVO.getModeOfTransport());
		setCarrierName(tmpVO.getCarrierName());
		setCodeListQualifier(tmpVO.getCodeListQualifier());
		setAgencyForCarrierCode(tmpVO.getAgencyForCarrierCode());
		setTransportLegRate(tmpVO.getTransportLegRate());
		setContractReference(tmpVO.getContractReference());
		setDomesticDepartureLocationQualifier(tmpVO.getDomesticDepartureLocationQualifier());
		setDomesticDeparturePlace(tmpVO.getDomesticDeparturePlace());
		setDomesticArrivalLocationQualifier(tmpVO.getDomesticArrivalLocationQualifier());
		setDomesticArrivalPlace(tmpVO.getDomesticArrivalPlace());
		setDeparturePlaceName(tmpVO.getDeparturePlaceName());
		setDepartureCodeListQualifier(tmpVO.getDepartureCodeListQualifier());
		//TODO:Neo to correct
		//setDepartureCodeListAgency(tmpVO.getDepartureCodesListAgency());
		setArrivalPlaceName(tmpVO.getArrivalPlaceName());
		setArrivalCodeListQualifier(tmpVO.getArrivalCodeListQualifier());
		setArrivalCodeListAgency(tmpVO.getArrivalCodeListAgency());
		setTransportInfoDateTimePeriodQualifier(tmpVO.getTransportInfoDateTimePeriodQualifier());
		setTransportInfoDateTimeFormatQualifier(tmpVO.getTransportInfoDateTimeFormatQualifier());
		setDepartureDate(tmpVO.getDepartureDate());
		setArrivalDate(tmpVO.getArrivalDate());
		setMailClassCode(tmpVO.getMailClassCode());
		setControlValue(tmpVO.getControlValue());
		setTotalPiecesControlQualifier(tmpVO.getTotalPiecesControlQualifier());
		setNumberOfReceptacles(tmpVO.getNumberOfReceptacles());
		setTotalPiecesMeasureUnitQualifier(tmpVO.getTotalPiecesMeasureUnitQualifier());
		setTotalWeightControlQualifier(tmpVO.getTotalWeightControlQualifier());
		setWeightOfReceptacles(tmpVO.getWeightOfReceptacles());
		setTotalWeightMeasureUnitQualifier(tmpVO.getTotalWeightMeasureUnitQualifier());
		setConsignmentContractReferenceNumber(tmpVO.getConsignmentContractReferenceNumber());
		setTransportContractReferenceQualifier(tmpVO.getTransportContractReferenceQualifier());
		setRefInfoTransportStageQualifier(tmpVO.getRefInfoTransportStageQualifier());
		setHandoverOriginLocationQualifier(tmpVO.getHandoverOriginLocationQualifier());
		setHandoverOriginLocationIdentifier(tmpVO.getHandoverOriginLocationIdentifier());
		setHandoverOriginLocationName(tmpVO.getHandoverOriginLocationName());
		setHandoverOriginCodeListQualifier(tmpVO.getHandoverOriginCodeListQualifier());
		setHandoverOriginCodeListAgency(tmpVO.getHandoverOriginCodeListAgency());
		setHandoverDestnLocationQualifier(tmpVO.getHandoverDestnLocationQualifier());
		setHandoverDestnLocationIdentifier(tmpVO.getHandoverDestnLocationIdentifier());
		setHandoverDestnLocationName(tmpVO.getHandoverDestnLocationName());
		setHandoverDestnCodeListQualifier(tmpVO.getHandoverDestnCodeListQualifier());
		setHandoverDestnCodeListAgency(tmpVO.getHandoverDestnCodeListAgency());
		setHandOverInfoDateTimePeriodQualifier(tmpVO.getHandOverInfoDateTimePeriodQualifier());
		setHandOverInfoDateTimeFormatQualifier(tmpVO.getHandOverInfoDateTimeFormatQualifier());
		setOriginCutOffPeriod(tmpVO.getOriginCutOffPeriod());
		setDestinationCutOffPeriod(tmpVO.getDestinationCutOffPeriod());
		setEquipmentQualifier(tmpVO.getEquipmentQualifier());
		setContainerNumber(tmpVO.getContainerNumber());
		setContainerInfoCodeListResponsibleAgency(tmpVO.getContainerInfoCodeListResponsibleAgency());
		setContainerType(tmpVO.getContainerType());
		setContainerInfoCodeListQualifier(tmpVO.getContainerInfoCodeListQualifier());
		setTypeCodeListResponsibleAgency(tmpVO.getTypeCodeListResponsibleAgency());
		setContainerJourneyIdentifier(tmpVO.getContainerJourneyIdentifier());
		setMeasurementApplication(tmpVO.getMeasurementApplication());
		setMeasurementDimension(tmpVO.getMeasurementDimension());
		setContainerInfoMeasureUnitQualifier(tmpVO.getContainerInfoMeasureUnitQualifier());
		setContainerWeight(tmpVO.getContainerWeight());
		setSealNumber(tmpVO.getSealNumber());
		setMessageSeqNum(tmpVO.getMessageSeqnum());
		setIsProcessed("N");
		setPostalAwbNumber(tmpVO.getPostalawbnumber());
		setShipperUpuCode(tmpVO.getShipperUpuCode());
		setConsigneeUpuCode(tmpVO.getConsigneeUpuCode());
		setConsignmentOrigin(tmpVO.getConsignmentOrigin());
		setConsignmentDestination(tmpVO.getConsignmentDestination());
		log.debug("CarditTempDetails" + " : " + "populateAttributes" + " Exiting");
	}

}
