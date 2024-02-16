/*
 * MaintainPrivilegeSessionImpl.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.products.defaults;

import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.business.products.defaults.vo.ProductFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.SubProductVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;

import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.ListSubProductSessionInterface;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1417
 *
 */
public class ListSubProductSessionImpl extends AbstractScreenSession
        implements ListSubProductSessionInterface {
	private static final String KEY_STATUS="status";
	private static final String KEY_PRIORITY="priority";
	private static final String KEY_TRANSPORTMODE="transportMode";
	private static final String KEY_SCREEN_ID = "products.defaults.listsubproducts";
	private static final String KEY_MODULE_NAME = "products.defaults";
	private static final String KEY_PAGESUBPRODUCTVO="pageSubProductVO";
	private static final String KEY_LIST = "ProductFilterVOs";
	private static final String KEY_BUTTONSTATUSFLAG = "ButtonStatusFlag";
	/**
     * This method returns the SCREEN ID for the List Sub product screen
     */
	 public String getScreenID(){
	        return KEY_SCREEN_ID;
	    }


    /**
     * This method returns the MODULE name for the List Sub product screen
     */
	 public String getModuleName(){
	        return KEY_MODULE_NAME;
	    }

	 /**
	     * This method is used to get the status from the session
	     * @return OneTimeVO
	     */
		public Collection<OneTimeVO>  getStatus(){
		    return (Collection<OneTimeVO>)getAttribute(KEY_STATUS);
		}

		/**
		 * This method is used to set the status in session
		 * @param status
		 */
		public void setStatus(Collection<OneTimeVO>  status) {
		    setAttribute(KEY_STATUS, (ArrayList<OneTimeVO>)status);
		}
		/**
		 * @return void
		 */
		public void removeStatus(){
			removeAttribute("KEY_STATUS");
		}
		/**
	     * This method is used to get the priority from the session
	     * @return OneTimeVO
	     */
		public Collection<OneTimeVO>  getPriority(){
		    return (Collection<OneTimeVO>)getAttribute(KEY_PRIORITY);
		}

		/**
		 * This method is used to set the priority in session
		 * @param priority
		 */
		public void setPriority(Collection<OneTimeVO>  priority) {
		    setAttribute(KEY_PRIORITY, (ArrayList<OneTimeVO>)priority);
		}
		/**
		 * @return void
		 */
		public void removePriority(){
			removeAttribute("KEY_PRIORITY");
		}
		/**
	     * This method is used to get the transportMode from the session
	     * @return OneTimeVO
	     */
		public Collection<OneTimeVO>  getTransportMode(){
		    return (Collection<OneTimeVO>)getAttribute(KEY_TRANSPORTMODE);
		}

		/**
		 * This method is used to set the transportMode in session
		 * @param transportMode
		 */
		public void setTransportMode(Collection<OneTimeVO>  transportMode) {
		    setAttribute(KEY_TRANSPORTMODE, (ArrayList<OneTimeVO>)transportMode);
		}
		/**
		 * @return void
		 */
		public void removeTransportMode(){
			removeAttribute("KEY_TRANSPORTMODE");
		}

		/**
	     * This method is used to get the ProductVO from the session
	     * @return Page<ProductVO>
	     */
		public Page<SubProductVO>  getPageSubProductVO(){
		    return (Page<SubProductVO>)getAttribute(KEY_PAGESUBPRODUCTVO);
		}

		/**
		 * This method is used to set the ProductVO in session
		 * @param pageSubProductVO
		 */
		public void setPageSubProductVO(Page<SubProductVO>  pageSubProductVO) {
		    setAttribute(KEY_PAGESUBPRODUCTVO, (Page<SubProductVO>)pageSubProductVO);
		}
		/**
		 * @return void
		 */
		public void removePageSubProductVO(){
			removeAttribute("KEY_PAGESUBPRODUCTVO");
		}
		
		//Added now
		
		public ProductFilterVO getProductFilterVODetails() {
			return (ProductFilterVO) getAttribute(KEY_LIST);

		}
		
		public void setProductFilterVODetails(ProductFilterVO productDetails) {
			setAttribute(KEY_LIST, (ProductFilterVO) productDetails);
		}

		public void removeProductFilterVODetails() {
		 	removeAttribute(KEY_LIST);
		 }
		/**
		 * @return void
		 */
		/*public void removeAllAttributes(){
			removeStatus();
			removePageSubProductVO();
			removeTransportMode();
			removePriority();
		}*/

		
		public String  getButtonStatusFlag(){
		    return (String)getAttribute(KEY_BUTTONSTATUSFLAG);
		}
		public void setButtonStatusFlag(String  buttonStatusFlag) {
		    setAttribute(KEY_BUTTONSTATUSFLAG, (String)buttonStatusFlag);
		}
		/**
		 * 
		 */
		public Integer getTotalRecordsCount() {
		    return (Integer)getAttribute("totalRecordsCount");
		  }
		/**
		 * 
		 */
		  public void setTotalRecordsCount(int totalRecordsCount) {
		    setAttribute("totalRecordsCount", Integer.valueOf(totalRecordsCount));
		  }
		  /**
		   * 
		   */
		  public void removeTotalRecordsCount() {
		    removeAttribute("totalRecordsCount");
		  }
		}


