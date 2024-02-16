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
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@Table(name = "MALMRABLGPARMST")
@Entity
@Staleable
public class BillingParamterMaster {
	private static final Log LOGGER = LogFactory.getLogger("MRA BILLING PARAMETER MASTER");
	private BillingParamterMasterPK billingParamterMasterPK;
	private String parameterDescription;
	private String excludeFlag;
	private String lastUpdatedUser;
	private Calendar lastUpdatedTime;
	private String parameterValue;

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "functionPoint", column = @Column(name = "FUNPNT")),
			@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM")),
			@AttributeOverride(name = "functionName", column = @Column(name = "FUNNAM")),
			@AttributeOverride(name = "parameterCode", column = @Column(name = "PARCOD")),

	})
	public BillingParamterMasterPK getBillingParamterMasterPK() {
		return billingParamterMasterPK;
	}

	public void setBillingParamterMasterPK(BillingParamterMasterPK billingParamterMasterPK) {
		this.billingParamterMasterPK = billingParamterMasterPK;
	}
	@Column(name = "PARDES")
	public String getParameterDescription() {
		return parameterDescription;
	}

	public void setParameterDescription(String parameterDescription) {
		this.parameterDescription = parameterDescription;
	}

	@Column(name = "EXCINCFLG")
	public String getExcludeFlag() {
		return excludeFlag;
	}

	public void setExcludeFlag(String excludeFlag) {
		this.excludeFlag = excludeFlag;
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
	

	@Column(name = "PARVAL")
	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	public static BillingParamterMaster find(BillingParamterMasterPK billingParamterMasterPK)
			throws SystemException, FinderException {
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(BillingParamterMaster.class, billingParamterMasterPK);
		
	}

	public BillingParamterMaster(BillingParameterVO billingParameterVO) throws SystemException {
		populatePK(billingParameterVO);
		populateAttributes(billingParameterVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode(),
					createException);
		}
		
	}
	
	public BillingParamterMaster() {
	}

	
	private void populateAttributes(BillingParameterVO billingParameterVO) {

		this.lastUpdatedTime = billingParameterVO.getLastUpdatedTime();
		this.lastUpdatedUser = billingParameterVO.getLastUpdatedUser();
		this.parameterValue = billingParameterVO.getParameterValue();
		this.parameterDescription = billingParameterVO.getParameterDescription();
		this.excludeFlag = billingParameterVO.getExcludeFlag();
	}

	private void populatePK(BillingParameterVO billingParameterVO) {
		billingParamterMasterPK = new BillingParamterMasterPK();
		billingParamterMasterPK.setCompanyCode(billingParameterVO.getCompanyCode());
		billingParamterMasterPK.setSerialNumber(billingParameterVO.getSerialNumber());
		billingParamterMasterPK.setFunctionPoint(billingParameterVO.getFunctionPoint());
		billingParamterMasterPK.setFunctionName(billingParameterVO.getFunctionName());
		billingParamterMasterPK.setParameterCode(billingParameterVO.getParamterCode());
	}
	public void remove() throws SystemException {
		LOGGER.entering("BillingSchedule", "REMOVE METHOD CALLED");
		try {
			
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode(),
					removeException);
		}
		LOGGER.exiting("CONTAINER", "REMOVE");
	}
	

}
