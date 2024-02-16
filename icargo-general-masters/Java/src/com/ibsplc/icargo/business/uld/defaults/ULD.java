/*
 * ULD.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.operations.flthandling.vo.OperationalULDAuditFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.OperationalULDAuditVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.business.shared.currency.vo.CurrencyConvertorVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeFilterVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeValidationVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDValidationFilterVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMMessageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMValidationFilterVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMValidationVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDHistoryVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO;
import com.ibsplc.icargo.business.uld.defaults.proxy.SharedAirlineProxy;
import com.ibsplc.icargo.business.uld.defaults.proxy.SharedCurrencyProxy;
import com.ibsplc.icargo.business.uld.defaults.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.uld.defaults.proxy.SharedULDProxy;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.EstimatedULDStockFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.EstimatedULDStockVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ExcessStockAirportFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ExcessStockAirportVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.ULDInTransactionException;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.vo.RelocateULDVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDNumberVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDValidationVO;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsPersistenceConstants;
//import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
//import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
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
 * This class is used to persist the ULD Details.
 *
 * @author A-1347
 *
 * @generated "UML to Java
 *            (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
// @Staleable
@Table(name = "ULDMST")
@Entity
/**
 *
 *
 */
public class ULD {

	private ULDPK uldPK;

	private String uldContour;

	private double tareWeight;

	private double displayTareWeight;

	private String displayTareWeightUnit;

	private double structuralWeight; 

	private double displayStructuralWeight;

	private String displayStructuralWeightUnit;

	private double baseLength;

	private double displayBaseLength;

	private double baseWidth;

	private double displayBaseWidth;

	private double baseHeight;

	private double displayBaseHeight;

	private String displayDimensionUnit;

	private String uldType;

	private int operationalAirlineIdentifier;

	private int ownerAirlineIdentifier;

	private String currentStation;

	private String ownerStation;

	private String damageStatus;

	private String overallStatus;

	private String cleanlinessStatus;

	private String location;

	private String vendor;

	private String manufacturer;

	private String uldSerialNumber;

	private Calendar purchaseDate;

	private String purchaseInvoiceNumber;

	private double displayUldPrice;

	private double uldPrice;

	private String uldPriceUnit;

	private double displayIataReplacementCost;

	private double IataReplacementCost;

	private String iataReplacementCostUnit;

	private double displayCurrentValue;

	private double currentValue;

	private String currentValueUnit;

	// Added for damage report capture

	// private byte[] damagePicture;

	private String repairStatus;

	private String repairSupervisor;

	private String investigationReport;

	// Added by AG (A-1496) for ULD Transaction

	private int loanReferenceNumber;

	private int borrowReferenceNumber;

	private Calendar lastUpdateTime;

	private String lastUpdateUser;

	private String remarks;

	private Calendar lastMovementDate;

	// intransit / Not Intransit
	private String transitStatus;

	//Added By A-6841 for CRQ ICRD-155382
	private String occupancyStatus;
	private String facilityType;

	private String controlReceiptNumber;

	private String uldNature;

	private Calendar depreciationDate;

	private Calendar lostDate;

	private Calendar abandonedDate;
	
	// Added by Sreekumar S
	private String uldGroupCode;
	
	// Added by Preet for AirNZ CR 447
	private Calendar manufactureDate;
	private int lifeSpan;
    private String tsoNumber;
	// QF1501
    private String agentCode;
    private String agentStation;
    
    //Added by Preet for AirNZ 449
    // denotes the agent to whom ULD is released
    private String releasedTo;
    
	private static final String LOST_STATUS = "L";
	
	private static final String DAMAGE_STATUS_SCRAPPED = "S";
	
	private static final String OVERALL_STATUS_UNSERVICEABLE = "N";

	private static final String MODULE = "uld.defaults";

	private static final Log log = LogFactory.getLogger(" ULD DEFAULTS");
	
	private static final String SYSPAR_BASE="system.defaults.unit.currency";
	
	private static final String SYSPAR_EXCHANGERATE="uld.defaults.exchangeratepriorityorder";
	//Added by A-7426 as part of ICRD-198430 starts
	private static final String REPAIRED_STATUS = "R";
	private static final String DAMAGED_STATUS = "D";
	private static final String NOT_DAMAGED_STATUS = "N";
	public static final String OPERATIONAL_STATUS="O";   
	private static final String NIL = "NIL";
    //Added by A-7426 as part of ICRD-198430 ends
	//added by A-2408 for QF1013 starts
	
	/**
	 * This flag indicates whether SCM has been received for this ULD from ant station
	 * its value will be Y if SCM is recieved and N if ULD is missing in the expected SCM
	 * by default it should be null
	 */
	private String scmFlag;
	/**
	 * This indicates the date on which SCM is first received from this ULD's current station.
	 */
	private Calendar scmDate;
	
	//ends

	private String content;
	
	 private String flightInfo;
	 
	private Calendar lastSCMDate;
	
	/**
	 * Added by A-7131 for ICRD-213319
	 */
	private String lastMovementDetails;
	/**
	 * @return the lastSCMDate
	 */
	@Column(name = "LSTSCMDAT")
	public Calendar getLastSCMDate() {
		return lastSCMDate;
	}

	/**
	 * @param lastSCMDate the lastSCMDate to set
	 */
	public void setLastSCMDate(Calendar lastSCMDate) {
		this.lastSCMDate = lastSCMDate;
	}

	/**
	 * @return the flightInfo
	 */
	 @Column(name = "FLTDTL")
	public String getFlightInfo() {
		return flightInfo;
	}

	/**
	 * @param flightInfo the flightInfo to set
	 */
	public void setFlightInfo(String flightInfo) {
		this.flightInfo = flightInfo;
	}

	/**
	 * @return the content
	 */
	@Column(name = "CNT")
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the agentCode
	 */
	@Column(name="AGTCOD")
	public String getAgentCode() {
		return agentCode;
	}

	/**
	 * @param agentCode the agentCode to set
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	/**
	 * @return the agentStation
	 */
	@Column(name="STNCOD")
	public String getAgentStation() {
		return agentStation;
	}

	/**
	 * @param agentStation the agentStation to set
	 */
	public void setAgentStation(String agentStation) {
		this.agentStation = agentStation;
	}
	/**
	 * Constructor
	 */
	public ULD() {
	}

