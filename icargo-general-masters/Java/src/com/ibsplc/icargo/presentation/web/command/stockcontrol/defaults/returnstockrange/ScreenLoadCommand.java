/*
 * ScreenLoadCommand.java Created on Aug 26, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.returnstockrange;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.MonitorStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MonitorStockSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ReturnStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ReturnStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1952
 * 
 */
public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");

	private static final String WARNING = "N";
	
	private static final String ONE = "1";

	/**
	 * The execute method in BaseCommand
	 * 
	 * @author A-1952
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		ReturnStockForm frm = (ReturnStockForm) invocationContext.screenModel;
		MonitorStockSession session = getScreenSession("stockcontrol.defaults",
				"stockcontrol.defaults.monitorstock");
		ReturnStockSession sessionReturn = getScreenSession(
				"stockcontrol.defaults",
				"stockcontrol.defaults.returnstockrange");
		// sessionReturn.removeAllAttributes();
		StockFilterVO stockFilterVO = new StockFilterVO();

		/*
		 * Collection<MonitorStockVO> collmonitorVO =
		 * session.getCollectionMonitorStockVO();
		 */

		String chk = frm.getStockHolder();
		frm.setWarningOk(WARNING);
		// commented by A-7740 as a part of ICRD-225106
		//frm.setModeRangeFrom("0");
		MonitorStockVO selectedVO = new MonitorStockVO();

		MonitorStockVO stockVO = session.getMonitorStockHolderVO();
		Page<MonitorStockVO> monitorStockVO = session.getMonitorStockDetails();
		if (chk != null && chk.trim().length() > 0) {
			if (stockVO != null
					&& chk.equalsIgnoreCase(stockVO.getStockHolderCode())) {
				selectedVO = stockVO;
			} else if (monitorStockVO != null && monitorStockVO.size() > 0) {
				for (MonitorStockVO stkVO : monitorStockVO) {
					if (chk.equalsIgnoreCase(stkVO.getStockHolderCode())) {
						selectedVO = stkVO;
						break;
					}
				}
			}
		}
		/*
		 * if(collmonitorVO!=null){ for(MonitorStockVO
		 * monitorStockVO:collmonitorVO){
		 * if(monitorStockVO.getStockHolderCode().equals(chk)){ selectedVO =
		 * monitorStockVO; break;
		 * 
		 * } else{ Collection<MonitorStockVO> childVO =
		 * monitorStockVO.getMonitorStock(); for(MonitorStockVO stkVO:childVO){
		 * if(stkVO.getStockHolderCode().equals(chk)){ selectedVO = stkVO;
		 * break;
		 * 
		 * } } } } }
		 */

		boolean isManual = false;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		frm.setStockHolder(selectedVO.getStockHolderCode());
		frm.setSubType(selectedVO.getDocumentSubType());
		frm.setDocType(session.getDocumentType());
		log
				.log(
						Log.FINE,
						"\n\n............Return........session.getManual()...............> ",
						session.getManual());
		if ("Y".equals(session.getManual())) {
			log.log(Log.FINE,
					"\n\n........Return.............Manal Flag true...............> ");
			isManual = true;
		}
		frm.setManual(isManual);
		frm.setReference(selectedVO.getReference());

		HashMap<String, String> indexMap = getIndexMap(
				sessionReturn.getIndexMap(), invocationContext);
		if (sessionReturn.getIndexMap() != null) {
			indexMap = sessionReturn.getIndexMap();
		}
		if (indexMap == null) {
			indexMap = new HashMap();
			indexMap.put(ONE, ONE);
		}
		stockFilterVO.setStockHolderCode(chk);
		stockFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		stockFilterVO.setDocumentSubType(selectedVO.getDocumentSubType()); // To be reviewed
																			// CHANGE
																			// HARD
																			// CODING
		stockFilterVO.setDocumentType(session.getDocumentType());
		stockFilterVO.setAirlineIdentifier(getAirlineIdentifier(sessionReturn
				.getPartnerAirline()));
		//Added By A-2882 for bug 108954 starts
		  stockFilterVO.setManual(isManual);
		 //Added By A-2882 for bug 108954 ends
		  stockFilterVO.setPageNumber(1);
		  stockFilterVO.setAbsoluteIndex(1);
		  stockFilterVO.setTotalRecords(-1);
		sessionReturn.setCollectionRangeVO(null);
		sessionReturn.setStockRangeVO(null);
		sessionReturn.setPageRangeVO(null);
		/*StockRangeVO stkRangeVO = viewDetails(stockFilterVO);
		sessionReturn.setStockRangeVO(stkRangeVO);*/
		populateTotalDocumentCountAndStockRageVO(stockFilterVO, frm, sessionReturn);
		sessionReturn.setStockFilterVO(stockFilterVO);
		RangeVO row = new RangeVO();
		row.setStartRange("");
		row.setEndRange("");
		row.setNumberOfDocuments(0);

		Collection<RangeVO> rangeVO = new ArrayList<RangeVO>();
		rangeVO.add(row);
		sessionReturn.setCollectionRangeVO(rangeVO);
		invocationContext.target = "screenload_success";
	}

	private int getAirlineIdentifier(AirlineLovVO partnerAirline) {
		int ownAirlineIdentifier=getApplicationSession().getLogonVO().getOwnAirlineIdentifier();
		if(partnerAirline!=null){
			int airlineIdentifier=partnerAirline.getAirlineIdentifier(); 				
			
			return (airlineIdentifier>0)?airlineIdentifier:ownAirlineIdentifier;
		} else {
			return ownAirlineIdentifier;
		}
	}

	/**
	 * Method to set the view details.
	 * 
	 * @param stockFilterVO
	 *//*
	public StockRangeVO viewDetails(StockFilterVO stockFilterVO) {
		StockRangeVO stockRangeVO = null;
		StockControlDefaultsDelegate stockControlDefaultsDelegate = new StockControlDefaultsDelegate();
		try {

			stockRangeVO = stockControlDefaultsDelegate
					.viewRange(stockFilterVO);
		} catch (BusinessDelegateException businessDelegateException) {
//printStackTrrace()();
		}
		return stockRangeVO;

	}*/

	/**
	 * @param stockFilterVO
	 * @param frm
	 * @param sessionReturn
	 */
	private void populateTotalDocumentCountAndStockRageVO(StockFilterVO stockFilterVO,
			ReturnStockForm frm,ReturnStockSession sessionReturn) {
		try {
			int totalDocCount = new StockControlDefaultsDelegate()
					.findTotalNoOfDocuments(stockFilterVO);
			frm.setTotalDocCount(totalDocCount);
		} catch (BusinessDelegateException businessDelegateException) {
		}
		StockRangeVO stockRangeVO = new StockRangeVO();
		sessionReturn.setStockRangeVO(stockRangeVO);
	}
}
