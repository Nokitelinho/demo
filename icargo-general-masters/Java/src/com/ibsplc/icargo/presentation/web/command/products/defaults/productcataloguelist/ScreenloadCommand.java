/*
 * ScreenloadCommand.java Created on Jun 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.products.defaults.productcataloguelist;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.products.defaults.vo.ProductFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.products.defaults.ProductDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.ListProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ProductCatalogueListForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * ScreenloadProductLovCommdand is for screenload action of productLov
 * @author a-1870
 *
 */
public class ScreenloadCommand extends BaseCommand{
	//private static final String COMPANY_CODE = "AV";
	/**
     * Log
     */
    private Log log = LogFactory.getLogger("PRODUCTS.DEFAULTS");
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		ListProductSessionInterface session = 
			getScreenSession(
					"product.defaults","products.defaults.listproducts");
		ProductCatalogueListForm productCatalogueListForm = 
			(ProductCatalogueListForm)invocationContext.screenModel;
		ProductFilterVO prdFilterVOs=getSearchDetails(productCatalogueListForm);
		ProductFilterVO newprdFilterVOs =new  ProductFilterVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		// Added by A-5183 for < ICRD-22065 > Starts	
			
			if(ProductCatalogueListForm.PAGINATION_MODE_FROM_LIST.equals(productCatalogueListForm.getNavigationMode())
					|| productCatalogueListForm.getNavigationMode() == null){
				
				prdFilterVOs.setTotalRecords(-1);
				productCatalogueListForm.setDisplayPage("1");
			
			}else if(ProductCatalogueListForm.PAGINATION_MODE_FROM_NAVIGATION_LINK.equals(productCatalogueListForm.getNavigationMode()))
			{				
				prdFilterVOs.setTotalRecords(session.getTotalRecords());				
				
				
			}			
			
			// Added by A-5183 for < ICRD-22065 > Ends
			int displayPage=Integer.parseInt(productCatalogueListForm.getDisplayPage());
		
		try{
			ProductDefaultsDelegate productDefaultsDelegate = new ProductDefaultsDelegate();
			
			Page<ProductVO> page =productDefaultsDelegate.findProducts(prdFilterVOs,displayPage);
			
		
			ProductVO productVO = null;
			
			for(ProductVO p : page){
				
				log.log(Log.FINE, "p.getCompanyCode()", p.getCompanyCode());
				log.log(Log.FINE, "p.getProductCode", p.getProductCode());
				newprdFilterVOs =new  ProductFilterVO();
				newprdFilterVOs.setCompanyCode(p.getCompanyCode());
				newprdFilterVOs.setProductCode(p.getProductCode());
				
				log.log(Log.FINE,"teste");
				productVO = productDefaultsDelegate.findImage(newprdFilterVOs);
				log.log(Log.FINE,"Condition test");
				log.log(Log.FINE, "productVO", productVO);
				if(productVO!= null){
					p.setFinalService("true");
					
				}else{
					p.setFinalService("false");
					
				}
				
				
			}
			
			
			if(page==null || page.size()==0){

				ErrorVO error = null;
				error = new ErrorVO("products.defaults.noproductsfound");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				if(errors!=null && errors.size()>0){
					productCatalogueListForm.setPageProductCatalogue(null);
					invocationContext.addAllError(errors);
					invocationContext.target = "list_failure";
					return;
				}
			}
			
			
			
			
			
			productCatalogueListForm.setPageProductCatalogue(page);
			
			session.setPageProductCatalogueList(page);
			session.setTotalRecords(page.getTotalRecordCount());
		}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
//printStackTrrace()();
			errors = handleDelegateException(businessDelegateException);
		}
		invocationContext.target="screenload_success";
	}
	/**
	 *@param productCatalogueListForm
	 * @return ProductLovFilterVO
	 */
	private ProductFilterVO getSearchDetails(ProductCatalogueListForm productCatalogueListForm)
	{
		ProductFilterVO productFilterVO = new ProductFilterVO();
		productFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		return productFilterVO;
	}
}
