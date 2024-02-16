/*
 * MaintainPrivilegeSessionInterface.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.products.defaults.vo.ProductFeedbackVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-1366
 *
 */
public interface ListProductSessionInterface extends ScreenSession {

    /*public ListProductsModel getListProductsModel();
    public void setListProductsModel(ListProductsModel listProductsModel);

    public ListProductsModel getProductLovModel();
    public void setProductLovModel(ListProductsModel listProductsModel);

    public void removeProductLovModel();*/
	/**
	 * Method for getting status from session
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO>  getStatus();
	/**
	 * Method for setting status to session
	 * @param status
	 */
	public void setStatus(Collection<OneTimeVO> status);
	/**
	 * Method for removing status from session
	 *
	 */
	public void removeStatus();
	/**
	 * Method for getting priority from session
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO>  getPriority();
	/**
	 * Method for setting priority to session
	 * @param priority
	 */
	public void setPriority(Collection<OneTimeVO> priority);
	/**
	 * method for removing priority from session
	 *
	 */
	public void removePriority();
	/**
	 * Method for getting transportmode from session
	 * @return Collection<OneTimeVO>
	 */
	public Collection<OneTimeVO>  getTransportMode();
	/**
	 * Method for setting transportmode to session
	 * @param transportMode
	 */
	public void setTransportMode(Collection<OneTimeVO> transportMode);
	/**
	 * Method for removing transportmode from session
	 *
	 */
	public void removeTransportMode();
	/**
	 * Method for getting getPageProductVO from session
	 * @return Page<ProductVO>
	 */
	public Page<ProductVO>   getPageProductVO();
	/**
	 * Method for setting getPageProductVO to session
	 * @param pageProductVO
	 */
	public void setPageProductVO(Page<ProductVO> pageProductVO);
	/**
	 * Method for removing getPageProductVO from session
	 *@return void
	 */
	public void removePageProductVO();

	/**
	 * Method for getting PageProductFeedbackVO from session
	 * @return Page<ProductFeedbackVO>
	 */
	public Page<ProductFeedbackVO>   getPageProductFeedbackVO();
	/**
	 * Method for setting PageProductFeedbackVO to session
	 * @param pageProductVO
	 */
	public void setPageProductFeedbackVO(Page<ProductFeedbackVO> pageProductVO);
	/**
	 * Method for removing PageProductFeedbackVO from session
	 *@return void
	 */
	public void removePageProductFeedbackVO();

	/**
	 * Method for getting ProductVO from session
	 * @return Page<ProductVO>
	 */
	public Page<ProductVO>   getPageProductCatalogueList();
	/**
	 * Method for setting ProductVO to session
	 * @param pageProductVO
	 */
	public void setPageProductCatalogueList(Page<ProductVO> pageProductVO);
	/**
	 * Method for removing ProductVO from session
	 *@return void
	 */
	public void removePageProductCatalogueList();

	public ProductFilterVO getFilterDetails();
	public void setFilterDetails(ProductFilterVO filterDetails);

	//Added now
	public ProductFilterVO getProductFilterVO();

    public void setProductFilterVO(ProductFilterVO productDetails);

    public void removeProductFilterVO();

    //Added new
    public HashMap getIndexMap();

    //Added new
    public void setIndexMap(HashMap indexMap);
    
    public abstract Integer getTotalRecords(); //added by A-5201 for CR ICRD-22065
    public abstract void setTotalRecords(int i); //added by A-5201 for CR ICRD-22065
    //Added for ICRD-166985 by A-5117 --starts
    /**
	 * Methods for ProdcutCategories  begins
	 */
	public Collection<OneTimeVO> getProductCategories();
	/**
	 * Methods for getting 
	 */
	public void setProductCategories(Collection<OneTimeVO> productCateogries);
	/**
	 * Methods for removing 
	 */
	public void removeProductCategories();
	 //Added for ICRD-166985 by A-5117 --ends

}
