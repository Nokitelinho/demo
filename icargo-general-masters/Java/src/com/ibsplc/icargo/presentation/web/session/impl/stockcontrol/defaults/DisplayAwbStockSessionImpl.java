	/*
 * DisplayAwbStockSessionImpl.java Created on Jan 21, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

	package com.ibsplc.icargo.presentation.web.session.impl.stockcontrol.defaults;

	import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeHistoryVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.DisplayAwbStockSession;
		
		/**
		 * @author A-3184 
		 *
		 */
		public class DisplayAwbStockSessionImpl extends AbstractScreenSession
		        implements DisplayAwbStockSession{
			
		private static final String KEY_SCREEN_ID_LSR = "stockcontrol.defaults.viewawbstock";
		private static final String KEY_MODULE_NAME_LSR = "stockcontrol.defaults";
		private static final String KEY_STOCKRANGEHISTORYVO_LSR = "stockRangeHistoryVO";
		private static final String KEY_STOCKRANGEVO_LSR = "stockRangeHistoryVOs";
		private static final String STOCKSTATUS = "stockStatus";
		private static final String KEY_ONETIMEVO = "oneTimeVO";
		private static final String STOCKUTILISATIONSTATUS ="stockUtilizationStatus";
		/**
		 * This method returns the SCREEN ID for the List Stock Range History Screen
		 */
		public String getScreenID(){
		    return KEY_SCREEN_ID_LSR;
		}
		
		/**
		 * This method returns the MODULE name for the List Stock Range History Screen
		 */
		public String getModuleName(){
		    return KEY_MODULE_NAME_LSR;
		}
		/**
		 * This method returns StockRangeHistoryVO
		 */
		public StockRangeHistoryVO getStockRangeHistoryVO(){
		
			return (StockRangeHistoryVO)getAttribute(KEY_STOCKRANGEHISTORYVO_LSR);
		}
		
		
		/**
		 * This method is used to set the StockRangeHistoryVO in session
		 * @param stockRangeHistoryVO
		 */
		
		public void setStockRangeHistoryVO(StockRangeHistoryVO stockRangeHistoryVO){
			setAttribute(KEY_STOCKRANGEHISTORYVO_LSR,(StockRangeHistoryVO)stockRangeHistoryVO);
		}
		
		/**
		 * This method is used to set the StockRangeHistoryVO in session
		 * @param stockRangeHistoryVO
		 * @return KEY_STOCKRANGEVO_LSR
		 */
		
		public Collection<StockRangeHistoryVO> getStockRangeHistoryVOs(){
		
			return (Collection<StockRangeHistoryVO>)getAttribute(KEY_STOCKRANGEVO_LSR);
		}
		
		
		/**
		 * This method is used to set the StockRangeHistoryVOs in session
		 * @param stockRangeHistoryVOs
		 */
		public void setStockRangeHistoryVOs(Collection<StockRangeHistoryVO> stockRangeHistoryVOs){
			setAttribute(KEY_STOCKRANGEVO_LSR,(ArrayList<StockRangeHistoryVO>)stockRangeHistoryVOs);
		}
		
		/**
		 * @return Returns the sTATUS.
		 */
		public static String getStockStatus() {
			return STOCKSTATUS;
		}
		/**
		 * @return Returns the STOCKSTATUS.
		 */
		public Collection<OneTimeVO> getOneTimeStockStatus() {
			return (Collection<OneTimeVO>) getAttribute(STOCKSTATUS);
			
		}
		/**
		 * This method is used to set the status obtained from onetime into session
		 * @param oneTimeStockStatus
		 */
		public void setOneTimeStockStatus(Collection<OneTimeVO> oneTimeStockStatus) {
			setAttribute(STOCKSTATUS, (ArrayList<OneTimeVO>) oneTimeStockStatus);
			
		}
		/**
		 * This method is used to remove the StockStatus from session
		 * 
		 */
		public void removeOneTimeStockStatus() {
			removeAttribute(KEY_ONETIMEVO);
			}
		
		/**
		 * @return Returns the STOCKUTILISATIONSTATUS.
		 */
		public static String getStockUtilizationStatus() {
			return STOCKUTILISATIONSTATUS;
		}
		/**
		 * @return Returns the STOCKUTILISATIONSTATUS.
		 */
		public Collection<OneTimeVO> getOneTimeStockUtilizationStatus() {
			return (Collection<OneTimeVO>) getAttribute(STOCKUTILISATIONSTATUS);
			
		}
		/**
		 * This method is used to set the StockUtilizationStatus obtained from onetime into session
		 * @param oneTimeStockUtilizationStatus
		 */
		public void setOneTimeStockUtilizationStatus(Collection<OneTimeVO> oneTimeStockUtilizationStatus) {
			setAttribute(STOCKUTILISATIONSTATUS, (ArrayList<OneTimeVO>) oneTimeStockUtilizationStatus);
			
		}
		
		
		}
