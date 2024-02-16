/*
 * EditRangeSessionImpl.java Created on Aug 30, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.stockcontrol.defaults;

import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.EditRangeSession;


/**
 * @author A-1927
 *
 */
public class EditRangeSessionImpl extends AbstractScreenSession
        implements EditRangeSession{

	private static final String KEY_SCREEN_ID_ER = "stockcontrol.defaults.editrange";
	private static final String KEY_MODULE_NAME_ER = "stockcontrol.defaults";
	private static final String KEY_ALLOC_RANGEVO_ER = "rangeAllocatedVO";
	private static final String KEY_AVAIL_RANGEVO_ER = "rangeAvailableVO";
	private static final String KEY_STOCKCONTROLFOR_ER="stockControlFor";

    /**
     * This method returns the SCREEN ID for the Maintain stock holder screen
     */
    public String getScreenID(){
        return KEY_SCREEN_ID_ER;
    }

    /**
     * This method returns the MODULE name for the Maintain stock  holder screen
     */
    public String getModuleName(){
        return KEY_MODULE_NAME_ER;
    }


    /**
	 * Thie method is used to get the RangeVOs
	 * from session
	 * @return RangeVO
	 */

	public Collection<RangeVO> getAllocatedRangeVos(){

		return (Collection<RangeVO>)getAttribute(KEY_ALLOC_RANGEVO_ER);
	}


	/**
	 * This method is used to set the RangeVOs in session
	 * @param allocatedRangeVos
	 */

	public void setAllocatedRangeVos(Collection<RangeVO> allocatedRangeVos){

		setAttribute(KEY_ALLOC_RANGEVO_ER,(ArrayList<RangeVO>)allocatedRangeVos);
	}


   /**
   	 * Thie method is used to get the RangeVOs
   	 * from session
   	 * @return RangeVO
   	 */

   	public Collection<RangeVO> getAvailableRangeVos(){

   		return (Collection<RangeVO>)getAttribute(KEY_AVAIL_RANGEVO_ER);
   	}


   	/**
   	 * This method is used to set the RangeVOs in session
   	 * @param availableRangeVos
   	 */

   	public void setAvailableRangeVos(Collection<RangeVO> availableRangeVos){

   		setAttribute(KEY_AVAIL_RANGEVO_ER,(ArrayList<RangeVO>)availableRangeVos);
   	}




   	 /**
	  * Thie method is used to get the stockControlFor
	  * from session
	  * @return stockControlFor
	  */

	 public String getStockControlFor(){
			return (String)getAttribute(KEY_STOCKCONTROLFOR_ER);
	}

	/**
	 * This method is used to set the stockControlFor in session
	 * @param stockControlFor
	 */
	 public void setStockControlFor(String stockControlFor){
			setAttribute(KEY_STOCKCONTROLFOR_ER,(String)stockControlFor);
	}


}
