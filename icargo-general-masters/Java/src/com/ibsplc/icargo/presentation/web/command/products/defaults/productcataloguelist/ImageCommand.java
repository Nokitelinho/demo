/*
 * ImageCommand.java Created on Jul 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.products.defaults.productcataloguelist;


import com.ibsplc.icargo.business.products.defaults.vo.ProductFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.framework.model.ImageModel;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.products.defaults.ProductDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.ListProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ProductCatalogueListForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * ScreenloadProductLovCommdand is for screenload action of productLov
 * @author a-1870
 *
 */
public class ImageCommand extends BaseCommand{
	
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
		ProductDefaultsDelegate productDefaultsDelegate =
				new ProductDefaultsDelegate();
		try {
			ProductVO productVO = productDefaultsDelegate.findImage(prdFilterVOs);
			if(productVO!=null){
			log.log(Log.FINE, "\n\n\n\n*****image in image command----------",
					productVO.getImage());
			ImageModel image=productVO.getImage();
			invocationContext.setImage( image );
			}
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			 handleDelegateException(businessDelegateException);
		}
			 
	}
	
	/**
	 *@param productCatalogueListForm
	 * @return ProductFilterVO
	 */
	private ProductFilterVO getSearchDetails(
			ProductCatalogueListForm productCatalogueListForm)
	{
		ProductFilterVO productFilterVO = new ProductFilterVO();
		String prdCode=productCatalogueListForm.getPrdCode();
		log.log(Log.FINE,
				"\n\n\n\n*****productCatalogueListForm.getPrdCode()----------",
				prdCode);
		productFilterVO.setCompanyCode(
				getApplicationSession().getLogonVO().getCompanyCode());
		productFilterVO.setProductCode(prdCode);
		return productFilterVO;
	}
	}
