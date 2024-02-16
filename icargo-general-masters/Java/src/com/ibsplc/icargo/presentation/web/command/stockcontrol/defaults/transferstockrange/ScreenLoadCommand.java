/*
 * ScreenLoadCommand.java Created on Aug 26, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.transferstockrange;

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
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.TransferStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.TransferStockForm;
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

	//private static final String ZERO = "0";
	
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	//private static final int RANGE_LENGTH = 7;
	private static final String ONE = "1";
	/**
	 * The execute method of Base Command
	 * @param invocationContext
	 * @throws CommandInvocationException
	 *
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ScreenLoadCommand","execute");
		TransferStockForm frm = (TransferStockForm) invocationContext.screenModel;
		MonitorStockSession session = getScreenSession("stockcontrol.defaults",
				"stockcontrol.defaults.monitorstock");
		TransferStockSession sessiontransfer = getScreenSession(
				"stockcontrol.defaults",
				"stockcontrol.defaults.transferstockrange");
		StockFilterVO stockFilterVO = new StockFilterVO();
		/*Collection<MonitorStockVO> collmonitorVO = session
				.getCollectionMonitorStockVO();*/
		 // commented by A-7740 as a part of ICRD-225106
		//frm.setModeRangeFrom(ZERO);
		String chk = frm.getStockHolder();
		MonitorStockVO selectedVO = new MonitorStockVO();
		//sessiontransfer.removeAllAttributes();
		//StockRangeVO updatedStkRangeVO =new StockRangeVO();
		//Collection<RangeVO> availableRanges = new ArrayList <RangeVO>();
	/*	int startRangeCount ;
		int endRangeCount ;*/
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
		/*if (collmonitorVO != null) {
			for (MonitorStockVO monitorStockVO : collmonitorVO) {
				if (monitorStockVO.getStockHolderCode().equals(chk)) {
					selectedVO = monitorStockVO;
					break;

				} else {
					Collection<MonitorStockVO> childVO = monitorStockVO
							.getMonitorStock();

					for (MonitorStockVO stkVO : childVO) {
						if (stkVO.getStockHolderCode().equals(chk)) {
							selectedVO = stkVO;
							break;

						}
					}
				}
			}
		}*/

		boolean isManual = false;
		frm.setStockHolder(selectedVO.getStockHolderCode());
		frm.setTransferFrom(selectedVO.getStockHolderCode());
		frm.setSubType(selectedVO.getDocumentSubType());
		frm.setDocType(session.getDocumentType());
		log
				.log(
						Log.FINE,
						"\n\n.........Transfer...........session.getManual()...............> ",
						session.getManual());
		if("Y".equals(session.getManual())){
			log.log(Log.FINE,"\n\n.....Transfer................Manal Flag true...............> ");
			isManual = true;
		}
		frm.setManual(isManual);
		frm.setReference(selectedVO.getReference());
		
		HashMap<String, String> indexMap = getIndexMap(
				sessiontransfer.getIndexMap(), invocationContext);
		if (sessiontransfer.getIndexMap() != null) {
			indexMap = sessiontransfer.getIndexMap();
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
		stockFilterVO.setManual(isManual);
		stockFilterVO.setAirlineIdentifier(getAirlineIdentifier(sessiontransfer.getPartnerAirline()));
		stockFilterVO.setPageNumber(1);
		stockFilterVO.setAbsoluteIndex(1);
		stockFilterVO.setTotalRecords(-1);
		sessiontransfer.setCollectionRangeVO(null);
		sessiontransfer.setStockRangeVO(null);
		sessiontransfer.setPageRangeVO(null);
		//Added by A-2882 for bug 108953 starts
		sessiontransfer.setStockFilterVO(stockFilterVO);
		//Added by A-2882 for bug 108953 ends
		//StockRangeVO stkRangeVO = viewDetails(stockFilterVO);
		// added by A-4752 as part of icrd-14416. 
		
		// Range for some data is saved without prefix in database
		/*if(stkRangeVO.getAvailableRanges()!= null  && 
				stkRangeVO.getAvailableRanges().size() >0){
		for(RangeVO rangeVo : stkRangeVO.getAvailableRanges()){
			startRangeCount =0;
			endRangeCount=0;
			if(DocumentValidationVO.DOC_TYP_AWB.equals(stockFilterVO.getDocumentType())) {
				if(rangeVo.getStartRange().length() < 7 ){
					startRangeCount = 7 - rangeVo.getStartRange().length();
					for(int i=0 ; i<startRangeCount ; i++){
					rangeVo.setStartRange(0 + rangeVo.getStartRange());
					}
					
				}if(rangeVo.getEndRange().length() < 7 ){
					endRangeCount = 7 - rangeVo.getEndRange().length();
					for(int i=0 ; i<endRangeCount ; i++){
					rangeVo.setEndRange(0 + rangeVo.getEndRange());
					}
				}
			}
			availableRanges.add(rangeVo);
		}
		updatedStkRangeVO.setAvailableRanges(availableRanges);
		}else {
			updatedStkRangeVO = stkRangeVO;
		}
		sessiontransfer.setStockRangeVO(updatedStkRangeVO);*/
		populateTotalDocumentCountAndStockRageVO(stockFilterVO, frm, sessiontransfer);
		RangeVO row = new RangeVO();
		row.setStartRange("");
		row.setEndRange("");
		row.setNumberOfDocuments(0);

		Collection<RangeVO> rangeVO = new ArrayList<RangeVO>();
		rangeVO.add(row);
		sessiontransfer.setCollectionRangeVO(rangeVO);
		log.exiting("ScreenLoadCommand","execute");
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
	 */
	/*public StockRangeVO viewDetails(StockFilterVO stockFilterVO) {
		StockRangeVO stockRangeVO = null;
		try {
			stockRangeVO = new StockControlDefaultsDelegate()
					.viewRange(stockFilterVO);
		} catch (BusinessDelegateException businessDelegateException) {*/
//printStackTrrace()();
	/*	}
		return stockRangeVO;

	}*/
	
	/**
	 * @param stockFilterVO
	 * @param frm
	 * @param sessiontransfer
	 */
	private void populateTotalDocumentCountAndStockRageVO(StockFilterVO stockFilterVO,
			TransferStockForm frm,TransferStockSession sessiontransfer) {
		try {
			int totalDocCount = new StockControlDefaultsDelegate()
					.findTotalNoOfDocuments(stockFilterVO);
			frm.setTotalDocCount(totalDocCount);
		} catch (BusinessDelegateException businessDelegateException) {
		}
		StockRangeVO stockRangeVO = new StockRangeVO();
		sessiontransfer.setStockRangeVO(stockRangeVO);
	}
}
