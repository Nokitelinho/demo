/*
 * StockControlDefaultsBI.java Created on Jul 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReserveAWBVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.*;
import com.ibsplc.icargo.framework.report.exception.ReportGenerationException;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.xibase.server.framework.audit.AuditException;
import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interfaces.BusinessInterface;
import com.ibsplc.xibase.server.framework.persistence.entity.AvoidForcedStaleDataChecks;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.requestprocessor.eblflow.Flow;
import com.ibsplc.xibase.server.framework.requestprocessor.eblflow.FlowService;

/**
 * Business Interface for stockcontrol module. This interface is realized by the
 * service layer
 *
 * @author A-1358
 *
 */
public interface StockControlDefaultsBI extends BusinessInterface, FlowService{

	//Added by A-5133 for bug ICRD-16709 starts
	public void createHistory(StockAllocationVO stockAllocationVo,String status)
	throws RemoteException, SystemException;
	//Added by A-5133 for bug ICRD-16709 end
	

	/**
	 * This method is used to validate the stock holder codes. Used by
	 * saveStockHolder method to validate the approver codes
	 *
	 * @param companyCode
	 * @param stockHolderCodes
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException
	 */
	 Page<StockDetailsVO> listStockDetails(StockDetailsFilterVO stockDetailsFilterVO) 
	throws RemoteException,	SystemException;
	
	void validateStockHolders(String companyCode,
			Collection<String> stockHolderCodes) throws RemoteException,
			SystemException, StockControlDefaultsBusinessException;

	/**
	 * This method is used to create/modify stockholder details. For create
	 * operationFlag is set as I and for modify, operationFlag is set as 'U'
	 *
	 * @param stockHolderVO
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException
	 * @throws RangeExistsException
	 */
	void saveStockHolderDetails(StockHolderVO stockHolderVO)
			throws RemoteException, SystemException, RangeExistsException,
			StockControlDefaultsBusinessException;

	/**
	 * This method is used to delete a stockholder. deletion is possible only if
	 * no stock exists under the stock holder dirctly.
	 *
	 * @param stockHolderdetailsVO
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws RangeExistsException
	 * @throws CancelStockHolderException
	 * @throws StockControlDefaultsBusinessException
	 */
	void deleteStockHolder(StockHolderDetailsVO stockHolderdetailsVO)
			throws RemoteException, SystemException, RangeExistsException,CancelStockHolderException,
			StockControlDefaultsBusinessException;

	/**
	 * Used to list the details of a selected stockholder
	 *
	 * @param companyCode
	 * @param stockHolderCode
	 * @return StockHolderVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	StockHolderVO findStockHolderDetails(String companyCode,
			String stockHolderCode) throws RemoteException, SystemException;

	/**
	 * This method finds details for stock holders which meet the filter
	 * criteria and all stock holders who fall under them.
	 *
	 * @param filterVO
	 * @return Collection
	 * @throws RemoteException
	 * @throws SystemException
	 */
	// Collection<StockHolderDetailsVO>
	Page<StockHolderDetailsVO> findStockHolders(
			StockHolderFilterVO filterVO) throws RemoteException,
			SystemException;

	/**
	 * This method is invoked to obtain the stock holder lov
	 *
	 * @param filterVO
	 * @param displayPage
	 * @return page
	 * @throws RemoteException
	 * @throws SystemException
	 */
	Page<StockHolderLovVO> findStockHolderLov(StockHolderLovFilterVO filterVO, int displayPage)
			throws RemoteException, SystemException;

	/**
	 * This method is used to create/modify/cancel stock request details based
	 * on operational flag
	 *
	 * @param stockRequestVO
	 * @return String
	 * @throws RemoteException
	 * @throws SystemException
	 */
	String saveStockRequestDetails(StockRequestVO stockRequestVO)
			throws RemoteException, SystemException;

	/**
	 * This method will fetch the details of a particular stock request
	 *
	 * @param stockRequestFilterVO
	 * @return StockRequestVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	StockRequestVO findStockRequestDetails(
			StockRequestFilterVO stockRequestFilterVO) throws RemoteException,
			SystemException;

	/**
	 * This method will cancel a particular stock request
	 *
	 * @param stockRequestVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	void cancelStockRequest(StockRequestVO stockRequestVO)
			throws RemoteException, SystemException;

	/**
	 * This method will fetch the stock requests satisfying the filter criteria
	 *
	 * @param stockRequestFilterVO
	 * @param displaypage
	 * @return Page<StockRequestVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	Page<StockRequestVO> findStockRequests(
			StockRequestFilterVO stockRequestFilterVO, int displaypage)
			throws RemoteException, SystemException;

	

	/**
	 * This method is used to approve stock requests
	 *
	 * @author A-1883
	 * @param stockRequestApproveVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	@AvoidForcedStaleDataChecks
	void approveStockRequests(StockRequestApproveVO stockRequestApproveVO)
			throws RemoteException, SystemException,
			StockControlDefaultsBusinessException;

	/**
	 * This method is used to reject stock requests
	 *
	 * @param stockRequests
	 * @throws RemoteException
	 * @throws SystemException
	 */
	void rejectStockRequests(Collection<StockRequestVO> stockRequests)
			throws RemoteException, SystemException;

