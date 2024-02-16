/*
 * Stock.java Created on Sep 3, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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

import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentForBookingVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.country.vo.CountryVO;
import com.ibsplc.icargo.business.shared.area.country.vo.CountryValidationVO;
import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.shared.document.vo.SharedRangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.proxy.DocumentTypeProxy;
import com.ibsplc.icargo.business.stockcontrol.defaults.proxy.OperationsShipmentProxy;
import com.ibsplc.icargo.business.stockcontrol.defaults.proxy.SharedAirlineProxy;
import com.ibsplc.icargo.business.stockcontrol.defaults.proxy.SharedAreaProxy;
import com.ibsplc.icargo.business.stockcontrol.defaults.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.BlacklistStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestForOALVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockReuseHistoryVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockVO;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsDAO;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Represents the stock held by a stockholder against a particular
 * document type. Also holds the information regarding the approver
 * and reorder properties
 * @author A-1358
 *
 */

@Table(name="STKHLDSTK")
@Entity
@Staleable
public class Stock {

	 private Log log = LogFactory.getLogger("STOCK");

	 private static final String MODULE_NAME = "stockcontrol.defaults";
	 private static final String P =	"P";
	 private static final String C =	"C";
	 private static final String ENHANCESTOCKCONTROL ="stockcontrol.defaults.enhanceawbreuserestrictioncheck";
	 private static final String CUTOVERPARAMETERS ="admin.defaults.cutoverparameters";

    private StockPK stockPK;

    /**
     * This field holds the company code of the approver of this stock
     */
    private String stockApproverCompany;

    /**
     * This field holds the stockHolderCode for the approver of
     * this stock
     */
    private String stockApproverCode;

    /**
     * Reorder lvel of teh stock. When stock inhand reaches this level
     * system autorequest for new stock or generate alerts based on setup
     */
    private long reorderLevel;

    /**
     * Indicates the quantity to be ordered along with each automatic request
     */
    private int reorderQuantity;

    /**
     * Indicates whether an alert is to be generated when stock reaches
     * the reorder level. Possible values are 'Y' and 'N'
     */
    private String reorderAlertFlag;

    /**
     * Indicates whether stock request needs to be raised automatically when
     * stock reaches the reorder level
     */
    private String autoRequestFlag;

    /**
     * General remarks
     */
    private String remarks;
    /**
     * physicalAvailableStock
     */
    private long physicalAvailableStock;
    /**
     * physicalAllocatedStock
     */
    private long physicalAllocatedStock;
    /**
     * manualAvailableStock
     */
    private long manualAvailableStock;
    /**
     * manualAllocatedStock
     */
    private long manualAllocatedStock;

    /**
     * autoprocessQuantity
     */
    private int autoprocessQuantity;

    /**
     * Collection of ranges that constitute this stock
     * Set<Range>
     */
    private Set<Range> ranges;

