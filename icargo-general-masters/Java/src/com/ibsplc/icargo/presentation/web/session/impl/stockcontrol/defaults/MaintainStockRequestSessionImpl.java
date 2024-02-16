/**
 * MaintainStockRequestSessionImpl.java Created on Sep 2, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.stockcontrol.defaults;

import java.util.Collection;
import java.util.HashMap;
import java.util.ArrayList;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MaintainStockRequestSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1927
 *
 */
public class MaintainStockRequestSessionImpl extends AbstractScreenSession
        implements MaintainStockRequestSession {


	private static final String KEY_ONETIMEVO_MSH = "oneTimeVO";
	private static final String KEY_DOCTYPE_MSH = "docType";
	private static final String KEY_STOCKREQUESTVO_MSH = "stockRequestVO";
	/**
	 * Added by A-4772 for ICRD-9882.Changed the 
	 * Screen id value as per standard for UISKC002
	 */
	private static final String KEY_SCREEN_ID_MSH = "stockcontrol.defaults.maintainstockrequest";
	private static final String KEY_MODULE_NAME_MSH = "stockcontrol.defaults";
	private static final String STATUS_MSH = "status";
	private static final String PRIO_STOCKHOLDER_MSH = "prioritizedstockholder";
	private static final String KEY_PARTNER_AIRLINES="partner.airlines";


    /**
	 * This method is used to set StockRequestVO object into
	 * the session
	 * @param documentTypes
	 */

    public StockRequestVO getStockRequestVO() {

		return (StockRequestVO)getAttribute(KEY_STOCKREQUESTVO_MSH);

	}

    /**
	 * This method is used to set StockRequestVO object into
	 * the session
	 * @param stockRequestVO
	 */
    public void setStockRequestVO(StockRequestVO stockRequestVO) {

		setAttribute(KEY_STOCKREQUESTVO_MSH,(StockRequestVO)stockRequestVO);
	}


   /**
    * @return screenID
    */
    public String getScreenID() {
        return KEY_SCREEN_ID_MSH;
    }

    /**
     * @return moduleName
     */
    public String getModuleName() {
        return KEY_MODULE_NAME_MSH;
    }

    /**
     * All document types and subtypes are fetched on screenload and
     * stored as a Map with document type as key and subtype collection
     * as value. This method returns this map object from the session
     * @return Map
     */
    public  HashMap<String,Collection<String>> getDocumentTypes(){
        return (HashMap<String,Collection<String>>)getAttribute(KEY_DOCTYPE_MSH);
    }

	/**
	 * This method is used to set documentType-subtype map object into
	 * the session
	 * @param documentTypes
	 */
    public void setDocumentTypes (HashMap<String,Collection<String>> documentTypes){

		setAttribute(KEY_DOCTYPE_MSH,(HashMap<String,Collection<String>>)documentTypes);
    }

	/**
	 * This method is used to fetch the stockholdertypes stored in
	 * session. Stockholder types are fetched from onetime during
	 * screenload
	 * @return Collection<String>
	 */
	public Collection<OneTimeVO> getOneTimeStock(){
	   return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMEVO_MSH);
	}

	/**
	 * This method is used to set the stockholder types into the session
	 * @param stockHolderTypes
	 * Collection<String>
	 */
	public void setOneTimeStock(Collection<OneTimeVO> stockHolderTypes){
		setAttribute(KEY_ONETIMEVO_MSH,(ArrayList<OneTimeVO>)stockHolderTypes);
	}



	/**
	 * Thie method is used to get the OneTimeVO for child screen
	 * from session
	 * @return OneTimeVO
	 */
	public Collection<OneTimeVO> getOneTimeStatus() {
			return (Collection<OneTimeVO>)getAttribute(STATUS_MSH);
	}

	/**
	 * This method is used to set the OneTimeVO in session for child
	 * @param oneTimeStatus
	 */
	
	public void setOneTimeStatus(Collection<OneTimeVO> oneTimeStatus) {
		setAttribute(STATUS_MSH,(ArrayList<OneTimeVO>)oneTimeStatus);
	}



	/**
	 * Thie method is used to get the PriotirizedStockHolderVO for child screen
	 * from session
	 * @return PrioritizedStockHolderVO
	 */

	public Collection<StockHolderPriorityVO> getPrioritizedStockHolders(){

		return (Collection<StockHolderPriorityVO>)getAttribute(PRIO_STOCKHOLDER_MSH);
	}


	/**
	 * This method is used to set the PriotirizedStockHolderVO in session for child
	 * @param prioritizedStockHolders
	 */

	public void setPrioritizedStockHolders(Collection<StockHolderPriorityVO> prioritizedStockHolders){

		setAttribute(PRIO_STOCKHOLDER_MSH,(ArrayList<StockHolderPriorityVO>)prioritizedStockHolders);
	}

	public void setPartnerAirlines(Page<AirlineLovVO> partnerAirlines) {
		setAttribute(KEY_PARTNER_AIRLINES,partnerAirlines);		
	}

	public Page<AirlineLovVO> getPartnerAirlines() {
		return getAttribute(KEY_PARTNER_AIRLINES);
	}




}