	/**
	 * This method is used to Complete stock requests
	 *
	 * @param stockRequests
	 * @throws RemoteException
	 * @throws SystemException
	 */
	void completeStockRequests(Collection<StockRequestVO> stockRequests)
			throws RemoteException, SystemException;

	/**
	 * This method will implement the audit method in Auditor. It will calls the
	 * audit method of the audit controller.
	 *
	 * @param auditVo
	 * @throws AuditException
	 * @throws RemoteException
	 * @throws SystemException
	 */
	void audit(AuditVO auditVo) throws AuditException, RemoteException,
			SystemException;

	/**
	 * This method is used to fetch the stockholder types sorted based on
	 * prioity
	 *
	 * @param companyCode
	 * @return Collection<StockHolderPriorityVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	Collection<StockHolderPriorityVO> findStockHolderTypes(String companyCode)
			throws RemoteException, SystemException;

	/**
	 * This method will validate stockholdertype and code
	 *
	 * @param stockRequestVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	void validateStockHolderTypeCode(StockRequestVO stockRequestVO)
			throws RemoteException, SystemException,
			StockControlDefaultsBusinessException;

	/**
	 * @author A-1883
	 * @param rangeFilterVO
	 * @return Collection<RangeVO>
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	Collection<RangeVO> findRanges(RangeFilterVO rangeFilterVO)
			throws SystemException, RemoteException,
			StockControlDefaultsBusinessException;

	/**
	 *
	 * @param stockFilterVO
	 * @return StockRangeVO
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException
	 * @throws RemoteException
	 */
	StockRangeVO viewRange(StockFilterVO stockFilterVO) throws SystemException,
			StockControlDefaultsBusinessException, RemoteException;

	/**
	 * @author A-1944
	 * @param stockFilterVO
	 * @return Collection<MonitorStockVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	Page<MonitorStockVO> monitorStock(StockFilterVO stockFilterVO)
			throws RemoteException, SystemException,
			StockControlDefaultsBusinessException;

	/**
	 * @author A-1944
	 * @param stockFilterVO
	 * @return Collection<MonitorStockVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	MonitorStockVO findMonitoringStockHolderDetails(StockFilterVO stockFilterVO)
			throws RemoteException, SystemException,
			StockControlDefaultsBusinessException;
	/**
	 * This method is used to find the stock holder codes for a collection of
	 * stock control privileges
	 *
	 * @param companyCode
	 * @param privileges
	 * @return Collection<String>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	Collection<String> findStockHolderCodes(String companyCode,
			Collection<String> privileges) throws RemoteException,
			SystemException;

	/**
	 * This method will find the blacklisted stock matching the filter criteria
	 *
	 * @param stockFilterVO
	 * @param displayPage
	 * @return Page<BlacklistStockVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	Page<BlacklistStockVO> findBlacklistedStock(StockFilterVO stockFilterVO,
			int displayPage) throws RemoteException, SystemException;

	
	/**
	 * Allocating stock with ranges
	 *
	 * @param stockAllocationVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	StockAllocationVO allocateStock(StockAllocationVO stockAllocationVO)
			throws SystemException, RemoteException,
			StockControlDefaultsBusinessException;

	/**
	 * Method for black listing a stock
	 *
	 * @param blacklistStockVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	
	void blacklistStock(BlacklistStockVO blacklistStockVO)
			throws SystemException, RemoteException,
			StockControlDefaultsBusinessException;

	/**
	 * Revoking blacklisted stock
	 *
	 * @param blacklistStockVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	void revokeBlacklistStock(BlacklistStockVO blacklistStockVO)
			throws SystemException, RemoteException,
			StockControlDefaultsBusinessException;

	/**
	 * @param companyCode
	 * @param stockHolderCodes
	 * @return Collection<StockHolderPriorityVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Collection<StockHolderPriorityVO> findPriorities(String companyCode,
			Collection<String> stockHolderCodes) throws SystemException,
			RemoteException;

	/**
	 * @param companyCode
	 * @param stockHolderCode
	 * @param docType
	 * @param docSubType
	 * @return String
	 * @throws SystemException
	 * @throws RemoteException
	 */
	String findApproverCode(String companyCode, String stockHolderCode,
			String docType, String docSubType) throws SystemException,
			RemoteException;

