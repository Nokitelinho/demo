/*
 * BlackListStock.java Created on Sep 13, 2005
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


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.shared.document.vo.SharedRangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.BlacklistStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAuditVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.proxy.DocumentTypeProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsPersistenceConstants;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.xibase.server.framework.audit.util.AuditUtils;
import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;
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
 * @author A-1366
 * *
 *  */
 @Table(name="STKBLKLSTRNG")
 @Entity
 //@Staleable

public class BlackListStock {

	 private Log log = LogFactory.getLogger("BLACKLIST STOCK");
    /**
     * BlackListStockPK
     */
    private BlackListStockPK blackListStockPk;
    /**
     * Remarks
     */
    private String remarks;
    /**
     * Stock Holder Code
     */
    private String stockHolderCode;
    /**
     * First Level Stock Holder Code
     */
    private String firstLevelStockHolderCode;
    /**
     * Last updated date
     */
    private Calendar lastUpdateTime;
    /**
     * Last updated user
     */
    private String lastUpdateUser;
    /**
     * blacklistDate
     */
    private Calendar blacklistDate;

    private long asciiStartRange;

    private long asciiEndRange;
    
    private String actionCode;

    /**
     * manual flag
     * Added by A-7373 for ICRD-241944
     */

    private String isManual;

    public BlackListStock(){

    }

