package com.ibsplc.neoicargo.mail.component;

import javax.persistence.*;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.CarditEnquiryFilterVO;
import com.ibsplc.neoicargo.mail.vo.CarditReceptacleVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import java.time.ZonedDateTime;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/** 
 * This entity persists the information about a receptacle in the Cardit
 * @author A-1739
 */
@Setter
@Getter
@Entity
@Slf4j
@IdClass(CarditReceptaclePK.class)
@Table(name = "MALCDTRCP")
public class CarditReceptacle extends BaseEntity implements Serializable {
	private static final String MAIL_OPERATIONS = "mail.operations";
	/** 
	* The origin office of exchange
	*/
	@Column(name = "ORGEXGOFF")
	private String originExchangeOffice;
	/** 
	* The destination office of exchange
	*/
	@Column(name = "DSTEXGOFF")
	private String destinationExchangeOffice;
	/** 
	* The category of mail in despatch
	*/
	@Column(name = "MALCTG")
	private String mailCategoryCode;
	/** 
	* The last digit of the year
	*/
	@Column(name = "DSPYER")
	private int lastDigitOfYear;
	/** 
	* The weight of the receptacle
	*/
	@Column(name = "RCPWGT")
	private double receptacleWeight;
	/** 
	* String indicating whether this is the highest numbered receptacle in the container
	*/
	@Column(name = "HSTRCPNUM")
	private String highestNumberReceptacleIndicator;
	/** 
	* String indicating whether receptacle contains registered or insured items
	*/
	@Column(name = "RCPRGDINS")
	private String regdOrInsuredIndicator;
	/** 
	* The mail sub class of the despatch
	*/
	@Column(name = "MALSUBCLS")
	private String mailSubClassCode;
	/** 
	* The serial number of the despatch
	*/
	@Column(name = "DSPSRLNUM")
	private String despatchNumber;
	/** 
	* The receptacle serial number in the despatch
	*/
	@Column(name = "RCPSRLNUM")
	private String receptacleSerialNumber;
	/** 
	* Receptacle handling class
	*/
	@Column(name = "HNDCLS")
	private String handlingClass;
	/** 
	* Code list responsible agency, coded
	*/
	@Column(name = "CODLSTAGY")
	private String codeListResponsibleAgency;
	/** 
	* Receptacle dangerous goods indicator
	*/
	@Column(name = "DGRGDSIND")
	private String dangerousGoodsIndicator;
	/** 
	* Package identification
	*/
	@Column(name = "REFQLF")
	private String referenceQualifier;
	/** 
	* Type pf receptacle
	*/
	@Column(name = "RCPTYP")
	private String receptacleType;
	/** 
	* The despatch identification information
	*/
	@Column(name = "DSPIDR")
	private String despatchIdentification;
	/** 
	* Measurement value
	*/
	@Column(name = "MMTAPNQLF")
	private String measurementApplicationQualifier;
	/** 
	* Measure unit qualifier -Kilogramme
	*/
	@Column(name = "MMTUNTQLF")
	private String measureUnitQualifier;
	/** 
	* Measurement dimension, coded
	*/
	@Column(name = "WGTTYP")
	private String receptacleWeightType;
	/** 
	* Document name coded
	*/
	@Column(name = "DOCMSGNAM")
	private String documentOrMessageNameCode;
	/** 
	* The CDTTYP : CarditType (Message Function) 
	*/
	@Column(name = "CDTTYP")
	private String carditType;
	@Column(name = "MALHNDTIM")
	private LocalDateTime handoverTime;

	@Id
	@Transient
	private String companyCode;
	@Id
	@Column(name = "CDTKEY")
	private String carditKey;
	@Id
	@Column(name = "RCPIDR")
	private String receptacleId;

	public CarditReceptacle() {
	}

