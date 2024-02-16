/*
 * MaintainStockHolderSessionImpl.java Created on Aug 11, 2005
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
import java.util.Map;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderLovVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MaintainStockHolderSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1358
 *
 * This class realizes the session interface for MaintainStockHolder screen
 * The session in this case holds the document types and the stock holdertype
 */
public class MaintainStockHolderSessionImpl extends AbstractScreenSession
        implements MaintainStockHolderSession {
	/**
	 * Added by A-4772 for ICRD-9882.Changed the 
	 * Screen id value as per standard for UISK009
	 */
	private static final String KEY_SCREEN_ID = "stockcontrol.defaults.maintainstockholder";
	
	private static final String KEY_MODULE_NAME = "stockcontrol.defaults";
	
	private static final String KEY_STOCKHOLDERTYPE = "stockHolderType";
	
	private static final String KEY_MAP = "map";
	
	private static final String KEY_STOCKVO = "stockVO";
	
	private static final String KEY_ONETIMESTOCK = "oneTimeStock";
	
	private static final String KEY_PRIORSTOCKHOLDERS = "prioritizedStockHolders";
	
	private static final String KEY_ID = "id";
	
	private static final String KEY_PAGELOVVO = "pageStockLovVO";
	
	private static final String KEY_DOCTYPE = "docType";
	
	private static final String KEY_DOCSUBTYPE = "docSubType";
	
	private static final String KEY_MODE = "mode";
	
	private static final String KEY_STOCKHOLDERVO = "stockHolderVO";
	
	private static final String KEY_APPROVERCODE="approverCode";
	
	private static final String KEY_STOCK_APPROVERCODE="stockApproverCode";
	
	private static final String KEY_PARTNER_AIRLINE="parnter.airlines";
	
	

	/**
     * This method returns the SCREEN ID for the  Maintain Stock holder  screen
     */


    public String getScreenID(){
        return KEY_SCREEN_ID;
    }



    /**
     * This method returns the MODULE name for the Maintain Stock holder screen
     */
    public String getModuleName(){
        return KEY_MODULE_NAME;
    }

    /**
     * This method is used to get the stockHolderType from the session
     * @return Collection<OneTimeVO>
     */
	public Collection<OneTimeVO>  getStockHolderType(){
	    return (Collection<OneTimeVO>)getAttribute(KEY_STOCKHOLDERTYPE);
	}

	/**
	 * This method is used to set the stockHolderType in session
	 * @param stockHolderType
	 */
	public void setStockHolderType(Collection<OneTimeVO>  stockHolderType) {
	    setAttribute(KEY_STOCKHOLDERTYPE, (ArrayList<OneTimeVO>)stockHolderType);
	}
	/**
	 * @return void
	 */
	public void removeStockHolderType(){
		removeAttribute(KEY_STOCKHOLDERTYPE);
	}

	 /**
     * This method is used to get the pageStockLovVO from the session
     * @return Page<StockHolderLovVO
     */
	public Page<StockHolderLovVO>  getPageStockLovVO(){
	    return (Page<StockHolderLovVO>)getAttribute(KEY_PAGELOVVO);
	}

	/**
	 * This method is used to set the pageStockLovVO in session
	 * @param pageStockLovVO
	 */
	public void setPageStockLovVO(Page<StockHolderLovVO>  pageStockLovVO) {
	    setAttribute(KEY_PAGELOVVO, (Page<StockHolderLovVO>)pageStockLovVO);
	}
	/**
	 * @return void
	 */
	public void removePageStockLovVO(){
		removeAttribute(KEY_PAGELOVVO);
	}

	/**
     * This method is used to get the id from the session
     * @return String
     */
	public String  getId(){
	    return (String)getAttribute(KEY_ID);
	}

	/**
	 * This method is used to set the id in session
	 * @param id
	 */
	public void setId(String  id) {
	    setAttribute(KEY_ID, (String)id);
	}
	/**
	 * @return void
	 */
	public void removeId(){
		removeAttribute(KEY_ID);
	}

	/**
     * This method is used to get the DocType from the session
     * @return String
     */
	public String  getDocType(){
	    return (String)getAttribute(KEY_DOCTYPE);
	}

	/**
	 * This method is used to set the DocType in session
	 * @param docType
	 */
	public void setDocType(String  docType) {
	    setAttribute(KEY_DOCTYPE, (String)docType);
	}
	/**
	 * @return void
	 */
	public void removeDocType(){
		removeAttribute(KEY_DOCTYPE);
	}

	/**
     * This method is used to get the mode from the session
     * @return String
     */
	public String  getMode(){
	    return (String)getAttribute(KEY_MODE);
	}

	/**
	 * This method is used to set the mode in session
	 * @param mode
	 */
	public void setMode(String  mode) {
	    setAttribute(KEY_MODE, (String)mode);
	}
	/**
	 * @return void
	 */
	public void removeMode(){
		removeAttribute(KEY_MODE);
	}

	/**
     * This method is used to get the DocSubType from the session
     * @return String
     */
	public String  getDocSubType(){
	    return (String)getAttribute(KEY_DOCSUBTYPE);
	}

	/**
	 * This method is used to set the DocSubType in session
	 * @param docSubType
	 */
	public void setDocSubType(String  docSubType) {
	    setAttribute(KEY_DOCSUBTYPE, (String)docSubType);
	}
	/**
	 * @return void
	 */
	public void removeDocSubType(){
		removeAttribute(KEY_DOCSUBTYPE);
	}

	/**
     * This method is used to get the stockHolderType from the session
     * @return Collection<StockHolderPriorityVO>
     */
	public Collection<StockHolderPriorityVO>  getPrioritizedStockHolders(){
	    return (Collection<StockHolderPriorityVO>)getAttribute(KEY_PRIORSTOCKHOLDERS);
	}

	/**
	 * This method is used to set the stockHolderType in session
	 * @param prioritizedStockHolders
	 */
	public void setPrioritizedStockHolders(Collection<StockHolderPriorityVO>  prioritizedStockHolders) {
	    setAttribute(KEY_PRIORSTOCKHOLDERS, (ArrayList<StockHolderPriorityVO>)prioritizedStockHolders);
	}
	/**
	 * @return void
	 */
	public void removePrioritizedStockHolders(){
		removeAttribute(KEY_PRIORSTOCKHOLDERS);
	}

	 /**
     * This method is used to get the stockHolderType from the session
     * @return Collection<OneTimeVO>
     */
	public Collection<OneTimeVO>  getOneTimeStock(){
	    return (Collection<OneTimeVO>)getAttribute(KEY_ONETIMESTOCK);
	}

	/**
	 * This method is used to set the stockHolderType in session
	 * @param oneTimeStock
	 */
	public void setOneTimeStock(Collection<OneTimeVO>  oneTimeStock) {
	    setAttribute(KEY_ONETIMESTOCK, (ArrayList<OneTimeVO>)oneTimeStock);
	}
	/**
	 * @return void
	 */
	public void removeOneTimeStock(){
		removeAttribute(KEY_ONETIMESTOCK);
	}

	 /**
     * This method is used to get the stockVO from the session
     * @return Collection<stockVO>
     */
	public Collection<StockVO>  getStockVO(){
	    return (Collection<StockVO>)getAttribute(KEY_STOCKVO);
	}

	/**
	 * This method is used to set the stockVO in session
	 * @param stockVO
	 */
	public void setStockVO(Collection<StockVO>  stockVO) {
	    setAttribute(KEY_STOCKVO, (ArrayList<StockVO>)stockVO);
	}
	/**
	 * @return void
	 */
	public void removeStockVO(){
		removeAttribute(KEY_STOCKVO);
	}

	 /**
     * This method is used to get the map from the session
     * @return HashMap<String,Collection<String>>
     */
	public HashMap<String,Collection<String>>  getMap(){
	    return (HashMap<String,Collection<String>>)getAttribute(KEY_MAP);
	}

	/**
	 * This method is used to set the map in session
	 * @param map
	 */
	public void setMap(HashMap<String,Collection<String>>  map) {
	    setAttribute(KEY_MAP, (HashMap<String,Collection<String>>)map);
	}
	/**
	 * @return void
	 */
	public void removeMap(){
		removeAttribute(KEY_MAP);
	}


    /**
     * All document types and subtypes are fetched on screenload and
     * stored as a Map with document type as key and subtype collection
     * as value. This method returns this map object from the session
     * @return Map
     */
	public Map getDocumentTypes() {
	    return null;
	}

	/**
	 * This method is used to set documentType-subtype map object into
	 * the session
	 * @param documentTypes
	 */
	public void setDocumentTypes(Map documentTypes ) {
	}

	/**
	 * This method is used to fetch the stockholdertypes stored in
	 * session. Stockholder types are fetched from onetime during
	 * screenload
	 * @return Collection<String>
	 */
	public Collection<String> getStockHolderTypes() {
	    return null;
	}

	/**
	 * This method is used to set the stockholder types into the session
	 * @param stockHolderTypes
	 * Collection<String>
	 */
	public void setStockHolderTypes(Collection<String> stockHolderTypes) {
	}
	/**
	 * Method to remove attribute from session
	 */
	/*public void removeAllAttributes(){
		this.removeAllAttribute();
	}*/
	
    /**
     * This method is used to get the stockHolderVO from the session
     * @return StockHolderVO
     */
	public StockHolderVO  getStockHolderVO(){
	    return (StockHolderVO)getAttribute(KEY_STOCKHOLDERVO);
	}

	/**
	 * This method is used to set the stockHolderVO in session
	 * @param stockHolderVO
	 */
	public void setStockHolderVO(StockHolderVO  stockHolderVO) {
	    setAttribute(KEY_STOCKHOLDERVO, (StockHolderVO)stockHolderVO);
	}	
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MaintainStockHolderSession#setApproverCode(java.lang.String)
	 */
	public void setApproverCode(String approverCode){
		setAttribute(KEY_APPROVERCODE,(String)approverCode);
	}
	
	public String getApproverCode(){
		return (String)getAttribute(KEY_APPROVERCODE);
	}



	public void setPartnerAirlines(Page<AirlineLovVO> partnerAirlines) {
		setAttribute(KEY_PARTNER_AIRLINE,partnerAirlines);
	}



	public Page<AirlineLovVO> getPartnerAirlines() {
		return getAttribute(KEY_PARTNER_AIRLINE);
	}


	
	//Added by A-5174 fro bug ICRD- 32653

	/**
	 * @return the keyStockApprovercode
	 */
	public  String getStockApprovercode() {
		return getAttribute(KEY_STOCK_APPROVERCODE);
	}
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MaintainStockHolderSession#setStockApprovercode(java.lang.String)
	 */
	public void setStockApprovercode(String stockApproverCode){
		setAttribute(KEY_STOCK_APPROVERCODE,(String)stockApproverCode);
	}
	
	
}
