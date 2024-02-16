/*
 * Range.java Created on Jul 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults;




import java.util.Calendar;
import java.util.Collection;


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.BlacklistStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsPersistenceConstants;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Represents the range of documents belonging to a particular stock
 * Aggregation of ranges constitute a stock
 * @author A-1358
 */
@Table(name="STKRNG")
@Entity
@Staleable
public class Range {

	private  Log log = LogFactory.getLogger("RANGE");
    /**
     * Range P
     */
    private RangePK rangePK;
    /**
     * Start range
     */
    private String startRange;
    /**
     * End Range
     */
    private String endRange;
    /**
     * Number of documents
     */
    private long numberOfDocuments;
    /**
     * manual flag
     */

    private String isManual;

    /**
     * Black List flag
     */
    //private String isBlackList;
    /**
     * ascii format of startRange
     */
    private long asciiStartRange;
    /**
     * ascii format of endRange
     */
    private long asciiEndRange;
    
    /*
     *	this represents the order in which the stock is allocated, 
     *	corresponding to which the reservation can be done
     * 
     */
    private Calendar stockAcceptanceDate;

    /**
     * Last updated date
     */
    private Calendar lastUpdateDate;
    /**
     * Last updated user
     */
    private String lastUpdateUser;
    /**
     * Default Constructor
     *
     */
    public Range(){

    }

    /**
     * @return endRange.
     *
     */
    @Column(name="ENDRNG")
    public String getEndRange() {
        return endRange;
    }
    /**
     * @param endRange
     */
    public void setEndRange(String endRange) {
        this.endRange = endRange;
    }
    /**
	 * @return Returns the stockAcceptanceDate.
	 */
    @Column(name="RNGACPDAT")
    @Temporal(TemporalType.DATE)
	public Calendar getStockAcceptanceDate() {
		return stockAcceptanceDate;
	}

	/**
	 * @param stockAcceptanceDate The stockAcceptanceDate to set.
	 */
	public void setStockAcceptanceDate(Calendar stockAcceptanceDate) {
		this.stockAcceptanceDate = stockAcceptanceDate;
	}

