/*
 * StockControlDefaultsDelegate.java Created on Aug 9, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults;

import java.util.Collection;

import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReserveAWBVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.BlacklistStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
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
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeHistoryVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestApproveVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestForOALVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.TransitStockVO;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegate;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1358
 *
 */

@Module("stockcontrol")
@SubModule("defaults")
public class StockControlDefaultsDelegate extends BusinessDelegate {

	private Log log = LogFactory.getLogger("STOCKCONTROL");

	/**
	 * This method is used to validate the stock holder codes. Used by
	 * saveStockHolder method to validate the approver codes
	 *
	 * @param companyCode
	 * @param stockHolderCodes
	 * @throws BusinessDelegateException
	 */
	public Page<StockDetailsVO> listStockDetails(
			StockDetailsFilterVO stockDetailsFilterVO ) throws BusinessDelegateException {
		log.entering("StockControlDefaultsDelegate", "listStockDetails---------->");
		return despatchRequest("listStockDetails", stockDetailsFilterVO);
	}
	
	public void validateStockHolders(String companyCode,
			Collection<String> stockHolderCodes)
			throws BusinessDelegateException {
		despatchRequest("validateStockHolders", companyCode, stockHolderCodes);
	}

	/**
	 * This method is used to create/modify stockholder details. For create
	 * operationFlag is set as I and for modify, operationFlag is set as 'U'
	 *
	 * @param stockHolderVO
	 * @throws BusinessDelegateException
	 */
	public void saveStockHolderDetails(StockHolderVO stockHolderVO)
			throws BusinessDelegateException {
		log.entering("StockControlDefaultsDelegate", "saveStockHolderDetails");
		despatchRequest("saveStockHolderDetails", stockHolderVO);
		log.exiting("StockControlDefaultsDelegate", "saveStockRequestDetails");
	}

	/**
	 * This method is used to delete a stockholder. deletion is possible only if
	 * no stock exists under the stock holder dirctly.
	 *
	 * @param stockHolderDetailsVO
	 * @throws BusinessDelegateException
	 */
	public void deleteStockHolder(StockHolderDetailsVO stockHolderDetailsVO)
			throws BusinessDelegateException {
		despatchRequest("deleteStockHolder", stockHolderDetailsVO);
	}

	/**
	 * Used to list the details of a selected stockholder
	 *
	 * @param companyCode
	 * @param stockHolderCode
	 * @return StockHolderVO
	 * @throws BusinessDelegateException
	 */
	public StockHolderVO findStockHolderDetails(String companyCode,
			String stockHolderCode) throws BusinessDelegateException {
		return despatchRequest("findStockHolderDetails", companyCode,
				stockHolderCode);
	}

	/**
	 * This method finds details for stock holders which meet the filter
	 * criteria and all stock holders who fall under them.
	 *
	 * @param filterVO
	 * @return Collection
	 * @throws BusinessDelegateException
	 *             Collection<StockHolderDetailsVO>
	 */
	public Page<StockHolderDetailsVO> findStockHolders(
			StockHolderFilterVO filterVO) throws BusinessDelegateException {
		log.entering("StockControlDefaultsDelegate", "findStockHolders---------->");
		return despatchRequest("findStockHolders", filterVO);
	}

	/**
	 * This method is invoked to obtain the stock holder lov
	 *
	 * @param filterVO
	 * @param displayPage
	 * @return Page<StockHolderLovVO>
	 * @throws BusinessDelegateException
	 */
	public Page<StockHolderLovVO> findStockHolderLov(
			StockHolderLovFilterVO filterVO, int displayPage)
			throws BusinessDelegateException {
		return despatchRequest("findStockHolderLov", filterVO, displayPage);
	}

	/**
	 * This method will validate stockholdertype and code
	 *
	 * @author A-1883
	 * @param stockRequestVO
	 * @throws BusinessDelegateException
	 */
	public void validateStockHolderTypeCode(StockRequestVO stockRequestVO)
			throws BusinessDelegateException {
		despatchRequest("validateStockHolderTypeCode", stockRequestVO);
	}

	/**
	 * This method is used to create/modify/cancel stock request details based
	 * on operational flag
	 *
	 * @author A-1883
	 * @param stockRequestVO
	 * @return String
	 * @throws BusinessDelegateException
	 */
	public String saveStockRequestDetails(StockRequestVO stockRequestVO)
			throws BusinessDelegateException {
		log.entering("StockControlDefaultsDelegate", "saveStockRequestDetails");
		return despatchRequest("saveStockRequestDetails", stockRequestVO);
	}

