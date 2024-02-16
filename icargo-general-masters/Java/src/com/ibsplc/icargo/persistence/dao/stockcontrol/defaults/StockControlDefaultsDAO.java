/*
 * StockControlDefaultsDAO.java Created on Jul 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.stockcontrol.defaults;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentForBookingVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.BlacklistStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.InformAgentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.MonitorStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAgentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAgentVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
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
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeHistoryVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestForOALVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockReuseHistoryVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockVO;
import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1358
 *
 * This interface defines the database queries that are required by the
 * stockcontrol module
 */
public interface StockControlDefaultsDAO {  

	/**
	 * This method returns stockvalidationVo for all valid stockholders in the
	 * given collection.
	 * 
	 * @param companyCode
	 * @param stockHolderCodes
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<String> findStockHoldersForValidation(String companyCode,
			Collection<String> stockHolderCodes) throws SystemException,
			PersistenceException;

	/**
	 * This method issues a DAO query to obtain the details of a particular
	 * stockholder
	 *
	 * @param companyCode
	 * @param stockHolderCode
	 * @return StockHolderVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	StockHolderVO findStockHolderDetails(String companyCode,
			String stockHolderCode) throws SystemException,
			PersistenceException;

	/**
	 * @author A-1883
	 * @param stockRequestVO
	 * @return Collection<String>
	 * @throws SystemException
	 */
	Collection<String> validateStockHolderTypeCode(StockRequestVO stockRequestVO)
			throws SystemException, PersistenceException;

	/**
	 * This method finds details for stock holders which meet the filter
	 * criteria and all stock holders who fall under them.
	 *
	 * @param filterVO
	 * @return Collection<StockHolderDetailsVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Page<StockHolderDetailsVO> findStockHolders(
			StockHolderFilterVO filterVO) throws SystemException,
			PersistenceException;

	//Added by Chippy for CR1878
	Page<StockDetailsVO> listStockDetails(
			StockDetailsFilterVO filterVO) throws SystemException,
			PersistenceException;
	/**
	 * This method is invoked to obtain the stock holder lov
	 *
	 * @param filterVO
	 * @param displayPage
	 * @return Page<StockHolderLovVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Page<StockHolderLovVO> findStockHolderLov(StockHolderLovFilterVO filterVO,
			int displayPage) throws SystemException, PersistenceException;

	/**
	 * This method is used to find the stock requests matching the specified
	 * filter criteria
	 *
	 * @param stockRequestFilterVO
	 * @param displayPage
	 * @return Page<StockRequestVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Page<StockRequestVO> findStockRequests(
			StockRequestFilterVO stockRequestFilterVO, int displayPage)
			throws SystemException, PersistenceException;

	/**
	 * This method is used to find the details of a particular stock request
	 *
	 * @param stockRequestFilterVO
	 * @return StockRequestVO
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	StockRequestVO findStockRequestDetails(
			StockRequestFilterVO stockRequestFilterVO) throws SystemException,
			PersistenceException;

	/**
	 * This method will fetch the stock requests for approval Stock requests
	 * having status other than Completed, rejected are retrieved
	 *
	 * @param stockRequestFilterVO
	 * @return Collection<StockRequestVO>
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	Collection<StockRequestVO> findStockRequestsForApproval(
			StockRequestFilterVO stockRequestFilterVO) throws SystemException,
			PersistenceException;

	/**
	 * This method is used to fetch the stockholder types sorted based on
	 * prioity
	 *
	 * @param companyCode
	 * @return Collection<StockHolderPriorityVO>
	 * @throws SystemException
	 */
	Collection<StockHolderPriorityVO> findStockHolderTypes(String companyCode)
			throws SystemException, PersistenceException;

	/**
	 * @author A-1883
	 * @param rangeFilterVO
	 * @return Collection<RangeVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<RangeVO> findRanges(RangeFilterVO rangeFilterVO)
			throws SystemException, PersistenceException;

	/**
	 * @author A-1883
	 * @param stockFilterVO
	 * @return Collection<RangeVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<RangeVO> findAllocatedRanges(StockFilterVO stockFilterVO)
			throws SystemException, PersistenceException;

	/**
	 * @author A-1944 This method is used to monitor the stock of a stock holder
	 * @param stockFilterVO
	 * @return Collection<MonitorStockVO>
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	Page<MonitorStockVO> monitorStock(StockFilterVO stockFilterVO)
			throws SystemException, PersistenceException;



	/**
	 * @author A-1944 This method is used to monitor the stock of a stock holder
	 * @param stockFilterVO
	 * @return Collection<MonitorStockVO>
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	MonitorStockVO findMonitoringStockHolderDetails(StockFilterVO stockFilterVO)
			throws SystemException, PersistenceException;

	/**
	 * This method is used to find the stock holder codes for a collection of
	 * stock control privileges
	 *
	 * @author A-1883
	 * @param companyCode
	 * @param privileges
	 * @return Collection<String>
	 * @throws SystemException
	 */
	Collection<String> findStockHolderCodes(String companyCode,
			Collection<String> privileges) throws SystemException,
			PersistenceException;

