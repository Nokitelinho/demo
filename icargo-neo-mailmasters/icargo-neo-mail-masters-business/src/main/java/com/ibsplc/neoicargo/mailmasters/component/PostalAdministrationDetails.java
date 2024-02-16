package com.ibsplc.neoicargo.mailmasters.component;

import javax.persistence.*;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;

import java.io.Serializable;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mailmasters.vo.PostalAdministrationDetailsVO;
import com.ibsplc.neoicargo.mailmasters.dao.MailMastersDAO;
import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.Setter;

/** 
 * @author A-3109
 */
@Setter
@Getter
@Entity
@IdClass(PostalAdministrationDetailsPK.class)
//Removed as not required
//@SequenceGenerator(name = "MALPOADTLSEQ", initialValue = 1, allocationSize = 1, sequenceName = "MALPOADTL_seq")
@Table(name = "MALPOADTL")
public class PostalAdministrationDetails extends BaseEntity implements Serializable {
	private static final String MODULE_NAME = "mail.masters";

	@Id
	@Column(name = "POACOD")
	private String poacod;
	@Id
	@Column(name = "PARCOD")
	private String parcod;
	@Id
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MALPOADTLSEQ")
	@Column(name = "SERNUM")
	private String sernum;
	/** 
	* billingSource
	*/
	@Column(name = "BLGSRC")
	private String billingSource;
	/** 
	* billingFrequency
	*/
	@Column(name = "BLGFRQ")
	private String billingFrequency;
	/** 
	* settlementCurrencyCode
	*/
	@Column(name = "STLCUR")
	private String settlementCurrencyCode;
	/** 
	* validFrom
	*/
	@Column(name = "VLDFRM")
	private ZonedDateTime validFrom;
	/** 
	* validTo
	*/
	@Column(name = "VLDTOO")
	private ZonedDateTime validTo;
	/** 
	* parameterType
	*/
	@Column(name = "PARTYP")
	private String parameterType;
	/** 
	* parameterValue
	*/
	@Column(name = "PARVAL")
	private String parameterValue;
	/** 
	* partyIdentifier
	*/
	@Column(name = "PTYIDR")
	private String partyIdentifier;
	/** 
	* detailedRemarks
	*/
	@Column(name = "DTLRMK")
	private String detailedRemarks;

	public PostalAdministrationDetails() {
	}

	public PostalAdministrationDetails(PostalAdministrationDetailsVO postalAdministrationDetailsVO) {
		populatePK(postalAdministrationDetailsVO);
		populateAttributes(postalAdministrationDetailsVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}
	}

	/** 
	* This method is used to find the Instance of the Entity
	* @param companyCode
	* @param
	* @param
	* @param
	* @return
	* @throws SystemException
	* @throws FinderException
	*/
	public static PostalAdministrationDetails find(String companyCode, String poaCode, String parcode, String sernum)
			throws FinderException {
		PostalAdministrationDetailsPK postalAdminDetailsPK = new PostalAdministrationDetailsPK();
		postalAdminDetailsPK.setCompanyCode(companyCode);
		postalAdminDetailsPK.setPoacod(poaCode);
		postalAdminDetailsPK.setParcod(parcode);
		postalAdminDetailsPK.setSernum(sernum);
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(PostalAdministrationDetails.class, postalAdminDetailsPK);
	}

	/** 
	* @throws SystemException
	* @throws RemoveException
	*/
	public void remove() throws RemoveException {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException ex) {
			throw ex;
		}
	}


	public void update(PostalAdministrationDetailsVO postalAdministrationDetailsVO) {
		populateAttributes(postalAdministrationDetailsVO);
	}


	private void populateAttributes(PostalAdministrationDetailsVO postalAdministrationDetailsVO) {
		this.billingSource = postalAdministrationDetailsVO.getBillingSource();
		this.billingFrequency = postalAdministrationDetailsVO.getBillingFrequency();
		this.settlementCurrencyCode = postalAdministrationDetailsVO.getSettlementCurrencyCode();
		if (postalAdministrationDetailsVO.getValidFrom() != null) {
			setValidFrom(postalAdministrationDetailsVO.getValidFrom());
		}
		if (postalAdministrationDetailsVO.getValidTo() != null) {
			setValidTo(postalAdministrationDetailsVO.getValidTo());
		}
		this.parameterType = postalAdministrationDetailsVO.getParameterType();
		this.parameterValue = postalAdministrationDetailsVO.getParameterValue();
		this.detailedRemarks = postalAdministrationDetailsVO.getDetailedRemarks();
		this.partyIdentifier = postalAdministrationDetailsVO.getPartyIdentifier();
	}


	private void populatePK(PostalAdministrationDetailsVO postalAdministrationDetailsVO) {
		this.setCompanyCode(postalAdministrationDetailsVO.getCompanyCode());
		this.setPoacod(postalAdministrationDetailsVO.getPoaCode());
		this.setParcod(postalAdministrationDetailsVO.getParCode());
		this.setSernum(postalAdministrationDetailsVO.getSernum());
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
	* @author A-3251
	* @param postalAdministrationDetailsVO
	* @throws SystemException
	*/
	public static PostalAdministrationDetailsVO validatePoaDetails(
			PostalAdministrationDetailsVO postalAdministrationDetailsVO) {
		try {
			return constructDAO().validatePoaDetails(postalAdministrationDetailsVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

}
