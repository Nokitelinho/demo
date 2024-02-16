/*
 * ListBlackListedStockSession.java Created on Sep 21, 2005
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
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.BlacklistStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;



/**
 * @author A-1927
 *
 */
public interface ListBlackListedStockSession extends ScreenSession {



	 /**
	  * Method for getting allocatedRangesVos from session
	  * @return HashMap<String,Collection<String>>
	  */
	  HashMap<String,Collection<String>> getDocumentTypes();

	 /**
	  * Method for setting allocatedRangesVos to session
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
	   * @return Collection<StockHolderPriorityVO>
	   */
	   void setPrioritizedStockHolders(Collection<StockHolderPriorityVO> prioritizedStockHolders);

	  /**
	   * Method for getting blacklistStockVOs from session
	   * @return  Page<BlacklistStockVO>
	   */
	   Page<BlacklistStockVO> getBlacklistStockVOs();

	  /**
	   * Method for setting blacklistStockVOs to session
	   * @return  Page<BlacklistStockVO>
	   */
	   void setBlacklistStockVOs(Page<BlacklistStockVO> blacklistStockVOs);

	  /**
	   * Method for getting blacklistStockVOs from session
	   * @return  BlacklistStockVO
	   */
	   BlacklistStockVO getBlacklistStockVO();

	  /**
	   * Method for setting blacklistStockVOs to session
	   * @return  BlacklistStockVO
	   */
	   void setBlacklistStockVO(BlacklistStockVO blacklistStockVOs);
	   
	   
	   
		void setTotalRecords(int totalRecords);//Added by A-5214 as part from the ICRD-20959
		
		Integer getTotalRecords();//Added by A-5214 as part from the ICRD-20959




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

}
