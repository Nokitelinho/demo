package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailboxId;

import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.MailEventVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailboxIdVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailboxIdModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailEvent;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ListCommand extends AbstractCommand{

	private Log log = LogFactory.getLogger("OPERATIONS MAIL");
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		
		this.log.entering("ListCommand", "execute");
		LogonAttributes logonAttributes = (LogonAttributes)getLogonAttribute();
		MailboxIdModel mailboxIdmodel = (MailboxIdModel)actionContext.getScreenModel(); //Model
		MailboxIdVO mailboxIdVO = new MailboxIdVO();
		List<MailEvent> mailevents = new ArrayList<MailEvent>();
		List<ErrorVO> warningErrors = new ArrayList<ErrorVO>();
		MailTrackingDefaultsDelegate mailtrackingdefaultsdelegate = new MailTrackingDefaultsDelegate();
		
		mailboxIdVO.setMailboxID(mailboxIdmodel.getMailboxId());
		mailboxIdVO.setCompanyCode(logonAttributes.getCompanyCode());
		mailboxIdVO= mailtrackingdefaultsdelegate.findMailboxId(mailboxIdVO);
		
		if( mailboxIdVO==null){			
			ErrorVO warningError = new ErrorVO("Mail Box Id does not exist. Do you want to create a new one?");
		    warningError.setErrorDisplayType(ErrorDisplayType.WARNING);
		    warningErrors.add(warningError);
            actionContext.addAllError(warningErrors);
            return;
		}else{
		mailboxIdmodel.setMailboxName(mailboxIdVO.getMailboxName());
		mailboxIdmodel.setOwnerCode(mailboxIdVO.getOwnerCode());
		mailboxIdmodel.setResditTriggerPeriod(mailboxIdVO.getResditTriggerPeriod());
		mailboxIdmodel.setPartialResdit(mailboxIdVO.isPartialResdit());
		mailboxIdmodel.setMsgEventLocationNeeded(mailboxIdVO.isMsgEventLocationNeeded());
		mailboxIdmodel.setResditversion(mailboxIdVO.getResditversion());
		mailboxIdmodel.setMessagingEnabled(mailboxIdVO.getMessagingEnabled());
		mailboxIdmodel.setMailboxStatus(mailboxIdVO.getMailboxStatus());
		mailboxIdmodel.setMailboxOwner(mailboxIdVO.getMailboxOwner());
		mailboxIdmodel.setRemarks(mailboxIdVO.getRemarks());
		if(("P").equals(mailboxIdVO.getMessagingEnabled())){
			  for (MailEventVO mail : mailboxIdVO.getMailEventVOs()) {
				  mailevents = MailOperationsModelConverter.constructmailboxId(mail,mailevents);
			  }
			  mailboxIdmodel.setMailEvents(mailevents);
		}
		
		ResponseVO responseVO = new ResponseVO();
		ArrayList<MailboxIdModel> results = new ArrayList<MailboxIdModel>();
		results.add(mailboxIdmodel);
		responseVO.setResults(results);
	    actionContext.setResponseVO(responseVO);
		
	}
	
		log.exiting("ListCommand","execute");
	}
}