	/**
	 * @param companyCode
	 * @param stockHolderCode
	 * @param docType
	 * @param docSubType
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	void checkStock(String companyCode, String stockHolderCode, String docType,
			String docSubType) throws SystemException, RemoteException,
			StockControlDefaultsBusinessException;


	/**
	 * @author A-1883
	 * @param companyCode
	 * @param doctype
	 * @param documentNumber
	 * @return Boolean
	 * @throws RemoteException
	 * @throws SystemException
	 */
	Boolean checkForBlacklistedDocument(String companyCode, String doctype,
			String documentNumber) throws RemoteException, SystemException;

	// *************************************************************************
	// ************************ R2 coding starts *******************************
	// *************************************************************************

	/*
	 * Added by Sinoob
	 */
	/**
	 * @param stockFilterVO
	 * @return StockAllocationVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	StockAllocationVO findStockForAirline(StockFilterVO stockFilterVO)
			throws SystemException, RemoteException;

    /**
     * @param reserveAWBVO
     * @return
     * @throws SystemException
     * @throws RemoteException
     */
    ReserveAWBVO reserveDocument(ReserveAWBVO reserveAWBVO)
    		throws SystemException, RemoteException, StockControlDefaultsBusinessException;

	// Added by Sinoob ends
	/**
	 * @author A-1863
	 * @param stockFilterVO
	 * @return Page
	 * @throws RemoteException
	 * @throws SystemException
	 */
	Page<StockVO> findStockList(StockFilterVO stockFilterVO) throws RemoteException,
			SystemException;

	/**
	 * @author A-1863
	 * @param stockRequestForOALVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	void saveStockRequestForOAL(StockRequestForOALVO stockRequestForOALVO)
			throws SystemException, RemoteException;

	/**
	 * @author A-1863
	 * @param stockFilterVO
	 * @return Collection<StockRequestForOALVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Collection<StockRequestForOALVO> findStockRequestForOAL(
			StockFilterVO stockFilterVO) throws SystemException,
			RemoteException;

	/**
	 * @param reservationFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Collection<ReservationVO> findReservationListing(
			ReservationFilterVO reservationFilterVO) throws SystemException,
			RemoteException;

	/**
	 * @param reservationVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	void cancelReservation(Collection<ReservationVO> reservationVO)
			throws SystemException, RemoteException, StockControlDefaultsBusinessException;

	/**
	 * @param reservationVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	void extendReservation(Collection<ReservationVO> reservationVO)
			throws SystemException, RemoteException, StockControlDefaultsBusinessException;

	/**
	 * @param reservationFilterVO
	 * @throws StockControlDefaultsBusinessException
	 * @throws SystemException
	 */
	void expireReservations(ReservationFilterVO reservationFilterVO)
		throws StockControlDefaultsBusinessException, SystemException, RemoteException;

	/**
	 * This method will implement the audit method in Auditor.
	 * It will calls the audit method of the audit controller.
	 *
	 * @param auditVos
	 * @throws AuditException
	 * @throws RemoteException
	 */
	void audit(Collection<AuditVO> auditVos) throws AuditException, RemoteException;

	 /**
	  * Method to find document details
	  * @param companyCode
	  * @param airlineIdentifier
	  * @param documentNumber
	  * @return
	  * @throws RemoteException
	  * @throws SystemException
	  */
	 StockRequestVO findDocumentDetails(String companyCode, int airlineIdentifier,
			String documentNumber) throws RemoteException, SystemException;
	 /**
	  * @author a-1885
	  * @param filterVo
	  * @throws SystemException
	  * @throws RemoteException
	  */
	 void sendReorderMessages(InformAgentFilterVO filterVo)throws
	    SystemException,RemoteException;

