/*
 * ListBlackListedStockSessionImpl.java Created on Sep 21, 2005
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
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.BlacklistStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ListBlackListedStockSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1927
 *
 */
public class ListBlackListedStockSessionImpl extends AbstractScreenSession
        implements ListBlackListedStockSession{

	private static final String KEY_SCREEN_ID_LBS = "stockcontrol.defaults.listblacklistedstock";
	private static final String KEY_MODULE_NAME_LBS = "stockcontrol.defaults";
	private static final String PRIO_STOCKHOLDER_LBS = "prioritizedstockholder";
	private static final String KEY_DOCTYPE_LBS = "docType";
	private static final String KEY_BLACKLISTVO_LBS = "blackListVO";
	private static final String KEY_BLACKLISTSTOCKVO_LBS = "blackListStockVO";
	
	private static final String KEY_PARTNER_AIRLINES="partner.airlines";
	
	private static final String KEY_TOTAL_RECORDS = "totalRecords";//Added by A-5214 as part from the ICRD-20959

    /**
     * This method returns the SCREEN ID for the Allocate New Stock screen
     */
    public String getScreenID(){
        return KEY_SCREEN_ID_LBS;
    }

    /**
     * This method returns the MODULE name for the Allocate New Stock screen
     */
    public String getModuleName(){
        return KEY_MODULE_NAME_LBS;
    }

	/**
	 * All document types and subtypes are fetched on screenload and
	 * stored as a Map with document type as key and subtype collection
	 * as value. This method returns this map object from the session
	 * @return Map
	 */
	 public  HashMap<String,Collection<String>> getDocumentTypes(){
	      return (HashMap<String,Collection<String>>)getAttribute(KEY_DOCTYPE_LBS);
	 }

	/**
	 * This method is used to set documentType-subtype map object into
	 * the session
	 * @param documentTypes
	 */
	 public void setDocumentTypes (HashMap<String,Collection<String>> documentTypes){
		setAttribute(KEY_DOCTYPE_LBS,(HashMap<String,Collection<String>>)documentTypes);
	 }


	/**
	 * Thie method is used to get the PriotirizedStockHolderVO for child screen
	 * from session
	 * @return PriotirizedStockHolderVO
	 */

	public Collection<StockHolderPriorityVO> getPrioritizedStockHolders(){

		return (Collection<StockHolderPriorityVO>)getAttribute(PRIO_STOCKHOLDER_LBS);
	}


	/**
	 * This method is used to set the PriotirizedStockHolderVO in session for child
	 * @param prioritizedStockHolders
	 */

	public void setPrioritizedStockHolders(Collection<StockHolderPriorityVO> prioritizedStockHolders){

		setAttribute(PRIO_STOCKHOLDER_LBS,(ArrayList<StockHolderPriorityVO>)prioritizedStockHolders);
	}

	 /**
	  * Thie method is used to get the BlacklistStockVOs
	  * from session
      * @return BlacklistStockVOs
	  */

	public Page<BlacklistStockVO> getBlacklistStockVOs(){

		return (Page<BlacklistStockVO>)getAttribute(KEY_BLACKLISTVO_LBS);
	}


	/**
	 * This method is used to set the BlacklistStockVOs in session
	 * @param blacklistStockVos
	 */

	public void setBlacklistStockVOs(Page<BlacklistStockVO> blacklistStockVos){
		setAttribute(KEY_BLACKLISTVO_LBS,(Page<BlacklistStockVO>)blacklistStockVos);
	}

	 /**
	  * Thie method is used to get the BlacklistStockVOs
	  * from session
	  * @return BlacklistStockVO
	  */
	public BlacklistStockVO getBlacklistStockVO(){

		return (BlacklistStockVO)getAttribute(KEY_BLACKLISTSTOCKVO_LBS);
	}


	/**
	 * This method is used to set the BlacklistStockVO in session
	 * @param blacklistStockVo
	 */

	public void setBlacklistStockVO(BlacklistStockVO blacklistStockVo){
		setAttribute(KEY_BLACKLISTSTOCKVO_LBS,(BlacklistStockVO)blacklistStockVo);
	}

	public void setPartnerAirlines(Page<AirlineLovVO> partnerAirlines) {
		setAttribute(KEY_PARTNER_AIRLINES,partnerAirlines);
		
	}

	public Page<AirlineLovVO> getPartnerAirlines() {
		return getAttribute(KEY_PARTNER_AIRLINES);
	}

	/**
	  * Added by A-5214 as part from the ICRD-20959
	  * This method is used to set total records values in session
	  * @param int
	*/
	public void setTotalRecords(int totalRecords){
       setAttribute(KEY_TOTAL_RECORDS, Integer.valueOf(totalRecords));
    }
	
	/**
	  * Added by A-5214 as part from the ICRD-20959
	  * This method is used to get total records values from session
	  * from session
	  * @return Integer
	*/
	
	public Integer getTotalRecords() {
		return (Integer)getAttribute(KEY_TOTAL_RECORDS);
	}



}
