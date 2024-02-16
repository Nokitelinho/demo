/*
 * ListInvoicDetailsCommand.java Created on Nov 20 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.ux.invoicenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.InvoicFilter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.MailMRAModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.gpareporting.InvoicEnquiryModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.ux.invoicenquiry.ListInvoicDetailsCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-8464	:	20-Nov-2018	:	Draft
 */

public class ListInvoicDetailsCommand extends AbstractCommand{
	
    private Log logger = LogFactory.getLogger("Mail MRA ListInvoicDetailsCommand");
    
    private static final String MODULE_NAME = "mail.mra";
    private static final String SCREEN_ID = "mail.mra.gpareporting.ux.invoicenquiry";	
    private static final String STATUS_SUCCESS = "success";
    private static final String NO_DATA_FOUND = "No data found";
    private static final String USPS_INTERNATIONAL_PA = "mailtracking.defaults.uspsinternationalpa";
	private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";

	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException {
		
		logger.entering("ListInvoicDetailsCommand","execute");
		logger.log(Log.INFO,"MODULE_NAME: "+MODULE_NAME+" , SCREEN_ID:"+SCREEN_ID);
		
		Page<InvoicDetailsVO> invoicDetailsVOPage = null;
		//InvoicFilterVO invoicEnquiryFilterVO is the filter inputs required for list query
		InvoicFilterVO invoicEnquiryFilterVO = new InvoicFilterVO();
		InvoicEnquiryModel invoicEnquiryModel = (InvoicEnquiryModel) actionContext.getScreenModel();
		// InvoicFilter invoicEnquiryFilterFromUI is the filter from UI
		InvoicFilter invoicEnquiryFilterFromUI = (InvoicFilter)invoicEnquiryModel.getInvoicFilter();
		ResponseVO responseVO = new ResponseVO();
		ArrayList results = new ArrayList();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
		
		//Setting the values from UI filter to the filter to be input to the query
		List<ErrorVO> erros=(List<ErrorVO>)updateFilterVO(invoicEnquiryFilterFromUI, invoicEnquiryFilterVO, logonAttributes);
        if (erros!=null && erros.size()>0){        	
        	actionContext.addAllError(erros);
        	return; 
        }
        if(invoicEnquiryFilterVO.getGpaCode()!=null &&  invoicEnquiryFilterVO.getGpaCode().trim().length() > 0) {
        boolean isValidPaCode = validateuspsPacode(invoicEnquiryFilterVO.getGpaCode());
        if(!isValidPaCode){
        	actionContext.addError(new ErrorVO(NO_DATA_FOUND));		
			return;
        }
		}
        try{
  	      oneTimeValues = new SharedDefaultsDelegate().findOneTimeValues(logonAttributes.getCompanyCode(),getOneTimeParameterTypes());
        }catch (BusinessDelegateException e){
  	      actionContext.addAllError(handleDelegateException(e));
        }	 
        
        invoicDetailsVOPage = listInvoicDetails(invoicEnquiryFilterVO, invoicEnquiryFilterFromUI.getDisplayPage());
        
		if(invoicDetailsVOPage==null || invoicDetailsVOPage.size()==0){			
			actionContext.addError(new ErrorVO(NO_DATA_FOUND));		
			return;
		}
		
		else{		
			ArrayList invoicDetailsList = MailMRAModelConverter.constructInvoicDetails(invoicDetailsVOPage,oneTimeValues);
			PageResult pageList = new PageResult(invoicDetailsVOPage, invoicDetailsList);	
			invoicEnquiryModel.setCurrencyCode(invoicDetailsVOPage.get(0).getCurrencyCode());
			invoicEnquiryModel.setInvoicDetails(pageList);
			results.add(invoicEnquiryModel);
			responseVO.setResults(results);
			responseVO.setStatus(STATUS_SUCCESS);
			actionContext.setResponseVO(responseVO);
		}
		
		logger.exiting("ListInvoicDetailsCommand","execute");
        
	}
	
	
	 private Page<InvoicDetailsVO> listInvoicDetails(InvoicFilterVO invoicFilterVO, String displayPage) {
		 Page<InvoicDetailsVO> invoicDetailsVOs = null;
		 int pageNumber = Integer.parseInt(displayPage);
		 invoicFilterVO.setPageNumber(pageNumber);
	      try
	      {	
	    	  logger.log(Log.INFO, "Going to invoke listInvoicDetails with filter: " + invoicFilterVO);
	    	  invoicDetailsVOs = new MailTrackingMRADelegate().listInvoicDetails(invoicFilterVO);
	    	  logger.log(Log.INFO, "Result of listInvoicDetails from InvoicEnquiry screen: " + invoicDetailsVOs);
	      } catch (BusinessDelegateException businessDelegateException) {
	        businessDelegateException.getMessageVO().getErrors();
	        handleDelegateException(businessDelegateException);
	      }
		 
		 return invoicDetailsVOs;
	}


