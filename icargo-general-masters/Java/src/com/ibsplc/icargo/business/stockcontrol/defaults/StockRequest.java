/*
 * StockRequest.java Created on Jul 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
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

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsPersistenceConstants;
import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsSqlDAO;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *  Represents a single stock requested by a stockholder against
 *  document type. Every request is routed to the approver of the
 *  respective document type for that stock holder. Information
 *  regarding the life cycle of the stock request is available in
 *  this class. Hence it contains the approved quantity rejection
 *  remarks etc
 *  @author A-1358
 *
 */
@Table(name="STKREQMST")
@Entity
//@Staleable
public class StockRequest {

	private Log log = LogFactory.getLogger("STOCK REQUEST");
	/**
	 * StockRequestPK
	 */
	private StockRequestPK stockRequestPk;
	/**
	 * Requested Date
	 */

	private Calendar requestDate;
	/**
	 * Document type
	 */
	private String documentType;
	/**
	 * Document sub type
	 */
	private String documentSubType;
	/**
	 * Flag indicating whether stock is manual or not.
	 * Possible values are 'Y' and 'N'
	 */
	private String isManual;
	/**
	 * Status of stock request.Possible values are
	 * N - New
	 * A - Approved
	 * R - Rejected
	 * L - Allocated
	 * M - Completed
	 * C - Cancelled
	 */
	private String status;
	/**
	 * Quantity of requested stock
	 */
	private long requestedStock;
	/**
	 * Quantity of approved stock
	 */
	private long approvedStock;
	/**
	 * Quantity of allocated stock
	 */
	private long allocatedStock;
	/**
	 * Remarks
	 */
	private String remarks;
	/**
	 * Remarks of the approval
	 */
	private String approvalRemarks;
	/**
	 * Last updated date
	 */
	private Calendar lastUpdateDate;
	/**
	 * Last updated user
	 */
	private String lastUpdateUser;
	/**
	 * Default Contructor
	 *
	 */
	/**
	 * Stock holder code
	 */
	private String stockHolderCode;
	/**
	 * requestCreatedBy
	 */
	private String requestCreatedBy;
	/**
	 * Default Constructor
	 */
	public StockRequest(){

	}
	/**
	 * @return Returns the allocatedStock.
	 *
	 */
	@Column(name="ALCSTKQTY")
	@Audit(name="AllocatedStock")
	public long getAllocatedStock() {
		return allocatedStock;
	}
	/**
	 * @param allocatedStock The allocatedStock to set.
	 */
	public void setAllocatedStock(long allocatedStock) {
		this.allocatedStock = allocatedStock;
	}
	/**
	 * @return Returns the approvalRemarks.
	 *
	 */
	@Column(name="APRREJREM")
	@Audit(name="approvalRemarks")
	public String getApprovalRemarks() {
		return approvalRemarks;
	}
	/**
	 * @param approvalRemarks The approvalRemarks to set.
	 */
	public void setApprovalRemarks(String approvalRemarks) {
		this.approvalRemarks = approvalRemarks;
	}
	/**
	 * @return Returns the documentSubType.
	 *
	 */
	@Column(name="DOCSUBTYP")
	@Audit(name="documentSubType")
	public String getDocumentSubType() {
		return documentSubType;
	}
	/**
	 * @param documentSubType The documentSubType to set.
	 */
	public void setDocumentSubType(String documentSubType) {
		this.documentSubType = documentSubType;
	}
	/**
	 * @return Returns the documentType.
	 */
	@Column(name="DOCTYP")
	@Audit(name="documentType")
	public String getDocumentType() {
		return documentType;
	}
	/**
	 * @param documentType The documentType to set.
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	/**
	 * @return Returns the isManual.
	 *
	 */
	@Column(name="MNLSTKFLG")
	@Audit(name="isManual")
	public String getIsManual() {
		return isManual;
	}
	/**
	 * @param isManual The isManual to set.
	 */
	public void setIsManual(String isManual) {
		this.isManual = isManual;
	}
	/**
	 * @return Returns the lastUpdateDate.
	 *
	 */
	@Version
	@Column(name="LSTUPDDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateDate() {
		return lastUpdateDate;
	}
	/**
	 * @param lastUpdateDate The lastUpdateDate to set.
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
	 * @param lastUpdateUser The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	/**
	 * @return Returns the remarks.
	 *
	 */
	@Column(name="REQRMK")
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
	 * @return Returns the requestDate.
	 *
	 */
	@Audit(name="requestDate")
	@Column(name="REQDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getRequestDate() {
		return requestDate;
	}
	/**
	 * @param requestDate The requestDate to set.
	 */
	public void setRequestDate(Calendar requestDate) {
		this.requestDate = requestDate;
	}
	/**
	 * @return Returns the requestedStock.
	 *
	 */
	@Column(name="REQSTKQTY")
	@Audit(name="RequestedStock")
	public long getRequestedStock() {
		return requestedStock;
	}
	/**
	 * @param requestedStock The requestedStock to set.
	 */
	public void setRequestedStock(long requestedStock) {
		this.requestedStock = requestedStock;
	}
	/**
	 * @return Returns the status.
	 *
	 */
	@Audit(name="status")
	@Column(name="REQSTA")
	public String getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return Returns the stockRequestPk.
	 *
	 */
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="requestRefNumber", column=@Column(name="REQREFNUM")),
		@AttributeOverride(name="airlineIdentifier", column=@Column(name="ARLIDR"))}
	)
	public StockRequestPK getStockRequestPk() {
		return stockRequestPk;
	}
	/**
	 * @param stockRequestPk The stockRequestPk to set.
	 */
	public void setStockRequestPk(StockRequestPK stockRequestPk) {
		this.stockRequestPk = stockRequestPk;
	}
	/**
	 * @return Returns the approvedStock.
	 *
	 */
	@Column(name="APRSTKQTY")
	@Audit(name="ApprovedStock")
	public long getApprovedStock() {
		return approvedStock;
	}
	/**
	 * @param approvedStock The approvedStock to set.
	 */
	public void setApprovedStock(long approvedStock) {
		this.approvedStock = approvedStock;
	}
	/**
	 * @return Returns the stockHolderCode.
	 */
	@Audit(name="stockHolderCode")
	@Column(name="STKHLDCOD")
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
	 * 
	 * 	Method		:	StockRequest.setRequestCreatedBy
	 *	Added by 	:	A-5258 on Aug 23, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param requestCreatedBy 
	 *	Return type	: 	void
	 */
	public void setRequestCreatedBy(String requestCreatedBy) {
		this.requestCreatedBy = requestCreatedBy;
	}
	/**
	 * 
	 * 	Method		:	StockRequest.getRequestCreatedBy
	 *	Added by 	:	A-5258 on Aug 23, 2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	@Column(name="REQCRTUSR")
	public String getRequestCreatedBy() {
		return requestCreatedBy;
	}
	/**
	 * @author A-1883
	 * Contructor
	 * @param stockRequestVO
	 * @throws SystemException
	 */
	public StockRequest(StockRequestVO stockRequestVO)throws SystemException {
			log.entering("StockRequest","*********************StockRequest Constructor******************");
		populatePk(stockRequestVO);
		populateAttributes(stockRequestVO);
		try{
			PersistenceController.getEntityManager().persist(this);
		}
		catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());
		}
		log.exiting("StockRequest","*********************StockRequest Constructor******************");
	}
	/**
	 * @author A-1883
	 * This method will update the StockRequest details
	 * @param stockRequestVO
	 * @throws SystemException
	 */
	public void update(StockRequestVO stockRequestVO) throws SystemException{
		log.entering("StockRequest","---------update---------");
		populateAttributes(stockRequestVO);
		log.exiting("StockRequest","---------update---------");
	}

	/**
	 * This method is used to remove the business object StockRequest
	 * @throws SystemException
	 */
	public void remove() throws SystemException{

	}

	/**
	 * @author A-1883
	 * This method is used to find a Stock Request using PK
	 * @param companyCode
	 * @param requestRefNumber
	 * @param airlineIdentifier 
	 * @return StockRequestVO
	 * @throws SystemException
	 */
	public static StockRequest find(String companyCode, String requestRefNumber, String airlineIdentifier)
	throws SystemException{
		StockRequest stockRequest=null;
		StockRequestPK stockRequestPK = new StockRequestPK();
		stockRequestPK.setCompanyCode(companyCode);
		stockRequestPK.setRequestRefNumber(requestRefNumber);
		stockRequestPK.setAirlineIdentifier(Integer.parseInt(airlineIdentifier));
		EntityManager entityManager = PersistenceController.getEntityManager();
		try{
			stockRequest=entityManager.find(StockRequest.class,stockRequestPK);
		}
		catch (FinderException finderException) {
			throw new SystemException(finderException.getErrorCode());
		}
		return stockRequest;
	}

	/**
	 * This method is used to find the stock requests matching the specified criteria
	 * @param stockRequestFilterVO
	 * @param displayPage
	 * @return Page<StockRequestVO>
	 * @throws SystemException
	 */
	public static Page<StockRequestVO> findStockRequests(StockRequestFilterVO stockRequestFilterVO, int displayPage)
	throws SystemException{
		try {
			return constructDAO().findStockRequests(stockRequestFilterVO,displayPage);
		}
		catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * @author A-1883
	 * This method is used to find the details of a particular stock request
	 * @param stockRequestFilterVO
	 * @return StockRequestVO
	 * @throws SystemException
	 */
	public static StockRequestVO findStockRequestDetails(StockRequestFilterVO stockRequestFilterVO)
	throws SystemException{
		try {
			return constructDAO().findStockRequestDetails(stockRequestFilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * This method will cancel a particular stock request
	 * @param stockRequestVO
	 * @throws SystemException
	 */
	public void cancelStockRequest(StockRequestVO stockRequestVO)
	throws SystemException{
		this.setStatus(StockRequestVO.STATUS_CANCEL);
		this.setLastUpdateDate(stockRequestVO.getLastUpdateDate());
		this.setLastUpdateUser(stockRequestVO.getLastUpdateUser());
	}
	/**
	 * This method will fetch the stock requests for approval
	 * Stock requests having status other than Completed, rejected are retrieved
	 * @param stockRequestFilterVO
	 * @return Collection<StockRequestVO>
	 * @throws SystemException
	 */
	public static Collection<StockRequestVO> findStockRequestsForApproval
	(StockRequestFilterVO stockRequestFilterVO)
	throws SystemException{
		return null;
	}
	/**
	 * This method is used to approve a particular stock request
	 * @param stockRequestVO
	 * @throws SystemException
	 */
	public void approveStockRequest(StockRequestVO stockRequestVO) throws SystemException{
		long approved = stockRequestVO.getApprovedStock();
		long allocated = stockRequestVO.getAllocatedStock();
		long requested = stockRequestVO.getRequestedStock();
		updateStatus(requested,approved,allocated);
		setApprovedStock(stockRequestVO.getApprovedStock());
		setApprovalRemarks(stockRequestVO.getApprovalRemarks());
		//if(stockRequestVO.getLastUpdateDate()!=null){
			//this.setLastUpdateDate(stockRequestVO.getLastUpdateDate());
		//}
		//else{
			//this.setLastUpdateDate(this.getLastUpdateDate());
		//}
		//this.setLastUpdateUser(stockRequestVO.getLastUpdateUser());
	}

	/**
	 * This method is used to update stock request status and allocated stock quantity
	 * @param allocated
	 * @throws SystemException
	 */
	public void updateStockRequest(long allocated) throws SystemException{
		long qty = this.getAllocatedStock();
		setAllocatedStock(qty+allocated);
		updateStatus(this.getRequestedStock(),this.getApprovedStock(),this.getAllocatedStock());
	}

	/**
	 * This method is used to update stock request status based on requested,approved and allocated stock
	 * @param requested
	 * @param approved
	 * @param allocated
	 * @throws SystemException
	 */
	private void updateStatus(long requested,long approved,long allocated){
		if(approved == 0 && allocated == 0){
			setStatus(StockRequestVO.STATUS_NEW);
		}
		else if(approved == allocated) {
			if( approved >= requested ){
				setStatus(StockRequestVO.STATUS_COMPLETED);
			}
			else {
				setStatus(StockRequestVO.STATUS_ALLOCATED);
			}
		}
		else {
			setStatus(StockRequestVO.STATUS_APPROVED);
		}

	}

	/**
	 * This method is used to reject a particular stock request
	 * @param remarks
	 * @return
	 * @throws SystemException
	 */
	public void rejectStockRequest(String remarks) throws SystemException {
		this.setStatus(StockRequestVO.STATUS_REJECTED);
		this.setApprovalRemarks(remarks);
	}
	/**
	 * This method is used to Complete a particular stock request
	 * @throws SystemException
	 */
	public void completeStockRequests(StockRequestVO stockRequestVO) throws SystemException {
		this.setStatus(StockRequestVO.STATUS_COMPLETED);
		this.setLastUpdateDate(stockRequestVO.getLastUpdateDate());
		this.setLastUpdateUser(stockRequestVO.getLastUpdateUser());
	}
	/**
	 * Methid for setting attributes
	 * @author A-1883
	 * @param stockRequestVO
	 * @throws SystemException
	 */
	private void populateAttributes(StockRequestVO stockRequestVO)throws SystemException {
		log.entering("StockRequest","---------populateAttributes---------");
		log.log(Log.FINE, "stockRequestVO", stockRequestVO);
		setAllocatedStock(stockRequestVO.getAllocatedStock());
		setApprovalRemarks(stockRequestVO.getApprovalRemarks());
		setApprovedStock(stockRequestVO.getApprovedStock());
		setDocumentSubType(stockRequestVO.getDocumentSubType());
		setDocumentType(stockRequestVO.getDocumentType());
		setIsManual(stockRequestVO.isManual() ? StockRequestVO.FLAG_YES :StockRequestVO.FLAG_NO);
		setLastUpdateDate(stockRequestVO.getLastUpdateDate().toCalendar());
		setLastUpdateUser(stockRequestVO.getLastUpdateUser());
		setRemarks(stockRequestVO.getRemarks());
		setRequestDate(stockRequestVO.getRequestDate());
		setRequestedStock(stockRequestVO.getRequestedStock());
		setStatus(stockRequestVO.getStatus());
		setRequestedStock(stockRequestVO.getRequestedStock());
		setStockHolderCode(stockRequestVO.getStockHolderCode());
		setRequestCreatedBy(stockRequestVO.getRequestCreatedBy());
		log.exiting("StockRequest","---------populateAttributes---------");
	}
	/**
	 * Method for populating StockRequestPK
	 * @author A-1883
	 * @param stockRequestVO
	 * @throws SystemException
	 */
	private void populatePk(StockRequestVO stockRequestVO)throws SystemException {
		log.entering("StockRequest","---------populatePk---------");
		StockRequestPK stockRequestPK=new StockRequestPK();
		stockRequestPK.setCompanyCode(stockRequestVO.getCompanyCode());
		stockRequestPK.setAirlineIdentifier(Integer.parseInt(stockRequestVO.getAirlineIdentifier()));
		//condition added for ICRD-4259 BY A-5117 
		if(stockRequestVO.getRequestRefNumber()==null || ("").equals(stockRequestVO.getRequestRefNumber().trim())){
		Criterion criterion = KeyUtils.getCriterion(stockRequestVO.getCompanyCode(),
				"REQUEST_REFERENCE_NUMBER",stockRequestVO.getStockHolderCode());
		criterion.setStartAt("10000");
		stockRequestPK.setRequestRefNumber(stockRequestVO.getStockHolderCode()+KeyUtils.getKey(criterion));
		}else{
		stockRequestPK.setRequestRefNumber(stockRequestVO.getStockHolderCode()+stockRequestVO.getRequestRefNumber());
		}
		
		this.stockRequestPk=stockRequestPK;
		log.exiting("StockRequest","---------populatePk---------");
	}
	/**
	 * @author A-1883
	 * @return StockControlDefaultsSqlDAO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	private static StockControlDefaultsSqlDAO constructDAO()
	throws SystemException, PersistenceException {
		EntityManager entityManager = PersistenceController.getEntityManager();
		return StockControlDefaultsSqlDAO.class.cast
		(entityManager.getQueryDAO(StockControlDefaultsPersistenceConstants.MODULE_NAME));
	}

}
