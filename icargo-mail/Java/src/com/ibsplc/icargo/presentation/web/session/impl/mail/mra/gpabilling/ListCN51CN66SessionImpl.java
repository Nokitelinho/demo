/*
 * ListCN51CN66SessionImpl.java Created on Jan 10, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.gpabilling;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66VO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListCN51CN66Session;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1556
 *
 */
public class ListCN51CN66SessionImpl extends AbstractScreenSession implements ListCN51CN66Session {

private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private final static String SCREENID = "mailtracking.mra.gpabilling.listcn51cn66";

	private static final String KEY_CN51CN66DETAILS = "cn51cn66vos";

	private static final String KEY_CN51CN66FILTER = "cn51cn66filtervos";

	private static final String KEY_CN66DETAILS = "cn66details";

	private static final String KEY_CN51DETAILS = "cn51details";
	
	private static final String KEY_STATUS_ONETIME= "categoryonetimes";
	
	private static final String KEY_PARENTSCREENFLAG = "parentScreenId";
	private static final String KEY_INDEXMAP = "indexmap";
	private static final String KEY_TOTALRECORDS = "totalrecords";
	private static final String  TOTALRECORDS_Unlabelled = "totalRecordsunlabelled";
	
	private static final String KEY_SYSPARAMETERS="systemParameterByCodes";//Added for ICRD-189966
	/**
	 *This method is used to set indexMap to the session
	 * @param indexMap
	 */
	public void setIndexMap(HashMap indexMap){
		setAttribute( KEY_INDEXMAP,(HashMap<String,String>)indexMap);
	 }
	/**
	 * This method returns the hashmap
	 * 
	 * @return HashMap - HashMap<String,String>
	 */
	
	public HashMap getIndexMap(){
		return (HashMap<String,String>)getAttribute(KEY_INDEXMAP);
	}

	 /**
     * @return
     */
    public String getScreenID() {

        return SCREENID;
    }

    /**
     * @return
     */
    public String getModuleName() {
        return MODULE_NAME;
    }

    /**
     *
     */
    public ListCN51CN66SessionImpl() {
        super();

    } 
    /**
     * @param cN51CN66VO
     */
    public void setCN51CN66VO(CN51CN66VO cN51CN66VO){
    	setAttribute(KEY_CN51CN66DETAILS,cN51CN66VO);
    }
    /**
     * 
     */

    public CN51CN66VO getCN51CN66VO(){
    	return getAttribute(KEY_CN51CN66DETAILS);
    }

    /**
     * 
     */
    public void removeCN51CN66VO(){
    	removeAttribute(KEY_CN51CN66DETAILS);
    }
    /**
     * @param cn51CN66FilterVO
     */
    
    
    public void setCN51CN66FilterVO(CN51CN66FilterVO cn51CN66FilterVO){
    	setAttribute(KEY_CN51CN66FILTER,cn51CN66FilterVO);
    }
    /**
     * 
     */

    public CN51CN66FilterVO getCN51CN66FilterVO(){
    	return getAttribute(KEY_CN51CN66FILTER);
    }

   /**
    * 
    */

    public void removeCN51CN66FilterVO(){
    	removeAttribute(KEY_CN51CN66FILTER);
    }
    /**
     * Method to setCN66VOs
     * @param cN66detailsVOs
     */
    
  
    public void setCN66VOs(Page<CN66DetailsVO> cN66detailsVOs) {

		setAttribute( KEY_CN66DETAILS,cN66detailsVOs );

	}
    /**
     * Method to getCN66VOs
     * @return Page<CN66DetailsVO>
     */
    public Page<CN66DetailsVO> getCN66VOs() {

		return (Page<CN66DetailsVO>) getAttribute(KEY_CN66DETAILS);
	}



    /**
     * 
     */

    public void removeCN66VOs(){
    	removeAttribute(KEY_CN66DETAILS);
    }

/**
 * Method to getCN51DetailsVOs
 * @return Page<CN51DetailsVO>
 */
    