	 /*
	  * added by Sinoob T S
	  */
	 /**
	 * @param stockAgentVOs
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	void saveStockAgentMappings(Collection<StockAgentVO> stockAgentVOs)
	 	throws SystemException, RemoteException,
	 			StockControlDefaultsBusinessException;

	/**
	 * @param stockAgentFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Page<StockAgentVO> findStockAgentMappings(
			StockAgentFilterVO stockAgentFilterVO)
		throws SystemException, RemoteException;

	/**
	 * @param documentFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	DocumentValidationVO validateDocument(DocumentFilterVO documentFilterVO)
			throws SystemException, RemoteException, StockControlDefaultsBusinessException;

	/**
	 * @param filterVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	void deleteDocumentFromStock(DocumentFilterVO filterVO)
		throws SystemException, RemoteException, StockControlDefaultsBusinessException;

	/**
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	DocumentValidationVO findNextDocumentNumber(DocumentFilterVO filterVO)
			throws SystemException, RemoteException, StockControlDefaultsBusinessException;
	 /*
	  * added by Sinoob T S ends
	  */
	/**
	 *
	 */
	void autoStockDeplete(StockDepleteFilterVO stockDepleteFilterVO)
	throws SystemException,RemoteException, StockControlDefaultsBusinessException;

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws ReportGenerationException
	 * @throws RemoteException
	 */
	Map<String, Object> generateReserveAWBReport(ReportSpec reportSpec)
	throws SystemException, ReportGenerationException,RemoteException;

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws ReportGenerationException
	 * @throws RemoteException
	 */
	Map<String, Object> generateReservationListReport(ReportSpec reportSpec)
	throws SystemException, ReportGenerationException,RemoteException;

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws ReportGenerationException
	 * @throws RemoteException
	 */
	Map<String, Object> generateStockListReport(ReportSpec reportSpec)
	throws SystemException, ReportGenerationException,RemoteException ;

	/**
	 * @param stockAllocationVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	void returnDocumentToStock(StockAllocationVO stockAllocationVO)
		throws SystemException, RemoteException;


	/**
	 * @author a-2870
	 * @param documentValidationVO
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException
	 */
    void validateAgentForStockHolder(AWBDocumentValidationVO documentValidationVO)
	throws SystemException,RemoteException,StockControlDefaultsBusinessException;



	/**
 	 * @param reportSpec
 	 * @return
 	 * @throws SystemException
 	 * @throws ReportGenerationException
 	 * @throws RemoteException
 	 */
 	Map<String, Object> generateAWBStockReport(ReportSpec reportSpec)
 	throws SystemException, ReportGenerationException,RemoteException;
 	
 	/**@author a-3184
     * @param stockDetailsFilterVO    
     * @throws SystemException
     * @throws RemoteException
     * @throws StockControlDefaultsBusinessException 
     */
    StockDetailsVO findCustomerStockDetails(StockDetailsFilterVO stockDetailsFilterVO)
	throws SystemException, RemoteException,StockControlDefaultsBusinessException;
    
    /**@author a-4421
     * @param String  
     * @throws SystemException
     * @throws RemoteException
     * @throws StockControlDefaultsBusinessException 
     */
    void updateStockUtilization(String source)
	throws SystemException,RemoteException;

  
 	 /**
 	 * Method for listing stock history
 	 * 
 	 * @param stockRangeFilterVO 
 	 * @throws SystemException
 	 * @throws RemoteException
 	 * @throws StockControlDefaultsBusinessException
 	 */
     Collection<StockRangeHistoryVO> findStockRangeHistory(
    		 StockRangeFilterVO stockRangeFilterVO)
 			throws RemoteException, SystemException;
     
     /**
 	 * @author a-2870
 	 * @param reportSpec
 	 * @return
 	 * @throws RemoteException
 	 * @throws SystemException
 	 */
 	public Map<String, Object> findStockRangeHistoryReport(ReportSpec reportSpec)
 			throws RemoteException, SystemException;
	
 	
 	      
	 /**
 	 * Method for listing transit stock details
 	 * @author A-4443
 	 * @param stockRequestFilterVO 
 	 * @throws SystemException
 	 * @throws RemoteException
 	 * @throws StockControlDefaultsBusinessException
 	 */
	public Collection<TransitStockVO> findTransitStocks(
    		 StockRequestFilterVO stockRequestFilterVO)
 			throws RemoteException, SystemException;
    
	 /**
 	 * Method for confirmation of transit stocks
 	 * @author A-4443
 	 * @param TransitStockVO 
 	 * @throws SystemException
 	 * @throws RemoteException
 	 * @throws StockControlDefaultsBusinessException
 	 */
	public Collection<TransitStockVO> confirmStockRange(
			Collection<TransitStockVO> transitStockVOs)
 			throws RemoteException, SystemException;
	/**
 	 * Method for update missing stock of transit stocks
 	 * @author A-4443
 	 * @param TransitStockVO 
 	 * @throws SystemException
 	 * @throws RemoteException
 	 * @throws StockControlDefaultsBusinessException
 	 */
	public Collection<TransitStockVO> updateMissingStock(
			TransitStockVO transitStockVO)
 			throws RemoteException, SystemException;
	
