/*
 * GPABillingMaster.java Created on Dec 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.mra.MailTrackingMRABusinessException;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingStatusVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceFilterVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.MRAGPABillingDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/*
 * @author Philip
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         Jan 8, 2007   Philip 		            Initial draft
 *  0.2         Jan 18,2007   Indu V.K. 				Implemented the entity methods and findGPABillingEntries
 *
 *
 */




@Entity
@Table(name = "MTKGPABLGMST")
@Staleable


public class GPABillingMaster {

    private GPABillingMasterPK gpaBillingMasterPK;
    //private int totalPiecesReceived;
    
    private double totalBillableWeight;
    private double totalBilledWeight;
    private double totalBillableAmount;
    private double totalBilledAmount;
    //TODO set<GPABillingDetails>
    private Set<GPABillingDetails> gpaBillingDetails;
    private String basisType;
    private String currencyCode;
    /*
     * Module name
     */
    private static final  String MODULE_NAME="mail.mra.gpabilling";
    private Log log = LogFactory.getLogger("MRA GPABILLINGMASTER ");

    public GPABillingMaster() {

    }

    /**
     * @return Returns the gpaBillingDetails.
     */

    @OneToMany  
    @JoinColumns( {
	 @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
	 @JoinColumn(name = "GPACOD", referencedColumnName = "GPACOD", insertable=false, updatable=false),
	 @JoinColumn(name = "BLGBAS", referencedColumnName = "BLGBAS", insertable=false, updatable=false)})

    public Set<GPABillingDetails> getgpaBillingDetails() {
        return gpaBillingDetails;
    }
    /**
     * @param gpaBillingDetails The gpaBillingDetails to set.
     */
    public void setgpaBillingDetails(Set<GPABillingDetails> gpaBillingDetails) {
        this.gpaBillingDetails = gpaBillingDetails;
    }
    /**
     * @return Returns the totalBillableAmount.
     */

    @Column(name="TOTBLBAMT")
    public double getTotalBillableAmount() {
        return totalBillableAmount;
    }
    /**
     * @param totalBillableAmount The totalBillableAmount to set.
     */
    public void setTotalBillableAmount(double totalBillableAmount) {
        this.totalBillableAmount = totalBillableAmount;
    }
    /**
     * @return Returns the totalBillableWeight.
     */
    @Column(name="TOTBLBWGT")
    public double getTotalBillableWeight() {
        return totalBillableWeight;
    }
    /**
     * @param totalBillableWeight The totalBillableWeight to set.
     */
    public void setTotalBillableWeight(double totalBillableWeight) {
        this.totalBillableWeight = totalBillableWeight;
    }
    /**
     * @return Returns the totalBilledAmount.
     */
     @Column(name="TOTBLDAMT")
    public double getTotalBilledAmount() {
        return totalBilledAmount;
    }
    /**
     * @param totalBilledAmount The totalBilledAmount to set.
     */
    public void setTotalBilledAmount(double totalBilledAmount) {
        this.totalBilledAmount = totalBilledAmount;
    }
    /**
     * @return Returns the totalBilledWeight.
     */
    @Column(name="TOTBLDWGT")
    public double getTotalBilledWeight() {
        return totalBilledWeight;
    }
    /**
     * @param totalBilledWeight The totalBilledWeight to set.
     */
    public void setTotalBilledWeight(double totalBilledWeight) {
        this.totalBilledWeight = totalBilledWeight;
    }
   
   
    @Column(name="BASTYP")
    /**
	 * @return Returns the basisType.
	 */
	public String getBasisType() {
		return basisType;
	}

	/**
	 * @param basisType The basisType to set.
	 */
	public void setBasisType(String basisType) {
		this.basisType = basisType;
	}

    /**
     * @return Returns the billingCurrencyCode.
     */
     @Column(name="CURCOD")
    public String getCurrencyCode() {
        return currencyCode;
    }
    /**
     * @param currencyCode The billingCurrencyCode to set.
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
	/**
     * @return Returns the gpaBillingMasterPK.
     */

