/*
	 * ActivateProductCommand.java Created on Feb 20th, 2006
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.listproducts;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.products.defaults.ProductDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.ListProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ListProductForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * ActivateProductCommand class for activating the product
 * @author
 *
 */
public class ActivateProductCommand extends BaseCommand{
	private static final String ACTIVE = "Active";
	private static final String NEW = "New";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {


		ListProductSessionInterface session = getScreenSession( "product.defaults","products.defaults.listproducts");
		ListProductForm listProductForm = (ListProductForm)invocationContext.screenModel;
		Page<ProductVO> activate =session.getPageProductVO();
		Collection<ProductVO> productVO=new ArrayList<ProductVO>();
		String[] chk=listProductForm.getCheckBox();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		errors = validateForm(listProductForm);
		if(errors!=null && errors.size()>0){
			invocationContext.addAllError(errors);
			invocationContext.target = "activate_failure";
			return;
		}
		for(int i=0;i<chk.length;i++){
			for(ProductVO vo:activate){
				if(vo!=null){

					if(vo.getProductCode().equals(chk[i])){
						vo.setStatus("A");
						listProductForm.setMode("A");
						vo.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
						productVO.add(vo);

					}
				}
			}
		}
		try{
			ProductDefaultsDelegate productDefaultsDelegate = new ProductDefaultsDelegate();
			productDefaultsDelegate.updateProductStatus(productVO);


			

		}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		
		if (errors != null && errors.size() > 0) {
			//log.log(Log.FINE,"<---------------Save Failure---------->");
			invocationContext.addAllError(errors);
			
			invocationContext.target ="activate_failure";
			return;
			
		}

		invocationContext.target ="activate_success";

	}
	private Collection<ErrorVO> validateForm(ListProductForm listProductForm){


		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		ListProductSessionInterface session = getScreenSession( "product.defaults","products.defaults.listproducts");
		Page<ProductVO> pageProductVO =session.getPageProductVO();
		String[] chk=listProductForm.getCheckBox();
		if(chk.length==1){
			for(ProductVO productVO:pageProductVO){
				if(chk[0].equals(productVO.getProductCode())){
					if(ACTIVE.equals(productVO.getStatus())){
							error = new ErrorVO("products.defaults.cannotactivateproduct");
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);


					}
					else if(NEW.equals(productVO.getStatus())){
						if(!productVO.getIsRateDefined()){
							error = new ErrorVO("products.defaults.cannotactivatenproductnotratedefined");
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);

						}
					}
					break;
				}

			}
		}else{
			error = new ErrorVO("products.defaults.cannotactivatenproductmorethanone");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}


		return errors;
	}
}

