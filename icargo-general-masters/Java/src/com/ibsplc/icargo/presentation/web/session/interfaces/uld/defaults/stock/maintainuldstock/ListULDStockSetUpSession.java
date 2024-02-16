/*
 * ListULDStockSetUpSession.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.stock.maintainuldstock;


import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigVO;
import java.util.Collection;
import java.util.HashMap;


/**
 * @author A-2105
 *
 */
public interface ListULDStockSetUpSession extends ScreenSession {
/**
 * 
 * @return
 */
     public String getScreenID();
/**
 * 
 * @return
 */
     public String getModuleName();
/**
 * 
 * @return
 */
     public Collection<ULDStockConfigVO> getULDStockDetails();
/**
 * 
 * @param uldStockDetails
 */
	 public void setULDStockDetails(Collection<ULDStockConfigVO> uldStockDetails);
/**
 * 
 *
 */
	 public void removeULDStockDetails();

	 /**
		 * Methods for getting status
		 */
		public Collection<ULDStockConfigVO> getULDStockConfigVOs();
		
		/**
		 * Methods for setting status
		 */
		public void setULDStockConfigVOs(Collection<ULDStockConfigVO> uldStockConfigVOs);
		
		/**
		 * Methods for removing status
		 */
		public void removeULDStockConfigVOs();
		
		/**
		 * The setter for PARENT_SCREENIDR
		 * @param parentScreenId
		 */
		public void setAirLineCode(String airLineCode);
		
		/**
		 * The getter for PARENT_SCREENIDR
		 * @return string parentScreenId
		 */
		public String getAirLineCode();
		
		/**
		 * Remove method for parentScreenId
		 */
		public void removeAirLineCode();
		
		/**
		 * @return Returns the oneTimeValues.
		 */
		public HashMap<String, Collection<OneTimeVO>> getOneTimeValues();

		/**
		 * @param oneTimeValues
		 *            The oneTimeValues to set.
		 */
		public void setOneTimeValues(
				HashMap<String, Collection<OneTimeVO>> oneTimeValues);
		
		//Added by A-2412
		
		public void setUldNature(String airLineCode);
		public String getUldNature();
		
}
