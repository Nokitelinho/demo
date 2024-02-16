package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailboxId;

import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailboxIdModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ScreenLoadCommand extends AbstractCommand {
	
	private Log log = LogFactory.getLogger("OPERATIONS MAIL");
	public void execute(ActionContext actionContext)throws BusinessDelegateException,
	CommandInvocationException {
		this.log.entering("ScreenLoadCommand", "execute");
		LogonAttributes logonAttributes = getLogonAttribute();
		MailboxIdModel mailboxIdmodel = (MailboxIdModel)actionContext.getScreenModel();
		SharedDefaultsDelegate sharedDefaultsDelegate = 
	    	      new SharedDefaultsDelegate();
		
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
	     try
	     		{
	    	      oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
	    	        logonAttributes.getCompanyCode(), getOneTimeParameterTypes());
	    	    }
	    	    catch (BusinessDelegateException e)
	    	    {
	    	      actionContext.addAllError(handleDelegateException(e));
	    	    }	
	     mailboxIdmodel.setOneTimeValues(oneTimeValues);
	     
	     ResponseVO responseVO = new ResponseVO();
			ArrayList<MailboxIdModel> results = new ArrayList<MailboxIdModel>();
			results.add(mailboxIdmodel);
			responseVO.setResults(results);
		    actionContext.setResponseVO(responseVO);
		    
		    log.exiting("ScreenLoadCommand","execute");
	}

	private Collection<String> getOneTimeParameterTypes() {
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add("mailtracking.defaults.postaladministration.resditversion");
		oneTimeList.add("mailtracking.defaults.mailcategory");
		oneTimeList.add("mail.operations.partytype");
		return oneTimeList;
	}

}
