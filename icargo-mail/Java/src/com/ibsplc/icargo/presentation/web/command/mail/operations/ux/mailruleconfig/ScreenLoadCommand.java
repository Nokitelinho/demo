package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailruleconfig;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailRuleConfigModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ScreenLoadCommand extends AbstractCommand {
	
	private static final String CATEGORY = "mailtracking.defaults.mailcategory";

	private Log log = LogFactory.getLogger("OPERATIONS MAIL");
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		
		this.log.entering("ScreenLoadCommand", "execute");
		 ResponseVO responseVO = new ResponseVO();
		    actionContext.setResponseVO(responseVO);
		    
			LogonAttributes logonAttributes = (LogonAttributes)getLogonAttribute();
			MailRuleConfigModel mailRuleConfigModel = (MailRuleConfigModel) actionContext.getScreenModel();
			SharedDefaultsDelegate sharedDefaultsDelegate = 
				new SharedDefaultsDelegate(); 
			Map<String, Collection<OneTimeVO>> oneTimeValues = null;
			
			try {
				oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
						logonAttributes.getCompanyCode(), getOneTimeParameterTypes());			
			} catch (BusinessDelegateException e) {
				actionContext.addAllError(handleDelegateException(e));
			}
			mailRuleConfigModel.setOneTimeValues(MailOperationsModelConverter.constructOneTimeValues(oneTimeValues));
			
			List<MailRuleConfigModel> results = new ArrayList<>();
			results.add(mailRuleConfigModel); 
			responseVO.setResults(results);	
			actionContext.setResponseVO(responseVO);
			log.exiting("ScreenLoadCommand", "execute");
		
	}
	
	
	/**
	 * The method which returns the collection of
	 * onetime parameter types
     * @return parameterTypes
     */
    private Collection<String> getOneTimeParameterTypes() {
   	
    	Collection<String> parameterTypes = new ArrayList<String>();
    	parameterTypes.add(CATEGORY); 
    	return parameterTypes;    	
    }
	
}