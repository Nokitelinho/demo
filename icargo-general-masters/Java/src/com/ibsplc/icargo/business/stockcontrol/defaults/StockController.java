/*
 * StockController.java Created on Jul 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults;

import static com.ibsplc.icargo.framework.util.time.LocalDate.CALENDAR_DATE_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.msgbroker.message.vo.MessageDespatchDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductStockVO;
import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.country.vo.CountryValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.shared.document.vo.SharedRangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.proxy.DocumentTypeProxy;
import com.ibsplc.icargo.business.stockcontrol.defaults.proxy.MsgbrokerMessageProxy;
import com.ibsplc.icargo.business.stockcontrol.defaults.proxy.OperationsShipmentProxy;
import com.ibsplc.icargo.business.stockcontrol.defaults.proxy.ProductsDefaultsProxy;
import com.ibsplc.icargo.business.stockcontrol.defaults.proxy.SharedAgentProxy;
import com.ibsplc.icargo.business.stockcontrol.defaults.proxy.SharedAirlineProxy;
import com.ibsplc.icargo.business.stockcontrol.defaults.proxy.SharedAreaProxy;
import com.ibsplc.icargo.business.stockcontrol.defaults.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.stockcontrol.defaults.proxy.SharedDocumentProxy;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.Reservation;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.ReservationException;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReserveAWBVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.AWBDocumentValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.AgentDetailVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.BlacklistStockAuditVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.BlacklistStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.InformAgentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.MissingStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.MonitorStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAgentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAgentVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAuditVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDepleteFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDetailsFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDetailsVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderAuditVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderDetailsVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderLovFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderLovVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderStockDetailsVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeHistoryVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeUtilisationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestApproveVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestForOALVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockReuseHistoryVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.TransitStockVO;
import com.ibsplc.icargo.framework.event.annotations.Raise;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.report.agent.ReportAgent;
import com.ibsplc.icargo.framework.report.exception.ReportGenerationException;
import com.ibsplc.icargo.framework.report.vo.ReportMetaData;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.audit.util.AuditUtils;
import com.ibsplc.xibase.server.framework.audit.vo.AuditAction;
import com.ibsplc.xibase.server.framework.audit.vo.AuditFieldVO;
import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interceptor.Advice;
import com.ibsplc.xibase.server.framework.interceptor.Phase;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.util.error.ErrorUtils;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * Role class for the stockcontrol defaults subsystem
 *
 * @author A-1358
 *
 */
@Module("stockcontrol")
@SubModule("defaults")
public class StockController {

	private static final String BLANKSPACE = "";

	private static final String COUNTER_SALES = "stockcontrol.defaults.countersales";

	private Log log = LogFactory.getLogger("STOCK CONTROLLER");

	private static final String STOCK_DEFAULTS_STOCKHOLDERCODE = "stock.defaults.defaultstockholdercodeforcto";
	//private static final String STOCK_DEFAULTS_HOMEAIRLINECODE = "stock.defaults.homeairlinecode";
	private static final String STOCK_DEFAULTS_IS_INSTANTSTOCKDEPLETION_NEEDED = "stockcontrol.defaults.instantstockdepletionneeded";

	private static final String SYSTEMPARAMETERVALUE = "stock.defaults.defaultstockholdercodeforcto";

	private static final String STKLST_RPTID = "RPRSTK003";
	 private static final String YES = "Y";

	private static final String DOCSUBTYP_COMAT = "COMAT";

	private static final String STOCK_DEFAULTS_STOCKHOLDER_ACCOUNTING_PARAM = "stockcontrol.defaults.stockholderstockaccountingrequired";

	//added for icrd-3024 	starts 
    private static final String STOCK_DEFAULTS_CONFIRMATIONREQUIRED = "stockcontrol.defaults.isconfirmationprocessneeded";
	  //added for icrd-3024 	ends

    private static final String STOCK_DEFAULTS_STOCKINTRODUCTIONPERIOD = "stockcontrol.defaults.stockintroductionperiod";
    private static final String STOCK_DEFAULTS_ENABLESTOCKHISTORY="stockcontrol.defaults.enablestockhistory";
    public static final String STOCK_NOT_FOUND = "stockcontrol.defaults.stocknotfound";
    public static final String AWB_RESERVED_FOR_AIRPORT ="=AWB RESERVED FOR AIRPORT ";
    private static final String NO = "N";
	private static final String AWB_CANNOT_REUSE = "capacity.booking.awbcannotreuse";
	/**
	 * This method is used to validate the stock holder codes. Used by
	 * saveStockHolder method to validate the approver codes
	 *
	 * @author A-1885
	 * @param companyCode
	 * @param stockHolderCodes
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException
	 * @throws InvalidStockHolderException
	 */
	public void validateStockHolders(String companyCode,
			Collection<String> stockHolderCodes) throws SystemException,
			StockControlDefaultsBusinessException, InvalidStockHolderException {
		StockHolder.validateStockHolders(companyCode, stockHolderCodes);
		return;
	}

	/**
	 * This method is used to create/modify stockholder details. For create
	 * operationFlag is set as I and for modify, operationFlag is set as 'U'
	 *
	 * @author A-1885
	 * @param stockHolderVO
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException
	 */
	public void saveStockHolderDetails(StockHolderVO stockHolderVO)
			throws SystemException, StockControlDefaultsBusinessException {
		log.log(Log.FINE, ">>>>>>>>>>>inside Controller>>>>>>>>>>>>");
		//Collection<AuditFieldVO> auditFields = null;

		StockHolderAuditVO stockHolderAuditVo = new StockHolderAuditVO(
				StockHolderAuditVO.AUDIT_PRODUCTNAME,
				StockHolderAuditVO.AUDIT_MODULENAME,
				StockHolderAuditVO.AUDIT_ENTITY);

		if (StockHolderVO.OPERATION_FLAG_INSERT.equals(stockHolderVO
				.getOperationFlag())) {
			/*log.log(Log.FINE,"-----GOING TO CALL validateStockHolderCodeStationAgent");
			validateStockHolderCodeStationAgent(stockHolderVO);
			log.log(Log.FINE,"-----AFTER CALL validateStockHolderCodeStationAgent");*/
			if("H".equals(stockHolderVO.getStockHolderType())){
				log.log(Log.FINE,"-Inside Controller- going for dup-");
				/*if(StockHolder.checkForDuplicateHeadQuarter(stockHolderVO
						.getCompanyCode(),stockHolderVO.getStockHolderType())!=null){
					throw new StockControlDefaultsBusinessException
					(StockControlDefaultsBusinessException.HQ_ALREADY_EXIST);
				}*/
				for (StockVO stockVO : stockHolderVO.getStock()) {
					if(StockHolder.findHeadQuarterDetails(stockHolderVO.getCompanyCode(),stockVO.getAirlineIdentifier()
							,stockVO.getDocumentType(),stockVO.getDocumentSubType())!=null){
						throw new StockControlDefaultsBusinessException
						(StockControlDefaultsBusinessException.HQ_ALREADY_EXIST);
					}

				}
			}
//			--------------Added By A-2434 begins for mapping the stock holder as agent
			else if("A".equalsIgnoreCase(stockHolderVO.getStockHolderType())){
				SharedAgentProxy proxy = new SharedAgentProxy();
				try {
					//Checking whether the stockholder of type Agent is a valid Agent
					if(proxy.findAgentDetails(stockHolderVO.getCompanyCode(),stockHolderVO.getStockHolderCode())!=null){
						log.log(Log.INFO,"****************Agent is found. Going to map the stockholder as agent*************");
						Collection<StockAgentVO> stockAgentVOs = new ArrayList<StockAgentVO>();
						StockAgentVO stockAgentVO = new StockAgentVO();
						stockAgentVO.setAgentCode(stockHolderVO.getStockHolderCode());
						stockAgentVO.setStockHolderCode(stockHolderVO.getStockHolderCode());
						stockAgentVO.setCompanyCode(stockHolderVO.getCompanyCode());
						stockAgentVO.setLastUpdateUser(stockHolderVO.getLastUpdateUser());
						stockAgentVO.setOperationFlag(StockHolderVO.OPERATION_FLAG_INSERT);
						stockAgentVOs.add(stockAgentVO);
						try {
							//Maps the stock holder to agent
							saveStockAgentMappings(stockAgentVOs);
						} catch (StockControlDefaultsBusinessException e) {
							// To be reviewed Auto-generated catch block
//printStackTraccee()();
						} catch (CreateException e) {
							// To be reviewed Auto-generated catch block
//printStackTraccee()();
						} catch (FinderException e) {
							// To be reviewed Auto-generated catch block
//printStackTraccee()();
						} catch (RemoveException e) {
							// To be reviewed Auto-generated catch block
//printStackTraccee()();
						}
					}
				} catch (ProxyException e) {
					log.log(Log.ALL,"***************Proxy Exception *********");
				} catch (SystemException e) {
					log.log(Log.ALL,"***************System Exception *********");
				}
			}
//			--------------Added By A-2434 ends
			try {
					StockHolder stockHolder = null;
				try {
					stockHolder = StockHolder.find(stockHolderVO
							.getCompanyCode(), stockHolderVO
							.getStockHolderCode());
				} catch (InvalidStockHolderException invalidStockHolderException) {
					log.log(Log.FINE,">>>>>>No Stockholder found..going to create new>>>>>>>>>>>>");

				}
				log.log(Log.FINE, "Stockholder after find is----", stockHolder);
				if (stockHolder != null) {
					throw new StockControlDefaultsBusinessException(
							"stockcontrol.defaults.stockholderalreadyexists");
				}

				stockHolder = new StockHolder(stockHolderVO);

				stockHolderAuditVo = (StockHolderAuditVO)
				AuditUtils.populateAuditDetails(stockHolderAuditVo,stockHolder,true);
				auditStockHolderDetails(stockHolder,stockHolderAuditVo,
						StockHolderAuditVO.STOCKHOLDER_INSERT_ACTIONCODE);

				log.log(Log.FINE, "After saving the stockholder");
			} catch (SystemException systemException) {
				log.log(Log.FINE, "Inside controller Exception :----->",
						systemException.getError().getErrorCode());
				throw new StockControlDefaultsBusinessException(systemException
						.getError().getErrorCode());
			}
		} else if (StockHolderVO.OPERATION_FLAG_UPDATE.equals(stockHolderVO
				.getOperationFlag())) {
			try {
				StockHolder stockHolderUpdate = StockHolder.find(stockHolderVO
						.getCompanyCode(), stockHolderVO.getStockHolderCode());
				stockHolderAuditVo = (StockHolderAuditVO)
				AuditUtils.populateAuditDetails(stockHolderAuditVo,stockHolderUpdate,false);
				stockHolderUpdate.update(stockHolderVO);
				/* Added by A-2870 starts*/
				for (StockVO stockVO : stockHolderVO.getStock()) {
					if(StockVO.OPERATION_FLAG_INSERT.equals(stockVO.getOperationFlag())){
						Stock stockInsert = new Stock(stockHolderUpdate.getStockHolderPk().getCompanyCode(),
								stockHolderUpdate.getStockHolderPk().getStockHolderCode(),stockVO);
						stockHolderUpdate.getStock().add(stockInsert);
						StockAuditVO auditVo = new StockAuditVO
						(StockAuditVO.AUDIT_PRODUCTNAME,StockAuditVO.AUDIT_MODULENAME,
								StockAuditVO.AUDIT_ENTITY);
						auditVo = (StockAuditVO)AuditUtils.populateAuditDetails
						(auditVo,stockInsert,true);
						auditStockDetails(stockInsert,auditVo,AuditVO.CREATE_ACTION);
					}

				}
				/* Added by A-2870 Ends */
				//stockHolderUpdate.update(stockHolderVO);

				stockHolderAuditVo = (StockHolderAuditVO)AuditUtils.populateAuditDetails(stockHolderAuditVo,stockHolderUpdate,false);
				auditStockHolderDetails(stockHolderUpdate,stockHolderAuditVo,
						StockHolderAuditVO.STOCKHOLDER_DELETE_ACTIONCODE);

			} catch (RangeExistsException rangeExistsException) {
				for (ErrorVO errorVO : rangeExistsException.getErrors()) {
					throw new StockControlDefaultsBusinessException(errorVO
							.getErrorCode());
				}
			} catch (StockNotFoundException stockNotFoundException) {
				throw new StockControlDefaultsBusinessException(
						stockNotFoundException);
			} catch (InvalidStockHolderException invalidStockHolderException) {
				throw new StockControlDefaultsBusinessException(
						invalidStockHolderException);
			}
		}
	}


	/**
	 * This method is used to delete a stockholder. deletion is possible only if
	 * no stock exists under the stock holder dirctly.
	 *
	 * @author A-1885
	 *
	 * @param stockHolderDetailsVO
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException
	 * @throws RangeExistsException
	 * @throws CancelStockHolderException
	 */
	public void deleteStockHolder(StockHolderDetailsVO stockHolderDetailsVO)
			throws SystemException, StockControlDefaultsBusinessException,
			RangeExistsException, CancelStockHolderException  {
		//Collection<AuditFieldVO> auditFields = null;

		//Dummy check-in for IASCB-95800
		//Dummy check-in for IASCB-95792
		/*
		 * this condition checks whether any agent-mappings exists
		 * for the stock holder. if exists, exception is thrown,
		 * since the stock holder cannot be deleted
		 *
		 * added by sinoob t s
		 */
		boolean isMappingExisting = checkMappingsExist(
									stockHolderDetailsVO.getCompanyCode(),
									stockHolderDetailsVO.getStockHolderCode());

		if(isMappingExisting){
			log.log(Log.SEVERE, "Cannot delete StockHolder as Agent-Mapping exists ");
			throw new StockControlDefaultsBusinessException(
					StockControlDefaultsBusinessException.AGENT_MAPPING_EXISTS,
					new Object[] { stockHolderDetailsVO.getStockHolderCode() });
		}

		try {
			boolean isApprover = StockHolder.checkApprover(stockHolderDetailsVO.getCompanyCode(),
					stockHolderDetailsVO.getStockHolderCode());
			if(!isApprover){
				StockHolder stockHolder = StockHolder.find(stockHolderDetailsVO
						.getCompanyCode(), stockHolderDetailsVO
						.getStockHolderCode());
				stockHolder.setLastUpdateTime(stockHolder.getLastUpdateTime());
				stockHolder.remove();
				StockHolderAuditVO stockHolderAuditVo = new StockHolderAuditVO(
						StockHolderAuditVO.AUDIT_PRODUCTNAME,
						StockHolderAuditVO.AUDIT_MODULENAME,
						StockHolderAuditVO.AUDIT_ENTITY);
				stockHolderAuditVo = (StockHolderAuditVO)
				AuditUtils.populateAuditDetails(stockHolderAuditVo,stockHolder,
						false);
				auditStockHolderDetails(stockHolder,stockHolderAuditVo,
						AuditVO.DELETE_ACTION);

			}
			else{
				Object[] errorObj=new Object[1];
		    	errorObj[0]=stockHolderDetailsVO.getStockHolderCode();
	    		CancelStockHolderException cancelStockHolderException = new CancelStockHolderException(
	    				CancelStockHolderException.CANCEL_STOCKHOLDER,errorObj);
	    		throw cancelStockHolderException;
			}

		} catch (SystemException systemException) {
			throw new StockControlDefaultsBusinessException(systemException
					.getError().getErrorCode());
		} catch (RangeExistsException rangeExistsException) {
			for (ErrorVO errorVO : rangeExistsException.getErrors()) {
				throw new StockControlDefaultsBusinessException(errorVO
						.getErrorCode());
			}
		} catch (InvalidStockHolderException invalidStockHolderException) {
			throw new StockControlDefaultsBusinessException(
					invalidStockHolderException);
		}
	}

	/**
	 * This method validates whether any agent mapping exists for this
	 * stock holder
	 *
	 * added by Sinoob T S
	 *
	 * @param companyCode
	 * @param stockHolderCode
	 * @return
	 * @throws SystemException
	 */
	private boolean checkMappingsExist(
			String companyCode, String stockHolderCode) throws SystemException{
		StockAgentFilterVO filterVO = new StockAgentFilterVO();
		filterVO.setCompanyCode(companyCode);
		filterVO.setStockHolderCode(stockHolderCode);
		filterVO.setPageNumber(1);
		Page<StockAgentVO> stockAgents = null;
		stockAgents = StockAgentMapping.findStockAgentMappings(filterVO);
		if(stockAgents != null && stockAgents.size() > 0){
			return true;
		}
		return false;
	}

	/**
	 * Used to list the details of a selected stockholder
	 *
	 * @author A-1885
	 * @param companyCode
	 * @param stockHolderCode
	 * @return StockHolderVO
	 * @throws SystemException
	 */
	public StockHolderVO findStockHolderDetails(String companyCode,
			String stockHolderCode) throws SystemException {
		return StockHolder.findStockHolderDetails(companyCode, stockHolderCode);
	}

	/**
	 * This method finds details for stock holders which meet the filter
	 * criteria and all stock holders who fall under them.
	 *
	 * @author A-1885
	 * @param filterVO
	 * @return Collection
	 * @throws SystemException
	 *             Collection<StockHolderDetailsVO>
	 */
	public Page<StockHolderDetailsVO> findStockHolders(
			StockHolderFilterVO filterVO) throws SystemException {
		return StockHolder.findStockHolders(filterVO);
	}
	/**
	 * @author a-4421
	 * @param stockDetailsFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Page<StockDetailsVO> listStockDetails(
			StockDetailsFilterVO stockDetailsFilterVO) throws SystemException {
		try{
			if (stockDetailsFilterVO.getStockHolderCode() != null
					&& stockDetailsFilterVO.getStockHolderCode().trim()
							.length() > 0) {
				StockHolder.find(stockDetailsFilterVO.getCompanyCode(),
						stockDetailsFilterVO.getStockHolderCode());
			}

			return StockHolderStockDetail
					.listStockDetails(stockDetailsFilterVO);
		} catch (InvalidStockHolderException invalidStockHolderException) {
			throw new SystemException(
					InvalidStockHolderException.INVALID_STOCKHOLDER_FOR_MONITOR);
		}
	}

	/**
	 * This method is invoked to obtain the stock holder lov
	 *
	 * @author A-1885
	 * @param filterVO
	 * @param displayPage
	 * @return Page<StockHolderLovVO>
	 * @throws SystemException
	 */
	public Page<StockHolderLovVO> findStockHolderLov(
			StockHolderLovFilterVO filterVO, int displayPage)
			throws SystemException {
		return StockHolder.findStockHolderLov(filterVO, displayPage);

	}

	/**
	 * @author A-1883
	 * @param stockRequestVO
	 * @throws SystemException
	 * @throws InvalidStockHolderException
	 * @throws StockControlDefaultsBusinessException
	 */
	public void validateStockHolderTypeCode(StockRequestVO stockRequestVO)
			throws SystemException, InvalidStockHolderException,
			StockControlDefaultsBusinessException {
		StockHolder.validateStockHolderTypeCode(stockRequestVO);
	}

	/**
	 * This method is used to create/modify/cancel stock request details based
	 * on operational flag
	 *
	 * @param stockRequestVO
	 * @return String
	 * @throws SystemException
	 */
	@Advice(name = "stockcontrol.defaults.saveStockRequestDetails" , phase=Phase.POST_INVOKE)
	public String saveStockRequestDetails(StockRequestVO stockRequestVO)
			throws SystemException {
		log.entering("StockController", "-->saveStockRequestDetails<---");

		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		StockDepleteFilterVO stockDepleteFilterVO = new StockDepleteFilterVO();
		stockDepleteFilterVO.setCompanyCode(stockRequestVO.getCompanyCode());
		stockDepleteFilterVO.setAirlineId(logonAttributes.getAirlineIdentifier());
		//autoDepleteStock(stockDepleteFilterVO);

		if (StockRequestVO.OPERATION_FLAG_INSERT.equals(stockRequestVO
				.getOperationFlag())) {
			StockRequest stockRequest = new StockRequest(stockRequestVO);
			log.log(Log.INFO, "*******Insertion over", stockRequest.getStockRequestPk().getRequestRefNumber());
			log.exiting("StockController", "--->saveStockRequestDetails<---");
			if(StockHolder.findAutoProcessingQuantityAvailable(stockRequestVO)!=null){
				autoProcessingAllocateStock(stockRequestVO,
						stockRequest.getStockRequestPk().getRequestRefNumber());
			}
			return stockRequest.getStockRequestPk().getRequestRefNumber();
		} else if (StockRequestVO.OPERATION_FLAG_UPDATE.equals(stockRequestVO
				.getOperationFlag())) {
			StockRequest stockRequest = StockRequest.find(stockRequestVO
					.getCompanyCode(), stockRequestVO.getRequestRefNumber(),stockRequestVO.getAirlineIdentifier());
			stockRequest.update(stockRequestVO);
			log.log(Log.INFO, "*******Insertion over", stockRequest.getStockRequestPk().getRequestRefNumber());
			log.exiting("StockController", "-->saveStockRequestDetails<---");
			return stockRequest.getStockRequestPk().getRequestRefNumber();
		}
		return null;
	}


	/**
	 * This method will fetch the details of a particular stock request
	 *
	 * @param stockRequestFilterVO
	 * @return StockRequestVO
	 * @throws SystemException
	 */
	public StockRequestVO findStockRequestDetails(
			StockRequestFilterVO stockRequestFilterVO) throws SystemException {
		log.entering("StockController", "--->findStockRequestDetails<-----");
		return StockRequest.findStockRequestDetails(stockRequestFilterVO);
	}

	/**
	 * This method will fetch the stock requests satisfying the filter criteria
	 *
	 * @param stockRequestFilterVO
	 * @param displayPage
	 * @return Page<StockRequestVO>
	 * @throws SystemException
	 */
	public Page<StockRequestVO> findStockRequests(
			StockRequestFilterVO stockRequestFilterVO, int displayPage)
			throws SystemException {
		log.entering("StockController", "----------findStockRequests");
		return StockRequest
				.findStockRequests(stockRequestFilterVO, displayPage);
	}

	/**
	 * This method will cancel a particular stock request
	 *
	 * @param stockRequestVO
	 * @throws SystemException
	 */
	public void cancelStockRequest(StockRequestVO stockRequestVO)
			throws SystemException {
		//Collection<AuditFieldVO> auditFields = null;
		StockRequest stockRequest = StockRequest.find(stockRequestVO.getCompanyCode(),
				stockRequestVO.getRequestRefNumber(),stockRequestVO.getAirlineIdentifier());
		stockRequest.cancelStockRequest(stockRequestVO);
		/*StockRequestAuditVO stockRequestAuditVO = new StockRequestAuditVO(
				StockRequestAuditVO.AUDIT_PRODUCTNAME,
				StockRequestAuditVO.AUDIT_MODULENAME,
				StockRequestAuditVO.AUDIT_ENTITY);
		stockRequestAuditVO = (StockRequestAuditVO)
		AuditUtils.populateAuditDetails(stockRequestAuditVO,stockRequest,
				false);
		auditStockRequestDetails(stockRequest,stockRequestAuditVO,
				AuditVO.DELETE_ACTION);*/


	}

	
	/**
	 * This method is used to approve Stock Requests
	 *
	 * @author A-1883
	 * @param stockRequestApproveVO
	 * @throws SystemException
	 * @throws StockNotFoundException
	 * @throws StockControlDefaultsBusinessException
	 */
	public void approveStockRequests(StockRequestApproveVO stockRequestApproveVO)
			throws SystemException, StockNotFoundException,
			StockControlDefaultsBusinessException {

		try {
			log.entering("StockController", "----approveStockRequests()");
			log.log(Log.FINE, "*********stockRequestApproveVO In Server***",
					stockRequestApproveVO);
			StockHolder stockHolder = StockHolder.find(stockRequestApproveVO
					.getCompanyCode(), stockRequestApproveVO.getApproverCode());
			StockHolderAuditVO stockHolderAuditVO = new StockHolderAuditVO(
					StockHolderAuditVO.AUDIT_PRODUCTNAME,
					StockHolderAuditVO.AUDIT_MODULENAME,
					StockHolderAuditVO.AUDIT_ENTITY);
			//stockHolderAuditVO = (StockHolderAuditVO)
			//AuditUtils.populateAuditDetails(stockHolderAuditVO,stockHolder,
				//	false);
			stockHolder.approveStockRequests(stockRequestApproveVO);
			//stockHolderAuditVO = (StockHolderAuditVO)
			//AuditUtils.populateAuditDetails(stockHolderAuditVO,stockHolder,
			//		false);
			//auditStockHolderDetails(stockHolder,stockHolderAuditVO,
			//		AuditVO.UPDATE_ACTION);
			log.exiting("StockController", "----------approveStockRequests()");
		} catch (StockNotFoundException stockNotFoundException) {
			for (ErrorVO errorVO : stockNotFoundException.getErrors()) {
				throw new StockControlDefaultsBusinessException(errorVO
						.getErrorCode());
			}
		} catch (InvalidStockHolderException invalidStockHolderException) {
			throw new StockControlDefaultsBusinessException(
					invalidStockHolderException);
		}
	}

	/**
	 * This method is used to reject Stock Requests
	 *
	 * @param stockRequests
	 * @throws SystemException
	 * @return
	 */
	public void rejectStockRequests(Collection<StockRequestVO> stockRequests)
			throws SystemException {
		log.entering("StockController", "----rejectStockRequests()");
		for (StockRequestVO stockRequestVO : stockRequests) {
			StockRequest stockRequest = StockRequest.find(stockRequestVO
					.getCompanyCode(), stockRequestVO.getRequestRefNumber(),
					stockRequestVO.getAirlineIdentifier());


			/*StockRequestAuditVO stockRequestAuditVO = new StockRequestAuditVO(
					StockRequestAuditVO.AUDIT_PRODUCTNAME,
					StockRequestAuditVO.AUDIT_MODULENAME,
					StockRequestAuditVO.AUDIT_ENTITY);
			stockRequestAuditVO = (StockRequestAuditVO)
			AuditUtils.populateAuditDetails(stockRequestAuditVO,stockRequest,
					false);*/
			stockRequest.rejectStockRequest(
				stockRequestVO.getApprovalRemarks());
			/*stockRequestAuditVO = (StockRequestAuditVO)
			AuditUtils.populateAuditDetails(stockRequestAuditVO,stockRequest,
					false);
			auditStockRequestDetails(stockRequest,stockRequestAuditVO,
					AuditVO.UPDATE_ACTION);*/

		}
		log.exiting("StockController", "--------rejectStockRequests()");
	}

	/**
	 * This method is used to Complete Stock Requests
	 *
	 * @author A-1883
	 * @param stockRequests
	 * @throws SystemException
	 */
	public void completeStockRequests(Collection<StockRequestVO> stockRequests)
			throws SystemException {
		log.entering("StockController", "-----completeStockRequests()");
		for (StockRequestVO stockRequestVO : stockRequests) {
			StockRequest stockRequest = StockRequest.find(stockRequestVO
					.getCompanyCode(), stockRequestVO.getRequestRefNumber(),
					stockRequestVO.getAirlineIdentifier());

			/*StockRequestAuditVO stockRequestAuditVO = new StockRequestAuditVO(
					StockRequestAuditVO.AUDIT_PRODUCTNAME,
					StockRequestAuditVO.AUDIT_MODULENAME,
					StockRequestAuditVO.AUDIT_ENTITY);
			stockRequestAuditVO = (StockRequestAuditVO)
			AuditUtils.populateAuditDetails(stockRequestAuditVO,stockRequest,
					false);*/
			stockRequest.completeStockRequests(stockRequestVO);
			/*stockRequestAuditVO = (StockRequestAuditVO)
			AuditUtils.populateAuditDetails(stockRequestAuditVO,stockRequest,
					false);
			auditStockRequestDetails(stockRequest,stockRequestAuditVO,
					AuditVO.UPDATE_ACTION);*/
		}
		log.exiting("StockController", "------completeStockRequests()");
	}

	/**
	 * This method is used to find the available ranges for a stock holder code
	 *
	 * @author A-1883
	 * @param rangeFilterVO
	 * @throws SystemException
	 * @return Collection<RangeVO>
	 */
	public Collection<RangeVO> findRanges(RangeFilterVO rangeFilterVO)
			throws SystemException {

		StockDepleteFilterVO stockDepleteFilterVO = new StockDepleteFilterVO();
		stockDepleteFilterVO.setCompanyCode(rangeFilterVO.getCompanyCode());
		stockDepleteFilterVO.setAirlineId(rangeFilterVO.getAirlineIdentifier());
		//autoDepleteStock(stockDepleteFilterVO);

		log.entering("StockController", "----------findRanges()");
		return StockHolder.findRanges(rangeFilterVO);
	}

	/**
	 * This method is used to fetch the range details of a stockholder
	 *
	 * @author A-1883
	 * @param stockFilterVO
	 * @return
	 * @throws SystemException
	 * @throws StockNotFoundException
	 * @throws StockControlDefaultsBusinessException
	 */
	public StockRangeVO viewRange(StockFilterVO stockFilterVO)
			throws SystemException, StockNotFoundException,
			StockControlDefaultsBusinessException {
		log.entering("StockController", "----------viewRange()");

		StockDepleteFilterVO stockDepleteFilterVO = new StockDepleteFilterVO();
		stockDepleteFilterVO.setCompanyCode(stockFilterVO.getCompanyCode());
		stockDepleteFilterVO.setAirlineId(stockFilterVO.getAirlineIdentifier());
		//autoDepleteStock(stockDepleteFilterVO);
		return StockHolder.viewRange(stockFilterVO);
	}

	/**
	 * This method is used to monitor the stock of a stock holder
	 *
	 * @author A-1944
	 * @param stockFilterVO
	 * @return Collection<MonitorStockVO>
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException
	 */
	public Page<MonitorStockVO> monitorStock(StockFilterVO stockFilterVO)
			throws SystemException, StockControlDefaultsBusinessException {

		StockDepleteFilterVO stockDepleteFilterVO = new StockDepleteFilterVO();
		stockDepleteFilterVO.setCompanyCode(stockFilterVO.getCompanyCode());
		stockDepleteFilterVO.setAirlineId(stockFilterVO.getAirlineIdentifier());
		//autoDepleteStock(stockDepleteFilterVO);

		try {
			log.entering("StockController", "----------monitorStock()");
			StockHolder stockHolder = StockHolder.find(stockFilterVO
					.getCompanyCode(), stockFilterVO.getStockHolderCode());
			return stockHolder.monitorStock(stockFilterVO);
		} catch (InvalidStockHolderException invalidStockHolderException) {
			throw new StockControlDefaultsBusinessException
			(InvalidStockHolderException.INVALID_STOCKHOLDER_FOR_MONITOR);
		}
	}


	/**
	 * This method is used to monitor the stock of a stock holder
	 *
	 * @author A-1944
	 * @param stockFilterVO
	 * @return Collection<MonitorStockVO>
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException
	 */
	public MonitorStockVO findMonitoringStockHolderDetails(StockFilterVO stockFilterVO)
			throws SystemException, StockControlDefaultsBusinessException {
		try {
			log.entering("StockController", "----------monitorStock()");
			StockHolder stockHolder = StockHolder.find(stockFilterVO
					.getCompanyCode(), stockFilterVO.getStockHolderCode());
			return stockHolder.findMonitoringStockHolderDetails(stockFilterVO);
		} catch (InvalidStockHolderException invalidStockHolderException) {
			throw new StockControlDefaultsBusinessException
			(InvalidStockHolderException.INVALID_STOCKHOLDER_FOR_MONITOR);
		}
	}
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
	public Collection<String> findStockHolderCodes(String companyCode,
			Collection<String> privileges) throws SystemException {
		log.entering("StockController", "----------findStockHolderCodes()");
		return StockHolder.findStockHolderCodes(companyCode, privileges);
	}

	/**
	 * This method will find the blacklisted stock matching the filter criteria
	 *
	 * @author A-1883
	 * @param stockFilterVO
	 * @param displayPage
	 * @return Page<BlacklistStockVO>
	 * @throws SystemException
	 */
	public Page<BlacklistStockVO> findBlacklistedStock(
			StockFilterVO stockFilterVO, int displayPage)
			throws SystemException {
		log.entering("StockController", "----------findBlacklistedStock()");
		return BlackListStock.findBlacklistedStock(stockFilterVO, displayPage);
	}

	/**
	 * This method is used to allocate a stock range to stock holder and deplete
	 * the range from the approvers stock
	 *
	 * @param stockAllocationVO
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 * @throws StockNotFoundException
	 * @throws BlacklistedRangeExistsException
	 * @throws RangeExistsException
	 * @throws InvalidStockHolderException
	 * @throws StockControlDefaultsBusinessException
	 */
	@Raise(module="stockcontrol",submodule="defaults",event="STOCK_UPDATE_EVENT",methodId="stockcontrol.defaults.allocateStock") 
	public StockAllocationVO allocateStock(StockAllocationVO stockAllocationVO)
			throws SystemException, FinderException, StockNotFoundException,
			BlacklistedRangeExistsException, RangeExistsException,
			InvalidStockHolderException, StockControlDefaultsBusinessException {
		log.log(Log.FINE, "-----Entering allocateStock-----");
		log.log(Log.INFO, "---stockAllocationVO--- : ", stockAllocationVO);
		
		for(RangeVO rangevo : stockAllocationVO.getRanges()){
			StockFilterVO stockFilterVO = new StockFilterVO();
			stockFilterVO.setCompanyCode(stockAllocationVO.getCompanyCode());
			stockFilterVO.setAirlineIdentifier(stockAllocationVO.getAirlineIdentifier());
			stockFilterVO.setDocumentType(stockAllocationVO.getDocumentType());
			stockFilterVO.setDocumentSubType(stockAllocationVO.getDocumentSubType());
			stockFilterVO.setStockHolderCode(stockAllocationVO.getStockControlFor());
			stockFilterVO.setRangeFrom(rangevo.getStartRange());
			stockFilterVO.setRangeTo(rangevo.getEndRange());
			
				String masterDocumentNumber = checkStockUtilisationForRange(stockFilterVO);
				if(masterDocumentNumber!=null){
					throw new StockControlDefaultsBusinessException(StockControlDefaultsBusinessException.UTILISATION_EXISTS);
				}
			
		}
		
		
		SharedDefaultsProxy sharedDefaultsProxy = new SharedDefaultsProxy();
		Collection<String> syspar = new ArrayList<String>();
				syspar.add(STOCK_DEFAULTS_ENABLESTOCKHISTORY); 
			   //added for icrd-3024 	starts
	            syspar.add(STOCK_DEFAULTS_STOCKHOLDER_ACCOUNTING_PARAM);
				syspar.add(STOCK_DEFAULTS_CONFIRMATIONREQUIRED);
				syspar.add(STOCK_DEFAULTS_STOCKINTRODUCTIONPERIOD);
			   //added for icrd-3024 	ends
		boolean traceNeeded = false;   
//added for icrd-3024 	starts
	    String accountingNeeded=null;
	    int stockIntroductionPeriod = 0;
	    /**
	     * Added by A-2881 for stock confirmation process
	     */
	    boolean isConfirmationProcessNeeded=false;	   
//added for icrd-3024 	ends
		try{
			Map<String,String> sysmap= sharedDefaultsProxy.findSystemParameterByCodes(syspar);
			
			traceNeeded = StockAllocationVO.FLAG_YES.equals(sysmap.get(STOCK_DEFAULTS_ENABLESTOCKHISTORY))?true:false;
			//added for icrd-3024 	starts
            accountingNeeded=sysmap.get(STOCK_DEFAULTS_STOCKHOLDER_ACCOUNTING_PARAM);
			isConfirmationProcessNeeded=StockAllocationVO.FLAG_YES.equals(sysmap.get(STOCK_DEFAULTS_CONFIRMATIONREQUIRED))?true:false;
		  //added for icrd-3024 	ends
			stockIntroductionPeriod = Integer.parseInt(sysmap.get(STOCK_DEFAULTS_STOCKINTRODUCTIONPERIOD));
		}
		catch(ProxyException proxyException){ 
			for(ErrorVO ex : proxyException.getErrors()){
				throw new SystemException(ex.getErrorCode());
			}
		}   

		

		StockDepleteFilterVO stockDepleteFilterVO = new StockDepleteFilterVO();
		stockDepleteFilterVO.setCompanyCode(stockAllocationVO.getCompanyCode());
		stockDepleteFilterVO.setAirlineId(stockAllocationVO.getAirlineIdentifier());
		
		//autoDepleteStock(stockDepleteFilterVO);

		StockAllocationVO theNewStkAllocVO = null;
//added for icrd-3024 	starts
		Collection<RangeVO> transitRangeVOs=null;
		StockAllocationVO stockAllocationVOForTransit=null;
 //added for icrd-3024 	ends
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();

		StockAuditVO auditVO = new StockAuditVO(
				StockAuditVO.AUDIT_PRODUCTNAME,
				StockAuditVO.AUDIT_MODULENAME,
				StockAuditVO.AUDIT_ENTITY);
		if(stockAllocationVO.isNewStockFlag()){
			try{
				log.log(Log.FINE, "---trying to find StockHolder with:");   
				log.log(Log.FINE, "---CompanyCode:", stockAllocationVO.getCompanyCode());
				log.log(Log.FINE, "---StockHolderCode:", stockAllocationVO.getStockHolderCode());
				StockHolder stk = StockHolder.find(stockAllocationVO.getCompanyCode(),stockAllocationVO.getStockHolderCode());
			}
			catch(InvalidStockHolderException e){
				log.log(Log.SEVERE,
						"===-INVALID_STOCKHOLDERFORCREATENEW---1--",
						stockAllocationVO.getStockHolderCode());
				log.log(Log.SEVERE,
						"===-INVALID_STOCKHOLDERFORCREATENEW---2--",
						stockAllocationVO.getStockHolderCode());
				log.log(Log.SEVERE,
						"===-INVALID_STOCKHOLDERFORCREATENEW---3--",
						stockAllocationVO.getStockHolderCode());
				throw new StockControlDefaultsBusinessException
				(StockControlDefaultsBusinessException.INVALID_STOCKHOLDERFORCREATENEW);
			}
		}
		//Collection<AuditFieldVO> auditFields = null;

		/*
		 * This is to check whether start range is less than end range.
		 */
		Collection<RangeVO> ranges = null;
		if(stockAllocationVO.getRanges() != null){
			if(StockAllocationVO.MODE_TRANSFER.equals(stockAllocationVO.getTransferMode())){
				for(RangeVO rangeVO : stockAllocationVO.getRanges()){
					if(rangeVO.getEndRange()==null){
							if(ranges==null){
								ranges = new ArrayList<RangeVO>();
							}
							ranges.addAll(findRangeForTransfer(stockAllocationVO,
									rangeVO.getStartRange(),rangeVO.getNumberOfDocuments()));
							log.log(Log.FINE, "Return ranges found", ranges);
							if(ranges == null || ranges.size()==0) {
								throw new StockNotFoundException(StockNotFoundException.STOCK_NOTFOUND);
							}
						}
				}
				if(ranges!=null && ranges.size()>0){
					if(stockAllocationVO.getRanges()==null){
							stockAllocationVO.setRanges(new ArrayList<RangeVO>());
					}
					stockAllocationVO.setRanges(ranges);
				}
			}

		}
		if(stockAllocationVO.getRanges() != null && stockAllocationVO.getRanges().size()>0){
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			for(RangeVO rangeVO : stockAllocationVO.getRanges()){
				if(Range.findLong(rangeVO.getStartRange()) > Range.findLong(rangeVO.getEndRange())){
					ErrorVO errorVo = ErrorUtils.getError(
							StockControlDefaultsBusinessException
									.ENDRANGE_LESSTHAN_STARTRANGE,
									new Object[]{ rangeVO.getStartRange(),
									rangeVO.getEndRange()});
					errors.add(errorVo);
					StockControlDefaultsBusinessException stockControlException = new StockControlDefaultsBusinessException();
					stockControlException.addErrors(errors);
					log.log(Log.SEVERE,"THROWING EXCEPTION");
					throw stockControlException;
				}
			}
		}

		// added by A-3155
		//Commented by A-2881 for ICRD-3082
		/*if (StockAllocationVO.MODE_RETURN.equals(stockAllocationVO
				.getTransferMode())) {    
			log.log(Log.FINE, "--call for creating history for Return---"+stockAllocationVO);
			if(traceNeeded!=null && YES.equalsIgnoreCase(traceNeeded)){ 
			  createHistory(stockAllocationVO, StockAllocationVO.MODE_RETURN);
			}	 
		}    */
		
		if(stockAllocationVO.isNewStockFlag() && StockAllocationVO.OPERATION_FLAG_INSERT.equals(stockAllocationVO.getOperationFlag())){
			log.log(Log.INFO,"\n\n\n\n\n*************************************");
			log.log(Log.INFO,"\n\n\n\ngoing to persist\n\n\n\n\n\n");
			StockHolder st = null;
			StockVO stockVO = new StockVO();
			stockVO.setCompanyCode(stockAllocationVO.getCompanyCode());
			stockVO.setAirlineIdentifier(stockAllocationVO.getAirlineIdentifier());
			stockVO.setDocumentSubType(stockAllocationVO.getDocumentSubType());
			stockVO.setDocumentType(stockAllocationVO.getDocumentType());
			st = StockHolder.find(stockAllocationVO.getCompanyCode(),stockAllocationVO.getStockHolderCode());
			Stock stock =new Stock(stockAllocationVO.getCompanyCode(),stockAllocationVO.getStockHolderCode(),stockVO);
			st.getStock().add(stock);
			//st.setLastUpdateTime(stockAllocationVO.getLastUpdateTime());
			//st.setLastUpdateUser(stockAllocationVO.getLastUpdateUser());
			 st.setLastUpdateTime(stockAllocationVO.getLastUpdateTime());
			 st.setLastUpdateUser(st.getLastUpdateUser());
			log.log(Log.INFO,
					"\n\n\n\nValue of stockHolder.getStock\n\n\n\n\n\n", st.getStock().size());

		}


		if(stockAllocationVO.isConfirmationRequired()){

			/*
			 *	checking whether the airlineCode is home airline code
			 *	as per system parameters. If it is not , then validation
			 *	is done.
			 */
			Collection<String> invalidDocuments = null;
			Collection<String> processedDocuments = null;
			Collection<String> alreadyReservedDocuments = null;

			/*
			 * this proxy call is to find whether any awb is already reserved
			 * within the given ranges. The reserved awbs are returned.
			 *
			 */
			try {
				OperationsShipmentProxy proxy = new OperationsShipmentProxy();

				invalidDocuments = proxy.validateDocumentRange(stockAllocationVO);

				 /*
				  * This is to check whether there exists any documents
				  * in the given range which is completely processed
				  * These are all the processed docs which were
				  * in the system somewhat around 2 or 3 years before, and which
				  * can be used now.
				  *
				  * The user has to be informed about this, as a warning message
				  * about the already processed docs.
				  */
				processedDocuments = proxy.validateProcessedDocuments(stockAllocationVO);

			} catch (ProxyException proxyException) {
				log.log(Log.SEVERE,"===PRXY EXP==in OperationsShipmentProxy===");
				log.log(Log.SEVERE,"===PRXY EXP==in OperationsShipmentProxy===");
				throw new SystemException(proxyException.getMessage(),proxyException);
			}

			/*
			 * Checking whether any AWB is reserved among the given range
			 */
			alreadyReservedDocuments = checkReservedDocumentExists(stockAllocationVO);

			log.log(Log.INFO, "stock allocation vo:", stockAllocationVO);
			log.log(Log.INFO, "invalid documents are :", invalidDocuments);
			log.log(Log.INFO, "already processed docs are :",
					processedDocuments);
			log.log(Log.INFO, "already reserved docs are :",
					alreadyReservedDocuments);
			if(processedDocuments != null){
				// this reference of stockAllocationVO is used in the client to
				// show message information
				// This is set , so that the user is informed about already
				// processed docs, as a warning
				stockAllocationVO.setNumberOfProcessedDocs(processedDocuments.size());
			}

			/*
			 *  This boolean checks whether there is invalid documents or processedDocuments
			 *  if so, then the client should show a warning.. and for this the
			 *  same referrence of stkAllocationVO should be returned to client
			 *
			 */
			boolean isConfirmationRequired = false;

			/*
			 * if there is already reserved awbs in the given range, then it should
			 * be removed from the range given for allocation. So splitting of
			 * ranges is done here.
			 */
			int originalNumOfDocuments = getNumberOfDocuments(stockAllocationVO.getRanges());

			if(invalidDocuments != null && invalidDocuments.size() > 0){
				log.log(Log.INFO, "old StockAllocationVO is :",
						stockAllocationVO);
				//int originalNumOfDocuments = getNumberOfDocuments(stockAllocationVO.getRanges());
				Collection<RangeVO> updatedRanges =  removeInvalidDocuments(stockAllocationVO, invalidDocuments);
				stockAllocationVO.setRanges(updatedRanges);
				log.log(Log.INFO, "updated ranges is :", updatedRanges);
				log.log(Log.INFO, "new StockAllocationVO is :",
						stockAllocationVO);
				int newNumberOfDocuments = getNumberOfDocuments(updatedRanges);
				if(originalNumOfDocuments != newNumberOfDocuments){
					log.log(Log.INFO,"Invalid Documents Exists");
					log.log(Log.INFO,"Returning stockAllocationVO");
					stockAllocationVO.setRejectedDocuments(invalidDocuments);
					stockAllocationVO.setNoOfReqDocuments(originalNumOfDocuments);
					isConfirmationRequired = true;

					/*StockControlDefaultsBusinessException exception =
						new StockControlDefaultsBusinessException(
								StockControlDefaultsBusinessException.UNACCEPTABLE_DOCUMENTS_EXIST,
								new Object[]{originalNumOfDocuments,newNumberOfDocuments,stockAllocationVO});
					throw exception;*/
				}
			}

			if(processedDocuments != null && processedDocuments.size() > 0){
				isConfirmationRequired = true;

			}

			if(alreadyReservedDocuments != null && alreadyReservedDocuments.size() > 0){
				log.log(Log.INFO,"==Removing alreadyReserved Docs from thr rnge");
				Collection<RangeVO> updatedRanges =  removeInvalidDocuments(stockAllocationVO, alreadyReservedDocuments);
				stockAllocationVO.setRanges(updatedRanges);

				if(stockAllocationVO.getRejectedDocuments() == null){
					stockAllocationVO.setRejectedDocuments(new ArrayList<String>());
				}
				stockAllocationVO.getRejectedDocuments().addAll(alreadyReservedDocuments);
				stockAllocationVO.setNoOfReqDocuments(originalNumOfDocuments);

				log.log(Log.INFO,
						"updated ranges after removing alrdy Reserved docs:",
						updatedRanges);
				log
						.log(
								Log.INFO,
								"new StockAllocationVO after removing alrdy Reserved docs:",
								stockAllocationVO);
				isConfirmationRequired = true;
			}

			if(isConfirmationRequired){
				log.log(Log.FINE,"====returning same reference to client.");
				log.log(Log.FINE,"====returning same reference to client.");
				return stockAllocationVO;
			}
		}


		StockHolder stockHolder = new StockHolder();
		//isFromConfirmStock will be true when Confirm action
		//is done from Confirm Stock Screen.	
//Modified for ICRD-3024   - added !stockAllocationVO.isFromConfirmStock()
		if (!stockAllocationVO.isNewStockFlag()
				&& !stockAllocationVO.isFromConfirmStock()) {
			log.log(Log.FINE, "-----Not New Stock----", stockAllocationVO);
			stockHolder = StockHolder.find(stockAllocationVO.getCompanyCode(),
					stockAllocationVO.getStockControlFor());
			log.log(Log.FINE, "-----calling stockHolder.deplete----");
			/**
			 * Added for returning the stock whose approver deleted start
			 */
			if (stockAllocationVO.isApproverDeleted()) {
				stockHolder.deplete(stockAllocationVO, false,false);

				// for returning null... inorder to remove codeReview error
				return theNewStkAllocVO;
			}
			/**
			 * Added for returning the stock whose approver deleted end
			 */
			//stockHolder.setLastUpdateTime(stockAllocationVO.getLastUpdateTime());
			stockHolder.deplete(stockAllocationVO, false,false);

			log.log(Log.FINE, "-----after stockHolder.deplete----");
			/*
			 * auditDepleteRange(stockHolder,stockAllocationVO.
			 * getDocumentType(),stockAllocationVO.getDocumentSubType(),
			 * StockHolderAuditVO.ALLOCATERANGE_DEPLETE_ACTIONCODE);
			 */
			// auditDepleteDetails(stockHolder,stockAllocationVO.
			// getDocumentType(),stockAllocationVO.getDocumentSubType(),
			// AllocateStockAuditVO.ALLOCATERANGE_DEPLETE_ACTIONCODE);



			if(checkStockHolder(logonAttributes.getCompanyCode(),
					logonAttributes.getAirportCode(),
					stockAllocationVO.getStockControlFor())){
				log.log(Log.FINER, "^^^^stockAllocationVO", stockAllocationVO);
				StockFilterVO fltVo = new StockFilterVO();
				fltVo.setCompanyCode(stockAllocationVO.getCompanyCode());
				fltVo.setAirlineIdentifier(stockAllocationVO.getAirlineIdentifier());
				fltVo.setStockHolderCode(stockAllocationVO.getStockControlFor());
				fltVo.setDocumentType(stockAllocationVO.getDocumentType());
				fltVo.setDocumentSubType(stockAllocationVO.getDocumentSubType());
				fltVo.setAirportCode(stockAllocationVO.getAirportCode());

				log.log(Log.FINER, "^^^^filterVO", fltVo);
				// this method automatically commits the database...
				// and fetches the data
				StockVO stkVO = Stock.findStockForStockHolder(fltVo);
				log.log(Log.FINER, "^^^^stockVO", stkVO);
				if(stkVO != null && stkVO.getTotalStock()< stkVO.getReorderLevel()){
					stockAllocationVO.setHasMinReorderLevel(true);
					log.log(Log.FINER,"returning stockAllocationVO for re-order level info");
					return stockAllocationVO;
				}

				// re-order level warning ends
			}
		}
		  //added for icrd-3024 	starts
		/**
		 * Check for syspar whether to allocate to next stock holder 
		 * or move to transit stock.Also check whether call is from 
		 * Confirm Stock Screen
		 */
		if (!stockAllocationVO.isNewStockFlag() && isConfirmationProcessNeeded
				&& !stockAllocationVO.isFromConfirmStock()) {                             
			log.log(Log.FINE, "StockAllocationVO for transit before copy-->",
					stockAllocationVO);
			stockAllocationVOForTransit = new StockAllocationVO(
					stockAllocationVO);
			log.log(Log.FINE, "StockAllocationVO for transit -->",
					stockAllocationVOForTransit);
			transitRangeVOs = mergeRanges(stockAllocationVO.getCompanyCode(),
					stockAllocationVO.getDocumentType(), stockAllocationVO
							.getDocumentSubType(), stockAllocationVO
							.getRanges());
			log.log(Log.FINE, "Merged Ranges for Transit -->", transitRangeVOs);
			stockAllocationVOForTransit.setRanges(transitRangeVOs);
			moveToTransitRange(stockAllocationVOForTransit);
			return stockAllocationVO;
		}
		  //added for icrd-3024 	ends
		/*
		 * this check identifies whether the stock is to deplete only, or
		 *  to deplete and allocate to somebody else...
		 *
		 * if the stock holder code matches with the code which is returned from the
		 * system parameters , then depletion only
		 */
		boolean isDepleteOnly = checkStockHolder(
				logonAttributes.getCompanyCode(),
				logonAttributes.getAirportCode(),
				stockAllocationVO.getStockControlFor());
		log.log(Log.FINE, "\n ####isDepleteOnly", isDepleteOnly);
		if (!isDepleteOnly) {

			StockHolder stockHolderDest = new StockHolder();
			try {
				stockHolderDest = StockHolder.find(stockAllocationVO
						.getCompanyCode(), stockAllocationVO
						.getStockHolderCode());
				// added by Salish starts
			} catch (SystemException systemException) {
				log.log(Log.FINE,">>>>>>>>>No Stockholder found..>>>>>>>>>>>>");
				stockHolderDest = null;
			}
			log.log(Log.FINE, "Stockholder after find is----", stockHolderDest);
			if (stockHolderDest == null) {
				log.log(Log.FINE, "Stock Approver Already Deleted");
				throw new InvalidStockHolderException(
						InvalidStockHolderException.STOCKHOLDER_ALREADY_DELETED);
			}
			// added by Salish ends
			log.log(Log.FINE, "-----calling stockHolderDest.allocate----");

			if(traceNeeded){
				stockAllocationVO.setEnableStockHistory(true); 
			}
			stockHolderDest.allocate(stockAllocationVO,stockIntroductionPeriod);
			log.log(Log.FINE, "Last Update Time ", stockAllocationVO.getLastUpdateTime());
			log.log(Log.FINE, "-----after stockHolderDest.allocate----");

		}

		if (stockAllocationVO.isAllocate()) {
			log.log(Log.FINE, "-----Going to update Stock Request----");
			StockRequest stockRequest = StockRequest.find(
									stockAllocationVO.getCompanyCode(),
									stockAllocationVO.getRequestRefNumber(),
									String.valueOf(stockAllocationVO.getAirlineIdentifier()));
			int allocatedStock = 0;
			for (RangeVO rangeVo : stockAllocationVO.getRanges()) {
				allocatedStock += Range.difference(rangeVo.getStartRange(),
						rangeVo.getEndRange());
			}
			stockRequest.updateStockRequest(new Integer(allocatedStock)
					.longValue());
			stockRequest.setLastUpdateDate(stockAllocationVO.getLastUpdateTimeForStockReq());
			stockRequest.setLastUpdateUser(stockAllocationVO.getLastUpdateUser());
		}


		//	For updating the action time and action completed in the
		//	stock request for other airlines...
		log.log(Log.INFO,"Before updating other airlines: ");
		log.log(Log.INFO, "stockAllocationVO.getStockForOtherAirlines()",
				stockAllocationVO.getStockForOtherAirlines());
		if(stockAllocationVO.getStockForOtherAirlines() != null){
			for(StockRequestForOALVO stockRequestForOALVO : stockAllocationVO.getStockForOtherAirlines()){
				if(StockRequestForOALVO.OPERATION_FLAG_UPDATE.equals(stockRequestForOALVO.getOperationFlag())){
					StockRequestForOAL stockForOal = StockRequestForOAL.find(
										stockRequestForOALVO.getCompanyCode(),
										stockRequestForOALVO.getAirportCode(),
										stockRequestForOALVO.getDocumentType(),
										stockRequestForOALVO.getDocumentSubType(),
										stockRequestForOALVO.getAirlineId(),
										stockRequestForOALVO.getSerialNumber());
					stockForOal.setActionTime(stockRequestForOALVO.getActionTime().toCalendar());
					stockForOal.setIsRequestCompleted(StockRequestForOALVO.FLAG_YES);
				}
			}
		}


		/*
		 * auditAllocateRange(stockAllocationVO,StockHolderAuditVO.
		 * ALLOCATERANGE_INSERT_ACTIONCODE,
		 * stockHolderDest.getStockHolderType());
		 */
		// auditAllocateRangeDetails(stockAllocationVO,
		// AllocateStockAuditVO.ALLOCATERANGE_INSERT_ACTIONCODE);
		log.log(Log.FINE, "888888888888888888After Calling allocate");
		if(stockAllocationVO.isBlacklist()&& stockAllocationVO.isNewStockFlag()){
		createStockHolderStockDetails(stockAllocationVO,"BR");
		}
		else{
			createStockHolderStockDetails(stockAllocationVO,null);
		}
		//Added by A-2881 for ICRD-3082
		if(traceNeeded && stockAllocationVO.getTransactionCode()!=null){
			createHistory(stockAllocationVO,stockAllocationVO.getTransactionCode());
		}
		//for returning null... inorder to remove codeReview error
		return theNewStkAllocVO;

	}

//added by a-4443 for icrd-3024 	starts	
    
	/**
	 * 
	 * @param stockAllocationVO
	 * @throws SystemException
	 */
	private void moveToTransitRange(StockAllocationVO stockAllocationVO)
			throws SystemException {
		Collection<TransitStockVO> transitStockVOs = null;
		// This should construct TransitStockVOs as per the allocating range
		transitStockVOs = constructTransitStockVOs(stockAllocationVO);
		log.log(Log.FINE, "TransitStockVOs -->", transitStockVOs);
		if (transitStockVOs != null) {
			TransitStock.saveTransitStock(transitStockVOs);
        }
		if (StockAllocationVO.MODE_TRANSFER.equals(stockAllocationVO
				.getTransactionCode())) {
			createHistory(stockAllocationVO,
					StockAllocationVO.MODE_TRANSFER_TRANSIT);
		} else if (StockAllocationVO.MODE_RETURN.equals(stockAllocationVO
				.getTransactionCode())) {
			createHistory(stockAllocationVO,
					StockAllocationVO.MODE_RETURN_TRANSIT);
		} else if (StockAllocationVO.MODE_ALLOCATE.equals(stockAllocationVO
				.getTransactionCode())) {
			createHistory(stockAllocationVO,
					StockAllocationVO.MODE_ALLOCATE_TRANSIT);
		}
		
		log.log(Log.FINE, "Persistence completed -->", transitStockVOs);
	}
	
	/**
	 * This method will be invoked when Confirmation is done from 
	 * Confirm Stock Screen.Selected TransitStockVO will be argument.
	 * 
	 * @param stockVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<TransitStockVO> confirmStockRange(Collection<TransitStockVO> stockVOs)
			throws SystemException, FinderException,
			BlacklistedRangeExistsException, RangeExistsException,
			InvalidStockHolderException, StockNotFoundException,
			StockControlDefaultsBusinessException {
		log.entering("StockController", "confirmStockRange");
		if (stockVOs != null) {
			for (TransitStockVO stockVO : stockVOs) {

				StockAllocationVO stockAllocationVO = null;

				// construct StockAllocationVOs
				stockAllocationVO = constructStockAllocationVO(stockVO);

				// call delete from Transit range
				deleteTransitStockRange(stockVO);

				// Allocate Stock
				//Spring adapter call for ICRD-100821
				StockController stockController = (StockController)SpringAdapter.
				getInstance().getBean("stockcontroldefaultsController");
				stockController.allocateStock(stockAllocationVO);
				

			}
		}
		
		// list all not confirmed and missing stocks
		log.exiting("StockController", "confirmStockRange");
		//Commented by A-2881
		/*StockRequestFilterVO stockReqVO = new StockRequestFilterVO();
		stockReqVO.setCompanyCode(stockVO.getCompanyCode());
		stockReqVO.setDocumentType(stockVO.getDocumentType());
		stockReqVO.setDocumentSubType(stockVO.getDocumentSubType());
		stockReqVO.setStatus(stockVO.getConfirmStatus());
		stockReqVO.setStockHolderCode(stockVO.getStockHolderCode());
		stockReqVO.setAirlineIdentifier(""+stockVO.getAirlineIdentifier());
		stockReqVO.setOperation(stockVO.getTxnCode());
	
		Collection<TransitStockVO> transitStockVOs = findTransitStocks(stockReqVO);;*/
		return null;
	}
	
	/**
	 * This method will construct StockAllocationVO based on 
	 * each transfer 
	 * 
	 * @param stockVO
	 * @return
	 * @throws SystemException
	 */
	private StockAllocationVO constructStockAllocationVO(
			TransitStockVO stockVO)
			throws SystemException {
		log.entering("StockController", "constructStockAllocationVO");
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
		.getLogonAttributesVO();
		Collection<RangeVO> rangeVOs = new ArrayList<RangeVO>();
		RangeVO rangeVO = null;
		StockAllocationVO stockAllocationVO = new StockAllocationVO();

		stockAllocationVO.setCompanyCode(stockVO.getCompanyCode());
		stockAllocationVO.setDocumentType(stockVO.getDocumentType());
		stockAllocationVO.setDocumentSubType(stockVO.getDocumentSubType());
		stockAllocationVO.setStockControlFor(stockVO.getStockControlFor());
		stockAllocationVO.setStockHolderCode(stockVO.getStockHolderCode());
		stockAllocationVO.setManual(stockVO.isManual());
		//commented by a-4443 for icrd-3024 on 5-Mar-2012
		// stockAllocationVO.setRemarks();
		stockAllocationVO.setNewStockFlag(false);
		stockAllocationVO.setOperationFlag(TransitStockVO.OPERATION_FLAG_INSERT);
		//commented by a-4443 for icrd-3024 on 8-Mar-2012
		//stockAllocationVO.setAllocate(true);
		stockAllocationVO.setRemarks(stockVO.getTxnRemarks());   
		
		if(TransitStockVO.TRANSIT_ACTION_ALLOCATE.equals(stockVO.getTxnCode())){
			log.log(Log.FINE, "Transfer Mode is allocate");
			stockAllocationVO.setTransferMode(StockAllocationVO.MODE_NORMAL);
            //Added by A-2881 for ICRD-3082
			stockAllocationVO.setTransactionCode(StockAllocationVO.MODE_ALLOCATE);
		}else if(TransitStockVO.TRANSIT_ACTION_TRANSFER.equals(stockVO.getTxnCode())){
			log.log(Log.FINE, "Transfer Mode is transfer");
			stockAllocationVO.setTransferMode(StockAllocationVO.MODE_TRANSFER);
            //Added by A-2881 for ICRD-3082
			stockAllocationVO.setTransactionCode(StockAllocationVO.MODE_TRANSFER);
		}else if(TransitStockVO.TRANSIT_ACTION_RETURN.equals(stockVO.getTxnCode())){
			log.log(Log.FINE, "Transfer Mode is return");
			stockAllocationVO.setTransferMode(StockAllocationVO.MODE_RETURN);
            //Added by A-2881 for ICRD-3082
			stockAllocationVO.setTransactionCode(StockAllocationVO.MODE_RETURN);
		}
		
		
		stockAllocationVO.setAirlineIdentifier(stockVO.getAirlineIdentifier());
		if (TransitStockVO.TRANSIT_STATUS_NOT_CONFIRMED.equals(stockVO
				.getConfirmStatus())) {
			rangeVO = new RangeVO();
			rangeVO.setStartRange(stockVO.getActualStartRange());
			rangeVO.setEndRange(stockVO.getActualEndRange());
			rangeVO.setNumberOfDocuments(stockVO.getNumberOfDocs());
			rangeVOs.add(rangeVO);

		} else if (TransitStockVO.TRANSIT_STATUS_MISSING.equals(stockVO
				.getConfirmStatus())) {
			rangeVO = new RangeVO();
			// rangeVO.setCompanyCode(stockVO.getCompanyCode());
			// rangeVO.setDocumentType(stockVO.getDocumentType());
			// rangeVO.setDocumentSubType(stockVO.getDocumentSubType());
			// rangeVO.setAirlineIdentifier(stockVOairlineIdentifier);
			// Last update Time
			rangeVO.setStartRange(stockVO.getMissingStartRange());
			rangeVO.setEndRange(stockVO.getMissingEndRange());
			rangeVO.setNumberOfDocuments(stockVO.getMissingNumberOfDocs());
			rangeVOs.add(rangeVO);

		}
		stockAllocationVO.setRanges(rangeVOs);
		//To be checked
		stockAllocationVO.setLastUpdateUser(logonAttributes.getUserId());
		/*Replaced Location.ARP with Location.STN by A-5275 for ICRD-34296*/
		stockAllocationVO.setLastUpdateTime(new LocalDate(logonAttributes
				.getAirportCode(), Location.STN, true));
		stockAllocationVO.setFromConfirmStock(true);
		//stockAllocationVO.setRequestRefNumber(stockVO.set)
		log.exiting("StockController", "constructStockAllocationVO");
		return stockAllocationVO;

	}
	
	/**
	 * This method deletes the corresponding entry in 
	 * Stock Transit Range table
	 * @param stockVO
	 * @throws SystemException
	 */
	private void deleteTransitStockRange(TransitStockVO stockVO)
			throws SystemException {
		log.entering("StockController", "deleteTransitStockRange");
		TransitStock stock = null;
		stock = TransitStock.find(stockVO);
		stock.remove();
		log.exiting("StockController", "deleteTransitStockRange");

	}
	/**
	 * 
	 * @param stockAllocationVO
	 * @return
	 * @throws SystemException
	 */
	private Collection<TransitStockVO> constructTransitStockVOs(
			StockAllocationVO stockAllocationVO) throws SystemException {
		Collection<TransitStockVO> newTransitVOs = null;
		Collection<RangeVO> ranges=null;
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		if (stockAllocationVO.getRanges() != null) {
			for (RangeVO rangeVO : stockAllocationVO.getRanges()) {
				TransitStockVO stockVO = new TransitStockVO();
				stockVO.setCompanyCode(stockAllocationVO.getCompanyCode());
				stockVO.setAirlineIdentifier(stockAllocationVO
						.getAirlineIdentifier());
				stockVO.setDocumentType(stockAllocationVO.getDocumentType());
				stockVO.setDocumentSubType(stockAllocationVO
						.getDocumentSubType());
				stockVO.setActualStartRange(rangeVO.getStartRange());
				stockVO.setActualEndRange(rangeVO.getEndRange());
				//added by a-4443 for icrd-3024 on 8-Mar-2012
				stockVO.setOperationFlag("I");
				stockVO.setMissingStartRange(rangeVO.getStartRange());
				stockVO.setMissingEndRange(rangeVO.getEndRange());
				//Edited as part of ICRD-46860
				stockVO.setAsciiMissingStartRange(findLong(rangeVO.getStartRange()));
				stockVO.setAsciiMissingEndRange(findLong(rangeVO.getEndRange()));
				stockVO.setMissingNumberOfDocs(getNumberOfDocuments(ranges));
				
				if (StockAllocationVO.MODE_TRANSFER.equals(stockAllocationVO
						.getTransferMode())) {
					stockVO.setTxnCode(TransitStockVO.TRANSIT_ACTION_TRANSFER);
				} else if (StockAllocationVO.MODE_RETURN
						.equals(stockAllocationVO.getTransferMode())) {
					stockVO.setTxnCode(TransitStockVO.TRANSIT_ACTION_RETURN);
				} else if (!stockAllocationVO.isNewStockFlag()) {
					stockVO.setTxnCode(TransitStockVO.TRANSIT_ACTION_ALLOCATE);
				}
				stockVO.setTxnDate(new LocalDate(logonAttributes
						.getAirportCode(), Location.ARP, true));          
				stockVO.setConfirmStatus(TransitStockVO.TRANSIT_STATUS_NOT_CONFIRMED);
				stockVO.setStockHolderCode(stockAllocationVO.getStockHolderCode()); 
				stockVO.setStockControlFor(stockAllocationVO
						.getStockControlFor());    
				stockVO.setManual(stockAllocationVO.isManual());
				stockVO.setTxnRemarks(stockAllocationVO.getRemarks());   
				
				ranges=new ArrayList<RangeVO>();
				ranges.add(rangeVO);
				
				stockVO.setNumberOfDocs(getNumberOfDocuments(ranges));          
				stockVO.setLastUpdateUser(logonAttributes.getUserId());
				stockVO.setLastUpdateTime(new LocalDate(logonAttributes
						.getAirportCode(), Location.ARP, true)); 
				
				if(newTransitVOs==null) {
					newTransitVOs=new ArrayList<TransitStockVO>();
				}
				newTransitVOs.add(stockVO);         

			}
		}
		return newTransitVOs;
	}
	
	/**
	 * This method will be called from Missing Ranges popup 
	 * invoked from Confirm Stock Screen.The Collection<MissingStockVO>
	 * has the missing ranges entered from screen.
	 * 
	 * @param transitStockVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<TransitStockVO> updateMissingStock(
			TransitStockVO transitStockVO) throws SystemException,
			StockControlDefaultsBusinessException,
			BlacklistedRangeExistsException, RangeExistsException,
			InvalidStockHolderException, FinderException,
			StockNotFoundException {
		log.entering("StockController", "updateMissingStock");
		Collection<RangeVO> rangeVOsForAllocation = null;
		Collection<TransitStockVO> missingTransitStockVOs = null;
		Collection<TransitStockVO> transitStockVOs = null;
		StockAllocationVO stockAllocationVO = null;

		// Find the Confirmed stock range from actual and missing ranges
		rangeVOsForAllocation = getConfirmedStock(transitStockVO);

		// construct New TransitStockVOs for insertion
		missingTransitStockVOs = constructTransitStockVOsForMissingRanges(transitStockVO);

		//set existing TransitStockVO as D and adding to collection
		transitStockVO.setOperationFlag(TransitStockVO.OPERATION_FLAG_DELETE);
		if (transitStockVOs == null) {
			transitStockVOs = new ArrayList<TransitStockVO>();
		}
		transitStockVOs.addAll(missingTransitStockVOs);
		transitStockVOs.add(transitStockVO);

		//persist TransitStock
		TransitStock.saveTransitStock(transitStockVOs);

		//construct StockAllocationVO for actual allocation
		stockAllocationVO = constructStockAllocationVO(transitStockVO);
		stockAllocationVO.setRanges(rangeVOsForAllocation);

		//call allocateStock()
		//Spring adapter call for ICRD-100821
		StockController stockController = (StockController)SpringAdapter.
		getInstance().getBean("stockcontroldefaultsController");
		stockController.allocateStock(stockAllocationVO);

		log.exiting("StockController", "updateMissingStock");
		
		StockRequestFilterVO stockReqVO = new StockRequestFilterVO();
		stockReqVO.setCompanyCode(transitStockVO.getCompanyCode());
		stockReqVO.setDocumentType(transitStockVO.getDocumentType());
		stockReqVO.setDocumentSubType(transitStockVO.getDocumentSubType());
		stockReqVO.setStatus(transitStockVO.getConfirmStatus());
		stockReqVO.setStockHolderCode(transitStockVO.getStockHolderCode());
		stockReqVO.setAirlineIdentifier(""+transitStockVO.getAirlineIdentifier());
		stockReqVO.setOperation(transitStockVO.getTxnCode());
		//added as part of ICRD-15656 
		stockReqVO.setStockHolderType(transitStockVO.getStockHolderType());
		
		Collection<TransitStockVO> finaltransitStockVOs = findTransitStocks(stockReqVO);;
		return finaltransitStockVOs;
	}
	
	/**
	 * 
	 * @param transitStockVO
	 * @return
	 */
	private Collection<RangeVO> getConfirmedStock(TransitStockVO transitStockVO) {
		log.entering("StockController", "getConfirmedStock");
		Collection<RangeVO> ranges = new ArrayList<RangeVO>();
		Collection<MissingStockVO> missingStockVOs = transitStockVO
				.getMissingRanges();
		Long[] startRanges = new Long[missingStockVOs.size()];
		Long[] endRanges = new Long[missingStockVOs.size()];
		long startRange = 0;
		long endRange = 0;

		if (TransitStockVO.TRANSIT_STATUS_NOT_CONFIRMED.equals(transitStockVO
				.getConfirmStatus())) {
			startRange = findLong(transitStockVO.getActualStartRange());
			endRange = findLong(transitStockVO.getActualEndRange());
		} else if (TransitStockVO.TRANSIT_STATUS_MISSING.equals(transitStockVO
				.getConfirmStatus())) {
			startRange = transitStockVO.getAsciiMissingStartRange();
			endRange = transitStockVO.getAsciiMissingEndRange();
		}
		log.log(Log.FINE, "actual start range -->", startRange);
		log.log(Log.FINE, "actual end range -->", endRange);
		int i = 0;
		for (MissingStockVO missingStockVO : missingStockVOs) {
			log.log(Log.FINE, "Missing Start Range -->", i, "<--",
					missingStockVO.getAsciiMissingStartRange());
			log.log(Log.FINE, "Missing End Range -->", i, "<--", missingStockVO.getAsciiMissingEndRange());
			startRanges[i] = missingStockVO.getAsciiMissingStartRange();
			endRanges[i] = missingStockVO.getAsciiMissingEndRange();
			i++;
		}

		// Start/End Ranges are sorted by ascending order
		Arrays.sort(startRanges);
		Arrays.sort(endRanges);

		long tempStart = startRange;
		long tempEnd = endRange;
		i = 0;
		for (Long start : startRanges) {
			//Added for call from black list
			if(start>tempStart && start>tempEnd){                  
				return new ArrayList<RangeVO>();                  
			}
			if (start > tempStart) {
				RangeVO rangeVO = new RangeVO();
				rangeVO.setStartRange(String.valueOf(tempStart));
				rangeVO.setEndRange(String.valueOf(start - 1));
				ranges.add(rangeVO);
			}
			tempStart = endRanges[i] + 1;
			i++;
		}
		if (tempStart <= tempEnd) {
			RangeVO rangeVO = new RangeVO();
			rangeVO.setStartRange(String.valueOf(tempStart));
			rangeVO.setEndRange(String.valueOf(tempEnd));
			ranges.add(rangeVO);
		}
		log.log(Log.FINE, "Final Confirmed Ranges -->", ranges);
		log.exiting("StockController", "getConfirmedStock");
		return ranges;
	}
	
	/**
	 * This method constructs TransitStockVO for each of the missing ranges
	 * Actual Range remains the same in constructed TransitStockVOs
	 *  
	 * @param actualVO
	 * @return
	 */
	private Collection<TransitStockVO> constructTransitStockVOsForMissingRanges(
			TransitStockVO actualVO) throws SystemException {
		log.entering("StockController",
				"constructTransitStockVOsForMissingRanges");
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		Collection<TransitStockVO> missingTransitStockVOs = null;
		TransitStockVO transitStockVO = null;
		Collection<MissingStockVO> missingStockVOs = actualVO
				.getMissingRanges();
		if (missingStockVOs != null) {
			for (MissingStockVO missingStockVO : missingStockVOs) {
				if (!MissingStockVO.OPERATION_FLAG_DELETE.equals(missingStockVO
						.getOperationFlag())) {
					//transitStockVO = new TransitStockVO();

					transitStockVO = new TransitStockVO(actualVO);
					/*transitStockVO.setCompanyCode(actualVO.getCompanyCode());
					transitStockVO.setAirlineIdentifier(actualVO
							.getAirlineIdentifier());
					transitStockVO.setDocumentType(actualVO.getDocumentType());
					transitStockVO.setDocumentSubType(actualVO
							.getDocumentSubType());
					transitStockVO.setActualStartRange(actualVO
							.getActualStartRange());
					transitStockVO.setActualEndRange(actualVO
							.getActualEndRange());
					transitStockVO.setNumberOfDocs(actualVO.getNumberOfDocs());
					transitStockVO.setTxnCode(actualVO.getTxnCode());*/
					transitStockVO.setTxnDate(new LocalDate(logonAttributes
								.getAirportCode(), Location.ARP, true));
					transitStockVO
							.setConfirmStatus(TransitStockVO.TRANSIT_STATUS_MISSING);
					/*transitStockVO.setStockHolderCode(actualVO
							.getStockHolderCode());
					transitStockVO.setStockControlFor(actualVO
							.getStockControlFor());
					transitStockVO.setManual(actualVO.isManual());*/
					// Missing Stock Details
					transitStockVO.setMissingEndRange(missingStockVO
							.getMissingEndRange());
					transitStockVO.setMissingStartRange(missingStockVO
							.getMissingStartRange());
					transitStockVO.setAsciiMissingEndRange(missingStockVO
							.getAsciiMissingEndRange());
					transitStockVO.setAsciiMissingStartRange(missingStockVO
							.getAsciiMissingStartRange());
					transitStockVO.setMissingNumberOfDocs((missingStockVO
							.getAsciiMissingEndRange()
							- missingStockVO.getAsciiMissingStartRange())+1);
					transitStockVO.setLastUpdateUser(logonAttributes
							.getUserId());
					transitStockVO.setLastUpdateTime(new LocalDate(
							logonAttributes.getAirportCode(), Location.ARP,
							true));
					transitStockVO
							.setOperationFlag(TransitStockVO.OPERATION_FLAG_INSERT);
					transitStockVO.setMissingRemarks(missingStockVO.getMissingRemarks());
					if (missingTransitStockVOs == null) {
						missingTransitStockVOs = new ArrayList<TransitStockVO>();
					}
					missingTransitStockVOs.add(transitStockVO);
				}
			}
		}
		log.log(Log.FINE, "TransitStockVOs for missing range -->",
				missingTransitStockVOs);
		log.entering("StockController",
				"constructTransitStockVOsForMissingRanges");
		return missingTransitStockVOs;
	}
	
	 //added for icrd-3024 	ends
	private void createStockHolderStockDetails(
			StockAllocationVO stockAllocationVO, String txncode)
			throws SystemException {

		Map<String, String> sysparVal = new HashMap<String, String>();
		Collection<String> sysPar = new ArrayList<String>();
		sysPar.add(STOCK_DEFAULTS_STOCKHOLDER_ACCOUNTING_PARAM);
		String accountingFlag=null;
		try {
			sysparVal = new SharedDefaultsProxy()
					.findSystemParameterByCodes(sysPar);

		} catch (ProxyException exception) {
			throw new SystemException(exception.getMessage());
		}
		accountingFlag = sysparVal
				.get(STOCK_DEFAULTS_STOCKHOLDER_ACCOUNTING_PARAM);
		if(StockAllocationVO.FLAG_YES.equals(accountingFlag)){

		// set PK values for StockHolderStockDetailsVO

		log.log(Log.FINE, "StockHolderCode -->", stockAllocationVO.getStockHolderCode());
		log.log(Log.FINE, "StockControlFor -->", stockAllocationVO.getStockControlFor());
		StockHolderStockDetail stockDetail = null;
		StockHolderStockDetail stockController = null;
		// if(StockAllocationVO.MODE_BLACKLIST.equals(txncode)){
		if (StockAllocationVO.MODE_BLACKLIST.equals(txncode)) {
			try {
					stockController = findStockHolderStockDetail(
							stockAllocationVO,
						stockAllocationVO.getStockHolderCode());
				stockController.setBlackListedStock(stockController
						.getBlackListedStock()
							+ getNumberOfDocuments(stockAllocationVO
									.getRanges()));
			} catch (FinderException e) {
				// To be reviewed Auto-generated catch block
//printStackTraccee()();
				createNewStockHolderDetail(stockAllocationVO,
							stockAllocationVO.getStockHolderCode(),
							StockAllocationVO.MODE_BLACKLIST);
			}
		} 
		//for blacklist revoke
		else if ("BR".equals(txncode)) {
			try {
				stockDetail = findStockHolderStockDetail(stockAllocationVO,
						stockAllocationVO.getStockHolderCode());
					/*
					 * stockDetail.setReceivedStock(stockDetail.getReceivedStock(
					 * ) + getNumberOfDocuments(stockAllocationVO.getRanges()));
					 */
				stockDetail.setReturnUtilisedStock(stockDetail
						.getReturnUtilisedStock()
							+ getNumberOfDocuments(stockAllocationVO
									.getRanges()));
			} catch (FinderException e) {
				// To be reviewed Auto-generated catch block
//printStackTraccee()();
				createNewStockHolderDetail(stockAllocationVO,
						stockAllocationVO.getStockHolderCode(), "BR");
			}
		} else if (StockAllocationVO.MODE_UTILIZED_RETURNED.equals(txncode)) {
			try {
				LocalDate localDate=null;
				Calendar lastUpdatedTime =null;
				stockDetail = findStockHolderStockDetail(stockAllocationVO,
						stockAllocationVO.getStockHolderCode());
					if (stockDetail.getLastUpdateTimeUTC() == null) {
					localDate = new LocalDate(LocalDate.NO_STATION,
								Location.NONE, true);
						GMTDate dateUTC = localDate.toGMTDate();
						lastUpdatedTime = dateUTC.setTime("00:00:00")
								.toCalendar();

					} else {
			 lastUpdatedTime = stockDetail.getLastUpdateTimeUTC();
				}
					GMTDate capturedDateUTC = stockAllocationVO
							.getCaptureDateUTC();
				  if(capturedDateUTC.toCalendar().before(lastUpdatedTime)){
				stockDetail.setReturnUtilisedStock(stockDetail
						.getReturnUtilisedStock()
								+ getNumberOfDocuments(stockAllocationVO
										.getRanges()));
					}
					else{
						log.log(log.INFO, "captureDateUTC greater than LastUpdatedTimeUTC..Not updating....ReturnUtilized exiting-----");
				  }
			} catch (FinderException e) {
//printStackTraccee()();
				log.log(log.INFO,"create a new stockholder");
					LocalDate localDate = new LocalDate(LocalDate.NO_STATION,
							Location.NONE, true);
					GMTDate dateUTC = localDate.toGMTDate();
					Calendar lastUpdatedTime = dateUTC.setTime("00:00:00")
							.toCalendar();
					GMTDate capturedDateUTC = stockAllocationVO
							.getCaptureDateUTC();
					if (capturedDateUTC.toCalendar().before(lastUpdatedTime)) {

						createNewStockHolderDetail(stockAllocationVO,
								stockAllocationVO.getStockHolderCode(),
								StockAllocationVO.MODE_UTILIZED_RETURNED);
					}
			}

		} else if (stockAllocationVO.isNewStockFlag()) {
			// Create new for HQ.set received stock only
			try {
				stockDetail = findStockHolderStockDetail(stockAllocationVO,
						stockAllocationVO.getStockHolderCode());
				stockDetail.setReceivedStock(stockDetail.getReceivedStock()
							+ getNumberOfDocuments(stockAllocationVO
									.getRanges()));
			} catch (FinderException e) {
				// To be reviewed Auto-generated catch block
//printStackTraccee()();
				createNewStockHolderDetail(stockAllocationVO,
						stockAllocationVO.getStockHolderCode(), "R");
			}

		} else if (StockAllocationVO.MODE_TRANSFER.equals(stockAllocationVO
				.getTransferMode())) {
			// Transferred
			// Received will be against the stkhldcod who does the transfer.
			try {
				stockDetail = findStockHolderStockDetail(stockAllocationVO,
						stockAllocationVO.getStockHolderCode());
				stockDetail.setReceivedStock(stockDetail.getReceivedStock()
							+ getNumberOfDocuments(stockAllocationVO
									.getRanges()));
			} catch (FinderException e) {
				// To be reviewed Auto-generated catch block
//printStackTraccee()();
				createNewStockHolderDetail(stockAllocationVO,
						stockAllocationVO.getStockHolderCode(), "R");
			}
				// Transfered will be against the stkhldcod who does the
				// transfer.
			try {
					stockController = findStockHolderStockDetail(
							stockAllocationVO,
						stockAllocationVO.getStockControlFor());
				stockController.setTransferredStock(stockController
						.getTransferredStock()
							+ getNumberOfDocuments(stockAllocationVO
									.getRanges()));
			} catch (FinderException e) {
				// To be reviewed Auto-generated catch block
//printStackTraccee()();
				createNewStockHolderDetail(stockAllocationVO,
							stockAllocationVO.getStockControlFor(),
							StockAllocationVO.MODE_TRANSFER);
			}
		} else if (StockAllocationVO.MODE_RETURN.equals(stockAllocationVO
				.getTransferMode())) {
			try {
				stockDetail = findStockHolderStockDetail(stockAllocationVO,
						stockAllocationVO.getStockHolderCode());
				stockDetail.setReceivedStock(stockDetail.getReceivedStock()
							+ getNumberOfDocuments(stockAllocationVO
									.getRanges()));
			} catch (FinderException e) {
				// To be reviewed Auto-generated catch block
//printStackTraccee()();
				createNewStockHolderDetail(stockAllocationVO,
						stockAllocationVO.getStockHolderCode(), "R");
			}
			// Transfered will be against the stkhldcod who does the
			// transfer.
			try {
					stockController = findStockHolderStockDetail(
							stockAllocationVO,
						stockAllocationVO.getStockControlFor());
					stockController.setReturnStock(stockController
							.getReturnStock()
							+ getNumberOfDocuments(stockAllocationVO
									.getRanges()));
			} catch (FinderException e) {
				// To be reviewed Auto-generated catch block
//printStackTraccee()();
				createNewStockHolderDetail(stockAllocationVO,
						stockAllocationVO.getStockControlFor(), "RT");
			}
		} else {
			// allocate
			// set allocated stock.stockholderfor--->who do the operation
			// stockholdercode--->to whom the operation is performing
			try {
				stockDetail = findStockHolderStockDetail(stockAllocationVO,
						stockAllocationVO.getStockHolderCode());
				stockDetail.setReceivedStock(stockDetail.getReceivedStock()
							+ getNumberOfDocuments(stockAllocationVO
									.getRanges()));
			} catch (FinderException e) {

//printStackTraccee()();
				createNewStockHolderDetail(stockAllocationVO,
						stockAllocationVO.getStockHolderCode(), "R");
			}
			try {
					stockController = findStockHolderStockDetail(
							stockAllocationVO,
						stockAllocationVO.getStockControlFor());
				stockController.setAllocatedStock(stockController
						.getAllocatedStock()
							+ getNumberOfDocuments(stockAllocationVO
									.getRanges()));
			} catch (FinderException e) {
				// To be reviewed Auto-generated catch block
//printStackTraccee()();
				createNewStockHolderDetail(stockAllocationVO,
						stockAllocationVO.getStockControlFor(), "A");
			}
		}
		}
	}

	// To be reviewed Auto-generated catch block
//printStackTraccee()();

	// }
	/*
	 * if(stockHolderStockDetail!=null){ if(stockAllocationVO.isNewStockFlag()){
	 * stockHolderStockDetail.setReceivedStock(stockHolderStockDetail
	 * .getReceivedStock() +
	 * getNumberOfDocuments(stockAllocationVO.getRanges())); }
	 *
	 * //stockHolderStockDetail.setLastUpdateTime(stockAllocationVO.
	 * getLastUpdateTime());
	 * stockHolderStockDetail.setLastUpdateUser(stockAllocationVO
	 * .getLastUpdateUser()); }
	 */

	private void createNewStockHolderDetail(
			StockAllocationVO stockAllocationVO, String stockHolderCode,
			String type) throws SystemException {
		// Pk find failed
		StockDetailsVO stockDetailsVO=new StockDetailsVO();
		// StockHolderStockDetailsVO stockHolderStockDetailsVO = new
		// StockHolderStockDetailsVO();
		stockDetailsVO.setCompanyCode(stockAllocationVO.getCompanyCode());
		stockDetailsVO.setStockHolderCode(stockHolderCode);
		stockDetailsVO.setDocumentType(stockAllocationVO.getDocumentType());
		stockDetailsVO.setDocumentSubType(stockAllocationVO
				.getDocumentSubType());
		stockDetailsVO.setTransactionDate(new LocalDate(LocalDate.NO_STATION,
				Location.NONE, true));
		stockDetailsVO
				.setLastUpdatedUser(stockAllocationVO.getLastUpdateUser());
		stockDetailsVO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION,
				Location.NONE, true));

		log
				.log(Log.FINE, "StockHolderStockDetailsVO111  ---->",
						stockDetailsVO);
		if ("R".equals(type)) {
			stockDetailsVO
					.setReceivedStock(getNumberOfDocuments(stockAllocationVO
							.getRanges()));
		} else if ("A".equals(type)) {
			stockDetailsVO
					.setAllocatedStock(getNumberOfDocuments(stockAllocationVO
							.getRanges()));
		} else if (StockAllocationVO.MODE_TRANSFER.equals(type)) {
			stockDetailsVO
					.setTransferredStock(getNumberOfDocuments(stockAllocationVO
							.getRanges()));
		} else if ("RT".equals(type)) {
			stockDetailsVO
					.setReturnedStock(getNumberOfDocuments(stockAllocationVO
							.getRanges()));
		} else if (StockAllocationVO.MODE_BLACKLIST.equals(type)) {
			stockDetailsVO
					.setBlacklistedStock(getNumberOfDocuments(stockAllocationVO
							.getRanges()));
		} else if (StockAllocationVO.MODE_UTILIZED_RETURNED.equals(type)) {
			stockDetailsVO
					.setReturnedUtilizedStock(getNumberOfDocuments(stockAllocationVO
							.getRanges()));
		} else {
			stockDetailsVO
					.setReturnedUtilizedStock(getNumberOfDocuments(stockAllocationVO
							.getRanges()));
			/*
			 * stockDetailsVO
			 * .setReceivedStock(getNumberOfDocuments(stockAllocationVO
			 * .getRanges()));
			 */
		}
		new StockHolderStockDetail(stockDetailsVO, null);

		log.log(Log.FINE, "StockHolderStockDetailsVO---->", stockDetailsVO);
	}

	private StockHolderStockDetail findStockHolderStockDetail(
			StockAllocationVO stockAllocationVO, String stockHolderCode)
			throws SystemException, FinderException {
		StockHolderStockDetailPK pk = new StockHolderStockDetailPK();
		LocalDate localDate = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, true);

		pk.setCompanyCode(stockAllocationVO.getCompanyCode());
		pk.setStockHolderCode(stockHolderCode);
		pk.setDocumentType(stockAllocationVO.getDocumentType());
		pk.setDocumentSubType(stockAllocationVO.getDocumentSubType());

		// pk.setTxnDateString(localDate.toGMTDate().toDisplayFormat());
		// localDate.toGMTDate().toDisplayFormat("YYYYMMDD"));
		String issueDate = TimeConvertor.toStringFormat(localDate.toGMTDate()
				.toCalendar(), TimeConvertor.NORMAL_DATE_FORMAT);
		issueDate=issueDate.replace("/","");
		pk.setTxnDateString(Integer.parseInt(issueDate));
		StockHolderStockDetail stockHolderStockDetail = null;

		stockHolderStockDetail = StockHolderStockDetail.find(pk);

		return stockHolderStockDetail;
	}
	/*
	 * this method is used to remove all the invalid ranges or
	 * invalid documents from the range that is passed ...
	 * the range in which and invalid awb is existing is splitted into two
	 * ranges and is returned..
	 *
	 */
	private Collection<RangeVO> removeInvalidDocuments(
			StockAllocationVO stockAllocationVO, Collection<String> documents)
		throws SystemException{

		Collection<SharedRangeVO> removableRanges = new ArrayList<SharedRangeVO>();
		Collection<SharedRangeVO> originalRanges = new ArrayList<SharedRangeVO>();
		Collection<RangeVO> resultRanges = null;
		if(stockAllocationVO.getRanges() != null){
			for(RangeVO rangeVO : stockAllocationVO.getRanges()){
				SharedRangeVO orgShrRangeVo = new SharedRangeVO();
				orgShrRangeVo.setFromrange(rangeVO.getStartRange());
				orgShrRangeVo.setRangeDate(rangeVO.getStockAcceptanceDate());
				orgShrRangeVo.setToRange(rangeVO.getEndRange());
				originalRanges.add(orgShrRangeVo);
			}
		}
		if(documents != null){
			for(String document : documents){
				//invalidDocuments.add(document.substring(0,7));
				String invalidDocument = document.substring(RangeVO.SUBSTRING_START,RangeVO.SUBSTRING_COUNT);
				SharedRangeVO removeShrRangeVO = new SharedRangeVO();
				removeShrRangeVO.setFromrange(invalidDocument);
				removeShrRangeVO.setToRange(invalidDocument);
				removableRanges.add(removeShrRangeVO);
			}
		}

		Collection<SharedRangeVO> splitRanges = null;
		try {
			splitRanges = new DocumentTypeProxy().splitRanges(originalRanges, removableRanges);
		} catch (ProxyException proxyException) {
			log.log(Log.SEVERE,"===ProxyException From DocumentTypeProxy->splitRanges...");
			log.log(Log.SEVERE,"===ProxyException From DocumentTypeProxy->splitRanges...");
			log.log(Log.SEVERE,"===ProxyException From DocumentTypeProxy->splitRanges...");
			throw new SystemException(proxyException.getMessage(), proxyException);
		}
		if(splitRanges != null){
			resultRanges = new ArrayList<RangeVO>();
			for(SharedRangeVO splitShrRangeVO : splitRanges){
				RangeVO rangeVO = new RangeVO();
				rangeVO.setCompanyCode(stockAllocationVO.getCompanyCode());
				rangeVO.setStockHolderCode(stockAllocationVO.getStockHolderCode());
				rangeVO.setAirlineIdentifier(stockAllocationVO.getAirlineIdentifier());
				rangeVO.setStartRange(splitShrRangeVO.getFromrange());
				rangeVO.setStockAcceptanceDate(splitShrRangeVO.getRangeDate());
				rangeVO.setEndRange(splitShrRangeVO.getToRange());
				resultRanges.add(rangeVO);
			}
		}
		return resultRanges;
	}

	private int getNumberOfDocuments(Collection<RangeVO> ranges){
		int numberOfDocuments = 0;
		if(ranges == null){
			return 0;
		}else{
			for(RangeVO rangeVO : ranges){
				numberOfDocuments += Range.difference(rangeVO.getStartRange(),rangeVO.getEndRange());
			}
		}
		return numberOfDocuments;
	}

	/**
	 * This method performs the audit against StockHolder entity. This call is
	 * routed through the AuditController
	 *
	 * @param auditVO
	 * @throws SystemException
	 */
	public void auditStockHolder(AuditVO auditVO) throws SystemException {
		new StockHolderAudit((StockHolderAuditVO) auditVO);
	}

	/**
	 * This method performs the audit against StockRequest entity. This call is
	 * routed through the AuditController
	 *
	 * @author A-1883
	 * @param auditVO
	 * @throws SystemException
	 */
	public void auditStockRequest(AuditVO auditVO) throws SystemException {
		//new StockRequestAudit((StockRequestAuditVO) auditVO);
	}

	/**
	 * auditing the stock allocation
	 *
	 * @param auditVO
	 * @throws SystemException
	 */
	public void auditAllocateStock(AuditVO auditVO) throws SystemException {
		new StockHolderAudit((StockHolderAuditVO) auditVO);
	}

	/**
	 * Method for auditing blacklist operation
	 *
	 * @param auditVO
	 * @throws SystemException
	 */
	public void auditBlacklistStock(AuditVO auditVO) throws SystemException {
		new BlacklistStockAudit((BlacklistStockAuditVO) auditVO);
	}

	/**
	 * method for revoking blacklisted stock
	 *
	 * @param blacklistStockVO
	 * @throws SystemException
	 * @throws FinderException
	 * @throws StockNotFoundException
	 * @throws BlacklistedRangeExistsException
	 * @throws RangeExistsException
	 * @throws InvalidStockHolderException
	 * @throws StockControlDefaultsBusinessException
	 */
	@Advice(name = "stockcontrol.defaults.blacklistStock" , phase=Phase.POST_INVOKE)
	public void revokeBlacklistStock(BlacklistStockVO blacklistStockVO)
			throws SystemException, FinderException, StockNotFoundException,
			BlacklistedRangeExistsException, RangeExistsException,
			InvalidStockHolderException, StockControlDefaultsBusinessException {
		StockAuditVO auditVO = new StockAuditVO(
				StockAuditVO.AUDIT_PRODUCTNAME,
				StockAuditVO.AUDIT_MODULENAME,
				StockAuditVO.AUDIT_ENTITY);
		//BlackListStock blacklistStock = new BlackListStock();
		Collection<RangeVO> rangesToBeAllocatedAfterRevoke = new ArrayList<RangeVO>();
		BlacklistStockVO blacklistVO=new BlacklistStockVO();
		String startRange = blacklistStockVO.getRangeFrom();
		String endRange = blacklistStockVO.getRangeTo();
		String newRangeFrom = blacklistStockVO.getNewRangeFrom();
		String newRangeTo = blacklistStockVO.getNewRangeTo();



	    //Added by A-2881 for ICRD-3082-starts
		boolean traceNeeded=false;
		Collection<String> systemparameterCodes=new ArrayList<String>();
		systemparameterCodes.add(STOCK_DEFAULTS_ENABLESTOCKHISTORY);
		
		try{
			Map<String,String> sysmap= new SharedDefaultsProxy().findSystemParameterByCodes(systemparameterCodes);
			traceNeeded = StockAllocationVO.FLAG_YES.equals(sysmap.get(STOCK_DEFAULTS_ENABLESTOCKHISTORY))?true:false;
		}
		catch(ProxyException proxyException){
			for(ErrorVO ex : proxyException.getErrors()){
				throw new SystemException(ex.getErrorCode());
			}
		}
		//Added by A-2881 for ICRD-3082-ends

		//BlacklistStockVO blackllistStockVOCreate = new BlacklistStockVO();
		blacklistStockVO.setStatus("A");
		BlackListStock blacklistStock = BlackListStock.find(blacklistStockVO);
		log.log(Log.FINE, "before calling revoke method from controller");
		Collection<RangeVO> ranges = blacklistStock.revoke(blacklistStockVO);
		log.log(Log.FINE, "after calling revoke method from controller");
		if (ranges != null) {
			log.log(Log.FINE, "ranges  in  controller   ");
			for (RangeVO rangeVo : ranges) {
				log.log(Log.FINE,"after calling revoke method from controller");
				log.log(Log.FINE, rangeVo.getStartRange());
				log.log(Log.FINE, rangeVo.getEndRange());
				blacklistStockVO.setRangeFrom(rangeVo.getStartRange());
				blacklistStockVO.setRangeTo(rangeVo.getEndRange());
				log.log(Log.FINE, "before going to persist new black list   ");
				blacklistStockVO.setStatus("A");
				BlackListStock blackListStock = new BlackListStock(blacklistStockVO);
				auditVO = (StockAuditVO)AuditUtils.populateAuditDetails(auditVO,blackListStock,true);
		       	auditStock(blackListStock,auditVO,AuditVO.CREATE_ACTION,"BLACKLIST_STOCK");

			}
			blacklistStockVO.setRangeFrom(newRangeFrom);
			blacklistStockVO.setRangeTo(newRangeTo);
			blacklistStockVO.setStatus("D");
			Collection<BlacklistStockVO> deletingRanges = BlackListStock.findBlackListRangesForRevoke
				(blacklistStockVO);
			log.log(Log.FINE, "---Ranges to be deled--->>", deletingRanges);
			if(deletingRanges!=null){
				for(BlacklistStockVO blkStkVo : deletingRanges){
					blkStkVo.setStatus("D");
					log.log(Log.FINE, "---BlacklistVo--->>", blkStkVo);
					blacklistStock = BlackListStock.find(blkStkVo);
					Collection<RangeVO> rangesDelete = null;
					if(blacklistStock!=null){
						rangesDelete = blacklistStock.revoke(blacklistStockVO);
					}
					if(rangesDelete!=null){
						Collection<SharedRangeVO> sharedRangesToDelete = new ArrayList<SharedRangeVO>();
						Collection<SharedRangeVO> sharedRangesToRevoke = new ArrayList<SharedRangeVO>();
						SharedRangeVO shrRevokeRangeVO = new SharedRangeVO();
						shrRevokeRangeVO.setFromrange(blacklistStock.getBlackListStockPk().getStartRange());
						shrRevokeRangeVO.setToRange(blacklistStock.getBlackListStockPk().getEndRange());
						sharedRangesToRevoke.add(shrRevokeRangeVO);
						log.log(Log.FINE,
								" Delete ranges in stock controller----->",
								rangesDelete);
						for(RangeVO rangeVo : rangesDelete){
							log.log(Log.FINE,"after calling revoke method from controller");
							log.log(Log.FINE, rangeVo.getStartRange());
							log.log(Log.FINE, rangeVo.getEndRange());
							blkStkVo.setRangeFrom(rangeVo.getStartRange());
							blkStkVo.setRangeTo(rangeVo.getEndRange());
							blkStkVo.setLastUpdateTime(blacklistStockVO.getLastUpdateTime());
							blkStkVo.setLastUpdateUser(blacklistStockVO.getLastUpdateUser());
							blkStkVo.setBlacklistDate(blacklistStockVO.getBlacklistDate());
							blkStkVo.setRemarks(blacklistStockVO.getRemarks());
							log
									.log(
											Log.FINE,
											"before going to persist new black list   ",
											blkStkVo);
							blkStkVo.setStatus("D");
							BlackListStock blackListStock = new BlackListStock(blkStkVo);
							auditVO = (StockAuditVO)AuditUtils.populateAuditDetails(auditVO,blackListStock,true);
							auditStock(blackListStock,auditVO,AuditVO.CREATE_ACTION,"BLACKLIST_STOCK");

							SharedRangeVO removeShrRangeVO = new SharedRangeVO();
							removeShrRangeVO.setFromrange(rangeVo.getStartRange());
							removeShrRangeVO.setToRange(rangeVo.getEndRange());
							sharedRangesToDelete.add(removeShrRangeVO);
						}
						try{
							Collection<SharedRangeVO> shrRangesToBeAllocated = new DocumentTypeProxy().splitRanges(sharedRangesToRevoke,sharedRangesToDelete);
							for(SharedRangeVO sharedRangeVO : shrRangesToBeAllocated){
								RangeVO rangeVo = new RangeVO();
								rangeVo.setStartRange(sharedRangeVO.getFromrange());
								rangeVo.setEndRange(sharedRangeVO.getToRange());
								//Modified by A-7373 for ICRD-241944
								rangeVo.setManual(blkStkVo.isManual());
								rangeVo.setBlackList(false);
								rangesToBeAllocatedAfterRevoke.add(rangeVo);
							}
						}catch(ProxyException proxyException){
							for (ErrorVO errorVo : proxyException.getErrors()) {
								throw new SystemException(errorVo.getErrorCode());
							}
						}
					}
					//For creating the ranges for allocating
					else{
						RangeVO rangeVo = new RangeVO();
						if(blacklistStock!=null){
							rangeVo.setStartRange(blacklistStock.getBlackListStockPk().getStartRange());
							rangeVo.setEndRange(blacklistStock.getBlackListStockPk().getEndRange());
						}
						//Modified by A-7373 for ICRD-241944
						rangeVo.setManual(blkStkVo.isManual());
						rangeVo.setBlackList(false);
						rangesToBeAllocatedAfterRevoke.add(rangeVo);
					}
				}
			}

			//Alocate Stockto HQ After revoke
			StockAllocationVO stockAllocationVo = new StockAllocationVO();
			stockAllocationVo.setCompanyCode(blacklistStockVO.getCompanyCode());
			stockAllocationVo.setStockHolderCode("HQ");
			StockHolder st = StockHolder.find(stockAllocationVo
					.getCompanyCode(),stockAllocationVo.getStockHolderCode());
			stockAllocationVo.setDocumentType(blacklistStockVO
					.getDocumentType());
			stockAllocationVo.setDocumentSubType(blacklistStockVO
					.getDocumentSubType());
			stockAllocationVo.setAirlineIdentifier(blacklistStockVO
					.getAirlineIdentifier());
			stockAllocationVo.setNewStockFlag(true);
			//Added by A-7373 for ICRD-241944
			for(BlacklistStockVO blkStokVo : deletingRanges)
			{
			stockAllocationVo.setManual(blkStokVo.isManual());
			}
			
			stockAllocationVo.setLastUpdateTime(new LocalDate
					(LocalDate.NO_STATION,Location.NONE,st.getLastUpdateTime(),true));
			stockAllocationVo.setLastUpdateUser(blacklistStockVO.getLastUpdateUser());
			stockAllocationVo.setBlacklist(true);
			stockAllocationVo.setTransferMode(StockAllocationVO.MODE_NORMAL);
			//Added by A-2881 for ICRD-3082 -starts
			if(traceNeeded){
				stockAllocationVo.setTransactionCode(StockAllocationVO.MODE_REVOKE);
			}
			//Added by A-2881 for ICRD-3082 - ends
			//stockAllocationVo.setOperationFlag(StockAllocationVO.OPERATION_FLAG_INSERT);
			stockAllocationVo.setRanges(rangesToBeAllocatedAfterRevoke);
			log.log(Log.FINE, "before going to call allocate stock");
			try{
				StockController stockController = (StockController)SpringAdapter.
				getInstance().getBean("stockcontroldefaultsController");
				stockController.allocateStock(stockAllocationVo);
			}
			catch(StockNotFoundException ex){
				log.log(Log.FINE,"LLLLLLSTOCKNOTFOUND FOR HQLLLLLLL");
			}
			log.log(Log.FINE, "after allocate during revoke blacklist");
            //Added here by A-7373 for the manual flag setting
			//Added by A-2434 for sending the full ranges after revoke to the operations module
			RangeVO rangeVoForOperations = new RangeVO();
			rangeVoForOperations.setStartRange(newRangeFrom);
			rangeVoForOperations.setEndRange(newRangeTo);
			//Added by A-7373 for ICRD-241944
			for(BlacklistStockVO blkStockVo : deletingRanges)
			{
			rangeVoForOperations.setManual(blkStockVo.isManual());
			}
			rangeVoForOperations.setBlackList(false);
			Collection<RangeVO> rangeVOSForOperations = new ArrayList<RangeVO>();
			rangeVOSForOperations.add(rangeVoForOperations);
//					Added to change blacklisted status during revoke
			OperationsShipmentProxy operationsShipmentProxy = new OperationsShipmentProxy();
			try {
				log.log(Log.FINE, "----->>>Companycode <<<------",
						blacklistStockVO.getCompanyCode());
				log.log(Log.FINE, "----->>>Airline ID <<<------",
						blacklistStockVO.getAirlineIdentifier());
				log.log(Log.FINE, "----->>>Calling blacklistRange <<<------");
				operationsShipmentProxy.blacklistRange(blacklistStockVO
						.getCompanyCode(), blacklistStockVO
						.getAirlineIdentifier(), rangeVOSForOperations);
			} catch (ProxyException proxyException) {
				for (ErrorVO errorVo : proxyException.getErrors()) {
					throw new SystemException(errorVo.getErrorCode());
				}
			}

		}
		/*
		 * auditBlacklistStockRevoke(blacklistStock,BlacklistStockAuditVO.
		 * STOCK_BLKLST_REVOKE_ACTIONCODE,
		 * blacklistStockVO.getNewRangeFrom(),blacklistStockVO.
		 * getNewRangeTo());
		 */
		log.exiting("StockController", "revokeBlacklistStock");
	}

	/**
	 * This method is used to fetch the stockholder types sorted based on
	 * prioity
	 *
	 * @param companyCode
	 * @return Collection<StockHolderPriorityVO>
	 * @throws SystemException
	 */
	public Collection<StockHolderPriorityVO> findStockHolderTypes(
			String companyCode) throws SystemException {
		return StockHolder.findStockHolderTypes(companyCode);
	}

	/**
	 * @param companyCode
	 * @param stockHolderCode
	 * @param docType
	 * @param docSubType
	 * @throws SystemException
	 * @throws StockNotFoundException
	 */
	public void checkStock(String companyCode, String stockHolderCode,
			String docType, String docSubType) throws SystemException,
			StockNotFoundException {
		StockHolder.checkStock(companyCode, stockHolderCode, docType,
				docSubType);
	}

	/**
	 *
	 * @param companyCode
	 * @param stockHolderCodes
	 * @return Collection<StockHolderPriorityVO>
	 * @throws SystemException
	 */
	public Collection<StockHolderPriorityVO> findPriorities(String companyCode,
			Collection<String> stockHolderCodes) throws SystemException {
		return StockHolder.findPriorities(companyCode, stockHolderCodes);
	}

	/**
	 *
	 * @param companyCode
	 * @param stockHolderCode
	 * @param docType
	 * @param docSubType
	 * @return String
	 * @throws SystemException
	 */
	public String findApproverCode(String companyCode, String stockHolderCode,
			String docType, String docSubType) throws SystemException {
		return StockHolder.findApproverCode(companyCode, stockHolderCode,
				docType, docSubType);
	}


	/**
	 * Method for blacklisting a stock
	 *
	 * @param blacklistStockVO
	 * @throws SystemException
	 * @throws StockNotFoundException
	 * @throws BlacklistedRangeExistsException
	 * @throws StockControlDefaultsBusinessException
	 */
	@Advice(name = "stockcontrol.defaults.blacklistStock" , phase=Phase.POST_INVOKE) 
	public void blacklistStock(BlacklistStockVO blacklistStockVO)
			throws SystemException, StockNotFoundException,
			BlacklistedRangeExistsException,
			StockControlDefaultsBusinessException {
		if(blacklistStockVO.getRangeFrom() != null && blacklistStockVO.getRangeFrom().trim().length() > 0
				&& blacklistStockVO.getRangeTo()!= null && blacklistStockVO.getRangeTo().trim().length() > 0){
			log.log(Log.FINE, "\n\nEntry into range blacklist\n\n",
					blacklistStockVO);
			StockFilterVO stockFilterVO = new StockFilterVO();
			stockFilterVO.setCompanyCode(blacklistStockVO.getCompanyCode());
			stockFilterVO.setAirlineIdentifier(blacklistStockVO.getAirlineIdentifier());
			stockFilterVO.setDocumentType(blacklistStockVO.getDocumentType());
			stockFilterVO.setDocumentSubType(blacklistStockVO.getDocumentSubType());
			stockFilterVO.setStockHolderCode(blacklistStockVO.getStockHolderCode());
			stockFilterVO.setRangeFrom(blacklistStockVO.getRangeFrom());
			stockFilterVO.setRangeTo(blacklistStockVO.getRangeTo());
			
				String masterDocumentNumber = checkStockUtilisationForRange(stockFilterVO);
				if(masterDocumentNumber!=null){
					throw new StockControlDefaultsBusinessException(StockControlDefaultsBusinessException.UTILISATION_EXISTS);
				}
			
			blacklistStockHolderStock(blacklistStockVO);
		
		}

		else  {
			StockFilterVO stockFilterVO = new StockFilterVO();
			stockFilterVO.setCompanyCode(blacklistStockVO.getCompanyCode());
			stockFilterVO.setAirlineIdentifier(blacklistStockVO.getAirlineIdentifier());
			stockFilterVO.setDocumentType(blacklistStockVO.getDocumentType());
			stockFilterVO.setDocumentSubType(blacklistStockVO.getDocumentSubType());
			stockFilterVO.setStockHolderCode(blacklistStockVO.getStockHolderCode());
			stockFilterVO.setRangeFrom(blacklistStockVO.getRangeFrom());
			stockFilterVO.setRangeTo(blacklistStockVO.getRangeTo());
			
				String masterDocumentNumber = checkStockUtilisationForRange(stockFilterVO);
				if(masterDocumentNumber!=null){
					throw new StockControlDefaultsBusinessException(StockControlDefaultsBusinessException.UTILISATION_EXISTS);
				}
			
			log.log(Log.FINE,"\n\nEntry into stock holder blacklist\n\n");
			try {
				StockHolder stkhldr = StockHolder.find(blacklistStockVO.getCompanyCode(),blacklistStockVO.getStockHolderCode());
				StockVO stockVO = new StockVO();
				stockVO.setDocumentType(blacklistStockVO.getDocumentType());
				stockVO.setDocumentSubType(blacklistStockVO.getDocumentSubType());
				stockVO.setAirlineIdentifier(blacklistStockVO.getAirlineIdentifier());
				Stock stk = stkhldr.getStock(stockVO);


					for (Range range : stk.getRanges()) {
						BlacklistStockVO blkstockVO = new BlacklistStockVO();
						blkstockVO.setRangeFrom(range.getStartRange());
						blkstockVO.setRangeTo(range.getEndRange());
						blkstockVO.setCompanyCode(blacklistStockVO.getCompanyCode());
						blkstockVO.setStationCode(blacklistStockVO.getStationCode());
						blkstockVO.setStockHolderCode(blacklistStockVO.getStockHolderCode());
						blkstockVO.setDocumentType(blacklistStockVO.getDocumentType());
						blkstockVO.setDocumentSubType(blacklistStockVO.getDocumentSubType());
						blkstockVO.setAirlineIdentifier(blacklistStockVO.getAirlineIdentifier());
						blkstockVO.setBlacklistDate(blacklistStockVO.getBlacklistDate());
						blkstockVO.setLastUpdateTime(blacklistStockVO.getLastUpdateTime());
						blkstockVO.setLastUpdateUser(blacklistStockVO.getLastUpdateUser());
						blkstockVO.setRemarks(blacklistStockVO.getRemarks());
						log
								.log(
										Log.FINE,
										"\n\nbefore calling black list blkstockVO \n\n",
										blkstockVO);
						blacklistStockHolderStock(blkstockVO);
					}

			} catch (InvalidStockHolderException invalidStockHolderException) {
				log.log(Log.FINE, "&&&&%%%%--StockHolder Not exists\n\n ");
				throw new StockControlDefaultsBusinessException(
							invalidStockHolderException);
			}
		}

	}

	/**
	 * @author A-1885
	 * @param stocks
	 * @param blacklistStockVO
	 * @return Collection<StockVO>
	 */
	private Collection<StockVO> rearrangeRanges(Collection<StockVO> stocks,
			BlacklistStockVO blacklistStockVO) {
		log.log(Log.FINE,"-------Entering rearrange ranges---START------------");
		if (stocks != null && stocks.size() > 0) {
			int size = 0;
			long maxEndRange = 0;
			long minStartRange = 0;
			RangeVO rangeMax = null;
			RangeVO rangeMin = null;
			ArrayList<StockVO> stockVos = (ArrayList<StockVO>) stocks;
			log
					.log(Log.FINE, "------stockVos.size()---------", stockVos.size());
			size = stockVos.size();
			for (int i = 0; i < size; i++) {
				log.log(Log.FINE, "----------------Index-------------", i);
				StockVO stockVo = null;
				stockVo = stockVos.get(i);
				log.log(Log.FINE, "--------Index stock vo--------", stockVo);
				ArrayList<RangeVO> ranges = (ArrayList<RangeVO>) stockVo
						.getRanges();
				RangeVO rangeVo = null;
				if (ranges != null && ranges.size() > 0) {
					rangeVo = ranges.get(0);
				}
				log.log(Log.FINE, "---------Index Range vo--------", rangeVo);
				if (rangeVo!= null && i == 0) {
					maxEndRange = rangeVo.getAsciiEndRange();
					rangeMax = rangeVo;
					minStartRange = rangeVo.getAsciiStartRange();
					rangeMin = rangeVo;
				} else {
					if (rangeVo!= null && (rangeVo.getAsciiEndRange() > maxEndRange)) {
						log.log(Log.FINE, "----Maximum eng range---", rangeVo);
						maxEndRange = rangeVo.getAsciiEndRange();
						rangeMax = null;
						rangeMax = rangeVo;
					}
					if (rangeVo!= null && (rangeVo.getAsciiStartRange() < minStartRange)) {
						log.log(Log.FINE, "----Minimum eng range----", rangeVo);
						minStartRange = rangeVo.getAsciiStartRange();
						rangeMin = null;
						rangeMin = rangeVo; 
					}
				}
			}
			long startRange = findLong(blacklistStockVO.getRangeFrom());
			long endRange = findLong(blacklistStockVO.getRangeTo());
			if (endRange < rangeMax.getAsciiEndRange()) {
				rangeMax.setEndRange(blacklistStockVO.getRangeTo());
			}
			if (startRange > rangeMin.getAsciiStartRange()) {
				rangeMin.setStartRange(blacklistStockVO.getRangeFrom());
			}
			log.log(Log.FINE,
					"---------Entering rearrange ranges---END--------", stocks);
		}
		log.log(Log.FINE,"---------------END---------------------------------");
		return stocks;
	}

	/**
	 * @author A-1885
	 * @param stockHolder
	 * @param auditVo
	 * @param actionCode
	 * @throws SystemException
	 */
	private void auditStockHolderDetails(StockHolder stockHolder,
			StockHolderAuditVO auditVo,String actionCode)
			throws SystemException {
		log.log(Log.FINE,"ENTRY");
		StringBuilder additionalInfo = new StringBuilder();
		//StringBuilder stockInfo = new StringBuilder();
		auditVo.setCompanyCode(stockHolder.getStockHolderPk()
				.getCompanyCode());
		auditVo.setStockHolderCode(stockHolder.getStockHolderPk()
				.getStockHolderCode());
		auditVo.setStockHolderType(stockHolder.getStockHolderType());
		auditVo.setActionCode(actionCode);
		auditVo.setLastUpdateUser(stockHolder.getLastUpdateUser());
		for (AuditFieldVO auditField : auditVo.getAuditFields()) {
			if(auditField==null) {
				continue;
			}
			additionalInfo.append(" Field Name: ").append(
					auditField.getFieldName()).append(" Field Description: ")
					.append(auditField.getDescription()).append(" Old Value: ")
					.append(auditField.getOldValue()).append(" New Value: ")
					.append(auditField.getNewValue());
		}
		//additionalInfo.append(" STOCKHOLDER ").append(actionCode);
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
		log.log(Log.FINE,"RETURN");

	}
	/**
	 * @author A-1598
	 * @param auditFields
	 * @param stockRequestVO
	 * @param actionCode
	 * @throws SystemException
	 */
	/*private void auditStockRequestDetails(StockRequest stockRequest,
			StockRequestAuditVO auditVo,String actionCode)
			throws SystemException {
		StringBuilder additionalInfo = new StringBuilder();
		//StringBuilder stockInfo = new StringBuilder();
		auditVo.setCompanyCode(stockRequest.getStockRequestPk().companyCode);
		auditVo.setStockHolderCode(stockRequest.getStockHolderCode());
		auditVo.setDocumentSubType(stockRequest.getDocumentSubType());
		auditVo.setDocumentType(stockRequest.getDocumentType());
		auditVo.setActionCode(actionCode);
		auditVo.setTxnTime(new LocalDate(Calendar.getInstance(),true));
		auditVo.setUserId(stockRequest.getLastUpdateUser());
		auditVo.setRequestRefNumber(stockRequest.getStockRequestPk().requestRefNumber);

		for (AuditFieldVO auditField : auditVo.getAuditFields()) {
			additionalInfo.append(" Field Name: ").append(
					auditField.getFieldName()).append(" Field Description: ")
					.append(auditField.getDescription()).append(" Old Value: ")
					.append(auditField.getOldValue()).append(" New Value: ")
					.append(auditField.getNewValue());
		}
		additionalInfo.append(" STOCKREQUEST ").append(actionCode);
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

	}*/
	/**
	 * @author A-1598
	 * @param obj
	 * @param auditVo
	 * @param actionCode
	 * @param remark
	 * @throws SystemException
	 */
	public void auditStock(Object obj,
			StockAuditVO auditVo,String actionCode,String remark)
			throws SystemException {
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		StringBuilder additionalInfo = new StringBuilder();
		if(("DEPLETE_STOCK").equals(remark)){
			StockAllocationVO vo = (StockAllocationVO)obj;

			auditVo.setCompanyCode(vo.getCompanyCode());
			auditVo.setDocSubType(vo.getDocumentSubType());
			auditVo.setDocType(vo.getDocumentType());
			auditVo.setAirlineId(vo.getAirlineIdentifier());
			auditVo.setUserId(logonAttributes.getUserId());
			additionalInfo.append("STOCK HOLDER CODE").append(vo.getStockHolderCode());
		}else if(("ALLOCATE_STOCK").equals(remark)){
			Stock stock = (Stock)obj;

			auditVo.setCompanyCode(stock.getStockPK().getCompanyCode());
			auditVo.setDocSubType(stock.getStockPK().getDocumentSubType());
			auditVo.setDocType(stock.getStockPK().getDocumentType());
			auditVo.setAirlineId(stock.getStockPK().getAirlineIdentifier());
			auditVo.setUserId(logonAttributes.getUserId());
			additionalInfo.append("STOCK HOLDER CODE").append(stock.getStockPK().getStockHolderCode());

		}else if(("BLACKLIST_STOCK").equals(remark)){
			BlackListStock blackListStock = (BlackListStock)obj;
			auditVo.setCompanyCode(blackListStock.getBlackListStockPk()
					.getCompanyCode());
			auditVo.setDocSubType(blackListStock.getBlackListStockPk()
					.getDocumentSubType());
			auditVo.setDocType(blackListStock.getBlackListStockPk()
					.getDocumentType());
		    auditVo.setAirlineId(blackListStock.getBlackListStockPk()
		    		.getAirlineIdentifier());
			auditVo.setUserId(logonAttributes.getUserId());
		}
		auditVo.setActionCode(actionCode);
		auditVo.setTxnTime(new LocalDate(LocalDate.NO_STATION,
				Location.NONE,Calendar.getInstance(),true));
		for (AuditFieldVO auditField : auditVo.getAuditFields()) {
			if(auditField != null){
				additionalInfo.append(" Field Name: ").append(
					auditField.getFieldName()).append(" Field Description: ")
					.append(auditField.getDescription()).append(" Old Value: ")
					.append(auditField.getOldValue()).append(" New Value: ")
					.append(auditField.getNewValue());
				additionalInfo.append(" <br> ");
			}
		}
		additionalInfo.append(remark).append(" - ").append(actionCode).append(" ");
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
	/**
	 * @author A-1598
	 * @param blackListStock
	 * @param auditVo
	 * @param actionCode
	 * @param isToRevoke
	 * @throws SystemException
	 */
	protected void auditBlackListStockDetails(BlackListStock blackListStock,
			BlacklistStockAuditVO auditVo,String actionCode,boolean isToRevoke)
			throws SystemException {
		StringBuilder additionalInfo = new StringBuilder();

		auditVo.setCompanyCode(blackListStock.getBlackListStockPk()
				.getCompanyCode());


		auditVo.setDocumentSubType(blackListStock.getBlackListStockPk()
				.getDocumentSubType());
		auditVo.setDocumentType(blackListStock.getBlackListStockPk()
				.getDocumentType());
		auditVo.setActionCode(actionCode);
		auditVo.setTxnTime(new LocalDate(LocalDate.NO_STATION,
				Location.NONE,Calendar.getInstance(),true));
		auditVo.setUserId(blackListStock.getLastUpdateUser());


		for (AuditFieldVO auditField : auditVo.getAuditFields()) {
			additionalInfo.append(" Field Name: ").append(
					auditField.getFieldName()).append(" Field Description: ")
					.append(auditField.getDescription()).append(" Old Value: ")
					.append(auditField.getOldValue()).append(" New Value: ")
					.append(auditField.getNewValue());
		}
		if(isToRevoke){
		additionalInfo.append(" Revoke BlackList Stock ").append(actionCode);
		}else{
			additionalInfo.append(" BlackListStock ").append(actionCode);
		}
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
	/**
	 * @author A-1883
	 * @param auditFields
	 * @param stockRequest
	 * @param stockRequestVO
	 * @param actionCode
	 * @throws SystemException
	 */
/*	private void auditStockRequestDetails(Collection<AuditFieldVO> auditFields,
			StockRequest stockRequest, StockRequestVO stockRequestVO,
			String actionCode) throws SystemException {
		StringBuilder additionalInfo = new StringBuilder();
		StockRequestAuditVO stockRequestAuditVO = new StockRequestAuditVO(
				StockRequestVO.STOCKREQUEST_AUDIT_PRODUCTNAME,
				StockRequestVO.STOCKREQUEST_AUDIT_MODULENAME,
				StockRequestVO.STOCKREQUEST_AUDIT_ENTITYNAME);
		// stockRequestAuditVO.setCompanyCode(stockRequest.
		// getStockRequestPk().companyCode);
		stockRequestAuditVO.setRequestRefNumber(stockRequest
				.getStockRequestPk().requestRefNumber);
		stockRequestAuditVO.setStockHolderCode(stockRequestVO
				.getStockHolderCode());
		stockRequestAuditVO.setDocumentType(stockRequestVO.getDocumentType());
		stockRequestAuditVO.setDocumentSubType(stockRequestVO
				.getDocumentSubType());
		stockRequestAuditVO.setActionCode(actionCode);
		// constructing additional info with field name, description,
		// old value and new value
		for (AuditFieldVO auditField : auditFields) {
			additionalInfo.append(" Field Name: ").append(
					auditField.getFieldName()).append(" Field Description: ")
					.append(auditField.getDescription()).append(" Old Value: ")
					.append(auditField.getOldValue()).append(" New Value: ")
					.append(auditField.getNewValue());
		}
		stockRequestAuditVO.setAdditionalInfo(additionalInfo.toString());
		if (StockRequestVO.OPERATION_FLAG_INSERT.equals(stockRequestVO
				.getOperationFlag())) {
			stockRequestAuditVO.setAuditRemarks(AuditAction.INSERT.toString());
		} else if (StockRequestVO.OPERATION_FLAG_UPDATE.equals(stockRequestVO
				.getOperationFlag())) {
			stockRequestAuditVO.setAuditRemarks(AuditAction.UPDATE.toString());
		}

		 * else if(StockRequestVO.OPERATION_FLAG_CANCEL.equals
		 * (stockRequestVO.getOperationFlag())){ //To be reviewed change DELETE to CANCEL
		 * and also the hardcoding
		 * stockRequestAuditVO.setAuditRemarks(AuditAction.DELETE. toString());
		 * stockRequestAuditVO.setAdditionalInfo(stockRequest.
		 * getStockRequestPk().requestRefNumber+"Cancelled"); }


		AuditUtils.performAudit(stockRequestAuditVO);
		// To be reviewed remove after testing
		StockControlAuditMDB stockControlAuditMDB = new StockControlAuditMDB();
		Collection ary = new ArrayList();
		ary.add(stockRequestAuditVO);
		// stockControlAuditMDB.audit(ary);
		// remove

	}
*/
	/**
	 * @author A-1883
	 * @param stockRequest
	 * @param actionCode
	 * @throws SystemException
	 */
/*	private void auditStockRequestCancelDetails(StockRequest stockRequest,
			String actionCode) throws SystemException {
		StringBuilder additionalInfo = new StringBuilder();
		StockRequestAuditVO stockRequestAuditVO = new StockRequestAuditVO(
				StockRequestVO.STOCKREQUEST_AUDIT_PRODUCTNAME,
				StockRequestVO.STOCKREQUEST_AUDIT_MODULENAME,
				StockRequestVO.STOCKREQUEST_AUDIT_ENTITYNAME);
		// stockRequestAuditVO.setCompanyCode(stockRequest.
		// getStockRequestPk().companyCode);
		stockRequestAuditVO.setRequestRefNumber(stockRequest
				.getStockRequestPk().requestRefNumber);
		stockRequestAuditVO.setStockHolderCode(stockRequest
				.getStockHolderCode());
		stockRequestAuditVO.setDocumentType(stockRequest.getDocumentType());
		stockRequestAuditVO.setDocumentSubType(stockRequest
				.getDocumentSubType());
		stockRequestAuditVO.setActionCode(actionCode);
		// To be reviewed remove Hanrdcoding
		stockRequestAuditVO.setAuditRemarks("CANCEL");
		additionalInfo.append("Stock Request Reference Number-").append(
				stockRequest.getStockRequestPk().requestRefNumber).append(
				" : Cancelled");
		stockRequestAuditVO.setAdditionalInfo(additionalInfo.toString());
		AuditUtils.performAudit(stockRequestAuditVO);
		// To be reviewed remove after testing
		StockControlAuditMDB stockControlAuditMDB = new StockControlAuditMDB();
		Collection ary = new ArrayList();
		ary.add(stockRequestAuditVO);
		// stockControlAuditMDB.audit(ary);
		// remove
	}
*/
	/**
	 * @author A-1885
	 * @param stockHolder
	 * @param actionCode
	 * @throws SystemException
	 */
/*	private void auditStockHolderDeleteDetails(StockHolder stockHolder,
			String actionCode) throws SystemException {
		StockHolderAuditVO stockHolderAuditVO = new StockHolderAuditVO(
				StockHolderVO.STOCKHOLDER_AUDIT_PRODUCTNAME,
				StockHolderVO.STOCKHOLDER_AUDIT_MODULENAME,
				StockHolderVO.STOCKHOLDER_AUDIT_ENTITYNAME);
		// stockHolderAuditVO.setCompanyCode(stockHolder.
		// getStockHolderPk().companyCode);
		stockHolderAuditVO
				.setStockHolderCode(stockHolder.getStockHolderPk().stockHolderCode);
		stockHolderAuditVO.setStockHolderType(stockHolder.getStockHolderType());
		stockHolderAuditVO.setActionCode(actionCode);
		stockHolderAuditVO.setAdditionalInfo("Stock Holder Deleted");
		stockHolderAuditVO.setAuditRemarks(AuditAction.DELETE.toString());
		stockHolderAuditVO.setLastUpdateTime(stockHolder.getLastUpdateTime());
		stockHolderAuditVO.setLastUpdateUser(stockHolder.getLastUpdateUser());
		AuditUtils.performAudit(stockHolderAuditVO);
		StockControlAuditMDB stockControlAuditMDB = new StockControlAuditMDB();
		Collection ary = new ArrayList();
		ary.add(stockHolderAuditVO);
		// stockControlAuditMDB.audit(ary);
	}
*/
	/**
	 * For auditing range allocation
	 *
	 * @param stockAllocationVO
	 * @param actionCode
	 * @throws SystemException
	 */
/*	private void auditAllocateRangeDetails(StockAllocationVO stockAllocationVO,
			String actionCode) throws SystemException {
		log.log(Log.FINE,"---->>Inside auditAllocateRangeDetails----------->>>");
		log.log(Log.FINE,"---->>Inside auditAllocateRangeDetails code--->>>"
						+ stockAllocationVO.getStockHolderCode());
		log.log(Log.FINE,"-->>Inside auditAllocateRangeDetails action code-->>>"
						+ actionCode);
		log.log(Log.FINE,"------>>Inside auditAllocateRangeDetails time--->>>"
						+ stockAllocationVO.getLastUpdateTime().toCalendar());
		log.log(Log.FINE,"---->>Inside auditAllocateRangeDetails-user------->>>"
						+ stockAllocationVO.getLastUpdateUser());
		log.log(Log.FINE,"----->>Inside auditAllocateRangeDetails----------->>>"
						+ stockAllocationVO.getDocumentType());
		log.log(Log.FINE,"---->>Inside auditAllocateRangeDetails----------->>>"
						+ stockAllocationVO.getDocumentSubType());
		StringBuilder additionalInfo = new StringBuilder();
		StringBuilder rangeInfo = new StringBuilder();
		AllocateStockAuditVO allocateStockAuditVO = new AllocateStockAuditVO(
				StockAllocationVO.ALLOCATERANGE_AUDIT_PRODUCTNAME,
				StockAllocationVO.ALLOCATERANGE_AUDIT_MODULENAME,
				StockAllocationVO.ALLOCATERANGE_AUDIT_ENTITYNAME);
		allocateStockAuditVO.setStockHolderCode(stockAllocationVO
				.getStockHolderCode());
		allocateStockAuditVO.setActionCode(actionCode);
		allocateStockAuditVO.setLastUpdateTime(stockAllocationVO
				.getLastUpdateTime().toCalendar());
		allocateStockAuditVO.setLastUpdateUser(stockAllocationVO
				.getLastUpdateUser());
		allocateStockAuditVO.setDocType(stockAllocationVO.getDocumentType());
		allocateStockAuditVO.setDocSubType(stockAllocationVO
				.getDocumentSubType());
		additionalInfo.append("New Range with Ranges of Type:").append(
				stockAllocationVO.getDocumentType()).append("--").append(
				stockAllocationVO.getDocumentSubType());
		for (RangeVO rangeVo : stockAllocationVO.getRanges()) {
			rangeInfo.append(rangeVo.getStartRange().concat("-")).append(
					rangeVo.getEndRange());
		}
		additionalInfo.append(",").append(rangeInfo);
		allocateStockAuditVO.setAdditionalInfo(additionalInfo.toString());
		allocateStockAuditVO.setAuditRemarks(AuditAction.INSERT.toString());
		AuditUtils.performAudit(allocateStockAuditVO);
		StockControlAuditMDB stockControlAuditMDB = new StockControlAuditMDB();
		Collection ary = new ArrayList();
		ary.add(allocateStockAuditVO);
		// stockControlAuditMDB.audit(ary);

	}
*/
	/**
	 * For auditing the stockDeplete details
	 *
	 * @param stockHolder
	 * @param docType
	 * @param docSubType
	 * @param actionCode
	 * @throws SystemException
	 */
/*	private void auditDepleteDetails(StockHolder stockHolder, String docType,
			String docSubType, String actionCode) throws SystemException {
		AllocateStockAuditVO allocateStockAuditVO = new AllocateStockAuditVO(
				StockAllocationVO.ALLOCATERANGE_AUDIT_PRODUCTNAME,
				StockAllocationVO.ALLOCATERANGE_AUDIT_MODULENAME,
				StockAllocationVO.ALLOCATERANGE_AUDIT_ENTITYNAME);
		// stockHolderAuditVO.setCompanyCode(stockHolder.getStockHolderPk().companyCode);
		allocateStockAuditVO
				.setStockHolderCode(stockHolder.getStockHolderPk().stockHolderCode);
		allocateStockAuditVO.setDocType(docType);
		allocateStockAuditVO.setDocSubType(docSubType);
		allocateStockAuditVO.setActionCode(actionCode);
		allocateStockAuditVO.setAdditionalInfo("Stock Holder depleted");
		allocateStockAuditVO.setAuditRemarks(AuditAction.DELETE.toString());
		allocateStockAuditVO.setLastUpdateTime(stockHolder.getLastUpdateTime());
		allocateStockAuditVO.setLastUpdateUser(stockHolder.getLastUpdateUser());
		AuditUtils.performAudit(allocateStockAuditVO);
		StockControlAuditMDB stockControlAuditMDB = new StockControlAuditMDB();
		Collection ary = new ArrayList();
		ary.add(allocateStockAuditVO);
		// stockControlAuditMDB.audit(ary);
	}
*/
	/**
	 * Auditing of black listing
	 *
	 * @param blacklistStockVO
	 * @param actionCode
	 * @throws SystemException
	 */
/*	private void auditBlacklistStock(BlacklistStockVO blacklistStockVO,
			String actionCode) throws SystemException {
		StringBuilder additionalInfo = new StringBuilder();
		BlacklistStockAuditVO blacklistStockAuditVO = new BlacklistStockAuditVO(
				BlacklistStockVO.STOCKHOLDER_AUDIT_PRODUCTNAME,
				BlacklistStockVO.STOCKHOLDER_AUDIT_MODULENAME,
				BlacklistStockVO.STOCKHOLDER_AUDIT_ENTITYNAME);
		blacklistStockAuditVO.setDocumentType(blacklistStockVO
				.getDocumentType());
		blacklistStockAuditVO.setDocumentSubType(blacklistStockVO
				.getDocumentSubType());
		blacklistStockAuditVO.setActionCode(actionCode);
		additionalInfo.append("Blacklist range is :").append(
				blacklistStockVO.getRangeFrom()).append("-").append(
				blacklistStockVO.getRangeTo());
		blacklistStockAuditVO.setAdditionalInfo(additionalInfo.toString());
		blacklistStockAuditVO.setAuditRemarks(AuditAction.INSERT.toString());
		AuditUtils.performAudit(blacklistStockAuditVO);
		StockControlAuditMDB stockControlAuditMDB = new StockControlAuditMDB();
		Collection ary = new ArrayList();
		ary.add(blacklistStockAuditVO);
		// stockControlAuditMDB.audit(ary);
	}
*/
	/**
	 * Auditing the revoking of blacklist
	 *
	 * @param blacklistStock
	 * @param actionCode
	 * @param rangeFrom
	 * @param rangeTo
	 * @throws SystemException
	 */
/*	private void auditBlacklistStockRevoke(BlackListStock blacklistStock,
			String actionCode, String rangeFrom, String rangeTo)
			throws SystemException {
		StringBuilder additionalInfo = new StringBuilder();
		BlacklistStockAuditVO blacklistStockAuditVO = new BlacklistStockAuditVO(
				BlacklistStockVO.STOCKHOLDER_AUDIT_PRODUCTNAME,
				BlacklistStockVO.STOCKHOLDER_AUDIT_MODULENAME,
				BlacklistStockVO.STOCKHOLDER_AUDIT_ENTITYNAME);
		blacklistStockAuditVO.setDocumentType(blacklistStock
				.getBlackListStockPk().documentType);
		blacklistStockAuditVO.setDocumentSubType(blacklistStock
				.getBlackListStockPk().documentSubType);
		blacklistStockAuditVO.setActionCode(actionCode);
		blacklistStockAuditVO.setAuditRemarks(AuditAction.DELETE.toString());
		additionalInfo.append("Revoked Range is :").append(rangeFrom).append(
				"-").append(rangeTo);
		blacklistStockAuditVO.setAdditionalInfo(additionalInfo.toString());
		AuditUtils.performAudit(blacklistStockAuditVO);
		StockControlAuditMDB stockControlAuditMDB = new StockControlAuditMDB();
		Collection ary = new ArrayList();
		ary.add(blacklistStockAuditVO);
		// stockControlAuditMDB.audit(ary);
	}
*/
	/**
	 * For auditing allocating range
	 *
	 * @param stockAllocationVO
	 * @param actionCode
	 * @param stockHolderType
	 * @throws SystemException
	 */
/*	private void auditAllocateRange(StockAllocationVO stockAllocationVO,
			String actionCode, String stockHolderType) throws SystemException {
		StringBuilder additionalInfo = new StringBuilder();
		StringBuilder docInfo = new StringBuilder();
		StringBuilder rangeInfo = new StringBuilder();
		StockHolderAuditVO stockHolderAuditVO = new StockHolderAuditVO(
				StockHolderVO.STOCKHOLDER_AUDIT_PRODUCTNAME,
				StockHolderVO.STOCKHOLDER_AUDIT_MODULENAME,
				StockHolderVO.STOCKHOLDER_AUDIT_ENTITYNAME);
		stockHolderAuditVO.setStockHolderCode(stockAllocationVO
				.getStockHolderCode());
		stockHolderAuditVO.setStockHolderType(stockHolderType);
		stockHolderAuditVO.setActionCode(actionCode);
		stockHolderAuditVO.setAuditRemarks(AuditAction.INSERT.toString());
		stockHolderAuditVO.setLastUpdateTime(stockAllocationVO
				.getLastUpdateTime());
		stockHolderAuditVO.setLastUpdateUser(stockAllocationVO
				.getLastUpdateUser());
		docInfo.append("DocType:").append(stockAllocationVO.getDocumentType())
				.append(",").append("DocSubType:").append(
						stockAllocationVO.getDocumentSubType());
		for (RangeVO range : stockAllocationVO.getRanges()) {
			rangeInfo.append(range.getStartRange()).append("-").append(
					range.getEndRange());
		}
		additionalInfo.append(docInfo).append(rangeInfo);
		stockHolderAuditVO.setAdditionalInfo(additionalInfo.toString());
		AuditUtils.performAudit(stockHolderAuditVO);
		StockControlAuditMDB stockControlAuditMDB = new StockControlAuditMDB();
		Collection ary = new ArrayList();
		ary.add(stockHolderAuditVO);
		// stockControlAuditMDB.audit(ary);

	}
*/
	/**
	 * For auditing deplete range
	 *
	 * @param stockHolder
	 * @param docType
	 * @param docSubType
	 * @param actionCode
	 * @throws SystemException
	 */
/*	private void auditDepleteRange(StockHolder stockHolder, String docType,
			String docSubType, String actionCode) throws SystemException {
		StringBuilder additionalInfo = new StringBuilder();
		StockHolderAuditVO stockHolderAuditVO = new StockHolderAuditVO(
				StockHolderVO.STOCKHOLDER_AUDIT_PRODUCTNAME,
				StockHolderVO.STOCKHOLDER_AUDIT_MODULENAME,
				StockHolderVO.STOCKHOLDER_AUDIT_ENTITYNAME);
		stockHolderAuditVO.setActionCode(actionCode);
		stockHolderAuditVO.setAuditRemarks(AuditAction.DELETE.toString());
		stockHolderAuditVO.setActionCode(actionCode);
		stockHolderAuditVO
				.setStockHolderCode(stockHolder.getStockHolderPk().stockHolderCode);
		stockHolderAuditVO.setStockHolderType(stockHolder.getStockHolderType());
		stockHolderAuditVO.setLastUpdateTime(stockHolder.getLastUpdateTime());
		stockHolderAuditVO.setLastUpdateUser(stockHolder.getLastUpdateUser());
		additionalInfo.append("Range with DocType:").append(docType)
				.append(",").append("DocSubType:").append(docSubType).append(
						"Depleted");
		stockHolderAuditVO.setAdditionalInfo(additionalInfo.toString());
		AuditUtils.performAudit(stockHolderAuditVO);
		StockControlAuditMDB stockControlAuditMDB = new StockControlAuditMDB();
		Collection ary = new ArrayList();
		ary.add(stockHolderAuditVO);
		// stockControlAuditMDB.audit(ary);
	}
*/
	/**
	 * @author A-1883
	 * @param companyCode
	 * @param doctype
	 * @param documentNumber
	 * @return Boolean
	 * @throws SystemException
	 */
	public Boolean checkForBlacklistedDocument(String companyCode,
			String doctype, String documentNumber) throws SystemException {
		log.entering("StockController", "checkForBlacklistedDocument");
		if (BlackListStock.checkForBlacklistedDocument(companyCode, doctype,
				documentNumber)) {
			return Boolean.valueOf(true);
		} else {
			return  Boolean.valueOf(false);
		}

	}

	/**
	 * Method for calculating the base of number
	 *
	 * @author A-1885
	 * @param input
	 * @return base
	 */
	public long calculateBase(char input) {
		long inside = input;
		long base = 0;
		try {
			base = Integer.parseInt("" + input);
		} catch (NumberFormatException numberFormatException) {
			base = inside - 65;
		}
		return base;
	}

	/**
	 * To get the numeric value of the string
	 *
	 * @author A-1885
	 * @param range
	 * @return Numeric value
	 */
	public long findLong(String range) {
		char[] sArray = range.toCharArray();
		long base = 1;
		long sNumber = 0;
		for (int i = sArray.length - 1; i >= 0; i--) {
			sNumber += base * calculateBase(sArray[i]);
			int index = sArray[i];
			if (index > 57) {
				base *= 26;
			} else {
				base *= 10;
			}
		}
		return sNumber;
	}




//*****************************************************************************
//******************         R2 coding starts       ***************************
//*****************************************************************************

	/*
	 * Added by Sinoob
	 */
	/**
	 * @param stockFilterVO
	 * @return StockAllocationVO
	 * @throws SystemException
	 */
	public StockAllocationVO findStockForAirline(StockFilterVO stockFilterVO)
			throws SystemException {
		return Stock.findStockForAirline(stockFilterVO);
	}


	/*
	 * This method is to check whether the stock holder code begins with (CTO),
	 * which identifies whether the stock is to deplete only or to deplete and
	 * allocate to somebody else...
	 *
	 * if the stock holder code is just the code which is returned from the
	 * system parameters , then only depletion is done...
	 *
	 */
	private boolean checkStockHolder(String companyCode, String airportCode, String stockControlFor) throws SystemException{
		Collection<String> parameterCodes = new ArrayList<String>();
		parameterCodes.add(STOCK_DEFAULTS_STOCKHOLDERCODE);
		Map<String,String> map = Stock.findAirportParametersByCode(companyCode,
				airportCode, parameterCodes);
		log.log(Log.FINE, "\n@@@@Map of Parameters", map);
		if(stockControlFor == null){
			log.log(Log.WARNING,"---stockControlFor NOT set----");
			log.log(Log.WARNING,"---stockControlFor NOT set----");
		}else{
			if(map == null){
				log.log(Log.WARNING,
						"---CTO Code NOT FOUND in arpParameters----",
						airportCode);
				log.log(Log.WARNING,
						"---CTO Code NOT FOUND in arpParameters----",
						airportCode);
			}else{
				log.log(Log.INFO, "---value from Map : ", map.get(STOCK_DEFAULTS_STOCKHOLDERCODE));
				if(map.get(STOCK_DEFAULTS_STOCKHOLDERCODE) != null &&
						map.get(STOCK_DEFAULTS_STOCKHOLDERCODE).toString().startsWith(stockControlFor)){
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * This method is to check whether the airlineCode passed is the
	 * home airline code, which is got from system parameters
	 */
/*	private boolean checkForHomeAirline(String airlineCode) throws SystemException{
		Collection<String> systemParameterCodes = new ArrayList<String>();
		systemParameterCodes.add(STOCK_DEFAULTS_HOMEAIRLINECODE);
		Map<String,String> map = Stock.findSystemParameterByCodes(systemParameterCodes);
		if(map.get(STOCK_DEFAULTS_HOMEAIRLINECODE).toString().equals(airlineCode)){
			return true;
		}
		return false;
	}
*/
	/**
	 * Get the next value
	 *
	 * @param document
	 * @param offset String value
	 * @return String Next value
	 */
	public String getNextDocument(String document,int offset){
		char[] sArray=document.toCharArray();
		int carry = 1;
		for(int k=0; k < offset; k++) {
			carry=1;
			for(int i=sArray.length-1;i>=0;i--){
				int value=sArray[i];
				value+=carry;
				if(value==58){
					value=48;
					carry=1;
				}else if(value==91){
					value=65;
					carry=1;
				}else {
					carry=0;
				}

				sArray[i]=(char)value;
				if (carry==0){
					break;
				}
			}
		}
		if (carry==1){
			return null;
		}
		return new String(sArray);
	}


	// Added by Sinoob ends

	/**
	 * @author A-1863
	 * @param stockFilterVO
	 * @return Page<StockVO>
	 * @throws SystemException
	 */
	public Page<StockVO> findStockList(StockFilterVO stockFilterVO)
			throws SystemException {
		return Stock.findStockList(stockFilterVO);
	}

	/**
	 * @author A-1863
	 * @param stockRequestForOALVO
	 * @throws SystemException
	 */
	public void saveStockRequestForOAL(StockRequestForOALVO stockRequestForOALVO)
			throws SystemException {
		log.entering("StockControler", "saveStockRequestForOAL");
		if (StockRequestForOALVO.OPERATION_FLAG_INSERT
				.equals(stockRequestForOALVO.getOperationFlag())) {
			new StockRequestForOAL(stockRequestForOALVO);
		}
	}

	/**
	 * @author A-1863
	 * @param stockFilterVO
	 * @return Collection<StockRequestForOALVO>
	 * @throws SystemException
	 */
	public Collection<StockRequestForOALVO> findStockRequestForOAL(
			StockFilterVO stockFilterVO) throws SystemException {
		log.entering("StockControler", "findStockRequestForOAL");
		return Stock.findStockRequestForOAL(stockFilterVO);
	}

	/*
	 * Added by Sinoob
	 */
    /**
     * @param reserveAWBVO
     * @param stockHolderCode
     * @param checkDigit
     * @throws SystemException
     * @throws StockControlDefaultsBusinessException
     * @throws ReservationException
     */
    public void validateSpecificAWBs(ReserveAWBVO reserveAWBVO,
    		String stockHolderCode, int checkDigit)
    		throws SystemException, StockControlDefaultsBusinessException,
    			ReservationException{
    	log.log(Log.INFO,"ENTRY");
    	Collection<ErrorVO> errors = null;

    	boolean hasCheckDigitFlag = true;
    	hasCheckDigitFlag = validateCheckDigitFlag(
							    			reserveAWBVO.getCompanyCode(),
							    			reserveAWBVO.getDocumentType(),
							    			reserveAWBVO.getDocumentSubType());


    	if(reserveAWBVO.getSpecificDocNumbers() != null){

    		for(String document : reserveAWBVO.getSpecificDocNumbers()){
    			DocumentFilterVO filterVO = new DocumentFilterVO();
    			filterVO.setCompanyCode(reserveAWBVO.getCompanyCode());
    			filterVO.setAirlineIdentifier(reserveAWBVO.getAirlineIdentifier());
    			filterVO.setStockOwner(stockHolderCode);
    			filterVO.setDocumentType(reserveAWBVO.getDocumentType());
    			filterVO.setDocumentSubType(reserveAWBVO.getDocumentSubType());
    			filterVO.setDocumentNumber(document);

    			// validating the document.. if invalid it will throw exception
    			validateDocument(filterVO);

    			if(hasCheckDigitFlag){
    				ErrorVO errorVO = validateCheckDigit(document, checkDigit);
    				if(errorVO != null){
    					if(errors == null){ 
    						errors = new ArrayList<ErrorVO>();
    					}
    					errors.add(errorVO);  
    				}
    			}
    		}
    	}
    	if(errors != null && errors.size() > 0){
    		ReservationException reservationException = new ReservationException();
    		reservationException.addErrors(errors);
    		log.log(Log.SEVERE,"----ReservationException---*****");
    		throw reservationException;
    	}


    	log.log(Log.INFO,"RETURN");
    	//return reserveAWBVO;
    }


    private ErrorVO validateCheckDigit(String document, int checkDigit){
		long mod = findLong(document.substring(RangeVO.SUBSTRING_START,RangeVO.SUBSTRING_COUNT)) % checkDigit;
		int carry = 0;
		ErrorVO errorVO = null;
		try {
			carry = Integer.parseInt(document.substring(RangeVO.SUBSTRING_COUNT));
		} catch (NumberFormatException numberFormatException) {
			log.log(Log.SEVERE,"----INVALID_DOCUMENT---*****");
			errorVO = ErrorUtils.getError(ReservationException.INVALID_DOCUMENT, new Object[]{document});
			return errorVO;
		}

		if(mod != carry){
			errorVO = ErrorUtils.getError(ReservationException.INVALID_DOCUMENT, new Object[]{document});
			log.log(Log.SEVERE,"----mod != carry ---INVALID_DOCUMENT---*****");
			return errorVO;
		}
		return errorVO;
    }


    /**
     * @param stockAllocationVO
     * @param reserveAWBVO
     * @param checkDigit
     * @return
     * @throws SystemException
     */
    public Collection<String> reserveGeneralDocuments(StockAllocationVO stockAllocationVO,
    					ReserveAWBVO reserveAWBVO, int checkDigit)
    		throws SystemException {
    	log.log(Log.INFO,"ENTRY");


    	boolean hasCheckDigitFlag = true;
    	hasCheckDigitFlag = validateCheckDigitFlag(
							    			reserveAWBVO.getCompanyCode(),
							    			reserveAWBVO.getDocumentType(),
							    			reserveAWBVO.getDocumentSubType());

    	int numberOfDocuments = reserveAWBVO.getNumberOfDocuments();
    	int count = 0;
    	Collection<String> documents = new ArrayList<String>();
    	if(reserveAWBVO.getSpecificDocNumbers() != null){
    		documents = new ArrayList<String>(reserveAWBVO.getSpecificDocNumbers());
    	}
    	if(stockAllocationVO.getRanges() != null){
    		for(RangeVO rangeVO : stockAllocationVO.getRanges()){
    			String firstDocument = null;
    			long mod = 0;
    			if(hasCheckDigitFlag){
	    			mod = findLong(rangeVO.getStartRange()) % checkDigit;
	    			firstDocument = new StringBuilder(rangeVO.getStartRange()).append(mod).toString();
    			}else{
    				firstDocument = rangeVO.getStartRange();
    			}
    			if(!documents.contains(firstDocument)){
    				documents.add(firstDocument);
    				count ++;
    			}
    			long numOfDocsInRangeVO = rangeVO.getNumberOfDocuments();
    			for(int i=1; i<numOfDocsInRangeVO && count < numberOfDocuments; i++){
    				String nextDocument = getNextDocument(rangeVO.getStartRange(),i);
    				if(hasCheckDigitFlag){
    					mod = findLong(nextDocument) % checkDigit;
    					nextDocument = new StringBuilder(nextDocument).append(mod).toString();
    				}
    				if(!documents.contains(nextDocument)){
    					documents.add(nextDocument);
    					count ++;
    				}
    			}
    			if(count >= numberOfDocuments){
    				break;
    			}
    		}
    	}
    	log.log(Log.INFO, "RETURN", documents);
		return documents;
    }

    /**
     * @param companyCode
     * @param documentType
     * @param documentSubType
     * @return
     * @throws SystemException
     */
    public boolean validateCheckDigitFlag(String companyCode,
    		String documentType, String documentSubType)
    	throws SystemException{

    	Collection<DocumentVO> documentVOs = null;
    	documentVOs = new SharedDocumentProxy().findDocumentDetails(companyCode, documentType, documentSubType);
    	if(documentVOs != null){
    		for(DocumentVO docVO : documentVOs){
    			if(DocumentVO.FLAG_NO.equals(docVO.getCheckDigitFlag())){
    				return false;
    			}
    			return true;    			
    		}
    	}
    	return true;
    }
    //	added by Sinoob ends

    public void autoProcessingAllocateStock(StockRequestVO stockRequestVO, String reqRefNo)throws SystemException{
		log.log(Log.INFO,
				"\n\n Entering autoProcessingAllocateStock ... stockRequestVO",
				stockRequestVO);
		stockRequestVO.setRequestRefNumber(reqRefNo);
		if(stockRequestVO != null){
        	try{
				StockHolder stockHolder = StockHolder.find(stockRequestVO
    					.getCompanyCode(), stockRequestVO.getStockHolderCode());
        		Collection<Stock> stockColl = stockHolder.getStock();
        		Collection<RangeVO> rangeVOs = null;
        		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
        		long numberOfDocs = 0;
        		log.log(Log.INFO,
						"\n\n\n ....stockRequestVO.getDocumentType()",
						stockRequestVO.getDocumentType());
				log.log(Log.INFO,
						"\n\n\n ....stockRequestVO.getDocumentType()",
						stockRequestVO.getDocumentSubType());
				if(stockRequestVO.getDocumentType() != null &&
        				stockRequestVO.getDocumentSubType() !=null){
					log.log(Log.INFO,"\n\n\n ....doctype and subtype not null ");
					log.log(Log.INFO, "\n\n\n ...stockColl ", stockColl);
					if(stockColl != null){
        				for(Stock stock : stockColl){
        					if((stockRequestVO.getDocumentType().equalsIgnoreCase
        							(stock.getStockPK().getDocumentType()) &&
        							(stockRequestVO.getDocumentSubType().
        							equalsIgnoreCase(stock.getStockPK()
        									.getDocumentSubType())))
        							&& (stock.getAutoprocessQuantity() > 0)){
        						 RangeFilterVO rangeFilterVO=new RangeFilterVO();
        						 rangeFilterVO.setCompanyCode(stockRequestVO.getCompanyCode());
		 						 rangeFilterVO.setDocumentType(stockRequestVO.getDocumentType());
		 						 rangeFilterVO.setDocumentSubType(stockRequestVO.getDocumentSubType());
		 						 log
										.log(
												Log.INFO,
												"\n\n\n ....stock.getAutoprocessQuantity()..",
												stock.getAutoprocessQuantity());
								log
										.log(
												Log.INFO,
												"\n\n\n ...stockRequestVO.getRequestedStock()........",
												stockRequestVO.getRequestedStock());
								if(stock.getAutoprocessQuantity()<stockRequestVO.getRequestedStock()){
									numberOfDocs = stock.getAutoprocessQuantity();
									log.log(Log.INFO, "\n In if.........",
											numberOfDocs);
		 						 }
		 						 else{
		 							numberOfDocs = stockRequestVO.getRequestedStock();
		 							log.log(Log.INFO, "\n In else.........",
											numberOfDocs);
		 						 }
		 						 rangeFilterVO.setNumberOfDocuments(String.valueOf(numberOfDocs));
		 						 rangeFilterVO.setStockHolderCode(stock.getStockApproverCode());
		 						 rangeFilterVO.setStartRange("0");
		 						 //Code Starts.  //Added as part of story : IASCB-112621
		 						 if(stockRequestVO.getAirlineIdentifier()!=null){
		 							 rangeFilterVO.setAirlineIdentifier(Integer.parseInt(stockRequestVO.getAirlineIdentifier()));
		 						 }else{
		 						 rangeFilterVO.setAirlineIdentifier(logonAttributes.getOwnAirlineIdentifier());
		 						 }
		 						 rangeFilterVO.setManual(stockRequestVO.isManual());//Added by A-8368 as part of bug IASCB-169131
		 						 //Code End
		 						 StockRequestApproveVO approverVo = new
		 						 StockRequestApproveVO();
		 						 stockRequestVO.setApprovedStock(numberOfDocs);
		 						 Collection<StockRequestVO> requests = new ArrayList
		 						 			<StockRequestVO>();
		 						 requests.add(stockRequestVO);
		 						 approverVo.setCompanyCode(stockRequestVO.getCompanyCode());
		 						 approverVo.setApproverCode(stock.getStockApproverCode());
		 						 approverVo.setDocumentType(stockRequestVO
		 								 .getDocumentType());
		 						 approverVo.setDocumentSubType(stockRequestVO
		 								 .getDocumentSubType());
		 						 approverVo.setStockRequests(requests);
		 						 approveStockRequests(approverVo);
		 						 log
										.log(
												Log.INFO,
												"\n going to find range....rangeFilterVO",
												rangeFilterVO);
								rangeVOs = findRanges(rangeFilterVO);  
        						 log
										.log(
												Log.FINE,
												"\n\n\n\n-------------Obtained rangeVOs-------------",
												rangeVOs);
 								if(rangeVOs != null && rangeVOs.size()>0 && (stock.getStockPK().getAirlineIdentifier() == Integer.parseInt(stockRequestVO.getAirlineIdentifier()))){
        							 	
									StockAllocationVO stockAllocationVO=new StockAllocationVO();

        								stockAllocationVO.setDocumentType(stockRequestVO.getDocumentType());
        								stockAllocationVO.setDocumentSubType(stockRequestVO.getDocumentSubType());
        								stockAllocationVO.setStockControlFor(stock.getStockApproverCode()); // From the stock details of the stockholder
        								stockAllocationVO.setStockHolderCode(stockRequestVO.getStockHolderCode());
        								stockAllocationVO.setManual(stockRequestVO.isManual());
        								stockAllocationVO.setCompanyCode(logonAttributes.getCompanyCode());
        								stockAllocationVO.setNewStockFlag(false);
        								stockAllocationVO.setAllocate(true);
        								stockAllocationVO.setTransferMode(StockAllocationVO.MODE_NORMAL);
        								stockAllocationVO.setTransactionCode(StockAllocationVO.MODE_ALLOCATE);
        								if(stockRequestVO.getAirlineIdentifier()!=null) {
											stockAllocationVO.setAirlineIdentifier(Integer.parseInt(stockRequestVO.getAirlineIdentifier()));
										} else {
											stockAllocationVO.setAirlineIdentifier(logonAttributes.getOwnAirlineIdentifier());
										}
        								stockAllocationVO.setLastUpdateUser(logonAttributes.getUserId());
        								stockAllocationVO.setRanges(rangeVOs);
        								stockAllocationVO.setRequestRefNumber(reqRefNo);
        								stockAllocationVO.setAwbPrefix(stockRequestVO.getAwbPrefix());
        								stockAllocationVO.setAutoAllocated(YES); 
        								log
												.log(
														Log.INFO,
														"\n going to allocateStock....stockAllocationVO",
														stockAllocationVO);
        								StockController stockController = (StockController)SpringAdapter.
        								getInstance().getBean("stockcontroldefaultsController");
        								stockController.allocateStock(stockAllocationVO);
        								
        								
        						 }
        						 
        					}
        				}
        			}
        		}

        	}
        	 catch (InvalidStockHolderException invalidStockHolderException) {
				 log.log(Log.FINE,"\n\n\n\n--------	InvalidStockHolderException");
        		 for(ErrorVO errorVo : invalidStockHolderException.getErrors()){
        			 throw new SystemException(errorVo.getErrorCode());
        		 }
     		 }
        	 catch (RangeExistsException rangeExistsException) {
					  log.log(Log.FINE,"\n\n\n\n--------	RangeExistsException");
        		 for(ErrorVO errorVo : rangeExistsException.getErrors()){
        			 throw new SystemException(errorVo.getErrorCode());
        		 }
        	 }
        	 catch (FinderException finderException) {
					  log.log(Log.FINE,"\n\n\n\n--------	FinderException");
       			 throw new SystemException(finderException.getErrorCode());
        	 }
        	 catch (StockNotFoundException stockNotFoundException) {
					  log.log(Log.FINE,"\n\n\n\n--------	StockNotFoundException");
        		 for(ErrorVO errorVo : stockNotFoundException.getErrors()){
        			 throw new SystemException(errorVo.getErrorCode());
        		 }
        	 }
        	 catch (BlacklistedRangeExistsException blacklistedRangeExistsException) {
					  log.log(Log.FINE,"\n\n\n\n--------	BlacklistedRangeExistsException");
        		 for(ErrorVO errorVo : blacklistedRangeExistsException.getErrors()){
        			 throw new SystemException(errorVo.getErrorCode());
        		 }
        	 }
        	 catch (StockControlDefaultsBusinessException stockControlDefaultsBusinessException) {
					  log.log(Log.FINE,"\n\n\n\n--------	StockControlDefaultsBusinessException");
        		 for(ErrorVO errorVo : stockControlDefaultsBusinessException.getErrors()){
        			 throw new SystemException(errorVo.getErrorCode());
        		 }
        	 }
       }
        log.log(Log.FINE,"\n\n\n\n--------Exiting..............allocateautoprocess");
    }

   	/**
	 * Method to find document details
	 * @param companyCode
	 * @param airlineIdentifier
	 * @param documentNumber
	 * @return
	 * @throws SystemException
	 */
    public StockRequestVO findDocumentDetails(String companyCode,
			int airlineIdentifier,String documentNumber)throws SystemException{
		return StockHolder.findDocumentDetails(companyCode,airlineIdentifier,
																documentNumber);
	}
    /**
     * @author a-1885
     * @param filterVo
     * @throws SystemException
     */
    public void sendReorderMessages(InformAgentFilterVO filterVo)throws
    SystemException{
    	log.log(Log.FINE,"----ENTERING sendReorderMessages -----");
    	Collection<StockHolderStockDetailsVO> details =
    		StockHolder.findStockHolderStockDetails(filterVo);
    	log.log(Log.FINE, "------Result After query:----", details);
		if(details!=null && details.size()>0){
    		for(StockHolderStockDetailsVO detailVo : details){
    			log.log(Log.FINE, "---in Loop detailVo :--", detailVo);
				if(((detailVo.getPhysicalStockAvailable()+
    					detailVo.getManualStockAvailable())-
    					detailVo.getReorderLevel())<=0){
    				if(detailVo.isAutoProcessing()){
    					log.log(Log.FINE,"=====autoprocessing====");
    					autoRequestForStock(detailVo,filterVo);
    				}
    				if(detailVo.isReorderAlertFlag()){
    					log.log(Log.FINE,"====Reorder alert====");
    					if(detailVo.getContactDetails()!=null
    							&& detailVo.getContactDetails().trim().length()>0){
    					sendMessageForStockHolder(detailVo,filterVo);
    					}
    				}
    			}
    		}
    	}

    }
    /**
     * @author a-1885
     * @param detailVo
     * @param filterVo
     * @throws SystemException
     */
    private void sendMessageForStockHolder(StockHolderStockDetailsVO
    		detailVo,InformAgentFilterVO filterVo)throws SystemException{
    	log.log(Log.FINE,"---sendMessageForStockHolder--");
    	LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
    	MessageVO messageVO = new MessageVO();
    	messageVO.setCompanyCode(filterVo.getCompanyCode());
    	messageVO.setStationCode(logonAttributes.getStationCode());
    	StringBuilder mes = new StringBuilder();
    	mes.append("Dear Stock Holder,").append("\n").
    	append("Available ").append(detailVo.getDocType()).append(" - ").append(detailVo.getDocSubType()).append(" stock of ").append(detailVo.getStockHolderCode()).append(" is ").append(detailVo.getPhysicalStockAvailable()).
    	append(" which is below reorder level ").append(detailVo.getReorderLevel()).append("\n").
    	append("Thank You");
    	messageVO.setRawMessage(mes.toString());
    	messageVO.setOriginalMessage(mes.toString());
    	messageVO.setMessageType("UNK");
    	messageVO.setReceiptOrSentDate(new LocalDate(logonAttributes
    			.getStationCode(),Location.STN,true));
    	messageVO.setMessageDirection("O");
    	messageVO.setStatus("S");
    	messageVO.setMessageSource(detailVo.getContactDetails());
    	messageVO.setOperationFlag("I");
    	Collection<MessageDespatchDetailsVO> detailsVOs=new
		ArrayList<MessageDespatchDetailsVO>();
    	MessageDespatchDetailsVO detailsVO=new MessageDespatchDetailsVO();
		detailsVO.setOperationFlag("I");
		detailsVO.setAddress(detailVo.getContactDetails());
		detailsVO.setMode("M");
		detailsVOs.add(detailsVO);
		messageVO.setDespatchDetails(detailsVOs);
    	MsgbrokerMessageProxy proxy = new MsgbrokerMessageProxy();
    	try {
			proxy.sendMessage(messageVO);
		} catch (ProxyException e) {
			for(ErrorVO erroVo : e.getErrors()){
				throw new SystemException(erroVo.getErrorCode());
			}
		}
    }
    /**
     * @author a-1885
     * @param detailVo
     * @param filterVo
     * @throws SystemException
     */
    private void autoRequestForStock(StockHolderStockDetailsVO
    		detailVo,InformAgentFilterVO filterVo)throws SystemException{
    	log.log(Log.FINE, "=====autoRequestForStock=====", detailVo);
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
    	StockRequestVO reqVo = new StockRequestVO();
    	reqVo.setCompanyCode(filterVo.getCompanyCode());
    	reqVo.setStockHolderCode(detailVo.getStockHolderCode());
    	reqVo.setDocumentType(detailVo.getDocType());
    	reqVo.setDocumentSubType(detailVo.getDocSubType());
    	reqVo.setRequestDate(new LocalDate
    			(logonAttributes.getStationCode(),Location.STN,true));
    	reqVo.setRequestedStock(detailVo.getReorderQuantity());
    	reqVo.setManual(false);
    	reqVo.setStatus(StockRequestVO.STATUS_NEW);
    	reqVo.setOperationFlag(StockRequestVO.OPERATION_FLAG_INSERT);
    	reqVo.setLastUpdateUser(logonAttributes.getUserId());
    	reqVo.setLastUpdateDate(new LocalDate(logonAttributes.
    			getStationCode(),Location.STN,true));
    	reqVo.setAirlineIdentifier(detailVo.getAirlineId());
    	//Modified as part of Bug ICRD-ICRD-277458 by A-8154
    	StockController stockController = (StockController)SpringAdapter.
				getInstance().getBean("stockcontroldefaultsController");
				stockController.saveStockRequestDetails(reqVo);
    }
    /**
     * @author a-1885
     * @param allocationVo
     * @param startRange
     * @param numberOfDocument
     * @return
     * @throws SystemException
     */
    private Collection<RangeVO> findRangeForTransfer(StockAllocationVO allocationVo,
    		String startRange,long numberOfDocument)throws SystemException{
    	log.log(Log.FINE,"-----findRangeForTransfer CONTROLLER------");
    	Collection<RangeVO> ranges = StockHolder.findRangeForTransfer
    	(allocationVo,startRange,numberOfDocument);
    	log.log(Log.FINE, "----Ranges Obtained--", ranges);
		return ranges;
    }

    /*
     * added by Sinoob starts
     */
    /**
     * @param stockAgentVOs
     * @throws SystemException
     * @throws CreateException
     * @throws FinderException
     * @throws RemoveException
     * @throws StockControlDefaultsBusinessException
     */
    public void saveStockAgentMappings(Collection<StockAgentVO> stockAgentVOs)
    		throws SystemException, CreateException, FinderException,
    		RemoveException, StockControlDefaultsBusinessException{
    	log.log(Log.INFO,"ENTRY");
    	if(stockAgentVOs == null){
    		log.log(Log.WARNING,"stockAgentVOs is NULL");
    		return;
    	}

		// for removing the vos with operation flag D
    	for(StockAgentVO stockAgentVO : stockAgentVOs){
			if(StockAgentVO.OPERATION_FLAG_DELETE.equals(
									stockAgentVO.getOperationFlag())){
				StockAgentMapping.find(
						stockAgentVO.getCompanyCode(),
						stockAgentVO.getAgentCode())
						.remove();
			}
		}

    	// this variable is to hold the agent codes which appear duplicated
    	// this is stored as comma separated values
    	StringBuilder duplicateAgents = null;

    	// for inserting the mappings with operation flag I
    	for(StockAgentVO stockAgentVO : stockAgentVOs){
			if(StockAgentVO.OPERATION_FLAG_INSERT.equals(stockAgentVO.getOperationFlag())){

				// if finderException is not thrown, it means duplicate entry
				// so throw business exception
				try {
					log.log(Log.FINEST,
							"---finding StkAgntMap with agentCode ",
							stockAgentVO.getAgentCode());
					StockAgentMapping.find(
							stockAgentVO.getCompanyCode(),
							stockAgentVO.getAgentCode());
					if(duplicateAgents == null){
						duplicateAgents = new StringBuilder(stockAgentVO.getAgentCode());
					}else{
						duplicateAgents = duplicateAgents.append(", ").append(stockAgentVO.getAgentCode());
					}
					log.log(Log.FINEST, "$$$$$ Duplicate agentCode ",
							stockAgentVO.getAgentCode());
				} catch (FinderException e) {
					new StockAgentMapping(stockAgentVO);
				}
			}
			else if(StockAgentVO.OPERATION_FLAG_UPDATE.equals(stockAgentVO.getOperationFlag())) {
				try{
					log.log(Log.FINE,"Inside Update ......");
					StockAgentMapping stockAgentMapping=StockAgentMapping.find(
							stockAgentVO.getCompanyCode(),
							stockAgentVO.getAgentCode());
					 stockAgentMapping.update(stockAgentVO);
				}
				catch(FinderException e) {
					log.log(Log.SEVERE,"**** The StockAgentVO Can`t Be Updated **** ");
				}
			}
		}

    	// checking for whether duplicate agent codes were found
    	if(duplicateAgents != null){
    		log
					.log(Log.SEVERE, "Duplicate Agent Codes Found ",
							duplicateAgents);
			throw new StockControlDefaultsBusinessException(
					StockControlDefaultsBusinessException.DUPLICATE_AGENT_FOUND,
					new Object[] {duplicateAgents.toString()});

    	}
    	log.log(Log.INFO,"RETURN");
    }


    /**
     * @param stockAgentFilterVO
     * @return
     * @throws SystemException
     */
    public Page<StockAgentVO> findStockAgentMappings(
    		StockAgentFilterVO stockAgentFilterVO)
		throws SystemException{
       	return StockAgentMapping.findStockAgentMappings(stockAgentFilterVO);
    }

     /** @param documentFilterVO
     * @return
     * @throws SystemException
     * @throws StockControlDefaultsBusinessException
     */
    /*
     *
     * Check the document type. For time being do validation only if
     * the document is AWB else return NULL
     * Return AWBDocumentValidationVO (inherited from DocumentValidationVO)
     *
     * if stockOwner <> NULL
     * 1. Validate if document number is present in any AWB stock.
     * Else throw StockControlDefaultsBusinessException with errorcode
     * 2. Get StockHolderCode corresponding to stockOwner from Stock-Agent
     * mapping. If mapping is not present return NULL.
     * // skip this...If stockholder has a status 'cancelled' return NULL
     * 3. Validate if stockHolder owns AWB stock.
     * Else throw StockControlDefaultsBusinessException with errorcode
     * 4. Validate if stockHolder holds the given documentNumber in his stock.
     * Else throw StockControlDefaultsBusinessException with errorcode
     * 5. Populate AWBDocumentValidationVO with stockholder details and product
     * details and return
     *
     * else if stockOwner = NULL
     * 1. If document number is not present in any AWB stock throw
     * StockControlDefaultsBusinessException with errorcode
     * 2. If document number is present, find the stockholder details
     * 3. Find the agent code from Stock-Agent mapping. If mapping is not
     * present, leave agent details blank in return VO
     * 4. If mapping is present, find the agent name from Shared-Agent master
     * More than one agent could be present against a stock holder code
     * If agent code is not present in agent master, do not populate agent
     * code in the return VO
     * 5. Find if product is configured against the documentType-subtype.
     * If present, find product code, product name, scc, priority and
     * transportation mode. There could be more than one product
     *
     */
	public DocumentValidationVO validateDocument(
			DocumentFilterVO documentFilterVO)
		throws SystemException, StockControlDefaultsBusinessException {
		log.log(Log.INFO, "DocumentFilterVO : ", documentFilterVO);

		if(DocumentValidationVO.DOC_TYP_AWB.equals(
				documentFilterVO.getDocumentType())){
			log.log(Log.INFO,"Document Type is AWB. So, calling awbValidation");
			return validateAWB(documentFilterVO);
		}else if(DocumentValidationVO.DOC_TYP_COURIER.equals(
				documentFilterVO.getDocumentType())){
			log.log(Log.INFO,"Document Type is COU. So, calling courierValidation");
			return validateCourier(documentFilterVO.getCompanyCode(),
					documentFilterVO.getDocumentNumber(),
					documentFilterVO.getAirlineIdentifier(),
					DocumentValidationVO.DOC_TYP_COURIER,
					documentFilterVO.getStockOwner());
		}else if(DocumentValidationVO.DOC_TYP_EBT.equals(
				documentFilterVO.getDocumentType())){
			log.log(Log.INFO,"Document Type is EBT. So, calling ebtValidation");
			return validateEBT(documentFilterVO.getCompanyCode(),
					documentFilterVO.getDocumentNumber(),
					documentFilterVO.getAirlineIdentifier(),
					DocumentValidationVO.DOC_TYP_EBT,
					documentFilterVO.getStockOwner());
		}
		//Added as part of ICRD-46860
		else if(DocumentValidationVO.DOC_TYP_INV.equals(documentFilterVO.getDocumentType())) {			
			log.log(Log.INFO,"Document Type is INV. So, calling InvoiceValidation");
			return validateInvoice(documentFilterVO);			
		} else{
			log.log(Log.WARNING,"UNIDENTIFIED DOC_TYPE. So, returning NULL :");
			return null;
		}
	}


	/**
	 * Validates the AWB document.
	 * ============================================
	 * if stockOwner <> NULL
     * 1. Validate if document number is present in any AWB stock.
     * Else throw StockControlDefaultsBusinessException with errorcode
     * 2. Get StockHolderCode corresponding to stockOwner from Stock-Agent
     * mapping. If mapping is not present return NULL.
     * // skip this...If stockholder has a status 'cancelled' return NULL
     * 3. Validate if stockHolder owns AWB stock.
     * Else throw StockControlDefaultsBusinessException with errorcode
     * 4. Validate if stockHolder holds the given documentNumber in his stock.
     * Else throw StockControlDefaultsBusinessException with errorcode
     * 5. Populate AWBDocumentValidationVO with stockholder details and product
     * details and return
     *
     * else if stockOwner = NULL
     * 1. If document number is not present in any AWB stock throw
     * StockControlDefaultsBusinessException with errorcode
     * 2. If document number is present, find the stockholder details
     * 3. Find the agent code from Stock-Agent mapping. If mapping is not
     * present, leave agent details blank in return VO
     * 4. If mapping is present, find the agent name from Shared-Agent master
     * More than one agent could be present against a stock holder code
     * If agent code is not present in agent master, do not populate agent
     * code in the return VO
     * 5. Find if product is configured against the documentType-subtype.
     * If present, find product code, product name, scc, priority and
     * transportation mode. There could be more than one product
     *
	 *
	 * @param companyCode
	 * @param documentNumber
	 * @param airlineId
	 * @param agentCode
	 * @param documentType
	 * @param documentSubType
	 * @param docFilterVO
	 * @return documentValidationVO
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException
	 */
	private DocumentValidationVO validateAWB(DocumentFilterVO docFilterVO)
			throws SystemException, StockControlDefaultsBusinessException{

		String companyCode  = docFilterVO.getCompanyCode();
		String documentNumber  = docFilterVO.getDocumentNumber();
		int airlineId  = docFilterVO.getAirlineIdentifier();
		String agentCode = docFilterVO.getStockOwner();
		String documentType = DocumentValidationVO.DOC_TYP_AWB;
		String documentSubType = docFilterVO.getDocumentSubType();

		AWBDocumentValidationVO awbValidationVO = new AWBDocumentValidationVO();

		/*
		 * Added this check and change, for integrating AWB Acceptance and R2 Stock
		 * If the Document is in ReservedAWB Table, then it should be removed
		 * from that, after acceptance
		 *
		 */
		if(docFilterVO.isOtherAirline()){
			log.log(Log.FINE,"====OTHER ARLINE, validating RESERV status");
			log.log(Log.FINE,"====OTHER ARLINE, validating RESERV status");

			ReservationVO reservationVO = Reservation.findReservationDetails(docFilterVO);

			LogonAttributes logon = ContextUtils.getSecurityContext().getLogonAttributesVO();

			if(reservationVO != null){
				if( ! logon.getAirportCode().equals(reservationVO.getAirportCode()) ){
					log.log(Log.SEVERE, AWB_RESERVED_FOR_AIRPORT,
							reservationVO.getAirportCode());
					log.log(Log.SEVERE, AWB_RESERVED_FOR_AIRPORT,
							reservationVO.getAirportCode());
					log.log(Log.SEVERE, AWB_RESERVED_FOR_AIRPORT,
							reservationVO.getAirportCode());
					throw new StockControlDefaultsBusinessException(
						StockControlDefaultsBusinessException.AWB_RESERVED_FOR_ANOTHER_AIRPORT,
						new Object[] { reservationVO.getAirportCode() } );

				}
				awbValidationVO.setStatus(DocumentValidationVO.STATUS_RESERVED);
				awbValidationVO.setStockHolderCode(reservationVO.getStockHolderCode());
				awbValidationVO.setStockHolderType("A");	// hard coded for Agent
				awbValidationVO.setAgentDetails(new ArrayList<AgentDetailVO>());

				AgentDetailVO agentDetailVO = new AgentDetailVO();
				agentDetailVO.setAgentCode(reservationVO.getAgentCode());
				agentDetailVO.setCustomerCode(reservationVO.getCustomerCode());

				awbValidationVO.getAgentDetails().add(agentDetailVO);

				log.log(Log.FINER, "====RETURNING awbValidationVO==",
						awbValidationVO);
				return awbValidationVO;
			}
		}



		String stockHolderCode = null;
		String stockHolderType = null;

		if(agentCode != null){
			/*
			 * checks whether at least one AWB stock contains this
			 * documentNumber inside the range, else throws exception
			 */
			if(! DocumentValidationVO.STATUS_RESERVED.equals(docFilterVO.getStatus())){
				checkAwbExistsInAnyStock(companyCode, airlineId, documentType, documentNumber,RangeVO.SUBSTRING_COUNT);
			}
			Collection<String> agentCodes = new ArrayList<String>();
			agentCodes.add(agentCode);

			/*
			 * finds the stockholder code corresponding to
			 * the agent code from the stock-agent mapping
			 */
			stockHolderCode = findStockHolderCodeForAgent(
											companyCode, agentCode);
			if(stockHolderCode == null || ("").equals(stockHolderCode.trim())){
				log.log(Log.WARNING, "returning NULL");
				//return null;
				throw new StockControlDefaultsBusinessException(
						StockControlDefaultsBusinessException.STOCKHOLDER_NOTFOUNDFOR_AGENT,
						new Object[] { agentCode });
			}else{
				Collection<AgentDetailVO> agentDetails = null;
				agentDetails = validateAgents(companyCode,agentCodes);

				awbValidationVO.setAgentDetails(agentDetails);
			}
			/*
			 * checks whether the stockholder has "AWB" document type
			 * if present, then checks whether any range has the
			 * document number included in it
			 * else throws exception
			 */
			if(! DocumentValidationVO.STATUS_RESERVED.equals(docFilterVO.getStatus())){
				checkDocumentAvailable(companyCode, airlineId, stockHolderCode, documentNumber);
			}else{
				// checks whether this stock holder code is against the reserved document
				// if invalid then throw exception as above,
				ReservationVO resVO = Reservation.findReservationDetails(docFilterVO);
				if(resVO == null || ! stockHolderCode.equals(resVO.getStockHolderCode())){
					log.log(Log.SEVERE, "====INVALD STOCK HOLDER",
							stockHolderCode);
					log.log(Log.SEVERE, "====INVALD STOCK HOLDER",
							stockHolderCode);
					throw new StockControlDefaultsBusinessException(
							StockControlDefaultsBusinessException.STOCKHOLDER_STOCK_NOTFOUND,
							new Object[] { stockHolderCode });
				}
			}

		}else{	//ie, IF agentCode == null
			/*
			 * checks whether at least one AWBstock contains this
			 * documentNumber inside the range and returns the
			 * stockHolder code , else throws exception
			 */
			stockHolderCode = checkAwbExistsInAnyStock(
										companyCode, airlineId,
										documentType, documentNumber,RangeVO.SUBSTRING_COUNT);

			awbValidationVO.setStatus(DocumentValidationVO.STATUS_INSTOCK);

			StockHolderVO stkHolderVO = StockHolder.findStockHolderDetails(
											companyCode, stockHolderCode);

			Collection<String> agentCodes = null;
			/*
			 * finds the agents associated with the stockHolder from the mapping
			 */
			if(stkHolderVO != null){
				stockHolderType = stkHolderVO.getStockHolderType();
				/**
				 * if stockholder type is HQ,region level or station level , return error
				 */

				agentCodes = findAgentsForStockHolder(
						companyCode, stkHolderVO.getStockHolderCode());
			}
			/*
			 * finds collection of agentVOs with agent code and names
			 */
			if(agentCodes != null && agentCodes.size() > 0){
				Collection<AgentDetailVO> agentDetails = null;
				agentDetails = validateAgents(companyCode,agentCodes);

				awbValidationVO.setAgentDetails(agentDetails);
			}
		}


		Collection<ProductStockVO> productStockVos = null;

		log.log(Log.FINER,"\n\n\n\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		log.log(Log.INFO, "--documentSubType : ", documentSubType);
		if(documentSubType == null){
			documentSubType = findSubTypeForDocument(
					companyCode, airlineId, documentType, documentNumber);
		}

		try {
			productStockVos = new ProductsDefaultsProxy().
								findProductsForStock(companyCode,
										documentType, documentSubType);
		} catch (ProxyException e) {
			throw new SystemException(
					SystemException.UNEXPECTED_SERVER_ERROR, e);
		}

		awbValidationVO.setStockHolderCode(stockHolderCode);
		awbValidationVO.setDocumentType(documentType);
		awbValidationVO.setDocumentNumber(documentNumber);
		awbValidationVO.setStockHolderType(stockHolderType);
		awbValidationVO.setDocumentSubType(documentSubType);

		awbValidationVO.setProductStockVOs(productStockVos);


		log.log(Log.INFO, "returning AWBValidationVO :", awbValidationVO);
		return awbValidationVO;

	}

	/**
	 * return whether an AWB is blacklisted or not
	 * @param companyCode
	 * @param airlineId
	 * @param documentType
	 * @param documentNumber
	 * @param documentSubType
	 */
	private void checkIfAWBBlacklisted(String companyCode, int airlineId, String documentType,
									   String documentNumber, String documentSubType) throws SystemException,
			StockControlDefaultsBusinessException{

		log.entering("CheckIfAWBBlacklisted","Started ");
		BlacklistStockVO blacklistStockVO = new BlacklistStockVO();
		blacklistStockVO.setCompanyCode(companyCode);
		blacklistStockVO.setAirlineIdentifier(airlineId);
		blacklistStockVO.setDocumentType(documentType);
		blacklistStockVO.setRangeFrom(documentNumber);
		blacklistStockVO.setRangeTo(documentNumber);
		blacklistStockVO.setDocumentSubType(documentSubType);
		boolean status = Range.checkIfAWBBlackListed(blacklistStockVO);
		if(status){
			log.log(Log.SEVERE, "Doc NUM blacklisted", documentNumber);
			throw new StockControlDefaultsBusinessException(
					StockControlDefaultsBusinessException.INVALID_AWB,
					new Object[] { documentNumber });
		}

	}

	private void checkIfAwbIsVoid(String companyCode, String documentType,
								  String documentNumber, String documentSubType) throws SystemException,
			StockControlDefaultsBusinessException {
		log.entering("CheckIfAWBBlacklisted","Started ");
		boolean status = Range.checkIfAWBIsVoid(companyCode,documentNumber,documentType,documentSubType);
		if(status){
			log.log(Log.SEVERE, "Doc NUM is void", documentNumber);
			throw new StockControlDefaultsBusinessException(
					StockControlDefaultsBusinessException.INVALID_AWB,
					new Object[] { documentNumber });
		}
	}


	/**
	 * Checks whether any Stockholder's Stock contains this awbNumber
	 * inside the range. If so returns the stockHolder code
	 * otherwise throws exception
	 *
	 * @param companyCode
	 * @param airlineId
	 * @param documentType
	 * @param documentNumber
	 * @param length 
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException
	 * 
	 */
	private String checkAwbExistsInAnyStock(
			String companyCode, int airlineId,
			String documentType, String documentNumber, int lengthofFormat)
		throws SystemException, StockControlDefaultsBusinessException{
		log.log(Log.FINER,"ENTRY");
		long mstDocNumber = 0;
		//modified ByA-5111 for BUG_id:82797
		if(DocumentValidationVO.DOC_TYP_AWB.equals(documentType)&&documentNumber.length()>=lengthofFormat) {
			mstDocNumber = findLong(documentNumber.substring(
					RangeVO.SUBSTRING_START,lengthofFormat));	
		}
		else if("ORDINO".equals(documentType)&&documentNumber.length()>=lengthofFormat) {
			mstDocNumber = findLong(documentNumber.substring(
					RangeVO.SUBSTRING_START,lengthofFormat));	
		}
		else {
			mstDocNumber = findLong(documentNumber.substring(
					RangeVO.SUBSTRING_START,documentNumber.length()));	
		}
		String stockHolderCode = Range.checkDocumentExistsInAnyStock(
							companyCode, airlineId, documentType, mstDocNumber);
		if(stockHolderCode == null || ("").equals(stockHolderCode.trim())){
			log.log(Log.SEVERE, "No stock contains AWB NUM ", documentNumber);
			//Edited as part of ICRD-46860
			if(DocumentValidationVO.DOC_TYP_AWB.equals(documentType)) {
			throw new StockControlDefaultsBusinessException(
				StockControlDefaultsBusinessException.AWBNUMBER_NOTFOUND_INANYSTOCK,
				new Object[] { documentNumber });
			} else {
				throw new StockControlDefaultsBusinessException(
						StockControlDefaultsBusinessException.STOCK_NOT_FOUND,
						new Object[] { documentNumber });	
			}
			
		}
		log.log(Log.FINER,"RETURN");
		return stockHolderCode;
	}

	/**
	 * Returns the stock holder code associated with an agent code
	 * from the stock-agent mapping table
	 * If there is no mapping found against the agent, NULL is returned
	 *
	 * @param companyCode
	 * @param agentCode
	 * @return
	 * @throws SystemException
	 */
	private String findStockHolderCodeForAgent(
			String companyCode, String agentCode) throws SystemException{
		log.log(Log.FINER,"ENTRY");
		StockAgentMapping stkAgentMapping = null;
		try {
			stkAgentMapping = StockAgentMapping.find(companyCode, agentCode);
		} catch (FinderException finderException) {
			log.log(Log.INFO, "Mapping NOT FOUND for agent ", agentCode);
			return null;
		}
		log.log(Log.INFO, " stockHolderCode is ", stkAgentMapping.getStockHolderCode());
		log.log(Log.FINER,"RETURN");
		return stkAgentMapping.getStockHolderCode();
	}


	/**
	 * Validates whether the 'AWB' document type is present in any stock
	 * for the stockHolder. If AWB document is not present,
	 * exception is thrown as "awbstocknotfoundforstockholder"
	 *
	 * Otherwise, checks whether the 'document number' is contained in
	 * any of the range for any 'AWB' stock
	 * If it is not contained, then exception is thrown as
	 * "awbnotexistinginanyrangeforstockholder"
	 *
	 * @param companyCode
	 * @param airlineId
	 * @param stockHolderCode
	 * @param documentNumber
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException
	 */
	private void checkDocumentAvailable(
			String companyCode, int airlineId, String stockHolderCode, String documentNumber)
		throws SystemException, StockControlDefaultsBusinessException{
		log.log(Log.FINER,"ENTRY");
		StockHolder stkHolder = null;
		try {
			stkHolder = StockHolder.find(companyCode, stockHolderCode);
		} catch (InvalidStockHolderException e) {
			log.log(Log.SEVERE, "Invalid Stock Holder ", stockHolderCode);
			throw new StockControlDefaultsBusinessException(
					StockControlDefaultsBusinessException.STOCKHOLDER_STOCK_NOTFOUND,
					new Object[] { stockHolderCode });

		}
		/*
		 * If at least a single stock is not there, exception is thrown there itself
		 */
		if(stkHolder.getStock() == null){
			log.log(Log.SEVERE, "No stock found for Stock Holder ",
					stockHolderCode);
			throw new StockControlDefaultsBusinessException(
					StockControlDefaultsBusinessException.STOCKHOLDER_STOCK_NOTFOUND,
					new Object[] { stockHolderCode });
		}

		/*
		 * checks whether the stockHolder has at least one "AWB" document type
		 */
		stkHolder.checkForAWBStock(airlineId);

		try {
			/*
			 * if he has at least one AWB document type, then checks whether
			 * the document number which is passed, is contained in any
			 * of the ranges ( the document number is passed as long type)
			 *
			 * here the error is caught and thrown again for embedding the
			 * stock holder code and document number with the throwable
			 */
			stkHolder.checkAWBRangeExists(airlineId, findLong(
					documentNumber.substring(
							RangeVO.SUBSTRING_START,RangeVO.SUBSTRING_COUNT)));
		} catch (StockControlDefaultsBusinessException e) {

			String errorMsg = new StringBuffer(" AWB ")
								.append(documentNumber)
								.append(" NOT existing for stockholder ")
								.append(stockHolderCode)
								.toString();
			log.log(Log.SEVERE, errorMsg );
			throw new StockControlDefaultsBusinessException(
					StockControlDefaultsBusinessException.STOCKHOLDER_AWB_NOT_EXISTING,
					new Object[] { stockHolderCode, documentNumber } );
		}
		log.log(Log.FINER,"RETURN");
	}


	/**
	 * Returns the collection of agents, associated with the given stockHolder
	 *
	 * @param companyCode
	 * @param stockHolderCode
	 * @return
	 * @throws SystemException
	 */
	private Collection<String> findAgentsForStockHolder(
			String companyCode, String stockHolderCode)
				throws SystemException{
		log.log(Log.FINER,"");
		return StockAgentMapping.findAgentsForStockHolder(companyCode, stockHolderCode);
	}

	/**
	 * Validates the agent codes and returns the collection of vo
	 * which contains the agent code and agent name
	 *
	 * @param companyCode
	 * @param agentCodes
	 * @return
	 * @throws SystemException
	 */
	private Collection<AgentDetailVO> validateAgents(
			String companyCode, Collection<String> agentCodes)
		throws SystemException{
		log.log(Log.FINER,"ENTRY");
		Collection<AgentDetailVO> agentDetailVOs = null;
		Collection<AgentVO> sharedAgentVOs = null;
		Map<String,AgentVO> sharedAgentMap = null;

		try {
			sharedAgentMap = new SharedAgentProxy().validateAgents(companyCode,agentCodes);
		} catch (ProxyException e) {
			for(ErrorVO errorVo : e.getErrors()){
				throw new SystemException(errorVo.getErrorCode(),errorVo.getErrorData());
			}
		}

		if(sharedAgentMap != null){
			sharedAgentVOs = sharedAgentMap.values();
			if(sharedAgentVOs != null){
				AgentDetailVO agentDetailVO = null;
				for(AgentVO sharedAgentVO : sharedAgentVOs){
					if(agentCodes.contains(sharedAgentVO.getAgentCode())){
						agentDetailVO = new AgentDetailVO();
						agentDetailVO.setAgentCode(sharedAgentVO.getAgentCode());
						agentDetailVO.setAgentName(sharedAgentVO.getAgentName());
						agentDetailVO.setCustomerCode(sharedAgentVO.getCustomerCode());
						agentDetailVO.setCustomerName(sharedAgentVO.getCustomerName());
						agentDetailVO.setAgentIataCode(sharedAgentVO.getIataAgentCode());
						agentDetailVO.setAgentCassCode(sharedAgentVO.getCassAgentCode());
						agentDetailVO.setAgentPlace(sharedAgentVO.getAgentCity());
						agentDetailVO.setAgentStatus(sharedAgentVO.getAgentStatus());
						agentDetailVO.setValidFrom(sharedAgentVO.getValidFrom());
						agentDetailVO.setValidTo(sharedAgentVO.getValidTo());
						if(agentDetailVOs == null){
							agentDetailVOs = new ArrayList<AgentDetailVO>();
						}
						agentDetailVOs.add(agentDetailVO);
					}
				}
			}else{
				log.log(Log.WARNING,"NO values found inside shared Map");
			}
		}else{
			log.log(Log.WARNING,"Proxy returned NULL");
		}
		log.log(Log.INFO, "$$$$$ agentDetailVOs : ", agentDetailVOs);
		log.log(Log.FINER,"RETURN");
		return agentDetailVOs;
	}


	/**
	 * @param companyCode
	 * @param airlineId
	 * @param documentType
	 * @param documentNumber
	 * @return
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException
	 */
	private String findSubTypeForDocument(String companyCode, int airlineId,
			String documentType, String documentNumber)
		throws SystemException, StockControlDefaultsBusinessException {
		log.log(Log.FINE,"ENTRY");
		String docSubType = null;
		//Edited as part of ICRD-46860
		long mstDocNumber = 0;
		if(DocumentValidationVO.DOC_TYP_AWB.equals(documentType)) {
			mstDocNumber = findLong(documentNumber.substring(RangeVO.SUBSTRING_START,RangeVO.SUBSTRING_COUNT));
		} else {
			mstDocNumber = findLong(documentNumber.substring(RangeVO.SUBSTRING_START,documentNumber.length()));	
		}		
		docSubType = Range.findSubTypeForDocument(companyCode, airlineId, documentType, mstDocNumber);
		log.log(Log.FINE,"RETURN");
		return docSubType;
	}

	/**
	 * This method is used to remove the document number from stock. Removal is
	 * done when the document number is utilized. Note that this method will not
	 * be called during stock transfer or blacklisting
	 *
	 * @param filterVO
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException
	 */
	/*
	 * If stockOwner = NULL
	 * 1.Find the stock containing the given document Number. If
	 *   documentNumber is not present in any stock throw
	 *   StockControlDefaultsBusinessException with errorcode
	 * 2.Remove the document Number from stock using deplete method
	 *   in StockHolder
	 *
	 * if stockOwner <> NULL
	 * 1.Find the stock containing the given document Number. If
	 *   documentNumber is not present in any stock throw
	 *   StockControlDefaultsBusinessException with errorcode
	 * 2.If documentType <> AWB stockHolder = stockOwner
	 *   Else if documentType = AWB, find the stockHolderCode corresponding
	 *   to stockOwner
	 * 3.If stockholder in mapping does not exist throw
	 *   StockControlDefaultsBusinessException with errorcode
	 * 3.Check if documentNumber belongs to the stockHolder identified
	 *   Else throw StockControlDefaultsBusinessException with errorcode
	 * 4.Remove the document Number from stock using deplete method
	 *   in StockHolder
	 *
	 */
	public void deleteDocumentFromStock(DocumentFilterVO filterVO)
		throws SystemException, StockControlDefaultsBusinessException {
		log.log(Log.INFO, "DocumentFilterVO ", filterVO);
		String stockHolderCode = null;
		/*
		 * for R2 business....
		 */
		if(DocumentValidationVO.STATUS_RESERVED.equals(filterVO.getStatus())){
			log.log(Log.FINE,"====$$$ ReservedDocument!!!...Going to delete");
			Reservation reservation = null;
			ReservationVO reservationVO = Reservation.findReservationDetails(filterVO);
			if(reservationVO != null){
				reservation = Reservation.find(filterVO.getCompanyCode(),
											filterVO.getAirlineIdentifier(),
											reservationVO.getAirportCode(),
											filterVO.getDocumentNumber());
				try {
					reservation.setLastUpdateTime(reservation.getLastUpdateTime());
					reservation.remove();
				} catch (RemoveException e) {
					log.log(Log.SEVERE,"===REMOV EXCP");
					throw new SystemException(e.getErrorCode(), e);
				}
			}
			log.log(Log.FINE,"returning.......");
			return ;

		}

		//replaced the block to here for ICRD-129108 starts


			log.log(Log.INFO, "--documentSubType : ", filterVO.getDocumentSubType());
			if(filterVO.getDocumentSubType() == null){
				log.log(Log.INFO,"== === Doc Sub Type NULL... so finding...");
				log.log(Log.FINER,"\n\n ================================");
				String docSubType = null;
				docSubType = findSubTypeForDocument(filterVO.getCompanyCode(),
											filterVO.getAirlineIdentifier(),
											filterVO.getDocumentType(),
											filterVO.getDocumentNumber());
				log.log(Log.INFO, "== === Doc Sub Type after find is :",
						docSubType);
				filterVO.setDocumentSubType(docSubType);
			}
		//replaced the block to here for ICRD-129108 ends
		if(filterVO.getStockOwner() == null){
			/*
			 * checks whether at least one AWBstock contains this
			 * documentNumber inside the range and returns the
			 * stockHolder code , else throws exception
			 */
			stockHolderCode = checkAwbExistsInAnyStock(filterVO.getCompanyCode(),
						filterVO.getAirlineIdentifier(),
						filterVO.getDocumentType(),
						filterVO.getDocumentNumber(),RangeVO.SUBSTRING_COUNT);


			/*
			 * depletes the range from the stock ( the range is the docNumber)
			 */
			if(DocumentValidationVO.DOC_TYP_AWB.equals(filterVO.getDocumentType())) {
				depleteDocumentFromStock(filterVO, stockHolderCode,7);
			} else {
				depleteDocumentFromStock(filterVO, stockHolderCode,filterVO.getDocumentNumber().length());
			}
			

		}else{
			String documentOwner = null;
			/*
			 * checks whether at least one AWBstock contains this
			 * documentNumber inside the range and returns the
			 * stockHolder code , else throws exception
			 */
			DocumentTypeProxy documentTypeProxy =  new DocumentTypeProxy();
	    	com.ibsplc.icargo.business.shared.document.vo.DocumentFilterVO documentFilterVO = new com.ibsplc.icargo.business.shared.document.vo.DocumentFilterVO();
	    	documentFilterVO.setCompanyCode(filterVO.getCompanyCode());
	    	documentFilterVO.setDocumentCode(filterVO.getDocumentType());
	    	documentFilterVO.setDocumentSubType(filterVO.getDocumentSubType());
			Collection<DocumentVO> doctype = null;
	    	DocumentVO documentVO = new DocumentVO();
	    	try {
				doctype = documentTypeProxy.findDocumentDetails(documentFilterVO);
			} catch (ProxyException e) {
				throw new SystemException(
						SystemException.UNEXPECTED_SERVER_ERROR, e);
			}
			if(doctype!=null&& doctype.size()!=0){
			 documentVO = doctype.iterator().next();
			}
			/*
			 * Commented as part of ICRD-46860
			 */
			int length=7;
			/* if((DocumentVO.FLAG_NO).equals(documentVO.getCheckDigitFlag())){
				 String numFormat = documentVO.getFormatString();
    			 int lengthOfNumFormat=numFormat.trim().length();
    			if(numFormat!=null && lengthOfNumFormat>1){
    			String numOfDigits= numFormat.substring(1,lengthOfNumFormat);
    			 length=Integer.parseInt(numOfDigits);
			 }
			 }*/
			documentOwner = checkAwbExistsInAnyStock(filterVO.getCompanyCode(),
											filterVO.getAirlineIdentifier(),
											filterVO.getDocumentType(),
											filterVO.getDocumentNumber(),length);
			if(DocumentValidationVO.DOC_TYP_AWB.equals(filterVO.getDocumentType())){
				/*
				 * finds the stockholder code corresponding to
				 * the agent code from the stock-agent mapping
				 */
				stockHolderCode = findStockHolderCodeForAgent(
						filterVO.getCompanyCode(), filterVO.getStockOwner());

				if(stockHolderCode == null || ("").equals(stockHolderCode.trim())){
					log.log(Log.SEVERE, "Mapping for stockOwner NOT found ",
							filterVO.getStockOwner());
					throw new StockControlDefaultsBusinessException(
							StockControlDefaultsBusinessException.AGENT_MAPPING_NOTFOUND,
							new Object[] { filterVO.getStockOwner() } );
				}
			}else{
				log.log(Log.INFO, " document type NOT AWB, but ", filterVO.getDocumentType());
				stockHolderCode = filterVO.getStockOwner();
			}

			log.log(Log.FINE, "stockHolderCode : ", stockHolderCode);
			log.log(Log.FINE, "documentOwner : ", documentOwner);
			if(documentOwner.equals(stockHolderCode)){

				if(DocumentValidationVO.DOC_TYP_AWB.equals(filterVO.getDocumentType())) {
					depleteDocumentFromStock(filterVO, stockHolderCode,length);
				} else {
					depleteDocumentFromStock(filterVO, stockHolderCode,filterVO.getDocumentNumber().length());
				}

			}else{
				String errorMsg = new StringBuffer("DocumentNumber ")
										.append(filterVO.getDocumentNumber())
										.append(" does NOT belong to ")
										.append("the stockHolder ")
										.append(stockHolderCode)
										.toString();
				log.log(Log.SEVERE, "Document Owner and StockHolder code NOT matching");
				log.log(Log.SEVERE, errorMsg);
				throw new StockControlDefaultsBusinessException(
						StockControlDefaultsBusinessException.DOCUMENT_NOTOF_STOCKHOLDER,
						new Object[] { filterVO.getDocumentNumber(), stockHolderCode } );
			}
		}



		log.log(Log.INFO,"RETURN");
	}



	/**
	 * Deletes the stock-range which is the document number itself,
	 * from the StockHolder's stock
	 *
	 * @param filterVO
	 * @param stockHolderCode
	 * @param length 
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException	 * 
	 * 
	 */
	
	private void depleteDocumentFromStock(
			DocumentFilterVO filterVO, String stockHolderCode,int lengthofFormat)
		throws SystemException, StockControlDefaultsBusinessException{
		log.log(Log.INFO,"ENTRY");
		log.log(Log.FINE, "--depltedocumentfromstock-filtervo-", filterVO);
		log.log(Log.FINE, "--depltedocumentfromstock-stockcod-", stockHolderCode);

		Collection<RangeVO> ranges = new ArrayList<RangeVO>();

		StockAllocationVO stockAllocationVO = new StockAllocationVO();
		stockAllocationVO.setCompanyCode(filterVO.getCompanyCode());
		stockAllocationVO.setAirlineIdentifier(filterVO.getAirlineIdentifier());
		//Added by A-5153 for CRQ_ICRD-38007
		stockAllocationVO.setDocumentNumber(filterVO.getDocumentNumber());
		stockAllocationVO.setStockControlFor(stockHolderCode);
		stockAllocationVO.setDocumentType(filterVO.getDocumentType());
		stockAllocationVO.setDocumentSubType(filterVO.getDocumentSubType());
		stockAllocationVO.setTransferMode(StockAllocationVO
				.MODE_NORMAL);
		RangeVO rangeVo = new RangeVO();
		//		Added by a-2434 for temporal solution for optimistic control
		StockHolderVO stockHolderVo = findStockHolderDetails(filterVO.getCompanyCode(),stockHolderCode);
		rangeVo.setLastUpdateTime(stockHolderVo.getLastUpdateTime());
		//    		Added by a-2434 for temporal solution for optimistic control ends
		if(DocumentValidationVO.DOC_TYP_AWB.equals(filterVO.getDocumentType())){
			rangeVo.setStartRange(filterVO.getDocumentNumber().substring(0,7));
			rangeVo.setEndRange(filterVO.getDocumentNumber().substring(0,7));
		} else {
			rangeVo.setStartRange(filterVO.getDocumentNumber());
			rangeVo.setEndRange(filterVO.getDocumentNumber());
		}
		//Added for icrd-90972
		//fetching the MNLFLG for solving invoice depletion for manual case
		RangeVO tempRangeVo = StockHolder.findRangeDelete(filterVO.getCompanyCode(), filterVO.getDocumentType(), filterVO.getDocumentSubType(), rangeVo.getStartRange());
		stockAllocationVO.setManual(tempRangeVo.isManual());
		
		ranges.add(rangeVo);
		stockAllocationVO.setRanges(ranges);

		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();

		stockAllocationVO.setLastUpdateUser(logonAttributes.getUserId());
		stockAllocationVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		
		String status = "U";
		try {
			saveStockUtilisation(stockAllocationVO,status);
		} catch (SystemException e) {
			log.log(Log.SEVERE, "----->>>SystemException");
		}

		log.log(Log.INFO,"RETURN");
	}

	/**
		 * @author a-1885
		 * @param companyCode
		 * @param docType
		 * @param doSubType
		 * @param ranges
		 * @return
		 * @throws SystemException
		 */

		private Collection<RangeVO> mergeRanges(
				String companyCode,String docType,String docSubType,
				Collection<RangeVO> ranges)throws SystemException{
			Collection<RangeVO> outputRanges = null;
			Collection<SharedRangeVO> sharedRange=null;
			Collection<SharedRangeVO> sharedRangeResult= null;
			SharedRangeVO sharedRangeVO = null;
			DocumentVO documentVo=new DocumentVO();
			if(ranges!=null && ranges.size()>0){
				for(RangeVO rangeVo : ranges){
	    			sharedRangeVO = new SharedRangeVO();
	    			sharedRangeVO.setFromrange(rangeVo.getStartRange());
	    			sharedRangeVO.setRangeDate(rangeVo.getStockAcceptanceDate());
	    			sharedRangeVO.setToRange(rangeVo.getEndRange());
	    			if(sharedRange==null){
	    				sharedRange = new ArrayList<SharedRangeVO>();
	    			}
	    			sharedRange.add(sharedRangeVO);
	    		}
				try{
	    			DocumentTypeProxy documentTypeProxy= new DocumentTypeProxy();
	    			documentVo.setCompanyCode(companyCode);
	    			documentVo.setDocumentType(docType);
	    			documentVo.setDocumentSubType(docSubType);
	    			documentVo.setRange(sharedRange);
	    			sharedRangeResult=documentTypeProxy.mergeRanges(documentVo);
	    			log.log(Log.FINE, "--Merge-->>>", sharedRangeResult);
	       		}catch(ProxyException proxyException){
	    			for(ErrorVO errorVO : proxyException.getErrors()){
	    				throw new SystemException(errorVO.getErrorCode());
	    			}
	    		}
	    		if(sharedRangeResult!=null && sharedRangeResult.size()>0){
		    		for(SharedRangeVO sharedVo : sharedRangeResult){
		    			log.log(Log.FINE, "---THE RANGE VO---", sharedVo);
						RangeVO rangeVo = new RangeVO();
		    			rangeVo.setStartRange(sharedVo.getFromrange());
		    			rangeVo.setStockAcceptanceDate(sharedVo.getRangeDate());
		    			rangeVo.setEndRange(sharedVo.getToRange());
		    			if(outputRanges==null){
		    				outputRanges = new ArrayList<RangeVO>();
		    			}
		    			outputRanges.add(rangeVo);
		    		}
	    		}
			}
			log.log(Log.FINE, "::::OUTPUT RANGES::::-->", outputRanges);
			return outputRanges;
	}
		/**@author a-4421
		 * @param string
		 * @throws SystemException
		 * @return null
	 	 */
		public void updateStockUtilization(String source)throws SystemException{
			log.entering("stockController ", " updateStockUtilization ");
			StockHolderStockDetail.updateStockUtilization(source);
		}
/**
	 * @param stockDepleteFilterVO
	 * @throws SystemException
	 * @throws SystemException
	 * @return null
 	 */
	/*public void autoDepleteStock(StockDepleteFilterVO stockDepleteFilterVO)
	throws SystemException{

		log.entering("stockController ", "autoDepleteStock ");
		GMTDate actualTime = new GMTDate(true);       
		log.log(Log.FINE, ">>-->>:actualTime-->>:-->>", actualTime);
		stockDepleteFilterVO.setActualDate(actualTime);      
		//Call StockHolder.findStockRangeUtilisation
		Collection<RangeVO> newRangeVOs = new ArrayList<RangeVO>();
		Collection<StockAllocationVO> stockAllocationVOs = StockHolder
				.findStockRangeUtilisation(stockDepleteFilterVO);      
		SharedDefaultsProxy sharedDefaultsProxy = new SharedDefaultsProxy();
		Collection<String> syspar = new ArrayList<String>();  
				syspar.add("stockcontrol.defaults.enablestockhistory"); 
		
		String traceNeeded = null;   
		try{
			Map<String,String> sysmap= sharedDefaultsProxy.findSystemParameterByCodes(syspar);
			
			traceNeeded = sysmap.get("stockcontrol.defaults.enablestockhistory");
		}
		catch(ProxyException proxyException){ 
			for(ErrorVO ex : proxyException.getErrors()){
				throw new SystemException(ex.getErrorCode());
			}
		} 

		
	String cn = StockDepleteFilterVO.class.getName();        
		String rn = cn.replace('.', '/') + ".class";    
		String path = getClass().getClassLoader().getResource(rn).getPath();
		int ix = path.indexOf("!");      
		if(ix >= 0) {
		log.log(Log.FINE,"---"+ path.substring(0, ix));
		} else {
		 log.log(Log.FINE,"---"+ path);  
		}          
		
		//Merge the result with mergeRanges method
		if(stockAllocationVOs!=null && stockAllocationVOs.size()>0){
			for(StockAllocationVO stockAllocationVO :stockAllocationVOs){
				if(traceNeeded!=null && YES.equalsIgnoreCase(traceNeeded)){
				 createUtilisationHistory(stockAllocationVO,StockAllocationVO.MODE_USED);
				 createHistory(stockAllocationVO,StockAllocationVO.MODE_USED); 
				}	
				newRangeVOs = mergeRanges(stockAllocationVO.getCompanyCode(),stockAllocationVO.getDocumentType(),
						stockAllocationVO.getDocumentSubType(),stockAllocationVO.getRanges());
				stockAllocationVO.setRanges(newRangeVOs);
			}


			//find each stockholders and call deplete method
			for(StockAllocationVO stockAllocationVO :stockAllocationVOs){
				try{
					stockAllocationVO
							.setTransferMode(StockAllocationVO.MODE_TRANSFER);
					StockHolder stockHolder = StockHolder.find(
							stockAllocationVO.getCompanyCode(),
							stockAllocationVO.getStockHolderCode());
					if(stockHolder!=null){
						stockHolder.deplete(stockAllocationVO, true, true);
					}
				}catch(BlacklistedRangeExistsException blacklistedRangeExistsException){
					for(ErrorVO errorVo : blacklistedRangeExistsException.getErrors()){
						throw new SystemException(errorVo.getErrorCode());
					}
				}catch(StockNotFoundException stockNotFoundException){
					for(ErrorVO errorVo : stockNotFoundException.getErrors()){
						throw new SystemException(errorVo.getErrorCode());
					}
				}
				catch(InvalidStockHolderException invalidStockHolderException){
					for(ErrorVO errorVo : invalidStockHolderException.getErrors()){
						throw new SystemException(errorVo.getErrorCode());
					}
				}
			}
		}
		for(StockAllocationVO stockAllocationVO :stockAllocationVOs){

			RangeVO rangeVO = new RangeVO();
			String companyCode=stockAllocationVO.getCompanyCode();
			String stockHolderCode=stockAllocationVO.getStockHolderCode();
			String docType=stockAllocationVO.getDocumentType();
			String docSubType=stockAllocationVO.getDocumentSubType();
			int airlineIdentifier=stockAllocationVO.getAirlineIdentifier();

			StockRangeUtilisation stockRangeUtilisation = new StockRangeUtilisation();
			stockRangeUtilisation.find(companyCode,stockHolderCode,docType,docSubType,
					airlineIdentifier,rangeVO);
		}
		if(!(StockAllocationVO.MODE_RETURN).equalsIgnoreCase(stockDepleteFilterVO.getReopenFlag())){
			stockDepleteFilterVO.setReopenFlag(StockAllocationVO.MODE_NORMAL); 
		}
		// Modified under BUG_ICRD-23686_AiynaSuresh_28Mar13
		StockHolder.deleteStockRangeUtilisation  
		(stockDepleteFilterVO.getCompanyCode(),stockDepleteFilterVO.getAirlineId(),actualTime,stockDepleteFilterVO.getLogClearingThreshold());
		log.exiting("stockController ", "autoDepleteStock ");   

	}*/

	
	/**
	 * @author A-5153
	 * @param stockDepleteFilterVO
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException 
	 */
	@Advice(name = "stockcontrol.defaults.findBlacklistedStockFromUTL" , phase=Phase.POST_INVOKE)
	public void autoDepleteStock(StockDepleteFilterVO stockDepleteFilterVO)
			throws SystemException, StockControlDefaultsBusinessException {

		log.entering("stockController ", "autoDepleteStock ");
		GMTDate actualTime = new GMTDate(true);
		log.log(Log.FINE, ">>-->>:actualTime-->>:-->>", actualTime);
		stockDepleteFilterVO.setActualDate(actualTime);
		Collection<StockAllocationVO> stockAllocationVOs = StockHolder
				.findStockRangeUtilisation(stockDepleteFilterVO);

		if (stockAllocationVOs != null && stockAllocationVOs.size() > 0) {
			StockHolder.autoDepleteStock();
		}
		
		/*Collection<String> syspar = new ArrayList<String>();  
		syspar.add("stockcontrol.defaults.enablestockhistory"); 
		SharedDefaultsProxy sharedDefaultsProxy = new SharedDefaultsProxy();
		String traceNeeded = null;   
		try{
			Map<String,String> sysmap= sharedDefaultsProxy.findSystemParameterByCodes(syspar);
			
			traceNeeded = sysmap.get("stockcontrol.defaults.enablestockhistory");
		}
		catch(ProxyException proxyException){ 
			for(ErrorVO ex : proxyException.getErrors()){
				throw new SystemException(ex.getErrorCode());
			}
		} 
		if(stockAllocationVOs!=null && stockAllocationVOs.size()>0){
			for(StockAllocationVO stockAllocationVO :stockAllocationVOs){
				if(traceNeeded!=null && YES.equalsIgnoreCase(traceNeeded)){
				 createUtilisationHistory(stockAllocationVO,StockAllocationVO.MODE_USED);
				 createHistory(stockAllocationVO,StockAllocationVO.MODE_USED); 
				}
			}
		}*/
		/*StockHolder.deleteStockRangeUtilisation  
		(stockDepleteFilterVO.getCompanyCode(),stockDepleteFilterVO.getAirlineId(),actualTime,stockDepleteFilterVO.getLogClearingThreshold());*/
		log.exiting("stockController ", "autoDepleteStock ");

	}
 
	/**
	 * This method is used to find the next document number from a particular
	 * stock
	 *
	 * @param filterVO
	 * @return DocumentValidationVO
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException
	 */
	/*
	 * If stockOwner is not present in flterVO return NULL.
	 * Check the document type. if documentType <> AWB
	 * 1.Find StockHolder(stockowner) and use method findNextDocumentNumber
	 * (In the current implementation of this method, RangeExistsException
	 * is thrown if stock is not found. Replace with StockNotFoundException)
	 * 2.Populate documentNumber and stockholder details in
	 * DocumentValidationVO and return

	 * if documentType = AWB
	 * 1.Find stockHolder corresponding to stockOwner from Stock-Agent
	 * If mapping does not exist return NULL.
	 * 2.If mapping exist but the mapped stockHolder does not exist or is
	 * cancelled return NULL
	 * 3.If the stockHolder does not hold any AWB stock throw
	 * StockControlDefaultsBusinessException with errorcode
	 * 4.Populate documentNumber and stockholder details in
	 * AWBDocumentValidationVO and return
	 *
	 */
	public DocumentValidationVO findNextDocumentNumber(
			DocumentFilterVO filterVO)
		throws SystemException, StockControlDefaultsBusinessException {
		log.log(Log.FINER,"ENTRY");
		log.log(Log.FINER, "DocumentFilterVO", filterVO);
		DocumentValidationVO validationVO = null;
		if(filterVO.getStockOwner() == null){
			log.log(Log.INFO,"stockOwnerCode NULL...... returning NULL" );
			return null;
		}

		String stockHolderCode = null;
		StockHolder stockHolder = null;
		DocumentTypeProxy documentTypeProxy =  new DocumentTypeProxy();
    	com.ibsplc.icargo.business.shared.document.vo.DocumentFilterVO documentFilterVO = new com.ibsplc.icargo.business.shared.document.vo.DocumentFilterVO();
    	documentFilterVO.setCompanyCode(filterVO.getCompanyCode());
    	documentFilterVO.setDocumentCode(filterVO.getDocumentType());
    	documentFilterVO.setDocumentSubType(filterVO.getDocumentSubType());
    	
    	Collection<DocumentVO> doctype = null;
    	DocumentVO documentVO = new DocumentVO();
    	//Added as part of ICRD-46860
    	documentVO.setDocumentType(filterVO.getDocumentType());
    	//Added for ICRD-163211
    	documentVO.setDocumentSubType(filterVO.getDocumentSubType());
    	
    	try {
			doctype = documentTypeProxy.findDocumentDetails(documentFilterVO);
		} catch (ProxyException e) {
			throw new SystemException(
					SystemException.UNEXPECTED_SERVER_ERROR, e);
		}
		if(doctype!=null&& doctype.size()!=0){
		 documentVO = doctype.iterator().next();
		}
		if(DocumentValidationVO.DOC_TYP_AWB.equals(filterVO.getDocumentType())){
			/*
			 * finds the stockholder code corresponding to
			 * the agent code from the stock-agent mapping
			 */
			stockHolderCode = findStockHolderCodeForAgent(
					filterVO.getCompanyCode(), filterVO.getStockOwner());

			if(stockHolderCode == null || ("").equals(stockHolderCode.trim())){
				log.log(Log.WARNING, "NO-STOCKHOLDER-FOR-AGENT---", filterVO.getStockOwner());
				throw new StockControlDefaultsBusinessException(
						StockControlDefaultsBusinessException.STOCKHOLDER_NOTFOUNDFOR_AGENT,
						new Object[]{filterVO.getStockOwner()});
			}

			try {
				stockHolder = StockHolder.find(filterVO.getCompanyCode(), stockHolderCode);
			} catch (InvalidStockHolderException e) {
				log.log(Log.WARNING, "StockHolder NOT found ", stockHolderCode);
				log.log(Log.WARNING,"EXCEPTION----INVALID STOCKHOLDER-");
				throw new StockControlDefaultsBusinessException(
						StockControlDefaultsBusinessException.INVALID_STOCK_HOLDER,
						new Object[]{stockHolderCode});
			}

			/*
			 * checks whether the stockHolder has at least one "AWB" document type
			 */
			stockHolder.checkForAWBStock(filterVO.getAirlineIdentifier());
			documentVO.setSkipStockLocking(filterVO.isSkipStockLocking());
			String nextDocument = null;
			try {
				nextDocument = stockHolder.retrieveNextDocumentNumber(
											filterVO,documentVO);//Changed by A-6858 for ICRD-234889

			} catch (RangeExistsException e) {
				log.log(Log.SEVERE, "StockHolder stock NOT found ",
						stockHolderCode);
				throw new StockControlDefaultsBusinessException(
						StockControlDefaultsBusinessException.STOCKHOLDER_STOCK_NOTFOUND,
						new Object[] { stockHolderCode } );
			}
			String	documentSubType = null;
			
			if(nextDocument != null){
				
				documentSubType = findSubTypeForDocument(
						filterVO.getCompanyCode(),filterVO.getAirlineIdentifier(), filterVO.getDocumentType(),nextDocument);
				
				/**
				 * Added by A-3155 for preventing duplicate AWB when more one user captures awb
				 */
				/*new StockRangeUtilisationLog(filterVO.getCompanyCode(),
						filterVO.getAirlineIdentifier(),nextDocument.substring(0, 7));*/
				//End
			}
			validationVO = new AWBDocumentValidationVO();
			validationVO.setDocumentNumber(nextDocument);
			validationVO.setCompanyCode(filterVO.getCompanyCode());
			validationVO.setDocumentType(filterVO.getDocumentType());
			validationVO.setDocumentSubType(documentSubType);
			validationVO.setStockHolderCode(stockHolderCode);

		}else{
			stockHolderCode = filterVO.getStockOwner();
			try {
				stockHolder = StockHolder.find(filterVO.getCompanyCode(), stockHolderCode);
			} catch (InvalidStockHolderException e) {
				log.log(Log.SEVERE, "StockHolder NOT found ", stockHolderCode);
				throw new StockControlDefaultsBusinessException(
						StockControlDefaultsBusinessException.STOCKHOLDER_STOCK_NOTFOUND,
						new Object[] { stockHolderCode } );
			}

			
			String nextDocument = null;
			try {
				nextDocument = stockHolder.retrieveNextDocumentNumber(filterVO,
											documentVO);//Changed by A-6858 for ICRD-234889
			} catch (RangeExistsException e) {
				log.log(Log.SEVERE, "StockHolder stock NOT found ",
						stockHolderCode);
				throw new StockControlDefaultsBusinessException(
						StockControlDefaultsBusinessException.STOCKHOLDER_STOCK_NOTFOUND,
						new Object[] { stockHolderCode } );
			}
			String	documentSubType = null;
			if(nextDocument != null){
				//Modified by A-5807 for BUG ICRD-97700
				if(documentVO.getDocumentSubType()==null || documentVO.getDocumentSubType().trim().length()==0){
				documentSubType = findSubTypeForDocument(
						filterVO.getCompanyCode(),filterVO.getAirlineIdentifier(), filterVO.getDocumentType(),nextDocument);
				}
				else{
					documentSubType = documentVO.getDocumentSubType(); 	
				}
				/**
				 * Added by A-3155 for preventing duplicate AWB when more one user captures awb
				 */
				/*
				 * Commented as part of ICRD-46860
				 */
				/*int length=7;
				if((DocumentVO.FLAG_NO).equals(documentVO.getCheckDigitFlag())){
					 String numFormat = documentVO.getFormatString();
	    			 int lengthOfNumFormat=numFormat.trim().length();
	    			if(numFormat!=null && lengthOfNumFormat>1){
	    			String numOfDigits= numFormat.substring(1,lengthOfNumFormat);
	    			 length=Integer.parseInt(numOfDigits);
				 }
				 }*/
				/*new StockRangeUtilisationLog(filterVO.getCompanyCode(),
						filterVO.getAirlineIdentifier(),nextDocument);*/
				//Ends
			}
			validationVO = new DocumentValidationVO();
			validationVO.setDocumentNumber(nextDocument);
			validationVO.setCompanyCode(filterVO.getCompanyCode());
			validationVO.setDocumentType(filterVO.getDocumentType());
			validationVO.setDocumentSubType(documentSubType);
			validationVO.setStockHolderCode(stockHolderCode);
		}

		log.log(Log.INFO, "returning validationVO ", validationVO);
		log.log(Log.FINER,"RETURN");
		return validationVO;
	}


	private DocumentValidationVO validateCourier(String companyCode, String documentNumber,
			int airlineId, String documentType, String stockHolder)
			throws SystemException, StockControlDefaultsBusinessException{
		log.log(Log.FINER,"ENTRY");
		DocumentValidationVO docValidationVO = new DocumentValidationVO();
		long mstDocNumber = findLong(documentNumber);
		String stockHolderCode = Range.checkDocumentExistsInAnyStock(
				companyCode,airlineId,documentType,mstDocNumber);
		if(stockHolderCode == null || ("").equals(stockHolderCode.trim())){
			log.log(Log.SEVERE, "No stock contains the Courier ",
					documentNumber);
			throw new StockControlDefaultsBusinessException(
				StockControlDefaultsBusinessException.COURIER_NOTFOUND_INANYSTOCK,
				new Object[] { documentNumber });
		}
		if( ! stockHolderCode.equals(stockHolder)){
			log.log(Log.SEVERE, "Courier stock NOT Available ", documentNumber);
			log.log(Log.SEVERE, "Courier stock NOT Available with ",
					stockHolder);
			throw new StockControlDefaultsBusinessException(
				StockControlDefaultsBusinessException.COURIER_NOTWITH_STOCKHOLDER,
				new Object[] { documentNumber, stockHolder });
		}
		log.log(Log.FINER,"RETURN");
		return docValidationVO;
	}

	private DocumentValidationVO validateEBT(String companyCode, String documentNumber,
			int airlineId, String documentType, String stockHolder)
			throws SystemException, StockControlDefaultsBusinessException{
		log.log(Log.FINER,"ENTRY");
		DocumentValidationVO docValidationVO = new DocumentValidationVO();
		long mstDocNumber = findLong(documentNumber);
		String stockHolderCode = Range.checkDocumentExistsInAnyStock(
				companyCode,airlineId,documentType,mstDocNumber);
		if(stockHolderCode == null || ("").equals(stockHolderCode.trim())){
			log.log(Log.SEVERE, "No stock contains the EBT ", documentNumber);
			throw new StockControlDefaultsBusinessException(
				StockControlDefaultsBusinessException.EBT_NOTFOUND_INANYSTOCK,
				new Object[] { documentNumber });
		}

		if( ! stockHolderCode.equals(stockHolder)){
			log.log(Log.SEVERE, "EBT stock NOT Available ", documentNumber);
			log.log(Log.SEVERE, "EBT stock NOT Available with ", stockHolder);
			throw new StockControlDefaultsBusinessException(
				StockControlDefaultsBusinessException.EBT_NOTWITH_STOCKHOLDER,
				new Object[] { documentNumber, stockHolder });
		}

		log.log(Log.FINER,"RETURN");
		return docValidationVO;
	}

	private Collection<String> checkReservedDocumentExists(
			StockAllocationVO stockAllocationVO)
			throws SystemException{
		Collection<String> alreadyReservedDocs = null;
		Collection<RangeVO> rangeVOColl = stockAllocationVO.getRanges();

		StockFilterVO stockFilterVO = new StockFilterVO();
		stockFilterVO.setCompanyCode(stockAllocationVO.getCompanyCode());
		stockFilterVO.setAirlineIdentifier(stockAllocationVO.getAirlineIdentifier());
		if(stockAllocationVO.getAirportCode() != null){
			log.log(Log.FINEST,
					"===setting airporCode from stkAllcVo in filtVO:",
					stockAllocationVO.getAirportCode());
			stockFilterVO.setAirportCode(stockAllocationVO.getAirportCode());
		}else{
			LogonAttributes logon = ContextUtils.getSecurityContext().getLogonAttributesVO();
			log.log(Log.FINEST,
					"===settin airporCode from logon in the filterVO:", logon.getAirportCode());
			stockFilterVO.setAirportCode(logon.getAirportCode());
		}

		if(rangeVOColl != null){
			for(RangeVO rangeVO : rangeVOColl){
				stockFilterVO.setRangeFrom(rangeVO.getStartRange());
				stockFilterVO.setRangeTo(rangeVO.getEndRange());
				Collection<String> reservedDocs = Reservation.checkReservedDocumentExists(stockFilterVO);
				if(reservedDocs != null && reservedDocs.size() > 0){
					if(alreadyReservedDocs == null) {
						alreadyReservedDocs = new ArrayList<String>();
					}
					alreadyReservedDocs.addAll(reservedDocs);
				}
			}
		}
		log.log(Log.FINE, "====returning already REserve docs:",
				alreadyReservedDocs);
		return alreadyReservedDocs;
	}

	/*
	 * added by Sinoob ends
	 */

	/**
	 * @param stockAllocationVO
	 * @throws SystemException
	 */
	/*public void returnDocumentToStock(StockAllocationVO stockAllocationVO)
					throws SystemException{
		log.entering("StockController", "-->returnDocumentToStock<--------");
		log.log(Log.FINE, "--STockAllocationVO---", stockAllocationVO);
		log.log(Log.FINE, "--DocumentSubType from OPRSHPMSTMIS-->>>",
				stockAllocationVO.getDocumentSubType());
		StockDepleteFilterVO stockDepleteFilterVO = new StockDepleteFilterVO();
		stockDepleteFilterVO.setCompanyCode(stockAllocationVO.getCompanyCode());
		stockDepleteFilterVO.setAirlineId(stockAllocationVO.getAirlineIdentifier());
		// If a booking is cancelled just after creation ( and job didnt run by that time )
		// entry remains in UTL and on next job run the returned stock will be again depleted 
		// so autodeplete Stock is called first
		// Even if exception occurs in autodepleteStock returnDocumentoStock should work
		
		try {
			autoDepleteStock(stockDepleteFilterVO);   
		} catch (Exception e) {  
			// To be reviewed: handle exception
		}
		String stockHolderCode = findStockHolderCodeForAgent(
				stockAllocationVO.getCompanyCode(), stockAllocationVO.getStockControlFor());
		log.log(Log.FINE, "--STockHolderCode-->>>", stockHolderCode);
		stockAllocationVO.setStockHolderCode(stockHolderCode);
		StockHolder stockHolder = null;
		try{
			stockHolder = StockHolder.find(stockAllocationVO
				.getCompanyCode(),stockAllocationVO.getStockHolderCode());
		}
		catch(InvalidStockHolderException invalidStockHolderException){
			for(ErrorVO errorVo : invalidStockHolderException.getErrors()){
				throw new SystemException(errorVo.getErrorCode());
			}
		}
		if(stockHolder!=null){
			stockHolder.returnDocumentToStock(stockAllocationVO);   
		}
		createStockHolderStockDetails(stockAllocationVO,"UR");
		log.exiting("StockController", "-->returnDocumentToStock<---");
	}*/
	
	/**
	 * @author A-5153
	 * @param stockAllocationVO
	 * @throws SystemException
	 */
	public void returnDocumentToStock(StockAllocationVO stockAllocationVO)
			throws SystemException {
		log.entering("StockController", "returnDocumentToStock");

		log.log(Log.FINE, "--DocumentSubType from OPRSHPMSTMIS-->>>",
				stockAllocationVO.getDocumentSubType());
		//Added for ICRD-143455 Starts
		if(!DOCSUBTYP_COMAT.equalsIgnoreCase(stockAllocationVO.getDocumentSubType()) &&  
				(!DOCSUBTYP_COMAT.equalsIgnoreCase(stockAllocationVO.getDocumentSubType()) && !"HQ".equals(stockAllocationVO.getStockHolderCode()))){
		String stockHolderCode = findStockHolderCodeForAgent(
				stockAllocationVO.getCompanyCode(), stockAllocationVO.getStockControlFor());
		log.log(Log.FINE, "--STockHolderCode-->>>", stockHolderCode);
		stockAllocationVO.setStockControlFor(stockHolderCode);
		}
		//Added for ICRD-143455 Ends
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		stockAllocationVO.setLastUpdateUser(logonAttributes.getUserId());
		stockAllocationVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		String status = "D";
		try {
			saveStockUtilisation(stockAllocationVO,status);
		} catch (SystemException e) {
			log.log(Log.SEVERE, "----->>>SystemException");
		}


	}

		/**
		 * @author a-2870
		 * @param documentValidationVO
		 * @throws SystemException
		 * @throws StockControlDefaultsBusinessException
		 */
			public void validateAgentForStockHolder(AWBDocumentValidationVO documentValidationVO)
			throws SystemException,StockControlDefaultsBusinessException{
				log.entering("StockController", "validateAgentForStockHolder");
				String companyCode = documentValidationVO.getCompanyCode();
				String agentCode = null;
				String stkhldr = null;
				String approver = null;
				if(documentValidationVO.getAgentDetails() != null && documentValidationVO.getAgentDetails().size() > 0){
					agentCode = documentValidationVO.getAgentDetails().iterator().next().getAgentCode();
					stkhldr = findStockHolderCodeForAgent(companyCode,agentCode);
				//	log.log(Log.FINE,"\nagentCode="+agentCode+"   "+"stkhldr="+stkhldr);
				}
				if(stkhldr != null && !stkhldr.equals(documentValidationVO.getStockHolderCode())){
					StockHolder stk  = null;
					try{
						stk= StockHolder.find(companyCode,stkhldr);
						log.log(Log.FINE, "stock Holder", stk);
					}
					catch(InvalidStockHolderException invalidStockHolderException){
						log.log(Log.FINE,"--no stock holder found-");
						throw new StockControlDefaultsBusinessException(StockControlDefaultsBusinessException.INVALID_STOCKHOLDERFORAGENTDETAILS);

					}
					if(stk!=null){
						approver = stk.obtainApproverForStockHolder
						(documentValidationVO.getDocumentType());
						log.log(Log.FINE, "\napprover=", approver);
					}
					//if(approver == null || !approver.equals(documentValidationVO.getStockHolderCode())){//Modified as part of ICRD-182618
					if(approver == null){
						log.log(Log.FINE,"\n---------INVALID_STOCKHOLDERFORAGENTDETAILS--------");
						throw new StockControlDefaultsBusinessException(StockControlDefaultsBusinessException.INVALID_STOCKHOLDERFORAGENTDETAILS);
					}
				}
				else if(stkhldr == null){
					throw new StockControlDefaultsBusinessException(StockControlDefaultsBusinessException.INVALID_STOCKHOLDERFORAGENTDETAILS);
				}
				log.exiting("StockController", "validateAgentForStockHolder");

	}

		/**
		 * @param stockAllocationVO
		 * @throws SystemException
		 */
		public void saveStockUtilisation(StockAllocationVO stockAllocationVO,String status)
		throws SystemException{
			log.entering("stockController ", " saveStockUtilisation ");
			log.log(Log.FINE, "--saveStockUtilisation--", stockAllocationVO);
			String companyCode = stockAllocationVO.getCompanyCode();
			String stockHolderCode = stockAllocationVO.getStockControlFor();
			String documentType = stockAllocationVO.getDocumentType();
			String documentSubType = stockAllocationVO.getDocumentSubType();
			// Commented by A-5153 for CRQ_ICRD-38007
			//String status = "U";
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			LocalDate lastUpdateTime =new LocalDate(
					logonAttributes.getStationCode(),Location.ARP,true);      
			GMTDate lastUpdateTimeUTC = lastUpdateTime.toGMTDate();  
			int airlineIdentifier = stockAllocationVO.getAirlineIdentifier();
			Collection<RangeVO> rangeVOs = stockAllocationVO.getRanges();
			RangeVO rangeVO = new RangeVO();
			for(RangeVO vo: rangeVOs){  
				 rangeVO = vo;
			}     


			String sequenceNumber =StockRangeUtilisation.findStockRangeUtilisationExists(companyCode,
							stockHolderCode,documentType,documentSubType,airlineIdentifier,rangeVO);
			StockController stockController =(StockController)SpringAdapter.
			getInstance().getBean("stockcontroldefaultsController");
			if(!"B".equalsIgnoreCase(status)){
			//if(!"".equals(sequenceNumber) && sequenceNumber!=null){
			try{
				StockRangeUtilisation stockRangeUtilisation =
						StockRangeUtilisation.find(companyCode, stockHolderCode,
						sequenceNumber,documentType,documentSubType,airlineIdentifier,rangeVO.getStartRange());
				stockRangeUtilisation.update(rangeVO, status, lastUpdateTimeUTC);
				stockAllocationVO.setOperationFlag(StockAllocationVO.OPERATION_FLAG_UPDATE);			

			}catch(FinderException finderException){

				new StockRangeUtilisation(companyCode,stockHolderCode,documentType
						,documentSubType,airlineIdentifier,rangeVO,status,lastUpdateTimeUTC);
				stockAllocationVO.setOperationFlag(StockAllocationVO.OPERATION_FLAG_INSERT);				
				
					}
			}else{
		    	 new StockRangeUtilisation(companyCode,stockHolderCode,documentType
							,documentSubType,airlineIdentifier,rangeVO,status,lastUpdateTimeUTC);
					stockAllocationVO.setOperationFlag(StockAllocationVO.OPERATION_FLAG_INSERT);
			}
			stockController.triggerStockUtilisationAudit(stockAllocationVO);

			log.exiting("stockController ", " saveStockUtilisation ");
	}


		/**
		 * @author a-1863
		 * @param reportSpec
		 * @return Map<String, Object>
		 * @throws SystemException
		 * @throws ReportGenerationException
		 */
		public Map<String, Object> generateStockListReport(ReportSpec reportSpec)
		throws SystemException {
			log.entering("StockController","generateStockListReport");
			StockFilterVO filterVO = (StockFilterVO)reportSpec.getFilterValues().iterator().next();
			com.ibsplc.icargo.business.shared.document.vo.DocumentFilterVO documentFilterVO
					= new com.ibsplc.icargo.business.shared.document.vo.DocumentFilterVO();
			documentFilterVO.setCompanyCode(filterVO.getCompanyCode());
			Collection<DocumentVO> doctype = null;
			log.log(Log.INFO,"going to call findDocumentDetails");
			log.log(Log.INFO, "DOUCMENT SUBTYPE IS-------->", filterVO.getDocumentSubType());
			try{
				doctype = new DocumentTypeProxy().findDocumentDetails(documentFilterVO);
				log.log(Log.INFO, "doctype---->", doctype);

			}catch(ProxyException exception){
				throw new SystemException(exception.getMessage());
			}
			if(doctype!=null){
				if (filterVO.getDocumentSubType() != null
						&& filterVO.getDocumentSubType().trim().length() > 0
						&& !("ALL").equals(filterVO.getDocumentSubType())) {
					for (DocumentVO vo : doctype) {
						if (vo.getDocumentSubTypeDes().equals(filterVO.getDocumentSubType())) {
							filterVO.setDocumentSubType(vo.getDocumentSubType());
						}
					}
				}
			}
			log.log(Log.INFO, "DOUCMENT SUBTYPE IS after setting-------->",
					filterVO.getDocumentSubType());
			log.log(Log.INFO,"going to call validateAlphaCode");
			if(filterVO.getAirlineCode()!=null && filterVO.getAirlineCode().trim()
					.length()>0){
				try{
					log
							.log(Log.INFO, "-----------filterVO--------->",
									filterVO);
					AirlineValidationVO airlineVO = new SharedAirlineProxy().
						validateAlphaCode(filterVO.getCompanyCode(),filterVO.getAirlineCode());

					if(airlineVO!=null){
						log.log(Log.INFO,"-----------Entered--------->");
						filterVO.setAirlineIdentifier(airlineVO.getAirlineIdentifier());
					}
				}catch(ProxyException exception){
					throw new SystemException(exception.getMessage());
				}
			}
			log.log(Log.INFO, "-----------After filterVO--------->", filterVO);
			log.log(Log.INFO,"going to call findAirportParametersByCode");
			HashMap<String, String> sysparVal = null;
			Collection<String> sysPar = new ArrayList<String>();
			sysPar.add(SYSTEMPARAMETERVALUE);
			try {
				sysparVal = (HashMap<String, String>) new SharedAreaProxy().
				findAirportParametersByCode(
						filterVO.getCompanyCode(),
						filterVO.getAirportCode(), sysPar);

			} catch (ProxyException exception) {
				throw new SystemException(exception.getMessage());
			}
			filterVO.setStockHolderCode(sysparVal.get(SYSTEMPARAMETERVALUE));
			log.log(Log.INFO, "filterVO = ", filterVO);
			Page<StockVO> pag = findStockList(filterVO);

			Collection<StockVO> stockvos = new ArrayList<StockVO>();
			for (StockVO vo : pag) {
				try {
					AirlineValidationVO airlineVO =new SharedAirlineProxy().findAirline(
							filterVO.getCompanyCode(), vo.getAirlineId());
					vo.setAirline(airlineVO.getAlphaCode().toUpperCase());
				} catch (ProxyException exception) {
					throw new SystemException(exception.getMessage());
				}
				stockvos.add(vo);
			}

			if(filterVO.getDocumentType()==null) {
				filterVO.setDocumentType("ALL");
			}
			if(filterVO.getAirlineCode()==null) {
				filterVO.setAirlineCode("");
			}

			log.log(Log.INFO, "stockvos got from server = ", stockvos);
			ReportMetaData parameterMetaData = new ReportMetaData();
			parameterMetaData.setFieldNames(new String[] { "airlineCode",
					"documentType", "documentSubType" });
			reportSpec.addParameterMetaData(parameterMetaData);
			reportSpec.addParameter(filterVO);

			ReportMetaData reportMetaData = new ReportMetaData();
			reportMetaData.setColumnNames(new String[] { "ARLCOD", "DOCTYP",
					"SUBTYP", "TOTSTK", "RDRLVL" });
			reportMetaData.setFieldNames(new String[] { "airline", "documentType",
					"documentSubType", "totalStock", "reorderLevel" });
			reportSpec.setReportMetaData(reportMetaData);
			reportSpec.setData(stockvos);
			reportSpec.addExtraInfo(doctype);
			reportSpec.setReportId(STKLST_RPTID);

			return ReportAgent.generateReport(reportSpec);
	}
		


	/**
	 * Method for blacklisting a stock of StockHolder or Stock in the specified range
	 * @param blacklistStockVO
	 * @throws SystemException
	 * @throws StockNotFoundException
	 * @throws BlacklistedRangeExistsException
	 * @throws StockControlDefaultsBusinessException
	 */
	private void blacklistStockHolderStock(BlacklistStockVO blacklistStockVO)
	throws SystemException, StockNotFoundException,
	BlacklistedRangeExistsException,
	StockControlDefaultsBusinessException{

		log.log(Log.FINE, "----->>>Calling blacklistStockHolderStock in ctrl-------");
		// flag for checking stock holder exists or not
		boolean isStockHolder = false;
		boolean isConfirmationProcessNeeded=false;	
		boolean traceNeeded=false;	
		StockAllocationVO stockAllocationVo=null;
		String txncod="B";
		//For sending the ranges to operations
		Collection<RangeVO> rangeVOs = new ArrayList<RangeVO>();
		//For finding utilised stocks 
		Collection<RangeVO> utilisedRanges=null;
		RangeVO rangeVO = new RangeVO();
		rangeVO.setStartRange(blacklistStockVO.getRangeFrom());
		rangeVO.setEndRange(blacklistStockVO.getRangeTo());
		rangeVO.setBlackList(true);
		rangeVOs.add(rangeVO);

		if (!BlackListStock.alreadyBlackListed(blacklistStockVO)) {
			
			//Added by A-3791 for ICRD-110209
			//To split VO based on stock holder code
			Collection<StockVO> blacklistStockVos = Stock
					.findBlacklistRangesForBlackList(blacklistStockVO);
			boolean isManual=true;//ICRD-220368
			if (blacklistStockVos == null || blacklistStockVos.size() == 0) {
				blacklistStockVos = new ArrayList<StockVO>(1);
				blacklistStockVos.add(new StockVO());
			}
			if(blacklistStockVos!=null && blacklistStockVos.size()>0){
				for(StockVO stockVO:blacklistStockVos){
					BlacklistStockVO blklstStockVO = null;
					ArrayList<RangeVO> rangesArray = null;
					if (stockVO.getCompanyCode() != null) {
						blklstStockVO = new BlacklistStockVO();
						blklstStockVO.setCompanyCode(blacklistStockVO
								.getCompanyCode());
						blklstStockVO.setAirlineIdentifier(blacklistStockVO
								.getAirlineIdentifier());
						blklstStockVO.setDocumentType(blacklistStockVO
								.getDocumentType());
						blklstStockVO.setDocumentSubType(blacklistStockVO
								.getDocumentSubType());					
					rangesArray = (ArrayList<RangeVO>) stockVO.getRanges();
					// Modified by A-5290 for ICRD-204913
						if (rangesArray.get(0).getAsciiStartRange() < Range
								.findLong(blacklistStockVO.getRangeFrom())) {
							blklstStockVO.setRangeFrom(blacklistStockVO
									.getRangeFrom());
					}else{
							blklstStockVO.setRangeFrom(rangesArray.get(0)
									.getStartRange());
						}
						if (rangesArray.get(0).getAsciiEndRange() < Range
								.findLong(blacklistStockVO.getRangeTo())) {
							blklstStockVO.setRangeTo(rangesArray.get(0)
									.getEndRange());
					}else{
							blklstStockVO.setRangeTo(blacklistStockVO
									.getRangeTo());
						}
						blklstStockVO.setStockHolderCode(stockVO
								.getStockHolderCode());
						blklstStockVO.setLastUpdateTime(blacklistStockVO
								.getLastUpdateTime());
						blklstStockVO.setBlacklistDate(blacklistStockVO
								.getBlacklistDate());
					} else {
						blklstStockVO = blacklistStockVO;
					}
					if(rangesArray != null && rangesArray.size()>0){
						isManual=rangesArray.get(0).isManual();//ICRD-220368
					}
					blklstStockVO.setStatus("A"); 
					//Added by A-7373 for ICRD-241944
					if(rangesArray != null && rangesArray.size()>0){
						blklstStockVO.setManual(rangesArray.get(0).isManual());
					}
					blklstStockVO.setRemarks(blacklistStockVO.getRemarks());
					findAndSetStockHolderAppovers(blklstStockVO); 
					BlackListStock blacklistStock = new BlackListStock(
							blklstStockVO);
			BlacklistStockVO blacklistVO=new BlacklistStockVO();
			//audit
			StockAuditVO auditVO = new StockAuditVO(
					StockAuditVO.AUDIT_PRODUCTNAME,
					StockAuditVO.AUDIT_MODULENAME,
					StockAuditVO.AUDIT_ENTITY);
			auditVO = (StockAuditVO)
			AuditUtils.populateAuditDetails(auditVO,blacklistStock,
					true);
			auditStock(blacklistStock,auditVO,AuditVO
					.CREATE_ACTION,"BLACKLIST_STOCK");
			//audit complete
			//added by a-4443 for icrd-3024 on 13-Mar-2012 starts
			SharedDefaultsProxy sharedDefaultsProxy = new SharedDefaultsProxy();
			Collection<String> syspar = new ArrayList<String>();
			syspar.add(STOCK_DEFAULTS_CONFIRMATIONREQUIRED);
			syspar.add(STOCK_DEFAULTS_ENABLESTOCKHISTORY);
		 	   
	try{
		Map<String,String> sysmap= sharedDefaultsProxy.findSystemParameterByCodes(syspar);
		isConfirmationProcessNeeded=StockAllocationVO.FLAG_YES.equals(sysmap.get(STOCK_DEFAULTS_CONFIRMATIONREQUIRED))?true:false;
		traceNeeded=StockAllocationVO.FLAG_YES.equals(sysmap.get(STOCK_DEFAULTS_ENABLESTOCKHISTORY))?true:false;
	  }
	catch(ProxyException proxyException){ 
		for(ErrorVO ex : proxyException.getErrors()){
			throw new SystemException(ex.getErrorCode());
		}
	}   

	if (isConfirmationProcessNeeded){
			try {
				
						blacklistMissingStock(new BlacklistStockVO(blklstStockVO));        
			} catch (RangeExistsException e) {
				
				log.log(Log.SEVERE, "----->>>RangeExistsException");
			} catch (InvalidStockHolderException e) {
				
				log.log(Log.SEVERE, "----->>>InvalidStockHolderException");
			} catch (FinderException e) {
				
				log.log(Log.SEVERE, "----->>>FinderException");
			}
	}
			//added by a-4443 for icrd-3024 on 13-Mar-2012 ends
			Collection<StockVO> blacklistStocks = Stock
							.findBlacklistRanges(blklstStockVO);
			log.log(Log.FINE, "---blacklist ranges--->", blacklistStocks);
			Collection<StockVO> blacklistedStocks = rearrangeRanges(
							blacklistStocks, blklstStockVO);
			log.log(Log.FINE, "**************AFTER REARRANGE*******",
					blacklistedStocks);
			try {
				for (StockVO stockVo : blacklistedStocks) {              
					StockHolder stockHolder = StockHolder.find(stockVo
							.getCompanyCode(), stockVo.getStockHolderCode());
					 stockAllocationVo = new StockAllocationVO();
					stockAllocationVo.setCompanyCode(stockVo.getCompanyCode());
					stockAllocationVo.setStockHolderCode(stockVo
							.getStockHolderCode());
					stockAllocationVo.setDocumentType(blacklistStockVO
							.getDocumentType());
					stockAllocationVo.setDocumentSubType(blacklistStockVO
							.getDocumentSubType());
					stockAllocationVo.setAirlineIdentifier(blacklistStockVO
							.getAirlineIdentifier());
					stockAllocationVo
							.setTransferMode(StockAllocationVO.MODE_RETURN);
					stockAllocationVo.setRemarks(blacklistStockVO.getRemarks()); 
					stockAllocationVo.setManual(isManual);//ICRD-220368
					Collection<RangeVO> ranges = new ArrayList<RangeVO>();
					ArrayList<RangeVO> rangesArr = null;
					rangesArr = (ArrayList<RangeVO>) stockVo.getRanges();
					RangeVO rangeVo = new RangeVO();
					rangeVo.setStartRange(rangesArr.get(0).getStartRange());
					rangeVo.setEndRange(rangesArr.get(0).getEndRange());
					rangeVo.setBlackList(true);
					ranges.add(rangeVo);
					stockAllocationVo.setRanges(ranges);
					//Added by A-7373 for ICRD-241944 
					stockAllocationVo.setManual(rangesArr.get(0).isManual());
					log.log(Log.FINE,
							"--------Ranges going for deplete--------",
							stockAllocationVo);
					stockHolder.deplete(stockAllocationVo, true,false);
					stockHolder.setLastUpdateTime(stockHolder.getLastUpdateTime());
					stockHolder.setLastUpdateUser(stockHolder.getLastUpdateUser());
					blacklistStockVO.setStockHolderCode(stockVo
							.getStockHolderCode());

					blacklistVO.setCompanyCode(stockVo.getCompanyCode());
					blacklistVO.setStockHolderCode(stockVo.getStockHolderCode());
					blacklistVO.setDocumentType(blacklistStockVO.getDocumentType());
					blacklistVO.setDocumentSubType(blacklistStockVO.getDocumentSubType());
					blacklistVO.setAirlineIdentifier(blacklistStockVO.getAirlineIdentifier());
					blacklistVO.setRangeFrom(rangesArr.get(0).getStartRange());
					blacklistVO.setRangeTo(rangesArr.get(0).getEndRange());
					blacklistVO.setLastUpdateTime(blacklistStockVO.getLastUpdateTime());
					blacklistVO.setLastUpdateUser(blacklistStockVO.getLastUpdateUser());
					blacklistVO.setStatus("D");
					//Added by A-7373 for ICRD-241944
					blacklistVO.setManual(rangesArr.get(0).isManual());
					
					blacklistVO.setBlacklistDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
					blacklistVO.setRemarks(blacklistStockVO.getRemarks());
					blacklistStock = new BlackListStock(blacklistVO);

					createStockHolderStockDetails(stockAllocationVo, txncod);
					isStockHolder = true;
					//Added by A-2881 for ICRD-3082 -starts
					if(traceNeeded){  
						createHistory(stockAllocationVo,StockAllocationVO.MODE_BLACKLIST);
					}
                    //Added by A-2881 for ICRD-3082-ends
				}
			} catch (InvalidStockHolderException invalidStockHolderException) {
				log.log(Log.FINE, "&&&&%%%%--StockHolder Not exists\n\n ");
				throw new StockControlDefaultsBusinessException(
							invalidStockHolderException);
			}
			// Remove comment at the time of full build
			OperationsShipmentProxy operationsShipmentProxy = new OperationsShipmentProxy();
			try {
				log.log(Log.FINE, "----->>>Companycode <<<------",
						blacklistStockVO.getCompanyCode());
				log.log(Log.FINE, "----->>>Airline ID <<<------",
						blacklistStockVO.getAirlineIdentifier());
				log.log(Log.FINE, "----->>>Calling blacklistRange <<<------");
				operationsShipmentProxy.blacklistRange(blacklistStockVO
						.getCompanyCode(), blacklistStockVO
						.getAirlineIdentifier(), rangeVOs);
			} catch (ProxyException proxyException) {
				for (ErrorVO errorVo : proxyException.getErrors()) {
					throw new SystemException(errorVo.getErrorCode());
				}
			}
			                 

					
			
			// auditBlacklistStock(blacklistStockVO,BlacklistStockAuditVO.
			// STOCK_BLKLST_ACTIONCODE);
			if (!isStockHolder) {
				// no stock holder , inserting blank string
				blacklistStockVO.setStockHolderCode(" ");
			}
				}
				//Create blacklist history for stock ranges that are used
				createBlacklistHistoryForUsedRanges(blacklistStockVO); 
			}
		} else {
			log.log(Log.FINE, " This Range is already BlackListed");
			log.log(Log.FINE, "Throwing BlacklistedRangeExistsException");
			throw new BlacklistedRangeExistsException(
					BlacklistedRangeExistsException.ALREADY_BLACKLISTED_RANGE);
		}



	}
	/**
	 * Added by A-3791 for ICRD-110209
	 * */
	private void findAndSetStockHolderAppovers(BlacklistStockVO blacklistVO) throws SystemException {
		if(blacklistVO!=null){
			StockFilterVO filterVo = new StockFilterVO();
			filterVo.setCompanyCode(blacklistVO.getCompanyCode());
			filterVo.setDocumentType(blacklistVO.getDocumentType());
			filterVo.setDocumentSubType(blacklistVO.getDocumentSubType());
			filterVo.setStockHolderCode(blacklistVO.getStockHolderCode());
			filterVo.setAirlineIdentifier(blacklistVO.getAirlineIdentifier());
			try {
				Stock stk = Stock.find(filterVo);
				blacklistVO.setFirstLevelStockHolder(stk.getStockApproverCode());
				log.log(Log.FINE, "-*-*-*-*-*-*-*-*-*-*-STOCKAPPROVERCODE->"+stk.getStockApproverCode());
			} catch (FinderException e) {
				//e.printStackTrace();
				log.log(Log.SEVERE, "FinderException caught....");
			}
		}
	}
    /**
     * This method creates a transaction history as black listed 
     * for ranges that are utilised.
     * 10-15 is utilised
     * 16-20 is with a stock holder
     * 21-25 not in any stock
     * 
     * If user black lists 13-25 then 
     * 1)13-15 will be in history with status B provided corresponding utilistaion transaction 
     *   is properly marked.
     * 2)16-20 will be in history with status B
     * 3)21-25 will not be in history since not in any stock
     * 
     * 
     * @author A-2881
     * @param blacklistStockVO
     * @throws SystemException
     */
    private void createBlacklistHistoryForUsedRanges(
			BlacklistStockVO blacklistStockVO) throws SystemException {
		// Create Hisotry for blacklisted ranges which are used
    	log.entering("StockController", "crateBlacklistHistoryForUsedRanges");
		StockRangeFilterVO filterVO = new StockRangeFilterVO();
		Collection<RangeVO> finalBlackListedRange = null;
		RangeVO rangeVO = new RangeVO();

		rangeVO.setStartRange(blacklistStockVO.getRangeFrom());
		rangeVO.setEndRange(blacklistStockVO.getRangeTo());

		filterVO.setStatus(StockAllocationVO.MODE_USED);
		filterVO.setStartRange(blacklistStockVO.getRangeFrom());
		filterVO.setEndRange(blacklistStockVO.getRangeTo());
		filterVO.setCompanyCode(blacklistStockVO.getCompanyCode());
		filterVO.setAirlineIdentifier(blacklistStockVO.getAirlineIdentifier());
		Collection<StockRangeHistoryVO> utilisedHistory = StockRangeHistory
				.findStockRangeHistory(filterVO);

		if (utilisedHistory != null) {
			RangeVO usedBlacklistedRange = null;
			for (StockRangeHistoryVO stockRangeHistoryVO : utilisedHistory) {
				usedBlacklistedRange = new RangeVO();
				if (findLong(rangeVO.getStartRange()) > findLong(stockRangeHistoryVO
						.getStartRange())) {
					usedBlacklistedRange.setStartRange(rangeVO.getStartRange());
					usedBlacklistedRange.setAsciiStartRange(findLong(rangeVO
							.getStartRange()));
				} else {
					usedBlacklistedRange.setStartRange(stockRangeHistoryVO
							.getStartRange());
					usedBlacklistedRange
							.setAsciiStartRange(findLong(stockRangeHistoryVO
									.getStartRange()));
				}
				if (findLong(rangeVO.getEndRange()) > findLong(stockRangeHistoryVO
						.getEndRange())) {
					usedBlacklistedRange.setEndRange(stockRangeHistoryVO
							.getEndRange());
					usedBlacklistedRange.setAsciiEndRange(stockRangeHistoryVO
							.getAsciiEndRange());
				} else {
					usedBlacklistedRange.setEndRange(rangeVO.getEndRange());
					usedBlacklistedRange.setAsciiEndRange(findLong(rangeVO
							.getEndRange()));
				}
				finalBlackListedRange = new ArrayList<RangeVO>();
				log.log(Log.FINE, "Used Blacklist -->", finalBlackListedRange);
				finalBlackListedRange.add(usedBlacklistedRange);
				StockAllocationVO stkAllocationVo = new StockAllocationVO();
				stkAllocationVo.setCompanyCode(blacklistStockVO
						.getCompanyCode());
				stkAllocationVo.setStockHolderCode(stockRangeHistoryVO
						.getFromStockHolderCode());
				stkAllocationVo.setDocumentType(stockRangeHistoryVO
						.getDocumentType());
				stkAllocationVo.setDocumentSubType(stockRangeHistoryVO
						.getDocumentSubType());
				stkAllocationVo.setAirlineIdentifier(blacklistStockVO
						.getAirlineIdentifier());
				stkAllocationVo.setRemarks(blacklistStockVO.getRemarks());
				stkAllocationVo.setRanges(finalBlackListedRange);
				createHistory(stkAllocationVo, StockAllocationVO.MODE_BLACKLIST);
			}
		}
		log.exiting("StockController", "crateBlacklistHistoryForUsedRanges");
	}
	/**
	 * @throws FinderException
	 * @throws StockControlDefaultsBusinessException
	 * @throws InvalidStockHolderException
	 * @throws RangeExistsException
	 * @throws BlacklistedRangeExistsException
	 * @throws StockNotFoundException
	 * @throws SystemException
	 * @author A-4443
	 * @param blacklistStockVO
	 * @throws
	 */
	private void blacklistMissingStock(BlacklistStockVO blacklistStockVO) 
	throws SystemException, StockNotFoundException, BlacklistedRangeExistsException, 
	RangeExistsException, InvalidStockHolderException, StockControlDefaultsBusinessException, 
	FinderException {
		log.entering("StockController", "blacklistMissingStock");
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
		.getLogonAttributesVO();
		Collection<TransitStockVO> transitStockVOs = null;
		transitStockVOs = findBlackListRangesFromTransit(blacklistStockVO);
		Collection<RangeVO> splitRangeResult = null;  
		Collection<TransitStockVO> currentTransitStockVOs=null;                
		TransitStockVO transitStockVO = new TransitStockVO();		
		transitStockVO.setStockHolderCode(blacklistStockVO.getStockHolderCode());
		transitStockVO.setAirlineIdentifier(blacklistStockVO.getAirlineIdentifier());
		transitStockVO.setDocumentType(blacklistStockVO.getDocumentType());
		transitStockVO.setDocumentSubType(blacklistStockVO.getDocumentSubType());
		transitStockVO.setCompanyCode(blacklistStockVO.getCompanyCode());
		transitStockVO.setConfirmStatus(TransitStockVO.TRANSIT_STATUS_MISSING);  
		MissingStockVO missingStockVO = new MissingStockVO();
		/*missingStockVO.setMissingStartRange(blacklistStockVO.getRangeFrom());
		missingStockVO.setMissingEndRange(blacklistStockVO.getRangeTo());*/
		missingStockVO.setCompanyCode(blacklistStockVO.getCompanyCode());
		missingStockVO.setDocumentType(blacklistStockVO.getDocumentType());
		missingStockVO.setDocumentSubType(blacklistStockVO.getDocumentSubType());
		missingStockVO.setStockHolderCode(blacklistStockVO.getStockHolderCode());
		if(transitStockVOs.size()>0){
		for(TransitStockVO transitStock : transitStockVOs){
			splitRangeResult = new ArrayList<RangeVO>();   
			missingStockVO.setMissingStartRange(blacklistStockVO.getRangeFrom());
			missingStockVO.setMissingEndRange(blacklistStockVO.getRangeTo());	
			missingStockVO.setAsciiMissingStartRange(findLong(blacklistStockVO.getRangeFrom()));
			missingStockVO.setAsciiMissingEndRange(findLong(blacklistStockVO.getRangeTo()));
			transitStockVO.setAsciiMissingStartRange(findLong(transitStock.getMissingStartRange()));
			transitStockVO.setAsciiMissingEndRange(findLong(transitStock.getMissingEndRange()));
			transitStockVO.setStockHolderCode(transitStock.getStockHolderCode());
			transitStockVO.setStockControlFor(transitStock.getStockControlFor());
			transitStockVO.setTxnDate(transitStock.getTxnDate()); 
			transitStockVO.setMissingRemarks(transitStock.getMissingRemarks());
			transitStockVO.setTxnCode(transitStock.getTxnCode());          
			ArrayList<MissingStockVO> missingStockVOs=new ArrayList<MissingStockVO>();
			missingStockVOs.add(missingStockVO);
			transitStockVO.setMissingRanges(missingStockVOs);
			
			Collection<RangeVO> rangeVOsForAllocation = null;
			// Find the Confirmed stock range from actual and missing ranges
			rangeVOsForAllocation = getConfirmedStock(transitStockVO);                  
			Collection<TransitStockVO> transitStockVOsForAllocation = new ArrayList<TransitStockVO>();
			for(RangeVO rangevo:rangeVOsForAllocation){
				TransitStockVO transitStockVOForAllocation = new TransitStockVO(transitStockVO);
				//transitStockVOForAllocation.setStockHolderCode(transitStockVO.getStockHolderCode());
				transitStockVOForAllocation.setAirlineIdentifier(blacklistStockVO.getAirlineIdentifier());
				/*transitStockVOForAllocation.setDocumentType(transitStockVO.getDocumentType());
				transitStockVOForAllocation.setDocumentSubType(transitStockVO.getDocumentSubType());*/
				transitStockVOForAllocation.setCompanyCode(blacklistStockVO.getCompanyCode());
				transitStockVOForAllocation.setActualStartRange(rangevo.getStartRange());
				transitStockVOForAllocation.setActualEndRange(rangevo.getEndRange());
				transitStockVOForAllocation.setConfirmStatus(TransitStockVO.TRANSIT_STATUS_MISSING);
				transitStockVOForAllocation.setOperationFlag(TransitStockVO.OPERATION_FLAG_INSERT);
				transitStockVOForAllocation.setMissingStartRange(rangevo.getStartRange());
				transitStockVOForAllocation.setAsciiMissingStartRange(findLong(rangevo.getStartRange()));
				transitStockVOForAllocation.setMissingEndRange(rangevo.getEndRange());
				transitStockVOForAllocation.setAsciiMissingEndRange(findLong(rangevo.getEndRange()));
				transitStockVOForAllocation.setMissingNumberOfDocs((missingStockVO
						.getAsciiMissingEndRange()
						- missingStockVO.getAsciiMissingStartRange())+1);
				transitStockVOForAllocation.setLastUpdateTime(new LocalDate(logonAttributes
						.getAirportCode(), Location.ARP, true));
				transitStockVOForAllocation.setLastUpdateUser(logonAttributes.getUserId());
				/*transitStockVOForAllocation.setStockControlFor(transitStockVO.getStockControlFor());
				transitStockVOForAllocation.setTxnCode(transitStockVO.getTxnCode());
				transitStockVOForAllocation.setTxnDate(new LocalDate(logonAttributes
						.getAirportCode(), Location.ARP, true));*/
				transitStockVOsForAllocation.add(transitStockVOForAllocation); 
				
			}
			//persist TransitStock
			TransitStock.saveTransitStock(transitStockVOsForAllocation);
						
			// Find the Confirmed stock range from actual and missing ranges
			currentTransitStockVOs=new ArrayList<TransitStockVO>();
			currentTransitStockVOs.add(transitStock);
			splitRangeResult.addAll(splitRanges(currentTransitStockVOs,rangeVOsForAllocation));
			updateBlackHistory(splitRangeResult, blacklistStockVO, transitStock);
		//}
		
		
		//Collection<TransitStockVO> blacklistedTransitStockVOs = null;
		Collection<TransitStockVO> transitStockVOsToremoveFromTransit = null;
			
		//set existing TransitStockVO as D and adding to collection
		TransitStockVO transitStockVOtoDeleteFromTransit = new TransitStockVO(transitStock);        	
		/*transitStockVOtoDeleteFromTransit.setStockHolderCode(blacklistStockVO.getStockHolderCode());
		transitStockVOtoDeleteFromTransit.setAirlineIdentifier(blacklistStockVO.getAirlineIdentifier());
		transitStockVOtoDeleteFromTransit.setDocumentType(blacklistStockVO.getDocumentType());
		transitStockVOtoDeleteFromTransit.setDocumentSubType(blacklistStockVO.getDocumentSubType());
		transitStockVOtoDeleteFromTransit.setCompanyCode(blacklistStockVO.getCompanyCode());
		TransitStockVO stkVO = (TransitStockVO)((ArrayList)transitStockVOs).get(0);
		transitStockVOtoDeleteFromTransit.setSequenceNumber(stkVO.getSequenceNumber());
		transitStockVOtoDeleteFromTransit.setStockHolderCode(stkVO.getStockHolderCode());*/
		transitStockVOtoDeleteFromTransit.setOperationFlag(TransitStockVO.OPERATION_FLAG_DELETE);
		if (transitStockVOsToremoveFromTransit == null) {
			transitStockVOsToremoveFromTransit = new ArrayList<TransitStockVO>();              
		}
		transitStockVOsToremoveFromTransit.add(transitStockVOtoDeleteFromTransit);

		//persist TransitStock
		TransitStock.saveTransitStock(transitStockVOsToremoveFromTransit);
		//deleteTransitStockRange(transitStockVOsToremoveFromTransit);
		
		}
		
		/*if (splitRangeResult != null) {
				for (RangeVO range : splitRangeResult) {
					blacklistStockVO.setRangeFrom(range.getStartRange());
					blacklistStockVO.setRangeTo(range.getEndRange());
					blacklistStockVO.setLastUpdateTime(blacklistStockVO
							.getLastUpdateTime());
					blacklistStockVO.setLastUpdateUser(blacklistStockVO
							.getLastUpdateUser());
					blacklistStockVO.setStatus("D");
					blacklistStockVO.setBlacklistDate(new LocalDate(
							LocalDate.NO_STATION, Location.NONE, false));
					BlackListStock blackListStock = new BlackListStock(
							blacklistStockVO);
					// Create History for black listing
					StockAllocationVO stockAllocationVo = new StockAllocationVO();
					stockAllocationVo.setCompanyCode(blacklistStockVO
							.getCompanyCode());
					stockAllocationVo.setStockHolderCode(blacklistStockVO
							.getStockHolderCode());
					stockAllocationVo.setDocumentType(blacklistStockVO
							.getDocumentType());
					stockAllocationVo.setDocumentSubType(blacklistStockVO
							.getDocumentSubType());
					stockAllocationVo.setAirlineIdentifier(blacklistStockVO
							.getAirlineIdentifier());
					stockAllocationVo.setRemarks(blacklistStockVO.getRemarks());
					Collection<RangeVO> ranges = new ArrayList<RangeVO>();
					ranges.add(range);
					stockAllocationVo.setRanges(ranges);
					createHistory(stockAllocationVo,
							StockAllocationVO.MODE_BLACKLIST);    
				}
			}*/
		
		}
					
		log.exiting("StockController", "DeleteFromTransitStock");
		
		
	}
	/**
	 * 
	 * @param splitRangeResult
	 * @param blacklistStockVO
	 * @param transitStock
	 * @throws SystemException
	 */
	private void updateBlackHistory(Collection<RangeVO> splitRangeResult,BlacklistStockVO blacklistStockVO,TransitStockVO transitStock)throws SystemException{
		if (splitRangeResult != null) {
			for (RangeVO range : splitRangeResult) {
				blacklistStockVO.setRangeFrom(range.getStartRange());
				blacklistStockVO.setRangeTo(range.getEndRange());
				blacklistStockVO.setLastUpdateTime(blacklistStockVO
						.getLastUpdateTime());
				blacklistStockVO.setLastUpdateUser(blacklistStockVO
						.getLastUpdateUser());
				blacklistStockVO.setStatus("D");
				blacklistStockVO.setBlacklistDate(new LocalDate(
						LocalDate.NO_STATION, Location.NONE, false));
				BlackListStock blackListStock = new BlackListStock(
						blacklistStockVO);
				// Create History for black listing
				StockAllocationVO stockAllocationVo = new StockAllocationVO();
				stockAllocationVo.setCompanyCode(transitStock
						.getCompanyCode());
				stockAllocationVo.setStockHolderCode(transitStock
						.getStockHolderCode());
				stockAllocationVo.setDocumentType(transitStock
						.getDocumentType());
				stockAllocationVo.setDocumentSubType(transitStock
						.getDocumentSubType());
				stockAllocationVo.setAirlineIdentifier(transitStock
						.getAirlineIdentifier());
				stockAllocationVo.setRemarks(blacklistStockVO.getRemarks());
				Collection<RangeVO> ranges = new ArrayList<RangeVO>();
				ranges.add(range);
				stockAllocationVo.setRanges(ranges);
				createHistory(stockAllocationVo,
						StockAllocationVO.MODE_BLACKLIST);    
			}
		}
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
//		auditVo.setLastUpdateTime(this.getLastUpdateTime());
//		auditVo.setLastUpdateUser(this.getLastUpdateUser());
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
	 * @author A-3184
	 * @param reportSpec
	 * @return Map<String, Object>
	 * @throws SystemException
	 * @throws StockNotFoundException
	 * @throws ReportGenerationException
	 */
	public Map<String, Object> generateAWBStockReport(ReportSpec reportSpec)
	throws SystemException {
		log.entering("StockController","generateAWBStockReport");
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
		.getLogonAttributesVO();
		String stkhldcod = (String)reportSpec.getFilterValues().get(0);
		StockFilterVO filterVO = new StockFilterVO();
		filterVO.setCompanyCode(logonAttributes.getCompanyCode());
		filterVO.setStockHolderCode(stkhldcod);
		log.log(Log.INFO, "AWBStockReport Filter ", filterVO);
		if(stkhldcod!=null && stkhldcod.trim().length()>0){
			StockHolder stockHolderDest = new StockHolder();
			try {
				stockHolderDest = StockHolder.find(filterVO.getCompanyCode(),stkhldcod);
			} catch (InvalidStockHolderException e) {
				stockHolderDest = null;
			}
			log.log(Log.FINE, "Stockholder after find is----", stockHolderDest);
			if (stockHolderDest == null) {
				throw new SystemException(
						InvalidStockHolderException.INVALID_STOCKHOLDER_FOR_MONITOR);
			}
		}
		Collection<RangeVO> rangeVOs = new ArrayList<RangeVO>();
		rangeVOs = StockHolder.findAWBStockDetailsForPrint(filterVO);
		log.log(Log.INFO, "stockRangeVO ", rangeVOs);
		if(rangeVOs == null || rangeVOs.size()==0){
			throw new SystemException(StockControlDefaultsBusinessException.STOCK_NOT_FOUND);
		}else{
		reportSpec.addExtraInfo(rangeVOs);
		}
		return ReportAgent.generateReport(reportSpec);
	}
	/**
	 * This method is used to return the stock details of a customer
	 *
	 * @author A-3184
	 * @param stockDetailsFilterVO
	 * @return StockDetailsVO
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException
	 */
	public StockDetailsVO findCustomerStockDetails(StockDetailsFilterVO stockDetailsFilterVO)
			throws SystemException, StockControlDefaultsBusinessException {
		log.entering("StockController", "-->findCustomerStockDetails<---");
		log.log(Log.FINE, "--stockDetailsFilterVO---", stockDetailsFilterVO);
			String stockHolderCode = findStockHolderForCustomer(
				stockDetailsFilterVO.getCompanyCode(), stockDetailsFilterVO.getCustomerCode());
		log.log(Log.FINE, "--STockHolderCode-->>>", stockHolderCode);
			if(stockHolderCode == null || ("").equals(stockHolderCode.trim())){
				log.log(Log.FINE,"No agent Mapping with stockholder");
				throw new StockControlDefaultsBusinessException(
					StockControlDefaultsBusinessException.STOCKHOLDER_NOT_FOUND);
			}
		stockDetailsFilterVO.setStockHolderCode(stockHolderCode);
		StockHolder stockHolder = null;
		try{
			stockHolder = StockHolder.find(stockDetailsFilterVO
				.getCompanyCode(),stockDetailsFilterVO.getStockHolderCode());

		}
		catch(InvalidStockHolderException invalidStockHolderException){
			for(ErrorVO errorVo : invalidStockHolderException.getErrors()){
				throw new SystemException(errorVo.getErrorCode());
			}
		}

		log.exiting("StockController", "-->findCustomerStockDetails<---");
		return	stockHolder.findCustomerStockDetails(stockDetailsFilterVO);

	}
	/**
	 * Returns the StockHolder associated with an customer code
	 * by joining  the shared agent master and stockholderagent tables
	 * If there is no agent, NULL is returned
	 *
	 * @param companyCode
	 * @param agentCode
	 * @return
	 * @throws SystemException
	 */
	private String findStockHolderForCustomer(String companyCode,String customerCode)
		throws SystemException{
		log.log(Log.FINE,"-----findStockHolderForCustomer CONTROLLER------");
    	return StockHolder.findStockHolderForCustomer(companyCode,customerCode);

	}
	/**
	 * @author A-3155
	 * @param stockAllocationVO
	 * @param status
	 * @param
	 * @throws SystemException 
	 */
	private void createUtilisationHistory(StockAllocationVO stockAllocationVO,
			String status) throws SystemException {


		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		log.log(Log.FINE, " Inside Create History for Utilisation"); 
		Collection<RangeVO> ranges = stockAllocationVO.getRanges();   
		StockRangeUtilisationVO stockRangeUtilisationVO = new StockRangeUtilisationVO();
		stockRangeUtilisationVO.setCompanyCode(stockAllocationVO
				.getCompanyCode());
		stockRangeUtilisationVO.setDocumentType(stockAllocationVO
				.getDocumentType());
		stockRangeUtilisationVO.setDocumentSubType(stockAllocationVO
				.getDocumentSubType());
		stockRangeUtilisationVO.setStockHolderCode(stockAllocationVO
					.getStockHolderCode());
		if(stockAllocationVO.getStockHolderCode()==null 
				|| stockAllocationVO.getStockHolderCode().trim().length()==0) {
			stockRangeUtilisationVO.setStockHolderCode(stockAllocationVO.getStockControlFor());
		}  
		stockRangeUtilisationVO.setAirlineIdentifier(stockAllocationVO  
				.getAirlineIdentifier());

		stockRangeUtilisationVO.setLastUpdateTime(stockAllocationVO
				.getLastUpdateTime());
		if(stockAllocationVO.getLastUpdateUser()!=null){
			stockRangeUtilisationVO.setLastUpdateUser(stockAllocationVO.getLastUpdateUser());
		}else{
		stockRangeUtilisationVO.setLastUpdateUser(logonAttributes.getUserId());
		}
		if (status.equalsIgnoreCase(StockAllocationVO.MODE_USED)) {
			stockRangeUtilisationVO.setStatus(StockAllocationVO.MODE_USED);
		}
		if (status.equalsIgnoreCase(StockAllocationVO.MODE_REOPENED)) {
			stockRangeUtilisationVO.setStatus(StockAllocationVO.MODE_REOPENED);
			GMTDate transactionTimeUTC = stockAllocationVO.getExecutionDate().toGMTDate();
			stockRangeUtilisationVO.setTransactionDate(transactionTimeUTC);
		}
		if (ranges != null) {
			for (RangeVO rangeVO : ranges) {
				stockRangeUtilisationVO.setStartRange(rangeVO.getStartRange());
				stockRangeUtilisationVO.setEndRange(rangeVO.getEndRange());
				if (rangeVO.getAsciiStartRange() == 0) {
					rangeVO.setAsciiStartRange(Range.findLong(rangeVO
						.getStartRange()));

			}
				if (rangeVO.getAsciiEndRange() == 0) {
					rangeVO.setAsciiEndRange(Range.findLong(rangeVO
						.getEndRange()));
			}

				stockRangeUtilisationVO.setAsciiStartRange(rangeVO
					.getAsciiStartRange());
				stockRangeUtilisationVO.setAsciiEndRange(rangeVO
					.getAsciiEndRange());

				long n = ((rangeVO.getAsciiEndRange()) - (rangeVO
					.getAsciiStartRange())) + 1;
				rangeVO.setNumberOfDocuments(n);

				stockRangeUtilisationVO.setNumberOfDocuments(rangeVO
					.getNumberOfDocuments());

				if (rangeVO.isManual()) {
					stockRangeUtilisationVO
							.setRangeType(StockAllocationVO.MODE_MANUAL);
				} else {
					stockRangeUtilisationVO
							.setRangeType(StockAllocationVO.MODE_NEUTRAL);
			   }
				//added only for used status, transaction date taken from stkrngutl lstupdtimutc value
				// we are appending current time,bcoz time in stkrngutl is GMT..
				if(status.equalsIgnoreCase(StockAllocationVO.MODE_USED)){

					LocalDate currentTime = new LocalDate(
							logonAttributes.getStationCode(),Location.ARP,true);
					LocalDate dateToSet = new LocalDate(
							logonAttributes.getStationCode(),Location.ARP,true);
					LocalDate exectuionDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,true);
					if(rangeVO.getLastUpdateTime()!=null){
						exectuionDate =rangeVO.getLastUpdateTime();
					}

					String dateVal = exectuionDate.toDisplayDateOnlyFormat();
					String timeVal = currentTime.toDisplayTimeOnlyFormat();

					dateToSet.setDateAndTime(new StringBuilder (dateVal).append(" ").append(timeVal).toString());
					//part of bug 26660 GMT time kept for all transaction,so directly setting GMT time..
					stockRangeUtilisationVO.setTransactionDate(exectuionDate.toGMTDate());
				}
				log.log(Log.FINE, "  Create History completed!!!!!!!!!!",
						stockRangeUtilisationVO);
				if(stockAllocationVO.getLastUpdateUser()!=null){
					stockRangeUtilisationVO.setLastUpdateUser(stockAllocationVO.getLastUpdateUser());
				}else{
					stockRangeUtilisationVO.setLastUpdateUser(logonAttributes.getUserId());
				 }
				if(stockRangeUtilisationVO.getTransactionDate()==null) {
					stockRangeUtilisationVO.setTransactionDate(new LocalDate(
							LocalDate.NO_STATION, Location.NONE, true).toGMTDate());
				}  
				new StockRangeUtilisationHistory(stockRangeUtilisationVO);

			}
		} 


	}
	
	/**
	 * @author A-3155
	 * @param stockAllocationVO
	 * @param status
	 * @param
	 * @throws SystemException
	 */

	public void createHistory(StockAllocationVO stockAllocationVO,
			String status) throws SystemException {

		log.log(Log.FINE, " Inside Create History");
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		Collection<RangeVO> ranges = stockAllocationVO.getRanges();
		Collection<RangeVO>	rangesDelete = new ArrayList<RangeVO>();
		Collection<RangeVO> newRanges = new ArrayList<RangeVO>();
		Collection<RangeVO> rangeFromUser = stockAllocationVO.getRanges();
		Collection<RangeVO> rangesToDelete = new HashSet<RangeVO>(); 
		Collection<RangeVO> mergedRanges = new ArrayList<RangeVO>();

		StockRangeHistoryVO stockRangeHistoryVO = new StockRangeHistoryVO(); 
		// StockAllocationVO stockAllocationVO = new StockAllocationVO();

		stockRangeHistoryVO.setCompanyCode(stockAllocationVO.getCompanyCode());
		stockRangeHistoryVO
				.setDocumentType(stockAllocationVO.getDocumentType());
		stockRangeHistoryVO.setDocumentSubType(stockAllocationVO
				.getDocumentSubType());
		stockRangeHistoryVO.setAirlineIdentifier(stockAllocationVO
				.getAirlineIdentifier());
		stockRangeHistoryVO.setLastUpdateTime(stockAllocationVO
				.getLastUpdateTime());
		//Added by A-2881 for ICRD-3082
		stockRangeHistoryVO.setUserId(logonAttributes.getUserId());
		stockRangeHistoryVO.setRemarks(stockAllocationVO.getRemarks());
        //Added by A-2881 for ICRD-3082
		if(stockAllocationVO.getLastUpdateUser()!=null){
			stockRangeHistoryVO.setLastUpdateUser(stockAllocationVO.getLastUpdateUser());
		}else{
		stockRangeHistoryVO.setLastUpdateUser(logonAttributes.getUserId());
		 }
		if(YES.equals(stockAllocationVO.getAutoAllocated())){
			stockRangeHistoryVO.setAutoAllocated(YES);
		}else{
			stockRangeHistoryVO.setAutoAllocated(NO);
		}
		stockRangeHistoryVO.setTransactionDate(new LocalDate(
				LocalDate.NO_STATION, Location.NONE, true).toGMTDate());
		if (stockAllocationVO.isManual()) {
			stockRangeHistoryVO.setRangeType("M");
		} else {
			stockRangeHistoryVO.setRangeType("N");
		}
		if (status.equalsIgnoreCase(StockAllocationVO.MODE_CREATE)) { 
			// for allocation
			stockRangeHistoryVO.setStatus(StockAllocationVO.MODE_CREATE);
			stockRangeHistoryVO.setFromStockHolderCode(stockAllocationVO
					.getStockHolderCode());

		}
		else if (status.equalsIgnoreCase(StockAllocationVO.MODE_ALLOCATE)) {
			// for allocation
			stockRangeHistoryVO.setStatus(StockAllocationVO.MODE_ALLOCATE);
			stockRangeHistoryVO.setFromStockHolderCode(stockAllocationVO
					.getStockControlFor()); 
			stockRangeHistoryVO.setToStockHolderCode(stockAllocationVO
					.getStockHolderCode());
		} else if (status.equalsIgnoreCase(StockAllocationVO.MODE_BLACKLIST)) {
			// for blacklisting
			stockRangeHistoryVO.setStatus(StockAllocationVO.MODE_BLACKLIST);
			stockRangeHistoryVO.setFromStockHolderCode(stockAllocationVO
					.getStockHolderCode());
		} else if (status.equalsIgnoreCase(StockAllocationVO.MODE_RETURN)) {
			stockRangeHistoryVO.setStatus(StockAllocationVO.MODE_RETURN);
			stockRangeHistoryVO.setToStockHolderCode(stockAllocationVO
					.getStockHolderCode());
			stockRangeHistoryVO.setFromStockHolderCode(stockAllocationVO
					.getStockControlFor());
		} else if (status.equalsIgnoreCase(StockAllocationVO.MODE_USED)) {
			// for used
			stockRangeHistoryVO.setStatus(StockAllocationVO.MODE_USED);
			stockRangeHistoryVO.setFromStockHolderCode(stockAllocationVO
					.getStockControlFor());
			if(stockAllocationVO
					.getStockControlFor()==null) {
				stockRangeHistoryVO.setFromStockHolderCode(stockAllocationVO
						.getStockHolderCode());
			}	  
		}else if (status.equalsIgnoreCase(StockAllocationVO.MODE_REVOKE)) {
			// for revoke
			stockRangeHistoryVO.setStatus(StockAllocationVO.MODE_REVOKE);
			stockRangeHistoryVO.setFromStockHolderCode(stockAllocationVO
					.getStockHolderCode());
		}else if (status.equalsIgnoreCase(StockAllocationVO.MODE_TRANSFER)) {
			// for transfer
			stockRangeHistoryVO.setStatus(StockAllocationVO.MODE_TRANSFER);
			stockRangeHistoryVO.setToStockHolderCode(stockAllocationVO
					.getStockHolderCode());
			stockRangeHistoryVO.setFromStockHolderCode(stockAllocationVO
					.getStockControlFor());
		}
		//Added by A-2882 For Bug 90902 starts
		else if (status.equalsIgnoreCase(StockAllocationVO.MODE_REOPENED)) {
			// for revoke
			stockRangeHistoryVO.setStatus(StockAllocationVO.MODE_REOPENED);
			if(stockAllocationVO.getStockControlFor()!=null&& stockAllocationVO.getStockControlFor().trim().length()>0){
			String stockHolderCode=findStockHolderCodeForAgent(stockAllocationVO.getCompanyCode(),
					stockAllocationVO.getStockControlFor());
			
			stockRangeHistoryVO.setFromStockHolderCode(stockHolderCode);
			stockRangeHistoryVO.setToStockHolderCode(stockHolderCode);
			}
		}
		//Added by A-2882 For Bug 90902 ends
		//Added by A-2881   for icrd-3024 starts
		else if (StockAllocationVO.MODE_ALLOCATE_TRANSIT.equals(status)) {
			stockRangeHistoryVO
					.setStatus(StockAllocationVO.MODE_ALLOCATE_TRANSIT);
			stockRangeHistoryVO.setFromStockHolderCode(stockAllocationVO
					.getStockControlFor());
			stockRangeHistoryVO.setToStockHolderCode(stockAllocationVO
					.getStockHolderCode());
		}
		else if (StockAllocationVO.MODE_TRANSFER_TRANSIT.equals(status)) {
			stockRangeHistoryVO
					.setStatus(StockAllocationVO.MODE_TRANSFER_TRANSIT);
			stockRangeHistoryVO.setFromStockHolderCode(stockAllocationVO
					.getStockControlFor());
			stockRangeHistoryVO.setToStockHolderCode(stockAllocationVO
					.getStockHolderCode());
		}
		else if (StockAllocationVO.MODE_RETURN_TRANSIT.equals(status)) {
			stockRangeHistoryVO
					.setStatus(StockAllocationVO.MODE_RETURN_TRANSIT);
			stockRangeHistoryVO.setFromStockHolderCode(stockAllocationVO
					.getStockControlFor());
			stockRangeHistoryVO.setToStockHolderCode(stockAllocationVO
					.getStockHolderCode());
		}
		//Added by A-2881		for icrd-3024 ends
		//Added by A-2881 for ICRD-3082 -starts
		else if (StockAllocationVO.MODE_VOID.equals(status)) {
			//Void transaction - From Stock holder will be stockholder and 
			//To stock holder will be null
			stockRangeHistoryVO
					.setStatus(StockAllocationVO.MODE_VOID);
			stockRangeHistoryVO.setFromStockHolderCode(stockAllocationVO
					.getStockHolderCode());
			stockRangeHistoryVO.setVoidingCharge(stockAllocationVO.getVoidingCharge());
			stockRangeHistoryVO.setCurrencyCode(stockAllocationVO.getCurrencyCode());
		}
		 //Added by A-2881 for ICRD-3082-Ends

		if (rangeFromUser != null
				&& status.equalsIgnoreCase(StockAllocationVO.MODE_USED)) {

			newRanges.addAll(rangeFromUser);

			log.log(Log.FINE, "newRanges !!!!!!!!!!", newRanges);
			for (RangeVO res : newRanges) {
				res.setCompanyCode(stockAllocationVO.getCompanyCode());
				res.setAirlineIdentifier(stockAllocationVO
						.getAirlineIdentifier());
				res.setDocumentType(stockAllocationVO.getDocumentType());
				res.setDocumentSubType(stockAllocationVO.getDocumentSubType());
				res.setStockHolderCode(stockAllocationVO.getStockHolderCode());
				//added for bug 31778,to avoid Nullpointer exception when Range 0000000 Used.
				if(res.getStartRange()==null){
					res.setAsciiStartRange(-1);
				}else{
				res.setAsciiStartRange(Range.findLong(res.getStartRange()));
				}
				if(res.getEndRange()==null){
					res.setAsciiEndRange(-1);
				}else{
				res.setAsciiEndRange(Range.findLong(res.getEndRange()));
				}
				log.log(Log.FINE,
						"vo sent for entity for merge: res !!!!!!!!!!", res);
				if(res.getStockHolderCode()==null) {
					res.setStockHolderCode(stockAllocationVO.getStockControlFor());
				}    
				rangesDelete = StockRangeHistory.findUsedRangesForMerge(res,
						StockAllocationVO.MODE_USED);
				log.log(Log.FINE, "rangesToDelete !!!!!!!!!!", rangesToDelete);
				if(rangesDelete != null && rangesDelete.size()>0){
				rangesToDelete.addAll(rangesDelete);
			}
			}

			if (rangesToDelete != null && rangesToDelete.size() > 0) {

				newRanges.addAll(rangesToDelete);
				log.log(Log.FINE, "rangesToDelete is not null!!!!!!!!!!",
						newRanges);
				for (RangeVO rangeDel : rangesToDelete) {
					stockRangeHistoryVO.setCompanyCode(rangeDel
							.getCompanyCode());
					stockRangeHistoryVO.setAirlineIdentifier(rangeDel
							.getAirlineIdentifier());
					stockRangeHistoryVO.setSerialNumber(rangeDel
							.getSequenceNumber());
					stockRangeHistoryVO.setDocumentType(rangeDel
							.getDocumentType());
					stockRangeHistoryVO.setDocumentSubType(rangeDel
							.getDocumentSubType());
					stockRangeHistoryVO.setFromStockHolderCode(rangeDel
							.getStockHolderCode());
					stockRangeHistoryVO.setStartRange(rangeDel.getStartRange());
					stockRangeHistoryVO.setEndRange(rangeDel.getEndRange());
					stockRangeHistoryVO.setAsciiStartRange(rangeDel
							.getAsciiStartRange());
					stockRangeHistoryVO.setAsciiEndRange(rangeDel
							.getAsciiEndRange());

					log
							.log(
									Log.FINE,
									"stockRangeHistoryVO sent for find m/t in Entity!!!!!!!!!!",
									stockRangeHistoryVO);
					StockRangeHistory stockRangeHistory = null;
					try {
						stockRangeHistory = StockRangeHistory
								.find(stockRangeHistoryVO);
					} catch (FinderException finderException) {
						throw new SystemException(finderException.getErrorCode(),
								finderException);
					}
					if(stockRangeHistory!=null){
						stockRangeHistory.remove();
					}
				}
			}

			mergedRanges = mergeRanges(stockAllocationVO.getCompanyCode(),
					stockAllocationVO.getDocumentType(), stockAllocationVO
							.getDocumentSubType(), newRanges);

			log.log(Log.FINE, "mergedRanges !!!!!!!!!!", mergedRanges);
			if (mergedRanges != null && mergedRanges.size() > 0) {

				for (RangeVO mergeRange : mergedRanges) {   

					stockRangeHistoryVO.setStartRange(mergeRange
							.getStartRange());
					stockRangeHistoryVO.setEndRange(mergeRange.getEndRange());

					if (mergeRange.getAsciiStartRange() == 0) {
						mergeRange.setAsciiStartRange(Range.findLong(mergeRange
								.getStartRange()));
					}
					if (mergeRange.getAsciiEndRange() == 0) {
						mergeRange.setAsciiEndRange(Range.findLong(mergeRange
								.getEndRange()));
					}
					stockRangeHistoryVO.setAsciiStartRange(mergeRange
							.getAsciiStartRange());
					stockRangeHistoryVO.setAsciiEndRange(mergeRange
							.getAsciiEndRange());
					long n = ((mergeRange.getAsciiEndRange()) - (mergeRange
							.getAsciiStartRange())) + 1;
					mergeRange.setNumberOfDocuments(n);

					stockRangeHistoryVO.setNumberOfDocuments(mergeRange
							.getNumberOfDocuments());

					log
							.log(
									Log.FINE,
									"  Create History for Utilisation completed!!!!!!!!!!",
									stockRangeHistoryVO);
					new StockRangeHistory(stockRangeHistoryVO);

				}
				  
			}
		} else {
		if(ranges!=null){
			for(RangeVO rangeVO :ranges){
				stockRangeHistoryVO.setStartRange(rangeVO.getStartRange());
				stockRangeHistoryVO.setEndRange(rangeVO.getEndRange());
					if(rangeVO.getAsciiStartRange()==0){
						rangeVO.setAsciiStartRange(Range.findLong(rangeVO.getStartRange()));

					}
					if(rangeVO.getAsciiEndRange()==0){
						rangeVO.setAsciiEndRange(Range.findLong(rangeVO.getEndRange()));
					}
			    stockRangeHistoryVO.setAsciiStartRange(rangeVO.getAsciiStartRange());
				stockRangeHistoryVO.setAsciiEndRange(rangeVO.getAsciiEndRange());

				long n=((rangeVO.getAsciiEndRange())-(rangeVO.getAsciiStartRange())) +1;
				rangeVO.setNumberOfDocuments(n);

				stockRangeHistoryVO.setNumberOfDocuments(rangeVO.getNumberOfDocuments());

						if(stockRangeHistoryVO.getFromStockHolderCode()!=null){
			 	log.log(Log.FINE, "  Create History completed!!!!!!!!!!",
						stockRangeHistoryVO);
				new StockRangeHistory(stockRangeHistoryVO);
						     } 

				} 
			}
		}
	}	
	/**
	 * @author a-2870
	 * @param reportSpec
	 * @return
	 */
	public Map<String, Object> findStockRangeHistoryReport(ReportSpec reportSpec)
			throws SystemException {
		StockRangeFilterVO stockRangeFilterVO = (StockRangeFilterVO) reportSpec
				.getFilterValues().get(0);
		log.log(Log.INFO, " stockRangeFilterVO in StockCOntroller --> ",
				stockRangeFilterVO);
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		String companyCode = logonAttributes.getCompanyCode();
		Collection<String> fieldTypes = new ArrayList<String>();
		Collection<OneTimeVO> stockstatus = null;
		Collection<OneTimeVO> awbtype = null;
		Collection<OneTimeVO> stockutilizationstatus = null;
		SharedDefaultsProxy sharedDefaultsProxy = new SharedDefaultsProxy();
		fieldTypes.add("stockcontrol.defaults.stockstatus");
		fieldTypes.add("stockcontrol.defaults.awbtype");
		fieldTypes.add("stockcontrol.defaults.stockutilizationstatus");
			try {
				oneTimes =  sharedDefaultsProxy.findOneTimeValues( 
						companyCode, fieldTypes);  
			} catch (ProxyException e) {
				for (ErrorVO errorVo : e.getErrors()) {
					throw new SystemException(errorVo.getErrorCode());
				}

			}
		if (oneTimes != null) {
			stockstatus = oneTimes.get("stockcontrol.defaults.stockstatus");
			awbtype = oneTimes.get("stockcontrol.defaults.awbtype");
			stockutilizationstatus = oneTimes
					.get("stockcontrol.defaults.stockutilizationstatus");
		}
		Collection<StockRangeHistoryVO> historyVos = null;
		historyVos = findStockRangeHistory(stockRangeFilterVO);
		log.log(Log.INFO, " historyVos --> ", historyVos);
		//Added by a-4562 for icrd-3082 starts
		if (historyVos == null || historyVos.size() == 0) {
			throw new SystemException(STOCK_NOT_FOUND);

		}     
		else {
			for (StockRangeHistoryVO historyVO : historyVos) {
				for (OneTimeVO stockUtilStauts : stockutilizationstatus) {
					if (stockUtilStauts.getFieldValue().equalsIgnoreCase(
							historyVO.getStatus())) {
						historyVO.setStatus(stockUtilStauts
								.getFieldDescription());
						break;
					}
				} 
				for (OneTimeVO awbtypeVO : awbtype) {
					if (awbtypeVO.getFieldValue().equalsIgnoreCase(
							historyVO.getRangeType())) {
						historyVO.setRangeType(awbtypeVO.getFieldDescription());
						break;
					}
				}
			}
		}
		reportSpec.setData(historyVos);
		reportSpec.addParameter(stockRangeFilterVO);
		
		//Added by a-4562 for icrd-3082 ends
	

		return ReportAgent.generateReport(reportSpec);
	}
	/**
	 * @author A-3184
	 * @param stockRangeHistoryVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<StockRangeHistoryVO> findStockRangeHistory(
			StockRangeFilterVO stockRangeFilterVO) throws SystemException {

		StockDepleteFilterVO stockDepleteFilterVO = new StockDepleteFilterVO();    
		stockDepleteFilterVO.setCompanyCode(stockRangeFilterVO.getCompanyCode());
		stockDepleteFilterVO.setAirlineId(stockRangeFilterVO.getAirlineIdentifier());
		//autoDepleteStock(stockDepleteFilterVO);

		//Added to show AWB Range popup details with login station local time.
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		stockRangeFilterVO.setStation(logonAttributes.getStationCode());
		return StockRangeHistory.findStockRangeHistory(stockRangeFilterVO);
	}
		
	/**
	 * @author A-4443
	 * @param stockRangeHistoryVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException 
	 */
	public Collection<TransitStockVO> findTransitStocks(
			StockRequestFilterVO stockRequestFilterVO) throws SystemException {

		try {
			return TransitStock.findTransitStocks(stockRequestFilterVO);
		} catch (PersistenceException e) {
			
			log.log(Log.SEVERE, "----->>>FinderException");
			return null;
		}
		
	}	
	
	
	/**
	 * @author A-4443
	 * @param blacklistStockVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException 
	 */
	public Collection<TransitStockVO> findBlackListRangesFromTransit(
			BlacklistStockVO blacklistStockVO) throws SystemException {

		try {
			return TransitStock.findBlackListRangesFromTransit(blacklistStockVO);
		} catch (PersistenceException e) {
			
			log.log(Log.SEVERE, "----->>>persistenceException");
			return null;
		}
		
	}	
	
	/**
	 * @author A-4443
	 * @param TransitStockVO,Collection<RangeVO>
	 * @return Collection<RangeVO>
	 */
	private Collection<RangeVO> splitRanges(Collection<TransitStockVO> transitStockVOs,Collection<RangeVO> rangeVOsForAllocation) {
		log.entering("StockController", "splitRanges");
		DocumentTypeProxy documentTypeProxy = new DocumentTypeProxy();                 
		Collection<SharedRangeVO> splitedRange = new ArrayList<SharedRangeVO>();
		Collection<SharedRangeVO> originalSharedRanges = new ArrayList<SharedRangeVO>();
		Collection<SharedRangeVO> availableSharedRanges = new ArrayList<SharedRangeVO>();
		Collection<RangeVO> splitRangeResult = new ArrayList<RangeVO>();

		SharedRangeVO sharedVo = new SharedRangeVO();
		for(TransitStockVO transitStockVO:transitStockVOs){                 
		sharedVo = new SharedRangeVO();
		//This will contain the transit start and end ranges(TransitStockVO)
		sharedVo.setFromrange(transitStockVO.getMissingStartRange());
		// sharedVo.setRangeDate(null);
		sharedVo.setToRange(transitStockVO.getMissingEndRange());
		originalSharedRanges.add(sharedVo);           
		}
		log.log(Log.FINE, "original range----->", originalSharedRanges);
		for(RangeVO rangevo:rangeVOsForAllocation){   
		sharedVo = new SharedRangeVO();
		if (rangevo.getStartRange().length() < 7) {              
				StringBuilder sb = new StringBuilder("");
				for (int i = rangevo.getStartRange().length(); i < 7; i++) {
					sb.append("0");  
				}
				sharedVo.setFromrange(sb.append(rangevo.getStartRange())
						.toString());

			} else {
				sharedVo.setFromrange(rangevo.getStartRange());
			}
		if (rangevo.getEndRange().length() < 7) {
				StringBuilder sb = new StringBuilder("");
				for (int i = rangevo.getEndRange().length(); i < 7; i++) {
					sb.append("0");
				}
				sharedVo
						.setToRange(sb.append(rangevo.getEndRange()).toString());

			} else {
				sharedVo.setToRange(rangevo.getEndRange());
			}
		
		availableSharedRanges.add(sharedVo);
		}
		log.log(Log.FINE, "Available range------>", availableSharedRanges);
			//try {
			try {
				splitedRange = documentTypeProxy.splitRanges(
						originalSharedRanges, availableSharedRanges);
			} catch (ProxyException e) {
				
				log.log(Log.SEVERE, "----->>>ProxyException");
			} catch (SystemException e) {
				
				log.log(Log.SEVERE, "----->>>SystemException");
			}
		log.log(Log.FINE, "Inside splitRanges of stock splitedRange------>",
				splitedRange);
		if (splitedRange != null && splitedRange.size() != 0) {
			for (SharedRangeVO sharedVO : splitedRange) {
				RangeVO rangeVO = new RangeVO();
				rangeVO.setStartRange(sharedVO.getFromrange());
				rangeVO.setStockAcceptanceDate(sharedVO.getRangeDate());
				rangeVO.setEndRange(sharedVO.getToRange());
				splitRangeResult.add(rangeVO);

			}
		}

		
		log.log(Log.FINE, "Final Confirmed Ranges -->", splitRangeResult);
		log.exiting("StockController", "splitRanges");
		return splitRangeResult;
	}
	
	/**
	 * 
	 * @param blacklistVO
	 * @throws SystemException
	 */
	public void voidStock(BlacklistStockVO blacklistStockVO)
			throws SystemException, StockControlDefaultsBusinessException,
			InvalidStockHolderException, StockNotFoundException,
			BlacklistedRangeExistsException {
		// Assuming
		// -CMPCOD,STKHLDCOD,DOCTYP,DOCSUBTYP,STARNG,ENDRNG,REMARKS,AGTCOD will
		// be
		// present in BlacklistStockVO
		log.entering("StockController", "voidStock");
		
		
			StockFilterVO stockFilterVO = new StockFilterVO();
			stockFilterVO.setCompanyCode(blacklistStockVO.getCompanyCode());
			stockFilterVO.setAirlineIdentifier(blacklistStockVO.getAirlineIdentifier());
			stockFilterVO.setDocumentType(blacklistStockVO.getDocumentType());
			stockFilterVO.setDocumentSubType(blacklistStockVO.getDocumentSubType());
			stockFilterVO.setStockHolderCode(blacklistStockVO.getStockHolderCode());
			stockFilterVO.setRangeFrom(blacklistStockVO.getRangeFrom());
			stockFilterVO.setRangeTo(blacklistStockVO.getRangeTo());
			
				String masterDocumentNumber = checkStockUtilisationForRange(stockFilterVO);
				if(masterDocumentNumber!=null){
					throw new StockControlDefaultsBusinessException(StockControlDefaultsBusinessException.UTILISATION_EXISTS);
				}
			
		
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		StockVO stockVO = null;
		TransitStockVO transitVO = null;
		Collection<StockVO> stockVOs = null;
		StockAllocationVO stockAllocationVo = null;
		MissingStockVO missingStockVO = null;
		List<MissingStockVO> missingStocks = null;
		Collection<RangeVO> finalMissingRanges = null;
		Collection<TransitStockVO> finalTransitRanges = null;
		Collection<RangeVO> rangeForHistory=new ArrayList<RangeVO>(1);

		// Validate document in stock range/transit stock range
		blacklistStockVO = validateStockForVoiding(blacklistStockVO);

		// Either StockVO or TransitStockVO will be present
		stockVO = blacklistStockVO.getStockVO();
		transitVO = blacklistStockVO.getTransitStockVO();

		// create blacklist entry Entering as Depleted with D status
		blacklistStockVO.setStationCode(logonAttributes.getStationCode());
		LocalDate blackListDate = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, false);
		String blackListDateStr = blackListDate
				.toDisplayFormat(CALENDAR_DATE_FORMAT);
		blacklistStockVO.setBlacklistDate(blackListDate
				.setDate(blackListDateStr));
		blacklistStockVO.setStatus("D");
		blacklistStockVO.setActionCode("V");
		new BlackListStock(blacklistStockVO);

		if (stockVO != null) {
			// Document to be voided is in stock range
			stockVOs = new ArrayList<StockVO>(1);
			stockVOs.add(stockVO);
			Collection<StockVO> blacklistedStocks = rearrangeRanges(stockVOs,
					blacklistStockVO);
			log.log(Log.FINE, "after rearranging in void stock method",
					blacklistedStocks);
			for (StockVO stockVo : blacklistedStocks) {
				StockHolder stockHolder = StockHolder.find(stockVo
						.getCompanyCode(), stockVo.getStockHolderCode());
				stockAllocationVo = new StockAllocationVO();
				stockAllocationVo.setCompanyCode(stockVo.getCompanyCode());
				stockAllocationVo.setStockHolderCode(stockVo
						.getStockHolderCode());
				stockAllocationVo.setDocumentType(blacklistStockVO
						.getDocumentType());
				stockAllocationVo.setDocumentSubType(blacklistStockVO
						.getDocumentSubType());
				stockAllocationVo.setAirlineIdentifier(blacklistStockVO
						.getAirlineIdentifier());
				stockAllocationVo
						.setTransferMode(StockAllocationVO.MODE_RETURN);
				Collection<RangeVO> ranges = new ArrayList<RangeVO>();
				ArrayList<RangeVO> rangesArr = null;
				rangesArr = (ArrayList<RangeVO>) stockVo.getRanges();
				RangeVO rangeVo = new RangeVO();
				rangeVo.setStartRange(rangesArr.get(0).getStartRange());
				rangeVo.setEndRange(rangesArr.get(0).getEndRange());
				rangeVo.setBlackList(true);
				ranges.add(rangeVo);
				stockAllocationVo.setRanges(ranges);
				stockAllocationVo.setRemarks(blacklistStockVO.getRemarks());
				log.log(Log.FINE, "Ranges for deplete due void -->",
						stockAllocationVo);
				stockHolder.deplete(stockAllocationVo, true, false);
				stockHolder.setLastUpdateTime(stockHolder.getLastUpdateTime());
				stockHolder.setLastUpdateUser(stockHolder.getLastUpdateUser());
			}

		} else if (transitVO != null) {
			// Document to be voided is in transit range
			missingStockVO = new MissingStockVO();
			missingStocks = new ArrayList<MissingStockVO>(1);
			finalTransitRanges = new ArrayList<TransitStockVO>();

			missingStockVO.setAsciiMissingEndRange(findLong(blacklistStockVO
					.getRangeFrom()));
			missingStockVO.setAsciiMissingStartRange(findLong(blacklistStockVO
					.getRangeTo()));

			missingStocks.add(missingStockVO);

			transitVO.setMissingRanges(missingStocks);
			finalMissingRanges = getConfirmedStock(transitVO);

			if (finalMissingRanges != null) {
				for (RangeVO rangeVO : finalMissingRanges) {
					TransitStockVO missingTransitVO = new TransitStockVO(
							transitVO);
					missingTransitVO
							.setConfirmStatus(TransitStockVO.TRANSIT_STATUS_MISSING);
					missingTransitVO.setAsciiMissingEndRange(findLong(rangeVO
							.getEndRange()));
					missingTransitVO.setAsciiMissingStartRange(findLong(rangeVO
							.getStartRange()));
					missingTransitVO.setMissingStartRange(rangeVO
							.getStartRange());
					missingTransitVO.setMissingEndRange(rangeVO.getEndRange());
					missingTransitVO.setMissingNumberOfDocs(missingTransitVO
							.getAsciiMissingEndRange()
							- missingTransitVO.getAsciiMissingStartRange() + 1);
					missingTransitVO.setTxnDate(new LocalDate(logonAttributes
							.getAirportCode(), Location.ARP, true));
					missingTransitVO
							.setOperationFlag(TransitStockVO.OPERATION_FLAG_INSERT);

					finalTransitRanges.add(missingTransitVO);

				}
			}
			transitVO.setOperationFlag(TransitStockVO.OPERATION_FLAG_DELETE);
			finalTransitRanges.add(transitVO);
			log.log(Log.FINE, "Final transit ranges -->", finalTransitRanges);
			TransitStock.saveTransitStock(finalTransitRanges);

			//Added by A-2881 for ICRD-3082 - starts
			//StockAllocationVO for Voiding transaction history
			stockAllocationVo = new StockAllocationVO();
			stockAllocationVo.setCompanyCode(transitVO.getCompanyCode());
			stockAllocationVo.setStockHolderCode(transitVO
					.getStockHolderCode());
			stockAllocationVo.setDocumentType(transitVO
					.getDocumentType());
			stockAllocationVo.setDocumentSubType(transitVO
					.getDocumentSubType());
			stockAllocationVo.setAirlineIdentifier(transitVO
					.getAirlineIdentifier());       
			
			RangeVO rangeVO=new RangeVO(); 
			rangeVO.setStartRange(blacklistStockVO.getRangeFrom());
			rangeVO.setEndRange(blacklistStockVO.getRangeTo());
			rangeForHistory.add(rangeVO);
			stockAllocationVo.setRanges(rangeForHistory);  
           //Added by A-2881 for ICRD-3082 - ends
			
		}
		//Creating transaction history for void
        //Added by A-2881 for ICRD-3082 - starts
		stockAllocationVo.setTransactionCode(StockAllocationVO.MODE_VOID);
		stockAllocationVo.setVoidingCharge(blacklistStockVO.getVoidingCharge());
		stockAllocationVo.setCurrencyCode(blacklistStockVO.getCurrencyCode());
		stockAllocationVo.setRemarks(blacklistStockVO.getRemarks());
		createHistory(stockAllocationVo,StockAllocationVO.MODE_VOID);        
        //Added by A-2881 for ICRD-3082 - ends
		log.exiting("StockController", "voidStock");

	}
	
	/**
	 * For voiding from and to range will be same. This method validates whether
	 * the document is in stock range. if in stock range then corresponding
	 * stockVO is set to blacklistStockVO and returned. If it is not in stock
	 * range then check whether available in transit range with status as
	 * missing.If in transit range then set the corresponding TransitStockVO to
	 * blacklistStockVO and return. If not in both ranges
	 * stockcontroldefaultsbusinessexception is thrown.
	 * 
	 * @param blacklistStockVO
	 * @throws StockControlDefaultsBusinessException
	 * @throws SystemException
	 */
	public BlacklistStockVO validateStockForVoiding(
			BlacklistStockVO blacklistStockVO)
			throws StockControlDefaultsBusinessException, SystemException {
		log.entering("StockController", "validateStockForVoiding");
		Collection<StockVO> blacklistStocks = Stock
				.findBlacklistRanges(blacklistStockVO);
		StockVO stockVO = null;
		Collection<TransitStockVO> transitStockVOs = null;
		TransitStockVO transitStockVO = null;
		if (blacklistStocks != null && !blacklistStocks.isEmpty()) {
			// check whether the stockholder agent is same as agent from
			// blackliststockVo
			stockVO = blacklistStocks.iterator().next();
			blacklistStockVO.setStockVO(stockVO);
			
			//validating agent against stock
			StockAgentFilterVO stockAgentFilterVO = new StockAgentFilterVO();
			stockAgentFilterVO
					.setCompanyCode(blacklistStockVO.getCompanyCode());
			stockAgentFilterVO.setStockHolderCode(stockVO.getStockHolderCode());
			stockAgentFilterVO.setPageNumber(1);
			Page<StockAgentVO> stockagents = findStockAgentMappings(stockAgentFilterVO);
			for (StockAgentVO stockagent : stockagents) {
				blacklistStockVO.setAgentCode(stockagent.getAgentCode());
				break;
			}
			
		} else {
			// Then find in transit stock range with missing status
			// Queried from transit range
			transitStockVOs = findBlackListRangesFromTransit(blacklistStockVO);
			if (transitStockVOs == null || transitStockVOs.isEmpty()){
				throw new StockControlDefaultsBusinessException(
				"stockcontrol.defaults.documentnotinanystock");
			}
			else{
				transitStockVO = transitStockVOs.iterator().next();	
			}
				
              
			blacklistStockVO.setTransitStockVO(transitStockVO);
             //validating agent against stock
			StockAgentFilterVO stockAgentFilterVO = new StockAgentFilterVO();
			stockAgentFilterVO
					.setCompanyCode(transitStockVO.getCompanyCode());
			stockAgentFilterVO.setStockHolderCode(transitStockVO.getStockHolderCode());
			stockAgentFilterVO.setPageNumber(1);
			Page<StockAgentVO> stockagents = findStockAgentMappings(stockAgentFilterVO);
			for (StockAgentVO stockagent : stockagents) {
				blacklistStockVO.setAgentCode(stockagent.getAgentCode());
				break;
			}

		}
		log.exiting("StockController", "validateStockForVoiding");
		return blacklistStockVO;
	}
	
	/**
	 * @author A-2881
	 * @param stockRangeFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Page<StockRangeHistoryVO> findStockRangeHistoryForPage(
			StockRangeFilterVO stockRangeFilterVO) throws SystemException {
        log.entering("StockController", "findStockRangeHistoryForPage");
		StockDepleteFilterVO stockDepleteFilterVO = new StockDepleteFilterVO();    
		stockDepleteFilterVO.setCompanyCode(stockRangeFilterVO.getCompanyCode());
		stockDepleteFilterVO.setAirlineId(stockRangeFilterVO.getAirlineIdentifier());
		//autoDepleteStock(stockDepleteFilterVO);

		//Added to show AWB Range popup details with login station local time.
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		stockRangeFilterVO.setStation(logonAttributes.getStationCode());
		log.exiting("StockController", "findStockRangeHistoryForPage");
		return StockRangeHistory.findStockRangeHistoryForPage(stockRangeFilterVO);
		
	}
	 
	public void saveStockRangeHistory(
			Collection<StockRangeHistoryVO> stockRangeHistoryVOs) throws SystemException {
		for(StockRangeHistoryVO stockRangeHistoryVO: stockRangeHistoryVOs){
			stockRangeHistoryVO.setAsciiStartRange(Range.findLong(stockRangeHistoryVO.getStartRange()));
			stockRangeHistoryVO.setAsciiEndRange(Range.findLong(stockRangeHistoryVO.getStartRange()));
			new StockRangeHistory(stockRangeHistoryVO);
		}
		
	}	
	
	/** 
	 * @author A-4777
	 * @param reportSpec
	 * @return
	 * @throws SystemException
	 */
	public Map<String, Object> generateStockAllocationReport(ReportSpec reportSpec)
	throws SystemException {
		log.entering("StockController","generateStockAllocationReport");		
		return ReportAgent.generateReport(reportSpec);
	}
	/**
	 * @author A-4777
	 * @param documentFilterVO
	 * @return
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException
	 */
	private DocumentValidationVO validateInvoice(DocumentFilterVO documentFilterVO)
		throws SystemException, StockControlDefaultsBusinessException{		
		DocumentValidationVO documentValidationVO = new DocumentValidationVO();	
		String stockHolderCode = checkAwbExistsInAnyStock(documentFilterVO.getCompanyCode(),
				documentFilterVO.getAirlineIdentifier(),documentFilterVO.getDocumentType(),documentFilterVO.getDocumentNumber()
				,documentFilterVO.getDocumentNumber().length());
		documentValidationVO.setStockHolderCode(stockHolderCode);
		return documentValidationVO;		
		
	}
	public String checkStockUtilisationForRange(StockFilterVO stockFilterVO) throws SystemException{ 
		
		return StockHolder.checkStockUtilisationForRange(stockFilterVO);
		
	}		
    /**
	 * 
	 * @param documentFilterVO
	 * @return
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException
	 */
	public DocumentValidationVO validateDocumentForWS(
			DocumentFilterVO documentFilterVO)
		throws SystemException, StockControlDefaultsBusinessException {
		DocumentValidationVO documentValidationVO = null;
		AirlineValidationVO airlineValidationVO = null;
		try {
			airlineValidationVO = validateNumericCode(documentFilterVO.getCompanyCode(),documentFilterVO.getShipmentPrefix());
		} catch (StockControlDefaultsBusinessException e) {
			throw new SystemException(e.getErrors());
		}
		
		if(airlineValidationVO != null){
			documentFilterVO.setAirlineIdentifier(airlineValidationVO.getAirlineIdentifier());
			documentValidationVO = validateDocument(documentFilterVO);			
			if(documentValidationVO!=null &&
					DocumentValidationVO.DOC_TYP_AWB.equals(documentFilterVO.getDocumentType())){
				boolean isStockUtilized = Stock.constructDAO().checkAWBExistsInOperation(documentFilterVO.getCompanyCode(),
						documentFilterVO.getDocumentNumber(),airlineValidationVO.getAirlineIdentifier(),
						documentFilterVO.getDocumentType(),documentValidationVO.getDocumentSubType());
				if(isStockUtilized){
					throw new StockControlDefaultsBusinessException(StockControlDefaultsBusinessException.UTILISATION_EXISTS);
				}
			}
		}
		return documentValidationVO;	
	}
	
	/**
	 * Validate numeric code.
	 *
	 * @param companyCode the company code
	 * @param shipmentPrefix the shipment prefix
	 * @return the airline validation vo
	 * @throws SystemException the system exception
	 * @throws StockControlDefaultsBusinessException
	 * @author A-5265
     */
	public AirlineValidationVO validateNumericCode(String companyCode,
			String shipmentPrefix) throws SystemException, StockControlDefaultsBusinessException {
		try {
			return new SharedAirlineProxy().validateNumericCode(companyCode,shipmentPrefix);
		} catch (ProxyException e) {
			throw new StockControlDefaultsBusinessException(e);
		}
	}
	
	@Advice(name = "stockcontrol.defaults.stockutilisation" , phase=Phase.POST_INVOKE)
	public void triggerStockUtilisationAudit(StockAllocationVO stockAllocationVO){
		log.log(Log.FINE, "*******triggerStockUtilisationAudit");
	}
	/**
	 * Method for deleteBlackListedStock 
	 * @param documentNumber
	 * @param agentCode
	 * @return
	 * @throws SystemException
	 */
	public void deleteBlackListedStockFromUTL(ArrayList<String> masterDocNumbers)
	throws SystemException{
		log.log(Log.FINE,"-----deleteBlackListedStock CONTROLLER------");
		new StockRangeUtilisation().deleteBlackListedStockFromUTL(masterDocNumbers);
	}
	/**
	 * Method for findAutoPopulateSubtype 
	 * @param bookingVO
	 * @return String
	 * @throws SystemException
	 */
	public String findAutoPopulateSubtype(DocumentFilterVO documentFilterVO)
			throws SystemException, StockControlDefaultsBusinessException{
		String stockHolderCode = findStockHolderCodeForAgent(
				documentFilterVO.getCompanyCode(), documentFilterVO.getStockOwner());
		if(stockHolderCode == null || ("").equals(stockHolderCode.trim())){
			log.log(Log.WARNING, "NO-STOCKHOLDER-FOR-AGENT---", documentFilterVO.getStockOwner());
			throw new StockControlDefaultsBusinessException(
					StockControlDefaultsBusinessException.STOCKHOLDER_NOTFOUNDFOR_AGENT,
					new Object[]{documentFilterVO.getStockOwner()});
		}
		documentFilterVO.setStockHolderCode(stockHolderCode);
		return new Stock().findAutoPopulateSubtype(documentFilterVO);
	}
	
	public void deleteStock(Collection<RangeVO> rangeVOs)
			throws SystemException, StockControlDefaultsBusinessException{
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		for(RangeVO rangevo : rangeVOs){
			StockFilterVO stockFilterVO = new StockFilterVO();
			stockFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			stockFilterVO.setAirlineIdentifier(rangevo.getAirlineIdentifier());
			stockFilterVO.setDocumentType(rangevo.getDocumentType());
			stockFilterVO.setDocumentSubType(rangevo.getDocumentSubType());
			stockFilterVO.setStockHolderCode(rangevo.getStockHolderCode());
			stockFilterVO.setRangeFrom(rangevo.getStartRange());
			stockFilterVO.setRangeTo(rangevo.getEndRange());
			
				String masterDocumentNumber = checkStockUtilisationForRange(stockFilterVO);
				if(masterDocumentNumber!=null){
					throw new StockControlDefaultsBusinessException(StockControlDefaultsBusinessException.UTILISATION_EXISTS);
				} 
		}
		new StockHolder().deleteStock(rangeVOs);
	}
	
	/**
	 * @param stockFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Page<RangeVO> findAvailableRanges(StockFilterVO stockFilterVO) throws SystemException {
		log.entering("StockController", "----------findAvailableRanges()");
		return StockHolder.findAvailableRanges(stockFilterVO);
	}
	public int findTotalNoOfDocuments(StockFilterVO stockFilterVO) throws SystemException {
		log.entering("StockController", "----------findTotalNoOfDocuments()");
		return StockHolder.findTotalNoOfDocuments(stockFilterVO);
	}
	
	public String saveStockDetails(StockAllocationVO stockAllocationVO) throws SystemException {
		log.entering("StockController","saveStockDetails");		
		return Stock.saveStockDetails(stockAllocationVO);
	}
	public boolean isStockDetailsExists(DocumentFilterVO documentFilterVO) throws SystemException {
		log.entering("StockController","isStockDetailsExists");		
		return Stock.isStockDetailsExists(documentFilterVO);
	}

	/**
	 *
	 * @param documentFilterVO
	 * @return
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException
	 */
	public DocumentValidationVO validateDocumentStockForWS(
			DocumentFilterVO documentFilterVO)
			throws SystemException, StockControlDefaultsBusinessException {
		DocumentValidationVO documentValidationVO = null;
		AirlineValidationVO airlineValidationVO = null;
		try {
			airlineValidationVO = validateNumericCode(documentFilterVO.getCompanyCode(),documentFilterVO.getShipmentPrefix());
		} catch (StockControlDefaultsBusinessException e) {
			log.log(Log.INFO, "Exception in validateNumericCode" +e);
			throw new SystemException(e.getErrors());
		}

		if(airlineValidationVO != null){
			documentFilterVO.setAirlineIdentifier(airlineValidationVO.getAirlineIdentifier());
			documentValidationVO = validateDocumentStock(documentFilterVO);
			if(documentValidationVO!=null &&
					DocumentValidationVO.DOC_TYP_AWB.equals(documentFilterVO.getDocumentType())){
				boolean isStockUtilized = Stock.constructDAO().checkAWBExistsInOperation(documentFilterVO.getCompanyCode(),
						documentFilterVO.getDocumentNumber(),airlineValidationVO.getAirlineIdentifier(),
						documentFilterVO.getDocumentType(),documentValidationVO.getDocumentSubType());
				if(isStockUtilized){
					throw new StockControlDefaultsBusinessException(StockControlDefaultsBusinessException.UTILISATION_EXISTS);
				}
			}
		}
		return documentValidationVO;
	}

	/**
	 *
	 * @param documentFilterVO
	 * @return
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException
	 */
	public DocumentValidationVO validateDocumentStock(
			DocumentFilterVO documentFilterVO)
			throws SystemException, StockControlDefaultsBusinessException {
		log.log(Log.INFO, "DocumentFilterVO : ", documentFilterVO);

		if(DocumentValidationVO.DOC_TYP_AWB.equals(
				documentFilterVO.getDocumentType())){
			log.log(Log.INFO,"Document Type is AWB. So, calling awbValidation");
			return validateAWBStock(documentFilterVO);
		}else if(DocumentValidationVO.DOC_TYP_COURIER.equals(
				documentFilterVO.getDocumentType())){
			log.log(Log.INFO,"Document Type is COU. So, calling courierValidation");
			return validateCourier(documentFilterVO.getCompanyCode(),
					documentFilterVO.getDocumentNumber(),
					documentFilterVO.getAirlineIdentifier(),
					DocumentValidationVO.DOC_TYP_COURIER,
					documentFilterVO.getStockOwner());
		}else if(DocumentValidationVO.DOC_TYP_EBT.equals(
				documentFilterVO.getDocumentType())){
			log.log(Log.INFO,"Document Type is EBT. So, calling ebtValidation");
			return validateEBT(documentFilterVO.getCompanyCode(),
					documentFilterVO.getDocumentNumber(),
					documentFilterVO.getAirlineIdentifier(),
					DocumentValidationVO.DOC_TYP_EBT,
					documentFilterVO.getStockOwner());
		}
		//Added as part of ICRD-46860
		else if(DocumentValidationVO.DOC_TYP_INV.equals(documentFilterVO.getDocumentType())) {
			log.log(Log.INFO,"Document Type is INV. So, calling InvoiceValidation");
			return validateInvoice(documentFilterVO);
		} else{
			log.log(Log.WARNING,"UNIDENTIFIED DOC_TYPE. So, returning NULL :");
			return null;
		}
	}


	/**
	 *
	 * @param docFilterVO
	 * @return
	 * @throws SystemException
	 * @throws StockControlDefaultsBusinessException
	 */
	private DocumentValidationVO validateAWBStock(DocumentFilterVO docFilterVO)
			throws SystemException, StockControlDefaultsBusinessException{

		String companyCode  = docFilterVO.getCompanyCode();
		String documentNumber  = docFilterVO.getDocumentNumber();
		int airlineId  = docFilterVO.getAirlineIdentifier();
		String agentCode = docFilterVO.getStockOwner();
		String documentType = DocumentValidationVO.DOC_TYP_AWB;
		String documentSubType = docFilterVO.getDocumentSubType();

		AWBDocumentValidationVO awbValidationVO = new AWBDocumentValidationVO();

		/**
		 * Check whether the document number is blacklisted or not
		 * if blacklisted, return error
		 */

		checkIfAWBBlacklisted(companyCode,airlineId,documentType,documentNumber,documentSubType);
		/**
		 * check whether awb is void or not
		 * if void, return error
		 */
         checkIfAwbIsVoid(companyCode,documentType,documentNumber,documentSubType);
		/*
		 * Added this check and change, for integrating AWB Acceptance and R2 Stock
		 * If the Document is in ReservedAWB Table, then it should be removed
		 * from that, after acceptance
		 *
		 */
		if(docFilterVO.isOtherAirline()){
			log.log(Log.FINE,"====OTHER ARLINE, validating RESERV status");
			ReservationVO reservationVO = Reservation.findReservationDetails(docFilterVO);

			LogonAttributes logon = ContextUtils.getSecurityContext().getLogonAttributesVO();

			if(reservationVO != null){
				awbValidationVO = populateAwbValidationVO(reservationVO,logon);
			}
		}

		String stockHolderCode = null;
		String stockHolderType = null;

		if(agentCode != null){
			/*
			 * checks whether at least one AWB stock contains this
			 * documentNumber inside the range, else throws exception
			 */
			if(! DocumentValidationVO.STATUS_RESERVED.equals(docFilterVO.getStatus())){
				checkAwbExistsInAnyStock(companyCode, airlineId, documentType, documentNumber,RangeVO.SUBSTRING_COUNT);
			}
			Collection<String> agentCodes = new ArrayList<>();
			agentCodes.add(agentCode);

			/*
			 * finds the stockholder code corresponding to
			 * the agent code from the stock-agent mapping
			 */
			stockHolderCode = findStockHolderCodeForAgent(
					companyCode, agentCode);
			if(stockHolderCode == null || ("").equals(stockHolderCode.trim())){
				log.log(Log.WARNING, "returning NULL");
				throw new StockControlDefaultsBusinessException(
						StockControlDefaultsBusinessException.STOCKHOLDER_NOTFOUNDFOR_AGENT,
						new Object[] { agentCode });
			}else{
				Collection<AgentDetailVO> agentDetails = null;
				agentDetails = validateAgents(companyCode,agentCodes);

				awbValidationVO.setAgentDetails(agentDetails);
			}
			/*
			 * checks whether the stockholder has "AWB" document type
			 * if present, then checks whether any range has the
			 * document number included in it
			 * else throws exception
			 */
			if(! DocumentValidationVO.STATUS_RESERVED.equals(docFilterVO.getStatus())){
				checkDocumentAvailable(companyCode, airlineId, stockHolderCode, documentNumber);
			}else{
				// checks whether this stock holder code is against the reserved document
				// if invalid then throw exception as above,
				ReservationVO resVO = Reservation.findReservationDetails(docFilterVO);
				if(resVO == null || ! stockHolderCode.equals(resVO.getStockHolderCode())){
					log.log(Log.SEVERE, "====INVALD STOCK HOLDER",
							stockHolderCode);
					throw new StockControlDefaultsBusinessException(
							StockControlDefaultsBusinessException.STOCKHOLDER_STOCK_NOTFOUND,
							new Object[] { stockHolderCode });
				}
			}

		}else{	//ie, IF agentCode == null


			/*
			 * checks whether at least one AWBstock contains this
			 * documentNumber inside the range and returns the
			 * stockHolder code , else throws exception
			 */
			stockHolderCode = checkAwbExistsInAnyStock(
					companyCode, airlineId,
					documentType, documentNumber,RangeVO.SUBSTRING_COUNT);

			awbValidationVO.setStatus(DocumentValidationVO.STATUS_INSTOCK);

			StockHolderVO stkHolderVO = StockHolder.findStockHolderDetails(
					companyCode, stockHolderCode);

			Collection<String> agentCodes = null;
			/*
			 * finds the agents associated with the stockHolder from the mapping
			 */
			if(stkHolderVO != null){
				stockHolderType = stkHolderVO.getStockHolderType();

				agentCodes = findAgentsForStockHolder(
						companyCode, stkHolderVO.getStockHolderCode());


			}
			/*
			 * finds collection of agentVOs with agent code and names
			 */
			if(agentCodes != null && !agentCodes.isEmpty()){
				Collection<AgentDetailVO> agentDetails = null;
				agentDetails = validateAgents(companyCode,agentCodes);

				awbValidationVO.setAgentDetails(agentDetails);
			}
			else{
				throw new StockControlDefaultsBusinessException(
						StockControlDefaultsBusinessException.AGENT_NOT_FOUND,
						new Object[] { stockHolderCode });
			}
		}


		Collection<ProductStockVO> productStockVos = null;

		log.log(Log.FINER,"\n\n\n\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		log.log(Log.INFO, "--documentSubType : ", documentSubType);
		if(documentSubType == null){
			documentSubType = findSubTypeForDocument(
					companyCode, airlineId, documentType, documentNumber);
		}

		try {
			productStockVos = new ProductsDefaultsProxy().
					findProductsForStock(companyCode,
							documentType, documentSubType);
		} catch (ProxyException e) {
			throw new SystemException(
					SystemException.UNEXPECTED_SERVER_ERROR, e);
		}

		awbValidationVO.setStockHolderCode(stockHolderCode);
		awbValidationVO.setDocumentType(documentType);
		awbValidationVO.setDocumentNumber(documentNumber);
		awbValidationVO.setStockHolderType(stockHolderType);
		awbValidationVO.setDocumentSubType(documentSubType);

		awbValidationVO.setProductStockVOs(productStockVos);


		log.log(Log.INFO, "returning AWBValidationVO :", awbValidationVO);
		return awbValidationVO;

	}

	private AWBDocumentValidationVO populateAwbValidationVO(ReservationVO reservationVO, LogonAttributes logon) throws StockControlDefaultsBusinessException {
		AWBDocumentValidationVO awbValidationVO = new AWBDocumentValidationVO();
		if( ! logon.getAirportCode().equals(reservationVO.getAirportCode()) ){
			log.log(Log.SEVERE, AWB_RESERVED_FOR_AIRPORT,
					reservationVO.getAirportCode());
			log.log(Log.SEVERE, AWB_RESERVED_FOR_AIRPORT,
					reservationVO.getAirportCode());
			log.log(Log.SEVERE, AWB_RESERVED_FOR_AIRPORT,
					reservationVO.getAirportCode());
			throw new StockControlDefaultsBusinessException(
					StockControlDefaultsBusinessException.AWB_RESERVED_FOR_ANOTHER_AIRPORT,
					new Object[] { reservationVO.getAirportCode() } );

		}
		awbValidationVO.setStatus(DocumentValidationVO.STATUS_RESERVED);
		awbValidationVO.setStockHolderCode(reservationVO.getStockHolderCode());
		awbValidationVO.setStockHolderType("A");	// hard coded for Agent
		awbValidationVO.setAgentDetails(new ArrayList<AgentDetailVO>());
		AgentDetailVO agentDetailVO = new AgentDetailVO();
		agentDetailVO.setAgentCode(reservationVO.getAgentCode());
		agentDetailVO.setCustomerCode(reservationVO.getCustomerCode());

		awbValidationVO.getAgentDetails().add(agentDetailVO);

		log.log(Log.FINER, "====RETURNING awbValidationVO==",
				awbValidationVO);
		return awbValidationVO;
	}

	public void saveStockReuseHistory(Collection<StockReuseHistoryVO> stockReuseHistoryVOs) throws SystemException {
		log.entering(this.getClass().getSimpleName(), "----------saveStock()");
		for(StockReuseHistoryVO stockReuseHistoryvo:  stockReuseHistoryVOs  ) {
			StockReuseHistory stockReuseHistoryforsave = new StockReuseHistory(stockReuseHistoryvo);
			try {
				PersistenceController.getEntityManager().persist(stockReuseHistoryforsave);
			} catch (CreateException createException) {
				throw new SystemException(createException.getErrorCode(), createException);
			}
		}
		
	}

	public void removeStockReuseHistory(StockReuseHistoryVO stockReuseHistoryVO) throws SystemException{
		log.entering(this.getClass().getSimpleName(), "----------removeStock()");
		
				Collection <Integer> sernums =Stock.constructDAO().removeStockReuseHistory(stockReuseHistoryVO);
				for(Integer sernum : sernums ) {
					stockReuseHistoryVO.setSerialNumber(sernum);
					StockReuseHistory stockReuseHistory= new StockReuseHistory();
					try {
						stockReuseHistory = StockReuseHistory.find(stockReuseHistoryVO);
					} catch (FinderException e) {
						log.log(Log.INFO, "Finder exception on removeStockReuseHistory" +e);
						throw new SystemException(e.getErrorCode(), e);
					}
					if(stockReuseHistory != null) {
						stockReuseHistory.remove();
					}
				}
			

		
	}
	
	public void updateStockReuseHistory(Collection<StockReuseHistoryVO> stockReuseHistoryVOs) throws SystemException {
		log.entering(this.getClass().getSimpleName(), "----------saveStock()");		
		saveStockReuseHistory(stockReuseHistoryVOs);
	}
	public void doAWBReUseRestrictionCheckEnhanced(DocumentFilterVO documentFilterVo) throws SystemException{ 
		//First find the country validation VO's based on the system parameter for O/D/V
		StringBuilder attributes = new StringBuilder();
		Collection<CountryValidationVO> countryValidationVOs=null;
		int reusableYears = 0;
		try {
			countryValidationVOs =Proxy.getInstance().get(SharedAreaProxy.class).findAWBReuseRestrictionDetailsEnhanced(documentFilterVo);
		} catch (ProxyException e) {
			log.log(Log.FINE, e);
		}
		if(countryValidationVOs!=null && !countryValidationVOs.isEmpty()){
		for(CountryValidationVO countryValidationVO:countryValidationVOs){
		if(countryValidationVO != null ){
		reusableYears = countryValidationVO.getNoOfRestrictionYear();			
		if(reusableYears > 0){
			//find the capture date from stock reuse history table for the country code			
			StockReuseHistoryVO stockReuseHistoryVO=Stock.constructDAO().findAWBReuseHistoryDetails(documentFilterVo, countryValidationVO.getCountryCode());
			if(stockReuseHistoryVO!=null && stockReuseHistoryVO.getCaptureDate()!=null){
				try {
					validateReusableYears(attributes,stockReuseHistoryVO,reusableYears,countryValidationVO.getCountryCode());
				} catch (StockControlDefaultsBusinessException e) {
					log.log(Log.FINE, e);
					throw new SystemException(e.getErrors());
				}
			}
		}
		}
		log.exiting("ShipmenetValidator", "doAWBReUseRestrictionCheck");
		}
		
		}
	}
	private void validateReusableYears(StringBuilder attributes,StockReuseHistoryVO stockReuseHistoryVO, int reusableYears,String countryCode) throws StockControlDefaultsBusinessException {
				ErrorVO errorVo;
				LocalDate lastUpdatedTime = stockReuseHistoryVO.getCaptureDate();
				long miliSecondForlastUpdatedTime = lastUpdatedTime.getTimeInMillis();
				Calendar current = Calendar.getInstance();
				long miliSecondForcurrentDate = current.getTimeInMillis();
				long diffInMilis= miliSecondForcurrentDate-miliSecondForlastUpdatedTime;
				long diffInDays = diffInMilis / (24 * 60 * 60 * 1000);
				if(diffInDays < reusableYears * 365){
					errorVo = new ErrorVO(AWB_CANNOT_REUSE);
					errorVo.setErrorDisplayType(ErrorDisplayType.ERROR);
					attributes.append(countryCode);
					attributes.append(",");
					attributes.append(reusableYears);
					String[] errorData = attributes.toString().split(",");
					errorVo.setErrorData(errorData);	
					StockControlDefaultsBusinessException businessException = new StockControlDefaultsBusinessException();
					businessException.addError(errorVo);
					throw businessException;
				}
	}
}
