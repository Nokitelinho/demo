/*
 * ListMonitorStockCommand.java Created on Aug 26, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.monitorstock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.MonitorStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDepleteFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.framework.security.privilege.TransactionPrivilegeHelper;
import com.ibsplc.icargo.framework.security.privilege.vo.TransactionPrivilegeNewVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MonitorStockSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ReturnStockSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.TransferStockSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ViewRangeSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.MonitorStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1952
 * 
 */
public class ListMonitorStockCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	private static final String GENERATE_RPT = "generatereport";
	private static final String TX_COD_MONITOR_STOCK = "MONITOR_STOCK";

	/**
	 * The execute method in BaseCommand
	 * 
	 * @author A-1952
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("ListMonitorStockCommand", "execute");
		MonitorStockForm monitorStockForm = (MonitorStockForm) invocationContext.screenModel;
		MonitorStockSession session = getScreenSession("stockcontrol.defaults",
				"stockcontrol.defaults.monitorstock");
		session.setAwbPrefix(monitorStockForm.getAwbPrefix());
		String selected = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<MonitorStockVO> monitorStockVOsForSession = new ArrayList<MonitorStockVO>();
		StockFilterVO stockFilterVO = null;
		if ("Y".equals(monitorStockForm.getMode())) {
			log.log(Log.FINE, "\n\n......ListMonitorStockCommand.........");
			selected = session.getSelected();
			StringTokenizer stringTokenizer = new StringTokenizer(selected, "-");
			if (stringTokenizer.hasMoreTokens()) {
				monitorStockForm
						.setStockHolderCode(stringTokenizer.nextToken());
				monitorStockForm.setDocType(stringTokenizer.nextToken());
				monitorStockForm.setSubType(stringTokenizer.nextToken());
			}

		}

		log.log(Log.FINE,
				"\n\n.....................Form.getCode...............> ",
				monitorStockForm.getStockHolderCode());
		log.log(Log.FINE,
				"\n\n.....................Form.getDcType...............> ",
				monitorStockForm.getDocType());
		stockFilterVO = handleSearchDetails(monitorStockForm, session);

		StockControlDefaultsDelegate stockControlDefaultsDelegate = new StockControlDefaultsDelegate();
		try {
			session.setCollectionMonitorStockVO(null);
			session.setMonitorStockDetails(null);
			session.setMonitorStockHolderVO(null);

			HashMap<String, String> indexMap = null;
			HashMap<String, String> finalMap = null;
			if (session.getIndexMap() != null) {
				indexMap = session.getIndexMap();
			}
			if (indexMap == null) {
				log.log(Log.INFO, "INDEX MAP IS NULL");
				indexMap = new HashMap<String, String>();
				indexMap.put("1", "1");
			}
			int nAbsoluteIndex = 0;
			String displayPage = monitorStockForm.getDisplayPage();
			String strAbsoluteIndex = indexMap.get(displayPage);
			if (strAbsoluteIndex != null) {
				nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
			}
			stockFilterVO.setAbsoluteIndex(nAbsoluteIndex);
			session.setApproverCode(upper(monitorStockForm.getStockHolderCode()));
			/*
			 * Collection<MonitorStockVO> monitorStockVO =
			 * stockControlDefaultsDelegate.monitorStockDetails(stockFilterVO);
			 */

			StockDepleteFilterVO stockDepleteFilterVO = new StockDepleteFilterVO();
			stockDepleteFilterVO.setCompanyCode(getApplicationSession()
					.getLogonVO().getCompanyCode());
			stockDepleteFilterVO
					.setAirlineId(getAirlineIdentifier(monitorStockForm
							.getAwbPrefix()));
			//Commented by A-5153 for CRQ_ICRD-38007
			//stockControlDefaultsDelegate.autoStockDeplete(stockDepleteFilterVO);
			//Added for privilege check
			TransactionPrivilegeNewVO privilegeNewVO = getPrivilegeVO(TX_COD_MONITOR_STOCK);
			if(privilegeNewVO!=null){
			stockFilterVO.setPrivilegeLevelType(privilegeNewVO.getLevelType());
			stockFilterVO.setPrivilegeLevelValue(privilegeNewVO.getTypeValue());
			stockFilterVO.setPrivilegeRule(privilegeNewVO.getPrivilegeCode());
			}
			//Added for privilege check ends
			MonitorStockVO masterStockVO = stockControlDefaultsDelegate
					.findMonitoringStockHolderDetails(stockFilterVO);
			if (masterStockVO == null) {
				ErrorVO error = null;
				//Object[] obj = { "norecords" };
				error = new ErrorVO("stockcontrol.defaults.norecordsmonitornotauthorized");
				//error.setErrorDisplayType(ErrorDisplayType.INFO);
				errors.add(error);
				invocationContext.addAllError(errors);
				invocationContext.target = "screenload_success";
				return;
			}
			session.setMonitorStockHolderVO(masterStockVO);
			
			Page<MonitorStockVO> monitorStockVO = stockControlDefaultsDelegate
					.monitorStock(stockFilterVO);
			/*
			 * if(masterStockVO == null && (monitorStockVO==null ||
			 * monitorStockVO.size()==0 )){ log.log(Log.FINE,
			 * "\n\n.....................monitorStockVO...............> "+
			 * monitorStockVO); ErrorVO error = null; Object[] obj = {
			 * "norecords" }; error = new
			 * ErrorVO("stockcontrol.defaults.norecordsfound", obj);
			 * error.setErrorDisplayType(ErrorDisplayType.INFO);
			 * errors.add(error); invocationContext.addAllError(errors);
			 * invocationContext.target = "screenload_success"; return;
			 * 
			 * }
			 */

			if (monitorStockVO != null && monitorStockVO.size() > 0) {
				masterStockVO.setMonitorStock(monitorStockVO);
			}
			if (masterStockVO != null) {
				log.log(Log.FINE, "MASTER STOCK VO ", masterStockVO);
				monitorStockForm.setStockHolderCode(masterStockVO
						.getStockHolderCode());
				monitorStockForm.setDocType(masterStockVO.getDocumentType());
				monitorStockForm.setSubType(masterStockVO.getDocumentSubType());
				monitorStockForm.setStockHolderType(masterStockVO
						.getStockHolderType());
			}

			monitorStockVOsForSession.add(masterStockVO);
			session.setCollectionMonitorStockVO(monitorStockVOsForSession);
			session.setMonitorStockDetails(monitorStockVO);

			finalMap = indexMap;
			if (session.getMonitorStockDetails() != null) {
				finalMap = buildIndexMap(indexMap,
						session.getMonitorStockDetails());
			}
			session.setIndexMap(finalMap);

			if (monitorStockForm.isPartnerAirline()) {
				loadChildSessions(session,monitorStockForm);
			}else{
				ViewRangeSession sessionView = (ViewRangeSession) getScreenSession(
						"stockcontrol.defaults", "stockcontrol.defaults.viewrange");
				TransferStockSession sessiontransfer = getScreenSession(
						"stockcontrol.defaults",
						"stockcontrol.defaults.transferstockrange");
				ReturnStockSession sessionReturn = getScreenSession(
						"stockcontrol.defaults",
						"stockcontrol.defaults.returnstockrange");
				sessionView.setPartnerAirline(null);
				sessiontransfer.setPartnerAirline(null);
				sessionReturn.setPartnerAirline(null);
			}

		} catch (BusinessDelegateException businessDelegateException) {
//printStackTrrace()();
			errors = handleDelegateException(businessDelegateException);
			log.log(Log.FINE, "\n\n......EXCEPTIONNNNNNNN.........");
		}
		//Added as part of ICRD-46860
		if(GENERATE_RPT.equals(session.getReportGenerateMode())) {
			monitorStockForm.setReportGenerateMode(GENERATE_RPT);		
			
		}
		if (errors != null && errors.size() > 0) {
			log.log(Log.FINE, "\n\n..eoorooooooooo.......", errors);
			invocationContext.addAllError(errors);
			invocationContext.target = "screenload_success";
			return;
		}
		monitorStockForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		log.exiting("ListMonitorStockCommand", "execute");
		invocationContext.target = "screenload_success";

	}

	private void loadChildSessions(MonitorStockSession session, MonitorStockForm monitorStockForm) {
		ViewRangeSession sessionView = (ViewRangeSession) getScreenSession(
				"stockcontrol.defaults", "stockcontrol.defaults.viewrange");
		TransferStockSession sessiontransfer = getScreenSession(
				"stockcontrol.defaults",
				"stockcontrol.defaults.transferstockrange");
		ReturnStockSession sessionReturn = getScreenSession(
				"stockcontrol.defaults",
				"stockcontrol.defaults.returnstockrange");
		
		AirlineLovVO airlineLovVO=getAirlineLovVO(session,monitorStockForm);
		sessionView.setPartnerAirline(airlineLovVO);
		sessiontransfer.setPartnerAirline(airlineLovVO);
		sessionReturn.setPartnerAirline(airlineLovVO);

	}

	private AirlineLovVO getAirlineLovVO(MonitorStockSession session, MonitorStockForm monitorStockForm) {
		Page<AirlineLovVO> airlineLovVOs=session.getPartnerAirlines();
		if(airlineLovVOs!=null && !airlineLovVOs.isEmpty()){
			for(AirlineLovVO airlineLovVO:airlineLovVOs ){
				if(airlineLovVO.getAirlineIdentifier()==getAirlineIdentifier(monitorStockForm.getAwbPrefix())&&
						airlineLovVO.getAirlineName().equals(monitorStockForm.getAirlineName())
						&&airlineLovVO.getAirlineNumber().equals(getAirlineNumber(monitorStockForm.getAwbPrefix()))){
					return airlineLovVO;
				}
			}
		}
		return null;
	}

	private String getAirlineNumber(String awbPrefix) {
		return extractTokensFromAwbPrefix(awbPrefix, 0); 
	}

	/**
	 * Method to handleSearchDetails
	 * 
	 * @param monitorStockForm
	 * @param session
	 * @return
	 */
	private StockFilterVO handleSearchDetails(
			MonitorStockForm monitorStockForm, MonitorStockSession session) {

		StockFilterVO stockFilterVOs = new StockFilterVO();

		stockFilterVOs.setCompanyCode(getApplicationSession().getLogonVO()
				.getCompanyCode());
		stockFilterVOs.setStockHolderCode(upper(monitorStockForm
				.getStockHolderCode()));
		stockFilterVOs
				.setStockHolderType(monitorStockForm.getStockHolderType());
		stockFilterVOs.setDocumentType(monitorStockForm.getDocType());
		stockFilterVOs.setDocumentSubType(monitorStockForm.getSubType());
		log
				.log(
						Log.FINE,
						"\n\n......ListMonitorStockCommand..............monitorStockForm.isManual()............> ",
						monitorStockForm.isManual());
		stockFilterVOs.setManual(monitorStockForm.isManual());
		session.setDocumentType(monitorStockForm.getDocType());
		if (monitorStockForm.isManual()) {
			session.setManual("Y");
		} else {
			session.setManual("N");
		}
		int pageNumber = 0;
		if (monitorStockForm.getDisplayPage() != null
				&& monitorStockForm.getDisplayPage().trim().length() > 0) {
			pageNumber = Integer.parseInt(monitorStockForm.getDisplayPage());
		}
		/**
		 * Added by A-2589 for #102543
		 */
		stockFilterVOs
				.setAirlineIdentifier(getAirlineIdentifier(monitorStockForm
						.getAwbPrefix()));
		/**
		 * End #102543
		 */
		stockFilterVOs.setPageNumber(pageNumber);
		return stockFilterVOs;

	}

	private int getAirlineIdentifier(String awbPrefixFromForm) {		
		if (awbPrefixFromForm != null && awbPrefixFromForm.trim().length() > 0) {
			return Integer.parseInt(extractTokensFromAwbPrefix(awbPrefixFromForm, 2));
		}else{
			return getApplicationSession().getLogonVO()
			.getOwnAirlineIdentifier();
		}
	}
	
	private String extractTokensFromAwbPrefix(String awbPrefixFromForm,int tokenNum){
		String[] tokens = awbPrefixFromForm.split("-");
		String token="";
		switch (tokenNum) {
		case 0: {
			token = tokens[tokenNum];
			break;
		}
		case 1: {
			token = tokens[tokenNum];
			break;
		}
		case 2: {
			token = (tokens != null && tokens.length > 2) ? tokens[tokenNum]
					: "" + getApplicationSession().getLogonVO().getOwnAirlineIdentifier();
			break;
		}
		default: {

		}
		}
		
		return token;
			             
	}

	/**
	 * method to convert to upper case
	 * 
	 * @param input
	 * 
	 */

	private String upper(String input) {

		if (input != null) {
			return input.trim().toUpperCase();
		} else {
			return "";
		}
	}

	/**
	 * Method to bulid the hashmap to maintain absoluteindex
	 * 
	 * @param existingMap
	 *            HashMap<String, String>
	 * @param page
	 *            Page
	 * @return HashMap<String, String>
	 */
	private HashMap<String, String> buildIndexMap(
			HashMap<String, String> existingMap, Page page) {
		HashMap<String, String> finalMap = existingMap;
		String indexPage = String.valueOf((page.getPageNumber() + 1));
		boolean pageExits = false;
		Set<Map.Entry<String, String>> set = existingMap.entrySet();
		for (Map.Entry<String, String> entry : set) {
			String pageNum = entry.getKey();
			if (pageNum.equals(indexPage)) {
				pageExits = true;
			}
		}
		if (!pageExits) {
			finalMap.put(indexPage, String.valueOf(page.getAbsoluteIndex()));
		}

		return finalMap;
	}
	
	
	/**
	 * getPrivilegeVO()
	 * @param transactionCode
	 * @return TransactionPrivilegeNewVO
	 */
	private TransactionPrivilegeNewVO getPrivilegeVO(
			String transactionCode) {
		log.entering("ListMonitorStockCommand", "getPrivilegeVO");
		List<TransactionPrivilegeNewVO> privilegeList=null;
		try {
			privilegeList = (ArrayList<TransactionPrivilegeNewVO>) 
			TransactionPrivilegeHelper.getAllowededPrivilegesForTransaction(transactionCode);
		} catch (SystemException e) {			
			log.log(Log.SEVERE,e.getMessage());
		}
		log.exiting("ListMonitorStockCommand", "getPrivilegeVO");
		if(privilegeList!=null && !privilegeList.isEmpty()){
			return privilegeList.get(0);
		}
		return null;
	}
}
