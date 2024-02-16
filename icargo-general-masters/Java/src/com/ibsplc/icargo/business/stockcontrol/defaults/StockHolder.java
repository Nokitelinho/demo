/*
 * StockHolder.java Created on Jul 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
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

import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.InformAgentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.MonitorStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAuditVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDepleteFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDetailsFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDetailsVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderDetailsVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderLovFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderLovVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderStockDetailsVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeHistoryVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestApproveVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsPersistenceConstants;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.xibase.server.framework.audit.util.AuditUtils;
import com.ibsplc.xibase.server.framework.audit.vo.AuditAction;
import com.ibsplc.xibase.server.framework.audit.vo.AuditFieldVO;
import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.util.error.ErrorUtils;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Represents a stockholder that can hold the stock for different
 * document types. Every stockholder belongs to a specific type
 *
 * @author A-1358
 *
 */


@Table(name="STKHLDMST")
@Entity
public class StockHolder {

	private static final String DOCSUBTYP = "S";
	private static final String DOCTYP = "AWB";

	private static final String HQ = "HQ";

	 private  Log log = LogFactory.getLogger("STOCKHOLDER");

    private StockHolderPK stockHolderPk;

    /**
     * Denotes the stockholder type. Possible values are H-headqarters,
     * R-region, S-station,A-agent
     */
    private String stockHolderType;

    /**
     * Full qualified name of the stock holder.
     */
    private String stockHolderName;

    /**
     * Description of the stock holder
     */
    private String description;

    /**
     * Users holding this privilege would be able to perform actions over
     * the stock held by this stock holder
     */
    private String controlPrivilege;


    private String contactDetails;

    /**
     * Collection of stocks held by the user. Each stock represents the
     * ranges
     * held by the user against a particyular document type
     * Set<Stock>
     */
    private Set<Stock> stock;

    /**
     * For optimistic locking
     */
    private String lastUpdateUser;

    /**
     * For optimistic locking
     */
    private Calendar lastUpdateTime;

    /**
     * @return Returns the stockHolderPk.
     */
    @EmbeddedId
    @AttributeOverrides({
 		@AttributeOverride(name="companyCode",
 				column=@Column(name="CMPCOD")),
 		@AttributeOverride(name="stockHolderCode",
 				column=@Column(name="STKHLDCOD"))}
    )
    public StockHolderPK getStockHolderPk() {
        return stockHolderPk;
    }
    /**
     * @param stockHolderPk The stockHolderPk to set.
     */
    public void setStockHolderPk(StockHolderPK stockHolderPk) {
        this.stockHolderPk = stockHolderPk;
    }

    /**
     * @return Returns the controlPrivilege.
     */

    @Column(name="CTLPVG")
    @Audit(name="controlPrivilege")
    public String getControlPrivilege() {
        return controlPrivilege;
    }
    /**
     * @param controlPrivilege The controlPrivilege to set.
     */
    public void setControlPrivilege(String controlPrivilege) {
        this.controlPrivilege = controlPrivilege;
    }

    /**
     * @return Returns the description.
     */

    @Column(name="STKHLDDES")
    public String getDescription() {
        return description;
    }
    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return Returns the stockHolderName.
     */

    @Column(name="STKHLDNAM")
    @Audit(name="stockHolderName")
    public String getStockHolderName() {
        return stockHolderName;
    }
    /**
     * @param stockHolderName The stockHolderName to set.
     */
    public void setStockHolderName(String stockHolderName) {
        this.stockHolderName = stockHolderName;
    }

