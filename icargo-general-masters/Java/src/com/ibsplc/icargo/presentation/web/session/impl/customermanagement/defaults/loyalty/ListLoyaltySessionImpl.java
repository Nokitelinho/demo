/*
 * ListLoyaltySessionImpl.java Created on Apr 23, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.customermanagement.defaults.loyalty;


import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeFilterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.ListLoyaltySession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;



/**
 * @author A-1862
 *
 */

public class ListLoyaltySessionImpl extends AbstractScreenSession
		implements ListLoyaltySession {

	private static final String MODULE = "customermanagement.defaults";

	private static final String SCREENID =
		"customermanagement.defaults.listloyalty";
	private static final String KEY_LOYALTYPGM = "loyaltyProgrammeVO";
	
	private static final String KEY_FILTERVO = "filterVO";
	
	private static final String KEY_SCREENID = "screenid";
	
	
	private static final String KEY_PAGEURL="pageurl";
	
	private static final String KEY_ONETIMEVALUES
	= "customermanagement_defaults_listloyalty_onetimevalues";	
	
	 //added by a-5203
	private static final String TOTALRECORDS = "totalRecords";
	
	public Integer getTotalRecords()
    {
        return (Integer)getAttribute(TOTALRECORDS);
    }

    public void setTotalRecords(int totalRecords)
    {
        setAttribute(TOTALRECORDS, Integer.valueOf(totalRecords));
        
    }
    //end
    /**
	 * 
	 */
	public String getScreenID() {
		return SCREENID;
	}

	/**
	 * 
	 */
	public String getModuleName() {
		return MODULE;
	}
	
		/**
		 * @param screenId
		 */ 
	public void setParentScreenId(String screenId) {
		setAttribute(KEY_SCREENID, screenId);
	}
	/**
	 * 
	 */
	public String getParentScreenId() {
		return getAttribute(KEY_SCREENID);
	}
	/**
	 * 
	 */
	public void removeParentScreenId() {
		removeAttribute(KEY_SCREENID);
	}
/**
 * 
 */
	public String getPageURL(){
		return getAttribute(KEY_PAGEURL);
	}
	/**
	 * @param pageUrl
	 */
	public void setPageURL(String pageUrl){
		setAttribute(KEY_PAGEURL,pageUrl);
	}
	
   
   /**
    * @return Page<LoyaltyProgrammeVO>
    */
	public Page<LoyaltyProgrammeVO> getLoyaltyDetails() {
		return (Page<LoyaltyProgrammeVO>)getAttribute(KEY_LOYALTYPGM);
	}
/**
 * @param loyaltyDetails
 */
	public void setLoyaltyDetails(Page<LoyaltyProgrammeVO> loyaltyDetails) {
		 setAttribute(KEY_LOYALTYPGM,
				 (Page<LoyaltyProgrammeVO>)loyaltyDetails);
	}
	/**
	 * 
	 */
	public LoyaltyProgrammeFilterVO getFilterVO() {
		return (LoyaltyProgrammeFilterVO)getAttribute(KEY_FILTERVO);
	}
	/**
	 * @param loyaltyProgrammeFilterVO
	 */
	public void setFilterVO(LoyaltyProgrammeFilterVO loyaltyProgrammeFilterVO) {
		 setAttribute(KEY_FILTERVO,loyaltyProgrammeFilterVO);
	}
	/**
	 * 
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeValues() {
        return getAttribute(KEY_ONETIMEVALUES);
    }
   /**
    * @param oneTimeValues
    */
    public void setOneTimeValues(
    		HashMap<String, Collection<OneTimeVO>> oneTimeValues) {
    	setAttribute(KEY_ONETIMEVALUES, oneTimeValues);
    }
}