	/**
	 * This method will find the blacklisted stock matching the filter criteria
	 *
	 * @param stockFilterVO
	 * @param displayPage
	 * @return Page<BlacklistStockVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Page<BlacklistStockVO> findBlacklistedStock(StockFilterVO stockFilterVO,
			int displayPage) throws SystemException, PersistenceException;

	/**
	 * @param companyCode
	 * @param airlineIdentifier
	 * @param docType
	 * @param docSubType
	 * @param startRange
	 * @param endRange
	 * @return
	 * @throws SystemException
	 */
	Collection<RangeVO> checkDuplicateRange(String companyCode,
			int airlineIdentifier, String docType, String docSubType,
			String startRange, String endRange) throws SystemException;

	/**
	 * @param companyCode
	 * @param airlineId
	 * @param docType
	 * @param docSubType
	 * @param startRange
	 * @param endRange
	 * @return
	 * @throws SystemException
	 */
	Collection<StockVO> findBlacklistRanges(String companyCode, int airlineId,
			String docType, String docSubType, String startRange,
			String endRange) throws SystemException;

	/**
	 * @param companyCode
	 * @param airlineId
	 * @param docType
	 * @param docSubType
	 * @param startRange
	 * @param endRange
	 * @return
	 * @throws SystemException
	 */
	public Collection<BlacklistStockVO> findBlackListRangesForRevoke(String companyCode,
			int airlineId, String docType, String docSubType,
			String startRange, String endRange) throws SystemException ;

	/**
	 * @param companyCode
	 * @param airlineIdentifier
	 * @param docType
	 * @param docSubType
	 * @param startRange
	 * @param endRange
	 * @return
	 * @throws SystemException
	 */
	String checkBlacklistRanges(String companyCode, int airlineIdentifier,
			String docType, String docSubType, long startRange,
			long endRange) throws SystemException;

	/**
	 *
	 * @param companyCode
	 * @param stockHolderCodes
	 * @return Collection<StockHolderPriorityVO>
	 * @throws SystemException
	 */
	Collection<StockHolderPriorityVO> findPriorities(String companyCode,
			Collection<String> stockHolderCodes) throws SystemException,
			PersistenceException;

	/**
	 * @param companyCode
	 * @param stockHolderCode
	 * @param docType
	 * @param docSubType
	 * @return String
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	String findApproverCode(String companyCode, String stockHolderCode,
			String docType, String docSubType) throws SystemException,
			PersistenceException;

	/**
	 * @param companyCode
	 * @param stockHolderCode
	 * @param docType
	 * @param docSubType
	 * @throws SystemException
	 */
	List<String> checkStock(String companyCode, String stockHolderCode,
			String docType, String docSubType) throws SystemException,
			PersistenceException;

	/**
	 *
	 * @param companyCode
	 * @param docType
	 * @param docSubType
	 * @param incomeRange
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	RangeVO findRangeDelete(String companyCode, String docType,
			String docSubType, String incomeRange) throws SystemException,
			PersistenceException;


	/**
	 * @author A-1883
	 * @param companyCode
	 * @param docType
	 * @param documentNumber
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	boolean checkForBlacklistedDocument(String companyCode, String docType,
			String documentNumber) throws SystemException, PersistenceException;

	/**
	 * @author A-1883
	 * @param blacklistStockVO
	 * @return boolean
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	boolean alreadyBlackListed(BlacklistStockVO blacklistStockVO)
			throws SystemException, PersistenceException;

	//*****************************************************************************
	//******************        R2 coding starts        ***************************
	//*****************************************************************************

	/*
	 * Added by Sinoob
	 */
	/**
	 * @param stockFilterVO
	 * @return StockAllocationVO
	 * @throws SystemException
	 */
	StockAllocationVO findStockForAirline(StockFilterVO stockFilterVO)
			throws SystemException;

