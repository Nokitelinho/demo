package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailruleconfig;
import java.util.ArrayList;

import java.util.List;
import com.ibsplc.icargo.business.mail.operations.vo.MailRuleConfigVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailRuleConfigModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailRuleConfig;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class SaveCommand extends AbstractCommand{


	private Log log = LogFactory.getLogger("OPERATIONS MAIL");
	private  static final String SUCCESS_MESSAGE="Saved Successfully";
	private static final String SUCCESS="success";	
	
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		
		this.log.entering("SaveCommand", "execute");
		LogonAttributes logonAttributes = getLogonAttribute();
		MailRuleConfigModel mailRuleConfigModel = (MailRuleConfigModel)actionContext.getScreenModel(); //Model
		List<MailRuleConfig> mailRuleConfigList = mailRuleConfigModel.getMailRuleConfigList();
		List<ErrorVO> errorVOs = null;
		if(!mailRuleConfigList.isEmpty()){
		for(MailRuleConfig mailRuleConfig:mailRuleConfigList){
			MailRuleConfigVO mailRuleConfigVO=MailOperationsModelConverter.convertMailRuleConfig(mailRuleConfig);
			mailRuleConfigVO.setCompanyCode(logonAttributes.getCompanyCode());
			try {
				new MailTrackingDefaultsDelegate().saveMailRuleConfiguration(mailRuleConfigVO);
			}catch (BusinessDelegateException businessDelegateException) {
				 handleDelegateException(businessDelegateException);
				 errorVOs = handleDelegateException(businessDelegateException);
				 actionContext.addAllError(errorVOs);
				 return;
			}	
		}
		}
		
		
		
		
		ResponseVO responseVO = new ResponseVO();
		ArrayList<MailRuleConfigModel> results = new ArrayList<>();
		results.add(mailRuleConfigModel);
		responseVO.setResults(results);
		responseVO.setStatus(SUCCESS);
		ErrorVO error = new ErrorVO(SUCCESS_MESSAGE);      
		error.setErrorDisplayType(ErrorDisplayType.INFO);
		actionContext.addError(error); 
	    actionContext.setResponseVO(responseVO);

		log.exiting("SaveCommand","execute");
		
	}
}