	/**
	 * Constructor
	 *
	 * @param uldVO
	 * @throws SystemException
	 */
	public ULD(ULDVO uldVO) throws SystemException,
			CurrencyConversionException, DimensionConversionException {
		log.entering(MODULE, "ULD");
		try {
			EntityManager em = PersistenceController.getEntityManager();
			populatePk(uldVO);
			// Added by Preet on 26th Feb for setting Current value starts
			uldVO.setDisplayCurrentValue(uldVO.getDisplayUldPrice());
			uldVO.setCurrentValueUnit(uldVO.getUldPriceUnit());			
			//Added by Preet on 26th Feb for setting Current value ends			
			populateAttributes(uldVO);
			//Modified by A-7359 for ICRD-233082 starts here
			if(this.getPurchaseDate()==null){
				this.setPurchaseDate((new LocalDate(uldVO.getCurrentStation(),
						Location.ARP, true)));
			}
			//Modified by A-7359 for ICRD-233082 ends here
            em.persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}

	/**
	 * This method is used to convert the user specified units to system units.
	 *
	 * @param uldVO
	 * @throws SystemException
	 */
	private void convertUnits(ULDVO uldVO) throws SystemException,
			CurrencyConversionException, DimensionConversionException {

		populateWeight(uldVO);
		populateCurrency(uldVO);
		populateDimensions(uldVO);
	}

	/**
	 * private method to populate PK
	 *
	 * @param uldVO
	 * @throws SystemException
	 */
	public void populatePk(ULDVO uldVO) throws SystemException {
		ULDPK pk = new ULDPK();
		pk.setCompanyCode(uldVO.getCompanyCode());
		pk.setUldNumber(uldVO.getUldNumber());
		this.setUldPK(pk);
	}

	/**
	 *
	 *
	 * @param uldVO
	 * @throws SystemException
	 */
	public void populateAttributes(ULDVO uldVO) throws SystemException,
			CurrencyConversionException, DimensionConversionException {
		log
				.log(
						Log.FINE,
						"THE ULD VO IN THE POPULATE ATTRIBUTES IS FOUND TO BE  ",
						uldVO);
		this.setUldContour(uldVO.getUldContour());
	    this.setDamageStatus(uldVO.getDamageStatus());
		this.setBorrowReferenceNumber(uldVO.getBorrowReferenceNumber());
		this.setCleanlinessStatus(uldVO.getCleanlinessStatus());
		this.setCurrentStation(uldVO.getCurrentStation());
		// this.setDamageStatus(uldVO.getDamageStatus());
		if(uldVO.getDisplayCurrentValue() > 0) {
		this.setDisplayCurrentValue(uldVO.getDisplayCurrentValue());
		}
		else {
			this.setDisplayCurrentValue(uldVO.getCurrentValue());
		}			
		
		this.setCurrentValueUnit(uldVO.getCurrentValueUnit());
		this.setDisplayBaseHeight(uldVO.getBaseHeight()!=null?uldVO.getBaseHeight().getRoundedDisplayValue():0.0);
		this.setDisplayBaseLength(uldVO.getBaseLength()!=null?uldVO.getBaseLength().getRoundedDisplayValue():0.0);
		this.setDisplayBaseWidth(uldVO.getBaseWidth()!=null?uldVO.getBaseWidth().getRoundedDisplayValue():0.0);
		if(uldVO.getBaseWidth()!=null)
		this.setDisplayDimensionUnit(uldVO.getBaseWidth().getDisplayUnit());
		this.setDisplayStructuralWeight(uldVO.getStructuralWeight()!=null?uldVO.getStructuralWeight().getRoundedDisplayValue():0.0);
		//modified by A-7815
		if(uldVO.getStructuralWeightUnit()!=null) {
		this.setDisplayStructuralWeightUnit(uldVO.getStructuralWeightUnit());
		} else if(uldVO.getStructuralWeight()!=null) {
			this.setDisplayStructuralWeightUnit(uldVO.getStructuralWeight().getDisplayUnit());
		}
		this.setDisplayTareWeight(uldVO.getTareWeight()!=null?uldVO.getTareWeight().getRoundedDisplayValue():0.0);
		this.setDisplayTareWeightUnit(uldVO.getTareWeight()!=null?uldVO.getTareWeight().getDisplayUnit():null);
		this.setDisplayIataReplacementCost(uldVO
				.getDisplayIataReplacementCost());
		this.setIataReplacementCostUnit(uldVO
				.getDisplayIataReplacementCostUnit());
		if(uldVO.getDisplayUldPrice() > 0) {
		this.setDisplayUldPrice(uldVO.getDisplayUldPrice());
		}
		else {
			this.setDisplayUldPrice(uldVO.getUldPrice());
		}
		
		this.setUldPriceUnit(uldVO.getUldPriceUnit());
		if(uldVO.getLastUpdateUser()!=null)
		this.setLastUpdateUser(uldVO.getLastUpdateUser());
		this.setLoanReferenceNumber(uldVO.getLoanReferenceNumber());
		// Null check Added By A-7359 for ICRD-209902 starts here
		if (uldVO.getLocation() != null) {
		this.setLocation(uldVO.getLocation());
		} else {
			this.setLocation(NIL);
		}
		// Null check Added By A-7359 for ICRD-209902 ends here
		this.setManufacturer(uldVO.getManufacturer());
		this.setOperationalAirlineIdentifier(uldVO
				.getOperationalAirlineIdentifier());
		//Modified by A-3415 for ICRD-113953
		//if(DAMAGE_STATUS_SCRAPPED.equals(uldVO.getDamageStatus())){
		if(isULDNonOperational(uldVO.getDamageStatus())){
			this.setOverallStatus(OVERALL_STATUS_UNSERVICEABLE);
		}else{
			this.setOverallStatus(uldVO.getOverallStatus());
		}
		//modified for bug 38575 on 27Feb06
		/*if (LOST_STATUS.equals(uldVO.getOverallStatus())
				&& uldVO.getLostDate() != null
				&& uldVO.getLostDate().toString().trim().length() > 0) {
			this.setLostDate(uldVO.getLostDate().toCalendar());
		}*/

		//modified for bug 38575 on 27Feb06 ends
		this.setOwnerAirlineIdentifier(uldVO.getOwnerAirlineIdentifier());
		this.setOwnerStation(uldVO.getOwnerStation());
		if (uldVO.getPurchaseDate() != null) {
			this.setPurchaseDate(uldVO.getPurchaseDate().toCalendar());
		}
		this.setUldSerialNumber(uldVO.getUldSerialNumber());
		this.setUldType(uldVO.getUldType());
		this.setVendor(uldVO.getVendor());
		this.setPurchaseInvoiceNumber(uldVO.getPurchaseInvoiceNumber());
		this.setRemarks(uldVO.getRemarks());
		this.setTransitStatus(uldVO.getTransitStatus());
		//Added By A-6841 for CRQ ICRD-155382
		//Null check Added By A-7359 for ICRD-204232 starts here
		if (uldVO.getOccupiedULDFlag() != null) { 
			this.setOccupancyStatus(uldVO.getOccupiedULDFlag());
		}else{
			this.setOccupancyStatus("N");
		}
		// Null check Added By A-7359 for ICRD-204232 ends here
		//Null check Added By A-7359 for ICRD-209902 starts here
		if(uldVO.getFacilityType()!=null){
		this.setFacilityType(uldVO.getFacilityType());
		}else{
			this.setFacilityType(NIL);
		}
		// Null check Added By A-7359 for ICRD-209902 ends here
		this.setControlReceiptNumber(uldVO.getControlReceiptNumber());
		//this.setDepreciationDate(new LocalDate(uldVO.getCurrentStation(),Location.ARP, true));
		//if check added by a-3278 for bug 38599 on 26Feb09
		if(!uldVO.getCurrentStation().equals(this.getCurrentStation()) || ULDVO.OPERATION_FLAG_INSERT.equals(uldVO.getOperationalFlag())){
			this.setLastMovementDate((new LocalDate(uldVO.getCurrentStation(),
					Location.ARP, true)));
		}
		//a-3278 ends
		//added by nisha for bugfix on 27Jun08 starts
		if(uldVO.getUldNature() == null || uldVO.getUldNature().trim().length() == 0){
			this.setUldNature("GEN");
		}else{
			this.setUldNature(uldVO.getUldNature());
		}
		//ends
		this.setUldGroupCode(uldVO.getUldGroupCode());
		// Added by Preet for AirNZ CR 447 starts
		if (uldVO.getManufactureDate() != null) {
			this.setManufactureDate(uldVO.getManufactureDate().toCalendar());
		}
		this.setTsoNumber(uldVO.getTsoNumber());
		log.log(Log.INFO, "INSIDE UPDATE ULD", uldVO.getLifeSpan());
		this.setLifeSpan(uldVO.getLifeSpan());
		// Added by Preet for AirNZ CR 447 ends
		//added by A-2408 for QF1013 starts
		if(uldVO.getScmDate()!=null){
			this.setScmDate(uldVO.getScmDate().toCalendar());
		}else{
			this.setScmDate(null);
		}
		this.setScmFlag(uldVO.getScmFlag());
		//ends
		/* Added by a-3278 for CR QF1449 on 02Mar10
		 * A new field LSTSCMDAT in the ULDMST table is added to keep track of the last SCM date for a ULD, 
		 * populated every time an SCM strikes the system with the ULD
		 **/
		if(uldVO.getLastSCMDate()!=null){
			this.setLastSCMDate(uldVO.getLastSCMDate().toCalendar());
		}else{
			this.setLastSCMDate(null);
		}
		//CR QF1449 ends
		convertUnits(uldVO);
	}

	/**
	 * This method is used to populate the Weight corresponding to the
	 * ParticularAirline
	 * 
	 * @param uldVO
	 * @throws SystemException
	 */
	private void populateWeight(ULDVO uldVO) throws SystemException {
		SharedDefaultsProxy defaultsProxy = new SharedDefaultsProxy();
		log.log(Log.INFO, "INSIDE THE POPULATE WEIGHT");
		setTareWeight(uldVO.getTareWeight()!=null?uldVO.getTareWeight().getSystemValue()/* As part of correcting the Measure usage in setting value to entity classes ICRD-288684 */:0.0);
		setStructuralWeight(uldVO.getStructuralWeight()!=null?uldVO.getStructuralWeight().getSystemValue()/* As part of correcting the Measure usage in setting value to entity classes ICRD-288684 */:0.0);
		/*if (getDisplayTareWeight() > 0 && getDisplayTareWeightUnit() != null
				&& getDisplayTareWeightUnit().trim().length() > 0) {

			try {

				log.log(Log.INFO, "\nINSIDE THE POPULATE WEIGHT",
						UnitConversionVO.UNIT_TYPE_WEIGHT);
				log.log(Log.INFO, "INSIDE THE POPULATE WEIGHT",
						getDisplayTareWeightUnit());
				log.log(Log.INFO, "INSIDE THE POPULATE WEIGHT",
						getDisplayTareWeight());
				double tareWgt = defaultsProxy.findSystemUnitValue(
						getUldPK().getCompanyCode(),
						UnitConversionVO.UNIT_TYPE_WEIGHT,
						getDisplayTareWeightUnit(), getDisplayTareWeight())
						.getToValue();
				log.log(Log.FINE, "THE TAREWEIGHT AFTER CONVERSION IS ",
						tareWgt);
				setTareWeight(tareWgt);
			} catch (ProxyException ex) {
				throw new SystemException(ex.getMessage(), ex);
			 }
	      }

		if (getDisplayStructuralWeight() > 0
				&& getDisplayStructuralWeightUnit() != null
				&& getDisplayStructuralWeightUnit().trim().length() > 0) {
			try {
				log.log(Log.INFO, "\nINSIDE THE POPULATE STRUCTURAL WEIGHT",
						UnitConversionVO.UNIT_TYPE_WEIGHT);
				log.log(Log.INFO, "INSIDE THE POPULATE STRUCTURAL WEIGHT",
						getDisplayStructuralWeightUnit());
				log.log(Log.INFO, "INSIDE THE POPULATE STRUCTURAL WEIGHT",
						getDisplayStructuralWeight());
				double structuralWgt = defaultsProxy.findSystemUnitValue(
						getUldPK().getCompanyCode(),
						UnitConversionVO.UNIT_TYPE_WEIGHT,
						getDisplayStructuralWeightUnit(),
						getDisplayStructuralWeight()).getToValue();
				log.log(Log.FINE, "THE  STRUCTURALWEIGHT AFTER CONVERSION IS ",
						structuralWgt);
				setStructuralWeight(structuralWgt);
			} catch (ProxyException ex) {
				throw new SystemException(ex.getMessage(), ex);
			  }

		 }*/

	}

	/**
	 * This method is used to poulate the currency corresponding to the
	 * particular airline
	 * 
	 * @param uldVO
	 * @throws SystemException
	 */
	private void populateCurrency(ULDVO uldVO) throws SystemException,
			CurrencyConversionException {
			
		CurrencyConvertorVO currencyConvertorVO=new CurrencyConvertorVO();
		Collection<String> systemparameterCodes = new  ArrayList<String>();
		Map<String,String> map = new HashMap<String,String>();
		systemparameterCodes.add(SYSPAR_BASE);
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
			map = Proxy.getInstance().get(SharedDefaultsProxy.class).findSystemParameterByCodes(systemparameterCodes);
		} catch (ProxyException proxyException) {
			new SystemException(proxyException.getErrors());
		}
		String sysparBaseCurrency = map.get(SYSPAR_BASE);
		String sysparExchangeRate = map.get(SYSPAR_EXCHANGERATE);
		String[] sysparRate=sysparExchangeRate.split(",");
		int length = sysparRate.length;
		log.log(Log.INFO, "INSIDE THE POPULATE CURREENCY ");
		log.log(Log.FINE, "THE DISPLAY ULD PRICE IS", getDisplayUldPrice());
		log.log(Log.FINE, "THE DISPLAY ULD PRICE IS", getUldPriceUnit());
		log.log(Log.FINE, "THE  AIRLINE IDENTIFIER IS", uldVO.getOperationalAirlineIdentifier());
		currencyConvertorVO.setCompanyCode(getUldPK().getCompanyCode());
		currencyConvertorVO.setAirlineIdentifier(uldVO.getOperationalAirlineIdentifier());
		currencyConvertorVO.setToCurrencyCode(sysparBaseCurrency);
		currencyConvertorVO.setRatePickUpDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
			
	if (getDisplayUldPrice() > 0 && getUldPriceUnit() != null
				&& getUldPriceUnit().trim().length() > 0) {
			
			//String[] sysparCurrency=sysparBaseCurrency.split(",");
			
			int i = 0;
			/*
			 * seting cuurencyConvertorVO
			 */	
			double conversion = 0;
			boolean convRate = true;
			currencyConvertorVO.setFromCurrencyCode(getUldPriceUnit());
			while(convRate){
				if(i != length){
					currencyConvertorVO.setRatingBasisType(sysparRate[i]);
					i++;
				}else{
					convRate=false;
					break;
				}
					conversion = new SharedCurrencyProxy().findExchangeRate(currencyConvertorVO); 
				if(!(conversion == 0)){
					convRate=false;
				}
			}
			/*CurrencyRateVO currencyRateVO = currencyProxy
					.findCurrencyConvertedRates(getUldPK().getCompanyCode(),
							CurrencyRateVO.RATE_TYPE_DAILY, getUldPriceUnit(),
							getDisplayUldPrice(), uldVO
									.getOperationalAirlineIdentifier());
			 */
				if (conversion != 0) {
				    double priceOfTheULD = getDisplayUldPrice() * conversion;
					log.log(Log.FINE, "THE  ULDPRICE  AFTER CONVERSION IS ",
							priceOfTheULD);
				setUldPrice(priceOfTheULD);
		          }else{
		        	  CurrencyConversionException currencyConversionException = new CurrencyConversionException();
					   currencyConversionException.addError(new ErrorVO(CurrencyConversionException.CURRENCY_CONVERSION_IS_NOT_DEFINED,
										new Object[] {uldVO.getUldPriceUnit()}));
						throw currencyConversionException;
		          }
		  }else{
			  setUldPrice(0.0);
		  }
		log.log(Log.FINE, "THE DISPLAY CURRENT  VALUE  IS",
				getDisplayCurrentValue());
		log.log(Log.FINE, "THE DISPLAY CURRENT VALUE UNIT  IS",
				getCurrentValueUnit());
		try{
		if (getDisplayCurrentValue() > 0 && getCurrentValueUnit() != null
				&& getCurrentValueUnit().trim().length() > 0) {
			currencyConvertorVO.setFromCurrencyCode(getCurrentValueUnit());
			double conversionRate = 0;
			for(int i = 0;i<length;i++){
				currencyConvertorVO.setRatingBasisType(sysparRate[i]);
				conversionRate = new SharedCurrencyProxy().findExchangeRate(currencyConvertorVO);
				if(conversionRate != 0){
					break;
				}
			}
			double currValue=getDisplayCurrentValue()*conversionRate;
			log.log(Log.FINE, "THE  CURRENTPRICE  AFTER CONVERSION IS ",
					currValue);
			setCurrentValue(currValue);

		  }else{
			  setCurrentValue(0.0);
		  }
		}catch (CurrencyConversionException e) {
			log.log(Log.FINE, " \n %^%^%^%^%^");
			throw new CurrencyConversionException(
					CurrencyConversionException.CURRENCY_CONVERSION_IS_NOT_DEFINED,
					new Object[] { getCurrentValueUnit() });
		}		
		try {
			if (getDisplayIataReplacementCost() > 0
					&& getIataReplacementCostUnit() != null
					&& getIataReplacementCostUnit().trim().length() > 0) {
				currencyConvertorVO.setFromCurrencyCode(getIataReplacementCostUnit());
				double conversionRate = 0;
				for(int i = 0;i<length;i++){
					currencyConvertorVO.setRatingBasisType(sysparRate[i]);
					conversionRate = new SharedCurrencyProxy().findExchangeRate(currencyConvertorVO);
					if(conversionRate != 0){
						break;
					}
				}
				double iataReplacementCostAmount = getDisplayIataReplacementCost() * conversionRate;
				log.log(Log.FINE,
						"THE  IATAREPLACEMENTCOST  AFTER CONVERSION IS ",
						iataReplacementCostAmount);
				setIataReplacementCost(iataReplacementCostAmount);

			}else{
				setIataReplacementCost(0.0);
			}
		} catch (CurrencyConversionException e) {
			log.log(Log.FINE, " \n %^%^%^%^%^");
			throw new CurrencyConversionException(
					CurrencyConversionException.CURRENCY_CONVERSION_IS_NOT_DEFINED,
					new Object[] { getIataReplacementCostUnit() });
		}		

		}


