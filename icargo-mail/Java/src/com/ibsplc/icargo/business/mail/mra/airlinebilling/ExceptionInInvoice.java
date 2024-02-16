/*
 * ExceptionInInvoice.java Created on Feb20, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.airlinebilling;

import java.util.Calendar;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.ExceptionInInvoiceFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.ExceptionInInvoiceVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoLovVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling.MRAAirlineBillingDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;

import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-2391 
 */
@Entity
//@Deprecated
@Table(name = "MALMRAARLEXPINV")
//@Staleable
public class ExceptionInInvoice {
	
	private ExceptionInInvoicePK exceptionInInvoicePK;
	
	private String airlineCode;
	 
	
	private String remark;
	private String exceptionStatus;
	private String memoStatus;
	private String memoCode;
	private Double provisionalAmount;
	private Double reportedAmount;
	private Double differenceAmount;
	private String contractCurrency;
	private String interlineBillingType;
	private String lastUpdatedUser;
    private Calendar lastUpdatedTime;
//  Added By Deepthi as a part of BUG35399
	private Collection<AirlineExceptions> airlineExceptions;
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING EXCEPTIONININVOICE");
	 /*
     * Module name
     */
	private static final String MODULE_NAME="mail.mra.airlinebilling";
	 public ExceptionInInvoice() {

	    }
	
	 /**
	  * @return the airlineExceptions
	  */
	 @OneToMany
		@JoinColumns( {
				@JoinColumn(name = "INVNUM", referencedColumnName = "INVNUM", insertable = false, updatable = false),
				@JoinColumn(name = "ARLIDR", referencedColumnName = "ARLIDR", insertable = false, updatable = false),
				@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
				@JoinColumn(name = "CLRPRD", referencedColumnName = "CLRPRD", insertable = false, updatable = false) })
	 public Collection<AirlineExceptions> getAirlineExceptions() {
		 return airlineExceptions;
	 }

	 /**
	  * @param airlineExceptions
	  *            the airlineExceptions to set
	  */
	 public void setAirlineExceptions(
			 Collection<AirlineExceptions> airlineExceptions) {
		 this.airlineExceptions = airlineExceptions;
	 }

