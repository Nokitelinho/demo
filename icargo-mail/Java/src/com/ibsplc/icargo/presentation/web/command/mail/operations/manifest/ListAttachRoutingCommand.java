/*
 * ListAttachRoutingCommand.java Created on Jul 1 2016
 *
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.manifest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailManifestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */

public class ListAttachRoutingCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   
   private static final String TARGET = "screenload_success";
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailmanifest";
   private static final String STATUS_NO_RESULTS = "mailtracking.defaults.consignment.status.noresultsfound";

   /**
    * TARGET
    */
   private static final String LIST_SUCCESS = "list_success";
   private static final String BLANK = "";
  
    /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ListAttachRoutingCommand","execute");    	  
    	MailManifestForm mailManifestForm = (MailManifestForm)invocationContext.screenModel;
    	MailManifestSession mailManifestSession = getScreenSession(MODULE_NAME,SCREEN_ID);
    	Page<MailInConsignmentVO> mailbagpage=null;
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		
		/*
		    * Getting OneTime values
		    */
	       Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
		   if(oneTimes!=null){
			 
			   Collection<OneTimeVO> typeVOs = oneTimes.get("mailtracking.defaults.consignmentdocument.type");
			  
			   log.log(Log.FINE, "*******Getting OneTimeVOs***typeVOs***", typeVOs.size());
			mailManifestSession.setOneTimeType(typeVOs);
			}	
		   
		   
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	mailManifestSession.setConsignmentDocumentVO(null);
    	
    	
    	mailManifestForm.setSelectMail(null);
    
    	errors = validateForm(mailManifestForm,logonAttributes);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			mailManifestForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		}else{ 
			ConsignmentFilterVO consignmentFilterVO = new ConsignmentFilterVO();
			consignmentFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			consignmentFilterVO.setConsignmentNumber(mailManifestForm.getConDocNo().toUpperCase());
			consignmentFilterVO.setPaCode(mailManifestForm.getPaCode().toUpperCase());
			consignmentFilterVO.setScannedOnline(MailConstantsVO.FLAG_YES);
			
			ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
			try {
				consignmentDocumentVO = new MailTrackingDefaultsDelegate().
				        findConsignmentDocumentDetails(consignmentFilterVO);
				
						
			} catch (BusinessDelegateException businessDelegateException) {
				businessDelegateException.getMessageVO().getErrors();
				handleDelegateException(businessDelegateException);
			}
			
			log.log(Log.FINE, "consignmentDocumentVO ===>>>>",
					consignmentDocumentVO);
			if(consignmentDocumentVO == null) {
				consignmentDocumentVO = new ConsignmentDocumentVO();
				consignmentDocumentVO.setOperationFlag("I");
				//Added for bug 82870 starts
				mailManifestForm.setNewRoutingFlag("Y");
//				Added for bug 82870 ends
				invocationContext.addError(new ErrorVO(STATUS_NO_RESULTS));
				//mailManifestForm.setDisableListSuccess("N");
				log.log(Log.FINE, "consignmentDocumentVO IS NULL");
			}else {
				consignmentDocumentVO.setOperationFlag("U");
				log.log(Log.FINE, "consignmentDocumentVO IS not NULL",
						consignmentDocumentVO);
				if(consignmentDocumentVO.getRoutingInConsignmentVOs()==null  || consignmentDocumentVO.getRoutingInConsignmentVOs().size()==0){
					mailManifestForm.setNewRoutingFlag("Y");
				}
				mailManifestForm.setDirection(consignmentDocumentVO.getOperation());
				int totalRecords = 0;
				if(consignmentDocumentVO.getMailInConsignmentVOs()!=null){
					if(mailManifestSession.getMailVOs()!=null && mailManifestSession.getMailVOs().size()>0){
						consignmentDocumentVO.getMailInConsignmentcollVOs().addAll(mailManifestSession.getMailVOs());
					}
				}else {
					consignmentDocumentVO.setMailInConsignmentcollVOs(mailManifestSession.getMailVOs());
				}
			}
			consignmentDocumentVO.setCompanyCode(logonAttributes.getCompanyCode());
			consignmentDocumentVO.setConsignmentNumber(mailManifestForm.getConDocNo().toUpperCase());
			consignmentDocumentVO.setPaCode(mailManifestForm.getPaCode().toUpperCase());
			mailManifestSession.setConsignmentDocumentVO(consignmentDocumentVO);
			mailManifestForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		}

		invocationContext.target = LIST_SUCCESS;
		log.exiting("ListConsignmentCommand","execute");
	}
	

	/**
	 * Method to validate form.
	 * @param consignmentForm
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(MailManifestForm mailManifestForm,LogonAttributes logonAttributes) {
		String conDocNo = mailManifestForm.getConDocNo();
		String paCode = mailManifestForm.getPaCode();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(conDocNo == null || ("".equals(conDocNo.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.consignment.condocno.empty"));
		}
		if(paCode == null || ("".equals(paCode.trim()))){
			errors.add(new ErrorVO("mailtracking.defaults.consignment.pacode.empty"));
		}else{
//    	validate PA code
	  	log.log(Log.FINE, "Going To validate PA code ...in command");
			try {
		  			PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
					postalAdministrationVO  = new MailTrackingDefaultsDelegate().findPACode(
									logonAttributes.getCompanyCode(),paCode.toUpperCase());	  			
		   			if(postalAdministrationVO == null) {
		  				Object[] obj = {paCode.toUpperCase()};
		  				errors.add(new ErrorVO("mailtracking.defaults.consignment.pacode.invalid",obj));
		  			}
		  	
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
		return errors;
	}
	
	/**

	/**
	 * This method will be invoked at the time of screen load
	 * @param companyCode
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try{
			Collection<String> fieldValues = new ArrayList<String>();
			
			fieldValues.add("mailtracking.defaults.consignmentdocument.type");
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}   
}
