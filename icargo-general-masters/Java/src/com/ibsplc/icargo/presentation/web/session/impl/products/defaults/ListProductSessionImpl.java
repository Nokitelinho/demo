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
import java.util.HashMap;

import com.ibsplc.icargo.business.products.defaults.vo.ProductFeedbackVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;

import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.ListProductSessionInterface;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1417
 *
 */
public class ListProductSessionImpl extends AbstractScreenSession
        implements ListProductSessionInterface {

	private static final String KEY_STATUS="status";
	private static final String KEY_PRIORITY="priority";
	private static final String KEY_TRANSPORTMODE="transportMode";
	private static final String KEY_SCREEN_ID = "products.defaults.listproducts";
	private static final String KEY_MODULE_NAME = "products.defaults";
	private static final String KEY_PAGEPRODUCTVO="pageProductVO";
	private static final String KEY_PAGEPRODUCTFEEDBACKVO="pageProductFeedbackVO";
	private static final String KEY_PAGEPRODUCTCATALOGUE="pageProVO";
	private static final String KEY_FILTERDETAILS="productFilterDetails";
	private static final String KEY_LISTPRODUCT = "ProductFilterVO";

	public static final String KEY_INDEXMAP = "indexMap";
	
	private static final String TOTAL_RECORDS = "totalRecords";//added by A-5201 for CR ICRD-22065
	
	private static final String PRDCTG_ONE_TIME = "productcategoryonetime";//Added for ICRD-166985 by A-5117


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
		removeAttribute(KEY_STATUS);
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
		removeAttribute(KEY_PRIORITY);
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
		removeAttribute(KEY_TRANSPORTMODE);
	}

	/**
     * This method is used to get the ProductVO from the session
     * @return Page<ProductVO>
     */
	public Page<ProductVO>  getPageProductVO(){
	    return (Page<ProductVO>)getAttribute(KEY_PAGEPRODUCTVO);
	}

	/**
	 * This method is used to set the ProductVO in session
	 * @param pageProductVO
	 */
	public void setPageProductVO(Page<ProductVO>  pageProductVO) {
	    setAttribute(KEY_PAGEPRODUCTVO, (Page<ProductVO>)pageProductVO);
	}
	/**
	 * @return void
	 */
	public void removePageProductVO(){
		removeAttribute(KEY_PAGEPRODUCTVO);
	}

	/**
     * This method is used to get the ProductFeedbackVO from the session
     * @return Page<ProductFeedbackVO>
     */
	public Page<ProductFeedbackVO>  getPageProductFeedbackVO(){
	    return (Page<ProductFeedbackVO>)getAttribute(KEY_PAGEPRODUCTFEEDBACKVO);
	}

	/**
	 * This method is used to set the ProductFeedbackVO in session
	 * @param pageProductFeedbackVO
	 */
	public void setPageProductFeedbackVO(Page<ProductFeedbackVO>  pageProductFeedbackVO) {
	    setAttribute(KEY_PAGEPRODUCTFEEDBACKVO, (Page<ProductFeedbackVO>)pageProductFeedbackVO);
	}
	/**
	 * @return void
	 */
	public void removePageProductFeedbackVO(){
		removeAttribute(KEY_PAGEPRODUCTFEEDBACKVO);
	}
	/**
     * This method is used to get the ProductVO from the session
     * @return Page<ProductVO>
     */
	public Page<ProductVO>  getPageProductCatalogueList(){
	    return (Page<ProductVO>)getAttribute(KEY_PAGEPRODUCTCATALOGUE);
	}

	/**
	 * This method is used to set the ProductVO in session
	 * @param pageProVO
	 */
	public void setPageProductCatalogueList(Page<ProductVO>  pageProVO) {
	    setAttribute(KEY_PAGEPRODUCTCATALOGUE, (Page<ProductVO>)pageProVO);
	}
	/**
	 * @return void
	 */
	public void removePageProductCatalogueList(){
		removeAttribute(KEY_PAGEPRODUCTCATALOGUE);
	}
	/**
	 * @return void
	 */
	/*public void removeAllAttributes(){
		removeStatus();
		removePageProductVO();
		removeTransportMode();
		removePriority();
	}*/



	public void setFilterDetails(ProductFilterVO  productFilterDetails) {
	    setAttribute(KEY_FILTERDETAILS, (ProductFilterVO)productFilterDetails);
	}
	public ProductFilterVO  getFilterDetails(){
	    return (ProductFilterVO)getAttribute(KEY_FILTERDETAILS);
	}

	//added now
	public ProductFilterVO getProductFilterVO() {
		return (ProductFilterVO) getAttribute(KEY_LISTPRODUCT);

	}

	public void setProductFilterVO(ProductFilterVO productDetails) {
		setAttribute(KEY_LISTPRODUCT, (ProductFilterVO) productDetails);
	}

	public void removeProductFilterVO() {
	 	removeAttribute(KEY_LISTPRODUCT);
	 }


    /**
	 * get indexmap
	 * @return HashMap
	 */
     public HashMap getIndexMap(){
		return (HashMap<String,String>)getAttribute(KEY_INDEXMAP);
	  }



	   /**
		 * set index map
		 * @param indexMap
		 */
	 public void setIndexMap(HashMap indexMap){
		setAttribute( KEY_INDEXMAP,(HashMap<String,String>)indexMap);
	  }
	 
	//added by A-5201 for CR ICRD-22065 starts
	 
		/**
		  * This method is used to get total records values from session
		  * from session
		  * @return Integer
		*/
		  public Integer getTotalRecords()
		    {
		        return (Integer)getAttribute(TOTAL_RECORDS);
		    }
		  
		  /**
			  * This method is used to set total records values in session
			  * @param int
			*/
		    public void setTotalRecords(int totalRecords)
		    {
		        setAttribute(TOTAL_RECORDS, Integer.valueOf(totalRecords));
		    }
		    
		  //added by A-5201 for CR ICRD-22065 end
		   
		    //Added for ICRD-166985 by A-5117 --starts
		    public Collection<OneTimeVO> getProductCategories(){
				return (Collection<OneTimeVO>)getAttribute(PRDCTG_ONE_TIME);
			}
			/**
			 * @param prty
			 * @return 
			 * */
			public void setProductCategories(Collection<OneTimeVO> productCategories){
				setAttribute(PRDCTG_ONE_TIME, (ArrayList<OneTimeVO>)productCategories);
			}
			/**
			 * @param 
			 * @return 
			 * */
			public void removeProductCategories(){
				removeAttribute(PRDCTG_ONE_TIME);
			}
			//Added for ICRD-166985 by A-5117 -->> ends
}
