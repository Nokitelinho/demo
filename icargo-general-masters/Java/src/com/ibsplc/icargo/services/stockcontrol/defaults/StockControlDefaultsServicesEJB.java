/*
 * StockControlDefaultsServicesEJB.java Created on Jul 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.services.stockcontrol.defaults;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.BlacklistedRangeExistsException;
import com.ibsplc.icargo.business.stockcontrol.defaults.CancelStockHolderException;
import com.ibsplc.icargo.business.stockcontrol.defaults.InvalidStockHolderException;
import com.ibsplc.icargo.business.stockcontrol.defaults.RangeExistsException;
import com.ibsplc.icargo.business.stockcontrol.defaults.StockControlDefaultsBI;
import com.ibsplc.icargo.business.stockcontrol.defaults.StockControlDefaultsBusinessException;
import com.ibsplc.icargo.business.stockcontrol.defaults.StockController;
import com.ibsplc.icargo.business.stockcontrol.defaults.StockNotFoundException;
import com.ibsplc.icargo.business.stockcontrol.defaults.audit.StockControlDefaultsAuditController;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.ReservationController;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.ReservationException;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReserveAWBVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.AWBDocumentValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.BlacklistStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
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
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeHistoryVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestApproveVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestForOALVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockReuseHistoryVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.TransitStockVO;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.report.exception.ReportGenerationException;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.xibase.server.framework.audit.AuditException;
import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;
import com.ibsplc.xibase.server.framework.ejb.AbstractFacadeEJB;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.requestprocessor.eblflow.Flow;
import com.ibsplc.xibase.server.framework.requestprocessor.eblflow.execute.ExecutableFlow;
import com.ibsplc.xibase.server.framework.requestprocessor.eblflow.execute.ReflectionInvoker;
import com.ibsplc.xibase.server.framework.requestprocessor.eblflow.execute.ReflectionInvokerWithCache;
import com.ibsplc.xibase.server.framework.requestprocessor.eblflow.execute.SimpleClassFinder;
import com.ibsplc.xibase.server.framework.requestprocessor.eblflow.execute.SpringAdapterBeanLookup;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1358
 * @ejb.bean description="StockControlDefaultsServices"
 *           display-name="StockControlDefaultsServices"
 *           jndi-name="com.ibsplc.icargo.services.stockcontrol.defaults.StockControlDefaultsServicesHome"
 *           name="StockControlDefaultsServices" type="Stateless"
 *           view-type="remote"
 *           remote-business-interface="com.ibsplc.icargo.business.stockcontrol.defaults.StockControlDefaultsBI"
 *
 * @ejb.transaction type="Supports"
 *
 */