	/**
	 * @return Returns the exceptionInInvoicePK.
	 */
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
		@AttributeOverride(name = "airlineIdentifier", column = @Column(name = "ARLIDR")),
		@AttributeOverride(name = "invoiceNumber", column = @Column(name = "INVNUM")),
		@AttributeOverride(name = "clearancePeriod", column = @Column(name = "CLRPRD"))
		 })
	public ExceptionInInvoicePK getExceptionInInvoicePK() {
		return exceptionInInvoicePK;
	}

	/**
	 * @param exceptionInInvoicePK The exceptionInInvoicePK to set.
	 */
	public void setExceptionInInvoicePK(ExceptionInInvoicePK exceptionInInvoicePK) {
		this.exceptionInInvoicePK = exceptionInInvoicePK;
	}

	

	/**
	 * @return Returns the airlineCode.
	 */
	@Column(name = "ARLCOD")
	public String getAirlineCode() {
		return airlineCode;
	}
	/**
	 * @param airlineCode The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	

	
	/**
	 * @return Returns the contractCurrency.
	 */
	@Column(name = "CRTCURCOD")
	public String getContractCurrency() {
		return contractCurrency;
	}

	/**
	 * @param contractCurrency The contractCurrency to set.
	 */
	public void setContractCurrency(String contractCurrency) {
		this.contractCurrency = contractCurrency;
	}

	/**
	 * @return Returns the differenceAmount.
	 */
	@Column(name = "DIFAMT")
	public Double getDifferenceAmount() {
		return differenceAmount;
	}

	/**
	 * @param differenceAmount The differenceAmount to set.
	 */
	public void setDifferenceAmount(Double differenceAmount) {
		this.differenceAmount = differenceAmount;
	}

	/**
	 * @return Returns the exceptionStatus.
	 */
	@Column(name = "EXPSTA")
	public String getExceptionStatus() {
		return exceptionStatus;
	}

	/**
	 * @param exceptionStatus The exceptionStatus to set.
	 */
	public void setExceptionStatus(String exceptionStatus) {
		this.exceptionStatus = exceptionStatus;
	}

	/**
	 * @return Returns the interlineBillingType.
	 */
	@Column(name = "INTBLGTYP")
	public String getInterlineBillingType() {
		return interlineBillingType;
	}

	/**
	 * @param interlineBillingType The interlineBillingType to set.
	 */
	public void setInterlineBillingType(String interlineBillingType) {
		this.interlineBillingType = interlineBillingType;
	}

	

	/**
	 * @return Returns the memoStatus.
	 */
	@Column(name = "MEMSTA")
	public String getMemoStatus() {
		return memoStatus;
	}

	/**
	 * @param memoStatus The memoStatus to set.
	 */
	public void setMemoStatus(String memoStatus) {
		this.memoStatus = memoStatus;
	}

	/**
	 * @return Returns the provisionalAmount.
	 */
	@Column(name = "PVNAMTLSTCUR")
	public Double getProvisionalAmount() {
		return provisionalAmount;
	}

	/**
	 * @param provisionalAmount The provisionalAmount to set.
	 */
	public void setProvisionalAmount(Double provisionalAmount) {
		this.provisionalAmount = provisionalAmount;
	}

	/**
	 * @return Returns the remark.
	 */
	@Column(name = "RMK")
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
	 * @return Returns the reportedAmount.
	 */
	@Column(name = "RPDAMT")
	public Double getReportedAmount() {
		return reportedAmount;
	}

	/**
	 * @param reportedAmount The reportedAmount to set.
	 */
	public void setReportedAmount(Double reportedAmount) {
		this.reportedAmount = reportedAmount;
	}
	

	
	/**
	 * @return Returns the memoCode.
	 */
    @Column(name = "MEMCOD")
	public String getMemoCode() {
		return memoCode;
	}

	/**
	 * @param memoCode The memoCode to set.
	 */
	public void setMemoCode(String memoCode) {
		this.memoCode = memoCode;
	}
	/**
	 * @return the lastUpdatedTime
	 */
	@Version
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}


	/**
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}


	/**
	 * @return the lastUpdatedUser
	 */
	@Column(name = "LSTUPDUSR")
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
     * @author A-2391
     * removes the entity
     * @throws RemoveException
     * @throws SystemException
     */
    public void remove()throws RemoveException,SystemException{
    	log.entering(MODULE_NAME,"remove");
		
	    	log.exiting(MODULE_NAME,"remove");
		PersistenceController.getEntityManager().remove(this);
    }

	
	/**
     * @author A-2391
     *  finds the entity
     * @param companyCode 
     * @param airlineIdentifier
     * @param invoiceNumber
     * @param clearancePeriod
     * @return ExceptionInInvoice
     * @throws SystemException
     * @throws FinderException
     */


    public static ExceptionInInvoice find(String companyCode,
            int airlineIdentifier,String invoiceNumber,String clearancePeriod)
    throws SystemException,FinderException {
    	ExceptionInInvoicePK pk = new ExceptionInInvoicePK();
		pk.setCompanyCode(   companyCode);
		pk.setAirlineIdentifier(   airlineIdentifier);
		pk.setInvoiceNumber(   invoiceNumber);
		pk.setClearancePeriod( clearancePeriod);
		return PersistenceController.getEntityManager().find(
				ExceptionInInvoice.class, pk);

    }
    /**
     *  findAirlineExceptionInInvoices
     *
     * @param exceptionInInvoiceFilterVO
     * @return Page<ExceptionInInvoiceVO>
     * @throws SystemException
     */
    public static Page<ExceptionInInvoiceVO> findAirlineExceptionInInvoices(
    		ExceptionInInvoiceFilterVO exceptionInInvoiceFilterVO)
    throws SystemException{
    	Page<ExceptionInInvoiceVO> exceptionInInvoiceVOs=null;
    	try{
    		exceptionInInvoiceVOs=constructDAO().findAirlineExceptionInInvoices(exceptionInInvoiceFilterVO);
	      }
    	catch(PersistenceException persistenceException) {
		persistenceException.getErrorCode();
	    }
        return exceptionInInvoiceVOs;
        }
    /**
     *  findAirlineExceptionInInvoicesForReport
     *
     * @param exceptionInInvoiceFilterVO
     * @return Collection<ExceptionInInvoiceVO>
     * @throws SystemException
     */
    public static Collection<ExceptionInInvoiceVO> findAirlineExceptionInInvoicesForReport(
    		ExceptionInInvoiceFilterVO exceptionInInvoiceFilterVO)
    throws SystemException{
    	Collection<ExceptionInInvoiceVO> exceptionInInvoiceVOs=null;
    	try{
    		exceptionInInvoiceVOs=constructDAO().findAirlineExceptionInInvoicesForReport(exceptionInInvoiceFilterVO);
	      }
    	catch(PersistenceException persistenceException) {
		persistenceException.getErrorCode();
	    }
        return exceptionInInvoiceVOs;
        }
    /**
     * method for calling up the DAO for the submodule
     * @author a-2391
     * @return queryDAO
     * @throws SystemException
     */
    private static MRAAirlineBillingDAO constructDAO()
    									throws SystemException {
    	MRAAirlineBillingDAO queryDAO =null;
        try {
			 queryDAO = (MRAAirlineBillingDAO)PersistenceController
			 								.getEntityManager()
							 				.getQueryDAO(MODULE_NAME);
        } catch (PersistenceException e) {
				throw new SystemException(e.getMessage(),e);
        }

        return queryDAO;
    }

	
    /**
     * 
     * @param companyCode
     * @param memoCode
     * @param pageNumber
     * @return Page<MemoLovVO>
     * @throws SystemException
     */
    public  static Page<MemoLovVO> displayMemoLOV
	(String companyCode, String memoCode, int pageNumber)throws SystemException{
		try{
			MRAAirlineBillingDAO mraAirlineBillingDAO = MRAAirlineBillingDAO.class.cast(
						PersistenceController.getEntityManager().getQueryDAO(MODULE_NAME));
			return mraAirlineBillingDAO.displayMemoLOV( companyCode,  memoCode, pageNumber);
		}catch( PersistenceException e){
			throw new SystemException( e.getErrorCode());
		}
	}



}
