/*
 * MRAFormOneInv.java Created on Jul 28,2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InvoiceInFormOneVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.OptimisticConcurrencyException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-2391
 *
 */
@Entity
@Table(name = "MALMRAARLFRMONEINV")

public class MRAFormOneInv {



    private MRAFormOneInvPK formOneInvPK;
    private Calendar invDate;
    private String lstCurCode;
    private Double totMisAmt;
    private Double exgRate;
    private Double totBlgAmt;
    private String invStatus;
    private String formOneStatus;
    
    
    private String lastUpdatedUser;
	private Calendar lastUpdatedTime;

	private double totalBaseAmount;

    private static final String MODULE_NAME = "mail.mra.airlinebilling";
    
    private  Log log = LogFactory.getLogger("MRA AIRLINEBILLING FormOne");

 

    /**
     * Default Constructor
     */
    public MRAFormOneInv() {
    }

   /**
    * Constructor
    * @author A-2391
    * @param formOneInvVO
    * @throws SystemException
    */
    public MRAFormOneInv(InvoiceInFormOneVO formOneInvVO)
    throws SystemException{
    	staticLogger().entering("FormOneInv","inside FormOneInv");
    	MRAFormOneInvPK constructedformOneInvPK
    						=new MRAFormOneInvPK(formOneInvVO.getCompanyCode(),formOneInvVO.getAirlineIdentifier(),
    								formOneInvVO.getClearancePeriod(),formOneInvVO.getIntBlgTyp(),
    								formOneInvVO.getClassType(),
    								formOneInvVO.getInvoiceNumber());
    	this.setFormOneInvPK(constructedformOneInvPK);
    	populateAttributes(formOneInvVO);


    	try{
    	PersistenceController.getEntityManager().persist(this);
    	}
    	catch(CreateException e){
    		throw new SystemException(e.getErrorCode());
    	}
    	
    	
    }

     
    /**
 * @return the lastUpdatedUser
 */
    @Column(name="LSTUPDUSR")
    public String getLastUpdatedUser() {
    	return lastUpdatedUser;
}

	/**
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	
	/**
	 * @return the lastUpdateTime
	 *  
	 */
	@Version
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	/**
	 * @param lastUpdatedTime The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	

   
	
	

	

	/**
	 * @return Returns the formOnePK.
	 */

    @EmbeddedId
	@AttributeOverrides({
        @AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
        @AttributeOverride(name = "airlineIdentifier", column = @Column(name = "ARLIDR")),
        @AttributeOverride(name = "clearancePeriod", column = @Column(name = "CLRPRD")),
        @AttributeOverride(name = "intBillingType", column = @Column(name = "INTBLGTYP")),
        @AttributeOverride(name = "classType", column = @Column(name = "CLSTYP")),
        @AttributeOverride(name = "invNum", column = @Column(name = "INVNUM"))})
	public MRAFormOneInvPK getFormOneInvPK() {
		return formOneInvPK;
	}

	/**
	 * @param formOneInvPK The formOnePK to set.
	 */
	public void setFormOneInvPK(MRAFormOneInvPK formOneInvPK) {
		this.formOneInvPK = formOneInvPK;
	}

	
  
	

	

	/**
	 * @return Returns the invDate.
	 */
	@Column(name = "INVDAT")
	public Calendar getInvDate() {
		return invDate;
	}

	/**
	 * @param invDate The invDate to set.
	 */
	public void setInvDate(Calendar invDate) {
		this.invDate = invDate;
	}

	/**
	 * @return Returns the totBasAmt.
	 */
	//@Column(name = "TOTMISAMT")
	@Column(name = "TOTMISAMTLSTCUR")     //Modified by A-7929 as part of ICRD-265471
	public Double getTotMisAmt() {
		return totMisAmt;
	}

	/**
	 * @param totMisAmt The totMisAmt to set.
	 */
	public void setTotMisAmt(Double totMisAmt) {
		this.totMisAmt = totMisAmt;
	}

	/**
	 * @return Returns the totBlgAmt.
	 */
	//@Column(name = "TOTBLGAMT")            //Modified by A-7929 as part of ICRD-265471
	@Column(name = "TOTBLGAMTLSTCUR")
	public Double getTotBlgAmt() {
		return totBlgAmt;
	}

