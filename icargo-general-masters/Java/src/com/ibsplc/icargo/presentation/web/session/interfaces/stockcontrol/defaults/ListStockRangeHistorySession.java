/*
 * ListStockRangeHistorySession.java Created on Jan 21, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults;



import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeHistoryVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

	/**
	 * @author A-3184
	 *
	 */
	public interface ListStockRangeHistorySession extends ScreenSession { 
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
	 * Method for getting OneTimeAwbType from session
	 * @return Collection<OneTimeVO>
	 */
	Collection<OneTimeVO> getOneTimeAwbType();
	/**
 	 * Method for setting oneTimeAwbType to session
 	 * @param oneTimeAwbType
 	 */
     void setOneTimeAwbType(Collection<OneTimeVO> oneTimeAwbType);
     /**
	 * Method for getting OneTimeStockUtilizationStatus from session
	 * @return Collection<OneTimeVO>
	 */
     Collection<OneTimeVO> getOneTimeStockUtilizationStatus();
     /**
 	 * Method for setting oneTimeStockUtilizationStatus to session
 	 * @param oneTimeStockUtilizationStatus
 	 */
     void setOneTimeStockUtilizationStatus(Collection<OneTimeVO> oneTimeStockUtilizationStatus);
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
	 * Method for getting stockRangeHistoryVOs from session
	 * @return Collection<StockRangeHistoryVO>
	 */
	 StockRangeHistoryVO getStockRangeHistoryVO();
	 /**
 	 * Method for setting stockRangeHistoryVOs to session
 	 * @param StockRangeHistoryVO
 	 */
	 void setStockRangeHistoryVO(StockRangeHistoryVO stockRangeHistoryVO);
	 /**
	  * Method for getting docSubType from session
	  * @return ArrayList<String>
	  */
	  ArrayList<String> getDocumentTypes();

	 /**
	  * Method for setting docSubType to session
	  * @return ArrayList<String>
	  */
	  void setDocumentTypes(ArrayList<String> documentTypes );
	  
	/**
	 * @author A-2881
	 * @param findAirlineLov
	 * 
	 */
	public void setPartnerAirlines(Page<AirlineLovVO> findAirlineLov);

	/**
	 * @author A-2881
	 * @return Page<AirlineLovVO>
	 * 
	 */
	public Page<AirlineLovVO> getPartnerAirlines();
	
	/**
	 * Method for getting map from session
	 * @return HashMap
	 */
	public HashMap<String,Collection<String>>  getMap();
	/**
	 * Method for setting map to session
	 * @param map
	 */
	public void setMap(HashMap<String,Collection<String>> map);
	/**
	 * 
	 * @param stockRangeVOs
	 */
	public void setPageStockRangeHistoryVOs(Page<StockRangeHistoryVO> stockRangeVOs);
	/**
	 * 
	 * @return
	 */
	public Page<StockRangeHistoryVO> getPageStockRangeHistoryVOs();
	
	public void removePageStockRangeHistoryVOs();//added by T-1927 for ICRD-19368
	 
	//Added by A-5220 for ICRD-20959 starts
	void setTotalRecordCount(int toralRecordCount);
	int getTotalRecordCount();
	//Added by A-5220 for ICRD-20959 ends
}
 