	//	Added by Sinoob ends

	/**
	 * @author A-1863
	 * @param stockFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Page<StockVO> findStockList(StockFilterVO stockFilterVO)
			throws SystemException, PersistenceException;

	/**
	 * @author A-1863
	 * @param stockFilterVO
	 * @return Collection<StockRequestForOALVO>
	 * @throws SystemException
	 */
	Collection<StockRequestForOALVO> findStockRequestForOAL(
			StockFilterVO stockFilterVO) throws SystemException,
			PersistenceException;

	/**
	 *
	 * @param reservationFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<ReservationVO> findReservationListing(
			ReservationFilterVO reservationFilterVO) throws SystemException,
			PersistenceException;

	/**
	 * @param reservationFilterVO
	 * @param expiryPeriod
	 * @return
	 * @throws SystemException
	 */
	Collection<ReservationVO> findExpiredReserveAwbs(
			ReservationFilterVO reservationFilterVO, int expiryPeriod)
			throws SystemException;

	/**
	 * @param stockFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	StockVO findStockForStockHolder(StockFilterVO stockFilterVO) throws SystemException, PersistenceException;

	/**
	 * Method to find document details
	 * @param companyCode
	 * @param airlineIdentifier
	 * @param documentNumber
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	StockRequestVO findDocumentDetails(String companyCode, int airlineIdentifier,
			String documentNumber) throws SystemException, PersistenceException;
	/**
	 * @author a-1885
	 * @param filterVo
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<StockHolderStockDetailsVO> findStockHolderStockDetails
	(InformAgentFilterVO filterVo)throws SystemException,PersistenceException;

	/**
	 * Method to check if the stock holder code is an approver
	 * @param companyCode
	 * @param stockholderCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	boolean checkApprover(String companyCode, String stockholderCode)
			throws SystemException, PersistenceException;
	/**
	 * @author a-1885
	 * @param stockAllocationVo
	 * @param startRange
	 * @param numberOfDocuments
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<RangeVO> findRangeForTransfer(StockAllocationVO
			stockAllocationVo,String startRange,long numberOfDocuments)
			throws SystemException,PersistenceException;

	/*
	 * added by Sinoob T S
	 */
	/**
	 * @param stockAgentFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Page<StockAgentVO> findStockAgentMappings(StockAgentFilterVO stockAgentFilterVO)
		throws SystemException, PersistenceException;

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
	 * @throws PersistenceException
	 */
	String checkDocumentExistsInAnyStock(String companyCode,int airlineId,
										String documentType,long documentNumber)
		throws SystemException, PersistenceException;


	/**
	 * @param companyCode
	 * @param stockHolderCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<String> findAgentsForStockHolder(
			String companyCode, String stockHolderCode)
		throws SystemException, PersistenceException;

	/**
	 * @param companyCode
	 * @param airlineId
	 * @param documentType
	 * @param documentNumber
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	String findSubTypeForDocument(String companyCode, int airlineId,
			String documentType, long documentNumber)
		throws SystemException, PersistenceException;

	/*
	 * added by Sinoob T S ends
	 */

