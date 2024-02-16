/*
 * SaveCommand.java Created on Aug 26, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.feedback;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.products.defaults.vo.ProductFeedbackVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.products.defaults.ProductDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.FeedbackForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * ScreenloadProductLovCommdand is for screenload action of productLov
 * @author a-1870
 *
 */
public class SaveCommand extends BaseCommand{
	
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
		FeedbackForm feedbackForm = (FeedbackForm)invocationContext.screenModel;
		ProductFeedbackVO productFeedbackVO=saveDetails(feedbackForm);
		log.log(Log.FINE, "\n\n\n -----productFeedbackVO from client---->",
				productFeedbackVO);
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		errors = validateForm(feedbackForm);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = "save_failure";
			return;
		}
		
		try{
			ProductDefaultsDelegate productDefaultsDelegate = 
				new ProductDefaultsDelegate();
			productDefaultsDelegate.saveProductFeedback(productFeedbackVO);
			
		}catch(BusinessDelegateException businessDelegateException){
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}
		
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = "save_failure";
			return;
		}
		feedbackForm.setSaveSuccessful("Y");
		ErrorVO error = null;
		Object[] obj = { "Segment" };
		error = new ErrorVO("products.defaults.savesuccess", obj); //saved successfully
		error.setErrorDisplayType(ErrorDisplayType.INFO);
		errors.add(error);
		invocationContext.addAllError(errors);
		invocationContext.target="save_success";
		feedbackForm.setAddress("");
		feedbackForm.setEmail("");
		feedbackForm.setName("");
		feedbackForm.setComments("");
	}
	/**
	 * 
	 * @param feedbackForm
	 * @return ProductFeedbackVO
	 */
	private ProductFeedbackVO saveDetails(FeedbackForm feedbackForm)
	{
		ProductFeedbackVO productFeedbackVO = new ProductFeedbackVO();
		productFeedbackVO.setProductCode(feedbackForm.getCode());
		productFeedbackVO.setAddress(feedbackForm.getAddress());
		log.log(Log.FINE, "\n\n\n -----feedbackForm.getName()---->",
				feedbackForm.getName());
		productFeedbackVO.setName(feedbackForm.getName());
		productFeedbackVO.setEmailId(feedbackForm.getEmail());
		//Calendar lastUpdateDate =Calendar.getInstance(
				//getApplicationSession().getLogonVO().getTimeZone());
		productFeedbackVO.setLastupdatedTime(
				new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		productFeedbackVO.setLastupdatedUser(
				getApplicationSession().getLogonVO().getUserId());
		productFeedbackVO.setRemarks(feedbackForm.getComments());
		productFeedbackVO.setCompanyCode(
				getApplicationSession().getLogonVO().getCompanyCode());
		LocalDate date = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		productFeedbackVO.setFeedbackDate(date);
		return productFeedbackVO;
	}
	
	/**
	 * 
	 * @param form
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(FeedbackForm form){
		log.log(Log.FINE,"\n\n\n -----inside-validateForm---->" );
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if("".equals(form.getName())){
			log.log(Log.FINE,"\n\n\n -----inside-1 error---->" );
			ErrorVO error = null;
			error = new ErrorVO(
						"products.defaults.namecannotbenull");
		    error.setErrorDisplayType(ErrorDisplayType.ERROR);
		    errors.add(error);
		}
		if("".equals(form.getEmail())){
			log.log(Log.FINE,"\n\n\n -----inside-2 error---->" );
			ErrorVO error = null;
			error = new ErrorVO(
						"products.defaults.emailcannotbenull");
		    error.setErrorDisplayType(ErrorDisplayType.ERROR);
		    errors.add(error);
		}
		return errors;
	}
}