public class StockControlDefaultsServicesEJB extends AbstractFacadeEJB
		implements StockControlDefaultsBI {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Log log = LogFactory.getLogger("STOCKCONTROL");

	

	
	/**
	 * This method is used to validate the stock holder codes. Used by
	 * saveStockHolder method to validate the approver codes
	 *
	 * @param companyCode
	 * @param stockHolderCodes -
	 *            Collection <String>
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException
	 */
	public void validateStockHolders(String companyCode,
			Collection<String> stockHolderCodes) throws RemoteException,
			SystemException, StockControlDefaultsBusinessException {
		try {
			new StockController().validateStockHolders(companyCode, stockHolderCodes);
		} catch (InvalidStockHolderException invalidStockHolderException) {
			for (ErrorVO err : invalidStockHolderException.getErrors()) {
				// System.out.println("-Inside EJB exception---->>>"
				// + err.getErrorCode());
			}
			/*
			 * StockControlDefaultsBusinessException
			 * stockControlDefaultsBusinessException = new
			 * StockControlDefaultsBusinessException
			 * (invalidStockHolderException);
			 */
			/*
			 * for(ErrorVO err : stockControlDefaultsBusinessException.
			 * getErrors()){ System.out.println("-Inside EJB exception2---->>>"+
			 * err.getErrorCode()); }
			 */
			throw new StockControlDefaultsBusinessException(
					invalidStockHolderException);
		}
	}
	

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
	public void saveStockHolderDetails(StockHolderVO stockHolderVO)
			throws RemoteException, SystemException,
			StockControlDefaultsBusinessException, RangeExistsException {

		log.log(Log.FINE, ">>>>>>>>>>>>>>>>>>>>>>>inside ejb>>>>>");
		new StockController().saveStockHolderDetails(stockHolderVO);

	}

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
	public void deleteStockHolder(StockHolderDetailsVO stockHolderdetailsVO)
			throws RemoteException, SystemException, RangeExistsException,
			CancelStockHolderException,StockControlDefaultsBusinessException {
		new StockController().deleteStockHolder(stockHolderdetailsVO);
	}

	/**
	 * Used to list the details of a selected stockholder
	 *
	 * @param companyCode
	 * @param stockHolderCode
	 * @return StockHolderVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public StockHolderVO findStockHolderDetails(String companyCode,
			String stockHolderCode) throws RemoteException, SystemException {
		return new StockController().findStockHolderDetails(companyCode,
				stockHolderCode);
	}

	/**
	 * This method finds details for stock holders which meet the filter
	 * criteria and all stock holders who fall under them.
	 *
	 * @param filterVO
	 * @return Collection
	 * @throws RemoteException
	 * @throws SystemException
	 *             Collection<StockHolderDetailsVO>
	 */
	public Page<StockHolderDetailsVO> findStockHolders(
			StockHolderFilterVO filterVO) throws RemoteException,
			SystemException {
		log.entering("StockControlDefaultsServicesEJB",
		"findStockHolders");
		return new StockController().findStockHolders(filterVO);
	}

	/**
	 * This method is invoked to obtain the stock holder lov
	 *
	 * @param filterVO
	 * @param displayPage
	 * @return Page<StockHolderLovVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Page<StockHolderLovVO> findStockHolderLov(
			StockHolderLovFilterVO filterVO, int displayPage)
			throws RemoteException, SystemException {
		return new StockController().findStockHolderLov(filterVO, displayPage);
	}

	/**
	 * @author A-1883 This method will validate stockholdertype and code
	 * @param stockRequestVO
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException
	 */
	public void validateStockHolderTypeCode(StockRequestVO stockRequestVO)
			throws RemoteException, SystemException,
			StockControlDefaultsBusinessException {
		try {
			// log.entering("StockControlDefaultsServicesEJB",
			// "validateStockHolderTypeCode");
			new StockController().validateStockHolderTypeCode(stockRequestVO);
		} catch (InvalidStockHolderException invalidStockHolderException) {
			throw new StockControlDefaultsBusinessException(
					invalidStockHolderException);
		}
	}

	/**
	 * This method is used to create/modify/cancel stock request details based
	 * on operational flag
	 *
	 * @author A-1883
	 * @param stockRequestVO
	 * @return String
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public String saveStockRequestDetails(StockRequestVO stockRequestVO)
			throws RemoteException, SystemException {
		// log.entering("StockControlDefaultsServicesEJB",
		// "saveStockRequestDetails");
		StockController stockController = (StockController)SpringAdapter.
				getInstance().getBean("stockcontroldefaultsController");
		return stockController.saveStockRequestDetails(stockRequestVO);
		// log.exiting("StockControlDefaultsServicesEJB",
		// "saveStockRequestDetails");
	}

	/**
	 * This method will fetch the details of a particular stock request
	 *
	 * @param stockRequestFilterVO
	 * @return StockRequestVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public StockRequestVO findStockRequestDetails(
			StockRequestFilterVO stockRequestFilterVO) throws RemoteException,
			SystemException {
		log.entering("StockControlDefaultsServicesEJB",
				"findStockRequestDetails");
		return new StockController()
				.findStockRequestDetails(stockRequestFilterVO);
	}

	/**
	 * @author A-1883 This method will cancel a particular stock request
	 * @param stockRequestVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void cancelStockRequest(StockRequestVO stockRequestVO)
			throws RemoteException, SystemException {
		log.entering("StockControlDefaultsServicesEJB", "cancelStockRequest");
		new StockController().cancelStockRequest(stockRequestVO);
		log.exiting("StockControlDefaultsServicesEJB", "cancelStockRequest");
	}

	/**
	 * This method will fetch the stock requests satisfying the filter criteria
	 *
	 * @param stockRequestFilterVO
	 * @param displayPage
	 * @return Page<StockRequestVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Page<StockRequestVO> findStockRequests(
			StockRequestFilterVO stockRequestFilterVO, int displayPage)
			throws RemoteException, SystemException {
		log.entering("StockControlDefaultsServicesEJB", "findStockRequests");
		return new StockController().findStockRequests(stockRequestFilterVO,
				displayPage);

	}

	

	/**
	 * This method is used to approve stock requests
	 *
	 * @author A-1883
	 * @param stockRequestApproveVO
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException
	 */
	public void approveStockRequests(StockRequestApproveVO stockRequestApproveVO)
			throws RemoteException, SystemException,
			StockControlDefaultsBusinessException {
		log.entering("StockControlDefaultsServicesEJB", "approveStockRequests");
		try {
			new StockController().approveStockRequests(stockRequestApproveVO);
		} catch (StockNotFoundException stockNotFoundException) {
			throw new StockControlDefaultsBusinessException(
					stockNotFoundException);
		}
		log.exiting("StockControlDefaultsServicesEJB", "approveStockRequests");
	}

	/**
	 * This method is used to reject stock requests
	 *
	 * @author A-1883
	 * @param stockRequests
	 *            ,Collection <StockRequestVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void rejectStockRequests(Collection<StockRequestVO> stockRequests)
			throws RemoteException, SystemException {
		log.entering("StockControlDefaultsServicesEJB", "rejectStockRequests");
		new StockController().rejectStockRequests(stockRequests);
		log.exiting("StockControlDefaultsServicesEJB", "rejectStockRequests");
	}

	/**
	 * This method is used to Complete stock requests
	 *
	 * @author A-1883
	 * @param stockRequests ,
	 *            Collection <StockRequestVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void completeStockRequests(Collection<StockRequestVO> stockRequests)
			throws RemoteException, SystemException {
		log
				.entering("StockControlDefaultsServicesEJB",
						"completeStockRequests");
		new StockController().completeStockRequests(stockRequests);
		log.exiting("StockControlDefaultsServicesEJB", "completeStockRequests");
	}

	/**
	 * @param companyCode 
	 * @return Collection<StockHolderPriorityVO>
	 * @throws SystemException
	 * @throws RemoteException
	 *
	 */
	public Collection<StockHolderPriorityVO> findStockHolderTypes(
			String companyCode) throws SystemException, RemoteException {
		return new StockController().findStockHolderTypes(companyCode);
	}

	/**
	 * Audit for stockcontrol defaults subsystem It calls the audit method of
	 * the controller.
	 *
	 * @param auditVo
	 * @throws AuditException
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void audit(AuditVO auditVo) throws AuditException, RemoteException,
			SystemException {
		StockControlDefaultsAuditController stockControlDefaultsAuditController = new StockControlDefaultsAuditController();
		stockControlDefaultsAuditController.audit(auditVo);
	}

	/**
	 * @author A-1883
	 * @param rangeFilterVO
	 * @return Collection<RangeVO>
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	public Collection<RangeVO> findRanges(RangeFilterVO rangeFilterVO)
			throws SystemException, RemoteException,
			StockControlDefaultsBusinessException {
		// log.entering("StockControlDefaultsServicesEJB","findRanges");
		return new StockController().findRanges(rangeFilterVO);

	}

	/**
	 * This method is used to fetch the range details of a stockholder
	 *
	 * @author A-1883
	 * @param stockFilterVO
	 * @return StockRangeVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	public StockRangeVO viewRange(StockFilterVO stockFilterVO)
			throws SystemException, StockControlDefaultsBusinessException,
			RemoteException {
		log.entering("StockControlDefaultsServicesEJB", "viewRange");
		try {
			return new StockController().viewRange(stockFilterVO);
		} catch (StockNotFoundException stockNotFoundException) {
			throw new StockControlDefaultsBusinessException(
					stockNotFoundException);
		}
	}

	/**
	 * @param stockFilterVO
	 * @return Collection<MonitorStockVO>
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException
	 */
	public Page<MonitorStockVO> monitorStock(StockFilterVO stockFilterVO)
			throws RemoteException, SystemException,
			StockControlDefaultsBusinessException {
		// log.entering("StockControlDefaultsServicesEJB","monitorStock");
		return new StockController().monitorStock(stockFilterVO);
	}

	/**
	 * @author A-1944
	 * @param stockFilterVO
	 * @return MonitorStockVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public MonitorStockVO findMonitoringStockHolderDetails(StockFilterVO stockFilterVO)
	throws RemoteException, SystemException, StockControlDefaultsBusinessException {
		return new StockController().findMonitoringStockHolderDetails(stockFilterVO);
}
	/**
	 * This method will find the blacklisted stock matching the filter criteria
	 *
	 * @param stockFilterVO
	 * @param displayPage
	 * @return Page<BlacklistStockVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Page<BlacklistStockVO> findBlacklistedStock(
			StockFilterVO stockFilterVO, int displayPage)
			throws RemoteException, SystemException {
		return new StockController().findBlacklistedStock(stockFilterVO, displayPage);
	}

	
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
	public Collection<String> findStockHolderCodes(String companyCode,
			Collection<String> privileges) throws RemoteException,
			SystemException {
		return new StockController().findStockHolderCodes(companyCode, privileges);
	}

	/**
	 * Method for allocating stock
	 *
	 * @param stockAllocationVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	public StockAllocationVO allocateStock(StockAllocationVO stockAllocationVO)
			throws SystemException, RemoteException,
			StockControlDefaultsBusinessException {
		StockAllocationVO stkAllocationVO = null;
		try {
			StockController stockController = (StockController)SpringAdapter.
			getInstance().getBean("stockcontroldefaultsController");
			stkAllocationVO = stockController.allocateStock(stockAllocationVO);
			log.log(Log.FINER,"\n\nEJB  EJB  EJB  EJB EJB  EJB  EJB  EJB \n");
			log.log(Log.INFO, "returning stockallocationvo from client : ",
					stkAllocationVO);
		} catch (FinderException finderException){
			throw new SystemException(finderException.getMessage(), finderException);
		} catch (RangeExistsException rangeExistsException) {
			throw new StockControlDefaultsBusinessException(
					rangeExistsException);
		} catch (StockNotFoundException stockNotFoundException) {
			throw new StockControlDefaultsBusinessException(
					stockNotFoundException);
		} catch (BlacklistedRangeExistsException blacklistedRangeExistsException) {
			throw new StockControlDefaultsBusinessException(
					blacklistedRangeExistsException);
		} catch (InvalidStockHolderException invalidStockHolderException) {
			throw new StockControlDefaultsBusinessException(
					invalidStockHolderException);
		}
		return stkAllocationVO;
	}

	/**
	 * Method for blacklisting a stock
	 *
	 * @param blacklistStockVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	public void blacklistStock(BlacklistStockVO blacklistStockVO)
			throws SystemException, RemoteException,
			StockControlDefaultsBusinessException {
		try {
			StockController stockController = (StockController)SpringAdapter.getInstance().getBean("stockcontroldefaultsController");
			stockController.blacklistStock(blacklistStockVO);
		} catch (StockNotFoundException stockNotFoundException) {
			throw new StockControlDefaultsBusinessException(
					stockNotFoundException);
		} catch (BlacklistedRangeExistsException blacklistedRangeExistsException) {
			throw new StockControlDefaultsBusinessException(
					blacklistedRangeExistsException);
		}
	}

	/**
	 * Method for revoking the blacklisted stock
	 *
	 * @param blacklistStockVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	public void revokeBlacklistStock(BlacklistStockVO blacklistStockVO)
			throws SystemException, RemoteException,
			StockControlDefaultsBusinessException {
		try {
			StockController stockController = (StockController)SpringAdapter.getInstance().getBean("stockcontroldefaultsController");
			stockController.revokeBlacklistStock(blacklistStockVO);
		} catch (FinderException finderException){
			throw new SystemException(finderException.getMessage(), finderException);
		} catch (StockNotFoundException stockNotFoundException) {
			throw new StockControlDefaultsBusinessException(
					stockNotFoundException);
		} catch (BlacklistedRangeExistsException blacklistedRangeExistsException) {
			throw new StockControlDefaultsBusinessException(
					blacklistedRangeExistsException);
		} catch (RangeExistsException rangeExistsException) {
			throw new StockControlDefaultsBusinessException(
					rangeExistsException);
		} catch (InvalidStockHolderException invalidStockHolderException) {
			throw new StockControlDefaultsBusinessException(
					invalidStockHolderException);
		}
	}

	/**
	 * @param companyCode
	 * @param stockHolderCode
	 * @param docType
	 * @param docSubType
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	public void checkStock(String companyCode, String stockHolderCode,
			String docType, String docSubType) throws SystemException,
			RemoteException, StockControlDefaultsBusinessException {
		try {
			new StockController().checkStock(companyCode, stockHolderCode, docType,
					docSubType);
		} catch (StockNotFoundException stockNotFoundException) {
			throw new StockControlDefaultsBusinessException(
					stockNotFoundException);
		}
	}

	/**
	 *
	 * @param companyCode
	 * @param stockHolderCodes
	 * @return Collection<StockHolderPriorityVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<StockHolderPriorityVO> findPriorities(String companyCode,
			Collection<String> stockHolderCodes) throws SystemException,
			RemoteException {
		return new StockController().findPriorities(companyCode, stockHolderCodes);
	}

	/**
	 *
	 * @param companyCode
	 * @param stockHolderCode
	 * @param docType
	 * @param docSubType
	 * @return String
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public String findApproverCode(String companyCode, String stockHolderCode,
			String docType, String docSubType) throws SystemException,
			RemoteException {
		return new StockController().findApproverCode(companyCode, stockHolderCode,
				docType, docSubType);
	}



	/**
	 * @author A-1883
	 * @param companyCode
	 * @param doctype
	 * @param documentNumber
	 * @return Boolean
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Boolean checkForBlacklistedDocument(String companyCode,
			String doctype, String documentNumber) throws RemoteException,
			SystemException {
		log.log(Log.FINE, "Entering the StockControl Service EJB");
		return new StockController().checkForBlacklistedDocument(companyCode,
				doctype, documentNumber);
	}

	// *************************************************************************
	// ************************* R2 coding starts ******************************
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
	public StockAllocationVO findStockForAirline(StockFilterVO stockFilterVO)
			throws SystemException, RemoteException {
		log.log(Log.FINE, "----stockFilterVO-----", stockFilterVO);
		return new StockController().findStockForAirline(stockFilterVO);
	}

    /**
     * @param reserveAWBVO
     * @return
     * @throws SystemException
     * @throws RemoteException
     * @throws StockControlDefaultsBusinessException
     */
    public ReserveAWBVO reserveDocument(ReserveAWBVO reserveAWBVO)
			throws SystemException, RemoteException, StockControlDefaultsBusinessException{
    	log.log(Log.FINE, "----reserveAWBVO-----", reserveAWBVO);
			try {
				return new ReservationController().reserveDocument(reserveAWBVO);
			} catch (BlacklistedRangeExistsException blacklistedRangeExistsException) {
				throw new StockControlDefaultsBusinessException(blacklistedRangeExistsException.getMessage(),blacklistedRangeExistsException);
			} catch (StockNotFoundException stockNotFoundException) {
				throw new StockControlDefaultsBusinessException(stockNotFoundException.getMessage(),stockNotFoundException);
			} catch (CreateException createException) {
				throw new SystemException(createException.getErrorCode(),createException);
			} catch (FinderException finderException){
				throw new SystemException(finderException.getErrorCode(),finderException);
			} catch (RangeExistsException rangeExistsException){
				throw new StockControlDefaultsBusinessException(rangeExistsException.getMessage(),rangeExistsException);
			} catch (InvalidStockHolderException invalidStockHolderException){
				throw new StockControlDefaultsBusinessException(invalidStockHolderException.getMessage(),invalidStockHolderException);
			} catch (ReservationException reservationException){
				throw new StockControlDefaultsBusinessException(reservationException);
			}
    }

	// Added by Sinoob ends

	/**
	 * @author A-1863
	 * @param stockFilterVO
	 * @return Page<StockVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<StockVO> findStockList(StockFilterVO stockFilterVO)
			throws RemoteException, SystemException {
		return new StockController().findStockList(stockFilterVO);
	}

	/**
	 * @author A-1863
	 * @param stockRequestForOALVO
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void saveStockRequestForOAL(StockRequestForOALVO stockRequestForOALVO)
			throws SystemException, RemoteException {
		log.entering("StockControlDefaultsServicesEJB",
				"saveStockRequestForOAL");
		new StockController().saveStockRequestForOAL(stockRequestForOALVO);
	}

	/**
	 * @author A-1863
	 * @param stockFilterVO
	 * @return Collection<StockRequestForOALVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<StockRequestForOALVO> findStockRequestForOAL(
			StockFilterVO stockFilterVO) throws SystemException,
			RemoteException {
		log.entering("StockControlDefaultsServicesEJB",
				"findStockRequestForOAL");
		return new StockController().findStockRequestForOAL(stockFilterVO);
	}

	/**
	 * @param reservationFilterVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<ReservationVO> findReservationListing(
			ReservationFilterVO reservationFilterVO) throws RemoteException,
			SystemException {
		return new ReservationController()
				.findReservationListing(reservationFilterVO);
	}

	/**
	 * method to cancelReservation
	 *
	 * @param reservationVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	public void cancelReservation(Collection<ReservationVO> reservationVO)
			throws SystemException, RemoteException, StockControlDefaultsBusinessException {
		ReservationController reservationController = new ReservationController();
		try {
			reservationController.cancelReservation(reservationVO);
		} catch (ReservationException reservationException) {
			throw new StockControlDefaultsBusinessException(reservationException);
		} catch(RemoveException removeException){
			throw new SystemException(removeException.getErrorCode(),removeException);
		}

	}

	/**
	 * method to extendReservation
	 *
	 * @param reservationVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	public void extendReservation(Collection<ReservationVO> reservationVO)
			throws SystemException, RemoteException,
					StockControlDefaultsBusinessException {
		ReservationController reservationController = new ReservationController();
		try {
			reservationController.extendReservation(reservationVO);
		} catch (ReservationException e) {
			throw new StockControlDefaultsBusinessException(e);
		}

	}

	/**
	 * @param reservationFilterVO
	 * @throws StockControlDefaultsBusinessException
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void expireReservations(ReservationFilterVO reservationFilterVO)
		throws StockControlDefaultsBusinessException, SystemException, RemoteException{
		try {
			new ReservationController().expireReservations(reservationFilterVO);
		} catch (ReservationException reservationException) {
			throw new StockControlDefaultsBusinessException(reservationException);
		} catch(RemoveException removeException){
			throw new SystemException(removeException.getErrorCode(),removeException);
		}
	}

	/**
	 * Method to find document details
	 *
	 * @param companyCode
	 * @param airlineIdentifier
	 * @param documentNumber
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public StockRequestVO findDocumentDetails(String companyCode, int airlineIdentifier,
				String documentNumber) throws RemoteException, SystemException{
		 log.entering("StockControlDefaultsServicesEJB","findDocumentDetails");
		 return new StockController().findDocumentDetails(companyCode,
				 							airlineIdentifier,documentNumber);
	 }
	/**
	 * @author a-1885
	 * @param filterVo
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void sendReorderMessages(InformAgentFilterVO filterVo)throws
    SystemException,RemoteException{
		new StockController().sendReorderMessages(filterVo);
	}


	/*
	 * added by Sinoob T S
	 */
	/**
	 * @param stockAgentVOs
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	public void saveStockAgentMappings(Collection<StockAgentVO> stockAgentVOs)
 			throws SystemException, RemoteException,
 				StockControlDefaultsBusinessException{
		log.log(Log.FINER, "stockAgentVOs : ", stockAgentVOs);
		try {
			new StockController().saveStockAgentMappings(stockAgentVOs);
		} catch (CreateException e) {
			throw new SystemException(e.getErrorCode(), e);
		} catch (FinderException e) {
			throw new SystemException(e.getErrorCode(), e);
		} catch (RemoveException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}

	/**
	 * @param stockAgentFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Page<StockAgentVO> findStockAgentMappings(
			StockAgentFilterVO stockAgentFilterVO)
		throws SystemException, RemoteException{

		log.log(Log.INFO, "stockAgentFilterVO : ", stockAgentFilterVO);
		return new StockController().findStockAgentMappings(stockAgentFilterVO);
	}

	/**
	 * @param documentFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	public DocumentValidationVO validateDocument(
			DocumentFilterVO documentFilterVO)
		throws SystemException, RemoteException, StockControlDefaultsBusinessException{
		log.log(Log.INFO, "@@@@ DocumentFilterVO : ", documentFilterVO);
		return new StockController().validateDocument(documentFilterVO);
	}

	/**
	 * @param filterVO
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	public void deleteDocumentFromStock(DocumentFilterVO filterVO)
		throws SystemException, RemoteException, StockControlDefaultsBusinessException{
		log.log(Log.INFO, "#### DocumentFilterVO : ", filterVO);
		new StockController().deleteDocumentFromStock(filterVO);
	}

	/**
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	public DocumentValidationVO findNextDocumentNumber(
			DocumentFilterVO filterVO)
	throws SystemException, RemoteException, StockControlDefaultsBusinessException{
		log.log(Log.INFO, "$$$$ DocumentFilterVO : ", filterVO);
		return new StockController().findNextDocumentNumber(filterVO);
	}
	/*
	 * added by Sinoob T S ends
	 */

	/**
	 * @param stockAllocationVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public void returnDocumentToStock(StockAllocationVO stockAllocationVO)
	throws SystemException, RemoteException{
		new StockController().returnDocumentToStock(stockAllocationVO);
	}


		/**
		 * @author a-2870
		 * @param aWBDocumentValidationVO
		 * @throws SystemException
		 * @throws RemoteException
		 * @throws StockControlDefaultsBusinessException
		 */
		public void validateAgentForStockHolder(AWBDocumentValidationVO aWBDocumentValidationVO)
		throws SystemException, RemoteException, StockControlDefaultsBusinessException {
			log.entering("StockControlDefaultsServicesEJB","validateAgentForStockHolder");
			new StockController().validateAgentForStockHolder(aWBDocumentValidationVO);
	}

	/*
		 * added by Sinoob T S ends
		 */
		/**
		 * @throws StockControlDefaultsBusinessException 
		 *
		 */
		public void autoStockDeplete(StockDepleteFilterVO stockDepleteFilterVO)
		throws SystemException,RemoteException, StockControlDefaultsBusinessException{
			StockController stockController = (StockController)SpringAdapter.
			getInstance().getBean("stockcontroldefaultsController");
			stockController.autoDepleteStock(stockDepleteFilterVO);
		}

	/**
		 * @param reportSpec
		 * @return
		 * @throws SystemException
		 * @throws ReportGenerationException
		 */
		public Map<String, Object> generateStockListReport(ReportSpec reportSpec)
		throws SystemException, ReportGenerationException,RemoteException {
			return new StockController().generateStockListReport(reportSpec);
		}

		/**
		 * @param reportSpec
		 * @return Map<String, Object>
		 * @throws SystemException
		 * @throws ReportGenerationException
		 * @throws RemoteException
		 */
		public Map<String, Object> generateReservationListReport(ReportSpec reportSpec)
		throws SystemException, ReportGenerationException,RemoteException{
			return new ReservationController().generateReservationListReport(reportSpec);
		}

		/**
		 * @param reportSpec
		 * @return
		 * @throws SystemException
		 * @throws ReportGenerationException
		 * @throws RemoteException
		 */
		public Map<String, Object> generateReserveAWBReport(ReportSpec reportSpec)
		throws SystemException, ReportGenerationException,RemoteException{
			return new ReservationController().generateReserveAWBReport(reportSpec);
	}

	/**
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 * @throws ReportGenerationException
	 */
	public Map<String, Object> generateAWBStockReport(ReportSpec reportSpec)
	throws SystemException, ReportGenerationException,RemoteException {
		return new StockController().generateAWBStockReport(reportSpec);
	}
	 /**
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	public StockDetailsVO findCustomerStockDetails(
			StockDetailsFilterVO stockDetailsFilterVO)
	throws SystemException, RemoteException, StockControlDefaultsBusinessException{
		log.log(Log.INFO, "$$$$ stockDetailsFilterVO : ", stockDetailsFilterVO);
		return new StockController().findCustomerStockDetails(stockDetailsFilterVO);
	}
	/*
	 *following method Added by Chippy for CR1878
	 * */
	public Page<StockDetailsVO> listStockDetails(
			StockDetailsFilterVO stockDetailsFilterVO) throws RemoteException,
			SystemException {
		log.entering("StockControlDefaultsServicesEJB",
		"listStockDetails");
		return new StockController().listStockDetails(stockDetailsFilterVO);
		
	}
	public void updateStockUtilization(String source)
	throws SystemException,RemoteException{
		new StockController().updateStockUtilization(source);
	}

	/**
	 * This method will find the stock history matching the filter criteria
	 * @param stockRangeHistoryVO
	 * @return Collection<StockRangeHistoryVO>
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Collection<StockRangeHistoryVO> findStockRangeHistory(
			StockRangeFilterVO stockRangeFilterVO)
			throws RemoteException, SystemException {
				
			return new  StockController().findStockRangeHistory(stockRangeFilterVO);
	}
	
	/**
	 * @author a-2870
	 * @param reportSpec
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public Map<String, Object> findStockRangeHistoryReport(ReportSpec reportSpec)
			throws RemoteException, SystemException {
		return new StockController().findStockRangeHistoryReport(reportSpec);
	}
	
		
		
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
			throws RemoteException, SystemException {
				
			return new  StockController().findTransitStocks(stockRequestFilterVO);
	}

	

	 /**
 	 * Method for listing transit stock details
 	 * @author A-4443
 	 * @param TransitStockVO 
 	 * @throws SystemException
 	 * @throws RemoteException
 	 * @throws StockControlDefaultsBusinessException
 	 */
	public Collection<TransitStockVO> confirmStockRange(
			Collection<TransitStockVO> transitStockVOs)
			throws RemoteException, SystemException {
			try {
				return new  StockController().confirmStockRange(transitStockVOs);
			} catch (BlacklistedRangeExistsException e) {
				// TODO Auto-generated catch block
			log.log(Log.SEVERE, "BlacklistedRangeExistsException");
			} catch (RangeExistsException e) {
				// TODO Auto-generated catch block
				log.log(Log.SEVERE, "RangeExistsException");
			} catch (InvalidStockHolderException e) {
				// TODO Auto-generated catch block
				log.log(Log.SEVERE, "InvalidStockHolderException");
			} catch (StockNotFoundException e) {
				// TODO Auto-generated catch block
				log.log(Log.SEVERE, "StockNotFoundException");
			} catch (StockControlDefaultsBusinessException e) {
				// TODO Auto-generated catch block
				log.log(Log.SEVERE, "StockControlDefaultsBusinessException");
			} catch (FinderException e) {
				// TODO Auto-generated catch block
				log.log(Log.SEVERE, "FinderException");
			}
			return null;
	}
	
	
	 /**
 	 * Method for updating missing stock details
 	 * @author A-4443
 	 * @param TransitStockVO 
 	 * @throws SystemException
 	 * @throws RemoteException
 	 * @throws StockControlDefaultsBusinessException
 	 */
	public Collection<TransitStockVO> updateMissingStock(
			TransitStockVO transitStockVO)
			throws RemoteException, SystemException {
			
				try {
					return new  StockController().updateMissingStock(transitStockVO);
				} catch (StockControlDefaultsBusinessException e) {
					// TODO Auto-generated catch block
					log.log(Log.SEVERE, "StockControlDefaultsBusinessException");
				} catch (BlacklistedRangeExistsException e) {
					// TODO Auto-generated catch block
					log.log(Log.SEVERE, "BlacklistedRangeExistsException");
				} catch (RangeExistsException e) {
					// TODO Auto-generated catch block
					log.log(Log.SEVERE, "RangeExistsException");
				} catch (InvalidStockHolderException e) {
					// TODO Auto-generated catch block
					log.log(Log.SEVERE, "InvalidStockHolderException");
				} catch (StockNotFoundException e) {
					// TODO Auto-generated catch block
					log.log(Log.SEVERE, "StockNotFoundException");
				} catch (FinderException e) {
					// TODO Auto-generated catch block
					log.log(Log.SEVERE, "FinderException");
				}
				return null;
			
	}
	/**
	 * @author A-2881
	 * 
	 */
	public BlacklistStockVO validateStockForVoiding(
			BlacklistStockVO blacklistStockVO) throws SystemException,
			RemoteException, StockControlDefaultsBusinessException {
		return new StockController().validateStockForVoiding(blacklistStockVO);
	}
    
	/**
	 * @author A-2881
	 */
	public void voidStock(BlacklistStockVO blacklistStockVO)
			throws SystemException, RemoteException,
			StockControlDefaultsBusinessException, InvalidStockHolderException,
			StockNotFoundException, BlacklistedRangeExistsException {
		new StockController().voidStock(blacklistStockVO);
	}
	
	/**
	 * @author A-2881
	 */
	public Page<StockRangeHistoryVO> findStockRangeHistoryForPage(
			StockRangeFilterVO stockRangeFilterVO)
			throws RemoteException,
			SystemException {

		return new StockController()
		.findStockRangeHistoryForPage(stockRangeFilterVO);
	}

	/**
	 * @author A-5103
	 */
	public void saveStockRangeHistory(Collection<StockRangeHistoryVO> stockRangeHistoryVOs) 
	throws RemoteException,SystemException {

		 new StockController()
		.saveStockRangeHistory(stockRangeHistoryVOs);
	}
	

	//Added by A-5133 for bug ICRD-16709 starts
	public void createHistory(StockAllocationVO stockAllocationVo,String status) 
	throws RemoteException, SystemException
	{		
		new StockController().createHistory(stockAllocationVo,StockAllocationVO.MODE_VOID);		
	}
	//Added by A-5133 for bug ICRD-16709 end

	/**
	 * @author A-4777
	 */
	public Map<String, Object> generateStockAllocationReport(ReportSpec reportSpec)
	throws SystemException, ReportGenerationException,RemoteException {
		return new StockController().generateStockAllocationReport(reportSpec);
	}
	/**
	 * @author A-4777
	 * @param documentNumber
	 * @param offset
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public String getNextDocument(String documentNumber,int offset) throws RemoteException, SystemException {
		
		 return new StockController().getNextDocument(documentNumber,offset);
		
	}	 
    /**
	 * @author A-4823
	 * @param documentFilterVO
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 * @throws SystemException
	 */
	public DocumentValidationVO validateDocumentForWS(
			DocumentFilterVO documentFilterVO)
		throws SystemException, RemoteException, StockControlDefaultsBusinessException{
		log.log(Log.INFO, "@@@@ DocumentFilterVO : ", documentFilterVO);
		return new StockController().validateDocumentForWS(documentFilterVO);
	}

	/**
	 *
	 * @param documentFilterVO
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 */
	public DocumentValidationVO validateDocumentStockForWS(
			DocumentFilterVO documentFilterVO)
			throws SystemException, RemoteException, StockControlDefaultsBusinessException{
		log.log(Log.INFO, "@@@@ DocumentFilterVO : ", documentFilterVO);
		return new StockController().validateDocumentStockForWS(documentFilterVO);
	}
	  /**
	 * @author A-6767
	 * @param  stockAllocationVO
	 * @param  status
	 * @throws RemoteException
	 * @throws StockControlDefaultsBusinessException
	 * @throws SystemException
	 */
	public void saveStockUtilisation(
			StockAllocationVO stockAllocationVO,String status)
		throws SystemException, RemoteException, StockControlDefaultsBusinessException{
		log.log(Log.INFO, "@@@@ stockAllocationVO : ", stockAllocationVO);
		new StockController().saveStockUtilisation(stockAllocationVO,status);
	}

	
	@Override
	public String findAutoPopulateSubtype(DocumentFilterVO documentFilterVO)
			throws SystemException, RemoteException,
			StockControlDefaultsBusinessException {
		log.log(Log.INFO, "findAutoPopulateSubtype ", "entry");
		return new StockController().findAutoPopulateSubtype(documentFilterVO);
	}

	@Override
	public void deleteStock(Collection<RangeVO> rangeVOs)
			throws SystemException, RemoteException,
			StockControlDefaultsBusinessException {
		log.log(Log.INFO, "deleteStock ", "entry");
		new StockController().deleteStock(rangeVOs);
	}
	public String saveStockDetails(StockAllocationVO stockAllocationVO)
		throws SystemException, RemoteException {
		log.log(Log.INFO, "@@@@ saveStockDetails : ", stockAllocationVO);
		return new StockController().saveStockDetails(stockAllocationVO);
	}
	
	public boolean isStockDetailsExists(DocumentFilterVO documentFilterVO)
		throws SystemException, RemoteException {
		log.log(Log.INFO, "@@@@ isStockDetailsExists : ", documentFilterVO);
		return new StockController().isStockDetailsExists(documentFilterVO);
	}
	public Page<RangeVO> findAvailableRanges(StockFilterVO stockFilterVO)
			throws RemoteException,SystemException {
		return new StockController()
		.findAvailableRanges(stockFilterVO);
	}
	public int findTotalNoOfDocuments(StockFilterVO stockFilterVO)
			throws RemoteException,SystemException {
		return new StockController()
		.findTotalNoOfDocuments(stockFilterVO);
	}
	
	@Override
    public Serializable doFlow(Flow flow) throws BusinessException, SystemException, RemoteException {
       boolean disableMethodCache= Boolean.getBoolean("xibase.eblflow.methodcache.disabled");
       com.ibsplc.xibase.server.framework.requestprocessor.eblflow.execute.Invoker invoker = 
           ((com.ibsplc.xibase.server.framework.requestprocessor.eblflow.execute.Invoker) (disableMethodCache ? ((com.ibsplc.xibase.server.framework.requestprocessor.eblflow.execute.Invoker) (new ReflectionInvoker()))
           : ((com.ibsplc.xibase.server.framework.requestprocessor.eblflow.execute.Invoker) (new ReflectionInvokerWithCache()))));
       log.log(Log.INFO,String.format("Using Invoker %s\n",new Object[] { invoker.getClass().getName() }));
       ExecutableFlow eflow = new ExecutableFlow(flow, invoker,new SimpleClassFinder(), new SpringAdapterBeanLookup());
       Serializable answer = (Serializable) eflow.execute();
       log.log(Log.INFO,String.format("Flow returns %s \n",new Object[] { answer }));
       return answer;
    }


	public AirlineValidationVO validateNumericCode(String companyCode,
			String shipmentPrefix)
		throws SystemException, RemoteException, StockControlDefaultsBusinessException{
		return new StockController().validateNumericCode(companyCode,shipmentPrefix);
	}


	@Override
	public void saveStockReuseHistory(Collection<StockReuseHistoryVO> stockReuseHistoryVOs)
			throws SystemException, RemoteException {
		new StockController().saveStockReuseHistory(stockReuseHistoryVOs);
	}
	public void removeStockReuseHistory(StockReuseHistoryVO stockReuseHistoryVO)
			throws SystemException, RemoteException {
		new StockController().removeStockReuseHistory(stockReuseHistoryVO);
}
	public void updateStockReuseHistory(Collection<StockReuseHistoryVO> stockReuseHistoryVOs)
					throws SystemException, RemoteException {
				new StockController().updateStockReuseHistory(stockReuseHistoryVOs);
		
	}
	
	public void doAWBReUseRestrictionCheckEnhanced(DocumentFilterVO documentFilterVO)throws SystemException, RemoteException{
		new StockController().doAWBReUseRestrictionCheckEnhanced(documentFilterVO);
	}
}