	/**
	 * This method will fetch the details of a particular stock request
	 *
	 * @author A-1883
	 * @param stockRequestFilterVO
	 * @return StockRequestVO
	 * @throws BusinessDelegateException
	 */
	public StockRequestVO findStockRequestDetails(
			StockRequestFilterVO stockRequestFilterVO)
			throws BusinessDelegateException {
		log.entering("StockControlDefaultsDelegate", "findStockRequestDetails");
		return despatchRequest("findStockRequestDetails", stockRequestFilterVO);
	}

	/**
	 * @author A-1883 This method will fetch the stock requests satisfying the
	 *         filter criteria
	 * @param stockRequestFilterVO
	 * @param displayPage
	 * @return Page<StockRequestVO>
	 * @throws BusinessDelegateException
	 */
	public Page<StockRequestVO> findStockRequests(
			StockRequestFilterVO stockRequestFilterVO, int displayPage)
			throws BusinessDelegateException {
		log.entering("StockControlDefaultsDelegate", "findStockRequests");
		return despatchRequest("findStockRequests", stockRequestFilterVO,
				displayPage);
	}

	/**
	 * This method is used to find the stock holder codes for a collection of
	 * stock control privileges
	 *
	 * @param privileges
	 * @return Collection <String>
	 * @throws BusinessDelegateException
	 */
	public Collection<String> findStockHolderCodes(Collection<String> privileges)
			throws BusinessDelegateException {
		return null;
	}

	/**
	 * This method will cancel a particular stock request
	 *
	 * @author A-1883
	 * @param companyCode
	 * @param requestRefNumber
	 * @throws BusinessDelegateException
	 */
	public void cancelStockRequest(StockRequestVO stockRequestVO)
			throws BusinessDelegateException {
		log.entering("StockControlDefaultsDelegate", "cancelStockRequest");
		despatchRequest("cancelStockRequest", stockRequestVO);
	}

	/**
	 * This method is used to approve stock requests
	 *
	 * @author A-1883
	 * @param stockRequestApproveVO
	 * @throws BusinessDelegateException
	 */
	public void approveStockRequests(StockRequestApproveVO stockRequestApproveVO)
			throws BusinessDelegateException {
		log.entering("StockControlDefaultsDelegate", "approveStockRequests");
		despatchRequest("approveStockRequests", stockRequestApproveVO);
		log.exiting("StockControlDefaultsDelegate", "approveStockRequests");
	}

	/**
	 * This method is used to reject stock requests
	 *
	 * @param stockRequests
	 *            ,Collection <StockRequestVO>
	 * @throws BusinessDelegateException
	 */
	public void rejectStockRequests(Collection<StockRequestVO> stockRequests)
			throws BusinessDelegateException {
		log.entering("StockControlDefaultsDelegate", "rejectStockRequests");
		despatchRequest("rejectStockRequests", stockRequests);
		log.exiting("StockControlDefaultsDelegate", "rejectStockRequests");
	}

	/**
	 * This method is used to Complete stock requests
	 *
	 * @param stockRequests
	 *            ,Collection <StockRequestVO>
	 * @throws BusinessDelegateException
	 */
	public void completeStockRequests(Collection<StockRequestVO> stockRequests)
			throws BusinessDelegateException {
		log.entering("StockControlDefaultsDelegate", "completeStockRequests");
		despatchRequest("completeStockRequests", stockRequests);
		log.exiting("StockControlDefaultsDelegate", "completeStockRequests");
	}

