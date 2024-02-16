
/*
 * ULDRepair.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.shared.currency.vo.CurrencyConvertorVO;

import com.ibsplc.icargo.business.uld.defaults.CurrencyConversionException;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairDetailsListVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairInvoiceVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.UldDmgRprFilterVO;
import com.ibsplc.icargo.business.uld.defaults.proxy.SharedCurrencyProxy;
import com.ibsplc.icargo.business.uld.defaults.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsPersistenceConstants;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1347
 *
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
@Table(name="ULDRPR")
@Entity
public class ULDRepair {

	private static final String MODULE="uld.defaults";
	private Log log = LogFactory.getLogger("ULD");
	
	private static final String RATETYPE_DAILY_FOR_CURRENCYCONVERSION = "DLY";
	
	
    private ULDRepairPK uldRepairPK;
    
    private String repairHead;
    private String repairStation;
    private Calendar repairDate;
    private double repairAmount;
    private double displayAmount;
    private String displayCurrency;
    private String repairRemarks;
    
    private String lastUpdateUser;
    private Calendar lastUpdateTime;
    private String invoiceRefNumber;
    private double waivedAmount;
    
    private static final String SYSPAR_BASE="system.defaults.unit.currency";
	
	private static final String SYSPAR_EXCHANGERATE="uld.defaults.exchangeratepriorityorder";
    private Set<ULDRepairDetails> repairDetails;
    //Added by A-7359 for ICRD-248560
    private static final String SYSPAR_ULDINVCURRENCY = "uld.defaults.uldinvoicingcurrency";
    
    
	/**
	 * 
     * Default constructor
     *
     */
	public ULDRepair() {

	}

	/**
     * constructor for the BO
     *
     * @param companyCode
     * @param uldNumber
     * @param repairSequenceNumber
     * @throws SystemException
     */
	public ULDRepair(String companyCode, String uldNumber, long repairSequenceNumber)
		throws SystemException {
	}
	
	/**
	 * 
	 * @param companyCode
	 * @param uldNumber
	 * @param uldRepairVO
	 * @throws SystemException
	 */
	public ULDRepair(String companyCode, String uldNumber , ULDRepairVO uldRepairVO)
	throws SystemException , CurrencyConversionException{
		log.entering("ULDRepair","Constructor");
		populatePk(companyCode , uldNumber);
		populateAttributes(uldRepairVO);
		
		log.log(Log.INFO, "companyCode", companyCode);
		log.log(Log.INFO, "AirlineId", uldRepairVO.getAirlineIdentifier());
		/*
		AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
		SharedAirlineProxy sharedAirlineProxy = 
			new SharedAirlineProxy();
		try{
			airlineValidationVO = 
				sharedAirlineProxy.findAirline(companyCode , uldRepairVO.getAirlineIdentifier());
			log.log(Log.INFO,"!!!!!!!AirlineBaseCurrency !!!!"+airlineValidationVO.getBaseCurrencyCode());
		}catch(ProxyException proxyException){
			throw new SystemException(proxyException.getMessage(),proxyException);
		}
		log.log(Log.INFO,"!!!!!!AirlineBaseCurrency"+airlineValidationVO.getBaseCurrencyCode());
		
		SharedCurrencyProxy sharedCurrencyProxy = 
			new SharedCurrencyProxy();
		double conversionFactor = 0 ;
		log.log(Log.INFO,"!!!!!currency"+uldRepairVO.getCurrency());
		try{
			conversionFactor = 
				sharedCurrencyProxy.
				findCurrencyConversionRate(
						companyCode ,
						uldRepairVO.getCurrency() , 
						airlineValidationVO.getBaseCurrencyCode());
		}catch(ProxyException proxyException){
			throw new SystemException(proxyException.getMessage(),proxyException);
		}*/
		CurrencyConvertorVO currencyConvertorVO=new CurrencyConvertorVO();
		Collection<String> systemparameterCodes = new  ArrayList<String>();
		Map<String,String> map = new HashMap<String,String>();
		systemparameterCodes.add(SYSPAR_ULDINVCURRENCY);
		systemparameterCodes.add(SYSPAR_EXCHANGERATE);
		/**
		 * Implementing Exchange Rates according to CRINT1122
		 */	
		SharedCurrencyProxy currencyProxy = new SharedCurrencyProxy();
		/*
		 * Getting system parameters for Exchange Rates and
		 * base Currency
		 */
		try {
			map = new SharedDefaultsProxy().findSystemParameterByCodes(systemparameterCodes);
		} catch (ProxyException proxyException) {
			new SystemException(proxyException.getErrors());
		}
		String sysparBaseCurrency = map.get(SYSPAR_ULDINVCURRENCY);
		String sysparExchangeRate = map.get(SYSPAR_EXCHANGERATE);
		String[] sysparRate=sysparExchangeRate.split(",");
		int length = sysparRate.length;
		
		log.log(Log.INFO, "!!!!!currency", uldRepairVO.getCurrency());
		//try{
		currencyConvertorVO.setCompanyCode(companyCode);
		currencyConvertorVO.setAirlineIdentifier(uldRepairVO.getAirlineIdentifier());
		currencyConvertorVO.setToCurrencyCode(sysparBaseCurrency);
		currencyConvertorVO.setRatePickUpDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
			
		currencyConvertorVO.setFromCurrencyCode(uldRepairVO.getCurrency());
		double conversionRate = 0;
		for(int i = 0;i<length;i++){
			currencyConvertorVO.setRatingBasisType(sysparRate[i]);
			conversionRate = new SharedCurrencyProxy().findExchangeRate(currencyConvertorVO);
			if(!(conversionRate == 0)){
				break;
			}
		}
			/*currencyRateVO = 
				sharedCurrencyProxy.findCurrencyConvertedRates(companyCode , RATETYPE_DAILY_FOR_CURRENCYCONVERSION , 
						uldRepairVO.getCurrency() , uldRepairVO.getDisplayAmount() , uldRepairVO.getAirlineIdentifier());*/
		//}catch(ProxyException proxyException){
		//	throw new CurrencyConversionException(CurrencyConversionException.CURRENCY_CONVERSION_IS_NOT_DEFINED);
			//throw new SystemException(proxyException.getMessage(),proxyException);
		//}
		if(conversionRate==0){
			CurrencyConversionException currencyConversionException = new CurrencyConversionException();

			currencyConversionException
					.addError(new ErrorVO(
							CurrencyConversionException.CURRENCY_CONVERSION_IS_NOT_DEFINED,
							new Object[] { uldRepairVO
									.getCurrency() }));
				throw currencyConversionException;
			 
		}
		log.log(Log.INFO, "!!!!!currencyRate ", conversionRate);
		log.log(Log.INFO, "!!!!!DisplayAmount", uldRepairVO.getDisplayAmount());
		//double convertedAmount = conversionFactor*uldRepairVO.getDisplayAmount();
		double convertedAmount = conversionRate*uldRepairVO.getDisplayAmount();
		log.log(Log.INFO, "!!!!!!convertedAmount", convertedAmount);
		this.setRepairAmount(convertedAmount);
		this.setDisplayCurrency(sysparBaseCurrency);
		EntityManager em = PersistenceController.getEntityManager();
		log.log(Log.INFO,"!!!!GOing to insert this");
		try{
			em.persist(this);
		}catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());
		}
		uldRepairVO.setRepairSequenceNumber(this.getUldRepairPK().getRepairSequenceNumber());
		populateChildren(companyCode , uldNumber , uldRepairVO);
	}
	/**
	 * 
	 * @param companyCode
	 * @param uldNumber
	 * @param damageReferenceNumber
	 * @param repairSerialNumber
	 */	
	private void populatePk(String companyCode, String uldNumber) {
		log.entering("ULDRepair","populatePk");
		ULDRepairPK repairPK = new ULDRepairPK();
		repairPK.setCompanyCode(companyCode);
		repairPK.setUldNumber(uldNumber);
		this.setUldRepairPK(repairPK);
		log.exiting("ULDRepair","populatePk");
	}

	/**
	 * 
	 * @param uldRepairVO
	 * @throws SystemException
	 */
	private void populateAttributes(ULDRepairVO uldRepairVO)
		throws SystemException {
		log.entering("ULDRepair","populateAttributes");

		this.setDisplayAmount(uldRepairVO.getDisplayAmount());
		this.setDisplayCurrency(uldRepairVO.getCurrency());
		this.setLastUpdateUser(uldRepairVO.getLastUpdateUser());
		this.setRepairDate(uldRepairVO.getRepairDate());
		this.setRepairHead(uldRepairVO.getRepairHead());
		this.setRepairRemarks(uldRepairVO.getRemarks());
		this.setRepairStation(uldRepairVO.getRepairStation());
		log.exiting("ULDRepair","populateAttributes");
	}
	   
    
	/**
	 * 
	 * @param companyCode
	 * @param uldNumber
	 * @param uldRepairVO
	 * @throws SystemException
	 */
    public void populateChildren(String companyCode , String uldNumber , ULDRepairVO uldRepairVO) 
		throws SystemException {
    	log.entering("ULDRepair","populateChildren");
    	log.log(Log.INFO,"!!!!!Going to Child");
    	new ULDRepairDetails(companyCode , uldNumber , 
    						uldRepairVO.getRepairSequenceNumber() , 
    						uldRepairVO.getDamageReferenceNumber());
    }
	
	
	/**
	 * 
	 * @param companyCode
	 * @param uldNumber
	 * @param repairSerialNumber
	 * @return
	 * @throws SystemException
	 */
	public static ULDRepair find(
	        String companyCode, String uldNumber, long repairSerialNumber) 
	throws SystemException{
		ULDRepairPK repairPK = new ULDRepairPK();
		ULDRepair uldRepair = new ULDRepair();
		repairPK.setCompanyCode(companyCode);
		repairPK.setUldNumber(uldNumber);
		repairPK.setRepairSequenceNumber(repairSerialNumber);
	    EntityManager em = PersistenceController.getEntityManager();
	    try{
	    	uldRepair = em.find(ULDRepair.class , repairPK);
	    }catch(FinderException finderException){
	    	throw new SystemException(finderException.getErrorCode());
	    }
	    return uldRepair;
	}
	

	/**
	 * 
	 * @param uldRepairVO
	 * @throws SystemException
	 */
    public void update(ULDRepairVO uldRepairVO)
   		throws SystemException {
    	log.entering("ULDRepair","update");
    	this.setDisplayCurrency(uldRepairVO.getCurrency());
		this.setLastUpdateUser(uldRepairVO.getLastUpdateUser());
		this.setRepairAmount(uldRepairVO.getAmount());
		this.setRepairDate(uldRepairVO.getRepairDate());
		this.setRepairHead(uldRepairVO.getRepairHead());
		this.setRepairRemarks(uldRepairVO.getRemarks());
		this.setRepairStation(uldRepairVO.getRepairStation());
		this.setLastUpdateTime(uldRepairVO.getLastUpdateTime());
    }

	/**
     * method to remove BO
     *
     * @throws SystemException
     */
    public void remove() throws SystemException {
    	ULDRepairDetails repairDetail = new ULDRepairDetails();
    	log.entering("ULDRepair","update");
    	if(this.repairDetails != null && this.repairDetails.size() > 0){
    		repairDetail.remove();
    	}
    	log.log(Log.INFO,"!!!!GOing ot remove this");
    	EntityManager em = PersistenceController.getEntityManager();
    	try{
    		em.remove(this);
    	}catch(RemoveException removeException){
    		throw new SystemException(removeException.getErrorCode()) ;
    	}
	}

    /**
     * 
     * @param uldRepairFilterVO
     * @return
     * @throws SystemException
     */
    public Page<ULDRepairDetailsListVO> 
    listULDRepairDetails(ULDRepairFilterVO uldRepairFilterVO)
    throws SystemException{
    	EntityManager em = PersistenceController.getEntityManager();
    	try{
    		ULDDefaultsDAO uldDefaultsDAO = 
    			ULDDefaultsDAO.class.cast(
    					em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
    		return uldDefaultsDAO.listULDRepairDetails(uldRepairFilterVO);
    	}catch(PersistenceException persistenceException){ 
    		throw new SystemException(persistenceException.getErrorCode());
    	}
    }
    
   
    /**
     */
    public void removeChildren() {
    }
    
    
    /**
     * This method retrieves the repair head details for invoicing.
     * @author A-1883
     * @param companyCode
     * @param invoiceRefNumber
     * @return ULDRepairInvoiceVO
     * @throws SystemException
     */
	public static ULDRepairInvoiceVO findRepairInvoiceDetails(String companyCode,
    		String invoiceRefNumber)
    throws SystemException {   
		 Log log =LogFactory.getLogger("ULD_MANAGEMENT");
		 log.entering("ULDRepair","findRepairInvoiceDetails");
 	    	return constructDAO().findRepairInvoiceDetails(companyCode,invoiceRefNumber);
    }

	/**
     * @author A-1883
     * @return ULDDefaultsDAO
     * @throws SystemException
     */
    private static ULDDefaultsDAO constructDAO() throws SystemException {
        try {
            EntityManager em = PersistenceController.getEntityManager();
            return ULDDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
        } catch (PersistenceException persistenceException) {
        	persistenceException.getErrorCode();
            throw new SystemException(persistenceException.getMessage());
        }
}
    
    /**
     * @author a-3093
     * @param uldRepairFilterVO
     * @param displayPage
     * @return
     * @throws SystemException
     */
    public Page<ULDRepairDetailsListVO> listDamageRepairDetails(UldDmgRprFilterVO uldRepairFilterVO ,int displayPage)
    throws SystemException{
    	EntityManager em = PersistenceController.getEntityManager();
    	try{
    		ULDDefaultsDAO uldDefaultsDAO = 
    			ULDDefaultsDAO.class.cast(
    					em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
    		return uldDefaultsDAO.listDamageRepairDetails(uldRepairFilterVO ,displayPage);
    	}catch(PersistenceException persistenceException){
    		throw new SystemException(persistenceException.getErrorCode());
    	}
    }
	/**
	 * @return Returns the displayAmount.
	 */
    @Column(name = "DISRPRAMT")
	public double getDisplayAmount() {
		return displayAmount;
	}

	/**
	 * @param displayAmount The displayAmount to set.
	 */
	public void setDisplayAmount(double displayAmount) {
		this.displayAmount = displayAmount;
	}


	/**
	 * @return Returns the displayCurrency.
	 */
	@Column(name = "DISRPRCUR")
	public String getDisplayCurrency() {
		return displayCurrency;
	}

	/**
	 * @param displayCurrency The displayCurrency to set.
	 */
	public void setDisplayCurrency(String displayCurrency) {
		this.displayCurrency = displayCurrency;
	}

	
	/**
	 * @return Returns the lastUpdateUser.
	 */
	@Column(name = "LSTUPDUSR")
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
	 * @return Returns the repairAmount.
	 */
	@Column(name = "RPRAMT")
	@Audit(name = "RepairAmount")
	public double getRepairAmount() {
		return repairAmount;
	}

	/**
	 * @param repairAmount The repairAmount to set.
	 */
	public void setRepairAmount(double repairAmount) {
		this.repairAmount = repairAmount;
	}

	
	/**
	 * @return Returns the repairDate.
	 */
	
	@Column(name = "RPRDAT")
	@Audit(name = "RepairDate")

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getRepairDate() {
		return repairDate;
	}

	/**
	 * @param repairDate The repairDate to set.
	 */
	public void setRepairDate(Calendar repairDate) {
		this.repairDate = repairDate;
	}


	/**
	 * @return Returns the repairHead.
	 */
	@Column(name = "RPRHED")
	@Audit(name = "RepairHead")
	public String getRepairHead() {
		return repairHead;
	}

	/**
	 * @param repairHead The repairHead to set.
	 */
	public void setRepairHead(String repairHead) {
		this.repairHead = repairHead;
	}

	
	/**
	 * @return Returns the repairRemarks.
	 */
	@Column(name = "RPRRMK")
	public String getRepairRemarks() {
		return repairRemarks;
	}

	/**
	 * @param repairRemarks The repairRemarks to set.
	 */
	public void setRepairRemarks(String repairRemarks) {
		this.repairRemarks = repairRemarks;
	}

	
	/**
	 * @return Returns the repairStation.
	 */
	@Column(name = "RPRARP")
	@Audit(name = "RepairStation")
	public String getRepairStation() {
		return repairStation;
	}

	/**
	 * @param repairStation The repairStation to set.
	 */
	public void setRepairStation(String repairStation) {
		this.repairStation = repairStation;
	}
	
	/**
	 * @return Returns the uldRepairPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "companyCode",column = @Column(name = "CMPCOD")),
		@AttributeOverride(name = "uldNumber",column = @Column(name = "ULDNUM")),
		@AttributeOverride(name = "repairSequenceNumber",column = @Column(name = "RPRSEQNUM"))})
	    public ULDRepairPK getUldRepairPK() {
		return uldRepairPK;
	}

	/**
	 * @param uldRepairPK The uldRepairPK to set.
	 */
	public void setUldRepairPK(ULDRepairPK uldRepairPK) {
		this.uldRepairPK = uldRepairPK;
	} 
    

	/**
	 * @return Returns the repairDetails.
	 */
	
	@OneToMany
	@JoinColumns( {
	@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD",insertable=false, updatable=false),
	@JoinColumn(name = "ULDNUM", referencedColumnName = "ULDNUM",insertable=false, updatable=false),
	@JoinColumn(name = "RPRSEQNUM", referencedColumnName = "RPRSEQNUM",insertable=false, updatable=false)})			
    public Set<ULDRepairDetails> getRepairDetails() {
		return repairDetails;
	}	

	/**
	 * @param repairDetails The repairDetails to set.
	 */
	public void setRepairDetails(Set<ULDRepairDetails> repairDetails) {
		this.repairDetails = repairDetails;
	}
	
	
	/**
	 * @return Returns the lastUpdateTime.
	 */
	@Version
	@Column(name = "LSTUPDTIM")

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
	 * @return Returns the invoiceRefNumber.
	 */
	@Column(name="INVREFNUM")
	@Audit(name = "InvoiceRefNumber")
	public String getInvoiceRefNumber() {
		return invoiceRefNumber;
	}

	/**
	 * @param invoiceRefNumber The invoiceRefNumber to set.
	 */
	public void setInvoiceRefNumber(String invoiceRefNumber) {
		this.invoiceRefNumber = invoiceRefNumber;
	}

	/**
	 * @return Returns the waivedAmount.
	 */
	@Column(name = "WVDAMT")
	public double getWaivedAmount() {
		return waivedAmount;
	}

	/**
	 * @param waivedAmount The waivedAmount to set.
	 */
	public void setWaivedAmount(double waivedAmount) {
		this.waivedAmount = waivedAmount;
	}

}
