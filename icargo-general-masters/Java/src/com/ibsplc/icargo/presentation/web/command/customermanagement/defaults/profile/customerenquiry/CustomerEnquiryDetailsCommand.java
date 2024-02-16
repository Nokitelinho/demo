/*
 * CustomerEnquiryDetailsCommand.java Created on Jul 02, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customerenquiry;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.CustomerEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.CustomerEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * 
 * @author a-2883
 *
 */
public class CustomerEnquiryDetailsCommand extends BaseCommand{

	private static final String SCREENID = "customermanagement.defaults.customerenquiry";
	private static final String MODULENAME = "customermanagement.defaults";
	private static final String SUCCESS = "success";
	private static final String FAILURE = "failure";
	private static final String BLANK = "";
	private Log log = LogFactory.getLogger("CUSTOMERMANAGEMENT");
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		
		log.entering("CUSTOMER", "CustomerEnquiryDetailsCommand");
		CustomerEnquiryForm form = (CustomerEnquiryForm)invocationContext.screenModel;
		Collection<ErrorVO> errors =new ArrayList<ErrorVO>();
		CustomerEnquirySession session = 
			(CustomerEnquirySession)getScreenSession(MODULENAME,SCREENID);
		
		
		if(form.getCustomerCode() == null ||
				form.getCustomerCode().trim().length()==0){
			errors.add(new ErrorVO("customermanagement.defaults.custenquiry.msg.err.customercodemandatory"));
		}
		if(form.getFromDate() == null ||
				form.getFromDate().trim().length()==0){
			errors.add(new ErrorVO("customermanagement.defaults.custenquiry.msg.err.fromdatemandatory"));
		}
		if(form.getToDate() == null ||
				form.getToDate().trim().length()==0){
			errors.add(new ErrorVO("customermanagement.defaults.custenquiry.msg.err.todatemandatory"));
		}
		
		if(errors.size() > 0){
			invocationContext.addAllError(errors);
			invocationContext.target = FAILURE;
			return;
		} 
		
		errors = validateDates(form);
		if(errors.size()> 0){
			invocationContext.addAllError(errors);
			invocationContext.target = FAILURE;
			return;
		}
		
		
		CustomerMgmntDefaultsDelegate delegate = new CustomerMgmntDefaultsDelegate();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		CustomerFilterVO customerFilterVO = new CustomerFilterVO();
		CustomerVO customerVO = null;
		customerFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		if(form.getCustomerCode() != null && form.getCustomerCode().trim().length() >0){
			customerFilterVO.setCustomerCode(form.getCustomerCode());
			log.log(Log.FINE, " \n CustomerEnquiryDetailsCommand",
					customerFilterVO);
		}else{
			invocationContext.target = FAILURE;
			errors.add(new ErrorVO("customermanagement.defaults.custenquiry.msg.err.customercodemandatory"));
			invocationContext.addAllError(errors);
			return;
		}
		try {
			customerVO = delegate.customerEnquiryDetails(customerFilterVO);
		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
			invocationContext.addAllError(errors);
		}
		
		if(customerVO == null || errors.size() > 0){
			form.setEnquiryScreen("N");
			invocationContext.target = FAILURE;
			errors.add(new ErrorVO("customermanagement.defaults.norecordsfound"));
			invocationContext.addAllError(errors);
			return;
		}else{
			log.log(Log.FINE, "CustomerVo", customerVO);
			customerVO.setFromDate(form.getFromDate());
			customerVO.setToDate(form.getToDate());
			session.setEnquiryDetails(customerVO);
			form.setEnquiryScreen("Y");
			if(customerVO.getCustomerName() != null){
				form.setCustomerName(customerVO.getCustomerName());
			}else{
				form.setCustomerName(BLANK);
			}
			if(customerVO.getCompanyCode() != null ){
				form.setCompanyCode(customerVO.getCompanyCode());
			}else{
				form.setCompanyCode(BLANK);
			}
			if(customerVO.getCustomerCode() != null){
				form.setCustomerCode(customerVO.getCustomerCode());
			}else{
				form.setCustomerCode(BLANK);
			}
			if(customerVO.getStationCode() != null){
				form.setStation(customerVO.getStationCode());
			}else{
				form.setStation(BLANK);
			}
			if(customerVO.getStatus() != null &&
					"A".equals(customerVO.getStatus())){
				form.setStatus("Active");
			}else{
				form.setStatus("Inactive");
			}
			if(customerVO.getCustomerGroup() != null){
				form.setCustomerGroup(customerVO.getCustomerGroup());
			}else{
				form.setCustomerGroup(BLANK);
			}
			if(customerVO.getAgentCode() != null){
				form.setAgentCode(customerVO.getAgentCode());
			}else{
				form.setAgentCode(BLANK);
			}
			if(customerVO.getAirportCode() != null){
				form.setAirportCode(customerVO.getAirportCode());
			}else{
				form.setAirportCode(BLANK);
			}
			if(customerVO.getContactCustomer() != null){
				form.setPrimaryContact(customerVO.getContactCustomer());
			}else{
				form.setPrimaryContact(BLANK);
			}
			
			invocationContext.target = SUCCESS;
			return;
		}
		
		
	}

	private Collection<ErrorVO> validateDates(
			CustomerEnquiryForm form) {
		log.entering("ListCallingHistoryCommand", "validateDates");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(form.getFromDate() !=null && form.getToDate() != null){
			if ((!("").equals(form.getFromDate()))
					&& (!("").equals(form.getToDate()))) {
				if (!form.getFromDate().equals(form.getToDate())) {
					if (DateUtilities.isGreaterThan(form.getFromDate(), form
							.getToDate(), "dd-MMM-yyyy")) {
						ErrorVO errorVO = new ErrorVO(
								"customermanagement.defaults.custenquiry.msg.err.fromdategreaterthantodate");
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(errorVO);
					}
				}
			}
		}
		
		log.exiting("ListCallingHistoryCommand", "validateDates");
		return errors;
	}
	
	
}
