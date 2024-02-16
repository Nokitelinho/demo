package com.ibsplc.neoicargo.mail.component;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.CarditTempMsgVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.ReceptacleInformationMessageVO;
import lombok.Getter;
import lombok.Setter;

/**
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.CarditTempMailBagDetails.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-6287	:	02-Mar-2020	:	Draft
 */
@Setter
@Getter
@Entity
@Table(name = "MALCDTMSGRCPTMP")
public class CarditTempMailBagDetails {
	@Column(name = "NUMPKG")
	private String NumberOfPackages;
	@Column(name = "RCPTYP")
	private String ReceptacleType;
	@Column(name = "PKGTYP")
	private String PkgType;
	@Column(name = "REFQFR")
	private String ReferenceQualifier;
	@Column(name = "RCPIDR")
	private String ReceptacleID;
	@Column(name = "DGRIND")
	private String DangerousGoodsIndicator;
	@Column(name = "INSIND")
	private String InsuranceIndicator;
	@Column(name = "HNDCLS")
	private String HandlingClass;
	@Column(name = "CODLSTAGY")
	private String CodeListResponsibleAgency;
	@Column(name = "MSRAPPQFR")
	private String MeasurementApplicationQualifier;
	@Column(name = "RCPWGTTYP")
	private String ReceptacleWeightType;
	@Column(name = "MSRUNTQFR")
	private String MeasureUnitQualifier;
	@Column(name = "DOCMSGNAMCOD")
	private String DocumentOrMessageNameCode;
	@Column(name = "DSPIDN")
	private String DespatchIdentification;
	@Column(name = "ORGEXEOFF")
	private String OriginExchangeOffice;
	@Column(name = "DSTEXEOFF")
	private String DestinationExchangeOffice;
	@Column(name = "RCPMALCATCOD")
	private String ReceptacleInfoMailCategoryCode;
	@Column(name = "MALSUBCLSCOD")
	private String MailSubClassCode;
	@Column(name = "LSTDGTYAR")
	private String LastDigitOfYear;
	@Column(name = "DSPNUM")
	private String DespatchNumber;
	@Column(name = "RCPSERNUM")
	private String ReceptacleSerialNumber;
	@Column(name = "HGHNUMRCPIND")
	private String HighestNumberReceptacleIndicator;
	@Column(name = "RGDINSIND")
	private String RegdOrInsuredIndicator;
	@Column(name = "RCPWGT")
	private String ReceptacleWeight;
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "sequenceNumber", column = @Column(name = "SEQNUM")),
			@AttributeOverride(name = "ConsignmentIdentifier", column = @Column(name = "CNSMNTIDR")),
			@AttributeOverride(name = "DRTagNo", column = @Column(name = "DRTAGNUM")) })
	private CarditTempMailBagDetailsPK carditTempMailBagDetailsPK;
	@Column(name = "CONSELNUM")
	private String sealNumber;



	public CarditTempMailBagDetails() {
	}

	/**
	 * Constructor	: 	@param carditTempDetailsPK Constructor	: 	@param tmpVO Created by	:	A-6287 Created on	:	02-Mar-2020
	 * @throws SystemException
	 * @throws CreateException
	 */
	public CarditTempMailBagDetails(CarditTempDetailsPK carditTempDetailsPK, ReceptacleInformationMessageVO rcpInfoVO) {
		populateAttributes(carditTempDetailsPK, rcpInfoVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException e) {
			throw new SystemException(e.getMessage());
		}
	}

	/**
	 * Method		:	CarditTempMailBagDetails.populateAttributes Added by 	:	A-6287 on 02-Mar-2020 Used for 	: Parameters	:	@param tmpVO Return type	: 	void
	 * @throws SystemException
	 * @throws CreateException
	 */
	private void populateAttributes(CarditTempDetailsPK carditTempDetailsPK, ReceptacleInformationMessageVO rcpInfoVO) {
		CarditTempMailBagDetailsPK mailBagPK = null;
		mailBagPK = new CarditTempMailBagDetailsPK();
		mailBagPK.setCompanyCode(carditTempDetailsPK.getCompanyCode());
		mailBagPK.setSequenceNumber(carditTempDetailsPK.getSequenceNumber());
		mailBagPK.setConsignmentIdentifier(carditTempDetailsPK.getConsignmentIdentifier());
		mailBagPK.setDRTagNo(rcpInfoVO.getDRTagNo());
		setCarditTempMailBagDetailsPK(mailBagPK);
		setSealNumber(rcpInfoVO.getSealNumber());
		setReferenceQualifier(rcpInfoVO.getReferenceQualifier());
		setReceptacleID(rcpInfoVO.getReceptacleID());
		setDangerousGoodsIndicator(rcpInfoVO.getDangerousGoodsIndicator());
		setInsuranceIndicator(rcpInfoVO.getInsuranceIndicator());
		setHandlingClass(rcpInfoVO.getHandlingClass());
		setCodeListResponsibleAgency(rcpInfoVO.getCodeListResponsibleAgency());
		setMeasurementApplicationQualifier(rcpInfoVO.getMeasurementApplicationQualifier());
		setReceptacleWeightType(rcpInfoVO.getReceptacleWeightType());
		setMeasureUnitQualifier(rcpInfoVO.getMeasureUnitQualifier());
		setDocumentOrMessageNameCode(rcpInfoVO.getDocumentOrMessageNameCode());
		setDespatchIdentification(rcpInfoVO.getDespatchIdentification());
		if (rcpInfoVO.getReceptacleVO() != null) {
			setOriginExchangeOffice(rcpInfoVO.getReceptacleVO().getOriginExchangeOffice());
			setDestinationExchangeOffice(rcpInfoVO.getReceptacleVO().getDestinationExchangeOffice());
			setReceptacleInfoMailCategoryCode(rcpInfoVO.getReceptacleVO().getMailCategoryCode());
			setMailSubClassCode(rcpInfoVO.getReceptacleVO().getMailSubClassCode());
			setLastDigitOfYear(Integer.toString(rcpInfoVO.getReceptacleVO().getLastDigitOfYear()));
			setDespatchNumber(rcpInfoVO.getReceptacleVO().getDespatchNumber());
			setReceptacleSerialNumber(rcpInfoVO.getReceptacleVO().getReceptacleSerialNumber());
			setHighestNumberReceptacleIndicator(rcpInfoVO.getReceptacleVO().getHighestNumberReceptacleIndicator());
			setRegdOrInsuredIndicator(rcpInfoVO.getReceptacleVO().getRegdOrInsuredIndicator());
			setReceptacleWeight(Double.toString(rcpInfoVO.getReceptacleVO().getReceptacleWeight()));
		}
	}

	/**
	 * Getter for sealNumber Added by : A-5219 on 28-Nov-2020 Used for :
	 */
	public String getSealNumber() {
		return sealNumber;
	}

	/**
	 * @param sealNumber the sealNumber to setSetter for sealNumber Added by : A-5219 on 28-Nov-2020 Used for :
	 */
	public void setSealNumber(String sealNumber) {
		this.sealNumber = sealNumber;
	}
}
