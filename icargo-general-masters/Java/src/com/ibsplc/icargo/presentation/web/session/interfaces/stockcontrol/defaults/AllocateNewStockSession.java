/*
 * AllocateNewStockSession.java Created on Sep 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults;



import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


/**
 * @author A-1927
 *
 */
public interface AllocateNewStockSession extends ScreenSession {


	/**
	 * Method for getting allocatedRangesVos from session
	 * @return Collection<RangeVO>
	 */
	 Collection<RangeVO> getAllocatedRangeVos();

   	  /**
	   * Method for setting allocatedRangesVos to session
	   * @return Collection<RangeVO>
	   */
	 void setAllocatedRangeVos(Collection<RangeVO> allocatedRangeVos);

	 /**
	  * Method for getting availableRangesVos from session
	  * @return Collection<RangeVO>
	  */
	  Collection<RangeVO> getAvailableRangeVos();

   	  /**
	   * Method for setting availableRangesVos to session
	   * @return Collection<RangeVO>
	   */
	  void setAvailableRangeVos(Collection<RangeVO> allocatedRangeVos);

   	 /**
	  * Method for getting documentTypes from session
	  * @return HashMap<String,Collection<String>>
	  */
	  HashMap<String,Collection<String>> getDocumentTypes();

   	  /**
	   * Method for setting documentTypes to session
	   * @return HashMap<String,Collection<String>>
	   */
	  void setDocumentTypes(HashMap<String,Collection<String>> documentTypes );

   	  /**
	   * Method for getting prioritizedStockHolders from session
	   * @return Collection<StockHolderPriorityVO>
	   */
	   Collection<StockHolderPriorityVO> getPrioritizedStockHolders();

   	  /**
	   * Method for setting prioritizedStockHolders to session
	   * @return Collection<String>
	   */
	   void setPrioritizedStockHolders(Collection<StockHolderPriorityVO> prioritizedStockHolders);

   	  /**
	   * Method for getting stockControlFor from session
	   * @return Collection<String>
	   */
  	   Collection<String> getStockControlFor();

   	  /**
	   * Method for setting stockControlFor to session
	   * @return Collection<String>
	   */
  	   void setStockControlFor(Collection<String> stockControlFor);

  	   /**
  	 * @author A-2589
  	 * @param partnerAirlines
  	 * 
  	 */
  	public void setPartnerAirlines(Page<AirlineLovVO> partnerAirlines);
  	   /**
  	 * @author A-2589
  	 * @return Page<AirlineLovVO>
  	 * 
  	 */
  	public Page<AirlineLovVO> getPartnerAirlines();
  	
  	/**
	 *
	 * @return
	 */

		public StockAllocationVO   getStockAllocationVO();
	/**
	 *
	 * @param stockAllocationVO
	 */
		public void setStockAllocationVO(StockAllocationVO stockAllocationVO);
	/**
	 * Method for getting allocatedRangesVos from session
	 * @return Collection<RangeVO>
	 */
	Collection<RangeVO> getPrintReportRangeVos();

	  /**
	   * Method for setting allocatedRangesVos to session
	   * 
	   */
	void setPrintReportRangeVos(Collection<RangeVO> allocatedRangeVos);
}