	/*
	 * CheckForStockRequest to be autoprocessed Start
	 */
	/**
	 * @author a-1885
	 * @param requestVo
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	String findAutoProcessingQuantity(StockRequestVO requestVo)throws SystemException,
	PersistenceException;
	/*
	 * CheckForStockRequest to be autoprocessed End
	 */
	/**
	 * @author a-1885
	 */
	String checkDuplicateHeadQuarters(String companyCode,String stockHolderType)
	throws SystemException,PersistenceException;

	
	String findHeadQuarterDetails(String companyCode,int airlineIdentifier,String documentType,String documentSubType)
	throws SystemException,PersistenceException;
	/**
	 * @param StockFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<String> checkReservedDocumentExists(StockFilterVO filterVO)
			throws SystemException, PersistenceException;

	/**
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	ReservationVO findReservationDetails(DocumentFilterVO filterVO)
			throws SystemException, PersistenceException;
	/**
	 *
	 * @param stockDepleteFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<StockAllocationVO> findStockRangeUtilisation
	(StockDepleteFilterVO stockDepleteFilterVO)throws
	SystemException,PersistenceException;
	/**
	 * @author a-1885
	 * modified under BUG_ICRD-23686_AiynaSuresh_28Mar13
	 * @param companyCode
	 * @param airlineid
	 * @param logClearingThreshold
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	void deleteStockRangeUtilisation(String companyCode,int airlineid,GMTDate actualDate,int logClearingThreshold)
	throws SystemException,PersistenceException;



	/**
	 * @param companyCode
	 * @param stockHolderCode
	 * @param documentType
	 * @param documentSubType
	 * @param airlineIdentifier
	 * @param rangeVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	String findStockRangeUtilisationExists(String companyCode,String stockHolderCode,
			String documentType,String documentSubType,int airlineIdentifier,RangeVO rangeVO)throws
	SystemException,PersistenceException;


	/**
	 * @param companyCode
	 * @param airlineIdentifier
	 * @param stockHolderCode
	 * @param documentType
	 * @param documentNumber
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	boolean checkStockRangeUtilised(String companyCode,int airlineIdentifier,
			String stockHolderCode, String documentType,String documentSubType,String documentNumber)throws
	SystemException,PersistenceException;

	String findPrivileage(String stockControlFor) throws SystemException,PersistenceException;

	Collection<BlacklistStockVO> findRangesToBeDeleted(String companyCode,
				int airlineId, String docType, String docSubType,
			String startRange, String endRange)throws SystemException,PersistenceException;

	Collection<RangeVO> findRangesForViewRange(StockFilterVO stockFilterVO)throws SystemException,
	PersistenceException;
	
	/**
     * @author a-2434
     * @param Collection<RangeVO> rangeVOS
     * @return Collection
     * @throws SystemException
     */
    Collection<RangeVO> findRangesforMerge(Collection<RangeVO> rangeVOS) throws SystemException;
    
    /**
     * @author a-3184
     * @param Collection<RangeVO> rangeVOS
     * @return Collection
     * @throws SystemException
     */
    Collection<RangeVO> findAWBStockDetailsForPrint(StockFilterVO stockFilterVO) throws SystemException;
    
    /**
     * @author A-3155
     * @param masterDocumentNumber
     * @return
     * @throws SystemException
     */
    boolean checkAWBExistsInOperation(String companyCode,String masterDocumentNumber,int ownerID,String documentType, String documentSubType) throws SystemException;
    /**
     * @author A-3155
     * @param companyCode
     * @param airlineIdentifier
     * @param documentNumber
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    boolean checkStockRangeUtilisedLog(String companyCode,int airlineIdentifier,String documentNumber,String documentType, String documentSubType)throws
	SystemException,PersistenceException;
    
    /**
	 * @author A-3184 
	 * @param stockDetailsFilterVO
	 * @return StockDetailsVO
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	StockDetailsVO findCustomerStockDetails(StockDetailsFilterVO stockDetailsFilterVO)
			throws SystemException, PersistenceException;
	
	/**
	 * @param stockDetailsFilterVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	String findStockHolderForCustomer(String companyCode,String customerCode)
	throws SystemException,PersistenceException ;
	/**
	 * @author A-4421
	 * @param String
	 * @throws SystemException
	 */
	void updateStockUtilization(String source)throws SystemException;
	
	/**
	 * @author A-3184
	 * @param rangeVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
   Collection<RangeVO> findUsedRangesForMerge(
			RangeVO rangeVO,String status) throws SystemException,
			PersistenceException;
   /**
	 * @author A-3184
	 * @param rangeVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
  Collection<RangeVO> findUsedRangesInHis(
			RangeVO rangeVO,String status) throws SystemException,
			PersistenceException;
  /**
	 * @param companyCode
	 * @param airlineId
	 * @param documentType
	 * @param documentNumber
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
  DocumentFilterVO findSubTypeForReopenDocument(DocumentFilterVO filtervo, long documentNumber)
		throws SystemException, PersistenceException;

  	/** 
	 * @author A-2415
	 * @param stockRangeHistoryVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<StockRangeHistoryVO> findAgentCSVDetails(
			StockRangeFilterVO stockRangeFilterVO) throws SystemException,
			PersistenceException;  
	
	/** 
	 * @author A-3184
	 * @param companyCode
	 * @param documentType
	 * @param documentSubType
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	String  checkDocumentStatus(String companyCode,String docType,String docSubTyp) 
	throws SystemException,PersistenceException;
	
	/**
	 * @author A-3184
	 * @param companyCode
	 * @param airlineId
	 * @param documentType
	 * @param stockHolderCode
	 * @param isRegionorstation
	 * @return Collection<String>
	 * @throws SystemException
	 */
	Collection<String> findSubTypesForStockHolder(String companyCode,int airlineId,String documentType,
			String subType,String stockHolderCode,boolean isRegionorstation)
			throws SystemException, PersistenceException;
	
