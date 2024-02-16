package com.ibsplc.neoicargo.mailmasters.component;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.*;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceFilterVO;
import com.ibsplc.neoicargo.mailmasters.vo.PostalAdministrationDetailsVO;
import com.ibsplc.neoicargo.mailmasters.vo.PostalAdministrationVO;
import com.ibsplc.neoicargo.mailmasters.dao.MailMastersDAO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author a-3109
 */
@Setter
@Getter
@Slf4j
@Entity
@IdClass(PostalAdministrationPK.class)
@Table(name = "MALPOAMST")
public class PostalAdministration  extends BaseEntity implements Serializable {
	private static final String MODULE_NAME = "mail.masters";

	@Id
	@Column(name = "POACOD")
	private String paCode;
	/** 
	* paName
	*/
	@Column(name = "POANAM")
	private String paName;
	/** 
	* address
	*/
	@Column(name = "POAADR")
	private String address;
	/** 
	* country Code
	*/
	@Column(name = "CNTCOD")
	private String countryCode;
	/** 
	* partialResdit
	*/
	@Column(name = "PRTRDT")
	private String partialResdit;
	/** 
	* msgEventLocationNeeded
	*/
	@Column(name = "MSGEVTLOC")
	private String msgEventLocationNeeded;
	/** 
	* settlementCurrencyCode
	*/
	@Column(name = "STLCURCOD")
	private String settlementCurrencyCode;
	/** 
	* baseType
	*/
	@Column(name = "BASTYP")
	private String baseType;
	/** 
	* aiport code
	*/
	@Column(name = "BLGSRC")
	private String billingSource;
	/** 
	* billingFrequency
	*/
	@Column(name = "BLGFRQ")
	private String billingFrequency;
	/** 
	* conPerson
	*/
	@Column(name = "CONPSN")
	private String conPerson;
	/** 
	* state
	*/
	@Column(name = "STANAM")
	private String state;
	/** 
	* country
	*/
	@Column(name = "CNTNAM")
	private String country;
	/** 
	* mobile
	*/
	@Column(name = "MOBNUM")
	private String mobile;
	/** 
	* postCod
	*/
	@Column(name = "POSCOD")
	private String postCod;
	/** 
	* phone1
	*/
	@Column(name = "PHNONE")
	private String phone1;
	/** 
	* phone2
	*/
	@Column(name = "PHNTWO")
	private String phone2;
	/** 
	* fax
	*/
	@Column(name = "FAX")
	private String fax;
	/** 
	* email
	*/
	@Column(name = "EMLADR")
	private String email;
	/** 
	* city
	*/
	@Column(name = "CTYNAM")
	private String city;
	/** 
	* remarks
	*/
	@Column(name = "RMK")
	private String remarks;
	/** 
	* debInvCode
	*/
	@Column(name = "DBTINVCOD")
	private String debInvCode;
	/** 
	* status
	*/
	@Column(name = "ACTFLG")
	private String status;
	/** 
	* residtVersion
	*/
	@Column(name = "RDTVERNUM")
	private String residtVersion;
	/** 
	* account number
	*/
	@Column(name = "ACCNUM")
	private String accNum;
	/** 
	* vat Number
	*/
	@Column(name = "VATNUM")
	private String vatNumber;
	/** 
	* autoEmailReqd
	*/
	@Column(name = "AUTEMLREQ")
	private String autoEmailReqd;
	/** 
	* dueInDays
	*/
	@Column(name = "DUEDAY")
	private int dueInDays;
	/** 
	* isM39Compliant
	*/
	@Column(name = "M39ENBFLG")
	private String isM39Compliant;
	/** 
	* gibCustomerFlag
	*/
	@Column(name = "GIBFLG")
	private String gibCustomerFlag;
	@Column(name = "PROINVREQ")
	private String proformaInvoiceRequired;
	@Column(name = "RDTSNDPRD")
	private int resditTriggerPeriod;
	@Column(name = "LATVALLVL")
	private String latValLevel;
	@Column(name = "STLLVL")
	private String settlementLevel;
	@Column(name = "STLTOLPER")
	private double tolerancePercent;
	@Column(name = "STLTOLVAL")
	private double toleranceValue;
	@Column(name = "STLTOLMAXVAL")
	private double maxValue;
	@Column(name = "DUPMALPRD")
	private int dupMailbagPeriod;
	@Column(name = "SECEMLADRONE")
	private String secondaryEmail1;
	@Column(name = "SECEMLADRTWO")
	private String secondaryEmail2;
	/**
	* Is MessagingEnabled For PA
	*/
	@Column(name = "MSGENBFLG")
	private String messagingEnabled;