	private Collection<ErrorVO> updateFilterVO(InvoicFilter invoicEnquiryFilterFromUI, InvoicFilterVO invoicEnquiryFilterVO, LogonAttributes logonAttributes)
	    {
		
		ArrayList<ErrorVO> errors = new ArrayList<>();
		//error conditions can be added as per reqmt
		try{
		invoicEnquiryFilterVO.setFromDate(invoicEnquiryFilterFromUI.getFromDate());
		invoicEnquiryFilterVO.setGpaCode(invoicEnquiryFilterFromUI.getGpaCode());
		invoicEnquiryFilterVO.setInvoicId(invoicEnquiryFilterFromUI.getInvoicId());
		invoicEnquiryFilterVO.setMailbagId(invoicEnquiryFilterFromUI.getMailbagId());
		invoicEnquiryFilterVO.setSelectedProcessStatus(invoicEnquiryFilterFromUI.getSelectedProcessStatus());
		invoicEnquiryFilterVO.setToDate(invoicEnquiryFilterFromUI.getToDate());
		invoicEnquiryFilterVO.setSelectedProcessStatus(invoicEnquiryFilterFromUI.getSelectedProcessStatus());
		invoicEnquiryFilterVO.setSelectedClaimRange(invoicEnquiryFilterFromUI.getSelectedClaimRange());
		invoicEnquiryFilterVO.setOrg(invoicEnquiryFilterFromUI.getOrg());
		invoicEnquiryFilterVO.setDest(invoicEnquiryFilterFromUI.getDest());
		invoicEnquiryFilterVO.setMailSubClass(invoicEnquiryFilterFromUI.getMailSubClass());
		invoicEnquiryFilterVO.setTotalRecords(invoicEnquiryFilterFromUI.getTotalRecords());
		invoicEnquiryFilterVO.setDefaultPageSize(invoicEnquiryFilterFromUI.getDefaultPageSize());
		invoicEnquiryFilterVO.setCmpcod(logonAttributes.getCompanyCode());
		invoicEnquiryFilterVO.setProcessStatusFilter(invoicEnquiryFilterFromUI.getProcessStatusFilter());
		invoicEnquiryFilterVO.setInvoicfilterStatus(invoicEnquiryFilterFromUI.getInvoicfilterStatus());
		invoicEnquiryFilterVO.setFromScreen(invoicEnquiryFilterFromUI.getFromScreen());
		}
		catch(Exception e){
			errors.add(new ErrorVO(e.getMessage()));
		}
			return  errors;
	    }
	private boolean validateuspsPacode(String gpaCode){
		String paCode_int = "";
		String paCode_dom = "";
		Collection<String> systemParameters = new ArrayList<String>();
		Map<String, String> systemParameterMap=new HashMap<String, String>();
		systemParameters.add(USPS_INTERNATIONAL_PA);
		systemParameters.add(USPS_DOMESTIC_PA);
		try {
		systemParameterMap= new MailTrackingMRADelegate().validateuspsPacode(systemParameters);
		if (systemParameterMap != null) {
			paCode_int = systemParameterMap.get(USPS_INTERNATIONAL_PA);
			paCode_dom= systemParameterMap.get(USPS_DOMESTIC_PA);
		}
		if (paCode_int.equals(gpaCode) || paCode_dom.equals(gpaCode)) {
			return true;
		}
		}catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessageVO().getErrors();
			handleDelegateException(businessDelegateException);
		}
		return false;
	    }
	/**
	 * 
	 * 	Method		:	ListInvoicDetailsCommand.getOneTimeParameterTypes
	 *	Added by 	:	A-8061 on 17-Mar-2020
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<String>
	 */
	  private Collection<String> getOneTimeParameterTypes(){
		    Collection<String> parameterTypes = new ArrayList<String>();
		    parameterTypes.add(MRAConstantsVO.KEY_WGTUNITCOD_ONETIME);
		    return parameterTypes;
		  }

}