	/**
	 * @author A-3184
	 * @param companyCode
	 * @param airlineId
	 * @param documentType
	 * @param stockHolderCode
	 * @param isRegionorstation
	 * @return Collection<String>
	 * @throws SystemException
	 */
	Collection<String> findUsedStocksFromUtilisation(String companyCode,int airlineId,String documentType,
			String subType)
			throws SystemException, PersistenceException;
	
	/** 
	 * @author A-3184
	 * @param companyCode
	 * @param documentType
	 * @param documentSubType
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	String  checkForApproverStock(String companyCode,int airlineId,String docType,String docSubType,String stkApproverCode) 
	throws SystemException,PersistenceException;
	
	
	/** 
	 * @author A-3184
	 * @param stockAllocationVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	boolean  findOnlyUsedStockFromUtl(StockAllocationVO stockAllocationVO) 
	throws SystemException,PersistenceException;
	
	/** 
	 * @author A-3184
	 * @param blacklistStockVo  
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<RangeVO>  checkForUsedStockInHis(BlacklistStockVO blacklistStockVo) 
	throws SystemException,PersistenceException;
	
	/**
	 * @param companyCode
	 * @param airlineId
	 * @param docType
	 * @param docSubType
	 * @param startRange
	 * @param endRange
	 * @return
	 * @throws SystemException
	 */
	Collection<RangeVO> checkRangesInUtl(String companyCode, int airlineId,
			String docType, String docSubType, String startRange,
			String endRange) throws SystemException;
	
	/** 
	 * @author A-3184
	 * @param blacklistStockVo
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	String  returnFirstLevelStockHolder(BlacklistStockVO blacklistStockVo) 
	throws SystemException,PersistenceException;
	
	/** 
	 * @author A-3184
	 * @param blacklistStockVo
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<RangeVO>  checkForUsedStockInUTLHis(BlacklistStockVO blacklistStockVo) 
	throws SystemException,PersistenceException;
	
	/**
	 * @param companyCode	 
	 * @param documentType
	 * @param documentNumber
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	StockRangeVO findDocSubTypeFromUtl(String companyCode,String documentType, long documentNumber)
		throws SystemException, PersistenceException;

	   /**
		 * @author A-3155
		 * @param rangeVO 
		 * @return
		 * @throws SystemException
		 * @throws PersistenceException
		 */
	   Collection<RangeVO> findUtilisationRangesForMerge(
				RangeVO rangeVO) throws SystemException,
				PersistenceException;
									 

	 /**
		 * @author A-3184
		 * @param rangeFilterVO
		 * @return Collection<RangeVO>
		 * @throws SystemException
		 * @throws PersistenceException
		 */
		Collection<RangeVO> findRangesForHQ(RangeFilterVO rangeFilterVO)
				throws SystemException, PersistenceException;	


	 	    
	    /** 
		 * @author A-3155 & A-3184
		 * @param stockRangeHistoryVO
		 * @return
		 * @throws SystemException
		 * @throws PersistenceException
		 */
		Collection<StockRangeHistoryVO> findStockRangeHistory(
				StockRangeFilterVO stockRangeFilterVO) throws SystemException,
				PersistenceException;
		
		/** 
		 * @author A-3184
		 * @param stockAllocationVO
		 * @return
		 * @throws SystemException
		 * @throws PersistenceException
		 */
		List<Integer>  validateStockPeriod(StockAllocationVO stockAllocationVO,int stockIntroductionPeriod) 
		throws SystemException,PersistenceException;

		/** 
		 * @author A-3184
		 * @param rangeFilterVO
		 * @return
		 * @throws SystemException
		 * @throws PersistenceException   
		 */
		boolean  findDistributedRangesForHQ(RangeFilterVO rangeFilterVO) 
		throws SystemException,PersistenceException;
		
		/**
		 * @author A-2881
		 * @param stockRangeFilterVO
		 * @return
		 * @throws SystemException
		 * @throws PersistenceException
		 */
		Page<StockRangeHistoryVO> findStockRangeHistoryForPage(
				StockRangeFilterVO stockRangeFilterVO) throws SystemException,
				PersistenceException;
	
