/**
 * StockDefaultsBuilder.java Created on Aug 18, 2016
 *
 * Copyright 2015 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 *
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.builder;

import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.business.stockcontrol.defaults.BlacklistedRangeExistsException;
import com.ibsplc.icargo.business.stockcontrol.defaults.StockControlDefaultsBusinessException;
import com.ibsplc.icargo.business.stockcontrol.defaults.StockController;
import com.ibsplc.icargo.business.stockcontrol.defaults.StockNotFoundException;
import com.ibsplc.icargo.business.stockcontrol.defaults.StockRangeUtilisation;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.BlacklistStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDepleteFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interceptor.action.AbstractActionBuilder;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-6767
 *
 */
public class StockDefaultsBuilder extends AbstractActionBuilder{
	private Log log = LogFactory.getLogger("STOCKCONTROL DEFAULTS");
	private static final String REMARKS = "Blacklisted as part of Reprint or Payment advice Cancel";

	/**
	 * Audit for stock utilisation.
	 * 
	 * @param stockAllocationVO
	 *            the stock allocation vo
	 * @throws SystemException
	 *             the system exception
	 * @throws RemoteException
	 *             the remote exception
	 */
	public void findBlacklistedStockFromUTL(StockDepleteFilterVO stockDepleteFilterVO)
			throws SystemException {
		log.entering("StockDefaultsBuilder ", "findBlacklistedStockFromUTL ");
		Collection<BlacklistStockVO> blacklistStockVOs =null;
		ArrayList<String> masterDocNumbers = new ArrayList<String>();

		blacklistStockVOs=StockRangeUtilisation.findBlacklistedStockFromUTL();
			if(blacklistStockVOs!=null && blacklistStockVOs.size()>0){
			for(BlacklistStockVO blacklistStockVO:blacklistStockVOs){
				
				if(blacklistStockVO!=null){
					try {
						blacklistStockVO.setRemarks(REMARKS);
						new StockController().blacklistStock(blacklistStockVO);
						masterDocNumbers.add(blacklistStockVO.getRangeFrom().trim());
					} catch (StockNotFoundException e) {
						log.log(Log.SEVERE, "----->>>StockNotFoundException");
					} catch (BlacklistedRangeExistsException e) {
						log.log(Log.SEVERE, "----->>>BlacklistedRangeExistsException");
					} catch (StockControlDefaultsBusinessException e) {
						log.log(Log.SEVERE, "----->>>StockControlDefaultsBusinessException");
					}
					}
			}
			//For deleting Blacklisted stock from table
			try {
				new StockController().deleteBlackListedStockFromUTL(masterDocNumbers);
			} catch (Exception e) {
				log.log(Log.SEVERE, "----->>>Exception in Builder");
			}
			
		}

	}
}
