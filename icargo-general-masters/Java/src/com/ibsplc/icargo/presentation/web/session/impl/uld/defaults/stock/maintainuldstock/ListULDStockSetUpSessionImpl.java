/*
 * ListULDStockSetUpSessionImpl.java Created on Oct 10, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.stock.maintainuldstock;


import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.stock.maintainuldstock.ListULDStockSetUpSession;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockConfigVO;
import java.util.Collection;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author A-1496
 *
 */
public class ListULDStockSetUpSessionImpl extends AbstractScreenSession
		implements ListULDStockSetUpSession {

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID ="uld.defaults.maintainuldstock";
	
	private static final String KEY_ONETIMEVALUES = "oneTimeValues";

	private static final String KEY_STOCKDETAILS="ULDStockDetails";
	
	private static final String ULDSTOCKCONFIGVOS="ULDStockCongifVos";

	private static final String KEY_AIRLINECODE = "airlinecode";
	
	private static final String KEY_ULDNATURE = "uldnature";
	/**
	 * @return
	 */
	public String getScreenID(){

		return SCREENID;

	}
	/**
	 * @return
	 */
	public String getModuleName(){

		return MODULE;

	}
	/**
	 * @return
	 */
	public Collection<ULDStockConfigVO> getULDStockDetails() {
		return (Collection<ULDStockConfigVO>) getAttribute(KEY_STOCKDETAILS);

	}
/**
 * @param productDetails
 */

	public void setULDStockDetails(Collection<ULDStockConfigVO> productDetails) {
		setAttribute(KEY_STOCKDETAILS, (ArrayList<ULDStockConfigVO>) productDetails);
	}
/**
 * 
 */
	 public void removeULDStockDetails() {
	 	removeAttribute(KEY_STOCKDETAILS);
	 }
	 
	 //added by ayswarya
	 

	 /**
		 * @return
		 */
		public Collection<ULDStockConfigVO> getULDStockConfigVOs(){
			return (Collection<ULDStockConfigVO>) getAttribute(ULDSTOCKCONFIGVOS);
		}
		/**
		 * @param uldStockConfigVOs
		 */
		public void setULDStockConfigVOs(Collection<ULDStockConfigVO> uldStockConfigVOs){
			setAttribute(ULDSTOCKCONFIGVOS,(ArrayList<ULDStockConfigVO>) uldStockConfigVOs);
		}
		/**
		 * Methods for removing status
		 */
		public void removeULDStockConfigVOs(){
			removeAttribute(ULDSTOCKCONFIGVOS);
		}
		/**
		 * The setter for PARENT_SCREENIDR
		 * @param airLineCode
		 */
		public void setAirLineCode(String airLineCode) {
			setAttribute(KEY_AIRLINECODE, airLineCode);
		}
		/**
		 * @return
		 */
		public String getAirLineCode() {
			return getAttribute(KEY_AIRLINECODE);
		}
		/**
		 * Remove method for parentScreenId
		 */
		public void removeAirLineCode() {
			removeAttribute(KEY_AIRLINECODE);
		}
		
		/**
		 * @return Returns the oneTimeValues.
		 */
		public HashMap<String, Collection<OneTimeVO>> getOneTimeValues() {
			return getAttribute(KEY_ONETIMEVALUES);
		}

		/**
		 * @param oneTimeValues
		 *            The oneTimeValues to set.
		 */
		public void setOneTimeValues(
				HashMap<String, Collection<OneTimeVO>> oneTimeValues) {
			setAttribute(KEY_ONETIMEVALUES, oneTimeValues);
		}
		
		// Added by A-2412
		
		public String getUldNature() {
			return getAttribute(KEY_ULDNATURE);
		}
		public void setUldNature(String uldNature) {
			setAttribute(KEY_ULDNATURE, uldNature);
		}
}