    @EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="gpaCode", column=@Column(name="GPACOD")),
		@AttributeOverride(name="billingBasis", column=@Column(name="BLGBAS"))}
	)
    public GPABillingMasterPK getGpaBillingMasterPK() {
        return gpaBillingMasterPK;
    }
    /**
     * @param gpaBillingMasterPK The gpaBillingMasterPK to set.
     */
    public void setGpaBillingMasterPK(GPABillingMasterPK gpaBillingMasterPK) {
        this.gpaBillingMasterPK = gpaBillingMasterPK;
    }

    /**
     * @author A-2391
     * Finds tand returns the GPA Billing entries available
     * This includes billed, billable and on hold despatches
     *
     * @param gpaBillingEntriesFilterVO
     * @return Collection<GPABillingDetailsVO>
     * @throws SystemException
     */

    public static Collection<GPABillingDetailsVO> findGPABillingEntries(
            GPABillingEntriesFilterVO gpaBillingEntriesFilterVO)
    throws SystemException{

    	// log.entering(MODULE_NAME,"findGPABillingEntries");
    	Collection<GPABillingDetailsVO> gpaBillingDetailsVOs=null;
		// log.log(Log.INFO,"resulting vos: "+gpaBillingDetailsVOs);
		//log.exiting(MODULE_NAME,"findGPABillingEntries");
	return GPABillingDetails.findGPABillingEntries(gpaBillingEntriesFilterVO);

    }
    /**
     * @author A-2391
     * removes the entity
     * @throws RemoveException
     * @throws SystemException
     */
    public void remove()throws RemoveException,SystemException{
    	log.entering(MODULE_NAME,"remove");
		Set<GPABillingDetails> gPABillingDetails = this.getgpaBillingDetails();
	    	if(gPABillingDetails != null && gPABillingDetails.size() > 0 ){
	    		log.log(Log.INFO,"removing all gpaBillingDetails first ");
	    		for(GPABillingDetails gpaBillingDetail : gPABillingDetails){
	    			gpaBillingDetail.remove();
	        	}
	    		log.log(Log.INFO,"removing all gpaBillingDetail completed ");
	    	}
	    	log.exiting(MODULE_NAME,"remove");
		PersistenceController.getEntityManager().remove(this);
    }

    /**
     * @author A-2391
     * updates the entity
     * @throws RemoveException
     * @throws SystemException
     * @throws CreateException
     * @throws FinderException
     */
    public void update() throws SystemException,
	RemoveException,CreateException,FinderException
	{

	}
    /**
     * @author A-2391
     *  updates BillingStatus
     * @param gPABillingStatusVO
     * @throws RemoveException
     * @throws SystemException
     * @throws CreateException
     * @throws FinderException
     */

    public void updateBillingStatus(Collection<GPABillingStatusVO> gPABillingStatusVO) throws SystemException,
	RemoveException,CreateException,FinderException{
    	log.entering(MODULE_NAME,"update");
    	ArrayList<GPABillingStatusVO> gPABiilingStatusVOs=new ArrayList<GPABillingStatusVO>(gPABillingStatusVO);
    	int size=gPABiilingStatusVOs.size();
    	log.log(Log.INFO, "size of arraylist in entity", size);
		for(int i=0;i<size;i++)
    	{
    		String companycode=gPABiilingStatusVOs.get(i).getCompanyCode();
    		String billingBasis=gPABiilingStatusVOs.get(i).getBillingBasis();
    		String gpaCode=gPABiilingStatusVOs.get(i).getGpaCode();
    		int sequenceNumber=gPABiilingStatusVOs.get(i).getSequenceNumber();
    		String billingStatus=gPABiilingStatusVOs.get(i).getStatus();
    		String remarks=gPABiilingStatusVOs.get(i).getRemarks();
    		GPABillingDetails gpaDetails=GPABillingDetails.find(companycode,gpaCode,billingBasis,sequenceNumber);
    		gpaDetails.setBillingStatus(billingStatus);
    		gpaDetails.setRemarks(remarks);

    	}
    	log.log(Log.INFO, "COLLECTION OF GPABILLING DETAILS AFTER SETTING",
				gPABiilingStatusVOs);


    }
    /**
     * @author A-2391
     *  finds the entity
     * @param companyCode
     * @param gpaCode
     * @param billingBasis
     * @return GPABillingMaster
     * @throws RemoveException
     * @throws SystemException
     * @throws CreateException
     * @throws FinderException
     */


    public static GPABillingMaster find(String companyCode,
            String gpaCode,String billingBasis)
    throws SystemException,FinderException {
    	GPABillingMasterPK pk = new GPABillingMasterPK();
		pk.setCompanyCode(   companyCode);
		pk.setGpaCode(   gpaCode);
		pk.setBillingBasis(   billingBasis);
		return PersistenceController.getEntityManager().find(
				GPABillingMaster.class, pk);

    }
    
    
    /** method to generate an invoice
	 * @author a-2270
	 * @param generateInvoiceFilterVO
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */	
	
    public static void generateInvoice(GenerateInvoiceFilterVO generateInvoiceFilterVO)
	throws SystemException,MailTrackingMRABusinessException {
    	
    	String outParameter = null;
    	
    	try{
    		outParameter = constructDAO().generateInvoice(generateInvoiceFilterVO);			
		}
		catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
		//Modified by A-4809 for ICRD-39680 Starts
		if(outParameter!=null && outParameter.trim().length()>0){
			String[] outParam = outParameter.split("#");
		if( ! ("OK").equalsIgnoreCase(outParam[0]) ) {
			MailTrackingMRABusinessException mailBusExp 
							= new MailTrackingMRABusinessException();
			mailBusExp.addError(new ErrorVO(MailTrackingMRABusinessException.
											MTK_MRA_GPABILLING_STATUS_NOTOK));
			throw mailBusExp;			
		}
		}else{
			MailTrackingMRABusinessException mailBusExp = new MailTrackingMRABusinessException();
			mailBusExp.addError(new ErrorVO(MailTrackingMRABusinessException.MTK_MRA_GPABILLING_STATUS_NOTOK));
			throw mailBusExp;			
		}
		//Modified by A-4809 for ICRD-39680 Ends
	}   	
    /**
     * 
     * 	Method		:	GPABillingMaster.withdrawMailbags
     *	Added by 	:	A-6991 on 05-Sep-2017
     * 	Used for 	:   ICRD-211662
     *	Parameters	:	@param documentBillingDetailsVOs
     *	Parameters	:	@throws SystemException
     *	Parameters	:	@throws MailTrackingMRABusinessException 
     *	Return type	: 	void
     */
    public void withdrawMailbags(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs)
    		throws SystemException{
    
    	try{
    		 constructDAO().withdrawMailbags(documentBillingDetailsVOs);			
		}
		catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
    }
    
    public void finalizeProformaInvoice(Collection<CN51SummaryVO> summaryVOs)
    		throws SystemException{
    
    	try{
    		 constructDAO().finalizeProformaInvoice(summaryVOs);			
		}
		catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
    }
    /**
     * 
     * 	Method		:	GPABillingMaster.withdrawInvoice
     *	Added by 	:	A-6991 on 18-Sep-2017
     * 	Used for 	:   ICRD-211662
     *	Parameters	:	@param summaryVO
     *	Parameters	:	@throws SystemException 
     *	Return type	: 	void
     */
    public void withdrawInvoice(CN51SummaryVO summaryVO) throws SystemException{
    	try {
			constructDAO().withdrawInvoice(summaryVO);
		} 
    	catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
    }
    /**
	 * This method constructs the NonCASSDAO
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	private static MRAGPABillingDAO constructDAO()
	throws PersistenceException, SystemException{
		Log log = LogFactory.getLogger("MRA GPABILLING");
		log.entering("GPABillingMaster","constructDAO");
		EntityManager entityManager =
			PersistenceController.getEntityManager();
		return MRAGPABillingDAO.class.cast(
				entityManager.getQueryDAO(MODULE_NAME));
	}
    

}
