/*
 * GPAReportingBilling.java Created on SEP 29,2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpareporting;


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingDetailsVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.OptimisticConcurrencyException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3447---Initial Draft
 * 
 * 
 */
@Entity
@Staleable
@Deprecated
@Table(name = "MTKGPARPTBIL")
public class GPAReportingBilling {
	
	
	  private Log logs = LogFactory.getLogger("MAILTRACKING MRA GPAREPORTING");

	private static final String BIL_ID = "BIL";
	
	private GPAReportingBillingPK gPAReportingBillingPK;

	private String reportingFromString;

	
	private String reportingToString;
	
	
	//---------------------------------------------------------------------------
	
	/**
	 * @return the gPAReportingBillingPK
	 */
	
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "poaCode", column = @Column(name = "POACOD")),
			@AttributeOverride(name = "billingIdentifier", column = @Column(name = "BILIDR"))})
	public GPAReportingBillingPK getGPAReportingBillingPK() {
		return gPAReportingBillingPK;
	}

	/**
	 * @param reportingBillingPK the gPAReportingBillingPK to set
	 */
	public void setGPAReportingBillingPK(GPAReportingBillingPK reportingBillingPK) {
		gPAReportingBillingPK = reportingBillingPK;
	}

	/**
	 * @return the reportingFromString
	 */
	@Column(name = "REPPRDFRM")
	public String getReportingFromString() {
		return reportingFromString;
	}

	/**
	 * @param reportingFromString the reportingFromString to set
	 */
	public void setReportingFromString(String reportingFromString) {
		this.reportingFromString = reportingFromString;
	}

	/**
	 * @return the reportingToString
	 */
	@Column(name = "REPPRDTO")
	public String getReportingToString() {
		return reportingToString;
	}

	/**
	 * @param reportingToString the reportingToString to set
	 */
	public void setReportingToString(String reportingToString) {
		this.reportingToString = reportingToString;
	}
	
	
	public GPAReportingBilling(){
		
		
	}
		
	
	
	/**For Poputaing Pk and Details to the table 
	 * @author A-3447
	 * @param cCAdetailsVO
	 * @throws SystemException	  
	 */
	public GPAReportingBilling(GPAReportingDetailsVO gpaReportingDetailsVOO) throws SystemException {
		Log log = LogFactory.getLogger("MRA GPA RPT");
		logs.entering("GPA REPORTING", "contructor");
		//String billId=generateBillId(gpaReportingDetailsVOO.getCompanyCode());
		// logs.log(Log.INFO,"billId=--->" +billId);
		 GPAReportingBillingPK gpaReportingBillingPK = new GPAReportingBillingPK();
		 gpaReportingBillingPK.setCompanyCode(gpaReportingDetailsVOO.getCompanyCode());	
		 gpaReportingBillingPK.setBillingIdentifier(gpaReportingDetailsVOO.getBillingIdentifier());		  
		 gpaReportingBillingPK.setPoaCode(gpaReportingDetailsVOO.getPoaCode());
		log.log(log.FINE,"gpaReportingDetailsVOO*****"+gpaReportingDetailsVOO);
		populatePk(gpaReportingDetailsVOO);
		populateAttributes(gpaReportingDetailsVOO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode(),
					createException);
		}
		logs.exiting("MRA GPA RPT", "contructor");

	}
	
	
	/**
	 * @param gpaReportingDetailsVO
	 * @throws SystemException
	 */
	private void populatePk(GPAReportingDetailsVO gpaReportingDetailsVO)
			throws SystemException {
		Log log = LogFactory.getLogger("MRA GPA RPT");
		log.entering("GPAReportingDetails", "populatePK");
		this.setGPAReportingBillingPK((new GPAReportingBillingPK(gpaReportingDetailsVO.getCompanyCode(),gpaReportingDetailsVO.getBillingIdentifier(), gpaReportingDetailsVO
				.getPoaCode())));
		log.exiting("GPAReportingDetails", "populatePK");
	}

	/**
	 * 
	 * @param companyCode
	 * @param poaCode
	 * @param billingIdentifier
	 *  @throws SystemException
	 * @throws FinderException
	 */
	public static GPAReportingBilling find(String companyCode, String billingIdentifier,String poaCode)
			throws SystemException, FinderException {		
		return PersistenceController.getEntityManager().find(
				GPAReportingBilling.class,new GPAReportingBillingPK( companyCode, billingIdentifier,
						 poaCode));
	}
	/**
	 * Remove Method 
	 * @throws SystemException
	 * @author a-3447
	 * @exception RemoveException
	 */
	public void remove() throws RemoveException, SystemException {
		Log log = LogFactory.getLogger("MRA GPA REPORTING");
		
		log.entering("REmoved", "remove");
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (OptimisticConcurrencyException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
		//log.exiting(CLASSNAME, "remove");
	}
	
	/**
	 * This method Generates BillIdentifier in the Format -- BIL100
	 * @author A-3447
	 * @param companyCode
	 * @param airportCode
	 * @return String
	 * @throws SystemException
	 */
	public static String generateBillId(String companyCode)
			throws SystemException {
		Log log = LogFactory.getLogger("MRA GPA REPORTING");
		Criterion criterion = KeyUtils.getCriterion(companyCode, BIL_ID);
		String key = KeyUtils.getKey(criterion);
		log.log(Log.INFO, "keyGenerated" + key);
		
		  int keynum = Integer.parseInt(key);
	         StringBuilder str = new StringBuilder("BIL");
	         if(keynum<10){
	        	 str.append("00"); 
	         }
	         else if(keynum<100){
	        	str.append("0");
	         }
	         str.append(key);
	         log.log(Log.INFO,"keynum=--->" +keynum);         
	         return str.toString();
		 }
	
	
	/**
	 * @author A-3447
	 * @param gpaReportingDetailsVOO
	 */
	private void populateAttributes(GPAReportingDetailsVO gpaReportingDetailsVOO) {
		Log log = LogFactory.getLogger("MRA GPA REPORTING");
		log.entering("MRA GPA REPORTING", "populateAttributes");
		log.log(log.FINE,"gpaReportingDetailsVOO"+gpaReportingDetailsVOO);
		this.setReportingFromString(gpaReportingDetailsVOO.getReportingFrom().toDisplayDateOnlyFormat());
		this.setReportingToString(gpaReportingDetailsVOO.getReportingTo().toDisplayDateOnlyFormat());
		log.exiting("MRA -GPA- REPORTING", "populateAttributes");
	}
}