	/** 
	* Empty Constructor
	*/
	public PostalAdministration() {
	}

	/** 
	* @param postalAdministrationVO
	* @throws SystemException
	* @throws FinderException
	*/
	public PostalAdministration(PostalAdministrationVO postalAdministrationVO) {
		populatePK(postalAdministrationVO);
		populateAttribute(postalAdministrationVO);
		EntityManager em = PersistenceController.getEntityManager();
		try {
			em.persist(this);
		} catch (CreateException createException) {
			createException.getErrorCode();
			throw new SystemException(createException.getErrorCode());
		}
		savePostalAdministration(postalAdministrationVO);
	}

	/** 
	* @param postalAdministrationVO
	*/
	private void populatePK(PostalAdministrationVO postalAdministrationVO) {
		this.setCompanyCode(postalAdministrationVO.getCompanyCode());
		this.setPaCode(postalAdministrationVO.getPaCode());
	}

	/** 
	* @param postalAdministrationVO
	*/
	private void populateAttribute(PostalAdministrationVO postalAdministrationVO) {
		this.setAddress(postalAdministrationVO.getAddress());
		this.setCountryCode(postalAdministrationVO.getCountryCode());
		this.setPaName(postalAdministrationVO.getPaName());
		if (postalAdministrationVO.getMessagingEnabled() != null) {
			this.setMessagingEnabled(postalAdministrationVO.getMessagingEnabled());
		} else {
			this.setMessagingEnabled(MailConstantsVO.FLAG_NO);
		}
		if (postalAdministrationVO.getProformaInvoiceRequired() != null) {
			this.setProformaInvoiceRequired(postalAdministrationVO.getProformaInvoiceRequired());
		}
		this.setPartialResdit(postalAdministrationVO.isPartialResdit() ? PostalAdministrationVO.FLAG_YES
				: PostalAdministrationVO.FLAG_NO);
		this.setMsgEventLocationNeeded(
				postalAdministrationVO.isMsgEventLocationNeeded() ? PostalAdministrationVO.FLAG_YES
						: PostalAdministrationVO.FLAG_NO);
		if (postalAdministrationVO.getBaseType() != null) {
			this.setBaseType(postalAdministrationVO.getBaseType());
		} else {
			this.setBaseType(PostalAdministrationVO.FLAG_NO);
		}
		this.setLastUpdatedUser(postalAdministrationVO.getLastUpdateUser());
		this.setConPerson(postalAdministrationVO.getConPerson());
		this.setCity(postalAdministrationVO.getCity());
		this.setState(postalAdministrationVO.getState());
		this.setCountry(postalAdministrationVO.getCountry());
		this.setMobile(postalAdministrationVO.getMobile());
		this.setPostCod(postalAdministrationVO.getPostCod());
		this.setPhone1(postalAdministrationVO.getPhone1());
		this.setPhone2(postalAdministrationVO.getPhone2());
		this.setFax(postalAdministrationVO.getFax());
		this.setEmail(postalAdministrationVO.getEmail());
		this.setRemarks(postalAdministrationVO.getRemarks());
		if (postalAdministrationVO.getStatus() != null) {
			this.setStatus(postalAdministrationVO.getStatus());
		} else {
			this.setStatus("NEW");
		}
		this.setDebInvCode(postalAdministrationVO.getDebInvCode());
		if (postalAdministrationVO.getGibCustomerFlag() != null) {
			this.setGibCustomerFlag(postalAdministrationVO.getGibCustomerFlag());
		} else {
			this.setGibCustomerFlag(PostalAdministrationVO.FLAG_NO);
		}
		this.setAccNum(postalAdministrationVO.getAccNum());
		this.setResidtVersion(postalAdministrationVO.getResidtversion());
		this.setLatValLevel(postalAdministrationVO.getLatValLevel());
		this.setVatNumber(postalAdministrationVO.getVatNumber());
		if (postalAdministrationVO.getAutoEmailReqd() != null) {
			this.setAutoEmailReqd(postalAdministrationVO.getAutoEmailReqd());
		} else {
			this.setAutoEmailReqd(PostalAdministrationVO.FLAG_NO);
		}
		this.setDueInDays(postalAdministrationVO.getDueInDays());
		if ("1.1".equalsIgnoreCase(postalAdministrationVO.getResidtversion())
				|| MailConstantsVO.M49_1_1.equals(postalAdministrationVO.getResidtversion())) {
			this.setIsM39Compliant(PostalAdministrationVO.FLAG_YES);
		} else {
			this.setIsM39Compliant(PostalAdministrationVO.FLAG_NO);
		}
		this.setResditTriggerPeriod(postalAdministrationVO.getResditTriggerPeriod());
		this.setSettlementLevel(postalAdministrationVO.getSettlementLevel());
		this.setTolerancePercent(postalAdministrationVO.getTolerancePercent());
		this.setToleranceValue(postalAdministrationVO.getToleranceValue());
		this.setMaxValue(postalAdministrationVO.getMaxValue());
		this.dupMailbagPeriod = postalAdministrationVO.getDupMailbagPeriod();
		this.setSecondaryEmail1(postalAdministrationVO.getSecondaryEmail1());
		this.setSecondaryEmail2(postalAdministrationVO.getSecondaryEmail2());
	}

