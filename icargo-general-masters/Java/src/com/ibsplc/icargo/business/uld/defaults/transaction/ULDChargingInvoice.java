/*
 * ULDChargingInvoice.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.transaction;

import java.util.Calendar;
import java.util.Collection;

import javax.persistence.AttributeOverrides;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ChargingInvoiceFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDChargingInvoiceVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsPersistenceConstants;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1347
 *
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
@Staleable
@Table(name="ULDTXNINV")
@Entity
public class ULDChargingInvoice {

	private Log log=LogFactory.getLogger("ULD MANAGEMENT");

    private ULDChargingInvoicePK uldChargingInvoicePK;

    private String transactionType;
    private Calendar invoicedDate;
    private String invoicedToCode;
    private String invoicedToCodeName;
    private String invoiceRemark;
    private String paymentStatus;
    private String currencyCode;
    private Calendar lastUpdateTime;
    private String lastUpdateUser;
  //Added by Deepthi on 16Apr08 for AirNZ417
    /*
     * This is the total demurrage amount
     */
    private double  totalAmount;
    /*
     * This is the amount after deducting waiverAmount
     */
    private double netAmount;
    private double waiverAmount;
    private String partyType;
    
    /**
	 * @return the partyType
	 */
    @Column(name ="PTYTYP")
	public String getPartyType() {
		return partyType;
	}



	/**
	 * @param partyType the partyType to set
	 */
	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}



	/**
	 * @return Returns the uldChargingInvoicePK.
	 */
    @EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="invoiceRefNumber", column=@Column(name="INVREFNUM"))}
	)
	public ULDChargingInvoicePK getUldChargingInvoicePK() {
		return uldChargingInvoicePK;
	}



	/**
	 * @param uldChargingInvoicePK The uldChargingInvoicePK to set.
	 */
	public void setUldChargingInvoicePK(ULDChargingInvoicePK uldChargingInvoicePK) {
		this.uldChargingInvoicePK = uldChargingInvoicePK;
	}



	/**
	 * @return Returns the currencyCode.
	 */
	@Column(name="CURCOD")
	public String getCurrencyCode() {
		return currencyCode;
	}



	/**
	 * @param currencyCode The currencyCode to set.
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}



	/**
	 * @return Returns the invoicedDate.
	 */
	@Column(name="INVDAT")

	@Temporal(TemporalType.DATE)
	public Calendar getInvoicedDate() {
		return invoicedDate;
	}



	/**
	 * @param invoicedDate The invoicedDate to set.
	 */
	public void setInvoicedDate(Calendar invoicedDate) {
		this.invoicedDate = invoicedDate;
	}



	/**
	 * @return Returns the invoicedToCode.
	 */
	@Column(name="INVCOD")
	public String getInvoicedToCode() {
		return invoicedToCode;
	}



	/**
	 * @param invoicedToCode The invoicedToCode to set.
	 */
	public void setInvoicedToCode(String invoicedToCode) {
		this.invoicedToCode = invoicedToCode;
	}



	/**
	 * @return Returns the invoicedToCodeName.
	 */
	@Column(name="INVCODNAM")
	public String getInvoicedToCodeName() {
		return invoicedToCodeName;
	}



	/**
	 * @param invoicedToCodeName The invoicedToCodeName to set.
	 */
	public void setInvoicedToCodeName(String invoicedToCodeName) {
		this.invoicedToCodeName = invoicedToCodeName;
	}



	/**
	 * @return Returns the invoiceRemark.
	 */
	@Column(name="INVRMK")
	public String getInvoiceRemark() {
		return invoiceRemark;
	}



	/**
	 * @param invoiceRemark The invoiceRemark to set.
	 */
	public void setInvoiceRemark(String invoiceRemark) {
		this.invoiceRemark = invoiceRemark;
	}



	/**
	 * @return Returns the lastUpdateTime.
	 */
	@Column(name="LSTUPDTIM")

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}



	/**
	 * @param lastUpdateTime The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}



	/**
	 * @return Returns the lastUpdateUser.
	 */
	@Column(name="LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}



	/**
	 * @param lastUpdateUser The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}



	/**
	 * @return Returns the paymentStatus.
	 */
	@Column(name="PAYSTA")
	public String getPaymentStatus() {
		return paymentStatus;
	}



	/**
	 * @param paymentStatus The paymentStatus to set.
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}



	/**
	 * @return Returns the transactionType.
	 */
	@Column(name ="TXNTYP")
	public String getTransactionType() {
		return transactionType;
	}



	/**
	 * @param transactionType The transactionType to set.
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * @return the netAmount
	 */
	@Column(name ="NETAMT")
	public double getNetAmount() {
		return netAmount;
	}



	/**
	 * @param netAmount the netAmount to set
	 */
	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}



	/**
	 * @return the totalAmount
	 */
	@Column(name ="TOTAMT")
	public double getTotalAmount() {
		return totalAmount;
	}



	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}



	/**
	 * @return the waiverAmount
	 */
	@Column(name ="WVRAMT")
	public double getWaiverAmount() {
		return waiverAmount;
	}



	/**
	 * @param waiverAmount the waiverAmount to set
	 */
	public void setWaiverAmount(double waiverAmount) {
		this.waiverAmount = waiverAmount;
	}
	
	
	

	/**
	 * Constructor
	 */
	public ULDChargingInvoice() {
	}



	/**
	 * Constructor
	 *
	 * @param uldChargingInvoiceVO
	 * @throws SystemException
	 */
	public ULDChargingInvoice(ULDChargingInvoiceVO uldChargingInvoiceVO)
		throws SystemException {
		log.entering("ULDChargingInvoice","ULDChargingInvoice constructor");
		populatePk(uldChargingInvoiceVO);
		populateAttributes(uldChargingInvoiceVO);
		try{
    		PersistenceController.getEntityManager().persist(this);
    	}catch(CreateException createException){
    		log.log(Log.SEVERE,"CreateException");
    		throw new SystemException(createException.getErrorCode());
    	}
		log.exiting("ULDChargingInvoice","ULDChargingInvoice constructor");
	}


	/**
	 * private method to populate PK
	 *
	 * @param uldChargingInvoiceVO
	 */
	public void populatePk(ULDChargingInvoiceVO uldChargingInvoiceVO) {
		log.entering("ULDChargingInvoice","populatePk");
		ULDChargingInvoicePK invoicePK = new ULDChargingInvoicePK();
		invoicePK.setCompanyCode(uldChargingInvoiceVO.getCompanyCode());
		this.uldChargingInvoicePK = invoicePK ;
		log.exiting("ULDChargingInvoice","populatePk");
	}


	/**
	 * private method to populate attributes
	 *
	 * @param uldChargingInvoiceVO
	 * @throws SystemException
	 */
	public void populateAttributes(ULDChargingInvoiceVO uldChargingInvoiceVO)
		throws SystemException {
		log.entering("ULDChargingInvoice","populateAttributes");
		this.setCurrencyCode(uldChargingInvoiceVO.getCurrencyCode());
				if(uldChargingInvoiceVO.getInvoicedDate() != null){
				this.setInvoicedDate(uldChargingInvoiceVO.getInvoicedDate().toCalendar());
				}
				this.setInvoicedToCode(uldChargingInvoiceVO.getInvoicedToCode());
				this.setInvoicedToCodeName(uldChargingInvoiceVO.getInvoicedToCodeName());
				this.setInvoiceRemark(uldChargingInvoiceVO.getInvoiceRemark());
				if(uldChargingInvoiceVO.getLastUpdatedTime() != null){
				this.setLastUpdateTime(uldChargingInvoiceVO.getLastUpdatedTime().toCalendar());
				}
				this.setLastUpdateUser(uldChargingInvoiceVO.getLastUpdatedUser());
				this.setPaymentStatus(uldChargingInvoiceVO.getPaymentStatus());
				this.setTransactionType(uldChargingInvoiceVO.getTransactionType());
				//Added by a-3278 for AirNZ417 begins
				this.setTotalAmount(uldChargingInvoiceVO.getTotalAmount());
				this.setWaiverAmount(uldChargingInvoiceVO.getWaiverAmount());
				this.setNetAmount(uldChargingInvoiceVO.getNetAmount());
				//Added by a-3278 for AirNZ417 ends
				this.setPartyType(uldChargingInvoiceVO.getPartyType());	
				
		log.exiting("ULDChargingInvoice","populateAttributes");
	}

	/**
	 * This method invokes populate ULDChargingInvoice
	 * @param ULDChargingInvoiceVO
	 * @throws SystemException
	 */