	/**
	 *
	 * @param companyCode
	 * @return Collection<StockHolderPriorityVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<StockHolderPriorityVO> findStockHolderTypes(
			String companyCode) throws BusinessDelegateException {
		return despatchRequest("findStockHolderTypes", companyCode);
	}

	/**
	 * This method is used to find the available ranges for a stock holder code
	 *
	 * @param rangeFilterVO
	 * @return Collection <RangeVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<RangeVO> findRanges(RangeFilterVO rangeFilterVO)
			throws BusinessDelegateException {
		log.entering("StockControlDefaultsDelegate", "findRanges");
		return despatchRequest("findRanges", rangeFilterVO);
	}

	/**
	 * This method is used to allocate a stock range to stock holder and deplete
	 * the range from the approvers stock
	 *
	 * @param stockAllocationVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public StockAllocationVO allocateStock(StockAllocationVO stockAllocationVO)
			throws BusinessDelegateException {
		log
				.log(Log.INFO, "stockAllocationVO from client : ",
						stockAllocationVO);
		return despatchRequest("allocateStock", stockAllocationVO);

	}

	/**
	 * @author A-1944
	 * @param stockFilterVO
	 * @return Collection<MonitorStockVO>
	 * @throws BusinessDelegateException
	 */
	public Page<MonitorStockVO> monitorStock(StockFilterVO stockFilterVO)
			throws BusinessDelegateException {
		log.entering("StockControlDefaultsDelegate", "monitorStock");
		return despatchRequest("monitorStock", stockFilterVO);
	}

	/**
	 * @author A-1944
	 *
	 *
	 */
	public MonitorStockVO findMonitoringStockHolderDetails(StockFilterVO stockFilterVO)
		throws BusinessDelegateException {
		log.entering("StockControlDefaultsDelegate", "findMonitoringStockHolderDetails");
		return despatchRequest("findMonitoringStockHolderDetails", stockFilterVO);
	}

	/**
	 * This method is used to fetch the range details of a stockholder
	 *
	 * @author A-1883
	 * @param stockFilterVO
	 * @return StockRangeVO
	 * @throws BusinessDelegateException
	 */
	public StockRangeVO viewRange(StockFilterVO stockFilterVO)
			throws BusinessDelegateException {
		log.entering("StockControlDefaultsDelegate", "viewRange");
		return despatchRequest("viewRange", stockFilterVO);
	}

	/**
	 * This method is used to find the stock holder codes for a collection of
	 * stock control privileges
	 *
	 * @param companyCode
	 * @param privileges
	 * @return Collection<String>
	 * @throws BusinessDelegateException
	 */
	public Collection<String> findStockHolderCodes(String companyCode,
			Collection<String> privileges) throws BusinessDelegateException {
		log.entering("StockControlDefaultsDelegate", "findStockHolderCodes");
		return despatchRequest("findStockHolderCodes", companyCode, privileges);
	}

	/**
	 * blacklisting the stock
	 *
	 * @param blacklistStockVO
	 * @throws BusinessDelegateException
	 */
	public void blacklistStock(BlacklistStockVO blacklistStockVO)
			throws BusinessDelegateException {
		despatchRequest("blacklistStock", blacklistStockVO);
	}

	/**
	 * author A-4803
	 * blacklisting the stock
	 *
	 * @param blacklistStockVO
	 * @throws BusinessDelegateException
	 */
	public BlacklistStockVO validateStockForVoiding(BlacklistStockVO blacklistStockVO)
			throws BusinessDelegateException {
		return despatchRequest("validateStockForVoiding", blacklistStockVO);
	}
	/**
	 * method for revoking the blacklisted stock
	 *
	 * @param blacklistStockVO
	 * @throws BusinessDelegateException
	 */
	public void revokeBlacklistStock(BlacklistStockVO blacklistStockVO)
			throws BusinessDelegateException {
		despatchRequest("revokeBlacklistStock", blacklistStockVO);
	}

	/**
	 * This method will find the blacklisted stock matching the filter criteria
	 *
	 * @param stockFilterVO
	 * @param displayPage
	 * @return Page<BlacklistStockVO>
	 * @throws BusinessDelegateException
	 */
	public Page<BlacklistStockVO> findBlacklistedStock(
			StockFilterVO stockFilterVO, int displayPage)
			throws BusinessDelegateException {
		log.entering("StockControlDefaultsDelegate", "findBlacklistedStock");
		return despatchRequest("findBlacklistedStock", stockFilterVO,
				displayPage);
	}

	/**
	 * This method is used to set allocated quantity in the stock request object
	 *
	 * @author A-1883
	 * @param stockRequestVO
	 * @throws BusinessDelegateException
	 */
	public void allocateStockRequest(StockRequestVO stockRequestVO)
			throws BusinessDelegateException {
		log.entering("StockControlDefaultsDelegate", "allocateStockRequest");
		despatchRequest("allocateStockRequest", stockRequestVO);
		log.exiting("StockControlDefaultsDelegate", "allocateStockRequest");
	}

