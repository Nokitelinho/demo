package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailruleconfig;
import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailRuleConfigModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailRuleConfig;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ListCommand extends AbstractCommand{


	private Log log = LogFactory.getLogger("OPERATIONS MAIL");
	
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		
		this.log.entering("ListCommand", "execute");
		LogonAttributes logonAttributes = (LogonAttributes)getLogonAttribute();
		MailRuleConfigModel mailRuleConfigModel = (MailRuleConfigModel)actionContext.getScreenModel(); //Model
		List<MailRuleConfig> mailRuleConfigList = new ArrayList<MailRuleConfig>();
		MailRuleConfig mailRuleConfig =new MailRuleConfig();
		mailRuleConfigList.add(mailRuleConfig); 
		
		mailRuleConfigModel.setMailRuleConfigList(mailRuleConfigList);
		ResponseVO responseVO = new ResponseVO();
		ArrayList<MailRuleConfigModel> results = new ArrayList<>();
		results.add(mailRuleConfigModel);
		responseVO.setResults(results);
	    actionContext.setResponseVO(responseVO);

		log.exiting("ListCommand","execute");
		
	}
}