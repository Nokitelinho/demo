package com.ibsplc.icargo.business.mail.mra.defaults;
/**
 * @author A-9498
 *
 */
import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingParameterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingScheduleDetailsVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@Table(name = "MALMRABLGSCHMST")
@Entity
@Staleable
public class BillingScheduleMaster {
	private static final Log LOGGER = LogFactory.getLogger("MRA BILLING SCHEDULE MASTER");
	private BillingScheduleMasterPK billingScheduleMasterPK;
	private Calendar billingPeriodFromDate;
	private Calendar billingPeriodToDate;
	private Calendar gpaInvoiceGenarateDate;
	private Calendar passFileGenerateDate;
	private Calendar masterCutDataDate;
	private Calendar airLineUploadCutDate;
	private Calendar invoiceAvailableDate;
	private Calendar postalOperatorUploadDate;
	private String billingPeriod;
	private String lastUpdatedUser;
	private Calendar lastUpdatedTime;

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "periodNumber", column = @Column(name = "PRDNUM")),
			@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM")),
			@AttributeOverride(name = "billingType", column = @Column(name = "BLGTYP"))

	})
	public BillingScheduleMasterPK getBillingScheduleMasterPK() {
		return billingScheduleMasterPK;
	}

	public void setBillingScheduleMasterPK(BillingScheduleMasterPK billingScheduleMasterPK) {
		this.billingScheduleMasterPK = billingScheduleMasterPK;
	}

	@Column(name = "BLGPRDFRM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getBillingPeriodFromDate() {
		return billingPeriodFromDate;
	}

	public void setBillingPeriodFromDate(Calendar billingPeriodFromDate) {
		this.billingPeriodFromDate = billingPeriodFromDate;
	}

	@Column(name = "BLGPRDTOO")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getBillingPeriodToDate() {
		return billingPeriodToDate;
	}

	public void setBillingPeriodToDate(Calendar billingPeriodToDate) {
		this.billingPeriodToDate = billingPeriodToDate;
	}

	@Column(name = "GPAINVGEN")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getGpaInvoiceGenarateDate() {
		return gpaInvoiceGenarateDate;
	}

	public void setGpaInvoiceGenarateDate(Calendar gpaInvoiceGenarateDate) {
		this.gpaInvoiceGenarateDate = gpaInvoiceGenarateDate;
	}

	@Column(name = "PASFILGEN")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getPassFileGenerateDate() {
		return passFileGenerateDate;
	}

	public void setPassFileGenerateDate(Calendar passFileGenerateDate) {
		this.passFileGenerateDate = passFileGenerateDate;
	}

	@Column(name = "MSTCUTOFF")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getMasterCutDataDate() {
		return masterCutDataDate;
	}

	public void setMasterCutDataDate(Calendar masterCutDataDate) {
		this.masterCutDataDate = masterCutDataDate;
	}

	@Column(name = "ARLCUTOFF")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getAirLineUploadCutDate() {
		return airLineUploadCutDate;
	}

	public void setAirLineUploadCutDate(Calendar airLineUploadCutDate) {
		this.airLineUploadCutDate = airLineUploadCutDate;
	}

	@Column(name = "PASINVAVL")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getInvoiceAvailableDate() {
		return invoiceAvailableDate;
	}

	public void setInvoiceAvailableDate(Calendar invoiceAvailableDate) {
		this.invoiceAvailableDate = invoiceAvailableDate;
	}

	@Column(name = "POARCNUPL")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getPostalOperatorUploadDate() {
		return postalOperatorUploadDate;
	}

	public void setPostalOperatorUploadDate(Calendar postalOperatorUploadDate) {
		this.postalOperatorUploadDate = postalOperatorUploadDate;
	}

	@Column(name = "BLGPRD")
	public String getBillingPeriod() {
		return billingPeriod;
	}

	public void setBillingPeriod(String billingPeriod) {
		this.billingPeriod = billingPeriod;
	}

	@Column(name = "LSTUPDUSR")
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	public static Log getLog() {
		return LOGGER;
	}

	public static BillingScheduleMaster find(BillingScheduleMasterPK billingScheduleMasterPK)
			throws SystemException, FinderException {
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(BillingScheduleMaster.class, billingScheduleMasterPK);
	}

	public BillingScheduleMaster() {
	}

	public BillingScheduleMaster(BillingScheduleDetailsVO billingScheduleDetailsVO) throws SystemException {
		populatePK(billingScheduleDetailsVO);
		populateAttributes(billingScheduleDetailsVO);
		if (billingScheduleDetailsVO.getOpearationFlag() != null
				&& billingScheduleDetailsVO.getOpearationFlag().equals("I")) {
			try {
				PersistenceController.getEntityManager().persist(this);
			} catch (CreateException createException) { 
				throw new SystemException(createException.getErrorCode(),
						createException);
			}
			populateChildren(billingScheduleDetailsVO);
		}
	}

	private void populateAttributes(BillingScheduleDetailsVO billingScheduleDetailsVO) {
		BillingScheduleMasterPK billingScheduleMasterPk = new BillingScheduleMasterPK();
		billingScheduleMasterPk.setCompanyCode(billingScheduleDetailsVO.getCompanyCode());
		billingScheduleMasterPk.setPeriodNumber(billingScheduleDetailsVO.getPeriodNumber());
		billingScheduleMasterPk.setBillingType(billingScheduleDetailsVO.getBillingType());
		setBillingScheduleMasterPK(billingScheduleMasterPk);
		setAirLineUploadCutDate(billingScheduleDetailsVO.getAirLineUploadCutDate());
		setGpaInvoiceGenarateDate(billingScheduleDetailsVO.getGpaInvoiceGenarateDate());
		this.billingPeriod = billingScheduleDetailsVO.getBillingPeriod();
		this.billingPeriodFromDate = billingScheduleDetailsVO.getBillingPeriodFromDate();
		this.billingPeriodToDate = billingScheduleDetailsVO.getBillingPeriodToDate();
		this.gpaInvoiceGenarateDate = billingScheduleDetailsVO.getGpaInvoiceGenarateDate();
		this.invoiceAvailableDate = billingScheduleDetailsVO.getInvoiceAvailableDate();
		this.masterCutDataDate = billingScheduleDetailsVO.getMasterCutDataDate();
		this.lastUpdatedTime = billingScheduleDetailsVO.getLastUpdatedTime();
		this.lastUpdatedUser = billingScheduleDetailsVO.getLastUpdatedUser();
		this.passFileGenerateDate = billingScheduleDetailsVO.getPassFileGenerateDate();
		this.postalOperatorUploadDate = billingScheduleDetailsVO.getPostalOperatorUploadDate();
	}

	private void populatePK(BillingScheduleDetailsVO billingScheduleDetailsVO) {
		billingScheduleMasterPK = new BillingScheduleMasterPK();
		billingScheduleMasterPK.setCompanyCode(billingScheduleDetailsVO.getCompanyCode());
		billingScheduleMasterPK.setPeriodNumber(billingScheduleDetailsVO.getPeriodNumber());
		billingScheduleMasterPK.setBillingType(billingScheduleDetailsVO.getBillingType());
	}

	private void populateChildren(BillingScheduleDetailsVO billingScheduleDetailsVO) throws SystemException {
		billingScheduleMasterPK = getBillingScheduleMasterPK();
		BillingParameterVO billingParameterVO = new BillingParameterVO();
		if (billingScheduleDetailsVO.getParamsList() != null) {
			billingParameterVO.setFunctionName(billingScheduleDetailsVO.getPeriodNumber());
			billingParameterVO.setFunctionPoint("BLGSCH");
			billingParameterVO.setLastUpdatedTime(billingScheduleDetailsVO.getLastUpdatedTime());
			billingParameterVO.setLastUpdatedUser(billingScheduleDetailsVO.getLastUpdatedUser());
			billingParameterVO.setCompanyCode(billingScheduleDetailsVO.getCompanyCode());
			billingParameterVO.setSerialNumber(billingScheduleMasterPK.getSerialNumber());
			for (BillingParameterVO params : billingScheduleDetailsVO.getParamsList()) {
				billingParameterVO.setParameterDescription(params.getParameterDescription());
				billingParameterVO.setExcludeFlag(params.getExcludeFlag());
				billingParameterVO.setParameterValue(params.getParameterValue());
				billingParameterVO.setParamterCode(params.getParamterCode());
				new BillingParamterMaster(billingParameterVO);
			}
		}
	}

	public void remove(BillingScheduleDetailsVO billingScheduleDetailsVO) throws SystemException {
		LOGGER.entering("BillingSchedule", "REMOVE METHOD CALLED");
		try {
			removeChildren(billingScheduleDetailsVO);
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode(), removeException);
		}
		LOGGER.exiting("CONTAINER", "REMOVE");
	}

	private void removeChildren(BillingScheduleDetailsVO billingScheduleDetailsVO) throws SystemException {
		LOGGER.log(Log.INFO, "INSIDE THE REMOVE METHOD FOR THE CHILDREN");
		if (billingScheduleDetailsVO.getParamsList() != null && !(billingScheduleDetailsVO.getParamsList().isEmpty())) {
			
			for (BillingParameterVO params : billingScheduleDetailsVO.getParamsList()) {
				BillingParamterMaster billingParamterMaster = null;
				BillingParamterMasterPK billingParamterMasterPK = new BillingParamterMasterPK();
				billingParamterMasterPK.setFunctionName(billingScheduleDetailsVO.getPeriodNumber());
				billingParamterMasterPK.setFunctionPoint("BLGSCH");
				billingParamterMasterPK.setParameterCode(params.getParamterCode());
				billingParamterMasterPK.setCompanyCode(billingScheduleDetailsVO.getCompanyCode());
				billingParamterMasterPK.setSerialNumber((long) billingScheduleDetailsVO.getSerialNumber());
				try {
					billingParamterMaster = BillingParamterMaster.find(billingParamterMasterPK);
				} catch (FinderException e) {
					LOGGER.log(Log.FINE, e.getMessage(), e);
				}
				if (billingParamterMaster != null) {
					billingParamterMaster.remove();
				}

			}

			LOGGER.log(Log.INFO, "CHILD REMOVED");
		}

	}
}