    private String autoPopulateFlag;
    /**
     * @return Returns the stockPK.
     */
     @EmbeddedId
     @AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="airlineIdentifier", column=@Column(name="ARLIDR")),
		@AttributeOverride(name="stockHolderCode", column=@Column(name="STKHLDCOD")),
		@AttributeOverride(name="documentType", column=@Column(name="DOCTYP")),
		@AttributeOverride(name="documentSubType", column=@Column(name="DOCSUBTYP"))}
		)
	public StockPK getStockPK() {
        return stockPK;
    }

    /**
     * @param stockPK The stockPK to set.
     */
    public void setStockPK(StockPK stockPK) {
        this.stockPK = stockPK;
    }

    /**
     * 
     * @return autoPopulateFlag
     */
    @Column(name="AUTGENFLG")
    public String getAutoPopulateFlag() {
		return autoPopulateFlag;
	}
    /**
     * 
     * @param autoPopulateFlag
     */
	public void setAutoPopulateFlag(String autoPopulateFlag) {
		this.autoPopulateFlag = autoPopulateFlag;
	}
	/**
     * @return Returns the autoRequestFlag.
     */
    @Column(name="AUTREQFLG")
    @Audit(name="autoRequestFlag")
    public String getAutoRequestFlag() {
        return autoRequestFlag;
    }

    /**
     * @param autoRequestFlag The autoRequestFlag to set.
     */
    public void setAutoRequestFlag(String autoRequestFlag) {
        this.autoRequestFlag = autoRequestFlag;
    }

    /**
     * @return Returns the ranges.
     */

	@OneToMany
		@JoinColumns({
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD",insertable=false, updatable=false),
			@JoinColumn(name = "ARLIDR", referencedColumnName = "ARLIDR",insertable=false, updatable=false),
			@JoinColumn(name = "STKHLDCOD", referencedColumnName = "STKHLDCOD",insertable=false, updatable=false),
			@JoinColumn(name = "DOCTYP", referencedColumnName = "DOCTYP",insertable=false, updatable=false),
			@JoinColumn(name = "DOCSUBTYP", referencedColumnName = "DOCSUBTYP",insertable=false, updatable=false)}
	)
    public Set<Range> getRanges() {
        return ranges;
    }

    /**
     * @param ranges The ranges to set.
     */
    public void setRanges(Set<Range> ranges) {
        this.ranges = ranges;
    }

    /**
     * @return Returns the remarks.
     */
    @Column(name="STKRMK")
    //@Audit(name="remarks")
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks The remarks to set.
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return Returns the reorderAlertFlag.
     */
    @Column(name="ORDALT")
    @Audit(name="reorderAlertFlag")
    public String getReorderAlertFlag() {
        return reorderAlertFlag;
    }

    /**
     * @param reorderAlertFlag The reorderAlertFlag to set.
     */
    public void setReorderAlertFlag(String reorderAlertFlag) {
        this.reorderAlertFlag = reorderAlertFlag;
    }

    /**
     * @return Returns the reorderLevel.
     */
    @Column(name="ORDLVL")
    @Audit(name="reorderLevel")
    public long getReorderLevel() {
        return reorderLevel;
    }

    /**
     * @param reorderLevel The reorderLevel to set.
     */
    public void setReorderLevel(long reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    /**
     * @return Returns the reorderQuantity.
     */
    @Column(name="ORDQTY")
    @Audit(name="reorderQuantity")
    public int getReorderQuantity() {
        return reorderQuantity;
    }

    /**
     * @param reorderQuantity The reorderQuantity to set.
     */
    public void setReorderQuantity(int reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
    }

    /**
     * @return Returns the stockApproverCode.
     */
    @Column(name="STKAPRCOD")
    //@Audit(name="stockApproverCode")
    public String getStockApproverCode() {
        return stockApproverCode;
    }

    /**
     * @param stockApproverCode The stockApproverCode to set.
     */
    public void setStockApproverCode(String stockApproverCode) {
        this.stockApproverCode = stockApproverCode;
    }

    /**
     * @return Returns the stockApproverCompany.
     */
    @Column(name="STKAPRCMPCOD")
    //@Audit(name="stockApproverCompany")
    public String getStockApproverCompany() {
        return stockApproverCompany;
    }

    /**
     * @param stockApproverCompany The stockApproverCompany to set.
     */
    public void setStockApproverCompany(String stockApproverCompany) {
        this.stockApproverCompany = stockApproverCompany;
    }
   /**
    * Method for getting manually allocatedstock
    * @return manualAllocatedStock
    */
    @Column(name="MNLSTKQTYALC")
    @Audit(name="manualAllocatedStock")
	public long getManualAllocatedStock() {
		return manualAllocatedStock;
	}
	/**
	 * Method for setting manual allocating stock
	 * @param manualAllocatedStock
	 */
	public void setManualAllocatedStock(long manualAllocatedStock) {
		this.manualAllocatedStock = manualAllocatedStock;
	}
	/**
	 * Method for manually available stock
	 * @return manualAvailableStock
	 */
	@Column(name="MNLSTKQTYAVL")
	 @Audit(name="manualAvailableStock")
	public long getManualAvailableStock() {
		return manualAvailableStock;
	}
	/**
	 * Method for setting manual available stock
	 * @param manualAvailableStock
	 */
	public void setManualAvailableStock(long manualAvailableStock) {
		this.manualAvailableStock = manualAvailableStock;
	}
	/**
	 * Method for getting physically allocated Stock
	 * @return physicalAllocatedStock
	 */
	@Column(name="PHYSTKQTYALC")
	 @Audit(name="physicalAllocatedStock")
	public long getPhysicalAllocatedStock() {
		return physicalAllocatedStock;
	}
	/**
	 * Method for setting physical stock
	 * @param physicalAllocatedStock
	 */
	public void setPhysicalAllocatedStock(long physicalAllocatedStock) {
		this.physicalAllocatedStock = physicalAllocatedStock;
	}
	/**
	 * Method for getting physically available stock
	 * @return physicalAvailableStock
	 */
	@Column(name="PHYSTKQTYAVL")
	 @Audit(name="physicalAvailableStock")
	public long getPhysicalAvailableStock() {
		return physicalAvailableStock;
	}
	/**
	 * Method for setting the physical available stock
	 * @param physicalAvailableStock
	 */
	public void setPhysicalAvailableStock(long physicalAvailableStock) {
		this.physicalAvailableStock = physicalAvailableStock;
	}

    /**
     * Default Constructor
     */
    public Stock() {
        super();
    }

    /**
     * This constructor is used to create a new stock
     * @param companyCode
     * @param stockHolderCode
     * @param stockVO
     * @throws SystemException
     */
    public Stock(String companyCode,String stockHolderCode,StockVO stockVO)
    throws SystemException{
    	populatePk(companyCode,stockHolderCode,stockVO);
        populateAttributes(stockVO,companyCode);
         try{
        	PersistenceController.getEntityManager().persist(this);
        }catch(CreateException createException){
           	throw new SystemException(createException.getErrorCode());
        }
        /*if(stockVO.getRanges()!=null){
        populateRanges(stockVO.getRanges());
        }*/
       //To be reviewed populate Ranges
    }

    /**
     * Populate the PK class
     * @param companyCode
     * @param stockHolderCode
     * @param stockVO
     * @throws SystemException
     */
    private void populatePk(String companyCode,String stockHolderCode,
    		StockVO stockVO)throws SystemException {
    	//LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
    	StockPK stockPk=new StockPK();
    	stockPk.setCompanyCode(companyCode);
    	stockPk.setAirlineIdentifier(stockVO.getAirlineIdentifier());
    	stockPk.setStockHolderCode(stockHolderCode);
    	stockPk.setDocumentType(stockVO.getDocumentType());
    	stockPk.setDocumentSubType(stockVO.getDocumentSubType());
    	this.stockPK=stockPk;
    }

   /**
    * Populate stock attributes
    * @param stockVO
    * @param companyCode
    */
    private void populateAttributes(StockVO stockVO,String companyCode) {
    	if(stockVO.isAutoRequestFlag()){
    		this.setAutoRequestFlag(StockVO.FLAG_YES);
    	}else{
    		this.setAutoRequestFlag(StockVO.FLAG_NO);
    	}
    	if(stockVO.isReorderAlertFlag()){
    		this.setReorderAlertFlag(StockVO.FLAG_YES);
    	}else{
    		this.setReorderAlertFlag(StockVO.FLAG_NO);
    	}
    	if(stockVO.isAutoPopulateFlag()){
    		this.setAutoPopulateFlag(StockVO.FLAG_YES);
    	}else{
    		this.setAutoPopulateFlag(StockVO.FLAG_NO);
    	}
    	this.setRemarks(stockVO.getRemarks());
    	this.setReorderLevel(stockVO.getReorderLevel());
    	this.setReorderQuantity(stockVO.getReorderQuantity());
    	this.setStockApproverCode(stockVO.getStockApproverCode());
    	this.setStockApproverCompany(companyCode);
    	//Added for Auto processing
    	this.setAutoprocessQuantity(stockVO.getAutoprocessQuantity());
     }


    /**
	 * @param stockFilterVO
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 * Added by A-3791 for ICRD-110209
	 */
	public static Stock find(StockFilterVO stockFilterVO) 
			throws SystemException, FinderException{
		StockPK stockPk=new StockPK();
		stockPk.setCompanyCode(stockFilterVO.getCompanyCode());
		stockPk.setAirlineIdentifier(stockFilterVO.getAirlineIdentifier());
		stockPk.setDocumentType(stockFilterVO.getDocumentType());
		stockPk.setDocumentSubType(stockFilterVO.getDocumentSubType());
		stockPk.setStockHolderCode(stockFilterVO.getStockHolderCode());
		return PersistenceController.getEntityManager().find(
				Stock.class, stockPk);
	}
    /**
     * Remove method for stock entity
     * @throws SystemException
     */
    public void remove() throws SystemException {
      	try{
      		EntityManager entityManager = PersistenceController.
      		getEntityManager();
	   		entityManager.remove(this);
		}catch(RemoveException removeException){
			throw new SystemException(removeException.getErrorCode());
		}
    }

    /**
     * @param companyCode
     * @param airlineIdentifier
     * @param documentVO
     * @return
     * @throws SystemException
     */
    public String retrieveNextDocumentNumber(DocumentFilterVO documentFilterVO,DocumentVO documentVO)////Added by A-6858 for ICRD-234889
    		throws SystemException{
    	log.log(Log.FINE,"Entering stock find document number");
    	String companyCode=documentFilterVO.getCompanyCode();
    	int airlineIdentifier=documentFilterVO.getAirlineIdentifier();
    	
    	Range resultRange= new Range();
    	long asciiStartRange=0;
    	long airlineId=0;
    	boolean isStockRangeUtilised = false;
    	StringBuilder result=new StringBuilder();
    	

    	for (Range rangeIndex : this.getRanges()) {
			if (StockVO.FLAG_NO.equals(rangeIndex.getIsManual())) {
				resultRange = rangeIndex;
				// }
				// added for checking weather stockRange(documentNumber) is
				// already utilised
				isStockRangeUtilised = checkStockRangeUtilised(companyCode,
						airlineIdentifier, null, documentVO.getDocumentType(),documentVO.getDocumentSubType(), resultRange
								.getStartRange());
				asciiStartRange = resultRange.getAsciiStartRange();

				// Added by @author A-3351 for bug (92491) fix
				//if condition added A-5234 for ICRD-37740
				if(!isStockRangeUtilised){
					if(DocumentValidationVO.DOC_TYP_AWB.equals(documentVO.getDocumentType())){
						//Changed by A-6858 for ICRD-234889
					isStockRangeUtilised = checkAWBExistsInOperation(documentFilterVO,
								asciiStartRange,documentVO.getDocumentType(),documentVO.getDocumentSubType());
					}else{
						try{ 
			    			log.log(Log.FINE, "---->StockRangeUtilisationLog masterDocumentNumber---->", asciiStartRange);	
			    		new StockRangeUtilisationLog(companyCode, 
			    				airlineIdentifier,String.valueOf(asciiStartRange),documentVO.getDocumentType(),documentVO.getDocumentSubType()); 
			    		} 
			
			    		catch(SystemException se){ 
			    			isStockRangeUtilised = true; 
			    		}  
					}
				}

				// log.log(Log.FINE,"isStockRangeUtilised is "+
				// isStockRangeUtilised+"  "+asciiStartRange);
				while (isStockRangeUtilised) {
					asciiStartRange = asciiStartRange + 1;
					if ((asciiStartRange >= resultRange.getAsciiStartRange())
							&& (asciiStartRange <= resultRange
									.getAsciiEndRange())) {
						isStockRangeUtilised = checkStockRangeUtilised(
								companyCode, airlineIdentifier, null, documentVO.getDocumentType(),documentVO.getDocumentSubType(),
								String.valueOf(asciiStartRange));
						// log.log(Log.FINE,"isStockRangeUtilised is "+
						// isStockRangeUtilised+"  "+asciiStartRange);

						// Added by @author A-3351 for bug (92491) fix
						//if condition added A-5234 for ICRD-37740
						if(!isStockRangeUtilised){
							if(DocumentValidationVO.DOC_TYP_AWB.equals(documentVO.getDocumentType())){
							isStockRangeUtilised = checkAWBExistsInOperation(
									documentFilterVO, asciiStartRange,documentVO.getDocumentType(),documentVO.getDocumentSubType());
							}
							else{
								try{ 
					    			log.log(Log.FINE, "---->StockRangeUtilisationLog masterDocumentNumber---->", asciiStartRange);	
					    		new StockRangeUtilisationLog(companyCode, 
					    				airlineIdentifier,String.valueOf(asciiStartRange),documentVO.getDocumentType(),documentVO.getDocumentSubType()); 
					    		} 
					
					    		catch(SystemException se){ 
					    			isStockRangeUtilised = true; 
					    		}  
							}
						}
					} else {
						break;
					}
				}
				if (!isStockRangeUtilised
						) {
					break;
				}
			}
		}
	   	Boolean validRange = checkDocumentExists(airlineIdentifier,asciiStartRange);
		log.log(Log.FINE, "validRange ", validRange);
		if(!validRange){
			throw new SystemException(StockControlDefaultsBusinessException.STOCKHOLDER_STOCK_NOTFOUND);
    	 }
    	log.log(Log.FINE, "result range:", resultRange.getStartRange());
		//asciiStartRange=resultRange.getAsciiStartRange();
    	SharedAirlineProxy sharedAirlineProxy = new SharedAirlineProxy();
    	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
    	
	
    	
    	
		StringBuilder resultBuilder = new StringBuilder();
    	if( documentVO!=null){
    		
    			
    		 if((StockVO.FLAG_NO).equals(documentVO.getCheckDigitFlag())){
    			 String numFormat = documentVO.getFormatString();
    			 int lengthOfNumFormat=numFormat.trim().length();
    			if(numFormat!=null && lengthOfNumFormat>1){
    			String numOfDigits= numFormat.substring(1,lengthOfNumFormat);
    			int lengthOfStockFormat=Integer.parseInt(numOfDigits);
    			
    			int lengthOfStockGen =new StringBuilder().append(asciiStartRange).length();
    			int indexLength = lengthOfStockFormat-lengthOfStockGen;
    			//Added as part ICRD-46860
    			if(!DocumentVO.DOCUMENT_ALPHANUMVALIDATOR.equals(documentVO.getDocumentFormat())) {
	    			for(int index = 0;index<indexLength;index++){
	    	    		resultBuilder.append("0");
	    	    	}
    			}
    			resultBuilder.append(asciiStartRange);
    			}
                  return resultBuilder.toString();  			
    		 }
    		
    		 else{
    	try{
    		airlineValidationVO = sharedAirlineProxy.findAirline(companyCode,
    			airlineIdentifier);
    	}catch(ProxyException proxyException){
    		for(ErrorVO errorVO : proxyException.getErrors()){
    			throw new SystemException(errorVO.getErrorCode());
    		}
    	}
    	int checkDigit = airlineValidationVO.getAwbCheckDigit();
    	if(checkDigit!=0){
    		airlineId=asciiStartRange%checkDigit;
    	}
    	result=result.append(asciiStartRange).append(airlineId);
       	String resultRng = result.toString();
    	log.log(Log.FINE, "--Result Obtained--", resultRng);
		int length = resultRng.length();
    	int indexLength = 8-length;
    	log.log(Log.FINE, "---->length sub---->", indexLength);
		
    	for(int index = 0;index<indexLength;index++){
    		resultBuilder.append("0");
    	}
    	resultBuilder.append(resultRng);    
    	log.log(Log.FINE, "---Final Result--", resultBuilder.toString());
		
    }}
		return resultBuilder.toString();
    }
       /**
         * Stock is checked in STKRNGUTL and STKRNGUTLLOG (when more one user captures AWB )
		 * @param companyCode
		 * @param airlineId
		 * @param stockHolderCode
		 * @param documentType
		 * @param documentNumber
		 * @return
		 * @throws SystemException   
		 */
		public boolean checkStockRangeUtilised(String companyCode,
					int airlineId,String stockHolderCode, String documentType, String documentSubType,
					String documentNumber)throws SystemException{
				log.entering("Stock", "checkStockRangeUtilised");
				boolean isStockUtilised=false;
				try { 
					isStockUtilised= constructDAO().checkStockRangeUtilised(companyCode,airlineId,
											stockHolderCode,documentType,documentSubType,documentNumber);
					if(!isStockUtilised) {
						isStockUtilised=constructDAO().checkStockRangeUtilisedLog(companyCode, airlineId, documentNumber, documentType, documentSubType);
					}
					return isStockUtilised;   
				} catch (PersistenceException persistenceException) {
				throw new SystemException(persistenceException.getErrorCode());
				}
		}
    /**
     * Indicates whether this stock has ranges under it
     * @return boolean
     */
    public boolean hasRanges() {
       boolean hasRange=false;
       if(this.getRanges() != null && this.getRanges().size()>0){
    	   hasRange=true;
       }
    	   return hasRange;
    }

    /**
     * Updates the stock entity based on stockVO
     * @param stockVO
     * @param companyCode
     */
    public void update(StockVO stockVO,String companyCode) {
    	populateAttributes(stockVO,companyCode);
    }

    /**
     * Method for allocating the stock with ranges
     * @param stockAllocationVO
     * @throws SystemException
     * @throws RangeExistsException
     */
    public void allocate(StockAllocationVO stockAllocationVO)
    throws SystemException,RangeExistsException{
    	Collection<RangeVO> availableRange=new ArrayList<RangeVO>();
    	Collection<RangeVO> persistingRanges=new ArrayList<RangeVO>();
    	Collection<RangeVO> creatingRange=new ArrayList<RangeVO>();

/*    	Collection<RangeVO> duplicateRanges = new ArrayList<RangeVO>();

    	if(stockAllocationVO.getRanges() != null){
    		duplicateRanges.addAll(stockAllocationVO.getRanges());
    	}

    	StockFilterVO stockFilterVO = new StockFilterVO();
    	stockFilterVO.setCompanyCode(stockAllocationVO.getCompanyCode());
    	stockFilterVO.setAirlineIdentifier(stockAllocationVO.getAirlineIdentifier());
    	stockFilterVO.setDocumentSubType(stockAllocationVO.getDocumentSubType());
    	stockFilterVO.setDocumentType(stockAllocationVO.getDocumentType());
    	stockFilterVO.setStockHolderCode(stockAllocationVO.getStockHolderCode());

    	StockAllocationVO stkAllocationVo = Stock.findStockForAirline(stockFilterVO);
    	if(stkAllocationVo != null && stkAllocationVo.getRanges() != null){
    		duplicateRanges.addAll(stkAllocationVo.getRanges());
    	}

    	log.log(Log.INFO,"duplicateRanges:" + duplicateRanges);
*/

    	boolean isDuplicate=false;
    	if(stockAllocationVO.isNewStockFlag()&& !(stockAllocationVO.isBlacklist())){
    	   availableRange=duplicateRangeCheck(stockAllocationVO);
    	   log.log(Log.FINER, "\n^%&&&&&$availableRange (inside newStockFlag):",
				availableRange);
			if(availableRange.size()==0){
				 creatingRange = stockAllocationVO.getRanges();
				 log.log(Log.FINER,
						"\n^%$$$$$$$creatingRange (inside newStockFlag):",
						creatingRange);
				log.log(Log.FINE,"---------->>>inside no duplicate-------->>");
			}else{
				creatingRange=splitRanges(availableRange,stockAllocationVO.getRanges());
				log.log(Log.FINER,
						"\n^%$$$$$$$creatingRange(inside newStockFlag):",
						creatingRange);
				isDuplicate=true;
			}
    	}
    	else{
    		creatingRange=stockAllocationVO.getRanges();
    		log.log(Log.FINER,
					"\n^%$$$$$$$creatingRange(outside new stockflag):",
					creatingRange);
			log.log(Log.FINE,"------->>inside the not new flag------->>>");
    	}
    	log.log(Log.FINE, "------->>Before Merge- size of creatingstock->>>",
				creatingRange.size());
		for(RangeVO rangeVod:creatingRange){
    		log.log(Log.FINE,"---------------->>Before Merge------->>>");
    		log.log(Log.FINE, "---------------->>startRange------->>>",
					rangeVod.getStartRange());
			log.log(Log.FINE, "---------------->>endRange------->>>", rangeVod.getEndRange());
    	}
    	String manual=null;
    	if(stockAllocationVO.isManual()){
    		manual="Y";
    	}else{
    		manual="N";
    	}
    	persistingRanges = mergeRanges(creatingRange,manual);
    	//log.log(Log.FINER,"\n^%$$$$$$$persistingRanges:" + persistingRanges);

    	for(RangeVO rangeAf:persistingRanges){
    		log.log(Log.FINE,"---------------->>After Merge------->>>");
    		log.log(Log.FINE, "---------------->>startRange------->>>", rangeAf.getStartRange());
			log.log(Log.FINE, "---------------->>endRange------->>>", rangeAf.getEndRange());
    	}
    	log.log(Log.INFO, "******value of manual:", manual);
		Collection<RangeVO> rangesToDelete = Range.findRangesforMerge(creatingRange);
    	
    	if(persistingRanges!=null && rangesToDelete!=null ){
    		for(RangeVO rangeVO :rangesToDelete){
    			Range range = Range.find(rangeVO);
    			range.remove();
    		}
    	}
    	/*if(persistingRanges!=null && this.getRanges() != null ){

    		//for avoiding concurrent modification exception
    		
    		for(Range range :this.getRanges()){
    			log.log(Log.INFO,"******value range.getIsManual():" + range.getIsManual());
    			log.log(Log.INFO,"******rangeSequenceNumber:" + range.getRangePK().getRangeSequenceNumber());
            	if(manual.equals(range.getIsManual())){
            		rangesToDelete.add(range);
            		range.remove();
            	}
            }
    		// for avoiding concurrent modification exception
    		if(rangesToDelete != null && rangesToDelete .size() > 0  ) {
    			this.getRanges().removeAll(rangesToDelete);
    			rangesToDelete = null;
    		}
    	}*/
//		//Added for Testing
//    	log.log(Log.INFO,"\n@@@After Removing"+" "+
//    			( this.getRanges()!= null ? String.valueOf
//    						( this.getRanges().size()):"NULL"));

    	if(persistingRanges!=null ){

        	/*
        	 * this is to keep the date of creation of range consistant
        	 * while merging and splitting. if two ranges are merged the
        	 * date of creation of ranges should be the minimum date of them.
        	 *
        	 * so a duplicate copy of the ranges is kept before merging or
        	 * splitting and then its start range is checked with the
        	 * range vo in the collection which is going to persist. if it is
        	 * same, then the date of the dup collection is restored.
        	 *
        	 */
/*    		if (duplicateRanges != null){
    			for(RangeVO persistingRangeVo : persistingRanges){
    				log.log(Log.INFO,"\n@@@@@@@@@persistingRangeVo\n"+persistingRangeVo);
    				RangeVO tempRangeVO = null;
    				for(RangeVO duplicateRangeVO : duplicateRanges){
    					if(tempRangeVO == null){
    						tempRangeVO = new RangeVO();
    						tempRangeVO.setStartRange(duplicateRangeVO.getStartRange());
    						tempRangeVO.setStockAcceptanceDate(duplicateRangeVO.getStockAcceptanceDate());
    					}
    					log.log(Log.INFO,"\n@@@@@@@@@@@@@duplicateRangeVO\n"+duplicateRangeVO);
    					log.log(Log.INFO,"persistingRangeVo.getStartRange:"+persistingRangeVo.getStartRange());
    					log.log(Log.INFO,"duplicateRangeVO.getStartRange:"+duplicateRangeVO.getStartRange());

    					if(Range.findLong(tempRangeVO.getStartRange()) > Range.findLong(duplicateRangeVO.getStartRange())){
    						tempRangeVO.setStartRange(duplicateRangeVO.getStartRange());
    						tempRangeVO.setStockAcceptanceDate(duplicateRangeVO.getStockAcceptanceDate());
    					}

    					//To be reviewed	check for the date insertion...
    					//####
    					if( Range.findLong(persistingRangeVo.getStartRange()) >=
    								Range.findLong(duplicateRangeVO.getStartRange())){
    						persistingRangeVo.setStockAcceptanceDate(duplicateRangeVO.getStockAcceptanceDate());
    					}
    				}
    			}
    		}
*/
    		log.log(Log.INFO, "Going to populateRanges ");
    		log.log(Log.INFO, "stockAllocationVO.isManual()", stockAllocationVO.isManual());
			log.log(Log.INFO, "persistingRanges", persistingRanges);
			populateRanges(persistingRanges,stockAllocationVO.isManual());
    	}

    	if(isDuplicate){
    		RangeExistsException rangeExistsException=new RangeExistsException();
    		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    		int counter = 0;
    		StringBuilder rangeExists = new StringBuilder();
    		for(RangeVO rangeVo : availableRange){
    			if(counter==0){
	        		rangeExists.append(rangeVo.getStartRange().concat("-").
	        				concat(rangeVo.getEndRange()));
	        	}else{
	        		rangeExists.append(",").append(rangeVo.getStartRange().
	        				concat("-").concat(rangeVo.getEndRange()));
	        	}
	        	counter=1;
    		}
    		Object[] errorObj=new Object[1];
	    	errorObj[0]=rangeExists.toString();
	    	ErrorVO errorVo=new ErrorVO(RangeExistsException.RANGEVALUE_EXISTS,
	    			errorObj);
    		errors.add(errorVo);
    		rangeExistsException.addErrors(errors);
    		throw rangeExistsException;
    	}
     }

    /**
     * This method is used to deplete the ranges
     * @param stockAllocationVO
     * @param isBlacklist
     * @throws SystemException
     */
    public void deplete(StockAllocationVO stockAllocationVO,boolean isBlacklist)
    throws SystemException{
    	log.log(Log.FINE,"Inside deplete of stock----->");
    	String manual = null;
    	Collection<Range> findRange = null;
    	boolean isSpecialSplit=false;
         if(stockAllocationVO.isManual()){
         	manual="Y";
         }else{
         	manual="N";
         }
    	Set<Range> rangesInside=new HashSet<Range>();
    	Collection<RangeVO> splitRange=new ArrayList<RangeVO>();
    	Collection<RangeVO> incomingRange=new ArrayList<RangeVO>();



/*    	Collection<RangeVO> duplicateRanges = new ArrayList<RangeVO>();

    	if(stockAllocationVO.getRanges() != null){
    		duplicateRanges.addAll(stockAllocationVO.getRanges());
    	}

    	StockFilterVO stockFilterVO = new StockFilterVO();
    	stockFilterVO.setCompanyCode(stockAllocationVO.getCompanyCode());
    	stockFilterVO.setAirlineIdentifier(stockAllocationVO.getAirlineIdentifier());
    	stockFilterVO.setDocumentSubType(stockAllocationVO.getDocumentSubType());
    	stockFilterVO.setDocumentType(stockAllocationVO.getDocumentType());
    	stockFilterVO.setStockHolderCode(stockAllocationVO.getStockHolderCode());

    	StockAllocationVO stkAllocationVo = Stock.findStockForAirline(stockFilterVO);
    	if(stkAllocationVo != null && stkAllocationVo.getRanges() != null){
    		duplicateRanges.addAll(stkAllocationVo.getRanges());
    	}
*/

    	for(RangeVO rangeVo:stockAllocationVO.getRanges()){
    		String startRange = rangeVo.getStartRange();
    		String endRange = rangeVo.getEndRange();
    		findRange = findRange(rangeVo,manual);
    		if( findRange!= null){
    			rangesInside.addAll(findRange);
    		}
    		rangeVo.setStartRange(startRange);
    		rangeVo.setEndRange(endRange);
       	}
    	if(rangesInside != null){
    		for(Range range : rangesInside){
    			log.log(Log.INFO, "########### startRange :", range.getStartRange());
				log
						.log(Log.INFO, "########### endRange :", range.getEndRange());
    		}
		}

    	log.log(Log.FINE, "Inside deplete of stock ranges is ----->", ranges);
		Collection<RangeVO> availableRange= new ArrayList<RangeVO>();
    	log.log(Log.FINE,"before looping");
    	if(rangesInside.size()!=0 && rangesInside!=null){
    		log.log(Log.FINE, "inisde range size !=0", rangesInside.size());
			for(Range range1 : rangesInside){
    			log.log(Log.FINE, "Inside range size !=0 ranges ----->", range1.getStartRange());
				log.log(Log.FINE, "Inside range size !=0 ranges ----->", range1.getEndRange());
			RangeVO rangeVO = retrieveRangeVO(range1);
    		   availableRange.add(rangeVO);
    		}
    	}
    	log.log(Log.FINE, "Inside deplete of availableRange is ----->",
				availableRange);
		for(RangeVO rangbefore : availableRange){
    		log.log(Log.FINE, "startRange:", rangbefore.getStartRange());
			log.log(Log.FINE, "endRange:", rangbefore.getEndRange());
    	}
    	if(availableRange!=null && availableRange.size() != 0){
    		splitRange=splitRanges(availableRange,stockAllocationVO.getRanges());
    	}
    	log.log(Log.INFO, "after splitting : ", splitRange);
		if((splitRange == null || splitRange.size() == 0) && isBlacklist){
    		log.log(Log.FINE,"Going for special splitting----->");
    		availableRange = new ArrayList<RangeVO>();
    		if(this.getRanges() != null ) {
	    		for(Range range: this.getRanges()){
	    			availableRange.add(retrieveRangeVO(range));
	    		}
    		}
    		incomingRange=checkForNotAvailableRange(availableRange,
    				stockAllocationVO.getRanges());
    		if(incomingRange != null && incomingRange.size()!=0){
    			splitRange=splitRanges(availableRange,incomingRange);
    		}
    		isSpecialSplit=true;
    	}
    	log.log(Log.FINE, "Inside deplete of splitRange is ----->", splitRange);
		if(! isSpecialSplit){
    	  		for(Range range :rangesInside){
            	if(manual.equals(range.getIsManual())){
            		this.getRanges().remove(range);
            	range.remove();
            	}
            }
    	}
    	for(RangeVO rangeVOCheck : splitRange){
    		log.log(Log.FINE, "rangeVOCheck", rangeVOCheck.getStartRange());
			log.log(Log.FINE, "RangeVOCHEck", rangeVOCheck.getEndRange());
    	}

    	if(splitRange!=null &&splitRange.size()!=0){
        	/*
        	 * this is to keep the date of creation of range consistant
        	 * while merging and splitting. if two ranges are merged the
        	 * date of creation of ranges should be the minimum date of them.
        	 *
        	 * so a duplicate copy of the ranges is kept before merging or
        	 * splitting and then its start range is checked with the
        	 * range vo in the collection which is going to persist. if it is
        	 * same, then the date of the dup collection is restored.
        	 *
        	 */
 /*   		if(duplicateRanges != null){
    			for(RangeVO splitRangeVO : splitRange){
    				for(RangeVO duplicateRangeVO : duplicateRanges){
    					log.log(Log.INFO,"splitRangeVO.getStartRange:"+splitRangeVO.getStartRange());
    					log.log(Log.INFO,"splitRangeVO.getStartRange:"+duplicateRangeVO.getStartRange());

    					if( splitRangeVO.getStartRange().equals(duplicateRangeVO.getStartRange()) ||
    							splitRangeVO.getEndRange().equals(duplicateRangeVO.getEndRange()) ||
    							( (Range.findLong(splitRangeVO.getStartRange()) >
    							Range.findLong(duplicateRangeVO.getStartRange())) &&
    							(Range.findLong(splitRangeVO.getEndRange()) <
    									Range.findLong(duplicateRangeVO.getEndRange())))
    					){
    						splitRangeVO.setStockAcceptanceDate(duplicateRangeVO.getStockAcceptanceDate());
    					}

    				}
    			}
    		}
*/
    		log.log(Log.FINE,"Going to create new range");
    		populateRanges(splitRange,stockAllocationVO.isManual());
        }
    		log.log(Log.FINE,"Exiting deplete of stock----->");
     }

    /*
     * This method is used to check the stock availabilty
     * matching the number of documents.If the available stock is
     * less than the numberOfDocuments
     * System throws StockNotFoundException
     * @param numberOfDocuments
     * @throws StockNotFoundException
     */
   /* public void checkStockAvailabilty(long numberOfDocuments)
    	throws StockNotFoundException{

    }
    /**
     * This method is used to check whether the given set of range contains
     * any blacklisted range
     * @param ranges
     * @throws BlacklistedRangeExistsException
     */
   /* public void checkBlacklistedRange(Collection ranges)
        throws BlacklistedRangeExistsException{

    }


    /**
     * This method will remove ranges
     *
     */
    /*public void removeRanges(){

    }

    /**
     * This method will create Range
     * @param ranges
     */
   /* public void createRanges(Collection ranges){

    }

    /**
     * This method will check if any dupliclate range exists
     * for a given set of ranges and the range stock persists.
     * @param ranges
     * @return boolean
     */
    /*private boolean checkDuplicateRanges(Collection ranges){
        //To be reviewed
        //Call DocumentType proxy
    	return false;

    }*/

   /**
    * This method will perform the following validations
    * 1. Checks whether the allocated ranges are valid range
    * 2. Checks allocated range exists for the stock holder
    * 3. Checks if the allocated quantity of stock exists for the stock holder
    * 4. Checks if the allocated stock have any blacklisted ranges
    * @param stockAllocationVO
    * @param isBlacklist
    * @throws StockNotFoundException
    * @throws BlacklistedRangeExistsException
    * @throws SystemException
    */
    public void validateRanges(StockAllocationVO stockAllocationVO,
    		boolean isBlacklist)
    	throws StockNotFoundException,BlacklistedRangeExistsException,
    	SystemException{
    	log.log(Log.FINE, "------------Before calling validateRanges in ",
				"Stock--------------");
		validateRangeFormat(stockAllocationVO);
    	log.log(Log.FINE,
				"------------->>>>>>>>inside validate range stock--->>",
				stockAllocationVO.isNewStockFlag());
		if(!isBlacklist){
			blacklistedRange(stockAllocationVO);
		}
    	if(stockAllocationVO.isNewStockFlag()){
			//To be reviewed call duplicate range method
		}
    	if(!stockAllocationVO.isNewStockFlag() && !isBlacklist){
    		invalidStock(stockAllocationVO);
    	}

    }

    /**
     * This method will find the blacklisted stock matching
     * the filter criteria
     * @return Page<RangeVO>
     * @throws SystemException
     */
    /*public static Page findBlacklistedStock(StockFilterVO stockFilterVO)
     *  throws	SystemException{
        return null;
    }*/

    /**
     * This method is used to blacklist a stock
     * @param blacklistStockVO
     * @throws SystemException
     */
    public void blackListStock(BlacklistStockVO blacklistStockVO) throws
		SystemException{

    }
    /**
     * This method will merge the given set of range and the
     * range stock persists, if the conditions match
     * @param mergeRanges
     * @param manual
     * @return outputRanges
     * @throws SystemException
     */
    public Collection<RangeVO> mergeRanges(Collection<RangeVO> mergeRanges,
    		String manual)throws SystemException{
    	DocumentVO documentVo=new DocumentVO();
    	String companyCode = this.getStockPK().getCompanyCode();
    	String docType = this.getStockPK().getDocumentType();
    	String docSubType = this.getStockPK().getDocumentSubType();
    	Collection<SharedRangeVO> sharedRange=new ArrayList<SharedRangeVO>();
    	Collection<SharedRangeVO> sharedRangeResult=new ArrayList<SharedRangeVO>();
    	Collection<RangeVO> rangesToMerge= new ArrayList<RangeVO>();
    	Collection<RangeVO> outputRanges= new ArrayList<RangeVO>();
    	SharedRangeVO sharedRangeVO =null;
    	if(mergeRanges!=null){
    		for(RangeVO rangeVo : mergeRanges){
    			//Flag from "M" modified to "Y" for the manual flag merging issue
    			//Modified by A-7373 for ICRD-241944
    			if("Y".equalsIgnoreCase(manual)){
    				rangeVo.setManual(true);
    			} else{
    				rangeVo.setManual(false);
    			}
    			rangeVo.setCompanyCode(companyCode);
    			rangeVo.setDocumentType(docType);
    			rangeVo.setDocumentSubType(docSubType);
    			rangeVo.setAirlineIdentifier(this.getStockPK().getAirlineIdentifier());
    			rangeVo.setStockHolderCode(this.getStockPK().getStockHolderCode());
    			sharedRangeVO = new SharedRangeVO();
    			sharedRangeVO.setFromrange(rangeVo.getStartRange());
    			sharedRangeVO.setRangeDate(rangeVo.getStockAcceptanceDate());
    			sharedRangeVO.setToRange(rangeVo.getEndRange());
    			sharedRange.add(sharedRangeVO);
    			
    			//original.add(findRange(rangeVo,manual));
    		}
    		
    		rangesToMerge = Range.findRangesforMerge(mergeRanges);
    		for(RangeVO rangeVo : rangesToMerge){
    			sharedRangeVO = new SharedRangeVO();
    			sharedRangeVO.setFromrange(rangeVo.getStartRange());
    			sharedRangeVO.setRangeDate(rangeVo.getStockAcceptanceDate());
    			sharedRangeVO.setToRange(rangeVo.getEndRange());
    			sharedRange.add(sharedRangeVO);
    			
    			//original.add(findRange(rangeVo,manual));
    		}
    		log.log(Log.FINE, "-----Collection sending for merge------",
					sharedRange);
			for(SharedRangeVO sharedRangeVO1 : sharedRange){
    			log.log(Log.FINE,"-------------------");
    			log.log(Log.FINE, "sharedRangeVO", sharedRangeVO1.getFromrange());
				log.log(Log.FINE, "sharedRangeVO", sharedRangeVO1.getToRange());
				log.log(Log.FINE,"-------------------");
    		}
    		try{
    			DocumentTypeProxy documentTypeProxy= new DocumentTypeProxy();
    			documentVo.setCompanyCode(companyCode);
    			documentVo.setDocumentType(docType);
    			documentVo.setDocumentSubType(docSubType);
    			documentVo.setRange(sharedRange);
    			sharedRangeResult=documentTypeProxy.mergeRanges(documentVo);
    			log.log(Log.FINE,
						"@@@@@@@@Collection getting after merge@@@@@@",
						sharedRangeResult);
				for(SharedRangeVO sharedRangeVO1 : sharedRangeResult){
    				log.log(Log.FINE,"-------------------");
    				log.log(Log.FINE, "sharedVo1", sharedRangeVO1);
					log.log(Log.FINE, "sharedRangeVO", sharedRangeVO1.getFromrange());
					log.log(Log.FINE, "sharedRangeVO", sharedRangeVO1.getToRange());
					log.log(Log.FINE,"-------------------");
    			}
    		}catch(ProxyException proxyException){
    			for(ErrorVO errorVO : proxyException.getErrors()){
    				throw new SystemException(errorVO.getErrorCode());
    			}
    		}
    		for(SharedRangeVO sharedVo : sharedRangeResult){
    			RangeVO rangeVo = new RangeVO();
    			rangeVo.setStartRange(sharedVo.getFromrange());
    			rangeVo.setStockAcceptanceDate(sharedVo.getRangeDate());
    			rangeVo.setEndRange(sharedVo.getToRange());
    			outputRanges.add(rangeVo);
    		}
    	}
    	log.log(Log.FINE, "-------Merged ranges", outputRanges);
		return outputRanges;
    }
    /*
     * This method is used to remove the given set of range from the range
     * stock persists, which may lead to the splitting of ranges
     * @param ranges
     * @return Collection<RangevO>
     */
   /* private Collection removeRanges(Collection ranges){
        return null;
    }*/
    /**
     * Method for finding a specified range
     * @param rangeVO
     * @param manual
     * @return
     */
    private Collection<Range> findRange(RangeVO rangeVO,String manual){
    	Collection<Range> obtainedranges=new ArrayList<Range>();
    	return findRanges(obtainedranges,rangeVO,manual);
    }
    private Collection<Range> findRanges(Collection<Range> obtainedranges,RangeVO rangeVO,String manual){
    	if(this.getRanges()!= null ){
	    	for(Range range : this.getRanges()){
		    	long thisStartRange=range.getAsciiStartRange();
		    	long thisEndRange=range.getAsciiEndRange();
		    	long startRange=calculateRange(rangeVO.getStartRange());
		    	long endRange=calculateRange(rangeVO.getEndRange());
		    	if(((startRange>=thisStartRange && startRange<=thisEndRange) &&
		    			(endRange>=thisStartRange && endRange<=thisEndRange))
		    			&& manual.equals(range.getIsManual())){
		    		obtainedranges.add(range);
		    		return obtainedranges;
	    		}else{
	    			if((startRange>=thisStartRange && startRange<=thisEndRange) &&
	        				(endRange>=thisStartRange && endRange>thisEndRange) &&
	        				manual.equals(range.getIsManual())){
	    				obtainedranges.add(range);
	    				thisEndRange++;
	                    rangeVO.setStartRange(String.valueOf(thisEndRange));
	                    log.log(3, "----Ranges May be Combinational going to find the rest of ranges");
	                    return findRanges(obtainedranges,rangeVO,manual);
	    			}
	    		}
	    	}
    	}
    	return obtainedranges;
    }
    /**
     * Method for retrieving the range vo from a range object
     * @param range
     * @return RangeVO
     */
    private RangeVO retrieveRangeVO(Range range){
    	log.log(Log.FINE,"Inside retrieveRangeVO----->");
    	log.log(Log.FINE,range.getStartRange());
    	RangeVO rangeVO = new RangeVO();
    	rangeVO.setStartRange(range.getStartRange());
    	rangeVO.setEndRange(range.getEndRange());
    	rangeVO.setAsciiStartRange(range.getAsciiStartRange());
    	rangeVO.setAsciiEndRange(range.getAsciiEndRange());
    	rangeVO.setNumberOfDocuments(range.getNumberOfDocuments());
    	rangeVO.setStockAcceptanceDate(new LocalDate(LocalDate.NO_STATION,
    			Location.NONE,range.getStockAcceptanceDate(),false));
    	if("Y".equals(range.getIsManual())){
    		rangeVO.setManual(true);
    	}else{
    		rangeVO.setManual(false);
    	}
    	rangeVO.setAirlineIdentifier(range.getRangePK().getAirlineIdentifier());
    	rangeVO.setCompanyCode(range.getRangePK().getCompanyCode());
    	rangeVO.setStockHolderCode(range.getRangePK().getStockHolderCode());
    	rangeVO.setDocumentSubType(range.getRangePK().getDocumentSubType());
    	rangeVO.setDocumentType(range.getRangePK().getDocumentType());
    	rangeVO.setSequenceNumber(range.getRangePK().getRangeSequenceNumber());
    	log.log(Log.FINE, "exiting retrieveRangeVO----->", rangeVO);
		return rangeVO;
    }
   /**
    * Method for populating the range or creating a range
    * @param ranges
    * @param isManual
    * @throws SystemException
    */
    private void populateRanges(Collection<RangeVO> ranges,boolean isManual)
    throws SystemException{
    	LogonAttributes logon = ContextUtils.getSecurityContext().getLogonAttributesVO();
    	for(RangeVO rangeVo:ranges){
    		log.log(Log.INFO, "##############RangeVO", rangeVo);
			rangeVo.setNumberOfDocuments(findNumberOfDocuments(rangeVo.getEndRange()
    				,rangeVo.getStartRange()));
    		rangeVo.setAsciiStartRange(Range.findLong(rangeVo.getStartRange()));
    		rangeVo.setAsciiEndRange(Range.findLong(rangeVo.getEndRange()));
    		rangeVo.setManual(isManual);
    		rangeVo.setLastUpdateUser(logon.getUserId());
    		Range rangeNew = new Range(this.getStockPK().getCompanyCode(),
    				this.getStockPK().getStockHolderCode(),
    				this.getStockPK().getDocumentType(),
    				this.getStockPK().getDocumentSubType(),
    				this.getStockPK().getAirlineIdentifier(),rangeVo);
    		if(rangeNew !=null){
    			if(this.ranges == null ){
    				this.setRanges(new HashSet<Range>());
    			}
				this.ranges.add(rangeNew);
    		}
    	}
    }
    /**
     * Method for checking invalid range
     * @param rangeVo
     * @param manual
     * @return boolean
     * Manual flag check is removed as part of ICRD-87561
     */
    private boolean checkForInvalid(RangeVO rangeVo,String manual){
    	log.log(Log.FINE, "this.getRanges()", this.getRanges());
		if(this.getRanges() == null ) {
			return false;
		}
    	for(Range range:this.getRanges()){
    		log.log(Log.FINE,"inside checkForInvalid------>");

    		log.log(Log.INFO, " range.getStartRange() ", range.getStartRange());
			log.log(Log.INFO, " range.getEndRange() ", range.getEndRange());
			log.log(Log.INFO, " range.getAsciiStartRange() ", range.getAsciiStartRange());
			log.log(Log.INFO, " range.getAsciiEndRange() ", range.getAsciiEndRange());
			log.log(Log.INFO, " range.getIsManual() ", range.getIsManual());
			log.log(Log.INFO, " range.getNumberOfDocuments() ", range.getNumberOfDocuments());
			log.log(Log.FINE, "inside checkForInvalid manual is ------>",
					manual);
			long thisStartRange=range.getAsciiStartRange();
    		long thisEndRange=range.getAsciiEndRange();
    		long startRange=calculateRange(rangeVo.getStartRange());
    		long endRange=calculateRange(rangeVo.getEndRange());
    		log.log(Log.FINE,"inside checkForInvalid persisting ranges------>");
    		log.log(Log.FINE, "startRange:", thisStartRange);
			log.log(Log.FINE, "EndRange:", thisEndRange);
			log.log(Log.FINE,"inside checkForInvalid coming ranges------>");
    		log.log(Log.FINE, "startRange:", startRange);
			log.log(Log.FINE, "EndRange:", endRange);
			log.log(Log.FINE,
					"inside checkForInvalid persisting manual flag------>",
					range.getIsManual());
			//the below old check is added  for Transfer stock 
			if((startRange>=thisStartRange && startRange<=thisEndRange) &&
    				(endRange>=thisStartRange && endRange<=thisEndRange)&& manual.equals(range.getIsManual())){
    			log.log(Log.FINE,"check for invalid failed,stock is valid--->");
    			return false;

    		}else{
    			if((startRange>=thisStartRange && startRange<=thisEndRange) &&
        				(endRange>=thisStartRange && endRange>thisEndRange)&& manual.equals(range.getIsManual())){
    				thisEndRange++;
                    rangeVo.setStartRange(String.valueOf(thisEndRange));
                    log.log(3, "Ranges May be Combinational going to find the rest of ranges");
                    return checkForInvalid(rangeVo, manual);
    			}
    		}
    	}
    	return true;
    }
    /*
     * Method for getting previous range
     * @param range
     * @return String
     */
    /*private String previousRange(String range) {
		return String.valueOf(Integer.parseInt(range) - 1);
	}
    /**
     * Method for getting nextrange
     * @param range
     * @return String
     */
	/*private String nextRange(String range) {
		return String.valueOf(Integer.parseInt(range) + 1);
	}*/
	/**
	 * Method for merging ranges
	 * @param ranges
	 * @return Collection<RangeVO>
	 */
    /*private Collection<RangeVO> mergeRangeVo(Collection<RangeVO> ranges){
    	System.out.println("----------->>> inside merge---------------->>");
    	RangeVO rangeFirst=null;
    	RangeVO rangeSecond=null;
    	RangeVO mergedRange = null;
		boolean broken = false;
    	for(RangeVO rangeVO1 : ranges){
    		rangeFirst=rangeVO1;
    		for(RangeVO rangeVO2:ranges){
    			rangeSecond=rangeVO2;
    			if(previousRange(rangeVO1.getStartRange()).equals(rangeVO2.getEndRange())){
    			broken=true;
    			mergedRange=mergeRange(rangeVO2,rangeVO1);
    			break;
    			}
    			if(rangeVO1.getEndRange().equals(nextRange(rangeVO2.getStartRange()))){
    				broken=true;
    				mergedRange=mergeRange(rangeVO1,rangeVO2);
    				break;
    			}
    			if(broken){
    				break;
    			}
    		}

    	}
    	if(broken){
    		ranges.add(mergedRange);
			ranges.remove(rangeFirst);
			ranges.remove(rangeSecond);
			mergeRanges(ranges);
    	}

    	return ranges;
    }*/
    /*
     * Method for merging ranges
     * @param first
     * @param second
     * @return RangeVO
     */
    /*private RangeVO mergeRange(RangeVO first, RangeVO second) {
		first.setEndRange(second.getEndRange());
		return first;
	}*/
	/**
	 * Method for calculating number of documents
	 * @param rangeFrom
	 * @param rangeTo
	 * @return int
	 */
	private int findNumberOfDocuments(String rangeTo, String rangeFrom) {
		return Range.difference(rangeFrom,rangeTo);
	}
	/*
	 * Method for obtaining an integer from a string
	 * @param range
	 * @return int
	 */
	/*private int findNumeric(String range){
		return(Integer.parseInt(range));
	}*/
	/*
	 * Method for obtaining a String from an integer
	 * @param range
	 * @return String
	 */
	/*private String findString(int range){
		return String.valueOf(range);
	}*/
	/**
	 * Method for getting StockControlDefaultsDAO
	 * @return StockControlDefaultsDAO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	protected static StockControlDefaultsDAO constructDAO()
			throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return StockControlDefaultsDAO.class.cast(em.getQueryDAO(MODULE_NAME));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode(), persistenceException);
		}

		}
	 /**
	  * Method for finding blackListedRanges
	  * @param blacklistStockVO
	  * @return Collection<StockVO>
	  * @throws SystemException
	  */
	 public static Collection<StockVO> findBlacklistRanges(BlacklistStockVO
			 blacklistStockVO)
	 throws SystemException{
		 return constructDAO().findBlacklistRanges(blacklistStockVO.getCompanyCode(),
				 blacklistStockVO.getAirlineIdentifier(),
				 blacklistStockVO.getDocumentType(),blacklistStockVO.
				 getDocumentSubType(), blacklistStockVO.getRangeFrom(),
				 blacklistStockVO.getRangeTo());
	 }

	 /**
	  * Method for updating the available and allocated fields
	  * @param quantity
	  * @param isDepleteFlag
	  * @param isManualFlag
	  * @param flag
	  */
	 public void updateStatus(long quantity,boolean isDepleteFlag,
			 boolean isManualFlag,String flag){
		 log.log(Log.FINE, "------------->>>>updateStatus vo---->>", quantity);
		log.log(Log.FINE, "------------->>>>updateStatus vo---->>",
				isDepleteFlag);
		log.log(Log.FINE,"before updating");
		 log.log(Log.FINE, "manual Available stock", this.manualAvailableStock);
		log.log(Log.FINE, "manualAllocatedStock", this.manualAvailableStock);
		log.log(Log.FINE, "physical Available stock",
				this.physicalAvailableStock);
		log.log(Log.FINE, "physical AllocatedStock",
				this.physicalAllocatedStock);
		if(isManualFlag){
			 if(isDepleteFlag){
				 if(StockAllocationVO.MODE_NORMAL.equals(flag)){
					 this.manualAvailableStock = this.manualAvailableStock - quantity;
					 this.manualAllocatedStock = this.manualAllocatedStock + quantity;
				 }
				 else if(StockAllocationVO.MODE_TRANSFER.equals(flag)){
					 this.manualAvailableStock = this.manualAvailableStock
					 -quantity;
				 }
				 else if(StockAllocationVO.MODE_RETURN.equals(flag)){
					 this.manualAvailableStock = this.manualAvailableStock
					 -quantity;
				 }
			 }else{
				 if(StockAllocationVO.MODE_NORMAL.equals(flag)){
					 this.manualAvailableStock = this.manualAvailableStock
					 +quantity;
				 }
				 else if(StockAllocationVO.MODE_TRANSFER.equals(flag)){
					 this.manualAvailableStock = this.manualAvailableStock
					 +quantity;
				 }
				 else if(StockAllocationVO.MODE_RETURN.equals(flag)){
					 this.manualAvailableStock = this.manualAvailableStock
					 +quantity;
					// this.manualAllocatedStock = this.manualAllocatedStock
					// -quantity;
				 }
			 }

		 }else{
			 if(isDepleteFlag){
				 if(StockAllocationVO.MODE_NORMAL.equals(flag)){
				 this.physicalAvailableStock = this.physicalAvailableStock-quantity;
				 this.physicalAllocatedStock = this.physicalAllocatedStock+quantity;
				 }
				 else if(StockAllocationVO.MODE_TRANSFER.equals(flag)){
					 this.physicalAvailableStock = this.physicalAvailableStock
					 -quantity;
				 }
				 else if(StockAllocationVO.MODE_RETURN.equals(flag)){
					 this.physicalAvailableStock = this.physicalAvailableStock
					 -quantity;
				 }
			 }else{
				 if(StockAllocationVO.MODE_NORMAL.equals(flag)){
				 this.physicalAvailableStock=this.physicalAvailableStock+quantity;
				 }
				 else if(StockAllocationVO.MODE_TRANSFER.equals(flag)){
					 this.physicalAvailableStock=this.physicalAvailableStock
					 +quantity;
				 }
				 else if(StockAllocationVO.MODE_RETURN.equals(flag)){
					 this.physicalAvailableStock=this.physicalAvailableStock
					 +quantity;
					// this.physicalAllocatedStock=this.physicalAllocatedStock
					// -quantity;
				 }
			 }
		 }
		 log.log(Log.FINE,"after updating");
		 log.log(Log.FINE, "manual Available stock", this.manualAvailableStock);
		log.log(Log.FINE, "manualAllocatedStock", this.manualAvailableStock);
		log.log(Log.FINE, "physical Available stock",
				this.physicalAvailableStock);
		log.log(Log.FINE, "physical AllocatedStock",
				this.physicalAllocatedStock);
	 }

	/**
	 * It will check for any blacklist
	 * @param companyCode
	 * @param airlineIdentifier
	 * @param docType
	 * @param docSubType
	 * @param startRange
	 * @param endRange
	 * @return
	 * @throws SystemException
	 */
	private boolean checkForBlacklist(String companyCode,int airlineIdentifier,
			 	String docType,String docSubType,long asciiStartRange,long asciiEndRange)
	 throws SystemException{
		 if(constructDAO().checkBlacklistRanges(companyCode,airlineIdentifier,docType,docSubType,
				 asciiStartRange,asciiEndRange)!=null){
			 return true;
		 }
		 return false;
	 }
	 /**
	  * Check for any invalid stock in the stock vo
	  * @param stockAllocationVO
	  * @throws StockNotFoundException
	  */
	 private void invalidStock(StockAllocationVO stockAllocationVO)throws
	 StockNotFoundException{
		 String manual=null;
		 log.log(Log.FINE, "stockAllocationVO inside invalidStock ----->",
				stockAllocationVO);
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
	    	boolean hasErrorOccured=false;
	    	if(stockAllocationVO.isManual()){
	    		manual="Y";
	    	}else{
	    		manual="N";
	    	}
	    	int counter = 0;
	    	StringBuilder invalidRanges = new StringBuilder();
	    	for(RangeVO rangeVo: stockAllocationVO.getRanges()){
	    		log.log(Log.FINE, "rangeVo is ----->", rangeVo);
				String startRange = rangeVo.getStartRange();
	    		String endRange = rangeVo.getEndRange();
	    		if(checkForInvalid(rangeVo,manual)){
	    			log.log(Log.FINE,"Range not found----->");
	    			hasErrorOccured=true;
	    			if(counter==0){
	    				invalidRanges.append(startRange.concat("-").
	        				concat(endRange));
	    			}else{
	    				invalidRanges.append(",").append(startRange.
	        				concat("-").concat(endRange));
	    			}
	    			counter=1;
	    			log.log(Log.FINE,"888888Inside stockNotfoundException8888");
	    		}
	    		rangeVo.setStartRange(startRange);
	    		rangeVo.setEndRange(endRange);
	    	}
	    	Object[] errorObj=new Object[1];
	    	errorObj[0]=invalidRanges.toString();
	    	ErrorVO errorVo=new ErrorVO(StockNotFoundException.RANGE_NOTFOUND,
	    			errorObj);
	    	errors.add(errorVo);
	    	if(hasErrorOccured){
	    		log.log(Log.FINE,"throwing stockNotFoundException----->");
	    		StockNotFoundException stockNotFoundException=new
	    		StockNotFoundException(StockNotFoundException.RANGE_NOTFOUND);
	    		stockNotFoundException.addErrors(errors);
	    		throw stockNotFoundException;
	    	}
	 }
	 /**
	  * Check for any blacklisted stock
	  * @param stockAllocationVO
	  * @throws SystemException
	  * @throws BlacklistedRangeExistsException
	  */
	 private void blacklistedRange(StockAllocationVO stockAllocationVO)throws
	 SystemException,BlacklistedRangeExistsException{
		 Collection<ErrorVO> blacklistErrors=new ArrayList<ErrorVO>();
		 boolean hasBlacklistErrorOccured=false;
		 int counter = 0;
		 StringBuilder blacklistRanges = new StringBuilder();
		 for(RangeVO rangeVo: stockAllocationVO.getRanges()){
	    		if(checkForBlacklist(stockAllocationVO.getCompanyCode(),
	    				stockAllocationVO.getAirlineIdentifier(),
	    				stockAllocationVO.getDocumentType(),
	            		stockAllocationVO.getDocumentSubType(),rangeVo.
	            		getAsciiStartRange(),rangeVo.getAsciiEndRange())){
	    			hasBlacklistErrorOccured=true;
	            	if(counter==0){
		        		blacklistRanges.append(rangeVo.getStartRange().concat("-").
		        				concat(rangeVo.getEndRange()));
		        	}else{
		        		blacklistRanges.append(",").append(rangeVo.getStartRange().
		        				concat("-").concat(rangeVo.getEndRange()));
		        	}
		        	counter=1;
		        	log.log(Log.FINE,
							"888888888888888888888Inside stockNotfound",
							"Exception blacklist88888");
	            }
	    	}
		 	Object[] errorObj=new Object[1];
	    	errorObj[0]=blacklistRanges.toString();
	    	ErrorVO errorVo=new ErrorVO(BlacklistedRangeExistsException.
	    			BLACKLISTED_RANGE_EXISTS,errorObj);
	    	blacklistErrors.add(errorVo);
	    	if(hasBlacklistErrorOccured){
	    		BlacklistedRangeExistsException blacklistedRangeExistsException=new
	    		BlacklistedRangeExistsException();
	    		blacklistedRangeExistsException.addErrors(blacklistErrors);
	    		throw blacklistedRangeExistsException;
	    	}
	 }
	 /**
	  * Method for duplicate range exists during allocation
	  * @param stockAllocationVO
	  * @return
	  * @throws SystemException
	  */
	 private Collection<RangeVO> duplicateRangeCheck(StockAllocationVO
			 stockAllocationVO)
	 throws SystemException{
		Collection<RangeVO> availableRange=new ArrayList<RangeVO>();
		 for(RangeVO rangeVo : stockAllocationVO.getRanges()){
		    		log.log(Log.FINE,"------------>>>Inside duplicate check------->>");
		    		availableRange.addAll(constructDAO().checkDuplicateRange(
		    				stockAllocationVO.getCompanyCode(),
		    				stockAllocationVO.getAirlineIdentifier(),
		    				stockAllocationVO.getDocumentType(),stockAllocationVO
		    				.getDocumentSubType(),rangeVo.getStartRange(),
		    				rangeVo.getEndRange()));
				}
		 return availableRange;
	 }
	 /**
	  * private method for spliting the ranges by calling the proxy
	  * @param originalRanges
	  * @param availableRanges
	  * @return Collection<RangeVO>
	  * @throws SystemException
	  */
	 private Collection<RangeVO> splitRanges(Collection<RangeVO> originalRanges,
			 Collection<RangeVO> availableRanges)
	 throws SystemException{
		 log.log(Log.FINE,"Inside splitRanges of stock------>");
		 DocumentTypeProxy documentTypeProxy = new DocumentTypeProxy();
		 Collection<SharedRangeVO> splitedRange = new ArrayList<SharedRangeVO>() ;
		 Collection<SharedRangeVO> originalSharedRanges = new ArrayList<SharedRangeVO>() ;
		 Collection<SharedRangeVO> availableSharedRanges = new ArrayList<SharedRangeVO>();
		 Collection<RangeVO> splitRangeResult= new ArrayList<RangeVO>();
		 if(originalRanges != null && availableRanges !=null){
			 log.log(Log.FINE,"Inside if(originalRanges != null && availableRanges !=null)----->");
			 for(RangeVO rangeVo : originalRanges){
				 log.log(Log.FINE,"Inside for loop----->");
				 SharedRangeVO sharedVo=new SharedRangeVO();
				 sharedVo.setFromrange(rangeVo.getStartRange());
				 sharedVo.setRangeDate(rangeVo.getStockAcceptanceDate());
				 sharedVo.setToRange(rangeVo.getEndRange());
				 originalSharedRanges.add(sharedVo);
				 log.log(Log.FINE, "Inside for loop originalRanges----->",
						originalSharedRanges);
			 }
			 log.log(Log.FINE,
					"Inside splitRanges of stock originalRanges------>",
					originalSharedRanges);
			for(RangeVO rangeVo : availableRanges){
				 SharedRangeVO sharedVo=new SharedRangeVO();
				 sharedVo.setFromrange(rangeVo.getStartRange());
				 sharedVo.setRangeDate(rangeVo.getStockAcceptanceDate());
				 sharedVo.setToRange(rangeVo.getEndRange());
				 availableSharedRanges.add(sharedVo);
			 }
			 log.log(Log.FINE,
					"Inside splitRanges of stock availableSharedRanges------>",
					availableSharedRanges);
			try{
			 splitedRange=documentTypeProxy.splitRanges(originalSharedRanges,availableSharedRanges);
			 }catch(ProxyException proxyException){
				 log.log(Log.FINE,"Inside ProxyException----->");
				 for(ErrorVO errorVO : proxyException.getErrors()){
//printStackTraccee()();
						throw new SystemException(errorVO.getErrorCode());
					}
			 }
			 log.log(Log.FINE,
					"Inside splitRanges of stock splitedRange------>",
					splitedRange);
			if(splitedRange != null && splitedRange.size() !=0){
				 for(SharedRangeVO sharedVO : splitedRange){
					 RangeVO rangeVO =new RangeVO();
					 rangeVO.setStartRange(sharedVO.getFromrange());
					 rangeVO.setStockAcceptanceDate(sharedVO.getRangeDate());
					 rangeVO.setEndRange(sharedVO.getToRange());
					 splitRangeResult.add(rangeVO);

				}
			}
		 }
		/* for(RangeVO rangeVo2 : range2){
			 if(Range.findLong(rangeVo2.getStartRange()) == Range.findLong(rangeVo2.getEndRange())){
				 splitRangeResult.add(rangeVo2);
			 }
		 }*/
		 log.log(Log.FINE,"Exiting splitRanges");
		 return splitRangeResult;
	 }
	 /**
	  * Method for validating the range format
	  * @param stockAllocationVO
	  * @throws SystemException
	  */
	private void validateRangeFormat(StockAllocationVO stockAllocationVO)
	throws SystemException{
		log.log(Log.FINE,"------------Inside validateRanges in Stock--------------");
		log.log(Log.FINE, "------------stockAllocationVO--------------",
				stockAllocationVO);
		DocumentVO documentVo=new DocumentVO();
    	Collection<SharedRangeVO> sharedRange=new ArrayList<SharedRangeVO>();
    	for(RangeVO rangeVo : stockAllocationVO.getRanges()){
    		SharedRangeVO sharedRangeVO = new SharedRangeVO();
    		sharedRangeVO.setFromrange(rangeVo.getStartRange());
    		sharedRangeVO.setToRange(rangeVo.getEndRange());
    		sharedRange.add(sharedRangeVO);
    	}
    	DocumentTypeProxy documentTypeProxy = new DocumentTypeProxy();
    	documentVo.setCompanyCode(stockAllocationVO.getCompanyCode());
    	documentVo.setDocumentType(stockAllocationVO.getDocumentType());
    	documentVo.setDocumentSubType(stockAllocationVO.getDocumentSubType());
    	documentVo.setRange(sharedRange);
    	try{
    		log.log(Log.FINE, "------------before calling documentTypeProxy.",
					"validateRanges in Stock--------------");
			documentTypeProxy.validateRanges(documentVo);
    	}catch(ProxyException proxyException){
    		log.log(Log.FINE, "------------ProxyException in validateRanges ",
					"in Stock--------------");
			for(ErrorVO errorVo : proxyException.getErrors()){
//printStackTraccee()();
    			throw new SystemException(errorVo.getErrorCode(),proxyException);
    		}
    	}
	}
	/**
	 * this method is used for finding and removing the available range in case of black list
	 * @param available
	 * @param income
	 * @return
	 * @throws SystemException
	 */
	private Collection<RangeVO> checkForNotAvailableRange(Collection<RangeVO>
	available,Collection<RangeVO> income)
		throws SystemException{
		log.log(Log.FINE,"-----------inside checkfornotavailableRange----");
		long availableStartRange=0;
		long availableEndRange=0;
		long incomeStartRange=0;
		long incomeEndRange=0;
		Collection<RangeVO> outcome=new ArrayList<RangeVO>();
		Collection<RangeVO> removeList=new ArrayList<RangeVO>();
		for(RangeVO rangeVO:available){
			availableStartRange = rangeVO.getAsciiStartRange();
			availableEndRange = rangeVO.getAsciiEndRange();
			log.log(Log.FINE, "Availanle start range ---->",
					availableStartRange);
			log.log(Log.FINE, "Availanle end range ---->", availableEndRange);
			for (RangeVO inner:income){
				boolean isFlag=false;
				incomeStartRange = calculateRange(inner.getStartRange());
				incomeEndRange = calculateRange(inner.getEndRange());
				log.log(Log.FINE, "Incoming start range ---->",
						incomeStartRange);
				log.log(Log.FINE, "Incoming end range ---->", incomeEndRange);
				//ComingEnd range falls between available startrange and endrange
				//and start range lessthan available start range
				//Available 100-200 , coming 80-150
				//converting the incoming to 100-150
				if(incomeStartRange< availableStartRange
						&& incomeEndRange > availableStartRange && incomeEndRange
						< availableEndRange){
					inner.setStartRange(rangeVO.getStartRange());
					log.log(Log.FINE,"--Available 100-200 , coming 80-150--");
					outcome.add(inner);
					removeList.add(rangeVO);
					isFlag=true;
				}
				//Coming Start range falls between available startrange and endrange
				//and coming End range greater than available end range
				//Available 100-200 , coming 150-210
				//converting the incoming to 150-200
				if(incomeStartRange > availableStartRange && incomeStartRange <
						availableEndRange
						&& incomeEndRange > availableEndRange){
					inner.setEndRange(rangeVO.getEndRange());
					log.log(Log.FINE,"--Available 100-200 , coming 150-210--");
					outcome.add(inner);
					removeList.add(rangeVO);
					isFlag=true;
				}
//
				//Available 100-200 , coming 80-210
				//removing the range
				if(incomeStartRange < availableStartRange && incomeEndRange >
				availableEndRange){
					log.log(Log.FINE,"--Available 100-200 , coming 80-210--");
					removeList.add(rangeVO);
					isFlag=true;
				}
				/**
				 * Checking for equal condition
				 */
				if(incomeStartRange==availableStartRange && incomeEndRange ==
					availableEndRange){
					log.log(Log.FINE,"--Available 100-200 , coming 100-200--");
					if(!isFlag){
					removeList.add(rangeVO);
					}
				}
				log.log(Log.FINE, "Returning inner range is--->", inner);
			}

		}
		log.log(Log.FINE,"Going to remove special splitted blacklist range");
		RangePK pk = null;
		for (RangeVO vo: removeList){
			log.log(Log.FINE, "RangeVO----------->", vo);
			pk = new RangePK();
			pk.setCompanyCode(vo.getCompanyCode());
			pk.setDocumentType(vo.getDocumentType());
			pk.setDocumentSubType(vo.getDocumentSubType());
			pk.setStockHolderCode(vo.getStockHolderCode());
			pk.setRangeSequenceNumber(vo.getSequenceNumber());
			pk.setAirlineIdentifier(vo.getAirlineIdentifier());
			if(this.getRanges()!= null ){
				for(Range range:this.getRanges()){
					if(range.getRangePK().equals(pk)){
						log.log(Log.FINE,"Match found----------->");
						range.remove();
					}
				}
			}
		}
		return outcome;
	}
	/*
	 * Method for find the Between ranges
	 * @param rangeVO
	 * @param manual
	 * @return Range
	 */
	/*private Range findBetweenRange(RangeVO rangeVO,String manual){
		Range ranges=null;
    	for(Range range : this.getRanges()){
    	int thisStartRange=Integer.parseInt(range.getStartRange());
    	int thisEndRange=Integer.parseInt(range.getEndRange());
    	int startRange=Integer.parseInt(rangeVO.getStartRange());
    	int endRange=Integer.parseInt(rangeVO.getEndRange());
    	if((startRange>=thisStartRange && startRange<thisEndRange) ||
    			(endRange>=thisStartRange && endRange<=thisEndRange) &&
    			 manual.equals(range.getIsManual())){
    		  ranges=range;
    		  break;
     		}
    	}

    	return ranges;

	}*/
	/**
	 * This method is for calculating the long valude from sting
	 * @param range
	 * @return
	 */
	private long calculateRange(String range){
		return Range.findLong(range);
	}

	/*
	 * This method is for getting the range with same start and end range
	 *  in comparing
	 * the persisted ranges
	 * @param range1
	 * @param range2
	 * @return RangeVO
	 */
	/*private RangeVO equalRange(Collection<RangeVO> range1,RangeVO range2){
		RangeVO outcome=null;
		for(RangeVO  range1Vo:range1){
			if(range1Vo.getStartRange()!= range2.getStartRange() &&
					range1Vo.getEndRange()!=range2.getEndRange()){
				outcome=range2;
			}
		}

		return outcome;
	}*/







//****************************************************************************
//******************          R2 coding starts      **************************
//****************************************************************************

	/*
	 * Added by Sinoob
	 */
    /**
     * @param stockFilterVO
     * @return StockAllocationVO
     * @throws SystemException
     */
    public static StockAllocationVO findStockForAirline(StockFilterVO stockFilterVO)
			throws SystemException{
    	return constructDAO().findStockForAirline(stockFilterVO);
    }

    //	Added by Sinoob ends

    /**
     * @author A-1863
     * @param stockFilterVO
     * @return Page<StockVO>
     * @throws SystemException
     */
    public static Page<StockVO> findStockList(StockFilterVO stockFilterVO)
    												throws SystemException {
    	try{
        return constructDAO().findStockList(stockFilterVO);
    	}catch(PersistenceException exception){
    		throw new SystemException(exception.getErrorCode(),exception);
    	}
    }
    /**
     * @author A-1863
     * @param stockFilterVO
     * @return Collection<StockRequestForOALVO>
     * @throws SystemException
     */
    public static Collection<StockRequestForOALVO>
    			findStockRequestForOAL(StockFilterVO stockFilterVO)
												throws SystemException {
    	try{
            return constructDAO().findStockRequestForOAL(stockFilterVO);
        	}catch(PersistenceException exception){
        		throw new SystemException(exception.getErrorCode(),exception);
        	}
    }

    /**
     * @param systemparameterCodes
     * @return
     * @throws SystemException
     */
    public static Map<String, String> findSystemParameterByCodes(
			Collection<String> systemparameterCodes) throws SystemException{
    	Map<String, String> map = null;
    	try {
			map = new SharedDefaultsProxy().findSystemParameterByCodes(systemparameterCodes);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage(),proxyException);
		}
		return map;
    }

    /*
     * added by Sinoob
     */

    /**
     * @param companyCode
     * @param airportCode
     * @param parameterCodes
     * @return
     * @throws SystemException
     */
    public static Map<String,String> findAirportParametersByCode(
    		String companyCode, String airportCode,
    		Collection<String> parameterCodes) throws SystemException {
    	Map<String, String> map = null;
    	try {
			map = new SharedAreaProxy().findAirportParametersByCode(companyCode, airportCode, parameterCodes);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage(),e);
		}
    	return map;
    }

    /**
     * This method automatically commits the database before retrieving records
     * from it...
     *
     * @param stockFilterVO
     * @return
     * @throws SystemException
     */
    public static StockVO findStockForStockHolder(StockFilterVO stockFilterVO) throws SystemException{
    	try {
			return constructDAO().findStockForStockHolder(stockFilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode(),persistenceException);
		}
    }
    // added by Sinoob ends

	//Added for Auto processing
    /**
	 * @return Returns the autoprocessQuantity.
	 */
    @Column(name="ATOPRCQTY")
    @Audit(name="autoprocessQuantity")
	public int getAutoprocessQuantity() {
		return autoprocessQuantity;
	}

	/**
	 * @param autoprocessQuantity The autoprocessQuantity to set.
	 */
	public void setAutoprocessQuantity(int autoprocessQuantity) {
		this.autoprocessQuantity = autoprocessQuantity;
	}


	/**
	 * Checks whether the range which includes the document number
	 * is existing for the stock.
	 * The document number passed is of long type, which is converted by
	 * the findLong method
	 *
	 * @param airlineId
	 * @param documentNumber
	 * @return
	 * @throws SystemException
	 */
	public boolean checkDocumentExists(int airlineId, long documentNumber)
		throws SystemException{
		log.log(Log.FINER,"ENTRY");
		if(this.getRanges() != null){
			for(Range range : this.getRanges()){
				if(range.getAsciiStartRange() <= documentNumber &&
						range.getAsciiEndRange() >= documentNumber){
					log.log(Log.FINER, "RETURN", "true");
					return true;
				}
			}
		}
		log.log(Log.FINER, "RETURN", "false");
		return false;
	}

	/**
	 * @param stockAllocationVO
	 * @throws SystemException
	 */
	/** This method is to return a document to the stock which had been cancelled earlier*/
	public void returnDocumentToStock(StockAllocationVO stockAllocationVO)
	throws SystemException{
		log.entering("Stock","-->returnDocumentToStock<--");
		Collection<RangeVO> rangeVOs = stockAllocationVO.getRanges();
		RangeVO rangeVO = new RangeVO();
		for(RangeVO rangeVo : rangeVOs){
			rangeVO = rangeVo;
			log.log(Log.FINE, "rangeVO is -->", rangeVO);
			break;
		}
		//returnDocument(companyCode,stockHolderCode,documentType,airlineId,rangeVO);
		returnDocumentToStk(stockAllocationVO,rangeVO);
	log.exiting("Stock","-->returnDocumentToStock<--");
	}
	
	/**
	 * @author A-1885
	 * @param stockAllocationVo
	 * @throws SystemException
	 */
	private void returnDocumentToStk(StockAllocationVO stockAllocationVo
			,RangeVO rangeVo)
	throws SystemException{
		log.log(Log.FINE, "---Returndocumenttostk rangeVO is -->", rangeVo);
		Collection<RangeVO> rangesPersist = null;
		Collection<RangeVO> rangesToDelete = null;
		RangeVO rngVo = null;
		boolean isDuplicateFound = false;
		Collection<RangeVO> mergeRanges = null;
		if(rangeVo!=null){
			if(this.getRanges()!=null){
				/*This is done to ensure that AWB is alraedy there in stkrng table. 
				The situation comes when a reserved AWB is cancelled before the execution of stk_deplete job*/  
				for(Range rng : this.getRanges()){
					if(rng.getAsciiStartRange()<=Range.findLong(rangeVo.getStartRange()) &&
					   rng.getAsciiEndRange()>=Range.findLong(rangeVo.getEndRange())){
						log.log(Log.FINE,"---> AWB is not deleted from stock  <---");
						isDuplicateFound = true;
						break;
					}
				}
			}
			if(!isDuplicateFound){
				if(rangesPersist == null){
					rangesPersist = new ArrayList<RangeVO>();
				}
				rangesPersist.add(rangeVo);
			}
		}
		
		if(rangesPersist!=null){
			mergeRanges = mergeRanges(rangesPersist,"N");
			rangesToDelete = Range.findRangesforMerge(rangesPersist);
		
			log.log(Log.FINE, "--MergedRanges-->>>", mergeRanges);
			log.log(Log.FINE, "--Ranges to delete-->>>", rangesToDelete);
			if(mergeRanges!=null && rangesToDelete!=null && mergeRanges.size()>0 && rangesToDelete.size()>0 ){
	    		for(RangeVO rangeVO :rangesToDelete){
	    			Range range = Range.find(rangeVO);
	    			range.remove();
	    		}
	    	}
			if(mergeRanges!=null){
				/*if(this.getRanges()!=null){
					for(Range rang : this.getRanges()){
						log.log(Log.FINE,"++++++-RngDelete-->>>"+rang.getStartRange());
						rang.remove();
					}
				}*/
				populateRanges(mergeRanges,false);
				this.setPhysicalAvailableStock(this.getPhysicalAvailableStock()+1);
			}
		}
}
	
	             
	/**
	 * @author A-3155	
	 * @param materDocumentNumber
	 * @return              
	 * @throws SystemException
	 */
	 private  boolean checkAWBExistsInOperation(DocumentFilterVO documentFilterVO,long masterDocumentNumber, String documentType, String documentSubType) 
			 throws SystemException{
		 long airlineId=0;       
	 	 log.log(Log.FINE,"Entering checkAWBExistsInOperation"); 
		 	String companyCode=documentFilterVO.getCompanyCode();
	    	int ownerID=documentFilterVO.getAirlineIdentifier();
    	 StringBuilder result=new StringBuilder();
		  SharedAirlineProxy sharedAirlineProxy = new SharedAirlineProxy();
	    	AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
	    	try{
	    		airlineValidationVO = sharedAirlineProxy.findAirline(companyCode,
	    				ownerID);
	    	}catch(ProxyException proxyException){
	    		for(ErrorVO errorVO : proxyException.getErrors()){
	    			throw new SystemException(errorVO.getErrorCode());
	    		}
	    	}
	    	int checkDigit = airlineValidationVO.getAwbCheckDigit();
	    	if(checkDigit!=0){
	    		airlineId=masterDocumentNumber%checkDigit;
	    	}
	    	result=result.append(masterDocumentNumber).append(airlineId);
	       	String resultRng = result.toString();
	    	log.log(Log.FINE, "--Result Obtained--", resultRng);
			int length = resultRng.length();
	    	int indexLength = 8-length;
	    	log.log(Log.FINE, "---->length sub---->", indexLength);
			StringBuilder resultBuilder = new StringBuilder();
	    	for(int index = 0;index<indexLength;index++){
	    		resultBuilder.append("0");  
	    	}                                                                      
	    	resultBuilder.append(resultRng);
	    	boolean isStockUtilized = false ; 
	    	boolean isExistAWBReuseRestriction = false ; 
	    	
	    	    isStockUtilized = constructDAO().checkAWBExistsInOperation(companyCode,resultBuilder.toString(),ownerID,documentType,documentSubType); 
	    
    		if(!isStockUtilized){ 
    			//Changed by A-6858 for ICRD-234889: Check for awb re-use

    			isExistAWBReuseRestriction =checkForAWBReuseRestriction(documentFilterVO, Long.parseLong(resultBuilder.toString()));
    			if(!isStockUtilized && !isExistAWBReuseRestriction){
	    		try{ 
	    			log.log(Log.FINE, "---->StockRangeUtilisationLog masterDocumentNumber---->", masterDocumentNumber);	
	    		new StockRangeUtilisationLog(companyCode, 
	    				ownerID,String.valueOf(masterDocumentNumber),documentType,documentSubType); 
	    		} 
	
	    		catch(SystemException se){ 
	    		isStockUtilized = true; 
	    		}  
    			}

    		}   
    		if(isExistAWBReuseRestriction){
    			isStockUtilized = true;
    		}
    		 return isStockUtilized; 
	 }   
	 public String findAutoPopulateSubtype(DocumentFilterVO documentFilterVO)throws SystemException{
		 log.log(Log.FINE,"Entering findAutoPopulateSubtype in Entity"); 
		 String subtype=null;
		 try {
			subtype = constructDAO().findAutoPopulateSubtype(documentFilterVO);
		} catch (PersistenceException e) {
			log.log(Log.SEVERE,"exception in findAutoPopulateSubtype in dao");	
		}
		 return subtype;
	 }
	 /**
		 * @author A-7534
	     * This method is used to delete the ranges for HQ
	     * @param rangeVO
	     * @throws SystemException
	     */
	 public  void deleteStock(Collection<RangeVO> rangeVOs)
	 throws SystemException{
		 log.log(Log.FINE,"Inside deleteHQStock of stock----->");
	    Range range = null;
	    RangeVO availablerange=new RangeVO();
	    String compcode=null;
	        String stockholdercode=null;
	        String doctype=null;
	        String docsubtype=null;
	        boolean manual = false;
	        int  airlineid=0;
	    	Collection<RangeVO> splitRange=new ArrayList<RangeVO>();
	    	Collection<RangeVO> originalrange=new ArrayList<RangeVO>();
	    	Collection<RangeVO> newrange=new ArrayList<RangeVO>();
	    	for(RangeVO actualrange:rangeVOs){
	    		manual=false;
	    		originalrange.clear();
	    		newrange.clear();
	    		range = Range.find(actualrange);
	    		compcode= range.getRangePK().getCompanyCode();
	    		stockholdercode=range.getRangePK().getStockHolderCode();
	    		doctype=range.getRangePK().getDocumentType();
	    		docsubtype=range.getRangePK().getDocumentSubType();
	    		airlineid=range.getRangePK().getAirlineIdentifier();
	    		if("Y".equalsIgnoreCase(range.getIsManual())){
	    			manual=true;
	    		}
	    		if((!actualrange.getStartRange().equals(range.getStartRange())) ||
	    				(!actualrange.getEndRange().equals(range.getEndRange()))){
	    			availablerange.setStartRange(range.getStartRange());
	    			availablerange.setEndRange(range.getEndRange());
	    			//availablerange.setStockAcceptanceDate((range.getStockAcceptanceDate().);
	    			originalrange.add(availablerange);
	    			newrange.add(actualrange);
	    		}
	    		range.remove();
	    	splitRange = splitRanges(originalrange,newrange);
	    	log.log(Log.FINE,"ranges to be added----->"+splitRange);
	    	for(RangeVO rangeToAdd : splitRange){
	    		long asciistart=Range.findLong(rangeToAdd.getStartRange());
	        	long asciiend =Range.findLong(rangeToAdd.getEndRange());
	        	long docnum=Range.difference(rangeToAdd.getStartRange(),rangeToAdd.getEndRange());
	        	rangeToAdd.setAsciiStartRange(asciistart);
	        	rangeToAdd.setAsciiEndRange(asciiend);
	        	rangeToAdd.setNumberOfDocuments(docnum);
	        	rangeToAdd.setManual(manual);
	    			new Range(compcode,stockholdercode,doctype,
	    					docsubtype,airlineid,rangeToAdd);
				}
			}
	 } 
	 /**
	  * 
	  * 	Method		:	Stock.checkForAWBReuseRestriction
	  *	Added by 	:	A-6858 on 13-Mar-2018
	  * 	Used for 	:
	  *	Parameters	:	@param companyCode
	  *	Parameters	:	@param masterDocumentNumber
	  *	Parameters	:	@param ownerID
	  *	Parameters	:	@param awbDestination
	  *	Parameters	:	@return
	  *	Parameters	:	@throws SystemException 
	  *	Return type	: 	boolean
	  */
