package com.ibsplc.neoicargo.mailmasters.component;
;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.neoicargo.mailmasters.dao.MailMastersDAO;
import com.ibsplc.neoicargo.mailmasters.vo.*;
import com.ibsplc.xibase.server.framework.persistence.*;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/** 
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.MailBoxId.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-4809	:	Aug 5, 2016	:	Draft
 */
@Setter
@Getter
@Entity
@IdClass(MLDConfigurationPK.class)
@Table(name = "MALMLDCFG")
public class MLDConfiguration extends BaseEntity implements Serializable {

	private static final String MODULE_NAME = "mail.masters";

	@Id
	@Column(name = "ARPCOD")
	private String airportCode;

	@Id
	@Column(name = "CARIDR")
	private int carrierIdentifier;

	@Column(name = "ALLREQFLG")
	private String allocationRequired;
	@Column(name = "RECREQFLG")
	private String receivedRequired;
	@Column(name = "UPLREQFLG")
	private String upliftedRequired;
	@Column(name = "HNDREQFLG")
	private String hndRequired;
	@Column(name = "DLVREQFLG")
	private String deliveredRequired;
	@Column(name = "MLDVER")
	private String mldversion;
	@Column(name = "STGREQFLG")
	private String stagedRequired;
	@Column(name = "NSTREQFLG")
	private String nestedRequired;
	@Column(name = "RCFREQFLG")
	private String receivedFromFightRequired;
	@Column(name = "TFDREQFLG")
	private String transferredFromOALRequired;
	@Column(name = "RCTREQFLG")
	private String receivedFromOALRequired;
	@Column(name = "RETREQFLG")
	private String returnedRequired;


	/**
	* Empty Constructor
	*/
	public MLDConfiguration() {
	}

	public MLDConfiguration(MLDConfigurationVO mLDConfigurationVO)
			throws SystemException {

		populatePK(mLDConfigurationVO);
		populateAttributes(mLDConfigurationVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}

	}

	private void populateAttributes(MLDConfigurationVO mLDConfigurationVO) {
		//Removed setCarrierCode as part of bug ICRD-143797
		setAllocationRequired(mLDConfigurationVO.getAllocatedRequired());
		setDeliveredRequired(mLDConfigurationVO.getDeliveredRequired());
		setHndRequired(mLDConfigurationVO.gethNDRequired());
		setReceivedRequired(mLDConfigurationVO.getReceivedRequired());
		setUpliftedRequired(mLDConfigurationVO.getUpliftedRequired());
		//Added for CRQ ICRD-135130 by A-8061 starts
		setMldversion(mLDConfigurationVO.getMldversion());
		setStagedRequired(mLDConfigurationVO.getStagedRequired());
		setNestedRequired(mLDConfigurationVO.getNestedRequired());
		setReceivedFromFightRequired(mLDConfigurationVO.getReceivedFromFightRequired());
		setTransferredFromOALRequired(mLDConfigurationVO.getTransferredFromOALRequired());
		setReceivedFromOALRequired(mLDConfigurationVO.getReceivedFromOALRequired());
		setReturnedRequired(mLDConfigurationVO.getReturnedRequired());

	}

	private void populatePK(MLDConfigurationVO mLDConfigurationVO) {

		this.setCompanyCode(mLDConfigurationVO.getCompanyCode());
		this.setAirportCode(mLDConfigurationVO.getAirportCode());
		this.setCarrierIdentifier(mLDConfigurationVO
				.getCarrierIdentifier());

	}

	public void remove() throws SystemException {

		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
	}
	public static MLDConfiguration find(MLDConfigurationPK mLDConfigurationPK)
			throws SystemException, FinderException {
		return PersistenceController.getEntityManager().find(
				MLDConfiguration.class, mLDConfigurationPK);

	}

	public static Collection<MLDConfigurationVO> findMLDCongfigurations(
			MLDConfigurationFilterVO mLDConfigurationFilterVO)
			throws SystemException {
			return constructDAO().findMLDCongfigurations(
					mLDConfigurationFilterVO);
	}

	private static MailMastersDAO constructDAO() {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailMastersDAO.class.cast(em.getQueryDAO(MODULE_NAME));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}


}
