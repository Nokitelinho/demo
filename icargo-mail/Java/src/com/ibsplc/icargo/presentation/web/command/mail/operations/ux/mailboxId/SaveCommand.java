package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailboxId;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailboxIdVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailboxIdModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailEvent;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


public class SaveCommand extends AbstractCommand{

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailboxId";
	private Log log = LogFactory.getLogger("MAIL");
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException {
		this.log.entering("SaveCommand", "execute");
		
		MailboxIdModel mailboxIdmodel = (MailboxIdModel)actionContext.getScreenModel();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
		MailboxIdVO mailboxidVO = new MailboxIdVO();
		
		mailboxidVO.setMailboxID(mailboxIdmodel.getMailboxId());
		mailboxidVO.setCompanyCode(logonAttributes.getCompanyCode());
		mailboxidVO.setMailboxName(mailboxIdmodel.getMailboxName());
		mailboxidVO.setOwnerCode(mailboxIdmodel.getOwnerCode());
		mailboxidVO.setPartialResdit(mailboxIdmodel.isPartialResdit());
		mailboxidVO.setResditTriggerPeriod(mailboxIdmodel.getResditTriggerPeriod());
		mailboxidVO.setMsgEventLocationNeeded(mailboxIdmodel.isMsgEventLocationNeeded());
		mailboxidVO.setResditversion(mailboxIdmodel.getResditversion());
		mailboxidVO.setMessagingEnabled(mailboxIdmodel.getMessagingEnabled());
		mailboxidVO.setMailboxStatus(mailboxIdmodel.getMailboxStatus());
		mailboxidVO.setMailboxOwner(mailboxIdmodel.getMailboxOwner());
		mailboxidVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
		mailboxidVO.setRemarks(mailboxIdmodel.getRemarks());
		 
		 // Validating Owner Code
		if(mailboxIdmodel.getMailboxOwner().equals("AR"))
		{
		AirlineDelegate airlineDelegate = new AirlineDelegate();
         AirlineValidationVO airlineValidationVO = null;
         String ownerCode = mailboxIdmodel.getOwnerCode();
         errors = null;
         if (ownerCode != null && !"".equals(ownerCode)) {
             try {
                 airlineValidationVO = airlineDelegate.validateAlphaCode(
                         logonAttributes.getCompanyCode(),
                         ownerCode.trim().toUpperCase());



             }catch (BusinessDelegateException businessDelegateException) {
                 errors = handleDelegateException(businessDelegateException);
             }
             if (errors != null && errors.size() > 0) {



                 errors = new ArrayList<ErrorVO>();
                 Object[] obj = {ownerCode.toUpperCase()};
                 				ErrorVO error=new ErrorVO("Please Enter Valid OwnerCode",obj);
                 				error.setErrorDisplayType(ErrorDisplayType.ERROR);
                               actionContext.addError(error);
                               return;
             }
         }
		}
         //Validating PA Code
		if(mailboxIdmodel.getMailboxOwner().equals("PA"))
		{
         String pacode = mailboxIdmodel.getOwnerCode();
 		if (pacode != null && pacode.trim().length() > 0) {
 			log.log(Log.FINE, "Going To validate PA code ...in command");
 	  	PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
 		  try {
 			  postalAdministrationVO  = new MailTrackingDefaultsDelegate().findPACode(
 					  logonAttributes.getCompanyCode(),pacode.toUpperCase());
        }catch (BusinessDelegateException businessDelegateException) {
  			errors = handleDelegateException(businessDelegateException);
 	  	  }
 	  	  if (postalAdministrationVO == null) {
 	  		actionContext.addError(new ErrorVO("Please Enter Valid OwnerCode",
 	   				new Object[]{pacode.toUpperCase()}));
// 	  		actionContext.target = TARGET;
 	  		return;
 	  	  }
             }
         }   
//Validating SubClass
         MailEvent mailEvent = new MailEvent();
         MailTrackingDefaultsDelegate delegate=new MailTrackingDefaultsDelegate();
         String mailClass = null;
         if(mailboxIdmodel.getMailEvents() !=null && mailboxIdmodel.getMailEvents().size() >0){
         for (MailEvent mail : mailboxIdmodel.getMailEvents()) {
			  mailClass = mail.getMailClass();
		  }

 			try{		
 				Boolean validFlag=null;
 				validFlag = delegate.validateMailSubClass(logonAttributes.getCompanyCode(),mailClass);	
 				if(!validFlag){
 					errors = new ArrayList<ErrorVO>();
 					Object[] obj = {mailEvent.getMailClass()};
 					ErrorVO errorVO = new ErrorVO("Please Enter Valid SubClass",obj);
 					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
 					errors.add(errorVO);
 					actionContext.addError(errorVO);			
 					return;
 				}

 			}catch (BusinessDelegateException businessDelegateException) {
 				errors = handleDelegateException(businessDelegateException);
 			}

         }

		if(("P").equals(mailboxIdmodel.getMessagingEnabled())){
				mailboxidVO = MailOperationsModelConverter.convertMailevents(mailboxIdmodel.getMailEvents() , logonAttributes, mailboxidVO);
		}
		
		try {
			new MailTrackingDefaultsDelegate().saveMailboxId(mailboxidVO);
		}catch (BusinessDelegateException businessDelegateException) {
			errors=handleDelegateException(businessDelegateException);
		}
		if(mailboxIdmodel.getOperationalFlag()!=null && mailboxIdmodel.getOperationalFlag().equals("ACTIVE"))
		{
			ErrorVO error=new ErrorVO("The mailbox has been inactivated");
	        error.setErrorDisplayType(ErrorDisplayType.INFO);
	        actionContext.addError(error);
		}
		else if(mailboxIdmodel.getOperationalFlag()!=null && mailboxIdmodel.getOperationalFlag().equals("INACTIVE"))
		{
			ErrorVO error=new ErrorVO("The mailbox has been activated");
	        error.setErrorDisplayType(ErrorDisplayType.INFO);
	        actionContext.addError(error);
		}
		else
		{
		ErrorVO error=new ErrorVO("The changes have been saved successfully");
        error.setErrorDisplayType(ErrorDisplayType.INFO);
        actionContext.addError(error);
		}
        log.exiting("SaveCommand","execute");
	}
}
