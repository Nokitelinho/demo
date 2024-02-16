/*
 * StockListSessionImpl.java Created on Jan 17, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.stockcontrol.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.StockListSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1619
 *
 */
public class StockListSessionImpl extends AbstractScreenSession 
implements StockListSession {
	private static final String KEY_SCREEN_ID = "stockcontrol.defaults.cto.stocklist";
	private static final String KEY_MODULE_NAME = "stockcontrol.defaults";
	private static final String KEY_DOC="documentvalues";
	private static final String STK_DETAILS="stockdetails";
	private static final String KEY_INDEXMAP="index";
	private static final String KEY_FILTER="filtervo";
	private static final String KEY_VO="documentvo";
	private static final String KEY_LISTSTRING="fromlist";
	private static final String KEY_VOFROMLIST="vofromlist";
	private static final String KEY_TOTAL_RECORDS = "totalRecords";//Added by A-5221 as part from the ICRD-23107
	/**
     * This method returns the SCREEN ID for the List Product screen
     */


    public String getScreenID(){
        return KEY_SCREEN_ID;
    }

    /**
     * This method returns the MODULE name for the List Product screen
     */
    public String getModuleName(){
        return KEY_MODULE_NAME;
    }  
    /**
	 * @return  HashMap<String,Collection<String>>
	 */
    public HashMap<String,Collection<String>> getDocumentValues() {
    	return (HashMap<String,Collection<String>>)getAttribute(KEY_DOC);
    }
    
    /**
	 * This method is used to set the priority in session
	 * @param document
	 */
	public void setDocumentValues(HashMap<String,Collection<String>>  document) {
	    setAttribute(KEY_DOC, (HashMap<String,Collection<String>>)document);
	}
	/**
	 * @return void
	 */
	public void removeDocumentValues(){
		removeAttribute(KEY_DOC);
	}  
	
	/**
     * This method returns the embargodetailsvo in session
     * @return Page<EmbargoDetailsVO>
     */

	public Page<StockVO> getStockVOs(){
    	return (Page<StockVO>)getAttribute(STK_DETAILS);
    }
	/**
     * This method sets the embargodetailsvo in session
     * @param paramCode
     */
	public void setStockVOs(Page<StockVO> paramCode){
		setAttribute(STK_DETAILS, (Page<StockVO>)paramCode);
	}
	/**
     * This method removes the embargodetailsVos in session
     */
	public void removeStockVOs(){
		removeAttribute(STK_DETAILS);
	}
	
	/**
     * Method used to get indexMap
     * @return KEY_INDEXMAP - HashMap<String,String>
     */
	public HashMap<String,String>  getIndexMap(){
	    return (HashMap<String,String>)getAttribute(KEY_INDEXMAP);
	}

	/**
	 * Method used to set indexMap to session
	 * @param indexmap - HashMap<String,String>
	 */
	public void setIndexMap(HashMap<String,String>  indexmap) {
	    setAttribute(KEY_INDEXMAP, (HashMap<String,String>)indexmap);
	}
	/**
     * This method removes the indexMAp in session
     */
	public void removeIndexMap(){
		removeAttribute(KEY_INDEXMAP);
	}
	  	/**Method used to get indexMap
	     * @return StockFilterVO
	     */
		public StockFilterVO  getFilterVO(){
		    return (StockFilterVO)getAttribute(KEY_FILTER);
		}

		/**
		 * Method used to set indexMap to session
		 * @param stockFilterVO
		 */
		
		public void setFilterVO(StockFilterVO  stockFilterVO) {
		    setAttribute(KEY_FILTER, (StockFilterVO)stockFilterVO);
		}
		/**
	     * This method removes the indexMAp in session
	     */
		public void removeFilterVO(){
			removeAttribute(KEY_FILTER);
		}
		/** @return Collection<DocumentVO>
		 * 
		 */
		
		public Collection<DocumentVO>  getDocumentVO() {
			 return (Collection<DocumentVO>)getAttribute(KEY_VO);
			
		}

		/**
		 * Method used to set indexMap to session
		 * @param stockFilterVO
		 */
		public void setDocumentVO(Collection<DocumentVO>  stockFilterVO) {
			setAttribute(KEY_VO, (ArrayList<DocumentVO>)stockFilterVO);
			
		}
		/**
	     * This method removes the indexMAp in session
	     */
		public void removeDocumentVO() {
			removeAttribute(KEY_VO);
			
		}
		/**
		 * @return StockFilterVO 
		 */
		public StockFilterVO  getFilterFromList() {
			return (StockFilterVO)getAttribute(KEY_VOFROMLIST);
		}

		/**
		 * Method used to set indexMap to session
		 * @param stockFilterVO
		 */
		public void setFilterFromList(StockFilterVO  stockFilterVO) {
			setAttribute(KEY_VOFROMLIST, (StockFilterVO)stockFilterVO);
		}
		/**
	     * This method removes the indexMAp in session
	     */
		public void removeFilterFromList() {
			removeAttribute(KEY_VOFROMLIST);
		}
		/**
		 * @return String
		 */
		public String  getStringFromList() {
			return (String)getAttribute(KEY_LISTSTRING);
		}

		/**
		 * Method used to set indexMap to session
		 * @param stockFilterVO
		 */
		public void setStringFromList(String  stockFilterVO) {
			setAttribute(KEY_LISTSTRING, (String)stockFilterVO);
		}
		/**
	     * This method removes the indexMAp in session
	     */
		public void removeStringFromList() {
			removeAttribute(KEY_LISTSTRING);
		}
		
		
	 	/**
		  * Added by A-5221 as part from the ICRD-23107
		  * This method is used to set total records values in session
		  * @param int
		*/
		public void setTotalRecords(int totalRecords){
	      setAttribute(KEY_TOTAL_RECORDS, Integer.valueOf(totalRecords));
	   }
		
		/**
		  * Added by A-5221 as part from the ICRD-23107
		  * This method is used to get total records values from session
		  * from session
		  * @return Integer
		*/
		
		public Integer getTotalRecords() {
			return (Integer)getAttribute(KEY_TOTAL_RECORDS);
	 	}

}
