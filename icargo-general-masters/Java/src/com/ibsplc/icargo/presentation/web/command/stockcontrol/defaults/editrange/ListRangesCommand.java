/*
 * ListRangesCommand.java Created on Sep 9, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.editrange;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.EditRangeSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.EditRangeForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1927
 *
 */
public class ListRangesCommand extends BaseCommand {

	private static final String BLANKSPACE = "";
	
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * The execute method in BaseCommand
	 * @author A-1927
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("ListRangesCommand","execute--->");
    	EditRangeForm editRangeForm = (EditRangeForm) invocationContext.screenModel;
		EditRangeSession session = (EditRangeSession)
								getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.editrange");

		ApplicationSessionImpl applicationSession = getApplicationSession();
	    LogonAttributes logonAttributesVO  =  applicationSession.getLogonVO();


		session.setAvailableRangeVos(null);
		Collection<ErrorVO> errors = null;
		if(editRangeForm.getNumberOfDocs() != null && 
				editRangeForm.getNumberOfDocs().trim().length()>0 &&
				//Edited as part of ICRD-46860 for accepting ALPHANUMERIC formats also
				Long.valueOf(editRangeForm.getNumberOfDocs())==0){
			editRangeForm.setNumberOfDocs(BLANKSPACE);
		}
		errors = validateForm(editRangeForm,session);

		if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = "screenload_failure";
				return;
		}


		String companyCode=logonAttributesVO.getCompanyCode();
		String stockHolderCode=upper(editRangeForm.getStockHolderCode());
		String documentType=editRangeForm.getDocType();
		String documentSubType=editRangeForm.getSubType();
		String startRange=editRangeForm.getRangeFrom();
		String endRange=editRangeForm.getRangeTo();
		String numberOfDocuments=editRangeForm.getNumberOfDocs();

		String[] stockRangeFrom=editRangeForm.getStockRangeFrom();
		String[] stockRangeTo=editRangeForm.getStockRangeTo();
		int iterator = 0;

		Collection<RangeVO> retainRangeVOs = session.getAllocatedRangeVos();

		if(retainRangeVOs!=null){
			for(RangeVO tempRange:retainRangeVOs){
				tempRange.setStartRange(stockRangeFrom[iterator]);
				tempRange.setEndRange(stockRangeTo[iterator]);
				iterator++;
			}
			session.setAllocatedRangeVos(retainRangeVOs);
		}


		RangeFilterVO rangeFilterVO=new RangeFilterVO();
		Collection<RangeVO> rangeVOs=null;

		rangeFilterVO.setCompanyCode(companyCode);
		rangeFilterVO.setStockHolderCode(upper(session.getStockControlFor()));
		rangeFilterVO.setDocumentType(documentType);
		rangeFilterVO.setDocumentSubType(documentSubType);
		rangeFilterVO.setStartRange(startRange);
		rangeFilterVO.setEndRange(endRange);
		rangeFilterVO.setNumberOfDocuments(numberOfDocuments);
		rangeFilterVO.setAirlineIdentifier(getApplicationSession().getLogonVO().getOwnAirlineIdentifier());

       try{
			StockControlDefaultsDelegate stockControlDefaultsDelegate=new StockControlDefaultsDelegate();
			rangeVOs=stockControlDefaultsDelegate.findRanges(rangeFilterVO);
		}
		catch(BusinessDelegateException businessDelegateException){

			//errorVos = handleDelegateException(businessDelegateException);
		}
		
		   if (rangeVOs == null || rangeVOs.size() == 0) {
			ErrorVO error = null;
			error = new ErrorVO("stockcontrol.defaults.norecordsfound");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			invocationContext.addAllError(errors);
			invocationContext.target = "screenload_failure";
			return;

		 }
		

		session.setAvailableRangeVos((Collection<RangeVO>)rangeVOs);
		log.exiting("ListRangesCommand","execute");
        invocationContext.target = "screenload_success";
    }


    private Collection<ErrorVO> validateForm(EditRangeForm form,
					EditRangeSession session) {

				Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
				ErrorVO error = null;
	/*		cOMMENTED by A-7740 AS A PART OF ICRD-225106
	 * 	if("".equals(form.getRangeFrom())){
							Object[] obj = { "Range-From" };
							error = new ErrorVO("stockcontrol.defaults.plsenterrangefrmval", obj);
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);
				}*/
				if("".equals(form.getNumberOfDocs())){
					if("".equals(form.getRangeTo())){
								Object[] obj = { "Range-To" };
								error = new ErrorVO("stockcontrol.defaults.plzenterrangetoval", obj);
								error.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(error);
					}
				}




			return errors;
	}

	private String upper(String input){

		if(input!=null){
			return input.trim().toUpperCase();
		}else{
			return "";
		}
	}
}