	/**
     * @return Returns the numberOfDocuments.
     *
     */
    @Column(name="DOCNUM")
    public long getNumberOfDocuments() {
        return numberOfDocuments;
    }
    /**
     * The numberOfDocuments to set.
     * @param numberOfDocuments
     */
    public void setNumberOfDocuments(long numberOfDocuments) {
        this.numberOfDocuments = numberOfDocuments;
    }
    /**
     * @return Returns the rangePK.
     *
     */
    @EmbeddedId
    @AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="airlineIdentifier",column=@Column(name="ARLIDR")),
		@AttributeOverride(name="stockHolderCode",column=@Column(name="STKHLDCOD")),
		@AttributeOverride(name="documentType",column=@Column(name="DOCTYP")),
		@AttributeOverride(name="documentSubType",column=@Column(name="DOCSUBTYP")),
		@AttributeOverride(name="rangeSequenceNumber",column=@Column(name="RNGSEQNUM"))}
		)
    public RangePK getRangePK() {
        return rangePK;
    }
    /**
     * @param rangePK The rangePK to set.
     */
    public void setRangePK(RangePK rangePK) {
        this.rangePK = rangePK;
    }
    /**
     * @return Returns the startRange.
     *
     */
    @Column(name="STARNG")
    public String getStartRange() {
        return startRange;
    }
    /**
     * The startRange to set.
     * @param startRange
     */
    public void setStartRange(String startRange) {
        this.startRange = startRange;
    }
    /**
     * @return Returns the lastUpdateDate.
     *
     */
    @Version
   @Column(name="LSTUPDTIM")
   @Temporal(TemporalType.TIMESTAMP)
    public Calendar getLastUpdateDate() {
        return lastUpdateDate;
    }
    /**
     * The lastUpdateDate to set.
     * @param lastUpdateDate
     */
    public void setLastUpdateDate(Calendar lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
    /**
     * @return Returns the lastUpdateUser.
     *
     */
    @Column(name="LSTUPDUSR")
    public String getLastUpdateUser() {
        return lastUpdateUser;
    }
    /**
     * The lastUpdateUser to set.
     * @param lastUpdateUser
     */
    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }
    /**
     * Black List flag
     * @return String
     */
   /* @Column(name="BLKLSTFLG")
	public String getIsBlackList() {
		return isBlackList;
	}

	public void setIsBlackList(String isBlackList) {
		this.isBlackList = isBlackList;
	}*/


    @Column(name="MNLFLG")
	public String getIsManual() {
		return isManual;
	}
    /**
     * Setting the manual flag
     * @param isManual
     */
	public void setIsManual(String isManual) {
		this.isManual = isManual;
	}
	/**
	 * getting the ascii format of endRange
	 * @return asciiEndRange
	 */
	@Column(name="ASCENDRNG")
	public long getAsciiEndRange() {
		return asciiEndRange;
	}
	/**
	 * setting ascii format of end range
	 * @param asciiEndRange
	 */
	public void setAsciiEndRange(long asciiEndRange) {
		this.asciiEndRange = asciiEndRange;
	}
	/**
	 * Getting the ascii format of startRange
	 * @return asciiStartRange
	 */
	@Column(name="ASCSTARNG")
	public long getAsciiStartRange() {
		return asciiStartRange;
	}
	/**
	 * Setting the ascii format for startRange
	 * @param asciiStartRange
	 */
	public void setAsciiStartRange(long asciiStartRange) {
		this.asciiStartRange = asciiStartRange;
	}
	/**
	 *
	 * @param companyCode
	 * @param stockHolderCode
	 * @param docType
	 * @param docSubType
	 * @param airlineIdentifier
	 * @param rangeVO
	 * @throws SystemException
	 */
	public Range(String companyCode,String stockHolderCode,
			String docType,String docSubType,int airlineIdentifier,RangeVO rangeVO)
	throws SystemException{
		log.log(Log.FINE,"------------>>Inside range ----->>>>");
		populatePK(companyCode,stockHolderCode,docType,docSubType,airlineIdentifier);
		populateAttribute(rangeVO);
		try{
		PersistenceController.getEntityManager().persist(this);
		}catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());
		}

	}

	/**
	 * 
	 * @param rangeVO
	 * @return
	 * @throws SystemException 
	 */
	public static Range find(RangeVO rangeVO) throws SystemException{
		Range range = null;
		RangePK rngePK = new RangePK();
		rngePK.setCompanyCode(rangeVO.getCompanyCode());
		rngePK.setAirlineIdentifier(rangeVO.getAirlineIdentifier() );
		rngePK.setDocumentSubType(rangeVO.getDocumentSubType());
		rngePK.setDocumentType(rangeVO.getDocumentType());
		rngePK.setRangeSequenceNumber(rangeVO.getSequenceNumber());
		rngePK.setStockHolderCode(rangeVO.getStockHolderCode());
		EntityManager entityManager;
		try {
			entityManager = PersistenceController.getEntityManager();
			range=entityManager.find(Range.class,rngePK);
		}catch (FinderException finderException) {
			// To be reviewed Auto-generated catch block
			throw new SystemException(finderException.getErrorCode());
		}
		return range;
	}
	
	/**
	 *Removing a particular range
	 * @throws SystemException
	 */
    public void remove()throws SystemException{
    	try{
      	PersistenceController.getEntityManager().remove(this);
    	}catch(RemoveException removeException){
    		throw new SystemException(removeException.getErrorCode());
    	}
    }

    /**
     * Method for calculating the base of number
     * @param input
     * @return base
     */
	public static long calculateBase(char input){
		long inside=input;
		long base=0;
		try{
			base=Integer.parseInt(""+input);
		}catch(NumberFormatException numberFormatException){
			base=inside-65;
		}
		return base;
	}

	/** To get the numeric value of the string
	 *
	 * @param range
	 * @return Numeric value
	 */
	public static long findLong(String range){
		char[] sArray=range.toCharArray();
		long base=1;
		long sNumber=0;
		for(int i=sArray.length-1;i>=0;i--){
			sNumber+=base*calculateBase(sArray[i]);
			int index=sArray[i];
			if (index>57) {
				base*=26;
			} else {
				base*=10;
			}
		}
		return sNumber;
	}

	/** To find the difference between range from and range to value
	 *
	 * @param rangeFrom Range from value
	 * @param rangeTo Range to value
	 * @return Difference between the ranges
	 */
	public static int difference(String rangeFrom,String rangeTo){

		long difference=findLong(rangeTo)-findLong(rangeFrom);
		difference++;
		return (int)difference;
	}
    /**
     *Method for populating the pk
     * @param companyCode
     * @param stockHolderCode
     * @param docType
     * @param docSubType
     * @param airlineIdentifier
     * @return
     */
    private void populatePK(String companyCode,String stockHolderCode,
    		String docType,String docSubType,int airlineIdentifier){
    	log.log(Log.FINE,"-->>Inside range populatePK-->>>>");
    	log.log(Log.FINE, "-->>companycode--->>>>", companyCode);
		log.log(Log.FINE, "-->>stockholdercode-->>>>", stockHolderCode);
		log.log(Log.FINE, "->>doctype---->>>>", docType);
		log.log(Log.FINE, "--->>docsubtype----->>>>", docSubType);
		RangePK rangePk=new RangePK();
    	rangePk.setCompanyCode(companyCode);
    	rangePk.setStockHolderCode(stockHolderCode);
    	rangePk.setDocumentType(docType);
    	rangePk.setDocumentSubType(docSubType);
    	rangePk.setAirlineIdentifier(airlineIdentifier);
    	this.rangePK=rangePk;

    }

    /**
     *This method is for populate other attributes of range
     *
     * @param rangeVO
     * @throws SystemException
     */
    private void populateAttribute(RangeVO rangeVO) throws SystemException{
       	LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
    	log.log(Log.FINE,"-->>Inside range populateAttri-->>>>");
    	log.log(Log.FINE, "-->>startRange-->>>", rangeVO.
    			getStartRange());
		log.log(Log.FINE, "-->>endRange--->>>>", rangeVO.
    			getEndRange());
		log.log(Log.FINE, "--->>No of docs----->>>>", rangeVO.
    			getNumberOfDocuments());
		log.log(Log.FINE, "-->>blacklistflag--->>>>", rangeVO.
    			isBlackList());
		log.log(Log.FINE, "-->>manual----->>>>", rangeVO.isManual());
		this.setStartRange(rangeVO.getStartRange());
    	this.setNumberOfDocuments(rangeVO.getNumberOfDocuments());
    	this.setEndRange(rangeVO.getEndRange());
    	this.setAsciiStartRange(rangeVO.getAsciiStartRange());
    	this.setAsciiEndRange(rangeVO.getAsciiEndRange());
    	this.setLastUpdateUser(rangeVO.getLastUpdateUser());
    	if(rangeVO.getStockAcceptanceDate() != null) {
    		this.setStockAcceptanceDate(rangeVO.getStockAcceptanceDate().toCalendar());
    	}else{
    		/*
    		 * added as per R2 business
    		 * in R1 this date is not set. 
    		 * but that date field in Range table is not null
    		 */    		
    		LocalDate ld = new LocalDate(
    				logonAttributes.getStationCode(),Location.ARP,true);
    		this.setStockAcceptanceDate(ld.toCalendar());
    	}
    	
    	if(rangeVO.isManual()){
    		this.setIsManual("Y");
    	}else{
    		this.setIsManual("N");
    	}
    }
    
    /**
     * Checks whether any Stockholder's Stock contains this awbNumber 
	 * inside the range. If so returns the stockHolder code
	 * otherwise throws exception
	 *  
     * @param companyCode
     * @param airlineId
     * @param documentType
     * @param documentNumber
     * @return
     * @throws SystemException
     */
    public static String checkDocumentExistsInAnyStock(String companyCode, 
    		int airlineId, String documentType, long documentNumber)
			throws SystemException{
    	try {
			return constructDAO().checkDocumentExistsInAnyStock(companyCode, 
										airlineId,documentType,documentNumber);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
    }

    
    /**
     * @param companyCode
     * @param airlineId
     * @param documentType
     * @param documentNumber
     * @return
     * @throws SystemException
     */
    public static String findSubTypeForDocument(String companyCode, int airlineId,
			String documentType, long documentNumber) throws SystemException{
    	try {
			return constructDAO().findSubTypeForDocument(companyCode, 
										airlineId,documentType,documentNumber);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
    	
    }
    
    /**
     * @author a-2434
     * @param Collection<RangeVO> rangeVOS
     * @return Collection
     * @throws SystemException
     */
    public static Collection<RangeVO> findRangesforMerge(Collection<RangeVO> rangeVOS) throws SystemException{
    	try {
			return constructDAO().findRangesforMerge(rangeVOS);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
    	
    }

	/**
	 *
	 * @param blacklistStockVO
	 * @return
	 * @throws SystemException
	 */
    public static boolean checkIfAWBBlackListed(BlacklistStockVO blacklistStockVO) throws SystemException {
		try {

			return constructDAO().checkIfAWBBlackListed(blacklistStockVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}

	}

	/**
	 *
	 * @param companyCode
	 * @param documentNumber
	 * @param documentType
	 * @param documentSubType
	 * @return
	 * @throws SystemException
	 */
	public static Boolean checkIfAWBIsVoid(String companyCode,  String documentNumber,
										   String documentType, String documentSubType) throws SystemException{
		try {

			return constructDAO().checkIFAWBIsVoid(companyCode, documentNumber,
					documentType, documentSubType);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}
    
    private static StockControlDefaultsDAO constructDAO()
    	throws SystemException, PersistenceException {
    	EntityManager em = PersistenceController.getEntityManager();
    	return StockControlDefaultsDAO.class.cast(em.getQueryDAO(
    			StockControlDefaultsPersistenceConstants.MODULE_NAME));
    }

  }
