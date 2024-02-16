/*
 * AllocateNewStockSessionImpl.java Created on Sep 20, 2005
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
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.AllocateNewStockSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1927
 * 
 */
public class AllocateNewStockSessionImpl extends AbstractScreenSession
		implements AllocateNewStockSession {

	private static final String KEY_SCREEN_ID_ANS = "stockcontrol.defaults.allocatenewstock";

	private static final String KEY_MODULE_NAME_ANS = "stockcontrol.defaults";

	private static final String KEY_ALLOC_RANGEVO_ANS = "rangeAllocatedVO";

	private static final String KEY_AVAIL_RANGEVO_ANS = "rangeAvailableVO";

	private static final String PRIO_STOCKHOLDER_ANS = "prioritizedstockholder";

	private static final String KEY_DOCTYPE_ANS = "docType";

	private static final String KEY_STOCKCONTROLFOR_ANS = "stockControlFor";
	
	private static final String KEY_PARTNER_AIRLINES="partner.airlines";
	
	private static final String KEY_STOCKALLOCATIONVO = "stockallocationvo";
	private static final String KEY_PRINT_REPORT_RANGEVO_ANS = "printreportrangeVO";

	/**
	 * This method returns the SCREEN ID for the Allocate New Stock screen
	 * 
	 * @return screenId
	 */
	public String getScreenID() {
		return KEY_SCREEN_ID_ANS;
	}

	/**
	 * This method returns the MODULE name for the Allocate New Stock screen
	 * 
	 * @return modulename
	 */
	public String getModuleName() {
		return KEY_MODULE_NAME_ANS;
	}

	/**
	 * Thie method is used to get the RangeVOs from session
	 * 
	 * @return RangeVO
	 */

	public Collection<RangeVO> getAllocatedRangeVos() {

		return (Collection<RangeVO>) getAttribute(KEY_ALLOC_RANGEVO_ANS);
	}

	/**
	 * This method is used to set the RangeVOs in session
	 * 
	 * @param allocatedRangeVos
	 */

	public void setAllocatedRangeVos(Collection<RangeVO> allocatedRangeVos) {

		setAttribute(KEY_ALLOC_RANGEVO_ANS,
				(ArrayList<RangeVO>) allocatedRangeVos);
	}

	/**
	 * Thie method is used to get the RangeVOs from session
	 * 
	 * @return RangeVO
	 */

	public Collection<RangeVO> getAvailableRangeVos() {

		return (Collection<RangeVO>) getAttribute(KEY_AVAIL_RANGEVO_ANS);
	}

	/**
	 * This method is used to set the RangeVOs in session
	 * 
	 * @param availableRangeVos
	 */

	public void setAvailableRangeVos(Collection<RangeVO> availableRangeVos) {

		setAttribute(KEY_AVAIL_RANGEVO_ANS,
				(ArrayList<RangeVO>) availableRangeVos);
	}

	/**
	 * All document types and subtypes are fetched on screenload and stored as a
	 * Map with document type as key and subtype collection as value. This
	 * method returns this map object from the session
	 * 
	 * @return Map
	 */
	public HashMap<String, Collection<String>> getDocumentTypes() {
		return (HashMap<String, Collection<String>>) getAttribute(KEY_DOCTYPE_ANS);
	}

	/**
	 * This method is used to set documentType-subtype map object into the
	 * session
	 * 
	 * @param documentTypes
	 */
	public void setDocumentTypes(
			HashMap<String, Collection<String>> documentTypes) {
		setAttribute(KEY_DOCTYPE_ANS,
				(HashMap<String, Collection<String>>) documentTypes);
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

	/**
	 * Thie method is used to get the StockControlFor from session
	 * 
	 * @return Collection<String>
	 */

	public Collection<String> getStockControlFor() {

		return (Collection<String>) getAttribute(KEY_STOCKCONTROLFOR_ANS);
	}

	/**
	 * This method is used to set the StockControlFor in session
	 * 
	 * @param stockHolderFor
	 */

	public void setStockControlFor(Collection<String> stockHolderFor) {

		setAttribute(KEY_STOCKCONTROLFOR_ANS,
				(ArrayList<String>) stockHolderFor);
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
	/**
	 * Thie method is used to get the RangeVOs from session for printReport
	 * 
	 * @return RangeVO
	 */

	public Collection<RangeVO> getPrintReportRangeVos() {

		return (Collection<RangeVO>) getAttribute(KEY_PRINT_REPORT_RANGEVO_ANS);
	}

	/**
	 * This method is used to set the RangeVOs in session for printReport 
	 * 
	 * @param allocatedRangeVos
	 */

	public void setPrintReportRangeVos(Collection<RangeVO> allocatedRangeVos) {

		setAttribute(KEY_PRINT_REPORT_RANGEVO_ANS,
				(ArrayList<RangeVO>) allocatedRangeVos);
	}
}
