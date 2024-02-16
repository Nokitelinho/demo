/*
 * ViewBillingLineSessionImpl.java Created on Mar 12, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.defaults;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ViewBillingLineSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;




/**
 * @author A-2398
 *
 */
public class ViewBillingLineSessionImpl	extends 
AbstractScreenSession implements ViewBillingLineSession {
   /**
    * Module Name
    */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

    /**
     * ScreenID
     */
    private static final String SCREENID = 
    	"mailtracking.mra.defaults.viewbillingline";
    
    /**
     * Session variable for OneTimeVOs 
     */
    private static final String KEY_ONETIME_VOs = "onetimevos";	
    /**
     * indexMap
     */
    public static final String KEY_INDEXMAP = "indexMap";
    
    /**
     * BillingLineVOs 
     */
    public static final String KEY_BLGLINDTL = "billingLineDetails";
    
    private static final String KEY_BILLINGLINEFILTERVO="billinglinefiltervo";
    
    private static final String KEY_PARENTSCREENID="parentscreenid";
    public static final String KEY_SELECTED_BLG_LINE= "selectedBillingLineVO";
    
     

	/** The Constant TOTAL_RECORDS_COUNT.
	 * Added by A-5497 */
	private static final String TOTAL_RECORDS_COUNT= "totalrecordscount";
	 


    /**
     * Default Constructor
     */
    public ViewBillingLineSessionImpl() {
        super();

    }

    
   
    /**
     * This method is used for PageAwareMultiMapper to get the Index Map
     * @return  HashMap<String,String>
     */
    public HashMap<String,String>getIndexMap(){
    	 return (HashMap<String,String>)getAttribute(KEY_INDEXMAP);
    }

    /**Sets the hashmap for Absolute index of page
     * @param indexMap
     */
    public void setIndexMap(HashMap<String,String>indexMap){
    	 setAttribute(KEY_INDEXMAP, (HashMap<String,String>)indexMap);
    }
    /**
     * Removes the hashmap for Absolute index of page
     *
     */
    public void removeIndexMap(){
    	removeAttribute(KEY_INDEXMAP);
    }
    
   
    /**
	 * 
	 * @return
	 */	 

	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs(){

		return getAttribute(KEY_ONETIME_VOs);

	}

	
	/**
	 * Method to set One time Vo Collection to the session
	 */
	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs){

		setAttribute(KEY_ONETIME_VOs, oneTimeVOs);

	}
/**@author A-2398
 * 
 *
 */
	public void removeOneTimeVOs() {

		removeAttribute(KEY_ONETIME_VOs);

	}
	
	/**
	 * 
	 * @return
	 */	 

	public Page<BillingLineVO> getBillingLineDetails(){

		return getAttribute(KEY_BLGLINDTL);

	}

	
	/**
	 * Method to set One time Vo Collection to the session
	 */
	public void setBillingLineDetails(Page<BillingLineVO> billingLineDetails){

		setAttribute(KEY_BLGLINDTL, billingLineDetails);

	}
/**@author A-2398
 * 
 *
 */
	public void removeBillingLineDetails() {

		removeAttribute(KEY_BLGLINDTL);

	}
	
    
    /**
     * @return String
     */
    @Override
    public String getModuleName() {
        return MODULE_NAME;
    } 
    /**
	 * @return
	 */
    @Override
    public String getScreenID() {

        return SCREENID;
    }



    /**
     * @return
     */
	public BillingLineFilterVO getBillingLineFilterVO() {		
		return getAttribute(KEY_BILLINGLINEFILTERVO);
	}



	/**
	 * @param billingLineFilterVO
	 */
	public void setBillingLineFilterVO(BillingLineFilterVO billingLineFilterVO) {
		setAttribute(KEY_BILLINGLINEFILTERVO,billingLineFilterVO);
		
	}



	/**
	 * 
	 */
	public void removeBillingLineFilterVO() {
		removeAttribute(KEY_BILLINGLINEFILTERVO);
		
	}



	/**
	 * @return
	 */
	public String getParentScreenId() {
		
		return getAttribute(KEY_PARENTSCREENID);
	}



	/**
	 * @param parentScreenId
	 */
	public void setParentScreenId(String parentScreenId) {
		setAttribute(KEY_PARENTSCREENID,parentScreenId);
		
	}



	/**
	 * 
	 */
	public void removeParentScreenId() {
		removeAttribute(KEY_PARENTSCREENID);
		
	}
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ViewBillingLineSession#getTotalRecordsCount()
	 *	Added by 			: A-5497 on 29-Apr-2014
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 */
	public Integer getTotalRecordsCount() {
		return (Integer)getAttribute(TOTAL_RECORDS_COUNT);
	}
	/**
	 * Overriding Method	:	@see com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ViewBillingLineSession#setTotalRecordsCount(int)
	 * Added by 			: A-5497 on 29-Apr-2014
	 * Used for 	:
	 * Parameters	:	@param totalRecordsCount
	 *
	 * @param totalRecordsCount the new total records count
	 */
	public void setTotalRecordsCount(int totalRecordsCount) {
		setAttribute(TOTAL_RECORDS_COUNT, Integer.valueOf(totalRecordsCount));
	}
	 /**
		 * 
		 * @param selectedBillingLine
		 */
		public void setSelectedBillingLine(BillingLineVO selectedBillingLine){
	    	setAttribute(KEY_SELECTED_BLG_LINE,selectedBillingLine);
	    }
		/**
		 * 
		 * @return
		 */
		public BillingLineVO getSelectedBillingLine(){
			return (BillingLineVO)getAttribute(KEY_SELECTED_BLG_LINE);
		}
	   
}
