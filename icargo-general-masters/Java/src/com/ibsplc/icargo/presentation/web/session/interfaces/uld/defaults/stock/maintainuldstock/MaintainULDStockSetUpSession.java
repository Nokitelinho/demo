/*
 * MaintainULDStockSetUpSession.java Created on Aug 1, 2005
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
public interface MaintainULDStockSetUpSession extends ScreenSession {

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
     public Collection<ULDStockConfigVO> getMaintainULDStockDetails();
/**
 * 
 * @param uldStockDetails
 */
	 public void setMaintainULDStockDetails(Collection<ULDStockConfigVO> uldStockDetails);
	 
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



}