    /**
     * @return Returns the blackListStockPk.
     * */
    @EmbeddedId
    @AttributeOverrides({
		@AttributeOverride(name="companyCode", 	column=@Column(name="CMPCOD")),
		@AttributeOverride(name="airlineIdentifier",	column=@Column(name="ARLIDR")),
		@AttributeOverride(name="documentType",	column=@Column(name="DOCTYP")),
		@AttributeOverride(name="documentSubType",	column=@Column(name="DOCSUBTYP")),
		@AttributeOverride(name="startRange", column=@Column(name="STARNG")),
		@AttributeOverride(name="endRange",	column=@Column(name="ENDRNG")),
		@AttributeOverride(name="status",	column=@Column(name="BLKSTA"))
		})
    public BlackListStockPK getBlackListStockPk() {
        return blackListStockPk;
    }
    /**
     * @param blackListStockPk The blackListStockPk to set.
     */
    public void setBlackListStockPk(BlackListStockPK
    		blackListStockPk) {
        this.blackListStockPk = blackListStockPk;
    }
    /**
     * @return Returns the lastUpdateDate.
     *
     */
    @Version
    @Column(name="LSTUPDTIM")
    @Temporal(TemporalType.TIMESTAMP)
    public Calendar getLastUpdateTime() {
        return lastUpdateTime;
    }
    /**
     * @param lastUpdateDate
     * The lastUpdateDate to set.
     * @param lastUpdateTime
     */
    public void setLastUpdateTime(Calendar lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
    /**
     * Method for getting blacklisted date
     * @return blacklistDate
     */
    @Column(name="BLKLSTDAT")
    @Audit(name="blacklistDate")
    @Temporal(TemporalType.DATE)
    public Calendar getBlacklistDate() {
		return blacklistDate;
	}
    /**
     * Method for setting blacklisted date
     * @param blacklistDate
     */
	public void setBlacklistDate(Calendar blacklistDate) {
		this.blacklistDate = blacklistDate;
	}
    /**
     * Returns the lastUpdateUser.
     * @return lastUpdateUser
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
     * @return Returns the remarks.
     *
     */
    @Column(name="BLKLSTRMK")
     @Audit(name="remarks")
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
	 * @return Returns the stockHolderCode.
	 */
    @Column(name="STKHLDCOD")
    @Audit(name="stockholdercode")
	public String getStockHolderCode() {
		return stockHolderCode;
	}
	/**
	 * @param stockHolderCode The stockHolderCode to set.
	 */
	public void setStockHolderCode(String stockHolderCode) {
		this.stockHolderCode = stockHolderCode;
	}
	/**
	 * @return Returns the firstLevelStockHolderCode.
	 */
	@Column(name="FRTLVLSTKHLDCOD")
    @Audit(name="firstlevelstockholdercode")
	public String getFirstLevelStockHolderCode() {
		return firstLevelStockHolderCode;
	}
	/**
	 * @param firstLevelStockHolderCode The firstLevelStockHolderCode to set.
	 */
	public void setFirstLevelStockHolderCode(String firstLevelStockHolderCode) {
		this.firstLevelStockHolderCode = firstLevelStockHolderCode;
	}
	/**
     * getting the asciiEndRange
     * @return asciiEndRange
     */
    @Column(name="ASCENDRNG")
     @Audit(name="asciiEndRange")
    public long getAsciiEndRange() {
		return asciiEndRange;
	}
    /**
     * Setting the asciiEndRange
     * @param asciiEndRange
     */
	public void setAsciiEndRange(long asciiEndRange) {
		this.asciiEndRange = asciiEndRange;
	}
	/**
	 * Getting asciiStartRange
	 * @return asciiStartRange
	 */
	@Column(name="ASCSTARNG")
	 @Audit(name="asciiStartRange")
	public long getAsciiStartRange() {
		return asciiStartRange;
	}
	
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
	 * Setting asciiStartRange
	 * @param asciiStartRange
	 */
	public void setAsciiStartRange(long asciiStartRange) {
		this.asciiStartRange = asciiStartRange;
	}


    /**
     * Constructor
     * @param blacklistStockVO
     * @throws SystemException
     */
    public BlackListStock(BlacklistStockVO blacklistStockVO)
    throws SystemException{
    	log.entering("Constructor","BlacklistStock");
    	validateRangeFormat(blacklistStockVO);
    	populatePK(blacklistStockVO);
    	populateAttributes(blacklistStockVO);
    	try{
    		PersistenceController.getEntityManager().persist(this);
    	}catch(CreateException createException){
    		throw new SystemException(createException.getErrorCode());
    	}
    	log.exiting("Constructor","BlacklistStock");
    }
    /**
     */
/*    public void Operation1() {
    }
*/   /**
    * Finding a particular blacklisted stock
    * @param blacklistStockVO
    * @return
    * @throws SystemException
    */
    public static BlackListStock  find(BlacklistStockVO
    		blacklistStockVO)  throws SystemException{

    	BlackListStock blackListStock=null;
    	BlackListStockPK blackListStockPK = new BlackListStockPK();
    	blackListStockPK.setCompanyCode(blacklistStockVO.
    				getCompanyCode());
    	blackListStockPK.setAirlineIdentifier
    		(blacklistStockVO.getAirlineIdentifier());
    	blackListStockPK.setDocumentType(blacklistStockVO.
    				getDocumentType());
    	blackListStockPK.setDocumentSubType(blacklistStockVO.
    				getDocumentSubType());
    	blackListStockPK.setStartRange(blacklistStockVO.getRangeFrom());
    	blackListStockPK.setEndRange(blacklistStockVO.getRangeTo());
    	blackListStockPK.setStatus(blacklistStockVO.getStatus());
		try{
		EntityManager entityManager = PersistenceController.
					getEntityManager();

		blackListStock=entityManager.find(BlackListStock.class,
					blackListStockPK);
		}
		catch (FinderException finderException) {
			throw new SystemException(finderException.getErrorCode());
		}
		return blackListStock;
    }

    /**
     * Remove method for BlackListStock entity
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
     * Method for revoking a black listed stock
     * @param blacklistStockVO
     * @return
     * @throws SystemException
     */
    public  Collection<RangeVO> revoke(BlacklistStockVO blacklistStockVO)
    throws SystemException{
    	Collection<RangeVO> ranges=new ArrayList<RangeVO>();
    	Collection<RangeVO> resultRange=new ArrayList<RangeVO>();
    	RangeVO rangeVo=new RangeVO();
    	rangeVo.setStartRange(blacklistStockVO.getNewRangeFrom());
    	rangeVo.setEndRange(blacklistStockVO.getNewRangeTo());
    	ranges.add(rangeVo);
    	log.log(Log.FINE,"before calling split range in revoke method");
    	resultRange=splitRanges(ranges);
    	log.log(Log.FINE,"after calling split range in revoke method");
    	try{
    		log.log(Log.FINE,"removing current black list ");
    		PersistenceController.getEntityManager().remove(this);
    		StockAuditVO auditVO = new StockAuditVO(
    				StockAuditVO.AUDIT_PRODUCTNAME,
    				StockAuditVO.AUDIT_MODULENAME,
    				StockAuditVO.AUDIT_ENTITY);
    		auditVO = (StockAuditVO)
			AuditUtils.populateAuditDetails(auditVO,this,
					false);
    		StockController controller = new StockController();
    		controller.auditStock(this,auditVO,
					AuditVO.DELETE_ACTION,"BLACKLIST_STOCK");
    	}catch(RemoveException removeException){
    		throw new SystemException(removeException.getErrorCode());
    	}
    	log.log(Log.FINE,"returning splited ranges from revoke method ");
    	return resultRange;
    }
    /**
     * Method for spliting the ranges
     * @param ranges
     * @return
     * @throws SystemException
     */
    private Collection<RangeVO> splitRanges(Collection<RangeVO> ranges)
    throws SystemException {
    	log.log(Log.FINE, "--------------->>>inside Split ranges ",
				"in blacklist--->>");
		log.log(Log.FINE,this.getBlackListStockPk().getStartRange());
    	log.log(Log.FINE,this.getBlackListStockPk().getEndRange());
    	Collection<SharedRangeVO> rangeInside=new
    						ArrayList<SharedRangeVO>();
    	Collection<SharedRangeVO> rangeOutside=new
    						ArrayList<SharedRangeVO>();
    	Collection<SharedRangeVO> resultRange=new
    						ArrayList<SharedRangeVO>();
    	Collection<RangeVO> result=new ArrayList<RangeVO>();
    	SharedRangeVO sharedRangeVO=new SharedRangeVO();
    	sharedRangeVO.setFromrange(this.getBlackListStockPk().getStartRange());
    	sharedRangeVO.setToRange(this.getBlackListStockPk().getEndRange());
    	rangeInside.add(sharedRangeVO);
    	for(RangeVO rangeVOin : ranges){
    		log.log(Log.FINE,"inside convertion from outside vo ");
    		SharedRangeVO sharedVo = new SharedRangeVO();
    		sharedVo.setFromrange(rangeVOin.getStartRange());
    		sharedVo.setToRange(rangeVOin.getEndRange());
    		rangeOutside.add(sharedVo);
    	}
    	if(rangeInside!=null && rangeOutside!=null){
    		DocumentTypeProxy documentTypeProxy = new
    				DocumentTypeProxy();
    	try{
    		log.log(Log.FINE, " before calling split range ",
					"method from shared   ");
		resultRange=documentTypeProxy.splitRanges(rangeInside,
    			rangeOutside);
    	log.log(Log.FINE, " after calling split range method ",
				"from shared   ");
    	}catch(ProxyException proxyException){
    		for(ErrorVO errorVO : proxyException.getErrors()){
    			throw new SystemException(errorVO.getErrorCode());
    		}
    	}

    	for(SharedRangeVO sharedVo : resultRange){
    		log.log(Log.FINE," converting shared range in to rangeVo");
    		RangeVO rangeVo =new RangeVO();
    		rangeVo.setStartRange(sharedVo.getFromrange());
    		rangeVo.setEndRange(sharedVo.getToRange());
    		result.add(rangeVo);

    	}
    	}

    	log.log(Log.FINE," returning the result   ");
//    	To be reviewed spliting the ranges
    	return result;
    }
    /**
     * This method will find the blacklisted stock matching
     * the filter criteria
     * @param stockFilterVO
     * @param displayPage
     * @return Page<BlacklistStockVO>
     * @throws SystemException
     */
    public static Page<BlacklistStockVO> findBlacklistedStock
    (StockFilterVO stockFilterVO,int displayPage)throws SystemException
    {
    	try {
			return constructDAO().findBlacklistedStock(stockFilterVO,
					displayPage);
		}
		catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
    }
    /**
     * populating the pk for blackliststock
     * @param blacklistStockVO
     */
    private void populatePK(BlacklistStockVO blacklistStockVO){
    	log.log(Log.FINE,"----------populate pk-----  ");
    	log.log(Log.FINE,blacklistStockVO.getCompanyCode());
    	log.log(Log.FINE,blacklistStockVO.getDocumentType());
    	log.log(Log.FINE,blacklistStockVO.getDocumentSubType());
    	log.log(Log.FINE,blacklistStockVO.getRangeFrom());
    	log.log(Log.FINE,blacklistStockVO.getRangeTo());
    	log.log(Log.FINE,blacklistStockVO.getStatus());
    	BlackListStockPK blackListStockPK=new BlackListStockPK();
    	blackListStockPK.setCompanyCode(blacklistStockVO.
    	getCompanyCode());
    	blackListStockPK.setAirlineIdentifier(blacklistStockVO.getAirlineIdentifier());
    	blackListStockPK.setDocumentType(blacklistStockVO.
    	getDocumentType());
    	blackListStockPK.setDocumentSubType(blacklistStockVO.
    	getDocumentSubType());
    	blackListStockPK.setStartRange(blacklistStockVO.getRangeFrom());
    	blackListStockPK.setEndRange(blacklistStockVO.getRangeTo());
    	blackListStockPK.setStatus(blacklistStockVO.getStatus());
    	this.blackListStockPk=blackListStockPK;

    }
    /**
     * popultating other attributes
     * @param blacklistStockVO
     */
    private void populateAttributes(BlacklistStockVO blacklistStockVO)
    {
    	log.log(Log.FINE,"----------populate attributes-----  ");
    	log.log(Log.FINE,blacklistStockVO.getRemarks());
    	log.log(Log.FINE,blacklistStockVO.getLastUpdateUser());
    	this.setRemarks(blacklistStockVO.getRemarks());
    	this.setLastUpdateTime(blacklistStockVO.getLastUpdateTime().
    			toCalendar());
    	this.setLastUpdateUser(blacklistStockVO.getLastUpdateUser());
    	this.setBlacklistDate(blacklistStockVO.getBlacklistDate());
    	this.setAsciiStartRange(Range.findLong(blacklistStockVO.
    			getRangeFrom()));
    	this.setAsciiEndRange(Range.findLong(blacklistStockVO.
    			getRangeTo()));
    	this.setActionCode(blacklistStockVO.getActionCode());
    	//Added by A-3791 for ICRD-110209
    	this.setStockHolderCode(blacklistStockVO.getStockHolderCode());
    	this.setFirstLevelStockHolderCode(blacklistStockVO.getFirstLevelStockHolder());
    	//Added by A-7373 for ICRD-241944
      	if(blacklistStockVO.isManual()){
    		this.setIsManual("Y");
    	}else{
    		this.setIsManual("N");
    	}
    }

    /**
     * method for validating the range format
     * @param blacklistStockVO
     * @throws SystemException
     */
    private void validateRangeFormat(BlacklistStockVO blacklistStockVO)
	throws SystemException{
		log.log(Log.FINE,"--Inside validateRanges in Stock----");
		DocumentVO documentVo=new DocumentVO();
    	Collection<SharedRangeVO> sharedRange=new
    	ArrayList<SharedRangeVO>();
    	//for(RangeVO rangeVo : stockAllocationVO.getRanges()){
    		SharedRangeVO sharedRangeVO = new SharedRangeVO();
    		sharedRangeVO.setFromrange(blacklistStockVO.getRangeFrom());
    		sharedRangeVO.setToRange(blacklistStockVO.getRangeTo());
    		sharedRange.add(sharedRangeVO);
    	//}
    	DocumentTypeProxy documentTypeProxy = new DocumentTypeProxy();
    	documentVo.setCompanyCode(blacklistStockVO.getCompanyCode());
    	documentVo.setDocumentType(blacklistStockVO.getDocumentType());
    	documentVo.setDocumentSubType(blacklistStockVO.
    			getDocumentSubType());

    	documentVo.setRange(sharedRange);
    	try{
			log.log(Log.FINE, "------------before calling documentType",
					"Proxy.validateRanges in Stock--------------");
			documentTypeProxy.validateRanges(documentVo);
    	}catch(ProxyException proxyException){
    		log.log(Log.FINE, "------------ProxyException in ",
					"validateRanges in Stock--------------");
			for(ErrorVO errorVo : proxyException.getErrors()){
    			
    			throw new SystemException(errorVo.getErrorCode());
    		}
    	}
	}

    /**
     * @author A-1883
     * @return StockControlDefaultsSqlDAO
     * @throws SystemException
     * @throws PersistenceException
     */
    private static StockControlDefaultsDAO constructDAO()
	throws SystemException, PersistenceException {
		EntityManager entityManager = PersistenceController.
		getEntityManager();
		return StockControlDefaultsDAO.class.cast
		(entityManager.getQueryDAO
				(StockControlDefaultsPersistenceConstants.MODULE_NAME));
	}
	/**
	 * @param companyCode
	 * @param docType
	 * @param documentNumber
	 * @return
	 * @throws SystemException
	 */
	public static boolean checkForBlacklistedDocument(String companyCode,String docType,String documentNumber)
	    throws SystemException {
		 try {
				return constructDAO().checkForBlacklistedDocument(companyCode,
			   			docType,documentNumber);
			}
			catch (PersistenceException persistenceException) {
				throw new SystemException(persistenceException.getErrorCode());
			}
	 }
	 /**
	  * @author A-1883
	  * @param blacklistStockVO
	  * @return boolean
	  * @throws SystemException
	  */
	 public static boolean alreadyBlackListed(BlacklistStockVO blacklistStockVO)
	    throws SystemException {
		 try {
				return constructDAO().alreadyBlackListed(blacklistStockVO);
			}
			catch (PersistenceException persistenceException) {
				throw new SystemException(persistenceException.getErrorCode());
			}
	 }

	 /**
	  * Method for finding blackListedRanges
	  * @param blacklistStockVO
	  * @return Collection<BlacklistStockVO>
	  * @throws SystemException
	 * @throws PersistenceException
	  */
	 public static Collection<BlacklistStockVO> findBlackListRangesForRevoke(BlacklistStockVO
			 blacklistStockVO) throws SystemException{
		 try{
			 return constructDAO().findBlackListRangesForRevoke(blacklistStockVO.getCompanyCode(),
					 blacklistStockVO.getAirlineIdentifier(),
					 blacklistStockVO.getDocumentType(),blacklistStockVO.
					 getDocumentSubType(), blacklistStockVO.getRangeFrom(),
					 blacklistStockVO.getRangeTo());
		 }catch (PersistenceException persistenceException) {
				throw new SystemException(persistenceException.getErrorCode());
			}

	 }

 	/**
	  * Method for finding blackListedRanges
	  * @param blacklistStockVO
	  * @return Collection<BlacklistStockVO>
	  * @throws SystemException
	 * @throws PersistenceException
	  */
	 public static Collection<BlacklistStockVO> findRangesToBeDeleted(BlacklistStockVO blacklistStockVO)throws SystemException{
	 		 try{
	 			 return constructDAO().findRangesToBeDeleted(blacklistStockVO.getCompanyCode(),
	 					 blacklistStockVO.getAirlineIdentifier(),
	 					 blacklistStockVO.getDocumentType(),blacklistStockVO.
	 					 getDocumentSubType(), blacklistStockVO.getRangeFrom(),
	 					 blacklistStockVO.getRangeTo());
	 		 }catch (PersistenceException persistenceException) {
	 				throw new SystemException(persistenceException.getErrorCode());
	 			}

	 }

	/**
	 * @return the actionCode
	 */
	@Column(name="ACTCOD")
	public String getActionCode() {
		return actionCode;
	}

	/**
	 * @param actionCode the actionCode to set
	 */
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

}
