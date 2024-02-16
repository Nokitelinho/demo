/*
 * ScreenLoadCommand.java Created on July 12, 2017
 *
 * Copyright  IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.deletestockrange;

import java.util.HashMap;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.MonitorStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.DeleteStockRangeSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MonitorStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.DeleteStockRangeForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ScreenLoadCommand extends BaseCommand {

	private static final String SUCCESS = "screenload_success";
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	private static final String ONE = "1";

	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		this.log.entering("ScreenLoadCommand", "execute");
		DeleteStockRangeForm form = (DeleteStockRangeForm) invocationContext.screenModel;
		MonitorStockSession session = (MonitorStockSession) getScreenSession(
				"stockcontrol.defaults", "stockcontrol.defaults.monitorstock");
		DeleteStockRangeSession sessionDeleteStockRange = (DeleteStockRangeSession) getScreenSession(
				"stockcontrol.defaults",
				"stockcontrol.defaults.deletestockrange");
		StockFilterVO stockFilterVO = new StockFilterVO();
		String chk = form.getStockHolder();
		MonitorStockVO selectedVO = new MonitorStockVO();
		MonitorStockVO stockVO = session.getMonitorStockHolderVO();
		Page<MonitorStockVO> monitorStockVO = session.getMonitorStockDetails();
		if(chk != null && chk.trim().length()>0){
			if(stockVO != null && chk.equalsIgnoreCase(stockVO.getStockHolderCode())){
				selectedVO = stockVO;
			}else if(monitorStockVO != null && monitorStockVO.size()>0){
				for(MonitorStockVO stkVO : monitorStockVO){
					if(chk.equalsIgnoreCase(stkVO.getStockHolderCode())){
						selectedVO = stkVO;
						break;
					}
				}
			}
		}
		form.setStockHolder(selectedVO.getStockHolderCode());
		form.setDeleteFrom("0");
		HashMap<String, String> indexMap = getIndexMap(
				sessionDeleteStockRange.getIndexMap(), invocationContext);
		if (sessionDeleteStockRange.getIndexMap() != null) {
			indexMap = sessionDeleteStockRange.getIndexMap();
		}
		if (indexMap == null) {
			indexMap = new HashMap();
			indexMap.put(ONE, ONE);
		}
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		stockFilterVO.setStockHolderCode(chk);
		stockFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		stockFilterVO.setDocumentSubType(selectedVO.getDocumentSubType());
		stockFilterVO.setDocumentType(session.getDocumentType());
		stockFilterVO.setAirlineIdentifier(getAirlineIdentifier(sessionDeleteStockRange.getPartnerAirline()));
		stockFilterVO.setPageNumber(1);
		stockFilterVO.setAbsoluteIndex(1);
		stockFilterVO.setTotalRecords(-1);
		sessionDeleteStockRange.setStockRangeVO(null);
		sessionDeleteStockRange.setStockFilterVO(null);
		sessionDeleteStockRange.setCollectionRangeVO(null);
		sessionDeleteStockRange.setPageRangeVO(null);
		sessionDeleteStockRange.setStockFilterVO(stockFilterVO);
		/*StockRangeVO stkRangeVO = viewDetails(stockFilterVO);
		sessionDeleteStockRange.setStockRangeVO(stkRangeVO);*/
		populateTotalDocumentCountAndStockRageVO(stockFilterVO, form, sessionDeleteStockRange);
		log.exiting("ScreenLoadCommand","execute");
		invocationContext.target = SUCCESS;
		
	}

	private int getAirlineIdentifier(AirlineLovVO partnerAirline) {
		int ownAirlineIdentifier = getApplicationSession().getLogonVO()
				.getOwnAirlineIdentifier();
		if (partnerAirline != null) {
			int airlineIdentifier = partnerAirline.getAirlineIdentifier();

			return airlineIdentifier > 0 ? airlineIdentifier
					: ownAirlineIdentifier;
		}
		return ownAirlineIdentifier;
	}

	/*public StockRangeVO viewDetails(StockFilterVO stockFilterVO) {
		StockRangeVO stockRangeVO = null;
		try {
			stockRangeVO = new StockControlDefaultsDelegate()
					.viewRange(stockFilterVO);
		} catch (BusinessDelegateException localBusinessDelegateException) {
			log.log(Log.SEVERE,"exception in viewDetails in ScreenloadCommand ");
		}
		return stockRangeVO;
	}*/
	/**
	 * @param stockRangeVO
	 * @param frm
	 */
	private void populateTotalDocumentCountAndStockRageVO(StockFilterVO stockFilterVO,DeleteStockRangeForm frm,
			DeleteStockRangeSession session) {
		try {
			int totalDocCount = new StockControlDefaultsDelegate()
					.findTotalNoOfDocuments(stockFilterVO);
			frm.setTotalDocCount(totalDocCount);
		} catch (BusinessDelegateException businessDelegateException) {
		}
		StockRangeVO stockRangeVO = new StockRangeVO();
		session.setStockRangeVO(stockRangeVO);
	}
}
