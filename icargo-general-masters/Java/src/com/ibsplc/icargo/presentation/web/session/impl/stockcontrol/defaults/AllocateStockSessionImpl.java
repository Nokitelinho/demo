/*
 * AllocateStockSessionImpl.java Created on Sep 20, 2005
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
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.AllocateStockSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1870
 *
 */
public class AllocateStockSessionImpl extends AbstractScreenSession
implements AllocateStockSession{
	private static final String KEY_SCREEN_ID = "UISKC003";
	private static final String KEY_MODULE_NAME = "stockcontrol.defaults";
	private static final String KEY_MAP = "map";
	private static final String KEY_STATUS="status";
	private static final String KEY_DOCTYPE = "docType";
	private static final String KEY_DOCSUBTYPE = "docSubType";
	private static final String KEY_STOCKHOLDERTYPE = "stockHolderType";
	private static final String KEY_PAGESTOCKREQUEST = "pageStockRequestVO";
	private static final String KEY_RANGEVO = "rangeVO";
	private static final String KEY_STOCKHOLDERFOR = "stockHolderFor";
	private static final String KEY_MODE = "mode";
	private static final String KEY_DATA = "data";
	private static final String KEY_CHECK = "check";
	private static final String PRIO_STOCKHOLDER_ANS = "prioritizedstockholder";
	private static final String FILTER_DETAILS = "allocateStockFilterDetails";
	private static final String BUTTONSTATUS_FLAG = "buttonStatusFlag";
	private static final String KEY_STOCKREQUESTVO = "stockRequestVO";
	private static final String KEY_PARTNER_AIRLINES="partner.airlines";
	private static final String KEY_STOCKALLOCATIONVO = "stockallocationvo";
	/**
     * This method returns the SCREEN ID for the AllocateStock screen
     */


    public String getScreenID(){
        return KEY_SCREEN_ID;
    }



    /**
     * This method returns the MODULE name for the AllocateStock screen
     */
    public String getModuleName(){
        return KEY_MODULE_NAME;
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
		removeAttribute("KEY_MAP");
	}
	/**
     * This method is used to get the data from the session
     * @return String
     */
	public String  getData(){
	    return (String)getAttribute(KEY_DATA);
	}

	/**
	 * This method is used to set the data in session
	 * @param data
	 */
	public void setData(String  data) {
	    setAttribute(KEY_DATA, (String)data);
	}
	/**
	 * @return void
	 */
	public void removeData(){
		removeAttribute(KEY_DATA);
	}
	/**
     * This method is used to get the check from the session
     * @return String
     */
	public String  getCheck(){
	    return (String)getAttribute(KEY_CHECK);
	}

	/**
	 * This method is used to set the check in session
	 * @param check
	 */
	public void setCheck(String  check) {
	    setAttribute(KEY_CHECK, (String)check);
	}
	/**
	 * @return void
	 */
	public void removeCheck(){
		removeAttribute(KEY_CHECK);
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
     * This method is used to get the status from the session
     * @return OneTimeVO
     */
	public Collection<OneTimeVO>  getStatus(){
	    return (Collection<OneTimeVO>)getAttribute(KEY_STATUS);
	}

	/**
	 * This method is used to set the status in session
	 * @param status
	 */
	public void setStatus(Collection<OneTimeVO>  status) {
	    setAttribute(KEY_STATUS, (ArrayList<OneTimeVO>)status);
	}
	/**
	 * @return void
	 */
	public void removeStatus(){
		removeAttribute(KEY_STATUS);
	}

	/**
     * This method is used to get the StockHolderFor from the session
     * @return Collection<String>
     */
	public Collection<String>  getStockHolderFor(){
	    return (Collection<String>)getAttribute(KEY_STOCKHOLDERFOR);
	}

	/**
	 * This method is used to set the StockHolderFor in session
	 * @param stockHolderFor
	 */
	public void setStockHolderFor(Collection<String>  stockHolderFor) {
	    setAttribute(KEY_STOCKHOLDERFOR, (ArrayList<String>)stockHolderFor);
	}
	/**
	 * @return void
	 */
	public void removeStockHolderFor(){
		removeAttribute(KEY_STOCKHOLDERFOR);
	}
	/**
     * This method is used to get the rangeVO from the session
     * @return OneTimeVO
     */
	public Collection<RangeVO>  getRangeVO(){
	    return (Collection<RangeVO>)getAttribute(KEY_RANGEVO);
	}

	/**
	 * This method is used to set the rangeVO in session
	 * @param rangeVO
	 */
	public void setRangeVO(Collection<RangeVO>  rangeVO) {
	    setAttribute(KEY_RANGEVO, (ArrayList<RangeVO>)rangeVO);
	}
	/**
	 * @return void
	 */
	public void removeRangeVO(){
		removeAttribute(KEY_RANGEVO);
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
     * This method is used to get the pageStockRequestVO from the session
     * @return  Page<StockRequestVO>
     */
	public Page<StockRequestVO>  getPageStockRequestVO(){
	    return (Page<StockRequestVO>)getAttribute(KEY_PAGESTOCKREQUEST);
	}

	/**
	 * This method is used to set the pageStockRequestVO in session
	 * @param pageStockRequestVO
	 */
	public void setPageStockRequestVO(Page<StockRequestVO>  pageStockRequestVO) {
	    setAttribute(KEY_PAGESTOCKREQUEST, (Page<StockRequestVO>)pageStockRequestVO);
	}
	/**
	 * @return void
	 */
	public void removePageStockRequestVO(){
		removeAttribute(KEY_PAGESTOCKREQUEST);
	}
	
	/**
	 * Thie method is used to get the PriotirizedStockHolderVO for child screen
	 * from session
	 * 
	 * @return PriotirizedStockHolderVO
	 */

	public Collection<StockHolderPriorityVO> getPrioritizedStockHolders() {

		return (Collection<StockHolderPriorityVO>) getAttribute(PRIO_STOCKHOLDER_ANS);
	}

	/**
	 * This method is used to set the PriotirizedStockHolderVO in session for
	 * child
	 * 
	 * @param prioritizedStockHolders
	 */

	public void setPrioritizedStockHolders(
			Collection<StockHolderPriorityVO> prioritizedStockHolders) {

		setAttribute(PRIO_STOCKHOLDER_ANS,
				(ArrayList<StockHolderPriorityVO>) prioritizedStockHolders);
	}
	
	public StockRequestFilterVO  getFilterDetails(){
	    return (StockRequestFilterVO)getAttribute(FILTER_DETAILS);
	}
	public void setFilterDetails(StockRequestFilterVO  stockRequestFilterVO) {
	    setAttribute(FILTER_DETAILS, (StockRequestFilterVO)stockRequestFilterVO);
	}

	
	public String getButtonStatusFlag(){
	    return (String)getAttribute(BUTTONSTATUS_FLAG);
	}
	public void setButtonStatusFlag(String  buttonStatusFlag) {
	    setAttribute(BUTTONSTATUS_FLAG, (String)buttonStatusFlag);
	}	
	
	/**
     * This method is used to get the stockRequestVO from the session
     * @return StockRequestVO
     */
	public StockRequestVO getStockRequestVO(){
	    return (StockRequestVO)getAttribute(KEY_STOCKREQUESTVO);
	}

	/**
	 * This method is used to set the StockRequestVO in session
	 * @param StockRequestVO
	 */
	public void setStockRequestVO(StockRequestVO stockRequestVO) {
	    setAttribute(KEY_STOCKREQUESTVO, (StockRequestVO)stockRequestVO);
	}

	public void setPartnerAirlines(Page<AirlineLovVO> partnerAirlines) {
		setAttribute(KEY_PARTNER_AIRLINES,partnerAirlines);
		
	}

	public Page<AirlineLovVO> getPartnerAirlines() {
		return getAttribute(KEY_PARTNER_AIRLINES);
	}	
	
	/**
	 * This method returns the stockAllocationVO
	 * @return stockAllocationVO
	 */
	public StockAllocationVO   getStockAllocationVO(){
	   return (StockAllocationVO)getAttribute(KEY_STOCKALLOCATIONVO);
	}
	/**
	* @param stockAllocationVO
	*/
	public void setStockAllocationVO(StockAllocationVO stockAllocationVO){
		  setAttribute(KEY_STOCKALLOCATIONVO, (StockAllocationVO)stockAllocationVO);
	}
	
}