	/**
	 *
	 * @param companyCode
	 * @param stockHolderCodes
	 * @return Collection<StockHolderPriorityVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<StockHolderPriorityVO> findPriorities(String companyCode,
			Collection<String> stockHolderCodes)
			throws BusinessDelegateException {
		return despatchRequest("findPriorities", companyCode, stockHolderCodes);
	}

	/**
	 *
	 * @param companyCode
	 * @param stockHolderCode
	 * @param docType
	 * @param docSubType
	 * @return String
	 * @throws BusinessDelegateException
	 */
	public String findApproverCode(String companyCode, String stockHolderCode,
			String docType, String docSubType) throws BusinessDelegateException {
		return despatchRequest("findApproverCode", companyCode,
				stockHolderCode, docType, docSubType);
	}

	/**
	 * @param companyCode
	 * @param stockHolderCode
	 * @param docType
	 * @param docSubType
	 * @return
	 * @throws BusinessDelegateException
	 */
	public void checkStock(String companyCode, String stockHolderCode,
			String docType, String docSubType) throws BusinessDelegateException {
		despatchRequest("checkStock", companyCode, stockHolderCode, docType,
				docSubType);
	}


	// *****************************************************************************
	// ****************** R2 coding starts ***************************
	// *****************************************************************************

	/*
	 * Added by Sinoob
	 */
	/**
	 * @param stockFilterVO
	 * @return StockAllocationVO
	 * @throws BusinessDelegateException
	 */
	public StockAllocationVO findStockForAirline(StockFilterVO stockFilterVO)
			throws BusinessDelegateException {
		log.log(Log.INFO, "stockFilterVO :", stockFilterVO);
		return despatchRequest("findStockForAirline", stockFilterVO);
	}

	/**
	 * @param reserveAWBVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public ReserveAWBVO reserveDocument(ReserveAWBVO reserveAWBVO)
		throws BusinessDelegateException{
		log.log(Log.INFO, "reserveAWBVO : ", reserveAWBVO);
		return despatchRequest("reserveDocument", reserveAWBVO);
	}


	// Added by Sinoob ends

	/**
	 * @author A-1863
	 * @param stockFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<StockVO> findStockList(StockFilterVO stockFilterVO)
			throws BusinessDelegateException {
		return despatchRequest("findStockList", stockFilterVO);
	}

	/**
	 * @author A-1863
	 * @param stockRequestForOALVO
	 * @throws BusinessDelegateException
	 */
	public void saveStockRequestForOAL(StockRequestForOALVO stockRequestForOALVO)
			throws BusinessDelegateException {
		despatchRequest("saveStockRequestForOAL", stockRequestForOALVO);
	}

	/**
	 * @author A-1863
	 * @param stockFilterVO
	 * @return Collection<StockRequestForOALVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<StockRequestForOALVO> findStockRequestForOAL(
			StockFilterVO stockFilterVO) throws BusinessDelegateException {
		return despatchRequest("findStockRequestForOAL", stockFilterVO);
	}

	/**
	 * @param reservationFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<ReservationVO> findReservationListing(
			ReservationFilterVO reservationFilterVO)
			throws BusinessDelegateException {
		return despatchRequest("findReservationListing", reservationFilterVO);
	}

	/**
	 * @param reservationVO
	 * @throws BusinessDelegateException
	 */
	public void cancelReservation(Collection<ReservationVO> reservationVO)
			throws BusinessDelegateException {
		despatchRequest("cancelReservation", reservationVO);
		// find the Reservaion Bo,update the record status
	}

	/**
	 * @param reservationVO
	 * @throws BusinessDelegateException
	 */
	public void extendReservation(Collection<ReservationVO> reservationVO)
			throws BusinessDelegateException {
		despatchRequest("extendReservation", reservationVO);
		// find the ReservationBo,call the update method in ReservationBO
	}


	/**
	 * @param reservationFilterVO
	 * @throws BusinessDelegateException
	 */
	public void expireReservations(ReservationFilterVO reservationFilterVO)
		throws BusinessDelegateException{
		despatchRequest("expireReservations", reservationFilterVO);
	}

