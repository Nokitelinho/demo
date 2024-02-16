/*
 * ULDAgreementDetails.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc;

import java.util.Calendar;

import javax.persistence.AttributeOverrides;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementDetailsVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;


/**
 * @author A-1347
 *
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
@Staleable
@Table(name="ULDAGRMNTDTL")
@Entity
public class ULDAgreementDetails {

	private Log log=LogFactory.getLogger("ULD MANAGEMENT");

    private String uldTypeCode;
    private String station;
    private Calendar agreementFromDate;
    private Calendar agreementToDate;
    private int freeLoanPeriod;
    private String demurrageFrequency;
    private double demurrageRate;
    private double tax;
    private String currency;
    private String remark;


    private ULDAgreementDetailsPK uldAgreementDetailsPK;


    /**
     * @return Returns the uldTypeCode.
     */
    @Column(name="ULDTYPCOD")
    public String getUldTypeCode() {
        return uldTypeCode;
    }
    /**
     * @param uldTypeCode The uldTypeCode to set.
     */
    public void setUldTypeCode(String uldTypeCode) {
        this.uldTypeCode = uldTypeCode;
    }

    /**
     * @return Returns the agreementFromDate.
     */
  	@Column(name="AGRMNTFRMDAT")

	@Temporal(TemporalType.DATE)
    public Calendar getAgreementFromDate() {
        return agreementFromDate;
    }
    /**
     * @param agreementFromDate The agreementFromDate to set.
     */
    public void setAgreementFromDate(Calendar agreementFromDate) {
        this.agreementFromDate = agreementFromDate;
    }
    /**
     * @return Returns the agreementToDate.
     */
    @Column(name="AGRMNTTOODAT")

	@Temporal(TemporalType.DATE)
    public Calendar getAgreementToDate() {
        return agreementToDate;
    }
    /**
     * @param agreementToDate The agreementToDate to set.
     */
    public void setAgreementToDate(Calendar agreementToDate) {
        this.agreementToDate = agreementToDate;
    }
    /**
     * @return Returns the currency.
     */
    @Column(name="CURCOD")
    public String getCurrency() {
        return currency;
    }
    /**
     * @param currency The currency to set.
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    /**
     * @return Returns the demurrageFrequency.
     */
    @Column(name="DMRFQY")
    public String getDemurrageFrequency() {
        return demurrageFrequency;
    }
    /**
     * @param demurrageFrequency The demurrageFrequency to set.
     */
    public void setDemurrageFrequency(String demurrageFrequency) {
        this.demurrageFrequency = demurrageFrequency;
    }
    /**
     * @return Returns the demurrageRate.
     */
    @Column(name="DMRRAT")
    public double getDemurrageRate() {
        return demurrageRate;
    }
    /**
     * @param demurrageRate The demurrageRate to set.
     */
    public void setDemurrageRate(double demurrageRate) {
        this.demurrageRate = demurrageRate;
    }
    /**
     * @return Returns the freeLoanPeriod.
     */
    @Column(name="FRELONPRD")
    public int getFreeLoanPeriod() {
        return freeLoanPeriod;
    }
    /**
     * @param freeLoanPeriod The freeLoanPeriod to set.
     */
    public void setFreeLoanPeriod(int freeLoanPeriod) {
        this.freeLoanPeriod = freeLoanPeriod;
    }
    /**
     * @return Returns the remark.
     */
    @Column(name="AGRMNTRMK")
    public String getRemark() {
        return remark;
    }
    /**
     * @param remark The remark to set.
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    /**
     * @return Returns the station.
     */
    @Column(name="ARPCOD")
    public String getStation() {
        return station;
    }
    /**
     * @param station The station to set.
     */
    public void setStation(String station) {
        this.station = station;
    }
    /**
     * @return Returns the tax.
     */
    @Column(name="TAXAMT")
    public double getTax() {
        return tax;
    }
    /**
     * @param tax The tax to set.
     */
    public void setTax(double tax) {
        this.tax = tax;
    }
    /**
     * @return Returns the uldAgreementDetailsPK.
     */
    @EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="agreementNumber", column=@Column(name="AGRMNTNUM")),
		@AttributeOverride(name="sequenceNumber", column=@Column(name="SEQNUM"))})
		public ULDAgreementDetailsPK getUldAgreementDetailsPK() {
        	return uldAgreementDetailsPK;
    	}
    	/**
    	 * @param uldAgreementDetailsPK The uldAgreementDetailsPK to set.
    	 */
    public void setUldAgreementDetailsPK(
            ULDAgreementDetailsPK uldAgreementDetailsPK) {
        this.uldAgreementDetailsPK = uldAgreementDetailsPK;
    }


	/**
	 * Constructor
	 */
	public ULDAgreementDetails() {
	}


	/**
	 * Constructor
	 * @param  companyCode
	 * @param  agreementNumber
	 * @param uldAgreementDetailsVO
	 * @throws SystemException
	 */
	public ULDAgreementDetails(String companyCode,String agreementNumber,
	        ULDAgreementDetailsVO uldAgreementDetailsVO)
		throws SystemException {
		int serialNumber = uldAgreementDetailsVO.getSequenceNumber();
		Log log = LogFactory.getLogger("ULDAgreementDetails");
    	log.entering("ULDAgreementDetails","Constructor");
    	populatePk(companyCode,agreementNumber,serialNumber);
    	populateAttributes(uldAgreementDetailsVO);
     	EntityManager em = PersistenceController.getEntityManager();
   		log.log(Log.INFO, "CompanyCode-->>", companyCode);
		log.log(Log.INFO, "AgreementNumber-->>", agreementNumber);
		log.log(Log.INFO, "ULDAgreementDetailsVO-->>>", uldAgreementDetailsVO);
		log.log(Log.INFO,"--->>>>GOING TO ONE ROW INSERT CHILD");
    	try{
  			em.persist(this);
   		}catch(CreateException createException){
   			throw new SystemException(createException.getErrorCode());
   		}
    	log.log(Log.INFO,"--->>>>INSERTED");
	}


	/**
	 * private method to populate PK
	 *
	 * @param  companyCode
	 * @param  agreementNumber
	 * @param  serialNumber
	 */
	public void populatePk(String companyCode, String agreementNumber,int serialNumber) {
		uldAgreementDetailsPK = new ULDAgreementDetailsPK();
		uldAgreementDetailsPK.setCompanyCode(companyCode);
		uldAgreementDetailsPK.setAgreementNumber(agreementNumber);
		uldAgreementDetailsPK.setSequenceNumber(serialNumber);
		this.setUldAgreementDetailsPK(uldAgreementDetailsPK);
	}


	/**
	 * private method to populate attributes
	 *
	 * @param uldAgreementDetailsVO
	 * @throws SystemException
	 */
	public void populateAttributes(ULDAgreementDetailsVO uldAgreementDetailsVO)
		throws SystemException {
		setUldTypeCode(uldAgreementDetailsVO.getUldTypeCode());
		setStation(uldAgreementDetailsVO.getStation());
		if(uldAgreementDetailsVO.getAgreementFromDate() != null) {
			setAgreementFromDate(uldAgreementDetailsVO.getAgreementFromDate().toCalendar());
		}
		if(uldAgreementDetailsVO.getAgreementToDate() != null) {
			setAgreementToDate(uldAgreementDetailsVO.getAgreementToDate()==null?null:uldAgreementDetailsVO.getAgreementToDate().toCalendar());
		}
		setFreeLoanPeriod(uldAgreementDetailsVO.getFreeLoanPeriod());
		setDemurrageFrequency(uldAgreementDetailsVO.getDemurrageFrequency());
		setDemurrageRate(uldAgreementDetailsVO.getDemurrageRate());
		setTax(uldAgreementDetailsVO.getTax());
		setCurrency(uldAgreementDetailsVO.getCurrency());
		setRemark(uldAgreementDetailsVO.getRemark());

	}


	/**
	 * method to update the BO
	 *
	 * @param uldAgreementDetailsVO
	 * @throws SystemException
	 */
	public void update(ULDAgreementDetailsVO uldAgreementDetailsVO)
		throws SystemException {
		Log log = LogFactory.getLogger("ULDAgreementDetails") ;
		log.entering("ULDAgreementDetails","update");
		log.log(Log.INFO,"Going to Update ULDAgreementDetails");
		setUldTypeCode(uldAgreementDetailsVO.getUldTypeCode());
		setStation(uldAgreementDetailsVO.getStation());
		setAgreementFromDate(uldAgreementDetailsVO.getAgreementFromDate().toCalendar());
		setAgreementToDate(uldAgreementDetailsVO.getAgreementToDate()==null?null:uldAgreementDetailsVO.getAgreementToDate().toCalendar());
		setFreeLoanPeriod(uldAgreementDetailsVO.getFreeLoanPeriod());
		setDemurrageFrequency(uldAgreementDetailsVO.getDemurrageFrequency());
		setDemurrageRate(uldAgreementDetailsVO.getDemurrageRate());
		setTax(uldAgreementDetailsVO.getTax());
		setCurrency(uldAgreementDetailsVO.getCurrency());
		setRemark(uldAgreementDetailsVO.getRemark());

	}


	/**
	 * This method is used to remove the business object.
	 * It interally calls the remove method within EntityManager
	 *
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		Log log = LogFactory.getLogger("ULDAgreementDetails") ;
		log.entering("ULDAgreementDetails","remove");
		log.log(Log.INFO,"Going to Remove ULDAgreementDetails");
		try{
    		PersistenceController.getEntityManager().remove(this);
    	}
    	catch(RemoveException removeException){
    		throw new SystemException(removeException.getErrorCode());
    	}
	}


  	 /**
     * This method finds the ULDAgreementDetails instance based on the AgreementDetailsPK
     *
     * @param companyCode
     * @param agreementNumber
     * @param sequenceNumber
     * @return
     * @throws SystemException
     */
  	public static ULDAgreementDetails find( String companyCode, String agreementNumber,int sequenceNumber)
  		throws SystemException {
  		try {
	    	EntityManager em  = PersistenceController.getEntityManager() ;
	    	ULDAgreementDetailsPK uldAgreementDetailsPk =
	    									new ULDAgreementDetailsPK() ;
	    	uldAgreementDetailsPk.setCompanyCode(companyCode );
	    	uldAgreementDetailsPk.setAgreementNumber(agreementNumber );
	    	uldAgreementDetailsPk.setSequenceNumber(sequenceNumber );
	    	return em.find(ULDAgreementDetails.class , uldAgreementDetailsPk) ;
    	}catch(FinderException finderException) {
    		throw new SystemException(finderException.getErrorCode()) ;
    	}
  	}


}
