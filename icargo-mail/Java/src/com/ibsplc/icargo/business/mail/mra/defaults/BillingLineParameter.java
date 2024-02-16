/*
 * BillingLineParameter.java Created on Feb 23, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineParameterVO;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.OptimisticConcurrencyException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;


/**
 * @author a-1556
 *
 */

@Entity
@Table(name = "MALMRABLGMTXLINPAR")
@Staleable


public class BillingLineParameter {

    private BillingLineParameterPK billingLineParameterPK;
    
    private String parameterValue;
    private String excludeFlag;
    
  //  private static   Log log =LogFactory.getLogger("MRA BILLING LINE PARAMETER");
    
    /**
     * 
     */
    public BillingLineParameter() { 

    }

    /**
     * @return Returns the billingLineParameterPK.
     */
    @EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="billingMatrixID", column=@Column(name="BLGMTXCOD")),
		@AttributeOverride(name="billingLineSequenceNumber", column=@Column(name="BLGLINSEQNUM")),
		@AttributeOverride(name="parameterCode", column=@Column(name="PARCOD"))}
		//@AttributeOverride(name="parameterValue", column=@Column(name="PARVAL"))}
	 )
    public BillingLineParameterPK getBillingLineParameterPK() {
        return billingLineParameterPK;
    }
    /**
     * @param billingLineParameterPK The billingLineParameterPK to set.
     */
    public void setBillingLineParameterPK(
            BillingLineParameterPK billingLineParameterPK) {
        this.billingLineParameterPK = billingLineParameterPK;
    }

	/**
	 * @return the parameterValue
	 */
    @Audit(name="Parameter Value")
	 @Column(name="PARVAL")
	public String getParameterValue() {
		return parameterValue;
	}


	/**
	 * @param parameterValue the parameterValue to set
	 */
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}


	/**
	 * @return the excludeFlag
	 */
	 @Audit(name="Exclude Flag")
	 @Column(name="EXCFLG")
	public String getExcludeFlag() {
		return excludeFlag;
	}


	/**
	 * @param excludeFlag the excludeFlag to set
	 */
	public void setExcludeFlag(String excludeFlag) {
		this.excludeFlag = excludeFlag;
	}

	public BillingLineParameter(String companyCode,String billingMatrixID,int billingLineSequenceNumber,
    		BillingLineParameterVO parameterVO) throws SystemException{
	//	log.entering("BillingLineParameter","BillingLineParameter");
		populatePK(parameterVO,companyCode,billingMatrixID,billingLineSequenceNumber);
		this.setExcludeFlag(parameterVO.getExcludeFlag());
		this.setParameterValue(parameterVO.getParameterValue());
		try{
			(PersistenceController.getEntityManager()).persist(this);
		}
		catch (CreateException createException) {
		//	log.log(Log.SEVERE,
				//	"CreateException caught; SystemException thrown");
			throw new SystemException(createException.getErrorCode(),
					createException);
		}
		//log.exiting("FlightMVTLog","FlightMVTLog");
		}
    /**
     * @param companyCode
     * @param billingMatrixId
     * @param billingLineSequenceNumber
     * @param parameterCode
     * @param parameterValue
     * @return
     * @throws SystemException
     */
    public static BillingLineParameter find(String companyCode,String billingMatrixId,
    		int billingLineSequenceNumber, String parameterCode,
    		String parameterValue)throws SystemException{
		//log.entering("BillingLineParameter","find");
		BillingLineParameter billingLineParameter=null;
		BillingLineParameterPK blgLineParameterPK=new BillingLineParameterPK(companyCode,billingMatrixId,
				billingLineSequenceNumber,parameterCode);
		try {
			billingLineParameter=PersistenceController.getEntityManager().find(
					BillingLineParameter.class,blgLineParameterPK);
		} catch (FinderException e) {		
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} 
		//log.exiting("BillingLineParameterPK","find");
		return billingLineParameter;
		
	}
    /**
     * @param parameterVO
     * @param companyCode
     * @param billingMatrixId
     * @param billingSequenceNumber
     */
    private void populatePK(BillingLineParameterVO parameterVO,String companyCode,String billingMatrixId,
    		int billingSequenceNumber){
    	BillingLineParameterPK blgLineParameterPK = new BillingLineParameterPK(companyCode,billingMatrixId,
    			billingSequenceNumber,parameterVO.getParameterCode());
    	this.setBillingLineParameterPK(blgLineParameterPK);
    }
    /** 
	 * @throws SystemException
	 */
	public void remove()throws SystemException{
		//log.entering("BillingLine","remove");
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException e) {			
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} catch (OptimisticConcurrencyException e) {			
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} 
	//	log.exiting("BillingLine","remove");
	}
	//Added by a7531 for icrd-224979
	public void update(BillingLineParameterVO parameterVO){
		this.setExcludeFlag(parameterVO.getExcludeFlag());
		this.setParameterValue(parameterVO.getParameterValue());
	}
}

