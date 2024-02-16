/*
 * AllocateStockSession.java Created on Feb 09, 2012
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
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.MissingStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.TransitStockVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-4443
 *
 */
public interface ConfirmStockSession extends ScreenSession {
	/**
	 * Method for getting stockHolderType from session
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO>  getStockHolderType();
	/**
	 * Method for setting stockHolderType to session
	 * @param stockHolderType
	 */
	public void setStockHolderType(Collection<OneTimeVO> stockHolderType);
	/**
	 * Method for removing stockHolderType from session
	 *
	 */
	public void removeStockHolderType();
	/**
	 * Method for getting StockHolderFor from session
	 * @return Collection<String>
	 */
	public Collection<String>  getStockHolderFor();
	/**
	 * Method for setting StockHolderFor to session
	 * @param stockHolderFor
	 */
	public void setStockHolderFor(Collection<String> stockHolderFor);
	/**
	 * Method for removing stockHolderType from session
	 *
	 */
	public void removeStockHolderFor();
	/**
	 * Method for getting rangeVO from session
	 * @return Collection<RangeVO
	 */
	public Collection<RangeVO>  getRangeVO();
	/**
	 * Method for setting rangeVO to session
	 * @param rangeVO
	 */
	public void setRangeVO(Collection<RangeVO> rangeVO);
	/**
	 * Method for removing rangeVO from session
	 *
	 */
	public void removeRangeVO();
	/**
	 * Method for getting pageStockRequestVO from session
	 * @return Page<StockRequestVO>
	 */
	public Page<StockRequestVO>  getPageStockRequestVO();
	/**
	 * Method for setting pageStockRequestVO to session
	 * @param pageStockRequestVO
	 */
	public void setPageStockRequestVO(Page<StockRequestVO> pageStockRequestVO);
	/**
	 * Method for removing pageStockRequestVO from session
	 *
	 */
	public void removePageStockRequestVO();
	/**
	 * Method for getting Data from session
	 * @return String
	 */
	public String  getData();
	/**
	 * Method for setting data to session
	 * @param data
	 */
	public void setData(String data);
	/**
	 * Method for removing data from session
	 *
	 */
	public void removeData();
	/**
	 * Method for getting Check from session
	 * @return String
	 */
	public String  getCheck();
	/**
	 * Method for setting Check to session
	 * @param data
	 */
	public void setCheck(String check);
	/**
	 * Method for removing Check from session
	 *
	 */
	public void removeCheck();
	/**
	 * Method for getting DocType from session
	 * @return String
	 */
	public String  getDocType();
	/**
	 * Method for setting DocType to session
	 * @param docType
	 */
	public void setDocType(String docType);
	/**
	 * Method for removing DocType from session
	 *
	 */
	public void removeDocType();
	
	
	/**
	 * Method for getting DocSubType from session
	 * @return String
	 */
	public String  getDocSubType();
	/**
	 * Method for setting DocSubType to session
	 * @param docSubType
	 */
	public void setDocSubType(String docSubType);
	/**
	 * Method for removing DocSubType from session
	 *
	 */
	public void removeDocSubType();
	/**
	 * Method for getting status from session
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO>  getStatus();
	/**
	 * Method for setting status to session
	 * @param status
	 */
	public void setStatus(Collection<OneTimeVO> status);
	/**
	 * Method for removing status from session
	 *
	 */
	public void removeStatus();
	

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
	 * Method for removing map from session
	 *
	 */
	public void removeMap();
	/**
	 * Method for getting mode from session
	 * @return String
	 */
	public String  getMode();
	/**
	 * Method for setting mode to session
	 * @param mode
	 */
	public void setMode(String mode);
	/**
	 * Method for removing mode from session
	 *
	 */
	public void removeMode();
	
	public void removeAllAttributes();
	

 	  /**
	   * Method for getting prioritizedStockHolders from session
	   * @return Collection<StockHolderPriorityVO>
	   */
	   Collection<StockHolderPriorityVO> getPrioritizedStockHolders();

 	  /**
	   * Method for setting prioritizedStockHolders to session
	   * @return Collection<String>
	   */
	   void setPrioritizedStockHolders(
			   Collection<StockHolderPriorityVO> prioritizedStockHolders);
	
	   
	public StockRequestFilterVO  getFilterDetails();
	public void setFilterDetails(StockRequestFilterVO stockRequestFilterVO);
	
	public String getButtonStatusFlag();
	public void setButtonStatusFlag(String buttonStatusFlag);
	
	/**
	 * Method for getting stockRequestVO from session
	 * @return String
	 */
	public StockRequestVO getStockRequestVO();
	/**
	 * Method for setting stockRequestVO to session
	 * @param stockRequestVO
	 */
	public void setStockRequestVO(StockRequestVO stockRequestVO);	
	
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
	 * Method for getting operation from session
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO>  getOperation();
	/**
	 * Method for setting operation to session
	 * @param operation
	 */
	public void setOperation(Collection<OneTimeVO> status);
	/**
	 * Method for removing operation from session
	 *
	 */
	public void removeOperation();
	
	/**
	 * Method for getting rangeVO from session
	 * @return Collection<RangeVO
	 */
	public Collection<TransitStockVO>  getTransitStockVOs();
	/**
	 * Method for setting rangeVO to session
	 * @param rangeVO
	 */
	public void setTransitStockVOs(Collection<TransitStockVO> transitStockVOs);
	/**
	 * Method for removing rangeVO from session
	 *
	 */
	public void removeTransitStockVOs();
	/**
	 * Method for getting rangeVO from session
	 * @return Collection<RangeVO
	 */
	public Collection<TransitStockVO>  getSelectedTransitStockVOs();
	/**
	 * Method for setting rangeVO to session
	 * @param rangeVO
	 */
	public void setSelectedTransitStockVOs(Collection<TransitStockVO> transitStockVOs);
	/**
	 * Method for removing rangeVO from session
	 *
	 */
	public void removeSelectedTransitStockVOs();
	/**
	 * Method for getting MissingStockVO from session
	 * @return Collection<MissingStockVO
	 */
	public Collection<MissingStockVO>  getMissingStockVOs();
	/**
	 * Method for setting MissingStockVO to session
	 * @param MissingStockVO
	 */
	public void setMissingStockVOs(Collection<MissingStockVO> missingStockVO);
	/**
	 * Method for removing MissingStockVO from session
	 *
	 */
	public void removeMissingStockVOs();
}