	/**
	 * Method to find document details
	 * @param companyCode
	 * @param airlineIdentifier
	 * @param documentNumber
	 * @return
	 * @throws BusinessDelegateException
	 */
	public StockRequestVO findDocumentDetails(String companyCode, int airlineIdentifier,
			String documentNumber) throws BusinessDelegateException{
		log.entering("StockControlDefaultsDelegate", "findDocumentDetails");
		return despatchRequest("findDocumentDetails", companyCode, airlineIdentifier,
				documentNumber);
	}

	/*
	 * added by Sinoob T S
	 */
	/**
	 * This method is used to save Stock-Agent mapping
	 * @param stockAgentVOs
	 * @throws BusinessDelegateException
	 */
	public void saveStockAgentMappings(Collection<StockAgentVO> stockAgentVOs)
			throws BusinessDelegateException{
		log.log(Log.INFO, "---inside---", stockAgentVOs);
		despatchRequest("saveStockAgentMappings", stockAgentVOs);
		log.log(Log.INFO,"RETURN");
	}


	/**
	 * This method is used to fetch Stock-Agent mappings
	 *
	 * @param stockAgentFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Page<StockAgentVO> findStockAgentMappings(
			StockAgentFilterVO stockAgentFilterVO)
		throws BusinessDelegateException{
		log.log(Log.INFO,"--inside$$$$--");
		log.log(Log.INFO, "stockAgentFilterVO : ", stockAgentFilterVO);
		return despatchRequest("findStockAgentMappings", stockAgentFilterVO);
	}

	/**
	 * @param documentFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public DocumentValidationVO validateDocument(
			DocumentFilterVO documentFilterVO) throws BusinessDelegateException{
		log.log(Log.INFO, "@@@@ DocumentFilterVO ", documentFilterVO);
		return despatchRequest("validateDocument" , documentFilterVO);
	}

	/**
	 * @param filterVO
	 * @throws BusinessDelegateException
	 */
	public void deleteDocumentFromStock(DocumentFilterVO filterVO)
		throws BusinessDelegateException{
		log.log(Log.INFO, "#### DocumentFilterVO ", filterVO);
		despatchRequest("deleteDocumentFromStock" , filterVO);
	}

	/**
	 * @param filterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public DocumentValidationVO findNextDocumentNumber(
			DocumentFilterVO filterVO) throws BusinessDelegateException{
		log.log(Log.INFO, "$$$$ DocumentFilterVO ", filterVO);
		return despatchRequest("findNextDocumentNumber" , filterVO);
	}

	/**
	 * @param filterVO
	 * @return
	 * @throws BusinessDelegateException
	 */
	public void autoStockDeplete(
			StockDepleteFilterVO stockDepleteFilterVO) throws BusinessDelegateException{
		log.log(Log.INFO, "$$$$ autoStockDeplete ", stockDepleteFilterVO);
		despatchRequest("autoStockDeplete" , stockDepleteFilterVO);   
	}
	/*
	 * added by Sinoob ends
	 */


	/**
	 * @param stockAgentVos
	 * @return
	 * @throws BusinessDelegateException
	 */
	/*	public void saveStockHolderAgentMappings(Collection<StockAgentVO> stockAgentVos)
										throws BusinessDelegateException {
	log.log(Log.FINE," Inside saveStockHolderAgentMapping  saveStockHolderAgentMappings");

	despatchRequest("saveStockHolderAgentMappings",stockAgentVos);
	}

	public Page<StockAgentVO> findStockHolderAgentMappings(StockAgentVO stockAgentVo)
					   throws BusinessDelegateException{
		log.log(Log.FINE," Inside findStockHolderAgentMappings  findStockHolderAgentMappings");
		return despatchRequest("findStockHolderAgentMappings",stockAgentVo);
	}
	*/
	/**
	 * @param stockAllocationVO
	 * @throws BusinessDelegateException
	 */
	public void returnDocumentToStock(
			StockAllocationVO stockAllocationVO ) throws BusinessDelegateException{

		log.log(Log.INFO, "$$$$ stockAllocationVO ", stockAllocationVO);
		despatchRequest("returnDocumentToStock" , stockAllocationVO);
	}

	/*public void validateAgentForStockHolder(AWBDocumentValidationVO documentValidationVO)
	throws BusinessDelegateException{
		despatchRequest("validateAgentForStockHolder",documentValidationVO);
	}*/

	/*
	 * added by A-2882 ends
	 */


