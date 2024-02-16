/*
 * DisplayAwbStockSession.java Created on Jan 21, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults;



import java.util.Collection;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeHistoryVO;
import com.ibsplc.icargo.framework.session.ScreenSession;



/**
 * @author A-3184 
 *
 */
public interface DisplayAwbStockSession extends ScreenSession {
		/**
		 * Method for getting stockRangeHistoryVOs from session
		 * @return Collection<StockRangeHistoryVO>
		 */
	  	 Collection<StockRangeHistoryVO> getStockRangeHistoryVOs();
	  	/**
	 	 * Method for setting stockRangeHistoryVOs to session
	 	 * @param StockRangeHistoryVO
	 	 */
	     void setStockRangeHistoryVOs(Collection<StockRangeHistoryVO> stockRangeHistoryVOs);
	     /**
	 	 * Method for getting stockRangeHistoryVO from session
	 	 * @return StockRangeHistoryVO
	 	 */
		 StockRangeHistoryVO getStockRangeHistoryVO();
		 /**
		 * Method for setting stockRangeHistoryVO to session
		 * @param stockRangeHistoryVO
		 */
		 void setStockRangeHistoryVO(StockRangeHistoryVO stockRangeHistoryVO);
		 /**
		 * Method for getting OneTimeStockStatus from session
		 * @return Collection<OneTimeVO>
		 */
		 Collection<OneTimeVO> getOneTimeStockStatus();
		 /**
		 * Method for setting oneTimeStockStatus to session
		 * @param oneTimeStockStatus
		 */
	     void setOneTimeStockStatus(Collection<OneTimeVO> oneTimeStockStatus);
	     /**
	 	 * Method for getting OneTimeStockUtilizationStatus from session
	 	 * @return Collection<OneTimeVO>
	 	 */
	     Collection<OneTimeVO> getOneTimeStockUtilizationStatus();
	     /**
	 	 * Method for setting OneTimeStockUtilizationStatus to session
	 	 * @param stockHolderFor
	 	 */
	     void setOneTimeStockUtilizationStatus(Collection<OneTimeVO> oneTimeStockUtilizationStatus);
	}


