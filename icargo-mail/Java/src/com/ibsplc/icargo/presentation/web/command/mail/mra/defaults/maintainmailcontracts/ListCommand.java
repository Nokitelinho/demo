/*
 * ListCommand.java Created on Apr 02, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainmailcontracts;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainMailContractsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainMailContractsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class ListCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MaintainMAilConttracts ScreenloadCommand");

	private static final String CLASS_NAME = "ListCommand";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintainmailcontracts";
	private static final String ACTION_SUCCESS = "screenload_success";
	private static final String DETAILS_FAILURE="screenload_failure";
	private static final String CONTRACT_NUM_MANDATORY="mailtracking.mra.defaults.msg.err.contractnummandatory";
	private static final String VERSION_MANDATORY="mailtracking.mra.defaults.msg.err.versionmandatory";
	private static final String CREATE_NEW="mailtracking.mra.defaults.msg.err.createnew";
	private static final String BLANK="";
	private static final String SCREEN_LIST="list";
	private static final String SCREEN_SCREENLOAD="screenload";
	private static final String SCREEN_NOSAVE="nosave";
	private static final String SCREEN_NEW="new";
	private static final String NO_DATA="mailtracking.mra.defaults.msg.err.nodata";
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	 public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {
	log.entering(CLASS_NAME, "execute");
	MaintainMailContractsForm form=(MaintainMailContractsForm)invocationContext.screenModel;
	MaintainMailContractsSession session=(MaintainMailContractsSession)getScreenSession(MODULE_NAME,SCREEN_ID);
	String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
	
	MailTrackingMRADelegate delegate=new MailTrackingMRADelegate(); 
	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	ErrorVO errorVO=null;
	String contractNumber=form.getContractRefNumber();
	String version=form.getVersion();
	String contractNum="";
	String ver="";
	if(contractNumber!=null && contractNumber.trim().length()>0){
		contractNum=contractNumber.trim().toUpperCase();
	}else{
		errorVO=new ErrorVO(CONTRACT_NUM_MANDATORY);
		errors.add(errorVO);
	}
	if(version!=null && version.trim().length()>0){
		
		ver=version.trim().toUpperCase();
	}else{
		errorVO=new ErrorVO(VERSION_MANDATORY);
		errors.add(errorVO);
	}
	 if(errors!=null && errors.size()>0){
    	 log.log(Log.FINE,"!!!inside errors!= null");
			invocationContext.addAllError(errors);
			session.removeMailContractDetails();
			session.removeMailContractVO();
			clearFormValue(form);
			invocationContext.target=DETAILS_FAILURE;
			return;
		}
	 log.log(Log.INFO, "FILTER VO", contractNum);
	MailContractVO mailContractVO=null;
	 try{
		mailContractVO=delegate.viewMailContract(companyCode,contractNum,ver);
	 }catch(BusinessDelegateException businessDelegateException){
		errors=handleDelegateException(businessDelegateException); 
	 }
	 
	 
	if(mailContractVO!=null){
		log.log(Log.INFO, "vo from listing", mailContractVO);
		session.setMailContractVO(mailContractVO);
		session.setMailContractDetails(mailContractVO.getMailContractDetailsVos());
		setFormValues(form,mailContractVO);
		if("LATEST".equals(form.getVersion())&& "D".equals(mailContractVO.getAgreementStatus())) {
			form.setScreenStatus(SCREEN_LIST);
		} else {
			form.setScreenStatus(SCREEN_NOSAVE);
		}
	}else{
		
		if("LATEST".equals(form.getVersion())){
			errorVO=new ErrorVO(CREATE_NEW);
			form.setScreenStatus(SCREEN_NEW);
			form.setAgreementStatus("D");
		}else{
			errorVO=new ErrorVO(NO_DATA);
			form.setScreenStatus(SCREEN_SCREENLOAD);
		}
		
		errors.add(errorVO);
	}
	if(errors!=null && errors.size()>0){
   	 log.log(Log.FINE,"!!!inside errors!= null");
			invocationContext.addAllError(errors);
			session.removeMailContractDetails();
			session.removeMailContractVO();
			clearFormValue(form);
			invocationContext.target=DETAILS_FAILURE;
			return;
		}
	invocationContext.target = ACTION_SUCCESS;
	log.exiting(CLASS_NAME, "execute");
	 }
	 /**
	 * @param form
	 * @param vo
	 */
	private void setFormValues(MaintainMailContractsForm form,MailContractVO vo){
			form.setDescription(vo.getContractDescription());
			form.setPaCode(vo.getGpaCode());
			form.setAirlineCode(vo.getAirlineCode());
			form.setAgreementStatus(vo.getAgreementStatus());
			form.setAgreementType(vo.getAgreementType());
			if(vo.getValidFromDate()!=null) {
				form.setFromDate(vo.getValidFromDate().toDisplayDateOnlyFormat());
			}
			if(vo.getValidToDate()!=null) {
				form.setToDate(vo.getValidToDate().toDisplayDateOnlyFormat());
			}
			
			if(vo.getBillingDetails()!=null){
			ArrayList<String> billvo=new ArrayList<String>(vo.getBillingDetails());
			StringBuilder sbul=new StringBuilder();
			for(String bill:billvo){
				if(bill!=null && bill.trim().length()>0) {
					sbul.append(bill).append(",");
				}
			}
			form.setBillingMatrix(sbul.toString());
			}
			
			
	 }
	
	/**
	 * @param form
	 */
	private void clearFormValue(MaintainMailContractsForm form){
		form.setDescription(BLANK);
		form.setPaCode(BLANK);
		form.setAirlineCode(BLANK);
		form.setAgreementStatus(BLANK);
		form.setAgreementType(BLANK);
		form.setFromDate(BLANK);
		form.setToDate(BLANK);
		form.setBillingMatrix(BLANK);
	
	}
}