	/** 
	* @param postalAdministrationVO
	* @throws SystemException
	*/
	public void savePostalAdministration(PostalAdministrationVO postalAdministrationVO) {
		Collection<PostalAdministrationDetailsVO> postalAdministrationDetailsVOs = postalAdministrationVO
				.getPaDetails();
		if (postalAdministrationDetailsVOs != null && postalAdministrationDetailsVOs.size() > 0) {
			for (PostalAdministrationDetailsVO postalAdminVO : postalAdministrationDetailsVOs) {
				new PostalAdministrationDetails(postalAdminVO);
			}
		}
	}

	/** 
	* @param companyCode
	* @param paCode
	* @return
	* @throws SystemException
	* @throws FinderException
	*/
	public static PostalAdministration find(String companyCode, String paCode) throws FinderException {
		PostalAdministrationPK postalAdministrationPk = new PostalAdministrationPK();
		postalAdministrationPk.setCompanyCode(companyCode);
		postalAdministrationPk.setPaCode(paCode);
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(PostalAdministration.class, postalAdministrationPk);
	}

	/** 
	* @throws SystemException
	* @throws RemoveException
	*/
	public void remove() throws RemoveException {

		PersistenceController.getEntityManager().remove(this);
		log.info("BEFORE flush for PostalAdministrationCache  DELETE");
		//TODO: Neo to correct Cashe
//		cache.invalidateForGroup(PostalAdministrationCache.POSTALADMINISTRATION_CACHE_GROUP);
//		cache.invalidateForGroup(PostalAdministrationCache.POSTALADMINISTRATIONPARTYIDENTIFIER_CACHE_GROUP);
	}

	/** 
	* @param postalAdministrationVO
	* @throws RemoveException
	* @throws SystemException
	* @throws FinderException
	*/
	public void update(PostalAdministrationVO postalAdministrationVO) throws FinderException {

		populateAttribute(postalAdministrationVO);
		if(Objects.nonNull(postalAdministrationVO.getLastUpdateTime())) {
			this.setLastUpdatedTime(Timestamp.valueOf(postalAdministrationVO.getLastUpdateTime().toLocalDateTime()));
		}
		updatePAdetails(postalAdministrationVO.getPaDetails());
		log.info("BEFORE flush for PostalAdministrationCache  UPDATE");
	}