	/**
	 * @author A-5153
	 * @param filterVO
	 * @throws SystemException
	 */
	void autoDepleteStock() throws SystemException, PersistenceException;
	
	String checkStockUtilisationForRange(StockFilterVO stockFilterVO) throws SystemException, PersistenceException;
	/**
	 * @author A-6767
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<BlacklistStockVO> findBlacklistedStockFromUTL() throws SystemException,
			PersistenceException;
	/**
	 * @author A-6767
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	void deleteBlackListedStockFromUTL(ArrayList<String> masterDocNumbers) throws SystemException,
			PersistenceException;
	/**
	 * @author A-7534
	 * @param bookingVO
	 * @return String
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public String findAutoPopulateSubtype(DocumentFilterVO documentFilterVO)throws SystemException,
	PersistenceException;

	/**
	 * 
	 * 	Method		:	StockControlDefaultsDAO.findAWBUsageDetails
	 *	Added by 	:	A-6858 on 13-Mar-2018
	 * 	Used for 	:
	 *	Parameters	:	@param country
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param masterDocumentNumber
	 *	Parameters	:	@param ownerID
	 *	Parameters	:	@return
	 *	Parameters	:	@throws PersistenceException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	ShipmentForBookingVO
	 */
	public ShipmentForBookingVO findAWBUsageDetails(String country,String companyCode,long 
			 masterDocumentNumber, int ownerID,String originDestinationIndicator)throws PersistenceException, SystemException;
	
	public String saveStockDetails(StockAllocationVO stockAllocationVO) throws PersistenceException, SystemException;
	
    public boolean isStockDetailsExists(DocumentFilterVO documentFilterVO) throws PersistenceException, SystemException;
    /**
	 * @param companyCode
	 * @param airlineId
	 * @param docType
	 * @param docSubType
	 * @param startRange
	 * @param endRange
	 * @return
	 * @throws SystemException
	 */
	Collection<StockVO> findBlacklistRangesForBlackList(String companyCode, int airlineId,
			String docType, String docSubType, String startRange,
			String endRange) throws SystemException;
	
	public Page<RangeVO> findAvailableRanges(StockFilterVO stockFilterVO) throws PersistenceException, SystemException;
	
	public int findTotalNoOfDocuments(StockFilterVO stockFilterVO) throws SystemException,
 	PersistenceException;
	
	/**
	  * 
	  * 	Method		:	StockControlDefaultsDAO.saveStockRangeUtilisationLog
	  *	Added by 	:	A-8146 on 21-Aug-2019
	  * 	Used for 	:
	  *	Parameters	:	@param companyCode
	  *	Parameters	:	@param airlineIdentifier
	  *	Parameters	:	@param documentNumber
	  *	Parameters	:	@param documentType
	  *	Parameters	:	@param documentSubType
	  *	Parameters	:	@param lastUpdateUser
	  *	Parameters	:	@param lastUpdateTime
	  *	Parameters	:	@throws PersistenceException
	  *	Parameters	:	@throws SystemException 
	  *	Return type	: 	void
	 * @throws SQLIntegrityConstraintViolationException 
	 * @throws SQLException 
	  */
	public void saveStockRangeUtilisationLog(String companyCode, int airlineIdentifier, String documentNumber,
			String documentType, String documentSubType, String lastUpdateUser, Calendar lastUpdateTime)throws PersistenceException, SystemException;


	/**
	 * Method		:	StockControlDefaultsDAO.checkIfAWBBlackListed
	 *	Added by 	:	A-9677 on 01-april-2021
	 * @param blacklistStockVO
	 * @return
	 * @throws SystemException
	 */
     public boolean checkIfAWBBlackListed(BlacklistStockVO blacklistStockVO) throws SystemException;

	/**
	 *
	 * @param companyCode
	 * @param airlineIdentifier
	 * @param documentNumber
	 * @param documentType
	 * @param documentSubType
	 * @return
	 * @throws SystemException
	 */
	 public boolean checkIFAWBIsVoid(String companyCode,  String documentNumber,
									 String documentType, String documentSubType) throws SystemException;
	 /**
	 *
	 * @param companyCode 
	 * @throws SystemException 
	
	 */
	 public Collection <Integer> removeStockReuseHistory(StockReuseHistoryVO stockReuseHistoryVO) throws SystemException;
	 
	 public StockReuseHistoryVO findAWBReuseHistoryDetails(DocumentFilterVO documentFilterVo,String countryCode )throws SystemException;
}