    /**
     * @return Returns the stockHolderType.
     */
    @Audit(name = "StockHolerType")
    @Column(name="STKHLDTYP")
    public String getStockHolderType() {
        return stockHolderType;
    }
    /**
     * @param stockHolderType The stockHolderType to set.
     */
    public void setStockHolderType(String stockHolderType) {
        this.stockHolderType = stockHolderType;
    }
    /**
     * @author a-1885
     * @return contactDetails
     */
    @Column(name = "CNTADR")
    public String getContactDetails() {
		return contactDetails;
	}
    /**
     * @author a-1885
     * @param contactDetails
     */
	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}

    /**
     * @return Returns the stock.
     */

	@OneToMany
	@JoinColumns({
		@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD",
				insertable=false, updatable=false),
		@JoinColumn(name = "STKHLDCOD", referencedColumnName = "STKHLDCOD",
				insertable=false, updatable=false)}
	)
    public Set<Stock> getStock() {
        return stock;
    }
    /**
     * @param  stock The stock to set.
     */
    public void setStock(Set<Stock> stock) {
        this.stock = stock;
    }

    /**
     * @return Returns the lastUpdateTime.
     */
    @Version
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
     * Default Constructor
     */
    public StockHolder(){
        super();
    }

    /**
     * This constructor is used to create a new Stock Holder
     * @param stockHolderVO
     * @throws SystemException
     *
     */
    public StockHolder(StockHolderVO stockHolderVO) throws SystemException{
    	populatePk(stockHolderVO);
        populateAttributes(stockHolderVO);
        try{
        	PersistenceController.getEntityManager().persist(this);
        }catch(CreateException createException){
        	throw new SystemException(createException.getErrorCode());
        }
        populateChildren(stockHolderVO);
    }

    /**
     * Populates StockHolderPk
     * @param stockHolderVO
     * @return
     */
    private void populatePk(StockHolderVO stockHolderVO) {
    	StockHolderPK stockHolderPK=new StockHolderPK();
    	stockHolderPK.setCompanyCode(stockHolderVO.getCompanyCode());
    	stockHolderPK.setStockHolderCode(stockHolderVO.getStockHolderCode().
    	trim().toUpperCase());
    	this.stockHolderPk=stockHolderPK;
    }

    /**
     * Populates the attributes in stockHolder
     * @param stockHolderVO
     * @return
     */
    private void populateAttributes(StockHolderVO stockHolderVO)
    	throws SystemException{
    	LogonAttributes logon = ContextUtils.getSecurityContext().getLogonAttributesVO();
    	this.setControlPrivilege(stockHolderVO.getControlPrivilege());
    	if(stockHolderVO.getStockHolderName() != null){
    	this.setStockHolderName(stockHolderVO.getStockHolderName().toUpperCase());
    	}
    	this.setDescription(stockHolderVO.getDescription());
    	this.setStockHolderType(stockHolderVO.getStockHolderType());
    	this.setContactDetails(stockHolderVO.getStockHolderContactDetails());
    	this.setLastUpdateTime(stockHolderVO.getLastUpdateTime());
    	if(stockHolderVO.getLastUpdateUser() != null){
    		this.setLastUpdateUser(stockHolderVO.getLastUpdateUser());
    	}else{
    		this.setLastUpdateUser(logon.getUserId());
    	}
    }

    /**
     * Populate the child entity Stock
     * @param stockHolderVO
     * @return
     * @throws SystemException
     */
    private void populateChildren(StockHolderVO stockHolderVO)
    throws SystemException{
    	Collection<StockVO> stockCol=stockHolderVO.getStock();
    	for(StockVO stockVo : stockCol){
    		StockAuditVO stockAuditVo = new StockAuditVO
        	(StockAuditVO.AUDIT_PRODUCTNAME,StockAuditVO.AUDIT_MODULENAME,
        			StockAuditVO.AUDIT_ENTITY);
    		Stock stockCreated=new Stock(stockHolderVO.getCompanyCode(),
    				stockHolderVO.getStockHolderCode(),stockVo);
    		stockAuditVo = (StockAuditVO)AuditUtils
    		.populateAuditDetails(stockAuditVo,stockCreated,true);
    		auditStockDetails(stockCreated,stockAuditVo,AuditVO.CREATE_ACTION);
    		if(stock!=null){
    			this.stock.add(stockCreated);
    		}
    	}

    }


    /**
     * Finder method for Stock Holder entity
     *
     * @param companyCode
     * @param stockHolderCode
     * @return
     * @throws SystemException
     * @throws InvalidStockHolderException
     */
    public static StockHolder find(String companyCode, String stockHolderCode)
    throws SystemException ,InvalidStockHolderException {
    	StockHolder stockHolder=null;
		StockHolderPK stockHolderPK = new StockHolderPK();
		stockHolderPK.setCompanyCode(companyCode);
		stockHolderPK.setStockHolderCode(stockHolderCode);
		try{
		EntityManager entityManager = PersistenceController.getEntityManager();

			stockHolder=entityManager.find(StockHolder.class,stockHolderPK);
		}
		catch (FinderException finderException) {
			throw new InvalidStockHolderException();
		}
		return stockHolder;
      }

    /**
     * Updates the stock holder entity based on stockholderVO
     * @param stockHolderVO
     * @throws SystemException
     * @throws StockControlDefaultsBusinessException
     * @throws RangeExistsException
     * @throws StockNotFoundException
     * @return
     */
    public void update(StockHolderVO stockHolderVO) throws SystemException,
    	RangeExistsException,StockNotFoundException{
    	this.setStockHolderName(stockHolderVO.getStockHolderName());
    	this.setControlPrivilege(stockHolderVO.getControlPrivilege());
    	this.setDescription(stockHolderVO.getDescription());
    	this.setStockHolderType(stockHolderVO.getStockHolderType());
    	this.setContactDetails(stockHolderVO.getStockHolderContactDetails());
    	log
				.log(Log.FINE, "*****LastUpdateTime----->", this.getLastUpdateTime());
		if(stockHolderVO.getStock()!=null){
    		try{
    		updateChildren(stockHolderVO);
    		}catch(CreateException createException){
    			throw new SystemException(createException.getErrorCode());
    		}
    		catch(RemoveException removeException){
    			throw new SystemException(removeException.getErrorCode());
    		}
    	}
    	this.setLastUpdateTime(stockHolderVO.getLastUpdateTime());
    	//To be reviewed: catch Create exception, FinderException and
    	//Removeexception and throw
        //StockControlDefaultsBusinessException
    }

    /**
     * This method checks whether the given set of stock holders are valid
     * @param companyCode
     * @param stockHolderCodes
     * @throws SystemException
     * @throws StockControlDefaultsBusinessException
     * @throws InvalidStockHolderException
     */
    public static void validateStockHolders(String companyCode,
        Collection<String> stockHolderCodes)
    	throws SystemException, StockControlDefaultsBusinessException,
    	InvalidStockHolderException{
    	Collection<String> validStockHolders = null;
    	Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
    	try {
			validStockHolders=constructDAO().findStockHoldersForValidation
			(companyCode,stockHolderCodes);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
		int counter = 0;
		StringBuilder invalidStockholder = new StringBuilder();
		boolean hasinvalidStockHolders=false;
		if(stockHolderCodes.size()!=validStockHolders.size()){
		for(String input:stockHolderCodes){
			if(!(validStockHolders.contains(input))){
				hasinvalidStockHolders = true;
					if(counter==0){
					invalidStockholder.append(input);
					}else{
						invalidStockholder.append(",").append(input);
					}
					counter=1;
				}
			}
		}
		if(hasinvalidStockHolders){
			Object[] errorObj=new Object[1];
    		errorObj[0]=invalidStockholder.toString();
    		ErrorVO errorVo=new ErrorVO(InvalidStockHolderException.
    				INVALID_STOCKHOLDER,errorObj);
    		errors.add(errorVo);
			InvalidStockHolderException invalidStockHolderException=new
			InvalidStockHolderException();
			invalidStockHolderException.addErrors(errors);
			throw  invalidStockHolderException;
		}

	}

    /**
     * Deletes all the stock that falls under the stock holder. If ranges
     * exist against a particular stock then throw Exception
     * @throws SystemException
     * @throws RemoveException
     * @throws StockControlDefaultsBusinessException
     */
    public void deleteAllStock()
    	throws SystemException,RemoveException,
    	StockControlDefaultsBusinessException {
    }

    /**
     * Remove method for stock holder entity
     * @throws SystemException
     * @throws RemoveException
     * @throws RangeExistsException
     */
    public void remove() throws SystemException,RangeExistsException  {
    	//Collection<AuditFieldVO> auditFields;
    	Collection<ErrorVO> errors=checkForStockRange();
    	if(errors.size()<=0){
    		try{
          		EntityManager entityManager = PersistenceController.
          		getEntityManager();
    	   		for(Stock stockDelete:this.getStock()){
    	   			stockDelete.remove();
    	   			StockAuditVO auditvo = new StockAuditVO
    	   			(StockAuditVO.AUDIT_PRODUCTNAME,
    	   					StockAuditVO.AUDIT_MODULENAME,
    	   					StockAuditVO.AUDIT_ENTITY);
    	   			auditvo =(StockAuditVO)
    	   			AuditUtils.populateAuditDetails(auditvo,stockDelete,false);
    	   			auditStockDetails(stockDelete,auditvo,AuditVO.DELETE_ACTION);
    	   		}

          		entityManager.remove(this);
    	   	}catch( RemoveException  removeException){
    			throw new SystemException(removeException.getErrorCode() );
    		}
    	}
    	else{
    		log.log(Log.FINE,"----------->RangeExists-------------->");
    		RangeExistsException rangeExistsException=new
    		RangeExistsException();
    		rangeExistsException.addErrors(errors);
    		throw rangeExistsException;
    	}

    }

    /**
     * This method issues a DAO query to obtain the details of a particular
     * stockholder
     * @param companyCode
     * @param stockHolderCode
     * @return StockHolderVO
     * @throws SystemException
     */
    public static StockHolderVO findStockHolderDetails(String companyCode,
        String stockHolderCode) throws SystemException {
    	try {
			return constructDAO().findStockHolderDetails(companyCode,
					stockHolderCode);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}

    }

    /**
     * This method finds details for stock holders which meet the filter
     * criteria and all stock holders who fall under them.
     * @param filterVO
     * @return Collection<StockHolderDetailsVO>
     * @throws SystemException
     */
    public static Page<StockHolderDetailsVO> findStockHolders
    (StockHolderFilterVO filterVO)throws SystemException {
    	try {
    		return constructDAO().findStockHolders(filterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
    }

    /**
     * This method is used to fetch the
     * stockholder types sorted based on prioity
     * @param companyCode
     * @return Collection<StockHolderPriorityVO>
     * @throws SystemException
     */
    public static Collection<StockHolderPriorityVO> findStockHolderTypes
    (String companyCode)throws SystemException{
    	try {
			return constructDAO().findStockHolderTypes(companyCode);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
     }
    /**
     * Returns the Stock business Object corresponding to the stockVO passed
     * @param stockVO
     * @return Stock
     */
/*    private Stock retrieveStockFromCollection(StockVO stockVO) {
        return null;
    }
*/
    /**
     * This method is invoked to obtain the stock holder lov
     * @param filterVO
     * @param displayPage
     * @return Page<StockHolderLovVO>
     * @throws SystemException
     */
    public static Page<StockHolderLovVO> findStockHolderLov
    (StockHolderLovFilterVO filterVO,int displayPage)throws
    SystemException {
    	try {
			return constructDAO().findStockHolderLov(filterVO,displayPage);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
    }
   /**
    * @author A-1883
    * @param stockRequestVO
    * @throws SystemException
    * @throws InvalidStockHolderException
    */
    public static void validateStockHolderTypeCode(StockRequestVO
    		stockRequestVO)throws SystemException ,
    		InvalidStockHolderException{
    	try{
    	Collection<String> stockHolderNameIn = constructDAO().
    	validateStockHolderTypeCode(stockRequestVO);
    		if(stockHolderNameIn == null || stockHolderNameIn.size() == 0){
    			Object[] errorObj=new Object[1];
    			StringBuilder builder = new StringBuilder();
    			builder.append(stockRequestVO.getStockHolderCode()).
    			append(" for the specified type ");
    			errorObj[0]=builder.toString();
    			Collection<ErrorVO> errors= new ArrayList<ErrorVO>();
        		ErrorVO errorVo=new ErrorVO(InvalidStockHolderException.
        				INVALID_STOCKHOLDER,errorObj);
        		errors.add(errorVo);
    			InvalidStockHolderException invalidStockHolderException=
    				new InvalidStockHolderException();
    			invalidStockHolderException.addErrors(errors);
    			throw  invalidStockHolderException;
    		}
    	}
    	 catch (PersistenceException persistenceException) {
 			throw new SystemException(persistenceException.getErrorCode());
 		}
    }
    /**
     * This method is used to list ranges
     * @author A-1883
     * @param rangeFilterVO
     * @return Collection<RangeVO
     * @throws SystemException
     */
    public static Collection<RangeVO> findRanges (RangeFilterVO rangeFilterVO)
	throws SystemException{
    	try {
			return constructDAO().findRanges(rangeFilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
    }

    /**
     * This method will find the blacklisted stock matching
     * the filter criteria
     * @return Page<RangeVO>
     * @throws SystemException
     */
  /*  public static Page findBlacklistedStock(StockFilterVO stockFilterVO) throws
    	SystemException{
        return null;
    }*/

    /**
     * This method is used to monitor the stock of a stock holder
     * @param stockFilterVO
     * @return Collection<MonitorStockVO>
     * @throws SystemException
     */
  public Page<MonitorStockVO> monitorStock(StockFilterVO stockFilterVO)
    	throws SystemException{
	  	try {
			return constructDAO().monitorStock(stockFilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
    }


  /**
   * This method is used to monitor the stock of a stock holder
   * @param stockFilterVO
   * @return Collection<MonitorStockVO>
   * @throws SystemException
   */
  	public static MonitorStockVO findMonitoringStockHolderDetails(StockFilterVO stockFilterVO)
  	throws SystemException{

	  	try {
			return constructDAO().findMonitoringStockHolderDetails(stockFilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
  }
  /**
   * This method is used to find the stock holder codes for a
   * collection of stock control privileges
   * @param companyCode
   * @param privileges
   * @return Collection<String>
   * @throws SystemException
   */
 public static Collection<String> findStockHolderCodes(String companyCode,
		 Collection<String> privileges)throws SystemException {
	 try {
			return constructDAO().findStockHolderCodes(companyCode,privileges);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
 }
    /**
     * This method is used to fetch the range details of a stockholder
     * @param stockFilterVO
     * @return StockRangeVO
     * @throws SystemException
     * @throws StockNotFoundException
     */
   public static StockRangeVO viewRange(StockFilterVO stockFilterVO)
    	throws SystemException,StockNotFoundException{
	   try {
	    	 StockRangeVO stockRangeVO = new StockRangeVO();
	    	 stockRangeVO.setAvailableRanges(constructDAO().
	    	 			findRangesForViewRange(stockFilterVO));
	    	 //log.log(Log.FINE,"**********AvailableRanges"+stockRangeVO.
	    		//	 getAvailableRanges());
	    	 if(stockFilterVO.isViewRange()){
	    		 //if(!stockFilterVO.getStockHolderCode().equalsIgnoreCase(HQ)){
		    		 stockRangeVO.setAllocatedRanges(constructDAO().
		    				 findAllocatedRanges(stockFilterVO));
	    		 //}
	    	 }
	    	 //log.log(Log.FINE,"**********AllocatedRanges"+stockRangeVO.
	    		//	 getAllocatedRanges());
	    	 return stockRangeVO;
			} catch (PersistenceException persistenceException) {
				throw new SystemException(persistenceException.getErrorCode());
			}
    }
   /**
    * This method is used to allocate the ranges
    * @param stockAllocationVO
    * @throws SystemException
    * @throws StockNotFoundException
    * @throws BlacklistedRangeExistsException
    * @throws RangeExistsException
 * @throws StockControlDefaultsBusinessException 
    */
    public void allocate(StockAllocationVO stockAllocationVO,int stockIntroductionPeriod)
    throws SystemException,StockNotFoundException,
    BlacklistedRangeExistsException,RangeExistsException, StockControlDefaultsBusinessException{
    	 if ((stockAllocationVO.isNewStockFlag()) && (!stockAllocationVO.isBlacklist()) 
    			 && DOCTYP.equals(stockAllocationVO.getDocumentType()))
         {
            String mergeRanges = validateStockPeriod(stockAllocationVO,stockIntroductionPeriod);
            if(mergeRanges!=null){
            	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
            	ErrorVO errorVo = ErrorUtils.getError(
						StockControlDefaultsBusinessException
								.RANGES_WITHIN_STOCKINTRODUCTIONPERIOD,
								new Object[]{ mergeRanges,
								stockIntroductionPeriod});
				errors.add(errorVo);
				StockControlDefaultsBusinessException stockControlException = new StockControlDefaultsBusinessException();
				stockControlException.addErrors(errors);
				log.log(Log.SEVERE,"THROWING EXCEPTION");
				throw stockControlException;
            }
            
         }
    	LogonAttributes logon = ContextUtils.getSecurityContext().getLogonAttributesVO();
    	//StockAuditVO auditVO = new StockAuditVO(
		//		StockAuditVO.AUDIT_PRODUCTNAME,
		//		StockAuditVO.AUDIT_MODULENAME,
		//		StockAuditVO.AUDIT_ENTITY);
    	log.log(Log.FINE,"-----Inside allocate in Stockhoder----");
        StockVO stockVo = new StockVO();
        stockVo.setCompanyCode(stockAllocationVO.getCompanyCode());
        stockVo.setStockHolderCode(stockAllocationVO.getStockHolderCode());
        stockVo.setDocumentType(stockAllocationVO.getDocumentType());
        stockVo.setDocumentSubType(stockAllocationVO.getDocumentSubType());
        stockVo.setAirlineIdentifier(stockAllocationVO.getAirlineIdentifier());
        log.log(Log.FINE, "-----before calling  getStock in allocate ",
				"in Stockhoder----");
		Stock stockIn = getStock(stockVo);
        log.log(Log.FINE, "Found Stock in allocate is------->", stock);
		if(stockAllocationVO.isNewStockFlag()&&
        		!(stockAllocationVO.isBlacklist())){
        	log.log(Log.FINE,"befor caling validateRange---");
        	stockIn.validateRanges(stockAllocationVO,false);
        	log.log(Log.FINE,"-*-afer caling validateRange---");
        	//Commented by A-2881 for ICRD-3082
        	/*if(stockAllocationVO.isEnableStockHistory()) {
				createHistory(stockAllocationVO,StockAllocationVO.MODE_CREATE);
			}*/
        }
        if(!stockAllocationVO.isNewStockFlag()){
       // 	auditVO = (StockAuditVO)AuditUtils.populateAuditDetails(auditVO,stockIn,false);
        }
        stockIn.allocate(stockAllocationVO);

        stockIn.updateStatus(calculateQuantity(stockAllocationVO),
        		false,stockAllocationVO.isManual(),
        		stockAllocationVO.getTransferMode());
        if(stockAllocationVO.isNewStockFlag()){
        //	auditVO = (StockAuditVO)AuditUtils.populateAuditDetails(auditVO,stockIn,true);
     	//	new StockController().auditStock(stockIn,auditVO,AuditVO.CREATE_ACTION,"ALLOCATE_STOCK");
        }else{
        //	auditVO = (StockAuditVO)AuditUtils.populateAuditDetails(auditVO,stockIn,false);
     	//	new StockController().auditStock(stockIn,auditVO,AuditVO.UPDATE_ACTION,"ALLOCATE_STOCK");
        }

        log.log(Log.FINE, "stockAllocationVO.getLastUpdateTime()",
				stockAllocationVO.getLastUpdateTime());
		// this.setLastUpdateTime(this.getLastUpdateTime());
        if(stockAllocationVO.getLastUpdateUser() != null){
        	this.setLastUpdateUser(stockAllocationVO.getLastUpdateUser());
        }else{
        	this.setLastUpdateUser(logon.getUserId());
        }
        log.log(Log.FINE, "this.getLastUpdateTime() from entity", this.getLastUpdateTime());
		log.log(Log.FINE, "this.getLastUpdateTime() from Vo", stockAllocationVO.getLastUpdateTime());
		this.setLastUpdateTime(stockAllocationVO.getLastUpdateTime());
    }

    /**
     *  This method is used to deplete the ranges
     * @param stockAllocationVO
     * @param isBlacklist
     * @throws SystemException
     * @throws StockNotFoundException
     * @throws BlacklistedRangeExistsException
     */
    public void deplete(StockAllocationVO stockAllocationVO,
    		boolean	isBlacklist,boolean isFromOperation)throws SystemException,StockNotFoundException,
    		BlacklistedRangeExistsException{
    	LogonAttributes logon = ContextUtils.getSecurityContext().getLogonAttributesVO();
    	//StockAuditVO auditVO = new StockAuditVO(
		//		StockAuditVO.AUDIT_PRODUCTNAME,
		//		StockAuditVO.AUDIT_MODULENAME,
		//		StockAuditVO.AUDIT_ENTITY);
    	log.log(Log.FINE,"-----Inside deplete in Stockhoder----");
    	StockVO stockVO= new StockVO();
        stockVO.setCompanyCode(stockAllocationVO.getCompanyCode());
        stockVO.setAirlineIdentifier(stockAllocationVO.getAirlineIdentifier());
        stockVO.setStockHolderCode(stockAllocationVO.getStockControlFor());
        stockVO.setDocumentType(stockAllocationVO.getDocumentType());
        stockVO.setDocumentSubType(stockAllocationVO.getDocumentSubType());
        stockVO.setAirlineIdentifier(stockAllocationVO.getAirlineIdentifier());
        log.log(Log.FINE, "before calling getStock in deplete : ", stockVO);
		Stock stockIn=getStock(stockVO);
        log.log(Log.FINE, "Found Stock in deplete is---------------->", stock);
		stockIn.validateRanges(stockAllocationVO,isBlacklist);
        log.log(Log.FINE,"--before calling  stock.deplete in Stockhoder----");
        stockIn.deplete(stockAllocationVO,isBlacklist);
        log.log(Log.FINE,"--after calling  stock.deplete in Stockhoder----");
        Collection<RangeVO> rangesToBeallocated = stockAllocationVO.getRanges();
        //----Added by a-2434 for optimestic cuncurrency bug fix-----
        if(rangesToBeallocated!=null && !rangesToBeallocated.isEmpty()){
        	for(RangeVO rangeVO : rangesToBeallocated){
        		this.setLastUpdateTime(rangeVO.getLastUpdateTime());
        		break;
        	}
        }
        //audit
    	//auditVO = (StockAuditVO)AuditUtils.populateAuditDetails(auditVO,stockIn,false);
       //	new StockController().auditStock(stockAllocationVO,auditVO,AuditVO.DELETE_ACTION,"DEPLETE_STOCK");
    	//audit ends
        log.log(Log.FINE,"--before calling stock.updateStatus in Stockhoder--");
        stockIn.updateStatus(calculateQuantity(stockAllocationVO),true,
        		stockAllocationVO.isManual(),
        		stockAllocationVO.getTransferMode());
        //if(!isFromOperation){
        //	this.setLastUpdateTime(stockAllocationVO.getLastUpdateTime());
       // }
       // else{
       // 	this.setLastUpdateTime(this.getLastUpdateTime());
       // }
        //this.setLastUpdateTime(stockAllocationVO.getLastUpdateTime().toCalendar());
       // this.setLastUpdateTime(this.getLastUpdateTime());
        if(stockAllocationVO.getLastUpdateUser() != null){
        	this.setLastUpdateUser(stockAllocationVO.getLastUpdateUser());
        }else{
        	this.setLastUpdateUser(logon.getUserId());
        }
        log.log(Log.FINE, "Last updated time---------", stockAllocationVO.getLastUpdateTime());

    }
  /**
   * This method is used to approve Stock request
   * @author A-1883
   * @param stockRequestApproveVO
   * @throws SystemException
   * @throws StockNotFoundException
   */
    public void  approveStockRequests(StockRequestApproveVO
    		stockRequestApproveVO)throws SystemException,
    		StockNotFoundException {
    	/*long availableStock = checkStockAvailable(stockRequestApproveVO);
    	long requestedStock = 0;
    	for(StockRequestVO stockRequestVO :stockRequestApproveVO.
    			getStockRequests()){
    		requestedStock += stockRequestVO.getApprovedStock();
    	}
    		if(availableStock < requestedStock){
    			log.log(Log.FINE,"In approveStockRequests***" +
    					"StockNotFoundException");
    			throw new StockNotFoundException();

    		}*/
    		for(StockRequestVO stockRequestVO :stockRequestApproveVO.
    				getStockRequests()){
    			StockRequest stockRequest = StockRequest.find
    			(stockRequestVO.getCompanyCode(),
    						stockRequestVO.getRequestRefNumber(),
    						stockRequestVO.getAirlineIdentifier());
    			log.log(Log.FINE, "In approveStockRequests****stockRequest",
						stockRequest);
				stockRequest.approveStockRequest(stockRequestVO);
    		}

    }

    /**
     * @param companyCode
     * @param stockHolderCodes
     * @return Collection<StockHolderPriorityVO>
     * @throws SystemException
     */
    public static Collection<StockHolderPriorityVO>  findPriorities
    (String companyCode,Collection<String> stockHolderCodes )
    throws SystemException{
    	try {
 			return constructDAO().findPriorities(companyCode,stockHolderCodes);
 		} catch (PersistenceException persistenceException) {
 			throw new SystemException(persistenceException.getErrorCode());
 		}
    }
    /**
     * @param companyCode
     * @param stockHolderCode
     * @param docType
     * @param docSubType
     * @return String
     * @throws SystemException
     */
    public static String  findApproverCode(String companyCode,String
    		stockHolderCode,String docType,String docSubType)
    throws SystemException {
    	 try {
  			return constructDAO().findApproverCode(companyCode,
  					stockHolderCode,docType,docSubType);
  		} catch (PersistenceException persistenceException) {
  			throw new SystemException(persistenceException.getErrorCode());
  		}
    }

    /**
     *
     * @param companyCode
     * @param stockHolderCode
     * @param docType
     * @param docSubType
     * @throws SystemException
     * @throws StockNotFoundException
     */
    public static void  checkStock(String companyCode,String stockHolderCode,
     		  String docType,String docSubType)throws SystemException,
     		  StockNotFoundException {
    	try {
  			List<String> approverCodes = constructDAO().checkStock
  			(companyCode,stockHolderCode,docType,docSubType);
  			if(approverCodes == null || approverCodes.size() == 0 ){
  				 throw new StockNotFoundException();
  			}
  		} catch (PersistenceException persistenceException) {
  			throw new SystemException(persistenceException.getErrorCode());
  		}
    }

    /**
     *
     * @param companyCode
     * @param docType
     * @param docSubType
     * @param incomeRange
     * @return
     * @throws SystemException
     */
    public static RangeVO findRangeDelete(String companyCode,String docType,
    		String docSubType,String incomeRange)throws SystemException	{
    	try {
 			return constructDAO().findRangeDelete(companyCode,
 					docType,docSubType,incomeRange);
 		} catch (PersistenceException persistenceException) {
 			throw new SystemException(persistenceException.getErrorCode());
 		}
    }

    /**
     * @param companyCode
     * @param airlineIdentifier
     * @param documentType
     * @param documentSubType
     * @return
     * @throws RangeExistsException
     * @throws SystemException
     */
    public String retrieveNextDocumentNumber(DocumentFilterVO documentFilterVO,
    		 DocumentVO documentVO)//A-6858
    	throws RangeExistsException,SystemException{
    		log.log(Log.FINE,"Entering find next document number in stock holder");
    		Stock resultStock=null;
    		String result =null;
    		for(Stock stockIndex:this.getStock()){
    			/* As part of ICRD-85038
    			 * As per Create Stock Screen behaviour a stockholder can have stock range under various airlineIds.
    			 * Checking whether a stock exists should be based on this airlineId. 
    			 * For eg; If Agent A1 has Stock range under airline Ids 1001 and 1235 
    			 * next document for 235 60060011 should be checked for stock under airline Id 1235 and not 1001.   
    			 * 
    			 * Added AirlineIdentifier Check for the following case : 
    			 * For Stock Holder  - Agent1 , there exists an AWB range 1010101 - 1010102 for a different airline(say American Airlines: AA(1001))
    			 * In order to avoid an error : "Stock does not exists for the Agent" and restrict the fetching of this stock range
    			 */
    			if(documentVO.getDocumentType().equals(stockIndex.getStockPK().getDocumentType())    					
    						&&(documentFilterVO.getAirlineIdentifier() == stockIndex.getStockPK().getAirlineIdentifier())){    				
    				//Modified as part of ICRD-201875
    				if(documentVO.getDocumentSubType() != null && documentVO.getDocumentSubType().length() > 0){
    					if(documentVO.getDocumentSubType().equals(stockIndex.getStockPK().getDocumentSubType())){    				
	    					if(stockIndex.hasRanges()){
	    						resultStock=stockIndex;
	    						break;
	    					}
    					}
    				}else{    				
    					if(stockIndex.hasRanges()){
    						resultStock=stockIndex;
    						break;
    					}
    				}
    			}
    		}
    		if(resultStock != null){
    		log.log(Log.FINE,">>>>>>>>>>>>>>>>The stock details<<<<<<<<<<<<<<<<");
    		log.log(Log.FINE, "companycode:", resultStock.getStockPK()
    				.getCompanyCode());
			log.log(Log.FINE, "stockHoldercode:", resultStock.getStockPK().getStockHolderCode());
			log.log(Log.FINE, "doctype:", resultStock.getStockPK().getDocumentType());
			log.log(Log.FINE, "docsubtype:", resultStock.getStockPK().getDocumentSubType());
			//Changed by A-6858 for ICRD-234889
			result = resultStock.retrieveNextDocumentNumber(documentFilterVO,documentVO);
    		}else{
    			throw new RangeExistsException(RangeExistsException.RANGE_NOT_EXISTS);
    		}
    		return result;

    }
    /**
     * Method for checking stock available
     * @param stockRequestApproveVO
     * @return availableStock
     * @throws StockNotFoundException
     */
/*    private long checkStockAvailable(StockRequestApproveVO
    		stockRequestApproveVO)
    	throws StockNotFoundException{
    		StockVO stockVO = new StockVO();
    		stockVO.setCompanyCode(stockRequestApproveVO.getCompanyCode());
    		stockVO.setDocumentType(stockRequestApproveVO.getDocumentType());
    		stockVO.setDocumentSubType(stockRequestApproveVO.getDocumentSubType());
    		stockVO.setStockHolderCode(stockRequestApproveVO.getApproverCode());
    		log.log(Log.FINE,"Before FindStock");
    	      	Stock stockIn = getStock(stockVO);
    	      	log.log(Log.FINE,"**********stock"+stock);
    	      long availableStock = 0;
    	     for(Range range:stockIn.getRanges()){
    	    	  	 availableStock  += range.getNumberOfDocuments();
    	    	  	log.log(Log.FINE,"*****availableStock"+availableStock);
    	     }
    	  return availableStock;
    }
*/    /**
     * Method for getting the StockControlDefaultsDAO
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    private static StockControlDefaultsDAO constructDAO()
	throws SystemException, PersistenceException {
		EntityManager em = PersistenceController.getEntityManager();
		return StockControlDefaultsDAO.class.cast
		(em.getQueryDAO(StockControlDefaultsPersistenceConstants.MODULE_NAME));

	}
   /**
    * Method for updating the stock
    * @param stockHolderVO
    * @throws CreateException
    * @throws SystemException
    * @throws RemoveException
    * @throws RangeExistsException
    * @throws StockNotFoundException
    */
    private void updateChildren(StockHolderVO stockHolderVO)
    throws CreateException,SystemException,RemoveException,
    RangeExistsException,StockNotFoundException {
    	//updateStockInsert(stockHolderVO.getStock(),stockHolderVO.getOperationFlag());
    	updateStockModify(stockHolderVO.getStock(),stockHolderVO.
    			getOperationFlag());
    	updateStockDelete(stockHolderVO.getStock());
    }
   /**
    * Method for modifying a stock
    * @param stockVOs
    * @param operationalFlag
    * @throws SystemException
    * @throws StockNotFoundException
    */
    private void updateStockModify(Collection<StockVO> stockVOs,
    		String operationalFlag)	throws SystemException,
    		StockNotFoundException{
    	//Collection<AuditFieldVO> auditFields;
    	for (StockVO stockVO : stockVOs){
			if(StockVO.OPERATION_FLAG_UPDATE.equals(stockVO.
					getOperationFlag())){
				Stock stockUpdate = getStock(stockVO);
				log.log(Log.FINE, "----find stock--", stockUpdate.getStockPK()
						.getAirlineIdentifier());
				StockAuditVO auditVo = new StockAuditVO
				(StockAuditVO.AUDIT_PRODUCTNAME,StockAuditVO.AUDIT_MODULENAME,
						StockAuditVO.AUDIT_ENTITY);
				auditVo = (StockAuditVO)AuditUtils.populateAuditDetails
				(auditVo,stockUpdate,false);
				if(stockUpdate != null){
					stockUpdate.update(stockVO,this.getStockHolderPk().
							getCompanyCode());
					auditVo = (StockAuditVO)AuditUtils.populateAuditDetails
					(auditVo,stockUpdate,false);
					auditStockDetails(stockUpdate,auditVo,AuditVO.UPDATE_ACTION);
					/*AuditUtils.updateAuditableFieldDetails
					 * (stockUpdate,auditFields);
		    		auditStockDetails(auditFields, stockVO,
		    		StockHolderAuditVO.STOCK_UPDATE_ACTIONCODE,
		    				operationalFlag);*/

				}
			}
		}
		return;
    }
   /**
    * This method returns the stock BO class, whose values are identical
    * to the values passed to this method ( as stockVO )
    *
    * Method for finding a particular stock
    * @param stockVO
    * @return Stock
    * @throws StockNotFoundException
    */
    public Stock getStock(StockVO stockVO) throws StockNotFoundException{
    	StockPK stockPK=null;
		Stock stockOuter=null;
		log.log(Log.FINE, "----Thse stock inside the stock holer ", stockVO);
		if(this.getStock() != null){
			for(Stock stock : this.getStock()){
				stockPK = new StockPK();

				stockPK.setCompanyCode(this.getStockHolderPk().getCompanyCode());
				stockPK.setStockHolderCode(this.getStockHolderPk()
						.getStockHolderCode());
				stockPK.setDocumentType(stockVO.getDocumentType());
				stockPK.setDocumentSubType(stockVO.getDocumentSubType());
				stockPK.setAirlineIdentifier(stockVO.getAirlineIdentifier());


				log.log(Log.INFO, "$$$$$$$$$$$$$$$$ stockPK.companyCode ",
						stockPK.getCompanyCode());
				log.log(Log.INFO, "$$$$$$$$$$$$$$$$ stockPK.stockHolderCode ",
						stockPK.getStockHolderCode());
				log.log(Log.INFO, "$$$$$$$$$$$$$$$$ stockPK.documentType",
						stockPK.getDocumentType());
				log.log(Log.INFO, "$$$$$$$$$$$$$$$$ stockPK.documentSubType",
						stockPK.getDocumentSubType());
				log.log(Log.INFO, "$$$$$$$$$$$$$$$$ stockPK.airlineIdentifier",
						stockPK.getAirlineIdentifier());
				if(stockPK.equals(stock.getStockPK())){
					stockOuter=stock;
				}
			}
		}
		if(stockOuter == null){
			throw new StockNotFoundException();
		}
		return stockOuter;
    }
   /**
    * Method for updating stock holder with a stock to delete
    * @param stockVOs
    * @throws SystemException
    * @throws RangeExistsException
    * @throws StockNotFoundException
    */
    private void updateStockDelete(Collection<StockVO> stockVOs)
    	throws SystemException,RangeExistsException,StockNotFoundException{
    	//Collection<AuditFieldVO> auditFields;
    	Collection<ErrorVO> errors =new ArrayList<ErrorVO>();
    	Object[] errorObj=new Object[1];
    	for(StockVO stockVO : stockVOs){
    		if(StockVO.OPERATION_FLAG_DELETE.equals(stockVO.
    				getOperationFlag())){
    			Stock stockIn=getStock(stockVO);
    			if(stockIn!=null){
    				if(stockIn.hasRanges()){
    					log.log(Log.FINE,"-->inside hasRangeCheck->>>>>>");
    					errorObj[0]=stockIn.getStockPK().getCompanyCode()+
    						stockIn.getStockPK().getAirlineIdentifier() +
						stockIn.getStockPK().getStockHolderCode()+
						stockIn.getStockPK().getDocumentType()+
						stockIn.getStockPK().getDocumentType();
    					ErrorVO errorVO =new ErrorVO(RangeExistsException.RANGE_EXISTS,errorObj);
    					errors.add(errorVO);
    					break;
    				}
    			}
    		}
    	}
    	if(errors.size()<=0){
    		for(StockVO stockVo : stockVOs){
    			if(StockVO.OPERATION_FLAG_DELETE.equals(stockVo.
    					getOperationFlag())){
    				Stock stockDelete=getStock(stockVo);
    				//auditFields = AuditUtils.getAuditableFieldDetails
    				//(stockDelete,false);
    				if(stockDelete!=null){
    					stockDelete.remove();
    					StockAuditVO auditVo = new StockAuditVO
    					(StockAuditVO.AUDIT_PRODUCTNAME,StockAuditVO.AUDIT_MODULENAME,
    							StockAuditVO.AUDIT_ENTITY);
    					auditVo = (StockAuditVO)AuditUtils.populateAuditDetails
    					(auditVo,stockDelete,false);
    					auditStockDetails(stockDelete,auditVo,AuditVO.DELETE_ACTION);
    					//auditStockDeleteDetails(stockDelete,
    					//StockHolderAuditVO.STOCK_DELETE_ACTIONCODE);
    					this.stock.remove(stockDelete);
    				}
    			}
    		}
    	}else{
    		log.log(Log.FINE,"------------>RangeExists-------------->");
    		RangeExistsException rangeExistsException=new
    		RangeExistsException();
    		rangeExistsException.addErrors(errors);
    		throw rangeExistsException;

    	}
    }
    /**
     * Checking for any stock has range
     * @return Collection<ErrorVO>
     */
    private Collection<ErrorVO> checkForStockRange(){
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	Object[] errorObj=new Object[1];
    	for(Stock stock:this.getStock()){
    		if(stock.hasRanges()){
    			errorObj[0]=this.getStockHolderPk().getStockHolderCode();
    			ErrorVO errorVO =new ErrorVO(RangeExistsException.RANGE_EXISTS,errorObj);
    			errors.add(errorVO);
    			break;
    		}
    	}
    	return errors;
    }
/**
 * Method for auditing stockdetails
 * @param auditFields
 * @param stockVO
 * @param actionCode
 * @param operationalFlag
 * @throws SystemException
 */
/* private void auditStockDetails(Collection<AuditFieldVO> auditFields,
		 StockVO stockVO,String actionCode
		 ,String operationalFlag)
    throws SystemException{
    	StringBuilder additionalInfo = new StringBuilder();
    	StockHolderAuditVO stockHolderAuditVO = new StockHolderAuditVO
    	(StockHolderVO.STOCKHOLDER_AUDIT_PRODUCTNAME,
    			StockHolderVO.STOCKHOLDER_AUDIT_MODULENAME,
    			StockHolderVO.STOCKHOLDER_AUDIT_ENTITYNAME);
    	stockHolderAuditVO.setStockHolderCode(this.getStockHolderPk().
    			stockHolderCode);
    	stockHolderAuditVO.setStockHolderType(this.
    			getStockHolderType());
    	stockHolderAuditVO.setActionCode(actionCode);
    	stockHolderAuditVO.setLastUpdateTime(this.
    			getLastUpdateTime());
    	stockHolderAuditVO.setLastUpdateUser(this.
    			getLastUpdateUser());
    	if(StockHolderVO.OPERATION_FLAG_INSERT.equals(stockVO.
    			getOperationFlag())
    			|| StockHolderVO.OPERATION_FLAG_INSERT.equals
    			(operationalFlag)){
    	stockHolderAuditVO.setAuditRemarks(AuditAction.INSERT.toString());
    	}
    	else if(StockHolderVO.OPERATION_FLAG_UPDATE.equals
    			(stockVO.getOperationFlag())){
    		stockHolderAuditVO.setAuditRemarks(AuditAction.
    				UPDATE.toString());
    	}
    	for(AuditFieldVO auditField : auditFields) {
			additionalInfo.append(" Field Name: ").append
			(auditField.getFieldName()).append(" Field Description: ")
				.append(auditField.getDescription()).
				append(" Old Value: ").append(auditField.getOldValue())
				.append(" New Value: ").append(auditField.getNewValue());
		}
    	 additionalInfo.append("CompanyCode:").append(this.getStockHolderPk().
    			 companyCode.concat("-")).append("StockHolderCode:").
    			 append(this.getStockHolderPk().stockHolderCode.
    			concat("-")).append("DocumentTye:").append(stockVO.
    			getDocumentType().concat("-")).append("DocumentSubType:").
    			append(stockVO.getDocumentSubType());

    	stockHolderAuditVO.setAdditionalInfo(additionalInfo.toString());
    	AuditUtils.performAudit(stockHolderAuditVO);
    	StockControlAuditMDB stockControlAuditMDB=new StockControlAuditMDB();
    	Collection ary = new ArrayList();
    	ary.add(stockHolderAuditVO);
    	//stockControlAuditMDB.audit(ary);

    }
*//**
 * Method for auditing stockdelete details
 * @param stock
 * @param actionCode
 * @throws SystemException
 */
 /*private void auditStockDeleteDetails(Stock stock,String actionCode)
 throws SystemException{
	 StringBuilder additionalInfo = new StringBuilder();
	 StockHolderAuditVO stockHolderAuditVO=new StockHolderAuditVO(
 			StockHolderVO.STOCKHOLDER_AUDIT_PRODUCTNAME,StockHolderVO.
 			STOCKHOLDER_AUDIT_MODULENAME,
 			StockHolderVO.STOCKHOLDER_AUDIT_ENTITYNAME);
 	//stockHolderAuditVO.setCompanyCode(stockHolder.getStockHolderPk()
	 //.companyCode);
 	stockHolderAuditVO.setStockHolderCode(this.getStockHolderPk().
 			stockHolderCode);
 	stockHolderAuditVO.setStockHolderType(this.getStockHolderType());
 	stockHolderAuditVO.setActionCode(actionCode);
 	stockHolderAuditVO.setLastUpdateTime(this.getLastUpdateTime());
 	stockHolderAuditVO.setLastUpdateUser(this.getLastUpdateUser());
 	additionalInfo.append("Stock corresponding:").append(this.
 			getStockHolderPk().companyCode.concat("-"))
 	.append(this.getStockHolderPk().stockHolderCode.concat("-")).
 	append(stock.getStockPK().documentType.concat("-"))
 	.append(stock.getStockPK().documentSubType).append(" is  Deleted");
 	stockHolderAuditVO.setAdditionalInfo(additionalInfo.toString());
 	stockHolderAuditVO.setAuditRemarks(AuditAction.DELETE.toString());
 	AuditUtils.performAudit(stockHolderAuditVO);
 	StockControlAuditMDB stockControlAuditMDB=new StockControlAuditMDB();
 	Collection ary = new ArrayList();
 	ary.add(stockHolderAuditVO);
 	//stockControlAuditMDB.audit(ary);
 }
*/ /**
  * Method for returning the requested stock quantity
  * @param stockAllocationVO
  * @return long
  */
 private long calculateQuantity(StockAllocationVO stockAllocationVO){
	 log.log(Log.FINE,"-------------->>>>inside caluclateQty---->>");
	 long stockQuantity=0;
	 int qty = 0;
	 for(RangeVO rangeVo : stockAllocationVO.getRanges()){
     	qty=Range.difference(rangeVo.getStartRange(),rangeVo.getEndRange());
     	stockQuantity=stockQuantity+new Integer(qty).longValue();
     }
	 log.log(Log.FINE,"-------------->>>>outside caluclateQty---->>");
	 return stockQuantity;
 }
 	/**
 	 * @author A-1885
	 * @param stockIn
	 * @param auditVo
	 * @param actionCode
	 * @throws SystemException
	 */
	private void auditStockDetails(Stock stockIn,
			StockAuditVO auditVo,String actionCode)
			throws SystemException {
		if(auditVo!=null && stockIn!=null && stockIn.getStockPK()!=null){
		StringBuilder additionalInfo = new StringBuilder();
		//StringBuilder stockInfo = new StringBuilder();
		auditVo.setCompanyCode(stockIn.getStockPK().getCompanyCode());
		auditVo.setStockHolderCode(stockIn.getStockPK().getStockHolderCode());
		auditVo.setDocType(stockIn.getStockPK().getDocumentType());
		auditVo.setDocSubType(stockIn.getStockPK().getDocumentSubType());
		auditVo.setAirlineId(stockIn.getStockPK().getAirlineIdentifier());
		auditVo.setActionCode(actionCode);
		auditVo.setLastUpdateTime(this.getLastUpdateTime());
		auditVo.setLastUpdateUser(this.getLastUpdateUser());
		if(auditVo.getAuditFields()!=null && auditVo.getAuditFields().size()>0){
			for (AuditFieldVO auditField : auditVo.getAuditFields()) {
				additionalInfo.append(" Field Name: ").append(
						auditField.getFieldName()).append(" Field Description: ")
						.append(auditField.getDescription()).append(" Old Value: ")
						.append(auditField.getOldValue()).append(" New Value: ")
						.append(auditField.getNewValue());
			}
		}
		additionalInfo.append(" STOCK ").append(actionCode);
		auditVo.setAdditionalInfo(additionalInfo.toString());
		if(AuditVO.CREATE_ACTION.equals(actionCode)){
			auditVo.setAuditRemarks(AuditAction.INSERT.toString());
		}
		if(AuditVO.UPDATE_ACTION.equals(actionCode)){
			auditVo.setAuditRemarks(AuditAction.UPDATE.toString());
		}
		if(AuditVO.DELETE_ACTION.equals(actionCode)){
			auditVo.setAuditRemarks(AuditAction.DELETE.toString());
		}
		AuditUtils.performAudit(auditVo);
		}

	}

	/**
	 * Method to find document details
	 * @param companyCode
	 * @param airlineIdentifier
	 * @param documentNumber
	 * @return
	 * @throws SystemException
	 */
	public static StockRequestVO findDocumentDetails(String companyCode,
			int airlineIdentifier,String documentNumber)throws SystemException{
	  	try {
			return constructDAO().findDocumentDetails(companyCode,
											airlineIdentifier,documentNumber);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	 * @author a-1885
	 * @param filterVo
	 * @return
	 * @throws SystemException
	 */
	public static Collection<StockHolderStockDetailsVO>
		findStockHolderStockDetails(InformAgentFilterVO filterVo)throws
		SystemException{
		try {
			return constructDAO().findStockHolderStockDetails(filterVo);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * Method to find if the stock holder is an approver
	 * @author A-1927
	 * @param companyCode
	 * @param stockHolderCode
	 * @return
	 * @throws SystemException
	 */
	public static boolean checkApprover(String companyCode,
            String stockHolderCode) throws SystemException {
        	try {
    			return constructDAO().checkApprover(companyCode,
    					stockHolderCode);
    		} catch (PersistenceException persistenceException) {
    			throw new SystemException(persistenceException.getErrorCode());
    		}

     }
	/**
	 * @author a-1885
	 * @param stockAllocationVo
	 * @param startRange
	 * @param numberOfDocuments
	 * @return
	 * @throws SystemException
	 */
	public static Collection<RangeVO> findRangeForTransfer(StockAllocationVO
			stockAllocationVo,String startRange,long numberOfDocuments)
			throws SystemException{
		try {
			return constructDAO().findRangeForTransfer(stockAllocationVo,
					startRange,numberOfDocuments);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}


	/**
	 * Validates whether the stock of this stockholder has any stock
	 * with document type as 'AWB'
	 * If no stock is found under type 'AWB' exception is thrown
	 *
	 * @param airlineId
	 * @throws StockControlDefaultsBusinessException
	 */
	public void checkForAWBStock(int airlineId)
			throws StockControlDefaultsBusinessException{
		log.log(Log.FINER,"ENTRY");

		/*
		 * if there is no stock for the stock holder,
		 * throw AWB STOCK not found exception
		 */
		if(this.getStock() == null){
			log.log(Log.SEVERE, "No AWB Stock found for stockholder ", this.getStockHolderPk().getStockHolderCode());
			throw new StockControlDefaultsBusinessException(
					StockControlDefaultsBusinessException.STOCKHOLDER_AWBSTOCK_NOTFOUND,
					new Object[] { this.getStockHolderPk().getStockHolderCode() });
		}

		/*
		 * iterates the child entities and checks whether any
		 * stock exists with document type as 'AWB'
		 */
		boolean hasAWBStock = false;
		for(Stock stk : this.getStock()){
			if(stk.getStockPK().getDocumentType().equals(
					DocumentValidationVO.DOC_TYP_AWB) &&
					stk.getStockPK().getAirlineIdentifier() == airlineId){
				hasAWBStock = true;
			}
		}

		/*
		 * if there is no stock for the stock holder,
		 * throw AWB STOCK not found exception
		 */
		if(! hasAWBStock){
			log.log(Log.SEVERE, "No AWB Stock found for stockholder ", this.getStockHolderPk().getStockHolderCode());
			throw new StockControlDefaultsBusinessException(
					StockControlDefaultsBusinessException.STOCKHOLDER_AWBSTOCK_NOTFOUND,
					new Object[] { this.getStockHolderPk().getStockHolderCode() });
		}
		log.log(Log.FINER,"RETURN");
	}


	/**
	 * Iterates each 'AWB' stock and checks whether any range has
	 * the document number included in it
	 *
	 * @param airlineId
	 * @param documentNumber
	 * @throws StockControlDefaultsBusinessException
	 * @throws SystemException
	 */
	public void checkAWBRangeExists(int airlineId, long documentNumber)
			throws StockControlDefaultsBusinessException, SystemException{
		log.log(Log.FINER,"ENTRY");
		if(this.getStock() == null){
			return;
		}

		for(Stock stock : this.getStock()){
			if(stock.getStockPK().getDocumentType().equals(
					DocumentValidationVO.DOC_TYP_AWB) &&
					stock.getStockPK().getAirlineIdentifier() == airlineId){
				/*
				 * calls the checkDocumentExists method in Stock entity
				 */
				if( stock.checkDocumentExists(airlineId, documentNumber) ){
					/*
					 * if at least one range has the 'AWBNUM' in it
					 * then return
					 */
					log.log(Log.FINER,"RETURN");
					return;
				}
			}
		}

		/*
		 * throws exception since the document number is not found
		 * in the ranges of 'AWB' document type.
		 * If at least one range exists in any of the sub type, the method
		 * will be returned before entering this block
		 */
		log.log(Log.SEVERE, "AWB document number not existing " );
		throw new StockControlDefaultsBusinessException();

	}
	/**
	 * @author a-1885
	 * @param reqVo
	 * @return
	 * @throws SystemException
	 */
	public static String findAutoProcessingQuantityAvailable(StockRequestVO reqVo)
	throws SystemException{
		try {
			return constructDAO().findAutoProcessingQuantity(reqVo);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	 * @author a-1885
	 * @param companyCode
	 * @param stockHolderType
	 * @return
	 * @throws SystemException
	 */
	public static String checkForDuplicateHeadQuarter(String companyCode,
			String stockHolderType)throws SystemException{
		try {
			return constructDAO().checkDuplicateHeadQuarters(companyCode,
					stockHolderType);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	
	
	public static String findHeadQuarterDetails(String companyCode,int airlineIdentifier,
			String documentType,String documentSubType) throws SystemException {
        	try {
    			return constructDAO().findHeadQuarterDetails(companyCode,airlineIdentifier,
    					documentType,documentSubType);
    		} catch (PersistenceException persistenceException) {
    			throw new SystemException(persistenceException.getErrorCode());
    		}

     }
	/**
	 * @author A-1885
	 * @param stockAllocationVO
	 * @throws SystemException
	 */
	public void returnDocumentToStock(StockAllocationVO stockAllocationVO)
	throws SystemException{
		StockVO stockVO = new StockVO();
		stockVO.setCompanyCode(stockAllocationVO.getCompanyCode());
		stockVO.setAirlineIdentifier(stockAllocationVO.getAirlineIdentifier());
		stockVO.setDocumentType(stockAllocationVO.getDocumentType());
		stockVO.setDocumentSubType(stockAllocationVO.getDocumentSubType());
		stockVO.setStockHolderCode(stockAllocationVO.getStockHolderCode());
		Stock stk = null;
		try{
			stk = getStock(stockVO);
		}
		catch(StockNotFoundException stkntfndexc){
			for(ErrorVO errorVo : stkntfndexc.getErrors()){
				throw new SystemException(errorVo.getErrorCode());
			}
		}
		if(stk!=null){
			stk.returnDocumentToStock(stockAllocationVO);
		}

	}
	/**
	 * @A-2870
	 */
	public String obtainApproverForStockHolder(String doctyp){
		String approver = null;
		if(this.getStock()!=null){
			for(Stock stk : this.getStock()){
				if(stk.getStockPK().getDocumentType().equals(doctyp)){
					approver = stk.getStockApproverCode();
				}
			}
		}
		return approver;
	}

	/**
		 *
		 * @param stockDepleteFilterVO
		 * @return
		 * @throws SystemException
		 */
		public static Collection<StockAllocationVO> findStockRangeUtilisation
		(StockDepleteFilterVO stockDepleteFilterVO)throws SystemException{
		    try {
				return constructDAO().findStockRangeUtilisation(stockDepleteFilterVO);
				} catch (PersistenceException persistenceException) {
				throw new SystemException(persistenceException.getErrorCode());
			}

		}    
	

	 public static String  findPrivileage(String stockControlFor)
	    throws SystemException {

	    	 try {


	  			return constructDAO().findPrivileage(stockControlFor);
	  		} catch (PersistenceException persistenceException) {

	  			throw new SystemException(persistenceException.getErrorCode());
	  		}  
	    }

 /**
     * This method is used to fetch the range details of a stockholder
     * @param stockFilterVO
     * @return StockRangeVO
     * @throws SystemException
     * @throws StockNotFoundException
     */
   public static Collection<RangeVO> findAWBStockDetailsForPrint(StockFilterVO stockFilterVO)
    	throws SystemException{
	   try {
		   Collection<RangeVO> rangeVOs = new ArrayList<RangeVO>();
		   rangeVOs = constructDAO().findAWBStockDetailsForPrint(stockFilterVO);	    	
	    	return rangeVOs;
			} catch (PersistenceException persistenceException) {
				throw new SystemException(persistenceException.getErrorCode());
			}
    }
   /**
    * This method is used to return stock details to customer enquiry
    * @param stockDetailsFilterVO
    * @return StockDetailsVO
    * @throws SystemException
    */
 public static StockDetailsVO findCustomerStockDetails(StockDetailsFilterVO stockDetailsFilterVO)
   	throws SystemException{
	  	try {
			return constructDAO().findCustomerStockDetails(stockDetailsFilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
   }
 /**
  * This method issues a DAO query to obtain the stockholder of a particular
  * customer
  * @param companyCode
  * @param customerCode
  * @return 
  * @throws SystemException
  */
 public static String findStockHolderForCustomer(String companyCode,String customerCode)
 	throws SystemException {
 	try {
			return constructDAO().findStockHolderForCustomer(companyCode,customerCode);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}

 }
  
 /**
	 * @author a-1885
	 * logClearingThreshold added under 
	 * BUG_ICRD-23686_AiynaSuresh_28Mar13
	 * @param companyCode
	 * @param airlineId
	 * @param logClearingThreshold
	 * @throws SystemException 
	 */
	public static void deleteStockRangeUtilisation(String companyCode,
			int airlineId,GMTDate actualDate,int logClearingThreshold)throws SystemException{		
		try {
			constructDAO().deleteStockRangeUtilisation(companyCode,
					airlineId,actualDate,logClearingThreshold);   
			} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}   
}
	
	/**
	 * @A-3155
	 * @param stockAllocationVO
	 * @throws SystemException
	 */
private void createHistory(StockAllocationVO stockAllocationVO,String status) throws SystemException{

		log.log(Log.FINE, " Inside Create History");
		Collection<RangeVO> ranges = stockAllocationVO.getRanges();
  
		StockRangeHistoryVO stockRangeHistoryVO = new StockRangeHistoryVO();

		stockRangeHistoryVO.setCompanyCode(stockAllocationVO.getCompanyCode());
		stockRangeHistoryVO.setDocumentType(stockAllocationVO.getDocumentType());
		stockRangeHistoryVO.setDocumentSubType(stockAllocationVO.getDocumentSubType());
		stockRangeHistoryVO.setAirlineIdentifier(stockAllocationVO.getAirlineIdentifier());
		stockRangeHistoryVO.setLastUpdateTime(stockAllocationVO.getLastUpdateTime());
		stockRangeHistoryVO.setLastUpdateUser(stockAllocationVO.getLastUpdateUser());
		stockRangeHistoryVO.setTransactionDate(new GMTDate(true));  

		if(status.equalsIgnoreCase(StockAllocationVO.MODE_CREATE)){
			// for creation of new ranges
			stockRangeHistoryVO.setStatus(StockAllocationVO.MODE_CREATE);

		}
		if(status.equalsIgnoreCase(StockAllocationVO.MODE_DELETED)){ 
			// for deleted ranges
			stockRangeHistoryVO.setStatus(StockAllocationVO.MODE_DELETED);

		}

		stockRangeHistoryVO.setFromStockHolderCode(stockAllocationVO.getStockHolderCode());

		/*if(stockAllocationVO.isManual()){  
			stockRangeHistoryVO.setRangeType(StockAllocationVO.MODE_MANUAL);
		}
		else{
			stockRangeHistoryVO.setRangeType(StockAllocationVO.MODE_NEUTRAL);
		}*/
  

		for(RangeVO rangeVO :ranges){
			stockRangeHistoryVO.setStartRange(rangeVO.getStartRange());
			stockRangeHistoryVO.setEndRange(rangeVO.getEndRange());
				if(rangeVO.getAsciiStartRange()==0){
					rangeVO.setAsciiStartRange(Range.findLong(rangeVO.getStartRange()));

				}
				if(rangeVO.getAsciiEndRange()==0){
					rangeVO.setAsciiEndRange(Range.findLong(rangeVO.getEndRange()));
				}
				if(rangeVO.isManual()){
					stockRangeHistoryVO.setRangeType(StockAllocationVO.MODE_MANUAL);
				}else{
					stockRangeHistoryVO.setRangeType(StockAllocationVO.MODE_NEUTRAL);
				}



		    stockRangeHistoryVO.setAsciiStartRange(rangeVO.getAsciiStartRange());
			stockRangeHistoryVO.setAsciiEndRange(rangeVO.getAsciiEndRange());
			stockRangeHistoryVO.setStartRange(rangeVO.getStartRange());

				if(rangeVO.getNumberOfDocuments()==0){

					long n=((rangeVO.getAsciiEndRange())-(rangeVO.getAsciiStartRange())) +1;
					rangeVO.setNumberOfDocuments(n);
				}
			stockRangeHistoryVO.setNumberOfDocuments(rangeVO.getNumberOfDocuments());
		 	stockRangeHistoryVO.setStartRange(rangeVO.getStartRange());
		 	log.log(Log.FINE, "  Create History completed!!!!!!!!!!",
					stockRangeHistoryVO);
			new StockRangeHistory(stockRangeHistoryVO);

		}
	}
 
 
	/**
	 * @author A-5153
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException
	 */
	public static void autoDepleteStock() throws SystemException,
			StockControlDefaultsBusinessException {

		try {
			constructDAO().autoDepleteStock();
		} catch (PersistenceException persistenceException) {
			// throw new SystemException(persistenceException.getErrorCode());
			throw new StockControlDefaultsBusinessException(
					persistenceException.getMessage());
		}

	}
	
	public static String checkStockUtilisationForRange(StockFilterVO stockFilterVO) throws SystemException{ 

		try {
			return constructDAO().checkStockUtilisationForRange(stockFilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}

	}
	private String validateStockPeriod(StockAllocationVO stockAllocationVO,int stockIntroductionPeriod)throws SystemException{		
		List<Integer> list = null;
		try{
			list = constructDAO().validateStockPeriod(stockAllocationVO,stockIntroductionPeriod);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
		if(list!=null && list.size()>0){
			String errorRanges = mergeRanges(list);
			return errorRanges;
		}
		return null;
	}
	public String mergeRanges(List<Integer> list ){

         List<Integer> sublistsStarsAndEnds= new ArrayList<Integer>();
         sublistsStarsAndEnds.add(list.get(0));
         for (int i=1; i<list.size()-1; i++){
             if (list.get(i)>1+list.get(i-1)){
              
                    sublistsStarsAndEnds.add(list.get(i-1));
                    sublistsStarsAndEnds.add(list.get(i));
             }
         }
         sublistsStarsAndEnds.add(list.get(list.size()-1));
        // System.out.println("sublistsStarsAndEnds: " + sublistsStarsAndEnds);

         List<Integer[]> ranges= new ArrayList<Integer[]>();
         for (int i=0; i<sublistsStarsAndEnds.size()-1; i=i+2){
             Integer[] currentrange=new Integer[2];
             currentrange[0]=sublistsStarsAndEnds.get(i);
             currentrange[1]=sublistsStarsAndEnds.get(i+1);
             ranges.add(currentrange);
         }
         StringBuilder rangestxt= null;
         for (int i=0; i<ranges.size(); i++){
        	 if(rangestxt!=null){
        		 rangestxt.append(", ");
        		 rangestxt.append(ranges.get(i)[0]).append("-").append(ranges.get(i)[1]);
        	 }
        	 else{
        		 rangestxt = new StringBuilder();
        		 rangestxt.append(ranges.get(i)[0]).append("-").append(ranges.get(i)[1]);
        	 }
          } 
        // System.out.println("ranges: " + rangestxt);
         if(rangestxt!=null){
    	 	return rangestxt.toString() ;
         }       
        return null;
        
     } 
	/**
	 * @author A-7534
	 * @param rangeVOs
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException
	 */
	public void deleteStock(Collection<RangeVO> rangeVOs)
    		throws SystemException,StockControlDefaultsBusinessException{
		log.entering("StockHolder","deleteStock");
		if(rangeVOs!=null && rangeVOs.size()>0){
			new Stock().deleteStock(rangeVOs);
			deletedStockHistory(rangeVOs);
		}
		return;
	}
	/**
	 * @author A-7534
	 * @param ranges
	 * @throws SystemException
	 */
	private void deletedStockHistory(Collection<RangeVO> ranges)
			throws SystemException {
		LogonAttributes logon = ContextUtils.getSecurityContext().getLogonAttributesVO();
		StockAllocationVO stockAllocationVO = new StockAllocationVO();
		Collection<RangeVO> rangeVOs = null;
		RangeVO rangeAdd = null;
		for(RangeVO rangeVO : ranges){
			stockAllocationVO.setCompanyCode(rangeVO.getCompanyCode());
			stockAllocationVO.setAirlineIdentifier(rangeVO.getAirlineIdentifier());
			stockAllocationVO.setStockHolderCode(rangeVO.getStockHolderCode());
			stockAllocationVO.setDocumentType(rangeVO.getDocumentType());
			stockAllocationVO.setDocumentSubType(rangeVO.getDocumentSubType());
			stockAllocationVO.setLastUpdateUser(logon.getUserId());
			rangeVOs = new ArrayList<RangeVO>();
			rangeAdd = new RangeVO();
			rangeAdd.setStartRange(rangeVO.getStartRange());
			rangeAdd.setEndRange(rangeVO.getEndRange());
			if(rangeVO.isManual()){
				rangeAdd.setManual(true);
			}else{
				rangeAdd.setManual(false);
			}
			rangeVOs.add(rangeAdd);
			stockAllocationVO.setRanges(rangeVOs);
			createHistory(stockAllocationVO,"D");
		}

    }
	
	/**
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 */
	  public static Page<RangeVO> findAvailableRanges(StockFilterVO stockFilterVO) throws SystemException {
	    	try {
	    		return constructDAO().findAvailableRanges(stockFilterVO);
			} catch (PersistenceException persistenceException) {
				throw new SystemException(persistenceException.getErrorCode());
			}
	    }
	 /**
	  * @param filterVO
	  * @return
	  * @throws SystemException
	  */
	  public static int findTotalNoOfDocuments(StockFilterVO stockFilterVO) throws SystemException {
		  try {
			  return constructDAO().findTotalNoOfDocuments(stockFilterVO);
		  } catch (PersistenceException persistenceException) {
			  throw new SystemException(persistenceException.getErrorCode());
		  }
	  }
}
