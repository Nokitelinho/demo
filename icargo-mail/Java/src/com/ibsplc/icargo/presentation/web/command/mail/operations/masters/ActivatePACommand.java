/*
 * ActivatePACommand.java Created on June 17, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.masters;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
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
 * @author A-3108
 *
 */
public class ActivatePACommand  extends BaseCommand{
	
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = 
						"mailtracking.defaults.masters.postaladministration";
	private static final String STATUS_NEW="NEW";
	private static final String STATUS_ACTIVE="ACTIVE";
	private static final String STATUS_INACTIVE="INACTIVE";
	private Log log = LogFactory.getLogger("SavePACommand");
	private static final String SUCCESS = "save_success";
	private static final String FAILURE = "save_failure";
	private static final String ACTIVATE_SUCCESS = 
		"mailtracking.defaults.pamaster.msg.info.activatesuccess";
	private static final String PEFORMAINVOICECHECK_ON="on";
	

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
    	log.log(Log.FINE, "\n\n in the save command----------> \n\n");
    	
    	PostalAdministrationForm paMasterForm =
						(PostalAdministrationForm)invocationContext.screenModel;
		PostalAdministrationSession paSession = 
						getScreenSession(MODULE_NAME,SCREEN_ID);
    	
		//LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	    Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    
	    PostalAdministrationVO paVO = new PostalAdministrationVO();
		if(paSession.getPaVO() != null){
			paVO = paSession.getPaVO();
			paVO.setOperationFlag(paMasterForm.getOpFlag());
			paVO.setRemarks(paMasterForm.getRemarks());
			paVO.setStatus(STATUS_ACTIVE);	
			if("on".equals(paMasterForm.getAutoEmailReqd())){
				paVO.setAutoEmailReqd(PostalAdministrationVO.FLAG_YES);
			}
			//added by A-7371 as part of ICRD-229820
			if (paMasterForm.getProformaInvoiceRequired()!=null && (PEFORMAINVOICECHECK_ON.equals(paMasterForm.getProformaInvoiceRequired()))) {
				paVO.setProformaInvoiceRequired(MailConstantsVO.FLAG_YES);
			} else {
				paVO.setProformaInvoiceRequired(MailConstantsVO.FLAG_NO);
			}
			
		}
			
				
			
			
		
log.log(Log.FINE, "\n\n paVO----------> ", paVO);
		try {
			new MailTrackingDefaultsDelegate().savePACode(paVO);
		}catch(BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		
		if(errors != null && errors.size()>0) {
			invocationContext.addAllError(errors);
			paSession.setPaVO(paVO);
			invocationContext.target = FAILURE;
	    	return;
		}
		
		
		ErrorVO error = new ErrorVO(ACTIVATE_SUCCESS);
		errors.add(error);
		invocationContext.addAllError(errors);
		
    	paMasterForm.setPaCode("");
    	paMasterForm.setPaName("");
    	paMasterForm.setCountryCode("");
    	paMasterForm.setAddress("");
    	paMasterForm.setPartialResdit(false);
    	paMasterForm.setMsgEventLocationNeeded(false);
    	//paMasterForm.setSettlementCurrencyCode("");
    	paMasterForm.setBaseType("");
    	//paMasterForm.setBillingFrequency("");
    	//paMasterForm.setBillingSource("");
    	paMasterForm.setStatus("");
    	paMasterForm.setDebInvCode("");
    	paMasterForm.setConPerson("");
    	paMasterForm.setCity("");
    	paMasterForm.setState("");
    	paMasterForm.setCountry("");
    	paMasterForm.setMobile("");
    	paMasterForm.setPostCod("");
    	paMasterForm.setPhone1("");
    	paMasterForm.setPhone2("");
    	paMasterForm.setFax("");
    	paMasterForm.setEmail("");
    	paMasterForm.setRemarks(""); 
    	paMasterForm.setAccNum("");
    	paMasterForm.setVatNumber("");
    	paMasterForm.setProformaInvoiceRequired("");//added by A-7371 as part of ICRD-229820
    	paSession.setPaVO(null);
    	paSession.setPostalAdministrationDetailsVOs(null);
    	
    	paMasterForm.setOpFlag(OPERATION_FLAG_INSERT);
    	paMasterForm.setStatusActiveOrInactive(STATUS_ACTIVE);
    	paMasterForm.setScreenStatusFlag
     					(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = SUCCESS;	

}
}