	public CarditReceptacle(CarditPK carditPK, CarditReceptacleVO carditReceptacleVO) {
		populatePK(carditPK, carditReceptacleVO);
		populateAttributes(carditReceptacleVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getMessage(), createException.getMessage(), createException);
		}
	}

	/** 
	* Populates the PK fields
	* @param carditPK
	* @param carditReceptacleVO
	*/
	public void populatePK(CarditPK carditPK, CarditReceptacleVO carditReceptacleVO) {
		this.setCompanyCode(carditPK.getCompanyCode());
		this.setCarditKey(carditPK.getCarditKey());
		this.setReceptacleId(carditReceptacleVO.getReceptacleId());
	}

	/** 
	* @param carditReceptacleVO
	*/
	private void populateAttributes(CarditReceptacleVO carditReceptacleVO) {
		setCodeListResponsibleAgency(carditReceptacleVO.getCodeListResponsibleAgency());
		setDangerousGoodsIndicator(carditReceptacleVO.getDangerousGoodsIndicator());
		setDespatchIdentification(carditReceptacleVO.getDespatchIdentification());
		setDestinationExchangeOffice(carditReceptacleVO.getDestinationExchangeOffice());
		setDocumentOrMessageNameCode(carditReceptacleVO.getDocumentOrMessageNameCode());
		setHandlingClass(carditReceptacleVO.getHandlingClass());
		setHighestNumberReceptacleIndicator(carditReceptacleVO.getHighestNumberReceptacleIndicator());
		setLastDigitOfYear(carditReceptacleVO.getLastDigitOfYear());
		setMailCategoryCode(carditReceptacleVO.getMailCategoryCode());
		setMailSubClassCode(carditReceptacleVO.getMailSubClassCode());
		setMeasurementApplicationQualifier(carditReceptacleVO.getMeasurementApplicationQualifier());
		setMeasureUnitQualifier(carditReceptacleVO.getMeasureUnitQualifier());
		setOriginExchangeOffice(carditReceptacleVO.getOriginExchangeOffice());
		setReceptacleSerialNumber(carditReceptacleVO.getReceptacleSerialNumber());
		setDespatchNumber(carditReceptacleVO.getDespatchNumber());
		setReceptacleType(carditReceptacleVO.getReceptacleType());
		setReceptacleWeight(carditReceptacleVO.getReceptacleWeight().getRoundedDisplayValue().doubleValue());
		setReceptacleWeightType(carditReceptacleVO.getReceptacleWeightType());
		setReferenceQualifier(carditReceptacleVO.getReferenceQualifier());
		setRegdOrInsuredIndicator(carditReceptacleVO.getRegdOrInsuredIndicator());
		setCarditType(carditReceptacleVO.getCarditType());
		if (carditReceptacleVO.getHandoverTime() != null) {
			setHandoverTime(carditReceptacleVO.getHandoverTime());
		}
	}

	/** 
	* @author A-3227
	* @param carditRcpPK
	* @return
	* @throws FinderException
	* @throws SystemException
	*/
	public static CarditReceptacle find(CarditReceptaclePK carditRcpPK) throws FinderException {
		return PersistenceController.getEntityManager().find(CarditReceptacle.class, carditRcpPK);
	}

	/** 
	* @author A-3227
	* @throws SystemException
	*/
	public void remove() {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getMessage(), removeException.getMessage(), removeException);
		}
	}

	/** 
	* @author A-2553
	* @return
	* @throws SystemException
	*/
	private static MailOperationsDAO constructDAO() {
		try {
			return MailOperationsDAO.class
					.cast(PersistenceController.getEntityManager().getQueryDAO(MAIL_OPERATIONS));
		} catch (PersistenceException exception) {
			throw new SystemException("Query DAO not found", exception.getMessage(), exception);
		}
	}

	/** 
	* @author a-2553
	* @param carditEnquiryFilterVO
	* @param pageNumber
	* @return
	* @throws SystemException
	*/
	public static Page<MailbagVO> findCarditMails(CarditEnquiryFilterVO carditEnquiryFilterVO, int pageNumber) {
		try {
			return constructDAO().findCarditMails(carditEnquiryFilterVO, pageNumber);
		} catch (PersistenceException exception) {
			throw new SystemException("findcarditmails");
		}
	}

	/** 
	* @author 
	* @param mailbagID
	* @return
	* @throws SystemException
	*/
	public static CarditReceptacleVO findDuplicateMailbagsInCardit(String companyCode, String mailbagID) {
		try {
			return constructDAO().findDuplicateMailbagsInCardit(companyCode, mailbagID);
		} catch (PersistenceException exception) {
			throw new SystemException("findDuplicateMailbagsInCardit");
		}
	}

	public void setHandoverTime(ZonedDateTime handoverTime) {
		if (Objects.nonNull(handoverTime)) {
			this.handoverTime = handoverTime.toLocalDateTime();
		}
	}
	public CarditReceptaclePK createCarditReceptaclePK(CarditPK carditPK, CarditReceptacleVO carditReceptacleVO) {
		CarditReceptaclePK carditReceptaclePK = new CarditReceptaclePK();
		carditReceptaclePK.setCompanyCode(carditPK.getCompanyCode());
		carditReceptaclePK.setCarditKey(carditPK.getCarditKey());
		carditReceptaclePK.setReceptacleId(carditReceptacleVO.getReceptacleId());
		return carditReceptaclePK;
	}
}
