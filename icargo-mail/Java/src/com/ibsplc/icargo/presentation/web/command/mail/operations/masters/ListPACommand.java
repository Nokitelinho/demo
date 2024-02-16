/*
 * ListPACommand.java Created on June 19, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.masters;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.PostalAdministrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PostalAdministrationForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2047
 *
 */
public class ListPACommand extends BaseCommand {

	private static final String SUCCESS = "list_success";
	private static final String FAILURE = "list_failure";
	private Log log = LogFactory.getLogger("ListOECommand");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = 
						"mailtracking.defaults.masters.postaladministration";
	
	private static final String NO_MATCH = "mailtracking.defaults.pamaster.msg.err.noMatch";
	private static final String PA_CODE_EMPTY = 
							"mailtracking.defaults.pamaster.msg.err.paempty";
	
	
 
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
    	log.log(Log.FINE, "\n\n in the list command----------> \n\n");
    	
    	PostalAdministrationForm paMasterForm =
						(PostalAdministrationForm)invocationContext.screenModel;
    	PostalAdministrationSession paSession = 
										getScreenSession(MODULE_NAME,SCREEN_ID);

    	
    	ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
	    
	    Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    
	    PostalAdministrationVO paVO = new PostalAdministrationVO();
	    
	    MailTrackingDefaultsDelegate delegate = 
	    									new MailTrackingDefaultsDelegate();
	  //Added by A-7794 as part of ICRD-229736
	    paMasterForm.setParCode(null);
 	    if(("").equals(paMasterForm.getPaCode())) {
	    	log.log(Log.FINE, "\n\n PA_CODE_EMPTY =============> \n\n");
	    	ErrorVO error = new ErrorVO(PA_CODE_EMPTY);	    	
	    	errors.add(error);	
	    	
	    	invocationContext.addAllError(errors);
	    	paSession.setPaVO(null);
	    	paMasterForm.setPaCode("");
	    	paMasterForm.setPaName("");
	    	paMasterForm.setCountryCode("");
	    	paMasterForm.setAddress("");
	    	paMasterForm.setScreenStatusFlag
						(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	    	invocationContext.target = FAILURE;
	    	return;
    	}	
 	  
	    if(errors != null && errors.size()>0) {
			invocationContext.addAllError(errors);
			paSession.setPaVO(paVO);
			invocationContext.target = FAILURE;
	    	return;
		}
	   
 	   String pa = paMasterForm.getPaCode().toUpperCase();
 	 
 	   
 	   try {
 		   
 			   paVO = delegate.findPACode(companyCode,pa);
 			   log.log(Log.FINE, "\n\n paVO =========>", paVO);
			if(paVO == null ) {
 				   log.log(Log.SEVERE,"\n\n *******no match found********** \n\n");
 				   ErrorVO error = new ErrorVO(NO_MATCH);
 				   errors.add(error);
 				   invocationContext.addAllError(errors);
 				   paSession.setPaVO(null);
 				   //    	paMasterForm.setPaCode("");
 				   paMasterForm.setPaName("");
 				   paMasterForm.setCountryCode("");
 				   paMasterForm.setAddress("");		    	
 				   paMasterForm.setOpFlag(OPERATION_FLAG_INSERT);
 				   
 				   paMasterForm.setScreenStatusFlag
 				   (ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
 				   invocationContext.target = SUCCESS;
 				   return;
 			  	   }
 	   }catch(BusinessDelegateException businessDelegateException) {
	    	handleDelegateException(businessDelegateException);	    
	    	ErrorVO error = new ErrorVO(NO_MATCH);
			errors.add(error);
			invocationContext.addAllError(errors);
			paMasterForm.setOpFlag(OPERATION_FLAG_INSERT);
	    	
	    	paMasterForm.setScreenStatusFlag
					(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    	invocationContext.target = SUCCESS;
	    	return;
	    	
	    }
	    
	    paSession.setPaVO(paVO);
	    paSession.setPostalAdministrationDetailsVOs(paVO.getPostalAdministrationDetailsVOs());
	   // paMasterForm.setBillingFrequency(paVO.getBillingFrequency());
	   // paMasterForm.setBillingSource(paVO.getBillingSource());
	    paMasterForm.setBaseType(paVO.getBaseType());
	  //Added by A-5200 for the ICRD-78230 starts
		if(paVO.getGibCustomerFlag()!=null && "Y".equals(paVO.getGibCustomerFlag()))
    	{
			paMasterForm.setGibCustomerFlag("on");
    	}else{
    		paMasterForm.setGibCustomerFlag("off");
    	}	
	  //Added by A-5200 for the ICRD-78230 ends
		
	 //Added by A-6991 for the ICRD-211662 Starts
		if(paVO.getProformaInvoiceRequired()!=null && (MailConstantsVO.FLAG_YES.equals(paVO.getProformaInvoiceRequired()) ||
				paVO.getProformaInvoiceRequired().equalsIgnoreCase("on"))){
			paVO.setProformaInvoiceRequired("on");
			paMasterForm.setProformaInvoiceRequired("on");
    	}else{
    		paVO.setProformaInvoiceRequired("off");
    		paMasterForm.setProformaInvoiceRequired("off");
    	}	 
	  //Added by A-6991 for the ICRD-211662 ends
		
	    if(PostalAdministrationVO.FLAG_YES.equals(paVO.getAutoEmailReqd())){
	    	paVO.setAutoEmailReqd("on"); 
	    }
	    if(paVO.getStatus()==null){
	    paVO.setStatus("NEW");
	    }
	    else{
	    	paMasterForm.setStatusActiveOrInactive(paVO.getStatus());
	    }

	    paMasterForm.setOpFlag(OPERATION_FLAG_UPDATE);
	    
	    paMasterForm.setScreenStatusFlag
							(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    invocationContext.target = SUCCESS;

	}
	
}