	/**
	 * @author A-2881
	 * @param blacklistStockVO
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException
	 * @throws InvalidStockHolderException
	 * @throws StockNotFoundException
	 * @throws BlacklistedRangeExistsException
	 */
	public void voidStock(BlacklistStockVO blacklistStockVO)
	throws SystemException,RemoteException,StockControlDefaultsBusinessException,
	InvalidStockHolderException, StockNotFoundException,
	BlacklistedRangeExistsException;
	
	/**
	 * @author A-2881
	 * @param blacklistStockVO
	 * @return
	 * @throws StockControlDefaultsBusinessException
	 * @throws SystemException
	 */
	public BlacklistStockVO validateStockForVoiding(
			BlacklistStockVO blacklistStockVO)
	throws  SystemException,RemoteException,StockControlDefaultsBusinessException;
	/**
	 * @author A-5103
	 * @param stockRangeHistoryVOs
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void saveStockRangeHistory(
			Collection<StockRangeHistoryVO> stockRangeHistoryVOs) throws  SystemException, RemoteException ;
	
	/**
	 * @author A-2881
	 * @param stockRangeFilterVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	Page<StockRangeHistoryVO> findStockRangeHistoryForPage(
   		 StockRangeFilterVO stockRangeFilterVO)
			throws RemoteException, SystemException;
	/**
	 * @param adviceAsyncHelperVO
	 * @throws RemoteException
	 */
	public void handleAdvice(com.ibsplc.xibase.server.framework.interceptor.vo.AsyncAdviceHelperVO adviceAsyncHelperVO) throws RemoteException;
	/**
	 * @author A-4777
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws ReportGenerationException
	 * @throws RemoteException
	 */
	Map<String, Object> generateStockAllocationReport(ReportSpec reportSpec)throws SystemException, ReportGenerationException,RemoteException;
	/**
	 * @author A-4777
	 * @param documentNumber
	 * @param offset
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	String getNextDocument(String documentNumber,int offset)throws RemoteException, SystemException ;
	
    /**
	 * 
	 * @param documentFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	DocumentValidationVO validateDocumentForWS(DocumentFilterVO documentFilterVO)
			throws SystemException, RemoteException, StockControlDefaultsBusinessException;

	/**
	 *
	 * @param documentFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	DocumentValidationVO validateDocumentStockForWS(DocumentFilterVO documentFilterVO)
			throws SystemException, RemoteException, StockControlDefaultsBusinessException;
	/**
	 * @author A-6767
	 * @param stockAllocationVO
	 * @param status
	 * @throws SystemException
	 * @throws InvalidStockHolderException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	public void saveStockUtilisation(
			StockAllocationVO stockAllocationVO,String status)
		throws SystemException, RemoteException, StockControlDefaultsBusinessException;
	/**
	 * @author A-7534
	 * @param documentFilterVO
	 * @return String
	 * @throws InvalidStockHolderException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	public String findAutoPopulateSubtype(DocumentFilterVO documentFilterVO)
			throws SystemException, RemoteException, StockControlDefaultsBusinessException;
	/**
	 * @author A-7534
	 * @param rangeVOs
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	public void deleteStock(Collection<RangeVO> rangeVOs)
			throws SystemException, RemoteException, StockControlDefaultsBusinessException;
	
	Page<RangeVO> findAvailableRanges(StockFilterVO stockFilterVO)
			throws RemoteException,SystemException;
	
	public int findTotalNoOfDocuments(StockFilterVO stockFilterVO)
			throws RemoteException,SystemException;
	
	public String saveStockDetails(StockAllocationVO stockAllocationVO)
			throws SystemException, RemoteException;
	
	public boolean isStockDetailsExists(DocumentFilterVO documentFilterVO)
			throws SystemException, RemoteException;
	
	public  Serializable doFlow(Flow paramFlow) throws BusinessException, SystemException, RemoteException;
	public AirlineValidationVO validateNumericCode(String companyCode,String shipmentPrefix) throws SystemException, RemoteException, StockControlDefaultsBusinessException;
	public  void saveStockReuseHistory(Collection<StockReuseHistoryVO> stockReuseHistoryVOs) throws SystemException, RemoteException ;
	public  void removeStockReuseHistory(StockReuseHistoryVO stockReuseHistoryVO) throws SystemException, RemoteException ;
	public  void updateStockReuseHistory(Collection<StockReuseHistoryVO> stockReuseHistoryVOs) throws SystemException, RemoteException;
	public void doAWBReUseRestrictionCheckEnhanced(DocumentFilterVO documentFilterVO) throws SystemException, RemoteException; 
}