	private void updatePAdetails(Collection<PostalAdministrationDetailsVO> paDetailsVos) throws FinderException {
		if (paDetailsVos != null) {
			for (PostalAdministrationDetailsVO paDetailsVO : paDetailsVos) {
				if (PostalAdministrationDetailsVO.OPERATION_FLAG_DELETE.equals(paDetailsVO.getOperationFlag())) {
					PostalAdministrationDetails paDetails = PostalAdministrationDetails.find(
							paDetailsVO.getCompanyCode(), paDetailsVO.getPoaCode(), paDetailsVO.getParCode(),
							paDetailsVO.getSernum());
					if (paDetails != null) {
						try {
							paDetails.remove();
						} catch (RemoveException ex) {
							throw new SystemException(ex.getMessage(), ex.getMessage(), ex);
						}
					}
				}
			}
			for (PostalAdministrationDetailsVO paDetailsVO : paDetailsVos) {
				if (PostalAdministrationDetailsVO.OPERATION_FLAG_INSERT.equals(paDetailsVO.getOperationFlag())) {
					new PostalAdministrationDetails(paDetailsVO);
				}
				if (PostalAdministrationDetailsVO.OPERATION_FLAG_UPDATE.equals(paDetailsVO.getOperationFlag())) {
					PostalAdministrationDetails paDetails = PostalAdministrationDetails.find(
							paDetailsVO.getCompanyCode(), paDetailsVO.getPoaCode(), paDetailsVO.getParCode(),
							paDetailsVO.getSernum());
					paDetails.update(paDetailsVO);
				}
			}
		}
	}

	/** 
	* @return
	* @throws SystemException
	*/
	private static MailMastersDAO constructDAO() {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailMastersDAO.class.cast(em.getQueryDAO(MODULE_NAME));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/** 
	* @author A-2037 This method is used to find Local PAs
	* @param companyCode
	* @param countryCode
	* @return Collection<PostalAdministrationVO>
	* @throws SystemException
	*/
	public static Collection<PostalAdministrationVO> findLocalPAs(String companyCode, String countryCode) {
		try {
			return constructDAO().findLocalPAs(companyCode, countryCode);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/** 
	* @author A-2037 Method for PALov containing PACode and PADescription
	* @param companyCode
	* @param paCode
	* @param paName
	* @param pageNumber
	* @return
	* @throws SystemException
	*/
	public static Page<PostalAdministrationVO> findPALov(String companyCode, String paCode, String paName,
			int pageNumber, int defaultSize) {
		try {
			return constructDAO().findPALov(companyCode, paCode, paName, pageNumber, defaultSize);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/** 
	* @param companyCode
	* @param officeOfExchange
	* @return
	* @throws SystemException
	*/
	public static PostalAdministrationVO findPADetails(String companyCode, String officeOfExchange) {
		try {
			return constructDAO().findPADetails(companyCode, officeOfExchange);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage(), e);
		}
	}

	/** 
	* Method		:	PostalAdministration.findAllPACodes Added by 	:	A-4809 on 08-Jan-2014 Used for 	:	ICRD-42160 Parameters	:	@param generateInvoiceFilterVO Parameters	:	@return Parameters	:	@throws SystemException Return type	: 	Collection<PostalAdministrationVO>
	*/
	public static Collection<PostalAdministrationVO> findAllPACodes(GenerateInvoiceFilterVO generateInvoiceFilterVO) {
		try {
			return constructDAO().findAllPACodes(generateInvoiceFilterVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	public static PostalAdministrationVO findPACode(String companyCode,
													String paCode){
			return constructDAO().findPACode(companyCode, paCode);
	}
	public static String findUpuCodeNameForPA(String companyCode,
											  String paCode)
			throws SystemException, PersistenceException {
		return constructDAO().findUpuCodeNameForPA(companyCode, paCode);
	}
	public static String findPartyIdentifierForPA(String companyCode,
												  String paCode)
			throws SystemException {
		try {
			return constructDAO().findPartyIdentifierForPA(companyCode, paCode);
		} catch(PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}
}
