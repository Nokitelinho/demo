/*
 * ListStockRangeHistorySessionImpl.java Created on Jan 21, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.session.impl.stockcontrol.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeHistoryVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession; 
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ListStockRangeHistorySession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

	/**
	 * @author A-3184 & A-3155
	 *
	 */
	public class ListStockRangeHistorySessionImpl extends AbstractScreenSession
	        implements ListStockRangeHistorySession{
		
		private static final String KEY_SCREEN_ID_LSR = "stockcontrol.defaults.listawbstockhistory";
		private static final String KEY_MODULE_NAME_LSR = "stockcontrol.defaults";
		private static final String KEY_STOCKRANGEHISTORYVO_LSR = "stockRangeHistoryVO";
		private static final String KEY_STOCKRANGEVO_LSR = "stockRangeHistoryVOs";
		private static final String AWBTYPES = "awbType";
		private static final String STOCKSTATUS = "stockStatus";
		private static final String KEY_ONETIMEVO = "oneTimeVO";
		private static final String STOCKUTILISATIONSTATUS ="stockUtilizationStatus";
		private static final String KEY_DOCSUBTYPE_LBS = "docSubType";
		private static final String KEY_PARTNER_AIRLINES="partner.airlines";
		private static final String KEY_DOCTYPE_MAP="stockcontrol.defaults.doctypemap";
		private static final String KEY_PAGE_STOCK_RANGE="stockcontrol.defaults.pagestockranges";
		//Added by A-5220 for ICRD-20959 starts
		public static final String KEY_TOTAL_RECORD_COUNT="stockcontrol.defaults.list.totalrecordcount";
		//Added by A-5220 for ICRD-20959 ends
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
		 * @return KEY_STOCKRANGEHISTORYVO_LSR
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
		 * @return Returns the AWBTYPES.
		 */
		public static String getAwbType() {
			return AWBTYPES;
		}
		/**
		 * @return Returns the STOCKSTATUS.
		 */
		public Collection<OneTimeVO> getOneTimeStockStatus() {
			return (Collection<OneTimeVO>) getAttribute(STOCKSTATUS);
			
		}
		/**
		 * This method is used to set the oneTimeStockStatus in session
		 * @param oneTimeStockStatus
		 */

		public void setOneTimeStockStatus(Collection<OneTimeVO> oneTimeStockStatus) {
			setAttribute(STOCKSTATUS, (ArrayList<OneTimeVO>) oneTimeStockStatus);
			
		}
		/**
		 * @return Returns the KEY_ONETIMEVO.
		 */
		public Collection<OneTimeVO> getOneTimeAwbType() {
			return (Collection<OneTimeVO>) getAttribute(KEY_ONETIMEVO);
		
		}
		/**
		 * This method is used to set the oneTimeAwbType in session
		 * @param oneTimeAwbType
		 */

		public void setOneTimeAwbType(Collection<OneTimeVO> oneTimeAwbType) {
			setAttribute(KEY_ONETIMEVO, (ArrayList<OneTimeVO>) oneTimeAwbType);
			
		}
		/**
		 * @param removeOneTimeRequestedBy
		 *
		 */
		public void removeOneTimeAwbType() {
			removeAttribute(KEY_ONETIMEVO);
			}
		/**
		 * @param removeOneTimeStatus
		 *
		 */
		public void removeOneTimeStockStatus() {
			removeAttribute(KEY_ONETIMEVO);
			}

		/**
		 * @return Returns the sTOCKUTILISATIONSTATUS.
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
		 * This method is used to set the oneTimeStockUtilizationStatus in session
		 * @param oneTimeStockUtilizationStatus
		 */
		public void setOneTimeStockUtilizationStatus(Collection<OneTimeVO> oneTimeStockUtilizationStatus) {
			setAttribute(STOCKUTILISATIONSTATUS, (ArrayList<OneTimeVO>) oneTimeStockUtilizationStatus);
			
		}
		/**
		 * All subtypes are fetched on screenload and
		 * stored as a Map with  subtype collection
		 * as value. This method returns this map object from the session
		 * @return Map
		 */
		 public  ArrayList<String> getDocumentTypes(){
		      return (ArrayList<String>)getAttribute(KEY_DOCSUBTYPE_LBS);
		 }

		/**
		 * This method is used to set document-subtype map object into
		 * the session
		 * @param documentSubTypes
		 */
		 public void setDocumentTypes (ArrayList<String> documentTypes){
			setAttribute(KEY_DOCSUBTYPE_LBS,(ArrayList<String>)documentTypes);
		 }
		 
		 /**
		  * @author A-2881
		  */
		 public void setPartnerAirlines(Page<AirlineLovVO> partnerAirlines) {
		    setAttribute(KEY_PARTNER_AIRLINES, partnerAirlines);
	     }
         
		 /**
		  * @author A-2881
		  */
	     public Page<AirlineLovVO> getPartnerAirlines() {
		    return getAttribute(KEY_PARTNER_AIRLINES);
		 }

		/**
		 * @author A-2881
		 */
		public HashMap<String, Collection<String>> getMap() {
			return getAttribute(KEY_DOCTYPE_MAP);
		}

		/**
		 * @author A-2881
		 */
		public void setMap(HashMap<String, Collection<String>> map) {
			setAttribute(KEY_DOCTYPE_MAP, map);
			
		}

		public Page<StockRangeHistoryVO> getPageStockRangeHistoryVOs() {
			return getAttribute(KEY_PAGE_STOCK_RANGE);
		}

		public void setPageStockRangeHistoryVOs(Page<StockRangeHistoryVO> stockRangeVOs) {
		    setAttribute(KEY_PAGE_STOCK_RANGE, stockRangeVOs);
		}
		 /**
		  * added by T-1927 for ICRD-19368
		  */
		public void removePageStockRangeHistoryVOs() {
			removeAttribute(KEY_PAGE_STOCK_RANGE);
		}
		
		//Added by A-5220 for ICRD-20959 starts
		public void setTotalRecordCount(int toralRecordCount) {
			// TODO Auto-generated method stub
			setAttribute(KEY_TOTAL_RECORD_COUNT, toralRecordCount);
		}
		public int getTotalRecordCount() {
			// TODO Auto-generated method stub
			return (Integer)getAttribute(KEY_TOTAL_RECORD_COUNT);
		}
		//Added by A-5220 for ICRD-20959 ends

	}
