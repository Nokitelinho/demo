/*
 * MaintainStockHolderSession.java Created on Aug 11, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderLovVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1358
 *
 * This class implements the session interface for MaintainStockHolder screen
 * The session in this case holds the document types and the stock holdertype
 */
public interface MaintainStockHolderSession extends ScreenSession {
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
	 * Method for getting id from session
	 * @return String
	 */
	public String  getId();
	/**
	 * Method for setting id to session
	 * @param id
	 */
	public void setId(String id);
	/**
	 * Method for removing id from session
	 *
	 */
	public void removeId();
	
	/**
	 * @param approverCode
	 */
	public void setApproverCode(String approverCode);
	
	/**
	 * @return
	 */
	public String getApproverCode();
	
	
	
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
	
	/**
	 * Method for getting pageStockLovVO from session
	 * @return Page<StockHolderLovVO>
	 */
	public Page<StockHolderLovVO>  getPageStockLovVO();
	/**
	 * Method for setting pageStockLovVO to session
	 * @param pageStockLovVO
	 */
	public void setPageStockLovVO(Page<StockHolderLovVO> pageStockLovVO);
	/**
	 * Method for removing pageStockLovVO from session
	 *
	 */
	public void removePageStockLovVO();
	
	
	/**
	 * Method for getting stockHolderType from session
	 * @return Collection<StockHolderPriorityVO>
	 */
	public Collection<StockHolderPriorityVO>  getPrioritizedStockHolders();
	/**
	 * Method for setting stockHolderType to session
	 * @param prioritizedStockHolders
	 */
	public void setPrioritizedStockHolders(Collection<StockHolderPriorityVO> prioritizedStockHolders);
	/**
	 * Method for removing prioritizedStockHolders from session
	 *
	 */
	public void removePrioritizedStockHolders();
	
	
	
	/**
	 * Method for getting stockHolderType from session
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO>  getOneTimeStock();
	/**
	 * Method for setting stockHolderType to session
	 * @param stockHolderType
	 */
	public void setOneTimeStock(Collection<OneTimeVO> oneTimeStock);
	/**
	 * Method for removing stockHolderType from session
	 *
	 */
	public void removeOneTimeStock();
	
	
	
	/**
	 * Method for getting stockVO from session
	 * @return Collection<StockVO>
	 */
	public Collection<StockVO>  getStockVO();
	/**
	 * Method for setting stockVO to session
	 * @param stockVO
	 */
	public void setStockVO(Collection<StockVO> stockVO);
	/**
	 * Method for removing stockVO from session
	 *
	 */
	public void removeStockVO();
	
	
	
	
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
     * All document types and subtypes are fetched on screenload and 
     * stored as a Map with document type as key and subtype collection 
     * as value. This method returns this map object from the session
     * @return Map
     */
	Map getDocumentTypes();

	/**
	 * This method is used to set documentType-subtype map object into
	 * the session
	 * @param documentTypes
	 */
	void setDocumentTypes (Map documentTypes );
	
	/**
	 * This method is used to fetch the stockholdertypes stored in 
	 * session. Stockholder types are fetched from onetime during 
	 * screenload 
	 * @return Collection<String>
	 */
	Collection<String> getStockHolderTypes();
	
	/**
	 * This method is used to set the stockholder types into the session
	 * @param stockHolderTypes
	 * Collection<String>
	 */
	void setStockHolderTypes(Collection<String> stockHolderTypes);
	
	/**
	 * Method for getting stockHolderVO from session
	 * @return StockHolderVO
	 */
	public StockHolderVO getStockHolderVO();
	/**
	 * Method for setting stockHolderVO to session
	 * @param stockHolderVO
	 */
	public void setStockHolderVO(StockHolderVO stockHolderVO);
	
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
	 * @return the keyStockApprovercode
	 */
	public  String getStockApprovercode();
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MaintainStockHolderSession#setStockApprovercode(java.lang.String)
	 */
	public void setStockApprovercode(String stockApproverCode);
		
}
