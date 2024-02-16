/*
 * GenerateSCMSession.java Created on Aug 01, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMMessageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1862
 * 
 */
public interface GenerateSCMSession extends ScreenSession {
/**
 * 
 * @return
 */
	public String getPageURL();
/**
 * 
 * @param pageurl
 */
	public void setPageURL(String pageurl);
/**
 * 
 * @param uldNumbers
 */
	void setExtraUld(ArrayList<String> uldNumbers);
/**
 * 
 * @return
 */
	ArrayList<String> getExtraUld();
/**
 * 
 * @param uldNumbers
 */
	void setMissingUld(ArrayList<String> uldNumbers);
/**
 * 
 * @return
 */
	ArrayList<String> getMissingUld();
/**
 * 
 * @param uldNumbers
 */
	void setSystemStock(Page<ULDVO> uldNumbers);            
/**
 * 
 * @return
 */
	Page<ULDVO> getSystemStock();
/**
 * 
 * @param filterVO
 */
	public void setMessageFilterVO(SCMMessageFilterVO filterVO);
/**
 * 
 * @return
 */
	public SCMMessageFilterVO getMessageFilterVO();
	
	/**
	 * The setter method for indexMap
	 * @param indexMap
	 */
	public void setIndexMap(
			HashMap<String, String> indexMap);
    /**
     * The getter method for indexMap
     * @return indexMap
     */
    public HashMap<String, String> getIndexMap();

     void setChangedSystemStock(Collection<ULDVO> uldNumbers);
     Collection<ULDVO> getChangedSystemStock();
     
     /**
 	 * 
 	 * @return
 	 */
 	public Collection<OneTimeVO> getFacilityType();

 	/**
 	 * 
 	 * @param contentType
 	 */
 	public void setFacilityType(Collection<OneTimeVO> facilityType);
	//added by a-3045 for bug 24468 starts
 	
 	public Collection<OneTimeVO> getUldStatusList();

 	/**
 	 * 
 	 * @param contentType
 	 */
 	public void setUldStatusList(Collection<OneTimeVO> uldStatusList);
 	/**
 	 * 
 	 * @param filterVO
 	 */
 		public void setULDListFilterVO(ULDListFilterVO filterVO);
 	/**
 	 * 
 	 * @return
 	 */
 		public ULDListFilterVO getULDListFilterVO();
	//added by a-3045 for bug 24468 ends
 	
 		/**
 		 * 
 		 * @return
 		 */
 		public Integer getTotalRecords();
 		
 		/**
 		 * 
 		 * @param totalRecords
 		 */
 		public void setTotalRecords(Integer totalRecords);
 		
 		public void setUpdatedULDStocks(HashMap<String, ULDVO> updatedULDs);
 		
 		public HashMap<String, ULDVO>  getUpdatedULDStocks();
 		
 		/**
 		 * 
 		 * @param newuldNumbers
 		 */ 
 		void setNewSystemStock(Collection<ULDVO> newuldNumbers);
 		
 		/**
 		 * 
 		 * @return
 		 */
 	     Collection<ULDVO> getNewSystemStock();
 	    /**
 		 * @return the keyListedDynamicQuery
 		 */
 		 String getKeyListedDynamicQuery();
 		 void setKeyListedDynamicQuery(String keyListedDynamicQuery);
}