	  /**
	   *
	 * This methods is used to populate the dimensions corresponding to the
	 * airline identifier
	 * 
	 * @param uldVO
	 * @throws SystemException
	 */
	private void populateDimensions(ULDVO uldVO) throws SystemException,
			DimensionConversionException {
		
		//SharedDefaultsProxy defaultsProxy = new SharedDefaultsProxy();
		log.log(Log.INFO, "POPULATE DIMENSIONS");
		setBaseLength(uldVO.getBaseLength()!=null?uldVO.getBaseLength().getSystemValue()/* As part of correcting the Measure usage in setting value to entity classes ICRD-288684 */:0.0);
		setBaseHeight(uldVO.getBaseHeight()!=null?uldVO.getBaseHeight().getSystemValue()/* As part of correcting the Measure usage in setting value to entity classes ICRD-288684 */:0.0);
		setBaseWidth(uldVO.getBaseWidth()!=null?uldVO.getBaseWidth().getSystemValue()/* As part of correcting the Measure usage in setting value to entity classes ICRD-288684 */:0.0);
		
		/*if (getDisplayBaseLength() > 0 && getDisplayDimensionUnit() != null
				&& getDisplayDimensionUnit().trim().length() > 0) {
			try {
				log.log(Log.INFO, "\nINSIDE THE POPULATE DIMENSION UNIT1  ",
						UnitConversionVO.UNIT_TYPE_DIMENSION);
				log.log(Log.INFO, "INSIDE THE POPULATE DIMENSION UNIT1",
						getDisplayDimensionUnit());
				log.log(Log.INFO, "INSIDE THE POPULATE DIMENSION UNIT1",
						getDisplayBaseLength());
				double length = defaultsProxy.findSystemUnitValue(
						getUldPK().getCompanyCode(),
						UnitConversionVO.UNIT_TYPE_DIMENSION,
						getDisplayDimensionUnit(), getDisplayBaseLength())
						.getToValue();
				log.log(Log.FINE, "THE VALUE OF THE LENGTH AFTER CONVERSION",
						length);
				setBaseLength(length);

			} catch (ProxyException ex) {
				throw new DimensionConversionException(
						DimensionConversionException.DIMENSION_CONVERSION_IS_NOT_DEFINED,
						new Object[] { getDisplayDimensionUnit() });
		  }

		}

		if (getDisplayBaseHeight() > 0 && getDisplayDimensionUnit() != null
				&& getDisplayDimensionUnit().trim().length() > 0) {
			try {
				log.log(Log.INFO, "\nINSIDE THE POPULATE DIMENSION UNIT2",
						UnitConversionVO.UNIT_TYPE_DIMENSION);
				log.log(Log.INFO, "INSIDE THE POPULATE DIMENSION UNIT2",
						getDisplayDimensionUnit());
				log.log(Log.INFO, "INSIDE THE POPULATE DIMENSION UNIT2",
						getDisplayBaseHeight());
				double height = defaultsProxy.findSystemUnitValue(
						getUldPK().getCompanyCode(),
						UnitConversionVO.UNIT_TYPE_DIMENSION,
						getDisplayDimensionUnit(), getDisplayBaseHeight())
						.getToValue();
				log.log(Log.FINE, "THE VALUE OF THE HEIGHT AFTER CONVERSION",
						height);
				setBaseHeight(height);
			} catch (ProxyException ex) {
				throw new DimensionConversionException(
						DimensionConversionException.DIMENSION_CONVERSION_IS_NOT_DEFINED,
						new Object[] { getDisplayDimensionUnit() });
			}

		}
		if (getDisplayBaseWidth() > 0 && getDisplayDimensionUnit() != null
				&& getDisplayDimensionUnit().trim().length() > 0) {
			try {
				log.log(Log.INFO, "\nINSIDE THE POPULATE DIMENSION UNIT3",
						UnitConversionVO.UNIT_TYPE_DIMENSION);
				log.log(Log.INFO, "INSIDE THE POPULATE DIMENSION UNIT3",
						getDisplayDimensionUnit());
				log.log(Log.INFO, "INSIDE THE POPULATE DIMENSION UNIT3",
						getDisplayBaseWidth());
				double width = defaultsProxy.findSystemUnitValue(
						getUldPK().getCompanyCode(),
						UnitConversionVO.UNIT_TYPE_DIMENSION,
						getDisplayDimensionUnit(), getDisplayBaseWidth())
						.getToValue();
				log.log(Log.FINE, "THE VALUE OF THE WIDTH AFTER CONVERSION",
						width);
			setBaseWidth(width);
			} catch (ProxyException ex) {
				throw new DimensionConversionException(
						DimensionConversionException.DIMENSION_CONVERSION_IS_NOT_DEFINED,
						new Object[] { getDisplayDimensionUnit() });
		    }

		  }*/

		 }

	/**
	 * This method finds the ULD instance based in the PK
	 *
	 * @param companyCode
	 * @param uldNumber
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static ULD find(String companyCode, String uldNumber)
			throws SystemException, FinderException {
		ULD uld = null;
		ULDPK uldPk = new ULDPK();
		uldPk.setCompanyCode(companyCode);
		uldPk.setUldNumber(uldNumber);
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(ULD.class, uldPk);
	}

	/**
	 * method to update the BO
	 *
	 * @param uldVO
	 * @throws SystemException
	 */
	public void update(ULDVO uldVO) throws SystemException,
			CurrencyConversionException, DimensionConversionException {
		log.entering("INSIDE THE ULD", "update" + uldVO);
		if(!"L".equalsIgnoreCase(this.getOverallStatus()) && LOST_STATUS.equals(uldVO.getOverallStatus())){
			this.setLostDate(new LocalDate(uldVO.getCurrentStation(),Location.ARP,true));
		}  
		populateAttributes(uldVO);
	}

	/**
	 *
	 * @param damageStatus
	 * @param overallStatus
	 * @param damagePicture
	 * @param repairStatus
	 * @param supervisor
	 * @param investigationReport
	 * @throws SystemException
	 */
	public void update(String damageStatus, String overallStatus,
			String damagePicture, String repairStatus, String supervisor,
			String investigationReport , String lastUpdateUser , LocalDate lastUpdateTime) throws SystemException {
		log.entering("ULD", "update");   
		//Added by A-7426 as part of ICRD-198430 starts
		if(repairStatus!=null && repairStatus.equals(REPAIRED_STATUS)){
			setDamageStatus(NOT_DAMAGED_STATUS);
			setOverallStatus(OPERATIONAL_STATUS);
		}		
		
		//Added by A-7426 as part of ICRD-198430 ends
		else{
		setDamageStatus(damageStatus);
		//Modified by A-3415 for ICRD-113953
		//if(DAMAGE_STATUS_SCRAPPED.equals(uldVO.getDamageStatus())){
		if(isULDNonOperational(damageStatus)){
			setOverallStatus(OVERALL_STATUS_UNSERVICEABLE);
		}else{
			setOverallStatus(overallStatus);
			}
		}
		// setDamagePicture(damagePicture);
		setRepairStatus(repairStatus);
		setRepairSupervisor(supervisor);
		setInvestigationReport(investigationReport);
		setLastUpdateUser(lastUpdateUser);
		setLastUpdateTime(lastUpdateTime);
	}

	/**
	 *
	 * @param overallStatus
	 * @param currentStation
	 * @param lastUpdateUser
	 * @throws SystemException
	 */
	/*
	 * public void update(String overallStatus , String currentStation , String
	 * lastUpdateUser) throws SystemException{ log.entering("ULD","update");
	 * setOverallStatus(overallStatus); setCurrentStation(currentStation);
	 * setLastUpdateUser(lastUpdateUser); }
	 */

	/**
	 * 
	 * @param overallStatus
	 * @param lastUpdateUser
	 * @throws SystemException
	 */
	/*
	 * public void update(String overallStatus , String lastUpdateUser) throws
	 * SystemException{ log.entering("ULD","update");
	 * setOverallStatus(overallStatus); setLastUpdateUser(lastUpdateUser); }
	 */

	/**
	 * 
	 * @param currentStation
	 * @throws SystemException
	 */
	public void update(String currentStation) throws SystemException {
		log.entering("ULD", "update");
		setCurrentStation(currentStation);
//		added by nisha for QF1013 starts
		setScmDate(null);
		setScmFlag(null);
		//ends 
	}

	/**
	 *
	 * @param currentStation
	 * @param operatingAirlineIdentifier
	 * @throws SystemException
	 */
	public void update(String currentStation, int operatingAirlineIdentifier)
			throws SystemException {
		log.entering("ULD", "update");
		setOperationalAirlineIdentifier(operatingAirlineIdentifier);
		setCurrentStation(currentStation);
//		added by nisha for QF1013 starts
		setScmDate(null);
		setScmFlag(null);
		//ends 
	}

	/**
	 * This method is used to remove the business object. It interally calls the
	 * remove method within EntityManager
	 *
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		log.entering("INSIDE THE ULD", "REMOVE");
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode(),
					removeException);
		}
	}

	/**
	 * Used to populate the business object with values from VO
	 *
	 * @return ULDGroupVO
	 */
	public ULDVO retrieveVO() {
		return null;
	}

	/**
	 * This method is used to retrive the ULDs associated to a ULDType.
	 *
	 * @param companyCode
	 * @param uldTypeCode
	 * @return Collection
	 * @throws SystemException
	 */
	public static Collection<String> findULDsForType(String companyCode,
			String uldTypeCode) throws SystemException {
		return null;
	}