	/**
	 * @param totBlgAmt The totBlgAmt to set.
	 */
	public void setTotBlgAmt(Double totBlgAmt) {
		this.totBlgAmt = totBlgAmt;
	}

	

	
	private static Log staticLogger(){
    	return LogFactory.getLogger(MODULE_NAME);
    }

    /**
     * @param formOneInvVO
     */
    private void populateAttributes(InvoiceInFormOneVO formOneInvVO){
    	  log.log(Log.INFO, " -- inside populateAttributes of child ---- ");
    	 this.setInvDate(formOneInvVO.getInvoiceDate());
    	 this.setLstCurCode(formOneInvVO.getLstCurCode());
    	 if(formOneInvVO.getTotMisAmt()!=null){
    	 this.setTotMisAmt(formOneInvVO.getTotMisAmt().getAmount());
    	 }
    	 this.setExgRate(formOneInvVO.getExgRate());
    	 if(formOneInvVO.getBillingTotalAmt()!=null){
    	 this.setTotBlgAmt(formOneInvVO.getBillingTotalAmt().getAmount());
    	 }
    	 this.setInvStatus(formOneInvVO.getInvStatus());
    	 this.setFormOneStatus(formOneInvVO.getFormOneStatus());
    	 this.setLastUpdatedUser(formOneInvVO.getLastUpdateUser());
    	 this.setLastUpdatedTime(formOneInvVO.getLastUpdateTime());

   	  log.log(Log.INFO, " -- exiting populateAttributes of child ---- ");

    }
  /**
   * 
   * @param companyCode
   * @param airlineIdentifier
   * @param clearancePeriod
   * @param intBlgTyp
   * @param classType
   * @param invNum
   * @return
   * @throws FinderException
   * @throws SystemException
   */
    public static MRAFormOneInv find(String companyCode,
			int airlineIdentifier, String clearancePeriod,String intBlgTyp,
			String classType, String invNum)
			throws FinderException, SystemException {
    	MRAFormOneInvPK formOneInvPKk = new MRAFormOneInvPK(
				companyCode, airlineIdentifier, clearancePeriod,intBlgTyp,
				classType, invNum);
    	MRAFormOneInv foundEntity = null;
		return PersistenceController.getEntityManager().find(MRAFormOneInv.class, formOneInvPKk);
	}
    /**
     * 
     * @param formOneInvVO
     */
	public void update(InvoiceInFormOneVO formOneInvVO) {
		  log.log(Log.INFO, " -- inside update of child ---- ");
		populateAttributes(formOneInvVO);
		
	}

	/**
	 * 
	 * @throws RemoveException
	 * @throws SystemException
	 */
	public void remove() throws RemoveException, SystemException {
		
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (OptimisticConcurrencyException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
		
	}

	

	/**
	 * @return Returns the lstCurCode.
	 */
	@Column(name = "LSTCURCOD")
	public String getLstCurCode() {
		return lstCurCode;
	}

	/**
	 * @param lstCurCode The lstCurCode to set.
	 */
	public void setLstCurCode(String lstCurCode) {
		this.lstCurCode = lstCurCode;
	}

	/**
	 * @return Returns the exgRate.
	 */
	@Column(name = "EXGRATLSTBLGCUR")
	public Double getExgRate() {
		return exgRate;
	}

	/**
	 * @param exgRate The exgRate to set.
	 */
	public void setExgRate(Double exgRate) {
		this.exgRate = exgRate;
	}

	/**
	 * @return Returns the invStatus.
	 */
	@Column(name = "INVSTA")
	public String getInvStatus() {
		return invStatus;
	}

	/**
	 * @param invStatus The invStatus to set.
	 */
	public void setInvStatus(String invStatus) {
		this.invStatus = invStatus;
	}

	/**
	 * @return Returns the formOneStatus.
	 */
	@Column(name = "INVFRMONESTA")
	public String getFormOneStatus() {
		return formOneStatus;
	}

	/**
	 * @param formOneStatus The formOneStatus to set.
	 */
	public void setFormOneStatus(String formOneStatus) {
		this.formOneStatus = formOneStatus;
	}
	@Column(name = "TOTBASAMT")

	public double getTotalBaseAmount() {

		return totalBaseAmount;

	}



	/**

	 * @param totalBaseAmount

	 *            The totalBaseAmount to set.

	 */

	public void setTotalBaseAmount(double totalBaseAmount) {

		this.totalBaseAmount = totalBaseAmount;

	}

}
