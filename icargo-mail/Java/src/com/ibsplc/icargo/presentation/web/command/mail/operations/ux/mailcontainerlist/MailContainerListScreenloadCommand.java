package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailcontainerlist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailContainerListModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class MailContainerListScreenloadCommand  extends AbstractCommand{

	
	
	private static final String FLTTYPE_ONETIME_SEARCHMODE = "mailtracking.defaults.containersearchmode";
	private static final String CLASS_NAME = "MailContainerScreenLoadCommand";
	private static final Log LOG = LogFactory.getLogger(CLASS_NAME);
	
	
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		LOG.entering(CLASS_NAME, "execute");
		ArrayList<MailContainerListModel> results = new ArrayList<>();
		ResponseVO responseVO = new ResponseVO();
		LogonAttributes logonAttributes = getLogonAttribute();
		MailContainerListModel mailContainerListModel = (MailContainerListModel) actionContext
				.getScreenModel();
		
		
		Map<String, Collection<OneTimeVO>> onetimes = getOneTimeValues(logonAttributes);
		mailContainerListModel.setOneTimeValues(onetimes);
		
		
		results.add(mailContainerListModel);
		responseVO.setResults(results);
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
		
	
	
	
	
	}
	
	
	
	private Collection<String> getOneTimeParameterTypes() {
		ArrayList<String> parameterTypes = new ArrayList<>();
		parameterTypes.add(FLTTYPE_ONETIME_SEARCHMODE);
		return parameterTypes;
	}
	
	

	private Map<String, Collection<OneTimeVO>> getOneTimeValues(
			LogonAttributes logonAttributes) {
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		try {

			oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(),
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}

		return ((HashMap<String, Collection<OneTimeVO>>) oneTimeValues);
	}
	


}