/*	public void populateChildren(ULDChargingInvoiceVO uldChargingInvoiceVO)
		throws SystemException {
	}*/






	  /**
	   * This method finds the ULDChargingInvoice instance based on the ULDChargingInvoicePK
	   *
	   * @param  companyCode
	   * @param  transactionType
	   * @param  invoiceRefNumber
	   * @return ULDChargingInvoice
	   * @throws SystemException
	   */
		public static ULDChargingInvoice find( String companyCode,
		        				String transactionType,
		        				int invoiceRefNumber)
			throws SystemException {
		    return null;
		}


		/**
		 * method to update the BO
		 *
		 * @param uldChargingInvoiceVO
		 * @throws SystemException
		 */
		public void update(ULDChargingInvoiceVO uldChargingInvoiceVO)
			throws SystemException {

		}

		/**
		 * This method is used to remove the business object.
		 * It interally calls the remove method within EntityManager
		 *
		 * @throws SystemException
		 */
		public void remove() throws SystemException {

		}


		/**
		 * This method is used for listing uld Invoice
		 * @author A-1883
		 * @param chargingInvoiceFilterVO
		 * @param pageNumber
		 * @return Page<ULDChargingInvoiceVO>
		 * @throws SystemException
		 *
		 */
		public static Page<ULDChargingInvoiceVO> listULDChargingInvoice(
				ChargingInvoiceFilterVO chargingInvoiceFilterVO,int pageNumber)
		throws SystemException{
			try {
	 	    	EntityManager em = PersistenceController.getEntityManager();
	 	    	ULDDefaultsDAO uldDefaultsDAO = ULDDefaultsDAO.class.cast(
	 	    			em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
	 	    	return uldDefaultsDAO.listULDChargingInvoice(chargingInvoiceFilterVO,pageNumber);
	 	     }catch (PersistenceException persistenceException) {
	 			throw new SystemException(persistenceException.getErrorCode());
	 		}
		}

		/**
		 * This method is used for listing uld Invoice Details
		 * @author A-1883
		 * @param companyCode
		 * @param invoiceRefNumber
		 * @return Collection<ULDTransactionDetailsVO>
		 * @throws SystemException
		 */
		public static Collection<ULDTransactionDetailsVO> viewULDChargingInvoiceDetails(String companyCode,
				String invoiceRefNumber)throws SystemException{
			try {
	 	    	EntityManager em = PersistenceController.getEntityManager();
	 	    	ULDDefaultsDAO uldDefaultsDAO = ULDDefaultsDAO.class.cast(
	 	    			em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
	 	    	return uldDefaultsDAO.viewULDChargingInvoiceDetails(companyCode,invoiceRefNumber);
	 	     }catch (PersistenceException persistenceException) {
	 			throw new SystemException(persistenceException.getErrorCode());
	 		}
		}
		/**
		 * For InvoiceRefNumber LOV
		 * @author A-1883
		 * @param companyCode
		 * @param displayPage
		 * @param invRefNo
		 * @return Page<String>
		 * @throws SystemException
		 * @exception PersistenceException
		 */
		  public static Page<String> findInvoiceRefNumberLov(String companyCode,int displayPage,String invRefNo)
		  throws SystemException{
			try {
	 	    	EntityManager em = PersistenceController.getEntityManager();
	 	    	ULDDefaultsDAO uldDefaultsDAO = ULDDefaultsDAO.class.cast(
	 	    			em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
	 	    	return uldDefaultsDAO.findInvoiceRefNumberLov(companyCode,displayPage,invRefNo);
	 	     }catch (PersistenceException persistenceException) {
	 			throw new SystemException(persistenceException.getErrorCode());
	 		}
		}



		
}
