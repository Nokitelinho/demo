/*
 * SaveCommand.java Created on Jan 3, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.changebillingstatus;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ChangeBillingStatusSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ChangeBillingStatusPopupForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3434
 *
 */
public class SaveCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("InterLineEntries ChangeStatusOk");

	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.changestatus";
	private static final String ACTION_SUCCESS = "save_success";
	private static final String KEY_SAME_STATUS="mailtracking.mra.defaults.changestatus.samestatus";
	private static final String KEY_STATUS_CANNOTBEOH="mailtracking.mra.defaults.changestatus.statuscannotbeoh";
	private static final String ACTION_FAILURE = "save_failure";
	private static final String WITHDRAWN_CANT_CHNG_TO_OH="mailtracking.mra.defaults.changestatus.withdrawntooh";
	
	
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
   log.entering("ChangeStatusSaveCommand","execute");
   ChangeBillingStatusPopupForm form=(ChangeBillingStatusPopupForm)invocationContext.screenModel;
   ChangeBillingStatusSession popupsession = (ChangeBillingStatusSession) getScreenSession(
 				MODULE_NAME, SCREEN_ID);
 		
   MailTrackingMRADelegate mailTrackingMRADelegate=new MailTrackingMRADelegate ();
   Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
   Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs=null;
   documentBillingDetailsVOs = popupsession.getDocumentBillingDetailvoCol();
  
   String bilingType="";
   if(documentBillingDetailsVOs!=null && documentBillingDetailsVOs.size()!=0){
	   for(DocumentBillingDetailsVO documentBillingDetailsVO:documentBillingDetailsVOs){

		   /*if (form.getBillingStatus().equals(documentBillingDetailsVO.getBillingStatus())){

    		errors.add(new ErrorVO(KEY_SAME_STATUS));
    		invocationContext.addAllError(errors);

			invocationContext.target = ACTION_FAILURE;
			return;
		}*/

		   //If it is a record from MTKMRACCADTL,status cannot be changed

		 /*  if (documentBillingDetailsVO.getCcaRefNumber()!=null && 
				   !"Y".equals(documentBillingDetailsVO.getMemoFlag())){
			   errors.add(new ErrorVO(KEY_STATUS_CANNOTBEOH));
			   invocationContext.addAllError(errors);

			   invocationContext.target = ACTION_FAILURE;
			   return;
		   }*/
		   //Added by A-6991 for ICRD-211662
		   if(!"O".equalsIgnoreCase(documentBillingDetailsVO.getIntblgType())){
		   documentBillingDetailsVO.setIntblgType(MRAConstantsVO.BLGTYPE_GPA);  
		   }
		   bilingType=documentBillingDetailsVO.getIntblgType();
		   if (MRAConstantsVO.ONHOLD.equalsIgnoreCase(form.getBillingStatus()) && 
				   MRAConstantsVO.WITHDRAWN.equalsIgnoreCase(documentBillingDetailsVO.getBillingStatus())){

	    		errors.add(new ErrorVO(WITHDRAWN_CANT_CHNG_TO_OH));
	    		invocationContext.addAllError(errors);

				invocationContext.target = ACTION_FAILURE;
				return;
		   }
		   if(form.getBillingStatus()!=null && form.getBillingStatus().trim().length()>0){

			   documentBillingDetailsVO.setBillingStatus(form.getBillingStatus());
		   }

		   if(form.getPopupRemarks()!=null && form.getPopupRemarks().trim().length()>0){


			   documentBillingDetailsVO.setRemarks(form.getPopupRemarks());

		   }else{
			   documentBillingDetailsVO.setRemarks("");
		   }


	   }
    	}
   		log.log(Log.FINE, "documentBillingDetailsVOcol... >>",
				documentBillingDetailsVOs);
			try {
    			
    			// calling the Delegate
				if(MRAConstantsVO.BLGTYPE_GPA.equals(bilingType)){
    			 
    		mailTrackingMRADelegate.changeStatus(documentBillingDetailsVOs);
				}else{
					mailTrackingMRADelegate.changeStatusForInterline(documentBillingDetailsVOs);
				}
    			

    		} catch (BusinessDelegateException e) {
    			errors = handleDelegateException(e);

    		}
    		if(errors != null && errors.size() > 0 ){
        		invocationContext.target = ACTION_FAILURE;
        		invocationContext.addAllError(errors);
        		return;
        	}
    		form.setScreenStatus("popupSave");
    		log.log(Log.FINE, "ScreenStatus: ", form.getScreenStatus());
			invocationContext.target = ACTION_SUCCESS;
    		log.exiting("ChangeStatusCommandSave", "execute");

    }

}