/*	 private boolean checkForAWBReuseRestriction(String companyCode, long 
			 masterDocumentNumber, int ownerID,String awbDestination) throws SystemException{
		 boolean isRestrictionExists = false; 
		 CountryVO countryVO = new CountryVO();
		 int reusableYears = 0;
		 try {
				countryVO = new SharedAreaProxy().findAWBReuseRestrictionDetails(companyCode,awbDestination);
			}catch (ProxyException e) {
				log.log(Log.FINE, "ProxyException");
			} catch (SystemException e) {
				log.log(Log.FINE, "SystemException");
			}
		 if(countryVO != null){
			 reusableYears = countryVO.getAwbRestrictionPeriod();
		  }
		 if(reusableYears > 0){
			 ShipmentForBookingVO shipmentForBookingVO = null;
			 try{
				 shipmentForBookingVO =  constructDAO().findAWBUsageDetails(countryVO.getCountryCode(), companyCode, masterDocumentNumber, ownerID);
				}catch (PersistenceException e) {
					log.log(Log.SEVERE,"exception in findAutoPopulateSubtype in dao");	
				}
			 if(shipmentForBookingVO != null && (P.equals(shipmentForBookingVO.getShipmentStatus()) || 
						C.equals(shipmentForBookingVO.getShipmentStatus())))
				{
				LocalDate lastUpdatedTime = shipmentForBookingVO.getLastUpdateTime();
				long miliSecondForlastUpdatedTime = lastUpdatedTime.getTimeInMillis();
				Calendar current = Calendar.getInstance();
				long miliSecondForcurrentDate = current.getTimeInMillis();
				long diffInMilis= miliSecondForcurrentDate-miliSecondForlastUpdatedTime;
				long diffInDays = diffInMilis / (24 * 60 * 60 * 1000);
				if(diffInDays < reusableYears*365){
					isRestrictionExists = true;
				}
			}
		 }
		 return isRestrictionExists;
	 }   */   
	 
		public static String saveStockDetails(StockAllocationVO stockAllocationVO) throws SystemException {
		    try {
				return constructDAO().saveStockDetails(stockAllocationVO);
		    } catch (PersistenceException persistenceException) {
		    	throw new SystemException(persistenceException.getErrorCode());
			}		
		}
		
		public static boolean isStockDetailsExists(DocumentFilterVO documentFilterVO) throws SystemException {
		    try {
				return constructDAO().isStockDetailsExists(documentFilterVO);
		    } catch (PersistenceException persistenceException) {
		    	throw new SystemException(persistenceException.getErrorCode());
			}		
		}
		/**
		 * 
		 * @param blacklistStockVO
		 * @return
		 * @throws SystemException
		 */
		 public static Collection<StockVO> findBlacklistRangesForBlackList(BlacklistStockVO
				 blacklistStockVO)
		 throws SystemException{
			 return constructDAO().findBlacklistRangesForBlackList(blacklistStockVO.getCompanyCode(),
					 blacklistStockVO.getAirlineIdentifier(),
					 blacklistStockVO.getDocumentType(),blacklistStockVO.
					 getDocumentSubType(), blacklistStockVO.getRangeFrom(),
					 blacklistStockVO.getRangeTo());
	 }   
	 /**
	  * 
	  * 	Method		:	Stock.checkForAWBReuseRestriction
	  *	Added by 	:	A-6858 on 13-Mar-2018
	  * 	Used for 	:
	  *	Parameters	:	@param companyCode
	  *	Parameters	:	@param masterDocumentNumber
	  *	Parameters	:	@param ownerID
	  *	Parameters	:	@param awbDestination
	  *	Parameters	:	@return
	  *	Parameters	:	@throws SystemException 
	  *	Return type	: 	boolean
	  */
	 public boolean checkForAWBReuseRestriction(DocumentFilterVO documentFilterVO, long 
			 masterDocumentNumber) throws SystemException{
			String companyCode=documentFilterVO.getCompanyCode();
	    	int ownerID=documentFilterVO.getAirlineIdentifier();
		 boolean isRestrictionExists = false; 
		 if((documentFilterVO.getAwbDestination()!= null && documentFilterVO.getAwbDestination().length()>0)
			 || (documentFilterVO.getAwbOrigin()!=null && documentFilterVO.getAwbOrigin().length()>0)) {
		 Collection<CountryValidationVO> countryValidationVOs=null;
		 String originDestinationIndicator="";
		 int reusableYears = 0;
		 Collection<String> parameterCodes = new HashSet<>();
			parameterCodes.add("admin.defaults.cutoverparameters");
			parameterCodes.add(ENHANCESTOCKCONTROL);
			Map<String, String> systemParameters = null;
			try {
				systemParameters = Proxy.getInstance().get(SharedDefaultsProxy.class).findSystemParameterByCodes(parameterCodes);
			} catch (ProxyException e) {
				log.log(Log.SEVERE, "Exception Caught" + e.getMessage());
			} catch (SystemException e) {
				log.log(Log.SEVERE, "Exception Caught" + e.getMessage());
			}
			if(systemParameters!=null && !systemParameters.isEmpty() && systemParameters.get(ENHANCESTOCKCONTROL) != null && "Y".equals(systemParameters.get(ENHANCESTOCKCONTROL))){
				isRestrictionExists = checkForAWBReuseRestrictionEnhanced(documentFilterVO,masterDocumentNumber);
			}else{
			if(systemParameters != null && systemParameters.size() > 0 && systemParameters.get(CUTOVERPARAMETERS) != null
				&& systemParameters.get("admin.defaults.cutoverparameters").contains("AWBRUSRSTSPC")
					&& masterDocumentNumber >0){
				try {
					List<String> params=null;
					params = new OperationsShipmentProxy().findAWBReuseRestrictionDetailsSpecific(companyCode,
							ownerID, String.valueOf(masterDocumentNumber));
					ShipmentForBookingVO  shipmentForBooking=new ShipmentForBookingVO();
					if(params!=null && params.size()>0){
						String sDate1 = params.get(0);
						Date date1 = new Date();
						try {
							date1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").parse(sDate1);
							sDate1 = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss").format(date1);
						} catch (ParseException e1) {
							log.log(Log.SEVERE, "Exception caught"+e1.getMessage());
						}
						LocalDate date = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
						date.setDateAndTime(sDate1);
						shipmentForBooking.setLastUpdateTime(date);
						LocalDate lastUpdatedTime = shipmentForBooking.getLastUpdateTime();
						long miliSecondForlastUpdatedTime = lastUpdatedTime.getTimeInMillis();
						Calendar current = Calendar.getInstance();
						reusableYears=Integer.parseInt(params.get(1));
						long miliSecondForcurrentDate = current.getTimeInMillis();
						long diffInMilis= miliSecondForcurrentDate-miliSecondForlastUpdatedTime;
						long diffInDays = diffInMilis / (24 * 60 * 60 * 1000);
						if(diffInDays < reusableYears*365){
							isRestrictionExists = true;
						}
					}
				} catch (SystemException | ProxyException e) {
					log.log(Log.SEVERE, "Exception caught"+e.getMessage());
				}
			}else{
					}
		 try {
			 countryValidationVOs = new SharedAreaProxy().findAWBReuseRestrictionDetails(companyCode,documentFilterVO.getAwbDestination(),documentFilterVO.getAwbOrigin());
			}catch (ProxyException e) {
				log.log(Log.FINE, "ProxyException");
			} catch (SystemException e) {
				log.log(Log.FINE, "SystemException");
			}
		 if(countryValidationVOs!=null && countryValidationVOs.size()>0){
			for(CountryValidationVO countryValidationVO:countryValidationVOs){
				if(countryValidationVO != null ){
					reusableYears = countryValidationVO.getNoOfRestrictionYear();
		  }
		 if(reusableYears > 0){
			 ShipmentForBookingVO shipmentForBookingVO = null;
			 try{
						 originDestinationIndicator = getOriginDestinationIndicator(documentFilterVO,originDestinationIndicator, countryValidationVO);
						 shipmentForBookingVO =  constructDAO().findAWBUsageDetails(countryValidationVO.getCountryCode(), companyCode, masterDocumentNumber, ownerID, originDestinationIndicator);
				}catch (PersistenceException e) {
					log.log(Log.SEVERE,"exception in findAutoPopulateSubtype in dao");	
				}
			 if(shipmentForBookingVO != null && (P.equals(shipmentForBookingVO.getShipmentStatus()) || 
						C.equals(shipmentForBookingVO.getShipmentStatus())))
				{
				LocalDate lastUpdatedTime = shipmentForBookingVO.getLastUpdateTime();
				long miliSecondForlastUpdatedTime = lastUpdatedTime.getTimeInMillis();
				Calendar current = Calendar.getInstance();
				long miliSecondForcurrentDate = current.getTimeInMillis();
				long diffInMilis= miliSecondForcurrentDate-miliSecondForlastUpdatedTime;
				long diffInDays = diffInMilis / (24 * 60 * 60 * 1000);
				if(diffInDays < reusableYears*365){
					isRestrictionExists = true;
							break;
						}
					}
					}
				}
			}
			}
		 }
		 return isRestrictionExists;
	 }

	public String getOriginDestinationIndicator(DocumentFilterVO documentFilterVO, String originDestinationIndicator,
			CountryValidationVO countryValidationVO) {
		if(documentFilterVO.getAwbOrigin().equals(countryValidationVO.getAirportCode())){
				originDestinationIndicator="O";
			}else if(documentFilterVO.getAwbDestination().equals(countryValidationVO.getAirportCode())){
				originDestinationIndicator="D";
			}else{
				//nothing to do
			}
		return originDestinationIndicator;
	}      
	 
	 private boolean checkForAWBReuseRestrictionEnhanced(DocumentFilterVO documentFilterVO, long 
			 masterDocumentNumber) throws SystemException{
		 boolean isRestrictionExists = false;
		 Collection<CountryValidationVO> countryValidationVOs=null;
		 int reusableYears = 0;
		 documentFilterVO.setDocumentNumber(String.valueOf(masterDocumentNumber));
		 try {
				countryValidationVOs =Proxy.getInstance().get(SharedAreaProxy.class).findAWBReuseRestrictionDetailsEnhanced(documentFilterVO);
			} catch (ProxyException e) {
				log.log(Log.FINE, e);
			}
			if(countryValidationVOs!=null && !countryValidationVOs.isEmpty()){
				for(CountryValidationVO countryValidationVO:countryValidationVOs){
				if(countryValidationVO != null ){
				reusableYears = countryValidationVO.getNoOfRestrictionYear();			
				if(reusableYears > 0){
					//find the capture date from stock reuse history table for the country code			
					StockReuseHistoryVO stockReuseHistoryVO=Stock.constructDAO().findAWBReuseHistoryDetails(documentFilterVO, countryValidationVO.getCountryCode());
					if(stockReuseHistoryVO!=null && stockReuseHistoryVO.getCaptureDate()!=null){
						LocalDate captureDate = stockReuseHistoryVO.getCaptureDate();
						long miliSecondForCaptureDate = captureDate.getTimeInMillis();
						Calendar current = Calendar.getInstance();
						long miliSecondForcurrentDate = current.getTimeInMillis();
						long diffInMilis= miliSecondForcurrentDate-miliSecondForCaptureDate;
						long diffInDays = diffInMilis / (24 * 60 * 60 * 1000);
						if(diffInDays < reusableYears*365){
							isRestrictionExists = true;
									break;
						}
							
					}
				}
				}				
				}
				}
		 return isRestrictionExists;
	 }
}             