	public Page<CN51DetailsVO> getCN51DetailsVOs(){
		return (Page<CN51DetailsVO>)getAttribute(KEY_CN51DETAILS);
	}
	
/**
 * Method to setCN51DetailsVOs
 * @param cn51DetailsVOs
 */
	public void setCN51DetailsVOs(Page<CN51DetailsVO> cn51DetailsVOs){
		setAttribute(KEY_CN51DETAILS, cn51DetailsVOs);	}
	/**
	 * Method to remove HashMap<String, Collection<AccountingEntryDetailsVO>>
	 */
	public void removeCN51DetailsVOs(){
		removeAttribute(KEY_CN51DETAILS);
	}
	
	
	 /**
     * @return HashMap
     */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs(){

	     return getAttribute(KEY_STATUS_ONETIME);

	     }
	     /**

	     *  sets onetimes

	     * @param oneTimeVOs

	     */
	     public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs){

	     setAttribute(KEY_STATUS_ONETIME, oneTimeVOs);

	     }
	     /**

	     *

	     *remove onetimes

	     */

	     public void removeOneTimeVOs() {

	     removeAttribute(KEY_STATUS_ONETIME);
	     }
	     
	     /**
	 	 * Method to return ParentScreenFlag
	 	 * @return parentScreenFlag
	 	 */
	 	public String getParentScreenFlag(){
	 		return getAttribute(KEY_PARENTSCREENFLAG);
	 	}
	 	/**
	 	 * Method to set ParentScreenFlag
	 	 * @param parentScreenFlag
	 	 */
	 	public void setParentScreenFlag(String parentScreenFlag){
	 		setAttribute(KEY_PARENTSCREENFLAG, parentScreenFlag);
	 	}
	 	/**
	 	 * Method to remove ParentScreenFlag
	 	 */
	 	public void removeParentScreenFlag(){
	 		removeAttribute(KEY_PARENTSCREENFLAG);
	 	}
	     
	     
	 	public Integer getTotalRecords() {
			
			return getAttribute(KEY_TOTALRECORDS);
		}
		
		
		public void setTotalRecords(int totalRecords) {
			
			setAttribute(KEY_TOTALRECORDS, totalRecords);
		}  

		/**
	     * This method removes the totalRecords in session
	     */
		public void removeTotalRecords() {
		 	removeAttribute(KEY_TOTALRECORDS);
		}
	 	
		 /**
		 * @return Returns the totalRecords.
		 */
		public Integer getTotalRecordsUnlabelled() {
			return getAttribute(TOTALRECORDS_Unlabelled);
		}
		
		/**
		 * @param totalRecords The totalRecords to set.
		 */
		public void setTotalRecordsUnlabelled(int totalRecordsUnlabelled) {
			setAttribute(TOTALRECORDS_Unlabelled,totalRecordsUnlabelled);
		}
		
		/**
	     * This method removes the totalRecords in session
	     */
		public void removeTotalRecordsUnlabelled() {
		 	removeAttribute(TOTALRECORDS_Unlabelled);
		}
		 /**
		 * Method to return HashMap<String, Collection<CN51DetailsVO>>
		 * @return HashMap<String, Collection<CN51DetailsVO>>
		 */
		public HashMap<String, Collection<CN51DetailsVO>> getCN51VO(){
			return (HashMap<String, Collection<CN51DetailsVO>>)getAttribute(KEY_CN51DETAILS);
		}
		/**
		 * Method to set HashMap<String, Collection<CN51DetailsVO>>
		 * @param cn51DetailsVOs
		 */
		public void setCN51VO(HashMap<String,
				Collection<CN51DetailsVO>> cn51DetailsVOs){
			setAttribute(KEY_CN51DETAILS, cn51DetailsVOs);
		}
		/***
		 * 
		 */
		/**
		 * Set system parameters from session
		 */
		public HashMap<String, String> getSystemparametres()
		{
			return getAttribute(KEY_SYSPARAMETERS);
		}
		/**
		 * Get system parameters from session
		 */
		public void setSystemparametres(HashMap<String, String> sysparameters)
		{
			setAttribute(KEY_SYSPARAMETERS, sysparameters);
		}
		/**
		 * Remove system parameters from session
		 */
	     public void removeSystemparametres() {

	     removeAttribute(KEY_SYSPARAMETERS);
	     }

}