	/**
	 * @param stockControlFor
	 * @return
	 * @throws BusinessDelegateException
	 */

public String findPrivileage(String stockControlFor)
	throws BusinessDelegateException{
	log.log(Log.INFO, "stockControlFor : ", stockControlFor);
	return despatchRequest("findPrivileage", stockControlFor);
}

  public void deleteHQStock(RangeFilterVO rangeFilterVO)
		throws BusinessDelegateException{
	log.log(Log.INFO, "#### RangeFilterVO : ", rangeFilterVO);
	despatchRequest("deleteHQStock", rangeFilterVO);
	}
/**
 * @author A-7534
 * @param rangeFilterVO
 * @throws BusinessDelegateException
 */
  public void deleteStock(Collection<RangeVO> rangeVOs)
			throws BusinessDelegateException{
		log.log(Log.INFO,"in Delegate - deleteStock");
		despatchRequest("deleteStock", rangeVOs);
  }

  /**
	 * @author A-3184 & A-3155
	 * @param StockRangeHistoryVO
	 * @return
	 * @throws BusinessDelegateException
	 */

	public Collection<StockRangeHistoryVO> findStockRangeHistory(
			StockRangeFilterVO stockRangeFilterVO)
			throws BusinessDelegateException{ 
			log.log(Log.INFO,"$$$$Delegate stockRangeHistoryVO: ");
			return despatchRequest("findStockRangeHistory",stockRangeFilterVO);
	}
	
	/**
	 * @author A-3184 
	 * @param StockDepleteFilterVO
	 * @return
	 * @throws BusinessDelegateException
	 */

	public void autoDepleteStock(
			StockDepleteFilterVO stockDepleteFilterVO)
			throws BusinessDelegateException{
		log.log(Log.INFO, "#### StockDepleteFilterVO : ", stockDepleteFilterVO);
		despatchRequest("autoDepleteStock", stockDepleteFilterVO);
	}
	
	/**
	 * @param filterVO
	 * @throws BusinessDelegateException
	 */
	public void reopenDocumentToStock(DocumentFilterVO filterVO)
		throws BusinessDelegateException{
		log.log(Log.INFO, "#### DocumentFilterVO ", filterVO);
		despatchRequest("reopenDocumentToStock" , filterVO);
	}
	/**
	 * This method is used to list the transit stocks
	 * @author A-4443
	 * @param 
	 * @throws BusinessDelegateException
	 */
	public Collection<TransitStockVO> findTransitStocks(
			StockRequestFilterVO stockRequestFilterVO ) throws BusinessDelegateException {
		log.entering("StockControlDefaultsDelegate", "findTransitStocks---------->");
		return despatchRequest("findTransitStocks", stockRequestFilterVO);
	}

	/**
	 * This method is used to list the transit stocks
	 * @author A-4443
	 * @param 
	 * @throws BusinessDelegateException
	 */
	public Collection<TransitStockVO> confirmStockRange(
			Collection<TransitStockVO> transitStockVOs ) throws BusinessDelegateException {
		log.entering("StockControlDefaultsDelegate", "confirmStockRange---------->");
		return despatchRequest("confirmStockRange", transitStockVOs);
	}
	/**
	 * This method is used to update the transit stock with the missing stocks
	 * @author A-4443
	 * @param 
	 * @throws BusinessDelegateException
	 */
	public Collection<TransitStockVO> updateMissingStock(
			TransitStockVO transitStockVO ) throws BusinessDelegateException {
		log.entering("StockControlDefaultsDelegate", "updateMissingStock---------->");
		return despatchRequest("updateMissingStock", transitStockVO);
	}
	
	public Page<StockRangeHistoryVO> findStockRangeHistoryForPage(
			StockRangeFilterVO stockRangeFilterVO)
			throws BusinessDelegateException{ 
			log.log(Log.INFO,"findStockRangeHistoryForPage");
			return despatchRequest("findStockRangeHistoryForPage",stockRangeFilterVO);
	}
	
	public Page<RangeVO> findAvailableRanges(StockFilterVO stockFilterVO)
			throws BusinessDelegateException{ 
			log.log(Log.INFO,"findAvailableRanges");
			return despatchRequest("findAvailableRanges",stockFilterVO);
	}
	public int findTotalNoOfDocuments(StockFilterVO stockFilterVO)
			throws BusinessDelegateException{ 
			log.log(Log.INFO,"findTotalNoOfDocuments");
			return despatchRequest("findTotalNoOfDocuments",stockFilterVO);
	}
}
