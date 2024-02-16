package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailruleconfig;


import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.MailboxIdVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailRuleConfigModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailRuleConfig;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ValidateMailRuleConfigCommand extends AbstractCommand {
	
	private static final String CATEGORY = "mailtracking.defaults.mailcategory";

	private Log log = LogFactory.getLogger("OPERATIONS MAIL");
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		
		this.log.entering("ValidateMailRuleConfigCommand", "execute");
		 ResponseVO responseVO = new ResponseVO();
		    actionContext.setResponseVO(responseVO);
		    MailRuleConfigModel mailRuleConfigModel = (MailRuleConfigModel)actionContext.getScreenModel(); //Model
		    LogonAttributes logonAttributes =getLogonAttribute();
			
		    MailboxIdVO mailboxIdVO = new MailboxIdVO();
		    MailRuleConfig mailRuleConfig=mailRuleConfigModel.getMailRuleConfig();
			List<ErrorVO> warningErrors = new ArrayList<ErrorVO>();
			MailTrackingDefaultsDelegate mailtrackingdefaultsdelegate = new MailTrackingDefaultsDelegate();
			
			mailboxIdVO.setMailboxID(mailRuleConfig.getMailboxId());
			mailboxIdVO.setCompanyCode(logonAttributes.getCompanyCode());
			mailboxIdVO= mailtrackingdefaultsdelegate.findMailboxId(mailboxIdVO);
			
			if( mailboxIdVO==null){			
				ErrorVO warningError = new ErrorVO("Mail Box Id does not exist. Do you want to create a new one?");
			    warningError.setErrorDisplayType(ErrorDisplayType.WARNING);
			    warningErrors.add(warningError);
	            actionContext.addAllError(warningErrors);
	            return;
			}
			
			List<MailRuleConfigModel> results = new ArrayList<>();
			results.add(mailRuleConfigModel); 
			responseVO.setResults(results);	
			actionContext.setResponseVO(responseVO);
			log.exiting("ValidateMailRuleConfigCommand", "execute");
		
	}
}