	/**
	 * This method validates the format of the specified ULD
	 *
	 * @param companyCode
	 * @param uldNumber
	 * @throws SystemException
	 * @throws InvalidULDFormatException
	 * changed by nisha
	 * ULD No...size btw 8 and 11
	 * first three position-ULD TypeCode
	 * next 3 to 5 positions- Alphanumeric serial no:
	 * last 2 to 3 positions -Alphanumeric airline code-
	 */
	public static boolean validateULDFormat(String companyCode, String uldNumber)
			throws SystemException, InvalidULDFormatException {boolean isValidType = false;
			Map<String, ULDTypeValidationVO> map = new HashMap<String, ULDTypeValidationVO>();
			Collection<String> coll = new ArrayList<String>();
			//AirlineValidationVO airlineVo = null;
			boolean isTwoAlphaCode=false;
			//Added by A-2052 for the bug 102284 starts
			uldNumber = uldNumber.toUpperCase().trim();
			log.log(Log.FINE, "\nuldNumber inside validateULDFormat--->>>",
					uldNumber);
			//Added by A-2052 for the bug 102284 ends
			//added by A-5125 for the bug ICRD-26465 for validating space in b/n Uld Number
			boolean containsSpace=false;
			if(uldNumber.contains(" "))
			{
				log
						.log(
								Log.FINE,
								"\n Uld Number having SpaceFrom validateULDFormat--->>>",
								uldNumber);
				containsSpace=true;
			}
			//end A-5125
			if (!(uldNumber.length() < 8 || uldNumber.length() > 11 || containsSpace)) {
				
				String uldTypeCode = uldNumber.substring(0, 3);
				//log.log(Log.FINE, "uldTypeCode" + uldTypeCode);
				coll.add(uldTypeCode);
				String twoalphacode= uldNumber.substring(uldNumber.length() - 2);
				String thralphacode= uldNumber.substring(uldNumber.length() - 3);
				//log.log(log.INFO,"two alpha and threealphacode"+twoalphacode);
				
				//checking the alpha code in use
				//Commented as part of ICRD 21184
				//String arlDtl=validateOwnerCode(companyCode,twoalphacode,thralphacode);
				
				//Added as part of ICRD 21184 starts
				String arlDtl=null;
				try {
					arlDtl=Proxy.getInstance().get(SharedAirlineProxy.class).validateOwnerCode(companyCode,twoalphacode,thralphacode);
				} catch(ProxyException e){
					//proxy exception
				}
				//Added as part of ICRD 21184 ends
				String airlineCode="";
				if(arlDtl!=null && arlDtl.trim().length()>0){
					
					//log.log(log.INFO,"airlinecode"+arlDtl);
					
					if("2".equals(arlDtl)){
						//log.log(log.INFO,"************two alpha code in use***********");
						airlineCode=twoalphacode;
						isTwoAlphaCode=true;
					}else{
						//log.log(log.INFO,"************Three alpha code in use***********");
						airlineCode=thralphacode;
					}
				}else{
					throw new InvalidULDFormatException(
							InvalidULDFormatException.INVALID_ULD_FORMAT,
							new Object[] { uldNumber });
				}
				String serNo="";
				if(isTwoAlphaCode){
					serNo=uldNumber.substring(3,uldNumber.length() - 2);
				}else{
					serNo=uldNumber.substring(3,uldNumber.length() - 3);
					
				}
				/* CR ICRD-143324
				 * Uld Serial should be in the format  4	5	6	7	8
									 				   m	n	n	n	n					 				
					4 th place can be aplhaNumeric		
				 */
				boolean isValidULDSerialNumber=false;
				if(isValidULDSerialNumber(serNo))
				{
					log
							.log(
									Log.FINE,
									"\n Serial Number contains  AlpaNumber of Uld inside validateULDFormat--->>>",
									serNo);
					log.log(Log.FINE, "\n ValidFormat--->>>", serNo);
					isValidULDSerialNumber=true;
				}
				if((serNo.trim().length() != 5 && serNo.trim().length() != 4 )|| !isValidULDSerialNumber ){    
					throw new InvalidULDFormatException(
							InvalidULDFormatException.INVALID_ULD_FORMAT,
							new Object[] { uldNumber });
				}
						
				//log.log(Log.FINE, "uldTypeCode" + airlineCode);
				//log.log(Log.FINE, "serialNo" + serNo);
				try {
					map = Proxy.getInstance().get(SharedULDProxy.class).validateULDTypeCodes(companyCode,
							coll);
					if (map.size() == 1) {
						isValidType=true;
					}
				} catch (ProxyException ex) {
					throw new InvalidULDFormatException(
							InvalidULDFormatException.INVALID_ULD_FORMAT,
							new Object[] { uldNumber });
				}
				return isValidType;
			} else {
				throw new InvalidULDFormatException(
						InvalidULDFormatException.INVALID_ULD_FORMAT,
						new Object[] { uldNumber });
			}
	}
	/**
	 * @author A-2408
	 * @param companyCode
	 * @param twoalpha
	 * @param threealpha
	 * @return
	 * @throws SystemException
	 */
	//Commented as part of ICRD 21184
	/*public static String validateOwnerCode(String companyCode,String twoalpha,String threealpha)
	throws SystemException{
		Log logger = LogFactory.getLogger(" ULD_DEFAULTS");
		logger.entering(MODULE, "getAlphaCodeInUse");
		try {
			return constructDAO().validateOwnerCode(companyCode,twoalpha,threealpha);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
	}*/

	/**
	 *
	 * @param companyCode
	 * @param uldTypeCode
	 * @return
	 * @throws SystemException
	 */

