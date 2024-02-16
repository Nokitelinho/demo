/*
 * MaintainStockRequestSession.java Created on Sep 2, 2005
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
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * This class implements the session interface for MaintainStockRequest screen
 * The session in this case holds the document types and the stock holdertype
 * @author A-1927
 *
 */
public interface MaintainStockRequestSession extends ScreenSession {




    StockRequestVO getStockRequestVO();

    void setStockRequestVO(StockRequestVO stockRequestVO);



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
	 * Method for getting oneTimeStock from session
	 * @return Collection<OneTimeVO>
	 */
	Collection<OneTimeVO> getOneTimeStock();

	/**
	 * Method for setting oneTimeStock to session
	 * @return Collection<OneTimeVO>
	 */
	void setOneTimeStock(Collection<OneTimeVO> stockHolderTypes);

	/**
	 * Method for getting oneTimeStatus from session
	 * @return Collection<OneTimeVO>
	 */
	 Collection<OneTimeVO> getOneTimeStatus();

	/**
	 * Method for setting oneTimeStatus to session
	 * @return Collection<OneTimeVO>
	 */
	 void setOneTimeStatus(Collection<OneTimeVO>  oneTimeStatus);

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
	  * For #102543 Base product bug
	 * @author A-2589
	 * @param partnerAirlines
	 * 
	 */
	public void setPartnerAirlines(Page<AirlineLovVO> partnerAirlines);
	 
	 /**
	  * For #102543 Base product bug
	 * @author A-2589
	 * @return Page<AirlineLovVO>
	 * 
	 */
	public Page<AirlineLovVO> getPartnerAirlines();
}