	public static ULDTypeVO findULDTypeStructuralDetails(String companyCode,
			String uldTypeCode) throws SystemException {
		ULDTypeVO uldTypeVo = null;
		List<ULDTypeVO> uldTypeVos = null;
		String uldNumber = null;
		String uldAirlineCode = null;
		String uldSerialNumber = null;
		
		if(uldTypeCode != null && uldTypeCode.length() > 3) {			
			uldNumber = uldTypeCode;
			uldTypeCode = uldNumber.substring(0, 3);
			String twoalphacode= uldNumber.substring(uldNumber.length() - 2);
			String thralphacode= uldNumber.substring(uldNumber.length() - 3);
			String arlDtl=null;
			boolean isTwoAlphaCode = false;
			try {
				arlDtl=Proxy.getInstance().get(SharedAirlineProxy.class).validateOwnerCode(companyCode,twoalphacode,thralphacode);
			} catch(ProxyException e){
				//proxy exception
			}			
			
			if(arlDtl!=null && arlDtl.trim().length()>0){
				if("2".equals(arlDtl)){				
					uldAirlineCode=twoalphacode;
					isTwoAlphaCode=true;
				}else{
					uldAirlineCode=thralphacode;
				}
			}			
			if(isTwoAlphaCode){
				uldSerialNumber=uldNumber.substring(3,uldNumber.length() - 2);
			}else{
				uldSerialNumber=uldNumber.substring(3,uldNumber.length() - 3);				
			}									
		}
		
		//log.entering("INSIDE THE ULD", "findULDTypeStructuralDetails");
		ULDTypeFilterVO filterVO = new ULDTypeFilterVO();
		filterVO.setCompanyCode(companyCode);
		filterVO.setUldTypeCode(uldTypeCode);
		try {

			uldTypeVos = (List<ULDTypeVO>) Proxy.getInstance().get(SharedULDProxy.class)
					.findULDTypes(filterVO);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage(),
					proxyException);
		}
		if (uldTypeVos != null && uldTypeVos.size() > 0) {
			//log.log(Log.INFO, "SIZE OF ULDTYPEVOS >>>>>>>>>>>>>"
					//+ uldTypeVos.size());
			uldTypeVo = uldTypeVos.get(0);
			
			if(uldNumber != null) {				
				ULDValidationFilterVO uldValidationFilterVO = new ULDValidationFilterVO();
				uldValidationFilterVO.setCompanyCode(companyCode);
				uldValidationFilterVO.setUldTypeCode(uldTypeCode);
				uldValidationFilterVO.setUldAirlineCode(uldAirlineCode);
				uldValidationFilterVO.setSerialNumber(uldSerialNumber);
				uldValidationFilterVO.setUldNumber(uldNumber);
				
				try {
					Measure tareWeight = Proxy.getInstance().get(SharedULDProxy.class).findULDTareWeight(uldValidationFilterVO);
					if(tareWeight != null) {
						uldTypeVo.setTareWt(tareWeight);
					}
				} catch (ProxyException proxyException) {
					throw new SystemException(proxyException.getMessage(),proxyException);
				}
			}			
		}
		return uldTypeVo;
	}

	/**
	 * This method retrieves the details of the specified ULD
	 *
	 * @param companyCode
	 * @param uldNumber
	 * @return
	 * @throws SystemException
	 */
	public static ULDVO findULDDetails(String companyCode, String uldNumber)
			throws SystemException {
		Log logger = LogFactory.getLogger(" ULD_DEFAULTS");
		logger.entering(MODULE, "findULDDetails");
		try {
			return constructDAO().findULDDetails(companyCode, uldNumber);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
	}

	/**
	 * @author A-2619 This method is used to list the ULD History
	 * @return Collection
	 * @param uldHistoryVO.
	 * @throws SystemException.
	 */
	public static Page<ULDHistoryVO> listULDHistory(
			ULDHistoryVO uldHistoryVO) throws SystemException {
		Log logger = LogFactory.getLogger(" ULD_DEFAULTS");
		logger.entering(MODULE, "listULDHistory");
		try {
			return constructDAO().listULDHistory(uldHistoryVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
	}

	/**
	 * This method checks if there already exists a manufaturer-serial number
	 * combination for any ULD
	 *
	 * @param uldVo
	 * @return
	 * @throws SystemException
	 * @throws DuplicateManufacturerNumberExistsException
	 */
	public static boolean checkDuplicateManufacturerNumber(ULDVO uldVo)
			throws SystemException, DuplicateManufacturerNumberExistsException {
		Log logger = LogFactory.getLogger("ULD_DEFAULTS");
		logger.entering("INSIDE THE ULD", "checkDuplicateManufacturerNumber");
		try {
			return constructDAO().checkDuplicateManufacturerNumber(uldVo);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode(),
					persistenceException);
		}

	}

	/**
	 * This method checks if the uld is currently loaned or borrowed
	 *
	 * @param companyCode
	 * @param uldNumber
	 * @return
	 * @throws SystemException
	 * @throws ULDInTransactionException
	 */
	public static boolean checkULDInTransaction(String companyCode,
			String uldNumber) throws SystemException, ULDInTransactionException {
		Log logg = LogFactory.getLogger("ULD_DEFAULTS");
		logg.entering("INSIDE THE ULD", "checkULDInTransaction");
		boolean isULDInTransactionEnabled = false;
		try {
			if(isULDInTransactionEnabled()){
				isULDInTransactionEnabled = 
						constructDAO().checkULDInTransaction(companyCode, uldNumber);
			}
		} catch (PersistenceException PersistenceException) {
			throw new SystemException(PersistenceException.getErrorCode(),
					PersistenceException);
		}
		return isULDInTransactionEnabled;
	}

	/**
	 * This methd checks if the ULD is either loaned/borrowed or if the
	 * manufacturer-serial number combination already exists for the ULD
	 *
	 * @param uldVo
	 * @return
	 * @throws SystemException
	 * @throws DuplicateManufacturerNumberExistsException
	 * @throws ULDInTransactionException
	 */
	public static boolean validateULDForModification(ULDVO uldVo)
			throws SystemException, DuplicateManufacturerNumberExistsException,
			ULDInTransactionException {
		Log loger = LogFactory.getLogger("ULD_DEFAULTS");
		boolean isULDInTransaction = checkULDInTransaction(uldVo
				.getCompanyCode(), uldVo.getUldNumber());
		if (isULDInTransaction) {
			loger.log(Log.SEVERE, "ULDInTransactionException IS THROWN");
			throw new ULDInTransactionException(
					ULDInTransactionException.ULD_IN_TRANSACTION, new Object[] {
							uldVo.getCompanyCode(), uldVo.getUldNumber() });
		}
		return true;
	}

	/**
	 * This method retrieves the uld details of the specified filter condition
	 *
	 * @param uldListFilterVO
	 * @param pageNumber
	 * @return Page<ULDListVO>
	 * @throws SystemException
	 */
	public static Page<ULDListVO> findULDList(ULDListFilterVO uldListFilterVO,
			int pageNumber) throws SystemException {
		Log logging = LogFactory.getLogger("ULD_DEFAULTS");
		logging.entering(MODULE, "findULDList");
		Page<ULDListVO> listVoPage = null;
		try {
			listVoPage = constructDAO()
					.findULDList(uldListFilterVO, pageNumber);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
		return listVoPage;
	}

	/**
	 * This method validates if the ULD exists in the system
	 * 
	 * @author A-1883
	 * @param companyCode
	 * @param uldNumber
	 * @return ULDValidationVO
	 * @throws SystemException
	 */
	public static ULDValidationVO validateULD(String companyCode,
			String uldNumber) throws SystemException {
		Log log = LogFactory.getLogger("ULD");
		log.entering("ULD", "validateULD");
		return constructDAO().validateULD(companyCode, uldNumber);
	}

	/*
	 * public static boolean checkULDInOperations(String companyCode,String
	 * uldNUmber) throws SystemException,ULDInOperationException{ boolean
	 * isULDInOperations=false; Log log=LogFactory.getLogger("ULD_DEFAULTS");
	 * try{ log.entering("CALLING THE ","IMPORT PROXY"); isULDInOperations= new
	 * ImportProxy().checkULDInOperations(companyCode,uldNUmber);
	 *
	 * if(!isULDInOperations){ log.entering(" ULD NOT IN IMPORT ","CHECK AGAINST
	 * THE EXPORT"); isULDInOperations= new
	 * ExportProxy().checkULDInOperations(companyCode,uldNUmber); }
	 * if(!isULDInOperations){ log.entering("ULD NOT IN THE IMPORT AND
	 * EXPORT","CHECK AGAINST THE WAREHOUSE"); isULDInOperations=new
	 * WarehouseProxy().checkULDInOperations(companyCode,uldNUmber); }
	 * }catch(ProxyException ex){ throw new ULDInOperationException(); } return
	 * isULDInOperations; }
	 */

	/**
	 * @return Returns the uldPK.
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "uldNumber", column = @Column(name = "ULDNUM")) })
	public ULDPK getUldPK() {
		return uldPK;
	}

	/**
	 * @param uldPK
	 *            The uldPK to set.
	 */
	public void setUldPK(ULDPK uldPK) {
		this.uldPK = uldPK;
	}

	/**
	 * @return Returns the baseHeight.
	 */
	@Column(name = "BASHGT")
	public double getBaseHeight() {
		return baseHeight;
	}

	/**
	 * @param baseHeight
	 *            The baseHeight to set.
	 */
	public void setBaseHeight(double baseHeight) {
		this.baseHeight = baseHeight;
	}

	/**
	 * @return Returns the baseLength.
	 */
	@Column(name = "BASLEN")
	public double getBaseLength() {
		return baseLength;
	}

	/**
	 * @param baseLength
	 *            The baseLength to set.
	 */
	public void setBaseLength(double baseLength) {
		this.baseLength = baseLength;
	}

	/**
	 * @return Returns the baseWidth.
	 */
	@Column(name = "BASWID")
	public double getBaseWidth() {
		return baseWidth;
	}

	/**
	 * @param baseWidth
	 *            The baseWidth to set.
	 */
	public void setBaseWidth(double baseWidth) {
		this.baseWidth = baseWidth;
	}

	/**
	 * @return Returns the cleanlinessStatus.
	 */
	@Column(name = "CLNSTA")
	public String getCleanlinessStatus() {
		return cleanlinessStatus;
	}

	/**
	 * @param cleanlinessStatus
	 *            The cleanlinessStatus to set.
	 */
	public void setCleanlinessStatus(String cleanlinessStatus) {
		this.cleanlinessStatus = cleanlinessStatus;
	}

	/**
	 * @return Returns the currentStation.
	 */
	@Column(name = "CURARP")
	@Audit(name = "CurrentStation")
	public String getCurrentStation() {
		return currentStation;
	}

	/**
	 * @param currentStation
	 *            The currentStation to set.
	 */
	public void setCurrentStation(String currentStation) {
		this.currentStation = currentStation;
	}

	/**
	 * @return Returns the currentValue.
	 */
	@Column(name = "CURVAL")
	public double getCurrentValue() {
		return currentValue;
	}

	/**
	 * @param currentValue
	 *            The currentValue to set.
	 */
	public void setCurrentValue(double currentValue) {
		this.currentValue = currentValue;
	}

	/**
	 * @return Returns the currentValueUnit.
	 */
	@Column(name = "CURVALUNT")
	public String getCurrentValueUnit() {
		return currentValueUnit;
	}

	/**
	 * @param currentValueUnit
	 *            The currentValueUnit to set.
	 */
	public void setCurrentValueUnit(String currentValueUnit) {
		this.currentValueUnit = currentValueUnit;
	}

	/**
	 * @return Returns the damageStatus.
	 */
	@Column(name = "DMGSTA")
	@Audit(name = "DamageStatus")
	public String getDamageStatus() {
		return damageStatus;
	}

	/**
	 * @param damageStatus
	 *            The damageStatus to set.
	 */
	public void setDamageStatus(String damageStatus) {
		this.damageStatus = damageStatus;
	}

	/**
	 * @return Returns the displayBaseHeight.
	 */
	@Column(name = "DISBASHGT")
	public double getDisplayBaseHeight() {
		return displayBaseHeight;
	}

	/**
	 * @param displayBaseHeight
	 *            The displayBaseHeight to set.
	 */
	public void setDisplayBaseHeight(double displayBaseHeight) {
		this.displayBaseHeight = displayBaseHeight;
	}

	/**
	 * @return Returns the displayBaseLength.
	 */
	@Column(name = "DISBASLEN")
	public double getDisplayBaseLength() {
		return displayBaseLength;
	}

	/**
	 * @param displayBaseLength
	 *            The displayBaseLength to set.
	 */
	public void setDisplayBaseLength(double displayBaseLength) {
		this.displayBaseLength = displayBaseLength;
	}

	/**
	 * @return Returns the displayBaseWidth.
	 */
	@Column(name = "DISBASWID")
	public double getDisplayBaseWidth() {
		return displayBaseWidth;
	}

	/**
	 * @param displayBaseWidth
	 *            The displayBaseWidth to set.
	 */
	public void setDisplayBaseWidth(double displayBaseWidth) {
		this.displayBaseWidth = displayBaseWidth;
	}

	/**
	 * @return Returns the displayDimensionUnit.
	 */
	@Column(name = "DISDIMUNT")
	public String getDisplayDimensionUnit() {
		return displayDimensionUnit;
	}

	/**
	 * @param displayDimensionUnit
	 *            The displayDimensionUnit to set.
	 */
	public void setDisplayDimensionUnit(String displayDimensionUnit) {
		this.displayDimensionUnit = displayDimensionUnit;
	}

	/**
	 * @return Returns the displayStructuralWeight.
	 */
	@Column(name = "DISSTRWGT")
	public double getDisplayStructuralWeight() {
		return displayStructuralWeight;
	}

	/**
	 * @param displayStructuralWeight
	 *            The displayStructuralWeight to set.
	 */
	public void setDisplayStructuralWeight(double displayStructuralWeight) {
		this.displayStructuralWeight = displayStructuralWeight;
	}

	/**
	 * @return Returns the displayStructuralWeightUnit.
	 */
	@Column(name = "DISSTRWGTUNT")
	public String getDisplayStructuralWeightUnit() {
		return displayStructuralWeightUnit;
	}

	/**
	 * @param displayStructuralWeightUnit
	 *            The displayStructuralWeightUnit to set.
	 */
	public void setDisplayStructuralWeightUnit(
			String displayStructuralWeightUnit) {
		this.displayStructuralWeightUnit = displayStructuralWeightUnit;
	}

	/**
	 * @return Returns the displayTareWeight.
	 */
	@Column(name = "DISTARWGT")
	public double getDisplayTareWeight() {
		return displayTareWeight;
	}

	/**
	 * @param displayTareWeight
	 *            The displayTareWeight to set.
	 */
	public void setDisplayTareWeight(double displayTareWeight) {
		this.displayTareWeight = displayTareWeight;
	}

	/**
	 * @return Returns the displayTareWeightUnit.
	 */
	@Column(name = "DISTARWGTUNT")
	public String getDisplayTareWeightUnit() {
		return displayTareWeightUnit;
	}

	/**
	 * @param displayTareWeightUnit
	 *            The displayTareWeightUnit to set.
	 */
	public void setDisplayTareWeightUnit(String displayTareWeightUnit) {
		this.displayTareWeightUnit = displayTareWeightUnit;
	}

	/**
	 * @return Returns the iataReplacementCost.
	 */
	@Column(name = "ITAREPCST")
	public double getIataReplacementCost() {
		return IataReplacementCost;
	}

	/**
	 * @param iataReplacementCost
	 *            The iataReplacementCost to set.
	 */
	public void setIataReplacementCost(double iataReplacementCost) {
		this.IataReplacementCost = iataReplacementCost;
	}

	/**
	 * @return Returns the iataReplacementCostUnit.
	 */
	@Column(name = "ITAREPCSTUNT")
	public String getIataReplacementCostUnit() {
		return iataReplacementCostUnit;
	}

	/**
	 * @param iataReplacementCostUnit
	 *            The iataReplacementCostUnit to set.
	 */
	public void setIataReplacementCostUnit(String iataReplacementCostUnit) {
		this.iataReplacementCostUnit = iataReplacementCostUnit;
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
	 * @param lastUpdateTime
	 *            The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return Returns the lastUpdateUser.
	 */
	@Column(name = "LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param lastUpdateUser
	 *            The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * @return Returns the location.
	 */
	@Column(name = "LOC")
	@Audit(name = "Location")
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            The location to set.
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return Returns the manufacturer.
	 */
	@Column(name = "MFR")
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * @param manufacturer
	 *            The manufacturer to set.
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * @return Returns the operationalAirlineIdentifier.
	 */
	@Column(name = "OPRARLIDR")
	@Audit(name = "OperationalAirlineIdentifier")
	public int getOperationalAirlineIdentifier() {
		return operationalAirlineIdentifier;
	}

	/**
	 * @param operationalAirlineIdentifier
	 *            The operationalAirlineIdentifier to set.
	 */
	public void setOperationalAirlineIdentifier(int operationalAirlineIdentifier) {
		this.operationalAirlineIdentifier = operationalAirlineIdentifier;
	}

	/**
	 * @return Returns the overallStatus.
	 */
	@Column(name = "OVLSTA")
	@Audit(name = "OverallStatus")
	public String getOverallStatus() {
		return overallStatus;
	}

	/**
	 * @param overallStatus
	 *            The overallStatus to set.
	 */
	public void setOverallStatus(String overallStatus) {
		this.overallStatus = overallStatus;
	}

	/**
	 * @return Returns the ownerAirlineIdentifier.
	 */
	@Column(name = "OWNARLIDR")
	public int getOwnerAirlineIdentifier() {
		return ownerAirlineIdentifier;
	}

	/**
	 * @param ownerAirlineIdentifier
	 *            The ownerAirlineIdentifier to set.
	 */
	public void setOwnerAirlineIdentifier(int ownerAirlineIdentifier) {
		this.ownerAirlineIdentifier = ownerAirlineIdentifier;
	}

	/**
	 * @return Returns the ownerStation.
	 */
	@Column(name = "OWNARP")
	public String getOwnerStation() {
		return ownerStation;
	}

	/**
	 * @param ownerStation
	 *            The ownerStation to set.
	 */
	public void setOwnerStation(String ownerStation) {
		this.ownerStation = ownerStation;
	}

	/**
	 * @return Returns the purchaseDate.
	 */
	@Column(name = "PURDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getPurchaseDate() {
		return purchaseDate;
	}

	/**
	 * @param purchaseDate
	 *            The purchaseDate to set.
	 */
	public void setPurchaseDate(Calendar purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	/**
	 * @return Returns the purchaseInvoiceNumber.
	 */
	@Column(name = "PURINVNUM")
	public String getPurchaseInvoiceNumber() {
		return purchaseInvoiceNumber;
	}

	/**
	 * @param purchaseInvoiceNumber
	 *            The purchaseInvoiceNumber to set.
	 */
	public void setPurchaseInvoiceNumber(String purchaseInvoiceNumber) {
		this.purchaseInvoiceNumber = purchaseInvoiceNumber;
	}

	/**
	 * @return Returns the structuralWeight.
	 */
	@Column(name = "STRWGT")
	@Audit(name = "StructuralWeight")
	public double getStructuralWeight() {
		return structuralWeight;
	}

	/**
	 * @param structuralWeight
	 *            The structuralWeight to set.
	 */
	public void setStructuralWeight(double structuralWeight) {
		this.structuralWeight = structuralWeight;
	}

	/**
	 * @return Returns the tareWeight.
	 */
	@Column(name = "TARWGT")
	@Audit(name = "TareWeight")
	public double getTareWeight() {
		return tareWeight;
	}

	/**
	 * @param tareWeight
	 *            The tareWeight to set.
	 */
	public void setTareWeight(double tareWeight) {
		this.tareWeight = tareWeight;
	}

	/**
	 * @return Returns the uldContour.
	 */
	@Column(name = "ULDCNT")
	public String getUldContour() {
		return uldContour;
	}

	/**
	 * @param uldContour
	 *            The uldContour to set.
	 */
	public void setUldContour(String uldContour) {
		this.uldContour = uldContour;
	}

	/**
	 * @return Returns the uldPrice.
	 */
	@Column(name = "ULDPRC")
	public double getUldPrice() {
		return uldPrice;
	}

	/**
	 * @param uldPrice
	 *            The uldPrice to set.
	 */
	public void setUldPrice(double uldPrice) {
		this.uldPrice = uldPrice;
	}

	/**
	 * @return Returns the uldPriceUnit.
	 */
	@Column(name = "ULDPRCUNT")
	public String getUldPriceUnit() {
		return uldPriceUnit;
	}

	/**
	 * @param uldPriceUnit
	 *            The uldPriceUnit to set.
	 */
	public void setUldPriceUnit(String uldPriceUnit) {
		this.uldPriceUnit = uldPriceUnit;
	}

	/**
	 * @return Returns the uldSerialNumber.
	 */
	@Column(name = "ULDSERNUM")
	public String getUldSerialNumber() {
		return uldSerialNumber;
	}

	/**
	 * @param uldSerialNumber
	 *            The uldSerialNumber to set.
	 */
	public void setUldSerialNumber(String uldSerialNumber) {
		this.uldSerialNumber = uldSerialNumber;
	}

	/**
	 * @return Returns the uldType.
	 */
	@Column(name = "ULDTYP")
	public String getUldType() {
		return uldType;
	}

	/**
	 * @param uldType
	 *            The uldType to set.
	 */
	public void setUldType(String uldType) {
		this.uldType = uldType;
	}

	/**
	 * @return Returns the vendor.
	 */
	@Column(name = "VDR")
	public String getVendor() {
		return vendor;
	}

	/**
	 * @param vendor
	 *            The vendor to set.
	 */
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	/**
	 *
	 * @param remarks
	 *            The remarks to be set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 *
	 * @return Returns the Remarks
	 */
	@Column(name = "ULDRMK")
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @return Returns the investigationReport.
	 */
	@Column(name = "INVRPT")
	public String getInvestigationReport() {
		return investigationReport;
	}

	/**
	 * @param investigationReport
	 *            The investigationReport to set.
	 */
	public void setInvestigationReport(String investigationReport) {
		this.investigationReport = investigationReport;
	}

	/**
	 * @return Returns the repairStatus.
	 */
	@Column(name = "RPRSTA")
	public String getRepairStatus() {
		return repairStatus;
	}

	/**
	 * @param repairStatus
	 *            The repairStatus to set.
	 */

	public void setRepairStatus(String repairStatus) {
		this.repairStatus = repairStatus;
	}

	/**
	 * @return Returns the repairSupervisor.
	 */
	@Column(name = "SPR")
	public String getRepairSupervisor() {
		return repairSupervisor;
	}

	/**
	 * @param repairSupervisor
	 *            The repairSupervisor to set.
	 */
	public void setRepairSupervisor(String repairSupervisor) {
		this.repairSupervisor = repairSupervisor;
	}

	/**
	 * @return Returns the damagePicture.
	 */
	// public byte[] getDamagePicture() {
	// return damagePicture;
	// }
	/**
	 * @param damagePicture
	 *            The damagePicture to set.
	 */
	// public void setDamagePicture(byte[] damagePicture) {
	// this.damagePicture = damagePicture;
	// }
	/**
	 * @return Returns the borrowReferenceNumber.
	 */
	@Column(name = "BRWREFNUM")
	public int getBorrowReferenceNumber() {
		return borrowReferenceNumber;
	}

	/**
	 * @param borrowReferenceNumber
	 *            The borrowReferenceNumber to set.
	 */
	public void setBorrowReferenceNumber(int borrowReferenceNumber) {
		this.borrowReferenceNumber = borrowReferenceNumber;
	}

	/**
	 * @return Returns the loanReferenceNumber.
	 */
	@Column(name = "LONREFNUM")
	public int getLoanReferenceNumber() {
		return loanReferenceNumber;
	}

	/**
	 * @param loanReferenceNumber
	 *            The loanReferenceNumber to set.
	 */
	public void setLoanReferenceNumber(int loanReferenceNumber) {
		this.loanReferenceNumber = loanReferenceNumber;
	}

	/**
	 *
	 * @return Returns the LastMovementDate
	 */
	@Column(name = "LSTMVTDAT")
	@Audit(name = "LastMovementDate")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastMovementDate() {
		return lastMovementDate;
	}

	/**
	 *
	 * @param lastMovementDate
	 *            The LastMovementDate to be set
	 */
	public void setLastMovementDate(Calendar lastMovementDate) {
		this.lastMovementDate = lastMovementDate;
	}

	/**
	 * @return Returns the displayCurrentValue.
	 */
	@Column(name = "DISCURVAL")
	public double getDisplayCurrentValue() {
		return displayCurrentValue;
	}

	/**
	 * @param displayCurrentValue
	 *            The displayCurrentValue to set.
	 */
	public void setDisplayCurrentValue(double displayCurrentValue) {
		this.displayCurrentValue = displayCurrentValue;
	}

	/**
	 * @return Returns the displayIataReplacementCost.
	 */
	@Column(name = "DISITAREPCST")
	public double getDisplayIataReplacementCost() {
		return displayIataReplacementCost;
	}

	/**
	 * @param displayIataReplacementCost
	 *            The displayIataReplacementCost to set.
	 */
	public void setDisplayIataReplacementCost(double displayIataReplacementCost) {
		this.displayIataReplacementCost = displayIataReplacementCost;
	}

	/**
	 * @return Returns the displayUldPrice.
	 */
	@Column(name = "DISULDPRC")
	public double getDisplayUldPrice() {
		return displayUldPrice;
	}

	/**
	 * @param displayUldPrice
	 *            The displayUldPrice to set.
	 */
	public void setDisplayUldPrice(double displayUldPrice) {
		this.displayUldPrice = displayUldPrice;
	}

	/**
	 * 
	 * @return Returns the uldGroupCode
	 */
	@Column(name = "ULDGRPCOD")
	public String getUldGroupCode() {
		return uldGroupCode;
	}

	/**
	 * 
	 * @param uldGroupCode
	 * 				The uldGroupCode to set
	 */
	public void setUldGroupCode(String uldGroupCode) {
		this.uldGroupCode = uldGroupCode;
	}
	/**
	 * 
	 * @return Returns the occupancyStatus
	 */
	@Column(name = "ULDOCPSTA")
	public String getOccupancyStatus() {
		return occupancyStatus;
	}
	/**
	 * 
	 * @param occupancyStatus
	 * 				The occupancyStatus to set
	 */
	public void setOccupancyStatus(String occupancyStatus) {
		this.occupancyStatus = occupancyStatus;
	} 

	/**
	 * This method is used to list ULDs at a purticular station
	 *
	 * @param companyCode
	 * @param stationCode
	 * @param airlineIdentifier
	 * @return Collection<String>
	 * @throws SystemException
	 *
	 */
	public static Page<String> findStationUlds(String companyCode,String uldNumber,
			String stationCode,String transactionType, int airlineIdentifier,int displayPage) throws SystemException {
		try{	
		//boolean isAirlineUser = logonAttributes.isAirlineUser();
			return constructDAO().findStationUlds(companyCode, uldNumber,
					stationCode,transactionType, airlineIdentifier, displayPage);
			}catch(PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}
	/**
	 * This mthod is going to check whether the ULD is booked by the agent if
	 * the ULD is Loaned to Agent
	 *
	 * @param companyCode
	 * @param agentCode
	 * @param uldNumber
	 * @return boolean
	 * @throws SystemException
	 */
	public boolean checkAgentBookedULD(String companyCode, String agentCode,
			String uldNumber) throws SystemException {
		return false;

	}

	/**
	 * This method is used to list the ULD Set up Configuration
	 *
	 * @param uldStockConfigFilterVO
	 * @return Page<ULDStockConfigVO>
	 * @throws SystemException
	 */
	public Page listULDStockConfig(ULDStockConfigFilterVO uldStockConfigFilterVO)
			throws SystemException {
		return null;

	}

	/**
	 * This method is used to Monitor ULD stock
	 *
	 * @author A-1883
	 * @param uLDStockConfigFilterVO
	 * @param displayPage
	 * @return Page<ULDStockListVO>
	 * @throws SystemException
	 */
	public static Page<ULDStockListVO> findULDStockList(
			ULDStockConfigFilterVO uLDStockConfigFilterVO, int displayPage)
			throws SystemException {
		Log log = LogFactory.getLogger("ULD");
		log.entering("ULD", "findULDStockList");
		return constructDAO().findULDStockList(uLDStockConfigFilterVO,
				displayPage);
	}

	/**
	 * This method returns the ULDDefaultsDAO.
	 *
	 * @throws SystemException
	 */
	private static ULDDefaultsDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return ULDDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getMessage());
		}
	}

	/**
	 *
	 * @param companyCode
	 * @param airlineId
	 * @return
	 * @throws SystemException
	 */
	//Commented as part of ICRD 21184
	/*public static String findAirlineCode(String companyCode, String airlineId)
	throws SystemException {
		try {
			return constructDAO().findAirlineCode(companyCode, airlineId);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}*/

	//Commented as part of ICRD-21184

	/**
	 *
	 * @param companyCode
	 * @param airlineId
	 * @return
	 * @throws SystemException
	 */
//	public static String findCarrierCode(String companyCode, int airlineId)
//	throws SystemException {
//		try {
//			return constructDAO().findCarrierCode(companyCode, airlineId);
//		} catch (PersistenceException e) {
//			throw new SystemException(e.getErrorCode(), e);
//		}
//	}

	/**
	 *
	 * @param companyCode
	 * @param airlineCodes
	 * @return
	 * @throws SystemException
	 */
	
	//Commented as part of ICRD 21184
	/*public static HashMap<String, Integer> listAirlineIdentifiers(
			String companyCode, Set<String> airlineCodes)
			throws SystemException {
		try {
			return constructDAO().listAirlineIdentifiers(companyCode,
					airlineCodes);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}*/

	/**
	 *
	 * @param companyCode
	 * @param airportCode
	 * @return
	 * @throws SystemException
	 */
	public static ULDAirportLocationVO findCurrentLocation(String companyCode,
			String airportCode,String content) throws SystemException {
		try {
			return constructDAO().findCurrentLocation(companyCode, airportCode,content);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}

	public static boolean isDummyULDMovementPresent(String companyCode,
			int carrierId,String flightnum,LocalDate flightDate,String uldNum,String pol,String pou) throws SystemException {
		try {
			return constructDAO().isDummyULDMovementPresent( companyCode,
					 carrierId, flightnum, flightDate, uldNum, pol, pou);
			} catch (PersistenceException e) {
				throw new SystemException(e.getErrorCode(), e);
			}
	}
	
	/**
	 *
	 * @param companyCode
	 * @param airportCode
	 * @return
	 * @throws SystemException
	 */
	public static HashMap<String, Collection<String>> populateLocation(
			String companyCode, String airportCode) throws SystemException {
		try {
			return constructDAO().populateLocation(companyCode, airportCode);

		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}

	/**
	 *
	 * @param companyCode
	 * @param airportCode
	 * @param uldNumbers
	 * @return
	 * @throws SystemException
	 */
	 public static Collection<ULDVO> validateULDs(String companyCode,
			String airportCode, Collection<String> uldNumbers)
			throws SystemException {
		try {
			return constructDAO().validateULDs(companyCode, airportCode,
					uldNumbers);

		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
			}
	 }

	@Column(name = "TRNSTA")
	/**
	 * @return Returns the transitStatus.
	 */
	public String getTransitStatus() {
		return transitStatus;
	}

	/**
	 * @param transitStatus
	 *            The transitStatus to set.
	 */
	public void setTransitStatus(String transitStatus) {
		this.transitStatus = transitStatus;
	}

	/**
	 * @return String Returns the facilityType.
	 */
	@Column(name = "FACTYP")
	public String getFacilityType() {
		return this.facilityType;
	}

	/**
	 * @param facilityType
	 *            The facilityType to set.
	 */
	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}

	/**
	 *
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ULDVO> findUldDetailsForSCM(
			SCMMessageFilterVO filterVO) throws SystemException {
		 try {
				return constructDAO().findUldDetailsForSCM(filterVO);

			} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
			}
	 }

	/**
	 * @return Returns the controlReceiptNumber.
	 */
	 @Column(name = "CRN")
	public String getControlReceiptNumber() {
		return controlReceiptNumber;
	}

	/**
	 * @param controlReceiptNumber
	 *            The controlReceiptNumber to set.
	 */
	public void setControlReceiptNumber(String controlReceiptNumber) {
		this.controlReceiptNumber = controlReceiptNumber;
	}

	/***************************************************************************
	 *
	 * @param companyCode
	 * @param airportCode
	 * @param airlineIdentifier
	 * @param uldIdentifier
	 * @param date
	 * @throws SystemException
	 */
	public static Collection<String> findSCMFromMonitorULD(String companyCode,
			ULDStockListVO uldStockListVO	
		) throws SystemException {

		try {
			return constructDAO().findSCMFromMonitorULD(companyCode,
					uldStockListVO);

		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}

	}

	/**
	 * This method retrieves the uld details of the specified filter condition
	 *
	 * @param uldListFilterVO
	 * @param pageNumber
	 * @return ULDListVO
	 * @throws SystemException
	 */
	public static ULDListVO findULDListForHHT(String companyCode,
			String uldNumber) throws SystemException {
		Log logging = LogFactory.getLogger("ULD_DEFAULTS");
		logging.entering(MODULE, "findULDListForHHT");
		ULDListVO listVo = new ULDListVO();
		try {
			listVo = constructDAO().findULDListForHHT(companyCode, uldNumber);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
		return listVo;
	}

	//Commented as part of ICRD-21184
	/**
	 *
	 * A-1950
	 * 
	 * @param companyCode
	 * @param twoalphacode
	 * @param threealphacode
	 * @return
	 * @throws SystemException
	 */
//	public static String findOwnerCode(String companyCode, String twoalphacode,
//			String threealphacode) throws SystemException {
//		try {
//			return constructDAO().findOwnerCode(companyCode, twoalphacode,
//					threealphacode);
//		} catch (PersistenceException ex) {
//			throw new SystemException(ex.getErrorCode(), ex);
//		}
//	}

	/**
	 * @return Returns the uldNature.
	 */
	@Column(name = "ULDNAT")
	public String getUldNature() {
		return this.uldNature;
	}

	/**
	 * @param uldNature
	 *            The uldNature to set.
	 */
	public void setUldNature(String uldNature) {
		this.uldNature = uldNature;
	}

	/**
	 *
	 * A-1950
	 * 
	 * @param uldListFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<String> findULDs(ULDListFilterVO uldListFilterVO)
			throws SystemException {
		try {
			return constructDAO().findULDs(uldListFilterVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}

	/**
	 * @return Returns the depreciationDate.
	 */
	@Column(name = "ULDDEPDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getDepreciationDate() {
		return this.depreciationDate;
	}

	/**
	 * @param depreciationDate
	 *            The depreciationDate to set.
	 */
	public void setDepreciationDate(Calendar depreciationDate) {
		this.depreciationDate = depreciationDate;
	}

	/**
	 * @return Returns the lostDate.
	 */
	@Column(name = "ULDLSTDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getLostDate() {
		return this.lostDate;
	}

	/**
	 * @param lostDate
	 *            The lostDate to set.
	 */
	public void setLostDate(Calendar lostDate) {
		this.lostDate = lostDate;
	}

	/**
	 * @return Returns the abandonedDate.
	 */
	@Column(name = "ULDABDDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getAbandonedDate() {
		return this.abandonedDate;
	}

	/**
	 * @param abandonedDate
	 *            The abandonedDate to set.
	 */
	public void setAbandonedDate(Calendar abandonedDate) {
		this.abandonedDate = abandonedDate;
	}
	
	/**
	 * @return the lifeSpan
	 */
	@Column(name = "LIFSPN")
	public int getLifeSpan() {
		return lifeSpan;
	}

	/**
	 * @param lifeSpan the lifeSpan to set
	 */
	public void setLifeSpan(int lifeSpan) {
		this.lifeSpan = lifeSpan;
	}

	/**
	 * @return the manufactureDate
	 */
	@Column(name = "MANDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getManufactureDate() {
		return manufactureDate;
	}

	/**
	 * @param manufactureDate the manufactureDate to set
	 */
	public void setManufactureDate(Calendar manufactureDate) {
		this.manufactureDate = manufactureDate;
	}

	/**
	 * @return the tsoNumber
	 */
	@Column(name = "TSONUM")
	public String getTsoNumber() {
		return tsoNumber;
	}

	/**
	 * @param tsoNumber the tsoNumber to set
	 */
	public void setTsoNumber(String tsoNumber) {
		this.tsoNumber = tsoNumber;
	}
	
	/**
	 * @return the releasedTo
	 */
	
	
	@Column(name = "RELTOO")
	public String getReleasedTo() {
		return releasedTo;
	}

	/**
	 * @param releasedTo the releasedTo to set
	 */
	public void setReleasedTo(String releasedTo) {
		this.releasedTo = releasedTo;
	}

	/**
	 * @return Returns the scmDate.
	 *@Temporal is changed from DATE to TIMESTAMP for 34176 on 21Jan09
	 */
	@Column(name = "SCMDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getScmDate() {
		return scmDate;
	}

	/**
	 * @param scmDate The scmDate to set.
	 */
	public void setScmDate(Calendar scmDate) {
		this.scmDate = scmDate;
	}

	/**
	 * @return Returns the scmFlag.
	 */
	@Column(name = "SCMFLG")
	public String getScmFlag() {
		return scmFlag;
	}

	/**
	 * @param scmFlag The scmFlag to set.
	 */
	public void setScmFlag(String scmFlag) {
		this.scmFlag = scmFlag;
	}

	/**
	 *
	 * @param companyCode
	 * @param uldnos
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ULD> findULDObjects(String companyCode,
			Collection<String> uldnos) throws SystemException {
		try {
			return constructULDObjectDAO().findULDObjects(companyCode, uldnos);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}

	/**
	 *
	 * @param companyCode
	 * @param uldnos
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ULD> findAllULDObjects(String companyCode)
			throws SystemException {
		try {
			return constructULDObjectDAO().findAllULDObjects(companyCode);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}

	/**
	 *
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
//	Changed by a-3459 for INT ULD593
	//ULDObjectDAO is changed to ULDObjectQueryInterface
	private static ULDObjectQueryInterface constructULDObjectDAO() throws SystemException,
			PersistenceException {
		return PersistenceController.getEntityManager().getObjectQueryDAO("uld.defaults");
	}

	/**
	 * @param ULDSCMReconcileVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ULDVO> findUldDatasForSCM(
			ULDSCMReconcileVO uLDSCMReconcileVO) throws SystemException {
		 try {
				return constructDAO().findUldDatasForSCM(uLDSCMReconcileVO);

			} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
			}

	}

	 /* Added by A-2412 on 18th Oct for Editable CRN cr */
	public static Collection<String> checkForDuplicateCRN(String companyCode,TransactionVO transactionVO)
			throws SystemException {
		try {
			return constructDAO().checkForDuplicateCRN(companyCode,transactionVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
		
	}
	 /* Added by A-3459 on 26th Sep for checking Single Duplicate CRN  */
	public static String findDuplicateCRN(String companyCode,String crnNumber)
			throws SystemException {
		try {
			return constructDAO().findDuplicateCRN(companyCode,crnNumber);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
		
	}
	/* Added by A-3459 on 26th Sep for checking Single Duplicate CRN ends */

	
	/**
	 * @author A-2667
	 * Added as a part of ANA CR 1471
	 * @param companyCode,userCode 
	 * @return
	 * @throws SystemException
	 */
	public static String findDefaultAirlineCode(String companyCode, String userCode)
	throws SystemException,PersistenceException{
		try {
			return constructDAO().findDefaultAirlineCode(companyCode, userCode);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}
	
	/**
	 * @author A-2667
	 * Added as a part of ANA CR 1478
	 * @param  
	 * @return
	 * @throws SystemException
	 */
	public static String findCurrentAirport(String uldNumber,String companyCode)
	throws SystemException,PersistenceException{
		try {
			return constructDAO().findCurrentAirport(companyCode,uldNumber);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}

	/**
	 * @author A-2667
	 * @param relocateULDVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public static Collection<AuditDetailsVO> findULDAuditEnquiryDetails(RelocateULDVO relocateULDVO)
	throws SystemException{
		try{
			return constructDAO().findULDAuditEnquiryDetails(relocateULDVO);
		}catch(PersistenceException e){
			throw new SystemException(e.getErrorCode() , e);
		}
	}
	
	/**
	 * @author A-1950
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public static Collection<ULDStockListVO> findStockDeviation(String companyCode)
	throws SystemException{
		try{
			return constructDAO().findStockDeviation(companyCode);
		}catch(PersistenceException e){
			throw new SystemException(e.getErrorCode() , e);
		}
	}
	/**
	 * @author A-2408
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ULD> findAllULDObjectsforSCM(String companyCode)
	throws SystemException{
		try{
		return	constructULDObjectDAO().findAllULDObjectsforSCM(companyCode);
		}catch(PersistenceException e){
			throw new SystemException(e.getErrorCode() , e);
		}
	}
	/**
	 * @author A-3429
	 * @param uldMovementFilterVO
	 * @return ULDNumberVO
	 * @throws SystemException
	 */
	public static ULDNumberVO findULDHistoryCounts(ULDMovementFilterVO uldMovementFilterVO)
    			throws SystemException{
		try{
			return constructDAO().findULDHistoryCounts(uldMovementFilterVO);
		}catch(PersistenceException e){
			throw new SystemException(e.getErrorCode() , e);
		}
	}
	
	//added by a-3045 for CR QF1020 starts
	/**
	 * @author A-3045
	 * @param companyCode
	 * @param period
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ULD> findAllLostULDObjects(String companyCode , int period)
	throws SystemException{
		try{
			return constructULDObjectDAO().findAllLostULDObjects(companyCode , period);
		}catch(PersistenceException ex){
			throw new SystemException(ex.getErrorCode(),ex);
		}
	}
	//added by a-3045 for CR QF1020 ends
	
	//added by a-3045 for bug17959 starts
	/**
	 * @author A-3045
	 * @param companyCode
	 * @param uldNumbers
	 * @return
	 * @throws SystemException
	 */
	public static Collection<String> checkULDInUse(String companyCode , Collection<String>  uldNumbers)
	throws SystemException{
		try{
			return constructDAO().checkULDInUse(companyCode , uldNumbers);
		}catch(PersistenceException ex){
			throw new SystemException(ex.getErrorCode(),ex);
		}
	}
	//added by a-3045 for bug17959 ends
	
	//added by a-3045 for bug18712 starts
	//to update status of SCm pending ULDs while processing SCM
	//This method is implemented because only 1000 ULDs can be updated using hibernate Query
	/**
	 * @author A-3045
	 * @param uldListFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static void updateSCMStatusForPendingULDs(ULDListFilterVO uldListFilterVO)
	throws SystemException{
		try{
			constructDAO().updateSCMStatusForPendingULDs(uldListFilterVO);
		}catch(PersistenceException ex){
			throw new SystemException(ex.getErrorCode(),ex);
		}
	}
	//added by a-3045 for bugbug18712 ends
	
	/**
	 * This method retrieves the uld details of the specified filter condition
	 * @author A-3045
	 * @param uldListFilterVO
	 * @return Collection<ULDListVO>
	 * @throws SystemException
	 */
	public static Collection<ULDListVO> findULDListColl(ULDListFilterVO uldListFilterVO)
	throws SystemException {
		Log logging = LogFactory.getLogger("ULD_DEFAULTS");
		logging.entering(MODULE, "findULDListColl");
		Collection<ULDListVO> listVo = null;
		try {
			listVo = constructDAO()
					.findULDListColl(uldListFilterVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
		return listVo;
	}
	
	public static Collection<ULDListVO> findUldInventoryList(ULDListFilterVO uldListFilterVO)
			throws SystemException {
		Log logging = LogFactory.getLogger("ULD_DEFAULTS");
		logging.entering(MODULE, "findUldInventoryList");
		Collection<ULDListVO> listVo = null;
		try {
			listVo = constructDAO()
					.findUldInventoryList(uldListFilterVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
		return listVo;
	}
	
	/**
	 * This method retrieves the uld details of the specified filter condition
	 * @author A-3045
	 * @param uldListFilterVO
	 * @param pageNumber
	 * @return Page<ULDVO>
	 * @throws SystemException
	 */
	public static Page<ULDVO> findULDListForSCM(ULDListFilterVO uldListFilterVO,
			int pageNumber) throws SystemException {
		Log logging = LogFactory.getLogger("ULD_DEFAULTS");
		logging.entering(MODULE, "findULDListForSCM");
		Page<ULDVO> uldVoPage = null;
		try {
			uldVoPage = constructDAO()
					.findULDListForSCM(uldListFilterVO, pageNumber);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
		return uldVoPage;
	}
	
	
	/**
	 * This method retrieves the uld details of the specified filter condition for SCM validation screen at a particular for a specified period
	 * @author A-3459
	 * @param scmValidationFilterVO
	 * @param pageNumber
	 * @return Page<SCMValidationVO>
	 * @throws SystemException
	 */
	public static Page<SCMValidationVO> findSCMValidationList(SCMValidationFilterVO scmValidationFilterVO) 
		throws SystemException {
		Log logging = LogFactory.getLogger("ULD_DEFAULTS");
		logging.entering(MODULE, "findULDListForSCM");
		Page<SCMValidationVO> scmValidationVOPage = null;
		try {
			scmValidationVOPage = constructDAO()
					.findSCMValidationList(scmValidationFilterVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
		return scmValidationVOPage;
	}
	/**
	 * 
	 * @author A-6344
	 * @param findSCMSequenceNum
	 * @return String sequencenum
	 * @throws SystemException
	 */
	public static String findSCMSequenceNum(String comapnyCode,String aiportcode,String airlineId)
	throws SystemException {
		try {
			return  constructDAO().findSCMSequenceNum(comapnyCode,aiportcode,airlineId);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
		
	}
	
	/**
	 * 
	 * @author A-3459
	 * @param scmValidationFilterVO
	 * @return Collection<SCMValidationVO>
	 * @throws SystemException
	 */
	public static Collection<SCMValidationVO> findSCMValidationListColl(SCMValidationFilterVO scmValidationFilterVO)
	throws SystemException {
		Log logging = LogFactory.getLogger("ULD_DEFAULTS");
		logging.entering(MODULE, "findULDListColl");
		Collection<SCMValidationVO> scmValidationVO = null;
		try {
			scmValidationVO = constructDAO()
					.findSCMValidationListColl(scmValidationFilterVO);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
		return scmValidationVO;
	}
	
	/**
	 * @author A-3278
	 * @param filterVO
	 * @return Collection<ULDListVO>
	 * @throws SystemException
	 */
	public static Collection<ULDListVO> findULDsForSCM(
			ULDListFilterVO filterVO) throws SystemException {
		 try {
				return constructDAO().findULDsForSCM(filterVO);

			} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
			}
	 }
	
	
	/**
	 * @author a-3278
	 * @param companyCode
	 * @param period
	 * @param userId
	 * @throws SystemException
	 */
	public static void updateSCMStatusForULD(String companyCode, int period , String userId)
	throws SystemException {
		EntityManager em = PersistenceController.getEntityManager();
		try {
			ULDDefaultsDAO uldDefaultsDAO = 
     			ULDDefaultsDAO.class.cast(
     					em.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
     		uldDefaultsDAO.updateSCMStatusForULD(companyCode,period,userId);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}
	public static Collection<ULDStockListVO> findULDStockListCollection(
			ULDStockConfigFilterVO uLDStockConfigFilterVO)
			throws SystemException {
		log.entering("ULD", "findULDStockList");
		return constructDAO().findULDStockListCollection(uLDStockConfigFilterVO);
	}
	public static Page<EstimatedULDStockVO> findEstimatedULDStockList(
			EstimatedULDStockFilterVO estimatedULDStockFilterVO,
			int displayPage)
			throws SystemException {
		Log log = LogFactory.getLogger("ULD");
		log.entering("ULD", "findEstimatedULDStockList");
		return constructDAO().findEstimatedULDStockList(estimatedULDStockFilterVO,
				displayPage);
	}
	public static Page<ExcessStockAirportVO> findExcessStockAirportList(
			ExcessStockAirportFilterVO excessStockAirportFilterVO,
			int displayPage)
			throws SystemException {
		Log log = LogFactory.getLogger("ULD");
		log.entering("ULD", "findExcessStockAirportList");
		return constructDAO().findExcessStockAirportList(excessStockAirportFilterVO,
				displayPage);
	}
	
	/**
	 * Added for ICRD-192217
	 * @author A-3791
	 * @param estimatedULDStockFilterVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public static Collection<EstimatedULDStockVO> findULDStockListForNotification(EstimatedULDStockFilterVO estimatedULDStockFilterVO)
			throws SystemException {
		return constructDAO().findULDStockListForNotification(estimatedULDStockFilterVO);
	}
	/**
	 * Added for ICRD-192280
	 * @author A-3791
	 * @param estimatedULDStockFilterVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public static Collection<FlightDetailsVO> findUCMMissingFlights(EstimatedULDStockFilterVO estimatedULDStockFilterVO)
			throws SystemException {
		return constructDAO().findUCMMissingFlights(estimatedULDStockFilterVO);
	}
	
	/*
	 * Added by A-3415 for ICRD-113953.
	 * Only if the damage status matches the status in the system parameter
	 * the overall status is to be set as non operationl
	 */
	private boolean isULDNonOperational(String damageStatus){
		boolean isULDNonOperational = false;
		Collection<String> systemparameterCodes = new  ArrayList<String>();
		Map<String,String> map = new HashMap<String,String>();
		systemparameterCodes.add(ULDVO.NON_OPERATIONAL_DAMAGE_STATUS);
		try {
			map = Proxy.getInstance().get(SharedDefaultsProxy.class).findSystemParameterByCodes(systemparameterCodes);
		} catch (ProxyException proxyException) {
			log.log(Log.SEVERE, "System Parameter for Damage Status not found ");
		} catch (SystemException SystemException) {
			log.log(Log.SEVERE, "System Parameter for Damage Status not found ");
		}
		if(map!=null && damageStatus!=null){
			String nonOperationalDamageStatus = map.get(ULDVO.NON_OPERATIONAL_DAMAGE_STATUS);
			if(nonOperationalDamageStatus!=null && nonOperationalDamageStatus.indexOf(damageStatus)>-1){
				isULDNonOperational=true;
			}			
		}		
		return isULDNonOperational;
	}
	
	/**
	 * 
	 * @param sernum
	 * @return
	 */
	private static boolean isValidULDSerialNumber(String serNo){
		if(!nullOrEmpty(serNo)){
			if(serNo.matches(("[0-9]+")) || serNo.matches("[A-Z]{1}[0-9]+")){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Added By : A-5444
	 * @param value
	 * @return
	 */
	private static boolean nullOrEmpty(String value){
		return (value==null || value.trim().length()<=0);
	} 
	/**
	 * @return the lastMovementDetails
	 */
	@Column(name="LSTMVTDTL")
	public String getLastMovementDetails() {
		return lastMovementDetails;
	}
	/**
	 * @param lastMovementDetails the lastMovementDetails to set
	 */
	public void setLastMovementDetails(String lastMovementDetails) {
		this.lastMovementDetails = lastMovementDetails;
	} 
	
	/**
	 * @author A-7794 
	 * Added as part of ICRD- 208677
	 * This method lists the ULD audit Details       
	 * @param operationalULDAuditFilterVO
	 * @return Page<OperationalULDAuditVO>
	 * @throws SystemException
	 */
	public Page<OperationalULDAuditVO> findOprAndMailULDAuditDetails(
			OperationalULDAuditFilterVO operationalULDAuditFilterVO)
			throws SystemException {
		try {
			return constructDAO().findOprAndMailULDAuditDetails(
					operationalULDAuditFilterVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getMessage();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * 	Method		:	ULD.findULDObjectsForDepreciation
	 *	Added by 	:	A-7359 on 09-May-2018
	 * 	Used for 	:	ICRD-233082 ULD Current Value Depreciation
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<ULD>
	 * @throws SystemException 
	 */
	public static Collection<ULD> findULDObjectsForDepreciation(
			String companyCode) throws SystemException {
		try {
			return constructULDObjectDAO().findULDObjectsForDepreciation(companyCode);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}
	//added by A-8368 as part of ICRD-333282
	/**
	 * @author A-8368
	 * @param companyCode
	 * @param rowCount
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ULDFlightMessageReconcileVO> constructULDFlightMessageReconcileVO(String companyCode , int rowCount)
	throws SystemException{
	  try {
		return constructULDObjectDAO().findUldsForMarkMovement(companyCode, rowCount);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}
	
	/**
	 * 
	 * 	Method		:	ULD.findSystemParameterByCodes
	 *	Added by 	:	A-5444 on 25-Sep-2019
	 * 	Used for 	:
	 *	Parameters	:	@param paramsCodes
	 *	Parameters	:	@return 
	 *	Return type	: 	Map<String,String>
	 */
	@SuppressWarnings({ "unchecked" })
	private static Map<String,String> findSystemParameterByCodes(String[] paramsCodes){
		Map<String,String> params = new HashMap<String,String>();
		try {
			params = new SharedDefaultsProxy().findSystemParameterByCodes(
					Arrays.asList(paramsCodes));
		} catch (ProxyException proxyException) {
			log.log(Log.SEVERE, "System Parameter for Damage Status not found ");
		} catch (SystemException SystemException) {
			log.log(Log.SEVERE, "System Parameter for Damage Status not found ");
		}
		return params;
	}
	
	/**
	 * 
	 * 	Method		:	ULD.isULDInTransactionEnabled
	 *	Added by 	:	A-5444 on 25-Sep-2019
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	boolean
	 */
	private static boolean isULDInTransactionEnabled() {
		final String ULD_IN_TRANSACTION = "uld.default.enableuldtransaction";
		//if true or null and restrict deletion
		//if false allow deletion 
		Map<String, String> params = 
				findSystemParameterByCodes(
						new String[] { ULD_IN_TRANSACTION });
		boolean uldInTransactionEnabled = (params==null || params.isEmpty() ||
				!OperationalULDAuditVO.FLAG_NO.equals(params.get(ULD_IN_TRANSACTION))) ? 
						true: false;
		return uldInTransactionEnabled;
	}
}
